package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing log entry information.
 * @author Infosys
 *
 */
@XStreamAlias("logEntry")
public class LogEntry {
	@XStreamAlias("roleId")
	private Integer roleId;
	
	@XStreamAlias("entityID")
	private Integer entityID;
	
	@XStreamAlias("createdDate")
	private String createdDate;
	
	@XStreamAlias("createdByName")
	private String createdByName;
	
	@XStreamAlias("action")
	private String action;
	
	@XStreamAlias("comment")
	private String comment;
	
	@XStreamAlias("value")
	private String value;

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getEntityID() {
		return entityID;
	}

	public void setEntityID(Integer entityID) {
		this.entityID = entityID;
	}
}
