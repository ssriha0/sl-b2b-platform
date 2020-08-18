/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-May-2014	KMSTRSUP   Infosys				2.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import org.apache.log4j.Logger;

import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.update.SOUpdateEstimateRequest;
import com.newco.marketplace.api.beans.so.update.SOUpdateEstimateResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;

/**
 * This class would act as a Servicer class for Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOUpdateEstimateStatusService extends BaseService {

	private XStreamUtility conversionUtility;
	private Logger logger = Logger.getLogger(SOUpdateEstimateStatusService.class);
	private IServiceOrderBO serviceOrderBO;
	private SecurityProcess securityProcess;
	private IRelayServiceNotification relayNotificationService;
	
	public SOUpdateEstimateStatusService() {
		super(PublicAPIConstant.SOUPDATESTIMATESTATUS_XSD,
				PublicAPIConstant.SOUPDATESTIMATESTATUS_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				SOUpdateEstimateRequest.class, SOUpdateEstimateResponse.class);
	}

	/**
	 * Implement the  logic .
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		
		logger.info("Entering execute method SOUpdateEstimateStatusService::");
		String soId = apiVO.getSOId();
		int buyerId = apiVO.getBuyerIdInteger();
		SOUpdateEstimateRequest soUpdateRequest = (SOUpdateEstimateRequest) apiVO.getRequestFromPostPut();
		SOUpdateEstimateResponse soUpdateResponse = new SOUpdateEstimateResponse();
		int buyerResourceId = soUpdateRequest.getIdentification().getId();
		SecurityContext securityContext = null;
		boolean relayServicesNotifyFlag = false;
		
		logger.info("The buyer resource ::"+buyerResourceId);
		ServiceOrder serviceOrder = null;
		try {
			securityContext = getSecurityContextForBuyer(buyerResourceId);
		}
		catch(NumberFormatException nme){
			logger.error("SOAcceptEstimateService.execute(): Number Format Exception " +
						 "occurred for Buyer id: " + buyerId, nme);
		}
		catch (Exception exception) {
			logger.error("SOAcceptEstimateService.execute(): Exception occurred while " +
						 "accessing security context with Buyer id: " + buyerId, exception);
		}
		
		if (securityContext == null) {
			logger.error("SOCreateService.execute(): SecurityContext is null");
			return createErrorResponse(ResultsCode.INVALID_RESOURCE_ID.getMessage(), ResultsCode.INVALID_RESOURCE_ID.getCode());
		} else {
			try {
				serviceOrder = serviceOrderBO.getServiceOrder(soId);

				// Validate the buyer resource id in the identification tag
				// Validate the service order and the estimate id

				if ((null != serviceOrder)
						&& (serviceOrder.getBuyer().getBuyerId().intValue() != securityContext
								.getCompanyId().intValue())) {

					Results results = Results
							.getError(ResultsCode.NOT_AUTHORISED_BUYER_ID
									.getMessage()
									+ ":" + soId, ResultsCode.FAILURE.getCode());
					soUpdateResponse.setResults(results);

				} else if (null == serviceOrder) {
					Results results = Results
							.getError(ResultsCode.SERVICE_ORDER_DOES_NOT_EXIST
									.getMessage()
									+ ":" + soId, ResultsCode.FAILURE.getCode());
					soUpdateResponse.setResults(results);
				} else {
					// Validate the estimate id
					
					
					Integer estid = Integer.valueOf(soUpdateRequest.getEstimationId());
					logger.info("estid ::"+estid);
					boolean validEstimate = serviceOrderBO.validateEstimate(soId, estid);
					logger.info("validEstimate::"+validEstimate);
					
					if(validEstimate){
						String modifiedBy = securityContext.getUsername();
						
						logger.info("modifiedBy::"+modifiedBy);
						
						String source = PublicAPIConstant.CLIENT;
						// Update the status of estimate and the date of update
						
						EstimateVO estimateVO=serviceOrderBO.getEstimateMainDetails(soId, estid);
																		
						serviceOrderBO.updateEstimateStatus(soId,estid,
								soUpdateRequest.getAction(),
								soUpdateRequest.getComments(),
								source,
								modifiedBy,
								soUpdateRequest.getCustomerName());
						
						if(null!=estimateVO){
						estimateVO.setAction("UPDATED");
						estimateVO.setComments(soUpdateRequest.getComments());
						estimateVO.setAcceptSource(source);
						estimateVO.setRespondedCustomerName(soUpdateRequest.getCustomerName());
						estimateVO.setModifiedBy(modifiedBy);
						estimateVO.setSoId(soId);
						estimateVO.setStatus(soUpdateRequest.getAction());
						
						serviceOrderBO.insertEstimateHistory(estimateVO);
						}
						
						// TODO later : update the service order sub status
						// TODO later : update service order history
						// TODO later : convert to tasks when status is accepted
						
						// Senting Notification for Relay Services
						relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId);
						
						if(relayServicesNotifyFlag){
							relayNotificationService.sentNotificationRelayServices(MPConstants.UPDATE_ESTIMATE_STATUS_API_EVENT, soId);
						}
						
						// create success response
						return createSuccessResponse();
					}else{
						return createErrorResponse(ResultsCode.INVALID_ESTIMATION_ID_ERROR.getMessage(), ResultsCode.INVALID_ESTIMATION_ID_ERROR.getCode());
					}
				}

			} catch (Exception e) {
				logger.error("Exception-->" + e.getMessage(), e);
			}

			logger.info("Exiting execute method");
			return soUpdateResponse;

		}
	}

	
	private SOUpdateEstimateResponse createErrorResponse(String message, String code){
		SOUpdateEstimateResponse updateResponse=new SOUpdateEstimateResponse();
		Results results = Results.getError(message, code);
		updateResponse.setResults(results);
		return updateResponse;
	}
	
	
	private SOUpdateEstimateResponse createSuccessResponse() {
		SOUpdateEstimateResponse response = new SOUpdateEstimateResponse();
		Results results = Results.getSuccess();
		response.setResults(results);
		return response;
	}
	
	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public SecurityProcess getSecurityProcess() {
		return securityProcess;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
	
}