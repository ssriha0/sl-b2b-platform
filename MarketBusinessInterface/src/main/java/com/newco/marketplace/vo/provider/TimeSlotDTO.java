package com.newco.marketplace.vo.provider;

import java.util.Calendar;
import java.util.Date;

public class TimeSlotDTO {
	Date startTime;
	Date endTime;
	private boolean hourInd24 = false;
	// default = false
	private boolean matchedSoPrefDateTime = false;

	public TimeSlotDTO(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@SuppressWarnings("deprecation")
	public TimeSlotDTO(Date startDate, Date endDate, Calendar startTimeCal, Calendar endTimeCal) {
		startTimeCal.set(Calendar.HOUR_OF_DAY, startDate.getHours());
		startTimeCal.set(Calendar.MINUTE, startDate.getMinutes());
		startTimeCal.set(Calendar.SECOND, startDate.getSeconds());
		this.startTime = startTimeCal.getTime();

		endTimeCal.set(Calendar.HOUR_OF_DAY, endDate.getHours());
		endTimeCal.set(Calendar.MINUTE, endDate.getMinutes());
		endTimeCal.set(Calendar.SECOND, endDate.getSeconds());
		this.endTime = endTimeCal.getTime();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isHourInd24() {
		return hourInd24;
	}

	public void setHourInd24(boolean hourInd24) {
		this.hourInd24 = hourInd24;
	}

	public boolean isMatchedSoPrefDateTime() {
		return matchedSoPrefDateTime;
	}

	public void setMatchedSoPrefDateTime(boolean matchedSoPrefDateTime) {
		this.matchedSoPrefDateTime = matchedSoPrefDateTime;
	}
	
	@Override
	public String toString() {
		return ("TimeSlotDTO ["
				+ "startTime=" + startTime 
				+ ", endTime=" + endTime 
				+ ", hourInd24=" + hourInd24 
				+ ", matchedSoPrefDateTime=" + matchedSoPrefDateTime + "]");
	}
		
}
