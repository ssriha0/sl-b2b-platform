package com.newco.marketplace.leadoutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("createsoreply")		
public class ResponseLeadDetails {
	
	@XStreamAlias("transactionid")	
	private String transactionId;
	
	@XStreamAlias("warning")	
	private Warning warning;
	
	@XStreamAlias("customernum")	
	private String customerNum;
	
	@XStreamAlias("serviceordernum")	
	private String serviceOrderNum;
	
	@XStreamAlias("serviceunitnum")	
	private String serviceUnitNum;
	
	@XStreamAlias("customerfirstname")	
	private String customerFirstName;
	
	@XStreamAlias("customermiddlename")	
	private String customerMiddleName;
	
	@XStreamAlias("customerlastname")	
	private String customerLastName;
	
	@XStreamAlias("address1")	
	private String address1;
	
	@XStreamAlias("address2")	
	private String address2;
	
	@XStreamAlias("aptnum")	
	private String aptNum;
	
	@XStreamAlias("city")	
	private String city;
	
	@XStreamAlias("state")	
	private String state;
	
	@XStreamAlias("zip")	
	private String zip;
	
	@XStreamAlias("phonenum")	
	private String phoneNum;
	
	@XStreamAlias("alternatephonenum")	
	private String alternatePhoneNum;
	
	@XStreamAlias("coveragecode")	
	private String coverageCode;
	
	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Warning getWarning() {
		return warning;
	}

	public void setWarning(Warning warning) {
		this.warning = warning;
	}

	public String getServiceOrderNum() {
		return serviceOrderNum;
	}

	public void setServiceOrderNum(String serviceOrderNum) {
		this.serviceOrderNum = serviceOrderNum;
	}

	public String getServiceUnitNum() {
		return serviceUnitNum;
	}

	public void setServiceUnitNum(String serviceUnitNum) {
		this.serviceUnitNum = serviceUnitNum;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerMiddleName() {
		return customerMiddleName;
	}

	public void setCustomerMiddleName(String customerMiddleName) {
		this.customerMiddleName = customerMiddleName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAlternatePhoneNum() {
		return alternatePhoneNum;
	}

	public void setAlternatePhoneNum(String alternatePhoneNum) {
		this.alternatePhoneNum = alternatePhoneNum;
	}

	public String getCoverageCode() {
		return coverageCode;
	}

	public void setCoverageCode(String coverageCode) {
		this.coverageCode = coverageCode;
	}
	
}
