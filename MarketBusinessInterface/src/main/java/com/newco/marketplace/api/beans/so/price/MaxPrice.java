package com.newco.marketplace.api.beans.so.price;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;



@XStreamAlias("maxPrice")
public class MaxPrice {
	
	@XStreamAlias("maxLaborPrice")
	private BigDecimal maxLabourPrice;
	
	@XStreamAlias("maxPartPrice")
	private BigDecimal maxPartsPrice;
	
	@XStreamAlias("maxPermitPrice")
	private BigDecimal customerPrePaid;

	public BigDecimal getMaxLabourPrice() {
		return maxLabourPrice;
	}

	public void setMaxLabourPrice(BigDecimal maxLabourPrice) {
		this.maxLabourPrice = maxLabourPrice;
	}

	public BigDecimal getMaxPartsPrice() {
		return maxPartsPrice;
	}

	public void setMaxPartsPrice(BigDecimal maxPartsPrice) {
		this.maxPartsPrice = maxPartsPrice;
	}

	public BigDecimal getCustomerPrePaid() {
		return customerPrePaid;
	}

	public void setCustomerPrePaid(BigDecimal customerPrePaid) {
		this.customerPrePaid = customerPrePaid;
	}

	
	
	
	
}
