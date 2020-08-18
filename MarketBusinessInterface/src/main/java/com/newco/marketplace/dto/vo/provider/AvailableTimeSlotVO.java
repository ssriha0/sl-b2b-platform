package com.newco.marketplace.dto.vo.provider;



/**
 * This class would act as the VO class for search Firms API for standard service offerings
 *
 */
public class AvailableTimeSlotVO {
	
	private String day;
	private String timeWindowString;
	private Integer timeWindow;
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTimeWindowString() {
		return timeWindowString;
	}
	public void setTimeWindowString(String timeWindowString) {
		this.timeWindowString = timeWindowString;
	}
	public Integer getTimeWindow() {
		return timeWindow;
	}
	public void setTimeWindow(Integer timeWindow) {
		this.timeWindow = timeWindow;
	}
	
	
}
