package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Data")
public class LMSPostData {

	@XStreamAlias("SRC")
	private String source;

	@XStreamAlias("Landing_Page")
	private String landingPage;

	@XStreamAlias("IP_Address")
	private String ipAddress;

	@XStreamAlias("First_Name")
	private String firstName;

	@XStreamAlias("Last_Name")
	private String lastName;

	@XStreamAlias("Address")
	private String address;

	@XStreamAlias("City")
	private String city;

	@XStreamAlias("State")
	private String state;

	@XStreamAlias("Zip")
	private String zip;

	@XStreamAlias("Primary_Phone")
	private String primaryPhone;

	@XStreamAlias("Email")
	private String email;

	@XStreamAlias("Detailed_Service_Description")
	private String additionalInfo;

	@XStreamAlias("Product_Or_Project")
	private String secondaryProjects;

	@XStreamAlias("Source_Name")
	private String sourceName;

	
	public LMSPostData(String source, String landingPage, String ipAddress,
			String firstName, String lastName, String address, String city,
			String state, String zip, String primaryPhone, String email,
			String additionalInfo, String secondaryProjects, String sourceName) {
		this.source = source;
		this.landingPage = landingPage;
		this.ipAddress = ipAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.primaryPhone = primaryPhone;
		this.email = email;
		this.additionalInfo = additionalInfo;
		this.secondaryProjects = secondaryProjects;
		this.sourceName = sourceName;
	}

	public LMSPostData() {

	}



	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLandingPage() {
		return landingPage;
	}

	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String string) {
		this.primaryPhone = string;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSecondaryProjects() {
		return secondaryProjects;
	}

	public void setSecondaryProjects(String secondaryProjects) {
		this.secondaryProjects = secondaryProjects;
	}

}
