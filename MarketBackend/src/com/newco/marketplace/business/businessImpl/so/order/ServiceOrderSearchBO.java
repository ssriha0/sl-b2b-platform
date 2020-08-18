package com.newco.marketplace.business.businessImpl.so.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.ABaseCriteriaHandler;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.buyer.BuyerSubstatusAssocVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBSearchVO;
import com.newco.marketplace.dto.vo.provider.AvailableTimeSlotVO;
import com.newco.marketplace.dto.vo.provider.SearchFirmsResponseVO;
import com.newco.marketplace.dto.vo.provider.SearchFirmsVO;
import com.newco.marketplace.dto.vo.provider.ServiceOfferingPriceVO;
import com.newco.marketplace.dto.vo.provider.ServiceOfferingsVO;
import com.newco.marketplace.dto.vo.serviceOfferings.CodeDetails;
import com.newco.marketplace.dto.vo.serviceOfferings.Result;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.serviceOrderTabsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientHandlerException;
//import com.sun.jersey.api.client.WebResource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 * 
 * $Revision: 1.47 $ $Author: akashya $ $Date: 2008/05/21 22:54:21 $
 */

/*
 * Maintenance History: See bottom of file
 */

public class ServiceOrderSearchBO extends BaseOrderBO implements
		IServiceOrderSearchBO {

	private static final Logger LOGGER = Logger.getLogger(ServiceOrderSearchBO.class.getName());
	private static final String BUYER = "Buyer";
	private static final String PROVIDER = "Provider";
    private static final char removeApostrophe='\'';
	private IOrderGroupDao orderGroupDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO#retrieveSOSearchResults(com.newco.marketplace.dto.vo.serviceorder.CriteriaMap)
	 */
	public ProcessResponse retrieveSOSearchResultsIds(CriteriaMap criteriaMap)
			throws DataServiceException {

		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		ProcessResponse processResp = new ProcessResponse();
		String searchType = null;
		String searchValue = null;
		List<String> ids = null;
		
		boolean isBuyer = false;
		if (null != criteriaMap) {
			if(criteriaMap.get("IS_BUYER")!=null)
			{
				isBuyer = (Boolean)criteriaMap.get("IS_BUYER");
				criteriaMap.remove("IS_BUYER");
			}
			criteriaHandler.doCommonQueryInit(criteriaMap,Boolean.FALSE);
		}
		SortCriteria sortCriteria = (SortCriteria) criteriaMap
				.get(OrderConstants.SORT_CRITERIA_KEY);
		OrderCriteria orderCriteria = (OrderCriteria) criteriaMap
				.get(OrderConstants.ORDER_CRITERIA_KEY);
		SearchCriteria searchCriteria = (SearchCriteria) criteriaMap
				.get(OrderConstants.SEARCH_CRITERIA_KEY);
		FilterCriteria filterCriteria = (FilterCriteria) criteriaMap
				.get(OrderConstants.FILTER_CRITERIA_KEY);
		PagingCriteria pagingCriteria = (PagingCriteria) criteriaMap.get(OrderConstants.PAGING_CRITERIA_KEY);
		searchType = searchCriteria.getSearchType();
		if(searchCriteria.getSearchValue() != null && StringUtils.isNotEmpty(searchCriteria.getSearchValue())){
			searchValue = searchCriteria.getSearchValue().trim();
		}else{
			searchValue = searchCriteria.getSearchValue();
		}
		ServiceOrderSearchResultsVO soSearchVO = new ServiceOrderSearchResultsVO();	

		setFilterCriteriaAndSearchVO(filterCriteria, soSearchVO);
		
		//searchType = "11";
		boolean	searchTypeNumeric = NumberUtils.isNumber(searchType);
		try {
/*01-25-10		ValidatorResponse validatorResp = new ValidatorResponse(VALID_RC, VALID_RC);
			if (searchTypeNumeric){
		    	validatorResp = new SOSearchValidator().validate(
						Integer.parseInt(searchType), searchValue);
				if (Integer.parseInt(searchType) == 3)
				{
					searchValue = addHyphensToSoId(searchValue);
				}

			}
			if (validatorResp.isError()) {
				setProcessRespFromValidatorResp(processResp, validatorResp);
				return processResp;
			} 
*/
			soSearchVO.setSearchByValue(searchValue);
			soSearchVO.setSortColumnName(sortCriteria.getSortColumnName());
			soSearchVO.setSortOrder(sortCriteria.getSortOrder());
			soSearchVO.setRoleId(orderCriteria.getRoleId());
			soSearchVO.setManageSOFlag(filterCriteria.isManageSOFlag());
			
			//Code for Advance Search Filter
			soSearchVO = setAdvanceSearchCriteria(searchCriteria, soSearchVO);
			
		
			PBSearchVO pbsearchVO = new PBSearchVO();
//01-25-10	if (searchTypeNumeric && Integer.parseInt(searchType) == OrderConstants.SEARCH_BY_PB_FILTER_ID) {	
			if (searchTypeNumeric) {			
			/*  validatorResp=new  SOSearchValidator().validatePbFilterId(filterCriteria.getPbfilterId());
			
			   if (validatorResp.isError()) {
					setProcessRespFromValidatorResp(processResp, validatorResp);
					return processResp;
				} else {
			*/		pbsearchVO.setBuyerId((orderCriteria.getCompanyId()));
					pbsearchVO.setFilterId(filterCriteria.getPbfilterId());
					pbsearchVO.setFilterOpt(filterCriteria.getPbfilterOpt());
					pbsearchVO.setStartIndex(pagingCriteria.getStartIndex());
					pbsearchVO.setEndIndex(pagingCriteria.getEndIndex());
					pbsearchVO.setSortColumnName(sortCriteria.getSortColumnName());
					pbsearchVO.setSortOrder(sortCriteria.getSortOrder());
					pbsearchVO.setSoStatus(soSearchVO.getSoStatus());
					pbsearchVO.setSoSubStatus(soSearchVO.getSoSubStatus());
					String searchBuyerId = null;
					if(filterCriteria.getBuyerRefTypeId()!=null && filterCriteria.getBuyerRefTypeId() == 2 && filterCriteria.getSearchByBuyerId()!=null && filterCriteria.getSearchByBuyerId().equals("true")){
						pbsearchVO.setSearchBuyerId(filterCriteria.getBuyerRefValue());
						pbsearchVO.setBuyerRefTypeId(null);
						pbsearchVO.setBuyerRefValue(null);	
					}
					else{
					pbsearchVO.setBuyerRefTypeId(filterCriteria.getBuyerRefTypeId());
					pbsearchVO.setBuyerRefValue(filterCriteria.getBuyerRefValue());	
					}
					//if the flow is from the sod of wfm.
					if(filterCriteria.getWfm_sodFlag()!=null&& (filterCriteria.getWfm_sodFlag().equals("Provider")||filterCriteria.getWfm_sodFlag().equals("Firm")))
					{
						pbsearchVO.setWfm_sodFlag(filterCriteria.getWfm_sodFlag());
						pbsearchVO.setSoId(filterCriteria.getSoId());
					}
					pbsearchVO.setBuyerInd(isBuyer);
			//	}
			}
			
			setRoleType(orderCriteria, soSearchVO); 
			if (searchTypeNumeric){
				switch (Integer.parseInt(searchType)) {
				case 1:
					ids = getSoSearchDAO().getServiceOrderByPhoneNumber(soSearchVO);
					break;
				case 2:
					ids = getSoSearchDAO().getServiceOrderByZipCode(soSearchVO);
					break;
				case 3:
					ids = getSoSearchDAO().getServiceOrderBySoID(soSearchVO);
					break;
				case 4:
					ids = getSoSearchDAO().getServiceOrderByEndUserName(soSearchVO);
					break;
				case 5:
					ids = getSoSearchDAO().getServiceOrderByTechnicianID(soSearchVO);
					break;
				case 6:
					soSearchVO.setSearchByValue(searchValue);
					ids = getSoSearchDAO().getServiceOrderByTechnicianName(soSearchVO);
					break;
				case 7:
					ids = getPowerBuyerDAO().getServiceOrderByPBFilterId(pbsearchVO);
					break;
				case 8:
					ids = getSoSearchDAO().getServiceOrderByProviderFirmID(soSearchVO);
					break;
				case 9:
					ids = getSoSearchDAO().getServiceOrderByAddress(soSearchVO);
					break;				
				case 10:
					ids = getSoSearchDAO().getServiceOrderByCheckNumber(soSearchVO);	
					break;
				case 11:
					ids = getSoSearchDAO().getServiceOrderByAdvanceSearch(soSearchVO);	
					break;
				case 12:
					ids = getPowerBuyerDAO().getActiveServiceOrderByProvider(pbsearchVO);
					break;
				case 13:
					ids =decideIfAutocloseAlone(soSearchVO,searchCriteria);
					break;
				default:
					break;
				}
			}else{
				if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotBlank(soSearchVO.getSearchByValue())) {
					// REPLACE ALL NON DIGITS WITH NOTHING
					soSearchVO.setSearchByType( searchType.replaceAll("\\D", "") );
					// Formatting the buyer reference UNIT NUMBER with preceding zeros 
					if(OrderConstants.BUYER_REFERENCE_ID_ONE.equals(soSearchVO.getSearchByType())){
						String searchValueFormatted = soSearchVO.getSearchByValue();
						searchValueFormatted = OrderConstants.BUYER_REFERENCE_UNIT_NUMBER_FORMAT + searchValueFormatted;
						searchValueFormatted = searchValueFormatted.substring(searchValueFormatted.length() - OrderConstants.BUYER_REFERENCE_UNIT_NUMBER_LENGTH);
						soSearchVO.setSearchByValue(searchValueFormatted);
					}
					ids = getSoSearchDAO().getServiceOrderIDsByCustomRefID(soSearchVO);
				}
			}
		} catch (Exception e) {
			logger.error("service order search error:  " + e.getMessage(), e);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(e.getMessage());
		}
		processResp.setCode(VALID_RC);
		processResp.setSubCode(VALID_RC);
		processResp.setObj(ids);
		return processResp;
	}
	
	private List<String> decideIfAutocloseAlone(ServiceOrderSearchResultsVO soSearchVO,SearchCriteria searchCriteria){
		
		List<String> ids = new ArrayList<String>();
		List<String> advanceIds=new ArrayList<String>();
		List<String> result=new ArrayList<String>();
		soSearchVO.setAutocloseRuleList(searchCriteria.getAutocloseRuleList());
		try
		{
		if(soSearchVO.getAutocloseAlone()!=null && soSearchVO.getAutocloseAlone()==1){
			result=getSoSearchDAO().getAutocloseFailedByRule(soSearchVO);
		}
		else if(soSearchVO.getAutocloseAlone()!=null &&soSearchVO.getAutocloseAlone()==0 && searchCriteria.getAutocloseRuleList().size()>0){
			ids=getSoSearchDAO().getAutocloseFailedByRule(soSearchVO);
			advanceIds = getSoSearchDAO().getServiceOrderByAdvanceSearch(soSearchVO);
			result=ids;
			result.retainAll(advanceIds);
		
		}
		}
		catch(Exception e)
		{
			logger.error("service order search error:  " + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Description: Puts out log message and sets ProcessResponse fields
	 * @param processResp
	 * @param validatorResp
	 */
	private void setProcessRespFromValidatorResp(ProcessResponse processResp,
			ValidatorResponse validatorResp) {
		logger.debug("service order search failed validation, reason: "
				+ validatorResp.getMessages());

		processResp.setCode(USER_ERROR_RC);
		processResp.setSubCode(USER_ERROR_RC);
		processResp.setMessages(validatorResp.getMessages());
	}

	/**
	 * Description: Sets the passed in FilterCriteria and ServiceOrderSearchResultsVO
	 * @param filterCriteria
	 * @param soSearchVO
	 */
	private void setFilterCriteriaAndSearchVO(FilterCriteria filterCriteria,
			ServiceOrderSearchResultsVO soSearchVO) {
		if(filterCriteria != null && filterCriteria.getStatus() != null)
		{
			Integer[] filterCriteriaStatus = filterCriteria.getStatus();
			int filterStatus = filterCriteriaStatus[0];
			String filterSubStatusString = filterCriteria.getSubStatus(); 
			
			if (filterStatus != OrderConstants.SEARCH_STATUS
					&& filterStatus != 0) {
				soSearchVO.setSoStatus(new Integer(filterStatus));
				if (StringUtils.isNotEmpty(filterSubStatusString)
						&& StringUtils.isNumeric(filterSubStatusString)
						&& !filterSubStatusString.equals("0")) {
					soSearchVO.setSoSubStatus(new Integer(filterSubStatusString));
				}
			} 
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO#retrievePagedSOSearchResults(com.newco.marketplace.dto.vo.serviceorder.CriteriaMap)
	 */
	public ProcessResponse retrievePagedSOSearchResults(CriteriaMap criteriaMap)
			throws DataServiceException {

		ProcessResponse processResp = new ProcessResponse();
		List<String> allIds = null;
		List<String> idsToGet = new ArrayList<String>();
		PaginationVO pagination = null;
		ServiceOrderMonitorResultVO serviceOrderMonitorResultVO = new ServiceOrderMonitorResultVO();
		Map<String,Object> paramMap = new HashMap<String, Object>();

		serviceOrderMonitorResultVO
				.setServiceOrderResults(new ArrayList<ServiceOrderSearchResultsVO>());
		

		int totalRecordCount = 0;

		PagingCriteria pagingCriteria = (PagingCriteria) criteriaMap
				.get(OrderConstants.PAGING_CRITERIA_KEY);
		SortCriteria sortCriteria = (SortCriteria) criteriaMap
				.get(OrderConstants.SORT_CRITERIA_KEY);
		SearchCriteria searchCriteria = (SearchCriteria) criteriaMap
				.get(OrderConstants.SEARCH_CRITERIA_KEY);
		OrderCriteria orderCriteria = (OrderCriteria) criteriaMap
				.get(OrderConstants.ORDER_CRITERIA_KEY);
		FilterCriteria filterCriteria = (FilterCriteria) criteriaMap
				.get(OrderConstants.FILTER_CRITERIA_KEY);

		// get paged results
		ServiceOrderSearchResultsVO soSearchVO = new ServiceOrderSearchResultsVO();
		allIds = (List<String>) searchCriteria.getSoIds();

		if ((allIds != null) && !allIds.isEmpty()) {
			totalRecordCount = allIds.size();
			pagination = new ABaseCriteriaHandler()._getPaginationDetail(
					totalRecordCount, pagingCriteria.getPageSize(),
					pagingCriteria.getStartIndex(), pagingCriteria
							.getEndIndex());

			int start = pagination.getStartIndex() - 1;
			int end = pagination.getCurrentPaginet().getEndIndex() - 1;

			for (int i = start; i <= end; i++) {
				idsToGet.add(allIds.get(i));
			}
			
			soSearchVO.setSoIds(idsToGet);
			soSearchVO.setSortColumnName(sortCriteria.getSortColumnName());
			soSearchVO.setSortOrder(sortCriteria.getSortOrder());
			setRoleType(orderCriteria, soSearchVO);
			soSearchVO.setRoleId(orderCriteria.getRoleId());
			soSearchVO.setVendorId(orderCriteria.getCompanyId());
			soSearchVO.setFilterId(filterCriteria.getPbfilterId());
			
			soSearchVO.setManageSOFlag(filterCriteria.isManageSOFlag());
			soSearchVO.setResourceId(filterCriteria.getResourceId());
			
			List<ServiceOrderSearchResultsVO> soSearchList = getSoSearchDAO()
					.getServiceOrderPagedResults(soSearchVO,searchCriteria);
			
			
			//sort the list
			sortSOList(soSearchList, criteriaMap);			
			//limit and page size
			//soSearchList = soSearchList.subList((pagingCriteria.getStartIndex()-1), (pagingCriteria.getEndIndex()-1));
			
			serviceOrderMonitorResultVO.setServiceOrderResults(soSearchList);
			if (null != soSearchList) {
				List<String> soIdList = new ArrayList<String>();
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					if(null != so) {
						
						//SL-21801 to show max price for estimation so
						if(null!=so.getTotalEstimationPrice() && so.getTotalEstimationPrice()>0 ){
						so.setSpendLimit(so.getTotalEstimationPrice());	
						}

						soIdList.add(so.getSoId());
						//R16_0 : SL_20728 : removing html contents in overview section to display the same as normal text in SOM
						so.setSoTitleDesc(ServiceLiveStringUtils.removeHTML(so.getSoTitleDesc()));
						convertGMTToGivenTimeZone(so);
					}
				}
				paramMap.put("soIdList", soIdList);
				List<ServiceOrderSearchResultsVO> soProviderPhoneDetailList = getSoSearchDAO()
				.getServiceOrdersProviderPhones(paramMap);
				
				for(ServiceOrderSearchResultsVO soProviderPhoneDetail : soProviderPhoneDetailList){
					for(ServiceOrderSearchResultsVO so : soSearchList){
						if(so.getSoId().equals(soProviderPhoneDetail.getSoId())){
							so.setProviderPrimaryPhoneNumber(soProviderPhoneDetail.getProviderPrimaryPhoneNumber());
							so.setProviderAltPhoneNumber(soProviderPhoneDetail.getProviderAltPhoneNumber());
							so.setProviderMainPhoneNo(soProviderPhoneDetail.getProviderMainPhoneNo());
							so.setProviderMobilePhoneNo(soProviderPhoneDetail.getProviderMobilePhoneNo());
						}
					}
				}				
				
				boolean isGroupIdPresentInCriteria = isGroupIdPresentInCriteria(criteriaMap);
				if (!isGroupIdPresentInCriteria) {
					// Re-sequence the grouped orders in the list
					boolean insertSiblingsForSearch = true;
					soSearchList = reorderSOListForGroupingAndSorting(soSearchList, criteriaMap, insertSiblingsForSearch);
					serviceOrderMonitorResultVO.setServiceOrderResults(soSearchList);
				}
			}
			serviceOrderMonitorResultVO.setPaginationVO(pagination);
		}

		processResp.setCode(VALID_RC);
		processResp.setSubCode(VALID_RC);
		processResp.setObj(serviceOrderMonitorResultVO);
		return processResp;
	}

//	// NOT USED - referred by getTabData()
//	public List<ServiceOrderSearchResultsVO> findservicebyuser(
//			serviceOrderTabsVO request) throws DataServiceException {
//
//		List<ServiceOrderSearchResultsVO> soSearchList1 = new ArrayList<ServiceOrderSearchResultsVO>();
//		ServiceOrderSearchResultsVO singleSearchResult = new ServiceOrderSearchResultsVO();
//		soSearchList1 = getSoSearchDAO().getSObyWFState(request);
//		int j = soSearchList1.size();
//		for (int i = 0; i < j; i++) {
//			singleSearchResult = getSoSearchDAO().getBuyerUserName(
//					soSearchList1.get(i));
//			soSearchList1.get(i).setBuyerFirstName(
//					singleSearchResult.getBuyerFirstName());
//			soSearchList1.get(i).setBuyerLastName(
//					singleSearchResult.getBuyerLastName());
//			if(soSearchList1.get(i) != null){
//				convertGMTToGivenTimeZone(soSearchList1.get(i));
//			}
//		}
//		return soSearchList1;
//	}

	private void setRoleType(OrderCriteria orderCriteria, ServiceOrderSearchResultsVO soSearchVO) {
		if (StringUtils.equalsIgnoreCase(orderCriteria.getRoleType(), BUYER)) {
			soSearchVO.setRoleType(BUYER);
			soSearchVO.setBuyerID(orderCriteria.getCompanyId());
			soSearchVO.setVendBuyerResourceId(orderCriteria.getVendBuyerResId());
		} else if (StringUtils.equalsIgnoreCase(orderCriteria.getRoleType(), SIMPLE_BUYER)) {
			soSearchVO.setRoleType(SIMPLE_BUYER);
			soSearchVO.setBuyerID(orderCriteria.getCompanyId());
			soSearchVO.setVendBuyerResourceId(orderCriteria.getVendBuyerResId());
		} else if (StringUtils.equalsIgnoreCase(orderCriteria.getRoleType(), PROVIDER)) {
				soSearchVO.setRoleType(PROVIDER);
				soSearchVO.setAcceptedResourceId(orderCriteria.getVendBuyerResId());
				soSearchVO.setVendorId(orderCriteria.getCompanyId());
		} else if (StringUtils.equalsIgnoreCase(orderCriteria.getRoleType(), NEWCO_ADMIN)) {
			soSearchVO.setRoleType(NEWCO_ADMIN);
			Integer adoptedRoleId = orderCriteria.getAdoptedRoleId();
			if (adoptedRoleId != null && adoptedRoleId == OrderConstants.BUYER_ROLEID) {
				soSearchVO.setVendBuyerResourceId(orderCriteria.getVendBuyerResId());
				soSearchVO.setBuyerID(orderCriteria.getCompanyId());
			} else if (adoptedRoleId != null && adoptedRoleId == OrderConstants.PROVIDER_ROLEID) {
				soSearchVO.setAcceptedResourceId(orderCriteria.getVendBuyerResId());
				soSearchVO.setVendorId(orderCriteria.getCompanyId());
			}
		}
		return;
	}

	
	// SOM
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes(
			CriteriaMap criteriaMap) throws DataServiceException {

		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		PaginationVO pagination = null;
		String queryName = null;
		Map<String,Object> paramMap = new HashMap<String, Object>();
		 
		ServiceOrderMonitorResultVO serviceOrderMonitorResultVO = null;
		if (criteriaMap != null) {
			criteriaHandler.doCommonQueryInit(criteriaMap,Boolean.FALSE);
		}

		OrderCriteria orderCriteria = (OrderCriteria) criteriaMap
				.get(OrderConstants.ORDER_CRITERIA_KEY);
		Integer statusId[] = orderCriteria.getStatusId();
		
		// Start Carlos Testing
//		statusId[0] = OrderConstants.DRAFT_STATUS;
//		Random rand = new Random();
//		if(rand.nextBoolean())
//			statusId[0] += 10;
//		
//		orderCriteria.setStatusId(statusId);
		// End Carlos Testing

		statusId = criteriaHandler.getValidatedStatusCodes(statusId[0],
				orderCriteria.getRoleId());
		orderCriteria.setStatusId(statusId);
		int totalRecordCount = 0;
		if (OrderConstants.BUYER_ROLEID == orderCriteria.getRoleId().intValue()) {
			queryName = OrderConstants.SOM_COUNT_FOR_BUYER_QUERY;
		} else if (OrderConstants.SIMPLE_BUYER_ROLEID == orderCriteria.getRoleId().intValue()) {
			queryName = OrderConstants.SOM_COUNT_FOR_BUYER_QUERY;
		} else if (OrderConstants.PROVIDER_ROLEID == orderCriteria.getRoleId().intValue()) {
			queryName = OrderConstants.SOM_COUNT_FOR_PROVIDER_QUERY;
		} else {
			queryName = OrderConstants.SOM_COUNT_FOR_BUYER_QUERY;
		}

		totalRecordCount = getPaginationDao().getResultSetCount(queryName,
				statusId, criteriaMap);

		pagination = criteriaHandler._getPaginationDetail(totalRecordCount,
				criteriaHandler.getClientPageSize(), criteriaHandler
						.getClientStartIndex(), criteriaHandler
						.getClientEndIndex());
		
		//boolean loadSOMswitch=getServiceOrderDao().loadSOMswitch();
		//logger.info("loadSOMswitch"+loadSOMswitch); 
		
		
		boolean loadSOMswitch = true;
		loadSOMswitch = getServiceOrderDao().loadSOMswitch();
		logger.info("searchBOloadSOMswitch in getServiceOrdersByStatusTypes::::"+loadSOMswitch);

		/*if (null != loadSOMSwitch) {

			if (null != loadSOMSwitch.get("loadSOMswitch")) {
				loadSOMswitch = loadSOMSwitch.get("loadSOMswitch");
			} else {
				loadSOMswitch = getServiceOrderDao().loadSOMswitch();
			}

			if (loadSOMswitch) {
				boolean loadSOMInactiveSwitch = false;
				if (null != loadSOMSwitch.get("loadSOMInactiveSwitch")) {
					loadSOMInactiveSwitch = loadSOMSwitch
							.get("loadSOMInactiveSwitch");
				} else {
					loadSOMInactiveSwitch = getServiceOrderDao()
							.loadSOMswitch();
				}
				// inactive->on - old query
				if (loadSOMInactiveSwitch) {
					loadSOMswitch = false;
				}

			}

		} else {

			loadSOMswitch = getServiceOrderDao().loadSOMswitch();

			if (loadSOMswitch) {

				boolean loadSOMInactiveSwitch = false;
				loadSOMInactiveSwitch = getServiceOrderDao().loadSOMswitch();

				// inactive->on - old query
				if (loadSOMInactiveSwitch) {
					loadSOMswitch = false;
				}

			}
		}*/
 
		//logger.info("searchBOloadSOMswitch: "+loadSOMswitch);
		serviceOrderMonitorResultVO = getSoSearchDAO()
				.getServiceOrdersByStatusTypes(criteriaMap,loadSOMswitch);
		if(serviceOrderMonitorResultVO != null){
			List<ServiceOrderSearchResultsVO> soSearchResultsList = serviceOrderMonitorResultVO.getServiceOrderResults();
			if(soSearchResultsList != null){
				List<String> soIdList = new ArrayList<String>();
				for (ServiceOrderSearchResultsVO so : soSearchResultsList) {
					if (so != null) {
						soIdList.add(so.getSoId());
						//R16_0 : SL_20728 : removing html contents in overview section to display the same as normal text in SOM
						so.setSoTitleDesc(ServiceLiveStringUtils.removeHTML(so.getSoTitleDesc()));
						convertGMTToGivenTimeZone(so);
					}
				}
				paramMap.put("soIdList", soIdList);
				List<ServiceOrderSearchResultsVO> soProviderPhoneDetailList = getSoSearchDAO()
				.getServiceOrdersProviderPhones(paramMap);
				
				for(ServiceOrderSearchResultsVO soProviderPhoneDetail : soProviderPhoneDetailList){
					for(ServiceOrderSearchResultsVO so : soSearchResultsList){
						if(so.getSoId().equals(soProviderPhoneDetail.getSoId())){
							so.setProviderPrimaryPhoneNumber(soProviderPhoneDetail.getProviderPrimaryPhoneNumber());
							so.setProviderAltPhoneNumber(soProviderPhoneDetail.getProviderAltPhoneNumber());
							so.setProviderMainPhoneNo(soProviderPhoneDetail.getProviderMainPhoneNo());
							so.setProviderMobilePhoneNo(soProviderPhoneDetail.getProviderMobilePhoneNo());
						}
					}
				}
				
				boolean isGroupIdPresentInCriteria = isGroupIdPresentInCriteria(criteriaMap);
				if (!isGroupIdPresentInCriteria) {
					// Re-sequence the grouped orders in the list
					boolean insertSiblingsForSearch = false;
					soSearchResultsList = reorderSOListForGroupingAndSorting(soSearchResultsList, criteriaMap, insertSiblingsForSearch);
					serviceOrderMonitorResultVO.setServiceOrderResults(soSearchResultsList);
				}
			}
		}
		serviceOrderMonitorResultVO.setPaginationVO(pagination);
		return serviceOrderMonitorResultVO;
	}
	
	
	
	/*
	// SOM
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes(
			CriteriaMap criteriaMap, String displayTab) throws DataServiceException {

		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		PaginationVO pagination = null;
		String queryName = null;

		ServiceOrderMonitorResultVO serviceOrderMonitorResultVO = null;
		if (criteriaMap != null) {
			criteriaHandler.doCommonQueryInit(criteriaMap,Boolean.FALSE);
		}

		OrderCriteria orderCriteria = (OrderCriteria) criteriaMap
				.get(OrderConstants.ORDER_CRITERIA_KEY);
		Integer statusId[] = orderCriteria.getStatusId();
		
		// Start Carlos Testing
//		statusId[0] = OrderConstants.DRAFT_STATUS;
//		Random rand = new Random();
//		if(rand.nextBoolean())
//			statusId[0] += 10;
//		
//		orderCriteria.setStatusId(statusId);
		// End Carlos Testing

		statusId = criteriaHandler.getValidatedStatusCodes(statusId[0],
				orderCriteria.getRoleId());
		orderCriteria.setStatusId(statusId);
		int totalRecordCount = 0;
		if (OrderConstants.BUYER_ROLEID == orderCriteria.getRoleId().intValue()) {
			queryName = OrderConstants.SOM_COUNT_FOR_BUYER_QUERY;
		} else if (OrderConstants.SIMPLE_BUYER_ROLEID == orderCriteria.getRoleId().intValue()) {
			queryName = OrderConstants.SOM_COUNT_FOR_BUYER_QUERY;
		} else if (OrderConstants.PROVIDER_ROLEID == orderCriteria.getRoleId().intValue()) {
			queryName = OrderConstants.SOM_COUNT_FOR_PROVIDER_QUERY;
		} else {
			queryName = OrderConstants.SOM_COUNT_FOR_BUYER_QUERY;
		}

		totalRecordCount = getPaginationDao().getResultSetCount(queryName,
				statusId, criteriaMap);

		pagination = criteriaHandler._getPaginationDetail(totalRecordCount,
				criteriaHandler.getClientPageSize(), criteriaHandler
						.getClientStartIndex(), criteriaHandler
						.getClientEndIndex());

		serviceOrderMonitorResultVO = getSoSearchDAO()
				.getServiceOrdersByStatusTypes(criteriaMap, displayTab);
		if(serviceOrderMonitorResultVO != null){
			List<ServiceOrderSearchResultsVO> soSearchResultsList = serviceOrderMonitorResultVO.getServiceOrderResults();
			if(soSearchResultsList != null){
				for (ServiceOrderSearchResultsVO so : soSearchResultsList) {
					if (so != null) {
						convertGMTToGivenTimeZone(so);
					}
				}
				
				boolean isGroupIdPresentInCriteria = isGroupIdPresentInCriteria(criteriaMap);
				if (!isGroupIdPresentInCriteria) {
					// Re-sequence the grouped orders in the list
					boolean insertSiblingsForSearch = false;
					soSearchResultsList = reorderSOListForGroupingAndSorting(soSearchResultsList, criteriaMap, insertSiblingsForSearch);
					serviceOrderMonitorResultVO.setServiceOrderResults(soSearchResultsList);
				}
			}
		}
		serviceOrderMonitorResultVO.setPaginationVO(pagination);
		return serviceOrderMonitorResultVO;
	}
	
	
	
	*/
	
	
	
	
	
	
	
	
	
	protected List<ServiceOrderSearchResultsVO> reorderSOListForGroupingAndSorting(List<ServiceOrderSearchResultsVO> soSearchResultsList,
								CriteriaMap criteriaMap, boolean insertSiblingsForSearch) throws DataServiceException {
		
		//Step-1: Extract and replace children orders with respective grouped order
		Map<String, List<ServiceOrderSearchResultsVO>> groupIdChildrenMap = new HashMap<String, List<ServiceOrderSearchResultsVO>>();
		List<ServiceOrderSearchResultsVO> soListHavingGroupedOrders = replaceChildrenWithGroupedOrder(soSearchResultsList, groupIdChildrenMap);
		
		//TODO: Remove this step after thorough testing of "SOM sorting on different columns in different tabs for different login: buyer/provider" 
		//Step-2: Sort the list containing individual + grouped orders based on criteriaMap containing sort criteria (and order criteria which defines default sorting)
		//sortSOList(soListHavingGroupedOrders, criteriaMap);
		
		//Step-3:  Replace grouped order with respective children
		List<ServiceOrderSearchResultsVO> returnSOList = replaceGroupedOrderWithChildren(soListHavingGroupedOrders, groupIdChildrenMap,
								criteriaMap, insertSiblingsForSearch);
		return returnSOList;
	}
	
	protected List<ServiceOrderSearchResultsVO> replaceChildrenWithGroupedOrder(List<ServiceOrderSearchResultsVO> soSearchResultsList,
							Map<String, List<ServiceOrderSearchResultsVO>> groupIdChildrenMap) {
		
		List<ServiceOrderSearchResultsVO> soListHavingGroupedOrders = new ArrayList<ServiceOrderSearchResultsVO>();
		for (ServiceOrderSearchResultsVO soSearchResultsVO : soSearchResultsList) {
			String groupId = soSearchResultsVO.getParentGroupId();
			if (StringUtils.isBlank(groupId)) {  // This is an individual SO
				soListHavingGroupedOrders.add(soSearchResultsVO);
			} else {	// This is a child SO, insert in sortList a group order instead of child SO
				List<ServiceOrderSearchResultsVO> childSOList = groupIdChildrenMap.get(groupId);
				if (childSOList == null) {
					ServiceOrderSearchResultsVO soRepresentingGroupedOrder = soSearchResultsVO.clone();
					soRepresentingGroupedOrder.setSoId(soSearchResultsVO.getParentGroupId());
					soRepresentingGroupedOrder.setSoTitle(soSearchResultsVO.getParentGroupTitle());
					soRepresentingGroupedOrder.setSpendLimit(soSearchResultsVO.getGroupSpendLimit());
					soListHavingGroupedOrders.add(soRepresentingGroupedOrder);
					childSOList = new ArrayList<ServiceOrderSearchResultsVO>();
					groupIdChildrenMap.put(groupId, childSOList);
				}
				childSOList.add(soSearchResultsVO);
			}
		}
		
		return soListHavingGroupedOrders;
	}

	protected void sortSOList(List<ServiceOrderSearchResultsVO> soListForSorting, CriteriaMap criteriaMap) {
		
		// Extract sorting criteria from criteriaMap
		SortCriteria sortCriteria = null;
		OrderCriteria orderCriteria = null;
		boolean noSort = true;
		if (criteriaMap != null) {
			sortCriteria = (SortCriteria)criteriaMap.get(OrderConstants.SORT_CRITERIA_KEY);
			orderCriteria = (OrderCriteria)criteriaMap.get(OrderConstants.ORDER_CRITERIA_KEY);
		}
		String sortAttributeName = StringUtils.EMPTY;
		String sortOrder = StringUtils.EMPTY;
		if (sortCriteria != null) {
			sortAttributeName = sortCriteria.getSortColumnName();
			sortOrder = sortCriteria.getSortOrder();
		}
		
		if(StringUtils.isNotEmpty(sortAttributeName) && !StringUtils.equals(sortAttributeName, OrderConstants.NULL_STRING) ) {
			String[] validSortAttributes = new String[] {
					ServiceOrderComparator.SORT_ATTR_SOM_STATUS, ServiceOrderComparator.SORT_ATTR_SOM_SOID, ServiceOrderComparator.SORT_ATTR_SOM_SPENDLIMIT,
					ServiceOrderComparator.SORT_ATTR_SOM_SERVICEDATE, ServiceOrderComparator.SORT_ATTR_SOM_APPTTIME, ServiceOrderComparator.SORT_ATTR_SOM_ORDERAGE};
			List<String> validSortAttributesList = Arrays.asList(validSortAttributes);
			if (validSortAttributesList.indexOf(sortAttributeName) == -1) {
				noSort = true;
				sortAttributeName = ServiceOrderComparator.SORT_ATTR_SOM_SERVICEDATE;
				sortOrder = OrderConstants.SORT_ORDER_DESC;
			}
		} else {
			sortAttributeName = ServiceOrderComparator.SORT_ATTR_SOM_SERVICEDATE;
			sortOrder = OrderConstants.SORT_ORDER_DESC;
			Integer statusId[] = orderCriteria.getStatusId();
			if(statusId != null && statusId.length > 0) {
				List<Integer> statusIdList = Arrays.asList(statusId);
				if (statusIdList.indexOf(OrderConstants.DRAFT_STATUS) >= 0) { //For Draft - default sort should be Created Date
					logger.info("getServiceOrdersByStatusTypes()-->DraftTab");
					sortAttributeName = ServiceOrderComparator.SORT_ATTR_SOM_CREATEDDATE;
				}
			}
		}
		
		// Sort the input list using comparator based on sortAttributeName and sortOrder
		if(noSort){
			if (OrderConstants.SORT_ORDER_ASC.equals(sortOrder)) {
				Collections.sort(soListForSorting, ServiceOrderComparator.soAscComparatorsMap.get(sortAttributeName));
			} else {
				Collections.sort(soListForSorting, ServiceOrderComparator.soDescComparatorsMap.get(sortAttributeName));
			}
		}
	}

	protected List<ServiceOrderSearchResultsVO> replaceGroupedOrderWithChildren(List<ServiceOrderSearchResultsVO> soListHavingGroupedOrders,
			Map<String, List<ServiceOrderSearchResultsVO>> groupIdChildrenMap, CriteriaMap criteriaMap, boolean insertSiblingsForSearch) throws DataServiceException {

		List<ServiceOrderSearchResultsVO> returnSOList = new ArrayList<ServiceOrderSearchResultsVO>();
		int orderIndex = 0;
		int totalOrdersCount = soListHavingGroupedOrders.size();
		for (ServiceOrderSearchResultsVO soSearchResultsVO : soListHavingGroupedOrders) {
			++orderIndex;
			if (soSearchResultsVO.getSoId().equals(soSearchResultsVO.getParentGroupId())) { // SO-ID same as Parent-Group-Id, means it's a grouped order
				String groupId = soSearchResultsVO.getParentGroupId();
				List<ServiceOrderSearchResultsVO> childrenSOList = groupIdChildrenMap.get(groupId);
				
				//if (!isSearchFromOrderGroupManager(criteriaMap)) {
					setGroupIdInCriteriaMap(groupId, criteriaMap);
					if (insertSiblingsForSearch) {
						ProcessResponse pr = retrievePagedSOSearchResults(criteriaMap);
						childrenSOList = ((ServiceOrderMonitorResultVO)pr.getObj()).getServiceOrderResults();
					} else if (isGroupToBeRefreshed(orderIndex, totalOrdersCount, criteriaMap)) {
						ServiceOrderMonitorResultVO somResultVO = getServiceOrdersByStatusTypes(criteriaMap);
						childrenSOList = somResultVO.getServiceOrderResults();
					}
					setGroupIdInCriteriaMap(null, criteriaMap); // remove groupId from criteria by setting null
				//}
				
				// Possibility of duplicate children orders for provider admin login ...
				// Concatenate the resourceId with groupId, to be handled by front-end monitorHelper tag to display duplicate group orders for each resource
				childrenSOList = orderByResourceId(childrenSOList);

				//TODO: Remove this commented line after thorough testing of "SOM sorting on spend limit" and "Group increase spend limit" functionality
				//updateGroupSpendLimitFromSumOfChildrenSpendLimits(childrenSOList);
				returnSOList.addAll(childrenSOList);
			} else {
				returnSOList.add(soSearchResultsVO);
			}
		}
		return returnSOList;
	}
	
	private boolean isSearchFromOrderGroupManager(CriteriaMap criteriaMap) {
		boolean isSearchFromOrderGroupManager = false;
		SearchCriteria searchCriteria = (SearchCriteria) criteriaMap.get(OrderConstants.SEARCH_CRITERIA_KEY);
		if (searchCriteria != null && searchCriteria.isSearchFromOrderGroupManager()) {
			isSearchFromOrderGroupManager = true;
		}
		return isSearchFromOrderGroupManager;
	}

	private boolean isGroupIdPresentInCriteria(CriteriaMap criteriaMap) {
		boolean isQueryForChildren = false;
		SearchCriteria searchCriteria = (SearchCriteria)criteriaMap.get(OrderConstants.SEARCH_CRITERIA_KEY);
		if (searchCriteria != null) {
			String groupId = searchCriteria.getGroupId();
			if (StringUtils.isNotBlank(groupId)) {
				isQueryForChildren = true;
			}
		}
		return isQueryForChildren;
	}
	
	private void setGroupIdInCriteriaMap(String groupId, CriteriaMap criteriaMap) {
		SearchCriteria searchCriteria = (SearchCriteria)criteriaMap.get(OrderConstants.SEARCH_CRITERIA_KEY);
		if (searchCriteria != null) {
			searchCriteria.setGroupId(groupId);
		}
	}
	
	protected boolean isGroupToBeRefreshed(int orderIndex, int totalOrdersCount, CriteriaMap criteriaMap) {
		
		if (criteriaMap == null || criteriaMap.get(OrderConstants.PAGING_CRITERIA_KEY) == null) { // Records are not paginated
			return false;
		}
		
		/* Discussed with Greg: Comment this section to refresh group i.e. always query children from database for grouped orders
		if (orderIndex == 1 && pagingCriteria.getStartIndex() >= pagingCriteria.getPageSize()) { // The grouped order is first order on second page onwards
			return true;
		}
		
		if (orderIndex == totalOrdersCount) { // The grouped order is last order on current page
			return true;
		}
		*/
		
		// Return true to refresh group i.e. always query children from database for grouped orders
		return true;
	}
	
	protected void updateGroupSpendLimitFromSumOfChildrenSpendLimits(List<ServiceOrderSearchResultsVO> childrenSOList) {
		Double groupSpendLimitLabor = 0.0;
		Double groupSpendLimitParts = 0.0;
		for (ServiceOrderSearchResultsVO childSO : childrenSOList) {
			groupSpendLimitLabor += childSO.getSpendLimit();
			groupSpendLimitParts += childSO.getSpendLimitParts();
		}
		for (ServiceOrderSearchResultsVO childSO : childrenSOList) {
			childSO.setGroupSpendLimit(groupSpendLimitLabor + groupSpendLimitParts);
			childSO.setGroupSpendLimitLabor(groupSpendLimitLabor);
			childSO.setGroupSpendLimitParts(groupSpendLimitParts);
		}
	}
	
	protected List<ServiceOrderSearchResultsVO> orderByResourceId(List<ServiceOrderSearchResultsVO> childrenSOList) {
		List<ServiceOrderSearchResultsVO> childrenSOListOrderedByResourceId = new ArrayList<ServiceOrderSearchResultsVO>();
		List<ServiceOrderSearchResultsVO> nullResourceIdChildSOList = new ArrayList<ServiceOrderSearchResultsVO>();
		Set<String> resourceIdSet = new HashSet<String>();
		for (ServiceOrderSearchResultsVO childRoutedSO : childrenSOList) {
			String resourceId = childRoutedSO.getRoutedResourceId();
			if (resourceId == null || OrderConstants.NULL_RESOURCE_ID.equals(resourceId)) {
				nullResourceIdChildSOList.add(childRoutedSO);
			} else {
				childrenSOListOrderedByResourceId.add(childRoutedSO);
				resourceIdSet.add(resourceId);
				String changedGroupId = new StringBuilder(childRoutedSO.getParentGroupId()).append("#").append(resourceId).append("#").toString();
				childRoutedSO.setParentGroupId(changedGroupId);
			}
		}
		
		if (!resourceIdSet.isEmpty()) {
			if (!nullResourceIdChildSOList.isEmpty()) {
				for (String resourceId : resourceIdSet) {
					for (ServiceOrderSearchResultsVO nullResourceIdSO : nullResourceIdChildSOList) {
						ServiceOrderSearchResultsVO clonedNullResourceIdSO = nullResourceIdSO.clone();
						String changedGroupId = new StringBuilder(nullResourceIdSO.getParentGroupId()).append("#").append(resourceId).append("#").toString();
						clonedNullResourceIdSO.setParentGroupId(changedGroupId);
						clonedNullResourceIdSO.setRoutedResourceId(resourceId);
						childrenSOListOrderedByResourceId.add(clonedNullResourceIdSO);
					}
				}
			}
			Collections.sort(childrenSOListOrderedByResourceId, ServiceOrderComparator.resourceIdComparator);
			childrenSOList = childrenSOListOrderedByResourceId;
		}
		return childrenSOList;
	}

	//This method is currently used only by Batch processes and we are modifying the query to
	//eliminate re-factored service order please do not use this query for other purposes in future
	public List<ServiceOrderSearchResultsVO> findServiceOrderByStatusForBatch(
			serviceOrderTabsVO request) throws DataServiceException {

		List<ServiceOrderSearchResultsVO> soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
		soSearchList = getSoSearchDAO().getSObyWFStateForBatch(request);
		if(soSearchList != null && soSearchList.size() > 0){
			Iterator<ServiceOrderSearchResultsVO> itr = soSearchList.iterator();
			while(itr.hasNext()){
				ServiceOrderSearchResultsVO obj = itr.next();
				if(obj != null){
					convertGMTToGivenTimeZone(obj);
				}
			}
		}

		return soSearchList;
	}
	
	public ServiceDatetimeSlot getDateTimeSlotForOrder(ServiceOrderSearchResultsVO serviceOrderSearchResultsVO) throws DataServiceException{
		ServiceDatetimeSlot serviceDatetimeSlot =  getSoSearchDAO().getDateTimeSlotForOrder(serviceOrderSearchResultsVO);
		serviceDatetimeSlot.setTimeZone(serviceOrderSearchResultsVO.getServiceLocationTimezone());
		convertGMTToGivenTimeZoneForSlot(serviceDatetimeSlot);
		return serviceDatetimeSlot;
	}
	
	//This method is currently used only by Batch processes and we are modifying the query to
	//eliminate re-factored service order please do not use this query for other purposes in future
	public List<ServiceOrderSearchResultsVO> findServiceOrderForConditionalRouting(
			serviceOrderTabsVO request) throws DataServiceException {
		List<ServiceOrderSearchResultsVO> soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
		
		soSearchList = getSoSearchDAO().getSOforConditionalRouting(request);
		
		if(soSearchList != null && soSearchList.size() > 0){
			Iterator<ServiceOrderSearchResultsVO> itr = soSearchList.iterator();
			while(itr.hasNext()){
				ServiceOrderSearchResultsVO obj = itr.next();
				if(obj != null){
					convertGMTToGivenTimeZone(obj);
				}
			}
		}

		return soSearchList;
	}
	
	public List<ServiceOrderSearchResultsVO> findConditionalOffers(Timestamp currentDateTime) throws DataServiceException{
		List<ServiceOrderSearchResultsVO> soSearchList = null;
		logger.debug("Entering ServiceOrderSearchBO --> findConditionalOffers()");
		try {
			soSearchList = getSoSearchDAO().getConditionalOffers(currentDateTime);
		} catch (DataServiceException e) {
			logger.error("[ServiceOrderSearchBO.findConditionalOffers - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}
		logger.debug("Leaving ServiceOrderSearchBO --> findConditionalOffers()");
		return soSearchList;
	}
	
	private static void convertGMTToGivenTimeZone(ServiceOrderSearchResultsVO obj){
		if (obj.getAppointStartDate() != null
				&& obj.getServiceTimeStart() != null) {
			HashMap<String, Object> startDateTimeMap = TimeUtils
					.convertGMTToGivenTimeZone(obj
							.getAppointStartDate(), obj
							.getServiceTimeStart(), obj.getServiceLocationTimezone());
			if (startDateTimeMap != null && !startDateTimeMap.isEmpty()){
				obj.setAppointStartDate(
						(Timestamp) startDateTimeMap.get(OrderConstants.GMT_DATE));
				obj.setServiceTimeStart(
					(String) startDateTimeMap.get(OrderConstants.GMT_TIME));
			}
		}

		if (obj.getAppointEndDate() != null
				&& obj.getServiceTimeEnd() != null) {
			HashMap<String, Object> endDateTimeMap = TimeUtils
					.convertGMTToGivenTimeZone(obj
							.getAppointEndDate(), obj
							.getServiceTimeEnd(), obj.getServiceLocationTimezone());
			if (endDateTimeMap != null && !endDateTimeMap.isEmpty()){
				obj.setAppointEndDate(
						(Timestamp) endDateTimeMap.get(OrderConstants.GMT_DATE));
				obj.setServiceTimeEnd(
					(String) endDateTimeMap.get(OrderConstants.GMT_TIME));
			}
		}
	}
	
	
	private static void convertGMTToGivenTimeZoneForSlot(ServiceDatetimeSlot serviceDatetimeSlot){
		if (serviceDatetimeSlot.getServiceStartDate() != null
				&& serviceDatetimeSlot.getServiceStartTime() != null) {
			HashMap<String, Object> startDateTimeMap = TimeUtils
					.convertGMTToGivenTimeZone(serviceDatetimeSlot.getServiceStartDate(), serviceDatetimeSlot.getServiceStartTime()
							, serviceDatetimeSlot.getTimeZone());
			if (startDateTimeMap != null && !startDateTimeMap.isEmpty()){
				serviceDatetimeSlot.setServiceStartDate(
						(Timestamp) startDateTimeMap.get(OrderConstants.GMT_DATE));
				serviceDatetimeSlot.setServiceStartTime(
					(String) startDateTimeMap.get(OrderConstants.GMT_TIME));
			}
		}

		if (serviceDatetimeSlot.getServiceEndDate() != null
				&& serviceDatetimeSlot.getServiceEndTime() != null) {
			HashMap<String, Object> endDateTimeMap = TimeUtils
					.convertGMTToGivenTimeZone(serviceDatetimeSlot.getServiceEndDate(), serviceDatetimeSlot.getServiceEndTime()
							, serviceDatetimeSlot.getTimeZone());
			if (endDateTimeMap != null && !endDateTimeMap.isEmpty()){
				serviceDatetimeSlot.setServiceEndDate(
						(Timestamp) endDateTimeMap.get(OrderConstants.GMT_DATE));
				serviceDatetimeSlot.setServiceEndTime(
					(String) endDateTimeMap.get(OrderConstants.GMT_TIME));
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO#findNotScheduledSO()
	 */
	public List<ServiceOrderSearchResultsVO> findNotScheduledSO() throws BusinessServiceException{
		List<ServiceOrderSearchResultsVO> soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
		Calendar timeOneDayBefore = Calendar.getInstance();
		//Get the time before 24hrs
		timeOneDayBefore.add(Calendar.DAY_OF_MONTH, -1);
		Timestamp tsOneDayBefore = new Timestamp(timeOneDayBefore.getTimeInMillis());
		
		try {
			for (ServiceOrderSearchResultsVO serviceOrderSearchResultsVO : getSoSearchDAO().getNotScheduledSO()) {
				if (tsOneDayBefore.after(serviceOrderSearchResultsVO.getCreatedDate())) {
					soSearchList.add(serviceOrderSearchResultsVO);
				}
			}
		} catch (Exception e) {
			logger.error("[ServiceOrderSearchBO.findNotScheduledSO - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new BusinessServiceException("Error", e);
		} 
		return soSearchList;
	}
	
	public List<SearchFilterVO> getSearchFilters(SearchFilterVO searchFilterVO) throws BusinessServiceException{

		List<SearchFilterVO> savedFilters = new ArrayList<SearchFilterVO>();

		try{

		savedFilters = getSoSearchDAO().getSearchFilters(searchFilterVO);
		/*This method was written to remove  Apostrophe in the name of searchFilter
		 * which cause alignment issue in spendlimit increase widget in FF18 as part of
		 * SL-18573
		 */
			if (null != savedFilters && savedFilters.size() > 0) {
				for (SearchFilterVO filterVO : savedFilters) {
					if (null != filterVO && StringUtils.isNotEmpty(filterVO.getFilterName())) {
						if (StringUtils.contains(filterVO.getFilterName(),removeApostrophe)) {
							String filterNameWithoutAphostrophe = StringUtils.remove(filterVO.getFilterName(),removeApostrophe);
							filterVO.setFilterName(filterNameWithoutAphostrophe);

						}

					}

				}
			}

		}catch(DataServiceException dse){

		logger.error("Error while getting the search filters");

		}

		return savedFilters;

		}
	
	private String addHyphensToSoId(String soId)
	{
		StringBuffer soID = new StringBuffer(soId.trim());
		for( int i = 0; i < soID.length(); i++)
		{
			char c = soID.charAt(i);
			if ( (i ==3||i== 8||i==13) && !(c == '-'))
			{
				soID.insert(i,'-');
			}
		}
		
		return soID.toString();
	}
	private ServiceOrderSearchResultsVO setAdvanceSearchCriteria(SearchCriteria searchCriteria,ServiceOrderSearchResultsVO soSearchVO){
		Integer autocloseAlone=1;
		if(null != searchCriteria.getSelectedStates() && 
				!searchCriteria.getSelectedStates().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedStateCodes(searchCriteria.getSelectedStates());
			
		}
		if(null != searchCriteria.getSelectedSkills() && 
				!searchCriteria.getSelectedSkills().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedSkills(searchCriteria.getSelectedSkills());
			
		}
		if(null != searchCriteria.getSelectedMarkets() && 
				!searchCriteria.getSelectedMarkets().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedMarkets(searchCriteria.getSelectedMarkets());
			
		}
		//SL-15642:adding new search categories
		//setting order acceptance type
		if(null != searchCriteria.getSelectedAcceptanceTypes() && 
				!searchCriteria.getSelectedAcceptanceTypes().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedAcceptanceTypes(searchCriteria.getSelectedAcceptanceTypes());
			
		}
		//setting pricing type
		if(null != searchCriteria.getSelectedPricingTypes() && 
				!searchCriteria.getSelectedPricingTypes().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedPricingTypes(searchCriteria.getSelectedPricingTypes());
			
		}
		//setting assignment type
		if(null != searchCriteria.getSelectedAssignmentTypes() && 
				!searchCriteria.getSelectedAssignmentTypes().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedAssignmentTypes(searchCriteria.getSelectedAssignmentTypes());
			
		}
		//setting posting method
		if(null != searchCriteria.getSelectedPostingMethods() && 
				!searchCriteria.getSelectedPostingMethods().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedPostingMethods(searchCriteria.getSelectedPostingMethods());
			
		}
		
		//R12_1
		//SL-20362
		//setting Pending Reschedule
				if(null != searchCriteria.getSelectedPendingReschedule() && 
						!searchCriteria.getSelectedPendingReschedule().isEmpty()){
					autocloseAlone=0;
					soSearchVO.setSelectedPendingReschedule(searchCriteria.getSelectedPendingReschedule());
					
		}
		//R12_1
		//SL-20554
		//setting closure method
		if(null != searchCriteria.getSelectedClosureMethod() && 
			!searchCriteria.getSelectedClosureMethod().isEmpty()){
				autocloseAlone=0;
				soSearchVO.setSelectedClosureMethod(searchCriteria.getSelectedClosureMethod());
							
		}
		
		if(null != searchCriteria.getSelectedCustomRefs() && 
				!searchCriteria.getSelectedCustomRefs().isEmpty()){
			autocloseAlone=0;
			ArrayList<String> tempList = null;
			Iterator it = searchCriteria.getSelectedCustomRefs().keySet().iterator();
			List<ServiceOrderCustomRefVO> custRefs = new ArrayList<ServiceOrderCustomRefVO>();
			while(it.hasNext()) { 
				String key = (String)it.next(); 
				//Priority 18 issue changes
				//Setting multiple values for same custom reference to be passed onto 'Advance query'.
				tempList = searchCriteria.getSelectedCustomRefs().get(key); 
				 if (tempList != null) {
			         for (String value: tempList) {
			        	 ServiceOrderCustomRefVO custRef = new ServiceOrderCustomRefVO();
						 custRef.setRefTypeId(Integer.parseInt(key));
						 custRef.setRefValue(value);
						 custRefs.add(custRef);
			         }
			      }

			} 				
			soSearchVO.setSelectedCustomRefs(custRefs);				
			
		}
		if(null != searchCriteria.getSelectedCheckNumbers() && 
				!searchCriteria.getSelectedCheckNumbers().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedCheckNumbers(searchCriteria.getSelectedCheckNumbers());
			soSearchVO.setPaymentType(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK);
			
		}
		if(null != searchCriteria.getSelectedCustomerNames() && 
				!searchCriteria.getSelectedCustomerNames().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedCustomerNames(searchCriteria.getSelectedCustomerNames());
			
		}
		if(null != searchCriteria.getSelectedPhones() && 
				!searchCriteria.getSelectedPhones().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedPhones(searchCriteria.getSelectedPhones());
			
		}
		if(null != searchCriteria.getSelectedProviderFirmIds() && 
				!searchCriteria.getSelectedProviderFirmIds().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedProviderFirmIds(searchCriteria.getSelectedProviderFirmIds());
			
		}
		if(null != searchCriteria.getSelectedServiceOrderIds() && 
				!searchCriteria.getSelectedServiceOrderIds().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedServiceOrderIds(searchCriteria.getSelectedServiceOrderIds());
			
		}
		if(null != searchCriteria.getSelectedServiceProIds() && 
				!searchCriteria.getSelectedServiceProIds().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedServiceProIds(searchCriteria.getSelectedServiceProIds());
			
		}
		if(null != searchCriteria.getSelectedServiceProNames() && 
				!searchCriteria.getSelectedServiceProNames().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedServiceProNames(searchCriteria.getSelectedServiceProNames());
			
		}
		if(null != searchCriteria.getSelectedZipCodes() && 
				!searchCriteria.getSelectedZipCodes().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedZipCodes(searchCriteria.getSelectedZipCodes());
			
		}
		if(null != searchCriteria.getSelectedMainCatIdList() && 
				!searchCriteria.getSelectedMainCatIdList().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedMainCatIdList(searchCriteria.getSelectedMainCatIdList());
		}
		if(null != searchCriteria.getSelectedCatAndSubCatIdList()&& 
				!searchCriteria.getSelectedCatAndSubCatIdList().isEmpty()){
			autocloseAlone=0;
			soSearchVO.setSelectedCatAndSubCatIdList(
																				searchCriteria.getSelectedCatAndSubCatIdList());
		}
		if(null != searchCriteria.getSelectedStatusSubStatus() &&
                !searchCriteria.getSelectedStatusSubStatus().isEmpty()){
			autocloseAlone=0;
			List<BuyerSubstatusAssocVO> statusList = new ArrayList<BuyerSubstatusAssocVO>();
			Iterator it = searchCriteria.getSelectedStatusSubStatus().keySet().iterator();
			while(it.hasNext()) { 				
			     String key = (String)it.next(); 
	             String val = (String)searchCriteria.getSelectedStatusSubStatus().get(key); 
	             if( null != val){
	            	 StringTokenizer subStatusValue = new StringTokenizer(val,"|");				
	 				 if(null != subStatusValue && subStatusValue.countTokens() >= 1){					
	 				 int noOfSubStatus = subStatusValue.countTokens();
	 				 //Setting the status and sub status to the list
	 				 for (int index = 1; index <= noOfSubStatus; index++) {
	 					 BuyerSubstatusAssocVO statusVO = new BuyerSubstatusAssocVO();
	 					 if(null != key)
	 						 statusVO.setWfStateId(new Integer(key));
	 			         if(null != val)
	 			        	 statusVO.setSubstatusId(new Integer(subStatusValue.nextToken()));
	 			         statusList.add(statusVO);
	 					}				 
	 				}
	             }else{//Setting the status alone to the list if sub status is not present
	 					BuyerSubstatusAssocVO statusVO = new BuyerSubstatusAssocVO();
	 					statusVO.setWfStateId(new Integer(key));
	 					statusList.add(statusVO);
	 			}            
			}
           soSearchVO.setSelectedStatuses(statusList);           
		}
		DateFormat sdf_yyyyddMM = new SimpleDateFormat("MM/dd/yyyy");	
		try{
			if(null != searchCriteria.getStartDateList()
					&& !searchCriteria.getStartDateList().isEmpty()){
				autocloseAlone=0;
				List<String> dateList = searchCriteria.getStartDateList();
				int listSize = dateList.size();
				List<Date> tempList = new ArrayList<Date>();
				for(int index=0;index<listSize;index++){						
					tempList.add(sdf_yyyyddMM.parse(dateList.get(index)));
				}
				soSearchVO.setStartDateList(tempList);
				//soSearchVO.setStartDateList(sdf_yyyyddMM.parse(searchCriteria.getStartDateList()));				
			}
			if(null != searchCriteria.getEndDateList()
					&& !searchCriteria.getEndDateList().isEmpty()){
				autocloseAlone=0;
				List<String> dateList = searchCriteria.getEndDateList();
				int listSize = dateList.size();
				List<Date> tempList = new ArrayList<Date>();
				for(int index=0;index<listSize;index++){						
					tempList.add(sdf_yyyyddMM.parse(dateList.get(index)));
				}
				soSearchVO.setEndDateList(tempList);
			}
		}catch(Exception e){
			logger.error("Date parse exception");
		}
		soSearchVO.setAutocloseAlone(autocloseAlone);
		return soSearchVO;
	}
	public SearchFilterVO saveSearchFilter(SearchFilterVO searchFilterVO )throws BusinessServiceException{
		try{
			
			searchFilterVO = getSoSearchDAO().saveSearchFilter(searchFilterVO);
		}catch(DataServiceException dse){
			logger.error("Error while saving the search filters");
		}
		return searchFilterVO;
	}
	
	public SearchFilterVO getSelectedSearchFilter(SearchFilterVO searchFilterVO) throws BusinessServiceException{
		SearchFilterVO savedFilter = new SearchFilterVO();
		try{
			savedFilter = getSoSearchDAO().getSelectedSearchFilter(searchFilterVO);
		}catch(DataServiceException dse){
			logger.error("Error while getting the search filters");
		}
		return savedFilter;
	}
	public SearchFilterVO deleteSearchFilter(SearchFilterVO searchFilterVO )throws BusinessServiceException{
		try{
			
			searchFilterVO = getSoSearchDAO().deleteSearchFilter(searchFilterVO);
		}catch(DataServiceException dse){
			logger.error("Error while saving the search filters");
		}
		return searchFilterVO;
	}
	
	//R12_1
	//SL-20379
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes(
			CriteriaMap criteriaMap, String tab) throws DataServiceException {

		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		PaginationVO pagination = null;
		String queryName = null;
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		ServiceOrderMonitorResultVO serviceOrderMonitorResultVO = null;
		if (criteriaMap != null) {
			if(StringUtils.isNotBlank(tab)){
				criteriaHandler.doCommonQueryInit(criteriaMap,Boolean.FALSE, tab);
			}else{
				criteriaHandler.doCommonQueryInit(criteriaMap,Boolean.FALSE);
			}
		}

		OrderCriteria orderCriteria;
		if(StringUtils.isNotBlank(tab)){
			orderCriteria = (OrderCriteria) criteriaMap
			.get(OrderConstants.ORDER_CRITERIA_KEY+"_"+tab);
		}else{
			orderCriteria = (OrderCriteria) criteriaMap
			.get(OrderConstants.ORDER_CRITERIA_KEY);
		}
		Integer statusId[] = orderCriteria.getStatusId();
		
		// Start Carlos Testing
//		statusId[0] = OrderConstants.DRAFT_STATUS;
//		Random rand = new Random();
//		if(rand.nextBoolean())
//			statusId[0] += 10;
//		
//		orderCriteria.setStatusId(statusId);
		// End Carlos Testing

		statusId = criteriaHandler.getValidatedStatusCodes(statusId[0],
				orderCriteria.getRoleId());
		orderCriteria.setStatusId(statusId);
		int totalRecordCount = 0;
		if (OrderConstants.BUYER_ROLEID == orderCriteria.getRoleId().intValue()) {
			queryName = OrderConstants.SOM_COUNT_FOR_BUYER_QUERY;
		} else if (OrderConstants.SIMPLE_BUYER_ROLEID == orderCriteria.getRoleId().intValue()) {
			queryName = OrderConstants.SOM_COUNT_FOR_BUYER_QUERY;
		} else if (OrderConstants.PROVIDER_ROLEID == orderCriteria.getRoleId().intValue()) {
			queryName = OrderConstants.SOM_COUNT_FOR_PROVIDER_QUERY;
		} else {
			queryName = OrderConstants.SOM_COUNT_FOR_BUYER_QUERY;
		}

		if(StringUtils.isNotBlank(tab)){
			totalRecordCount = getPaginationDao().getResultSetCount(queryName,statusId, criteriaMap, tab);
			logger.info("Tab value in getServiceOrdersByStatusTypes:::: "+tab);
		}else{
			totalRecordCount = getPaginationDao().getResultSetCount(queryName,statusId, criteriaMap);
			logger.info("Tab value is empty getServiceOrdersByStatusTypes.");
		}

		pagination = criteriaHandler._getPaginationDetail(totalRecordCount,
				criteriaHandler.getClientPageSize(), criteriaHandler
						.getClientStartIndex(), criteriaHandler
						.getClientEndIndex());
		
		
		
		
		boolean loadSOMswitch = true;
		loadSOMswitch = getServiceOrderDao().loadSOMswitch();
		logger.info("searchBOloadSOMswitch in getServiceOrdersByStatusTypes1:::: "+loadSOMswitch);
		
		
		
		if(StringUtils.isNotBlank(tab)){
			logger.info("tab value in getServiceOrdersByStatusTypes:::: "+tab);
			serviceOrderMonitorResultVO = getSoSearchDAO().getServiceOrdersByStatusTypes(criteriaMap, tab,loadSOMswitch);
		}else{
			logger.info("tab value in empty getServiceOrdersByStatusTypes");
			serviceOrderMonitorResultVO = getSoSearchDAO().getServiceOrdersByStatusTypes(criteriaMap,loadSOMswitch);
		}
		
		if(serviceOrderMonitorResultVO != null){
			List<ServiceOrderSearchResultsVO> soSearchResultsList = serviceOrderMonitorResultVO.getServiceOrderResults();
			if(soSearchResultsList != null){
				List<String> soIdList = new ArrayList<String>();
				for (ServiceOrderSearchResultsVO so : soSearchResultsList) {
					if (so != null) {
						//SL-21801
						if(null != so.getTotalEstimationPrice() && !so.getTotalEstimationPrice().equals(0.00)){
							so.setSpendLimit(so.getTotalEstimationPrice());
						}
						soIdList.add(so.getSoId());
						//R16_0 : SL_20728 : removing html contents in overview section to display the same as normal text in SOM
						so.setSoTitleDesc(ServiceLiveStringUtils.removeHTML(so.getSoTitleDesc()));
						convertGMTToGivenTimeZone(so);
					}
				}
				paramMap.put("soIdList", soIdList);
				List<ServiceOrderSearchResultsVO> soProviderPhoneDetailList = getSoSearchDAO()
				.getServiceOrdersProviderPhones(paramMap);
				
				for(ServiceOrderSearchResultsVO soProviderPhoneDetail : soProviderPhoneDetailList){
					for(ServiceOrderSearchResultsVO so : soSearchResultsList){
						if(so.getSoId().equals(soProviderPhoneDetail.getSoId())){
							so.setProviderPrimaryPhoneNumber(soProviderPhoneDetail.getProviderPrimaryPhoneNumber());
							so.setProviderAltPhoneNumber(soProviderPhoneDetail.getProviderAltPhoneNumber());
							so.setProviderMainPhoneNo(soProviderPhoneDetail.getProviderMainPhoneNo());
							so.setProviderMobilePhoneNo(soProviderPhoneDetail.getProviderMobilePhoneNo());
						}
					}
				}
				
				boolean isGroupIdPresentInCriteria = isGroupIdPresentInCriteria(criteriaMap);
				if (!isGroupIdPresentInCriteria) {
					// Re-sequence the grouped orders in the list
					boolean insertSiblingsForSearch = false;
					soSearchResultsList = reorderSOListForGroupingAndSorting(soSearchResultsList, criteriaMap, insertSiblingsForSearch);
					serviceOrderMonitorResultVO.setServiceOrderResults(soSearchResultsList);
				}
			}
		}
		serviceOrderMonitorResultVO.setPaginationVO(pagination);
		return serviceOrderMonitorResultVO;
	}
	
	public IOrderGroupDao getOrderGroupDAO() {
		return orderGroupDAO;
	}

	public void setOrderGroupDAO(IOrderGroupDao orderGroupDAO) {
		this.orderGroupDAO = orderGroupDAO;
	}

	/* SL-21308 : Standard Service Offerings Search API
	 */
	public SearchFirmsResponseVO getSearchFirmsResult(
			SearchFirmsVO searchFirmsVO) throws DataServiceException {
		SearchFirmsResponseVO searchFirmsResponse = null;
	
			List<String> zipList = null;
			//fetching zipcodes if radius is given in request
			if(null!=searchFirmsVO.getRadius()){
				zipList = getSoSearchDAO().getZipList(searchFirmsVO.getZip(), searchFirmsVO.getRadius());
				LOGGER.info("size of zip list from query"+ zipList.size());
				/*try {
					zipList = fetchAvailableZipCodes(searchFirmsVO.getZip(),searchFirmsVO.getRadius());
				} catch (BusinessServiceException e) {
					e.printStackTrace();
				}*/
				LOGGER.info("size of zip list from zip code finder web service"+ zipList.size());
			}
			else{
				//if radius is not given in request, set zipcode in the request in zipList
				zipList = new ArrayList<String>();
				zipList.add(searchFirmsVO.getZip());
			}
			searchFirmsVO.setZipcodeList(zipList);
			//fetching service offerings  zipcode list
			List<ServiceOfferingsVO> searchFirmsList = getSoSearchDAO().getSearchFirmsResult(searchFirmsVO);
			List<ServiceOfferingsVO> availableFirmsList = null;
			// If service date is present in the request
			if (null!=searchFirmsVO.getServiceDate1() && null!=searchFirmsVO.getServiceDate2() && 
					null!= searchFirmsList && !(searchFirmsList.isEmpty())){
				availableFirmsList = findAvailableFirms(searchFirmsVO,
						searchFirmsList);
			}
			else {
					//if date is not specified in request
					availableFirmsList = searchFirmsList;
				}
					if (null!=availableFirmsList && !(availableFirmsList.isEmpty())){
						Map<Integer,ServiceOfferingsVO> firmPriceMap = new HashMap<Integer, ServiceOfferingsVO>();
						Map<Integer,List<Integer>> firmOfferingMap = new HashMap<Integer, List<Integer>>();
						for (ServiceOfferingsVO serviceOfferingsVO: availableFirmsList){
							mapfirmPriceDetails(firmPriceMap, firmOfferingMap,
									serviceOfferingsVO);
						}
						// finding availability of the firm
						List<Integer> offeringIdList = new ArrayList<Integer>();
						for (ServiceOfferingsVO serviceOffering: availableFirmsList){
							if(!offeringIdList.contains(serviceOffering.getServiceOfferingId())){
								offeringIdList.add(serviceOffering.getServiceOfferingId());
							}
						}
						searchFirmsVO.setOfferingIdList(offeringIdList);
						List<ServiceOfferingsVO> offerAvailablityList = getSoSearchDAO().getOfferingAvailabilityList(searchFirmsVO);
						if (null!=offerAvailablityList && !(offerAvailablityList.isEmpty())){
							Map<Integer,List<AvailableTimeSlotVO>> offerAvailablityMap = new HashMap<Integer, List<AvailableTimeSlotVO>>();
							
							for (ServiceOfferingsVO serviceOffering:offerAvailablityList){
								mapFirmAvailabilityDetails(offerAvailablityMap,
										serviceOffering);
							}
							searchFirmsResponse = new SearchFirmsResponseVO();
							searchFirmsResponse.setOfferAvailablityMap(offerAvailablityMap);
							searchFirmsResponse.setFirmOfferingMap(firmOfferingMap);
							searchFirmsResponse.setFirmPriceMap(firmPriceMap);
							
							List<Integer> availableFirmIdList = new ArrayList<Integer>();
							for (ServiceOfferingsVO serviceOffering: availableFirmsList){
								if(!availableFirmIdList.contains(serviceOffering.getVendorId())){
									availableFirmIdList.add(serviceOffering.getVendorId());
								}
							}
							//fetching business name of firms
							Map<Long, String> firmNames = getSoSearchDAO().getFirmNames(availableFirmIdList);
							//fetching rating of firms
							Map <Integer,BigDecimal> aggregateRatingMap =  getSoSearchDAO().getAggregateRating(availableFirmIdList);
							searchFirmsResponse.setFirmNames(firmNames);
							searchFirmsResponse.setAggregateRatingMap(aggregateRatingMap);	
						}	
					}
		return searchFirmsResponse;
	}

	/**
	 * @param offerAvailablityMap
	 * @param serviceOffering
	 */
	private void mapFirmAvailabilityDetails(
			Map<Integer, List<AvailableTimeSlotVO>> offerAvailablityMap,
			ServiceOfferingsVO serviceOffering) {
		List<AvailableTimeSlotVO> availableTimeSlotList = null;
		AvailableTimeSlotVO availability = new AvailableTimeSlotVO();
		if (offerAvailablityMap.containsKey(serviceOffering.getServiceOfferingId())){
			availableTimeSlotList = offerAvailablityMap.get(serviceOffering.getServiceOfferingId());
			availability.setDay(serviceOffering.getDay());
			availability.setTimeWindow(serviceOffering.getTimeWindow());
			availableTimeSlotList.add(availability);
		}
		else {
			availableTimeSlotList = new ArrayList<AvailableTimeSlotVO>();
			availability.setDay(serviceOffering.getDay());
			availability.setTimeWindow(serviceOffering.getTimeWindow());
			availableTimeSlotList.add(availability);
			offerAvailablityMap.put(serviceOffering.getServiceOfferingId(), availableTimeSlotList);
		}
	}

	/**
	 * @param firmPriceMap
	 * @param firmOfferingMap
	 * @param serviceOfferingsVO
	 */
	private void mapfirmPriceDetails(
			Map<Integer, ServiceOfferingsVO> firmPriceMap,
			Map<Integer, List<Integer>> firmOfferingMap,
			ServiceOfferingsVO serviceOfferingsVO) {
		//Forming Map of firmId and List of serviceOfferingId
		List<Integer> offeringIdList = null;
		 if (firmOfferingMap.containsKey(serviceOfferingsVO.getVendorId())){
			 offeringIdList = firmOfferingMap.get(serviceOfferingsVO.getVendorId());
			 offeringIdList.add(serviceOfferingsVO.getServiceOfferingId());
		 }
		 else{
			 offeringIdList = new ArrayList<Integer>();
			 offeringIdList.add(serviceOfferingsVO.getServiceOfferingId());
			 firmOfferingMap.put(serviceOfferingsVO.getVendorId(),offeringIdList);
		 }
		//finding zipcode and price list for  serviceOffering
		 //Forming Map of ServiceOfferingId and PriceList
		List<ServiceOfferingPriceVO> zipPriceList = null;
		ServiceOfferingsVO offering = null;
		ServiceOfferingPriceVO priceVO = new ServiceOfferingPriceVO();
		if (firmPriceMap.containsKey(serviceOfferingsVO.getServiceOfferingId())){
			offering = firmPriceMap.get(serviceOfferingsVO.getServiceOfferingId());
			zipPriceList = offering.getServiceOfferingPrice();
			priceVO.setZip(serviceOfferingsVO.getZip());
			priceVO.setPrice(serviceOfferingsVO.getPrice());
			zipPriceList.add(priceVO);
		}
		else {
			offering = new ServiceOfferingsVO();
			offering.setSkuId(serviceOfferingsVO.getSkuId());
			offering.setSku(serviceOfferingsVO.getSku());
			zipPriceList = new ArrayList<ServiceOfferingPriceVO>();
			priceVO.setZip(serviceOfferingsVO.getZip());
			priceVO.setPrice(serviceOfferingsVO.getPrice());
			zipPriceList.add(priceVO);
			offering.setServiceOfferingPrice(zipPriceList);
			firmPriceMap.put(serviceOfferingsVO.getServiceOfferingId(), offering);
		}
	}

	/**
	 * @param searchFirmsVO
	 * @param searchFirmsList
	 * @param availableFirmsList
	 * @return
	 * @throws DataServiceException
	 */
	private List<ServiceOfferingsVO> findAvailableFirms(
			SearchFirmsVO searchFirmsVO,
			List<ServiceOfferingsVO> searchFirmsList)
			throws DataServiceException {
		List<ServiceOfferingsVO> availableFirmsList = null;
		//find the firms which have capacity for the specified service dates
		List<Integer> vendorIdList = new ArrayList<Integer>();
		List<Integer> skuIds = new ArrayList<Integer>();
		for (ServiceOfferingsVO serviceOffering: searchFirmsList){
			if(!vendorIdList.contains(serviceOffering.getVendorId())){
				vendorIdList.add(serviceOffering.getVendorId());
			}
			if(!skuIds.contains(serviceOffering.getSkuId())){
				skuIds.add(serviceOffering.getSkuId());
			}
		}
		searchFirmsVO.setVendorIdList(vendorIdList);
		searchFirmsVO.setSkuIdList(skuIds);
		String timeZone=LocationUtils.getTimeZone(searchFirmsVO.getZip());
		searchFirmsVO.setServiceDate1GMT(getDateinGMT(searchFirmsVO.getServiceDate1(),timeZone));
		searchFirmsVO.setServiceDate2GMT(getDateinGMT(searchFirmsVO.getServiceDate2(),timeZone));
		List<ServiceOfferingsVO> availableFirmSKUList = getSoSearchDAO().getAvailableFirmSKUList(searchFirmsVO);
		//filter searchFirmsList from availableFirmSKUList to obtain availableFirmsList
		if (null!= availableFirmSKUList && !(availableFirmSKUList.isEmpty())){
			Map<Integer,List<Integer>> availableFirmSkuMap = new HashMap<Integer, List<Integer>>();
			
			for (ServiceOfferingsVO serviceOffering:availableFirmSKUList){
				List<Integer> skuIdList = null;
				if(availableFirmSkuMap.containsKey(serviceOffering.getVendorId())){
					skuIdList = availableFirmSkuMap.get(serviceOffering.getVendorId());
					skuIdList.add(serviceOffering.getSkuId());
				}
				else{
					skuIdList = new ArrayList<Integer>();
					skuIdList.add(serviceOffering.getSkuId());
					availableFirmSkuMap.put(serviceOffering.getVendorId(), skuIdList);
				}
			}
			availableFirmsList = new ArrayList<ServiceOfferingsVO>();
			for (ServiceOfferingsVO serviceOffering:searchFirmsList){
				if (availableFirmSkuMap.containsKey(serviceOffering.getVendorId())){
					List<Integer> skuIdList = availableFirmSkuMap.get(serviceOffering.getVendorId());
					if(skuIdList.contains(serviceOffering.getSkuId())){
						availableFirmsList.add(serviceOffering);
					}
				}
			}
		}
		return availableFirmsList;
	}
	
	public static Date getDateinGMT(Date timeZoneDate,String TimeZone){
		Timestamp ts = new Timestamp( timeZoneDate.getTime());
		Date gmtDate= TimeUtils.convertTimeToGMT(ts,TimeZone);
		return gmtDate;
	}
    
	/*public List<String> fetchAvailableZipCodes(String zip,String radius) throws BusinessServiceException{
    	List<String> zipCodeList =null;
    	Integer zipCode = Integer.valueOf(zip);
    	Integer maxRadius = Integer.valueOf(radius);
    	try{
    		zipCodeList = getAvailableZipCodes(zipCode,0,maxRadius);
    	}catch (Exception e) {
    		LOGGER.error("Exception in fetching available zipcode  around the zip"+zipCode+" within the radius"+radius );
			throw new BusinessServiceException(e);
		}
		return zipCodeList;
    }*/
    
	/*private List<String> getAvailableZipCodes(Integer zipCode, Integer minRadius,Integer maxRadius) {
		List<String> zipCodeList =null;
		Result response = invokeZipCodefinder(zipCode,minRadius,maxRadius);
		if(null!= response && null!= response.getDataList() && null!= response.getDataList().getCodeDetails()){
			List<CodeDetails> zipCodeResponse = response.getDataList().getCodeDetails();
			if(!zipCodeResponse.isEmpty()){
				zipCodeList = new ArrayList<String>();
				for(CodeDetails zip:zipCodeResponse){
					zipCodeList.add(zip.getZipCode().toString());
				}
			}
		}
		return zipCodeList;
	}*/
	
	/*private Result invokeZipCodefinder(Integer zipCode, Integer minRadius,Integer maxRadius) {
		Result response =null;
		String responseXml = getZipCodes(zipCode,minRadius,maxRadius);
		LOGGER.info("Response xml"+ responseXml);
		if(StringUtils.isNotBlank(responseXml)){
			if(StringUtils.contains(responseXml, "<Error>")){
				LOGGER.info("Error Response for the zip code finder");
			}else{
				LOGGER.info("Success Response for the zip code finder");
				response = (Result) deserializeLisResponse(responseXml,Result.class);
			}
		}
		return response;
	}*/
	/*public String getZipCodes(Integer zipCode,Integer minRadius, Integer maxRadius){
		WebResource resource;
		Client client = Client.create();
		String zipCodeResponse =null;
		try {
            client.setConnectTimeout(30000);
			client.setReadTimeout(30000);
			String url ="http://api.zip-codes.com/ZipCodesAPI.svc/1.0/xml/FindZipCodesInRadius?";
			if(null!=zipCode){
				url=url.concat("zipcode="+zipCode);
			}else{
				return "<Error>";
			}
            if(null!=maxRadius){
            	url=url.concat("&maximumradius="+maxRadius);
			}
            if(null!= minRadius){
            	url=url.concat("&minimumradius="+minRadius);
            }
			String key ="DEMOAPIKEY";
			String completeUrl= url.concat("&key="+key);
			resource = client.resource(completeUrl);
			zipCodeResponse =resource.accept("application/xml").get(String.class);
		}
		catch(ClientHandlerException ce){
			ce.printStackTrace();
		}
		catch (Exception e) {
			client.destroy();
			e.printStackTrace();
		}				
		return zipCodeResponse;
	}*/
	
	private Object deserializeLisResponse(String responseXml, Class<?> classz){
		XStream xstreamResponse = new XStream(new DomDriver());
		xstreamResponse.processAnnotations(classz);
		Result response = (Result) xstreamResponse.fromXML(responseXml);
		return response;
	}
	
}
