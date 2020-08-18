package com.newco.marketplace.business.businessImpl.inhomeOutBoundNotification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.beans.OrderUpdateResponse;
import com.newco.marketplace.inhomeoutboundnotification.beans.SendMessageResponse;
import com.newco.marketplace.inhomeoutboundnotification.service.IInhomeOutBoundAPIService;
import com.newco.marketplace.inhomeoutboundnotification.service.IInhomeOutBoundNotificationService;
import com.newco.marketplace.inhomeoutboundnotification.service.InHomeOutBoundNotificationClient;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InhomeResponseVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.NotificationOwnerDetails;
import com.newco.marketplace.leadoutboundnotification.vo.LeadNotificationStatusEnum;

public class InHomeOutBoundAPIServiceImpl implements IInhomeOutBoundAPIService{
	
	private static final Logger logger = Logger.getLogger(InHomeOutBoundAPIServiceImpl.class);
	
	//Use inhomeOutBoundNotificationService to fetch details from DB	
	private IInhomeOutBoundNotificationService inhomeOutBoundNotificationService;
	//Use inhomeOutboundNotificationClient to call the API
	private InHomeOutBoundNotificationClient inhomeOutboundNotificationClient;
	
	//changes starts for SL-20603
	private static final String PLAIN_ASCII =
		"AaEeIiOoUu"    	
	   + "AaEeIiOoUuYy"  	
	   + "AaEeIiOoUuYy"  	
	   + "AaOoNn"        	
	   + "AaEeIiOoUuYy"  	
	   + "Aa"            	
	   + "Cc"            	
	   + "OoUu"          	
	   ;

private static final String UNICODE =
		"\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
	   + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
	   + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
	   + "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1"
	   + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
	   + "\u00C5\u00E5"
	   + "\u00C7\u00E7"
	   + "\u0150\u0151\u0170\u0171"
	   ;

	//Changes ends for SL-20603

//Changes ends for SL-20858
private static final char[] hexChar = {
    '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
};
//Changes ends for SL-20858

		
	public String callAPIService(Integer noOfRetries, InHomeOutBoundNotificationVO notification, String serviceId) throws BusinessServiceException {
		
		logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:Begin");
		OrderUpdateResponse orderUpdateResponse = new OrderUpdateResponse();
		SendMessageResponse sendMessageResponse = new SendMessageResponse(); 
		InhomeResponseVO inhomeResponseVO = null;
		
		String responseXml = "";
		String requestXml = "";
		int retryCount = -1;
		int statusCode = 0;
				
		if(null != notification){
			retryCount = notification.getRetryCount();
			requestXml = notification.getXml();
			//escaping unwanted character from the request Xml Sl-20858 changes starts
			//requestXml = requestXml.replaceAll("(&#x)(..)(;)", "");
			//requestXml = StringEscapeUtils.unescapeXml(requestXml);
			logger.info("Request xml ="+requestXml);
			requestXml = convertNonAscii(requestXml);
			logger.info("Request xml after convertNonAscii function ="+requestXml);
			
			
			
			requestXml = requestXml.replaceAll("(&#x)(..)(;)", "");
			requestXml = requestXml.replaceAll("(&)(...)(;)", "");
			requestXml = requestXml.replaceAll("(#x)(..)(;)", "");
			requestXml = requestXml.replaceAll("(#x)(.)(;)", "");
			
			//requestXml = requestXml.replaceAll("&", "&amp;");
			requestXml = requestXml.replaceAll("\\?", "");
			requestXml = requestXml.replaceAll("\\;", "");
			requestXml = requestXml.replaceAll("\\|", "");
			requestXml = requestXml.replaceAll("\\{", "");
			requestXml = requestXml.replaceAll("\\}", "");
			requestXml = requestXml.replaceAll("\\[", "");
			requestXml = requestXml.replaceAll("\\]", "");
			//requestXml = requestXml.replaceAll("&", "");
		
			logger.info("Request xml after replaceAll function ="+requestXml);
			//requestXml = StringEscapeUtils.unescapeXml(requestXml);
			//logger.info("Request xml after unescape xml ="+requestXml);
			requestXml = unicodeEscape(requestXml);
			logger.info("Request xml after unicodeEscape function="+requestXml);
			requestXml =  requestXml.replaceAll("(\\\\u)(....)", "");
			logger.info("Final request xml ="+requestXml);
			//changes for SL-20858 ends
			logger.debug("InHomeOutBoundAPIServiceImpl.callAPIService:requestXml:"+requestXml);
		}
		
		try{
			//Fetch the request header and url to call the API	
			HashMap <String, String> apiDetails = inhomeOutBoundNotificationService.fetchApiDetails(serviceId);
			if(null != apiDetails){
				String url = apiDetails.get(InHomeNPSConstants.URL);
				String header = apiDetails.get(InHomeNPSConstants.HEADER);
				header = header + InHomeNPSConstants.SEQ_COM1 + InHomeNPSConstants.CURRENT_DATE_TIME + InHomeNPSConstants.SEQ_COL1 + getDateTime();
				logger.debug("InHomeOutBoundAPIServiceImpl.callAPIService:url:"+url);
				logger.debug("InHomeOutBoundAPIServiceImpl.callAPIService:header:"+header);
				
				// Call the NPS web service
				inhomeResponseVO = inhomeOutboundNotificationClient.createResponseFromNPS(requestXml, url, header);
				
				if(null != inhomeResponseVO){
					responseXml = inhomeResponseVO.getResponseXml();
					statusCode = inhomeResponseVO.getStatusCode();
					logger.debug("InHomeOutBoundAPIServiceImpl.callAPIService:responseXml:"+responseXml);
					logger.debug("InHomeOutBoundAPIServiceImpl.callAPIService:statusCode:"+statusCode);
					//If Status is Ok, deserialize the response xml
					if (200 == statusCode && StringUtils.isNotBlank(responseXml)){
						//For Call Close batch.
						if((InHomeNPSConstants.CLOSE_SERVICE_ID).equalsIgnoreCase(serviceId)){
							orderUpdateResponse = (OrderUpdateResponse)inhomeOutboundNotificationClient.deserializeOrderUpdateResponse(responseXml, OrderUpdateResponse.class);
						}
						//For message batch.
						else{
							sendMessageResponse = (SendMessageResponse)inhomeOutboundNotificationClient.deserializeSendMessageResponse(responseXml, SendMessageResponse.class);
							//Since both response are same except the main tag, orderUpdateResponse can be used to keep the deserialized data for both web services.
							orderUpdateResponse.setCorrelationId(sendMessageResponse.getCorrelationId());
							orderUpdateResponse.setResponseCode(sendMessageResponse.getResponseCode());
							orderUpdateResponse.setResponseMessage(sendMessageResponse.getResponseMessage());
							orderUpdateResponse.setMessages(sendMessageResponse.getMessages());
						}
					}
					else{
						orderUpdateResponse = null;
					}
				}
			}
		}catch (Exception e){
			logger.error("Printing exception : "+e);
			
			// If there is a failure because of NPS problems or SL program error
			// Set the exception & status
			notification.setException(e.getMessage());
			retryCount = retryCount + 1;
			if(retryCount >= noOfRetries){
				notification.setStatus(LeadNotificationStatusEnum.FAILURE.name());
			}
			else{
				notification.setStatus(LeadNotificationStatusEnum.RETRY.name());
			}
		}
		
		try{
			
			// There are no exceptions. However it could return an empty response or error codes
			if(null == notification.getException()){
				logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:null == notification.getException() is true");
				retryCount = retryCount + 1;
				// There is no response from NPS :
				if(null == orderUpdateResponse){
					logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:null == inHomeResponse is true");
					notification.setException(InHomeNPSConstants.NPS_EXCEPTION);
					notification.setStatus(LeadNotificationStatusEnum.ERROR.name());
				}
				// Error Response From NPS
				else if(null != orderUpdateResponse && null != orderUpdateResponse.getResponseCode()){
					logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:Error Response From NPS");
					String errorData = "";
					notification.setStatus(LeadNotificationStatusEnum.ERROR.name());
					// Error in missing required fields or validation check from web service. 
					if(InHomeNPSConstants.VALIDATION_ERROR_CODE.equalsIgnoreCase(orderUpdateResponse.getResponseCode())){
						errorData = InHomeNPSConstants.VALIDATION_ERROR;
						logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:VALIDATION_ERROR");
					}
					// Error Code and message returned from NPS resulting in a failed NPS update
					else if(InHomeNPSConstants.NPS_ERROR_CODE.equalsIgnoreCase(orderUpdateResponse.getResponseCode())){
						errorData = InHomeNPSConstants.NPS_ERROR;
						logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:NPS_ERROR");
					}
					// Error Code and message returned from NPJ resulting in a failed NPJ update
					else if(InHomeNPSConstants.NPJ_ERROR_CODE.equalsIgnoreCase(orderUpdateResponse.getResponseCode())){
						errorData = InHomeNPSConstants.NPJ_ERROR;
						logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:NPJ_ERROR");
					}
					// Connection to NPS/NPJ is down
					else if(InHomeNPSConstants.SYSTEM_DOWN_CODE.equalsIgnoreCase(orderUpdateResponse.getResponseCode())){
						errorData = InHomeNPSConstants.SYSTEM_DOWN;
						if(retryCount >= noOfRetries){
							notification.setStatus(LeadNotificationStatusEnum.ERROR.name());
						}
						else{
							notification.setStatus(LeadNotificationStatusEnum.RETRY.name());
						}
						logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:SYSTEM_DOWN_CODE");
					}
					//Service order doesn't exist in NPS
					else if(InHomeNPSConstants.SERVICE_ORDER_NOTFOUND_CODE.equalsIgnoreCase(orderUpdateResponse.getResponseCode())){
						errorData = InHomeNPSConstants.SERVICE_ORDER_NOTFOUND;
						notification.setStatus(LeadNotificationStatusEnum.ERROR.name());
						notification.setEmailInd(InHomeNPSConstants.EMAILIND);
						logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:SERVICE_ORDER_NOTFOUND_CODE");
						//Updating the nps_inactive_ind in so_workflow_controls
						inhomeOutBoundNotificationService.updateNPSIndicator(notification.getSoId());
					}
					//Service order canceled or closed in NPS
					else if(InHomeNPSConstants.SERVICE_ORDER_CANCELEDORCLOSED_CODE.equalsIgnoreCase(orderUpdateResponse.getResponseCode())){
						errorData = InHomeNPSConstants.SERVICE_ORDER_CANCELEDORCLOSED;
						notification.setStatus(LeadNotificationStatusEnum.ERROR.name());
						notification.setEmailInd(InHomeNPSConstants.EMAILIND);
						logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:SERVICE_ORDER_CANCELLEDORCLOSED_CODE");
						//Updating the nps_inactive_ind in so_workflow_controls
						inhomeOutBoundNotificationService.updateNPSIndicator(notification.getSoId());
					}
					// Success Response From NPS : response code 00
					else if(null != orderUpdateResponse && StringUtils.isNotBlank(orderUpdateResponse.getResponseCode()) && 
								InHomeNPSConstants.SUCCESS_CODE.equalsIgnoreCase(orderUpdateResponse.getResponseCode())){
						notification.setStatus(LeadNotificationStatusEnum.SUCCESS.name());
						logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:Success Response From NPS : response code 00");
					}
					// Saving the error messages to errorData
					if(null != orderUpdateResponse.getMessages() && null !=orderUpdateResponse.getMessages().getMessage() && !orderUpdateResponse.getMessages().getMessage().isEmpty()){
						errorData = errorData + InHomeNPSConstants.SEQ_COL;
						for(String error : orderUpdateResponse.getMessages().getMessage()){
							errorData = errorData + error + InHomeNPSConstants.SEQ_COM;
						}
						errorData = errorData.substring(0, errorData.length()-2);
						logger.debug("InHomeOutBoundAPIServiceImpl.callAPIService:errorData:"+errorData);
					}
					notification.setException(errorData);
				}	
				// Invalid Case
				else{
					notification.setStatus(LeadNotificationStatusEnum.ERROR.name());
					logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:Invalid Case");
				}
				if(StringUtils.isNotBlank(responseXml)){
					notification.setResponse(responseXml);
				}
			}
			notification.setRetryCount(retryCount);
			notification.setModifiedDate(new Date());
			
			//Updating the response in buyer_outbound_notification table
			inhomeOutBoundNotificationService.updateNotification(notification);
			if(InHomeNPSConstants.SYSTEM_DOWN_CODE.equalsIgnoreCase(orderUpdateResponse.getResponseCode()))
			{
				//Adding logic to send emails only after consuming all retries.
				if(retryCount >= noOfRetries)
				{
				sendSystemDownEmail(notification,InHomeNPSConstants.SYSTEM_DOWN_CODE);
				return "1";
				}
				
			}
			logger.info("InHomeOutBoundAPIServiceImpl.callAPIService:End");
		}
		catch(Exception e){
			logger.error("Exception in InHomeOutBoundAPIServiceImpl.callAPIService() due to "+e.getMessage());
			throw new BusinessServiceException("Exception in InHomeOutBoundAPIServiceImpl.callAPIService() due to "+ e.getMessage(), e);
		}
		return "0";
	}

	public IInhomeOutBoundNotificationService getInhomeOutBoundNotificationService() {
		return inhomeOutBoundNotificationService;
	}

	public void setInhomeOutBoundNotificationService(
			IInhomeOutBoundNotificationService inhomeOutBoundNotificationService) {
		this.inhomeOutBoundNotificationService = inhomeOutBoundNotificationService;
	}

	public InHomeOutBoundNotificationClient getInhomeOutboundNotificationClient() {
		return inhomeOutboundNotificationClient;
	}

	public void setInhomeOutboundNotificationClient(
			InHomeOutBoundNotificationClient inhomeOutboundNotificationClient) {
		this.inhomeOutboundNotificationClient = inhomeOutboundNotificationClient;
	}
	
	//SL-20603 changes starts
	// remove accented from a string and replace with ASCII equivalent
	private String convertNonAscii(String s) {
		if (s == null) return null;
		StringBuilder sb = new StringBuilder();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
		    int pos = UNICODE.indexOf(c);
		    if (pos > -1){
		    	sb.append(PLAIN_ASCII.charAt(pos));
		    }
		    else {
		        sb.append(c);
		    }
		}
		return sb.toString();
	}
	//changes for SL-20603 ends
	
	//changes for SL-20858 starts
	private static String unicodeEscape(String s) {
		  StringBuilder sb = new StringBuilder();
		  for (int i = 0; i < s.length(); i++) {
		      char c = s.charAt(i);
		      if ((c >> 7) > 0) {
		   sb.append("\\u");
		   sb.append(hexChar[(c >> 12) & 0xF]); // append the hex character for the left-most 4-bits
		   sb.append(hexChar[(c >> 8) & 0xF]);  // hex for the second group of 4-bits from the left
		   sb.append(hexChar[(c >> 4) & 0xF]);  // hex for the third group
		   sb.append(hexChar[c & 0xF]);         // hex for the last group, e.g., the right most 4-bits
		      }
		      else {
		   sb.append(c);
		      }
		  }
		  return sb.toString();
		     }
	
	//changes for SL-20858 ends
	
	// Method to find current date time in 'yyyymmdd:HHMMSS' to pass with the header of the web service.
	public String getDateTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyymmdd:HHMMSS");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	//Method to send email for System Down Error Code.
		private void sendSystemDownEmail(InHomeOutBoundNotificationVO waitingList,String errorCode) {

			logger.info("inJavaMail for waiting");
			String toAddress="";
			NotificationOwnerDetails notificationOwnerDetails = null;

			try {
				//get to address from lu_notification_owners
				notificationOwnerDetails = inhomeOutBoundNotificationService.getSystemDownEmailAddress(errorCode);
				if(null!=notificationOwnerDetails)
				{
					toAddress=notificationOwnerDetails.getEmailIds();
				}
				logger.info("Exception occurred in getAddresses: "+toAddress);

			} catch (BusinessServiceException e) {
				logger.error("error in fetching nps notification address" + e);
			}
			catch(Exception e)
			{
				logger.error("error in fetching nps notification address" + e);
			}
			if(StringUtils.isNotBlank(toAddress) && null!=notificationOwnerDetails && (InHomeNPSConstants.ON_VALUE).equals(notificationOwnerDetails.getActiveInd())){
				String toRecipients[] =toAddress.split(";");
			
				StringBuilder emailContent = new StringBuilder(InHomeNPSConstants.EMAIL_CONTENT_SYSTEM_DOWN+"\n");
				JavaMailSenderImpl mailSenderWaiting = new JavaMailSenderImpl();
				mailSenderWaiting.setHost(InHomeNPSConstants.MAIL_HOST);
				MimeMessage msg = mailSenderWaiting.createMimeMessage();
				MimeMessageHelper msgHelper = null;
				try {
					msgHelper = new MimeMessageHelper(msg, true);
					msgHelper.setSubject(InHomeNPSConstants.EMAIL_SUBJECT_SYSTEM_DOWN);
					msgHelper.setFrom(InHomeNPSConstants.NPS_EMAIL_FROM);
					msgHelper.setTo(toRecipients);
					if(null!=waitingList){
						
						
						emailContent.append(waitingList.getSoId()+"("+ waitingList.getNotificationId()+")"+"\n");
						
					}
					msgHelper.setText(emailContent.toString());
					mailSenderWaiting.send(msg);
					logger.info(InHomeNPSConstants.EMAIL_SUBJECT_SYSTEM_DOWN+ " email sent to " + toAddress);
				}
				catch (Exception e) {
					logger.info("Caught Exception"+e.getMessage());
				}


			}
		}
	
}
