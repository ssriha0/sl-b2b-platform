package com.newco.marketplace.dto.vo.powerbuyer;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */
public class PBBuyerFilterSummaryVO extends SerializableBaseVO {
	private static final long serialVersionUID = 638498150203420852L;
	
	private Integer buyerId;
	private Integer filterId;
	private String filterName;
	private Integer count1;
	private Integer count2;
	private Integer count3;
	private Boolean isSLAdmin;
	private Integer buyerRefTypeId;
	private String buyerRefValue;
	private ArrayList<Integer> excBuyerList = new ArrayList<Integer>();
	private String searchBuyerId;
	
public String getSearchBuyerId() {
		return searchBuyerId;
	}
	public void setSearchBuyerId(String searchBuyerId) {
		this.searchBuyerId = searchBuyerId;
	}
public ArrayList<Integer> getExcBuyerList() {
		return excBuyerList;
	}
	public void setExcBuyerList(ArrayList<Integer> excBuyerList) {
		this.excBuyerList = excBuyerList;
	}

	//	public String getIsSLAdmin() {
//		return isSLAdmin;
//	}
//	public void setIsSLAdmin(String isSLAdmin) {
//		this.isSLAdmin = isSLAdmin;
//	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getFilterId() {
		return filterId;
	}
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public Integer getCount1() {
		return count1;
	}
	public void setCount1(Integer count1) {
		this.count1 = count1;
	}
	public Integer getCount2() {
		return count2;
	}
	public void setCount2(Integer count2) {
		this.count2 = count2;
	}
	public Integer getCount3() {
		return count3;
	}
	public void setCount3(Integer count3) {
		this.count3 = count3;
	}
	@Override
	public String toString() {
	
		StringBuilder builder = new StringBuilder();
		
		builder.append("buyerId: " + buyerId + "\n");
		builder.append("filterId: " + filterId);
		builder.append("filterName: " + filterName + "\n");
		builder.append("count1: " + count1 + "\n");
		builder.append("count2: " + count2 + "\n");
		builder.append("count3: " + count3 + "\n");
		builder.append("isSLAdmin: " + isSLAdmin + "\n");
		return builder.toString();
	}
	public Boolean getIsSLAdmin() {
		return isSLAdmin;
	}
	public void setIsSLAdmin(Boolean isSLAdmin) {
		this.isSLAdmin = isSLAdmin;
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
}
