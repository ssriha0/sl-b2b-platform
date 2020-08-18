package com.newco.marketplace.api.beans.provider.capacity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("timeSlot")
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeSlot {
	
	@XStreamAlias("startTime")
	String startTime;
	
	@XStreamAlias("endTime")
	String endTime;
	
	public TimeSlot() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TimeSlot(String startTime, String endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	

}
