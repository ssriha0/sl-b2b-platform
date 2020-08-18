package com.newco.marketplace.dto.vo.serviceorder;

import com.newco.marketplace.webservices.base.CommonVO;



public class ProviderDetail extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677645486027956615L;
	private Integer providerId;
	private Integer contactId;
	private String vendorName;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String email;
	private String faxNo;
	private String street1;
	private String street2;
	private String city;
	private String stateCd;
	private String zip;
	private String zip4;
	private String country;
	private String emailPreference;
	private String smsPreference;
	private String mobileNo;
	private String altEmail;
	private String marketId;
	private String marketName;
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getStateCd() {
		return stateCd;
	}

	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZip4() {
		return zip4;
	}

	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getEmailPreference() {
		if ( null!=emailPreference && emailPreference.equals("1") )
		return "Y";
		else return "N";
	}
	

	public void setEmailPreference(String emailPreference) {
		this.emailPreference = emailPreference;
	}

	public String getSmsPreference() {
		if ("1".equals(smsPreference))
			return "Y";
		else return "N";	}

	public void setSmsPreference(String smsPreference) {
		this.smsPreference = smsPreference;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}	

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}	
}
