package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderSurveyResponseVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8415079933600043223L;
	private String soId;
	private Integer responseHdrId;
	private Integer entityTypeId;
	private Integer entityId;
	private Timestamp createdDate;
	
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public Integer getResponseHdrId() {
		return responseHdrId;
	}
	public void setResponseHdrId(Integer responseHdrId) {
		this.responseHdrId = responseHdrId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	} 
}
