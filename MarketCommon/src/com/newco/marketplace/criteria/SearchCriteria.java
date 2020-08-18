package com.newco.marketplace.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 00:51:57 $
 */

/*
 * Maintenance History
 * $Log: SearchCriteria.java,v $
 * Revision 1.4  2008/04/26 00:51:57  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.12.1  2008/04/23 11:42:06  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.3  2008/04/23 05:17:42  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.2  2008/02/05 20:24:45  spate05
 * serializing for session replication
 *
 * Revision 1.1.16.1  2008/02/05 20:22:17  spate05
 * serializing for session replication
 *
 * Revision 1.1  2007/11/27 00:20:50  mhaye05
 * Initial check in
 *
 */
public class SearchCriteria implements ICriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7558543784829111444L;
	private String searchType;
	private String searchValue;
	private List<String> soIds;
	private String groupId;
	private boolean searchFromOrderGroupManager = false;
	private List<String> selectedStates;
	private List<String> selectedSkills;
	private List<Integer> selectedMarkets;
	private List<String> selectedAcceptanceTypes;
	private List<String> selectedPricingTypes;
	private List<String> selectedAssignmentTypes;
	private List<String> selectedPostingMethods;
	//Priority 18 issue changes
	private Map<String,ArrayList<String>> selectedCustomRefs;
	private List<String> selectedCheckNumbers;
	private List<String> selectedCustomerNames;
	private List<String> selectedPhones;
	private List<String> selectedProviderFirmIds;
	private List<String> selectedServiceOrderIds;
	private List<String> selectedServiceProIds;
	private List<String> selectedServiceProNames;
	private List<String> selectedZipCodes;
	private List<String> selectedMainCatIdList;
	private List<String> selectedCatAndSubCatIdList;
	private List<String>  startDateList;
	private List<String>  endDateList;
	private List<String> autocloseRuleList;
	private Map<String,String> selectedStatusSubStatus;
	private String filterTemplate;
	private boolean filterNameSearch;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer pageLimit;
	
	//R12_1
	//SL-20362
	private List<String> selectedPendingReschedule;
	//R12_1
	//SL-20554
	private List<String> selectedClosureMethod;	
	

	public List<String> getSelectedPendingReschedule() {
			return selectedPendingReschedule;
		}
	public void setSelectedPendingReschedule(List<String> selectedPendingReschedule) {
			this.selectedPendingReschedule = selectedPendingReschedule;
		}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}

	public Map<String, String> getSelectedStatusSubStatus() {
		return selectedStatusSubStatus;
	}

	public void setSelectedStatusSubStatus(
			Map<String, String> selectedStatusSubStatus) {
		this.selectedStatusSubStatus = selectedStatusSubStatus;
	}

	public List<String> getSelectedStates() {
		return selectedStates;
	}

	public void setSelectedStates(List<String> selectedStates) {
		this.selectedStates = selectedStates;
	}

	public List<String> getSelectedSkills() {
		return selectedSkills;
	}

	public void setSelectedSkills(List<String> selectedSkills) {
		this.selectedSkills = selectedSkills;
	}

	public List<Integer> getSelectedMarkets() {
		return selectedMarkets;
	}

	public void setSelectedMarkets(List<Integer> selectedMarkets) {
		this.selectedMarkets = selectedMarkets;
	}

	public Map<String, ArrayList<String>> getSelectedCustomRefs() {
		return selectedCustomRefs;
	}
	public void setSelectedCustomRefs(
			Map<String, ArrayList<String>> selectedCustomRefs) {
		this.selectedCustomRefs = selectedCustomRefs;
	}
	public List<String> getSelectedCheckNumbers() {
		return selectedCheckNumbers;
	}

	public void setSelectedCheckNumbers(List<String> selectedCheckNumbers) {
		this.selectedCheckNumbers = selectedCheckNumbers;
	}

	public List<String> getSelectedCustomerNames() {
		return selectedCustomerNames;
	}

	public void setSelectedCustomerNames(List<String> selectedCustomerNames) {
		this.selectedCustomerNames = selectedCustomerNames;
	}

	public List<String> getSelectedPhones() {
		return selectedPhones;
	}

	public void setSelectedPhones(List<String> selectedPhones) {
		this.selectedPhones = selectedPhones;
	}

	public List<String> getSelectedProviderFirmIds() {
		return selectedProviderFirmIds;
	}

	public void setSelectedProviderFirmIds(List<String> selectedProviderFirmIds) {
		this.selectedProviderFirmIds = selectedProviderFirmIds;
	}

	public List<String> getSelectedServiceOrderIds() {
		return selectedServiceOrderIds;
	}

	public void setSelectedServiceOrderIds(List<String> selectedServiceOrderIds) {
		this.selectedServiceOrderIds = selectedServiceOrderIds;
	}

	public List<String> getSelectedServiceProIds() {
		return selectedServiceProIds;
	}

	public void setSelectedServiceProIds(List<String> selectedServiceProIds) {
		this.selectedServiceProIds = selectedServiceProIds;
	}

	public List<String> getSelectedServiceProNames() {
		return selectedServiceProNames;
	}

	public void setSelectedServiceProNames(List<String> selectedServiceProNames) {
		this.selectedServiceProNames = selectedServiceProNames;
	}

	public List<String> getSelectedZipCodes() {
		return selectedZipCodes;
	}

	public void setSelectedZipCodes(List<String> selectedZipCodes) {
		this.selectedZipCodes = selectedZipCodes;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public List<String> getSoIds() {
		return soIds;
	}

	public void setSoIds(List<String> soIds) {
		this.soIds = soIds;
	}

	public boolean isSet() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		this.setSelectedStates(null);
		this.setSelectedSkills(null);
		this.setSelectedMarkets(null);
		this.setSelectedCheckNumbers(null);
		this.setSelectedCustomerNames(null);
		this.setSelectedPhones(null);
		this.setSelectedProviderFirmIds(null);
		this.setSelectedServiceOrderIds(null);
		this.setSelectedServiceProIds(null);
		this.setSelectedServiceProNames(null);
		this.setSelectedZipCodes(null);
		this.setStartDateList(null);
		this.setEndDateList(null);
		this.setSelectedMainCatIdList(null);
		this.setSelectedCatAndSubCatIdList(null);
		this.setSelectedStatusSubStatus(null);
		this.setSelectedCustomRefs(null);				
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isSearchFromOrderGroupManager() {
		return searchFromOrderGroupManager;
	}

	public void setSearchFromOrderGroupManager(boolean searchFromOrderGroupManager) {
		this.searchFromOrderGroupManager = searchFromOrderGroupManager;
	}	

	public List<String> getSelectedMainCatIdList() {
		return selectedMainCatIdList;
	}

	public void setSelectedMainCatIdList(List<String> selectedMainCatIdList) {
		this.selectedMainCatIdList = selectedMainCatIdList;
	}

	public List<String> getSelectedCatAndSubCatIdList() {
		return selectedCatAndSubCatIdList;
	}

	public void setSelectedCatAndSubCatIdList(
			List<String> selectedCatAndSubCatIdList) {
		this.selectedCatAndSubCatIdList = selectedCatAndSubCatIdList;
	}

	public List<String> getStartDateList() {
		return startDateList;
	}

	public void setStartDateList(List<String> startDateList) {
		this.startDateList = startDateList;
	}

	public List<String> getEndDateList() {
		return endDateList;
	}

	public void setEndDateList(List<String> endDateList) {
		this.endDateList = endDateList;
	}

	public String getFilterTemplate() {
		return filterTemplate;
	}

	public void setFilterTemplate(String filterTemplate) {
		this.filterTemplate = filterTemplate;
	}

	public boolean isFilterNameSearch() {
		return filterNameSearch;
	}

	public void setFilterNameSearch(boolean filterNameSearch) {
		this.filterNameSearch = filterNameSearch;
	}

	public List<String> getAutocloseRuleList() {
		return autocloseRuleList;
	}

	public void setAutocloseRuleList(List<String> autocloseRuleList) {
		this.autocloseRuleList = autocloseRuleList;
	}

	public List<String> getSelectedAcceptanceTypes() {
		return selectedAcceptanceTypes;
	}

	public void setSelectedAcceptanceTypes(List<String> selectedAcceptanceTypes) {
		this.selectedAcceptanceTypes = selectedAcceptanceTypes;
	}

	public List<String> getSelectedPricingTypes() {
		return selectedPricingTypes;
	}

	public void setSelectedPricingTypes(List<String> selectedPricingTypes) {
		this.selectedPricingTypes = selectedPricingTypes;
	}

	public List<String> getSelectedAssignmentTypes() {
		return selectedAssignmentTypes;
	}

	public void setSelectedAssignmentTypes(List<String> selectedAssignmentTypes) {
		this.selectedAssignmentTypes = selectedAssignmentTypes;
	}

	public List<String> getSelectedPostingMethods() {
		return selectedPostingMethods;
	}

	public void setSelectedPostingMethods(List<String> selectedPostingMethods) {
		this.selectedPostingMethods = selectedPostingMethods;
	}
	public List<String> getSelectedClosureMethod() {
		return selectedClosureMethod;
	}
	public void setSelectedClosureMethod(List<String> selectedClosureMethod) {
		this.selectedClosureMethod = selectedClosureMethod;
	}

	

}
