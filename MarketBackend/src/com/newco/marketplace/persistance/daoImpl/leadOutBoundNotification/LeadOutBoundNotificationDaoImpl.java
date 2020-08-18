package com.newco.marketplace.persistance.daoImpl.leadOutBoundNotification;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.leadoutboundnotification.vo.LeadOutBoundNotificationVO;
import com.newco.marketplace.leadoutboundnotification.vo.LeadOutboundDetailsVO;
import com.newco.marketplace.persistence.iDao.leadOutBoundNotification.LeadOutBoundNotificationDao;
import com.newco.marketplace.exception.core.DataServiceException;
import com.sears.os.dao.impl.ABaseImplDao;

public class LeadOutBoundNotificationDaoImpl extends ABaseImplDao implements LeadOutBoundNotificationDao{
	private static final Logger LOGGER = Logger.getLogger(LeadOutBoundNotificationDaoImpl.class.getName());
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LeadOutBoundNotificationVO> fetchRecords(Integer noOfRetries, Integer recordsProcessingLimit) throws DataServiceException {
		List<LeadOutBoundNotificationVO> notificationVOList=null;
		try{
			HashMap hm = new HashMap();
			hm.put("noOfRetries", noOfRetries);
			hm.put("recordsProcessingLimit", recordsProcessingLimit);
			notificationVOList = (List<LeadOutBoundNotificationVO>) queryForList("fetchNotificationRecords.query", hm);
		}catch(Exception e){
			LOGGER.info("Exception occurred in fetchRecords: "+e.getMessage());
		}
		return notificationVOList;
	}
	
	//get value from application_properties for a key
	public String getPropertyFromDB(String appKey)	throws DataServiceException{
		String value=(String) queryForObject("getAppKeyValueLeads.query",appKey);
		return value;
	}
	
	public void updateNotification(LeadOutBoundNotificationVO leadOutBoundNotificationVO) throws DataServiceException {
		try{
			update("updateLeadNotification.query", leadOutBoundNotificationVO);
		}catch(Exception e){
			LOGGER.info("Exception occurred in updateNotification: "+e.getMessage());
		}
	}
	
	public void updateStatus(List<String> leadIds){
		try{
			update("updateLeadNotificationStatus.query", leadIds);
		}catch(Exception e){
			LOGGER.info("Exception occurred in updateStatus: "+e.getMessage());
		}
	}
	
	public LeadOutboundDetailsVO fetchLeadDetails(String leadId) throws DataServiceException {
		LeadOutboundDetailsVO detailsVOList = null;
		try{
			detailsVOList = (LeadOutboundDetailsVO) queryForObject("fetchLeadDetails.query", leadId);
		}catch(Exception e){
			LOGGER.info("Exception occurred in fetchLeadDetails: "+e.getMessage());
		}
		return detailsVOList;
	}
	
	@SuppressWarnings("unchecked")
	public List<LeadOutBoundNotificationVO> fetchFailedRecords() throws DataServiceException {
		List<LeadOutBoundNotificationVO> failureList = null;
		try{
			failureList = (List<LeadOutBoundNotificationVO>)queryForList("fetchFailedRecords.query");
		}catch(Exception e){
			LOGGER.info("Exception occurred in fetchFailedRecords: "+e.getMessage());
		}
		return failureList;
	}
	
	public void setEmailIndicator(List<String> failureList) throws DataServiceException{
		try{
			update("updateEmailIndicator.query", failureList);
		}catch(Exception e){
			LOGGER.info("Exception occurred in setEmailIndicator: "+e.getMessage());
		}
	}
	
	public void insertLeadOutBoundDetails(LeadOutBoundNotificationVO leadOutBoundNotificationVO) throws DataServiceException{
		try{
			insert("insertLeadOutBoundDetails.query", leadOutBoundNotificationVO);
		}catch(Exception e){
			LOGGER.info("Exception occurred in insertLeadOutBoundDetails: "+e.getMessage());
		}
	}
	
	public Integer fetchRecordsCount(Integer noOfRetries) throws DataServiceException{
		Integer count = null;
		try{
			count=(Integer) queryForObject("getLeadOutboundRecordsCount.query", noOfRetries);
		}catch(Exception e){
			LOGGER.info("Exception occurred in fetchRecordsCount: "+e.getMessage());
		}
		return count;
	}
}
