package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;


public class ResourceLocationType extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3773685905776973331L;
	private Integer locnId = -1; 
	private Integer resourceId = -1;
	private Integer locnTypeId = -1;
	private String locnName = "";
	private String street1 = ""; 
	private String street2 = "";
	private String city = "";
	private String stateCd = ""; 
	private String zip = "";
	private String zip4 = ""; 
	private String country = "";
	private Integer radius = -1;
	private String hourlyRate="";
	
	/**
	 * @return the HourlyRate
	 */
	public String getHourlyRate() {
		return hourlyRate;
	}
	/**
	 * @param the HourlyRate to set
	 */
	public void setHourlyRate(String hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	/**
	 * @return the radius
	 */
	public Integer getRadius() {
		return radius;
	}
	/**
	 * @param radius the radius to set
	 */
	public void setRadius(Integer radius) {
		this.radius = radius;
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
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the locnId
	 */
	public Integer getLocnId() {
		return locnId;
	}
	/**
	 * @param locnId the locnId to set
	 */
	public void setLocnId(Integer locnId) {
		this.locnId = locnId;
	}
	/**
	 * @return the locnName
	 */
	public String getLocnName() {
		return locnName;
	}
	/**
	 * @param locnName the locnName to set
	 */
	public void setLocnName(String locnName) {
		this.locnName = locnName;
	}
	/**
	 * @return the locnTypeId
	 */
	public Integer getLocnTypeId() {
		return locnTypeId;
	}
	/**
	 * @param locnTypeId the locnTypeId to set
	 */
	public void setLocnTypeId(Integer locnTypeId) {
		this.locnTypeId = locnTypeId;
	}
	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the stateCd
	 */
	public String getStateCd() {
		return stateCd;
	}
	/**
	 * @param stateCd the stateCd to set
	 */
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	/**
	 * @return the street1
	 */
	public String getStreet1() {
		return street1;
	}
	/**
	 * @param street1 the street1 to set
	 */
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	/**
	 * @return the street2
	 */
	public String getStreet2() {
		return street2;
	}
	/**
	 * @param street2 the street2 to set
	 */
	public void setStreet2(String street2) {
		this.street2 = street2;
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
	/**
	 * @return the zip4
	 */
	public String getZip4() {
		return zip4;
	}
	/**
	 * @param zip4 the zip4 to set
	 */
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}




}