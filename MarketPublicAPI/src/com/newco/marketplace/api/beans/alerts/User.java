package com.newco.marketplace.api.beans.alerts;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the SendAlertService
 * @author Infosys
 *
 */
@XStreamAlias("user")
public class User {
	
	@XStreamAlias("birthday-on")
	private String birthdayOn;
	
	@XStreamAlias("carrier-id")
	private String carrierId;
	
	@XStreamAlias("carrier-updated-at")
	private String carrierUpdated;
	
	@XStreamAlias("country-calling-code")
	private String code;
		
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("first-name")
	private String firstName;
	
	@XStreamAlias("gender")
	private String gender;
	
	@XStreamAlias("last-name")
	private String lastName;
	
	@XStreamAlias("mobile-phone")
	private String mobilePhone;
	
	@XStreamAlias("phone")
	private String phone;
	
	@XStreamAlias("postal-code")
	private String postalCode;
	
	@XStreamAlias("timezone-id")
	private String timezoneId;
	
	
	public String getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTimezoneId() {
		return timezoneId;
	}

	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}

	public String getBirthdayOn() {
		return birthdayOn;
	}

	public void setBirthdayOn(String birthdayOn) {
		this.birthdayOn = birthdayOn;
	}

	public String getCarrierUpdated() {
		return carrierUpdated;
	}

	public void setCarrierUpdated(String carrierUpdated) {
		this.carrierUpdated = carrierUpdated;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	
}