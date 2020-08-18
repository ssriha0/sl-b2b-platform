package com.newco.marketplace.dto.vo.integration;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class IntegrationTransactionVO extends SerializableBaseVO {

	private static final long serialVersionUID = 1L;
	
	Long transactionId;
	Long batchId;
	Integer transactionTypeId;
	String externalOrderNumber;
	Date createdDate;
	Integer statusId;
	String soId;
	Long buyerResourceId;
	private String exception;
	
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
	public Integer getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(Integer transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public String getExternalOrderNumber() {
		return externalOrderNumber;
	}
	public void setExternalOrderNumber(String externalOrderNumber) {
		this.externalOrderNumber = externalOrderNumber;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Long getBuyerResourceId() {
		return buyerResourceId;
	}
	public void setBuyerResourceId(Long buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getException() {
		return exception;
	}

}
