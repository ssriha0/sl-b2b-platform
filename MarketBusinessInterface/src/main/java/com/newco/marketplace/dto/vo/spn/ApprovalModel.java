package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 
 *
 */
public class ApprovalModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7248931954968265109L;
	private Boolean vehicleLiabilitySelected;
	private Boolean vehicleLiabilityVerified;
	private String vehicleLiabilityAmt;
	private Boolean workersCompensationSelected;
	private Boolean workersCompensationVerified;
	private Boolean commercialGeneralLiabilitySelected;
	private Boolean commercialGeneralLiabilityVerified;
	private String commercialGeneralLiabilityAmt;
	private Boolean meetingRequired;
	private String minimumCompletedServiceOrders;
	private List<String> viewServicesAndSkills;
	private List<SPNCreateNetworkDisplayDocumentsVO> spnDocumentList;
	private List<Integer> selectedMainServices = new ArrayList<Integer>();
	private List<Integer> selectedSubServices1 = new ArrayList<Integer>();
	private List<Integer> selectedSubServices2 = new ArrayList<Integer>();	
	private List<Integer> selectedSkills = new ArrayList<Integer>();
	private List<Integer> selectedLanguages = new ArrayList<Integer>();
	private List<Integer> selectedMarkets = new ArrayList<Integer>();
	private List<String> selectedStates = new ArrayList<String>();
	private Integer selectedMinimumRating;
	private Integer selectedSalesVolume;
	private Integer selectedCompanySize;
	private List<Integer> selectedVendorCredTypes = new ArrayList<Integer>();
	private List<Integer> selectedVendorCredCategories = new ArrayList<Integer>();
	private List<Integer> selectedResCredTypes = new ArrayList<Integer>();
	private List<Integer> selectedResCredCategories = new ArrayList<Integer>();
	private Boolean isAllStatesSelected;
	private Boolean isAllMarketsSelected;
	private Boolean isNotRated;

	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getVehicleLiabilitySelected() {
		return vehicleLiabilitySelected;
	}
	/**
	 * 
	 * @param vehicleLiabilitySelected
	 */
	public void setVehicleLiabilitySelected(Boolean vehicleLiabilitySelected) {
		this.vehicleLiabilitySelected = vehicleLiabilitySelected;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getVehicleLiabilityVerified() {
		return vehicleLiabilityVerified;
	}
	/**
	 * 
	 * @param vehicleLiabilityVerified
	 */
	public void setVehicleLiabilityVerified(Boolean vehicleLiabilityVerified) {
		this.vehicleLiabilityVerified = vehicleLiabilityVerified;
	}
	/**
	 * 
	 * @return String
	 */
	public String getVehicleLiabilityAmt() {
		return vehicleLiabilityAmt;
	}
	/**
	 * 
	 * @param vehicleLiabilityAmt
	 */
	public void setVehicleLiabilityAmt(String vehicleLiabilityAmt) {
		this.vehicleLiabilityAmt = vehicleLiabilityAmt;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getWorkersCompensationSelected() {
		return workersCompensationSelected;
	}
	/**
	 * 
	 * @param workersCompensationSelected
	 */
	public void setWorkersCompensationSelected(Boolean workersCompensationSelected) {
		this.workersCompensationSelected = workersCompensationSelected;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getWorkersCompensationVerified() {
		return workersCompensationVerified;
	}
	/**
	 * 
	 * @param workersCompensationVerified
	 */
	public void setWorkersCompensationVerified(Boolean workersCompensationVerified) {
		this.workersCompensationVerified = workersCompensationVerified;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getCommercialGeneralLiabilitySelected() {
		return commercialGeneralLiabilitySelected;
	}
	/**
	 * 
	 * @param commercialGeneralLiabilitySelected
	 */
	public void setCommercialGeneralLiabilitySelected(Boolean commercialGeneralLiabilitySelected) {
		this.commercialGeneralLiabilitySelected = commercialGeneralLiabilitySelected;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getCommercialGeneralLiabilityVerified() {
		return commercialGeneralLiabilityVerified;
	}
	/**
	 * 
	 * @param commercialGeneralLiabilityVerified
	 */
	public void setCommercialGeneralLiabilityVerified(Boolean commercialGeneralLiabilityVerified) {
		this.commercialGeneralLiabilityVerified = commercialGeneralLiabilityVerified;
	}
	/**
	 * 
	 * @return String
	 */
	public String getCommercialGeneralLiabilityAmt() {
		return commercialGeneralLiabilityAmt;
	}
	/**
	 * 
	 * @param commercialGeneralLiabilityAmt
	 */
	public void setCommercialGeneralLiabilityAmt(String commercialGeneralLiabilityAmt) {
		this.commercialGeneralLiabilityAmt = commercialGeneralLiabilityAmt;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getMeetingRequired() {
		return meetingRequired;
	}
	/**
	 * 
	 * @param meetingRequired
	 */
	public void setMeetingRequired(Boolean meetingRequired) {
		this.meetingRequired = meetingRequired;
	}
	/**
	 * 
	 * @return String
	 */
	public String getMinimumCompletedServiceOrders() {
		return minimumCompletedServiceOrders;
	}
	/**
	 * 
	 * @param minimumCompletedServiceOrders
	 */
	public void setMinimumCompletedServiceOrders(
			String minimumCompletedServiceOrders) {
		this.minimumCompletedServiceOrders = minimumCompletedServiceOrders;
	}
	/**
	 * 
	 * @return List
	 */
	public List<String> getViewServicesAndSkills() {
		return viewServicesAndSkills;
	}
	/**
	 * 
	 * @param viewServicesAndSkills
	 */
	public void setViewServicesAndSkills(List<String> viewServicesAndSkills) {
		this.viewServicesAndSkills = viewServicesAndSkills;
	}
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getSelectedMainServices() {
		return selectedMainServices;
	}
	/**
	 * 
	 * @param selectedMainServices
	 */
	public void setSelectedMainServices(List<Integer> selectedMainServices) {
		this.selectedMainServices = selectedMainServices;
	}
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getSelectedSkills() {
		return selectedSkills;
	}
	/**
	 * 
	 * @param selectedSkills
	 */
	public void setSelectedSkills(List<Integer> selectedSkills) {
		this.selectedSkills = selectedSkills;
	}
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getSelectedLanguages() {
		return selectedLanguages;
	}
	/**
	 * 
	 * @param selectedLanguages
	 */
	public void setSelectedLanguages(List<Integer> selectedLanguages) {
		this.selectedLanguages = selectedLanguages;
	}
	/**
	 * 
	 * @return List
	 */
	public List<SPNCreateNetworkDisplayDocumentsVO> getSpnDocumentList() {
		return spnDocumentList;
	}
	/**
	 * 
	 * @param spnDocumentList
	 */
	public void setSpnDocumentList(
			List<SPNCreateNetworkDisplayDocumentsVO> spnDocumentList) {
		this.spnDocumentList = spnDocumentList;
	}
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getSelectedSubServices1() {
		return selectedSubServices1;
	}
	/**
	 * 
	 * @param selectedSubServices1
	 */
	public void setSelectedSubServices1(List<Integer> selectedSubServices1) {
		this.selectedSubServices1 = selectedSubServices1;
	}
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getSelectedSubServices2() {
		return selectedSubServices2;
	}
	/**
	 * 
	 * @param selectedSubServices2
	 */
	public void setSelectedSubServices2(List<Integer> selectedSubServices2) {
		this.selectedSubServices2 = selectedSubServices2;
	}
	
	/**
	 * @return the selectedStates
	 */
	public List<String> getSelectedStates() {
		return selectedStates;
	}
	/**
	 * @param selectedStates the selectedStates to set
	 */
	public void setSelectedStates(List<String> selectedStates) {
		this.selectedStates = selectedStates;
	}
	/**
	 * @return the selectedCompanySize
	 */
	public Integer getSelectedCompanySize() {
		return selectedCompanySize;
	}
	/**
	 * @param selectedCompanySize the selectedCompanySize to set
	 */
	public void setSelectedCompanySize(Integer selectedCompanySize) {
		this.selectedCompanySize = selectedCompanySize;
	}
	
	/**
	 * @return the selectedSalesVolume
	 */
	public Integer getSelectedSalesVolume() {
		return selectedSalesVolume;
	}
	/**
	 * @param selectedSalesVolume the selectedSalesVolume to set
	 */
	public void setSelectedSalesVolume(Integer selectedSalesVolume) {
		this.selectedSalesVolume = selectedSalesVolume;
	}
	/**
	 * @return the selectedMarkets
	 */
	public List<Integer> getSelectedMarkets() {
		return selectedMarkets;
	}
	/**
	 * @param selectedMarkets the selectedMarkets to set
	 */
	public void setSelectedMarkets(List<Integer> selectedMarkets) {
		this.selectedMarkets = selectedMarkets;
	}
	/**
	 * @return the selectedVendorCredCategories
	 */
	public List<Integer> getSelectedVendorCredCategories() {
		return selectedVendorCredCategories;
	}
	/**
	 * @param selectedVendorCredCategories the selectedVendorCredCategories to set
	 */
	public void setSelectedVendorCredCategories(
			List<Integer> selectedVendorCredCategories) {
		this.selectedVendorCredCategories = selectedVendorCredCategories;
	}
	/**
	 * @return the selectedVendorCredTypes
	 */
	public List<Integer> getSelectedVendorCredTypes() {
		return selectedVendorCredTypes;
	}
	/**
	 * @param selectedVendorCredTypes the selectedVendorCredTypes to set
	 */
	public void setSelectedVendorCredTypes(List<Integer> selectedVendorCredTypes) {
		this.selectedVendorCredTypes = selectedVendorCredTypes;
	}
	/**
	 * @return the selectedResCredTypes
	 */
	public List<Integer> getSelectedResCredTypes() {
		return selectedResCredTypes;
	}
	/**
	 * @param selectedResCredTypes the selectedResCredTypes to set
	 */
	public void setSelectedResCredTypes(List<Integer> selectedResCredTypes) {
		this.selectedResCredTypes = selectedResCredTypes;
	}
	/**
	 * @return the selectedResCredCategories
	 */
	public List<Integer> getSelectedResCredCategories() {
		return selectedResCredCategories;
	}
	/**
	 * @param selectedResCredCategories the selectedResCredCategories to set
	 */
	public void setSelectedResCredCategories(List<Integer> selectedResCredCategories) {
		this.selectedResCredCategories = selectedResCredCategories;
	}
	/**
	 * Adds a selected service to the existing services
	 * @param nodeId
	 */
	public void addSelectedService(Integer nodeId){
		List<Integer> selectedMainServices2 = getSelectedMainServices();
		if (selectedMainServices2 == null) {
			selectedMainServices2= new ArrayList<Integer>();
		}
		selectedMainServices2.add(nodeId);
		setSelectedMainServices(selectedMainServices2);
	}
	
	/**
	 * 
	 * @param nodeId
	 */
	public void addSelectedSub1(Integer nodeId){
		List<Integer> selectedSub1 = getSelectedSubServices1();
		if (selectedSub1 == null) {
			selectedSub1= new ArrayList<Integer>();
		}
		selectedSub1.add(nodeId);
		setSelectedSubServices1(selectedSub1);
	}
	/**
	 * 
	 * @param nodeId
	 */
	public void addSelectedSub2(Integer nodeId){
		List<Integer> selectedSub2 = getSelectedSubServices2();
		if (selectedSub2 == null) {
			selectedSub2= new ArrayList<Integer>();
		}
		selectedSub2.add(nodeId);
		setSelectedSubServices2(selectedSub2);
	}
	
	/**
	 * Adds a selected skill to the existing skills
	 * @param skillId
	 */
	public void addSelectedSkill(Integer skillId){
		List<Integer> selectedSkills2 = getSelectedSkills();
		if (selectedSkills2 == null) {
			selectedSkills2 = new ArrayList<Integer>();
		}
		selectedSkills2.add(skillId);
		setSelectedSkills(selectedSkills2);
	}
	
	/**
	 * Adds a selected language to the existing languages
	 * @param languageId
	 */
	public void addLanguage(Integer languageId){
		List<Integer> languages = getSelectedLanguages();
		if (languages == null) {
			languages= new ArrayList<Integer>();
		}
		languages.add(languageId);
		setSelectedLanguages(languages);
	}
	
	/**
	 * Adds a selected state to the existing states
	 * @param stateCd
	 */
	public void addState(String stateCd){
		List<String> states = getSelectedStates();
		if (states == null) {
			states= new ArrayList<String>();
		}
		states.add(stateCd);
		setSelectedStates(states);
	}
	
	/**
	 * Adds a selected Market to the existing Markets
	 * @param marketId
	 */
	public void addMaket(Integer marketId){
		List<Integer> markets = getSelectedMarkets();
		if (markets == null) {
			markets= new ArrayList<Integer>();
		}
		markets.add(marketId);
		setSelectedMarkets(markets);
	}
	
	
	/**
	 * Adds a selected resource credential type to the existing credential types
	 * @param resCredTypeId
	 */
	public void addResourceCredentialType(Integer resCredTypeId){
		List<Integer> resourceCredTypeIds = getSelectedResCredTypes();
		if (resourceCredTypeIds == null) {
			resourceCredTypeIds= new ArrayList<Integer>();
		}
		resourceCredTypeIds.add(resCredTypeId);
		setSelectedResCredTypes(resourceCredTypeIds);
	}
	
	/**
	 * Adds a selected resource credential category to the existing credential categories
	 * @param resCredCategoryId
	 */
	public void addResourceCredentialCategory(Integer resCredCategoryId){
		List<Integer> resourceCredCategoryIds = getSelectedResCredCategories();
		if (resourceCredCategoryIds == null) {
			resourceCredCategoryIds= new ArrayList<Integer>();
		}
		resourceCredCategoryIds.add(resCredCategoryId);
		setSelectedResCredCategories(resourceCredCategoryIds);
	}
	
	/**
	 * Adds a selected vendor credential type to the existing credential types
	 * @param vendorCredTypeId
	 */
	public void addVendorCredentialType(Integer vendorCredTypeId){
		List<Integer> vendorCredTypeIds = getSelectedVendorCredTypes();
		if (vendorCredTypeIds == null) {
			vendorCredTypeIds= new ArrayList<Integer>();
		}
		vendorCredTypeIds.add(vendorCredTypeId);
		setSelectedVendorCredTypes(vendorCredTypeIds);
	}
	
	/**
	 * Adds a selected vendor credential category to the existing credential categories
	 * @param vendorCredCategoryId
	 */
	public void addVendorCredentialCategory(Integer vendorCredCategoryId){
		List<Integer> vendorCredCategoryIds = getSelectedVendorCredCategories();
		if (vendorCredCategoryIds == null) {
			vendorCredCategoryIds= new ArrayList<Integer>();
		}
		vendorCredCategoryIds.add(vendorCredCategoryId);
		setSelectedVendorCredCategories(vendorCredCategoryIds);
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getSelectedMinimumRating()
	{
		return selectedMinimumRating;
	}
	/**
	 * 
	 * @param selectedMinimumRating
	 */
	public void setSelectedMinimumRating(Integer selectedMinimumRating)
	{
		this.selectedMinimumRating = selectedMinimumRating;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getIsAllStatesSelected() {
		return isAllStatesSelected;
	}
	/**
	 * 
	 * @param isAllStatesSelected
	 */
	public void setIsAllStatesSelected(Boolean isAllStatesSelected) {
		this.isAllStatesSelected = isAllStatesSelected;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getIsAllMarketsSelected() {
		return isAllMarketsSelected;
	}
	/**
	 * 
	 * @param isAllMarketsSelected
	 */
	public void setIsAllMarketsSelected(Boolean isAllMarketsSelected) {
		this.isAllMarketsSelected = isAllMarketsSelected;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getIsNotRated() {
		return isNotRated;
	}
	/**
	 * 
	 * @param isNotRated
	 */
	public void setIsNotRated(Boolean isNotRated) {
		this.isNotRated = isNotRated;
	}

}