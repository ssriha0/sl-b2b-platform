package com.newco.marketplace.persistance.daoImpl.inhomeOutBoundNotification;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;
import com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification.IInHomeOutBoundNotificationDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class InHomeOutBoundNotificationDaoImpl extends ABaseImplDao implements IInHomeOutBoundNotificationDao{
	
	private static final Logger LOGGER = Logger.getLogger(InHomeOutBoundNotificationDaoImpl.class.getName());
	
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
	
	//Get property value for a corresponding key from application_flags table. 
	public String getPropertyFromApplicationFlag(String inhomeNpsFlag) throws DataServiceException {
		String flag = null;
		try{
			flag =(String) queryForObject("getAppFlagValue.query", inhomeNpsFlag);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in getPropertyFromApplicationFlag: "+e.getMessage());
			throw new DataServiceException("Exception occurred in getPropertyFromApplicationFlag: "+e.getMessage(),e);
		}
		return flag;
	}
	
	//Fetch the count of records in the table buyer_outbound_notification for a specific service id and 
	//within specific no of retries.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer fetchRecordsCount(Integer noOfRetries, String serviceId) throws DataServiceException{
		HashMap params = new HashMap();
		params.put("noOfRetries", noOfRetries);
		params.put("serviceId", serviceId);
		
		Integer count = null;
		try{
			count = (Integer) queryForObject("getOutboundRecordsCount.query", params);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in fetchRecordsCount: "+e.getMessage());
			throw new DataServiceException("Exception occurred in fetchRecordsCount: "+e.getMessage(),e);
		}
		return count;
	}
	
	//Method to fetch the records in the table buyer_outbound_notification for a specific service id.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<InHomeOutBoundNotificationVO> fetchRecords(Integer noOfRetries, String serviceId, 
			Integer recordsProcessingLimit) throws DataServiceException {
		
		List<InHomeOutBoundNotificationVO> notificationVOList=null;
		try{
			HashMap hm = new HashMap();
			hm.put("noOfRetries", noOfRetries);
			hm.put("serviceId", serviceId);
			hm.put("recordsProcessingLimit", recordsProcessingLimit);
			notificationVOList = (List<InHomeOutBoundNotificationVO>) queryForList("fetchOutboundRecords.query", hm);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in fetchRecords: "+e.getMessage());
			throw new DataServiceException("Exception occurred in fetchRecords: "+e.getMessage(),e);
		}
		return notificationVOList;
	}
	
	//Updating status of fetched records to 'STARTED'. This will make sure another batch instance 
	//will not pick these records.
	public void updateStatus(List<String> notificationIds) throws DataServiceException{
		try{
			update("updateNotificationStatus.query", notificationIds);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in updateStatus: "+e.getMessage());
			throw new DataServiceException("Exception occurred in updateStatus: "+e.getMessage(),e);
		}
	}
	
	//Method to fetch the api details (url, header) from buyer_outbound_service table for a specific service_id.
	@SuppressWarnings("unchecked")
	public HashMap <String, String> fetchApiDetails(String serviceId) throws DataServiceException {
		HashMap <String, String> apiDetails = new HashMap <String, String>();
		try{
			apiDetails =(HashMap <String, String>) queryForObject("fetchApiDetails.query", serviceId);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in fetchApiDetails: "+e.getMessage());
			throw new DataServiceException("Exception occurred in fetchApiDetails: "+e.getMessage(),e);
		}
		return apiDetails;
	}
	
	//Method to update buyer_outbound_notification table after calling the web service.
	public void updateNotification(InHomeOutBoundNotificationVO notification) throws DataServiceException{
		try{
			update("updateInhomeNotification.query", notification);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in updateNotification: "+e.getMessage());
			throw new DataServiceException("Exception occurred in updateNotification: "+e.getMessage(),e);
		}
	}
	//Method to update nps indicator in so_workflow_controls
	public void updateNPSIndicator(String soId) throws DataServiceException {
		try{
			update("updateNpsIndicator.query", soId);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in updateNPSIndicator: "+e.getMessage());
			throw new DataServiceException("Exception occurred in updateNPSIndicator: "+e.getMessage(),e);
		}
		
	}

	//To fetch system down error code email for Inhome OutBound Notification
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NotificationOwnerDetails getSystemDownEmailAddress(String errorCode) throws DataServiceException{
		NotificationOwnerDetails notificationOwnerDetails = null;
		try{
			notificationOwnerDetails = (NotificationOwnerDetails) queryForObject("getOutboundEmailSystemDown.query", errorCode);
		}
		catch(Exception e){
			LOGGER.info("Exception occurred in getSystemDownEmailAddress: "+e.getMessage());
			throw new DataServiceException("Exception occurred in getSystemDownEmailAddress: "+e.getMessage(),e);
		}
		return notificationOwnerDetails;
	}
	
	
	//Updating status of fetched records for 'System Down' error code to 'WAITING'. This will make sure another batch instance 
		//will pick these records.
		public void updateSystemDownStatus(List<String> notificationIds) throws DataServiceException{
			try{
				update("updateSystemDownStatus.query", notificationIds);
			}
			catch(Exception e){
				LOGGER.info("Exception occurred in updateSystemDownStatus: "+e.getMessage());
				throw new DataServiceException("Exception occurred in updateSystemDownStatus: "+e.getMessage(),e);
			}
		}
	
}
