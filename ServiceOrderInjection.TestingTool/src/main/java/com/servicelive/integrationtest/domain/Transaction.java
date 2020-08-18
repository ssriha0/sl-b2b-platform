package com.servicelive.integrationtest.domain;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

	private static final long serialVersionUID = -7911078806927949344L;
	
	private Long transactionId;
	private Long batchId;
	private String typeName;
	private String externalOrderNumber;
	private Date createdOn;
	private Date processAfter;
	private Date claimedOn;
	private String claimedBy;
	private String statusName;
	private String exception;
	private String serviceLiveOrderId;
	
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getExternalOrderNumber() {
		return externalOrderNumber;
	}
	public void setExternalOrderNumber(String externalOrderNumber) {
		this.externalOrderNumber = externalOrderNumber;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getProcessAfter() {
		return processAfter;
	}
	public void setProcessAfter(Date processAfter) {
		this.processAfter = processAfter;
	}
	public Date getClaimedOn() {
		return claimedOn;
	}
	public void setClaimedOn(Date claimedOn) {
		this.claimedOn = claimedOn;
	}
	public String getClaimedBy() {
		return claimedBy;
	}
	public void setClaimedBy(String claimedBy) {
		this.claimedBy = claimedBy;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public void setServiceLiveOrderId(String serviceLiveOrderId) {
		this.serviceLiveOrderId = serviceLiveOrderId;
	}
	public String getServiceLiveOrderId() {
		return serviceLiveOrderId;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStatusName() {
		return statusName;
	}

}
