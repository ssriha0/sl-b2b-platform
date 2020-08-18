package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.Date;

//Vo class which holds lead_history values.
public class SLLeadHistoryVO {

	private Integer lead_history_id=null;
	private String sl_lead_id=null;
	private Integer actionId=null;
	private String actionDescription=null;
	private String actionName=null;	
	private String chgComment=null;
	private Date createdDate;
	private String modifiedBy=null;
	private Date modifiedDate;
	private Integer roleId;
	private String roleName;
	private String createdBy;
	private String createdActualDate;
	private Integer entityId;
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public String getCreatedActualDate() {
		return createdActualDate;
	}
	public void setCreatedActualDate(String createdActualDate) {
		this.createdActualDate = createdActualDate;
	}
	public Integer getLead_history_id() {
		return lead_history_id;
	}
	public void setLead_history_id(Integer lead_history_id) {
		this.lead_history_id = lead_history_id;
	}
	public String getSl_lead_id() {
		return sl_lead_id;
	}
	public void setSl_lead_id(String sl_lead_id) {
		this.sl_lead_id = sl_lead_id;
	}
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public String getActionDescription() {
		return actionDescription;
	}
	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}
	public String getChgComment() {
		return chgComment;
	}
	public void setChgComment(String chgComment) {
		this.chgComment = chgComment;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	
		
	

	
}
