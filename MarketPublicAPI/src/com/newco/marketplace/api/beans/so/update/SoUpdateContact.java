package com.newco.marketplace.api.beans.so.update;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.so.Phone;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing service contact information for 
 * the SOUpdateService
 *
 *
 */
@XStreamAlias("contact")
public class SoUpdateContact {
	
	@OptionalParam
	@XStreamAlias("contactLocnType")
	private String contactLocnType;
	
	@OptionalParam
	@XStreamAlias("firstName")
	private String firstName;
	
	@OptionalParam
	@XStreamAlias("lastName")
	private String lastName;
	
	@OptionalParam
	@XStreamAlias("primaryPhoneNo")
	private Phone primaryPhoneNo;
	
	@OptionalParam
	@XStreamAlias("altPhoneNo")
	private Phone altPhoneNo;
	
	@OptionalParam
	@XStreamAlias("email")
	private String email;
	

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

	public Phone getPrimaryPhoneNo() {
		return primaryPhoneNo;
	}

	public void setPrimaryPhoneNo(Phone primaryPhoneNo) {
		this.primaryPhoneNo = primaryPhoneNo;
	}

	public Phone getAltPhoneNo() {
		return altPhoneNo;
	}

	public void setAltPhoneNo(Phone altPhoneNo) {
		this.altPhoneNo = altPhoneNo;
	}

	public String getContactLocnType() {
		return contactLocnType;
	}

	public void setContactLocnType(String contactLocnType) {
		this.contactLocnType = contactLocnType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
