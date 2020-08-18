/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-May-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.CustomReference;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.search.SOSearchRequest;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.beans.so.search.SearchResults;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.SearchRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.DataException;
import com.sears.os.service.ServiceConstants;

/**
 * This class would act as a Mapper class for Mapping SearchRequest Object to
 * SearchRequestVO Object.and also for mapping list of ServiceOrderTabResultsVO
 * to SoSearchResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOSearchMapper {
	private Logger logger = Logger.getLogger(SOSearchMapper.class);

	/**
	 * This method is for mapping Mapping SearchRequest Object to
	 * SearchRequestVO Object.
	 * 
	 * @param searchRequest
	 *            SearchRequest
	 * @param securityContext
	 *            SecurityContext
	 * @throws DataException
	 * @return SearchRequestVO
	 */
	public SearchRequestVO mapServiceOrder(SOSearchRequest searchRequest,
			SecurityContext securityContext) throws DataException {
		logger.info("Entering SOSearchMapper.mapServiceOrder()");
		SearchRequestVO searchRequestVO = new SearchRequestVO();
		if (securityContext.getCompanyId() == null) {
			logger.error("The company ID is null in the Security Context");
			throw new NullPointerException("The company ID is null in the"
					+ " Security Context");
		}
		logger.info("Setting Buyer Information from securitycontext");
		searchRequestVO.setBuyerId(securityContext.getCompanyId());
		searchRequestVO.setRoleId(securityContext.getRoleId());
		searchRequestVO.setVendorResouceId(securityContext.getVendBuyerResId());
		searchRequestVO.setCustomerName(searchRequest.getSosearch()
				.getCustomerName());
		searchRequestVO.setServiceLocZipcode(searchRequest.getSosearch()
				.getServiceLocZipcode());
		searchRequestVO.setServiceLocPhone(searchRequest.getSosearch()
				.getServiceLocPhone());
		if (searchRequest.getSosearch().getMaxResultSet() != null) {
			searchRequestVO.setPageSize(Integer.parseInt(searchRequest
					.getSosearch().getMaxResultSet()));
		}
		logger.info("Setting Customer Reference search values to list of " +
				"ServiceOrderCustomRefVO");
		if (searchRequest.getSosearch().getCustomReferences() != null) {
			if (searchRequest.getSosearch().getCustomReferences()
					.getCustomRefList() != null
					&& searchRequest.getSosearch().getCustomReferences()
							.getCustomRefList().size() > 0) {
				List<ServiceOrderCustomRefVO> soCustomerRefList = new 
					ArrayList<ServiceOrderCustomRefVO>();
				Iterator<CustomReference> customerRefList = searchRequest
						.getSosearch().getCustomReferences().getCustomRefList()
						.iterator();
				while (customerRefList.hasNext()) {
					CustomReference customReference = (CustomReference)
						customerRefList.next();
					ServiceOrderCustomRefVO serviceOrderCustomRefVO = new 
						ServiceOrderCustomRefVO();
					serviceOrderCustomRefVO.setRefType(customReference
							.getName());
					serviceOrderCustomRefVO.setRefValue(customReference
							.getValue());
					soCustomerRefList.add(serviceOrderCustomRefVO);
				}
				searchRequestVO.setCustomRefs(soCustomerRefList);
			}
		}
		logger.info("Leaving SOSearchMapper.mapServiceOrder()");
		return searchRequestVO;

	}

	/**
	 * This method is for mapping Mapping list of ServiceOrderSearchResultsVO to
	 * SoSearchResponse Object.
	 * 
	 * @param searchResultList
	 *            List<ServiceOrderSearchResultsVO>
	 * @throws DataException
	 * @return SoSearchResponse
	 */
	public SOSearchResponse mapResponseSearch(
			List<ServiceOrder> searchResultList){
		logger.info("Inside mapResponseSearch--->Starts");
		SOSearchResponse soSearchResponse = new SOSearchResponse();
		SearchResults searchResults = new SearchResults();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		List<OrderStatus> orderStatusList = new ArrayList<OrderStatus>();
		logger
				.info("Setting searchResultList values to list of OrderStatus" +
						" object of Response object");
		if (searchResultList != null && searchResultList.size() > 0) {

			Iterator<ServiceOrder> searchList = searchResultList.iterator();
			while (searchList.hasNext()) {
				ServiceOrder searchResultVO = (ServiceOrder) searchList.next();
				OrderStatus orderStatus = new OrderStatus();

				orderStatus.setSoId(searchResultVO.getSoId());
				if (null != searchResultVO.getStatus()) {
					orderStatus.setStatus(searchResultVO.getStatus());
				} else {
					orderStatus.setStatus("");
				}
				if (null != searchResultVO.getSubStatus()) {
					orderStatus.setSubstatus((searchResultVO.getSubStatus()));
				} else {
					orderStatus.setSubstatus("");
				}
				if (null != searchResultVO.getCreatedDate()) {

					orderStatus.setCreatedDate(CommonUtility.sdfToDate
							.format(searchResultVO.getCreatedDate()));
				} else {
					orderStatus.setCreatedDate("");
				}
				if (null != searchResultVO.getRoutedDate()) {

					orderStatus.setPostedDate(CommonUtility.sdfToDate
							.format(searchResultVO.getRoutedDate()));
				}
				if (null != searchResultVO.getAcceptedDate()) {

					orderStatus.setAcceptedDate(CommonUtility.sdfToDate
							.format(searchResultVO.getAcceptedDate()));
				}
				if (null != searchResultVO.getActivatedDate()) {

					orderStatus.setActiveDate(CommonUtility.sdfToDate
							.format(searchResultVO.getActivatedDate()));
				}
				if (null != searchResultVO.getCompletedDate()) {

					orderStatus.setCompletedDate(CommonUtility.sdfToDate
							.format(searchResultVO.getCompletedDate()));
				}
				if (null != searchResultVO.getClosedDate()) {
					orderStatus.setClosedDate(CommonUtility.sdfToDate
							.format(searchResultVO.getClosedDate()));
				}

				orderStatusList.add(orderStatus);
			}
			logger.info("Setting result and message as Success when the Search" +
					" result list is not empty");
			result.setMessage(searchResultList.size()+ 
					PublicAPIConstant.SEARCH_SUCCESSFUL);
			result.setCode(PublicAPIConstant.ONE);
			errorResult.setCode(PublicAPIConstant.ZERO);
			errorResult.setMessage("");
			searchResults.setOrderstatus(orderStatusList);
			soSearchResponse.setSearchResults(searchResults);
		} else {
			logger.info("Setting result and message as Failure when the Search" +
					" result list is empty");
			
			result.setCode(PublicAPIConstant.ZERO);
			result.setMessage("");
			errorResult.setMessage(CommonUtility.getMessage(
					PublicAPIConstant.SEARCH_UNSUCCESSFUL_ERROR_CODE));
			errorResult.setCode(ServiceConstants.USER_ERROR_RC);
			
		}
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		soSearchResponse.setResults(results);
		soSearchResponse.setVersion(PublicAPIConstant.SEARCH_VERSION);
		soSearchResponse
				.setSchemaLocation(PublicAPIConstant.SEARCH_SCHEMALOCATION);
		soSearchResponse
				.setNamespace(PublicAPIConstant.SEARCHRESPONSE_NAMESPACE);
		soSearchResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		logger.info("Inside mapResponseSearch--->Ends");
		return soSearchResponse;

	}	
}
