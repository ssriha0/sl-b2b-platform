package com.newco.marketplace.api.mobile.beans;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the Mobile
 * TimeOnSite Request
 * 
 * @author Infosys
 * 
 */
@XSD(name = "mobileTimeOnSiteRequest.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "soTimeOnSiteRequest")
@XStreamAlias("soTimeOnSiteRequest")
public class MobileTimeOnSiteRequest {

	/*@XStreamAlias("providerId")
	private Integer providerId;*/

	@XStreamAlias("eventType")
	private String eventType;

	@XStreamAlias("latitude")
	private Double latitude;

	@XStreamAlias("longitude")
	private Double longitude;

	/**
	 * @return the providerId
	 */
	/*public Integer getProviderId() {
		return providerId;
	}*/

	/**
	 * @param providerId
	 *            the providerId to set
	 */
	/*public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}*/

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 *            the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
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
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
