package com.newco.marketplace.vo.mobile;

import java.sql.Timestamp;

public class ProviderSOSearchVO {
	private Integer totalCountOfSo;
	private String soTitle;
	private String soId;
	private String soStatus;
	private String soSubStatus;
	private String serviceLocationTimeZone;
	private Integer dateType;
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	private String serviceStartDate;
	private String serviceEndDate;
	private String serviceTime1;
	private String serviceTime2;
	private String custFirstName;
	private String custLastName;
	private String city;
	private String state;
	private String zip;
	private String address1;
	private String address2;
	private Integer buyerId;
	private String buyerName;
	private String buyerLogo;
	private String merchStatus;
	private String scheduleStatus;
	private String eta;
	private Timestamp serverDateTime;
	private String acceptedResource;
	private String acceptedVendor;
	private String assignmentType;
	private Integer acceptedVendorId;
	private Integer acceptedResourceId;
	private Boolean followupFlag;
	

	public String getSoTitle() {
		return soTitle;
	}
	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public Timestamp getServiceDate1() {
		return serviceDate1;
	}
	public void setServiceDate1(Timestamp serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}
	public Timestamp getServiceDate2() {
		return serviceDate2;
	}
	public void setServiceDate2(Timestamp serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}
	public String getServiceTime1() {
		return serviceTime1;
	}
	public void setServiceTime1(String serviceTime1) {
		this.serviceTime1 = serviceTime1;
	}
	public String getServiceTime2() {
		return serviceTime2;
	}
	public void setServiceTime2(String serviceTime2) {
		this.serviceTime2 = serviceTime2;
	}
	public String getCustFirstName() {
		return custFirstName;
	}
	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}
	public String getCustLastName() {
		return custLastName;
	}
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
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
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerLogo() {
		return buyerLogo;
	}
	public void setBuyerLogo(String buyerLogo) {
		this.buyerLogo = buyerLogo;
	}
	public String getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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
	public Timestamp getServerDateTime() {
		return serverDateTime;
	}
	public void setServerDateTime(Timestamp serverDateTime) {
		this.serverDateTime = serverDateTime;
	}
	public String getSoSubStatus() {
		return soSubStatus;
	}
	public void setSoSubStatus(String soSubStatus) {
		this.soSubStatus = soSubStatus;
	}
	public String getServiceLocationTimeZone() {
		return serviceLocationTimeZone;
	}
	public void setServiceLocationTimeZone(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
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
	public Integer getDateType() {
		return dateType;
	}
	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
	public Integer getTotalCountOfSo() {
		return totalCountOfSo;
	}
	public void setTotalCountOfSo(Integer totalCountOfSo) {
		this.totalCountOfSo = totalCountOfSo;
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
	
	public Boolean getFollowupFlag() {
		return followupFlag;
	}
	public void setFollowupFlag(Boolean followupFlag) {
		this.followupFlag = followupFlag;
	}
}
