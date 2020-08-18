package com.servicelive.spn.buyer.auditor;

import java.util.List;

import com.servicelive.spn.common.detached.BackgroundFilterVO;
import com.servicelive.spn.core.SPNBaseModel;
import com.servicelive.domain.spn.detached.LookupVO;
import com.servicelive.domain.spn.detached.SPNAuditorSearchCriteriaVO;
import com.servicelive.domain.spn.detached.SPNAuditorSearchExpandCriteriaVO;
import com.servicelive.domain.spn.detached.SPNAuditorSearchResultVO;
import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.domain.spn.network.SPNHeader;
/**
 * 
 * 
 *
 */
public class SPNAuditorSearchModel extends SPNBaseModel
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SPNAuditorSearchCriteriaVO searchCriteriaVO;
	protected List<SPNAuditorSearchResultVO> searchResultsList;
	protected List<LookupMarket> marketList;
	protected List<SPNHeader> spnList;
	protected List<LookupStates> stateList;
	protected List<LookupVO> districtList;
	protected List<LookupSPNWorkflowState> providerFirmMembershipStatusList;
	protected SPNApplicantCounts spnApplicantCounts;
	protected SPNAuditorSearchExpandCriteriaVO expandCriteriaVO;
	
	//SL-19387 -To set value of Advance filter to model(Background Check)
	private BackgroundFilterVO backgroundFilterVO;
	//SL-20367
	private Boolean recertificationBuyerFeature = false;
	

	private Integer sEcho=1;
	private String iTotalRecords="2";
	private String iTotalDisplayRecords="2";
	private String aaData[][];
	
	public Boolean getRecertificationBuyerFeature() {
		return recertificationBuyerFeature;
	}
	public void setRecertificationBuyerFeature(Boolean recertificationBuyerFeature) {
		this.recertificationBuyerFeature = recertificationBuyerFeature;
	}
	/**
	 * 
	 * @return SPNAuditorSearchCriteriaVO
	 */
	public SPNAuditorSearchCriteriaVO getSearchCriteriaVO() {
		return searchCriteriaVO;
	}
	/**
	 * 
	 * @param searchCriteriaVO
	 */
	public void setSearchCriteriaVO(SPNAuditorSearchCriteriaVO searchCriteriaVO) {
		this.searchCriteriaVO = searchCriteriaVO;
	}
	/**
	 * 
	 * @return List<SPNAuditorSearchResultVO>
	 */
	public List<SPNAuditorSearchResultVO> getSearchResultsList() {
		return searchResultsList;
	}
	/**
	 * 
	 * @param searchResultsList
	 */
	public void setSearchResultsList(
			List<SPNAuditorSearchResultVO> searchResultsList) {
		this.searchResultsList = searchResultsList;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupMarket> getMarketList() {
		return marketList;
	}
	/**
	 * 
	 * @param marketList
	 */
	public void setMarketList(List<LookupMarket> marketList) {
		this.marketList = marketList;
	}
	/**
	 * 
	 * @return List
	 */
	public List<SPNHeader> getSpnList() {
		return spnList;
	}
	/**
	 * 
	 * @param spnList
	 */
	public void setSpnList(List<SPNHeader> spnList) {
		this.spnList = spnList;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupStates> getStateList() {
		return stateList;
	}
	/**
	 * 
	 * @param stateList
	 */
	public void setStateList(List<LookupStates> stateList) {
		this.stateList = stateList;
	}
	/**
	 * 
	 * @return SPNApplicantCounts
	 */
	public SPNApplicantCounts getSpnApplicantCounts() {
		return spnApplicantCounts;
	}
	/**
	 * 
	 * @param spnApplicantCounts
	 */
	public void setSpnApplicantCounts(SPNApplicantCounts spnApplicantCounts) {
		this.spnApplicantCounts = spnApplicantCounts;
	}
	/**
	 * 
	 * @return SPNAuditorSearchExpandCriteriaVO
	 */
	public SPNAuditorSearchExpandCriteriaVO getExpandCriteriaVO() {
		return expandCriteriaVO;
	}
	/**
	 * 
	 * @param expandCriteriaVO
	 */
	public void setExpandCriteriaVO(
			SPNAuditorSearchExpandCriteriaVO expandCriteriaVO) {
		this.expandCriteriaVO = expandCriteriaVO;
	}
	/**
	 * @return the districtList
	 */
	public List<LookupVO> getDistrictList() {
		return districtList;
	}
	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookupVO> districtList) {
		this.districtList = districtList;
	}
	/**
	 * @return the providerFirmMembershipStatusList
	 */
	public List<LookupSPNWorkflowState> getProviderFirmMembershipStatusList() {
		return providerFirmMembershipStatusList;
	}
	/**
	 * @param providerFirmMembershipStatusList the providerFirmMembershipStatusList to set
	 */
	public void setProviderFirmMembershipStatusList(
			List<LookupSPNWorkflowState> providerFirmMembershipStatusList) {
		this.providerFirmMembershipStatusList = providerFirmMembershipStatusList;
	}
	
	
	public Integer getsEcho() {
		return sEcho;
	}
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	public String getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(String iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public String getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(String iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public String[][] getAaData() {
		return aaData;
	}
	public void setAaData(String[][] aaData) {
		this.aaData = aaData;
	}
	

	public BackgroundFilterVO getBackgroundFilterVO() {
		return backgroundFilterVO;
	}
	public void setBackgroundFilterVO(BackgroundFilterVO backgroundFilterVO) {
		this.backgroundFilterVO = backgroundFilterVO;
	}
}
