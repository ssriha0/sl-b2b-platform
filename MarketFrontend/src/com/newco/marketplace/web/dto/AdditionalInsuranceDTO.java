package com.newco.marketplace.web.dto;


public class AdditionalInsuranceDTO extends SerializedBaseDTO
{
	private static final long serialVersionUID = 1L;
	private Integer vendorCredId;
	private Integer vendorId;
	private Integer categoryId;
	private String categoryName;
	private Integer policyAmount;
	private String policyExpiryDate;
	private Integer policyStatus;
	private String policyDescr;
	public Integer getVendorCredId() {
		return vendorCredId;
	}
	public void setVendorCredId(Integer vendorCredId) {
		this.vendorCredId = vendorCredId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getPolicyAmount() {
		return policyAmount;
	}
	public void setPolicyAmount(Integer policyAmount) {
		this.policyAmount = policyAmount;
	}
	public String getPolicyExpiryDate() {
		return policyExpiryDate;
	}
	public void setPolicyExpiryDate(String policyExpiryDate) {
		this.policyExpiryDate = policyExpiryDate;
	}
	public Integer getPolicyStatus() {
		return policyStatus;
	}
	public void setPolicyStatus(Integer policyStatus) {
		this.policyStatus = policyStatus;
	}
	public String getPolicyDescr() {
		return policyDescr;
	}
	public void setPolicyDescr(String policyDescr) {
		this.policyDescr = policyDescr;
	}
	
	
	
}
