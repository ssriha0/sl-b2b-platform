package com.newco.marketplace.business.businessImpl.inhomeOutBoundNotification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.IInhomeOutBoundNotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;
import com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification.IInHomeOutBoundNotificationDao;

/**
 * @author Infosys
 * Service Implementation class for InhomeOutBoundNotification batch and InhomeOutBoundMessage batch
 */

public class InHomeOutBoundNotificationServiceImpl implements IInhomeOutBoundNotificationService{
	
	private static final Logger LOGGER = Logger.getLogger(InHomeOutBoundNotificationServiceImpl.class.getName());
	
	//Use hsrOutBoundNotificationDao to fetch details from DB		
	private IInHomeOutBoundNotificationDao hsrOutBoundNotificationDao;
	
	//Get property value for a corresponding key from application_properties table. 
	public String getPropertyFromDB(String appKey) throws BusinessServiceException {
		try {
			return hsrOutBoundNotificationDao.getPropertyFromDB(appKey);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in getting app key value due to " + dse.getMessage(), dse);
		}	
	}
	
	//Get property value for a corresponding key from application_flags table. 
	public String getInHomeNpsFlag(String inhomeNpsFlag) throws BusinessServiceException {
		String inHomeNpsFlag = null;
		try{
			inHomeNpsFlag = hsrOutBoundNotificationDao.getPropertyFromApplicationFlag(inhomeNpsFlag);
		}catch(DataServiceException e){
			LOGGER.error("Exception in getting inhomenps flag"+ e.getMessage());
			throw new BusinessServiceException("Exception in getting inhomenps flag due to " + e.getMessage());
		}
		return inHomeNpsFlag;
	}
	
	//Fetch the count of records in the table buyer_outbound_notification for a specific service id and 
	//within specific no of retries.
	public Integer fetchRecordsCount(Integer noOfRetries, String serviceId) throws BusinessServiceException{
		Integer count = null;
		try{			
			count = hsrOutBoundNotificationDao.fetchRecordsCount(noOfRetries, serviceId);
		}catch(DataServiceException dse){
			LOGGER.error("Exception occurred in fetchRecordsCount: "+dse.getMessage());
			throw new BusinessServiceException("Exception in fetching the count due to" + dse.getMessage(), dse);
		}
		return count;
	}
	
	//Method to fetch the records in the table buyer_outbound_notification for a specific service id.
	public List<InHomeOutBoundNotificationVO> fetchRecords(Integer noOfRetries, String serviceId, 
			Integer recordsProcessingLimit) throws BusinessServiceException {
		
		List<InHomeOutBoundNotificationVO> notificationVOList = null;
		List<String> notificationIds = new ArrayList<String>();
		try {	
			//Fetching records in WAITING and RETRY status from buyer_outbound_notification for a specific service id and 
			//within specific no of retries.
			notificationVOList = hsrOutBoundNotificationDao.fetchRecords(noOfRetries, serviceId, recordsProcessingLimit);
			if(null != notificationVOList && !notificationVOList.isEmpty()){
				for(InHomeOutBoundNotificationVO records : notificationVOList){
					if(null != records.getNotificationId()){
						notificationIds.add(records.getNotificationId());
					}
				}
				//Updating status of fetched records to 'STARTED'. This will make sure another batch instance 
				//will not pick these records.
				if(null != notificationIds && !notificationIds.isEmpty()){
					hsrOutBoundNotificationDao.updateStatus(notificationIds);
				}
			}
		} 
		catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in fetching the records due to" + dse.getMessage(), dse);
		}
		return notificationVOList;
	}
	
	//Method to fetch the api details (url, header) from buyer_outbound_service table for a specific service_id.
	public HashMap <String, String> fetchApiDetails(String serviceId) throws BusinessServiceException{
		HashMap <String, String> apiDetails = new HashMap<String, String>();
		try{
			apiDetails = hsrOutBoundNotificationDao.fetchApiDetails(serviceId);	
		}
		catch(DataServiceException e){
			LOGGER.error("Exception in getting request header & url"+ e.getMessage());
			throw new BusinessServiceException("Exception in getting request header & url due to " + e.getMessage());
		}
		return apiDetails;
	}
	
	//Method to update buyer_outbound_notification table after calling the web service.
	public void updateNotification(InHomeOutBoundNotificationVO notification) throws BusinessServiceException{
		try {
			hsrOutBoundNotificationDao.updateNotification(notification);
		} 
		catch (DataServiceException dse) {
			LOGGER.error("Exception in updating data " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in updateNotificationl due to " + dse.getMessage());
		}
	}
	//Method to update nps indicator in so_workflow_controls 
	public void updateNPSIndicator(String soId) throws BusinessServiceException {
		try {
			hsrOutBoundNotificationDao.updateNPSIndicator(soId);
		} 
		catch (DataServiceException dse) {
			LOGGER.error("Exception in updating NPS Indicator " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in updateNPSIndicator due to " + dse.getMessage());
		}
		
	}
	
	public NotificationOwnerDetails getSystemDownEmailAddress(String errorCode) throws BusinessServiceException {
		try {
			return hsrOutBoundNotificationDao.getSystemDownEmailAddress(errorCode);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching address " + dse.getMessage(), dse);
			throw new BusinessServiceException(	"Exception in fetching address due to " + dse.getMessage(), dse);
		}
	}
	
	//Updating status of fetched records for 'System Down' error code to 'WAITING'
		public void updateSystemDownStatus(List<String> notificationIds) throws BusinessServiceException{
			try {
				hsrOutBoundNotificationDao.updateSystemDownStatus(notificationIds);
			} 
			catch (DataServiceException dse) {
				LOGGER.error("Exception in updateSystemDownStatus " + dse.getMessage(), dse);
				throw new BusinessServiceException("Exception in updateSystemDownStatus due to " + dse.getMessage());
			}
		}

	public IInHomeOutBoundNotificationDao getHsrOutBoundNotificationDao() {
		return hsrOutBoundNotificationDao;
	}

	public void setHsrOutBoundNotificationDao(
			IInHomeOutBoundNotificationDao hsrOutBoundNotificationDao) {
		this.hsrOutBoundNotificationDao = hsrOutBoundNotificationDao;
	}

	
	

}
