package com.newco.marketplace.api.mobile.beans.Filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("appointment")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentDateCriteria {

	@XStreamAlias("appointmentType")
	private String appointmentType;
	@XStreamAlias("appointmentStartDate")
	private String appointmentStartDate;
	@XStreamAlias("appointmentEndDate")
	private String appointmentEndDate;
	
	public String getAppointmentType() {
		return appointmentType;
	}
	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}
	public String getAppointmentStartDate() {
		return appointmentStartDate;
	}
	public void setAppointmentStartDate(String appointmentStartDate) {
		this.appointmentStartDate = appointmentStartDate;
	}
	public String getAppointmentEndDate() {
		return appointmentEndDate;
	}
	public void setAppointmentEndDate(String appointmentEndDate) {
		this.appointmentEndDate = appointmentEndDate;
	}
	

	
}
