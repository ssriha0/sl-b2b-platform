package com.newco.marketplace.api.provider.beans.v3_0;

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
@XSD(name = "providerTimeOnSiteRequest.xsd", path = "/resources/schemas/provider/v3_0/")
@XmlRootElement(name = "soTimeOnSiteRequest")
@XStreamAlias("soTimeOnSiteRequest")
public class ProviderTimeOnSiteRequest {

	/*@XStreamAlias("providerId")
	private Integer providerId;*/

	@XStreamAlias("eventType")
	private String eventType;

	@XStreamAlias("latitude")
	private Double latitude;

	@XStreamAlias("longitude")
	private Double longitude;
	
	@XStreamAlias("currentTripNo")
	private int currentTripNo;
	
	@XStreamAlias("tripStatus")
	private String tripStatus;
	
	@XStreamAlias("reasonCode")
	private String reasonCode;
	
	@XStreamAlias("checkInTime")
	private String checkInTime;
	
	@XStreamAlias("checkOutTime")
	private String checkOutTime;
	
	@XStreamAlias("tripSource")
	private String tripSource;

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

	/**
	 * @return currentTripNo
	 * trip number for departure
	 */
	public int getCurrentTripNo() {
		return currentTripNo;
	}
	
	/**
	 * @param currentTripNo
	 * trip number for departure
	 */
	public void setCurrentTripNo(int currentTripNo) {
		this.currentTripNo = currentTripNo;
	}

	/**
	 * @return reasonCode
	 * reason code while departure
	 */
	public String getReasonCode() {
		return reasonCode;
	}
	
	/**
	 * @param reasonCode
	 * reason code while departure
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getTripSource() {
		return tripSource;
	}

	public void setTripSource(String tripSource) {
		this.tripSource = tripSource;
	}	
	
}
