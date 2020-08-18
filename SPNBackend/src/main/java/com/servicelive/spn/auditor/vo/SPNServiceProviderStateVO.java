package com.servicelive.spn.auditor.vo;

import java.util.Date;

public class SPNServiceProviderStateVO {
	private Integer spnId;
	private Integer statusUpdateActionId;
	private String providerWfState;
	private String performanceLevelChangeComments;
	private Date createdDate = new Date();
	private String modifiedBy;
	private Date modifiedDate = new Date();
	private Integer performanceLevel;
	private Integer statusOverrideReasonId;
	private String statusOverrideComments;
	private Integer serviceProviderId;
	private Double performanceScore;
	private Boolean overrideInd;
	
	public Double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}
	public String getStatusOverrideComments() {
		return statusOverrideComments;
	}
	public void setStatusOverrideComments(String statusOverrideComments) {
		this.statusOverrideComments = statusOverrideComments;
	}
	
	
	public Integer getStatusOverrideReasonId() {
		return statusOverrideReasonId;
	}
	public void setStatusOverrideReasonId(Integer statusOverrideReasonId) {
		this.statusOverrideReasonId = statusOverrideReasonId;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Integer getStatusUpdateActionId() {
		return statusUpdateActionId;
	}
	public void setStatusUpdateActionId(Integer statusUpdateActionId) {
		this.statusUpdateActionId = statusUpdateActionId;
	}
	
	public String getProviderWfState() {
		return providerWfState;
	}
	public void setProviderWfState(String providerWfState) {
		this.providerWfState = providerWfState;
	}
	public String getPerformanceLevelChangeComments() {
		return performanceLevelChangeComments;
	}
	public void setPerformanceLevelChangeComments(
			String performanceLevelChangeComments) {
		this.performanceLevelChangeComments = performanceLevelChangeComments;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public Integer getPerformanceLevel() {
		return performanceLevel;
	}
	public void setPerformanceLevel(Integer performanceLevel) {
		this.performanceLevel = performanceLevel;
	}
	public Integer getServiceProviderId() {
		return serviceProviderId;
	}
	public void setServiceProviderId(Integer serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	public Boolean getOverrideInd() {
		return overrideInd;
	}
	public void setOverrideInd(Boolean overrideInd) {
		this.overrideInd = overrideInd;
	}
	
	

}
