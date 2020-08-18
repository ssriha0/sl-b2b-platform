package com.servicelive.esb.dto;

import java.io.Serializable;

public class HSRRepairLocation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Address address;
	
	private String businessAddressInd;
	
	private String contactName;
	
	private String serviceLocationCode;
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the businessAddressInd
	 */
	public String getBusinessAddressInd() {
		return businessAddressInd;
	}

	/**
	 * @param businessAddressInd the businessAddressInd to set
	 */
	public void setBusinessAddressInd(String businessAddressInd) {
		this.businessAddressInd = businessAddressInd;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the serviceLocationCode
	 */
	public String getServiceLocationCode() {
		return serviceLocationCode;
	}

	/**
	 * @param serviceLocationCode the serviceLocationCode to set
	 */
	public void setServiceLocationCode(String serviceLocationCode) {
		this.serviceLocationCode = serviceLocationCode;
	}

	
}
