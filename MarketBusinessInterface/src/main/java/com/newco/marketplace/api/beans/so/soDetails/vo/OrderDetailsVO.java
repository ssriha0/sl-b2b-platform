package com.newco.marketplace.api.beans.so.soDetails.vo;

import java.math.BigDecimal;


public class OrderDetailsVO {

	private String soId ;
	private String soStatus;	
	private String soSubStatus;	
	private String soTitle;	
	private String overView;
	private String buyerTerms;
	private String specialInstuctions;
	private BigDecimal spendLimitLabour;
	private BigDecimal spendLimitParts;
	private String priceModel;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}
	public String getSoSubStatus() {
		return soSubStatus;
	}
	public void setSoSubStatus(String soSubStatus) {
		this.soSubStatus = soSubStatus;
	}
	public String getSoTitle() {
		return soTitle;
	}
	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}
	public String getOverView() {
		return overView;
	}
	public void setOverView(String overView) {
		this.overView = overView;
	}
	public String getBuyerTerms() {
		return buyerTerms;
	}
	public void setBuyerTerms(String buyerTerms) {
		this.buyerTerms = buyerTerms;
	}
	public String getSpecialInstuctions() {
		return specialInstuctions;
	}
	public void setSpecialInstuctions(String specialInstuctions) {
		this.specialInstuctions = specialInstuctions;
	}
	public BigDecimal getSpendLimitLabour() {
		return spendLimitLabour;
	}
	public void setSpendLimitLabour(BigDecimal spendLimitLabour) {
		this.spendLimitLabour = spendLimitLabour;
	}
	public BigDecimal getSpendLimitParts() {
		return spendLimitParts;
	}
	public void setSpendLimitParts(BigDecimal spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}
	public String getPriceModel() {
		return priceModel;
	}
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	

}
