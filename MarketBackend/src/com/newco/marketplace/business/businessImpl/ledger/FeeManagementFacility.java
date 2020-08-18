package com.newco.marketplace.business.businessImpl.ledger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.dto.vo.fee.FeeConstants;
import com.newco.marketplace.dto.vo.fee.FeeInfoItemVO;
import com.newco.marketplace.dto.vo.fee.FeeInfoVO;
import com.newco.marketplace.dto.vo.fee.PromoConstants;
import com.newco.marketplace.dto.vo.ledger.LedgerBusinessTransactionVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.ledger.TransactionEntryVO;
import com.newco.marketplace.dto.vo.ledger.TransactionVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.persistence.iDao.feemanager.FeeDao;
import com.newco.marketplace.persistence.iDao.feemanager.FeeManagerDao;
import com.newco.marketplace.utils.MoneyUtil;


/**
 * $Revision: 1.30 $ $Author: glacy $ $Date: 2008/04/26 00:40:29 $
 */
public class FeeManagementFacility implements FeeConstants{
	private FeeManagerDao feeManagerDao;
	private FeeDao feeDao;
	private PromoBO promoBO;
	
	private static final Logger logger = Logger.getLogger(FeeManagementFacility.class.getName());
	
	/**
	 * @param marketVO
	 * @param business
	 * @return
	 */
	public FeeInfoVO calculateFee(MarketPlaceTransactionVO marketVO, LedgerBusinessTransactionVO business){		
		FeeInfoVO feeInfoVo = null;
		try {
			ArrayList<TransactionVO> transactionVOs = business.get_transactions();
			for(int i=0; i<transactionVOs.size(); i++){
				TransactionVO transactionVO = transactionVOs.get(i);
				marketVO.setLedgerEntryRuleId(transactionVO.getLedgerEntryRuleId());
				feeInfoVo = feeManagerDao.getLedgerFee(marketVO);
				ArrayList<FeeInfoItemVO> feeInfoItemVoAL = feeInfoVo.getFeeInfoItem();
						
				if(feeInfoVo.getLedgerFeeTypeId().intValue() == PENALITY_TYPE && (feeInfoVo.getLedgerEntryRuleId().intValue() == LedgerConstants.RULE_ID_RELEASE_PENALITY_PAYMENT
						|| marketVO.getLedgerEntryRuleId().intValue() == LedgerConstants.RULE_ID_SHC_RELEASE_PENALITY_PAYMENT)){
					double cancellationPenalty = calculateCancellationPenality(marketVO.getServiceOrder(),feeInfoItemVoAL);
					feeInfoVo.setFeeAmount(cancellationPenalty);
					logger.info("cancellationPenalty "+cancellationPenalty);
					return feeInfoVo;
				}
			}
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return feeInfoVo;
	}
	
	/**
	 * @param marketVO
	 * @param business
	 */
	public void populateLedgerAmounts(MarketPlaceTransactionVO marketVO, LedgerBusinessTransactionVO business)
	{		
		FeeInfoVO feeInfoVo = null;
		FeeInfoVO feeInfoVo1 = null;
		try {
			ArrayList<TransactionVO> transactionVOs = business.get_transactions();
			for(int i=0; i<transactionVOs.size(); i++){
				TransactionVO transactionVO = transactionVOs.get(i);
				marketVO.setLedgerEntryRuleId(transactionVO.getLedgerEntryRuleId());
				//feeInfoVo = feeDao.getLedgerFee(marketVO);
				feeInfoVo = getFeeManagerDao().getLedgerFee(marketVO);
				ArrayList<FeeInfoItemVO> feeInfoItemVoAL = feeInfoVo.getFeeInfoItem();
						
				if(feeInfoVo.getLedgerFeeTypeId().intValue() == PENALITY_TYPE && (feeInfoVo.getLedgerEntryRuleId().intValue() ==  LedgerConstants.RULE_ID_RELEASE_PENALITY_PAYMENT
						|| marketVO.getLedgerEntryRuleId().intValue() == LedgerConstants.RULE_ID_SHC_RELEASE_PENALITY_PAYMENT))
				{
					double cancellationPenalty = calculateCancellationPenality(marketVO.getServiceOrder(),feeInfoItemVoAL);
					feeInfoVo.setFeeAmount(cancellationPenalty);
					logger.info("cancellationPenalty "+cancellationPenalty);
					ArrayList<TransactionEntryVO> transactionEntries = transactionVO.getTransactions();
					for(int j=0; j<transactionEntries.size(); j++){
						TransactionEntryVO transactionEntry = transactionEntries.get(j);
						transactionEntry.setTransactionAmount(cancellationPenalty);						
					}
				}
				else if(feeInfoVo.getLedgerEntryRuleId().intValue() == LedgerConstants. RULE_ID_RETRUN_SO_FUNDING)
				{
					// TODO: If it is a fee: Service or Access.
					double orderAmount = calcOrderLimitAmt(marketVO);
					//feeInfoVo.setFeeAmount(orderAmount);
					logger.info("orderAmount "+orderAmount);
					ArrayList<TransactionEntryVO> transactionEntries = transactionVO.getTransactions();
					for(int j=0; j<transactionEntries.size(); j++){
						TransactionEntryVO transactionEntry = transactionEntries.get(j);
						transactionEntry.setTransactionAmount(orderAmount);						
					}					
				}
			}
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}
	

	/**
	 * Description:
	 * @param marketVO
	 * @param business
	 * @param feeType
	 * @return
	 * @throws Exception
	 */
	public FeeInfoVO calculateFeesAndAmountFromSO(MarketPlaceTransactionVO marketVO, LedgerBusinessTransactionVO business, Integer feeType) throws Exception {
		FeeInfoVO feeInfoVo = new FeeInfoVO();

//		try {
//			ArrayList<TransactionVO> transactionVOs = business.get_transactions();
//			Map<String,String> pricingRules = feeManagerDao.getLedgerRulePricingExpressionsMap();
//			
//			double serviceFeePercentage = promoBO.getPromoFee(marketVO.getServiceOrder(), PromoConstants.SERVICE_FEE_TYPE);	
//			
//			for (int i = 0; i < transactionVOs.size(); i++) {
//				TransactionVO transactionVO = transactionVOs.get(i);
//				marketVO.setLedgerEntryRuleId(transactionVO.getLedgerEntryRuleId());
//				marketVO.setFundingTypeId(business.getFundingTypeId());
//				feeInfoVo.setLedgerEntryRuleId(transactionVO.getLedgerEntryRuleId());
//				
//				
//				double feeAmount =  evalPricingRuleExpression(marketVO, pricingRules,serviceFeePercentage, transactionVO);
//				feeInfoVo.setFeeAmount(feeAmount);
//				if (feeInfoVo.getFeeAmount() < 0.0){
//					feeInfoVo.setFeeAmount(0.0);
//					logger.error("MAJOR BADNESS - TRYING TO USE A NEGATIVE NUMBER FOR TRANSACTION ENTRY:\n Fee Name - " + feeInfoVo.getFeeName()
//							+ "\nFee Amount - " + feeInfoVo.getFeeAmount()
//							+ "\nRule Amount - " + feeInfoVo.getLedgerEntryRuleId()
//							+ "\nFee ID - " + feeInfoVo.getLedgerFeeId());
//				}
//			}
//
//		} catch (Exception e) {
//			logger.error("Error in  calculateFeesAndAmountFromSO ", e);
//		}


		
		return feeInfoVo;
	}

	/**
	 * Description: Order Limit Amount = Spend Limit Labor + Spend Limit Parts
	 * @param marketVO
	 * @return
	 */
	public double calcOrderLimitAmt(MarketPlaceTransactionVO marketVO) {
		double orderLimitAmount = 0.0;
		orderLimitAmount = marketVO.getServiceOrder().getSpendLimitLabor() + marketVO.getServiceOrder().getSpendLimitParts();
		logger.debug("Order Limit Amount " + orderLimitAmount);
		return orderLimitAmount;
	}

	/**
	 * Description: Order Final Amount = Final Spend Labor + Final Spend Parts + Upsells
	 * @param marketVO
	 * @return
	 */
	public double calcOrderFinalAmt(MarketPlaceTransactionVO marketVO) {
		double orderFinalAmount = 0.0;
		double labor = 0.0;
		double parts = 0.0;
		double upsell = 0.0; 
		if (marketVO.getServiceOrder().getLaborFinalPrice() != null){
			labor = marketVO.getServiceOrder().getLaborFinalPrice();
		}
		if (marketVO.getServiceOrder().getPartsFinalPrice() != null){
			parts = marketVO.getServiceOrder().getPartsFinalPrice();
		}
		if (marketVO.getServiceOrder().getUpsellInfo() != null){
			upsell = calculateAddonTot(marketVO.getServiceOrder().getUpsellInfo());
		}
		//Need Money Util here
		orderFinalAmount = MoneyUtil.getRoundedMoney(labor + parts + upsell);
		logger.debug("order Final Amount " + orderFinalAmount);
		return orderFinalAmount;
	}
	
	/**
	 * Description: Add up upsell items 
	 * @param addons
	 * @return
	 */
	public Double calculateAddonTot(List<ServiceOrderAddonVO> addons) {
		Double totAddons = 0.0;
		
		if(null == addons){
			return MoneyUtil.getRoundedMoney(totAddons);
		}
		for (ServiceOrderAddonVO soAddonVO : addons) {
			totAddons = totAddons  + MoneyUtil.getRoundedMoney(   
								soAddonVO.getQuantity() * 
								MoneyUtil.getRoundedMoney(soAddonVO.getRetailPrice() * (1 - soAddonVO.getMargin()))
							); 
		}
		return MoneyUtil.getRoundedMoney(totAddons);
	}
	
	/**
	 * Description: Add up upsell items svc
	 * @param addons
	 * @return
	 */
	public Double calculateAddonSvcFee(
			List<ServiceOrderAddonVO> addons, double serviceFeePercentage) {
		Double totAddonsSvc = 0.0;
		for (ServiceOrderAddonVO soAddonVO : addons) {
			totAddonsSvc= totAddonsSvc+   MoneyUtil.getRoundedMoney(
						
								(
										soAddonVO.getQuantity() * 
										MoneyUtil.getRoundedMoney(
												soAddonVO.getRetailPrice() * (1 - soAddonVO.getMargin()))
								)*
							serviceFeePercentage
							);
		}
		return MoneyUtil.getRoundedMoney(totAddonsSvc);
	}
	
	
	private double calculateCancellationPenality(ServiceOrder order,ArrayList<FeeInfoItemVO> feeInfoItemVoAL)
	{
		double cancelPenality = 0.0;
		double penalityRate = 0.0;
		double penalityCost = 0.0;
				
		Timestamp startDate = order.getServiceDate1();		
		Timestamp endDate = order.getServiceDate2();
		
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		Calendar calendar = Calendar.getInstance();
		long currentTime = calendar.getTimeInMillis();
		logger.info("Inside FeeManagementFacility::::calculateFees:::"
				+startTime+"  "+endTime+"   "+currentTime);
		
		//boolean isStartInRange = checkWithInRange(currentTime,startTime);
		
		// The user is cancelling before the start time.
		if(true)
		{
			//Get the time difference between start time and cancel time 
			long timeDiff = Math.abs((startTime-currentTime));
			
			FeeInfoItemVO itemVo = new FeeInfoItemVO();
			Integer hrsDiff = convertToHours(timeDiff);
			itemVo.setTimePeriod(hrsDiff);	
			feeInfoItemVoAL.add(itemVo);
			
			Collections.sort(feeInfoItemVoAL,new Comparator<Object>()
			{
				public int compare(final Object obj1, final Object obj2)
				{
					final FeeInfoItemVO item1 = (FeeInfoItemVO)obj1;
					final FeeInfoItemVO item2 = (FeeInfoItemVO)obj2;
					
					final Integer hrs1 = item1.getTimePeriod();
					final Integer hrs2 = item2.getTimePeriod();
					
					if(hrs1.intValue() < hrs2.intValue())
						return -1;
					else
					if(hrs1.intValue() > hrs2.intValue())
						return 1;
					else
						return 0;
					
				}
			});
			logger.info("Values of AL");
			for(int i=0;i<feeInfoItemVoAL.size();i++)
				logger.info(feeInfoItemVoAL.get(i).getTimePeriod()+"    "+feeInfoItemVoAL.get(i).getFeeValue());
				
			int position = feeInfoItemVoAL.indexOf(itemVo);
			
			if(position == (feeInfoItemVoAL.size() - 1))
				return penalityCost;
			else
				position++;
			
			FeeInfoItemVO result = (FeeInfoItemVO)feeInfoItemVoAL.get(position);
		
			penalityRate = result.getFeeValue().doubleValue();
						
			if(result.getCalculatedBy().intValue() == PENALITY_TYPE_FIXED)
				return penalityRate;
			else
			if(result.getCalculatedBy().intValue() == PENALITY_TYPE_PERCENTAGE)
			{
				penalityCost = (order.getSpendLimitLabor()+order.getSpendLimitParts()) * penalityRate;
				return penalityCost;
			}
		}
		// The user is cancelling after the start time
		else 
		{				
			Iterator iterator = feeInfoItemVoAL.iterator();			
			while(iterator.hasNext())
			{
				FeeInfoItemVO feeInfoItemVo = (FeeInfoItemVO)iterator.next();
				// The table has a 0 in the hours column. 
				if(feeInfoItemVo.getTimePeriod().intValue() == AFTER_APPOINTMENT_START_TIME_HRS)
				{
					penalityRate = feeInfoItemVo.getFeeValue().doubleValue();
					
					if(feeInfoItemVo.getCalculatedBy().intValue() == PENALITY_TYPE_FIXED)
						return penalityRate;
					else
					if(feeInfoItemVo.getCalculatedBy().intValue() == PENALITY_TYPE_PERCENTAGE)
					{
						penalityCost = (order.getSpendLimitLabor()+order.getSpendLimitParts()) * penalityRate;
						return penalityCost;
					}					
				}				
			}			
		}	
		return cancelPenality;
	}
	
	
		
		
	/**
	 * @param marketVO
	 * @param pricingRules
	 * @param serviceFeePercentage
	 * @param transactionVO
	 * @return double
	 * @throws Exception
	 */
	private double evalPricingRuleExpression(MarketPlaceTransactionVO marketVO,Map<String,String> pricingRules, double serviceFeePercentage, TransactionVO transactionVO) throws Exception
	{
		String pricingRuleExpression = "" ;
		if(pricingRules != null){

			
			if (pricingRules.containsKey(marketVO.getLedgerEntryRuleId()+"_"+marketVO.getFundingTypeId()))
			{
				pricingRuleExpression = pricingRules.get(marketVO.getLedgerEntryRuleId()+"_"+marketVO.getFundingTypeId());
			}
			
		}
		
		String add = "+";
		String subtract = "-";
		boolean expressionExists = false;
		
		String previousOperator = "";
		pricingRuleExpression = StringUtils.deleteWhitespace(pricingRuleExpression);
		StringTokenizer st = null ;
		
		if (StringUtils.isNotBlank(pricingRuleExpression))
		{
			st = new StringTokenizer(pricingRuleExpression, "+-",true);
			expressionExists = true;
		}
		
		
		double  finalTransAmount = 0.0;
	
		
		List<ServiceOrderAddonVO> addons = new ArrayList<ServiceOrderAddonVO>();
		addons = marketVO.getServiceOrder().getUpsellInfo();
		
		while (st != null && st.hasMoreTokens())
		{
			String token = st.nextToken("+-");
			String operator = "";
			double transAmount = 0.0;
			boolean feeConstantFound = false;
			if (StringUtils.equalsIgnoreCase(token, POSTING_FEE)) {
				if (marketVO.getServiceOrder().getPostingFee()!= null)
				{
					transAmount = marketVO.getServiceOrder().getPostingFee();
					feeConstantFound = true;
				}

			}
			else if (StringUtils.equalsIgnoreCase(token, LABOUR_SPEND_LIMIT)) {
				if (marketVO.getServiceOrder().getSpendLimitLabor()!= null)
				{
					transAmount = marketVO.getServiceOrder().getSpendLimitLabor();
					feeConstantFound = true;
				}

			}
			else if (StringUtils.equalsIgnoreCase(token, PARTS_SPEND_LIMIT)) {
				if (marketVO.getServiceOrder().getSpendLimitParts()!= null)
				{
					transAmount = marketVO.getServiceOrder().getSpendLimitParts();
					feeConstantFound = true;
				}

			}
			else if (StringUtils.equalsIgnoreCase(token, FINAL_LABOUR)) {
				if (marketVO.getServiceOrder().getLaborFinalPrice() != null){
					transAmount = marketVO.getServiceOrder().getLaborFinalPrice();
					feeConstantFound = true;
				}

			}
			else if (StringUtils.equalsIgnoreCase(token, FINAL_PARTS)) {
				if (marketVO.getServiceOrder().getPartsFinalPrice() != null){
					transAmount = marketVO.getServiceOrder().getPartsFinalPrice();
					feeConstantFound = true;
				}
			}
			else if (StringUtils.equalsIgnoreCase(token, ACH_AMOUNT)) {
				if (marketVO.getAchAmount()!= null)
				{
					transAmount = marketVO.getAchAmount();
					feeConstantFound = true;
				}
			
			}
			else if (StringUtils.equalsIgnoreCase(token, FINAL_SERVICE_FEE)) {
				double finalPrice = calcOrderFinalAmt(marketVO);
				//double serviceFeeRegular =  MoneyUtil.getRoundedMoney((finalPrice - calculateAddonTot(addons)) * serviceFeePercentage);
				double serviceFeeRegular =  MoneyUtil.getRoundedMoney(finalPrice * serviceFeePercentage);
				transAmount = serviceFeeRegular;
				feeConstantFound = true;
			}
			else if (StringUtils.equalsIgnoreCase(token, UPSELL_PROVIDER_TOTAL)) {
				if (marketVO.getServiceOrder().getUpsellInfo() != null){
					transAmount = calculateAddonTot(marketVO.getServiceOrder().getUpsellInfo());
					feeConstantFound = true;
				}

			}
			/*else if (StringUtils.equalsIgnoreCase(token, UPSELL_SERVICE_FEE)) {
				transAmount =  calculateAddonSvcFee(addons,serviceFeePercentage);
				feeConstantFound = true;

			}*/
			else if (StringUtils.equalsIgnoreCase(token,RETAIL_CANCELLATION_FEE)) {
				if(marketVO.getServiceOrder().getRetailCancellationFee() != null){
					transAmount = marketVO.getServiceOrder().getRetailCancellationFee();
					feeConstantFound = true;
				}
				
			}

			else if (StringUtils.equalsIgnoreCase(token, CANCELLATION_FEE)) {
				if(marketVO.getServiceOrder().getCancellationFee() != null){
					transAmount = marketVO.getServiceOrder().getCancellationFee();
					feeConstantFound = true;
				}
				
			}
			else if (StringUtils.equalsIgnoreCase(token, RETAIL_SO_PRICE)) {
				if(marketVO.getServiceOrder().getRetailPrice() != null){
					transAmount = marketVO.getServiceOrder().getRetailPrice();
					feeConstantFound = true;
				}
				
			}
			else if (StringUtils.equalsIgnoreCase(token, INITIAL_SPEND_LIMIT)) {
				transAmount = calcOrderLimitAmt(marketVO);
				feeConstantFound = true;

			}
			else if (StringUtils.equalsIgnoreCase(token, ZERO)) {
				transAmount = 0.0;
				feeConstantFound = true;

			}
			else if (StringUtils.equalsIgnoreCase(token, add)) {
				operator = add;

			}
			else if (StringUtils.equalsIgnoreCase(token, subtract)) {
				operator = subtract;

			}

			
			if (StringUtils.equals(previousOperator, add)) {
				finalTransAmount = MoneyUtil.getRoundedMoney(finalTransAmount + transAmount);
			}
			else if (StringUtils.equals(previousOperator, subtract)) {
				finalTransAmount =MoneyUtil.getRoundedMoney( finalTransAmount - transAmount);
			}
			else if (feeConstantFound)
			{
				finalTransAmount = transAmount;
			}
			 previousOperator = operator;
			
		}
		
		 if (expressionExists)
		 {
				ArrayList<TransactionEntryVO> transactionEntries = transactionVO.getTransactions();
				for (int j = 0; j < transactionEntries.size(); j++) {
					TransactionEntryVO transactionEntry = transactionEntries.get(j);
					transactionEntry.setTransactionAmount(finalTransAmount);
				}
		 }
		 
		return finalTransAmount;
		
	}
	
	private boolean checkWithInRange(long currentTime, long checkTime)
	{
		if(currentTime >= checkTime)
			return false;
		else
			return true;		
	}
		
	private Integer convertToHours(long time){
	    int milliseconds = (int)(time % 1000);
	    int seconds = (int)((time/1000) % 60);
	    int minutes = (int)((time/60000) % 60);
	    int hours = (int)((time/3600000) % 24);
	    
	    if(milliseconds > 0 || seconds >0 || minutes > 0)
	    	hours++;
	    
	    return hours;
	  }

    public FeeManagerDao getFeeManagerDao() {
    
        return feeManagerDao;
    }

    public void setFeeManagerDao(FeeManagerDao feeManagerDao) {
    
        this.feeManagerDao = feeManagerDao;
    }

    public FeeDao getFeeDao() {
    
        return feeDao;
    }

    public void setFeeDao(FeeDao feeDao) {
    
        this.feeDao = feeDao;
    }

	public PromoBO getPromoBO() {
		return promoBO;
	}

	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}
}
