package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PreCallHistory {
	
	@XStreamAlias("reason")
	private String reason;
	
	@XStreamAlias("day")
	private String day;
	
	@XStreamAlias("date")
	private String date;
	
	@XStreamAlias("scheduleStatus")
	private String scheduleStatus;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

}
