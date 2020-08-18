package com.newco.marketplace.persistance.daoImpl.smasdatasynch;

import java.util.List;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.smsdatasynch.ISMSDataSynchDao;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author Infosys
 * SL-18979
 *
 */
public class SMSDataSynchDaoImpl extends ABaseImplDao implements ISMSDataSynchDao{
	
	/**fetch the opt-in status of the participants in the file from DB
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws DataServiceException
	 */
	public List<SMSDataSynchVO> fetchSubscriptionDetails(List<String> inputPersonIds) throws DataServiceException {

		List<SMSDataSynchVO> recordsFromDB = null;
		try{
			
			recordsFromDB = (List<SMSDataSynchVO>) queryForList("fetchSubscriptionDetails.query", inputPersonIds);
		}
		catch(Exception e){
			throw new DataServiceException("Exception occurred in SMSDataSynchDaoImpl.fetchSubscriptionDetails(): " + e, e);
		}
		return recordsFromDB;
	}
	
	/**update the opt-in status of the participants 
	 * in vendor_resource_sms_subscription table
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws DataServiceException
	 */
	public void updateSubscriptionStatus(List<SMSDataSynchVO> recordsToBeUpdated) throws DataServiceException {
		try{
		 /* for(SMSDataSynchVO smsDataSynchVO: recordsToBeUpdated){
	          update("updateSubscriptionStatus.query", smsDataSynchVO);                
	       }*/
		 batchUpdate("updateSubscriptionStatus.query", recordsToBeUpdated);
			
		}catch(Exception e){
			throw new DataServiceException("Exception occurred in SMSDataSynchDaoImpl.updateSubscriptionStatus(): " + e, e);
		}
		
	}
	/**update the opt-in/out dates of the participants 
	 * in vendor_resource_sms_subscription table
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws DataServiceException
	 */
	public void updateSubscriptionNoStatus(List<SMSDataSynchVO> recordsToBeUpdated) throws DataServiceException {
		try{
			for(SMSDataSynchVO smsDataSynchVO: recordsToBeUpdated){
	             update("updateSubscriptionNoStatus.query", smsDataSynchVO);                
	      }
		}catch(Exception e){
			throw new DataServiceException("Exception occurred in SMSDataSynchDaoImpl.updateSubscriptionStatus(): " + e, e);
		}
		
	}

	/**insert in the vendor_resource_sms_subscription_history table
	 * for the participants whose opt-in status changed
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws DataServiceException
	 */
	public void insertSubscriptionHistory(List<SMSDataSynchVO> recordsToBeUpdated) throws DataServiceException {
		try{
			batchInsert("insertSubscriptionHistory.query", recordsToBeUpdated);
			
		}catch(Exception e){
			throw new DataServiceException("Exception occurred in SMSDataSynchDaoImpl.insertSubscriptionHistory(): " + e, e);
		}
	}
	
	/**fetch the subscription id from application_constants in DB
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String fetchSubscriptionId(String subscriptionIdConstant) throws DataServiceException{
		String subscriptionId = null;
		try{
			subscriptionId = (String) queryForObject(
					"subscriptionId.query", subscriptionIdConstant);
		}
		catch(Exception e){
			throw new DataServiceException("Exception occurred in SMSDataSynchDaoImpl.fetchSubscriptionId(): " + e, e);
		}
		return subscriptionId;
	}
	
	/**fetch the resource id from vendor_resource_sms_subscription in DB
	 * @return List<Integer>
	 * @throws BusinessServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> fetchResourceIds(List<String> personIdList) throws DataServiceException{
		List<Integer> resourceIdList = null;
		try{
			resourceIdList = (List<Integer>) queryForList(
					"resourceIdListToUpdAltContact.query", personIdList);
		}
		catch(Exception e){
			throw new DataServiceException("Exception occurred in SMSDataSynchDaoImpl.fetchResourceIds(): " + e, e);
		}
		return resourceIdList;
	}
	
	/**update alternate contact to primary email in case of opt_out
	 * @param List<Integer>
	 * @throws BusinessServiceException
	 */
	public void updateAltContact(List<Integer> resourceIdList) throws DataServiceException{
		try{
			update("resetAltContact.query",resourceIdList);
		}
		catch(Exception e){
			throw new DataServiceException("Exception occurred in SMSDataSynchDaoImpl.updateAltContact(): " + e, e);
		}
	}
}
