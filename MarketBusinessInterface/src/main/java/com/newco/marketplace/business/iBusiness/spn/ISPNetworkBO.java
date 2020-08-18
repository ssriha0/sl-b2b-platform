package com.newco.marketplace.business.iBusiness.spn;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNetTierReleaseVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.servicelive.routing.tiered.vo.SPNTierEventInfoVO;
import com.servicelive.routing.tiered.vo.SPNTieredVO;
import com.servicelive.routing.tiered.vo.TierRouteServiceOrderVO;

public interface ISPNetworkBO {
	public List<SPNMonitorVO> getSpnListForBuyer(Integer buyerId) throws BusinessServiceException;
	
	/**
	 * gets the getting the All Spn list 
	 * @param buyerId
	 * @return List<SPNMonitorVO>
	 * @throws BusinessServiceException
	 */
	public List<SPNMonitorVO> getSpnAllListForBuyer(Integer buyerId) throws BusinessServiceException;
	
	/**
	 * gets the getting the map of spns and approved Provider count
	 * @param nil
	 * @return spnApprovedProviderCountMap
	 * @throws BusinessServiceException
	 */
	public Map<Long, Long> getSpnApprovedProviderCount() throws BusinessServiceException;
	
	public List<LookupVO> getSpnetList() throws BusinessServiceException;
	/**
	 * gets the tiers for given spn
	 * @param spnId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<SPNetTierReleaseVO> getTiersForSpn(Integer spnId) throws BusinessServiceException;
	
	/**
	 * get the next tier for given spnID & tierId from spnet_tier_release_minutes
	 * @param orderVO
	 * @return SPNTieredVO
	 * @throws BusinessServiceException
	 */
	public SPNTieredVO getSPNAdminNextTierInfo(TierRouteServiceOrderVO orderVO) throws BusinessServiceException;

	/**
	 * get the SO spnet Tier event info for given soId
	 * @param orderVO
	 * @return SPNTierEventInfoVO
	 * @throws BusinessServiceException
	 */
	public SPNTierEventInfoVO getSpnetSOTierEventInfo(TierRouteServiceOrderVO orderVO) throws BusinessServiceException;
	
	/**
	 * persist data into spnet_tier_event_info
	 * @param spnTierEventInfoVO
	 * @param isGrouped 
	 * @throws BusinessServiceException
	 */
	public void persistSOTierEventInformation(SPNTierEventInfoVO spnTierEventInfoVO, Boolean isGrouped) throws BusinessServiceException;
	
	/**
	 * delete data from spnet_tier_event_info for given orderId
	 * @param spnTierEventInfoVO
	 * @param isGrouped 
	 * @throws BusinessServiceException
	 */
	public void deleteSOTierEventInformation(String orderId, Boolean isGrouped) throws BusinessServiceException;
	
	public SPNetHeaderVO getSPNetHeaderInfo(Integer spnId) throws BusinessServiceException;
	
	/**
	 * returns true if Provider Firm has any one of 3 spn status (Member, OC, removed)
	 * @param buyerId
	 * @param providerFirmId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Boolean isSPFirmNetworkTabViewable(int buyerId, int providerFirmId) throws BusinessServiceException;
	
	/**
	 * returns true if Provider Firm has any one of 3 spn status (Member, OC, removed)
	 * @param buyerId
	 * @param providerFirmId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Boolean isProviderNetworkTabViewable(int buyerId, int providerId) throws BusinessServiceException;

	public List<SPNetTierReleaseVO> fetchRoutingPriorities(String spnId);
}
