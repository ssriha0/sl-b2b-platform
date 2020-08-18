package com.newco.marketplace.leadoutboundnotification.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("verazipreply")	
public class ResponseLeadDetailsVerazip {
	
	@XStreamAlias("transactionid")	
	private String transactionId;
	
	@XStreamAlias("verazipcompletioncode")	
	private String verazipCompletionCode;
	
	@XStreamAlias("customernum")	
	private String customerNum;
	
	@XStreamAlias("customerfirstname")	
	private String customerFirstName;
	
	@XStreamAlias("customermiddlename")	
	private String customerMiddleName;
	
	@XStreamAlias("customerlastname")	
	private String customerLastName;
	
	@XStreamAlias("phonenum")	
	private String phoneNum;
	
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
	
	@XStreamAlias("zipext")	
	private String zipExt;
	
	@XStreamAlias("verazipreturnedrows")	
	private String veraZipReturnedRows;
	
	@XStreamImplicit(itemFieldName="veraziptable")
	private List<VerazipDetails> resOrder;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getVerazipCompletionCode() {
		return verazipCompletionCode;
	}

	public void setVerazipCompletionCode(String verazipCompletionCode) {
		this.verazipCompletionCode = verazipCompletionCode;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
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

	public String getZipExt() {
		return zipExt;
	}

	public void setZipExt(String zipExt) {
		this.zipExt = zipExt;
	}

	public String getVeraZipReturnedRows() {
		return veraZipReturnedRows;
	}

	public void setVeraZipReturnedRows(String veraZipReturnedRows) {
		this.veraZipReturnedRows = veraZipReturnedRows;
	}

	public List<VerazipDetails> getResOrder() {
		return resOrder;
	}

	public void setResOrder(List<VerazipDetails> resOrder) {
		this.resOrder = resOrder;
	}
	
}
