package com.newco.marketplace.api.beans.so.addEstimate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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

	
		
}


