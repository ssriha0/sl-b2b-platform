package com.servicelive.domain.spn.detached;

import java.io.Serializable;

/**
 * 
 * @author svanloon
 *
 */
public class CampaignProviderMatchingCountVO extends ProviderMatchingCountsVO implements Serializable {

	private static final long serialVersionUID = 20090203L;

	private Integer campaignId;

	/**
	 * 
	 * @return Integer
	 */
	public Integer getCampaignId() {
		return campaignId;
	}
	/**
	 * 
	 * @param campaignId
	 */
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
}
