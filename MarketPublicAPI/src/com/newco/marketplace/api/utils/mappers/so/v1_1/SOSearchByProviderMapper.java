/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 17-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.beans.so.search.SearchResults;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.SearchRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.DataException;

/**
 * This class would act as a Mapper class for Mapping SearchRequest Object to
 * SearchRequestVO Object.and also for mapping list of ServiceOrderTabResultsVO
 * to SoSearchResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOSearchByProviderMapper {
	private Logger logger = Logger.getLogger(SOSearchByProviderMapper.class);

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
			List<ServiceOrder> searchResultList) {
		logger.info("Entering SOSearchByProviderMapper.mapResponseSearch()");
		SOSearchResponse soSearchResponse = new SOSearchResponse();
		SearchResults searchResults = new SearchResults();
		Results results = new Results();
		List<OrderStatus> orderStatusList = new ArrayList<OrderStatus>();
		logger.info("Setting searchResultList values to list of OrderStatus" +
						" object of Response object");
		if (searchResultList != null) {
			if(searchResultList.size() <= 0){
				logger.info("Setting result and message as Failure when the Search" +
				" result list is empty");
				results = Results.getError(
						ResultsCode.SEARCH_NO_RECORDS.getMessage(), 
						ResultsCode.SEARCH_NO_RECORDS.getCode());
				
			}else{
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
					if (null != searchResultVO.getPriceModel()) {
						orderStatus.setPriceModel(searchResultVO.getPriceModel());
					}

					orderStatusList.add(orderStatus);
				}
				logger.info("Setting result and message as Success when the Search" +
						" result list is not empty");			
				results = Results.getSuccess(searchResultList.size()+ 
						PublicAPIConstant.SEARCH_SUCCESSFUL);
				searchResults.setOrderstatus(orderStatusList);
				soSearchResponse.setSearchResults(searchResults);	
			}
		} else {
			logger.info("Setting result and message as Failure when the Search" +
					" result list is null");
			results = Results.getError(
					ResultsCode.SEARCH_UNSUCCESSFUL.getMessage(), 
					ResultsCode.SEARCH_UNSUCCESSFUL.getCode());
			
		}		
		soSearchResponse.setResults(results);
		soSearchResponse.setVersion(PublicAPIConstant.SEARCH_VERSION);
		soSearchResponse
				.setSchemaLocation(PublicAPIConstant.SEARCH_SCHEMALOCATION);
		soSearchResponse
				.setNamespace(PublicAPIConstant.SEARCHRESPONSE_NAMESPACE);
		soSearchResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		logger.info("Leaving SOSearchByProviderMapper.mapResponseSearch()");
		return soSearchResponse;

	}
	/**
	 * This method is for mapping Mapping list of ServiceOrderSearchResultsVO to
	 * SoSearchResponse Object.
	 * 
	 * @param requestMap
	 * @param securityContext	
	 * @return SearchRequestVO
	 */
	public SearchRequestVO mapSoSearchRequest(Map<String,String> requestMap, 
			 SecurityContext securityContext){
		logger.info("Entering SOSearchByProviderMapper.mapSoSearchRequest()");
		SearchRequestVO searchRequestVO = new SearchRequestVO();
		searchRequestVO.setRoleId(securityContext.getRoleId());
		searchRequestVO.setVendorResouceId(
							securityContext.getVendBuyerResId());
		searchRequestVO.setServiceLocZipcode(requestMap
				.get(PublicAPIConstant.ZIP));
		searchRequestVO.setCustomerName(requestMap
				.get(PublicAPIConstant.CUSTOMER_NAME));
		searchRequestVO.setServiceLocPhone(requestMap
				.get(PublicAPIConstant.SERVICE_LOC_PHONE));
		Integer pageSize = Integer.parseInt(requestMap
				.get(PublicAPIConstant.PAGE_SIZE_SET));
		searchRequestVO.setPageNumber(Integer.parseInt(requestMap
				.get(PublicAPIConstant.PAGE_NUMBER)));
		searchRequestVO.setPageSize(pageSize.intValue());
		searchRequestVO
				.setPageLimit((searchRequestVO.getPageSize() * (searchRequestVO
						.getPageNumber() - 1)) );

		String soStatus = requestMap.get(PublicAPIConstant.SO_STATUS);

		if (null != soStatus) {
			List<String> soStatusList = new ArrayList<String>();
			StringTokenizer strTok = new StringTokenizer(soStatus,
					PublicAPIConstant.SEPARATOR, false);
			int noOfStatuses = strTok.countTokens();
			for (int i = 1; i <= noOfStatuses; i++) {
				soStatusList.add(new String(strTok.nextToken()));
			}
			searchRequestVO.setStatus(soStatusList);
		}
		String customRefs = requestMap.get(PublicAPIConstant.CUSTOM_REFERENCES);
		String startDate = requestMap.get(PublicAPIConstant.CREATED_STARTDATE);
		String endDate = requestMap.get(PublicAPIConstant.CREATED_ENDDATE);
		Date createdStartDate = null;
		Date createdEndDate = null;
		try {
			if (startDate != null) {
				createdStartDate = CommonUtility.sdf3ToDate.parse(startDate);
			}
			if (endDate != null) {
				createdEndDate = CommonUtility.sdf3ToDate.parse(endDate);
			}
			// setting the createdStartDate and createEndDate
			searchRequestVO.setCreateStartDate(new Timestamp(createdStartDate
					.getTime()));
			searchRequestVO.setCreateEndDate(new Timestamp(createdEndDate
					.getTime()));
		} catch (Exception e) {
			logger.error("Exception Occurred while setting Service "
					+ "Start Time");
		}
		
		if (null != customRefs) {
			List <ServiceOrderCustomRefVO> customerRefList = new 
				ArrayList<ServiceOrderCustomRefVO>();
			StringTokenizer strTok = new StringTokenizer(customRefs, 
					PublicAPIConstant.CUSTOM_REFERENCES_PAIR_SEPERATOR, false);
			int noOfCustRefs = strTok.countTokens();
			for (int i = 1; i <= noOfCustRefs; i++) {
				String keyValuePair = strTok.nextToken();
				StringTokenizer keyOrValue = new StringTokenizer(
						keyValuePair,
						PublicAPIConstant.CUSTOM_REFERENCES_KEY_VALUE_SEPERATOR,
						false);

				ServiceOrderCustomRefVO custRef = new ServiceOrderCustomRefVO();
				custRef.setRefType(keyOrValue.nextToken());
				custRef.setRefValue(keyOrValue.nextToken());
				customerRefList.add(custRef);
			}
			searchRequestVO.setCustomRefs(customerRefList);
		}

		logger.info("Leaving SOSearchByProviderMapper.mapSoSearchRequest()");
		return searchRequestVO;
	}
}
