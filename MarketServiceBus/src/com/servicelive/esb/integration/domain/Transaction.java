package com.servicelive.esb.integration.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Transaction {
	private long transactionId;
	private Long batchId;
	private TransactionType transactionType;
	private String externalOrderNumber;
	private String serviceLiveOrderId;
	private Date processAfter;
	private ProcessingStatus processingStatus;
	private Date createdOn;
	private Date claimedOn;
	private String claimedBy;
	private Long buyerResourceId;
	
	public Transaction() {}
	
	public Transaction(long transactionId, Long batchId,
			TransactionType transactionType, String externalOrderNumber,
			String serviceLiveOrderId, Date processAfter,
			ProcessingStatus processingStatus, Date createdOn, Date claimedOn,
			String claimedBy) {
		super();
		this.transactionId = transactionId;
		this.batchId = batchId;
		this.transactionType = transactionType;
		this.externalOrderNumber = externalOrderNumber;
		this.serviceLiveOrderId = serviceLiveOrderId;
		this.processAfter = processAfter;
		this.processingStatus = processingStatus;
		this.createdOn = createdOn;
		this.claimedOn = claimedOn;
		this.claimedBy = claimedBy;
	}

	public Long getBatchId() {
		return batchId;
	}

	public String getClaimedBy() {
		return claimedBy;
	}

	public Date getClaimedOn() {
		return claimedOn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public String getExternalOrderNumber() {
		return externalOrderNumber;
	}

	public Date getProcessAfter() {
		return processAfter;
	}

	public ProcessingStatus getProcessingStatus() {
		return processingStatus;
	}

	public String getServiceLiveOrderId() {
		return serviceLiveOrderId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public void setClaimedBy(String claimedBy) {
		this.claimedBy = claimedBy;
	}

	public void setClaimedOn(Date claimedOn) {
		this.claimedOn = claimedOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setExternalOrderNumber(String externalOrderNumber) {
		this.externalOrderNumber = externalOrderNumber;
	}

	public void setProcessAfter(Date processAfter) {
		this.processAfter = processAfter;
	}

	public void setProcessingStatus(ProcessingStatus processingStatus) {
		this.processingStatus = processingStatus;
	}
	public void setServiceLiveOrderId(String serviceLiveOrderId) {
		this.serviceLiveOrderId = serviceLiveOrderId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public void setBuyerResourceId(Long buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}

	public Long getBuyerResourceId() {
		return buyerResourceId;
	}
}
