package com.newco.marketplace.dto.vo.ExploreMktPlace;

import java.util.List;

import com.newco.marketplace.criteria.FilterCriteria;

public class ExploreMktPlaceFilterCriteria extends FilterCriteria{

	private static final long serialVersionUID = -8871986675951786030L;
	
	private Integer distance;
	private Integer credentialCategory;
	private Integer selectedCredential;
	private List<Integer> selectedLangs;
	private Double rating;
	private Integer spnId;
	private List<String> perflevels;
	
	
	public ExploreMktPlaceFilterCriteria(Integer[] status, String subStatus) {
		super(status, subStatus,null,null, null);
	}
	
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Integer getCredentialCategory() {
		return credentialCategory;
	}
	public void setCredentialCategory(Integer credentialCategory) {
		this.credentialCategory = credentialCategory;
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
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
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
	 * @return the perflevels
	 */
	public List<String> getPerflevels() {
		return perflevels;
	}

	/**
	 * @param perflevels the perflevels to set
	 */
	public void setPerflevels(List<String> perflevels) {
		this.perflevels = perflevels;
	}	
	
}
