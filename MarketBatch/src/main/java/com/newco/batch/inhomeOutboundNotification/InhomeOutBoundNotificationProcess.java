package com.newco.batch.inhomeOutboundNotification;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.inhomeoutboundnotification.service.IInhomeOutBoundAPIService;
import com.newco.marketplace.inhomeoutboundnotification.service.IInhomeOutBoundNotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;


public class InhomeOutBoundNotificationProcess {
	
	private static final Logger logger = Logger.getLogger(InhomeOutBoundNotificationProcess.class);
	
	//Use inhomeOutBoundNotificationService to fetch details from DB		
	private IInhomeOutBoundNotificationService inhomeOutBoundNotificationService;
	//Use inhomeOutBoundAPIService to call the API
	private IInhomeOutBoundAPIService inhomeOutBoundAPIService;
	
	// process notification for call close
	public void processCallClose() {
		try {
			process(InHomeNPSConstants.CLOSE_SERVICE_ID);
		}
		catch (Exception e) {
			logger.error("Exception in InhomeOutBoundNotificationProcess.processNotification() due to "+e.getMessage(), e);
		}
	}

	//process notification for status change & add notes
	public void processMessages() {
		try {
			process(InHomeNPSConstants.SUBSTATUS_SERVICE_ID);
		}
		catch (Exception e) {
			logger.error("Exception in InhomeOutBoundNotificationProcess.processNotification() due to "+e.getMessage(), e);
		}
	}
	
	public void process(String serviceId) throws Exception{
		logger.info("InhomeOutBoundNotificationProcess.processNotification: Inside processNotification");
		//inhome_outbound_flag is the switch used for Inhome Notification. This value is fetched from the table 'application_flags'.
		//We have to make sure this flag is ON in db.
		if((InHomeNPSConstants.INHOME_NPS_MESSAGE_FLAG_ON).equalsIgnoreCase(inhomeOutBoundNotificationService.getInHomeNpsFlag(InHomeNPSConstants.INHOME_NPS_MESSAGE_FLAG))){
			logger.info("InhomeOutBoundNotificationProcess.processNotification: inhome_outbound_flag is ON");
			//Fetching the no.of retries allowed for failure cases.
			Integer noOfRetries = Integer.parseInt(inhomeOutBoundNotificationService.getPropertyFromDB(InHomeNPSConstants.NPS_NOTIFICATION_INHOME_NO_OF_RETRIES));
			logger.debug("InhomeOutBoundNotificationProcess.processNotification: noOfRetries:"+noOfRetries);
			//Fetching the no. of records to be processed in one go.
			Integer recordsProcessingLimit = Integer.parseInt(inhomeOutBoundNotificationService.getPropertyFromDB(InHomeNPSConstants.NPS_NOTIFICATION_INHOME_RECORDS_PROCESSING_LIMIT));
			logger.debug("InhomeOutBoundNotificationProcess.processNotification: recordsProcessingLimit:"+recordsProcessingLimit);
			//Fetching the total no. of records that need to be processed.
			Integer recordsCount = inhomeOutBoundNotificationService.fetchRecordsCount(noOfRetries, serviceId);
			logger.debug("InhomeOutBoundNotificationProcess.processNotification: recordsCount:"+recordsCount);
			//To avoid memory issues, only limited number of records will be processed at a time. The count is configured in application_properties
			//table. This value will be present in the variable 'recordsProcessingLimit'.
			while(recordsCount > 0){
				//Fetching records in WAITING and RETRY status from buyer_outbound_notification for a specific service id and 
				//within specific no of retries.
				List<InHomeOutBoundNotificationVO> notificationList = 
						inhomeOutBoundNotificationService.fetchRecords(noOfRetries, serviceId, recordsProcessingLimit);
				logger.info("InhomeOutBoundNotificationProcess.processNotification: No exception in fetchRecords");
						 
				List<String> notificationIds = new ArrayList<String>();
				
				if(null != notificationList){
					for (InHomeOutBoundNotificationVO vo : notificationList) {
						if(null != vo && null != vo.getXml() && null != vo.getSoId() && null != noOfRetries){
							//Processing each record by calling the web service.
							logger.info("InhomeOutBoundNotificationProcess.processNotification: About to call callAPIService()");
							String emailConstant=inhomeOutBoundAPIService.callAPIService(noOfRetries, vo, serviceId);
							if(InHomeNPSConstants.EMAIL_SYSTEM_DOWN_CONSTANT.equals(emailConstant))
							{
								recordsCount=0;
								for(InHomeOutBoundNotificationVO records : notificationList){
									if(null != records.getNotificationId()){
										notificationIds.add(records.getNotificationId());
																			}		
								}
								//Updating status of fetched records to 'WAITING'. This will make sure another batch instance 
								//will pick these records.
									if(null != notificationIds && !notificationIds.isEmpty()){
										inhomeOutBoundNotificationService.updateSystemDownStatus(notificationIds);
									}

									break;
							}
						}
					}
				}
				recordsCount = recordsCount - recordsProcessingLimit;
			}
		}
	}

	public IInhomeOutBoundNotificationService getInhomeOutBoundNotificationService() {
		return inhomeOutBoundNotificationService;
	}

	public void setInhomeOutBoundNotificationService(
			IInhomeOutBoundNotificationService inhomeOutBoundNotificationService) {
		this.inhomeOutBoundNotificationService = inhomeOutBoundNotificationService;
	}

	public IInhomeOutBoundAPIService getInhomeOutBoundAPIService() {
		return inhomeOutBoundAPIService;
	}

	public void setInhomeOutBoundAPIService(
			IInhomeOutBoundAPIService inhomeOutBoundAPIService) {
		this.inhomeOutBoundAPIService = inhomeOutBoundAPIService;
	}
}
