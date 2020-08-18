package com.newco.marketplace.vo.mobile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("serviceOrder")
@XmlRootElement(name = "serviceOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderSearchSO {

	@XStreamAlias("soId")
	private String soId;

	@XStreamAlias("soTitle")
	private String soTitle;

	@XStreamAlias("soStatus")
	private String soStatus;

	@XStreamAlias("soSubStatus")
	private String soSubStatus;

	@XStreamAlias("serviceWindowStartTime")
	private String serviceWindowStartTime;

	@XStreamAlias("serviceWindowEndTime")
	private String serviceWindowEndTime;

	@XStreamAlias("serviceStartDate")
	private String serviceStartDate;

	@XStreamAlias("serviceEndDate")
	private String serviceEndDate;

	@XStreamAlias("timeZone")
	private String timeZone;

	@XStreamAlias("customerFirstName")
	private String customerFirstName;

	@XStreamAlias("customerLastName")
	private String customerLastName;

	@XStreamAlias("streetAddress1")
	private String streetAddress1;

	@XStreamAlias("streetAddress2")
	private String streetAddress2;

	@XStreamAlias("city")
	private String city;

	@XStreamAlias("state")
	private String state;

	@XStreamAlias("zip")
	private String zip;

	@XStreamAlias("buyerId")
	private Integer buyerId;

	@XStreamAlias("buyerName")
	private String buyerName;

	@XStreamAlias("buyerLogo")
	private String buyerLogo;

	@XStreamAlias("merchStatus")
	private String merchStatus;

	@XStreamAlias("scheduleStatus")
	private String scheduleStatus;

	@XStreamAlias("serverDateTime")
	private String serverDateTime;

	@XStreamAlias("serviceTime")
	private String serviceTime;

	@XStreamAlias("eta")
	private String eta;

	//SL-20838
	@XStreamAlias("followupFlag")
	private Boolean followupFlag;
	
	@XStreamAlias("acceptedResource")
	private String acceptedResource;
	
	@XStreamAlias("acceptedVendor")
	private String acceptedVendor;
	
	@XStreamAlias("assignmentType")
	private String assignmentType;
	
	@XStreamAlias("acceptedVendorId")
	private Integer acceptedVendorId;
	
	@XStreamAlias("acceptedResourceId")
	private Integer acceptedResourceId;
	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getSoTitle() {
		return soTitle;
	}

	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}

	public String getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}

	public String getSoSubStatus() {
		return soSubStatus;
	}

	public void setSoSubStatus(String soSubStatus) {
		this.soSubStatus = soSubStatus;
	}

	public String getServiceWindowStartTime() {
		return serviceWindowStartTime;
	}

	public void setServiceWindowStartTime(String serviceWindowStartTime) {
		this.serviceWindowStartTime = serviceWindowStartTime;
	}

	public String getServiceWindowEndTime() {
		return serviceWindowEndTime;
	}

	public void setServiceWindowEndTime(String serviceWindowEndTime) {
		this.serviceWindowEndTime = serviceWindowEndTime;
	}

	public String getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(String serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public String getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(String serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getStreetAddress1() {
		return streetAddress1;
	}

	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}

	public String getStreetAddress2() {
		return streetAddress2;
	}

	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerLogo() {
		return buyerLogo;
	}

	public void setBuyerLogo(String buyerLogo) {
		this.buyerLogo = buyerLogo;
	}

	public String getMerchStatus() {
		return merchStatus;
	}

	public void setMerchStatus(String merchStatus) {
		this.merchStatus = merchStatus;
	}

	public String getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public String getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(String serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public Boolean getFollowupFlag() {
		return followupFlag;
	}

	public void setFollowupFlag(Boolean followupFlag) {
		this.followupFlag = followupFlag;
	}

	public String getAcceptedResource() {
		return acceptedResource;
	}

	public void setAcceptedResource(String acceptedResource) {
		this.acceptedResource = acceptedResource;
	}

	public String getAcceptedVendor() {
		return acceptedVendor;
	}

	public void setAcceptedVendor(String acceptedVendor) {
		this.acceptedVendor = acceptedVendor;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public Integer getAcceptedVendorId() {
		return acceptedVendorId;
	}

	public void setAcceptedVendorId(Integer acceptedVendorId) {
		this.acceptedVendorId = acceptedVendorId;
	}

	public Integer getAcceptedResourceId() {
		return acceptedResourceId;
	}

	public void setAcceptedResourceId(Integer acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}
	
}
