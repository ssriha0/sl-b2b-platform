package com.newco.marketplace.dto.vo.zipcoverage;

import java.util.List;

public class ZipCoverageFiltersDTO {
	
	List<StateNameDTO> stateNameList;
	List<ZipcodeDTO> zipList;
	List<ProviderListDTO> providersList;
	List<BuyerSpnListDTO> spnList;
	List<ProviderListDTO> spnProvidersList;
	List<QuestionsAnswersDTO> faqList;
	List<StateNameDTO> spnStateNameList;
	List<ZipcodeDTO> spnZipList;
	String mapboxUrl;
	
	public List<StateNameDTO> getStateNameList() {
		return stateNameList;
	}
	public void setStateNameList(List<StateNameDTO> stateNameList) {
		this.stateNameList = stateNameList;
	}
	public List<ZipcodeDTO> getZipList() {
		return zipList;
	}
	public void setZipList(List<ZipcodeDTO> zipList) {
		this.zipList = zipList;
	}
	public List<ProviderListDTO> getProvidersList() {
		return providersList;
	}
	public void setProvidersList(List<ProviderListDTO> providersList) {
		this.providersList = providersList;
	}
	public List<BuyerSpnListDTO> getSpnList() {
		return spnList;
	}
	public void setSpnList(List<BuyerSpnListDTO> spnList) {
		this.spnList = spnList;
	}
	public List<ProviderListDTO> getSpnProvidersList() {
		return spnProvidersList;
	}
	public void setSpnProvidersList(List<ProviderListDTO> spnProvidersList) {
		this.spnProvidersList = spnProvidersList;
	}
	
	public List<StateNameDTO> getSpnStateNameList() {
		return spnStateNameList;
	}
	public void setSpnStateNameList(List<StateNameDTO> spnStateNameList) {
		this.spnStateNameList = spnStateNameList;
	}
	public List<ZipcodeDTO> getSpnZipList() {
		return spnZipList;
	}
	public void setSpnZipList(List<ZipcodeDTO> spnZipList) {
		this.spnZipList = spnZipList;
	}
	public List<QuestionsAnswersDTO> getFaqList() {
		return faqList;
	}
	public void setFaqList(List<QuestionsAnswersDTO> faqList) {
		this.faqList = faqList;
	}
	public String getMapboxUrl() {
		return mapboxUrl;
	}
	public void setMapboxUrl(String mapboxUrl) {
		this.mapboxUrl = mapboxUrl;
	}
	

}
