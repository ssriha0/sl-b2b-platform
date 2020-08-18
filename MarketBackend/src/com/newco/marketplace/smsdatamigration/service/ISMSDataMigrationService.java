
package com.newco.marketplace.smsdatamigration.service;

import java.util.List;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;

/**
 * @author Infosys
 *
 */
public interface ISMSDataMigrationService {
	
	
	/**@Description: Invoke addParticipant API of Vibes
	 * @return
	 * @throws BusinessServiceException
	 */
	 public SMSDataSynchVO addParticipantinVibes(SMSDataSynchVO migartionVo) throws BusinessServiceException;
	
	
	
	/**@Description: Get all the market ready providers from the tmp table
	 * @return
	 * @throws BusinessServiceException
	 */
	public  List<SMSDataSynchVO> getRecordsForMigrationForSms() throws BusinessServiceException;

	
	/**@Description: Get all the providers not in inactive/deleted from the sms_subscription table
	 * @return
	 * @throws BusinessServiceException
	 */
	public  List<SMSDataSynchVO> getSubscriptionDetailsForSms(String mdn) throws BusinessServiceException;

	
	/**@Description: Update the migration status of the resources
	 * @return
	 * @throws BusinessServiceException
	 */
	public void updateMigrationDetails(List<SMSDataSynchVO> toBeAddedResources,
			String statusSuccess, String message) throws BusinessServiceException;
	
	/**@Description: Update the migration status of the resources
	 * @return
	 * @throws BusinessServiceException
	 */
	public void updateMigrationDetailsForSms(SMSDataSynchVO smsDataSynchVO,
			String status, String message) throws BusinessServiceException;

	
	/**@Description: Get the inactive subscription record for an SMS
	 * @return
	 * @throws BusinessServiceException
	 */
	public SMSDataSynchVO getInactiveSubscriptionDetail(String smsNo) throws BusinessServiceException;
	
	/**@Description: insert the subscription details in  sms_subscription table
	 * @param resourceList
	 * @throws BusinessServiceException
	 */
	public void insertSubscriptionDetails(List<SMSDataSynchVO> resourceList)throws BusinessServiceException;
	
	/**@Description: insert the subscription details in  sms_subscription table
	 * @param resourceList
	 * @throws BusinessServiceException
	 */
	public void insertSubscriptionDetailsForSms(SMSDataSynchVO smsDataSynchVO) throws BusinessServiceException;

}
