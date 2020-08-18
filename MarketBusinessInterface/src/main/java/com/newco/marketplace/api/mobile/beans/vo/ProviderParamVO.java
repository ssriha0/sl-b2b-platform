package com.newco.marketplace.api.mobile.beans.vo;

import java.util.List;

import com.newco.marketplace.api.mobile.common.ResultsCode;

public class ProviderParamVO  {
	
	private Integer pageNo;
	private Integer pageSize;
	private Integer resourceId;
	private Integer vendorId;
	private Integer roleId;
	private String soStatus;
	private String sortFlag;
	private String sortBy;
	private List<Integer> wfStatusIdList;
	private ResultsCode validationCode;
	private Integer countOfSearchRecords = 0;
	private int numberOfRecords;
	private int startIndex;
	//Buyer Logo URL
	private String pathUrl;
	private String baseUrl;
	//Default Constructor
	public ProviderParamVO() {}
	/**
	 * @param sortFlag
	 * @param sortBy
	 */
	public ProviderParamVO(String sortFlag, String sortBy) {
		this.sortFlag = sortFlag;
		this.sortBy = sortBy;
		this.validationCode=ResultsCode.SUCCESS;
	}
	
	public ResultsCode getValidationCode() {
		return validationCode;
	}
	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
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
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getCountOfSearchRecords() {
		return countOfSearchRecords;
	}
	public void setCountOfSearchRecords(Integer countOfSearchRecords) {
		this.countOfSearchRecords = countOfSearchRecords;
	}
	public List<Integer> getWfStatusIdList() {
		return wfStatusIdList;
	}
	public void setWfStatusIdList(List<Integer> wfStatusIdList) {
		this.wfStatusIdList = wfStatusIdList;
	}
	public String getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getSortFlag() {
		return sortFlag;
	}
	public void setSortFlag(String sortFlag) {
		this.sortFlag = sortFlag;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getPathUrl() {
		return pathUrl;
	}
	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public int getNumberOfRecords() {
		return numberOfRecords;
	}
	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getStartIndex() {
		int startIndex = 0;
		if (this.pageNo!= 0) {
			startIndex = ((this.pageNo != 1) ? ((this.pageNo - 1) * this.pageSize) : 0);
		}
		return startIndex;
	}
}
