package com.servicelive.orderfulfillment.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("customerAddressData")
public class CustomerAddressData {

	@XStreamAlias("addressLineOne")
	private String addressLineOne;

	@XStreamAlias("addressLineTwo")
	private String addressLineTwo;

	@XStreamAlias("aptNum")
	private String aptNum;

	@XStreamAlias("city")
	private String city;

	@XStreamAlias("state")
	private String state;

	@XStreamAlias("zipCode")
	private String zipCode;

	@XStreamAlias("zipCodeExtension")
	private String zipCodeExtension;

	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public String getAptNum() {
		return aptNum;
	}

	public void setAptNum(String aptNum) {
		this.aptNum = aptNum;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipCodeExtension() {
		return zipCodeExtension;
	}

	public void setZipCodeExtension(String zipCodeExtension) {
		this.zipCodeExtension = zipCodeExtension;
	}
	
	
}
