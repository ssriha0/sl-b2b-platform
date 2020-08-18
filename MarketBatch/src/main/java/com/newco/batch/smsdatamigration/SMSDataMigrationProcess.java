package com.newco.batch.smsdatamigration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.smsdatamigration.service.ISMSDataMigrationService;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;

/**
 * @author Infosys
 * SL-18979: This will migrate the resources whose alt contact 
 * method is SMS and not having an entry for the same in 
 * vendor_sms_subscription table
 * 
 */
public class SMSDataMigrationProcess {

	private static final Logger logger = Logger.getLogger(SMSDataMigrationProcess.class);

	private ISMSDataMigrationService smsDataMigrationService;

	
	
	/**
	 * This will migrate the resources whose alt contact 
     * method is SMS and not having an entry for the same in 
     * vendor_sms_subscription table
	 * @throws BusinessServiceException
	 */
	
	public void processSmsRecords() throws BusinessServiceException{
		try{
			//Fetch the details of the resources from vendor_resource_sms_migration_temp table 
			//whose status is Approved/Market ready and migration status is Pending 
			//to do order by vendor id
			List<SMSDataSynchVO> recordsForMigration = smsDataMigrationService.getRecordsForMigrationForSms();
			if(null == recordsForMigration || (null != recordsForMigration && recordsForMigration.isEmpty())){
				logger.info("No records available for migration");
				return;
			}
			for(SMSDataSynchVO resource:recordsForMigration){	
				try{
			// process each migration records
				processMigrationRecords(resource);
			}catch(Exception e){
				logger.error("error in migrating the record with migration Id "+resource.getMigrationId()+" Resource id "+resource.getResourceId()+" Error details"+e.getMessage());
				smsDataMigrationService.updateMigrationDetailsForSms(resource, Constants.STATUS_ERROR, e.getMessage());	
			}
		 }
		}
		catch(Exception e){
			throw new BusinessServiceException("Exception occurred in SMSDataMigrationProcess.processRecords() due to " + e);
		}	
	}





	private void processMigrationRecords(SMSDataSynchVO resource)
			throws BusinessServiceException {
		//fetch vendor_resource_sms_subscrition for the sms number having record in active/deleted for non terminated resources.
		List<SMSDataSynchVO> recordsFromDB=new ArrayList<SMSDataSynchVO>();
		
		String mdn = resource.getMdn();
		
		if(StringUtils.isNotBlank(mdn));{
			recordsFromDB=smsDataMigrationService.getSubscriptionDetailsForSms(mdn);
		}
		if(null!=recordsFromDB && !recordsFromDB.isEmpty()){
		  // if subscription records exist in db forthe sms number, then  copy them if it exist for the resource of the same vendor
		 copySubscriptionDetailsIfExist(resource, recordsFromDB);
		
		}else{
			// invoke vibes if the subscription details doesnot exist in vendor_resource_sms_subscription
			invokeVibesForSubscription(resource);				
		}
	}





	private void invokeVibesForSubscription(SMSDataSynchVO resource)
			throws
			BusinessServiceException {
		
		//invoke add participant
		SMSDataSynchVO subscriptionResponse = null;
		try{
			subscriptionResponse = smsDataMigrationService.addParticipantinVibes(resource);
		}
		catch(Exception e){
			 //in case of exception from Vibes
			//update the migration_status as ERROR 
		smsDataMigrationService.updateMigrationDetailsForSms(resource, Constants.STATUS_ERROR, e.getMessage());
			
		}
		//If the response is success
		// Response codes 200/201/409
		if(null != subscriptionResponse && 
				(200 == subscriptionResponse.getStatusCode()|| 201 == subscriptionResponse.getStatusCode() || 409 == subscriptionResponse.getStatusCode())){
				
			// If the response code is 409(already subscribed), get the details from vendor_resource_sms_susbscription
			if(409 == subscriptionResponse.getStatusCode()){
				SMSDataSynchVO inactiveSubscription = smsDataMigrationService.getInactiveSubscriptionDetail(resource.getMdn());
				if(null!= inactiveSubscription){
				  inactiveSubscription.setStatus(Constants.ACTIVE);
				  inactiveSubscription.setOptInIndicator(true);
				  subscriptionResponse = inactiveSubscription;
				}
			}		
			//Insert the details for the resources 
			//in vendor_resource_sms_subscription table 
			if(null!= subscriptionResponse && StringUtils.isNotBlank(subscriptionResponse.getPersonId())){
				insertSubscriptionDetailsForSms(subscriptionResponse, resource);
				//Update the migration_status as SUCCESS for all the resources
				smsDataMigrationService.updateMigrationDetailsForSms(resource, Constants.STATUS_SUCCESS, null);
			}
		   
		}
		else{
			//If no response or error response
			//update the migration_status as ERROR for all the resources under the vendors
			String error = null;
			if(null != subscriptionResponse){
				error = subscriptionResponse.getStatusText();
			}
			smsDataMigrationService.updateMigrationDetailsForSms(resource, Constants.STATUS_ERROR, error);
			
		}
	}





	private void copySubscriptionDetailsIfExist(SMSDataSynchVO resource,
			List<SMSDataSynchVO> recordsFromDB)
			throws BusinessServiceException {
		// check whether the subscription detail exist for the resource of the same vendor
		boolean sameVendorDetailsFound=false;
		
		for(SMSDataSynchVO smsDataSynchVO:recordsFromDB){
			if(resource.getVendorId().equals(smsDataSynchVO.getVendorId())){
				//copy subscription details
				resource.setPersonId(smsDataSynchVO.getPersonId());
				resource.setParticipationDateString(smsDataSynchVO.getParticipationDateString());
				resource.setExpireDateString(smsDataSynchVO.getExpireDateString());
				resource.setStatus(smsDataSynchVO.getStatus());
				resource.setOptInIndicator(smsDataSynchVO.getOptInIndicator());
				resource.setOptInDate(smsDataSynchVO.getOptInDate());
				resource.setOptOutDate(smsDataSynchVO.getOptOutDate());
				resource.setCreatedBy(Constants.SMS_MIGRATION_BATCH);
				resource.setModifiedBy(Constants.SMS_MIGRATION_BATCH);
				sameVendorDetailsFound=true;
				break;
			}
		}
		
		if(!sameVendorDetailsFound){
			// update migration records status as duplicate if the subscribtion details exist for another firm
			smsDataMigrationService.updateMigrationDetailsForSms(resource, Constants.STATUS_DUPLICATE, Constants.DUPLICATE_MESSAGE);
		}else{
			// insert into vendor_resource_sms_subscription
			smsDataMigrationService.insertSubscriptionDetailsForSms(resource);
			// mark migration records status as SUCCESS
			smsDataMigrationService.updateMigrationDetailsForSms(resource, Constants.STATUS_SUCCESS, null);
		}
	}
	
	
	
	
	
	
	


	
	
	/**
	 * This method will insert the subscription details
	 * @param subscriptionDetail
	 * @throws BusinessServiceException 
	 */
	private void insertSubscriptionDetailsForSms(SMSDataSynchVO subscriptionDetail, SMSDataSynchVO resource) throws BusinessServiceException {
		
			
	    	if(null != subscriptionDetail && null != resource){
						resource.setPersonId(subscriptionDetail.getPersonId());
						resource.setParticipationDateString(subscriptionDetail.getParticipationDateString());
						resource.setExpireDateString(subscriptionDetail.getExpireDateString());
						resource.setStatus(subscriptionDetail.getStatus());
						resource.setOptInIndicator(subscriptionDetail.getOptInIndicator());
						resource.setOptInDate(subscriptionDetail.getOptInDate());
						resource.setOptOutDate(subscriptionDetail.getOptOutDate());
						resource.setCreatedBy(Constants.SMS_MIGRATION_BATCH);
						resource.setModifiedBy(Constants.SMS_MIGRATION_BATCH);
					
			   try{	
				  smsDataMigrationService.insertSubscriptionDetailsForSms(resource);
			   }catch (Exception e) {
				  throw new BusinessServiceException("Exception occurred in insertSubscriptionDetails() while saving subscription details " + e);
			   }
			}
		
	}
	


	public ISMSDataMigrationService getSmsDataMigrationService() {
		return smsDataMigrationService;
	}



	public void setSmsDataMigrationService(
			ISMSDataMigrationService smsDataMigrationService) {
		this.smsDataMigrationService = smsDataMigrationService;
	}
}
