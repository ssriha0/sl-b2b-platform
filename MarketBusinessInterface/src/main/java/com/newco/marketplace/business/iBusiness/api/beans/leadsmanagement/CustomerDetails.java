package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XmlRootElement(name = "customerDetails")
@XmlAccessorType(XmlAccessType.FIELD)
@XStreamAlias("customerDetails")
public class CustomerDetails {

	@XStreamAlias("firstName") 
	private String firstName;
	
	@XStreamAlias("lastName") 
	private String lastName;
	
	@XStreamAlias("middleName") 
	private String middleName;
	
	@XStreamAlias("phone") 
	private String phone;
	
	@XStreamAlias("email") 
	private String email;
	
	@XStreamAlias("address")
	private Address address;

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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
