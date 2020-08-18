package com.newco.marketplace.persistence.iDao.spn;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNetTierReleaseVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.routing.tiered.vo.SPNTierEventInfoVO;
import com.servicelive.routing.tiered.vo.SPNTieredVO;
import com.servicelive.routing.tiered.vo.TierRouteServiceOrderVO;

public interface SPNetDao {
	
	public List<SPNMonitorVO> getSpnListForBuyer(Integer buyerId) throws Exception;
	
	public List<LookupVO> getSpnetList() throws Exception;
	
	public List<SPNMonitorVO> getSpnAllListForBuyer(Integer buyerId) throws Exception;
	
	public List<SPNetTierReleaseVO> getTiersForSpn(Integer spnId) throws DataServiceException;
	
	public List<SPNTieredVO> getSPNAdminNextTierInfo(TierRouteServiceOrderVO orderVO) throws DataServiceException;

	public SPNTierEventInfoVO getSpnetSOTierEventInfo(TierRouteServiceOrderVO orderVO) throws DataServiceException;

	public void persistSOTierEventInfo(SPNTierEventInfoVO spnTierEventInfoVO, Boolean isGrouped) throws DataServiceException;
	
	public void deleteSOTierEventInfo(String orderId, Boolean isGrouped) throws DataServiceException;
	
	public SPNetHeaderVO getSPNetHeaderInfo(Integer spnId) throws DataServiceException;
	
	public int getInfoForSPFirmAndBuyer(int buyerId, int providerFirmId) throws DataServiceException;
	
	public int getInfoForProviderAndBuyer(int buyerId, int providerId) throws DataServiceException;
	
	public Map<Long, Long> getSpnApprovedProviderCount() throws Exception;
	
	public List<SPNetTierReleaseVO> fetchRoutingPriorities(String spnId);
	

}
