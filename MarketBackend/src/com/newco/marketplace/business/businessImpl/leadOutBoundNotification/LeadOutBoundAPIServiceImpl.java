package com.newco.marketplace.business.businessImpl.leadOutBoundNotification;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.leadoutboundnotification.beans.LeadsError;
import com.newco.marketplace.leadoutboundnotification.beans.ResponseLeadDetails;
import com.newco.marketplace.leadoutboundnotification.beans.ResponseLeadDetailsError;
import com.newco.marketplace.leadoutboundnotification.beans.ResponseLeadDetailsVerazip;
import com.newco.marketplace.leadoutboundnotification.constatns.LeadOutBoundConstants;
import com.newco.marketplace.leadoutboundnotification.service.LeadOutBoundNotificationClient;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.leadoutboundnotification.service.ILeadOutBoundAPIService;
import com.newco.marketplace.leadoutboundnotification.service.ILeadOutBoundNotificationService;
import com.newco.marketplace.leadoutboundnotification.vo.LeadNotificationStatusEnum;
import com.newco.marketplace.leadoutboundnotification.vo.LeadOutBoundNotificationVO;
import com.newco.marketplace.leadoutboundnotification.vo.LeadResponseVO;

public class LeadOutBoundAPIServiceImpl implements ILeadOutBoundAPIService{
	
	private static final Logger logger = Logger.getLogger(LeadOutBoundAPIServiceImpl.class);
	private ILeadOutBoundNotificationService leadOutBoundNotificationService;
	private LeadOutBoundNotificationClient leadOutboundNotificationClient;
	
	public void callAPIService(String requestXml, Integer noOfRetries, String leadId, LeadOutBoundNotificationVO notification) throws BusinessServiceException {
		
		ResponseLeadDetails successResponse = new ResponseLeadDetails(); 
		ResponseLeadDetailsError errorResponse = new ResponseLeadDetailsError();
		ResponseLeadDetailsVerazip verazipResponse = new ResponseLeadDetailsVerazip();
		LeadResponseVO leadResponseVO = null;
		LeadOutBoundNotificationVO leadOutBoundNotificationVO = new LeadOutBoundNotificationVO();
		
		String responseXml = "";
		int retryCount = -1;
		int statusCode = 0;
				
		if(null!=notification){
			retryCount = notification.getRetryCount();
		}
		
		try{
			// Call the NPS web service
			leadResponseVO = leadOutboundNotificationClient.createResponseFromNPS(requestXml);
			if(null!=leadResponseVO){
				responseXml = leadResponseVO.getResponseXml();
				statusCode = leadResponseVO.getStatusCode();
				if (statusCode == 200 && !org.apache.commons.lang.StringUtils.isBlank(responseXml)){
					if(responseXml.contains(LeadOutBoundConstants.SUCCESS_RESPONSE)){
						successResponse = (ResponseLeadDetails) leadOutboundNotificationClient.deserializeSuccessResponse(responseXml,ResponseLeadDetails.class);
						errorResponse = null;
						verazipResponse = null;
					}
					else if(responseXml.contains(LeadOutBoundConstants.ERROR_RESPONSE)){
						errorResponse = (ResponseLeadDetailsError) leadOutboundNotificationClient.deserializeErrorResponse(responseXml,ResponseLeadDetailsError.class);
						successResponse = null;
						verazipResponse = null;
					}
					else if(responseXml.contains(LeadOutBoundConstants.VERAZIP_RESPONSE)){
						verazipResponse = (ResponseLeadDetailsVerazip) leadOutboundNotificationClient.deserializeVerazipResponse(responseXml,ResponseLeadDetailsVerazip.class);
						successResponse = null;
						errorResponse = null;
					}
					else{
						successResponse = null;
						errorResponse = null;
						verazipResponse = null;
					}
				}
				else{
					successResponse = null;
					errorResponse = null;
					verazipResponse = null;
				}
			}
		}catch (Exception e){
			// This is a failure because of NPS problems or SL program error
			retryCount = retryCount + 1;
			leadOutBoundNotificationVO.setException(e.getMessage());
			logger.info("Printing stacktrace for exception.");
			e.printStackTrace();
			// Here the batch will retry -- retry count is already set in the DB during the insertion.
			if(retryCount >= noOfRetries){
				leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.FAILURE.name());
			}
			else{
				leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.RETRY.name());
			}
		}
		// There are no exceptions. However it could return an empty response or error codes
		if(leadOutBoundNotificationVO.getException() == null){
			retryCount = retryCount + 1;
			// There is no response from NPS :
			if(null == successResponse && null == errorResponse && null == verazipResponse){
				leadOutBoundNotificationVO.setException(LeadOutBoundConstants.NPS_EXCEPTION);
				leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.ERROR.name());
			}
			// Error Response From NPS
			else if(null != errorResponse){
				if(null != errorResponse.getReturnCode()){
					String errorData = "";
					if(errorResponse.getReturnCode().equalsIgnoreCase(LeadOutBoundConstants.USER_ERROR_CODE)){
						errorData = LeadOutBoundConstants.USER_ERROR + " ";
						for(LeadsError leadsError : errorResponse.getError()){
							errorData = errorData + leadsError.getErrorCode() + LeadOutBoundConstants.SEQ_HYP + leadsError.getErrorFieldName() +" "+ leadsError.getTechnicalDescription() + ", ";
						}
						errorData = errorData.substring(0, errorData.length()-2);
					}
					else if(errorResponse.getReturnCode().equalsIgnoreCase(LeadOutBoundConstants.SYSTEM_ERROR_CODE_1) || errorResponse.getReturnCode().equalsIgnoreCase(LeadOutBoundConstants.SYSTEM_ERROR_CODE_2)){
						errorData = LeadOutBoundConstants.SYSTEM_ERROR + LeadOutBoundConstants.SEQ_HYP + errorResponse.getReturnDescription();
					}
					leadOutBoundNotificationVO.setException(errorData);
					leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.ERROR.name());
				}
			}	
			// Success Response From NPS
			else if(null != successResponse){
				if(null != successResponse.getServiceOrderNum()){
					leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.SUCCESS.name());
				}
			}
			// Success Verazip Response From NPS
			else if(null != verazipResponse){
				if(null != verazipResponse.getVerazipCompletionCode()){
					leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.SUCCESS.name());
				}
			}
			// Invalid Case
			else{
				leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.ERROR.name());
			}
			//Uncomment below code if ERROR status also need retry.
			/*if(retryCount < noOfRetries && !org.apache.commons.lang.StringUtils.equalsIgnoreCase(leadOutBoundNotificationVO.getStatus(), LeadNotificationStatusEnum.SUCCESS.name())){
				logger.info("LeadOutBoundAPIServiceImpl.callAPIService.retry");
				leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.RETRY.name());
			}*/
			if(StringUtils.isNotBlank(responseXml)){
				leadOutBoundNotificationVO.setResponse(responseXml);
			}
		}
		leadOutBoundNotificationVO.setLeadId(leadId);
		leadOutBoundNotificationVO.setRetryCount(retryCount);
		leadOutBoundNotificationVO.setModifiedDate(new Date());
		
		//Updating retry_count, exception, response and modified_date in lead_outbound_notification table
		leadOutBoundNotificationService.updateNotification(leadOutBoundNotificationVO);
	}
	
	public ILeadOutBoundNotificationService getLeadOutBoundNotificationService() {
		return leadOutBoundNotificationService;
	}

	public void setLeadOutBoundNotificationService(
			ILeadOutBoundNotificationService leadOutBoundNotificationService) {
		this.leadOutBoundNotificationService = leadOutBoundNotificationService;
	}

	public LeadOutBoundNotificationClient getLeadOutboundNotificationClient() {
		return leadOutboundNotificationClient;
	}

	public void setLeadOutboundNotificationClient(
			LeadOutBoundNotificationClient leadOutboundNotificationClient) {
		this.leadOutboundNotificationClient = leadOutboundNotificationClient;
	}
}
