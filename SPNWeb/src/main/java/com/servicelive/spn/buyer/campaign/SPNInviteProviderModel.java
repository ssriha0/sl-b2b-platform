package com.servicelive.spn.buyer.campaign;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.spn.buyer.common.SPNCreateModel;

/**
 * 
 * 
 *
 */
public class SPNInviteProviderModel extends SPNCreateModel
{
	private static final long serialVersionUID = 4508365275784473329L;
	private String spnId;
	private String campaignName="foo name";
	private String providerListString;
	private String startDate;
	private String endDate;
	
	private List<ProviderFirmSearchResultDTO> providerFirmList = new ArrayList<ProviderFirmSearchResultDTO>();

	public List<ProviderFirmSearchResultDTO> getProviderFirmList() {
		return providerFirmList;
	}

	public void setProviderFirmList(
			List<ProviderFirmSearchResultDTO> providerFirmList) {
		this.providerFirmList = providerFirmList;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getSpnId() {
		return spnId;
	}

	public void setSpnId(String spnId) {
		this.spnId = spnId;
	}

	public String getProviderListString() {
		return providerListString;
	}

	public void setProviderListString(String providerListString) {
		this.providerListString = providerListString;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	
}
