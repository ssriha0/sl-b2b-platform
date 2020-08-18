package com.newco.marketplace.vo.mobile.v2_0;

import java.sql.Timestamp;
import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * @author Infosys
 * 
 */
public class SORevisitNeededVO extends CommonVO {

	private static final long serialVersionUID = 1L;

	private Integer tripNumber;
	private String soId;
	private Integer resourceId;
	private Timestamp nextApptDate;
	private String nextApptDateString;
	private String nextApptStartTime;
	private String nextApptEndTime;
	private String revisitReason;
	private int merchDeliveredInd;
	private int workStartedInd;
	private String tripComments;
	private Date modifiedDate;
	private String modifiedBy;
	
	private Timestamp serviceDateStart;
	private Timestamp serviceDateEnd;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private String timeZone;
	
	private Timestamp currentServiceDateStart;
	private Timestamp currentServiceDateEnd;
	private String currentServiceTimeStart;
	private String currentServiceTimeEnd;
	
	private Integer vendorId;
	private String scheduleSource;
	private Integer scheduleStatusId;
	private Integer responseReasonId;
	private Integer customerApptConfirmInd;
	private String eta;
	private String userName;
	
	public Integer getTripNumber() {
		return tripNumber;
	}
	public void setTripNumber(Integer tripNumber) {
		this.tripNumber = tripNumber;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Timestamp getNextApptDate() {
		return nextApptDate;
	}
	public void setNextApptDate(Timestamp nextApptDate) {
		this.nextApptDate = nextApptDate;
	}
	public String getNextApptStartTime() {
		return nextApptStartTime;
	}
	public void setNextApptStartTime(String nextApptStartTime) {
		this.nextApptStartTime = nextApptStartTime;
	}
	public String getNextApptEndTime() {
		return nextApptEndTime;
	}
	public void setNextApptEndTime(String nextApptEndTime) {
		this.nextApptEndTime = nextApptEndTime;
	}
	public String getRevisitReason() {
		return revisitReason;
	}
	public void setRevisitReason(String revisitReason) {
		this.revisitReason = revisitReason;
	}
	public int getMerchDeliveredInd() {
		return merchDeliveredInd;
	}
	public void setMerchDeliveredInd(int merchDeliveredInd) {
		this.merchDeliveredInd = merchDeliveredInd;
	}
	public int getWorkStartedInd() {
		return workStartedInd;
	}
	public void setWorkStartedInd(int workStartedInd) {
		this.workStartedInd = workStartedInd;
	}
	public String getTripComments() {
		return tripComments;
	}
	public void setTripComments(String tripComments) {
		this.tripComments = tripComments;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getServiceDateStart() {
		return serviceDateStart;
	}
	public void setServiceDateStart(Timestamp serviceDateStart) {
		this.serviceDateStart = serviceDateStart;
	}
	public Timestamp getServiceDateEnd() {
		return serviceDateEnd;
	}
	public void setServiceDateEnd(Timestamp serviceDateEnd) {
		this.serviceDateEnd = serviceDateEnd;
	}
	public String getServiceTimeStart() {
		return serviceTimeStart;
	}
	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}
	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}
	public Timestamp getCurrentServiceDateStart() {
		return currentServiceDateStart;
	}
	public void setCurrentServiceDateStart(Timestamp currentServiceDateStart) {
		this.currentServiceDateStart = currentServiceDateStart;
	}
	public Timestamp getCurrentServiceDateEnd() {
		return currentServiceDateEnd;
	}
	public void setCurrentServiceDateEnd(Timestamp currentServiceDateEnd) {
		this.currentServiceDateEnd = currentServiceDateEnd;
	}
	public String getCurrentServiceTimeStart() {
		return currentServiceTimeStart;
	}
	public void setCurrentServiceTimeStart(String currentServiceTimeStart) {
		this.currentServiceTimeStart = currentServiceTimeStart;
	}
	public String getCurrentServiceTimeEnd() {
		return currentServiceTimeEnd;
	}
	public void setCurrentServiceTimeEnd(String currentServiceTimeEnd) {
		this.currentServiceTimeEnd = currentServiceTimeEnd;
	}
	public String getNextApptDateString() {
		return nextApptDateString;
	}
	public void setNextApptDateString(String nextApptDateString) {
		this.nextApptDateString = nextApptDateString;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getScheduleSource() {
		return scheduleSource;
	}
	public void setScheduleSource(String scheduleSource) {
		this.scheduleSource = scheduleSource;
	}
	public Integer getScheduleStatusId() {
		return scheduleStatusId;
	}
	public void setScheduleStatusId(Integer scheduleStatusId) {
		this.scheduleStatusId = scheduleStatusId;
	}
	public Integer getResponseReasonId() {
		return responseReasonId;
	}
	public void setResponseReasonId(Integer responseReasonId) {
		this.responseReasonId = responseReasonId;
	}
	public Integer getCustomerApptConfirmInd() {
		return customerApptConfirmInd;
	}
	public void setCustomerApptConfirmInd(Integer customerApptConfirmInd) {
		this.customerApptConfirmInd = customerApptConfirmInd;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
