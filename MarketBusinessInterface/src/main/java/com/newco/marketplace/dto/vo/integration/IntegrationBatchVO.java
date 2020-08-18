package com.newco.marketplace.dto.vo.integration;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class IntegrationBatchVO extends SerializableBaseVO {

	private static final long serialVersionUID = 1L;
	
	Long batchId;
	Integer integrationId;
	String fileName;
	Integer statusId;
	Date createdDate;
	Long buyerId;
	
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public Integer getIntegrationId() {
		return integrationId;
	}
	public void setIntegrationId(Integer integrationId) {
		this.integrationId = integrationId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
