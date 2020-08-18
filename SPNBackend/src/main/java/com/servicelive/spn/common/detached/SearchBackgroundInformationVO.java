/**
 * 
 */
package com.servicelive.spn.common.detached;

import java.io.Serializable;
import java.util.List;


//SL-19387
//Value Object class for setting values need to fetch details for Background Check.
public class SearchBackgroundInformationVO implements Serializable {

	
	private static final long serialVersionUID = 20090317;

	private Integer spnId;
	String stateCd;
	String status;
	
	

	private Integer buyerId;
	private String selectedReCertification;
	private String selectedSLBackgroundStatus;
	private String selectedSystemAction; 
	private List<String> selectedProviderFirmIds;
	private String pastDue;
	
	private String selectedReCertificationAll;
	private String selectedSLBackgroundStatusAll;
	private String selectedSystemActionAll; 
	
	
	
	private String sSearch;
	private int startIndex;//iDispalyStart
	private int numberOfRecords;
	private String sortColumnName;
	private String sortOrder;
	
	private String providerFirmName;
	private String providerFirmNumber;
	private Integer marketId;
	private String zipCode;
	private Integer districtId;
	
	
	
	public String getProviderFirmName() {
		return providerFirmName;
	}

	public void setProviderFirmName(String providerFirmName) {
		this.providerFirmName = providerFirmName;
	}

	public String getProviderFirmNumber() {
		return providerFirmNumber;
	}

	public void setProviderFirmNumber(String providerFirmNumber) {
		this.providerFirmNumber = providerFirmNumber;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getPastDue() {
		return pastDue;
	}

	public void setPastDue(String pastDue) {
		this.pastDue = pastDue;
	}



	public List<String> getSelectedProviderFirmIds() {
		return selectedProviderFirmIds;
	}

	public void setSelectedProviderFirmIds(List<String> selectedProviderFirmIds) {
		this.selectedProviderFirmIds = selectedProviderFirmIds;
	}

	public String getStateCd() {
		return stateCd;
	}

	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	
 
	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	
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

	public String getSelectedReCertification() {
		return selectedReCertification;
	}

	public void setSelectedReCertification(String selectedReCertification) {
		this.selectedReCertification = selectedReCertification;
	}

	public String getSelectedSLBackgroundStatus() {
		return selectedSLBackgroundStatus;
	}

	public void setSelectedSLBackgroundStatus(String selectedSLBackgroundStatus) {
		this.selectedSLBackgroundStatus = selectedSLBackgroundStatus;
	}

	public String getSelectedSystemAction() {
		return selectedSystemAction;
	}

	public void setSelectedSystemAction(String selectedSystemAction) {
		this.selectedSystemAction = selectedSystemAction;
	}

	public String getSelectedReCertificationAll() {
		return selectedReCertificationAll;
	}

	public void setSelectedReCertificationAll(String selectedReCertificationAll) {
		this.selectedReCertificationAll = selectedReCertificationAll;
	}

	public String getSelectedSLBackgroundStatusAll() {
		return selectedSLBackgroundStatusAll;
	}

	public void setSelectedSLBackgroundStatusAll(
			String selectedSLBackgroundStatusAll) {
		this.selectedSLBackgroundStatusAll = selectedSLBackgroundStatusAll;
	}

	public String getSelectedSystemActionAll() {
		return selectedSystemActionAll;
	}

	public void setSelectedSystemActionAll(String selectedSystemActionAll) {
		this.selectedSystemActionAll = selectedSystemActionAll;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
