package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails;

import java.sql.Timestamp;

public class UpdateScheduleVO {
	
	private int scheduleStatusId; 
	
	private String source; 
	private String reasonId;
	private String eta;
	private Integer customerConfirmedInd;
	private String soId;
	private Integer providerId;
	private Timestamp appointStartDate;
	private Timestamp appointEndDate;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private String specialInstructions;
	private String soNotes;
	private String modifiedByName;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	/**SL 18896 R8.2, pass the startDate & endDate parameter START**/
	private String serviceDateStart;
	private String serviceDateEnd;
	/**SL 18896 R8.2, pass the startDate & endDate parameter END**/
	/**R12.0 Sprint Adding trip no to update so_trip table**/
	private Integer tripNo;
	
	private Boolean customerAvailableFlag;
	
	private Integer custAvailableRespCode;
	private String serviceTimeZone;

	private Integer resourceId;
	private Integer custNotAvailableReasonCode;
	private String etaOriginalValue;
	
	private String startTimeFromRequest;
	private String endTimeFromRequest;
	private Integer minTimeWindow;
	private Integer maxTimeWindow;
	private Integer serviceDateType;
	private Integer roleId;

	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getServiceTimeZone() {
		return serviceTimeZone;
	}
	public void setServiceTimeZone(String serviceTimeZone) {
		this.serviceTimeZone = serviceTimeZone;
	}
	public Integer getCustAvailableRespCode() {
		return custAvailableRespCode;
	}
	public void setCustAvailableRespCode(Integer custAvailableRespCode) {
		this.custAvailableRespCode = custAvailableRespCode;
	}
	public Boolean getCustomerAvailableFlag() {
		return customerAvailableFlag;
	}
	public void setCustomerAvailableFlag(Boolean customerAvailableFlag) {
		this.customerAvailableFlag = customerAvailableFlag;
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
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
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
	public Timestamp getAppointStartDate() {
		return appointStartDate;
	}
	public void setAppointStartDate(Timestamp appointStartDate) {
		this.appointStartDate = appointStartDate;
	}
	public Timestamp getAppointEndDate() {
		return appointEndDate;
	}
	public void setAppointEndDate(Timestamp appointEndDate) {
		this.appointEndDate = appointEndDate;
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
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public String getSoNotes() {
		return soNotes;
	}
	public void setSoNotes(String soNotes) {
		this.soNotes = soNotes;
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
	public String getServiceDateStart() {
		return serviceDateStart;
	}
	public void setServiceDateStart(String serviceDateStart) {
		this.serviceDateStart = serviceDateStart;
	}
	public String getServiceDateEnd() {
		return serviceDateEnd;
	}
	public void setServiceDateEnd(String serviceDateEnd) {
		this.serviceDateEnd = serviceDateEnd;
	}
	public Integer getTripNo() {
		return tripNo;
	}
	public void setTripNo(Integer tripNo) {
		this.tripNo = tripNo;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getCustNotAvailableReasonCode() {
		return custNotAvailableReasonCode;
	}
	public void setCustNotAvailableReasonCode(Integer custNotAvailableReasonCode) {
		this.custNotAvailableReasonCode = custNotAvailableReasonCode;
	}
	public String getEtaOriginalValue() {
		return etaOriginalValue;
	}
	public void setEtaOriginalValue(String etaOriginalValue) {
		this.etaOriginalValue = etaOriginalValue;
	}
	public String getStartTimeFromRequest() {
		return startTimeFromRequest;
	}
	public void setStartTimeFromRequest(String startTimeFromRequest) {
		this.startTimeFromRequest = startTimeFromRequest;
	}
	public String getEndTimeFromRequest() {
		return endTimeFromRequest;
	}
	public void setEndTimeFromRequest(String endTimeFromRequest) {
		this.endTimeFromRequest = endTimeFromRequest;
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
	
}
