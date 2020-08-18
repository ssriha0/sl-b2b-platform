package com.servicelive.orderfulfillment.vo;

import java.util.Date;

public class OutboundNotificationVO {

	private String seqNo;
	private String soId;
	private Integer serviceId;
	private String xml;
	private Integer noOfRetries;
	private Integer activeInd;
	private Date createdDate;
	private Date modifiedDate;
	private String status;
	private String exception;
	private String response;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Integer getNoOfRetries() {
		return noOfRetries;
	}

	public void setNoOfRetries(Integer noOfRetries) {
		this.noOfRetries = noOfRetries;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
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

	public Integer getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(Integer activeInd) {
		this.activeInd = activeInd;
	}

}