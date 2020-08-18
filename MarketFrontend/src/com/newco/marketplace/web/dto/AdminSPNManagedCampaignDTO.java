package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;

public class AdminSPNManagedCampaignDTO extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1872770111600534507L;

	private List<SPNCampaignVO> campaignList = new ArrayList();
	private SPNCampaignVO newCampaign = new SPNCampaignVO();
	private Integer selectedSPN = -1;
	private Integer theCurrentSpnId = -1;
	private Integer selectedCampaignId;
	private Integer marketId;
	
	private List<SPNHeaderVO> serviceProviderNetworks = new ArrayList();

	public List<SPNCampaignVO> getCampaignList() {
		return campaignList;
	}

	public void setCampaignList(List<SPNCampaignVO> campaignList) {
		this.campaignList = campaignList;
	}

	public SPNCampaignVO getNewCampaign() {
		return newCampaign;
	}

	public void setNewCampaign(SPNCampaignVO newCampaign) {
		this.newCampaign = newCampaign;
	}

	public Integer getSelectedSPN() {
		return selectedSPN;
	}

	public void setSelectedSPN(Integer selectedSPN) {
		this.selectedSPN = selectedSPN;
	}

	public List<SPNHeaderVO> getServiceProviderNetworks() {
		return serviceProviderNetworks;
	}

	public void setServiceProviderNetworks(List<SPNHeaderVO> serviceProviderNetworks) {
		this.serviceProviderNetworks = serviceProviderNetworks;
	}

	public Integer getTheCurrentSpnId() {
		return theCurrentSpnId;
	}

	public void setTheCurrentSpnId(Integer theCurrentSpnId) {
		this.theCurrentSpnId = theCurrentSpnId;
	}

	public Integer getSelectedCampaignId() {
		return selectedCampaignId;
	}

	public void setSelectedCampaignId(Integer selectedCampaignId) {
		this.selectedCampaignId = selectedCampaignId;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

}
