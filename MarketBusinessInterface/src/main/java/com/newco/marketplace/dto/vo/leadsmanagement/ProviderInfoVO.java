package com.newco.marketplace.dto.vo.leadsmanagement;


import java.io.Serializable;
import java.util.Date;

public class ProviderInfoVO implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private String providerId;
	private String fullName;
	private String address;
	
	private String phoneNo;
	private String mobileNo;
	
	private String email;
	private String provider;
	private String providerPhoneno;
	private String appoinmentScheduled;
	private String firmStatus;
	private String businessName;
	private String resourceMobileNo;
	
	
	private String resourceFullName;
	private String resourcePhoneNo;
	private String resourceScheduledTime;
	private String serviceDate;
	
	private String completedTime;
	private String leadPrice;
	private String visits;
	private String cancelledOn;
	private String cancelledBy;
	private String cancelledByFirstName;
	private String cancelledByLastName;
	
	private String reason;
	private String leadId;
	private String resourceId;
    private String modifiedDate;
    private Double score;
    private int starRating;
    private String firmBusinessName;
	
	
	
    public String getResourceMobileNo() {
		return resourceMobileNo;
	}
	public void setResourceMobileNo(String resourceMobileNo) {
		this.resourceMobileNo = resourceMobileNo;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
    public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
    public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public int getStarRating() {
		return starRating;
	}
	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}
	
	public String getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}
	public String getLeadPrice() {
		return leadPrice;
	}
	public void setLeadPrice(String leadPrice) {
		this.leadPrice = leadPrice;
	}
	public String getVisits() {
		return visits;
	}
	public void setVisits(String visits) {
		this.visits = visits;
	}
	public String getCancelledOn() {
		return cancelledOn;
	}
	public void setCancelledOn(String cancelledOn) {
		this.cancelledOn = cancelledOn;
	}
	public String getCancelledBy() {
		return cancelledBy;
	}
	public void setCancelledBy(String cancelledBy) {
		this.cancelledBy = cancelledBy;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResourceFullName() {
		return resourceFullName;
	}
	public void setResourceFullName(String resourceFullName) {
		this.resourceFullName = resourceFullName;
	}
	public String getResourcePhoneNo() {
		return resourcePhoneNo;
	}
	public void setResourcePhoneNo(String resourcePhoneNo) {
		this.resourcePhoneNo = resourcePhoneNo;
	}
	
	public String getResourceScheduledTime() {
		return resourceScheduledTime;
	}
	public void setResourceScheduledTime(String resourceScheduledTime) {
		this.resourceScheduledTime = resourceScheduledTime;
	}
	
	public String getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}
	public String getFirmStatus() {
		return firmStatus;
	}
	public void setFirmStatus(String firmStatus) {
		this.firmStatus = firmStatus;
	}
	
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getProviderPhoneno() {
		return providerPhoneno;
	}
	public void setProviderPhoneno(String providerPhoneno) {
		this.providerPhoneno = providerPhoneno;
	}
	public String getAppoinmentScheduled() {
		return appoinmentScheduled;
	}
	public void setAppoinmentScheduled(String appoinmentScheduled) {
		this.appoinmentScheduled = appoinmentScheduled;
	}
	public String getFirmBusinessName() {
		return firmBusinessName;
	}
	public void setFirmBusinessName(String firmBusinessName) {
		this.firmBusinessName = firmBusinessName;
	}
	public String getCancelledByFirstName() {
		return cancelledByFirstName;
	}
	public void setCancelledByFirstName(String cancelledByFirstName) {
		this.cancelledByFirstName = cancelledByFirstName;
	}
	public String getCancelledByLastName() {
		return cancelledByLastName;
	}
	public void setCancelledByLastName(String cancelledByLastName) {
		this.cancelledByLastName = cancelledByLastName;
	}
	
	
	
	

}
