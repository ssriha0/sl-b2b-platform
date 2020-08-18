package com.servicelive.routingrulesengine.vo;

import com.sears.os.vo.SerializableBaseVO;

public class RoutingRulesPaginationVO extends SerializableBaseVO
	
{	
	
	private static final long serialVersionUID = 0L;
	
	private Integer totalRecords;
	private Integer totalPages;
	private Integer currentIndex;
	private Integer paginetStartIndex;
	private Integer paginetEndIndex;
	private Integer pageRequested;
	private String pageComment;
	
	private int sortCol;
	private int sortOrd;
	
	public String getPageComment() {
		return pageComment;
	}
	public void setPageComment(String pageComment) {
		this.pageComment = pageComment;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(Integer currentIndex) {
		this.currentIndex = currentIndex;
	}
	public Integer getPaginetEndIndex() {
		return paginetEndIndex;
	}
	public void setPaginetEndIndex(Integer paginetEndIndex) {
		this.paginetEndIndex = paginetEndIndex;
	}
	public Integer getPaginetStartIndex() {
		return paginetStartIndex;
	}
	public void setPaginetStartIndex(Integer paginetStartIndex) {
		this.paginetStartIndex = paginetStartIndex;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Integer getPageRequested() {
		return pageRequested;
	}
	public void setPageRequested(Integer pageRequested) {
		this.pageRequested = pageRequested;
	}
	public int getSortCol() {
		return sortCol;
	}
	public void setSortCol(int sortCol) {
		this.sortCol = sortCol;
	}
	public int getSortOrd() {
		return sortOrd;
	}
	public void setSortOrd(int sortOrd) {
		this.sortOrd = sortOrd;
	}
}
