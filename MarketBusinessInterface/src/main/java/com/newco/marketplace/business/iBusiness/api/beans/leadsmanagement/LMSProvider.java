package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("provider")
public class LMSProvider {

	@XStreamAlias("provider_id")
	private String lmsProviderId;
	
	@XStreamAlias("provider_login")
	private String firmId;
	
	@XStreamAlias("distance")
	private String distance;
	
	@XStreamAlias("company_name")
	private String companyName;
	
	@XStreamAlias("first_name")
	private String firstName;
	
	@XStreamAlias("last_name")
	private String lastName;
	
	@XStreamAlias("address")
	private String address;
	
	@XStreamAlias("city")
	private String city;
	
	@XStreamAlias("state")
	private String state;
	
	@XStreamAlias("zip")
	private String zip;
	
	@XStreamAlias("phone")
	private String phone;
	
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("website")
	private String website;
	
	@XStreamAlias("offer")
	private String offer;
	
	@XStreamAlias("hours_open")
	private String hours_open;
	
	@XStreamAlias("company_founded")
	private String company_founded;
	
	@XStreamAlias("logo")
	private String logo;
	
	@XStreamAlias("price")
	private String price;

	public String getLmsProviderId() {
		return lmsProviderId;
	}

	public void setLmsProviderId(String lmsProviderId) {
		this.lmsProviderId = lmsProviderId;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public String getHours_open() {
		return hours_open;
	}

	public void setHours_open(String hours_open) {
		this.hours_open = hours_open;
	}

	public String getCompany_founded() {
		return company_founded;
	}

	public void setCompany_founded(String company_founded) {
		this.company_founded = company_founded;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}
