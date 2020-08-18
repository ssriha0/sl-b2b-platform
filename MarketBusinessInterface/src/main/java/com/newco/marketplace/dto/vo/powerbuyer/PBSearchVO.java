package com.newco.marketplace.dto.vo.powerbuyer;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */
public class PBSearchVO extends SerializableBaseVO{
	private static final long serialVersionUID = 8625940621843888150L;

	private String viewName;
	private Integer buyerId;
	private Integer soStatus;
	private Integer soSubStatus;
	private List<String> soIds;
	private String sortColumnName;
	private String sortOrder;
	private Integer startIndex;
	private Integer endIndex;
	private Integer filterId;
	private String filterOpt;
	private Integer buyerRefTypeId;
	private String buyerRefValue;
	//sl-13522
	private String wfm_sodFlag;
	private String soId;
	private boolean buyerInd = false;
	private String searchBuyerId;
	
	
	public String getSearchBuyerId() {
		return searchBuyerId;
	}
	public void setSearchBuyerId(String searchBuyerId) {
		this.searchBuyerId = searchBuyerId;
	}
	public boolean isBuyerInd() {
		return buyerInd;
	}
	public void setBuyerInd(boolean buyerInd) {
		this.buyerInd = buyerInd;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(Integer soStatus) {
		this.soStatus = soStatus;
	}
	public Integer getSoSubStatus() {
		return soSubStatus;
	}
	public void setSoSubStatus(Integer soSubStatus) {
		this.soSubStatus = soSubStatus;
	}
	public List<String> getSoIds() {
		return soIds;
	}
	public void setSoIds(List<String> soIds) {
		this.soIds = soIds;
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
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	
	public Integer getFilterId() {
		return filterId;
	}
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	public String getFilterOpt() {
		return filterOpt;
	}
	public void setFilterOpt(String filterOpt) {
		this.filterOpt = filterOpt;
	}
	public Integer getBuyerRefTypeId() {
		return buyerRefTypeId;
	}
	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}
	public String getBuyerRefValue() {
		return buyerRefValue;
	}
	public void setBuyerRefValue(String buyerRefValue) {
		this.buyerRefValue = buyerRefValue;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("viewName: " + viewName + "\n");
		builder.append("buyerId: " + buyerId + "\n");
		builder.append("soStatus: " + soStatus + "\n");
		builder.append("soSubStatus: " + soSubStatus + "\n");
		builder.append("sortColumnName: " + sortColumnName + "\n");
		builder.append("sortOrder: " + sortOrder + "\n");
		builder.append("startIndex: " + startIndex + "\n");
		builder.append("endIndex: " + endIndex + "\n");
		builder.append("filterId: " + filterId + "\n");
		
		return builder.toString();
	}
	public String getWfm_sodFlag() {
		return wfm_sodFlag;
}
	public void setWfm_sodFlag(String wfm_sodFlag) {
		this.wfm_sodFlag = wfm_sodFlag;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
}
