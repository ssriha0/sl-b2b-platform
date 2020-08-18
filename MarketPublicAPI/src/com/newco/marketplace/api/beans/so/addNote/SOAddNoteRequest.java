package com.newco.marketplace.api.beans.so.addNote;

import com.newco.marketplace.api.beans.so.Identification;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the SOAddNoteService
 * @author Infosys
 *
 */
@XStreamAlias("addNoteRequest")
public class SOAddNoteRequest {
	
	@XStreamAlias("identification")
	private Identification identification;
	
	@XStreamAlias("note")
	private NewNoteType newNoteType;

	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

	public NewNoteType getNewNoteType() {
		return newNoteType;
	}

	public void setNewNoteType(NewNoteType newNoteType) {
		this.newNoteType = newNoteType;
	}
}
