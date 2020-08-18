package com.newco.marketplace.api.mobile.beans.addNotes;


import com.newco.marketplace.api.annotation.XSD;
//import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
//import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a bean class for storing request information for 
 * the AddNoteService
 * @author Infosys
 *
 */
@XSD(name="addNoteRequest.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name = "addNoteRequest")
@XStreamAlias("addNoteRequest")
public class AddNoteRequest{

	
	@XStreamAlias("note")
	private NoteType noteType;

	@XmlElement(name="note")
	public NoteType getNoteType() {
		return noteType;
	}

	public void setNoteType(NoteType newNoteType) {
		this.noteType = newNoteType;
	}

}

