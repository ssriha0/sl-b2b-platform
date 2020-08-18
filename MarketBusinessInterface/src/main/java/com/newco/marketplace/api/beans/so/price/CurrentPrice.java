package com.newco.marketplace.api.beans.so.price;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("currentPrice")
public class CurrentPrice {
	
	@XStreamAlias("maxPrice")
	private MaxPrice maxPrice;
	
	@XStreamAlias("finalPrice")
	private FinalPrice finalPrice;
	
	@XStreamAlias("totalMaxPrice")
	private BigDecimal totalMaxPrice;
	
	@XStreamAlias("totalFinalPrice")
	private BigDecimal totalFinalPrice;
	
	@XStreamAlias("buyerPostingFee")
	private BigDecimal buyerPostingFee;
	
	@XStreamAlias("providerServiceFee")
	private BigDecimal providerServiceFee;
	
	@XStreamAlias("providerPayment")
	private BigDecimal providerPayment;

	public MaxPrice getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(MaxPrice maxPrice) {
		this.maxPrice = maxPrice;
	}

	public FinalPrice getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(FinalPrice finalPrice) {
		this.finalPrice = finalPrice;
	}

	public BigDecimal getTotalMaxPrice() {
		return totalMaxPrice;
	}

	public void setTotalMaxPrice(BigDecimal totalMaxPrice) {
		this.totalMaxPrice = totalMaxPrice;
	}

	public BigDecimal getTotalFinalPrice() {
		return totalFinalPrice;
	}

	public void setTotalFinalPrice(BigDecimal totalFinalPrice) {
		this.totalFinalPrice = totalFinalPrice;
	}

	public BigDecimal getBuyerPostingFee() {
		return buyerPostingFee;
	}

	public void setBuyerPostingFee(BigDecimal buyerPostingFee) {
		this.buyerPostingFee = buyerPostingFee;
	}

	public BigDecimal getProviderServiceFee() {
		return providerServiceFee;
	}

	public void setProviderServiceFee(BigDecimal providerServiceFee) {
		this.providerServiceFee = providerServiceFee;
	}

	public BigDecimal getProviderPayment() {
		return providerPayment;
	}

	public void setProviderPayment(BigDecimal providerPayment) {
		this.providerPayment = providerPayment;
	}

	
	
}
