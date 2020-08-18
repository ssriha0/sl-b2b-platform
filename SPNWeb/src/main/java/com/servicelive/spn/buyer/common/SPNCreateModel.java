package com.servicelive.spn.buyer.common;

import java.util.List;

import com.servicelive.spn.core.SPNBaseModel;
import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.domain.spn.detached.CampaignMonitorRowVO;
import com.servicelive.domain.spn.network.SPNHeader;
/**
 * 
 * 
 *
 */
public abstract class SPNCreateModel extends SPNBaseModel
{	
	
	protected List<CampaignMonitorRowVO> campaignList;
	protected ApprovalModel approvalItems = null;
	protected SPNLookupVO lookupsVO = null;
	protected SPNHeader spnHeader = null;
	protected Integer buyerId = null;
	protected SPNLookupVO selectedApprovalItemsVO = null;
	
	/**
	 * 
	 * @return SPNHeader
	 */
	public SPNHeader getSpnHeader()
	{
		return spnHeader;
	}
	/**
	 * 
	 * @param spnHeader
	 */
	public void setSpnHeader(SPNHeader spnHeader)
	{
		this.spnHeader = spnHeader;
	}
	
	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
			
	/**
	 * @return the approvalItems
	 */
	public ApprovalModel getApprovalItems() {
		return approvalItems;
	}

	/**
	 * @param approvalItems the approvalItems to set
	 */
	public void setApprovalItems(ApprovalModel approvalItems) {
		this.approvalItems = approvalItems;
	}

	/**
	 * @return the lookupsVO
	 */
	public SPNLookupVO getLookupsVO() {
		return lookupsVO;
	}

	/**
	 * @param lookupsVO the lookupsVO to set
	 */
	public void setLookupsVO(SPNLookupVO lookupsVO) {
		this.lookupsVO = lookupsVO;
	}

	/**
	 * @return the campaignList
	 */
	public List<CampaignMonitorRowVO> getCampaignList() {
		return campaignList;
	}

	/**
	 * @param campaignList the campaignList to set
	 */
	public void setCampaignList(List<CampaignMonitorRowVO> campaignList) {
		this.campaignList = campaignList;
	}
	/**
	 * 
	 * @return SPNLookupVO
	 */
	public SPNLookupVO getSelectedApprovalItemsVO() {
		return selectedApprovalItemsVO;
	}
	/**
	 * 
	 * @param selectedApprovalItemsVO
	 */
	public void setSelectedApprovalItemsVO(SPNLookupVO selectedApprovalItemsVO) {
		this.selectedApprovalItemsVO = selectedApprovalItemsVO;
	}
}
