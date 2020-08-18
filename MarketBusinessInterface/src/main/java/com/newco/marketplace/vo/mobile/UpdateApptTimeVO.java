package com.newco.marketplace.vo.mobile;

import java.sql.Timestamp;

public class UpdateApptTimeVO {
	
	private int scheduleStatusId; 
	
	private String source; 
	private Integer reasonId;
	private String eta;
	private Integer customerConfirmedInd;
	private String soId;
	private Integer providerId;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private String modifiedByName;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String startDate;
	private String endDate;
	private String zone;
	private Integer minTimeWindow;
	private Integer maxTimeWindow;
	private Integer serviceDateType;
	private Integer wfStateId;
	private Integer tripNo;
	
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	public Integer getServiceDateType() {
		return serviceDateType;
	}
	public void setServiceDateType(Integer serviceDateType) {
		this.serviceDateType = serviceDateType;
	}
	public int getScheduleStatusId() {
		return scheduleStatusId;
	}
	public void setScheduleStatusId(int scheduleStatusId) {
		this.scheduleStatusId = scheduleStatusId;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getReasonId() {
		return reasonId;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	
	public Integer getCustomerConfirmedInd() {
		return customerConfirmedInd;
	}
	public void setCustomerConfirmedInd(Integer customerConfirmedInd) {
		this.customerConfirmedInd = customerConfirmedInd;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
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
	public String getModifiedByName() {
		return modifiedByName;
	}
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public Integer getMinTimeWindow() {
		return minTimeWindow;
	}
	public void setMinTimeWindow(Integer minTimeWindow) {
		this.minTimeWindow = minTimeWindow;
	}
	public Integer getMaxTimeWindow() {
		return maxTimeWindow;
	}
	public void setMaxTimeWindow(Integer maxTimeWindow) {
		this.maxTimeWindow = maxTimeWindow;
	}
	public Integer getTripNo() {
		return tripNo;
	}
	public void setTripNo(Integer tripNo) {
		this.tripNo = tripNo;
	}
	
}
