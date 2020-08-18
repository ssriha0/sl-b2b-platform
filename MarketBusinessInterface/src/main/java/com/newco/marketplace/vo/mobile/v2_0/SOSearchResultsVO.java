package com.newco.marketplace.vo.mobile.v2_0;

import java.util.List;


public class SOSearchResultsVO {

	private Integer totalSOCount;
	private boolean pageNumberExceededInd;
	private List<SOSearchResultVO> searchResultVOs;
	public Integer getTotalSOCount() {
		return totalSOCount;
	}
	public void setTotalSOCount(Integer totalSOCount) {
		this.totalSOCount = totalSOCount;
	}
	public List<SOSearchResultVO> getSearchResultVOs() {
		return searchResultVOs;
	}
	public void setSearchResultVOs(List<SOSearchResultVO> searchResultVOs) {
		this.searchResultVOs = searchResultVOs;
	}
	public boolean isPageNumberExceededInd() {
		return pageNumberExceededInd;
	}
	public void setPageNumberExceededInd(boolean pageNumberExceededInd) {
		this.pageNumberExceededInd = pageNumberExceededInd;
	}
	
	
}
