package com.newco.marketplace.api.beans.so.update;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing service location information for 
 * the SOUpdateService
 * 
 *
 */
@XStreamAlias("location")
public class SoUpdateLocation {
		
	@OptionalParam
	@XStreamAlias("locationType")
	private String locationType;
	
	@OptionalParam
	@XStreamAlias("locationName")
	private String locationName;
	
	@OptionalParam
	@XStreamAlias("address1")
	private String address1;
	
	@OptionalParam
	@XStreamAlias("address2")
	private String address2;
	
	@OptionalParam
	@XStreamAlias("city")
	private String city;
	
	@OptionalParam
	@XStreamAlias("note")
	private String note;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
