package com.newco.marketplace.translator.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ZipGeocode entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zip_geocode", uniqueConstraints = {})
public class ZipGeocode extends AbstractZipGeocode implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public ZipGeocode() {
		// intentionally blank
	}

	/** minimal constructor */
	public ZipGeocode(String zip, String city, String stateCd) {
		super(zip, city, stateCd);
	}

	/** full constructor */
	public ZipGeocode(String zip, String city, String stateCd, Integer areaCd,
			String countyFips, String countyName, String timeZone,
			String daylightSavingsFlg, Double latitude, Double longitude,
			String zipCodeType) {
		super(zip, city, stateCd, areaCd, countyFips, countyName, timeZone,
				daylightSavingsFlg, latitude, longitude, zipCodeType);
	}

}
