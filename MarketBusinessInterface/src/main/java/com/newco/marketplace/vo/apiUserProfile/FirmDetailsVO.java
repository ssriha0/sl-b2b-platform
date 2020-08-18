package com.newco.marketplace.vo.apiUserProfile;

public class FirmDetailsVO {
	
	private String businessName;
	private Integer vendorId;
	private Integer resourceId;
	private Integer primaryIndustryId;
	private String primaryIndustryDesc;
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getPrimaryIndustryId() {
		return primaryIndustryId;
	}
	public void setPrimaryIndustryId(Integer primaryIndustryId) {
		this.primaryIndustryId = primaryIndustryId;
	}
	public String getPrimaryIndustryDesc() {
		return primaryIndustryDesc;
	}
	public void setPrimaryIndustryDesc(String primaryIndustryDesc) {
		this.primaryIndustryDesc = primaryIndustryDesc;
	}
	
}
