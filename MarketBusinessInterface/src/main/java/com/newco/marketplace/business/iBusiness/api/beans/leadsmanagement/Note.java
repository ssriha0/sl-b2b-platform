package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
@XmlRootElement(name = "Note")
@XStreamAlias("Note")
@XmlAccessorType(XmlAccessType.FIELD)
public class Note {

	@XStreamAlias("Subject")
	private String Subject;

	@XStreamAlias("Message")
	private String Message;
	
	@XStreamAlias("NoteBy")
	private String NoteBy;

	@XStreamAlias("NoteDate")
	private  String NoteDate;
	
	@XStreamAlias("RoleId")
	private  Integer roleId;
	
	@OptionalParam
	private Integer NoteId;
	
	@XStreamOmitField
	private String modifiedBy;
	
	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getNoteBy() {
		return NoteBy;
	}

	public void setNoteBy(String noteBy) {
		NoteBy = noteBy;
	}

	public String getNoteDate() {
		return NoteDate;
	}

	public void setNoteDate(String noteDate) {
		NoteDate = noteDate;
	}

	public Integer getNoteId() {
		return NoteId;
	}

	public void setNoteId(Integer noteId) {
		NoteId = noteId;
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
	
}
