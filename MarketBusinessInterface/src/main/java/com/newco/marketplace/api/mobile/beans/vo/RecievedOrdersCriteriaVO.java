package com.newco.marketplace.api.mobile.beans.vo;

import java.util.ArrayList;
import java.util.List;


public class RecievedOrdersCriteriaVO {

	private int firmId;
	private int resourceId ;
	private int pageNo;
	private int pageSize;
	private int pageLimit;
	private int roleId;
	private boolean isAdmin = false;
	private boolean bidOnlyInd = false;
	private List<String> serviceOrderIds=new ArrayList<String>();
	
	public int getFirmId() {
		return firmId;
	}
	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
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
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
		public boolean isBidOnlyInd() {
		return bidOnlyInd;
	}
	public void setBidOnlyInd(boolean bidOnlyInd) {
		this.bidOnlyInd = bidOnlyInd;
	}
	public List<String> getServiceOrderIds() {
		return serviceOrderIds;
	}
	public void setServiceOrderIds(List<String> serviceOrderIds) {
		this.serviceOrderIds = serviceOrderIds;
	}
    
	
	
}
