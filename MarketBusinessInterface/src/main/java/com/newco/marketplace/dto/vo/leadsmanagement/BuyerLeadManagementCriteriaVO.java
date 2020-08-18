package com.newco.marketplace.dto.vo.leadsmanagement;

import java.sql.Timestamp;
import com.sears.os.vo.SerializableBaseVO;

public class BuyerLeadManagementCriteriaVO extends SerializableBaseVO
	
{	
	
	private static final long serialVersionUID = 0L;
	private Integer buyerId;
	private String sSearch;
	private int startIndex;
	private int numberOfRecords;
	private String sortColumnName;
	private String sortOrder;
	private Integer providerFirmId;
	private String searchColumnName;
	private String startDate;
	private String competedDate;
	private String filterSearchId;
	private String leadId;
	//SL-20893: To add second sort order
	private String orderByString;
	private String sortFirm;
	
	public String getFilterSearchId() {
		return filterSearchId;
	}
	public void setFilterSearchId(String filterSearchId) {
		this.filterSearchId = filterSearchId;
	}
	public Integer getProviderFirmId() {
		return providerFirmId;
	}
	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	public String getsSearch() {
		return sSearch;
	}
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getNumberOfRecords() {
		return numberOfRecords;
	}
	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
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
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getSearchColumnName() {
		return searchColumnName;
	}
	public void setSearchColumnName(String searchColumnName) {
		this.searchColumnName = searchColumnName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getCompetedDate() {
		return competedDate;
	}
	public void setCompetedDate(String competedDate) {
		this.competedDate = competedDate;
	}
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getOrderByString() {
		return orderByString;
	}
	public void setOrderByString(String orderByString) {
		this.orderByString = orderByString;
	}
	public String getSortFirm() {
		return sortFirm;
	}
	public void setSortFirm(String sortFirm) {
		this.sortFirm = sortFirm;
	}
	
	
}
