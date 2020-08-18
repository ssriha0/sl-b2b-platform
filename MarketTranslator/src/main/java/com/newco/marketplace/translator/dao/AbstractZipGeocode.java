package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractZipGeocode entity provides the base persistence definition of the
 * ZipGeocode entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractZipGeocode implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7728903415370828045L;
	private String zip;
	private String city;
	private String stateCd;
	private Integer areaCd;
	private String countyFips;
	private String countyName;
	private String timeZone;
	private String daylightSavingsFlg;
	private Double latitude;
	private Double longitude;
	private String zipCodeType;

	// Constructors

	/** default constructor */
	public AbstractZipGeocode() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractZipGeocode(String zip, String city, String stateCd) {
		this.zip = zip;
		this.city = city;
		this.stateCd = stateCd;
	}

	/** full constructor */
	public AbstractZipGeocode(String zip, String city, String stateCd,
			Integer areaCd, String countyFips, String countyName,
			String timeZone, String daylightSavingsFlg, Double latitude,
			Double longitude, String zipCodeType) {
		this.zip = zip;
		this.city = city;
		this.stateCd = stateCd;
		this.areaCd = areaCd;
		this.countyFips = countyFips;
		this.countyName = countyName;
		this.timeZone = timeZone;
		this.daylightSavingsFlg = daylightSavingsFlg;
		this.latitude = latitude;
		this.longitude = longitude;
		this.zipCodeType = zipCodeType;
	}

	// Property accessors
	@Id
	@Column(name = "zip", unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "city", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state_cd", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
	public String getStateCd() {
		return this.stateCd;
	}

	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	@Column(name = "area_cd", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getAreaCd() {
		return this.areaCd;
	}

	public void setAreaCd(Integer areaCd) {
		this.areaCd = areaCd;
	}

	@Column(name = "county_fips", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getCountyFips() {
		return this.countyFips;
	}

	public void setCountyFips(String countyFips) {
		this.countyFips = countyFips;
	}

	@Column(name = "county_name", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCountyName() {
		return this.countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	@Column(name = "time_zone", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public String getTimeZone() {
		return this.timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	@Column(name = "daylight_savings_flg", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public String getDaylightSavingsFlg() {
		return this.daylightSavingsFlg;
	}

	public void setDaylightSavingsFlg(String daylightSavingsFlg) {
		this.daylightSavingsFlg = daylightSavingsFlg;
	}

	@Column(name = "latitude", unique = false, nullable = true, insertable = true, updatable = true, precision = 22, scale = 0)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude", unique = false, nullable = true, insertable = true, updatable = true, precision = 22, scale = 0)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "zip_code_type", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public String getZipCodeType() {
		return this.zipCodeType;
	}

	public void setZipCodeType(String zipCodeType) {
		this.zipCodeType = zipCodeType;
	}

}