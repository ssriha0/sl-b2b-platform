package com.newco.marketplace.webservices.dto.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

public class NoteRequest extends ABaseWebserviceRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -895750420605879080L;
	private String note;
	private String subject;
	 
	//private Integer refNoteId;
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("note", getNote())
			.toString();
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
}
