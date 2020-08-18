package com.servicelive.spn.auditor.vo;

import java.util.Date;

public class SPNServiceProviderStateOverride {
	private Integer spnId;
	private Integer serviceProviderId;
	private Date createdDate = new Date();
	private Date modifiedDate = new Date();
	private Boolean activeInd;
	private String previousNetworkStatus;
	private String overridedNetworkStatus;
	private String createdBy;
	private Date validity_date;
	private String statusOverrideComments;
	private Integer statusOverrideReasonId;
	private Boolean noExpirationDateInd;
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Integer getServiceProviderId() {
		return serviceProviderId;
	}
	public void setServiceProviderId(Integer serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Boolean getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}
	public String getPreviousNetworkStatus() {
		return previousNetworkStatus;
	}
	public void setPreviousNetworkStatus(String previousNetworkStatus) {
		this.previousNetworkStatus = previousNetworkStatus;
	}
	public String getOverridedNetworkStatus() {
		return overridedNetworkStatus;
	}
	public void setOverridedNetworkStatus(String overridedNetworkStatus) {
		this.overridedNetworkStatus = overridedNetworkStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getValidity_date() {
		return validity_date;
	}
	public void setValidity_date(Date validity_date) {
		this.validity_date = validity_date;
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
	public Boolean getNoExpirationDateInd() {
		return noExpirationDateInd;
	}
	public void setNoExpirationDateInd(Boolean noExpirationDateInd) {
		this.noExpirationDateInd = noExpirationDateInd;
	}
}
