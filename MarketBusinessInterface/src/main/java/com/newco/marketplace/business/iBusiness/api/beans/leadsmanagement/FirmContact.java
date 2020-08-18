package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Contact")
public class FirmContact {

	@XStreamAlias("Address")
	private String address;

	@XStreamAlias("City")
	private String city;

	@XStreamAlias("State")
	private String state;
    
	@OptionalParam
	@XStreamAlias("CustomerZipCode")
	private String customerZipCode;

	@OptionalParam
	@XStreamAlias("ZipCode")
	private String ZipCode;

	@XStreamAlias("Phone")
	private String phone;

	@XStreamAlias("Email")
	private String email;

	public String getAddress() {
		return address;
	}

	public String getZipCode() {
		return ZipCode;
	}

	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
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

	public String getCustomerZipCode() {
		return customerZipCode;
	}

	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}
}
