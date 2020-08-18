package com.newco.marketplace.inhomeautoclosenotification.vo;

import java.util.Date;

public class InHomeAutoCloseVO {
	private Integer autoCloseId;
	private String soId;
	private String status;
	private Integer noOfRetries;
	private Integer emailInd;
	private Date createdDate;
	private Date modifiedDate;
	public Integer getAutoCloseId() {
		return autoCloseId;
	}
	public void setAutoCloseId(Integer autoCloseId) {
		this.autoCloseId = autoCloseId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getNoOfRetries() {
		return noOfRetries;
	}
	public void setNoOfRetries(Integer noOfRetries) {
		this.noOfRetries = noOfRetries;
	}
	public Integer getEmailInd() {
		return emailInd;
	}
	public void setEmailInd(Integer emailInd) {
		this.emailInd = emailInd;
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
	
	
	

}
