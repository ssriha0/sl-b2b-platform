package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.interfaces.OrderConstants;

public class SOWProviderSearchDTO extends SOWBaseTabDTO implements OrderConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 607889912070746444L;
	private Integer distance = 0;
	private Integer backgroundCheck = 0;
	private Integer insuranceFlag = 0;
	private SOWInsuranceDTO insuranceTypes;
	private Double rating = 0.0;	
	private Integer credentialCategory = 0;
	private Integer selectedCredential = 0;
	private List<LanguageDTO> languages;
	private Integer selectedLang;
	private Integer spn = 0;
	private List<Integer> perfLevelSelections = new ArrayList<Integer>();
	private Integer generalLiabilityRating = 0;
	private Integer vehicleLiabilityRating = 0;
	private Integer workersCompensationRating = 0;
	private Integer additionalInsuranceRating = 0;
	private boolean glRatingCheckBox = false;
	private boolean vlRatingCheckBox = false;
	private boolean wcRatingCheckBox = false;
	private boolean addRatingCheckBox=false;
	List<LookupVO> generalLiabilityRatingList = null;
	List<LookupVO> vehicleLiabilityRatingList = null;
	List<LookupVO> workersCompensationRatingList = null;
	List<LookupVO> additionalInsuranceList = null;
	private Double performanceScore;
	private boolean routingPriorityApplied;
	private String perfCriteriaLevel;
	private Boolean routingPriorityChecked;
	private Integer additionalInsuranceCatergoryId = 0;
	private List<Integer> selectedAddnInsurances= new ArrayList<Integer>();
	
	private List<String> additionalSelectedInsurances = new ArrayList<String>();
	
	public List<Integer> getPerfLevelSelections()
	{
		return perfLevelSelections;
	}

	public void setPerfLevelSelections(List<Integer> perfLevelSelections)
	{
		this.perfLevelSelections = perfLevelSelections;
	}

	public List<LanguageDTO> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LanguageDTO> languages) {
		this.languages = languages;
	}
	

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}	

	public Integer getBackgroundCheck() {
		return backgroundCheck;
	}

	public void setBackgroundCheck(Integer backgroundCheck) {
		this.backgroundCheck = backgroundCheck;
	}

	public Integer getInsuranceFlag() {
		return insuranceFlag;
	}

	public void setInsuranceFlag(Integer insuranceFlag) {
		this.insuranceFlag = insuranceFlag;
	}

	public SOWInsuranceDTO getInsuranceTypes() {
		return insuranceTypes;
	}

	public void setInsuranceTypes(SOWInsuranceDTO insuranceTypes) {
		this.insuranceTypes = insuranceTypes;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
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

	public String getPerfCriteriaLevel() {
		return perfCriteriaLevel;
	}

	public void setPerfCriteriaLevel(String perfCriteriaLevel) {
		this.perfCriteriaLevel = perfCriteriaLevel;
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
		if (distance == 0 &&
				null == selectedCredential &&
				credentialCategory == 0 &&
				null == rating &&
				null == spn &&
				null == backgroundCheck &&
				null == selectedLang) {
			
			addError("Search Filter", ENTER_PROVIDER_SEARCH_FILTER, SOW_TAB_ERROR);
		}
	}

	/**
	 * @return the spn 
	 */
	public Integer getSpn() {
		return spn;
	}

	/**
	 * @param spn the spn to set
	 */
	public void setSpn(Integer spn) {
		this.spn = spn;
	}

	public Integer getSelectedLang()
	{
		return selectedLang;
	}

	public void setSelectedLang(Integer selectedLang)
	{
		this.selectedLang = selectedLang;
	}

	public Integer getGeneralLiabilityRating() {
		return generalLiabilityRating;
	}

	public void setGeneralLiabilityRating(Integer generalLiabilityRating) {
		this.generalLiabilityRating = generalLiabilityRating;
	}

	public Integer getVehicleLiabilityRating() {
		return vehicleLiabilityRating;
	}

	public void setVehicleLiabilityRating(Integer vehicleLiabilityRating) {
		this.vehicleLiabilityRating = vehicleLiabilityRating;
	}

	public Integer getWorkersCompensationRating() {
		return workersCompensationRating;
	}

	public void setWorkersCompensationRating(Integer workersCompensationRating) {
		this.workersCompensationRating = workersCompensationRating;
	}

	public boolean isGlRatingCheckBox() {
		return glRatingCheckBox;
	}

	public void setGlRatingCheckBox(boolean glRatingCheckBox) {
		this.glRatingCheckBox = glRatingCheckBox;
	}

	public boolean isVlRatingCheckBox() {
		return vlRatingCheckBox;
	}

	public void setVlRatingCheckBox(boolean vlRatingCheckBox) {
		this.vlRatingCheckBox = vlRatingCheckBox;
	}

	public boolean isWcRatingCheckBox() {
		return wcRatingCheckBox;
	}

	public void setWcRatingCheckBox(boolean wcRatingCheckBox) {
		this.wcRatingCheckBox = wcRatingCheckBox;
	}

	public List<LookupVO> getGeneralLiabilityRatingList() {
		return generalLiabilityRatingList;
	}

	public void setGeneralLiabilityRatingList(
			List<LookupVO> generalLiabilityRatingList) {
		this.generalLiabilityRatingList = generalLiabilityRatingList;
	}

	public List<LookupVO> getVehicleLiabilityRatingList() {
		return vehicleLiabilityRatingList;
	}

	public void setVehicleLiabilityRatingList(
			List<LookupVO> vehicleLiabilityRatingList) {
		this.vehicleLiabilityRatingList = vehicleLiabilityRatingList;
	}

	public List<LookupVO> getWorkersCompensationRatingList() {
		return workersCompensationRatingList;
	}

	public void setWorkersCompensationRatingList(
			List<LookupVO> workersCompensationRatingList) {
		this.workersCompensationRatingList = workersCompensationRatingList;
	}

	public Double getPerformanceScore() {
		return performanceScore;
	}

	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}

	public boolean isRoutingPriorityApplied() {
		return routingPriorityApplied;
	}

	public void setRoutingPriorityApplied(boolean routingPriorityApplied) {
		this.routingPriorityApplied = routingPriorityApplied;
	}

	public Boolean getRoutingPriorityChecked() {
		return routingPriorityChecked;
	}

	public void setRoutingPriorityChecked(Boolean routingPriorityChecked) {
		this.routingPriorityChecked = routingPriorityChecked;
	}

	public List<LookupVO> getAdditionalInsuranceList() {
		return additionalInsuranceList;
	}

	public void setAdditionalInsuranceList(List<LookupVO> additionalInsuranceList) {
		this.additionalInsuranceList = additionalInsuranceList;
	}

	public Integer getAdditionalInsuranceRating() {
		return additionalInsuranceRating;
	}

	public void setAdditionalInsuranceRating(Integer additionalInsuranceRating) {
		this.additionalInsuranceRating = additionalInsuranceRating;
	}

	public boolean isAddRatingCheckBox() {
		return addRatingCheckBox;
	}

	public void setAddRatingCheckBox(boolean addRatingCheckBox) {
		this.addRatingCheckBox = addRatingCheckBox;
	}

	public Integer getAdditionalInsuranceCatergoryId() {
		return additionalInsuranceCatergoryId;
	}

	public void setAdditionalInsuranceCatergoryId(
			Integer additionalInsuranceCatergoryId) {
		this.additionalInsuranceCatergoryId = additionalInsuranceCatergoryId;
	}

	public List<Integer> getSelectedAddnInsurances() {
		return selectedAddnInsurances;
	}

	public void setSelectedAddnInsurances(List<Integer> selectedAddnInsurances) {
		this.selectedAddnInsurances = selectedAddnInsurances;
	}

	public List<String> getAdditionalSelectedInsurances() {
		return additionalSelectedInsurances;
	}

	public void setAdditionalSelectedInsurances(
			List<String> additionalSelectedInsurances) {
		this.additionalSelectedInsurances = additionalSelectedInsurances;
	}


	
	
	
	
	

}
