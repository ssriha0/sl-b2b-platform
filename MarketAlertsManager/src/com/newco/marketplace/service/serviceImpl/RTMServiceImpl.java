/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 01-Sep-2009	KMSTRSUP   Infosys				1.0
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
import com.newco.servicelive.campaigns.service.BaseService;
import com.newco.servicelive.campaigns.service.ResponsysRTMServiceImpl;

/**
 * This class is for sending mails using Responsys Real Time Messaging service.
 * 
 * @author Infosys
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RTMServiceImpl extends BaseService implements EmailAlertService {
	private static final Logger logger = Logger.getLogger(RTMServiceImpl.class.getName());
	private EmailAlertBO emailAlertBO;	
	private EmailAlertUtility emailAlertUtility;
	private ResponsysRTMServiceImpl providerRTMService;
	private boolean responsysEnableMode;

	/**
	 * This method processes the records in the alert_task table and calls the
	 * method for sending mail through responsys
	 * 
	 * @return void
	 */
	public void processAlert() {
		logger.info("Entering ResponsysRTMServiceImpl-->processAlert()");
		ArrayList<AlertTask> alertTaskList = null;
		
		if (isResponsysEnableMode()) {			
			try {
				logger.info("Fetching AlertTask list");
				alertTaskList = (ArrayList<AlertTask>) emailAlertBO
						.retrieveAlertTasks(EmailAlertConstants.RTM_PRIORITY,
								EmailAlertConstants.PROV_RESPONSYS);

			} catch (DataServiceException dse) {
				logger
						.error(
								"ResponsysRTMServiceImpl-->processAlert-->DataServiceException-->",
								dse);
			}
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			int templateid = 0;
			String campaignName = null;
			List<Long> updateAlertTaskIds = new ArrayList<Long>();
						
			for (int i = 0; i < alertTaskList.size(); i++) {
				AlertTask alertTask = (AlertTask) alertTaskList.get(i);

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
							
							if (!dataList.isEmpty() && providerRTMService.sendMail(dataList, campaignName)) {							
								try {
									emailAlertBO.updateAlertStatus(updateAlertTaskIds);
									updateAlertTaskIds.clear();
									dataList.clear();
								} catch (Exception e) {
									logger.error("RTMServiceImpl--> error occurred while update alert status: ", e);					
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
							.error("MAIL_SEND_EXCEPTION in ResponsysRTMServiceImpl-->processAlert()");
				} catch (Exception e) {
					logger
							.error(
									"ResponsysRTMServiceImpl-->processAlert-->EXCEPTION-->",
									e);
				}
			}
			if (!dataList.isEmpty()) {

				if(providerRTMService.sendMail(dataList, campaignName)){
					try {
						emailAlertBO.updateAlertStatus(updateAlertTaskIds);
						updateAlertTaskIds.clear();
						dataList.clear();
					} catch (Exception e) {
						logger.error("RTMServiceImpl--> error occurred while update alert status: ", e);					
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

	public ResponsysRTMServiceImpl getProviderRTMService() {
		return providerRTMService;
	}

	public void setProviderRTMService(ResponsysRTMServiceImpl providerRTMService) {
		this.providerRTMService = providerRTMService;
	}

	public boolean isResponsysEnableMode() {
		return responsysEnableMode;
	}

	public void setResponsysEnableMode(boolean responsysEnableMode) {
		this.responsysEnableMode = responsysEnableMode;
	}

}
