package com.newco.marketplace.dto.vo.memberOffer;

import java.io.Serializable;

public class MemberOffersCriteriaVO implements Serializable
{
	private static final long serialVersionUID = -1527770441184997895L;
	private int sortCriteria;
	private int perPageOfferCount;
	private int currentPageNo;
	private int totalNumberOfPages;
	private int startPageIndex;
	private int endPageIndex;
	private String sortColumn;
	private String sortOrder; 
	private String offerView;
	
	public String getOfferView() {
		return offerView;
	}
	public void setOfferView(String offerView) {
		this.offerView = offerView;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getStartPageIndex() {
		return startPageIndex;
	}
	public void setStartPageIndex(int startPageIndex) {
		this.startPageIndex = startPageIndex;
	}
	public int getEndPageIndex() {
		return endPageIndex;
	}
	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}	
	public int getSortCriteria() {
		return sortCriteria;
	}
	public void setSortCriteria(int sortCriteria) {
		this.sortCriteria = sortCriteria;
	}
	public int getPerPageOfferCount() {
		return perPageOfferCount;
	}
	public void setPerPageOfferCount(int perPageOfferCount) {
		this.perPageOfferCount = perPageOfferCount;
	}
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getTotalNumberOfPages() {
		return totalNumberOfPages;
	}
	public void setTotalNumberOfPages(int totalNumberOfPages) {
		this.totalNumberOfPages = totalNumberOfPages;
	}

}
