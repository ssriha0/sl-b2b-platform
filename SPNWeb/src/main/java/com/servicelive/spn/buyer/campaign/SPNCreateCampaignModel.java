package com.servicelive.spn.buyer.campaign;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.domain.spn.campaign.CampaignHeader;
import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.domain.spn.detached.ProviderMatchingCountsVO;
import com.servicelive.domain.spn.network.ReleaseTiersVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.buyer.common.SPNCreateModel;
/**
 * 
 * 
 *
 */
public class SPNCreateCampaignModel extends SPNCreateModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SPNHeader> spnList;
	private CampaignHeader campaignHeader = null;
	private String startDate = null;
	private String endDate = null;
	private ProviderMatchingCountsVO providersMatchingSPN = null;
	private ProviderMatchingCountsVO providersMatchingCampaign = null;
	private ApprovalModel spnApprovalItems = null;
	
	private List<ProviderFirmSearchResultDTO> providerFirmList = new ArrayList<ProviderFirmSearchResultDTO>();
	private List<String> invitedProviderCheckboxes = new ArrayList<String>();
	private List<Integer> invitedProviderIds = new ArrayList<Integer>();
	
	// Release Tiers
	private List<ReleaseTiersVO> releaseTiers = new ArrayList<ReleaseTiersVO>();
	
	// Invite Specific Provider Firm
	private String startDate2 = null;
	private String endDate2 = null;
	private String inviteCampaignName = null;
	private String spnId = null;
	private Integer campaignId = null;
	private List<String> providerCheckboxList = new ArrayList<String>();
	//R11_0 SL_19387 For setting the feature set "BACKGROUND_CHECK_RECERTIFICATION"
	private Boolean recertificationBuyerFeatureInd;
	
	public Boolean getRecertificationBuyerFeatureInd() {
		return recertificationBuyerFeatureInd;
	}
	public void setRecertificationBuyerFeatureInd(
			Boolean recertificationBuyerFeatureInd) {
		this.recertificationBuyerFeatureInd = recertificationBuyerFeatureInd;
	}
	public List<ProviderFirmSearchResultDTO> getProviderFirmList()
	{
		return providerFirmList;
	}
	public void setProviderFirmList(List<ProviderFirmSearchResultDTO> providerFirmList)
	{
		this.providerFirmList = providerFirmList;
	}
	/**
	 * @return the spnList
	 */
	public List<SPNHeader> getSpnList() {
		return spnList;
	}
	/**
	 * @param spnList the spnList to set
	 */
	public void setSpnList(List<SPNHeader> spnList) {
		this.spnList = spnList;
	}
	/**
	 * @return the campaignHeader
	 */
	public CampaignHeader getCampaignHeader() {
		return campaignHeader;
	}
	/**
	 * @param campaignHeader the campaignHeader to set
	 */
	public void setCampaignHeader(CampaignHeader campaignHeader) {
		this.campaignHeader = campaignHeader;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the providersMatchingSPN
	 */
	public ProviderMatchingCountsVO getProvidersMatchingSPN() {
		return providersMatchingSPN;
	}
	/**
	 * @param providersMatchingSPN the providersMatchingSPN to set
	 */
	public void setProvidersMatchingSPN(
			ProviderMatchingCountsVO providersMatchingSPN) {
		this.providersMatchingSPN = providersMatchingSPN;
	}
	/**
	 * @return the providersMatchingCampaign
	 */
	public ProviderMatchingCountsVO getProvidersMatchingCampaign() {
		return providersMatchingCampaign;
	}
	/**
	 * @param providersMatchingCampaign the providersMatchingCampaign to set
	 */
	public void setProvidersMatchingCampaign(
			ProviderMatchingCountsVO providersMatchingCampaign) {
		this.providersMatchingCampaign = providersMatchingCampaign;
	}
	/**
	 * 
	 * @return ApprovalModel
	 */
	public ApprovalModel getSpnApprovalItems() {
		return spnApprovalItems;
	}
	/**
	 * 
	 * @param spnApprovalItems
	 */
	public void setSpnApprovalItems(ApprovalModel spnApprovalItems) {
		this.spnApprovalItems = spnApprovalItems;
	}
	public List<String> getInvitedProviderCheckboxes()
	{
		return invitedProviderCheckboxes;
	}
	public void setInvitedProviderCheckboxes(List<String> invitedProviderCheckboxes)
	{
		this.invitedProviderCheckboxes = invitedProviderCheckboxes;
	}
	public List<Integer> getInvitedProviderIds()
	{
		return invitedProviderIds;
	}
	public void setInvitedProviderIds(List<Integer> invitedProviderIds)
	{
		this.invitedProviderIds = invitedProviderIds;
	}
	public String getStartDate2() {
		return startDate2;
	}
	public void setStartDate2(String startDate2) {
		this.startDate2 = startDate2;
	}
	public String getEndDate2() {
		return endDate2;
	}
	public void setEndDate2(String endDate2) {
		this.endDate2 = endDate2;
	}
	public String getSpnId() {
		return spnId;
	}
	public void setSpnId(String spnId) {
		this.spnId = spnId;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	public List<String> getProviderCheckboxList() {
		return providerCheckboxList;
	}
	public void setProviderCheckboxList(List<String> providerCheckboxList) {
		this.providerCheckboxList = providerCheckboxList;
	}
	public String getInviteCampaignName() {
		return inviteCampaignName;
	}
	public void setInviteCampaignName(String inviteCampaignName) {
		this.inviteCampaignName = inviteCampaignName;
	}
	public List<ReleaseTiersVO> getReleaseTiers() {
		return releaseTiers;
	}
	public void setReleaseTiers(List<ReleaseTiersVO> releaseTiers) {
		this.releaseTiers = releaseTiers;
	}
}
