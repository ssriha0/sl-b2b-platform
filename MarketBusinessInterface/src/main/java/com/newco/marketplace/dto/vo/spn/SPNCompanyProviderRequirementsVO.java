package com.newco.marketplace.dto.vo.spn;

import java.util.List;
import java.util.Map;

public class SPNCompanyProviderRequirementsVO {
	
	private Map<String, String> insuranceMap;
	private Map<String, SPNProviderRequirementsVO> credentialMap;
	private Map<String, SPNProviderRequirementsVO> languageMap;
	private Map<String, SPNProviderRequirementsVO> locationMap;
	private Map<String, SPNProviderRequirementsVO> marketMap;
	private Map<String, SPNProviderRequirementsVO> ratingMap;
	private Map<String, SPNProviderRequirementsVO> completedSOMap;
	private List<SPNSkillsAndServicesVO> servicesList; 
	private Map<String, SPNProviderRequirementsVO> credMap;
	private Integer totalResourceCount;
	//R11.0 SL-19387
	private Map<String, SPNProviderRequirementsVO> backgroundCheckMap;
	public Map<String, SPNProviderRequirementsVO> getBackgroundCheckMap() {
		return backgroundCheckMap;  
	}
	public void setBackgroundCheckMap(
			Map<String, SPNProviderRequirementsVO> backgroundCheckMap) {
		this.backgroundCheckMap = backgroundCheckMap;
	}
	
	
	public Integer getTotalResourceCount() {
		return totalResourceCount;
	}
	public void setTotalResourceCount(Integer totalResourceCount) {
		this.totalResourceCount = totalResourceCount;
	}
	public Map<String, SPNProviderRequirementsVO> getCredentialMap() {
		return credentialMap;
	}
	public void setCredentialMap(
			Map<String, SPNProviderRequirementsVO> credentialMap) {
		this.credentialMap = credentialMap;
	}
	public Map<String, SPNProviderRequirementsVO> getCredMap() {
		return credMap;
	}
	public void setCredMap(Map<String, SPNProviderRequirementsVO> credMap) {
		this.credMap = credMap;
	}
	
	public Map<String, SPNProviderRequirementsVO> getLanguageMap() {
		return languageMap;
	}
	public void setLanguageMap(Map<String, SPNProviderRequirementsVO> languageMap) {
		this.languageMap = languageMap;
	}
	public Map<String, SPNProviderRequirementsVO> getLocationMap() {
		return locationMap;
	}
	public void setLocationMap(Map<String, SPNProviderRequirementsVO> locationMap) {
		this.locationMap = locationMap;
	}
	public Map<String, SPNProviderRequirementsVO> getMarketMap() {
		return marketMap;
	}
	public void setMarketMap(Map<String, SPNProviderRequirementsVO> marketMap) {
		this.marketMap = marketMap;
	}
	public Map<String, SPNProviderRequirementsVO> getRatingMap() {
		return ratingMap;
	}
	public void setRatingMap(Map<String, SPNProviderRequirementsVO> ratingMap) {
		this.ratingMap = ratingMap;
	}
	public Map<String, SPNProviderRequirementsVO> getCompletedSOMap() {
		return completedSOMap;
	}
	public void setCompletedSOMap(
			Map<String, SPNProviderRequirementsVO> completedSOMap) {
		this.completedSOMap = completedSOMap;
	}
	public List<SPNSkillsAndServicesVO> getServicesList() {
		return servicesList;
	}
	public void setServicesList(List<SPNSkillsAndServicesVO> servicesList) {
		this.servicesList = servicesList;
	}
	public Map<String, String> getInsuranceMap() {
		return insuranceMap;
	}
	public void setInsuranceMap(Map<String, String> insuranceMap) {
		this.insuranceMap = insuranceMap;
	}
}
