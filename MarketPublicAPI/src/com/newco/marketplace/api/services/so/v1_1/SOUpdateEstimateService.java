/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-May-2014	KMSTRSUP   Infosys				2.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.newco.marketplace.api.utils.validators.EstimateValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addEstimate.AddEstimateRequest; 
import com.newco.marketplace.api.beans.so.addEstimate.AddEstimateResponse; 
import com.newco.marketplace.api.beans.so.update.SOUpdateEstimateResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.mappers.so.SODetailsRetrieveMapper;

import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;

/**
 * This class would act as a Servicer class for Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOUpdateEstimateService extends BaseService {

	private XStreamUtility conversionUtility;
	private Logger logger = Logger.getLogger(SOUpdateEstimateService.class);
	private IServiceOrderBO serviceOrderBO;
	private SecurityProcess securityProcess;
	private IRelayServiceNotification relayNotificationService;
	
    private IMobileGenericBO mobileGenericBO;
	private SODetailsRetrieveMapper retrieveMapper; 
	private EstimateValidator estimateValidator;
	private IMobileSOActionsBO mobileSOActionsBO;
	
	public SOUpdateEstimateService() {
		super(PublicAPIConstant.SOUPDATEESTIMATE_XSD,
				PublicAPIConstant.SOUPDATESTIMATE_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				AddEstimateRequest.class, AddEstimateResponse.class); 
	}

	/**
	 * Implement the  logic .
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		
		logger.info("Entering execute method SOUpdateEstimateStatusService::");
		String soId = apiVO.getSOId();
		int buyerId = apiVO.getBuyerIdInteger();
		AddEstimateRequest  addEstimateRequest = (AddEstimateRequest) apiVO.getRequestFromPostPut();
		AddEstimateResponse response =new AddEstimateResponse();
		int buyerResourceId = addEstimateRequest.getIdentification().getId();
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
					response.setResults(results);

				} else if (null == serviceOrder) {
					Results results = Results
							.getError(ResultsCode.SERVICE_ORDER_DOES_NOT_EXIST
									.getMessage()
									+ ":" + soId, ResultsCode.FAILURE.getCode());
					response.setResults(results);
				} else {		/**
					  1) Map request to vo class using mapper
					  2) do validation using validator,
					  3) Set error response using mapper in response if any
					  4) save estimate using BO
					  5) save items using BO
					  6) Set estimation Id in response using Mapper
					  7)Return response
					*/
					//declaring variables
					
					Integer estimationId = null;
					EstimateVO estimateVO =null;
					String userName = null;
					if(null != securityContext){
						userName = securityContext.getUsername();
					}	
					try {
						if(null!= addEstimateRequest){
							   if(StringUtils.isNotBlank(addEstimateRequest.getEstimationId()) && StringUtils.isNumeric(addEstimateRequest.getEstimationId())){
							      estimationId = Integer.valueOf(addEstimateRequest.getEstimationId());
							   }
							   //Mapping Request parameters to the VO object
							   estimateVO = retrieveMapper.mapAddEditEstimate(addEstimateRequest,estimationId, apiVO,userName);
							if(null!= estimateVO){
							  	//Validating the EstimationId and Price values
							    estimateVO = estimateValidator.validateAddEstimateRequest(estimateVO);
							    String resultCode=estimateVO.getApiResultCode().toString();
							    
							    if(null!= estimateVO.getApiResultCode() && "0000".equals(resultCode)){
							    	//Adding/Updating the Estimate Details in so_estimations and so_estimation_items
							    	estimateVO = mobileGenericBO.saveEditEstimate(estimateVO, estimateVO.getEstimationId());
							    	if(null!= estimateVO && null!= estimateVO.getAddEditestimationId()){
							    		
							    		
							    		if (null!=addEstimateRequest.getEstimationId() && !estimateVO.isEstimateUpdate() && !estimateVO.isEstimatePriceChange()) {
											response = retrieveMapper.createErrorResponseForNoChange();
										} else {						    		
							    		
							    		//Setting success Response
							    		response = retrieveMapper.createSuccessResponse(estimateVO.getAddEditestimationId());
							    		// Senting Notification for Relay Services
							    		//ServiceOrder serviceOrder = mobileSOActionsBO.getServiceOrder(estimateVO.getSoId());
							    		
							    		if(null!=serviceOrder){
							    			buyerId = serviceOrder.getBuyerId();
							    			relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,estimateVO.getSoId());
										
							    			if(relayServicesNotifyFlag){
							    				if(estimateVO.isEstimateAdded())
							    				{
							    					relayNotificationService.sentNotificationRelayServices(MPConstants.ADD_ESTIMATE_API_EVENT_BUYER, estimateVO.getSoId());				    					
							    				}else
							    				{				    					
							    					if(estimateVO.isEstimateUpdate())
							    					relayNotificationService.sentNotificationRelayServices(MPConstants.UPDATE_ESTIMATE_API_EVENT_BUYER, estimateVO.getSoId());
							    					if(estimateVO.isEstimatePriceChange())
								    				relayNotificationService.sentNotificationRelayServices(MPConstants.UPDATE_ESTIMATE_PRICE_API_EVENT_BUYER, estimateVO.getSoId());

							    				}
							    			}
							    		}
							    		
							    	}
							    		
							    	}else{ 
							    		//Setting error Response
							    		response = retrieveMapper.createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
							    	}
							     }//Setting Error Response set in EstimateVO ResultCode object from the validator
							     else{
							    	 response = retrieveMapper.createErrorResponse(estimateVO.getApiResultCode().getMessage(),estimateVO.getApiResultCode().getCode());
							     }
							}else{
								logger.info("EstimateVo is null from Mapper in AddEstimateService");
								response = retrieveMapper.createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
							}
						 }
					} catch (Exception e) {
						logger.error("Exception thrown in execute of AddEstimateService"+ e.getMessage());
						response = retrieveMapper.createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
					}
					}

			} catch (Exception e) {
				logger.error("Exception-->" + e.getMessage(), e);
			}

			logger.info("Exiting execute method");
			return response;

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

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public SODetailsRetrieveMapper getRetrieveMapper() {
		return retrieveMapper;
	}

	public void setRetrieveMapper(SODetailsRetrieveMapper retrieveMapper) {
		this.retrieveMapper = retrieveMapper;
	}

	public EstimateValidator getEstimateValidator() {
		return estimateValidator;
	}

	public void setEstimateValidator(EstimateValidator estimateValidator) {
		this.estimateValidator = estimateValidator;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}
	
	
	
	
}