package com.newco.marketplace.leadoutboundnotification.vo;

import java.util.Date;

public class LeadOutBoundNotificationVO {
	private String leadId;
	private String xml;
	private Integer retryCount;
	private String response;
	private String status ;
	private String exception;
	private Date createdDate;
	private Date modifiedDate;
	private Integer emailInd;
	
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}
	public Integer getEmailInd() {
		return emailInd;
	}
	public void setEmailInd(Integer emailInd) {
		this.emailInd = emailInd;
	}
}
