package com.newco.marketplace.api.beans.search.firms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("availableTimeSlot")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailableTimeSlot {
	
	@XStreamAlias("day")
	private String day;
	
	@XStreamAlias("timeWindow")
	private String timeWindow;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTimeWindow() {
		return timeWindow;
	}

	public void setTimeWindow(String timeWindow) {
		this.timeWindow = timeWindow;
	}

}
