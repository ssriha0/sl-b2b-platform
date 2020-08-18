package com.newco.marketplace.vo.mobile.v2_0;

import java.util.List;

public class SOSearchCriteriaVO {
	
	private List<String> custPhones;
	private List<String> custPhonesFormatted;
	private List<String> soIds;
	private List<String> custNames;
	private List<String> serviceEndDates;
	private List<String> serviceStartDates;
	private List<Integer> resourceIds;
	private List<String> serviceProNames;
	private List<String> zipCodes;
	private List<String> cityNames;
	private Integer pageNo;
	private Integer pageSize;
	private Integer pageLimit;
	private Integer roleId;
	private Integer vendorResouceId;
	private Integer firmId;
	private List<Integer> statuses;

	private List<String> serviceTimeEndDates;
	private List<String> serviceTimeStartDates;
	
	private String sortColumnName;
	private String sortOrder;
	private boolean checkGroupedOrders;
	private boolean manageSOFlagOnly;
	
	private Integer acceptedResourceId;

	
	
	
	
	public List<String> getCustPhonesFormatted() {
		return custPhonesFormatted;
	}
	public void setCustPhonesFormatted(List<String> custPhonesFormatted) {
		this.custPhonesFormatted = custPhonesFormatted;
	}
	public Integer getAcceptedResourceId() {
		return acceptedResourceId;
	}
	public void setAcceptedResourceId(Integer acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}
	public boolean isManageSOFlagOnly() {
		return manageSOFlagOnly;
	}
	public void setManageSOFlagOnly(boolean manageSOFlagOnly) {
		this.manageSOFlagOnly = manageSOFlagOnly;
	}
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public boolean isCheckGroupedOrders() {
		return checkGroupedOrders;
	}
	public void setCheckGroupedOrders(boolean checkGroupedOrders) {
		this.checkGroupedOrders = checkGroupedOrders;
	}
	public String getSortColumnName() {
		return sortColumnName;
	}
	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getVendorResouceId() {
		return vendorResouceId;
	}
	public void setVendorResouceId(Integer vendorResouceId) {
		this.vendorResouceId = vendorResouceId;
	}

	public List<String> getServiceTimeEndDates() {
		return serviceTimeEndDates;
	}
	public void setServiceTimeEndDates(List<String> serviceTimeEndDates) {
		this.serviceTimeEndDates = serviceTimeEndDates;
	}
	public List<String> getServiceTimeStartDates() {
		return serviceTimeStartDates;
	}
	public void setServiceTimeStartDates(List<String> serviceTimeStartDates) {
		this.serviceTimeStartDates = serviceTimeStartDates;
	}
	public Integer getPageLimit() {
		return pageLimit;
	}
	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<String> getCustPhones() {
		return custPhones;
	}
	public void setCustPhones(List<String> custPhones) {
		this.custPhones = custPhones;
	}
	public List<String> getSoIds() {
		return soIds;
	}
	public void setSoIds(List<String> soIds) {
		this.soIds = soIds;
	}
	public List<String> getCustNames() {
		return custNames;
	}
	public void setCustNames(List<String> custNames) {
		this.custNames = custNames;
	}
	public List<String> getServiceEndDates() {
		return serviceEndDates;
	}
	public void setServiceEndDates(List<String> serviceEndDates) {
		this.serviceEndDates = serviceEndDates;
	}
	public List<String> getServiceStartDates() {
		return serviceStartDates;
	}
	public void setServiceStartDates(List<String> serviceStartDates) {
		this.serviceStartDates = serviceStartDates;
	}
	public List<Integer> getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(List<Integer> resourceIds) {
		this.resourceIds = resourceIds;
	}
	public List<String> getServiceProNames() {
		return serviceProNames;
	}
	public void setServiceProNames(List<String> serviceProNames) {
		this.serviceProNames = serviceProNames;
	}
	public List<String> getZipCodes() {
		return zipCodes;
	}
	public void setZipCodes(List<String> zipCodes) {
		this.zipCodes = zipCodes;
	}
	public List<String> getCityNames() {
		return cityNames;
	}
	public void setCityNames(List<String> cityNames) {
		this.cityNames = cityNames;
	}
	public List<Integer> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<Integer> statuses) {
		this.statuses = statuses;
	}

	
	
}
