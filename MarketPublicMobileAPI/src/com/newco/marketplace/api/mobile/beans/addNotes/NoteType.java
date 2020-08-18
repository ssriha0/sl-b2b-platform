package com.newco.marketplace.api.mobile.beans.addNotes;

import javax.xml.bind.annotation.XmlAttribute;

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
	@XmlAttribute(name="private")
	public boolean isPrivateInd() {
		return privateInd;
	}

	public void setPrivateInd(boolean privateInd) {
		this.privateInd = privateInd;
	}
	@XmlAttribute(name="support")
	public boolean isSupportInd() {
		return supportInd;
	}

	public void setSupportInd(boolean supportInd) {
		this.supportInd = supportInd;
	}

	

}
