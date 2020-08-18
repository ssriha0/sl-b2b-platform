package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XmlRootElement(name = "Notes")
@XStreamAlias("Notes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Notes {

	@XmlElement(name="Note")
	@XStreamImplicit(itemFieldName = "Note")
	private List<Note> noteList;
	
	public List<Note> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}


}
