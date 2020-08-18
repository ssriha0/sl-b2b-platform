package com.newco.marketplace.api.mobile.beans.sodetails.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("latestTrip")
@XmlAccessorType(XmlAccessType.FIELD)
public class LatestTripDetails {

	@XStreamAlias("tripNo")
	private Integer tripNo;
	
	@XStreamAlias("checkInTime")
	private String checkInTime;
	
	@XStreamAlias("checkOutTime")
	private String checkOutTime;
	
	@XStreamAlias("tripStatus")
	private String tripStatus;

	public Integer getTripNo() {
		return tripNo;
	}

	public void setTripNo(Integer tripNo) {
		this.tripNo = tripNo;
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

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}
	
	

}
