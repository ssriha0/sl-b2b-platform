package com.servicelive.esb.dto;

import java.io.Serializable;

public class HSRSoCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	private String customerNumber;
	
	private String customerType;
	
	private String customerFirstName;
	
	private String customerLastName;
	
	private String customerPhone;
	
	private String customerAltPhone;
	
	private String customerPrefLang;
	
	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}

	/**
	 * @param customerNumber the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the customerFirstName
	 */
	public String getCustomerFirstName() {
		return customerFirstName;
	}

	/**
	 * @param customerFirstName the customerFirstName to set
	 */
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	/**
	 * @return the customerLastName
	 */
	public String getCustomerLastName() {
		return customerLastName;
	}

	/**
	 * @param customerLastName the customerLastName to set
	 */
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return the customerAltPhone
	 */
	public String getCustomerAltPhone() {
		return customerAltPhone;
	}

	/**
	 * @param customerAltPhone the customerAltPhone to set
	 */
	public void setCustomerAltPhone(String customerAltPhone) {
		this.customerAltPhone = customerAltPhone;
	}

	/**
	 * @return the customerPrefLang
	 */
	public String getCustomerPrefLang() {
		return customerPrefLang;
	}

	/**
	 * @param customerPrefLang the customerPrefLang to set
	 */
	public void setCustomerPrefLang(String customerPrefLang) {
		this.customerPrefLang = customerPrefLang;
	}
}
