package com.newco.marketplace.business.businessImpl.spn;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.spn.ISPNetworkBO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNetTierReleaseVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.spn.SPNetDao;
import com.servicelive.routing.tiered.vo.SPNTierEventInfoVO;
import com.servicelive.routing.tiered.vo.SPNTieredVO;
import com.servicelive.routing.tiered.vo.TierRouteServiceOrderVO;

/**
 * This is created for new SPN design, consider this BO too add any new spn functionality
 * @author sldev
 *
 */
public class SPNetworkBOImpl implements ISPNetworkBO {
	
	private SPNetDao spnetDao;
	
	private static final Logger logger = Logger.getLogger(SPNetworkBOImpl.class);
	
	public List<SPNMonitorVO> getSpnListForBuyer(Integer buyerId) throws BusinessServiceException{
		List<SPNMonitorVO>  spnList= null;
		try{
			spnList= spnetDao.getSpnListForBuyer(buyerId);
		}catch(Exception e){
			logger.error("Error thrown while getting list of spns for given buyerId -" + buyerId );
		}
		return spnList;
	}
	
	public List<SPNMonitorVO> getSpnAllListForBuyer(Integer buyerId) throws BusinessServiceException{
		List<SPNMonitorVO>  spnList= null;
		try{
			spnList= spnetDao.getSpnAllListForBuyer(buyerId);
		}catch(Exception e){
			logger.error("Error thrown while getting list of spns for given buyerId -" + buyerId );
		}
		return spnList;
	}
	
	public Map<Long, Long> getSpnApprovedProviderCount() throws BusinessServiceException{
		Map<Long, Long> spnApprovedProviderCountMap = null;
		try{
			spnApprovedProviderCountMap = spnetDao.getSpnApprovedProviderCount();
		}catch(Exception e){
			logger.error("Error thrown while getting the map of SPN and approved Provider count");
		}
		return spnApprovedProviderCountMap;
	}
	
	public List<LookupVO> getSpnetList() throws BusinessServiceException {
		List<LookupVO>  spnetList= null;
		try{
			spnetList= spnetDao.getSpnetList();
		}catch(Exception e){
			logger.error("Error thrown in getSpnetList() due to -"+ e.getMessage() );
		}
		return spnetList;
	}
	
	public List<SPNetTierReleaseVO> getTiersForSpn(Integer spnId) throws BusinessServiceException {
		List<SPNetTierReleaseVO> spnTiers = null;
		try{
			spnTiers = spnetDao.getTiersForSpn(spnId);
		}catch(Exception e){
			String msg = "error occurred in getTiersForSpn()" + e.getMessage();
			logger.error(msg);
			throw new  BusinessServiceException(msg,e);
		}
		
		return spnTiers;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISPNetworkBO#getSPNAdminNextTierInfo(com.servicelive.routing.tiered.vo.TierRouteServiceOrderVO)
	 */
	public SPNTieredVO getSPNAdminNextTierInfo(TierRouteServiceOrderVO orderVO) throws BusinessServiceException {
		SPNTieredVO spnNextTieredVO = null;
		try{
			List<SPNTieredVO> nextTierInfoList = spnetDao.getSPNAdminNextTierInfo(orderVO);
			if(nextTierInfoList!= null && nextTierInfoList.size() > 0){
				spnNextTieredVO = nextTierInfoList.get(0);
				if(nextTierInfoList.size() > 1){
					spnNextTieredVO.setNextTierId(nextTierInfoList.get(1).getTierId());					
				}
			}			
		}catch(Exception e){
			String msg = "error occurred in getSPNAdminTierInfo()" + e.getMessage();
			logger.error(msg);
			throw new  BusinessServiceException(msg,e);
		}
		
		
		return spnNextTieredVO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISPNetworkBO#getSpnetSOTierEventInfo(com.servicelive.routing.tiered.vo.TierRouteServiceOrderVO)
	 */
	public SPNTierEventInfoVO getSpnetSOTierEventInfo(TierRouteServiceOrderVO orderVO) throws BusinessServiceException {
		SPNTierEventInfoVO spnTierEventInfo = null;
		try{
			spnTierEventInfo = spnetDao.getSpnetSOTierEventInfo(orderVO);
		}catch(Exception e){
			String msg = "error occurred in getSpnetSOTierEventInfo()" + e.getMessage();
			logger.error(msg);
			throw new  BusinessServiceException(msg,e);
		}
		return spnTierEventInfo;
	}
	
	public void persistSOTierEventInformation(SPNTierEventInfoVO spnTierEventInfoVO, Boolean isGrouped) throws BusinessServiceException {
		try{
			spnetDao.persistSOTierEventInfo(spnTierEventInfoVO, isGrouped);
		}catch(Exception e){
			String msg = "error occurred in persistTierEventInformation()" + e.getMessage();
			logger.error(msg);
			throw new  BusinessServiceException(msg,e);
		}
	}
	
	public void deleteSOTierEventInformation(String orderId, Boolean isGrouped) throws BusinessServiceException {
		try{
			spnetDao.deleteSOTierEventInfo(orderId, isGrouped);
		}catch(Exception e){
			String msg = "error occurred in persistTierEventInformation()" + e.getMessage();
			logger.error(msg);
			throw new  BusinessServiceException(msg,e);
		}
	}
	

	public SPNetHeaderVO getSPNetHeaderInfo(Integer spnId)
			throws BusinessServiceException {
		SPNetHeaderVO spnetHeaderInfo = new SPNetHeaderVO();
		try{
			spnetHeaderInfo = spnetDao.getSPNetHeaderInfo(spnId);
		}catch(Exception e){
			String msg = "error occurred in getSPNetHeaderInfo("+ spnId  +")" + e.getMessage();
			logger.error(msg);
			throw new  BusinessServiceException(msg,e);
		}
		
		return spnetHeaderInfo;
	}
	
	public Boolean isSPFirmNetworkTabViewable(int buyerId, int providerFirmId)
			throws BusinessServiceException {
		Boolean isViewable = false;
		try{
			int count = spnetDao.getInfoForSPFirmAndBuyer(buyerId, providerFirmId);
			if(count > 0){
				isViewable = true;
			}
		}catch(Exception e){
			String msg = "error occurred in isSPFirmNetworkTabViewable("+ buyerId + "," +  providerFirmId +")" + e.getMessage();
			logger.error(msg);
			throw new  BusinessServiceException(msg,e);
		}
		return isViewable;
	}
	
	public Boolean isProviderNetworkTabViewable(int buyerId, int providerId) 
	        throws BusinessServiceException {
		Boolean isViewable = false;
		try{
			int count = spnetDao.getInfoForProviderAndBuyer(buyerId, providerId);
			if(count > 0){
				isViewable = true;
			}
		}catch(Exception e){
			String msg = "error occurred in isProviderNetworkTabViewable("+ buyerId + "," +  providerId +")" + e.getMessage();
			logger.error(msg);
			throw new  BusinessServiceException(msg,e);
		}
		return isViewable;	
	}
	
	public List<SPNetTierReleaseVO> fetchRoutingPriorities(String spnId){
		return spnetDao.fetchRoutingPriorities(spnId);
	}
	

	/**
	 * @return the spnetDao
	 */
	public SPNetDao getSpnetDao() {
		return spnetDao;
	}

	/**
	 * @param spnetDao the spnetDao to set
	 */
	public void setSpnetDao(SPNetDao spnetDao) {
		this.spnetDao = spnetDao;
	}





}
