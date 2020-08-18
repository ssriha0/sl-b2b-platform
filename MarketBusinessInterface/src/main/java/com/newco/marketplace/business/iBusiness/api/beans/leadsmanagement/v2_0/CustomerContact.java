package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CustomerContact")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerContact {

	@XStreamAlias("FirstName")
	@XmlElement(name="FirstName")
	private String firstName;
	
	@XStreamAlias("LastName")
	@XmlElement(name="LastName")
	private String lastName;
	
	@XStreamAlias("Contact")
	@XmlElement(name="Contact")
	private Contact contact;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}
