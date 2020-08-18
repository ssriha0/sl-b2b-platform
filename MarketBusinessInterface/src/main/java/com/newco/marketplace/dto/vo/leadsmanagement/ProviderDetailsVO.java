package com.newco.marketplace.dto.vo.leadsmanagement;

public class ProviderDetailsVO {
	private String firmId;
	private String providerId;
	private String city;
	private String state;
	private double distanceFromZipInMiles;
	private String resFirstname;
	private String resLastName;
	
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
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
	public double getDistanceFromZipInMiles() {
		return distanceFromZipInMiles;
	}
	public void setDistanceFromZipInMiles(double distanceFromZipInMiles) {
		this.distanceFromZipInMiles = distanceFromZipInMiles;
	}
	public String getResFirstname() {
		return resFirstname;
	}
	public void setResFirstname(String resFirstname) {
		this.resFirstname = resFirstname;
	}
	public String getResLastName() {
		return resLastName;
	}
	public void setResLastName(String resLastName) {
		this.resLastName = resLastName;
	}
	
}