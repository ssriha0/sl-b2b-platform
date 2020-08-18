/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 17-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOSearchByProviderMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.SearchRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * This class is a service class for searching Service Orders by provider.
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOSearchByProviderService extends BaseService {
	private Logger logger = Logger.getLogger(SOSearchByProviderService.class);

	private SOSearchByProviderMapper searchByProviderMapper;
	private IServiceOrderBO serviceOrderBO;

	public SOSearchByProviderService() {
		super(null, PublicAPIConstant.SEARCHRESPONSE_PRO_XSD,
				PublicAPIConstant.SEARCHRESPONSE_PROVIDER_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				PublicAPIConstant.SEARCH_SCHEMALOCATION, null,
				SOSearchResponse.class);
		addRequiredGetParam(PublicAPIConstant.PAGE_SIZE_SET, 
				DataTypes.INTEGER);
	}

	/**
	 * This method is for searching service orders by provider.
	 * 
	 * @param APIRequestVO	
	 * @return IAPIResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering SOSearchByProviderService.execute() ");
		SOSearchResponse soSearchResponse = new SOSearchResponse();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		SecurityContext securityContext = null;
		
		try {
		
		Integer providerId = apiVO.getProviderIdInteger();
		if(null != providerId){
				Integer vendorId = serviceOrderBO.getValidProvider(providerId);
				if(null == vendorId){
					Results results = Results.getError(
							ResultsCode.PROVIDER_DOES_NOT_EXIST.getMessage(),
							ResultsCode.PROVIDER_DOES_NOT_EXIST.getCode());

					soSearchResponse.setResults(results);
					soSearchResponse
							.setVersion(PublicAPIConstant.SEARCH_VERSION);
					soSearchResponse
							.setSchemaLocation(PublicAPIConstant.
									SEARCH_SCHEMALOCATION);
					soSearchResponse
							.setNamespace(PublicAPIConstant.
									SEARCHRESPONSE_PROVIDER_NAMESPACE);
					soSearchResponse
							.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
					return soSearchResponse;
				}
		}
		String soStatus = requestMap.get(PublicAPIConstant.SO_STATUS);
		if(soStatus==null){
			Results results = Results.getError(
					ResultsCode.STATUS_IS_REQUIRED.getMessage(),
					ResultsCode.STATUS_IS_REQUIRED.getCode());

			soSearchResponse.setResults(results);
			soSearchResponse
					.setVersion(PublicAPIConstant.SEARCH_VERSION);
			soSearchResponse
					.setSchemaLocation(PublicAPIConstant.
							SEARCH_SCHEMALOCATION);
			soSearchResponse
					.setNamespace(PublicAPIConstant.
							SEARCHRESPONSE_PROVIDER_NAMESPACE);
			soSearchResponse
					.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
			return soSearchResponse;
		}

		Integer resourceId = apiVO.getProviderResourceId();
		if (null != resourceId){
			
				securityContext = getSecurityContextForVendor(resourceId);
				if (securityContext == null) {
					logger.error("SecurityContext is null");
					Results results = Results.getError(
							ResultsCode.INVALID_RESOURCE_ID.getMessage(),
							ResultsCode.INVALID_RESOURCE_ID.getCode());

					soSearchResponse.setResults(results);
					soSearchResponse
							.setVersion(PublicAPIConstant.SEARCH_VERSION);
					soSearchResponse
							.setSchemaLocation(PublicAPIConstant.
									SEARCH_SCHEMALOCATION);
					soSearchResponse
							.setNamespace(PublicAPIConstant.
									SEARCHRESPONSE_PROVIDER_NAMESPACE);
					soSearchResponse
							.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
					return soSearchResponse;

				} else {
					if (!validate(requestMap)) {
						logger.error("Validation Error");
						String errorField = requestMap
								.get(PublicAPIConstant.ERROR_FIELD);
						Results results = null;
						if (PublicAPIConstant.ZIP.equals(errorField)) {
							results = Results
									.getError(
											ResultsCode.SEARCH_VALIDATION_ZIP_ERROR_CODE
													.getMessage(),
											ResultsCode.SEARCH_VALIDATION_ZIP_ERROR_CODE
													.getCode());
						} else if (PublicAPIConstant.PAGE_SIZE_SET
								.equals(errorField)){
							results = Results
									.getError(
					ResultsCode.SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE
												.getMessage(),
					ResultsCode.SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE
													.getCode());
						} else if (PublicAPIConstant.SO_STATUS_SET
								.equals(errorField)){
							results = Results
									.getError(
											ResultsCode.
								SEARCH_VALIDATION_PROVIDER_STATUS_SET_ERROR_CODE
													.getMessage(),
											ResultsCode.
								SEARCH_VALIDATION_PROVIDER_STATUS_SET_ERROR_CODE
													.getCode());
						}
						soSearchResponse.setResults(results);
						soSearchResponse
								.setVersion(PublicAPIConstant.SEARCH_VERSION);
						soSearchResponse
								.setSchemaLocation(PublicAPIConstant.
										SEARCH_SCHEMALOCATION);
						soSearchResponse
								.setNamespace(PublicAPIConstant.
										SEARCHRESPONSE_PROVIDER_NAMESPACE);
						soSearchResponse
								.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
						return soSearchResponse;
					}
					SearchRequestVO searchRequestVO = new SearchRequestVO();
					searchRequestVO = searchByProviderMapper.mapSoSearchRequest(
							requestMap,  securityContext);

					List<ServiceOrder> serviceOrderSearchResults = 
						new ArrayList<ServiceOrder>();
					searchRequestVO.setVendorResouceId(resourceId);
					serviceOrderSearchResults = serviceOrderBO
							.getSearchResultSetPaged(searchRequestVO);

					soSearchResponse = searchByProviderMapper
							.mapResponseSearch(serviceOrderSearchResults);
				}
			}
		} catch (NumberFormatException ex) {
			logger.error("SOSearchByProviderService.execute()-->"
					+ "NumberFormatException-->" + ex.getMessage(), ex);
			Results results = Results.getError(
					ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS
							.getMessage(),
					ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS
							.getCode());
			soSearchResponse = new SOSearchResponse();
			soSearchResponse.setResults(results);
		} catch (Exception ex) {
			logger.error("SOSearchByProviderService.execute()--> Exception-->"
					+ ex.getMessage(), ex);
			Results results = Results.getError(ResultsCode.GENERIC_ERROR
					.getMessage(), ResultsCode.GENERIC_ERROR.getCode());
			soSearchResponse = new SOSearchResponse();
			soSearchResponse.setResults(results);
		}
		logger.info("Leaving SOSearchByProviderService.execute()");
		return soSearchResponse;
	}

	/**
	 * This method is used for validating zip and max result set values.
	 * 
	 * @param requestMap	
	 * @return boolean
	 */
	private boolean validate(Map<String, String> requestMap) {
		logger.info("Entering SOSearchByProviderService.validate()");
		boolean result = false;
		String zip = requestMap.get(PublicAPIConstant.ZIP);
		String soStatus = requestMap.get(PublicAPIConstant.SO_STATUS);
		List<String> soStatusList = new ArrayList<String>();

		if (null != soStatus) {
			StringTokenizer strTok = new StringTokenizer(
					soStatus,
					PublicAPIConstant.SEPARATOR, false);
			int noOfStatuses = strTok.countTokens();
			for (int i = 1; i <= noOfStatuses; i++) {
				soStatusList.add(new String(strTok
						.nextToken()));
			}
		}
		Integer pageSize = Integer.parseInt(requestMap
				.get(PublicAPIConstant.PAGE_SIZE_SET));

		if (null != zip && zip.length() != 5) {
			requestMap
					.put(PublicAPIConstant.ERROR_FIELD, PublicAPIConstant.ZIP);
			return result;
		}
		List<Integer> pageSizeSetValues = Arrays.asList(
				PublicAPIConstant.PAGE_SIZE_SET_10,
				PublicAPIConstant.PAGE_SIZE_SET_20,
				PublicAPIConstant.PAGE_SIZE_SET_50,
				PublicAPIConstant.PAGE_SIZE_SET_100,
				PublicAPIConstant.PAGE_SIZE_SET_200,
				PublicAPIConstant.PAGE_SIZE_SET_500);
		if (!pageSizeSetValues.contains(pageSize)) {
			requestMap.put(PublicAPIConstant.ERROR_FIELD,
					PublicAPIConstant.PAGE_SIZE_SET);
			return result;
		}

		List<String> soStatusValues = Arrays.asList(
				PublicAPIConstant.SOSTATUS_SET_ACCEPTED,
				PublicAPIConstant.SOSTATUS_SET_ACTIVE,
				PublicAPIConstant.SOSTATUS_SET_CLOSED,
				PublicAPIConstant.SOSTATUS_SET_COMPLETED,
				PublicAPIConstant.SOSTATUS_SET_RECEIVED,
				PublicAPIConstant.SOSTATUS_SET_EXPIRED,
				PublicAPIConstant.SOSTATUS_SET_PROBLEM);
		if(!soStatusValues.containsAll(soStatusList)){
			requestMap.put(PublicAPIConstant.ERROR_FIELD, 
					PublicAPIConstant.SO_STATUS_SET);
			return result;	
		}	
		logger.info("Leaving SOSearchByProviderService.validate()");
		result = true;
		return result;
	}
	
	public void setSearchByProviderMapper(
			SOSearchByProviderMapper searchByProviderMapper) {
		this.searchByProviderMapper = searchByProviderMapper;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
	
}