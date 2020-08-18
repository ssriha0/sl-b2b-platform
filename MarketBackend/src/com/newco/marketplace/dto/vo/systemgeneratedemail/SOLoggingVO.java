package com.newco.marketplace.dto.vo.systemgeneratedemail;

import java.sql.Timestamp;

public class SOLoggingVO{
	
	private Long soLoggingId; 
    private String serviceOrderNo;
    private Integer actionId;
    private Integer buyerId; 
	private String soTitle; 
	private Integer wfStateId; 
	private Integer soSubStatusId; 
	private String serviceDateType; 
	private Timestamp serviceDate1; 
	private Timestamp serviceDate2; 
	private String serviceDateStartTime; 
	private String serviceDateEndTime;
	private Integer acceptedVendorId;
	private String timeZone;
	private String loggingChangeComment;
   
	public Long getSoLoggingId() {
		return soLoggingId;
	}

	public void setSoLoggingId(Long soLoggingId) {
		this.soLoggingId = soLoggingId;
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}


    public String getServiceOrderNo() {
        return serviceOrderNo;
    }

    public void setServiceOrderNo(String serviceOrderNo) {
        this.serviceOrderNo = serviceOrderNo;
    }
    
    public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getSoTitle() {
		return soTitle;
	}

	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}

	public Integer getWfStateId() {
		return wfStateId;
	}

	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}

	public Integer getSoSubStatusId() {
		return soSubStatusId;
	}

	public void setSoSubStatusId(Integer soSubStatusId) {
		this.soSubStatusId = soSubStatusId;
	}

	public String getServiceDateType() {
		return serviceDateType;
	}

	public void setServiceDateType(String serviceDateType) {
		this.serviceDateType = serviceDateType;
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

	public String getServiceDateStartTime() {
		return serviceDateStartTime;
	}

	public void setServiceDateStartTime(String serviceDateStartTime) {
		this.serviceDateStartTime = serviceDateStartTime;
	}

	public String getServiceDateEndTime() {
		return serviceDateEndTime;
	}

	public void setServiceDateEndTime(String serviceDateEndTime) {
		this.serviceDateEndTime = serviceDateEndTime;
	}

	public Integer getAcceptedVendorId() {
		return acceptedVendorId;
	}

	public void setAcceptedVendorId(Integer acceptedVendorId) {
		this.acceptedVendorId = acceptedVendorId;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	@Override
	public String toString() {
		return "SOLoggingVO [soLoggingId=" + soLoggingId + ", serviceOrderNo=" + serviceOrderNo + ", actionId="
				+ actionId + ", buyerId=" + buyerId + ", wfStateId=" + wfStateId + ", soSubStatusId=" + soSubStatusId
				+ "]";
	}

	public String getLoggingChangeComment() {
		return loggingChangeComment;
	}

	public void setLoggingChangeComment(String loggingChangeComment) {
		this.loggingChangeComment = loggingChangeComment;
	}   
    
}
