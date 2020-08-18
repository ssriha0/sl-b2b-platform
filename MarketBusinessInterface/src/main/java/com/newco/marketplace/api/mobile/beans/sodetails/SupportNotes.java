package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("supportNotes")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupportNotes {
	
	@XStreamImplicit(itemFieldName="supportNote")
	private List<SupportNote> supportNote;

	public List<SupportNote> getSupportNote() {
		return supportNote;
	}

	public void setSupportNote(List<SupportNote> supportNote) {
		this.supportNote = supportNote;
	}


	
}
