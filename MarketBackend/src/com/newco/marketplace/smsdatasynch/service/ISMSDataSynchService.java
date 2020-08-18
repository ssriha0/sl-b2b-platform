package com.newco.marketplace.smsdatasynch.service;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;


/**
 * @author Infosys
 * SL-18979
 */

public interface ISMSDataSynchService {

	/**fetch the opt-in status of the participants in the file from DB
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws BusinessServiceException
	 */
	public List<SMSDataSynchVO> fetchSubscriptionDetails(List<String> inputPersonIds) throws BusinessServiceException;

	/**update the opt-in status of the participants 
	 * in vendor_resource_sms_subscription table
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws BusinessServiceException
	 */
	public void updateSubscriptionStatus(List<SMSDataSynchVO> recordsToBeUpdated) throws BusinessServiceException;

	/**update the opt in date/opt out date of the participants 
	 * in vendor_resource_sms_subscription table
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws BusinessServiceException
	 */
	public void updateSubscriptionNoStatus(List<SMSDataSynchVO> recordsToBeUpdated) throws BusinessServiceException;
	
	/**insert in the vendor_resource_sms_subscription_history table
	 * for the participants whose opt-in status changed
	 * @param inputPersonIds
	 * @return List<SMSDataSynchVO>
	 * @throws BusinessServiceException
	 */
	public void insertSubscriptionHistory(List<SMSDataSynchVO> recordsToBeUpdated) throws BusinessServiceException;
	
	/**fetch the subscription id from application_constants in DB
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String fetchSubscriptionId(String subscriptionIdConstant) throws BusinessServiceException;
	
	/**fetch the resource id from DB
	 * @return List<Integer>
	 * @throws BusinessServiceException
	 */
	public List<Integer> fetchResourceIds(List<String> personIdList) throws BusinessServiceException;
	
	/**update alternate contact method in contact table for records who has opted out
	 * @throws BusinessServiceException
	 */
	public void updateAltContact(List<Integer> resourceIdList) throws BusinessServiceException;

}
