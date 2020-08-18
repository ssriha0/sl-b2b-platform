package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

public class ServiceDatetimeSlot {
	
	private Integer slotId;
	private String soId;

	private Timestamp serviceStartDate;
	private String serviceStartTime;

	private Timestamp serviceEndDate;
	private String serviceEndTime;
	private String slotSelected;
	
	private String preferenceInd;
	
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	//to represent CST or DST when daylight saving time
	private String timeZone;
	private String appointmentDateTime;
	

	public Timestamp getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Timestamp serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Timestamp getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Timestamp serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public Integer getSlotId() {
		return slotId;
	}

	public void setSlotId(Integer slotId) {
		this.slotId = slotId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getSlotSelected() {
		return slotSelected;
	}

	public void setSlotSelected(String slotSelected) {
		this.slotSelected = slotSelected;
	}

	public String getPreferenceInd() {
		return preferenceInd;
	}

	public void setPreferenceInd(String preferenceInd) {
		this.preferenceInd = preferenceInd;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(String appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}
	
	
	
	
	

}
