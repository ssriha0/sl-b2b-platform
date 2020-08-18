package com.newco.marketplace.dto.vo.serviceOfferings;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CodeDetails")
public class CodeDetails {

	@XStreamAlias("City")
	private String city;

	@XStreamAlias("County")
	private String county;

	@XStreamAlias("Distance")
	private double distance;

	@XStreamAlias("Latitude")
	private double latitude;

	@XStreamAlias("Longitude")
	private double longitude;

	@XStreamAlias("State")
	private String state;

	@XStreamAlias("Code")
	private String zipCode;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
}
