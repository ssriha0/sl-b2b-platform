package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
@XStreamAlias("address")
public class Address {
	@XStreamAlias("street")
	private String street;

	@XStreamAlias("city")
	private String city;
	
	@XStreamAlias("zip")
	private String zip;
	
	@XStreamAlias("state")
	private String state;
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


}
