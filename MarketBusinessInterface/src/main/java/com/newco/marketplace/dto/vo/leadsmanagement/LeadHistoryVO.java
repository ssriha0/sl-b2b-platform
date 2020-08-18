package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.Date;

//Vo class which holds lead_history values.
public class LeadHistoryVO {
	
	private Integer leadHistoryId;
	private String slLeadId;
    private Integer actionId;
    private String actionName;
	private String oldValue;
	private String newValue;
	private String description;
	private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private Integer roleId;
    private String modifiedBy;
    private Integer enitityId;
    private String buyerFirstName;
    private String buyerLastName;
    private String vendorId;
    private String resourceId;
    
	public Integer getEnitityId() {
		return enitityId;
	}
	public void setEnitityId(Integer enitityId) {
		this.enitityId = enitityId;
	}
	public Integer getLeadHistoryId() {
		return leadHistoryId;
	}
	public void setLeadHistoryId(Integer leadHistoryId) {
		this.leadHistoryId = leadHistoryId;
	}
	public String getSlLeadId() {
		return slLeadId;
	}
	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getBuyerFirstName() {
		return buyerFirstName;
	}
	public void setBuyerFirstName(String buyerFirstName) {
		this.buyerFirstName = buyerFirstName;
	}
	public String getBuyerLastName() {
		return buyerLastName;
	}
	public void setBuyerLastName(String buyerLastName) {
		this.buyerLastName = buyerLastName;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
   
    
}