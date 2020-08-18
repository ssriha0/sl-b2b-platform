package com.newco.marketplace.dto.vo.spn;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class SPNCampaignVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4384098284094230420L;
	private Integer spnId;
	private Integer campaignId;
	private Date campaignStateDate;
	private Date campaignEndDate;

	private boolean campaignIsTargetedToAllMarkets;
	private Integer totalProviderCnt;
	private Integer marketId;
	private Integer allMarkets;
	private String marketName;
	private Integer deleteInd;
	
	public SPNCampaignVO() {}
	
	
	public Date getCampaignEndDate() {
		return campaignEndDate;
	}
	public void setCampaignEndDate(Date campaignEndDate) {
		this.campaignEndDate = campaignEndDate;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	
	public boolean isCampaignIsTargetedToAllMarkets() {
		return campaignIsTargetedToAllMarkets;
	}
	public void setCampaignIsTargetedToAllMarkets(
			boolean campaignIsTargetedToAllMarkets) {
		this.campaignIsTargetedToAllMarkets = campaignIsTargetedToAllMarkets;
		if (campaignIsTargetedToAllMarkets) {
			allMarkets = new Integer(1);
		} else {
			allMarkets = new Integer(0);
		}
	}
	public Date getCampaignStateDate() {
		return campaignStateDate;
	}
	public void setCampaignStateDate(Date campaignStateDate) {
		this.campaignStateDate = campaignStateDate;
	}
	
	public Integer getTotalProviderCnt() {
		return totalProviderCnt;
	}
	public void setTotalProviderCnt(Integer totalProviderCnt) {
		this.totalProviderCnt = totalProviderCnt;
	}


	public Integer getMarketId() {
		return marketId;
	}


	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Integer getDeleteInd() {
		return deleteInd;
	}


	public void setDeleteInd(Integer deleteInd) {
		this.deleteInd = deleteInd;
	}


	public String getMarketName() {
		return marketName;
	}


	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}


	public Integer getSpnId() {
		return spnId;
	}


	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}


	public Integer getAllMarkets() {
		return allMarkets;
	}


	public void setAllMarkets(Integer allMarkets) {
		this.allMarkets = allMarkets;
		if (null != allMarkets && allMarkets.intValue() == 1) {
			this.campaignIsTargetedToAllMarkets = true;
		} else {
			this.campaignIsTargetedToAllMarkets = false;
		}
	}
}
