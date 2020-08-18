package com.servicelive.esb.integration.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Batch implements java.io.Serializable {

	private static final long serialVersionUID = 4089763041230482620L;
	
	private long batchId;
	
	private String fileName;
	private Date createdOn;
	private Long integrationId;
	private ProcessingStatus processingStatus;
	private String exception;

	public Batch() {}
	public Batch(long batchId, Long integrationId, String fileName,
			Date createdOn, ProcessingStatus processingStatus, String exception) {
		this.batchId = batchId;
		this.integrationId = integrationId;
		this.fileName = fileName;
		this.createdOn = createdOn;
		this.processingStatus = processingStatus;
		this.exception = exception;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public long getBatchId() {
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
	public void setProcessingStatus(ProcessingStatus processingStatus) {
		this.processingStatus = processingStatus;
	}
	public ProcessingStatus getProcessingStatus() {
		return processingStatus;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getException() {
		return exception;
	}
	public void setIntegrationId(Long integrationId) {
		this.integrationId = integrationId;
	}
	public Long getIntegrationId() {
		return integrationId;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
