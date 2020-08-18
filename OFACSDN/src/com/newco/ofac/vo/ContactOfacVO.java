package com.newco.ofac.vo;

import java.util.Date;

public class ContactOfacVO {
	
	private Integer resourceID;
	private Integer contactID;
	private Date dob;
	private String taxID;
	
	public Integer getResourceID() {
		return resourceID;
	}
	public void setResourceID(Integer resourceID) {
		this.resourceID = resourceID;
	}
	public Integer getContactID() {
		return contactID;
	}
	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getTaxID() {
		return taxID;
	}
	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}



}
