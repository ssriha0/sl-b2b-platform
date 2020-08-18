package com.newco.marketplace.api.beans.so.soDetails.vo;

import java.sql.Timestamp;

public class RescheduleDetailsVO {
	
	private Timestamp rescheduleServiceDate1;
	private String rescheduleServiceTime1;
	private Timestamp rescheduleServiceDate2;
	private String rescheduleServiceTime2;
	private String reasonCodeDescription;
	private String rescheduleInitiatedBy;
	private String rescheduleInitiatedDate;
	private String rescheduleTimeZoneDate1;
	private String rescheduleTimeZoneDate2;
	private int roleId;
	private String timeZone;
	
	
	
	public String getRescheduleTimeZoneDate1() {
		return rescheduleTimeZoneDate1;
	}
	public void setRescheduleTimeZoneDate1(String rescheduleTimeZoneDate1) {
		this.rescheduleTimeZoneDate1 = rescheduleTimeZoneDate1;
	}
	public String getRescheduleTimeZoneDate2() {
		return rescheduleTimeZoneDate2;
	}
	public void setRescheduleTimeZoneDate2(String rescheduleTimeZoneDate2) {
		this.rescheduleTimeZoneDate2 = rescheduleTimeZoneDate2;
	}
	public Timestamp getRescheduleServiceDate1() {
		return rescheduleServiceDate1;
	}
	public void setRescheduleServiceDate1(Timestamp rescheduleServiceDate1) {
		this.rescheduleServiceDate1 = rescheduleServiceDate1;
	}
	public Timestamp getRescheduleServiceDate2() {
		return rescheduleServiceDate2;
	}
	public void setRescheduleServiceDate2(Timestamp rescheduleServiceDate2) {
		this.rescheduleServiceDate2 = rescheduleServiceDate2;
	}
	public String getRescheduleServiceTime1() {
		return rescheduleServiceTime1;
	}
	public void setRescheduleServiceTime1(String rescheduleServiceTime1) {
		this.rescheduleServiceTime1 = rescheduleServiceTime1;
	}
	
	public String getRescheduleServiceTime2() {
		return rescheduleServiceTime2;
	}
	public void setRescheduleServiceTime2(String rescheduleServiceTime2) {
		this.rescheduleServiceTime2 = rescheduleServiceTime2;
	}
	
	public String getReasonCodeDescription() {
		return reasonCodeDescription;
	}
	public void setReasonCodeDescription(String reasonCodeDescription) {
		this.reasonCodeDescription = reasonCodeDescription;
	}
	public String getRescheduleInitiatedBy() {
		return rescheduleInitiatedBy;
	}
	public void setRescheduleInitiatedBy(String rescheduleInitiatedBy) {
		this.rescheduleInitiatedBy = rescheduleInitiatedBy;
	}
	public String getRescheduleInitiatedDate() {
		return rescheduleInitiatedDate;
	}
	public void setRescheduleInitiatedDate(String rescheduleInitiatedDate) {
		this.rescheduleInitiatedDate = rescheduleInitiatedDate;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	
}
