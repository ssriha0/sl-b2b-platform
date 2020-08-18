package com.newco.marketplace.api.beans.so.addNote.v1_1;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class to store information on the new note type
 * @author Infosys
 *
 */

@XStreamAlias("note")
public class NoteType {
	
	@XStreamAlias("subject")
	private String subject;
	
	@XStreamAlias("noteBody")
	private String noteBody;
	
	@XStreamAlias("private")
	@XStreamAsAttribute()   
	private boolean privateInd;
	
	@XStreamAlias("support")
	@XStreamAsAttribute()   
	private boolean supportInd;

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

	public boolean isPrivateInd() {
		return privateInd;
	}

	public void setPrivateInd(boolean privateInd) {
		this.privateInd = privateInd;
	}

	public boolean isSupportInd() {
		return supportInd;
	}

	public void setSupportInd(boolean supportInd) {
		this.supportInd = supportInd;
	}

}
