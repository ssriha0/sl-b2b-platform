package com.servicelive.domain.lookup;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ZipGeocode entity.
 * 
 */
@Entity
@Table(name = "zip_geocode", uniqueConstraints = {})
public class LookupZipGeocode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 20090823L;

	/** default constructor */
	public LookupZipGeocode() {
		super();
	}

	@Id
	@Column(name = "zip", unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	private String zip;

	@Column(name = "city", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String city;

	@Column(name = "state_cd", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
	private String stateCd;

	@Column(name = "area_cd", unique = false, nullable = true, insertable = true, updatable = true)
	private Integer areaCd;

	@Column(name = "county_fips", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String countyFips;

	@Column(name = "county_name", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	private String countyName;

	@Column(name = "time_zone", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	private String timeZone;

	@Column(name = "daylight_savings_flg", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private String daylightSavingsFlg;

	@Column(name = "latitude", unique = false, nullable = true, insertable = true, updatable = true, precision = 22, scale = 0)
	private Double latitude;

	@Column(name = "longitude", unique = false, nullable = true, insertable = true, updatable = true, precision = 22, scale = 0)
	private Double longitude;

	@Column(name = "zip_code_type", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private String zipCodeType;

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
	 * @return the areaCd
	 */
	public Integer getAreaCd() {
		return areaCd;
	}

	/**
	 * @param areaCd the areaCd to set
	 */
	public void setAreaCd(Integer areaCd) {
		this.areaCd = areaCd;
	}

	/**
	 * @return the countyFips
	 */
	public String getCountyFips() {
		return countyFips;
	}

	/**
	 * @param countyFips the countyFips to set
	 */
	public void setCountyFips(String countyFips) {
		this.countyFips = countyFips;
	}

	/**
	 * @return the countyName
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 * @param countyName the countyName to set
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the daylightSavingsFlg
	 */
	public String getDaylightSavingsFlg() {
		return daylightSavingsFlg;
	}

	/**
	 * @param daylightSavingsFlg the daylightSavingsFlg to set
	 */
	public void setDaylightSavingsFlg(String daylightSavingsFlg) {
		this.daylightSavingsFlg = daylightSavingsFlg;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the zipCodeType
	 */
	public String getZipCodeType() {
		return zipCodeType;
	}

	/**
	 * @param zipCodeType the zipCodeType to set
	 */
	public void setZipCodeType(String zipCodeType) {
		this.zipCodeType = zipCodeType;
	}

}
