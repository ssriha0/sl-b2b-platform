package com.newco.marketplace.business.businessImpl.inhomeOutBoundNotification;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.InHomeNotificationFailureService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;
import com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification.InHomeNotificationFailureDao;

public class InHomeNotificationFailureServiceImpl implements InHomeNotificationFailureService{

	private static final Logger LOGGER = Logger.getLogger(InHomeNotificationFailureServiceImpl.class.getName());

	private InHomeNotificationFailureDao inHomeNotificationFailureDao;
	
	// Method to fetch the failed records after nps processing.
	public List<InHomeOutBoundNotificationVO> fetchFailedRecords(Integer recordsProcessingLimit) throws BusinessServiceException {
		List<InHomeOutBoundNotificationVO > failureList = null;
		try {
			
			failureList = inHomeNotificationFailureDao.fetchFailedRecords(recordsProcessingLimit);
		}
		catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching failed records " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in fetching the failed records due to" + dse.getMessage(), dse);
		}
		return failureList;
	}

	public String getApplicationFlag(String flagKey) throws BusinessServiceException{
		String inHomeNpsFlag = null;
		try{
			inHomeNpsFlag = inHomeNotificationFailureDao.getPropertyFromApplicationFlag(flagKey);
		}catch(DataServiceException e){
			LOGGER.info("Exception in getting application flag"+ e.getMessage());
			throw new BusinessServiceException("Exception in getting application flag due to " + e.getMessage());
		}
		return inHomeNpsFlag;
	}
	
	// Method to fetch all the data from lu_notification_oners.
	public List<NotificationOwnerDetails> getNotificationOwnerDetails() throws BusinessServiceException {
		try {
			return inHomeNotificationFailureDao.getNotificationOwnerDetails();
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in getNotificationOwnerDetails() " + dse.getMessage(), dse);
			throw new BusinessServiceException(	"Exception in getNotificationOwnerDetails() " + dse.getMessage(), dse);
		}
	}
	
	public void setEmailIndicator(List<String> failureList) throws BusinessServiceException{
		try {
			inHomeNotificationFailureDao.setEmailIndicator(failureList);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in updating data " + dse.getMessage(), dse);
		}
	}

	//Get property value for a corresponding key from application_properties table. 
	public String getPropertyFromDB(String appKey) throws BusinessServiceException {
		try {
			return inHomeNotificationFailureDao.getPropertyFromDB(appKey);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in getting app key value due to " + dse.getMessage(), dse);
		}	
	}
	
	public InHomeNotificationFailureDao getInHomeNotificationFailureDao() {
		return inHomeNotificationFailureDao;
	}

	public void setInHomeNotificationFailureDao(
			InHomeNotificationFailureDao inHomeNotificationFailureDao) {
		this.inHomeNotificationFailureDao = inHomeNotificationFailureDao;
	}
	
	// Method to fetch the number of failed records after nps processing.
	public Integer fetchRecordsCount() throws BusinessServiceException {
		// TODO Auto-generated method stub
		try {
			return inHomeNotificationFailureDao.fetchRecordsCount();
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetchRecordsCount data " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in getting fetchRecordsCount due to " + dse.getMessage(), dse);
		}
	}
	public Integer fetchRecordsCountForWaiting() throws BusinessServiceException {
		// TODO Auto-generated method stub
		try {
			return inHomeNotificationFailureDao.fetchRecordsCountForWaiting();
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetchRecordsCountForWaiting data " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in getting fetchRecordsCountForWaiting due to " + dse.getMessage(), dse);
		}
	}

	public List<InHomeOutBoundNotificationVO> fetchWaitingRecords(Integer recordsProcessingLimit) throws BusinessServiceException {
		List<InHomeOutBoundNotificationVO > waitingList = null;
		try {
			
			waitingList = inHomeNotificationFailureDao.fetchWaitingRecords(recordsProcessingLimit);
		}
		catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching failed records " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in fetching the failed records due to" + dse.getMessage(), dse);
		}
		return waitingList;
	}


}
