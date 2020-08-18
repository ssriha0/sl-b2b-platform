package com.newco.marketplace.vo.mobile.v2_0;

import java.util.List;

public class SOAdvanceSearchCriteriaVO {
	
	private List<Integer> statusIds;
	private List<Integer> subStatusIds;
	private boolean noSubStatusInd;
	
	private List<Integer> marketIds;
	private List<Integer> serviceProIds;
	private String appointmentStartRange;
	private String appointmentEndRange;
	private String appointmentCriteriaType;
	private Integer pageNo;
	private Integer pageSize;
	private Integer pageLimit;
	private Integer roleId;
	private Integer firmId;
	private String sortColumnName;
	private String sortOrder;
	private boolean flaggedInd;
	private Boolean unAssignedInd;
	
	private Integer acceptedResourceId;

	public boolean isFlaggedInd() {
		return flaggedInd;
	}

	public void setFlaggedInd(boolean flaggedInd) {
		this.flaggedInd = flaggedInd;
	}
	public boolean isNoSubStatusInd() {
		return noSubStatusInd;
	}

	public void setNoSubStatusInd(boolean noSubStatusInd) {
		this.noSubStatusInd = noSubStatusInd;
	}

	

	public Boolean getUnAssignedInd() {
		return unAssignedInd;
	}

	public void setUnAssignedInd(Boolean unAssignedInd) {
		this.unAssignedInd = unAssignedInd;
	}

	public List<Integer> getStatusIds() {
		return statusIds;
	}

	public void setStatusIds(List<Integer> statusIds) {
		this.statusIds = statusIds;
	}

	public List<Integer> getSubStatusIds() {
		return subStatusIds;
	}

	public void setSubStatusIds(List<Integer> subStatusIds) {
		this.subStatusIds = subStatusIds;
	}

	public List<Integer> getMarketIds() {
		return marketIds;
	}

	public void setMarketIds(List<Integer> marketIds) {
		this.marketIds = marketIds;
	}

	public List<Integer> getServiceProIds() {
		return serviceProIds;
	}

	public void setServiceProIds(List<Integer> serviceProIds) {
		this.serviceProIds = serviceProIds;
	}

	

	public String getAppointmentStartRange() {
		return appointmentStartRange;
	}

	public void setAppointmentStartRange(String appointmentStartRange) {
		this.appointmentStartRange = appointmentStartRange;
	}

	public String getAppointmentEndRange() {
		return appointmentEndRange;
	}

	public void setAppointmentEndRange(String appointmentEndRange) {
		this.appointmentEndRange = appointmentEndRange;
	}

	public String getAppointmentCriteriaType() {
		return appointmentCriteriaType;
	}

	public void setAppointmentCriteriaType(String appointmentCriteriaType) {
		this.appointmentCriteriaType = appointmentCriteriaType;
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

	public Integer getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
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

	public Integer getAcceptedResourceId() {
		return acceptedResourceId;
	}

	public void setAcceptedResourceId(Integer acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}

	
}
