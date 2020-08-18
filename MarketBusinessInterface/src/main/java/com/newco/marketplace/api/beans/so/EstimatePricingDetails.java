package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("pricing")
public class EstimatePricingDetails {
		
	@XStreamAlias("totalLaborPrice")
	private String totalLaborPrice;

	@XStreamAlias("totalPartsPrice")
	private String totalPartsPrice;
	
	@XStreamAlias("totalOtherServicePrice")
    private String totalOtherServicePrice;

	@XStreamAlias("subTotal")
	private String subTotal;

	@XStreamAlias("discountType")
	private String discountType;

	@XStreamAlias("discountedPercentage")
	private String discountedPercentage; 

	@XStreamAlias("discountedAmount")
	private String discountedAmount;

	@XStreamAlias("taxRate")
	private String taxRate;

	@XStreamAlias("taxType")
	private String taxType;

	@XStreamAlias("taxPrice")
	private String taxPrice;

	@XStreamAlias("totalPrice")
	private String totalPrice;
	
	@OptionalParam
	@XStreamAlias("laborDiscountType")
	private String laborDiscountType;
	
	@OptionalParam
	@XStreamAlias("laborDiscountedPercentage")
	private String laborDiscountedPercentage;
	
	@OptionalParam
	@XStreamAlias("laborDiscountedAmount")
	private String laborDiscountedAmount;
	
	@OptionalParam
	@XStreamAlias("laborTaxRate")
	private String laborTaxRate;
	
	@OptionalParam
	@XStreamAlias("laborTaxPrice")
	private String laborTaxPrice;
	
	@OptionalParam
	@XStreamAlias("partsDiscountType")
	private String partsDiscountType;
	
	@OptionalParam
	@XStreamAlias("partsDiscountedPercentage")
	private String partsDiscountedPercentage;
	
	@OptionalParam
	@XStreamAlias("partsDiscountedAmount")
	private String partsDiscountedAmount;
	
	@OptionalParam
	@XStreamAlias("partsTaxRate")
	private String partsTaxRate;
	
	@OptionalParam
	@XStreamAlias("partsTaxPrice")
	private String partsTaxPrice;

	public String getTotalLaborPrice() {
		return totalLaborPrice;
	}

	public void setTotalLaborPrice(String totalLaborPrice) {
		this.totalLaborPrice = totalLaborPrice;
	}

	public String getTotalPartsPrice() {
		return totalPartsPrice;
	}

	public void setTotalPartsPrice(String totalPartsPrice) {
		this.totalPartsPrice = totalPartsPrice;
	}

	public String getTotalOtherServicePrice() {
		return totalOtherServicePrice;
	}

	public void setTotalOtherServicePrice(String totalOtherServicePrice) {
		this.totalOtherServicePrice = totalOtherServicePrice;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountedPercentage() {
		return discountedPercentage;
	}

	public void setDiscountedPercentage(String discountedPercentage) {
		this.discountedPercentage = discountedPercentage;
	}

	public String getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(String discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(String taxPrice) {
		this.taxPrice = taxPrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getLaborDiscountType() {
		return laborDiscountType;
	}

	public void setLaborDiscountType(String laborDiscountType) {
		this.laborDiscountType = laborDiscountType;
	}

	public String getLaborDiscountedPercentage() {
		return laborDiscountedPercentage;
	}

	public void setLaborDiscountedPercentage(String laborDiscountedPercentage) {
		this.laborDiscountedPercentage = laborDiscountedPercentage;
	}

	public String getLaborDiscountedAmount() {
		return laborDiscountedAmount;
	}

	public void setLaborDiscountedAmount(String laborDiscountedAmount) {
		this.laborDiscountedAmount = laborDiscountedAmount;
	}

	public String getLaborTaxRate() {
		return laborTaxRate;
	}

	public void setLaborTaxRate(String laborTaxRate) {
		this.laborTaxRate = laborTaxRate;
	}

	public String getLaborTaxPrice() {
		return laborTaxPrice;
	}

	public void setLaborTaxPrice(String laborTaxPrice) {
		this.laborTaxPrice = laborTaxPrice;
	}

	public String getPartsDiscountType() {
		return partsDiscountType;
	}

	public void setPartsDiscountType(String partsDiscountType) {
		this.partsDiscountType = partsDiscountType;
	}

	public String getPartsDiscountedPercentage() {
		return partsDiscountedPercentage;
	}

	public void setPartsDiscountedPercentage(String partsDiscountedPercentage) {
		this.partsDiscountedPercentage = partsDiscountedPercentage;
	}

	public String getPartsDiscountedAmount() {
		return partsDiscountedAmount;
	}

	public void setPartsDiscountedAmount(String partsDiscountedAmount) {
		this.partsDiscountedAmount = partsDiscountedAmount;
	}

	public String getPartsTaxRate() {
		return partsTaxRate;
	}

	public void setPartsTaxRate(String partsTaxRate) {
		this.partsTaxRate = partsTaxRate;
	}

	public String getPartsTaxPrice() {
		return partsTaxPrice;
	}

	public void setPartsTaxPrice(String partsTaxPrice) {
		this.partsTaxPrice = partsTaxPrice;
	}
	
			
}
