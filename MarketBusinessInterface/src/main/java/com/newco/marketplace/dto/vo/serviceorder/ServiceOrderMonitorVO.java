package com.newco.marketplace.dto.vo.serviceorder;

import java.util.ArrayList;

import com.newco.marketplace.vo.PaginationVO;
import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderMonitorVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3028923595114711149L;
	private Integer [] workFlowStatusIds; 
	private int startIndex;
	private int numberOfRecords;
	private String soStatus;
	private String buyerId;
	private Integer roleId;
	private Integer vendorId;
	private Integer soSubStatus;
	private boolean routedTab;
	private boolean bidTab;
	private boolean bulletinBoardTab;
	private String searchWords;
	private PaginationVO  pagination= null;
	//TODO
	//PLEASE CHANGE ME TO THE CORRECT TYPE!!!!
	private ArrayList<Object> serviceOrderResults;
	private String groupId;
	private String sortColumnName;
	private String sortOrder;
	private String serviceProName;
	private Integer buyerRoleId;
	private String marketName;
	private String priceModel;
	private boolean searchSealedBid=false;
	private boolean filterByPriceModal=false;
	
	boolean serviceDateSort = false;
	
	private boolean manageSOFlag;
	private String resourceId;
	
	public Integer getSoSubStatus() {
		return soSubStatus;
	}
	public void setSoSubStatus(Integer soSubStatus) {
		this.soSubStatus = soSubStatus;
	}
	public Integer [] getWorkFlowStatusIds() {
		return workFlowStatusIds;
	}
	public String getWorkFlowStatusIdsLogString() {
		if (workFlowStatusIds == null) return null;
		StringBuilder workFlowStatusIdsLogString = new StringBuilder("[");
		for (Integer stateId : workFlowStatusIds) {
			workFlowStatusIdsLogString.append(stateId).append(",");
		}
		if (workFlowStatusIds.length > 0) { // Trim last comma
			workFlowStatusIdsLogString.deleteCharAt(workFlowStatusIdsLogString.length()-1);
		}
		workFlowStatusIdsLogString.append("]");
		return workFlowStatusIdsLogString.toString();
	}
	public void setWorkFlowStatusIds(Integer[] workFlowStatusIds) {
		this.workFlowStatusIds = workFlowStatusIds;
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
	public String getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public ArrayList<Object> getServiceOrderResults() {
		return serviceOrderResults;
	}
	public void setServiceOrderResults(ArrayList<Object> serviceOrderResults) {
		this.serviceOrderResults = serviceOrderResults;
	}
	public PaginationVO getPagination() {
		return pagination;
	}
	public void setPagination(PaginationVO pagination) {
		this.pagination = pagination;
	}
	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	/**
	 * @return the routedTab
	 */
	public boolean isRoutedTab() {
		return routedTab;
	}
	/**
	 * @param routedTab the routedTab to set
	 */
	public void setRoutedTab(boolean routedTab) {
		this.routedTab = routedTab;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isBidTab() {
		return bidTab;
	}
	public void setBidTab(boolean bidTab) {
		this.bidTab = bidTab;
	}
	public boolean isBulletinBoardTab() {
		return bulletinBoardTab;
	}
	public void setBulletinBoardTab(boolean bulletinBoardTab) {
		this.bulletinBoardTab = bulletinBoardTab;
	}
	public String getSearchWords() {
		return searchWords;
	}
	public void setSearchWords(String searchWords) {
		this.searchWords = searchWords;
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getServiceProName() {
		return serviceProName;
	}
	public void setServiceProName(String serviceProName) {
		this.serviceProName = serviceProName;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public Integer getBuyerRoleId()
	{
		return buyerRoleId;
	}
	public void setBuyerRoleId(Integer buyerRoleId)
	{
		this.buyerRoleId = buyerRoleId;
	}
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	public String getPriceModel() {
		return priceModel;
	}
	public boolean isSearchSealedBid() {
		return searchSealedBid;
	}
	public void setSearchSealedBid(boolean searchSealedBid) {
		this.searchSealedBid = searchSealedBid;
	}
	public boolean isFilterByPriceModal() {
		return filterByPriceModal;
	}
	public void setFilterByPriceModal(boolean filterByPriceModal) {
		this.filterByPriceModal = filterByPriceModal;
	}
	public boolean isManageSOFlag() {
		return manageSOFlag;
	}
	public void setManageSOFlag(boolean manageSOFlag) {
		this.manageSOFlag = manageSOFlag;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public boolean isServiceDateSort() {
		return serviceDateSort;
	}
	public void setServiceDateSort(boolean serviceDateSort) {
		this.serviceDateSort = serviceDateSort;
	}

}
