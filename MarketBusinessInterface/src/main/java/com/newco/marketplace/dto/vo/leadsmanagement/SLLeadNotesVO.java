package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.Date;

//Vo class which holds lead_notes values.
public class SLLeadNotesVO {
	
	private Integer leadNoteId;
	private Integer entityId;
	private String slLeadId;
    private String noteCategory;
	private String note;
	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
    private String modifiedBy;
    private String noteType;
    private Integer roleId;
    private String alertSendTo;
    private String subject;
    

	
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public String getAlertSendTo() {
		return alertSendTo;
	}
	public void setAlertSendTo(String alertSendTo) {
		this.alertSendTo = alertSendTo;
	}
	public Integer getLeadNoteId() {
		return leadNoteId;
	}
	public void setLeadNoteId(Integer leadNoteId) {
		this.leadNoteId = leadNoteId;
	}
	public String getSlLeadId() {
		return slLeadId;
	}
	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}

	public String getNoteCategory() {
		return noteCategory;
	}
	public void setNoteCategory(String noteCategory) {
		this.noteCategory = noteCategory;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

    
}
