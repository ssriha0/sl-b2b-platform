package com.newco.marketplace.buyeroutboundnotification.vo;

public class CustomerInformationVO {
	private String customerAddress1;
	private String customerAddress2;
	private String customerCity;
	private String customerState;
	private String customerZipCode;
	private String customerZipSuffix;
	private String customerPrimaryPhoneNumber;
    private String customerAlternatePhonenumber;
    private String apt;
    
	public String getCustomerAlternatePhonenumber() {
		return customerAlternatePhonenumber;
	}

	public void setCustomerAlternatePhonenumber(String customerAlternatePhonenumber) {
		this.customerAlternatePhonenumber = customerAlternatePhonenumber;
	}

	

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerState() {
		return customerState;
	}

	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}

	public String getCustomerZipCode() {
		return customerZipCode;
	}

	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}

	public String getCustomerZipSuffix() {
		return customerZipSuffix;
	}

	public void setCustomerZipSuffix(String customerZipSuffix) {
		this.customerZipSuffix = customerZipSuffix;
	}

	public String getCustomerPrimaryPhoneNumber() {
		return customerPrimaryPhoneNumber;
	}

	public void setCustomerPrimaryPhoneNumber(String customerPrimaryPhoneNumber) {
		this.customerPrimaryPhoneNumber = customerPrimaryPhoneNumber;
	}

	public String getCustomerAddress1() {
		return customerAddress1;
	}

	public void setCustomerAddress1(String customerAddress1) {
		this.customerAddress1 = customerAddress1;
	}

	public String getCustomerAddress2() {
		return customerAddress2;
	}

	public void setCustomerAddress2(String customerAddress2) {
		this.customerAddress2 = customerAddress2;
	}

	public String getApt() {
		return apt;
	}

	public void setApt(String apt) {
		this.apt = apt;
	}
}
