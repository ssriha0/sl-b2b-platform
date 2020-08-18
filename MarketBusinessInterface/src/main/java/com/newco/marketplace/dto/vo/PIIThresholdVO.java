package com.newco.marketplace.dto.vo;

import java.sql.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class PIIThresholdVO extends CommonVO {

	private static final long serialVersionUID = 1L;
	
	private Integer thresholdId;
	private Double thresholdValue;
	private Integer role;
	private Boolean thresholdIndex;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	public Integer getThresholdId() {
		return thresholdId;
	}
	public void setThresholdId(Integer thresholdId) {
		this.thresholdId = thresholdId;
	}
	public Double getThresholdValue() {
		return thresholdValue;
	}
	public void setThresholdValue(Double thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Boolean getThresholdIndex() {
		return thresholdIndex;
	}
	public void setThresholdIndex(Boolean thresholdIndex) {
		this.thresholdIndex = thresholdIndex;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	

}
