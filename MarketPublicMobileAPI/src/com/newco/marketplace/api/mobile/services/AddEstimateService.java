package com.newco.marketplace.api.mobile.services;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.AddEstimateRequest;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.AddEstimateResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.SODetailsRetrieveMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.mobile.api.utils.validators.EstimateValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;

@APIRequestClass(AddEstimateRequest.class)
@APIResponseClass(AddEstimateResponse.class)
public class AddEstimateService extends BaseService{
	private static final Logger LOGGER = Logger.getLogger(AddEstimateService.class);
    private IMobileGenericBO mobileGenericBO;
	private SODetailsRetrieveMapper retrieveMapper;
	private EstimateValidator estimateValidator;
	private IRelayServiceNotification relayNotificationService;
	private IMobileSOActionsBO mobileSOActionsBO;
	
	/**
	 * This method handle the main logic for Adding/updating the estimate for a service order.
	 * Map request to vo class using mapper
     * do validation using validator,
	 * Set error response using mapper in response if any
	 * save estimate using BO
     * save items using BO
	 * Set estimation Id in response using Mapper
	 * Return response
	 * @param apiVO
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		/**
		  1) Map request to vo class using mapper
		  2) do validation using validator,
		  3) Set error response using mapper in response if any
		  4) save estimate using BO
		  5) save items using BO
		  6) Set estimation Id in response using Mapper
		  7)Return response
		*/
		//declaring variables
		AddEstimateResponse response =new AddEstimateResponse();
		Integer estimationId = null;
		EstimateVO estimateVO =null;
		String userName = null;
		boolean relayServicesNotifyFlag = false;
		Integer buyerId = null;
		
		//getting request from API vo
		AddEstimateRequest  addEstimateRequest = (AddEstimateRequest) apiVO.getRequestFromPostPut();
        Integer providerId = apiVO.getProviderResourceId();
		SecurityContext securityContext = getSecurityContextForVendor(providerId);
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
				    if(null!= estimateVO.getResultCode() && estimateVO.getResultCode().equals(ResultsCode.SUCCESS)){
				    	//Adding/Updating the Estimate Details in so_estimations and so_estimation_items
				    	estimateVO = mobileGenericBO.saveEditEstimate(estimateVO, estimateVO.getEstimationId());
				    	if(null!= estimateVO && null!= estimateVO.getAddEditestimationId()){
				    		
				    		if (!estimateVO.isEstimateUpdate() && !estimateVO.isEstimatePriceChange() && !estimateVO.isEstimateAdded()) {
				    			LOGGER.info("isEstimateUpdate isEstimatePriceChange isEstimateAdded are coming as:  FALSE");
								response = retrieveMapper.createErrorResponse(ResultsCode.NO_ESTIMATE_CHANGE.getMessage(),ResultsCode.NO_ESTIMATE_CHANGE.getCode());
							} else {

								// Setting success Response
								response = retrieveMapper.createSuccessResponse(estimateVO.getAddEditestimationId());
								// Senting Notification for Relay Services
								ServiceOrder serviceOrder = mobileSOActionsBO.getServiceOrder(estimateVO.getSoId());

								if (null != serviceOrder) {
									buyerId = serviceOrder.getBuyerId();
									relayServicesNotifyFlag = relayNotificationService.isRelayServicesNotificationNeeded(buyerId,
											estimateVO.getSoId());

									if (relayServicesNotifyFlag) {
										if (estimateVO.isEstimateAdded()) {
											
											Map<String, String> params = new HashMap<String, String>();

											if (null != estimateVO.getVendorId()) {
												List<Integer> vendorIds = new ArrayList<Integer>();
												vendorIds.add(estimateVO.getVendorId());
												String vendorDetails = mobileSOActionsBO.getVendorBNameList(vendorIds);

												params.put("firmsdetails", vendorDetails);
											}
											
											params.put("resourceId", null != estimateVO.getResourceId() ? estimateVO.getResourceId().toString() : "0");
											
											relayNotificationService.sentNotificationRelayServices(MPConstants.ADD_ESTIMATE_API_EVENT,
													estimateVO.getSoId(), params);
										} else {
											if (estimateVO.isEstimateUpdate() && !(estimateVO.isEstimatePriceChange())) {
												relayNotificationService.sentNotificationRelayServices(
														MPConstants.UPDATE_ESTIMATE_API_EVENT, estimateVO.getSoId());
											}
											if (estimateVO.isEstimatePriceChange()) {

												Map<String, String> params = new HashMap<String, String>();

												if (null != estimateVO.getVendorId()) {
													List<Integer> vendorIds = new ArrayList<Integer>();
													vendorIds.add(estimateVO.getVendorId());
													String vendorDetails = mobileSOActionsBO.getVendorBNameList(vendorIds);

													params.put("firmsdetails", vendorDetails);
												}
												
												params.put("resourceId", null != estimateVO.getResourceId() ? estimateVO.getResourceId().toString() : "0");

												relayNotificationService.sentNotificationRelayServices(
														MPConstants.UPDATE_ESTIMATE_PRICE_API_EVENT, estimateVO.getSoId(), params);
											}
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
				    	 response = retrieveMapper.createErrorResponse(estimateVO.getResultCode().getMessage(),estimateVO.getResultCode().getCode());
				     }
				}else{
					LOGGER.info("EstimateVo is null from Mapper in AddEstimateService");
					response = retrieveMapper.createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				}
			 }
		} catch (Exception e) {
			LOGGER.error("Exception thrown in execute of AddEstimateService"+ e.getMessage());
			response = retrieveMapper.createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		return response;
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

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	
	
}
