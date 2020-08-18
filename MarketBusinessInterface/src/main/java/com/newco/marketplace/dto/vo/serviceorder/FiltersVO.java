package com.newco.marketplace.dto.vo.serviceorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sears.os.vo.SerializableBaseVO;

public class FiltersVO extends SerializableBaseVO {

	private static final long serialVersionUID = -2997759648844837984L;

	private Integer filterId;
	private Integer resourceId;
	private String filterName;
	List<FilterCriteriaVO> criteriaList = new ArrayList<FilterCriteriaVO>();
	// Add comments for this Map.
	Map<String,List<FilterCriteriaVO>> criteriaMap;

	
	public Integer getFilterId() {
		return filterId;
	}

	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public List<FilterCriteriaVO> getCriteriaList() {
		return criteriaList;
	}

	public void setCriteriaList(List<FilterCriteriaVO> criteriaList) {
		this.criteriaList = criteriaList;
	}

	public Map<String, List<FilterCriteriaVO>> getCriteriaMap() {
		return criteriaMap;
	}

	public void setCriteriaMap(Map<String, List<FilterCriteriaVO>> criteriaMap) {
		this.criteriaMap = criteriaMap;
	}


	
	
	
	
}
