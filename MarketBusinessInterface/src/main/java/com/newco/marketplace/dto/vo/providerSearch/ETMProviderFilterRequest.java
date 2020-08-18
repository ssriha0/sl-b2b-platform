package com.newco.marketplace.dto.vo.providerSearch;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class ETMProviderFilterRequest extends SerializableBaseVO{
	
	private Integer distance;
	private Integer credentialCategory;
	private Integer selectedCredential;
	private List<Integer> selectedLangs;
	private Double rating;
	private String sortColumnName;
	private String sortOrder;
	private Integer startIndex = 1;
	private Integer numberOfRecords = 25;
	private String searchQueryKey;
	private Integer selectedLangsCount;
	private Integer spnId;
	private  List<String> performancelevels;
	
	public Integer getCredentialCategory() {
		return credentialCategory;
	}
	public void setCredentialCategory(Integer credentialCategory) {
		this.credentialCategory = credentialCategory;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}
	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public Integer getSelectedCredential() {
		return selectedCredential;
	}
	public void setSelectedCredential(Integer selectedCredential) {
		this.selectedCredential = selectedCredential;
	}
	public List<Integer> getSelectedLangs() {
		return selectedLangs;
	}
	public void setSelectedLangs(List<Integer> selectedLangs) {
		this.selectedLangs = selectedLangs;
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
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public String getSearchQueryKey() {
		return searchQueryKey;
	}
	public void setSearchQueryKey(String searchQueryKey) {
		this.searchQueryKey = searchQueryKey;
	}
	public Integer getSelectedLangsCount() {
		return selectedLangsCount;
	}
	public void setSelectedLangsCount(Integer selectedLangsCount) {
		this.selectedLangsCount = selectedLangsCount;
	}
	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	/**
	 * @return the performancelevels
	 */
	public List<String> getPerformancelevels() {
		return performancelevels;
	}
	/**
	 * @param performancelevels the performancelevels to set
	 */
	public void setPerformancelevels(List<String> performancelevels) {
		this.performancelevels = performancelevels;
	}
	
	
	

}
