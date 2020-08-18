package com.newco.marketplace.api.beans.so.addNote;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class to store information on the new note type
 * @author Infosys
 *
 */
@XStreamAlias("note")
public class NewNoteType {
	
	@XStreamAlias("subject")
	private String subject;
	
	@XStreamAlias("noteBody")
	private String noteBody;
	
	@XStreamAlias("private")
	@XStreamAsAttribute()   
	private boolean privateInd;

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

}
