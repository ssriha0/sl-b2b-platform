package com.newco.marketplace.criteria;

public class PagingCriteria implements ICriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4187250425041004743L;

	private String buyerId;

	private String subStatus;

	private int startIndex = 1;

	private int endIndex  = 25;

	private int pageSize = 25;

	
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
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

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public boolean isSet() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub
		startIndex = 1;
		endIndex  = 25;
		pageSize = 25;
	}
	
	public void configureForZeroBased() {
		startIndex = 0;
		endIndex  = 25;
		pageSize = 25;
	}

}
