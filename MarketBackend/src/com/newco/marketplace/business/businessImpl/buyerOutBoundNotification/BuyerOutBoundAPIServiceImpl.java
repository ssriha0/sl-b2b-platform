package com.newco.marketplace.business.businessImpl.buyerOutBoundNotification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.buyeroutboundnotification.beans.RequestHeader;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestJobcode;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrder;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrders;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestReschedInformation;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestRescheduleInfo;
import com.newco.marketplace.buyeroutboundnotification.beans.ResponseMsgBody;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.buyeroutboundnotification.service.BuyerOutboundNotificationClient;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundAPIService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerNotificationStatusEnum;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.ExceptionCodesEnum;
import com.newco.marketplace.buyeroutboundnotification.vo.ResponseVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.thoughtworks.xstream.XStream;

public class BuyerOutBoundAPIServiceImpl implements IBuyerOutBoundAPIService{
	private static final Logger logger = Logger.getLogger(BuyerOutBoundAPIServiceImpl.class);
	private IBuyerOutBoundNotificationService buyerOutBoundNotificationService;
	private BuyerOutboundNotificationClient buyerOutboundNotificationClient;
	
	public BuyerOutboundNotificationClient getBuyerOutboundNotificationClient() {
		return buyerOutboundNotificationClient;
	}
	public void setBuyerOutboundNotificationClient(
			BuyerOutboundNotificationClient buyerOutboundNotificationClient) {
		this.buyerOutboundNotificationClient = buyerOutboundNotificationClient;
	}
	public IBuyerOutBoundNotificationService getBuyerOutBoundNotificationService() {
		return buyerOutBoundNotificationService;
	}
	public void setBuyerOutBoundNotificationService(
			IBuyerOutBoundNotificationService buyerOutBoundNotificationService) {
		this.buyerOutBoundNotificationService = buyerOutBoundNotificationService;
	}
	
	/**
	 * Call API 
	 */
	public BuyerOutboundFailOverVO callAPIService(String xml, String soId) throws BusinessServiceException{
		XStream xstream = new XStream();
		Class[] classes = new Class[] { RequestMsgBody.class,
				RequestHeader.class, RequestOrders.class,
				RequestOrder.class, RequestReschedInformation.class,
				RequestJobcode.class, RequestRescheduleInfo.class };
		xstream.processAnnotations(classes);
		RequestMsgBody request = (RequestMsgBody) xstream.fromXML(xml);
		BuyerOutboundFailOverVO failOverVO = callAPIService(xml,request,soId,null);
		return failOverVO;
	}
	/**
	 *  Call API
	 */
	public BuyerOutboundFailOverVO callAPIService(String requestXml, RequestMsgBody request,String soId, 
				BuyerOutboundFailOverVO notification) throws BusinessServiceException {
		String sequenceNo = request.getHeader().getSeqNo();
		ResponseMsgBody response = new ResponseMsgBody();
		ResponseVO responseVO = new ResponseVO();
		String xml = "";
		boolean successInd = false;
		int noOfRetries = -1;
		int statusCode = 0;
		
		// If the call is from batch, that means there is a number of retry set in the DB
		if(null!=notification){
			noOfRetries = notification.getNoOfRetries();
		}
		BuyerOutboundFailOverVO failOverVO = new BuyerOutboundFailOverVO();
		failOverVO.setSeqNO(sequenceNo);
		failOverVO.setSoId(soId);
		failOverVO.setXml(requestXml);
		try{
			//Fix for SL-18770
			logger.info("SL-18770:Request XMl before unescape Characters");
			logger.info(requestXml);
			String requestXML=StringEscapeUtils.unescapeXml(requestXml);
			logger.info("SL-18870 Request XMl after unescape Characters");
			logger.info(requestXML);
			// Call the NPS web service
			responseVO = buyerOutboundNotificationClient.createResponseFromNPS(requestXml);
			
			if(null!=responseVO){
				xml = responseVO.getResponseXml();
				statusCode = responseVO.getStatusCode();
				if (statusCode == 200 && !org.apache.commons.lang.StringUtils.isBlank(xml)){
					
					// Parse the response from NPS if the web service call returns OK status 
					response = (ResponseMsgBody) buyerOutboundNotificationClient.deserializeResponse(xml,ResponseMsgBody.class);
		        }
			}
		}catch (Exception e){
			
			// This is a failure because of NPS problems or SL program error
			// TODO : Think about it. Do we need to retry this again?
			failOverVO.setException(e.getMessage());
			failOverVO.setStatus(BuyerNotificationStatusEnum.FAILURE.name());
			failOverVO.setActiveInd(true);
			logger.info("Printing stacktrace for exception.");
			e.printStackTrace();
			
			// Here the batch will retry -- retry count is already set in the DB during the insertion.
			// If this is the first call, this will change the  retry number to -2
			noOfRetries = noOfRetries -1;
		}
		// There are no exceptions. However it could return an empty response or error codes
		if(failOverVO.getException()==null){
			
			// There is no response from NPS :
			if(null==response || null==response.getHeader() || null==response.getOrders() 
					||response.getOrders().getResOrder()==null||
					response.getOrders().getResOrder().get(0)==null){
				
				// This is a local exception. Let the batch retry this again
				failOverVO.setException(BuyerOutBoundConstants.NPS_EXCEPTION);
				failOverVO.setStatus(BuyerNotificationStatusEnum.FAILURE.name());
				failOverVO.setActiveInd(true);
				
				
			}
			
			// Web service call returned error codes -
			// If the completion code is 000 - success
			// If the completion code is 008 - failure
			
			else if(StringUtils.equalsIgnoreCase(
					response.getOrders().getResOrder().get(0).getResponseCode(),BuyerOutBoundConstants.ERROR_CODE)){
				// Still checking for error to make sure that there is an error code in the list ?
				if(response.getOrders().getResOrder().get(0).getErrorCodes()!=null && 
						response.getOrders().getResOrder().get(0).getErrorCodes().getErrorCode()!=null &&
						response.getOrders().getResOrder().get(0).getErrorCodes().getErrorCode().size()>0 &&
						!org.apache.commons.lang.StringUtils.isBlank(
								response.getOrders().getResOrder().get(0).getErrorCodes().getErrorCode().get(0).getCode()) ){
					
					// This is some serious exception.This may result in error from batch as well.
					failOverVO.setStatus(BuyerNotificationStatusEnum.ERROR.name());
					failOverVO.setActiveInd(false);
					
				}
			}else if(StringUtils.equalsIgnoreCase(
					response.getOrders().getResOrder().get(0).getResponseCode(),BuyerOutBoundConstants.SUCCESS_CODE)){
				// Success - still checking to make sure that there are no values in the error code
				// Assumption - for successful API call, there will not be any error codes
				
				if(null!=response.getHeader()&& null!=response.getHeader().getSeqNo() &&
						(response.getOrders().getResOrder().get(0).getErrorCodes()==null||response.getOrders().
						  getResOrder().get(0).getErrorCodes().getErrorCode()==null||
						response.getOrders().getResOrder().get(0).getErrorCodes().getErrorCode().size()==0)){
					
					// Success
					failOverVO.setStatus(BuyerNotificationStatusEnum.SUCCESS.name());
					failOverVO.setActiveInd(false);
					
					successInd = true;
				}
				
			}else{
				failOverVO.setStatus(BuyerNotificationStatusEnum.ERROR.name());
				failOverVO.setActiveInd(false);
			}
			
			if(StringUtils.isNotBlank(xml)){
				failOverVO.setResponse(xml);
			}
			// For first time call, this value will be -ve
			noOfRetries = noOfRetries -1;
		}
		/* Error code is available - send e-mail to ServiceLive production support team.
		 * Exception available - send e-mail to NPS?
		 */
		
		String emailSwitch = buyerOutBoundNotificationService.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_EMAIL);
		String toAddresses="";
		if(StringUtils.equalsIgnoreCase(BuyerOutBoundConstants.TRUE, emailSwitch)){				
			if (!successInd && (null!=failOverVO.getStatus() && 
					failOverVO.getStatus().equalsIgnoreCase(BuyerNotificationStatusEnum.ERROR.name()))){
				try {
					toAddresses = buyerOutBoundNotificationService
							.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_SL_ADDRESS);
				} catch (BusinessServiceException e) {
					logger.error("error in fetching nps notification address" + e);
				}
				// Send the e-mail to ServiceLive team since the error is on SL side
				//sendFailureEmail(failOverVO, toAddresses); //Commented as per jira#SL-19879
			}
			
			// TODO Is this needed? or only on specific exceptions like 401 403 405 407 408 500 502 503 504 ?
			else if (!successInd && (null!=failOverVO.getStatus() && 
					failOverVO.getStatus().equalsIgnoreCase(BuyerNotificationStatusEnum.FAILURE.name()))
					&& StringUtils.isNotEmpty(failOverVO.getException()) 
					&& ExceptionCodesEnum.containsValue(statusCode)){
				
				try {
					toAddresses = buyerOutBoundNotificationService
							.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_ADDRESS);
				} catch (BusinessServiceException e) {
				logger.error("error in fetching nps notification address"+e);
				}
				// TODO do we need to send e-mails for 403 Forbidden,405 Method Not Allowed,406
				// TODO is this really needed here ? Or send after 4 retires by the batch?
				sendFailureEmail(failOverVO, toAddresses);
				
			}else if (!successInd && (null!=failOverVO.getStatus() && 
					failOverVO.getStatus().equalsIgnoreCase(BuyerNotificationStatusEnum.FAILURE.name()))){
				logger.info("inJavaMail");
				try {
					toAddresses = buyerOutBoundNotificationService
							.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_SL_ADDRESS);
				} catch (BusinessServiceException e) {
					logger.error("error in fetching nps notification address" + e);
				}
				// Any other exception, send e-mail to SL PROD support
				sendFailureEmail(failOverVO, toAddresses);
			}else if(noOfRetries == 0 && !successInd){
				try {
					toAddresses = buyerOutBoundNotificationService
							.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_ADDRESS);
				} catch (BusinessServiceException e) {
					logger.error("error in fetching nps notification address" + e);
				}
				// Exhausted the retry count -- send this to PROD support/NPS?
				sendFailureEmail(failOverVO, toAddresses);
			}
		}
		if(noOfRetries == 0){
			failOverVO.setActiveInd(false);
		}
		
		buyerOutBoundNotificationService.updateNotification(failOverVO);
		return failOverVO;
	}
	
	

	private void sendFailureEmail(BuyerOutboundFailOverVO vo, String toAddresses) {
		logger.info("inJavaMail");
		try {
			toAddresses = buyerOutBoundNotificationService
					.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_SL_ADDRESS);
		} catch (BusinessServiceException e) {
			logger.error("error in fetching nps notification address" + e);
		}
		String toRecipients[] = toAddresses.split(";");
		String content = BuyerOutBoundConstants.EMAIL_CONTENT;
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(MPConstants.MAIL_HOST);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper = null;
		try {
			mimeHelper = new MimeMessageHelper(mimeMessage, true);

			mimeHelper.setSubject(MPConstants.NPS_NOTIFICATION_SUBJECT);
			mimeHelper.setFrom(MPConstants.NPS_EMAIL_FROM);
			mimeHelper.setTo(toRecipients);
			if(null!=vo && null!= vo.getSoId()){
				content = content + BuyerOutBoundConstants.EMAIL_CONTENT_1 + vo.getSoId()+BuyerOutBoundConstants.EMAIL_SPACE;
			}
			if(null!=vo && null!= vo.getSeqNO()){
				content = content + BuyerOutBoundConstants.EMAIL_CONTENT_2 + vo.getSeqNO()+BuyerOutBoundConstants.EMAIL_SPACE;
			}
		} catch (Exception e) {
			logger.error("Caught Exception", e);
		}

		String requestfileName = MPConstants.NPS_NOTIFICATION_REQUEST
				+ vo.getSeqNO() + ".txt";
		String fileDir = "";
		try {
			fileDir = buyerOutBoundNotificationService
					.getPropertyFromDB(MPConstants.NPS_NOTIFICATION_DIRECTORY);
		} catch (BusinessServiceException e) {
			logger.error("error in fetching nps notification directory" + e);
		}
		File testFile = createFile(requestfileName, fileDir);
		FileWriter writer = null;
		PrintWriter pWriter = null;
		try {
			writer = new FileWriter(testFile);
			pWriter = new PrintWriter(new BufferedWriter(writer, 8192));
			if (null == testFile) {
				return;
			}
			if(null!=vo.getXml()){
				pWriter.write(vo.getXml());
				pWriter.flush();
			}
			
		} catch (Exception e) {
			logger.error("Caught Exception", e);
		} finally {
			if (null != pWriter) {
				pWriter.flush();
				pWriter.close();
			}
			if (null != writer) {
				try {
					writer.close();
				} catch (Exception e) {
					logger.error("Caught Exception", e);
				}
			}
		}
		File responsefile = null;
		String responsefileName = "";
		if(!StringUtils.isBlank(vo.getResponse())){
			
			responsefileName = MPConstants.NPS_NOTIFICATION_RESPONSE
					+ vo.getSeqNO() + ".txt";
			File responseFile = createFile(responsefileName, fileDir);
	
			try {
				writer = new FileWriter(responseFile);
				pWriter = new PrintWriter(new BufferedWriter(writer, 8192));
				if (null == testFile) {
					return;
				}
				if(null!=vo.getResponse()){
					pWriter.write(vo.getResponse());
					pWriter.flush();
				}
			} catch (Exception e) {
				logger.error("Caught Exception", e);
			} finally {
				if (null != pWriter) {
					pWriter.flush();
					pWriter.close();
				}
				if (null != writer) {
					try {
						writer.close();
					} catch (Exception e) {
						logger.error("Caught Exception", e);
					}
				}
			}
			responsefile = new File(fileDir + responsefileName);
		}
		logger.info("beforeattaching");
		File requestfile = new File(fileDir + requestfileName);
	
		
		// adding excel to the mail.
			try {
				if(!StringUtils.isBlank(vo.getXml()) && requestfile.exists()){
					mimeHelper.addAttachment(requestfileName, requestfile);
				}
				if(!StringUtils.isBlank(vo.getResponse())&& responsefile.exists()){
					mimeHelper.addAttachment(responsefileName, responsefile);
				}
				if(!StringUtils.isBlank(vo.getXml())||!StringUtils.isBlank(vo.getResponse())){
					content = content + BuyerOutBoundConstants.EMAIL_CONTENT_3;
				}
				mimeHelper.setText(content);
				logger.info("afterattaching");
				mailSender.send(mimeMessage);
				logger.info(MPConstants.NPS_NOTIFICATION_SUBJECT
						+ " email sent to " + toAddresses);

			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				logger.error("Caught Exception", e);

			}
	}
	
	
	
	public static File createFile(String fileName,String fileDir){
		File outputFile = null;
		try{			
			boolean success = new File(fileDir).mkdir();			
			outputFile = new File(fileDir + fileName);						
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	
		return outputFile;
	}
	}

