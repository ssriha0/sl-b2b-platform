package com.newco.marketplace.api.beans.provider.capacity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("availableTimeSlotList")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailableTimeSlotList {

	@XStreamAlias("date")
	private String date;
	
	@XStreamAlias("timeSlot")
	@XStreamImplicit(itemFieldName="timeSlot")
	private List<TimeSlot> timeSlot;


	public List<TimeSlot> getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(List<TimeSlot> timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
}
