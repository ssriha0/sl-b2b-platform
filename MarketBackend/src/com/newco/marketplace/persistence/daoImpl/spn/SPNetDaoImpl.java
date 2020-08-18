package com.newco.marketplace.persistence.daoImpl.spn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNetTierReleaseVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.spn.SPNetDao;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.routing.tiered.vo.SPNTierEventInfoVO;
import com.servicelive.routing.tiered.vo.SPNTieredVO;
import com.servicelive.routing.tiered.vo.TierRouteServiceOrderVO;

/**
 * This is created for new SPN design, consider this DAO too add any new spn functionality
 * @author sldev
 *
 */
public class SPNetDaoImpl extends ABaseImplDao implements SPNetDao {

	private static final Logger logger = Logger.getLogger(SPNetDaoImpl.class);
	public List<SPNMonitorVO> getSpnListForBuyer(Integer buyerId)
			throws Exception {
		List<SPNMonitorVO> spnListForBuyer = new ArrayList<SPNMonitorVO>();
		try {
			spnListForBuyer = queryForList("spnet_buyer.query", buyerId);
		}catch(Exception e){
			logger.error("error occured in getSpnListForBuyer for given buyerId--" + buyerId, e);
		}
		
		return spnListForBuyer;
	}
	
	public List<SPNMonitorVO> getSpnAllListForBuyer(Integer buyerId)
		throws Exception {
		List<SPNMonitorVO> spnListForBuyer = new ArrayList<SPNMonitorVO>();
		try {
			spnListForBuyer = queryForList("spnet_buyer_all.query", buyerId);
		}catch(Exception e){
			logger.error("error occured in getSpnAllListForBuyer for given buyerId--" + buyerId, e);
		}
		
		return spnListForBuyer;
	}
	
	public Map<Long, Long> getSpnApprovedProviderCount() throws Exception {
		Map<Long, Long> result = null;
		try{
			result = getSqlMapClient().queryForMap("spnet_approved_prov_count.query", null,"spn_id", "approved_prov_count");
		}catch(Exception e){
			logger.error("error occured in getSpnApprovedCount", e);
		}		
		return result;
	}
	
	public List<LookupVO> getSpnetList() throws Exception {
		List<LookupVO> spnetList = new ArrayList<LookupVO>();
		try {
			spnetList = queryForList("spnet_hdr.query", null);
		}catch(Exception e){
			logger.error("error occured in getSpnetList() due to "+ e.getMessage());
		}
		
		return spnetList;
	}
	
	public List<SPNetTierReleaseVO> getTiersForSpn(Integer spnId) throws DataServiceException{
		List<SPNetTierReleaseVO> tiersForSpn = new ArrayList<SPNetTierReleaseVO>();
		try{
			tiersForSpn = (ArrayList<SPNetTierReleaseVO>)queryForList("select_spnet_release_tier.query",spnId);
			
		}catch(Exception e){
			String msg = "error occured in getTiersForSpn(spnId) due to "+ e.getMessage();
			throw new DataServiceException(msg,e);
		}
		
		return tiersForSpn;
	}
	
	public List<SPNTieredVO> getSPNAdminNextTierInfo(TierRouteServiceOrderVO orderVO)
			throws DataServiceException {
		List<SPNTieredVO> spnTieredVOList = null;
		try{
			spnTieredVOList = (List<SPNTieredVO>)queryForList("select_spnet_release_next_tier.query", orderVO);
		}catch(Exception e){
			String msg = "error occured in getSPNAdminNextTierInfo(orderVO) due to "+ e.getMessage();
			throw new DataServiceException(msg,e);
		}
		
		return spnTieredVOList;
	}

	
	public SPNTierEventInfoVO getSpnetSOTierEventInfo(TierRouteServiceOrderVO orderVO) throws DataServiceException{
		SPNTierEventInfoVO spnTierEventInfoVO = null;
		try{
			if(orderVO.isGrouped()){
				spnTierEventInfoVO = (SPNTierEventInfoVO)queryForObject("select_spnet_so_group_tier_event.query", orderVO);
			}else{
				spnTierEventInfoVO = (SPNTierEventInfoVO)queryForObject("select_spnet_so_tier_event.query", orderVO);
			}
			
		}catch(Exception e){
			String msg = "error occured in getSpnetSOTierEventInfo(soId) due to "+ e.getMessage();
			throw new DataServiceException(msg,e);
		}
		return spnTierEventInfoVO;
	}
	
	public void persistSOTierEventInfo(SPNTierEventInfoVO spnTierEventInfoVO, Boolean isGrouped) throws DataServiceException{
		try{
			if(isGrouped){
				int count = update("tierRoute.update_spnet_group_tier_event", spnTierEventInfoVO);
				if(count==0){
					insert("tierRoute.insert_spnet_group_tier_event", spnTierEventInfoVO);
				}
			}else{
				int count = update("tierRoute.update_spnet_so_tier_event", spnTierEventInfoVO);
				if(count==0){
					insert("tierRoute.insert_spnet_so_tier_event", spnTierEventInfoVO);
				}
				
			}
			

		}catch(Exception e){
			String msg = "error occured in persistSOTierEventInfo() due to "+ e.getMessage();
			throw new DataServiceException(msg,e);
		}
	}
	
	public void deleteSOTierEventInfo(String orderId, Boolean isGrouped) throws DataServiceException{
		try{
			if(isGrouped){
					insert("tierRoute.delete_spnet_group_tier_event", orderId);
			}else{
					insert("tierRoute.delete_spnet_so_tier_event", orderId);
			}

		}catch(Exception e){
			String msg = "error occured in persistSOTierEventInfo() due to "+ e.getMessage();
			throw new DataServiceException(msg,e);
		}
	}
	
	public SPNetHeaderVO getSPNetHeaderInfo(Integer spnId)
			throws DataServiceException {
		SPNetHeaderVO spnetHeaderInfo = null;
		try{
			spnetHeaderInfo = (SPNetHeaderVO)queryForObject("spnet_hdr_info.query", spnId);
		}catch(Exception e){
			String msg = "error occured in getSPNetHeaderInfo() due to "+ e.getMessage();
			throw new DataServiceException(msg,e);
		}
		return spnetHeaderInfo;
	}
	
	public int getInfoForSPFirmAndBuyer(int buyerId, int providerFirmId) throws DataServiceException {
		int count = 0;
		Map paramMap = new HashMap();
		try{
			paramMap.put("buyerId", buyerId);
			paramMap.put("providerFirmId", providerFirmId);
			Integer countObj =  (Integer)queryForObject("sp_firm_spn_tab_viewable.query", paramMap);
			count = countObj!= null ? countObj.intValue(): 0 ;
		}catch(Exception e){
			String msg = "error occured in getInfoForSPFirmAndBuyer() due to "+ e.getMessage();
			throw new DataServiceException(msg,e);
		}
		return count;
	}
	
	public int getInfoForProviderAndBuyer(int buyerId, int providerId) throws DataServiceException {
		int count = 0;
		Map paramMap = new HashMap();
		try{
			paramMap.put("buyerId", buyerId);
			paramMap.put("providerId", providerId);
			Integer countObj =  (Integer)queryForObject("provider_spn_tab_viewable.query", paramMap);
			count = countObj!= null ? countObj.intValue(): 0 ;
		}catch(Exception e){
			String msg = "error occured in getInfoForSPFirmAndBuyer() due to "+ e.getMessage();
			throw new DataServiceException(msg,e);
		}
		return count;
	
		
	}
	
	@SuppressWarnings("unchecked")
	public List<SPNetTierReleaseVO> fetchRoutingPriorities(String spn){
		Integer spnId = Integer.parseInt(spn);
		List<SPNetTierReleaseVO> routingPrioritiesList = null;
		try{
		routingPrioritiesList = queryForList("fetchRoutingPriorities.query", spnId);
		}catch(Exception e){
			logger.error("Exception Occured while fetching routingPriorities.Exception is "+e);
		}
		return routingPrioritiesList;
	}

}
