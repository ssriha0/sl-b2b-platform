package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.Date;
import java.util.List;


public class ScheduleRequestVO {

	private String leadId;
	private Date serviceDate;
    private String serviceStartTime;
    private String serviceEndTime;
    private Integer vendorId;
	private int statusId; 
	private String timeZone;
    private String rescheduleReason; 
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
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
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
    public int getStatusId() {
		return statusId;
	}
	public String getRescheduleReason() {
		return rescheduleReason;
	}
	public void setRescheduleReason(String rescheduleReason) {
		this.rescheduleReason = rescheduleReason;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
    
	
}
