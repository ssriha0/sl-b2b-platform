package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing location information.
 * @author Infosys
 *
 */
@XStreamAlias("serviceLocation")
public class Location {
		
	@XStreamAlias("locationType")
	private String locationType;	
	
	@XStreamAlias("locationName")
	private String locationName;	
	
	@XStreamAlias("address1")
	private String address1;	
	
	@XStreamAlias("address2 ")
	private String address2 ;
	
	@XStreamAlias("city")
	private String city;
	
	@XStreamAlias("state")
	private String state;
	
	@XStreamAlias("zip")
	private String zip;
	
	@XStreamAlias("note")
	private String note;

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
