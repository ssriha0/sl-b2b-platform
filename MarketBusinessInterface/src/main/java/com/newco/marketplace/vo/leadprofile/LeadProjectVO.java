package com.newco.marketplace.vo.leadprofile;

public class LeadProjectVO {
	
	private String vendorId;
	private Integer categoryID;	
	private Integer projectId;	
	private boolean exclusive = false;	
	private boolean compPrice = false;	
	
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public boolean isExclusive() {
		return exclusive;
	}
	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}
	public boolean isCompPrice() {
		return compPrice;
	}
	public void setCompPrice(boolean compPrice) {
		this.compPrice = compPrice;
	}

	
	
}
