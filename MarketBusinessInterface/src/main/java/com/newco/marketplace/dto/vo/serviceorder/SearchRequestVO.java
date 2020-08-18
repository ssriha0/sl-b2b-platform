package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;




public class SearchRequestVO {

	
	private String customerName;
	private String serviceLocZipcode;
	private String serviceLocPhone;
	private List<String> status;
	private int roleId;
	private int buyerId;
	private int vendorResouceId;
	private Timestamp createStartDate;
	private Timestamp createEndDate;
	private int pageNumber;
	private int pageSize;
	private int pageLimit;
	private List<ServiceOrderCustomRefVO> customRefs;
	private String maxresults;
	private String searchFilter;
	private List<String> customRefSoIds;


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getServiceLocZipcode() {
		return serviceLocZipcode;
	}


	public void setServiceLocZipcode(String serviceLocZipcode) {
		this.serviceLocZipcode = serviceLocZipcode;
	}


	public String getServiceLocPhone() {
		return serviceLocPhone;
	}


	public void setServiceLocPhone(String serviceLocPhone) {
		this.serviceLocPhone = serviceLocPhone;
	}


	public List<ServiceOrderCustomRefVO> getCustomRefs() {
		return customRefs;
	}


	public void setCustomRefs(List<ServiceOrderCustomRefVO> customRefs) {
		this.customRefs = customRefs;
	}


	public int getRoleId() {
		return roleId;
	}


	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


	public int getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}


	public int getVendorResouceId() {
		return vendorResouceId;
	}


	public void setVendorResouceId(int vendorResouceId) {
		this.vendorResouceId = vendorResouceId;
	}


	public Date getCreateStartDate() {
		return createStartDate;
	}

	public void setCreateStartDate(Timestamp createStartDate) {
		this.createStartDate = createStartDate;
	}


	public void setCreateEndDate(Timestamp createEndDate) {
		this.createEndDate = createEndDate;
	}


	public Date getCreateEndDate() {
		return createEndDate;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPageLimit() {
		return pageLimit;
	}


	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}


	public String getMaxresults() {
		return maxresults;
	}


	public void setMaxresults(String maxresults) {
		this.maxresults = maxresults;
	}


	public List<String> getStatus() {
		return status;
	}


	public void setStatus(List<String> status) {
		this.status = status;
	}


	public String getSearchFilter() {
		return searchFilter;
	}


	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}


	public void setCustomRefSoIds(List<String> customRefSoIds) {
		this.customRefSoIds = customRefSoIds;
	}


	public List<String> getCustomRefSoIds() {
		return customRefSoIds;
	}

}
