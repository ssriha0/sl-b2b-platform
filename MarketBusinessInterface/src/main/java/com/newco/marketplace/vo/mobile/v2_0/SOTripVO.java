package com.newco.marketplace.vo.mobile.v2_0;

import java.sql.Timestamp;
import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class SOTripVO extends CommonVO{
	private static final long serialVersionUID = 1L;
	
	public SOTripVO() {
		
	}
	
	private Integer soTripId;
	private String soId;
	private Integer tripNo;
	private String tripStatus;
	private Long tripStartVisitId;
	private Long tripEndVisitId;
	private String tripStartSource;
	private String tripEndSource;
	private Timestamp currentApptStartDate;
	private Timestamp currentApptEndDate;
	private String currentApptStartTime;
	private String currentApptEndTime;
	private Timestamp nextApptStartDate;
	private Timestamp nextApptEndDate;
	private String nextApptStartTime;
	private String nextApptEndTime;
	private String revisitReason;
	private boolean merchDelieverd;
	private boolean workStarted;
	private String tripComments;
	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
	private String modifiedBy;
	private String revisitNeededCreatedBy;


	/**
	 * @return the soTripId
	 */
	public Integer getSoTripId() {
		return soTripId;
	}
	/**
	 * @param soTripId the soTripId to set
	 */
	public void setSoTripId(Integer soTripId) {
		this.soTripId = soTripId;
	}
	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}
	/**
	 * @param soId the soId to set
	 */
	public void setSoId(String soId) {
		this.soId = soId;
	}
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
	 * @return the merchDelieverd
	 */
	public boolean isMerchDelieverd() {
		return merchDelieverd;
	}
	/**
	 * @param merchDelieverd the merchDelieverd to set
	 */
	public void setMerchDelieverd(boolean merchDelieverd) {
		this.merchDelieverd = merchDelieverd;
	}
	/**
	 * @return the workStarted
	 */
	public boolean isWorkStarted() {
		return workStarted;
	}
	/**
	 * @param workStarted the workStarted to set
	 */
	public void setWorkStarted(boolean workStarted) {
		this.workStarted = workStarted;
	}
	/**
	 * @return the tripComments
	 */
	public String getTripComments() {
		return tripComments;
	}
	/**
	 * @param tripComments the tripComments to set
	 */
	public void setTripComments(String tripComments) {
		this.tripComments = tripComments;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the tripStartVisitId
	 */
	public Long getTripStartVisitId() {
		return tripStartVisitId;
	}
	/**
	 * @param tripStartVisitId the tripStartVisitId to set
	 */
	public void setTripStartVisitId(Long tripStartVisitId) {
		this.tripStartVisitId = tripStartVisitId;
	}
	/**
	 * @return the triEndVisitId
	 */
	public Long getTripEndVisitId() {
		return tripEndVisitId;
	}
	/**
	 * @param triEndVisitId the triEndVisitId to set
	 */
	public void setTripEndVisitId(Long tripEndVisitId) {
		this.tripEndVisitId = tripEndVisitId;
	}
	/**
	 * @return the currnetApptStartDate
	 */
	public Timestamp getCurrentApptStartDate() {
		return currentApptStartDate;
	}
	/**
	 * @param currentApptStartDate the currentApptStartDate to set
	 */
	public void setCurrentApptStartDate(Timestamp currentApptStartDate) {
		this.currentApptStartDate = currentApptStartDate;
	}
	/**
	 * @return the currentApptEndDate
	 */
	public Timestamp getCurrentApptEndDate() {
		return currentApptEndDate;
	}
	/**
	 * @param currentApptEndDate the currentApptEndDate to set
	 */
	public void setCurrentApptEndDate(Timestamp currentApptEndDate) {
		this.currentApptEndDate = currentApptEndDate;
	}
	/**
	 * @return the currentApptStartTime
	 */
	public String getCurrentApptStartTime() {
		return currentApptStartTime;
	}
	/**
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
	 * @return the nextApptStartDate
	 */
	public Timestamp getNextApptStartDate() {
		return nextApptStartDate;
	}
	/**
	 * @param nextApptStartDate the nextApptStartDate to set
	 */
	public void setNextApptStartDate(Timestamp nextApptStartDate) {
		this.nextApptStartDate = nextApptStartDate;
	}
	/**
	 * @return the nextApptEndDate
	 */
	public Timestamp getNextApptEndDate() {
		return nextApptEndDate;
	}
	/**
	 * @param nextApptEndDate the nextApptEndDate to set
	 */
	public void setNextApptEndDate(Timestamp nextApptEndDate) {
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
	public String getRevisitNeededCreatedBy() {
		return revisitNeededCreatedBy;
	}
	public void setRevisitNeededCreatedBy(String revisitNeededCreatedBy) {
		this.revisitNeededCreatedBy = revisitNeededCreatedBy;
	}
	
	
}
