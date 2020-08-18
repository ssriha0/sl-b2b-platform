package com.newco.marketplace.api.beans.so.soDetails.vo;


public class AppointmentVO {

	private String serviceStartDate;
	private String serviceEndDate;	
    private String serviceWindowStartTime;
    private String serviceWindowEndTime;
	private String timeZone;	 
	private String scheduleStatus; 
	private Integer maxTimeWindow; 
	private Integer minTimeWindow;
	
	public String getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(String serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	public String getServiceEndDate() {
		return serviceEndDate;
	}
	public void setServiceEndDate(String serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}
	public String getServiceWindowStartTime() {
		return serviceWindowStartTime;
	}
	public void setServiceWindowStartTime(String serviceWindowStartTime) {
		this.serviceWindowStartTime = serviceWindowStartTime;
	}
	public String getServiceWindowEndTime() {
		return serviceWindowEndTime;
	}
	public void setServiceWindowEndTime(String serviceWindowEndTime) {
		this.serviceWindowEndTime = serviceWindowEndTime;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	public Integer getMaxTimeWindow() {
		return maxTimeWindow;
	}
	public void setMaxTimeWindow(Integer maxTimeWindow) {
		this.maxTimeWindow = maxTimeWindow;
	}
	public Integer getMinTimeWindow() {
		return minTimeWindow;
	}
	public void setMinTimeWindow(Integer minTimeWindow) {
		this.minTimeWindow = minTimeWindow;
	}
	
	
}
