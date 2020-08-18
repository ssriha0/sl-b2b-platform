package com.newco.marketplace.business.businessImpl.smasdatasynch;

import java.util.List;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.smsdatasynch.ISMSDataSynchDao;
import com.newco.marketplace.smsdatasynch.service.ISMSDataSynchService;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;

/**
 * @author Infosys
 * SL-18979
 * Service Implementation class for SMS Data Synch between Vibes and ServiceLive
 */

public class SMSDataSynchServiceImpl implements ISMSDataSynchService {
	
	private ISMSDataSynchDao smsDataSynchProcessDao;
	

	/**fetch the opt-in status of the participants in the file from DB
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws BusinessServiceException
	 */
	public List<SMSDataSynchVO> fetchSubscriptionDetails(List<String> inputPersonIds) throws BusinessServiceException {

		List<SMSDataSynchVO> recordsFromDB = null;
		
		try{
			recordsFromDB = smsDataSynchProcessDao.fetchSubscriptionDetails(inputPersonIds);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception occurred in SMSDataSynchServiceImpl.fetchSubscriptionDetails(): " +e);
		}
		return recordsFromDB;
	}
	
	/**update the opt-in status of the participants 
	 * in vendor_resource_sms_subscription table
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws BusinessServiceException
	 */
	public void updateSubscriptionStatus(List<SMSDataSynchVO> recordsToBeUpdated) throws BusinessServiceException {
		try{
			smsDataSynchProcessDao.updateSubscriptionStatus(recordsToBeUpdated);
			
		}catch(DataServiceException e){
			throw new BusinessServiceException("Exception occurred in SMSDataSynchServiceImpl.updateSubscriptionStatus(): " + e, e);
		}
	}
	
	/**update the opt-in/out Date  of the participants 
	 * in vendor_resource_sms_subscription table
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws BusinessServiceException
	 */
	public void updateSubscriptionNoStatus(List<SMSDataSynchVO> recordsToBeUpdated) throws BusinessServiceException {
		try{
			smsDataSynchProcessDao.updateSubscriptionNoStatus(recordsToBeUpdated);
			
		}catch(DataServiceException e){
			throw new BusinessServiceException("Exception occurred in SMSDataSynchServiceImpl.updateSubscriptionNoStatus(): " + e, e);
		}
	}

	/**insert in the vendor_resource_sms_subscription_history table
	 * for the participants whose opt-in status changed
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws BusinessServiceException
	 */
	public void insertSubscriptionHistory(List<SMSDataSynchVO> recordsToBeUpdated) throws BusinessServiceException {
		try{
			smsDataSynchProcessDao.insertSubscriptionHistory(recordsToBeUpdated);
			
		}catch(DataServiceException e){
			throw new BusinessServiceException("Exception occurred in SMSDataSynchServiceImpl.insertSubscriptionHistory(): " + e, e);
		}
	}
	
	/**fetch the subscription id from DB
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String fetchSubscriptionId(String subscriptionIdConstant) throws BusinessServiceException {

		String subscriptionId = null;
		
		try{
			subscriptionId = smsDataSynchProcessDao.fetchSubscriptionId(subscriptionIdConstant);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception occurred in SMSDataSynchServiceImpl.fetchSubscriptionId(): " +e);
		}
		return subscriptionId;
	}
	
	/**fetch the resource id from DB
	 * @return List<Integer>
	 * @throws BusinessServiceException
	 */
	public List<Integer> fetchResourceIds(List<String> personIdList) throws BusinessServiceException {

		List<Integer> resourceIdList = null;
		
		try{
			resourceIdList = smsDataSynchProcessDao.fetchResourceIds(personIdList);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception occurred in SMSDataSynchServiceImpl.fetchResourceIds(): " +e);
		}
		return resourceIdList;
	}
	/**update alt contact method in contact table for records who has opted out
	 * @throws BusinessServiceException
	 */
	public void updateAltContact(List<Integer> resourceIdList) throws BusinessServiceException {	
		try{
			smsDataSynchProcessDao.updateAltContact(resourceIdList);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception occurred in SMSDataSynchServiceImpl.updateAltContact(): " +e);
		}
	}
	
	
	
	
	public ISMSDataSynchDao getSmsDataSynchProcessDao() {
		return smsDataSynchProcessDao;
	}

	public void setSmsDataSynchProcessDao(ISMSDataSynchDao smsDataSynchProcessDao) {
		this.smsDataSynchProcessDao = smsDataSynchProcessDao;
	}


}
