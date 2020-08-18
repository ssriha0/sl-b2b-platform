package com.newco.marketplace.vo.ordermanagement.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class CustomerDetails {
	
	@XStreamAlias("firstName") 
	private String firstName;
	
	@XStreamAlias("lastName") 
	private String lastName;
	
	@XStreamAlias("primaryPhone") 
	private String primaryPhone;
	
	@XStreamAlias("alternatePhone") 
	private String alternatePhone;

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

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}

	
}
