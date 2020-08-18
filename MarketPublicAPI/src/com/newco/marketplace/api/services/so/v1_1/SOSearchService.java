/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-Oct-2009	KMSTRSUP   Infosys				1.0
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
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOSearchMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.SearchRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * This class is a service class for searching Service Orders
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOSearchService extends BaseService{
	private Logger logger = Logger.getLogger(SOSearchService.class);
	
	private SOSearchMapper searchMapper;	
	private IServiceOrderBO serviceOrderBO;
	private ServiceOrderSearchBO serviceOrderSearchBO;
	public SOSearchService() {
		super(null, PublicAPIConstant.SEARCHRESPONSE_XSD,
				PublicAPIConstant.SEARCHRESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SEARCH_SCHEMALOCATION,
				null, SOSearchResponse.class);
		addRequiredGetParam(PublicAPIConstant.PAGE_SIZE_SET, DataTypes.INTEGER);
	}
	/**
	 * This method is for searching service orders.
	 * 
	 * @param APIRequestVO	
	 * @return IAPIResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of SOSearchService");
		SOSearchResponse soSearchResponse = new SOSearchResponse();
		Map<String,String> requestMap = apiVO.getRequestFromGetDelete();
		SecurityContext securityContext = null;
		
		int buyerId = apiVO.getBuyerIdInteger();
		
			try {
				securityContext = getSecurityContextForBuyerAdmin(buyerId);
			} catch (Exception exception) {
				logger.error("Exception occurred while accessing security " +
						"context using resourceId");
			}
			
				if(!validate(requestMap)){
					logger.error("Validation Error");
					String errorField = requestMap.get(
							PublicAPIConstant.ERROR_FIELD);
					Results results = null;
					if(PublicAPIConstant.ZIP.equals(errorField)){
						results = Results.getError(ResultsCode.
								SEARCH_VALIDATION_ZIP_ERROR_CODE.getMessage(),
								ResultsCode.
								SEARCH_VALIDATION_ZIP_ERROR_CODE.getCode());
					}else if(
							PublicAPIConstant.PAGE_SIZE_SET.equals(
									errorField))						
					
					{
						results = Results.getError(ResultsCode.
								SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE.
								getMessage(),
								ResultsCode.
								SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE.
								getCode());
					}
					else if(
							PublicAPIConstant.SO_STATUS_SET.equals(
									errorField))						
					
					{
						results = Results.getError(ResultsCode.
								SEARCH_VALIDATION_SOSTATUS_SET_ERROR_CODE.
								getMessage(),
								ResultsCode.
								SEARCH_VALIDATION_SOSTATUS_SET_ERROR_CODE.
								getCode());
					}	
					
					soSearchResponse.setResults(results);
					soSearchResponse.setVersion(
							PublicAPIConstant.SEARCH_VERSION);
					soSearchResponse
							.setSchemaLocation(
									PublicAPIConstant.SEARCH_SCHEMALOCATION);
					soSearchResponse
							.setNamespace(
									PublicAPIConstant.SEARCHRESPONSE_NAMESPACE);
					soSearchResponse.setSchemaInstance(
							PublicAPIConstant.SCHEMA_INSTANCE);
					return soSearchResponse;
				}
				SearchRequestVO searchRequestVO = new SearchRequestVO();
				
				searchRequestVO = searchMapper.mapSoSearchRequest(
						requestMap,securityContext);
				List<ServiceOrder> serviceOrderSearchResults = new 
					ArrayList<ServiceOrder>();
				List<ServiceOrder> serviceOrderSearchResultsOnRequestParams = new 
				    ArrayList<ServiceOrder>();
				SearchFilterVO searchFilterVO=new SearchFilterVO();
				if(null==searchRequestVO.getSearchFilter())
				{
				    serviceOrderSearchResults = serviceOrderBO
						    .getSearchResultSetPaged(searchRequestVO);			
				}
				else
				{
					try{int pageSizeMax = PublicAPIConstant.PAGE_SIZE_SET_500;
					    searchRequestVO.setPageNumber(PublicAPIConstant.INTEGER_ONE);
					    searchRequestVO.setPageSize(pageSizeMax);
					    searchRequestVO
							.setPageLimit((searchRequestVO.getPageSize() * (searchRequestVO
									.getPageNumber() - 1)) );
						serviceOrderSearchResultsOnRequestParams = serviceOrderBO
						        .getSearchResultSetPaged(searchRequestVO);
					    List<String> soIdsOnRequestParams = new ArrayList<String>();
					    List<String> soIdsOnFilter = new ArrayList<String>();
					    int soSearchResultsOnRequestParamsLength = serviceOrderSearchResultsOnRequestParams.size();
					    for(int index=0; index<soSearchResultsOnRequestParamsLength; index++){
					    	ServiceOrder serviceorder = serviceOrderSearchResultsOnRequestParams.get(index);
					    	String soId = serviceorder.getSoId();
					    	soIdsOnRequestParams.add(soId);
					    }
					    searchFilterVO.setEntityId(buyerId);
					    searchFilterVO.setFilterName(searchRequestVO.getSearchFilter());
						searchFilterVO=serviceOrderSearchBO.getSelectedSearchFilter(searchFilterVO);
						SearchCriteria searchCriteria=new SearchCriteria();
						searchCriteria=searchMapper.mapSavedFilterToSearchCriteria(searchFilterVO, searchCriteria);
						searchCriteria.setPageNumber(searchRequestVO.getPageNumber());
						searchCriteria.setPageSize(searchRequestVO.getPageSize());
						searchCriteria.setPageLimit(searchRequestVO.getPageLimit());
						CriteriaMap newMap=new CriteriaMap();
						newMap=mapCriteria(newMap,searchCriteria,securityContext);
						ProcessResponse soIds = serviceOrderSearchBO.retrieveSOSearchResultsIds(newMap);
						soIdsOnFilter = (List<String>)soIds.getObj();
						List<String> soIdsResult = filterServiceOrders(soIdsOnRequestParams, soIdsOnFilter);
						Integer pageSize = Integer.parseInt(requestMap
								.get(PublicAPIConstant.PAGE_SIZE_SET));
						searchRequestVO.setPageNumber(Integer.parseInt(requestMap
								.get(PublicAPIConstant.PAGE_NUMBER)));
						searchRequestVO.setPageSize(pageSize.intValue());
						searchRequestVO
								.setPageLimit((searchRequestVO.getPageSize() * (searchRequestVO
										.getPageNumber() - 1)) );
						searchCriteria.setPageNumber(searchRequestVO.getPageNumber());
						searchCriteria.setPageSize(searchRequestVO.getPageSize());
						searchCriteria.setPageLimit(searchRequestVO.getPageLimit());
						searchCriteria.setSoIds(soIdsResult);
						newMap.put(PublicAPIConstant.SEARCH_CRITERIA_KEY, searchCriteria);
						ProcessResponse response = serviceOrderSearchBO.retrievePagedSOSearchResults(newMap);
						ServiceOrderMonitorResultVO serviceOrderMonitorResultVO = (ServiceOrderMonitorResultVO) response.getObj();
						serviceOrderSearchResults=searchMapper.mapToServiceOrder(serviceOrderMonitorResultVO.getServiceOrderResults());
					}
					catch (Exception exception) {
						logger.error("Exception occurred  " + exception);
					}
				}
				soSearchResponse = searchMapper
						.mapResponseSearch(serviceOrderSearchResults);				
			
					
		logger.info("Leaving execute method of SOSearchService");
		return soSearchResponse;
	}
	
	public List<String> filterServiceOrders(List<String> soIdsOnRequestParams, List<String> soIdsOnFilter){
		List<String> soIdsResult = new ArrayList<String>();
		int soIdsOnFilterLength = soIdsOnFilter.size();
		int soIdsOnRequestParamsLength = soIdsOnRequestParams.size();
		for(int index1=0; index1<soIdsOnFilterLength; index1++){
			for(int index2=0; index2<soIdsOnRequestParamsLength; index2++){
				if(soIdsOnRequestParams.get(index2).equals(soIdsOnFilter.get(index1))){
					soIdsResult.add(soIdsOnRequestParams.get(index2));
				}
				
		    }
		}
		return soIdsResult;
	}
	
	private CriteriaMap mapCriteria(CriteriaMap newMap,SearchCriteria searchCriteria,
			SecurityContext securityContext)
	{
		OrderCriteria orderCriteria=new OrderCriteria();
		FilterCriteria filterCriteria=new FilterCriteria();
		PagingCriteria pagingCriteria=new PagingCriteria();
		orderCriteria.setCompanyId(securityContext.getCompanyId());
		orderCriteria.setRoleId(securityContext.getRoleId());
		orderCriteria.setRoleType(securityContext.getRole());
		orderCriteria.setVendBuyerResId(securityContext.getVendBuyerResId());
		SortCriteria sortCriteria=new SortCriteria();
		sortCriteria.setSortColumnName("s.created_date");
		sortCriteria.setSortOrder("desc");
		newMap.put(PublicAPIConstant.SEARCH_CRITERIA_KEY, searchCriteria);
		newMap.put(PublicAPIConstant.ORDER_CRITERIA_KEY,orderCriteria);
		newMap.put(PublicAPIConstant.SORT_CRITERIA_KEY,sortCriteria);
		newMap.put(PublicAPIConstant.FILTER_CRITERIA_KEY,filterCriteria);
		newMap.put(PublicAPIConstant.PAGING_CRITERIA_KEY,pagingCriteria);
		newMap.put("buyerID",securityContext.getCompanyId());
		return newMap;
	}
	/**
	 * This method is used for validating zip and max result set values.
	 * 
	 * @param requestMap	
	 * @return boolean
	 */
	private boolean validate(Map<String,String> requestMap){
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
		Integer pageSize = Integer.parseInt(requestMap.get(
				PublicAPIConstant.PAGE_SIZE_SET));
		
		if(null!=zip&&zip.length() != 5){
			requestMap.put(PublicAPIConstant.ERROR_FIELD, 
					PublicAPIConstant.ZIP);
			return result;	
		}
		List<Integer> pageSizeSetValues = Arrays.asList(
				PublicAPIConstant.PAGE_SIZE_SET_10,
				PublicAPIConstant.PAGE_SIZE_SET_20,
				PublicAPIConstant.PAGE_SIZE_SET_50,
				PublicAPIConstant.PAGE_SIZE_SET_100,
				PublicAPIConstant.PAGE_SIZE_SET_200,
				PublicAPIConstant.PAGE_SIZE_SET_500);		
		if(!pageSizeSetValues.contains(pageSize)){
			requestMap.put(PublicAPIConstant.ERROR_FIELD, 
					PublicAPIConstant.PAGE_SIZE_SET);
			return result;	
		}	
		
		List<String> soStatusValues = Arrays.asList(
				PublicAPIConstant.SOSTATUS_SET_POSTED,
				PublicAPIConstant.SOSTATUS_SET_ACCEPTED,
				PublicAPIConstant.SOSTATUS_SET_ACTIVE,
				PublicAPIConstant.SOSTATUS_SET_CLOSED,
				PublicAPIConstant.SOSTATUS_SET_COMPLETED,
				PublicAPIConstant.SOSTATUS_SET_DRAFTED,
				PublicAPIConstant.SOSTATUS_SET_PROBLEM,
				PublicAPIConstant.SOSTATUS_SET_EXPIRED,
				PublicAPIConstant.SOSTATUS_SET_PENDINGCANCEL
		);		
		if(!soStatusValues.containsAll(soStatusList)){
			requestMap.put(PublicAPIConstant.ERROR_FIELD, 
					PublicAPIConstant.SO_STATUS_SET);
			return result;	
		}	
		result = true;
		return result;
	}
	public void setSearchMapper(SOSearchMapper searchMapper) {
		this.searchMapper = searchMapper;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
	public void setServiceOrderSearchBO(ServiceOrderSearchBO serviceOrderSearchBO) {
		this.serviceOrderSearchBO = serviceOrderSearchBO;
	}
}