package com.newco.marketplace.buyeroutboundnotification.vo;

import java.sql.Timestamp;

public class SOScheduleOutboundNotificationVO {
	private Timestamp serviceOrderScheduleDate;
	private String serviceOrderScheduleFromTime;
	private String serviceOrderScheduleToTime;
	
	// Include the reschdule information in this class
	
	
	public String getServiceOrderScheduleFromTime() {
		return serviceOrderScheduleFromTime;
	}

	public Timestamp getServiceOrderScheduleDate() {
		return serviceOrderScheduleDate;
	}

	public void setServiceOrderScheduleDate(Timestamp serviceOrderScheduleDate) {
		this.serviceOrderScheduleDate = serviceOrderScheduleDate;
	}

	public void setServiceOrderScheduleFromTime(
			String serviceOrderScheduleFromTime) {
		this.serviceOrderScheduleFromTime = serviceOrderScheduleFromTime;
	}

	public String getServiceOrderScheduleToTime() {
		return serviceOrderScheduleToTime;
	}

	public void setServiceOrderScheduleToTime(String serviceOrderScheduleToTime) {
		this.serviceOrderScheduleToTime = serviceOrderScheduleToTime;
	}
}
