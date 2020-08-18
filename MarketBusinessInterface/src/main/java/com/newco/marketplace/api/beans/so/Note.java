package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a generic bean class for storing note information.
 * @author Infosys
 *
 */
@XStreamAlias("note")
public class Note {
	
	@XStreamAlias("roleId")
	private Integer roleId;
	
	@XStreamAlias("entityID")
	private Integer entityID;
	
	@XStreamAlias("createdDate")
	private String createdDate;
	
	@XStreamAlias("createdByName")
	private String createdByName;
	
	@XStreamAlias("subject")
	private String subject;
	
	@XStreamAlias("noteBody")
	private String noteBody;
	
	@XStreamAlias("noteType")
	private String noteType;

	@XStreamAlias("private")
	@XStreamAsAttribute()   
	private String privateInd;

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String cretaedByName) {
		this.createdByName = cretaedByName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getNoteBody() {
		return noteBody;
	}

	public void setNoteBody(String noteBody) {
		this.noteBody = noteBody;
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

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public String getPrivateInd() {
		return privateInd;
	}

	public void setPrivateInd(String privateInd) {
		this.privateInd = privateInd;
	}
	

}
