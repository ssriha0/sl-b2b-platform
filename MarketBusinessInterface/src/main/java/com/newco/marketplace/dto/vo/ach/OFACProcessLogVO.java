package com.newco.marketplace.dto.vo.ach;

import java.sql.Timestamp;

public class OFACProcessLogVO {
	
	private Timestamp periodStartDate;
	private Timestamp periodEndDate;
	private Timestamp processedDate;
	private String generatedFileName;
	private boolean processSuccessful;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Long transactionCount;
	
	public Timestamp getPeriodStartDate() {
		return periodStartDate;
	}
	public void setPeriodStartDate(Timestamp periodStartDate) {
		this.periodStartDate = periodStartDate;
	}
	public Timestamp getPeriodEndDate() {
		return periodEndDate;
	}
	public void setPeriodEndDate(Timestamp periodEndDate) {
		this.periodEndDate = periodEndDate;
	}
	public Timestamp getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(Timestamp processedDate) {
		this.processedDate = processedDate;
	}
	public String getGeneratedFileName() {
		return generatedFileName;
	}
	public void setGeneratedFileName(String generatedFileName) {
		this.generatedFileName = generatedFileName;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Long getTransactionCount() {
		return transactionCount;
	}
	public void setTransactionCount(Long transactionCount) {
		this.transactionCount = transactionCount;
	}
	public boolean isProcessSuccessful() {
		return processSuccessful;
	}
	public void setProcessSuccessful(boolean processSuccessful) {
		this.processSuccessful = processSuccessful;
	}
}
