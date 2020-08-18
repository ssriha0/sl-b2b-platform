package com.newco.marketplace.web.dto;

import java.util.List;


public class RevisitNeededInfoDTO extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1890786775085999556L;
	private String provider;
	private String appointmentDate;
	private String serviceWindow;
	private String checkedIn;
	private String checkedOut;
	private String nextAppointment;
	private String revisitReasonCode;
	private Boolean merchDeliveredInd;
	private Boolean workStartedInd;
	private String comments;
	private String departureReason;
	private String departureReasonHeader;
	private List changeTypes;
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getServiceWindow() {
		return serviceWindow;
	}
	public void setServiceWindow(String serviceWindow) {
		this.serviceWindow = serviceWindow;
	}
	public String getCheckedIn() {
		return checkedIn;
	}
	public void setCheckedIn(String checkedIn) {
		this.checkedIn = checkedIn;
	}
	public String getCheckedOut() {
		return checkedOut;
	}
	public void setCheckedOut(String checkedOut) {
		this.checkedOut = checkedOut;
	}
	public String getNextAppointment() {
		return nextAppointment;
	}
	public void setNextAppointment(String nextAppointment) {
		this.nextAppointment = nextAppointment;
	}
	public String getRevisitReasonCode() {
		return revisitReasonCode;
	}
	public void setRevisitReasonCode(String revisitReasonCode) {
		this.revisitReasonCode = revisitReasonCode;
	}
	public Boolean getMerchDeliveredInd() {
		return merchDeliveredInd;
	}
	public void setMerchDeliveredInd(Boolean merchDeliveredInd) {
		this.merchDeliveredInd = merchDeliveredInd;
	}
	public Boolean getWorkStartedInd() {
		return workStartedInd;
	}
	public void setWorkStartedInd(Boolean workStartedInd) {
		this.workStartedInd = workStartedInd;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List getChangeTypes() {
		return changeTypes;
	}
	public void setChangeTypes(List changeTypes) {
		this.changeTypes = changeTypes;
	}
	public String getDepartureReason() {
		return departureReason;
	}
	public void setDepartureReason(String departureReason) {
		this.departureReason = departureReason;
	}
	public String getDepartureReasonHeader() {
		return departureReasonHeader;
	}
	public void setDepartureReasonHeader(String departureReasonHeader) {
		this.departureReasonHeader = departureReasonHeader;
	}
	
	
}
