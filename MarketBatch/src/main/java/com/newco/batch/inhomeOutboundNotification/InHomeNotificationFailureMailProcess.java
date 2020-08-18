package com.newco.batch.inhomeOutboundNotification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.beans.OrderUpdateResponse;
import com.newco.marketplace.inhomeoutboundnotification.beans.SendMessageResponse;
import com.newco.marketplace.inhomeoutboundnotification.service.InHomeNotificationFailureService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Batch for sending email for failed records
 * @author Infosys
 */
public class InHomeNotificationFailureMailProcess {

	private static final Logger logger = Logger.getLogger(InHomeNotificationFailureMailProcess.class);

	private InHomeNotificationFailureService inHomeNotificationFailureService;

	/**
	 * Process the record picked by the batch
	 */
	public void process() throws BusinessServiceException {
		try {
			logger.info("Entering InHomeNofiticationFailureMail Process");
			// Fetching the no. of records to be processed in one go.
			Integer recordsProcessingLimit = Integer.parseInt(inHomeNotificationFailureService.getPropertyFromDB(InHomeNPSConstants.NPS_NOTIFICATION_INHOME_RECORDS_EMAIL_PROCESSING_LIMIT));
			// Method to fetch all the data from lu_notification_oners.
			List<NotificationOwnerDetails> notificationOwnerDetails = inHomeNotificationFailureService.getNotificationOwnerDetails();
			Map <String, NotificationOwnerDetails> notificationOwnerMap = new HashMap<String, NotificationOwnerDetails>();
			// Moving notificationOwnerDetails data to notificationOwnerMap for better accessing of data.
			for(NotificationOwnerDetails ownerDetails : notificationOwnerDetails){
				if(null != ownerDetails.getErrorCode()){
					notificationOwnerMap.put(ownerDetails.getErrorCode(), ownerDetails);
				}
				// For Waiting error code is null.
				else if((InHomeNPSConstants.STATUS_WAITING).equals(ownerDetails.getOwnerName())){
					notificationOwnerMap.put(ownerDetails.getOwnerName(), ownerDetails);
				}
			}
			// processing failed records of buyer_outbound_notification table for email.
			processRecordsForInHomeFailureEmails(notificationOwnerMap, recordsProcessingLimit);
			// processing waiting records of buyer_outbound_notification table for email.
			processWaitingRecordsForInhome(notificationOwnerMap, recordsProcessingLimit);
		}
		catch (BusinessServiceException bse) {
			logger.error("Caught Exception", bse);
		}
	}

	// Method to process failed records of buyer_outbound_notification table for email.
	private void processRecordsForInHomeFailureEmails(Map <String, NotificationOwnerDetails> notificationOwnerMap, Integer recordsProcessingLimit)
	throws BusinessServiceException {
		List<InHomeOutBoundNotificationVO> failureList;
		List<InHomeOutBoundNotificationVO> validationErrorFailureList = new ArrayList<InHomeOutBoundNotificationVO>();
		List<InHomeOutBoundNotificationVO> npsErrorFailureList = new ArrayList<InHomeOutBoundNotificationVO>();
		List<InHomeOutBoundNotificationVO> npjErrorFailureList = new ArrayList<InHomeOutBoundNotificationVO>();
		OrderUpdateResponse response = new OrderUpdateResponse();
		//Fetching the total no. of records that need to be processed.
		Integer recordsCount = inHomeNotificationFailureService.fetchRecordsCount();

		while(recordsCount > 0){
			validationErrorFailureList.clear();
			npsErrorFailureList.clear();
			npjErrorFailureList.clear();
			//Fetching the FAILURE/ERROR records from buyer_outbound_notification table 
			failureList = inHomeNotificationFailureService.fetchFailedRecords(recordsProcessingLimit);
			if(null != failureList && !failureList.isEmpty()){
				for(InHomeOutBoundNotificationVO failureRecord : failureList){
					if(StringUtils.isNotBlank(failureRecord.getResponse())){
						try{
							logger.info("response is not blank for processRecordsForInHomeFailureEmails");
							if((InHomeNPSConstants.CALL_CLOSE_SERVICE_ID_INT).equals(failureRecord.getServiceId())){
								logger.info("inside CLOSE_SERVICE_ID");
								response = (OrderUpdateResponse) deserializeOrderUpdateResponse(failureRecord.getResponse(), OrderUpdateResponse.class);
							}
							else if((InHomeNPSConstants.SUBSTATUS_SERVICE_ID_INT).equals(failureRecord.getServiceId())){
								logger.info("inside SUBSTATUS_SERVICE_ID");
								SendMessageResponse sendMessageResponse = new SendMessageResponse(); 
								sendMessageResponse = (SendMessageResponse) deserializeSendMessageResponse(failureRecord.getResponse(), SendMessageResponse.class);
								logger.info("inside SUBSTATUS_SERVICE_ID:"+sendMessageResponse.getCorrelationId());
								response.setCorrelationId(sendMessageResponse.getCorrelationId());
								response.setResponseCode(sendMessageResponse.getResponseCode());
								response.setResponseMessage(sendMessageResponse.getResponseMessage());
								response.setMessages(sendMessageResponse.getMessages());
							}
						}
						catch(Exception e){
							logger.error("Caught Exception in processRecordsForInHomeFailureEmails", e);
							validationErrorFailureList.add(failureRecord);
						}
						// Error in missing required fields or validation check from web service. 
						if(InHomeNPSConstants.VALIDATION_ERROR_CODE.equalsIgnoreCase(response.getResponseCode())){
							logger.info("VALIDATION_ERROR_CODE");
							validationErrorFailureList.add(failureRecord);
						}
						// Error Code and message returned from NPS resulting in a failed NPS update
						else if(InHomeNPSConstants.NPS_ERROR_CODE.equalsIgnoreCase(response.getResponseCode())){
							logger.info("NPS_ERROR_CODE");
							npsErrorFailureList.add(failureRecord);
						}
						// Error Code and message returned from NPJ resulting in a failed NPJ update
						else if(InHomeNPSConstants.NPJ_ERROR_CODE.equalsIgnoreCase(response.getResponseCode())){
							logger.info("NPJ_ERROR_CODE");
							npjErrorFailureList.add(failureRecord);
						}
					}
					// Including our own validation errors in validationErrorFailureList.
					// Validation error done by our code will not have a response xml. So it will be null/blank.
					else{
						validationErrorFailureList.add(failureRecord);
					}
				}
				List<String> failedNotificationIds = new ArrayList<String>();
				// Sending mail for validation errors.
				if(null != validationErrorFailureList && !validationErrorFailureList.isEmpty()){
					if((InHomeNPSConstants.ON_VALUE).equals(notificationOwnerMap.get(InHomeNPSConstants.VALIDATION_ERROR_CODE).getActiveInd())){
						sendFailureEmails(validationErrorFailureList, notificationOwnerMap.get(InHomeNPSConstants.VALIDATION_ERROR_CODE).getEmailIds(), 
								notificationOwnerMap.get(InHomeNPSConstants.VALIDATION_ERROR_CODE).getOwnerName());
					}
					// Setting email indicator in buyer_outbound_notification to 1 for all failed records when activeInd 
					// for Web Service Validation error in lu_notification_owners is 0
					else{
						for(InHomeOutBoundNotificationVO failureRecord : validationErrorFailureList){
							failedNotificationIds.add(failureRecord.getNotificationId());
						}
						inHomeNotificationFailureService.setEmailIndicator(failedNotificationIds);
						failedNotificationIds.clear();
					}
				}
				// Sending mail for NPS errors.
				if(null != npsErrorFailureList && !npsErrorFailureList.isEmpty()){
					if((InHomeNPSConstants.ON_VALUE).equals(notificationOwnerMap.get(InHomeNPSConstants.NPS_ERROR_CODE).getActiveInd())){
						sendFailureEmails(npsErrorFailureList, notificationOwnerMap.get(InHomeNPSConstants.NPS_ERROR_CODE).getEmailIds(), 
								notificationOwnerMap.get(InHomeNPSConstants.NPS_ERROR_CODE).getOwnerName());
					}
					// Setting email indicator in buyer_outbound_notification to 1 for all failed records when activeInd 
					// for NPS Error in lu_notification_owners is 0
					else{
						for(InHomeOutBoundNotificationVO failureRecord : npsErrorFailureList){
							failedNotificationIds.add(failureRecord.getNotificationId());
						}
						inHomeNotificationFailureService.setEmailIndicator(failedNotificationIds);
						failedNotificationIds.clear();
					}
				}
				// Sending mail for NPJ errors.
				if(null != npjErrorFailureList && !npjErrorFailureList.isEmpty()){
					if((InHomeNPSConstants.ON_VALUE).equals(notificationOwnerMap.get(InHomeNPSConstants.NPJ_ERROR_CODE).getActiveInd())){
						sendFailureEmails(npjErrorFailureList, notificationOwnerMap.get(InHomeNPSConstants.NPJ_ERROR_CODE).getEmailIds(), 
								notificationOwnerMap.get(InHomeNPSConstants.NPJ_ERROR_CODE).getOwnerName());
					}
					// Setting email indicator in buyer_outbound_notification to 1 for all failed records when activeInd 
					// for NPJ Error in lu_notification_owners is 0
					else{
						for(InHomeOutBoundNotificationVO failureRecord : npjErrorFailureList){
							failedNotificationIds.add(failureRecord.getNotificationId());
						}
						inHomeNotificationFailureService.setEmailIndicator(failedNotificationIds);
						failedNotificationIds.clear();
					}
				}
			}
			recordsCount = recordsCount - recordsProcessingLimit;
		}

	}
	
	// Method to process waiting records of buyer_outbound_notification table for email.
	private void processWaitingRecordsForInhome(Map <String, NotificationOwnerDetails> notificationOwnerMap, Integer recordsProcessingLimit)	
			throws BusinessServiceException {
		List<InHomeOutBoundNotificationVO> waitingList;
		//Fetching the total no. of records that need to be processed.
		Integer recordsCountForWaiting = inHomeNotificationFailureService.fetchRecordsCountForWaiting();
		//Check the status of inhome_outbound_email flag to decide whether email has to be send
		Integer emailSwitch = notificationOwnerMap.get(InHomeNPSConstants.STATUS_WAITING).getActiveInd();

		while(recordsCountForWaiting > 0){
			//Fetching the records from buyer_outbound_notification table which is in WAITING status
			waitingList = inHomeNotificationFailureService.fetchWaitingRecords(recordsProcessingLimit);
			if(null != waitingList && !waitingList.isEmpty()){
				if((InHomeNPSConstants.ON_VALUE).equals(emailSwitch)){
					sendWaitingEmails(waitingList, notificationOwnerMap.get(InHomeNPSConstants.STATUS_WAITING).getEmailIds());
				}
			}
			recordsCountForWaiting = recordsCountForWaiting - recordsProcessingLimit;
		}
	}

	//Method to send failure emails.
	private void sendFailureEmails(List<InHomeOutBoundNotificationVO> failureList, String emailIds, String subject) {
		List<String> failedNotificationIds = new ArrayList<String>();
		logger.info("inJavaMail");
		if(null != emailIds){
			String toRecipients[] = emailIds.split(";");
			StringBuilder content = new StringBuilder(InHomeNPSConstants.EMAIL_CONTENT+"\n\n");
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(InHomeNPSConstants.MAIL_HOST);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeHelper = null;
			try {
				mimeHelper = new MimeMessageHelper(mimeMessage, true);
				mimeHelper.setSubject(InHomeNPSConstants.NPS_NOTIFICATION_SUBJECT_INHOME1+subject+InHomeNPSConstants.NPS_NOTIFICATION_SUBJECT_INHOME2);
				mimeHelper.setFrom(InHomeNPSConstants.NPS_EMAIL_FROM);
				mimeHelper.setTo(toRecipients);
				if(null != failureList){
					content.append("<br></br>");
					content.append("<html><table border='1px solid black' cellspacing='0' cellpadding='0' layout='fixed'>");
					content.append("<tr><th bgcolor="+"#C4C4C4"+"> Sl No.</th><th bgcolor="+"#C4C4C4"+"> SO ID </th><th bgcolor="+"#C4C4C4"+">Notification Id</th><th bgcolor="+"#C4C4C4"+">Exception</th></tr>");
					int count = 1;
					for (InHomeOutBoundNotificationVO  inHomeVO : failureList){
						if(null != inHomeVO){
							failedNotificationIds.add(inHomeVO.getNotificationId());
							content.append("<tr><td>");
							content.append(count + "</td><td>"+ inHomeVO.getSoId()+"</td><td>"+ inHomeVO.getNotificationId()+"</td>"+"<td style='word-wrap:break-word;'>"+inHomeVO.getException()+"</td></tr>");
							count++;
						}
					}
					content.append("</table></html>");
				}
			}
			catch (Exception e) {
				logger.error("Caught Exception"+e.getMessage());
			}
			try {
				mimeHelper.setText(content.toString());
				mimeMessage.setContent(content.toString(),"text/html");
				mailSender.send(mimeMessage);
				logger.info(InHomeNPSConstants.NPS_NOTIFICATION_SUBJECT_INHOME1+subject+InHomeNPSConstants.NPS_NOTIFICATION_SUBJECT_INHOME2
						+ " email sent to " + emailIds);
				if(null != failedNotificationIds && !failedNotificationIds.isEmpty()){
					inHomeNotificationFailureService.setEmailIndicator(failedNotificationIds);
				}
			}
			catch (MessagingException e) {
				logger.error("Caught Exception", e);
			}
			catch (Exception bse) {
				logger.error("Caught Exception", bse);
			}
		}
	}
	//Method to send Waiting emails.
	private void sendWaitingEmails(List<InHomeOutBoundNotificationVO> waitingList, String emailIds) {
		logger.info("inJavaMail for waiting");
		if(null != emailIds){
			String toRecipients[] = emailIds.split(";");
			StringBuilder emailContent = new StringBuilder(InHomeNPSConstants.EMAIL_CONTENT_WAITING+"\n");
			JavaMailSenderImpl mailSenderWaiting = new JavaMailSenderImpl();
			mailSenderWaiting.setHost(InHomeNPSConstants.MAIL_HOST);
			MimeMessage msg = mailSenderWaiting.createMimeMessage();
			MimeMessageHelper msgHelper = null;
			try {
				msgHelper = new MimeMessageHelper(msg, true);
				msgHelper.setSubject(InHomeNPSConstants.EMAIL_SUBJECT_WAITING);
				msgHelper.setFrom(InHomeNPSConstants.NPS_EMAIL_FROM);
				msgHelper.setTo(toRecipients);
				if(null!=waitingList){
					int count = 1;
					for (InHomeOutBoundNotificationVO  inHomeVO : waitingList){
						if(null != inHomeVO){
							emailContent.append(count + ". "+ inHomeVO.getSoId()+"("+ inHomeVO.getNotificationId()+")"+"\n");
							count++;
						}
					}
				}
				msgHelper.setText(emailContent.toString());
				mailSenderWaiting.send(msg);
				logger.info(InHomeNPSConstants.EMAIL_SUBJECT_WAITING+ " email sent to " + emailIds);
			}
			catch (Exception e) {
				logger.info("Caught Exception"+e.getMessage());
			}


		}
	}
	
	// Mapping the response xml to SendMessageResponse class.
	public Object deserializeSendMessageResponse(String responseXml, Class<?> classz) throws Exception{
        XStream xstreamResponse = new XStream(new DomDriver());
        xstreamResponse.processAnnotations(classz);
        SendMessageResponse response = (SendMessageResponse) xstreamResponse.fromXML(responseXml);
        return response;
    } 
	
	// Mapping the response xml to OrderUpdateResponse class.
	public Object deserializeOrderUpdateResponse(String responseXml, Class<?> classz) throws Exception{
        XStream xstreamResponse = new XStream(new DomDriver());
        xstreamResponse.processAnnotations(classz);
        OrderUpdateResponse response = (OrderUpdateResponse) xstreamResponse.fromXML(responseXml);
        return response;
    } 
	
	public InHomeNotificationFailureService getInHomeNotificationFailureService() {
		return inHomeNotificationFailureService;
	}

	public void setInHomeNotificationFailureService(
			InHomeNotificationFailureService inHomeNotificationFailureService) {
		this.inHomeNotificationFailureService = inHomeNotificationFailureService;
	}
}
