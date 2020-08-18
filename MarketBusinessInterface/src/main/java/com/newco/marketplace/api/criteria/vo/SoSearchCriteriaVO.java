package com.newco.marketplace.api.criteria.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoSearchCriteriaVO {
	
	List<SearchCriteriaVO> searchCriteriaVOs;
	List<SoStatusVO>soStatusVOs;
	Map<Integer , List<SoStatusVO>> statusMap = new HashMap<Integer ,List<SoStatusVO>>();
	
	public List<SearchCriteriaVO> getSearchCriteriaVOs() {
		return searchCriteriaVOs;
	}
	public void setSearchCriteriaVOs(List<SearchCriteriaVO> searchCriteriaVOs) {
		this.searchCriteriaVOs = searchCriteriaVOs;
	}
	public List<SoStatusVO> getSoStatusVOs() {
		return soStatusVOs;
	}
	public void setSoStatusVOs(List<SoStatusVO> soStatusVOs) {
		this.soStatusVOs = soStatusVOs;
	}
	public Map<Integer, List<SoStatusVO>> getStatusMap() {
		return statusMap;
	}
	public void setStatusMap(Map<Integer, List<SoStatusVO>> statusMap) {
		this.statusMap = statusMap;
	}
	
	
}
