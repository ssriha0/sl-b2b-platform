package com.newco.marketplace.api.mobile.beans.submitReschedule.v3;

import java.sql.Timestamp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing reschedule information for 
 * the SORescheduleService
 * @author Infosys
 *
 */
@XStreamAlias("soRescheduleInfo")
public class SORescheduleInfo {

	@XStreamAlias("scheduleType")
	private String scheduleType;
	
	@XStreamAlias("serviceDateTime1")
	private String serviceDateTime1;
	
	@XStreamAlias("serviceDateTime2")
	private String serviceDateTime2;
	
	@XStreamAlias("serviceTimeStart")
	private String serviceTimeStart;
	
	@XStreamAlias("serviceTimeEnd")
	private String serviceTimeEnd;
	
	@XStreamAlias("reasonCode")
	private Integer reasonCode;
	
	@XStreamAlias("comments")
	private String comments;

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getServiceDateTime1() {
		return serviceDateTime1;
	}

	public void setServiceDateTime1(String serviceDateTime1) {
		this.serviceDateTime1 = serviceDateTime1;
	}

	public String getServiceDateTime2() {
		return serviceDateTime2;
	}

	public void setServiceDateTime2(String serviceDateTime2) {
		this.serviceDateTime2 = serviceDateTime2;
	}

	public String getServiceTimeStart() {
		return serviceTimeStart;
	}

	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}

	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}

	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}

	public Integer getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
