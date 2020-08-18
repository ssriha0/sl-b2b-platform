package com.newco.marketplace.api.beans.so.timeonsite;

import java.sql.Timestamp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the SOProviderTimeOnSiteService
 * @author seshu
 *
 */
@XStreamAlias("soTimeOnSiteRequest")
public class SOTimeOnSiteRequest {
	@XStreamAlias("timeOnSiteId")
	private Integer timeOnSiteId;
	
	@XStreamAlias("arrivalDateTime")
	private String arrivalDateTime;
	
	@XStreamAlias("departureDateTime")
	private String departureDateTime;
	
	private Timestamp arrivalDate;
	private Timestamp departureDate;
	private String arrivalTime;
	private String departureTime;
	
	public SOTimeOnSiteRequest(){
		
	}
	public Integer getTimeOnSiteId() {
		return timeOnSiteId;
	}

	public void setScheduleType(Integer timeOnSiteId) {
		this.timeOnSiteId = timeOnSiteId;
	}

	public String getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(String arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	public String getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(String departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public Timestamp getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Timestamp arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Timestamp getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Timestamp departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
}
