package com.newco.marketplace.inhomeoutboundnotification.vo;



public class InHomeSODetailsVO {
	
	private String soId;
	private Integer resourceId;
	private Integer buyerId;
	private Integer vendorId;
	
	
    private Integer noteTypeId;
    private Integer roleId;
    private String subjMessage;
    private Integer empId;
    private String createdBy;
    private boolean isSlAdmin;
    
    
	
	public boolean isSlAdmin() {
		return isSlAdmin;
	}
	public void setSlAdmin(boolean isSlAdmin) {
		this.isSlAdmin = isSlAdmin;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getNoteTypeId() {
		return noteTypeId;
	}
	public void setNoteTypeId(Integer noteTypeId) {
		this.noteTypeId = noteTypeId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getSubjMessage() {
		return subjMessage;
	}
	public void setSubjMessage(String subjMessage) {
		this.subjMessage = subjMessage;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	
	
	
	
	
	
	
}
