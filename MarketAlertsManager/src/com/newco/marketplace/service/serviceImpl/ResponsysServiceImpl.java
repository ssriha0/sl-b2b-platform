/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 14-Aug-2015	KMSLTVSZ     Infosys			1.0
 * 
 * 
 */
package com.newco.marketplace.service.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.mail.MailSendException;

import com.newco.marketplace.business.EmailAlertBO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.service.EmailAlertService;
import com.newco.marketplace.utils.constants.EmailAlertConstants;
import com.newco.marketplace.utils.utility.EmailAlertUtility;
import com.newco.servicelive.campaign.service.ResponsysInteractServiceImpl;

/**
 * This class is for sending mails using Responsys Interact Connect API service.
 * SL-20653 Responsys Upgrade
 * @author Infosys
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResponsysServiceImpl implements EmailAlertService {
	private static final Logger logger = Logger.getLogger(ResponsysServiceImpl.class.getName());
	private EmailAlertBO emailAlertBO;	
	private EmailAlertUtility emailAlertUtility;
	ResponsysInteractServiceImpl providerResponsysService;
	private boolean responsysInteractEnableMode;

	/**
	 * This method processes the records in the alert_task table and calls the
	 * method for sending mail through responsys
	 * 
	 * @return void
	 */
	public void processAlert() {
		logger.info("Entering ResponsysServiceImpl-->processAlert()");
		ArrayList<AlertTask> alertTaskList = null;
		
		if (isResponsysInteractEnableMode()) {			
			try {
				logger.info("Fetching AlertTask list");
				alertTaskList = (ArrayList<AlertTask>) emailAlertBO
						.retrieveAlertTasks(EmailAlertConstants.RTM_PRIORITY,
								EmailAlertConstants.PROV_RESPONSYS);
				//TODO loggers to be removed
				if(null != alertTaskList && !alertTaskList.isEmpty()){
					logger.info("alertTaskList Size:"+alertTaskList.size());
				}else{
					logger.info("alertTaskList is empty...");
				}
				//TODO loggers to be removed

			} catch (DataServiceException dse) {
				logger
						.error(
								"ResponsysServiceImpl-->processAlert-->DataServiceException-->",
								dse);
			}
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			int templateid = 0;
			String campaignName = null;
			/** Responsys folder name change done as part of v6 migration, change done on 06/08/2016 **/
			String responsysFolderName = null;
			List<Long> updateAlertTaskIds = new ArrayList<Long>();
						
			for (int i = 0; i < alertTaskList.size(); i++) {
				AlertTask alertTask = (AlertTask) alertTaskList.get(i);
				
				/** Responsys folder name change done as part of v6 migration, change done on 06/08/2016 **/
				responsysFolderName = alertTask.getResponsysFolderName();
				//TODO loggers to be removed
				logger.info("printing alertTask details");
				logger.info("alertTaskId:"+alertTask.getAlertTaskId());
				logger.info("templateId:"+alertTask.getTemplateId());
				logger.info("alertTo:"+alertTask.getAlertTo());
				logger.info("responsysFolder:"+responsysFolderName);
         	   //TODO loggers to be removed
				try {
					// Gets the templateInputValue in Map<String, String>
					// basePMap
					List<String> emails = new ArrayList<String>();
					String listOfEmails = null;
					if (null != alertTask.getAlertTo()) {
						listOfEmails = alertTask.getAlertTo();
						emailAlertUtility.addemailsToList(emails, listOfEmails);
					}
					if (null != alertTask.getAlertCc()) {
						listOfEmails = alertTask.getAlertCc();
						emailAlertUtility.addemailsToList(emails, listOfEmails);
					}
					if (null != alertTask.getAlertBcc()) {
						listOfEmails = alertTask.getAlertBcc();
						emailAlertUtility.addemailsToList(emails, listOfEmails);
					}
                   if(null!=emails&&emails.size()!=0)
                   {
					// Remove the duplicate entries from the email list.
					Collection<String> uniqueEmails = emailAlertUtility
							.removeDuplicateEntries(emails);
					
					// Checking if number of emails exceeded
					// RESPONSYS_RTM_MAX_COUNT
					if ((uniqueEmails.size() <= EmailAlertConstants.RESPONSYS_RTM_MAX_COUNT)) {

						/*
						 * Sending mails if template id is different from the
						 * previous one or sending mails if the total number of
						 * emails exceeds the limit in case of same template id
						 */
						if ((templateid != alertTask.getTemplateId())
								|| ((templateid == alertTask.getTemplateId()) && (dataList
										.size() + uniqueEmails.size()) > EmailAlertConstants.RESPONSYS_RTM_MAX_COUNT)) {
							 logger.info("inside if condition...");
							 /** Responsys folder name change done as part of v6 migration, change done on 06/08/2016 **/
							if (!dataList.isEmpty() && providerResponsysService.sendMergeMail(dataList, campaignName,responsysFolderName)) {							
								try {
									emailAlertBO.updateAlertStatus(updateAlertTaskIds);
									updateAlertTaskIds.clear();
									dataList.clear();
								} catch (Exception e) {
									logger.error("ResponsysServiceImpl--> error occurred while update alert status: ", e);					
								}								
							}
						}
						for (String destinationEmail : uniqueEmails) {
							Map<String, String> basePMap = emailAlertUtility
									.toParameterMap(alertTask
											.getTemplateInputValue());
							basePMap.put(EmailAlertConstants.EMAIL,
									destinationEmail);
							dataList.add(basePMap);
						}
						updateAlertTaskIds.add(alertTask.getAlertTaskId());
						templateid = alertTask.getTemplateId();
						campaignName = alertTask.getEid();
					}
				}

				} catch (MailSendException msEx) {

					logger
							.error("MAIL_SEND_EXCEPTION in ResponsysServiceImpl-->processAlert()");
				} catch (Exception e) {
					logger
							.error(
									"ResponsysServiceImpl-->processAlert-->EXCEPTION-->",
									e);
				}
			}
			if (!dataList.isEmpty()) {
				/** Responsys folder name change done as part of v6 migration, change done on 06/08/2016 **/
				if(providerResponsysService.sendMergeMail(dataList, campaignName,responsysFolderName)){
					try {
						emailAlertBO.updateAlertStatus(updateAlertTaskIds);
						updateAlertTaskIds.clear();
						dataList.clear();
					} catch (Exception e) {
						logger.error("ResponsysServiceImpl--> error occurred while update alert status: ", e);					
					}
				}
				
			}
		} 
	}

	public void setEmailAlertBO(EmailAlertBO emailAlertBO) {
		this.emailAlertBO = emailAlertBO;
	}

	public EmailAlertUtility getEmailAlertUtility() {
		return emailAlertUtility;
	}

	public void setEmailAlertUtility(EmailAlertUtility emailAlertUtility) {
		this.emailAlertUtility = emailAlertUtility;
	}

	public ResponsysInteractServiceImpl getProviderResponsysService() {
		return providerResponsysService;
	}

	public void setProviderResponsysService(
			ResponsysInteractServiceImpl providerResponsysService) {
		this.providerResponsysService = providerResponsysService;
	}

	public boolean isResponsysInteractEnableMode() {
		return responsysInteractEnableMode;
	}

	public void setResponsysInteractEnableMode(boolean responsysInteractEnableMode) {
		this.responsysInteractEnableMode = responsysInteractEnableMode;
	}

}
