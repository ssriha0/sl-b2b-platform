package com.newco.marketplace.api.mobile.beans.so.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("orderDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDetail {
	private String soId;
	private String groupId;
	private String soStatus;
	private String soSubStatus;
	private String recievedDate;
	private Integer buyerId;
	private String businessName;
	private String buyerName;
	private String title;
	private String acceptedVendor;
	private String acceptedResource;
	private Integer acceptedVendorId;
	private Integer acceptedResourceId;
	private String appointStartDate;
	private String assignmentType;
	
	private String appointEndDate;
	
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private String serviceLocationTimezone;
	private CustInfo custInfo;
	private Boolean groupInd;
	private ChildOrderDetails childOrderDetails;
	
	//SL-20838
	private Boolean followUpFlag;
	
	//R16_2_0_1: SL-21265: Adding price model
	private String priceModel;
	
	public String getAppointStartDate() {
		return appointStartDate;
	}
	public void setAppointStartDate(String appointStartDate) {
		this.appointStartDate = appointStartDate;
	}
	public String getAppointEndDate() {
		return appointEndDate;
	}
	public void setAppointEndDate(String appointEndDate) {
		this.appointEndDate = appointEndDate;
	}
	public String getServiceTimeStart() {
		return serviceTimeStart;
	}
	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}
	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}
	public String getServiceLocationTimezone() {
		return serviceLocationTimezone;
	}
	public void setServiceLocationTimezone(String serviceLocationTimezone) {
		this.serviceLocationTimezone = serviceLocationTimezone;
	}
	public CustInfo getCustInfo() {
		return custInfo;
	}
	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}
	public ChildOrderDetails getChildOrderDetails() {
		return childOrderDetails;
	}
	public void setChildOrderDetails(ChildOrderDetails childOrderDetails) {
		this.childOrderDetails = childOrderDetails;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
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
	
	public String getRecievedDate() {
		return recievedDate;
	}
	public void setRecievedDate(String recievedDate) {
		this.recievedDate = recievedDate;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAcceptedVendor() {
		return acceptedVendor;
	}
	public void setAcceptedVendor(String acceptedVendor) {
		this.acceptedVendor = acceptedVendor;
	}
	public String getAcceptedResource() {
		return acceptedResource;
	}
	public void setAcceptedResource(String acceptedResource) {
		this.acceptedResource = acceptedResource;
	}
	public Boolean getGroupInd() {
		return groupInd;
	}
	public void setGroupInd(Boolean groupInd) {
		this.groupInd = groupInd;
	}
	public Boolean getFollowUpFlag() {
		return followUpFlag;
	}
	public void setFollowUpFlag(Boolean followUpFlag) {
		this.followUpFlag = followUpFlag;
	}
	public String getPriceModel() {
		return priceModel;
	}
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
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
	public String getAssignmentType() {
		return assignmentType;
	}
	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}
	
}
