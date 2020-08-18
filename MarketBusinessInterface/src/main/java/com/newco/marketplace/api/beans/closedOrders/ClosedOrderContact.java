package com.newco.marketplace.api.beans.closedOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing contact information.
 * @author Infosys
 *
 */
@XStreamAlias("contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClosedOrderContact {
	
	@XStreamAlias("contactLocnType")
	private String contactLocnType;	
	
	@XStreamAlias("firstName")
	private String firstName;	
	
	@XStreamAlias("lastName")
	private String lastName;
	
	@XStreamAlias("primaryPhone")
	private ClosedOrderPhone primaryPhone;
	
	@XStreamAlias("altPhone")
	private ClosedOrderPhone altPhone;
	
	@XStreamAlias("email")
	private String email;

	public String getContactLocnType() {
		return contactLocnType;
	}

	public void setContactLocnType(String contactLocnType) {
		this.contactLocnType = contactLocnType;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPrimaryPhone(ClosedOrderPhone primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public void setAltPhone(ClosedOrderPhone altPhone) {
		this.altPhone = altPhone;
	}

	public ClosedOrderPhone getPrimaryPhone() {
		return primaryPhone;
	}

	public ClosedOrderPhone getAltPhone() {
		return altPhone;
	}

}
