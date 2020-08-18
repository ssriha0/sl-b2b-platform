package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("trip")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOTrip {
	
	@XStreamAlias("tripNo")
	private Integer tripNo;
	
	@XStreamAlias("currentApptStartDate")
	private String currentApptStartDate;
	
	@XStreamAlias("currentApptEndDate")
	private String currentApptEndDate;
	
	@XStreamAlias("currentApptStartTime")
	private String currentApptStartTime;
	
	@XStreamAlias("currentApptEndTime")
	private String currentApptEndTime;
	
	@XStreamAlias("checkInTime")
	private String checkInTime;
	
	@XStreamAlias("checkOutTime")
	private String checkOutTime;
	
	@XStreamAlias("tripStatus")
	private String tripStatus;
	
	@XStreamAlias("providerName")
	private String providerName;
	
	@XStreamAlias("tripStartSource")
	private String tripStartSource;
	
	@XStreamAlias("tripEndSource")
	private String tripEndSource;
	
	@XStreamAlias("nextApptStartDate")
	private String nextApptStartDate;
	
	@XStreamAlias("nextApptEndDate")
	private String nextApptEndDate;
	
	@XStreamAlias("nextApptStartTime")
	private String nextApptStartTime;
	
	@XStreamAlias("nextApptEndTime")
	private String nextApptEndTime;
	
	@XStreamAlias("revisitReason")
	private String revisitReason;
	
	@XStreamAlias("workStartedIndicator")
	private Integer workStartedIndicator;
	
	@XStreamAlias("merchandiseDeliveredIndicator")
	private Integer merchandiseDeliveredIndicator;
	
	@XStreamAlias("revisitComments")
	private String revisitComments;

	@XStreamAlias("changeLogs")
	private SOTripChangeLogs changeLogs;

	/**
	 * @return the tripNo
	 */
	public Integer getTripNo() {
		return tripNo;
	}

	/**
	 * @param tripNo the tripNo to set
	 */
	public void setTripNo(Integer tripNo) {
		this.tripNo = tripNo;
	}

	/**
	 * @return the currentApptStartDate
	 */
	public String getCurrentApptStartDate() {
		return currentApptStartDate;
	}

	/**
	 * @param currentApptStartDate the currentApptStartDate to set
	 */
	public void setCurrentApptStartDate(String currentApptStartDate) {
		this.currentApptStartDate = currentApptStartDate;
	}

	/**
	 * @return the currentApptEndDate
	 */
	public String getCurrentApptEndDate() {
		return currentApptEndDate;
	}

	/**
	 * @param currentApptEndDate the currentApptEndDate to set
	 */
	public void setCurrentApptEndDate(String currentApptEndDate) {
		this.currentApptEndDate = currentApptEndDate;
	}

	/**
	 * @return the currentApptStartTime
	 */
	public String getCurrentApptStartTime() {
		return currentApptStartTime;
	}

	/**List<
	 * @param currentApptStartTime the currentApptStartTime to set
	 */
	public void setCurrentApptStartTime(String currentApptStartTime) {
		this.currentApptStartTime = currentApptStartTime;
	}

	/**
	 * @return the currentApptEndTime
	 */
	public String getCurrentApptEndTime() {
		return currentApptEndTime;
	}

	/**
	 * @param currentApptEndTime the currentApptEndTime to set
	 */
	public void setCurrentApptEndTime(String currentApptEndTime) {
		this.currentApptEndTime = currentApptEndTime;
	}

	/**
	 * @return the checkInTime
	 */
	public String getCheckInTime() {
		return checkInTime;
	}

	/**
	 * @param checkInTime the checkInTime to set
	 */
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	/**
	 * @return the checkOutTime
	 */
	public String getCheckOutTime() {
		return checkOutTime;
	}

	/**
	 * @param checkOutTime the checkOutTime to set
	 */
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	/**
	 * @return the tripStatus
	 */
	public String getTripStatus() {
		return tripStatus;
	}

	/**
	 * @param tripStatus the tripStatus to set
	 */
	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	/**
	 * @return the providerName
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @param providerName the providerName to set
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**List<
	 * @return the nextApptStartDate
	 */
	public String getNextApptStartDate() {
		return nextApptStartDate;
	}

	/**
	 * @param nextApptStartDate the nextApptStartDate to set
	 */
	public void setNextApptStartDate(String nextApptStartDate) {
		this.nextApptStartDate = nextApptStartDate;
	}

	/**
	 * @return the nextApptEndDate
	 */
	public String getNextApptEndDate() {
		return nextApptEndDate;
	}

	/**
	 * @param nextApptEndDate the nextApptEndDate to set
	 */
	public void setNextApptEndDate(String nextApptEndDate) {
		this.nextApptEndDate = nextApptEndDate;
	}

	/**
	 * @return the nextApptStartTime
	 */
	public String getNextApptStartTime() {
		return nextApptStartTime;
	}

	/**
	 * @param nextApptStartTime the nextApptStartTime to set
	 */
	public void setNextApptStartTime(String nextApptStartTime) {
		this.nextApptStartTime = nextApptStartTime;
	}

	/**
	 * @return the nextApptEndTime
	 */
	public String getNextApptEndTime() {
		return nextApptEndTime;
	}

	/**
	 * @param nextApptEndTime the nextApptEndTime to set
	 */
	public void setNextApptEndTime(String nextApptEndTime) {
		this.nextApptEndTime = nextApptEndTime;
	}

	/**
	 * @return the revisitReason
	 */
	public String getRevisitReason() {
		return revisitReason;
	}

	/**
	 * @param revisitReason the revisitReason to set
	 */
	public void setRevisitReason(String revisitReason) {
		this.revisitReason = revisitReason;
	}

	/**
	 * @return the workStartedIndicator
	 */
	public Integer getWorkStartedIndicator() {
		return workStartedIndicator;
	}

	/**
	 * @param workStartedIndicator the workStartedIndicator to set
	 */
	public void setWorkStartedIndicator(Integer workStartedIndicator) {
		this.workStartedIndicator = workStartedIndicator;
	}

	/**
	 * @return the merchandiseDeliveredIndicator
	 */
	public Integer getMerchandiseDeliveredIndicator() {
		return merchandiseDeliveredIndicator;
	}

	/**
	 * @param merchandiseDeliveredIndicator the merchandiseDeliveredIndicator to set
	 */
	public void setMerchandiseDeliveredIndicator(
			Integer merchandiseDeliveredIndicator) {
		this.merchandiseDeliveredIndicator = merchandiseDeliveredIndicator;
	}

	/**
	 * @return the revisitComments
	 */
	public String getRevisitComments() {
		return revisitComments;
	}

	/**
	 * @param revisitComments the revisitComments to set
	 */
	public void setRevisitComments(String revisitComments) {
		this.revisitComments = revisitComments;
	}

	/**
	 * @return the changeLog
	 */
	public SOTripChangeLogs getChangeLogs() {
		return changeLogs;
	}

	/**
	 * @param changeLog the changeLog to set
	 */
	public void setChangeLogs(SOTripChangeLogs changeLogs) {
		this.changeLogs = changeLogs;
	}

	/**
	 * @return the tripStartSource
	 */
	public String getTripStartSource() {
		return tripStartSource;
	}

	/**
	 * @param tripStartSource the tripStartSource to set
	 */
	public void setTripStartSource(String tripStartSource) {
		this.tripStartSource = tripStartSource;
	}

	/**
	 * @return the tripEndSource
	 */
	public String getTripEndSource() {
		return tripEndSource;
	}

	/**
	 * @param tripEndSource the tripEndSource to set
	 */
	public void setTripEndSource(String tripEndSource) {
		this.tripEndSource = tripEndSource;
	}

}
