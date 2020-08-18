package com.newco.batch.leadOutBoundNotification;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.batch.leadOutBoundNotification.LeadOutBoundNotificationProcess;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.leadoutboundnotification.constatns.LeadOutBoundConstants;
import com.newco.marketplace.leadoutboundnotification.service.ILeadOutBoundAPIService;
import com.newco.marketplace.leadoutboundnotification.service.ILeadOutBoundNotificationService;
import com.newco.marketplace.leadoutboundnotification.vo.LeadOutBoundNotificationVO;
/**
 * Batch for sending lead details to OBU
 * @author Infosys
 */ 
public class LeadOutBoundNotificationProcess {
	
	private ILeadOutBoundNotificationService leadOutBoundNotificationService;
	private static final Logger logger = Logger.getLogger(LeadOutBoundNotificationProcess.class);
	private ILeadOutBoundAPIService leadOutBoundAPIService;
	
	/**
	 * Process the record picked by the batch
	 */
	public void process() throws BusinessServiceException {
		try {
			List<LeadOutBoundNotificationVO> failureList = null;
			//Fetching configurable property 'no of retries'.
			Integer noOfRetries = Integer.parseInt(leadOutBoundNotificationService.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_LEADS_NO_OF_RETRIES));
			Integer recordsProcessingLimit = Integer.parseInt(leadOutBoundNotificationService.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_LEADS_RECORDS_PROCESSING_LIMIT));
			Integer recordsCount = leadOutBoundNotificationService.fetchRecordsCount(noOfRetries);
			
			while(recordsCount > 0){
				List<LeadOutBoundNotificationVO> notificationList=null;
			
				//Fetching the records from lead_outbound_notification table which is in WAITING or RETRY status and
				//retry_count < noOfRetries.
				notificationList = leadOutBoundNotificationService.fetchRecords(noOfRetries, recordsProcessingLimit);
			
				if(null != notificationList){
					for (LeadOutBoundNotificationVO vo : notificationList) {
						if(null != vo && null != vo.getXml() && null != vo.getLeadId() && null != noOfRetries){
							//Processing each record by calling the web service.
							leadOutBoundAPIService.callAPIService(vo.getXml(), noOfRetries, vo.getLeadId(), vo);
						}
					}
				}
				recordsCount = recordsCount - recordsProcessingLimit;
			}
			//Fetching the records from lead_outbound_notification table which is in ERROR or FAILURE status and
			//email_ind is 0.
			failureList = leadOutBoundNotificationService.fetchFailedRecords();
			
			//Fetching configurable property 'leads email' which acts as a switch for failure email.
			String emailSwitch = leadOutBoundNotificationService.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_LEADS_EMAIL);
			if(null != failureList && !failureList.isEmpty() && StringUtils.equalsIgnoreCase(BuyerOutBoundConstants.TRUE, emailSwitch)){
				sendFailureEmails(failureList);
			}
		}
		catch (BusinessServiceException bse) {
			logger.error("Caught Exception", bse);
		}
	}
	
	//Method to send failure emails.
	private void sendFailureEmails(List<LeadOutBoundNotificationVO> failureList) {
		logger.info("inJavaMail");
		String toAddresses="";
		List<String> failedLeads = new ArrayList<String>();
		try {
			toAddresses = leadOutBoundNotificationService.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_LEADS_SL_ADDRESS);
		} catch (BusinessServiceException e) {
			logger.error("error in fetching nps notification address" + e);
		}
		if(null != toAddresses && StringUtils.isNotBlank(toAddresses)){
			String toRecipients[] = toAddresses.split(";");
			StringBuilder content = new StringBuilder(LeadOutBoundConstants.EMAIL_CONTENT+"\n");
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(MPConstants.MAIL_HOST); 
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeHelper = null;
			try {
				mimeHelper = new MimeMessageHelper(mimeMessage, true);
				mimeHelper.setSubject(MPConstants.NPS_NOTIFICATION_SUBJECT_LEADS); 
				mimeHelper.setFrom(MPConstants.NPS_EMAIL_FROM); 
				mimeHelper.setTo(toRecipients);
				if(null!=failureList){
					int count = 1;
					for (LeadOutBoundNotificationVO vo : failureList){
						if(null != vo.getLeadId()){
							failedLeads.add(vo.getLeadId());
							content.append(count + ". "+ vo.getLeadId()+"\n");
							count++;
						}
					}
				}
			} 
			catch (Exception e) {
				logger.error("Caught Exception", e);
			}
			
			try {
				mimeHelper.setText(content.toString());
				mailSender.send(mimeMessage);
				logger.info(MPConstants.NPS_NOTIFICATION_SUBJECT_LEADS
							+ " email sent to " + toAddresses);
				if(null != failedLeads && !failedLeads.isEmpty()){
					leadOutBoundNotificationService.setEmailIndicator(failedLeads);
				}
			}
			catch (MessagingException e) {
				logger.error("Caught Exception", e);
			}
			catch (BusinessServiceException bse) {
				logger.error("Caught Exception", bse);
			}
		}
	}

	public ILeadOutBoundNotificationService getLeadOutBoundNotificationService() {
		return leadOutBoundNotificationService;
	}

	public void setLeadOutBoundNotificationService(
			ILeadOutBoundNotificationService leadOutBoundNotificationService) {
		this.leadOutBoundNotificationService = leadOutBoundNotificationService;
	}

	public ILeadOutBoundAPIService getLeadOutBoundAPIService() {
		return leadOutBoundAPIService;
	}

	public void setLeadOutBoundAPIService(
			ILeadOutBoundAPIService leadOutBoundAPIService) {
		this.leadOutBoundAPIService = leadOutBoundAPIService;
	}
	
}
