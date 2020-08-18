package com.servicelive.esb.integration.domain;

public class Contact {
	private Long contactId;
	private Long serviceOrderId;
	private String firstName;
	private String lastName;
	private String businessName;
	private String email;
	
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setServiceOrderId(Long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public Long getServiceOrderId() {
		return serviceOrderId;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public Long getContactId() {
		return contactId;
	}
}
