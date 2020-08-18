package com.newco.marketplace.persistence.iDao.smsdatamigration;

import java.util.List;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;

public interface ISMSDataMigrationDao {
	

	
	/**@Description: Get all the market ready providers from the tmp table
	 * @return
	 * @throws DataServiceException
	 */
	public  List<SMSDataSynchVO> getMarketReadySMSVendorsForSms() throws DataServiceException;

	
	/**@Description: Get all the providers not in inactive/deleted from the sms_subscription table
	 * @return
	 * @throws DataServiceException
	 */
	public  List<SMSDataSynchVO> getActiveVendorsForSms(String mdn) throws DataServiceException;


	/**@Description: Update the migration status of the resources
	 * @return
	 * @throws DataServiceException
	 */
	public void updateMigrationDetails(List<SMSDataSynchVO> toBeAddedResources) throws DataServiceException;
	
	/**@Description: Update the migration status of the resources
	 * @return
	 * @throws DataServiceException
	 */
	public void updateMigrationDetailsForSms(SMSDataSynchVO smsDataSynchVO) throws DataServiceException;


	/**@Description: Get the inactive subscription record for an SMS
	 * @return
	 * @throws DataServiceException
	 */
	public SMSDataSynchVO getInactiveSubscriptionDetail(String smsNo) throws DataServiceException;
	
	/**@Description: insert the subscription details in  sms_subscription table
	 * @param resourceList
	 * @throws DataServiceException
	 */
   public void insertSubscriptionDetails(List<SMSDataSynchVO> resourceList) throws DataServiceException;
   
	/**@Description: insert the subscription details in  sms_subscription table
	 * @param resourceList
	 * @throws DataServiceException
	 */
	public void insertSubscriptionDetailsForSms(SMSDataSynchVO smsDataSynchVO)throws DataServiceException;


  
}
