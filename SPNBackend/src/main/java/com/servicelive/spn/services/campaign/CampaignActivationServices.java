package com.servicelive.spn.services.campaign;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_CAMPAIGN_ACTIVE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_CAMPAIGN_INACTIVE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.spn.common.detached.CampaignActivationVO;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.services.BaseServices;

/**
 * 
 * @author SVANLOO
 *
 */
public class CampaignActivationServices extends BaseServices {

	/**
	 * 
	 */
	public CampaignActivationServices() {
		super();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void approveCampaigns() throws Exception {
		List<CampaignActivationVO> campaigns = this.listActiveCampaigns();
		Integer campaignId;
		for(CampaignActivationVO campaign: campaigns) {
			campaignId = campaign.getCampaignId();
			logger.info("setting campaign[" + campaignId+ "] to active");
			campaign.setState(WF_STATUS_CAMPAIGN_ACTIVE);
			this.updateCampaign(campaign);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void inactivateCampaigns() throws Exception {
		List<CampaignActivationVO> campaignHeaders = this.listExpiredCampaigns();
		Integer campaignId;
		for(CampaignActivationVO campaignHeader: campaignHeaders) {
			campaignId = campaignHeader.getCampaignId();
			logger.info("setting campaign[" + campaignId+ "] to inactive");
			campaignHeader.setState(WF_STATUS_CAMPAIGN_INACTIVE);
			this.updateCampaign(campaignHeader);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<CampaignActivationVO> listActiveCampaigns() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now", CalendarUtil.getTodayAtMidnight());
		return  getSqlMapClient().queryForList("campaign.activation.getcampaigns", map);
	}

	@SuppressWarnings("unchecked")
	protected List<CampaignActivationVO> listExpiredCampaigns() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now", CalendarUtil.getTodayAtMidnight());
		return  getSqlMapClient().queryForList("campaign.activation.getexpiredcampaigns", map);
	}

	protected void updateCampaign(CampaignActivationVO campaign) throws Exception {
		getSqlMapClient().update("campaign.activation.updatecampaign", campaign);
	}

	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}
}
