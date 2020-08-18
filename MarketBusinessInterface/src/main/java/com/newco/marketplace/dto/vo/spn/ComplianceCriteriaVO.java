package com.newco.marketplace.dto.vo.spn;

import java.util.Date;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class ComplianceCriteriaVO extends SerializableBaseVO{
	private static final long serialVersionUID = 20090413L;
	
	private Integer vendorId;
	private List<String> selectedRequirements;
	private List<String> selectedComplianceStatus; 
	private List<String> selectedBuyers; 
	private List<String> selectedSPNs;
	private List<String> selectedProviders;

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
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	public List<String> getSelectedBuyers() {
		return selectedBuyers;
	}
	public void setSelectedBuyers(List<String> selectedBuyers) {
		this.selectedBuyers = selectedBuyers;
	}
	public List<String> getSelectedSPNs() {
		return selectedSPNs;
	}
	public void setSelectedSPNs(List<String> selectedSPNs) {
		this.selectedSPNs = selectedSPNs;
	}
	public List<String> getSelectedProviders() {
		return selectedProviders;
	}
	public void setSelectedProviders(List<String> selectedProviders) {
		this.selectedProviders = selectedProviders;
	}
	
	
	
	
	

}