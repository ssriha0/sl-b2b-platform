package com.newco.marketplace.web.dto;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;





public class BPSearchDTO extends SOWBaseTabDTO {

	private static final long serialVersionUID = -4070555946983599725L;
	private String flname;
	private String userId;

	private String sortColumnName ;
	private String sortOrder;
	
	private int startIndex;
	private int endIndex;
	private int pageSize;
	
	private String search;
	
	public BPSearchDTO()
	{
		
	}
	
	public String getFlname() {
		return flname;
	}


	public void setFlname(String flname) {
		this.flname = flname;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
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


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}


	public int getEndIndex() {
		return endIndex;
	}


	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}


	@Override
	public void validate() {
		if (ServiceLiveStringUtils.isNotEmpty(userId) && (! ServiceLiveStringUtils.isNumeric(userId))) {
			addError("Service Provider", OrderConstants.NUMERIC_PROVIDER_ID, OrderConstants.SOW_TAB_ERROR);
			dirtyFlag = true;
		}
	}


	@Override
	public String getTabIdentifier() {
		return null;
	}

}
