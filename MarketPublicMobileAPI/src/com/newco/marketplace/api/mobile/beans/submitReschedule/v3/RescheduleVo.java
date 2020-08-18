package com.newco.marketplace.api.mobile.beans.submitReschedule.v3;

import java.sql.Timestamp;
import java.util.Date;

import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.sears.os.vo.SerializableBaseVO;

public class RescheduleVo extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String soId;
	private Integer roleId;
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	private String startTime;
	private String endTime;
	private Integer rescheduleType;
	private Date rescheduleServiceDate1;
	private Date rescheduleServiceDate2;
	private Timestamp rescheduleServiceStartDate;
	private Timestamp rescheduleServiceEndDate;
	private String rescheduleServiceTime1;
	private String rescheduleServiceTime2;
	private String serviceLocnTimeZone;
	private String rescheduleRespondType;
	private Integer resourceId;
	private ResultsCode validationCode;
	private Integer entityId;
	private String reasonCodeDescription;
	private boolean isValidSignal;
	private Integer buyerId;
	private Date rescheduleServiceDate1INGMT;
	private Date rescheduleServiceDate2INGMT;
	
	
	private String scheduleType;
	private String serviceDateTime1;
	private String serviceDateTime2;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private Integer reasonCode;
	private String comments;
	
	
	
	public Date getRescheduleServiceDate1INGMT() {
		return rescheduleServiceDate1INGMT;
	}
	public void setRescheduleServiceDate1INGMT(Date rescheduleServiceDate1INGMT) {
		this.rescheduleServiceDate1INGMT = rescheduleServiceDate1INGMT;
	}
	public Date getRescheduleServiceDate2INGMT() {
		return rescheduleServiceDate2INGMT;
	}
	public void setRescheduleServiceDate2INGMT(Date rescheduleServiceDate2INGMT) {
		this.rescheduleServiceDate2INGMT = rescheduleServiceDate2INGMT;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Date getRescheduleServiceDate1() {
		return rescheduleServiceDate1;
	}
	public void setRescheduleServiceDate1(Date rescheduleServiceDate1) {
		this.rescheduleServiceDate1 = rescheduleServiceDate1;
	}
	public Date getRescheduleServiceDate2() {
		return rescheduleServiceDate2;
	}
	public void setRescheduleServiceDate2(Date rescheduleServiceDate2) {
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
	public Integer getRescheduleType() {
		return rescheduleType;
	}
	public void setRescheduleType(Integer rescheduleType) {
		this.rescheduleType = rescheduleType;
	}
	public String getServiceLocnTimeZone() {
		return serviceLocnTimeZone;
	}
	public void setServiceLocnTimeZone(String serviceLocnTimeZone) {
		this.serviceLocnTimeZone = serviceLocnTimeZone;
	}
	public Timestamp getRescheduleServiceStartDate() {
		return rescheduleServiceStartDate;
	}
	public void setRescheduleServiceStartDate(Timestamp rescheduleServiceStartDate) {
		this.rescheduleServiceStartDate = rescheduleServiceStartDate;
	}
	public Timestamp getRescheduleServiceEndDate() {
		return rescheduleServiceEndDate;
	}
	public void setRescheduleServiceEndDate(Timestamp rescheduleServiceEndDate) {
		this.rescheduleServiceEndDate = rescheduleServiceEndDate;
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
	public String getRescheduleRespondType() {
		return rescheduleRespondType;
	}
	public void setRescheduleRespondType(String rescheduleRespondType) {
		this.rescheduleRespondType = rescheduleRespondType;
	}
	public ResultsCode getValidationCode() {
		return validationCode;
	}
	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public String getReasonCodeDescription() {
		return reasonCodeDescription;
	}
	public void setReasonCodeDescription(String reasonCodeDescription) {
		this.reasonCodeDescription = reasonCodeDescription;
	}
	public boolean isValidSignal() {
		return isValidSignal;
	}
	public void setValidSignal(boolean isValidSignal) {
		this.isValidSignal = isValidSignal;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	public String getServiceDateTime1() {
		return serviceDateTime1;
	}
	public void setServiceDateTime1(String serviceDateTime1) {
		this.serviceDateTime1 = serviceDateTime1;
	}
	public String getServiceDateTime2() {
		return serviceDateTime2;
	}
	public void setServiceDateTime2(String serviceDateTime2) {
		this.serviceDateTime2 = serviceDateTime2;
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
	public Integer getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	    
}