/*
 *	Date        Project    	  Author       	 Version
 * -----------  --------- 	-----------  	---------
 * 12-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 *
 */
package com.newco.marketplace.api.beans.account.buyer;

import com.newco.marketplace.api.annotation.MaskedValue;
import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("buyerAccountDetails")
public class BuyerAccountDetails {
	@MaskedValue("default@default.com")
	@XStreamAlias("email")
	private String email;

	@XStreamAlias("buyerType")
	private String buyerType;

	@OptionalParam
	@XStreamAlias("firstName")
	private String firstName;

	@OptionalParam
	@XStreamAlias("lastName")
	private String lastName;

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
	@XStreamAlias("stateCode")
	private String stateCode;

	@OptionalParam
	@XStreamAlias("zip")
	private String zip;

	@OptionalParam
	@XStreamAlias("primaryPhone")
	private String primaryPhone;

	@XStreamAlias("alternatePhone")
	private String alternatePhone;
	
	@OptionalParam
	@XStreamAlias("sendSpecialOffer")
	private String sendSpecialOffer;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
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

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public String getSendSpecialOffer() {
		return sendSpecialOffer;
	}

	public void setSendSpecialOffer(String sendSpecialOffer) {
		this.sendSpecialOffer = sendSpecialOffer;
	}
}
