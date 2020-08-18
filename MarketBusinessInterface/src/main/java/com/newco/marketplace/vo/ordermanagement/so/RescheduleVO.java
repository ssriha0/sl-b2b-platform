package com.newco.marketplace.vo.ordermanagement.so;

import java.sql.Timestamp;
import java.util.Date;

import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.sears.os.vo.SerializableBaseVO;

public class RescheduleVO extends SerializableBaseVO{
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
	
	
	//changes
	
	private Date DBServiceDate1;
	private Date DBServiceDate2;
	private Integer urlRoleId;
	private String firmId;
	
	
	
	
	
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	public Integer getUrlRoleId() {
		return urlRoleId;
	}
	public void setUrlRoleId(Integer urlRoleId) {
		this.urlRoleId = urlRoleId;
	}
	public Date getDBServiceDate1() {
		return DBServiceDate1;
	}
	public void setDBServiceDate1(Date dBServiceDate1) {
		DBServiceDate1 = dBServiceDate1;
	}
	public Date getDBServiceDate2() {
		return DBServiceDate2;
	}
	public void setDBServiceDate2(Date dBServiceDate2) {
		DBServiceDate2 = dBServiceDate2;
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
	    
}