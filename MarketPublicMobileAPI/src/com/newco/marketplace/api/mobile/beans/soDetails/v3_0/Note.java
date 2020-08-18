package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("note")
@XmlAccessorType(XmlAccessType.FIELD)
public class Note {
	
	@XStreamAlias("noteId")
	private Integer noteId;
	
	@XStreamAlias("noteSubject")
	private String noteSubject;
	
	@XStreamAlias("noteBody")
	private String noteBody;
	
	@XStreamAlias("noteType")
	private String noteType;
	
	
	@XStreamAlias("author")
	private String author;
	
	@XStreamAlias("createdDate")
	private String createdDate;


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


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

	public String getNoteBody() {
		return noteBody;
	}


	public void setNoteBody(String noteBody) {
		this.noteBody = noteBody;
	}


	public String getNoteType() {
		return noteType;
	}


	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}



}
