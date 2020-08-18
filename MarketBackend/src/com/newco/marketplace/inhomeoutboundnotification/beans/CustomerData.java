package com.newco.marketplace.inhomeoutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the In Home Out
 * bound Notification Service
 * 
 * @author Infosys
 */
@XStreamAlias("customer")
public class CustomerData {

	@XStreamAlias("custKey")
	private String custKey;
	
	@XStreamAlias("customerNameData")
	private CustomerNameData customerNameData;

	@XStreamAlias("customerNameData")
	private CustomerAddressData customerAddressData;

	@XStreamAlias("customerPhoneData")
	private CustomerPhoneData customerPhoneData;

	//req clarification : not in sample request
	@XStreamAlias("preferredPrimaryCntFl")
	private String preferredPrimaryCntFl;

	@XStreamAlias("emailAddress")
	private String emailAddress;

	public String getCustKey() {
		return custKey;
	}

	public void setCustKey(String custKey) {
		this.custKey = custKey;
	}

	public CustomerNameData getCustomerNameData() {
		return customerNameData;
	}

	public void setCustomerNameData(CustomerNameData customerNameData) {
		this.customerNameData = customerNameData;
	}

	public CustomerAddressData getCustomerAddressData() {
		return customerAddressData;
	}

	public void setCustomerAddressData(CustomerAddressData customerAddressData) {
		this.customerAddressData = customerAddressData;
	}

	public CustomerPhoneData getCustomerPhoneData() {
		return customerPhoneData;
	}

	public void setCustomerPhoneData(CustomerPhoneData customerPhoneData) {
		this.customerPhoneData = customerPhoneData;
	}

	public String getPreferredPrimaryCntFl() {
		return preferredPrimaryCntFl;
	}

	public void setPreferredPrimaryCntFl(String preferredPrimaryCntFl) {
		this.preferredPrimaryCntFl = preferredPrimaryCntFl;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	
}
