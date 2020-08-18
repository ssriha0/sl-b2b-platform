package com.newco.marketplace.dto.vo.leadsmanagement;



//Vo class which holds sl_lead_hdr values.
public class LeadLoggingVO {
	
	private String slleadid;	
	private Integer actionId;		
	private String oldValue;	
	private String newValue;	
	private String chgComment;	
	private String createdBy;	
	private String modifiedBy;
	private Integer roleId;
	private Integer entityId;
	
	
	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public LeadLoggingVO(String slleadid, Integer actionId, String oldValue,
			String newValue, String chgComment, String createdBy,
			String modifiedBy, Integer roleId ,Integer entityId) {		
		this.slleadid = slleadid;
		this.actionId = actionId;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.chgComment = chgComment;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.roleId = roleId;
		this.entityId = entityId;
	}
	
	public LeadLoggingVO(){
		
	}
	
	public String getSlleadid() {
		return slleadid;
	}
	public void setSlleadid(String slleadid) {
		this.slleadid = slleadid;
	}
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
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
	public String getChgComment() {
		return chgComment;
	}
	public void setChgComment(String chgComment) {
		this.chgComment = chgComment;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	
	}
