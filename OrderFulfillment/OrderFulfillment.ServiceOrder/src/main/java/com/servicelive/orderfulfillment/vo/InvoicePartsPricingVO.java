package com.servicelive.orderfulfillment.vo;



public class InvoicePartsPricingVO {

	private Double retailPrice;
	private Double retailReimbursement;
	private Double retailSlGrossUp;
	private Double finalPartsPrice;
	private String soId;
	
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Double getRetailReimbursement() {
		return retailReimbursement;
	}
	public void setRetailReimbursement(Double retailReimbursement) {
		this.retailReimbursement = retailReimbursement;
	}
	public Double getRetailSlGrossUp() {
		return retailSlGrossUp;
	}
	public void setRetailSlGrossUp(Double retailSlGrossUp) {
		this.retailSlGrossUp = retailSlGrossUp;
	}
	public Double getFinalPartsPrice() {
		return finalPartsPrice;
	}
	public void setFinalPartsPrice(Double finalPartsPrice) {
		this.finalPartsPrice = finalPartsPrice;
	}
	
	

}