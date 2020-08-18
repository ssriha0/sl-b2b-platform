package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.servicelive.domain.spn.detached.LookupCriteriaDTO;
import com.servicelive.domain.spn.detached.SPNCoverageDTO;
import com.servicelive.domain.spn.detached.SPNPerformanceCriteria;
import com.servicelive.domain.spn.detached.SPNTierDTO;
import com.servicelive.domain.spn.network.SPNHeader;

public class SPNRoutingDTO implements Serializable 
{
	private static final long serialVersionUID = 0L;
	
	//look up criteria list
	private List<LookupCriteriaDTO> criteriaDTO = new ArrayList<LookupCriteriaDTO>();
	
	//SPN hdr data
	private SPNHeader spnHdr;
	private Integer aliasSpnId;
	
	//performance criteria list
	private List<SPNPerformanceCriteria> performanceCriteria = new ArrayList<SPNPerformanceCriteria>();
	
	//routing tier details
	private List<SPNTierDTO> spnTiers = new ArrayList<SPNTierDTO>();
	
	//coverage details
	private List<SPNCoverageDTO> coveragelist = new ArrayList<SPNCoverageDTO>();
	
	//filter lists
	private Map<String, String> stateList = new TreeMap<String, String>();
	private Map<String, Integer> marketList = new TreeMap<String, Integer>();
	private List<String> zipList = new ArrayList<String>();

	private Date modifiedDate;
	private int criteriaCount = 0;
	private int providerCount = 0;
	private int firmCount = 0;
	private String action = "";
	
	//input filter lists
	private List<String> selectedStates = new ArrayList<String>();
	private List<Integer> selectedMarkets = new ArrayList<Integer>();
	private String selectedZip;
	
	//for edit-cancel
	private List<SPNPerformanceCriteria> savedCriteria = new ArrayList<SPNPerformanceCriteria>();
	private String savedCriteriaLevel;
	private String savedCriteriaTimeframe;
	private int savedProvCount = -1;
	private int savedFirmCount = -1;
	private List<SPNCoverageDTO> savedCoverage = new ArrayList<SPNCoverageDTO>();
	private Map<String, String> savedStates = new TreeMap<String, String>();
	private Map<String, Integer> savedMarkets = new TreeMap<String, Integer>();
	private List<String> savedZips = new ArrayList<String>();
	private int disclaimer = 0;
	
	
	public int getSavedProvCount() {
		return savedProvCount;
	}

	public void setSavedProvCount(int savedProvCount) {
		this.savedProvCount = savedProvCount;
	}

	public int getSavedFirmCount() {
		return savedFirmCount;
	}

	public void setSavedFirmCount(int savedFirmCount) {
		this.savedFirmCount = savedFirmCount;
	}

	public List<SPNCoverageDTO> getSavedCoverage() {
		return savedCoverage;
	}

	public void setSavedCoverage(List<SPNCoverageDTO> savedCoverage) {
		this.savedCoverage = savedCoverage;
	}

	public Map<String, String> getSavedStates() {
		return savedStates;
	}

	public void setSavedStates(Map<String, String> savedStates) {
		this.savedStates = savedStates;
	}

	public Map<String, Integer> getSavedMarkets() {
		return savedMarkets;
	}

	public void setSavedMarkets(Map<String, Integer> savedMarkets) {
		this.savedMarkets = savedMarkets;
	}

	public List<String> getSavedZips() {
		return savedZips;
	}

	public void setSavedZips(List<String> savedZips) {
		this.savedZips = savedZips;
	}

	public String getSavedCriteriaLevel() {
		return savedCriteriaLevel;
	}

	public void setSavedCriteriaLevel(String savedCriteriaLevel) {
		this.savedCriteriaLevel = savedCriteriaLevel;
	}

	public String getSavedCriteriaTimeframe() {
		return savedCriteriaTimeframe;
	}

	public void setSavedCriteriaTimeframe(String savedCriteriaTimeframe) {
		this.savedCriteriaTimeframe = savedCriteriaTimeframe;
	}

	public int getCriteriaCount() {
		return criteriaCount;
	}

	public void setCriteriaCount(int criteriaCount) {
		this.criteriaCount = criteriaCount;
	}

	public SPNHeader getSpnHdr() {
		return spnHdr;
	}

	public void setSpnHdr(SPNHeader spnHdr) {
		this.spnHdr = spnHdr;
	}

	public List<SPNPerformanceCriteria> getPerformanceCriteria() {
		return performanceCriteria;
	}

	public void setPerformanceCriteria(
			List<SPNPerformanceCriteria> performanceCriteria) {
		this.performanceCriteria = performanceCriteria;
	}

	public List<SPNTierDTO> getSpnTiers() {
		return spnTiers;
	}

	public void setSpnTiers(List<SPNTierDTO> spnTiers) {
		this.spnTiers = spnTiers;
	}

	public List<SPNCoverageDTO> getCoveragelist() {
		return coveragelist;
	}

	public void setCoveragelist(List<SPNCoverageDTO> coveragelist) {
		this.coveragelist = coveragelist;
	}

	public List<LookupCriteriaDTO> getCriteriaDTO() {
		return criteriaDTO;
	}

	public void setCriteriaDTO(List<LookupCriteriaDTO> criteriaDTO) {
		this.criteriaDTO = criteriaDTO;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getProviderCount() {
		return providerCount;
	}

	public void setProviderCount(int providerCount) {
		this.providerCount = providerCount;
	}

	public int getFirmCount() {
		return firmCount;
	}

	public void setFirmCount(int firmCount) {
		this.firmCount = firmCount;
	}
	
	public Map<String, String> getStateList() {
		return stateList;
	}

	public void setStateList(Map<String, String> stateList) {
		this.stateList = stateList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Map<String, Integer> getMarketList() {
		return marketList;
	}

	public void setMarketList(Map<String, Integer> marketList) {
		this.marketList = marketList;
	}

	public List<String> getZipList() {
		return zipList;
	}

	public void setZipList(List<String> zipList) {
		this.zipList = zipList;
	}

	public List<String> getSelectedStates() {
		return selectedStates;
	}

	public void setSelectedStates(List<String> selectedStates) {
		this.selectedStates = selectedStates;
	}

	public List<Integer> getSelectedMarkets() {
		return selectedMarkets;
	}

	public void setSelectedMarkets(List<Integer> selectedMarkets) {
		this.selectedMarkets = selectedMarkets;
	}

	public String getSelectedZip() {
		return selectedZip;
	}

	public void setSelectedZip(String selectedZip) {
		this.selectedZip = selectedZip;
	}

	public List<SPNPerformanceCriteria> getSavedCriteria() {
		return savedCriteria;
	}

	public void setSavedCriteria(List<SPNPerformanceCriteria> savedCriteria) {
		this.savedCriteria = savedCriteria;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getAliasSpnId() {
		return aliasSpnId;
	}

	public void setAliasSpnId(Integer aliasSpnId) {
		this.aliasSpnId = aliasSpnId;
	}

	public int getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(int disclaimer) {
		this.disclaimer = disclaimer;
	}


}
