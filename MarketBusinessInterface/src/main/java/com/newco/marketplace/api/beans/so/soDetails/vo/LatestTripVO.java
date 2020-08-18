package com.newco.marketplace.api.beans.so.soDetails.vo;

public class LatestTripVO {
	
	private Integer tripNo;
	private String tripStatus;
	private String checkInTime;
	private String checkOutTime;

	
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
