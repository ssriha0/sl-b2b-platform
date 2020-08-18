package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing ServiceLocation information.
 * @author Infosys
 *
 */

@XStreamAlias("serviceLocation")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceLocation {
		
	@XStreamAlias("customerFirstName")
	private String customerFirstName;
	
	@XStreamAlias("customerLastName")
	private String customerLastName;
	
	@XStreamAlias("streetAddress1")
	private String streetAddress1;
	
	@XStreamAlias("streetAddress2")
	private String streetAddress2;
	
	@XStreamAlias("city")
	private String city;
	
	@XStreamAlias("state")
	private String state;
	
	@XStreamAlias("zip")
	private String zip;
	
	@XStreamAlias("primaryPhone")
	private String primaryPhone;
	
	@XStreamAlias("alternatePhone")
	private String alternatePhone;
	
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("serviceLocationNotes")
	private String serviceLocationNotes;
	
	@XStreamOmitField
	private String soContactId;


	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	/**
	 * @return the streetAddress1
	 */
	public String getStreetAddress1() {
		return streetAddress1;
	}

	/**
	 * @param streetAddress1 the streetAddress1 to set
	 */
	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}

	/**
	 * @return the streetAddress2
	 */
	public String getStreetAddress2() {
		return streetAddress2;
	}

	/**
	 * @param streetAddress2 the streetAddress2 to set
	 */
	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getServiceLocationNotes() {
		return serviceLocationNotes;
	}

	public void setServiceLocationNotes(String serviceLocationNotes) {
		this.serviceLocationNotes = serviceLocationNotes;
	}

	public String getSoContactId() {
		return soContactId;
	}

	public void setSoContactId(String soContactId) {
		this.soContactId = soContactId;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}


}
