package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing information for Provider 
 * @author Infosys
 *
 */

@XStreamAlias("provider")
@XmlAccessorType(XmlAccessType.FIELD)
public class Provider {
	
	@XStreamAlias("providerFirstName")
	private String providerFirstName;
	
	@XStreamAlias("providerLastName")
	private String providerLastName;

	@XStreamAlias("providerId")
	private Integer providerId;

	@XStreamAlias("firmId")
	private Integer firmId;

	@XStreamAlias("firmName")
	private String firmName;

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

	@XStreamAlias("providerPrimaryPhone")
	private String providerPrimaryPhone;

	@XStreamAlias("providerAltPhone")
	private String providerAltPhone;
	
	@XStreamAlias("firmPrimaryPhone")
	private String firmPrimaryPhone;

	@XStreamAlias("firmAltPhone")
	private String firmAltPhone;

	@XStreamAlias("primaryEmail")
	private String primaryEmail;

	@XStreamAlias("alternateEmail")
	private String alternateEmail;

	@XStreamAlias("smsNumber ")
	private String smsNumber ;

	public String getProviderFirstName() {
		return providerFirstName;
	}

	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}

	public String getProviderLastName() {
		return providerLastName;
	}

	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	/**
	 * @return the providerPrimaryPhone
	 */
	public String getProviderPrimaryPhone() {
		return providerPrimaryPhone;
	}

	/**
	 * @param providerPrimaryPhone the providerPrimaryPhone to set
	 */
	public void setProviderPrimaryPhone(String providerPrimaryPhone) {
		this.providerPrimaryPhone = providerPrimaryPhone;
	}

	/**
	 * @return the providerAltPhone
	 */
	public String getProviderAltPhone() {
		return providerAltPhone;
	}

	/**
	 * @param providerAltPhone the providerAltPhone to set
	 */
	public void setProviderAltPhone(String providerAltPhone) {
		this.providerAltPhone = providerAltPhone;
	}

	/**
	 * @return the firmPrimaryPhone
	 */
	public String getFirmPrimaryPhone() {
		return firmPrimaryPhone;
	}

	/**
	 * @param firmPrimaryPhone the firmPrimaryPhone to set
	 */
	public void setFirmPrimaryPhone(String firmPrimaryPhone) {
		this.firmPrimaryPhone = firmPrimaryPhone;
	}

	/**
	 * @return the firmAltPhone
	 */
	public String getFirmAltPhone() {
		return firmAltPhone;
	}

	/**
	 * @param firmAltPhone the firmAltPhone to set
	 */
	public void setFirmAltPhone(String firmAltPhone) {
		this.firmAltPhone = firmAltPhone;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}

	public String getStreetAddress1() {
		return streetAddress1;
	}

	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}

	public String getStreetAddress2() {
		return streetAddress2;
	}

	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
