package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;

public class FilterCriteriaVO extends SerializableBaseVO {

	private static final long serialVersionUID = -2997759648844837984L;

	private Integer filterId;
	private String searchCriteria;
	private String searchCriteriaValueId;
	private String searchCriteriaValueString;
	private Boolean flaggedSo;
	private Boolean unAssigned;
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
		
	}
	public Boolean getFlaggedSo() {
		return flaggedSo;
	}
	public void setFlaggedSo(Boolean flaggedSo) {
		this.flaggedSo = flaggedSo;
	}
	public Boolean getUnAssigned() {
		return unAssigned;
	}
	public void setUnAssigned(Boolean unAssigned) {
		this.unAssigned = unAssigned;
	}
	public String getSearchCriteriaValueId() {
		return searchCriteriaValueId;
	}
	public void setSearchCriteriaValueId(String searchCriteriaValueId) {
		this.searchCriteriaValueId = searchCriteriaValueId;
	}
	public String getSearchCriteriaValueString() {
		return searchCriteriaValueString;
	}
	public void setSearchCriteriaValueString(String searchCriteriaValueString) {
		this.searchCriteriaValueString = searchCriteriaValueString;
	}
	public Integer getFilterId() {
		return filterId;
	}
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
}
