package com.servicelive.spn.services.campaign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.detached.CampaignMonitorRowVO;
import com.servicelive.domain.spn.detached.CampaignProviderMatchingCountVO;
import com.servicelive.spn.common.detached.CampaignHistoryVO;
import com.servicelive.spn.services.BaseServices;

/**
 * 
 * @author svanloo
 *
 */
public class CampaignSummaryServices extends BaseServices
{
	/**
	 * 
	 * @param buyerId
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CampaignMonitorRowVO> getCampaignMonitorRows(Integer buyerId, Integer maxRows) throws Exception {
		return getCampaignMonitorRows(buyerId,Boolean.FALSE, maxRows);
	}
	
	public List<CampaignMonitorRowVO> getSaveCampaignListForCriteriaCampaign(Integer buyerId ) throws Exception {
		return getCampaignMonitorRows(buyerId,Boolean.TRUE, null);
	}
	
	@SuppressWarnings("unchecked")
	private List<CampaignMonitorRowVO> getCampaignMonitorRows(Integer buyerId, Boolean isCriteriabasedOnly, Integer maxRows) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buyerId", buyerId);
		paramMap.put("isCriteriabasedOnly", isCriteriabasedOnly);
		paramMap.put("maxRows", maxRows);
		System.out.println("Fetching campaignMonitorRowVOList with query campaign.monitor.getcampaigns in getCampaignMonitorRows");
		List<CampaignMonitorRowVO> campaignMonitorRowVOList = getSqlMapClient().queryForList("campaign.monitor.getcampaigns", paramMap);
		System.out.println("Done fetching campaignMonitorRowVOList with query campaign.monitor.getcampaigns in getCampaignMonitorRows");
		//create hashmap
		Map<Integer, CampaignMonitorRowVO> campaignMonitorRowVOMapByCampaignId = new HashMap<Integer, CampaignMonitorRowVO>();
		for(CampaignMonitorRowVO campaignMonitorRowVO: campaignMonitorRowVOList) {
			campaignMonitorRowVOMapByCampaignId.put(campaignMonitorRowVO.getCampaignId(), campaignMonitorRowVO);
		}
		// query for additional detail
		System.out.println("Fetching campaignProviderMatchingCountVOList with query campaign.monitor.getSummaryInfoProvFirm in getCampaignMonitorRows");
		List<CampaignProviderMatchingCountVO> campaignProviderMatchingCountVOList = getSqlMapClient().queryForList("campaign.monitor.getSummaryInfoProvFirm", buyerId);
		System.out.println("Done fetching campaignProviderMatchingCountVOList with query campaign.monitor.getSummaryInfoProvFirm in getCampaignMonitorRows");
		System.out.println("Fetching campaignServiceProviderMatchingCountVOList with query campaign.monitor.getSummaryInfoServicePro in getCampaignMonitorRows");
		List<CampaignProviderMatchingCountVO> campaignServiceProviderMatchingCountVOList = getSqlMapClient().queryForList("campaign.monitor.getSummaryInfoServicePro", buyerId);
		System.out.println("Done fetching campaignServiceProviderMatchingCountVOList with query campaign.monitor.getSummaryInfoServicePro in getCampaignMonitorRows");
		// combine data
		for(CampaignProviderMatchingCountVO providerMatchingCountExtendVO: campaignProviderMatchingCountVOList) {
			Integer campaignId = providerMatchingCountExtendVO.getCampaignId();
			CampaignMonitorRowVO campaignMonitorRowVO = campaignMonitorRowVOMapByCampaignId.get(campaignId);
			if(campaignMonitorRowVO == null) {
				logger.info("didn't find an campaignMonitorRowVO for campaignId=" + campaignId + ".");
				continue;
			}
			//campaignMonitorRowVO.setInvitedPros(providerMatchingCountExtendVO);
			campaignMonitorRowVO.getInvitedPros().setProviderFirmCounts(providerMatchingCountExtendVO.getProviderFirmCounts());
		}
		
		for(CampaignProviderMatchingCountVO providerMatchingCountExtendVO: campaignServiceProviderMatchingCountVOList) {
			Integer campaignId = providerMatchingCountExtendVO.getCampaignId();
			CampaignMonitorRowVO campaignMonitorRowVO = campaignMonitorRowVOMapByCampaignId.get(campaignId);
			if(campaignMonitorRowVO == null) {
				logger.info("didn't find an campaignMonitorRowVO for campaignId=" + campaignId + ".");
				continue;
			}
			//campaignMonitorRowVO.setInvitedPros(providerMatchingCountExtendVO);
			campaignMonitorRowVO.getInvitedPros().setProviderCounts(providerMatchingCountExtendVO.getProviderCounts());
		}
		return campaignMonitorRowVOList;
	}

	public Integer getCampaignCount(Integer buyerId, Boolean isCriteriabasedOnly) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buyerId", buyerId);
		paramMap.put("isCriteriabasedOnly", isCriteriabasedOnly);
		Integer count = (Integer)getSqlMapClient().queryForObject("campaign.monitor.getCampaignCount", paramMap);

		return count;
	}
	
	public Integer getActiveCampaignCount(Integer buyerId, Boolean isCriteriabasedOnly) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buyerId", buyerId);
		paramMap.put("isCriteriabasedOnly", isCriteriabasedOnly);
		Integer count = (Integer)getSqlMapClient().queryForObject("campaign.monitor.getActiveCampaignCount", paramMap);

		return count;
	}
	
	public Integer getInactiveCampaignCount(Integer buyerId, Boolean isCriteriabasedOnly) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buyerId", buyerId);
		paramMap.put("isCriteriabasedOnly", isCriteriabasedOnly);
		Integer count = (Integer)getSqlMapClient().queryForObject("campaign.monitor.getInactiveCampaignCount", paramMap);

		return count;
	}
	
	public Integer getPendingCampaignCount(Integer buyerId, Boolean isCriteriabasedOnly) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buyerId", buyerId);
		paramMap.put("isCriteriabasedOnly", isCriteriabasedOnly);
		Integer count = (Integer)getSqlMapClient().queryForObject("campaign.monitor.getPendingCampaignCount", paramMap);

		return count;
	}
	
	/**
	 * 
	 * @param campaignId
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CampaignHistoryVO> getCampaignHistory(Integer campaignId) throws Exception {
		return  getSqlMapClient().queryForList("campaign.monitor.getCampaignHistory", campaignId);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		//do nothing
	}
}
