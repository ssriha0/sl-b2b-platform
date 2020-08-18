package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class Contact {

	@XStreamAlias("Address")
	@XmlElement(name="Address")
	private String address;

	@XStreamAlias("City")
	@XmlElement(name="City")
	private String city;

	@XStreamAlias("State")
	@XmlElement(name="State")
	private String state;
	
	@XStreamAlias("CustomerZipCode")
	@XmlElement(name="CustomerZipCode")
	private String customerZipCode;
    
	@XStreamAlias("Phone")
	@XmlElement(name="Phone")
	private String phone;

	@XStreamAlias("Email")
	@XmlElement(name="Email")
	private String email;
	

	public String getCustomerZipCode() {
		return customerZipCode;
	}

	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

}
