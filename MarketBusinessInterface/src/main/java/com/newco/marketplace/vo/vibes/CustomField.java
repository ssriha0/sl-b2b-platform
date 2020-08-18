package com.newco.marketplace.vo.vibes;

import com.thoughtworks.xstream.annotations.XStreamAlias;

//R16_1: SL-18979: New Request class to store request details for Add Participant API for Vibes

@XStreamAlias("custom_field")
public class CustomField {

	@XStreamAlias("first_name")
	private String first_name;
	
	@XStreamAlias("last_name")
	private String last_name;

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
	}


	
	
}
