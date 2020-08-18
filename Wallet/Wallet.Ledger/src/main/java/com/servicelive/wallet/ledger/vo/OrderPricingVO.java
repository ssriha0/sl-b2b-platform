package com.servicelive.wallet.ledger.vo;

import java.io.Serializable;

import com.servicelive.common.util.MoneyUtil;

// TODO: Auto-generated Javadoc
/**
 * Class OrderPricingVO.
 */
public class OrderPricingVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1769752677501489327L;

	/** accessFee. */
	private Double accessFee;

	/** achAmount. */
	private Double achAmount;

	/** addOnTotal. */
	private Double addOnTotal;

	/** amount. */
	private Double amount;

	/** cancellationFee. */
	private Double cancellationFee;

	/** fundingAmountCC. */
	private Double fundingAmountCC;

	/** increaseSpendLimit. */
	private Double increaseSpendLimit;

	/** initialPrice. */
	private Double initialPrice;

	/** LaborFinalPrice. */
	private Double LaborFinalPrice = 0.0d;

	/** PartsFinalPrice. */
	private Double PartsFinalPrice = 0.0d;

	/** postingFee. */
	private Double postingFee;

	/** retailCancellationFee. */
	private Double retailCancellationFee;

	/** retailPrice. */
	private Double retailPrice;

	/** serviceFeePercentage. */
	private Double serviceFeePercentage;

	/** spendLimitLabor. */
	private Double spendLimitLabor;

	/** spendLimitParts. */
	private Double spendLimitParts;

	/** providerInvoiceParts.
	 *  Retail Price * % parts cost to inventory*/
	private Double retailPrices;
	
	/** providerInvoiceParts. 
	 * (Retail Price * Reimbursment) - (retailPrices)*/
	private Double reimbursementRetailPrice;
	
	/** providerInvoiceParts. 
	 * (Labor * SL Grossup) * 10%*/
	private Double partsSLGrossup; 
	
	/** providerInvoiceParts. 
	 * Retail Price * SL Grossup*/
	private Double retailPriceSLGrossup;
	
	private boolean isHSR = false;
	
	public boolean isHSR() {
		return isHSR;
	}

	public void setHSR(boolean isHSR) {
		this.isHSR = isHSR;
	}
	/**
	 * getAccessFee.
	 * 
	 * @return Double
	 */
	public Double getAccessFee() {

		return accessFee;
	}

	/**
	 * getAchAmount.
	 * 
	 * @return Double
	 */
	public Double getAchAmount() {

		return achAmount;
	}

	/**
	 * getAddOnServiceFee.
	 * 
	 * @return Double
	 */
	public Double getAddOnServiceFee() {

		Double addOnServiceFee = MoneyUtil.getRoundedMoneyCustom(addOnTotal * serviceFeePercentage);
		return addOnServiceFee;
	}

	/**
	 * getAddOnTotal.
	 * 
	 * @return Double
	 */
	public Double getAddOnTotal() {

		return addOnTotal;
	}

	/**
	 * getAmount.
	 * 
	 * @return Double
	 */
	public Double getAmount() {

		return amount;
	}

	/**
	 * getCancellationFee.
	 * 
	 * @return Double
	 */
	public Double getCancellationFee() {

		return cancellationFee;
	}

	/**
	 * getFinalCost.
	 * 
	 * @return double
	 */
	public double getFinalCost() {

		double orderFinalAmount = 0.0;
		double labor = 0.0;
		double parts = 0.0;
		double upsell = 0.0;
		if (getLaborFinalPrice() != null) {
			labor = getLaborFinalPrice();
		}
		if (getPartsFinalPrice() != null) {
			parts = getPartsFinalPrice();
		}
		if (getAddOnTotal() != null) {
			upsell = getAddOnTotal();
		}

		orderFinalAmount = labor + parts + upsell;
		return orderFinalAmount;
	}

	/**
	 * getFundingAmountCC.
	 * 
	 * @return Double
	 */
	public Double getFundingAmountCC() {

		return fundingAmountCC;
	}

	/**
	 * getIncreaseSpendLimit.
	 * 
	 * @return Double
	 */
	public Double getIncreaseSpendLimit() {

		return increaseSpendLimit;
	}

	/**
	 * getInitialPrice.
	 * 
	 * @return Double
	 */
	public Double getInitialPrice() {

		return initialPrice;
	}

	/**
	 * getLaborFinalPrice.
	 * 
	 * @return Double
	 */
	public Double getLaborFinalPrice() {

		return LaborFinalPrice;
	}

	/**
	 * getPartsFinalPrice.
	 * 
	 * @return Double
	 */
	public Double getPartsFinalPrice() {

		return PartsFinalPrice;
	}

	/**
	 * getPostingFee.
	 * 
	 * @return Double
	 */
	public Double getPostingFee() {

		return postingFee;
	}

	/**
	 * getRetailCancellationFee.
	 * 
	 * @return Double
	 */
	public Double getRetailCancellationFee() {

		return retailCancellationFee;
	}

	/**
	 * getRetailPrice.
	 * 
	 * @return Double
	 */
	public Double getRetailPrice() {

		return retailPrice;
	}

	/**
	 * getServiceFee.
	 * 
	 * @return double
	 */
	public double getServiceFee() {

		double labor = 0.0;
		double parts = 0.0;
		if (getLaborFinalPrice() != null) {
			labor = getLaborFinalPrice();
		}
		if (getPartsFinalPrice() != null) {
			parts = getPartsFinalPrice();
		}
		Double serviceFee = MoneyUtil.getRoundedMoney((labor + parts) * getServiceFeePercentage());
		return serviceFee;
	}

	/**
	 * getServiceFeePercentage.
	 * 
	 * @return Double
	 */
	public Double getServiceFeePercentage() {

		return serviceFeePercentage;
	}

	/**
	 * getSpendLimit.
	 * 
	 * @return double
	 */
	public double getSpendLimit() {

		double orderLimitAmount = 0.0;
		orderLimitAmount = getSpendLimitLabor() + getSpendLimitParts();
		return orderLimitAmount;
	}

	/**
	 * getSpendLimitLabor.
	 * 
	 * @return Double
	 */
	public Double getSpendLimitLabor() {

		return spendLimitLabor;
	}

	/**
	 * getSpendLimitParts.
	 * 
	 * @return Double
	 */
	public Double getSpendLimitParts() {

		return spendLimitParts;
	}

	/**
	 * getTotalServiceFee.
	 * 
	 * @return double
	 */
	public double getTotalServiceFee() {

		Double totalServiceFee = MoneyUtil.getRoundedMoney(getServiceFee() + getAddOnServiceFee());
		return totalServiceFee;
	}

	/**
	 * getTotalSpendLimit.
	 * 
	 * @return Double
	 */
	public Double getTotalSpendLimit() {

		return (getSpendLimit() + getAddOnTotal());
	}

	/**
	 * setAccessFee.
	 * 
	 * @param accessFee 
	 * 
	 * @return void
	 */
	public void setAccessFee(Double accessFee) {

		this.accessFee = accessFee;
	}

	/**
	 * setAchAmount.
	 * 
	 * @param achAmount 
	 * 
	 * @return void
	 */
	public void setAchAmount(Double achAmount) {

		this.achAmount = achAmount;
	}

	/**
	 * setAddOnTotal.
	 * 
	 * @param addOnTotal 
	 * 
	 * @return void
	 */
	public void setAddOnTotal(Double addOnTotal) {

		this.addOnTotal = addOnTotal;
	}

	/**
	 * setAmount.
	 * 
	 * @param amount 
	 * 
	 * @return void
	 */
	public void setAmount(Double amount) {

		this.amount = amount;
	}

	/**
	 * setCancellationFee.
	 * 
	 * @param cancellationFee 
	 * 
	 * @return void
	 */
	public void setCancellationFee(Double cancellationFee) {

		this.cancellationFee = cancellationFee;
	}

	/**
	 * setFundingAmountCC.
	 * 
	 * @param fundingAmountCC 
	 * 
	 * @return void
	 */
	public void setFundingAmountCC(Double fundingAmountCC) {

		this.fundingAmountCC = fundingAmountCC;
	}

	/**
	 * setIncreaseSpendLimit.
	 * 
	 * @param increaseSpendLimit 
	 * 
	 * @return void
	 */
	public void setIncreaseSpendLimit(Double increaseSpendLimit) {

		this.increaseSpendLimit = increaseSpendLimit;
	}

	/**
	 * setInitialPrice.
	 * 
	 * @param initialPrice 
	 * 
	 * @return void
	 */
	public void setInitialPrice(Double initialPrice) {

		this.initialPrice = initialPrice;
	}

	/**
	 * setLaborFinalPrice.
	 * 
	 * @param laborFinalPrice 
	 * 
	 * @return void
	 */
	public void setLaborFinalPrice(Double laborFinalPrice) {

		LaborFinalPrice = laborFinalPrice;
	}

	/**
	 * setPartsFinalPrice.
	 * 
	 * @param partsFinalPrice 
	 * 
	 * @return void
	 */
	public void setPartsFinalPrice(Double partsFinalPrice) {

		PartsFinalPrice = partsFinalPrice;
	}

	/**
	 * setPostingFee.
	 * 
	 * @param postingFee 
	 * 
	 * @return void
	 */
	public void setPostingFee(Double postingFee) {

		this.postingFee = postingFee;
	}

	/**
	 * setRetailCancellationFee.
	 * 
	 * @param retailCancellationFee 
	 * 
	 * @return void
	 */
	public void setRetailCancellationFee(Double retailCancellationFee) {

		this.retailCancellationFee = retailCancellationFee;
	}

	/**
	 * setRetailPrice.
	 * 
	 * @param retailPrice 
	 * 
	 * @return void
	 */
	public void setRetailPrice(Double retailPrice) {

		this.retailPrice = retailPrice;
	}

	/**
	 * setServiceFeePercentage.
	 * 
	 * @param serviceFeePercentage 
	 * 
	 * @return void
	 */
	public void setServiceFeePercentage(Double serviceFeePercentage) {

		this.serviceFeePercentage = serviceFeePercentage;
	}

	/**
	 * setSpendLimitLabor.
	 * 
	 * @param spendLimitLabor 
	 * 
	 * @return void
	 */
	public void setSpendLimitLabor(Double spendLimitLabor) {

		this.spendLimitLabor = spendLimitLabor;
	}

	/**
	 * setSpendLimitParts.
	 * 
	 * @param spendLimitParts 
	 * 
	 * @return void
	 */
	public void setSpendLimitParts(Double spendLimitParts) {

		this.spendLimitParts = spendLimitParts;
	}
	
	public Double getRetailPrices() {
		return retailPrices;
	}

	public void setRetailPrices(Double retailPrices) {
		this.retailPrices = retailPrices;
	}

	public Double getReimbursementRetailPrice() {
		return reimbursementRetailPrice;
	}

	public void setReimbursementRetailPrice(Double reimbursementRetailPrice) {
		this.reimbursementRetailPrice = reimbursementRetailPrice;
	}

	public Double getPartsSLGrossup() {
		return partsSLGrossup;
	}

	public void setPartsSLGrossup(Double partsSLGrossup) {
		this.partsSLGrossup = partsSLGrossup;
	}

	public Double getRetailPriceSLGrossup() {
		return retailPriceSLGrossup;
	}

	public void setRetailPriceSLGrossup(Double retailPriceSLGrossup) {
		this.retailPriceSLGrossup = retailPriceSLGrossup;
	}

}
