package com.newco.marketplace.smsdatasynch.vo;

import java.util.Date;

/**
 * @author Infosys
 * SL-18979
 * This is the vo class to hold 
 * the subscription details of the participants
 */
public class SMSDataSynchVO {

	
	private Integer subscriptionId;
	private Integer resourceId;
	private String personId;
	private Date participationDate;
	private Date expireDate;
	private String status;
	private String optInStatus;
	private boolean optInInd;
	private Date createdDate;
	private Date modifiedDate;
	private Date optInDate;
	private Date optOutDate;
	private String mdn;
	private String subscriptionEvent;
	//Added to handle DataMigration scenarios
	private Integer vendorId;
	private Integer migrationId;
	private String firstName;
	private String lastName;
	private int statusCode;
	private String statusText;
	private String createdBy;
	private String modifiedBy;
	private String participationDateString;
	private String expireDateString;
	private Boolean optInIndicator;
	private String errorString;
	private Integer wfStateId;
	

	
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	public Boolean getOptInIndicator() {
		return optInIndicator;
	}
	public void setOptInIndicator(Boolean optInIndicator) {
		this.optInIndicator = optInIndicator;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSubscriptionEvent() {
		return subscriptionEvent;
	}
	public void setSubscriptionEvent(String subscriptionEvent) {
		this.subscriptionEvent = subscriptionEvent;
	}
	public Integer getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public Date getParticipationDate() {
		return participationDate;
	}
	public void setParticipationDate(Date participationDate) {
		this.participationDate = participationDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOptInStatus() {
		return optInStatus;
	}
	public void setOptInStatus(String optInStatus) {
		this.optInStatus = optInStatus;
	}
	public boolean isOptInInd() {
		return optInInd;
	}
	public void setOptInInd(boolean optInInd) {
		this.optInInd = optInInd;
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
	
	public Date getOptInDate() {
		return optInDate;
	}
	public void setOptInDate(Date optInDate) {
		this.optInDate = optInDate;
	}
	public Date getOptOutDate() {
		return optOutDate;
	}
	public void setOptOutDate(Date optOutDate) {
		this.optOutDate = optOutDate;
	}
	public String getMdn() {
		return mdn;
	}
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getMigrationId() {
		return migrationId;
	}
	public void setMigrationId(Integer migrationId) {
		this.migrationId = migrationId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getParticipationDateString() {
		return participationDateString;
	}
	public void setParticipationDateString(String participationDateString) {
		this.participationDateString = participationDateString;
	}
	public String getExpireDateString() {
		return expireDateString;
	}
	public void setExpireDateString(String expireDateString) {
		this.expireDateString = expireDateString;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getErrorString() {
		return errorString;
	}
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	
}
