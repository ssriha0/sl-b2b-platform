package com.newco.marketplace.inhomeoutboundnotification.vo;

import java.sql.Timestamp;


public class InHomeRescheduleVO {
	private String soId;
	private String subjMessage;
	private Integer resourceId;
	private Integer empId;
	private Integer buyerId;
	private Integer vendorId;
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	private String startTime;
	private String endTime;
	private Timestamp rescheduleServiceDate1;
	private Timestamp rescheduleServiceDate2;
	private String rescheduleDate1;
	private String rescheduleDate2;
	private String rescheduleStartTime;
	private String rescheduleEndTime;
	private Timestamp buyerRescheduleServiceDate1;
	private Timestamp buyerRescheduleServiceDate2;
	private String buyerRescheduleStartTime;
	private String buyerRescheduleEndTime;
	private String timeZone;
	private int releasIndicator = 0;
	private Integer serviceDateType;
	private Integer buyerPreferredDateType;
	private Boolean isNPSMessageRequired = false;
	private String rescheduleMessage = "";
	private boolean releaseFlag = false;
	
	private String revisitReason;
	
	
	public String getRevisitReason() {
		return revisitReason;
	}
	public void setRevisitReason(String revisitReason) {
		this.revisitReason = revisitReason;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getSubjMessage() {
		return subjMessage;
	}
	public void setSubjMessage(String subjMessage) {
		this.subjMessage = subjMessage;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
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
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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
	public Timestamp getRescheduleServiceDate1() {
		return rescheduleServiceDate1;
	}
	public void setRescheduleServiceDate1(Timestamp rescheduleServiceDate1) {
		this.rescheduleServiceDate1 = rescheduleServiceDate1;
	}
	public Timestamp getRescheduleServiceDate2() {
		return rescheduleServiceDate2;
	}
	public void setRescheduleServiceDate2(Timestamp rescheduleServiceDate2) {
		this.rescheduleServiceDate2 = rescheduleServiceDate2;
	}
	public String getRescheduleStartTime() {
		return rescheduleStartTime;
	}
	public void setRescheduleStartTime(String rescheduleStartTime) {
		this.rescheduleStartTime = rescheduleStartTime;
	}
	public String getRescheduleEndTime() {
		return rescheduleEndTime;
	}
	public void setRescheduleEndTime(String rescheduleEndTime) {
		this.rescheduleEndTime = rescheduleEndTime;
	}
	public Timestamp getBuyerRescheduleServiceDate1() {
		return buyerRescheduleServiceDate1;
	}
	public void setBuyerRescheduleServiceDate1(Timestamp buyerRescheduleServiceDate1) {
		this.buyerRescheduleServiceDate1 = buyerRescheduleServiceDate1;
	}
	public Timestamp getBuyerRescheduleServiceDate2() {
		return buyerRescheduleServiceDate2;
	}
	public void setBuyerRescheduleServiceDate2(Timestamp buyerRescheduleServiceDate2) {
		this.buyerRescheduleServiceDate2 = buyerRescheduleServiceDate2;
	}
	public String getBuyerRescheduleStartTime() {
		return buyerRescheduleStartTime;
	}
	public void setBuyerRescheduleStartTime(String buyerRescheduleStartTime) {
		this.buyerRescheduleStartTime = buyerRescheduleStartTime;
	}
	public String getBuyerRescheduleEndTime() {
		return buyerRescheduleEndTime;
	}
	public void setBuyerRescheduleEndTime(String buyerRescheduleEndTime) {
		this.buyerRescheduleEndTime = buyerRescheduleEndTime;
	}
	public int getReleasIndicator() {
		return releasIndicator;
	}
	public void setReleasIndicator(int releasIndicator) {
		this.releasIndicator = releasIndicator;
	}
	public Integer getServiceDateType() {
		return serviceDateType;
	}
	public void setServiceDateType(Integer serviceDateType) {
		this.serviceDateType = serviceDateType;
	}
	public Integer getBuyerPreferredDateType() {
		return buyerPreferredDateType;
	}
	public void setBuyerPreferredDateType(Integer buyerPreferredDateType) {
		this.buyerPreferredDateType = buyerPreferredDateType;
	}
	public String getRescheduleDate1() {
		return rescheduleDate1;
	}
	public void setRescheduleDate1(String rescheduleDate1) {
		this.rescheduleDate1 = rescheduleDate1;
	}
	public String getRescheduleDate2() {
		return rescheduleDate2;
	}
	public void setRescheduleDate2(String rescheduleDate2) {
		this.rescheduleDate2 = rescheduleDate2;
	}
	public Boolean getIsNPSMessageRequired() {
		return isNPSMessageRequired;
	}
	public void setIsNPSMessageRequired(Boolean isNPSMessageRequired) {
		this.isNPSMessageRequired = isNPSMessageRequired;
	}
	public String getRescheduleMessage() {
		return rescheduleMessage;
	}
	public void setRescheduleMessage(String rescheduleMessage) {
		this.rescheduleMessage = rescheduleMessage;
	}
	/**
	 * @return the releaseFlag
	 */
	public boolean isReleaseFlag() {
		return releaseFlag;
	}
	/**
	 * @param releaseFlag the releaseFlag to set
	 */
	public void setReleaseFlag(boolean releaseFlag) {
		this.releaseFlag = releaseFlag;
	}
	
}
