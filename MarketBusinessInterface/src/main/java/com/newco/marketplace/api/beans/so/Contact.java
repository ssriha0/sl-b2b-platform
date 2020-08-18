package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing contact information.
 * @author Infosys
 *
 */
@XStreamAlias("contact")
public class Contact {
	
	@XStreamAlias("contactLocnType")
	private String contactLocnType;	
	
	@XStreamAlias("firstName")
	private String firstName;	
	
	@XStreamAlias("lastName")
	private String lastName;
	
	@XStreamAlias("primaryPhone")
	private Phone primaryPhone;
	
	@XStreamAlias("altPhone")
	private Phone altPhone;
	
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

	public void setPrimaryPhone(Phone primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public void setAltPhone(Phone altPhone) {
		this.altPhone = altPhone;
	}

	public Phone getPrimaryPhone() {
		return primaryPhone;
	}

	public Phone getAltPhone() {
		return altPhone;
	}

}
