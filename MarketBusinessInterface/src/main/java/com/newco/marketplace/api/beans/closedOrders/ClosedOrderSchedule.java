package com.newco.marketplace.api.beans.closedOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
* This is a generic bean class for storing schedule information.
* @author Infosys
*
*/
@XStreamAlias("schedule")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClosedOrderSchedule {
	
	@XStreamAlias("scheduleType")
	private String scheduleType;
	
	@XStreamAlias("serviceStartDate")
	private String serviceStartDate;
	
	@XStreamAlias("serviceEndDate")
	private String serviceEndDate;
	
	@XStreamAlias("serviceWindowStartTime")
	private String serviceWindowStartTime;
	
	@XStreamAlias("serviceWindowEndTime")
	private String serviceWindowEndTime;
	
	@OptionalParam
	@XStreamAlias("serviceLocationTimezone") 
	private String serviceLocationTimezone;

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

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

	public String getServiceLocationTimezone() {
		return serviceLocationTimezone;
	}

	public void setServiceLocationTimezone(String serviceLocationTimezone) {
		this.serviceLocationTimezone = serviceLocationTimezone;
	}
		

}
