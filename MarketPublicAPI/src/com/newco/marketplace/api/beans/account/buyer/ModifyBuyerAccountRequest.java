/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	pgangra   SHC				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.account.buyer;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class serves as POJO object used for converting request xml request, 
 * map data to backend VO Object. 
 *
 * @author priti
 *
 */
@XStreamAlias("modifyBuyerAccountRequest")
public class ModifyBuyerAccountRequest {
	
	@XStreamAlias("email")
	private String email;
	
	private String userName;
	
	private Integer buyerType;
	
	@XStreamAlias("firstName")
	private String firstName;
	
	@XStreamAlias("lastName")
	private String lastName;

	@XStreamAlias("primaryPhone")
	private String primaryPhone;
	
	@XStreamAlias("alternatePhone")
	private String alternatePhone;
	
	private Integer smsNumber;
	private Boolean smsIndicator;
	
	private Integer isHomeAddress=0;
	
	private String addressLabel;
	
	@XStreamAlias("address1")
	private String address1;
	
	@XStreamAlias("address2")
	private String address2;
	
	private String address3;
	
	@XStreamAlias("city")
	private String city;
	
	@XStreamAlias("stateCode")
	private String stateCode;
	
	@XStreamAlias("zip")
	private String zip;
	
	@XStreamAlias("einNo")
	private String einNo;
	
	@XStreamAlias("ssnInd")
	private String ssnInd;
	
	@XStreamAlias("dateOfBirth")
	private String dateOfBirth;
	
	@XStreamAlias("altIdType")
	private String altIdType;
	
	@XStreamAlias("altIdCountry")
	private String altIdCountry;
	
	private ArrayList<String> securityQuestions;
	private String securityAnswer;
	
	private int sendSpecialOffer=0;
	
	private int buyerResourceId;
	
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(Integer buyerType) {
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

	public Integer getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(Integer smsNumber) {
		this.smsNumber = smsNumber;
	}

	public Boolean getSmsIndicator() {
		return smsIndicator;
	}

	public void setSmsIndicator(Boolean smsIndicator) {
		this.smsIndicator = smsIndicator;
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

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
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

	public ArrayList<String> getSecurityQuestions() {
		return securityQuestions;
	}

	public void setSecurityQuestions(ArrayList<String> securityQuestions) {
		this.securityQuestions = securityQuestions;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	
	public int getSendSpecialOffer() {
		return sendSpecialOffer;
	}

	public void setSendSpecialOffer(int sendSpecialOffer) {
		this.sendSpecialOffer = sendSpecialOffer;
	}

	public Integer getIsHomeAddress() {
		return isHomeAddress;
	}

	public void setIsHomeAddress(Integer isHomeAddress) {
		this.isHomeAddress = isHomeAddress;
	}

	public String getAddressLabel() {
		return addressLabel;
	}

	public void setAddressLabel(String addressLabel) {
		this.addressLabel = addressLabel;
	}
	
	
	public int getBuyerResourceId() {
		return buyerResourceId;
	}

	public void setBuyerResourceId(int buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getEinNo() {
		return einNo;
	}

	public void setEinNo(String einNo) {
		this.einNo = einNo;
	}

	public String getSsnInd() {
		return ssnInd;
	}

	public void setSsnInd(String ssnInd) {
		this.ssnInd = ssnInd;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAltIdType() {
		return altIdType;
	}

	public void setAltIdType(String altIdType) {
		this.altIdType = altIdType;
	}

	public String getAltIdCountry() {
		return altIdCountry;
	}

	public void setAltIdCountry(String altIdCountry) {
		this.altIdCountry = altIdCountry;
	}
}
