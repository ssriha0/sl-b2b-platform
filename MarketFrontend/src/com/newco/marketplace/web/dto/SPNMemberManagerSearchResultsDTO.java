package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.dto.vo.spn.SPNMemberSearchResultVO;
import com.newco.marketplace.dto.vo.spn.SPNMemberSearchResults;

public class SPNMemberManagerSearchResultsDTO extends SerializedBaseDTO{
	
	private Integer type;
	private SPNMemberSearchResults result;
	private Integer filterCriteriaId;
	private List<Integer> selectedMembersList;
	private Integer spnId;
	//private List<SPNMemberManagerResultRow> results;
	private boolean noResults;
	
	
	
	public List<SPNMemberSearchResultVO> getResults() {
		return result.getSearchResults();
	}

	

	public SPNMemberSearchResults getResult() {
		return result;
	}

	public void setResult(SPNMemberSearchResults result) {
		this.result = result;
	}

	public Integer getFilterCriteriaId() {
		return filterCriteriaId;
	}

	public void setFilterCriteriaId(Integer filterCriteriaId) {
		this.filterCriteriaId = filterCriteriaId;
	}

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}



	public List<Integer> getSelectedMembersList() {
		return selectedMembersList;
	}



	public void setSelectedMembersList(List<Integer> selectedMembersList) {
		this.selectedMembersList = selectedMembersList;
	}



	public boolean isNoResults() {
		return noResults;
	}



	public void setNoResults(boolean noResults) {
		this.noResults = noResults;
	}

}
