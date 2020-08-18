/**
 * 
 */
package com.servicelive.spn.common.detached;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author hoza
 *
 */
public class ComplianceCriteriaVO implements Serializable {

	
	private static final long serialVersionUID = 20090317;

	private Integer spnId;
	private List<String> selectedRequirements;
	private List<String> selectedComplianceStatus; 
	private List<String> selectedMarkets; 
	private List<String> selectedStates;
	
	
	private String sSearch;
	private int startIndex;//iDispalyStart
	private int numberOfRecords;
	private String sortColumnName;
	private String sortOrder;
	
	
	

	
	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
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

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public List<String> getSelectedRequirements() {
		return selectedRequirements;
	}

	public void setSelectedRequirements(List<String> selectedRequirements) {
		this.selectedRequirements = selectedRequirements;
	}

	

	public List<String> getSelectedComplianceStatus() {
		return selectedComplianceStatus;
	}

	public void setSelectedComplianceStatus(List<String> selectedComplianceStatus) {
		this.selectedComplianceStatus = selectedComplianceStatus;
	}

	public List<String> getSelectedMarkets() {
		return selectedMarkets;
	}

	public void setSelectedMarkets(List<String> selectedMarkets) {
		this.selectedMarkets = selectedMarkets;
	}

	public List<String> getSelectedStates() {
		return selectedStates;
	}

	public void setSelectedStates(List<String> selectedStates) {
		this.selectedStates = selectedStates;
	}
	
	
	
	
	
}
