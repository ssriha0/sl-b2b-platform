package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.webservices.base.CommonVO;


public class RevisitNeededInfoVO extends CommonVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String provider;
	private Integer soTripId;
	private String appointmentDate;
	private Timestamp currentApptStartDate;
	private Timestamp currentApptEndDate;
	private String currentApptStartTime;
	private String currentApptEndTime;
	private String serviceWindow;
	private Date checkedInDate;
	private Date checkedOutDate;
	private String nextAppointment;
	private Timestamp nextAppointmentStartDate;
	private Timestamp nextAppointmentEndDate;
	private String nextAppointmentStartTime;
	private String nextAppointmentEndTime;
	private String revisitReason;
	private Boolean merchDeliveredInd;
	private Boolean workStartedInd;
	private String comments;
	private String departureReason;
	private String timeZone;
	private String revisitCreatedBy;
	private List<String> changeTypes;
	
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
	public String getNextAppointment() {
		return nextAppointment;
	}
	public void setNextAppointment(String nextAppointment) {
		this.nextAppointment = nextAppointment;
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
	public Timestamp getCurrentApptStartDate() {
		return currentApptStartDate;
	}
	public void setCurrentApptStartDate(Timestamp currentApptStartDate) {
		this.currentApptStartDate = currentApptStartDate;
	}
	public Timestamp getCurrentApptEndDate() {
		return currentApptEndDate;
	}
	public void setCurrentApptEndDate(Timestamp currentApptEndDate) {
		this.currentApptEndDate = currentApptEndDate;
	}
	public Date getCheckedInDate() {
		return checkedInDate;
	}
	public void setCheckedInDate(Date checkedInDate) {
		this.checkedInDate = checkedInDate;
	}
	public Date getCheckedOutDate() {
		return checkedOutDate;
	}
	public void setCheckedOutDate(Date checkedOutDate) {
		this.checkedOutDate = checkedOutDate;
	}
	public Timestamp getNextAppointmentStartDate() {
		return nextAppointmentStartDate;
	}
	public void setNextAppointmentStartDate(Timestamp nextAppointmentStartDate) {
		this.nextAppointmentStartDate = nextAppointmentStartDate;
	}
	public Timestamp getNextAppointmentEndDate() {
		return nextAppointmentEndDate;
	}
	public String getCurrentApptStartTime() {
		return currentApptStartTime;
	}
	public void setCurrentApptStartTime(String currentApptStartTime) {
		this.currentApptStartTime = currentApptStartTime;
	}
	public String getCurrentApptEndTime() {
		return currentApptEndTime;
	}
	public void setCurrentApptEndTime(String currentApptEndTime) {
		this.currentApptEndTime = currentApptEndTime;
	}
	public String getNextAppointmentStartTime() {
		return nextAppointmentStartTime;
	}
	public void setNextAppointmentStartTime(String nextAppointmentStartTime) {
		this.nextAppointmentStartTime = nextAppointmentStartTime;
	}
	public String getNextAppointmentEndTime() {
		return nextAppointmentEndTime;
	}
	public void setNextAppointmentEndTime(String nextAppointmentEndTime) {
		this.nextAppointmentEndTime = nextAppointmentEndTime;
	}
	public String getRevisitReason() {
		return revisitReason;
	}
	public void setRevisitReason(String revisitReason) {
		this.revisitReason = revisitReason;
	}
	public void setNextAppointmentEndDate(Timestamp nextAppointmentEndDate) {
		this.nextAppointmentEndDate = nextAppointmentEndDate;
	}
	public Integer getSoTripId() {
		return soTripId;
	}
	public void setSoTripId(Integer soTripId) {
		this.soTripId = soTripId;
	}
	public String getDepartureReason() {
		return departureReason;
	}
	public void setDepartureReason(String departureReason) {
		this.departureReason = departureReason;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public List<String> getChangeTypes() {
		return changeTypes;
	}
	public void setChangeTypes(List<String> changeTypes) {
		this.changeTypes = changeTypes;
	}
	public String getRevisitCreatedBy() {
		return revisitCreatedBy;
	}
	public void setRevisitCreatedBy(String revisitCreatedBy) {
		this.revisitCreatedBy = revisitCreatedBy;
	}
	
}
