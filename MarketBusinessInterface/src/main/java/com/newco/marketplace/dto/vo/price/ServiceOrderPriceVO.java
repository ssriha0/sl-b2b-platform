package com.newco.marketplace.dto.vo.price;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderPriceVO extends SerializableBaseVO{
	
	private static final long serialVersionUID = 0L;
	
	private String soId;
	private Double origSpendLimitLabor;
	private Double origSpendLimitParts;
	private Double discountedSpendLimitLabor;
	private Double discountedSpendLimitParts;
	private Double conditionalOfferPrice;
	private Double finalServiceFee;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String priceType;
	private Integer wfState;
	//SL-21945
	private Double partsTax;
	private Double partsDiscount;
	private Double laborTax;
	private Double laborDiscount;
	

	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Double getOrigSpendLimitLabor() {
		return origSpendLimitLabor;
	}
	public void setOrigSpendLimitLabor(Double origSpendLimitLabor) {
		this.origSpendLimitLabor = origSpendLimitLabor;
	}
	public Double getOrigSpendLimitParts() {
		return origSpendLimitParts;
	}
	public void setOrigSpendLimitParts(Double origSpendLimitParts) {
		this.origSpendLimitParts = origSpendLimitParts;
	}
	public Double getDiscountedSpendLimitLabor() {
		return discountedSpendLimitLabor;
	}
	public void setDiscountedSpendLimitLabor(Double discountedSpendLimitLabor) {
		this.discountedSpendLimitLabor = discountedSpendLimitLabor;
	}
	public Double getDiscountedSpendLimitParts() {
		return discountedSpendLimitParts;
	}
	public void setDiscountedSpendLimitParts(Double discountedSpendLimitParts) {
		this.discountedSpendLimitParts = discountedSpendLimitParts;
	}
	public Double getConditionalOfferPrice() {
		return conditionalOfferPrice;
	}
	public void setConditionalOfferPrice(Double conditionalOfferPrice) {
		this.conditionalOfferPrice = conditionalOfferPrice;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the finalServiceFee
	 */
	public Double getFinalServiceFee() {
		return finalServiceFee;
	}
	/**
	 * @param finalServiceFee the finalServiceFee to set
	 */
	public void setFinalServiceFee(Double finalServiceFee) {
		this.finalServiceFee = finalServiceFee;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public Integer getWfState() {
		return wfState;
	}
	public void setWfState(Integer wfState) {
		this.wfState = wfState;
	}
	public Double getPartsTax() {
		return partsTax;
	}
	public void setPartsTax(Double partsTax) {
		this.partsTax = partsTax;
	}
	public Double getPartsDiscount() {
		return partsDiscount;
	}
	public void setPartsDiscount(Double partsDiscount) {
		this.partsDiscount = partsDiscount;
	}
	public Double getLaborTax() {
		return laborTax;
	}
	public void setLaborTax(Double laborTax) {
		this.laborTax = laborTax;
	}
	public Double getLaborDiscount() {
		return laborDiscount;
	}
	public void setLaborDiscount(Double laborDiscount) {
		this.laborDiscount = laborDiscount;
	}
	
	
	
	
	
}
