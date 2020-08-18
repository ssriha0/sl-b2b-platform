package com.newco.marketplace.api.mobile.beans.so.search.advance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("appointment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Appointment {
	@XStreamAlias("appointmentFilter")
	private String appointmentFilter;
	@XStreamAlias("startRange")
	private String startRange;
	@XStreamAlias("endRange")
	private String endRange;
	public String getAppointmentFilter() {
		return appointmentFilter;
	}
	public void setAppointmentFilter(String appointmentFilter) {
		this.appointmentFilter = appointmentFilter;
	}
	public String getStartRange() {
		return startRange;
	}
	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}
	public String getEndRange() {
		return endRange;
	}
	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}
	
	
}
