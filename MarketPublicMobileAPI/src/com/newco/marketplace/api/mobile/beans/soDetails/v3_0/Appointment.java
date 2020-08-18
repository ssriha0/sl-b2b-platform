package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing Appointment information.
 * @author Infosys
 *
 */

@XStreamAlias("appointment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Appointment {

	@XStreamAlias("serviceStartDate")
	private String serviceStartDate;

	@XStreamAlias("serviceEndDate")
	private String serviceEndDate;	
	
    @XStreamAlias("serviceWindowStartTime")
    private String serviceWindowStartTime;
    
    @XStreamAlias("serviceWindowEndTime")
    private String serviceWindowEndTime;
	
	@XStreamAlias("timeZone")
	private String timeZone;	
    
	@XStreamAlias("scheduleStatus")
	private String scheduleStatus;
    
	@XStreamAlias("maxTimeWindow")
	private Integer maxTimeWindow;
    
	@XStreamAlias("minTimeWindow")
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
    

}