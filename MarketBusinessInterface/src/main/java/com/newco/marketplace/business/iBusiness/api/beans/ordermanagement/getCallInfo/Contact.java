package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("customerInfo")
public class Contact {
	
	
	
	@XStreamAlias("firstName")
	private String firstName;	
	
	@XStreamAlias("lastName")
	private String lastName;
	
	@XStreamAlias("primaryPhone")
	private String primaryPhone;
	
	@XStreamAlias("altPhone")
	private String altPhone;
	
	@XStreamAlias("location")
	private Location location;


	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
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

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public String getAltPhone() {
		return altPhone;
	}

	
}
