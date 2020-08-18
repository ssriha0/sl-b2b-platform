package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("supportNote")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupportNote {
	
	@XStreamAlias("noteId")
	private Integer noteId;
	
	@XStreamAlias("noteSubject")
	private String noteSubject;
	
	@XStreamAlias("note")
	private String note;
	
	public Integer getNoteId() {
	return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	public String getNoteSubject() {
		return noteSubject;
	}
	public void setNoteSubject(String noteSubject) {
		this.noteSubject = noteSubject;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	

}
