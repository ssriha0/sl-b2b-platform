package com.newco.marketplace.webservices.dto.serviceorder;

import org.codehaus.xfire.aegis.type.java5.XmlElement;

public class ClientServiceOrderNoteRequest extends ABaseWebserviceRequest {
	
	private static final long serialVersionUID = 1L;
	private String orderIDString;
	private String note;
	private String subject;
	private Integer roleId;
	private String createdBy;
	private String noteType;
	
	@XmlElement(minOccurs="1",nillable=false)
	public String getOrderIDString() {
		return orderIDString;
	}
	public void setOrderIDString(String orderIDString) {
		this.orderIDString = orderIDString;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
