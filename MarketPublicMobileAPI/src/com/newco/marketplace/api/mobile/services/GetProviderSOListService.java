package com.newco.marketplace.api.mobile.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.GetProviderSOListResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.ProviderSearchSOMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;

@APIResponseClass(GetProviderSOListResponse.class)
public class GetProviderSOListService extends BaseService {

	private IMobileSOManagementBO mobileSOManagementBO;
	private ProviderSearchSOMapper providerSearchSOMapper;

	private static final Logger logger = Logger
			.getLogger(GetProviderSOListService.class);
	public static final int PAGE_NO_LIMIT = 25;
	public static final int PAGE_SIZE_LIMIT = 250;

	public GetProviderSOListService() {
		/*
		 * super( null, PublicMobileAPIConstant.SEARCH_PROVIDER_SO_RESPONSE_XSD,
		 * PublicMobileAPIConstant.ADVANCED_PROVIDER_SO_MANAGEMENT_NAMESPACE,
		 * PublicMobileAPIConstant.ADVANCED_PROVIDER_SO_MANAGEMENT_SCHEMA,
		 * PublicMobileAPIConstant.SEARCH_PROVIDER_SO_RESPONSE_SCHEMALOCATION,
		 * null, GetProviderSOListResponse.class);
		 */
		super();
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {

		if (apiVO == null)
			return null;

		GetProviderSOListResponse response = new GetProviderSOListResponse();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		List<ProviderSOSearchVO> providerSOList = new ArrayList<ProviderSOSearchVO>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> urlParams = new HashMap<String, Object>();
		String resourceId = apiVO.getProviderResourceId().toString();
		String status = requestMap
				.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.SO_STATUS);
		String pageNo = requestMap
				.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.PAGE_NUMBER);
		String pageSize = requestMap
				.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.PAGE_SIZE);
		int pageNoInt = 0;
		int pageSizeInt = 0;
		
		/* If resourceId is empty return missing parameter */
		if (StringUtils.isBlank(resourceId)) {

			Results results = Results.getError(
					ResultsCode.PROVIDER_SO_SEARCH_INVALID_PROVIDER
							.getMessage(),
					ResultsCode.PROVIDER_SO_SEARCH_INVALID_PROVIDER.getCode());
			response.setResults(results);
			return response;
		}

		try{
			if (StringUtils.isNotBlank(pageNo)) {
				pageNoInt = Integer.parseInt(pageNo);
			}
			
			if (StringUtils.isNotBlank(pageSize)) {
				pageSizeInt = Integer.parseInt(pageSize);
			}
			
			// To check if the pageNo passed is valid
			if (StringUtils.isNotBlank(pageNo)) {
				if (!pageNo.matches("\\d+")) {
					Results results = Results.getError(
							ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_NUMBER
									.getMessage(),
							ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_NUMBER
									.getCode());
					response.setResults(results);
					return response;
				}

			}

			// To check if the page size passed is valid
			if (StringUtils.isNotBlank(pageSize)) {
				if (!pageSize.matches("\\d+")) {
					Results results = Results.getError(
							ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_SIZE
									.getMessage(),
							ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_SIZE
									.getCode());
					response.setResults(results);
					return response;

				}
				if (pageNoInt > PAGE_SIZE_LIMIT) {
					Results results = Results.getError(
							ResultsCode.PROVIDER_SO_SEARCH_NO_MATCH.getMessage(),
							ResultsCode.PROVIDER_SO_SEARCH_NO_MATCH.getCode());
					response.setResults(results);
					return response;
				}

			}
		}
		catch (Exception e) {
			// TODO: handle exception
			Results results = Results.getError(
					ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_NUMBER
							.getMessage(),
					ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_NUMBER
							.getCode());
			response.setResults(results);
			return response;
			
		}
		

		try {

			// To validate if the resource Id is valid
			// if a firmId corresponding to the resourceId is obtained, then the
			// resourceId is valid.
			Integer firmId = mobileSOManagementBO
					.validateProviderId(resourceId);
			if (null == firmId) {
				Results results = Results.getError(
						ResultsCode.PROVIDER_SO_SEARCH_INVALID_PROVIDER
								.getMessage(),
						ResultsCode.PROVIDER_SO_SEARCH_INVALID_PROVIDER
								.getCode());
				response.setResults(results);
				return response;
			}

			// convert the status from the request to corresponding statusIds
			List<Integer> wfStatusId = mobileSOManagementBO
					.validateSOStatus(status);

			if (wfStatusId.isEmpty()) {
				Results results = Results
						.getError(ResultsCode.PROVIDER_SO_SEARCH_INVALID_STATUS
								.getMessage(),
								ResultsCode.PROVIDER_SO_SEARCH_INVALID_STATUS
										.getCode());
				response.setResults(results);
				return response;
			}

			// mapping the request into a map to pass to DB
			params = providerSearchSOMapper.mapProviderSOSearchRequest(apiVO,
					wfStatusId, firmId);

			// Get the total number of records for the provided criteria
			Integer countOfSearchRecords = mobileSOManagementBO
					.getProviderSOSearchCount(params);

			if (pageNoInt != 0 && pageSizeInt !=0) {
				// Find last page and set error if the passed page no is greater
				// than the actual last page no
				double lastPage = Math.ceil((double) countOfSearchRecords
						/ pageSizeInt); // This is the available last page
				if (pageNoInt > lastPage) {
					Results results = Results.getError(
							ResultsCode.PROVIDER_SO_SEARCH_NO_MATCH
									.getMessage(),
							ResultsCode.PROVIDER_SO_SEARCH_NO_MATCH.getCode());
					response.setResults(results);
					return response;
				}

			}
			// retrieve search list from the database.
			providerSOList = mobileSOManagementBO
					.getProviderSOSearchList(params);

			/* If search record is found */
			if (providerSOList.size() > 0) {

				// fetch the base url and path url from the application
				// properties
				urlParams = mobileSOManagementBO.getURLs();

				// map the result received into response.
				response = providerSearchSOMapper.mapProviderSOSearchResponse(
						providerSOList, countOfSearchRecords, urlParams);

			}
			/* If no search record is found or if EOF is reached */
			else {
				Results results = Results.getError(
						ResultsCode.PROVIDER_SO_SEARCH_NO_MATCH.getMessage(),
						ResultsCode.PROVIDER_SO_SEARCH_NO_MATCH.getCode());
				response.setResults(results);
			}
		} catch (Exception e) {
			Results results = Results.getError(
					ResultsCode.PROVIDER_SO_SEARCH_FAILURE.getMessage(),
					ResultsCode.PROVIDER_SO_SEARCH_FAILURE.getCode());
			response.setResults(results);
			logger.error("Inside GetProviderSOListService-->Exception in Getting Search Results:"
					+ e.getMessage());
		}
		return response;
	}

	public IMobileSOManagementBO getMobileSOManagementBO() {
		return mobileSOManagementBO;
	}

	public void setMobileSOManagementBO(
			IMobileSOManagementBO mobileSOManagementBO) {
		this.mobileSOManagementBO = mobileSOManagementBO;
	}

	public ProviderSearchSOMapper getProviderSearchSOMapper() {
		return providerSearchSOMapper;
	}

	public void setProviderSearchSOMapper(
			ProviderSearchSOMapper providerSearchSOMapper) {
		this.providerSearchSOMapper = providerSearchSOMapper;
	}

}
