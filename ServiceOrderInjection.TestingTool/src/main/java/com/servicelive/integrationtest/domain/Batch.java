package com.servicelive.integrationtest.domain;

import java.io.Serializable;
import java.util.Date;

public class Batch implements Serializable {

	private static final long serialVersionUID = 4089763041230482620L;
	
	private Long batchId;
	private String fileName;
	private Date createdOn;
	private String statusName;
	private String exception;

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getException() {
		return exception;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStatusName() {
		return statusName;
	}

}
