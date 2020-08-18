package com.newco.marketplace.api.beans.leaddetailmanagement.addNotes;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class to store information on the new note type
 * @author Infosys
 *
 */

@XStreamAlias("leadNote")
public class LeadNoteType {
	
	@XStreamAlias("noteCategory")
	private String noteCategory;
	
	@XStreamAlias("noteType")
	private String noteType;
	
	@XStreamAlias("noteBody")
	private String noteBody;
	
	public String getNoteCategory() {
		return noteCategory;
	}

	public void setNoteCategory(String noteCategory) {
		this.noteCategory = noteCategory;
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
