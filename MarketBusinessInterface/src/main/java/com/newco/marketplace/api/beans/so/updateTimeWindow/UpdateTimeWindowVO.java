package com.newco.marketplace.api.beans.so.updateTimeWindow;




public class UpdateTimeWindowVO {
	//common parameters
	private Integer vendorId;
	private Integer resourceId;
	private String soId;
	private String userName;
	private Integer entityId;
	//This  variable is to set the buyer or provider or admin role
	private Integer roleId;
	private String createdBy;
	//request parameters
	private String startTime;
	private String endTime;
	private String eta;
	private Boolean customerConfirmedInd;
	//parameters from db fetch
	private String startDateInGMT;
	private String endDateInGMT;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private String timeZone;
	private Integer minTimeWindow;
	private Integer maxTimeWindow;
	private Integer serviceDateType;
	
	private String startTimeIn12hrformat;
	private String endTimeIn12hrformat;
	private String etaIn12hrformat;
	
	private String startTimeToSave;
	private String startDateToSave;
	private String endTimeToSave;
	private String endDateToSave;
	//This role level is to set the levl 1,2,3 of provider
	private Integer roleLevel;
	
	public Integer getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public Boolean getCustomerConfirmedInd() {
		return customerConfirmedInd;
	}
	public void setCustomerConfirmedInd(Boolean customerConfirmedInd) {
		this.customerConfirmedInd = customerConfirmedInd;
	}
	public String getStartDateInGMT() {
		return startDateInGMT;
	}
	public void setStartDateInGMT(String startDateInGMT) {
		this.startDateInGMT = startDateInGMT;
	}
	public String getEndDateInGMT() {
		return endDateInGMT;
	}
	public void setEndDateInGMT(String endDateInGMT) {
		this.endDateInGMT = endDateInGMT;
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
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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
	public Integer getServiceDateType() {
		return serviceDateType;
	}
	public void setServiceDateType(Integer serviceDateType) {
		this.serviceDateType = serviceDateType;
	}
	public String getStartTimeIn12hrformat() {
		return startTimeIn12hrformat;
	}
	public void setStartTimeIn12hrformat(String startTimeIn12hrformat) {
		this.startTimeIn12hrformat = startTimeIn12hrformat;
	}
	public String getEndTimeIn12hrformat() {
		return endTimeIn12hrformat;
	}
	public void setEndTimeIn12hrformat(String endTimeIn12hrformat) {
		this.endTimeIn12hrformat = endTimeIn12hrformat;
	}
	public String getEtaIn12hrformat() {
		return etaIn12hrformat;
	}
	public void setEtaIn12hrformat(String etaIn12hrformat) {
		this.etaIn12hrformat = etaIn12hrformat;
	}
	public String getStartTimeToSave() {
		return startTimeToSave;
	}
	public void setStartTimeToSave(String startTimeToSave) {
		this.startTimeToSave = startTimeToSave;
	}
	public String getStartDateToSave() {
		return startDateToSave;
	}
	public void setStartDateToSave(String startDateToSave) {
		this.startDateToSave = startDateToSave;
	}
	public String getEndTimeToSave() {
		return endTimeToSave;
	}
	public void setEndTimeToSave(String endTimeToSave) {
		this.endTimeToSave = endTimeToSave;
	}
	public String getEndDateToSave() {
		return endDateToSave;
	}
	public void setEndDateToSave(String endDateToSave) {
		this.endDateToSave = endDateToSave;
	}
}
