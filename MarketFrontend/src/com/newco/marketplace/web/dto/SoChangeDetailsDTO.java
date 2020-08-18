package com.newco.marketplace.web.dto;


public class SoChangeDetailsDTO extends SerializedBaseDTO{
	
	/**
	 *
	 */
	private static final long serialVersionUID = -2044411764190215587L;
	private String actionDescription=null;
	private String chgComment=null;
	private String createdDate;
	private String roleName;
	private String createdByName;
	private Integer entityId;	
	
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
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

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	

}
