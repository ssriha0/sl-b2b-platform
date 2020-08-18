package com.newco.marketplace.translator.dto;

public class SkuPrice extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7451284188331440229L;
	private String sku;
	private Double retailPrice;
	private Double margin;
	private Double adjustment;
	private Integer leadTime;
	private Double sellingPrice;
	private String specialtyCode;
	private String jobCodeType;
	private String coverage;
	private Double actualSellingPrice;
	
	public String getSpecialtyCode() {
		return specialtyCode;
	}
	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Double getMargin() {
		return margin;
	}
	public void setMargin(Double margin) {
		this.margin = margin;
	}
	public Double getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}
	public Integer getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(Integer leadTime) {
		this.leadTime = leadTime;
	}
	public Double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getJobCodeType() {
		return jobCodeType;
	}
	public void setJobCodeType(String jobCodeType) {
		this.jobCodeType = jobCodeType;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	public Double getActualSellingPrice() {
		return actualSellingPrice;
	}
	public void setActualSellingPrice(Double actualSellingPrice) {
		this.actualSellingPrice = actualSellingPrice;
	}
	
}
