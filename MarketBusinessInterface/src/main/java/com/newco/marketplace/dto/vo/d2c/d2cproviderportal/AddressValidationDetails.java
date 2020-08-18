package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

public class AddressValidationDetails {
	
	private String street_name;
	private String street_suffix;
	private String city_name;
	private String state_abbreviation;
	private String zipcode;
	private String plus4_code;
	
	public String getStreet_name() {
		return street_name;
	}
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	public String getStreet_suffix() {
		return street_suffix;
	}
	public void setStreet_suffix(String street_suffix) {
		this.street_suffix = street_suffix;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getState_abbreviation() {
		return state_abbreviation;
	}
	public void setState_abbreviation(String state_abbreviation) {
		this.state_abbreviation = state_abbreviation;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPlus4_code() {
		return plus4_code;
	}
	public void setPlus4_code(String plus4_code) {
		this.plus4_code = plus4_code;
	}
}
