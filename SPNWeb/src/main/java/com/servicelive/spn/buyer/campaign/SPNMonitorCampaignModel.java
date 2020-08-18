package com.servicelive.spn.buyer.campaign;

import com.servicelive.spn.buyer.common.SPNCreateModel;

/**
 * 
 * 
 *
 */
public class SPNMonitorCampaignModel extends SPNCreateModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer campaignId;
	private String approveComments;
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	private String stateCd;
	private String marketId;
	//R11_0 SL_19387 For setting the feature set "BACKGROUND_CHECK_RECERTIFICATION"
	private Boolean recertificationBuyerFeatureInd;
	public Boolean getRecertificationBuyerFeatureInd() {
		return recertificationBuyerFeatureInd;
	}

	public void setRecertificationBuyerFeatureInd(
			Boolean recertificationBuyerFeatureInd) {
		this.recertificationBuyerFeatureInd = recertificationBuyerFeatureInd;
	}
	
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	/**
	 * @return the campaignId
	 */
	public Integer getCampaignId() {
		return campaignId;
	}
	/**
	 * @param campaignId the campaignId to set
	 */
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	/**
	 * @return the approveComments
	 */
	public String getApproveComments() {
		return approveComments;
	}
	/**
	 * @param approveComments the approveComments to set
	 */
	public void setApproveComments(String approveComments) {
		this.approveComments = approveComments;
	}
	
	
}
