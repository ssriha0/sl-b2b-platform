package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ServiceOrderCustomer implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 6518089121517888725L;

	@XStreamAlias("Number")
	private String number;
	
	@XStreamAlias("Type")
	private String type;
	
	@XStreamAlias("FirstName")
	private String firstName;
	
	@XStreamAlias("LastName")
	private String lastName;
	
	@XStreamAlias("Phone")
	private String phone;
	
	@XStreamAlias("AltPhone")
	private String altPhone;
	
	@XStreamAlias("PreferredLanguage")
	private String preferredLanguage;
	
	@XStreamAlias("CellPhoneNumber")
	private String cellPhoneNumber;
	
	@XStreamAlias("SupplementalCustomerInformation")
	private String supplementalCustomerInformation;
	
	@XStreamAlias("Address")
	private Address address;

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getAltPhone() {
		return altPhone;
	}
	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
	}
	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}
	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPreferredLanguage() {
		return preferredLanguage;
	}
	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}
	public String getSupplementalCustomerInformation() {
		return supplementalCustomerInformation;
	}
	public void setSupplementalCustomerInformation(
			String supplementalCustomerInformation) {
		this.supplementalCustomerInformation = supplementalCustomerInformation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
