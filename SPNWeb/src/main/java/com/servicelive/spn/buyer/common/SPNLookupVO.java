package com.servicelive.spn.buyer.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.servicelive.domain.spn.detached.LookupVO;
import com.servicelive.domain.lookup.LookupCompanySize;
import com.servicelive.domain.lookup.LookupLanguage;
import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.domain.lookup.LookupMinimumRating;
import com.servicelive.domain.lookup.LookupPrimaryIndustry;
import com.servicelive.domain.lookup.LookupResourceCredentialType;
import com.servicelive.domain.lookup.LookupSPNDocumentType;
import com.servicelive.domain.lookup.LookupSalesVolume;
import com.servicelive.domain.lookup.LookupSkills;
import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.domain.lookup.LookupVendorCredentialType;
import com.servicelive.domain.spn.network.SPNDocument;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
/**
 * 
 * 
 *
 */
public class SPNLookupVO
{
	private List<LookupSkills> allMainServices = new ArrayList<LookupSkills>();
	private List<LookupSkills> subServices1 = new ArrayList<LookupSkills>();
	private List<LookupSkills> subServices2 = new ArrayList<LookupSkills>();
	private List<LookupVO> mainServicesWithSkills = new ArrayList<LookupVO>();
	private List<LookupLanguage> allLanguages = new ArrayList<LookupLanguage>();
	private List<LookupStates> allStates = new ArrayList<LookupStates>();
	private List<LookupMarket> allMarkets = new ArrayList<LookupMarket>();
	private List<LookupSalesVolume> allSalesVolumes = new ArrayList<LookupSalesVolume>();
	private List<LookupCompanySize> allCompanySizes = new ArrayList<LookupCompanySize>();
	private List<LookupMinimumRating> allMinimumRatings = new ArrayList<LookupMinimumRating>();
	private List<LookupSPNDocumentType> docTypesList = new ArrayList<LookupSPNDocumentType>();
	private Map<Integer, Map<String,List<LookupSkills>>> subServicesMap = new TreeMap<Integer, Map<String,List<LookupSkills>>>();
	private List<LookupResourceCredentialType> resCredTypesList = new ArrayList<LookupResourceCredentialType>();
	private List<LookupVO> resCredCategoriesWithTypes = new ArrayList<LookupVO>();
	private List<LookupVendorCredentialType> vendorCredTypesList = new ArrayList<LookupVendorCredentialType>();
	private List<LookupVO> vendorCredCategoriesWithTypes = new ArrayList<LookupVO>();
	private Collection<SPNDocument> allDocuments;
	private List<SPNExclusionsVO> vendorExclusions = new ArrayList<SPNExclusionsVO>();
	private List<SPNExclusionsVO> resExclusions = new ArrayList<SPNExclusionsVO>();
	//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	//Add attribute to the VO to hold the primary industry list
	private List<LookupPrimaryIndustry> primaryIndustry = new ArrayList<LookupPrimaryIndustry>();
	/**
	 * 
	 * @return List
	 */
	public List<LookupSkills> getAllMainServices() {
		return allMainServices;
	}
	/**
	 * 
	 * @param allMainServices
	 */
	public void setAllMainServices(List<LookupSkills> allMainServices) {
		this.allMainServices = allMainServices;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupVO> getMainServicesWithSkills() {
		return mainServicesWithSkills;
	}
	/**
	 * 
	 * @param mainServicesWithSkills
	 */
	public void setMainServicesWithSkills(List<LookupVO> mainServicesWithSkills) {
		this.mainServicesWithSkills = mainServicesWithSkills;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupLanguage> getAllLanguages() {
		return allLanguages;
	}
	/**
	 * 
	 * @param allLanguages
	 */
	public void setAllLanguages(List<LookupLanguage> allLanguages) {
		this.allLanguages = allLanguages;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupSPNDocumentType> getDocTypesList() {
		return docTypesList;
	}
	/**
	 * 
	 * @param docTypesList
	 */
	public void setDocTypesList(List<LookupSPNDocumentType> docTypesList) {
		this.docTypesList = docTypesList;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupSkills> getSubServices1() {
		return subServices1;
	}
	/**
	 * 
	 * @param subServices1
	 */
	public void setSubServices1(List<LookupSkills> subServices1) {
		this.subServices1 = subServices1;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupSkills> getSubServices2() {
		return subServices2;
	}
	/**
	 * 
	 * @param subServices2
	 */
	public void setSubServices2(List<LookupSkills> subServices2) {
		this.subServices2 = subServices2;
	}
	
	/**
	 * @return the allStates
	 */
	public List<LookupStates> getAllStates() {
		return allStates;
	}
	/**
	 * @param allStates the allStates to set
	 */
	public void setAllStates(List<LookupStates> allStates) {
		this.allStates = allStates;
	}
	
	/**
	 * @return the allMarkets
	 */
	public List<LookupMarket> getAllMarkets() {
		return allMarkets;
	}
	/**
	 * @param allMarkets the allMarkets to set
	 */
	public void setAllMarkets(List<LookupMarket> allMarkets) {
		this.allMarkets = allMarkets;
	}
	/**
	 * @return the allSalesVolumes
	 */
	public List<LookupSalesVolume> getAllSalesVolumes() {
		return allSalesVolumes;
	}
	/**
	 * @param allSalesVolumes the allSalesVolumes to set
	 */
	public void setAllSalesVolumes(List<LookupSalesVolume> allSalesVolumes) {
		this.allSalesVolumes = allSalesVolumes;
	}
	/**
	 * @return the allCompanySizes
	 */
	public List<LookupCompanySize> getAllCompanySizes() {
		return allCompanySizes;
	}
	/**
	 * @param allCompanySizes the allCompanySizes to set
	 */
	public void setAllCompanySizes(List<LookupCompanySize> allCompanySizes) {
		this.allCompanySizes = allCompanySizes;
	}
	/**
	 * @return the allMinimumRatings
	 */
	public List<LookupMinimumRating> getAllMinimumRatings() {
		return allMinimumRatings;
	}
	/**
	 * @param allMinimumRatings the allMinimumRatings to set
	 */
	public void setAllMinimumRatings(List<LookupMinimumRating> allMinimumRatings) {
		this.allMinimumRatings = allMinimumRatings;
	}
	/**
	 * 
	 * @return Map
	 */
	public Map<Integer, Map<String, List<LookupSkills>>> getSubServicesMap() {
		return subServicesMap;
	}
	/**
	 * 
	 * @param subServicesMap
	 */
	public void setSubServicesMap(
			Map<Integer, Map<String, List<LookupSkills>>> subServicesMap) {
		this.subServicesMap = subServicesMap;
	}
	/**
	 * @return the resCredTypesList
	 */
	public List<LookupResourceCredentialType> getResCredTypesList() {
		return resCredTypesList;
	}
	/**
	 * @param resCredTypesList the resCredTypesList to set
	 */
	public void setResCredTypesList(
			List<LookupResourceCredentialType> resCredTypesList) {
		this.resCredTypesList = resCredTypesList;
	}
	/**
	 * @return the resCredCategoriesWithTypes
	 */
	public List<LookupVO> getResCredCategoriesWithTypes() {
		return resCredCategoriesWithTypes;
	}
	/**
	 * @param resCredCategoriesWithTypes the resCredCategoriesWithTypes to set
	 */
	public void setResCredCategoriesWithTypes(
			List<LookupVO> resCredCategoriesWithTypes) {
		this.resCredCategoriesWithTypes = resCredCategoriesWithTypes;
	}
	/**
	 * @return the vendorCredTypesList
	 */
	public List<LookupVendorCredentialType> getVendorCredTypesList() {
		return vendorCredTypesList;
	}
	/**
	 * @param vendorCredTypesList the vendorCredTypesList to set
	 */
	public void setVendorCredTypesList(
			List<LookupVendorCredentialType> vendorCredTypesList) {
		this.vendorCredTypesList = vendorCredTypesList;
	}
	/**
	 * @return the vendorCredCategoriesWithTypes
	 */
	public List<LookupVO> getVendorCredCategoriesWithTypes() {
		return vendorCredCategoriesWithTypes;
	}
	/**
	 * @param vendorCredCategoriesWithTypes the vendorCredCategoriesWithTypes to set
	 */
	public void setVendorCredCategoriesWithTypes(
			List<LookupVO> vendorCredCategoriesWithTypes) {
		this.vendorCredCategoriesWithTypes = vendorCredCategoriesWithTypes;
	}
	/**
	 * @return the allDocuments
	 */
	public Collection<SPNDocument> getAllDocuments() {
		return allDocuments;
	}
	/**
	 * @param allDocuments the allDocuments to set
	 */
	public void setAllDocuments(Collection<SPNDocument> allDocuments) {
		this.allDocuments = allDocuments;
	}
	public List<SPNExclusionsVO> getVendorExclusions() {
		return vendorExclusions;
	}
	public void setVendorExclusions(List<SPNExclusionsVO> vendorExclusions) {
		this.vendorExclusions = vendorExclusions;
	}
	public List<SPNExclusionsVO> getResExclusions() {
		return resExclusions;
	}
	public void setResExclusions(List<SPNExclusionsVO> resExclusions) {
		this.resExclusions = resExclusions;
	}
	/**
	 * @return the primaryIndustry
	 */
	public List<LookupPrimaryIndustry> getPrimaryIndustry() {
		return primaryIndustry;
	}
	/**
	 * @param primaryIndustry the primaryIndustry to set
	 */
	public void setPrimaryIndustry(List<LookupPrimaryIndustry> primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	
	
	
}