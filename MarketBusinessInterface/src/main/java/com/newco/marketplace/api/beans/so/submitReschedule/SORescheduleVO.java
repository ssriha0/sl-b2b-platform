package com.newco.marketplace.api.beans.so.submitReschedule;

import java.sql.Timestamp;



public class SORescheduleVO {

	
	private String rescheduleDateType;	
	private String rescheduleStartDate;		
	private String rescheduleEndDate;	
	
	private String rescheduleStartTime;	
	private String rescheduleEndTime;	
	private Integer rescheduleReasonCode;	
	private String rescheduleComments;
	
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	
	private String soId;
	private String firmId;

	
	public String getRescheduleDateType() {
		return rescheduleDateType;
	}

	public void setRescheduleDateType(String rescheduleDateType) {
		this.rescheduleDateType = rescheduleDateType;
	}

	public String getRescheduleStartDate() {
		return rescheduleStartDate;
	}

	public void setRescheduleStartDate(String rescheduleStartDate) {
		this.rescheduleStartDate = rescheduleStartDate;
	}

	public String getRescheduleEndDate() {
		return rescheduleEndDate;
	}

	public void setRescheduleEndDate(String rescheduleEndDate) {
		this.rescheduleEndDate = rescheduleEndDate;
	}

	public String getRescheduleStartTime() {
		return rescheduleStartTime;
	}

	public void setRescheduleStartTime(String rescheduleStartTime) {
		this.rescheduleStartTime = rescheduleStartTime;
	}

	public String getRescheduleEndTime() {
		return rescheduleEndTime;
	}

	public void setRescheduleEndTime(String rescheduleEndTime) {
		this.rescheduleEndTime = rescheduleEndTime;
	}

	public Integer getRescheduleReasonCode() {
		return rescheduleReasonCode;
	}

	public void setRescheduleReasonCode(Integer rescheduleReasonCode) {
		this.rescheduleReasonCode = rescheduleReasonCode;
	}

	public String getRescheduleComments() {
		return rescheduleComments;
	}

	public void setRescheduleComments(String rescheduleComments) {
		this.rescheduleComments = rescheduleComments;
	}

	
	public Timestamp getServiceDate1() {
		return serviceDate1;
	}

	public void setServiceDate1(Timestamp serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}

	public Timestamp getServiceDate2() {
		return serviceDate2;
	}

	public void setServiceDate2(Timestamp serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

}
