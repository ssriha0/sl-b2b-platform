package com.newco.marketplace.web.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.delegates.IOrderGroupDelegate;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;

public class SOWPricingTabDTO extends SOWBaseTabDTO {
	
	private static final long serialVersionUID = -3074362541663946117L;
	private static final String CURRENCY_REGULAR_EXPRESSION = "^(((\\d{1,3},)+\\d{3}|\\d+)\\.?\\d{0,6})$|^(\\.(\\d{1,2}))$";

	private static final String ZERO = "0.00";

	private static final Logger logger = Logger.getLogger(SOWPricingTabDTO.class.getName());
	private List<ServiceOrderCustomRefVO> customRefs  = new ArrayList<ServiceOrderCustomRefVO>();
	private String orderType = "";
	private boolean shareContactInd;
	private boolean allowBidOrders = false;
	private boolean nonFundedInd = false;
	private boolean isSealedBidOrder = false;
	private boolean sealedBidInd;

	private double hourlyRate;
	private String laborSpendLimit = "0.00";	
	private String partsSpendLimit = "0.00"; 

	private double addFundsAmount;
	private double postingFee;
	private ArrayList<DropdownOptionDTO> rateOptions = new ArrayList<DropdownOptionDTO>();
	private ArrayList<DropdownOptionDTO> accountOptions = new ArrayList<DropdownOptionDTO>();
	
	private String selectedRadioPricing = ZERO;		
	private String selectedDropdownPricing = ZERO;		
	private String selectedRadioPayment = ZERO;
	private String selectedDropdownAccount = ZERO;
	private boolean isTabVisited = false; 
	private double billingEstimate = 0.0;
	private Double initialLaborSpendLimit;// used for retaining the value
											// initially in service order for
											// purpose of comparing the new
											// values used in edit price on
											// pricing tab
	private Double initialPartsSpendLimit;// used for retaining the value
											// initially in service order for
											// purpose of comparing the new
											// values used in edit price on
											// pricing tab
	
	private boolean isDraftOrder = true;

	// The following 4 members are for child Service Orders
	private double ogLaborSpendLimit = 0.0;
	private double ogPartsSpendLimit = 0.0;
	private double ogCurrentLaborSpendLimit = 0.0;
	private double ogCurrentPartsSpendLimit = 0.0;
	private Integer fundingType = 0;
	private double groupTotalPermits = 0.0;
	
	private ArrayList<SOTaskDTO> tasks;
	private boolean taskLevelPricingInd = false;
	private Double permitPrepaidPrice;
	private Double cancellationFee;
	private String buyerId;
	
	private String laborTaxPercentage="0.00";
	private String partsTax="0.00";
	

	public boolean isTaskLevelPricingInd() {
		return taskLevelPricingInd;
	}


	public void setTaskLevelPricingInd(boolean taskLevelPricingInd) {
		this.taskLevelPricingInd = taskLevelPricingInd;
	}


	public ArrayList<SOTaskDTO> getTasks() {
		return tasks;
	}


	public void setTasks(ArrayList<SOTaskDTO> tasks) {
		this.tasks = tasks;
	}


	@Override
	public void validate() 
	{
		Pattern p = null;
		Matcher m = null;
		NumberFormat formatter = new DecimalFormat("#0.00");
		//Clear out all previous errors
		//super.clearAllErrors();
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		
		//Create Regular expression matcher
		p = Pattern.compile(CURRENCY_REGULAR_EXPRESSION);
		
		//Validate Maximum Price for Labor
		m = p.matcher(this.getLaborSpendLimit());
		SOWPartsTabDTO tabDTO =(SOWPartsTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PARTS_TAB);

		//UI has been changed so that both Labor and Parts spend limits are processed under all circumstances.
		if(tabDTO != null && tabDTO.getPartsSuppliedBy() != null && (tabDTO.getPartsSuppliedBy().equals("1") || tabDTO.getPartsSuppliedBy().equals("2") || tabDTO.getPartsSuppliedBy().equals("3")))
 {
			// Don't validate any value until User has visited the tab before
			// going on to the next tab
			/*
			 * if(!isTabVisited()) { return; }
			 */

			boolean isEstimate = false;
			if (null != customRefs && !customRefs.isEmpty()) {
				for (ServiceOrderCustomRefVO soCustomRefVO : customRefs) {
					if (null != soCustomRefVO.getRefValue() && Constants.ESTIMATION_CUST_REF.equals(soCustomRefVO.getRefValue())) {
						isEstimate = true;
						break;
					}
				}
			}
			
			Double templaborLimit = com.newco.marketplace.web.utils.SLStringUtils.getMonetaryNumber(laborSpendLimit);
			if (!orderType.equals(Constants.PriceModel.ZERO_PRICE_BID) && (templaborLimit == null || !m.matches())) {
				super.addError(super.getTheResourceBundle().getString("Labor_Spend_Limit"),
						super.getTheResourceBundle().getString("Labor_Spend_Limit_Numeric_Value_Message"), OrderConstants.SOW_TAB_ERROR);

			} else if (!orderType.equals(Constants.PriceModel.ZERO_PRICE_BID) && !isEstimate && buyerId.equals("3333") && templaborLimit.equals(0.0)) {
				super.addError(super.getTheResourceBundle().getString("Labor_Spend_Limit"),
						super.getTheResourceBundle().getString("Labor_Spend_Limit_Numeric_Value_Message"), OrderConstants.SOW_TAB_ERROR);

			} else if (!orderType.equals(Constants.PriceModel.ZERO_PRICE_BID) && templaborLimit < 0.0) {
				super.addWarning(super.getTheResourceBundle().getString("Labor_Spend_Limit"),
						super.getTheResourceBundle().getString("Labor_Spend_Limit_Missing_Message"), OrderConstants.SOW_TAB_WARNING);

			} else { // If the entered Maximum Price for Labor value is valid,
						// then check if it is less than cancellation fee for
						// the particular buyerid.
				Double cancelFee = 0.00;
				if (null != cancellationFee) {
					cancelFee = cancellationFee;
				}

				// modified to include the check if $(Labor+Material) >=
				// Cancellation Fee
				Double tempPartsLimit = 0.00;
				Double maxLimit = templaborLimit;
				if (org.apache.commons.lang.StringUtils.isNotBlank(getPartsSpendLimit())) {
					tempPartsLimit = com.newco.marketplace.web.utils.SLStringUtils.getMonetaryNumber(partsSpendLimit);
					m = p.matcher(this.getPartsSpendLimit());
					if ((tempPartsLimit != null && m.matches() && tempPartsLimit >= 0.00)) {
						maxLimit = maxLimit + tempPartsLimit;
					}
				}
				if (!orderType.equals(Constants.PriceModel.ZERO_PRICE_BID) && maxLimit < cancelFee) {
					super.addWarning(super.getTheResourceBundle().getString("Labor_Spend_Limit"),
							super.getTheResourceBundle().getString("Labor_Spend_Limit_Below_Minimum_Value") + formatter.format(cancelFee),
							OrderConstants.SOW_TAB_WARNING);
				}
			}

			// Validate Maximum Price for Materials

			if (org.apache.commons.lang.StringUtils.isNotBlank(getPartsSpendLimit())) {
				Double tempPartsLimit = com.newco.marketplace.web.utils.SLStringUtils.getMonetaryNumber(partsSpendLimit);
				m = p.matcher(this.getPartsSpendLimit());

				if (!orderType.equals(Constants.PriceModel.ZERO_PRICE_BID) && (tempPartsLimit == null || !m.matches())) {
					super.addError(super.getTheResourceBundle().getString("Parts_Spend_Limit"),
							super.getTheResourceBundle().getString("Parts_Spend_Limit_Numeric_Value_Message"), OrderConstants.SOW_TAB_ERROR);

				} else if (!orderType.equals(Constants.PriceModel.ZERO_PRICE_BID) && tempPartsLimit < 0.0) {
					super.addWarning(super.getTheResourceBundle().getString("Parts_Spend_Limit"),
							super.getTheResourceBundle().getString("Parts_Spend_Limit_Missing_Message"), OrderConstants.SOW_TAB_WARNING);

				}
			}

		}
		_doWorkFlowValidation();
	}

	
	// This method is only for Service Orders that are children of Order Groups
	public void handleOrderGroupChanges(String groupId, IOrderGroupDelegate orderGroupDelegate, ServiceOrdersCriteria commonCriteria)
	{
		// This is the test if the SOW is editing a child order
		if(StringUtils.isBlank(groupId))
			return;

		if(orderGroupDelegate == null)
			return;

		boolean validate = false;
		Double spendLimitLaborFromForm = getOgLaborSpendLimit();
		Double spendLimitPartsFromForm = getOgPartsSpendLimit();

		Double spendLimitLaborFromDB = getOgCurrentLaborSpendLimit();
		Double spendLimitPartsFromDB = getOgCurrentPartsSpendLimit();
		
		
		// Cannot enter a smaller labor spend limit than we already have
		if(spendLimitLaborFromDB != null)
		{
			if(spendLimitLaborFromForm < spendLimitLaborFromDB)
			{
				spendLimitLaborFromForm = spendLimitLaborFromDB;
			}
		}

		// Cannot enter a smaller labor spend limit than we already have
		if(spendLimitPartsFromDB != null)
		{
			if(spendLimitPartsFromForm < spendLimitPartsFromDB)
			{
				spendLimitPartsFromForm = spendLimitPartsFromDB;
			}
		}

		// If nothing has changed or both new values are smaller, do not increaseSpendLimit.  Just return.
		if(spendLimitLaborFromForm <= spendLimitLaborFromDB && spendLimitPartsFromForm <= spendLimitPartsFromDB) 
		{
			return;
		}

		orderGroupDelegate.updateGroupSpendLimit(
													groupId,
													spendLimitLaborFromDB,
													spendLimitPartsFromDB,
													spendLimitLaborFromForm,
													spendLimitPartsFromForm,
													"",
													commonCriteria.getCompanyId(),
													validate,
													commonCriteria.getSecurityContext());

	}
	
	
	@Override
	public String getTabIdentifier() {
		return OrderConstants.SOW_PRICING_TAB;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}


	public double getAddFundsAmount() {
		return addFundsAmount;
	}

	public void setAddFundsAmount(double addFundsAmount) {
		this.addFundsAmount = addFundsAmount;
	}

	public ArrayList<DropdownOptionDTO> getRateOptions() {
		return rateOptions;
	}

	public void setRateOptions(ArrayList<DropdownOptionDTO> rateOptions) {
		this.rateOptions = rateOptions;
	}

	public ArrayList<DropdownOptionDTO> getAccountOptions() {
		return accountOptions;
	}

	public void setAccountOptions(ArrayList<DropdownOptionDTO> accountOptions) {
		this.accountOptions = accountOptions;
	}

	public String getSelectedRadioPricing() {
		return selectedRadioPricing;
	}

	public void setSelectedRadioPricing(String selectedRadioPricing) {
		this.selectedRadioPricing = selectedRadioPricing;
	}

	public String getSelectedDropdownPricing() {
		return selectedDropdownPricing;
	}

	public void setSelectedDropdownPricing(String selectedDropdownPricing) {
		this.selectedDropdownPricing = selectedDropdownPricing;
	}

	public String getSelectedRadioPayment() {
		return selectedRadioPayment;
	}

	public void setSelectedRadioPayment(String selectedRadioPayment) {
		this.selectedRadioPayment = selectedRadioPayment;
	}

	public String getSelectedDropdownAccount() {
		return selectedDropdownAccount;
	}

	public void setSelectedDropdownAccount(String selectedDropdownAccount) {
		this.selectedDropdownAccount = selectedDropdownAccount;
	}

	public boolean isTabVisited() {
		return isTabVisited;
	}

	public void setTabVisited(boolean isTabVisited) {
		this.isTabVisited = isTabVisited;
	}





	public double getBillingEstimate() {
		return billingEstimate;
	}


	public void setBillingEstimate(double billingEstimate) {
		this.billingEstimate = billingEstimate;
	}


	public double getPostingFee() {
		return postingFee;
	}


	public void setPostingFee(double postingFee) {
		this.postingFee = postingFee;
	}


	public String getLaborSpendLimit() {
		return laborSpendLimit;
	}


	public void setLaborSpendLimit(String laborSpendLimit) {
		this.laborSpendLimit = org.apache.commons.lang.StringUtils.trim(laborSpendLimit);
	}


	public String getPartsSpendLimit() {
		return partsSpendLimit;
	}


	public void setPartsSpendLimit(String partsSpendLimit) {
		this.partsSpendLimit = org.apache.commons.lang.StringUtils.trim(partsSpendLimit);
	}


	public Double getInitialLaborSpendLimit() {
		return initialLaborSpendLimit;
	}


	public void setInitialLaborSpendLimit(Double initialLaborSpendLimit) {
		this.initialLaborSpendLimit = initialLaborSpendLimit;
	}


	public Double getInitialPartsSpendLimit() {
		return initialPartsSpendLimit;
	}


	public void setInitialPartsSpendLimit(Double initialPartsSpendLimit) {
		this.initialPartsSpendLimit = initialPartsSpendLimit;
	}


	public double getOgPartsSpendLimit()
	{
		return ogPartsSpendLimit;
	}


	public void setOgPartsSpendLimit(double ogPartsSpendLimit)
	{
		this.ogPartsSpendLimit = ogPartsSpendLimit;
	}


	public double getOgLaborSpendLimit()
	{
		return ogLaborSpendLimit;
	}


	public void setOgLaborSpendLimit(double ogLaborSpendLimit)
	{
		this.ogLaborSpendLimit = ogLaborSpendLimit;
	}


	public double getOgCurrentLaborSpendLimit()
	{
		return ogCurrentLaborSpendLimit;
	}


	public void setOgCurrentLaborSpendLimit(double ogCurrentLaborSpendLimit)
	{
		this.ogCurrentLaborSpendLimit = ogCurrentLaborSpendLimit;
	}


	public double getOgCurrentPartsSpendLimit()
	{
		return ogCurrentPartsSpendLimit;
	}


	public void setOgCurrentPartsSpendLimit(double ogCurrentPartsSpendLimit)
	{
		this.ogCurrentPartsSpendLimit = ogCurrentPartsSpendLimit;
	}


	public Integer getFundingType() {
		return fundingType;
	}


	public void setFundingType(Integer fundingType) {
		this.fundingType = fundingType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setAllowBidOrders(boolean allowBidOrders) {
		this.allowBidOrders = allowBidOrders;
	}


	public boolean getAllowBidOrders() {
		return allowBidOrders;
	}


	public void setShareContactInd(boolean shareContactInd) {
		this.shareContactInd = shareContactInd;
	}


	public boolean isShareContactInd() {
		return shareContactInd;
	}
	
	public boolean getShareContactInd() {
		return shareContactInd;
	}


	public void setDraftOrder(boolean isDraftOrder) {
		this.isDraftOrder = isDraftOrder;
	}


	public boolean isDraftOrder() {
		return isDraftOrder;
	}


	public boolean isSealedBidOrder() {
		return isSealedBidOrder;
	}


	public void setSealedBidOrder(boolean isSealedBidOrder) {
		this.isSealedBidOrder = isSealedBidOrder;
	}


	public boolean isSealedBidInd() {
		return sealedBidInd;
	}


	public void setSealedBidInd(boolean sealedBidInd) {
		this.sealedBidInd = sealedBidInd;
	}


	public double getGroupTotalPermits() {
		return groupTotalPermits;
	}


	public void setGroupTotalPermits(double groupTotalPermits) {
		this.groupTotalPermits = groupTotalPermits;
	}


	public Double getPermitPrepaidPrice() {
		return permitPrepaidPrice;
	}


	public void setPermitPrepaidPrice(Double permitPrepaidPrice) {
		this.permitPrepaidPrice = permitPrepaidPrice;
	}


	public Double getCancellationFee() {
		return cancellationFee;
	}


	public void setCancellationFee(Double cancellationFee) {
		this.cancellationFee = cancellationFee;
	}


	public boolean isNonFundedInd() {
		return nonFundedInd;
	}


	public void setNonFundedInd(boolean nonFundedInd) {
		this.nonFundedInd = nonFundedInd;
	}


	public String getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}


	public String getLaborTaxPercentage() {
		return laborTaxPercentage;
	}


	public void setLaborTaxPercentage(String laborTaxPercentage) {
		this.laborTaxPercentage = org.apache.commons.lang.StringUtils.trim(laborTaxPercentage);
	}


	public String getPartsTax() {
		return partsTax;
	}


	public void setPartsTax(String partsTax) {
		this.partsTax = org.apache.commons.lang.StringUtils.trim(partsTax);
	}


	public List<ServiceOrderCustomRefVO> getCustomRefs() {
		return customRefs;
	}


	public void setCustomRefs(List<ServiceOrderCustomRefVO> customRefs) {
		this.customRefs = customRefs;
	}


   

    



	


	


}//end class

