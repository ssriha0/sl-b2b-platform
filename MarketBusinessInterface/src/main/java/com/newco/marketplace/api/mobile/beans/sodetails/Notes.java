package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;



import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("notes")
public class Notes {
	
	@XStreamImplicit(itemFieldName="note")
	private List<Note> note;

	public List<Note> getNote() {
		return note;
	}

	public void setNote(List<Note> note) {
		this.note = note;
	}

	
}
