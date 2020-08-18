package com.servicelive.wallet.ledger;

import java.sql.Timestamp;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.wallet.common.IIdentifierDao;
import com.servicelive.wallet.ledger.dao.ITransactionDao;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO;
import com.servicelive.wallet.ledger.vo.TransactionEntryVO;
import com.servicelive.wallet.ledger.vo.TransactionRuleVO;
import com.servicelive.wallet.ledger.vo.TransactionVO;
import com.servicelive.wallet.serviceinterface.vo.OrderPricingVO;

// TODO: Auto-generated Javadoc
/**
 * Class TransactionBuilder.
 */
public class TransactionBuilder implements ITransactionBuilder {

	/** logger. */
	private static final Logger logger = Logger.getLogger(TransactionBuilder.class.getName());

	/** identifierDao. */
	private IIdentifierDao identifierDao;

	/** transactionDao. */
	private ITransactionDao transactionDao;
	
	private IApplicationProperties applicationProperties;
	
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	/**
	 * buildBusinessTransaction.
	 * 
	 * @param eRules 
	 * @param businessTransId 
	 * 
	 * @return LedgerBusinessTransactionVO
	 */
	private LedgerBusinessTransactionVO buildBusinessTransaction(ArrayList<TransactionRuleVO> eRules, Long businessTransId) {

		LedgerBusinessTransactionVO businessTrans = new LedgerBusinessTransactionVO();

		businessTrans.setBusinessTransId(businessTransId);

		for (TransactionRuleVO rule : eRules) {
			TransactionVO theTransaction = new TransactionVO();
			Long transId = null;
			Long transEntry1 = null;
			Long transEntry2 = null;

			//transId = identifierDao.getNextIdentifier(IIdentifierDao.LEDGER_ENTRY_ID);
			//transEntry1 = identifierDao.getNextIdentifier(IIdentifierDao.LEDGER_TRANS_ID);
			//transEntry2 = identifierDao.getNextIdentifier(IIdentifierDao.LEDGER_TRANS_ID);

			//theTransaction.setLedgerEntryId(transId);

			// Create Common Entries
			TransactionEntryVO creditEntry = buildTransactionEntry(rule, true);
			//creditEntry.setLedgerEntryId(transId);
			//creditEntry.setTransactionId(transEntry1);

			TransactionEntryVO debitEntry = buildTransactionEntry(rule, false);
			//debitEntry.setLedgerEntryId(transId);
			//debitEntry.setTransactionId(transEntry2);

			theTransaction.addTransactionEntry(creditEntry);
			theTransaction.addTransactionEntry(debitEntry);

			theTransaction.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			theTransaction.setLedgerEntryDate(new Timestamp(System.currentTimeMillis()));
			theTransaction.setReconsidedDate(new Timestamp(System.currentTimeMillis()));
			theTransaction.setReconsiledInd(true);

			if(businessTransId.intValue() == CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEAT_SLB_FROM_BUYER 
				|| businessTransId.intValue()==CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEAT_SLB_FROM_PROVIDER){
							theTransaction.setReconsidedDate(null);
							theTransaction.setReconsiledInd(false);
						}
			if (businessTransId == CommonConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS ||
					businessTransId == CommonConstants.BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS ||
					businessTransId == CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER ||
					businessTransId == CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER || 
					businessTransId == CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER ||
					businessTransId == CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_BUYER ||
					businessTransId == CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_PROVIDER) {
				theTransaction.setReconsidedDate(null);
				theTransaction.setReconsiledInd(false);
			}

			theTransaction.setBusinessTransId(businessTransId);
			theTransaction.setLedgerEntryRuleId(rule.getLedgerEntryRuleId());
			theTransaction.setAffectsBalanceInd(rule.getAffectsBalanceInd());
			theTransaction.setAutoAchInd(rule.getAutoAchInd());
			businessTrans.addTransaction(theTransaction);
		}
		return businessTrans;
	}

	/**
	 * buildTransactionEntry.
	 * 
	 * @param tranRule 
	 * @param type 
	 * 
	 * @return TransactionEntryVO
	 */
	private TransactionEntryVO buildTransactionEntry(TransactionRuleVO tranRule, boolean type) {

		TransactionEntryVO entry = new TransactionEntryVO();

		entry.setLedgerEntryRuleId(tranRule.getLedgerEntryRuleId());
		entry.setTransactionTypeId(tranRule.getTransactionTypeId());

		if (type) {
			entry.setTaccountNo(tranRule.getTransRuleCreditTAcctNo());
			entry.setLedgerEntityTypeId(tranRule.getTransRuleCreditEntityTypeId());
			entry.setEntryTypeId(CommonConstants.ENTRY_TYPE_CREDIT);
			entry.setCredit(true);
		} else {
			entry.setTaccountNo(tranRule.getTransRuleDebitTAcctNo());
			entry.setLedgerEntityTypeId(tranRule.getTransRuleDebitEntityTypeId());
			entry.setEntryTypeId(CommonConstants.ENTRY_TYPE_DEBIT);
			entry.setCredit(false);
		}
		return entry;
	}

	/**
	 * calculateTransactionAmount.
	 * 
	 * @param orderPricingVO 
	 * @param pricingRuleExpression 
	 * 
	 * @return double
	 * 
	 * @throws Exception 
	 */
	private double calculateTransactionAmount(OrderPricingVO orderPricingVO, String pricingRuleExpression) throws Exception {

		String add = "+";
		String subtract = "-";
		String previousOperator = "";
		pricingRuleExpression = StringUtils.deleteWhitespace(pricingRuleExpression);
		StringTokenizer st = null;

		if (StringUtils.isNotBlank(pricingRuleExpression)) {
			st = new StringTokenizer(pricingRuleExpression, "+-", true);
		}

		double finalTransAmount = (orderPricingVO.getAmount() == null ? 0 : orderPricingVO.getAmount());

		while (st != null && st.hasMoreTokens()) {
			String token = st.nextToken("+-");
			String operator = "";
			double transAmount = 0.0;
			boolean feeConstantFound = false;
			if (StringUtils.equalsIgnoreCase(token, CommonConstants.POSTING_FEE)) {
				if (orderPricingVO.getPostingFee() != null) {
					transAmount = orderPricingVO.getPostingFee();
					feeConstantFound = true;
				}

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.LABOUR_SPEND_LIMIT)) {
				if (orderPricingVO.getSpendLimitLabor() != null) {
					transAmount = orderPricingVO.getSpendLimitLabor();
					feeConstantFound = true;
				}

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.PARTS_SPEND_LIMIT)) {
				if (orderPricingVO.getSpendLimitParts() != null) {
					transAmount = orderPricingVO.getSpendLimitParts();
					feeConstantFound = true;
				}

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.FINAL_LABOUR)) {
				if (orderPricingVO.getLaborFinalPrice() != null) {
					transAmount = orderPricingVO.getLaborFinalPrice();
					feeConstantFound = true;
				}

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.FINAL_PARTS)) {
				if (orderPricingVO.getPartsFinalPrice() != null) {
					transAmount = orderPricingVO.getPartsFinalPrice();
					feeConstantFound = true;
				}
			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.ACH_AMOUNT)) {
				if (orderPricingVO.getAchAmount() != null) {
					transAmount = orderPricingVO.getAchAmount();
					feeConstantFound = true;
				}

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.FINAL_SERVICE_FEE)) {
				if(orderPricingVO.isTaskLevelPricing() || orderPricingVO.isRelay()){
					if(orderPricingVO.getSoServiceFee()!=null)
					transAmount = orderPricingVO.getSoServiceFee().doubleValue();
																				  
				}else{
					transAmount = orderPricingVO.getServiceFee();
				}
				feeConstantFound = true;
			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.UPSELL_PROVIDER_TOTAL)) {
				if (orderPricingVO.getAddOnTotal() != null) {
					transAmount = orderPricingVO.getAddOnTotal();
					feeConstantFound = true;
				}

			}else if (StringUtils.equalsIgnoreCase(token, CommonConstants.UPSELL_PARTS_TOTAL)) {
				if (orderPricingVO.getPartsAmount() != null) {
					transAmount = orderPricingVO.getPartsAmount();
					feeConstantFound = true;
				}

			}  
			else if (StringUtils.equalsIgnoreCase(token, CommonConstants.RETAIL_CANCELLATION_FEE)) {
				if (orderPricingVO.getRetailCancellationFee() != null) {
					transAmount = orderPricingVO.getRetailCancellationFee();
					feeConstantFound = true;
				}

			}

			else if (StringUtils.equalsIgnoreCase(token, CommonConstants.CANCELLATION_FEE)) {
				if (orderPricingVO.getCancellationFee() != null) {
					transAmount = orderPricingVO.getCancellationFee();
					feeConstantFound = true;
				}

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.RETAIL_SO_PRICE)) {
				if (orderPricingVO.getRetailPrice() != null) {
					transAmount = orderPricingVO.getRetailPrice();
					feeConstantFound = true;
				}

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.INITIAL_SPEND_LIMIT)) {
				transAmount = orderPricingVO.getSpendLimit();
				feeConstantFound = true;

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.ZERO)) {
				transAmount = 0.0;
				feeConstantFound = true;

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.DEPOSIT_AMOUNT)) {
				transAmount = orderPricingVO.getAmount();
				feeConstantFound = true;

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.TRANSFER_AMOUNT)) {
				transAmount = orderPricingVO.getAmount();
				feeConstantFound = true;

			} else if (StringUtils.equalsIgnoreCase(token, CommonConstants.USER_ENTERED)) {
				transAmount = orderPricingVO.getAmount();
				feeConstantFound = true;

			} else if(StringUtils.equalsIgnoreCase(token, CommonConstants.RETAIL_PRICE)){
				if (null != orderPricingVO.getRetailPrices()) {
					transAmount = orderPricingVO.getRetailPrices();
					feeConstantFound = true;
				}				
			} else if(StringUtils.equalsIgnoreCase(token, CommonConstants.REIMBURSEMENT_RETAIL_PRICE)){
				if (null != orderPricingVO.getReimbursementRetailPrice()) {
					transAmount = orderPricingVO.getReimbursementRetailPrice();
					feeConstantFound = true;
				}				
			} else if(StringUtils.equalsIgnoreCase(token, CommonConstants.PARTS_SLGROSSUP)){
				if (null != orderPricingVO.getPartsSLGrossup()) {
					transAmount = orderPricingVO.getPartsSLGrossup();
					feeConstantFound = true;
				}
			} else if(StringUtils.equalsIgnoreCase(token, CommonConstants.RETAIL_PRICE_SLGROSSUP)){
				if (null != orderPricingVO.getRetailPriceSLGrossup()) {
					transAmount = orderPricingVO.getRetailPriceSLGrossup();
					feeConstantFound = true;
				}				
			}
			else if (StringUtils.equalsIgnoreCase(token, add)) {
				operator = add;

			} else if (StringUtils.equalsIgnoreCase(token, subtract)) {
				operator = subtract;

			}

			if (StringUtils.equals(previousOperator, add)) {
				finalTransAmount = MoneyUtil.getRoundedMoney(finalTransAmount + transAmount);
				previousOperator = "";
			} else if (StringUtils.equals(previousOperator, subtract)) {
				finalTransAmount = MoneyUtil.getRoundedMoney(finalTransAmount - transAmount);
				previousOperator = "";
			} else if (feeConstantFound) {
				finalTransAmount = transAmount;
			}
			previousOperator = operator;

		}

		return finalTransAmount;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.ITransactionBuilder#createTransactions(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public LedgerBusinessTransactionVO createTransactions(Integer entityTypeId, Long businessTransId, Long fundingTypeId) throws Exception {

		if (entityTypeId == null || businessTransId == null) {
			throw new Exception();
		}

		if (fundingTypeId == null || fundingTypeId.intValue() == 0) fundingTypeId = CommonConstants.FUNDING_TYPE_NON_FUNDED;

		// Get all the transaction rules
		ArrayList<TransactionRuleVO> eRules = getRules(entityTypeId, businessTransId, fundingTypeId);
		// Build business transaction
		LedgerBusinessTransactionVO businessTran = buildBusinessTransaction(eRules, businessTransId);
		businessTran.setFundingTypeId(fundingTypeId);
		businessTran.setEntityTypeId(new Long(entityTypeId));

		return businessTran;
	}
	
	public LedgerBusinessTransactionVO createTransactionsForNonHSR(Integer entityTypeId, Long businessTransId, Long fundingTypeId) throws Exception {

		if (entityTypeId == null || businessTransId == null) {
			throw new Exception();
		}

		if (fundingTypeId == null || fundingTypeId.intValue() == 0) fundingTypeId = CommonConstants.FUNDING_TYPE_NON_FUNDED;

		// Get all the transaction rules
		ArrayList<TransactionRuleVO> eRules = getRules(entityTypeId, businessTransId, fundingTypeId);
		Iterator<TransactionRuleVO> it = eRules.iterator();
		ArrayList<TransactionRuleVO> rules = new ArrayList<TransactionRuleVO>();
		
		while(it.hasNext()){
			TransactionRuleVO transactionRuleVO = it.next();
			if(1137 != transactionRuleVO.getLedgerEntryRuleId().intValue() && 1138 != transactionRuleVO.getLedgerEntryRuleId().intValue()
					&& 1139 != transactionRuleVO.getLedgerEntryRuleId().intValue() && 1140 != transactionRuleVO.getLedgerEntryRuleId().intValue()){
				rules.add(transactionRuleVO);
			}
		}
		// Build business transaction
		LedgerBusinessTransactionVO businessTran = buildBusinessTransaction(rules, businessTransId);
		businessTran.setFundingTypeId(fundingTypeId);
		businessTran.setEntityTypeId(new Long(entityTypeId));

		return businessTran;
	}

	public LedgerBusinessTransactionVO createTransactionsForHSRwithOutParts(Integer entityTypeId, Long businessTransId, Long fundingTypeId) throws Exception {

		if (entityTypeId == null || businessTransId == null) {
			throw new Exception();
		}

		if (fundingTypeId == null || fundingTypeId.intValue() == 0) fundingTypeId = CommonConstants.FUNDING_TYPE_NON_FUNDED;

		// Get all the transaction rules
		ArrayList<TransactionRuleVO> eRules = getRules(entityTypeId, businessTransId, fundingTypeId);
		Iterator<TransactionRuleVO> it = eRules.iterator();
		ArrayList<TransactionRuleVO> rules = new ArrayList<TransactionRuleVO>();
		
		while(it.hasNext()){
			TransactionRuleVO transactionRuleVO = it.next();
			if(1137 != transactionRuleVO.getLedgerEntryRuleId().intValue() && 1138 != transactionRuleVO.getLedgerEntryRuleId().intValue()
					&& 1139 != transactionRuleVO.getLedgerEntryRuleId().intValue() && 1140 != transactionRuleVO.getLedgerEntryRuleId().intValue()){
				rules.add(transactionRuleVO);
			}
		}
		// Build business transaction
		LedgerBusinessTransactionVO businessTran = buildBusinessTransaction(rules, businessTransId);
		businessTran.setFundingTypeId(fundingTypeId);
		businessTran.setEntityTypeId(new Long(entityTypeId));

		return businessTran;
	}
	public LedgerBusinessTransactionVO createTransactionsForHSRwithOutAddOn(Integer entityTypeId, Long businessTransId, Long fundingTypeId) throws Exception {

		if (entityTypeId == null || businessTransId == null) {
			throw new Exception();
		}

		if (fundingTypeId == null || fundingTypeId.intValue() == 0) fundingTypeId = CommonConstants.FUNDING_TYPE_NON_FUNDED;

		// Get all the transaction rules
		ArrayList<TransactionRuleVO> eRules = getRules(entityTypeId, businessTransId, fundingTypeId);
		Iterator<TransactionRuleVO> it = eRules.iterator();
		ArrayList<TransactionRuleVO> rules = new ArrayList<TransactionRuleVO>();
		
		while(it.hasNext()){
			TransactionRuleVO transactionRuleVO = it.next();
			if(1135 != transactionRuleVO.getLedgerEntryRuleId().intValue() && 1136 != transactionRuleVO.getLedgerEntryRuleId().intValue()){
				rules.add(transactionRuleVO);
			}
		}
		// Build business transaction
		LedgerBusinessTransactionVO businessTran = buildBusinessTransaction(rules, businessTransId);
		businessTran.setFundingTypeId(fundingTypeId);
		businessTran.setEntityTypeId(new Long(entityTypeId));

		return businessTran;
	}
	
	public LedgerBusinessTransactionVO createTransactionsForClosure(Integer entityTypeId, Long businessTransId,Long fundingTypeId) throws Exception{
		if (entityTypeId == null || businessTransId == null) {
			throw new Exception();
		}

		if (fundingTypeId == null || fundingTypeId.intValue() == 0) fundingTypeId = CommonConstants.FUNDING_TYPE_NON_FUNDED;

		// Get all the transaction rules
		ArrayList<TransactionRuleVO> eRules = getRules(entityTypeId, businessTransId, fundingTypeId);
		Iterator<TransactionRuleVO> it = eRules.iterator();
		ArrayList<TransactionRuleVO> rules = new ArrayList<TransactionRuleVO>();
		while(it.hasNext()){
			TransactionRuleVO transactionRuleVO = it.next();
			if(CommonConstants.RULE_REFUND_ESCROW != transactionRuleVO.getLedgerEntryRuleId().intValue() && 
			   CommonConstants.AUTO_ACH_REFUND != transactionRuleVO.getLedgerEntryRuleId().intValue() &&
			   CommonConstants.AUTO_ACH_GL_REFUND != transactionRuleVO.getLedgerEntryRuleId().intValue()){
						logger.info("price is zero for rules 1420,1480 and 1470");
						rules.add(transactionRuleVO);
			}
		}
		// Build business transaction
		LedgerBusinessTransactionVO businessTran = buildBusinessTransaction(rules, businessTransId);
		businessTran.setFundingTypeId(fundingTypeId);
		businessTran.setEntityTypeId(new Long(entityTypeId));

		return businessTran;
	}
	
	public LedgerBusinessTransactionVO createTransactionsForInvoicePartsEmpty(Integer entityTypeId, Long businessTransId,Long fundingTypeId,boolean priceNotValid) throws Exception{

		if (entityTypeId == null || businessTransId == null) {
			throw new Exception();
		}

		if (fundingTypeId == null || fundingTypeId.intValue() == 0) fundingTypeId = CommonConstants.FUNDING_TYPE_NON_FUNDED;

		// Get all the transaction rules
		ArrayList<TransactionRuleVO> eRules = getRules(entityTypeId, businessTransId, fundingTypeId);
		Iterator<TransactionRuleVO> it = eRules.iterator();
		ArrayList<TransactionRuleVO> rules = new ArrayList<TransactionRuleVO>();
	
		while(it.hasNext()){
			TransactionRuleVO transactionRuleVO = it.next();
			logger.info("transactionRuleVO.getLedgerEntryRuleId().intValue()::"+transactionRuleVO.getLedgerEntryRuleId().intValue());
			if(CommonConstants.REIMBURSEMENT_RETAIL_PRICE_RULE_ID != transactionRuleVO.getLedgerEntryRuleId().intValue() && 
			   CommonConstants.RETAIL_PRICE_RULE_ID != transactionRuleVO.getLedgerEntryRuleId().intValue()&&
			   CommonConstants.RETAIL_PRICE_SLGROSSUP_RULE_ID != transactionRuleVO.getLedgerEntryRuleId().intValue()){
				if(!(priceNotValid) || (priceNotValid && 
						CommonConstants.RULE_REFUND_ESCROW != transactionRuleVO.getLedgerEntryRuleId().intValue() && 
						CommonConstants.AUTO_ACH_REFUND != transactionRuleVO.getLedgerEntryRuleId().intValue() &&
						CommonConstants.AUTO_ACH_GL_REFUND != transactionRuleVO.getLedgerEntryRuleId().intValue())){
							logger.info("price is zero for rules 1420 and 1470");
							rules.add(transactionRuleVO);
						}
			}
		}
		// Build business transaction
		LedgerBusinessTransactionVO businessTran = buildBusinessTransaction(rules, businessTransId);
		businessTran.setFundingTypeId(fundingTypeId);
		businessTran.setEntityTypeId(new Long(entityTypeId));

		return businessTran;
	}
	

	
	/**
	 * getRules.
	 * 
	 * @param orginator 
	 * @param businessTransId 
	 * @param fundingTypeId 
	 * 
	 * @return ArrayList<TransactionRuleVO>
	 * 
	 * @throws DataServiceException 
	 */
	private ArrayList<TransactionRuleVO> getRules(Integer entityTypeId, Long businessTransId, Long fundingTypeId) throws DataServiceException {

		// dao query call here
		ArrayList<TransactionRuleVO> theRules = new ArrayList<TransactionRuleVO>();
		try {
			TransactionRuleVO vo = new TransactionRuleVO();
			vo.setBusTransId(businessTransId);
			vo.setLedgerEntityTypeId(entityTypeId);
			vo.setFundingTypeId(fundingTypeId);

			theRules = (ArrayList<TransactionRuleVO>) transactionDao.queryTransactionRule(vo);
		} catch (DataServiceException e1) {
			logger.error("Error in  getRules ", e1);
			throw e1;
		}

		return theRules;
	}

	/**
	 * populateEntityId.
	 * 
	 * @param transactionEntry 
	 * @param buyerId 
	 * @param vendorId 
	 * 
	 * @return void
	 */
	private void populateEntityId(TransactionEntryVO transactionEntry, Long buyerId, Long vendorId) {

		long ledgerEntityTypeId = transactionEntry.getLedgerEntityTypeId();

		if (ledgerEntityTypeId == (CommonConstants.LEDGER_ENTITY_TYPE_BUYER)) {
			transactionEntry.setLedgerEntityId(buyerId);
		} else if (ledgerEntityTypeId == (CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER)) {
			transactionEntry.setLedgerEntityId(vendorId);
		} else if (ledgerEntityTypeId == (CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW)) {
			transactionEntry.setLedgerEntityId(CommonConstants.ENTITY_ID_ESCROW);
		} else if (ledgerEntityTypeId == (CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN)) {
			transactionEntry.setLedgerEntityId(CommonConstants.ENTITY_ID_SERVICELIVE);
		} else if (ledgerEntityTypeId == (CommonConstants.LEDGER_ENTITY_TYPE_DEPOSITS_WITHDRAWLS)) {
			transactionEntry.setLedgerEntityId(CommonConstants.ENTITY_ID_DEPOSIT_WITHDRAWL);
		} else if (ledgerEntityTypeId == (CommonConstants.LEDGER_ENTITY_TYPE_VIRTUAL_CASH)) {
			transactionEntry.setLedgerEntityId(CommonConstants.ENTITY_ID_VIRTUAL_CASH);
		} else if (ledgerEntityTypeId == (CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION)) {
			transactionEntry.setLedgerEntityId(CommonConstants.ENTITY_ID_SERVICELIVE_OPERATION);
		} else if (ledgerEntityTypeId == (CommonConstants.LEDGER_ENTITY_TYPE_MANAGE_SERVICES)) {
			transactionEntry.setLedgerEntityId(CommonConstants.ENTITY_ID_MANAGED_SERVICES);
		}
		// populate originating buyer_id unconditionally
		transactionEntry.setOriginatingBuyerId(buyerId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.ITransactionBuilder#populateTransactionAmount(com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO, com.servicelive.wallet.ledger.vo.OrderPricingVO)
	 */
	public void populateTransactionAmount(LedgerBusinessTransactionVO businessTrans, OrderPricingVO ledgerVO) throws Exception {

		try {
			ArrayList<TransactionVO> transactionVOs = businessTrans.getTransactions();
			Map<String, String> pricingRules = transactionDao.getLedgerRulePricingExpressionsMap(businessTrans.getBusinessTransId());

			for (int i = 0; i < transactionVOs.size(); i++) {
				TransactionVO transactionVO = transactionVOs.get(i);

				String pricingRuleExpression = "";
				if (pricingRules != null) {
					String ruleId = transactionVO.getLedgerEntryRuleId() + "_" + businessTrans.getFundingTypeId();

					if (pricingRules.containsKey(ruleId)) {
						pricingRuleExpression = pricingRules.get(ruleId);
					}

				}
				//This code is for controlling the behaviour of In home SOs which were completed prior to release of SL-18195
				//fetch production date from application properties.
				boolean isNewOrder = false;
				String prodDateVal = applicationProperties.getPropertyFromDB(CommonConstants.PROD_DATE);
				logger.info("for close rule production date string value::"+prodDateVal);
				Date prodDate = Date.valueOf(prodDateVal);
				logger.info("for close rule production date::"+prodDate);
				if((null != ledgerVO.getCompletedDate()) && (prodDate.compareTo(ledgerVO.getCompletedDate()) <= 0)){
					isNewOrder = true;
				}
				logger.info("isNewOrder::"+isNewOrder);
				logger.info("isHSR::"+ledgerVO.isHSR());
				logger.info("isHasParts::"+ledgerVO.isHasParts());
				logger.info("getLedgerEntryRuleId()::"+transactionVO.getLedgerEntryRuleId());
				logger.info("pricingRuleExpression::"+pricingRuleExpression);
				if (!isNewOrder && ledgerVO.isHSR() && ledgerVO.isHasParts()
						&& new Long(120).equals(businessTrans.getBusinessTransId())
						&& (new Long(10005).equals(transactionVO.getLedgerEntryRuleId())
						|| new Long(10006).equals(transactionVO.getLedgerEntryRuleId()))) {
					pricingRuleExpression = pricingRuleExpression
							+ "+PARTS_SLGROSSUP";
					logger.info("transactionVO.getLedgerEntryRuleId()::"+transactionVO.getLedgerEntryRuleId());
					logger.info("pricingRuleExpression::"+pricingRuleExpression);
				}

				double amount = calculateTransactionAmount(ledgerVO, pricingRuleExpression);
				if (amount < 0){
					throw new Exception("Cannot have negative amount in ledger entry. Rule id is: " + transactionVO.getLedgerEntryRuleId() + "_" + businessTrans.getFundingTypeId());
				}
				ArrayList<TransactionEntryVO> transactionEntries = transactionVO.getTransactions();
				for (int j = 0; j < transactionEntries.size(); j++) {
					TransactionEntryVO transactionEntry = transactionEntries.get(j);
					transactionEntry.setTransactionAmount(amount);
				}
			}

		} catch (Exception e) {
			logger.error("Error in  populateTransactionAmount ", e);
			throw e;
		}

	}

	// Code for SLT-4299
	List<Long> toModifyRuleIds = Arrays.asList(new Long[] { 10005L, 10006L });
	List<Double> pennyValuesToCorrect = Arrays.asList(new Double[] { 0.01, 0.02, 0.03, -0.01, -0.02, -0.03 });
	public void populateTransactionAmountAdjustPennyVariance(LedgerBusinessTransactionVO businessTrans,
				OrderPricingVO ledgerVO, String soId) throws Exception {

			try {
				ArrayList<TransactionVO> transactionVOs = businessTrans.getTransactions();
				Map<String, String> pricingRules = transactionDao
						.getLedgerRulePricingExpressionsMap(businessTrans.getBusinessTransId());
				double debitRuleSum = 0.00;
				double creditRuleSum = 0.00;
				double tempAmtToModify = 0.00;

				List<Long> debitRuleIds = transactionDao.getDebitRulesListForVarianceCheck();
				logger.info("debitRulesList:" + debitRuleIds.toString());
				
				for (int i = 0; i < transactionVOs.size(); i++) {
					TransactionVO transactionVO = transactionVOs.get(i);

					String pricingRuleExpression = "";
					if (pricingRules != null) {
						String ruleId = transactionVO.getLedgerEntryRuleId() + "_" + businessTrans.getFundingTypeId();
						logger.info("ruleId:" + ruleId);
						if (pricingRules.containsKey(ruleId)) {
							pricingRuleExpression = pricingRules.get(ruleId);
						}
					}
					// This code is for controlling the behaviour of In home SOs
					// which were completed prior to release of SL-18195
					// fetch production date from application properties.
					boolean isNewOrder = false;
					String prodDateVal = applicationProperties.getPropertyFromDB(CommonConstants.PROD_DATE);
					Date prodDate = Date.valueOf(prodDateVal);
					if ((null != ledgerVO.getCompletedDate()) && (prodDate.compareTo(ledgerVO.getCompletedDate()) <= 0)) {
						isNewOrder = true;
					}
					logger.info("pricingRuleExpression::" + pricingRuleExpression);
					logger.info("transactionVO.getLedgerEntryRuleId()::" + transactionVO.getLedgerEntryRuleId());
					if (!isNewOrder && ledgerVO.isHSR() && ledgerVO.isHasParts()
							&& new Long(120).equals(businessTrans.getBusinessTransId())
							&& (new Long(10005).equals(transactionVO.getLedgerEntryRuleId())
									|| new Long(10006).equals(transactionVO.getLedgerEntryRuleId()))) {
						pricingRuleExpression = pricingRuleExpression + "+PARTS_SLGROSSUP";
					}

					double amount = calculateTransactionAmount(ledgerVO, pricingRuleExpression);

					if (debitRuleIds.contains(transactionVO.getLedgerEntryRuleId())) {
						debitRuleSum += amount;
						logger.info("debit: " + transactionVO.getLedgerEntryRuleId() + " amount:" + amount
								+ " debitRuleSum:" + debitRuleSum);
					}

					if (10005 == transactionVO.getLedgerEntryRuleId()) {
						tempAmtToModify = amount;

						// Get sum amount for the credit rules from DB
						creditRuleSum = transactionDao.getCreditAmountBySO(soId);
						logger.info("creditRuleSum:" + creditRuleSum);
					}

					if (amount < 0) {
						throw new Exception("Cannot have negative amount in ledger entry. Rule id is: "
								+ transactionVO.getLedgerEntryRuleId() + "_" + businessTrans.getFundingTypeId());
					}
					ArrayList<TransactionEntryVO> transactionEntries = transactionVO.getTransactions();
					for (int j = 0; j < transactionEntries.size(); j++) {
						TransactionEntryVO transactionEntry = transactionEntries.get(j);
						transactionEntry.setTransactionAmount(amount);
					}
				}

				if (tempAmtToModify > 0) {
					Double varianceAmt = MoneyUtil.getRoundedMoney(MoneyUtil.getRoundedMoney(debitRuleSum) - creditRuleSum);
					logger.info("Original 10005 Amt- " + tempAmtToModify + "         VarianceAmount- " + varianceAmt);
					
					// Variance amount other than these values will not be corrected
					if (pennyValuesToCorrect.contains(varianceAmt)) {
						tempAmtToModify = MoneyUtil.getRoundedMoney(tempAmtToModify - varianceAmt);

						logger.info("---10005 Amt after penny variance adjusted for soId- " + soId + " is- "+ tempAmtToModify );
						for (int i = 0; i < transactionVOs.size(); i++) {
							TransactionVO transactionVO = transactionVOs.get(i);
							if (toModifyRuleIds.contains(transactionVO.getLedgerEntryRuleId())) {
								ArrayList<TransactionEntryVO> transactionEntries = transactionVO.getTransactions();
								for (int j = 0; j < transactionEntries.size(); j++) {
									TransactionEntryVO transactionEntry = transactionEntries.get(j);
									transactionEntry.setTransactionAmount(tempAmtToModify);
								}
							}
						}
					} else {
						logger.info("---NoPennyVarianceAdjustmentRequired for soId- " + soId);
					}
				}
			} catch (Exception e) {
				logger.error("Error in  populateTransactionAmountAdjustPennyVariance() ", e);
				throw e;
			}
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ledger.ITransactionBuilder#populateTransactionEntry(com.servicelive.wallet.ledger.vo.TransactionVO, java.lang.Long, java.lang.Long, java.lang.Long, boolean, int)
	 */
	public long populateTransactionEntry(TransactionVO transaction, Long accountId, Long buyerId, Long vendorId, boolean ccInd, int returnEntryType) {

		long ledgerTransactionId = 0L;
		for (TransactionEntryVO transactionEntry : transaction.getTransactions()) {
			if (transactionEntry.getEntryTypeId() == returnEntryType) {
				ledgerTransactionId = transactionEntry.getTransactionId();
			}
			transactionEntry.setCCInd(ccInd);
			transactionEntry.setAccountNumber(accountId);
			populateEntityId(transactionEntry, buyerId, vendorId);
		}

		return ledgerTransactionId;
	}

	/**
	 * setIdentifierDao.
	 * 
	 * @param identifierDao 
	 * 
	 * @return void
	 */
	public void setIdentifierDao(IIdentifierDao identifierDao) {

		this.identifierDao = identifierDao;
	}

	/**
	 * setTransactionDao.
	 * 
	 * @param transactionDao 
	 * 
	 * @return void
	 */
	public void setTransactionDao(ITransactionDao transactionDao) {

		this.transactionDao = transactionDao;
	}

}
