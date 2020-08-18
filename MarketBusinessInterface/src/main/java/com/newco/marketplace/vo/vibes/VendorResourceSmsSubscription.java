package com.newco.marketplace.vo.vibes;

import java.util.Date;


//R16_1: SL-18979: New VO class to store response details for vendor resource sms subscription for Vibes
public class VendorResourceSmsSubscription {

	private String personId;
	private String participationDate;
	private String expireDate;
	private String status;
	private String resourceId;
	private Date createdDate;
	private Date modifiedDate;
	private Integer vendorResourceSmsSubscriptionId;
	private String smsNumber;
	private Integer optInInd;
	private Date optInDate;
	private Date optOutDate;
	private String createdBy;
	private String modifiedBy;
	
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getParticipationDate() {
		return participationDate;
	}
	public void setParticipationDate(String participationDate) {
		this.participationDate = participationDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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
	public Integer getVendorResourceSmsSubscriptionId() {
		return vendorResourceSmsSubscriptionId;
	}
	public void setVendorResourceSmsSubscriptionId(
			Integer vendorResourceSmsSubscriptionId) {
		this.vendorResourceSmsSubscriptionId = vendorResourceSmsSubscriptionId;
	}
	public String getSmsNumber() {
		return smsNumber;
	}
	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}
	public Integer getOptInInd() {
		return optInInd;
	}
	public void setOptInInd(Integer optInInd) {
		this.optInInd = optInInd;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
}
