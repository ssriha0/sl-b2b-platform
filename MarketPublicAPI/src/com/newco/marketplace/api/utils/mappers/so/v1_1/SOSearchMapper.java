/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 26-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.beans.so.search.SearchResults;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.SearchRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.DataException;

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
		logger.info("Inside mapResponseSearch--->Starts");
		SOSearchResponse soSearchResponse = new SOSearchResponse();
		SearchResults searchResults = new SearchResults();
		Results results = new Results();
		List<OrderStatus> orderStatusList = new ArrayList<OrderStatus>();
		logger.info("Setting searchResultList values to list of OrderStatus" +
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
				if (null != searchResultVO.getSowTitle()) {
					orderStatus.setTitle(searchResultVO.getSowTitle());
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
		} else {
			logger.info("Setting result and message as Failure when the Search" +
					" result list is empty");
			results = Results.getError(ResultsCode.SEARCH_UNSUCCESSFUL.getMessage(), 
					ResultsCode.SEARCH_UNSUCCESSFUL.getCode());
			
		}		
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
	/**
	 * This method is for mapping Mapping list of ServiceOrderSearchResultsVO to
	 * SoSearchResponse Object.
	 * 
	 * @param requestMap
	 * @param resourceId
	 * @param securityContext	
	 * @return SearchRequestVO
	 */
	public SearchRequestVO mapSoSearchRequest(Map<String,String> requestMap, 
			SecurityContext securityContext){
		logger.info("Entering SOSearchMapper.mapSoSearchRequest()");
		SearchRequestVO searchRequestVO = new SearchRequestVO();
		searchRequestVO.setSearchFilter(requestMap
				.get(PublicAPIConstant.SEARCH_FILTER));
		
		searchRequestVO.setBuyerId(securityContext.getCompanyId());
		searchRequestVO.setRoleId(securityContext.getRoleId());
		//searchRequestVO.setVendorResouceId(securityContext.getVendBuyerResId());
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
			List <String> soStatusList = new 
				ArrayList<String>();
			StringTokenizer strTok = new StringTokenizer(soStatus, 
					PublicAPIConstant.SEPARATOR, false);
			int noOfStatuses = strTok.countTokens();
			for (int i = 1; i <= noOfStatuses; i++) {
				soStatusList.add(new String(strTok
						.nextToken()));
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
			if(null!=createdStartDate){
				searchRequestVO.setCreateStartDate(new Timestamp(createdStartDate
					.getTime()));
			}
			if(null!=createdEndDate){
				searchRequestVO.setCreateEndDate(new Timestamp(createdEndDate
					.getTime()));
			}
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
		
		logger.info("Leaving SOSearchMapper.mapSoSearchRequest()");
		return searchRequestVO;
	}
	
	public SearchCriteria mapSavedFilterToSearchCriteria(SearchFilterVO filterVO,SearchCriteria searchCriteria ){
		String criteria = filterVO.getTemplateValue();
		//removing the { and } from start and end of criteria		
		String formattedCriteria = criteria.substring(1, criteria.length()-1);
		criteria = formattedCriteria;
		
		StringTokenizer criteriaToken = new StringTokenizer(criteria, ",");
		int noOfTokens = criteriaToken.countTokens();

		String token = "";
		for (int i = 1; i <= noOfTokens; i++) {		
			token = criteriaToken.nextToken();
			StringTokenizer eachCriteria = new StringTokenizer(token, "=");
			
			String criteriaName = eachCriteria.nextToken();
			if(StringUtils.isNotBlank(criteriaName)){
				criteriaName = criteriaName.trim();
				if(eachCriteria.hasMoreTokens()){
					String criteriaVal = eachCriteria.nextToken();
					if(PublicAPIConstant.SEARCH_FILTER_STATE_LIST.equals(criteriaName)){
						List<String> selectedStates = new ArrayList<String>();
						StringTokenizer stateVals = new StringTokenizer(criteriaVal, "|");
						while(stateVals.hasMoreTokens()){
							selectedStates.add(stateVals.nextToken());
						}
						searchCriteria.setSelectedStates(selectedStates);
					}else if(PublicAPIConstant.SEARCH_FILTER_SKILL_LIST.equals(criteriaName)){
						List<String> selectedSkills = new ArrayList<String>();
						StringTokenizer skillVals = new StringTokenizer(criteriaVal, "|");
						while(skillVals.hasMoreTokens()){
							selectedSkills.add(skillVals.nextToken());
						}
						searchCriteria.setSelectedSkills(selectedSkills);
					}else if(PublicAPIConstant.SEARCH_FILTER_MARKET_LIST.equals(criteriaName)){
						List<Integer> selectedMarkets = new ArrayList<Integer>();
						StringTokenizer marketVals = new StringTokenizer(criteriaVal, "|");
						while(marketVals.hasMoreTokens()){
							selectedMarkets.add(Integer.parseInt(marketVals.nextToken()));
						}
						searchCriteria.setSelectedMarkets(selectedMarkets);
					}
					else if(PublicAPIConstant.SEARCH_FILTER_CHECKNUMBER_LIST.equals(criteriaName)){
						List<String> selectedCheckNum = new ArrayList<String>();
						StringTokenizer checkNumVals = new StringTokenizer(criteriaVal, "|");
						while(checkNumVals.hasMoreTokens()){
							selectedCheckNum.add(checkNumVals.nextToken());
						}
						searchCriteria.setSelectedCheckNumbers(selectedCheckNum);
					}else if(PublicAPIConstant.SEARCH_FILTER_CUSTOMER_NAME_LIST.equals(criteriaName)){
						List<String> selectedCustName = new ArrayList<String>();
						StringTokenizer custNameVals = new StringTokenizer(criteriaVal, "|");
						while(custNameVals.hasMoreTokens()){
							selectedCustName.add(custNameVals.nextToken());
						}
						searchCriteria.setSelectedCustomerNames(selectedCustName);
					}else if(PublicAPIConstant.SEARCH_FILTER_PHONE_LIST.equals(criteriaName)){
						List<String> selectedPhones = new ArrayList<String>();
						StringTokenizer phoneVals = new StringTokenizer(criteriaVal, "|");
						while(phoneVals.hasMoreTokens()){
							selectedPhones.add(phoneVals.nextToken());
						}
						searchCriteria.setSelectedPhones(selectedPhones);
					}else if(PublicAPIConstant.SEARCH_FILTER_PROVIDER_FIRM_ID_LIST.equals(criteriaName)){
						List<String> selectedProFirmIds = new ArrayList<String>();
						StringTokenizer proFirmIdVals = new StringTokenizer(criteriaVal, "|");
						while(proFirmIdVals.hasMoreTokens()){
							selectedProFirmIds.add(proFirmIdVals.nextToken());
						}
						searchCriteria.setSelectedProviderFirmIds(selectedProFirmIds);
					}else if(PublicAPIConstant.SEARCH_FILTER_SO_ID_LIST.equals(criteriaName)){
						List<String> selectedSoIds = new ArrayList<String>();
						StringTokenizer soIdVals = new StringTokenizer(criteriaVal, "|");
						while(soIdVals.hasMoreTokens()){
							selectedSoIds.add(soIdVals.nextToken());
						}
						searchCriteria.setSelectedServiceOrderIds(selectedSoIds);
					}else if(PublicAPIConstant.SEARCH_FILTER_SERVICEPRO_ID_LIST.equals(criteriaName)){
						List<String> selectedServiceProIds = new ArrayList<String>();
						StringTokenizer serviceProIdVals = new StringTokenizer(criteriaVal, "|");
						while(serviceProIdVals.hasMoreTokens()){
							selectedServiceProIds.add(serviceProIdVals.nextToken());
						}
						searchCriteria.setSelectedServiceProIds(selectedServiceProIds);
					}else if(PublicAPIConstant.SEARCH_FILTER_SERVICEPRO_NAME_LIST.equals(criteriaName)){
						List<String> selectedServiceProNames = new ArrayList<String>();
						StringTokenizer serviceProNameVals = new StringTokenizer(criteriaVal, "|");
						while(serviceProNameVals.hasMoreTokens()){
							selectedServiceProNames.add(serviceProNameVals.nextToken());
						}
						searchCriteria.setSelectedServiceProNames(selectedServiceProNames);
					}else if(PublicAPIConstant.SEARCH_FILTER_ZIP_CODE_LIST.equals(criteriaName)){
						List<String> selectedZipCodes = new ArrayList<String>();
						StringTokenizer zipVals = new StringTokenizer(criteriaVal, "|");
						while(zipVals.hasMoreTokens()){
							selectedZipCodes.add(zipVals.nextToken());
						}
						searchCriteria.setSelectedZipCodes(selectedZipCodes);
					}else if(PublicAPIConstant.SEARCH_FILTER_START_DATE_LIST.equals(criteriaName)){
						List<String> selectedStartDates = new ArrayList<String>();
						StringTokenizer startDateVals = new StringTokenizer(criteriaVal, "|");
						while(startDateVals.hasMoreTokens()){
							selectedStartDates.add(startDateVals.nextToken());
						}
						searchCriteria.setStartDateList(selectedStartDates);
					}else if(PublicAPIConstant.SEARCH_FILTER_END_DATE_LIST.equals(criteriaName)){
						List<String> selectedEndDates = new ArrayList<String>();
						StringTokenizer endDateVals = new StringTokenizer(criteriaVal, "|");
						while(endDateVals.hasMoreTokens()){
							selectedEndDates.add(endDateVals.nextToken());
						}
						searchCriteria.setEndDateList(selectedEndDates);
					}else if(PublicAPIConstant.SEARCH_FILTER_MAIN_CATEGORY_LIST.equals(criteriaName)){
						List<String> selectedMainCats = new ArrayList<String>();
						StringTokenizer mainCatVals = new StringTokenizer(criteriaVal, "|");
						while(mainCatVals.hasMoreTokens()){
							selectedMainCats.add(mainCatVals.nextToken());
						}
						searchCriteria.setSelectedMainCatIdList(selectedMainCats);
					}else if(PublicAPIConstant.SEARCH_FILTER_CATEGORY_LIST.equals(criteriaName)){
						List<String> selectedCatIds = new ArrayList<String>();
						StringTokenizer catIdVals = new StringTokenizer(criteriaVal, "|");
						while(catIdVals.hasMoreTokens()){
							selectedCatIds.add(catIdVals.nextToken());
						}
						searchCriteria.setSelectedCatAndSubCatIdList(selectedCatIds);
					}
					else if(PublicAPIConstant.SEARCH_FILTER_STATUS_LIST.equals(criteriaName)){
						//criteriaVal.replace("{", "");
						//criteriaVal.replace("}", "");
						StringTokenizer statusVals = new StringTokenizer(criteriaVal, "#");
						Map<String,String> statusMap = new HashMap<String,String>();
						int noOfStatus = statusVals.countTokens();
						for (int k = 1; k <= noOfStatus; k++) {
							String keyValuePair = statusVals.nextToken();
							StringTokenizer keyOrValue = new StringTokenizer(keyValuePair,":");
							if(null != keyOrValue && keyOrValue.countTokens() > 1){
								statusMap.put(keyOrValue.nextToken(), keyOrValue.nextToken());
							}else{
								statusMap.put(keyOrValue.nextToken(),"0"); 
							}				
						}
						searchCriteria.setSelectedStatusSubStatus(statusMap);
					}else if(PublicAPIConstant.SEARCH_FILTER_CUST_REF_LIST.equals(criteriaName)){
						criteriaVal.replace("{", "");
						criteriaVal.replace("}", "");
						StringTokenizer custRefVals = new StringTokenizer(criteriaVal, "|");
						Map<String,ArrayList<String>> custRefMap = new HashMap<String,ArrayList<String>>();
						ArrayList<String> tempList = null;
						int noOfStatus = custRefVals.countTokens();
						for (int j = 1; j <= noOfStatus; j++) {
							String keyValuePair = custRefVals.nextToken();
							StringTokenizer keyOrValue = new StringTokenizer(keyValuePair,"#");
							if(null != keyOrValue && keyOrValue.countTokens() > 1){
								
								//Priority 18
								String key=keyOrValue.nextToken();
								if (custRefMap.containsKey(key)) {
								      tempList = custRefMap.get(key);
								      if(tempList == null)
								      {
								    	  tempList = new ArrayList<String>();
								      }
								      tempList.add(keyOrValue.nextToken());  
								   } else {
								      tempList = new ArrayList<String>();
								      tempList.add(keyOrValue.nextToken());               
								   }
								
								custRefMap.put(key, tempList);
							}else{
								custRefMap.put(keyOrValue.nextToken(),new ArrayList<String>());
							}				
						}
						searchCriteria.setSelectedCustomRefs(custRefMap);
					}
				}
			}
		}
		searchCriteria.setSearchType("11");
		return searchCriteria;
	}
	public List<ServiceOrder> mapToServiceOrder(List<ServiceOrderSearchResultsVO> serviceOrderResult)
	{
		List<ServiceOrder> soList=new ArrayList<ServiceOrder>();
		
		for (ServiceOrderSearchResultsVO so :serviceOrderResult) {
			ServiceOrder serviceOrder=new ServiceOrder();
			serviceOrder.setSoId(so.getSoId());
			serviceOrder.setCreatedDate(so.getCreatedDate());
			serviceOrder.setRoutedDate(so.getRoutedDate());
			serviceOrder.setAcceptedDate(so.getAcceptedDate());
			serviceOrder.setCompletedDate(so.getCompletedDate());
			serviceOrder.setClosedDate(so.getClosedDate());
			serviceOrder.setActivatedDate(so.getActivatedDate());
			serviceOrder.setSowTitle(so.getSoTitle());
			serviceOrder.setStatus(so.getSoStatusString());
			serviceOrder.setSubStatus(so.getSoSubStatusString());
			serviceOrder.setPriceModel(so.getPriceModel());
			soList.add(serviceOrder);
				}
		
		return soList;
	}
}
