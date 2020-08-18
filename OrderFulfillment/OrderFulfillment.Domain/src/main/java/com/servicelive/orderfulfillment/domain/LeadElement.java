package com.servicelive.orderfulfillment.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement()
@XmlSeeAlso(value = {LeadHdr.class,LeadPostedFirm.class,LeadDocuments.class,LeadCancel.class,LeadContactInfo.class})
@MappedSuperclass()
public abstract class LeadElement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7184105455539071307L;

	// validate this
	public List<String> validate() {
		return Collections.emptyList();
	}
	
	// copy from another element
	public void assign(LeadElement le) {
		if (le.getTypeName().equals(getTypeName())) {
		}
	}
	
	// update lead
	public void update(LeadElement le) 	{
		if (le.getTypeName().equals("LEAD")) {
		}
	}

	public String getTypeName() {
		return this.getClass().getName();
	}

}
