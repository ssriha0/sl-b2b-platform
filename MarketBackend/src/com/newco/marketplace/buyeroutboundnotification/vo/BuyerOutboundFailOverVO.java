package com.newco.marketplace.buyeroutboundnotification.vo;

import java.util.Date;


public class BuyerOutboundFailOverVO {
	private String seqNO;
	private String soId;
	private Integer serviceId;
	private String xml;
	private Integer noOfRetries;
	private boolean activeInd;
	private String response;
	private String status ;
	private String exception;
	private Date createdDate;
	
	public String getSeqNO() {
		return seqNO;
	}
	public void setSeqNO(String seqNO) {
		this.seqNO = seqNO;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
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
	public boolean isActiveInd() {
		return activeInd;
	}
	public void setActiveInd(boolean activeInd) {
		this.activeInd = activeInd;
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
}
