package com.newco.marketplace.vo.mobile.v2_0;

import java.sql.Timestamp;
import java.util.List;


public class SOSearchResultVO {

	private String soId;
	private String groupId;

	private String soStatus;
	private String soSubStatus;
	private Integer buyerId;
	private String businessName;
	private String title;
	private String acceptedVendor;
	private String acceptedResource;
	private Integer acceptedVendorId;
	private Integer acceptedResourceId;
	private List<SOSearchResultVO> childOrders;
	private boolean groupInd;
	private String parentGroupTitle;
	
	private String marketName;
	private Timestamp appointStartDate;
	
	private Timestamp appointEndDate;
	
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private String serviceLocationTimezone;
	private String street1;
	private String street2;
	private String city;
	private String stateCd;
	private String zip;
	private String endCustomerFirstName;
	private String endCustomerLastName;
	private String endCustomerName;
	
	//SL-20838
	private String firmFollowUpFlag;
	private String providerFollowUpFlag;
	private String assignmentType;
	
	private String routedDate;
	
	//SL-21265
	private String priceModel;
	
	public String getAssignmentType() {
		return assignmentType;
	}
	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public String getFirmFollowUpFlag() {
		return firmFollowUpFlag;
	}
	public void setFirmFollowUpFlag(String firmFollowUpFlag) {
		this.firmFollowUpFlag = firmFollowUpFlag;
	}
	public String getProviderFollowUpFlag() {
		return providerFollowUpFlag;
	}
	public void setProviderFollowUpFlag(String providerFollowUpFlag) {
		this.providerFollowUpFlag = providerFollowUpFlag;
	}
	public String getEndCustomerName() {
		return endCustomerName;
	}
	public void setEndCustomerName(String endCustomerName) {
		this.endCustomerName = endCustomerName;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	
	public String getParentGroupTitle() {
		return parentGroupTitle;
	}
	public void setParentGroupTitle(String parentGroupTitle) {
		this.parentGroupTitle = parentGroupTitle;
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<SOSearchResultVO> getChildOrders() {
		return childOrders;
	}
	public void setChildOrders(List<SOSearchResultVO> childOrders) {
		this.childOrders = childOrders;
	}
	public boolean isGroupInd() {
		return groupInd;
	}
	public void setGroupInd(boolean groupInd) {
		this.groupInd = groupInd;
	}
	public Timestamp getAppointStartDate() {
		return appointStartDate;
	}
	public void setAppointStartDate(Timestamp appointStartDate) {
		this.appointStartDate = appointStartDate;
	}
	public Timestamp getAppointEndDate() {
		return appointEndDate;
	}
	public void setAppointEndDate(Timestamp appointEndDate) {
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
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getEndCustomerFirstName() {
		return endCustomerFirstName;
	}
	public void setEndCustomerFirstName(String endCustomerFirstName) {
		this.endCustomerFirstName = endCustomerFirstName;
	}
	public String getEndCustomerLastName() {
		return endCustomerLastName;
	}
	public void setEndCustomerLastName(String endCustomerLastName) {
		this.endCustomerLastName = endCustomerLastName;
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
	public String getRoutedDate() {
		return routedDate;
	}
	public void setRoutedDate(String routedDate) {
		this.routedDate = routedDate;
	}
	public String getPriceModel() {
		return priceModel;
	}
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	
}
