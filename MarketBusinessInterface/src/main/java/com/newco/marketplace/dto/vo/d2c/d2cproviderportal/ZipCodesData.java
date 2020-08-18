package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

public class ZipCodesData {
	
	private String vendorLocId;
	private String Code; 
	private String zipCode;
	private String City;
	private String State;
	private String Latitude;
	private String Longitude;
	private String County;
	private String Distance;
	private String distance_miles;
	private String coverageId;
	private boolean selected=false;
	
	
	public String getVendorLocId() {
		return vendorLocId;
	}
	public void setVendorLocId(String vendorLocId) {
		this.vendorLocId = vendorLocId;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getCounty() {
		return County;
	}
	public void setCounty(String county) {
		County = county;
	}
	public String getDistance() {
		return Distance;
	}
	public void setDistance(String distance) {
		Distance = distance;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getDistance_miles() {
		return distance_miles;
	}
	public void setDistance_miles(String distance_miles) {
		this.distance_miles = distance_miles;
	}
	public String getCoverageId() {
		return coverageId;
	}
	public void setCoverageId(String coverageId) {
		this.coverageId = coverageId;
	}
	
	
	
	
}
	