package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Address")
public class Address implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 858702520029692739L;

	@XStreamAlias("StreetAddress1")
	private String streetAddress1;
	
	@XStreamAlias("StreetAddress2")
	private String streetAddress2;
	
	@XStreamAlias("City")
	private String city;
	
	@XStreamAlias("State")
	private String state;
	
	@XStreamAlias("ZipCode")
	private String zipCode;

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

	public String getStreetAddress1() {
		return streetAddress1;
	}

	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}

	public String getStreetAddress2() {
		return streetAddress2;
	}

	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
