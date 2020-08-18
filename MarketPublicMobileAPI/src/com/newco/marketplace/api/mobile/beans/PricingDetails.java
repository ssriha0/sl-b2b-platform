package com.newco.marketplace.api.mobile.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("pricing")
@XmlAccessorType(XmlAccessType.FIELD)
public class PricingDetails {
	
	@XStreamAlias("totalLaborPrice")
	private Double totalLaborPrice;
	
	@XStreamAlias("totalPartsPrice")
	private Double totalPartsPrice;
	
	@XStreamAlias("totalOtherServicePrice")
	private Double totalOtherServicePrice;
	
	@XStreamAlias("subTotal")
	private Double subTotal;
	
	@XStreamAlias("discountType")
	private String discountType;
	
	@XStreamAlias("discountedPercentage")
	private Double discountedPercentage;
	
	@XStreamAlias("discountedAmount")
	private Double discountedAmount;
	
	@XStreamAlias("taxRate")
	private Double taxRate;
	
	@XStreamAlias("taxType")
	private String taxType;
		
	@XStreamAlias("taxPrice")
	private Double taxPrice;
		
	@XStreamAlias("totalPrice")
	private Double totalPrice;
	
	@OptionalParam
	@XStreamAlias("laborDiscountType")
	private String laborDiscountType;
	
	@OptionalParam
	@XStreamAlias("laborDiscountedPercentage")
	private Double laborDiscountedPercentage;
	
	@OptionalParam
	@XStreamAlias("laborDiscountedAmount")
	private Double laborDiscountedAmount;
	
	@OptionalParam
	@XStreamAlias("laborTaxRate")
	private Double laborTaxRate;
	
	@OptionalParam
	@XStreamAlias("laborTaxPrice")
	private Double laborTaxPrice;
	
	@OptionalParam
	@XStreamAlias("partsDiscountType")
	private String partsDiscountType;
	
	@OptionalParam
	@XStreamAlias("partsDiscountedPercentage")
	private Double partsDiscountedPercentage;
	
	@OptionalParam
	@XStreamAlias("partsDiscountedAmount")
	private Double partsDiscountedAmount;
	
	@OptionalParam
	@XStreamAlias("partsTaxRate")
	private Double partsTaxRate;
	
	@OptionalParam
	@XStreamAlias("partsTaxPrice")
	private Double partsTaxPrice;

	
	public Double getTotalLaborPrice() {
		return totalLaborPrice;
	}

	public void setTotalLaborPrice(Double totalLaborPrice) {
		this.totalLaborPrice = totalLaborPrice;
	}

	public Double getTotalPartsPrice() {
		return totalPartsPrice;
	}

	public void setTotalPartsPrice(Double totalPartsPrice) {
		this.totalPartsPrice = totalPartsPrice;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Double getDiscountedPercentage() {
		return discountedPercentage;
	}

	public void setDiscountedPercentage(Double discountedPercentage) {
		this.discountedPercentage = discountedPercentage;
	}

	public Double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public Double getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(Double taxPrice) {
		this.taxPrice = taxPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
		
	public Double getTotalOtherServicePrice() {
		return totalOtherServicePrice;
	}

	public void setTotalOtherServicePrice(Double totalOtherServicePrice) {
		this.totalOtherServicePrice = totalOtherServicePrice;
	}

	public String getLaborDiscountType() {
		return laborDiscountType;
	}

	public void setLaborDiscountType(String laborDiscountType) {
		this.laborDiscountType = laborDiscountType;
	}

	public Double getLaborDiscountedPercentage() {
		return laborDiscountedPercentage;
	}

	public void setLaborDiscountedPercentage(Double laborDiscountedPercentage) {
		this.laborDiscountedPercentage = laborDiscountedPercentage;
	}

	public Double getLaborDiscountedAmount() {
		return laborDiscountedAmount;
	}

	public void setLaborDiscountedAmount(Double laborDiscountedAmount) {
		this.laborDiscountedAmount = laborDiscountedAmount;
	}

	public Double getLaborTaxRate() {
		return laborTaxRate;
	}

	public void setLaborTaxRate(Double laborTaxRate) {
		this.laborTaxRate = laborTaxRate;
	}

	public Double getLaborTaxPrice() {
		return laborTaxPrice;
	}

	public void setLaborTaxPrice(Double laborTaxPrice) {
		this.laborTaxPrice = laborTaxPrice;
	}

	public String getPartsDiscountType() {
		return partsDiscountType;
	}

	public void setPartsDiscountType(String partsDiscountType) {
		this.partsDiscountType = partsDiscountType;
	}

	public Double getPartsDiscountedPercentage() {
		return partsDiscountedPercentage;
	}

	public void setPartsDiscountedPercentage(Double partsDiscountedPercentage) {
		this.partsDiscountedPercentage = partsDiscountedPercentage;
	}

	public Double getPartsDiscountedAmount() {
		return partsDiscountedAmount;
	}

	public void setPartsDiscountedAmount(Double partsDiscountedAmount) {
		this.partsDiscountedAmount = partsDiscountedAmount;
	}

	public Double getPartsTaxRate() {
		return partsTaxRate;
	}

	public void setPartsTaxRate(Double partsTaxRate) {
		this.partsTaxRate = partsTaxRate;
	}

	public Double getPartsTaxPrice() {
		return partsTaxPrice;
	}

	public void setPartsTaxPrice(Double partsTaxPrice) {
		this.partsTaxPrice = partsTaxPrice;
	}

	

	
		
}


