package com.newco.marketplace.persistance.daoImpl.smsdatamigration;

import java.util.List;

import org.apache.log4j.Logger;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.smsdatamigration.ISMSDataMigrationDao;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author Infosys
 * SL-18979
 *
 */
public class SMSDataMigrationDaoImpl extends ABaseImplDao implements ISMSDataMigrationDao{
	
	private static final Logger logger = Logger.getLogger(SMSDataMigrationDaoImpl.class);
	

	/**@Description: Get all the market ready providers from the tmp table
	 * @return
	 * @throws DataServiceException
	 */
	public  List<SMSDataSynchVO> getMarketReadySMSVendorsForSms() throws DataServiceException{
		List<SMSDataSynchVO> marketReadySmsVendorList=null;
		try{
			marketReadySmsVendorList = (List<SMSDataSynchVO>)queryForList("getMarketReadyProvidersForSms.query");
		}
		catch (Exception e) {
			logger.error("Exception in SMSDataMigrationDaoImpl method getMarketReadySMSVendors()" +e.getMessage());
			new DataServiceException("Exception in SMSDataMigrationDaoImpl method getMarketReadySMSVendors()",e);
		}
		return marketReadySmsVendorList;
		
	}
	
	
	
	/**@Description: Get all the providers not in inactive/deleted from the sms_subscription table for an sms 
	 * @return
	 * @throws DataServiceException
	 */
	public  List<SMSDataSynchVO> getActiveVendorsForSms(String mdn) throws DataServiceException{
		List<SMSDataSynchVO> inactiveDeletedSMSVendorList=null;
		try{
			inactiveDeletedSMSVendorList = (List<SMSDataSynchVO>)queryForList("getActiveProvidersForSms.query",mdn);
		}
		catch (Exception e) {
			logger.error("Exception in SMSDataMigrationDaoImpl method getActiveVendorsForSms()" +e.getMessage());
			new DataServiceException("Exception in SMSDataMigrationDaoImpl method getActiveVendorsForSms()",e);
		}
		return inactiveDeletedSMSVendorList;
	}


	/**@Description: Update the migration status of the resources
	 * @return
	 * @throws DataServiceException
	 */
	public void updateMigrationDetails(List<SMSDataSynchVO> toBeAddedResources) throws DataServiceException {
		try{
			batchUpdate("migrationStatus.update", toBeAddedResources);
		}
		catch (Exception e) {
			throw new DataServiceException("Exception in SMSDataMigrationDaoImpl method updateMigrationDetails()",e);
		}
		
	}
	
	/**@Description: Update the migration status of the resources
	 * @return
	 * @throws DataServiceException
	 */
	public void updateMigrationDetailsForSms(SMSDataSynchVO smsDataSynchVO) throws DataServiceException {
		try{
           update("migrationStatus.update", smsDataSynchVO);
		}
		catch (Exception e) {
			throw new DataServiceException("Exception in SMSDataMigrationDaoImpl method updateMigrationDetailsForSms()",e);
		}
		
	}
	
	/**@Description: Get the inactive subscription record for an SMS
	 * @return
	 * @throws DataServiceException
	 */
	public SMSDataSynchVO getInactiveSubscriptionDetail(String smsNo) throws DataServiceException{
		SMSDataSynchVO inactiveRecord = null;
		try{
			inactiveRecord = (SMSDataSynchVO)queryForObject("getInactiveSubscriptionDetail.query", smsNo);
		}
		catch (Exception e) {
			new DataServiceException("Exception in SMSDataMigrationDaoImpl method getInactiveSubscriptionDetail()",e);
		}
		return inactiveRecord;
	}

	/**@Description: insert the subscription details in  sms_subscription table
	 * @param paramMap
	 * @throws DataServiceException
	 */

	public void insertSubscriptionDetails(List<SMSDataSynchVO> resourceList)throws DataServiceException {
		try{
			batchInsert("insertSubscriptionDetails.query", resourceList);
		}
		catch (Exception e) {
			throw new DataServiceException("General Exception @SMSDataMigrationDaoImpl.insertSubscriptionDetails() due to "+ e.getMessage());
	  	}
	}
	
	
	/**@Description: insert the subscription details in  sms_subscription table
	 * @param paramMap
	 * @throws DataServiceException
	 */
	public void insertSubscriptionDetailsForSms(SMSDataSynchVO smsDataSynchVO)throws DataServiceException {
		try{		
       insert("insertSubscriptionDetails.query", smsDataSynchVO);
		}
		catch (Exception e) {
			throw new DataServiceException("General Exception @SMSDataMigrationDaoImpl.insertSubscriptionDetails() due to "+ e.getMessage());
	  	}
	}
	


}
