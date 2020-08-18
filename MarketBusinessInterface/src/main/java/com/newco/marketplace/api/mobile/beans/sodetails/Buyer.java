package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing information of Buyer
 * @author Infosys
 *
 */

@XStreamAlias("buyer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Buyer {
		
	
	@XStreamAlias("businessName")
	private String businessName;
	
	@XStreamAlias("firstName")
	private String firstName;

	@XStreamAlias("lastName")
	private String lastName;

	@XStreamAlias("buyerUserId")
	private Integer buyerUserId;
	
	@XStreamAlias("resourceId")
	private Integer resourceId;

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

	@XStreamAlias("primaryphone")
	private String primaryphone;

	@XStreamAlias("alternatePhone")
	private String alternatePhone;

	@XStreamAlias("fax")
	private String fax;

	@XStreamAlias("email")
	private String email;
	
	@XStreamOmitField
	private Integer minTimeWindow;
 
	@XStreamOmitField
	private Integer maxTimeWindow;
 
	public Integer getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(Integer buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
	
	

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getPrimaryphone() {
		return primaryphone;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public void setPrimaryphone(String primaryphone) {
		this.primaryphone = primaryphone;
	}

	public String getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}

	public String getFax() {
		return fax;
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

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
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

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the minTimeWindow
	 */
	public Integer getMinTimeWindow() {
		return minTimeWindow;
	}

	/**
	 * @param minTimeWindow the minTimeWindow to set
	 */
	public void setMinTimeWindow(Integer minTimeWindow) {
		this.minTimeWindow = minTimeWindow;
	}

	/**
	 * @return the maxTimeWindow
	 */
	public Integer getMaxTimeWindow() {
		return maxTimeWindow;
	}

	/**
	 * @param maxTimeWindow the maxTimeWindow to set
	 */
	public void setMaxTimeWindow(Integer maxTimeWindow) {
		this.maxTimeWindow = maxTimeWindow;
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
	

}
