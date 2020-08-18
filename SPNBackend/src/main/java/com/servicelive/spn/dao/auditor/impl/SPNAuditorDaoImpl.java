package com.servicelive.spn.dao.auditor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.spn.detached.SPNAuditorSearchCriteriaVO;
import com.servicelive.domain.spn.detached.SPNAuditorSearchResultVO;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.common.util.PropertyManagerUtil;
import com.servicelive.spn.dao.auditor.SPNAuditorDao;
import com.servicelive.spn.services.BaseServices;

/**
 * @author hoza
 *
 */
public class SPNAuditorDaoImpl extends BaseServices implements SPNAuditorDao{
	
	private PropertyManagerUtil propertyManagerUtil;
	/**
	 * fetches the count of notification type for a provider & spn
	 * @param searchVO
	 * @return Integer
	 * @throws 
	 */
	public List<Integer> getListOfNotificationType(SPNAuditorSearchResultVO searchVO, Integer buyerId) throws DataServiceException{
		HashMap<String, Integer> inputMap = new HashMap<String, Integer>();
		inputMap.put("vendorId", searchVO.getProviderFirmId());
		inputMap.put("spnId", searchVO.getSpnId());
		inputMap.put("buyerId", buyerId);
		
		try{
			return getSqlMapClient().queryForList("network.auditorsearch.notification_type_count", inputMap);
		}
		catch(Exception e){
			logger.debug("Exception in SPNAuditorDaoImpl.getCountOfNotificationType due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
		
	/**
	 * fetches firms based on member compliance status
	 * @param auditorCriteriaVO
	 * @return List<SPNAuditorSearchResultVO>
	 * @throws 
	 */
	public List<SPNAuditorSearchResultVO> getProviderFirms(SPNAuditorSearchCriteriaVO spnAuditorSearchCriteriaVO) throws DataServiceException{
		Date time = CalendarUtil.getNow();
		CalendarUtil.addMinutes(time, -1*propertyManagerUtil.getSpnAuditorMonitorStickyQueueTimeoutMinutes());
		spnAuditorSearchCriteriaVO.setLockReferenceTime(time);

		try{
			return getSqlMapClient().queryForList("network.auditorsearch.search_provider_firms", spnAuditorSearchCriteriaVO);
			
		}catch(Exception e){
			logger.debug("Exception in SPNAuditorDaoImpl.getActionRequiredFirms due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		
	}
	
	public Integer getAdminResourceId(String username)	throws DataServiceException {
		try{
			return (Integer)getSqlMapClient().queryForObject("network.auditorsearch.getAdminResourceId", username);
			
		}catch(Exception e){
			logger.debug("Exception in SPNAuditorDaoImpl.getActionRequiredFirms due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}


	
	@Override
	protected void handleDates(Object entity) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param propertyManagerUtil the propertyManagerUtil to set
	 */
	public void setPropertyManagerUtil(PropertyManagerUtil propertyManagerUtil) {
		this.propertyManagerUtil = propertyManagerUtil;
	}


}
