package com.newco.marketplace.api.provider.beans.authenticateUser.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("location")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationDetail {
	
	@XStreamAlias("locnType")
	private String locnType;

	@XStreamAlias("streetAddress1")
	private String streetAddress1;
	
	@XStreamAlias("streetAddress2")
	private String streetAddress2;
	
	@XStreamAlias("aptNo")
	private String aptNo;

	@XStreamAlias("city")
	private String city;
	
	@XStreamAlias("state")
	private String state;

	@XStreamAlias("zip")
	private String zip;
	
	@XStreamAlias("country")
	private String country;
	

	public String getLocnType() {
		return locnType;
	}
	public void setLocnType(String locnType) {
		this.locnType = locnType;
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
	public String getAptNo() {
		return aptNo;
	}
	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	

}
