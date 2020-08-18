package com.newco.marketplace.persistance.daoImpl.inhomeOutBoundNotification;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;
import com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification.InHomeNotificationFailureDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class InHomeNotificationFailureDaoImpl extends ABaseImplDao implements InHomeNotificationFailureDao{
	private static final Logger LOGGER = Logger.getLogger(InHomeNotificationFailureDaoImpl.class.getName());
	
	// Method to fetch the failed records after nps processing.
	@SuppressWarnings("unchecked")
	public List<InHomeOutBoundNotificationVO> fetchFailedRecords(Integer recordsProcessingLimit) throws DataServiceException {
		List<InHomeOutBoundNotificationVO > failureList = null;
		try{
			failureList = (List<InHomeOutBoundNotificationVO >)queryForList("fetchFailedInHomeRecords.query",recordsProcessingLimit);
			
		}catch(Exception e){
			LOGGER.info("Exception occurred in fetchFailedRecords: "+e.getMessage());
			throw new DataServiceException("Exception in fetching the failed records due to" + e.getMessage(), e);
		}
		return failureList;
	}

	public String getPropertyFromApplicationFlag(String flagKey)throws DataServiceException {
		String flag = null;
		try{
			flag =(String) queryForObject("getAppFlagValue.query", flagKey);
		}catch(Exception e){
			LOGGER.info("Exception occurred in getPropertyFromApplicationFlag: "+e.getMessage());
			throw new DataServiceException("Exception occurred in getPropertyFromApplicationFlag: "+e.getMessage(),e);
		}
		return flag;
	}
	
	// Method to fetch all the data from lu_notification_oners.
	@SuppressWarnings("unchecked")
	public List<NotificationOwnerDetails> getNotificationOwnerDetails() throws DataServiceException {
		List<NotificationOwnerDetails> notificationOwnerDetails = null;
		try{
			notificationOwnerDetails = (List<NotificationOwnerDetails>)queryForList("getNotificationOwnerDetails.query");
		}catch(Exception e){
			LOGGER.error("Exception occurred in getNotificationOwnerDetails: "+e.getMessage());
			throw new DataServiceException("Exception occurred in getNotificationOwnerDetails: "+e.getMessage(),e);
		}
		return notificationOwnerDetails;
	}

	public void setEmailIndicator(List<String> failureList) throws DataServiceException{
		try{
			update("updateInHomeEmailIndicator.query", failureList);
		}catch(Exception e){
			LOGGER.info("Exception occurred in setEmailIndicator: "+e.getMessage());
		}
	}
	//Get property value for a corresponding key from application_properties table. 
	public String getPropertyFromDB(String appKey)	throws DataServiceException{
		String value = null;
		try{
			value=(String) queryForObject("getAppValueForKey.query",appKey);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in getPropertyFromDB: "+e.getMessage());
			throw new DataServiceException("Exception occurred in getPropertyFromDB: "+e.getMessage(),e);
		}
		return value;
	}
	
	// Method to fetch the number of failed records after nps processing.
	public Integer fetchRecordsCount() throws DataServiceException {
		// TODO Auto-generated method stub
		Integer value = null;
		try{
			value=(Integer) queryForObject("getRecordCountForEmail.query", null);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in fetchRecordsCount: "+e.getMessage());
			throw new DataServiceException("Exception occurred in fetchRecordsCount: "+e.getMessage(),e);
		}
		return value;
	}
	
	public Integer fetchRecordsCountForWaiting() throws DataServiceException {
		// TODO Auto-generated method stub
		Integer value = null;
		try{
			value=(Integer) queryForObject("getWaitingRecordCountForEmail.query", null);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in fetchRecordsCountForWaiting: "+e.getMessage());
			throw new DataServiceException("Exception occurred in fetchRecordsCountForWaiting: "+e.getMessage(),e);
		}
		return value;
	}
	@SuppressWarnings("unchecked")
	public List<InHomeOutBoundNotificationVO> fetchWaitingRecords(Integer recordsProcessingLimit) throws DataServiceException {
		List<InHomeOutBoundNotificationVO > waitingList = null;
		try{
			waitingList = (List<InHomeOutBoundNotificationVO >)queryForList("fetchWaitingInHomeRecords.query",recordsProcessingLimit);
			
		}catch(Exception e){
			LOGGER.info("Exception occurred in fetchWaitingRecords: "+e.getMessage());
			throw new DataServiceException("Exception in fetching the fetchWaitingRecords due to" + e.getMessage(), e);
		}
		return waitingList;
	}


}
