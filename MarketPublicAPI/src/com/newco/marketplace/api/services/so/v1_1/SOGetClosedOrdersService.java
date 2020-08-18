package com.newco.marketplace.api.services.so.v1_1;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.closedOrders.RetrieveClosedOrdersResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.ClosedSOByProviderMapper;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ClosedOrdersRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ClosedServiceOrderVO;

/**
 * This class would act as a Servicer class for retrieving closed SO Details for provider.
 * 
 * @author Infosys
 * @version 1.0
 */
@APIResponseClass(RetrieveClosedOrdersResponse.class)
public class SOGetClosedOrdersService extends BaseService {

	private Logger logger = Logger.getLogger(SOGetClosedOrdersService.class);
	private ClosedSOByProviderMapper closedSOByProviderMapper;
	private IServiceOrderBO serviceOrderBO;
	
	
	/**
	 * This method is for retrieving closed SO Details for provider.
	 * 
	 * @param fromDate
	 *            String,toDate String
	 * @return String
	 */
	
	public SOGetClosedOrdersService() {
		super(null, PublicAPIConstant.CLOSEDSORESPONSE_PRO_XSD,
				PublicAPIConstant.CLOSEDSORESPONSE_PRO_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				PublicAPIConstant.CLOSEDSORESPONSE_SCHEMALOCATION,
				null, RetrieveClosedOrdersResponse.class);
	}

	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of SOGetClosedOrdersService");
		RetrieveClosedOrdersResponse soGetResponse = new RetrieveClosedOrdersResponse();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		String providerId = apiVO.getProviderId();
		Results results = null;
		try {
			if (StringUtils.isNotBlank(providerId)){
				if(!validate(requestMap)){
					//no validation errors
					
					//mapping request parameters
					ClosedOrdersRequestVO closedOrdersRequestVO = closedSOByProviderMapper.mapClosedSoRequest(
							requestMap,  providerId);
					
					List<ClosedServiceOrderVO> serviceOrderResults = serviceOrderBO
							.getClosedServiceOrders(closedOrdersRequestVO);

					//mapping response
					soGetResponse = closedSOByProviderMapper
							.mapResponse(serviceOrderResults);
				}
				else{
					//validation error occured
					logger.error("Validation Error");
					String errorField = requestMap
							.get(PublicAPIConstant.ERROR_FIELD);
					if (PublicAPIConstant.ZIP.equalsIgnoreCase(errorField)) {
						results = Results
								.getError(
										ResultsCode.SEARCH_VALIDATION_ZIP_ERROR_CODE
												.getMessage(),
										ResultsCode.SEARCH_VALIDATION_ZIP_ERROR_CODE
												.getCode());
					} else if (PublicAPIConstant.COMPLETED_IN
							.equalsIgnoreCase(errorField)){
						results = Results
								.getError(
				ResultsCode.CLOSEDORDERS_VALIDATION_COMPLETEDIN_ERROR_CODE
											.getMessage(),
				ResultsCode.CLOSEDORDERS_VALIDATION_COMPLETEDIN_ERROR_CODE
												.getCode());
					} else if (PublicAPIConstant.NO_OF_ORDERS
							.equalsIgnoreCase(errorField)){
						results = Results
								.getError(
										ResultsCode.
										CLOSEDORDERS_VALIDATION_NOOFORDERS_ERROR_CODE
												.getMessage(),
										ResultsCode.
										CLOSEDORDERS_VALIDATION_NOOFORDERS_ERROR_CODE
												.getCode());
					}
					else if (PublicAPIConstant.INVALID_FILTER.equalsIgnoreCase(errorField)) {
						results = Results
								.getError(
										ResultsCode.
										INVALID_RESPONSE_FILTER
											.getMessage(),
										ResultsCode.
										INVALID_RESPONSE_FILTER
											.getCode());
					}
					soGetResponse.setResults(results);
				}
				
			}
		}catch (Exception e) {
			logger.error("Exception in execute method of SOGetClosedOrdersService-->" + e.getMessage(), e);
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			soGetResponse.setResults(results);
		}
		logger.info("Exiting execute method of SOGetClosedOrdersService");
		return soGetResponse;
	}
	
	/**
	 * This method is used for validating zip and completedIn and noOfOrders value.
	 * @param requestMap	
	 * @return boolean
	 */
	private boolean validate(Map<String, String> requestMap) {
		logger.info("Entering SOGetClosedOrdersService.validate()");
		boolean errorOccured = false;
		String zip = requestMap.get(PublicAPIConstant.ZIP);
		
//		if(null!= requestMap && !requestMap.isEmpty()){
//			if(!PublicAPIConstant.getClosedOrderFilter().containsAll(requestMap.keySet())){		
//				
//				requestMap.put(PublicAPIConstant.ERROR_FIELD,
//						PublicAPIConstant.INVALID_FILTER);
//				errorOccured= true;	
//			}
//		}
		
		if(!errorOccured){		
			//validating completedIn filter value
			Integer completedIn = null;
			String completedInString = requestMap.get(PublicAPIConstant.COMPLETED_IN);
			if(StringUtils.isNotBlank(completedInString)){
				if (StringUtils.isNumeric(completedInString)){
					completedIn = Integer.parseInt(completedInString);
					// validating whether value for Filter condition for SO closed date in months is less than 1
					if (null!=completedIn && completedIn.intValue()<PublicAPIConstant.INTEGER_ONE) {
						requestMap.put(PublicAPIConstant.ERROR_FIELD,
										PublicAPIConstant.COMPLETED_IN);
						errorOccured = true;
						}
				}
				else{
					//if completedIn value is not numeric
					requestMap.put(PublicAPIConstant.ERROR_FIELD,
								PublicAPIConstant.COMPLETED_IN);
					errorOccured = true;
					}
				}
		}	
	
		if(!errorOccured){
			//validating noOfOrders filter value
			Integer noOfOrders = null;
			String noOfOrdersString = requestMap.get(PublicAPIConstant.NO_OF_ORDERS);
			if(StringUtils.isNotBlank(noOfOrdersString)){
				if (StringUtils.isNumeric(noOfOrdersString)){
					noOfOrders = Integer.parseInt(noOfOrdersString);
					// validating whether value for Filter condition for no of orders is less than 1
					if (null!=noOfOrders && noOfOrders.intValue()<PublicAPIConstant.INTEGER_ONE) {
							requestMap.put(PublicAPIConstant.ERROR_FIELD,
										PublicAPIConstant.NO_OF_ORDERS);
							errorOccured = true;
						}
				}
				else{
					// if noOfOrders is not numeric
					requestMap.put(PublicAPIConstant.ERROR_FIELD,
								PublicAPIConstant.NO_OF_ORDERS);
					errorOccured = true;
					}
				}
			
		}
		
		if(!errorOccured && StringUtils.isNotBlank(zip) && zip.length() != 5){
			//validating zip filter value
			requestMap
						.put(PublicAPIConstant.ERROR_FIELD, PublicAPIConstant.ZIP);
			errorOccured = true;
		}
		
	
		logger.info("Leaving SOSearchByProviderService.validate()");
		return errorOccured;
	}
	
	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public ClosedSOByProviderMapper getClosedSOByProviderMapper() {
		return closedSOByProviderMapper;
	}

	public void setClosedSOByProviderMapper(
			ClosedSOByProviderMapper closedSOByProviderMapper) {
		this.closedSOByProviderMapper = closedSOByProviderMapper;
	}


	
}