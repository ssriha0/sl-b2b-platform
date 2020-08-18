package com.newco.marketplace.api.beans.hi.account.create.provider;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("provider")
public class Provider {
	
	@XStreamAlias("nameDetails")
	private NameDetails nameDetails;
	
	@XStreamAlias("ssn")
	private String ssn;
	
	@XStreamAlias("marketPlaceInd")
	private String marketPlaceInd;

	@XStreamAlias("roles")
	private Roles roles;
	
	@XStreamAlias("jobTitle")
	private String jobTitle;

	@XStreamAlias("address")
	private Address address;

	@XStreamAlias("geographicalRange")
	private String geographicalRange;

	@XStreamAlias("preferredBillingRate")
	private String preferredBillingRate;

	@XStreamAlias("userName")
	private String userName;

	@XStreamAlias("backgroundCheck")
	private BackgroundCheck backgroundCheck;

	@XStreamAlias("availability")
	private Availability availability;

	@XStreamAlias("businessPhone")
	private String businessPhone;
	
	@XStreamAlias("mobileNo")
	private String mobileNo;
	
	@XStreamAlias("businessExtn")
	private String businessExtn;

	@XStreamAlias("primaryEmail")
	private String primaryEmail;
	
	@XStreamAlias("altEmail")
	private String altEmail;
	
	@XStreamAlias("smsAddress")
	private String smsAddress;
	
	@XStreamAlias("secondaryContact")
	private String secondaryContact;
	
	@XStreamAlias("languages")
	private Languages languages;

	@XStreamAlias("licenseAndCertifications")
	private LicenseAndCertifications licenseAndCertifications;
	
	@XStreamAlias("subContractorCrewId")
	private String subContractorCrewId;

	public NameDetails getNameDetails() {
		return nameDetails;
	}

	public void setNameDetails(NameDetails nameDetails) {
		this.nameDetails = nameDetails;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getGeographicalRange() {
		return geographicalRange;
	}

	public void setGeographicalRange(String geographicalRange) {
		this.geographicalRange = geographicalRange;
	}

	public String getPreferredBillingRate() {
		return preferredBillingRate;
	}

	public String getUserName() {
		return userName;
	}

	public void setPreferredBillingRate(String preferredBillingRate) {
		this.preferredBillingRate = preferredBillingRate;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BackgroundCheck getBackgroundCheck() {
		return backgroundCheck;
	}

	public void setBackgroundCheck(BackgroundCheck backgroundCheck) {
		this.backgroundCheck = backgroundCheck;
	}

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getBusinessExtn() {
		return businessExtn;
	}

	public void setBusinessExtn(String businessExtn) {
		this.businessExtn = businessExtn;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getSmsAddress() {
		return smsAddress;
	}

	public void setSmsAddress(String smsAddress) {
		this.smsAddress = smsAddress;
	}

	public String getSecondaryContact() {
		return secondaryContact;
	}

	public void setSecondaryContact(String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}

	public Languages getLanguages() {
		return languages;
	}

	public void setLanguages(Languages languages) {
		this.languages = languages;
	}

	public LicenseAndCertifications getLicenseAndCertifications() {
		return licenseAndCertifications;
	}

	public void setLicenseAndCertifications(
			LicenseAndCertifications licenseAndCertifications) {
		this.licenseAndCertifications = licenseAndCertifications;
	}

	public String getSubContractorCrewId() {
		return subContractorCrewId;
	}

	public void setSubContractorCrewId(String subContractorCrewId) {
		this.subContractorCrewId = subContractorCrewId;
	}

	public String getMarketPlaceInd() {
		return marketPlaceInd;
	}

	public void setMarketPlaceInd(String marketPlaceInd) {
		this.marketPlaceInd = marketPlaceInd;
	}

}
