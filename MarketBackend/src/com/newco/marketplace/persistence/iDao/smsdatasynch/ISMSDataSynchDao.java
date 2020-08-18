package com.newco.marketplace.persistence.iDao.smsdatasynch;

import java.util.List;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;


/**
 * @author Infosys
 * SL-18979
 *
 */
public interface ISMSDataSynchDao {

	/**fetch the opt-in status of the participants in the file from DB
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws DataServiceException
	 */
	public List<SMSDataSynchVO> fetchSubscriptionDetails(List<String> inputPersonIds) throws DataServiceException;
	
	/**update the opt-in status of the participants 
	 * in vendor_resource_sms_subscription table
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws DataServiceException
	 */
	public void updateSubscriptionStatus(List<SMSDataSynchVO> recordsToBeUpdated) throws DataServiceException; 
	

	/**update the opt-in/out dates of the participants 
	 * in vendor_resource_sms_subscription table
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws DataServiceException
	 */
	public void updateSubscriptionNoStatus(List<SMSDataSynchVO> recordsToBeUpdated) throws DataServiceException; 

	/**insert in the vendor_resource_sms_subscription_history table
	 * for the participants whose opt-in status changed
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws DataServiceException
	 */
	public void insertSubscriptionHistory(List<SMSDataSynchVO> recordsToBeUpdated) throws DataServiceException; 
	
	/**fetch the subscription id from application_constants in DB
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String fetchSubscriptionId(String subscriptionIdConstant) throws DataServiceException;
	
	/**fetch the resource id from vendor_resource_sms_subscription in DB to update alternate contact to primary email in case of opt_out
	 * @return List<Integer>
	 * @throws BusinessServiceException
	 */
	public List<Integer> fetchResourceIds(List<String> personIdList) throws DataServiceException;
	
	/**update alternate contact to primary email in case of opt_out
	 * @param List<Integer>
	 * @throws BusinessServiceException
	 */
	public void updateAltContact(List<Integer> resourceIdList) throws DataServiceException;
	
}