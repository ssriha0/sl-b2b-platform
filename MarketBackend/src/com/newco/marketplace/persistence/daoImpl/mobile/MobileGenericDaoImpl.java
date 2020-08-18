package com.newco.marketplace.persistence.daoImpl.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.assignSO.AssignVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.RescheduleDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.ServiceOrderDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.beans.so.viewDashboard.DashBoardCountVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.criteria.vo.SearchCriteriaVO;
import com.newco.marketplace.api.criteria.vo.SoSearchCriteriaVO;
import com.newco.marketplace.api.criteria.vo.SoStatusVO;
import com.newco.marketplace.api.mobile.beans.vo.ProviderParamVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersCriteriaVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersVO;
import com.newco.marketplace.api.mobile.beans.vo.RequestBidVO;
import com.newco.marketplace.dto.vo.EstimateTaskVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.reasonCode.ReasonCodeVO;
import com.newco.marketplace.dto.vo.serviceorder.FilterCriteriaVO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusInput;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.v2_0.SOAdvanceSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultVO;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.sears.os.dao.impl.ABaseImplDao;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/21/10
 * DaoImpl layer for processing phase 2 mobile APIs 
 *
 */
public class MobileGenericDaoImpl extends ABaseImplDao implements IMobileGenericDao{
	private static final Logger LOGGER = Logger.getLogger(MobileGenericDaoImpl.class);
	public boolean checkIfSOIsActive(String soId)throws DataServiceException {
		String isValidStatus = null;
		try{
			isValidStatus = (String) queryForObject("mobileGeneric.checkIfSOIsActive.query", soId);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericDaoImpl-->checkIfSOIsActiveAccepted()");
			throw new DataServiceException(e.getMessage());
		}
		if(null!=isValidStatus ){
			return true;
		}
		return false;
	}
	public boolean checkIfSOIsInProblem(String soId)throws DataServiceException {
		String isValidStatus = null;
		try{
			isValidStatus = (String) queryForObject("mobileGeneric.checkIfSOIsInProblem.query", soId);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericDaoImpl-->checkIfSOIsInProblem()");
			throw new DataServiceException(e.getMessage());
		}
		if(null!=isValidStatus ){
			return true;
		}
		return false;
	}

	/**
	 * @param searchRequestVO
	 * @return
	 * 
	 * method to fetch service order details matching search criteria
	 */
	public List<SOSearchResultVO> getServiceOrderSearchResults(
			SOSearchCriteriaVO searchRequestVO) throws DataServiceException {

		List<SOSearchResultVO> searchResultVOs = null;
		// Integer totalCountGrouped = null;

		try{				
			// List<String> soIds = searchRequestVO.getSoIds();
			/*if(soIds!= null && !soIds.isEmpty()) {
				// SO id flowto look for bothe grouped and other orders
				searchResultVOs = (List<SOSearchResultVO>)queryForList("getServiceOrderForSearchCriteria.query", searchRequestVO);
				totalCount = (Integer)queryForObject("getCountForSearchCriteria.query", searchRequestVO);

				if(searchResultVOs == null) {
					searchResultVOs = new ArrayList<SOSearchResultVO>();
				}
				if(totalCount == null){
					totalCount = 0;
				}
				searchRequestVO.setCheckGroupedOrders(true);
				List<SOSearchResultVO> groupedSOSearchList = (List<SOSearchResultVO>)queryForList("getServiceOrderForSearchCriteria.query", searchRequestVO);
				totalCountGrouped = (Integer)queryForObject("getCountForSearchCriteria.query", searchRequestVO);
				searchRequestVO.setCheckGroupedOrders(false);

				if(groupedSOSearchList != null && !groupedSOSearchList.isEmpty()) {
					searchResultVOs.addAll(groupedSOSearchList);
					if(null != totalCountGrouped){
						totalCount = totalCount+totalCountGrouped;
					}
				}	
			} else {*/
			//Regular flow
			searchResultVOs	= queryForList("getServiceOrderForSearchCriteria.query", searchRequestVO);
			/*			}
			 */

		} catch (Exception ex) {
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return searchResultVOs;
	}

	/**
	 * @param searchRequestVO
	 * @return
	 */
	public Integer getTotalCountForServiceOrderSearchResults(
			SOSearchCriteriaVO searchRequestVO) throws DataServiceException {
		Integer totalCount = null;
		try{
			totalCount = (Integer)queryForObject("getCountForSearchCriteria.query", searchRequestVO);
		}
		catch(Exception ex){
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return totalCount;
	}
	public List<ServiceOrderStatusVO> getProblemTypeDescription(int problemStatus)throws DataServiceException {
		List<ServiceOrderStatusVO> serviceOrderStatusVOList = null;
		ServiceOrderStatusInput input = new ServiceOrderStatusInput();
		Integer valdiatedStatusIds[] = new Integer[1];
		try{
			valdiatedStatusIds[0]=new Integer(problemStatus);
			input.setStatusIds(valdiatedStatusIds);
			serviceOrderStatusVOList = queryForList("statusProblemSubstatus.query",input);

		}catch(Exception e){
			LOGGER.error("Exception inside getProblemTypeDescription() inside MobileGenericDaoImpl.java : "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return serviceOrderStatusVOList;
	}
	public String getProblemTypeDescription(String soId)throws DataServiceException {
		String problemDescription =null;
		try{
			problemDescription = (String) queryForObject("mobileGeneric.getProblemTypeDescription.query", soId);
		}catch (Exception e) {
			LOGGER.error("Exception inside getProblemTypeDescription() inside MobileGenericDaoImpl.java : "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return problemDescription;
	}
	public RequestBidVO fetchServiceOrderDetailsForBid(RequestBidVO bidVO)
			throws DataServiceException {
		RequestBidVO tempBidVO = null;
		try{
			tempBidVO = (RequestBidVO) queryForObject("mobileGeneric.fetchServiceOrderDetailsForBid.query", bidVO.getSoId());
			bidVO.setBuyerId(tempBidVO.getBuyerId());
			bidVO.setServiceDateStart(tempBidVO.getServiceDateStart());
			bidVO.setServiceDateEnd(tempBidVO.getServiceDateEnd());
			bidVO.setServiceTimeEnd(tempBidVO.getServiceTimeEnd());
		}catch (Exception e) {
			LOGGER.error("Exception inside fetchServiceOrderDetailsForBid() inside MobileGenericDaoImpl.java : "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return bidVO;
	}
	/**@Description: This method fetches all the reasonCodes available
	 * @param 
	 * @return
	 */
	public List<String> getProviderRespReason()throws DataServiceException {
		List<String> reasonlist = null;
		try {
			reasonlist = queryForList("mobileGeneric.getReaonCodeType");
		} catch (Exception exception) {
			throw new DataServiceException("MobileGenericDao - mobileGenericDao.reason query  for reason_codes  table Failed", exception);
		}
		return reasonlist;
	}
	/**@Description: This method fetches  reasonCodes specified by provider
	 * @param  reasonType
	 * @return 
	 */
	public List<ReasonCodeVO>  getRespReasonCodes(String reasonType)throws DataServiceException
	{
		ArrayList<ReasonCodeVO> reasonlist = null;
		if(reasonType.equalsIgnoreCase("TypeofProblem")){
			try {
				reasonlist = (ArrayList) queryForList("mobileGeneric.typeOfProblem.query");
			} catch (Exception exception) {
				throw new DataServiceException("MobileGenericDao - mobileGenericDao.typeOfProbReason query  for wf_states  table Failed", exception);
			}
		}
		else if(reasonType.equalsIgnoreCase("Reject")||reasonType.equalsIgnoreCase("ReleaseByprovider")|| reasonType.equalsIgnoreCase("ReleasebyFirm") )
		{
			try {
				reasonlist = (ArrayList) queryForList("mobileGeneric.providerResponse", reasonType);
			} catch (Exception exception) {
				throw new DataServiceException("MobileGenericDao - mobileGeneric.providerResponse query  for lu_mobile_actions  table Failed", exception);
			}

		}
		else if(reasonType.equalsIgnoreCase("Reschedule") ){
			List<Integer> codeList = Arrays.asList(OrderConstants.REASON_LIST);
			try {
				reasonlist = (ArrayList) queryForList("mobileGeneric.Rechedulereason", codeList);
			} catch (Exception exception) {
				throw new DataServiceException("MobileGenericDao - mobileGeneric.Rechedulereason query  for lu_mobile_actions  table Failed", exception);
			}

		}
		else if(reasonType.equalsIgnoreCase("CounterOfferPrice") || reasonType.equalsIgnoreCase("CounterOfferReschedule") )
		{
			try {
				reasonlist = (ArrayList) queryForList("mobileGeneric.counterOffer", reasonType);
			} catch (Exception exception) {
				throw new DataServiceException("MobileGenericDao - mobileGeneric.counterOffer query  for lu_mobile_actions  table Failed", exception);
			}

		}
		else if(reasonType.equalsIgnoreCase("PreCallConfirmScedule") || (reasonType.equalsIgnoreCase("PreCallCustNotAvailable")))
		{
			try {
				if(reasonType.equalsIgnoreCase("PreCallConfirmScedule")){
					reasonType ="CUST_RESPONSE";
				}else{
					reasonType = "CUST_NOT_AVAILABLE_REASON";
				}
				reasonlist = (ArrayList) queryForList("mobileGeneric.reasonOM", reasonType);
			} catch (Exception exception) {
				throw new DataServiceException("MobileGenericDao - mobileGeneric.reasonOM query  for lu_mobile_actions  table Failed", exception);
			}

		}
		else if(reasonType.equalsIgnoreCase("IncreaseLabor")|| reasonType.equalsIgnoreCase("IncreasePart"))
		{
			try {
				reasonlist = (ArrayList)queryForList("mobileGeneric.completionReason", reasonType);
			} catch (Exception exception) {
				throw new DataServiceException("MobileGenericDao - mobileGeneric.mobileGeneric.completionReason query  for lu_mobile_actions  table Failed", exception);
			}

		}else if(reasonType.equalsIgnoreCase("WarrantyHome")){

			try {
				reasonlist = (ArrayList) queryForList("mobileGeneric.warrrantyHomeReason", reasonType);
			} catch (Exception exception) {
				throw new DataServiceException("MobileGenericDao - mobileGeneric.warrrantyHomeReason query  for lu_mobile_actions  table Failed", exception);
			}
		}
		else
		{
			throw new IllegalArgumentException("Invalid reason code type: " + reasonType);
		}
		return reasonlist;

	}
	/**
	 * Method to get the ruoted resources under the firm.
	 */
	public List<Integer> getRoutedResourcesUnderFirm(AssignVO assignVO)throws DataServiceException{
		List<Integer> routedResourcesUnderFirm = null;
		try{
			routedResourcesUnderFirm = queryForList("getRoutedResourcesUnderFirm.query",assignVO);
		}
		catch(Exception ex){
			LOGGER.error("Exception Occured in MobileGenericDaoImpl-->getRoutedResourcesUnderFirm()");
			throw new DataServiceException(ex.getMessage());
		}
		return routedResourcesUnderFirm;
	}
	public boolean checkIfReasonIsValid(String reasonCode)throws DataServiceException {
		String result =null;
		try{
			result = (String)queryForObject("mobileGeneric.checkIfReasonIsValid.query", reasonCode);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericDaoImpl-->checkIfReasonIsValid()");
			throw new DataServiceException(e.getMessage());
		}
		if(null!=result){
			return true;
		}else{
			return false;
		}
	}
	public RescheduleVO checkIfRescheduleRequestExists(RescheduleVO rescheduleVo) throws DataServiceException {
		RescheduleVO loggingVo =null;
		Map parameterMap = new HashMap();
		parameterMap.put("soId", rescheduleVo.getSoId());
		parameterMap.put("actionId", MPConstants.RESCHEDUL_LOG_ID);
		try{
			loggingVo = (RescheduleVO) queryForObject("mobileGeneric.fetchLatestRescheduleLog.query",parameterMap);
		}catch (Exception e) {
			LOGGER.error("Exception in getting latest reschedule log"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return loggingVo;
	}
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException 
	 */
	public String checkIfReadyForConfirmAppointment(String soId) throws DataServiceException {
		String soIdFetched = null;
		try{
			soIdFetched = (String)queryForObject("mobileGeneric.checkIfReadyForConfirmAppointment.query", soId);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericDaoImpl-->checkIfReadyForConfirmAppointment():"+soId);
			e.printStackTrace();
			throw new DataServiceException(e.getMessage());
		}
		return soIdFetched;
	}
	/**
	 * @param soId
	 * @return
	 *
	 */
	public String checkIfReadyForPreCall(String soId) throws DataServiceException {
		String soIdFetched = null;
		try{
			soIdFetched = (String)queryForObject("mobileGeneric.checkIfReadyForPreCall.query", soId);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericDaoImpl-->checkIfReadyForPreCall():"+soId);
			e.printStackTrace();
			throw new DataServiceException(e.getMessage());
		}
		return soIdFetched;
	}
	/**
	 * @param soId
	 * @return
	 */
	public ServiceOrder getServiceOrderAssignmentAndScheduleDetails(String soId) throws DataServiceException  {
		ServiceOrder serviceOrder = null;
		try{
			serviceOrder = (ServiceOrder)queryForObject("mobileGeneric.getServiceOrderAssignmentAndScheduleDetails.query", soId);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in MobileGenericDaoImpl-->getServiceOrderAssignmentAndScheduleDetails():"+soId);
			e.printStackTrace();
			throw new DataServiceException(e.getMessage());
		}
		return serviceOrder;
	}
	public Integer getServiceOrderStatus(String soId) throws DataServiceException{
		Integer wfStateId = null;
		try{
			wfStateId = (Integer)queryForObject("getSoStatusData.query",soId);	
		}catch (Exception e) {
			LOGGER.error("Exception in fetching service order status"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return wfStateId;

	}


	/**
	 * @param groupIdsList
	 * @return
	 * 
	 * to get child orders for the group
	 */
	public List<SOSearchResultVO> getChildOrdersForGroup(
			List<String> groupIdsList,Integer firmId) throws DataServiceException{
		List<SOSearchResultVO> searchResultVOs = null;
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("firmId", firmId);
		params.put("groupIdsList", groupIdsList);

		try{				
			if(groupIdsList!= null && !groupIdsList.isEmpty()) {
				searchResultVOs = queryForList("mobilegeneric.fetchChildOrdersforGroup", params);
			}
		}
		catch (Exception e) {
			LOGGER.error("Exception in getChildOrdersForGroup");
			e.printStackTrace();
			throw new DataServiceException(e.getMessage());
		}
		return searchResultVOs;

	}
	public boolean isGroupedOrderInEditMode(String groupId)throws DataServiceException {
		boolean isSOInEditMode = false;
		Integer lockedinEdit =null;
		try{
			lockedinEdit = (Integer) queryForObject("getLockedInEditForGroup.query", groupId);
			if(null!= lockedinEdit && (OrderConstants.SO_EDIT_MODE_FLAG == lockedinEdit.intValue())){
				isSOInEditMode =true;
			}
		}catch (Exception e) {
			LOGGER.error("Exception in getting lockforEdit for grouped order"+ groupId + "error"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isSOInEditMode;
	}

	/**
	 * Delete Mobile filter flow
	 * @param resourceId
	 * @param filterId
	 * @throws BusinessServiceException
	 */
	public int deleteFilter(Integer resourceId, String filterId) throws DataServiceException{
		int deletedFilters = 0;
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("resourceId", resourceId);
		params.put("filterId", filterId);

		try{
			deletedFilters = delete("deleteMobileFilter.delete", params);
		}catch (Exception e) {
			LOGGER.error("Exception occured while deleting a filter:"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return deletedFilters;
	}
	/**@Description: getting count of service orders satisfying criteria.
	 * @param providerParamVO
	 * @return Integer
	 * @throws DataServiceException
	 */
	public Integer validateCountOfRecords(ProviderParamVO providerParamVO)throws DataServiceException {
		Integer countOfRecords = 0;
		try{
			countOfRecords = (Integer) queryForObject("providerSOCountMobile.query", providerParamVO);
		}catch (Exception e) {
			LOGGER.error("Exception occured while fetching count of service orders:"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return countOfRecords;
	}
	/**Fetching List of service orders available for providers
	 * @param providerParamVO
	 * @return List<ProviderSOSearchVO>
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderSOSearchVO> getProviderSOList(ProviderParamVO providerParamVO) throws DataServiceException {
		List<ProviderSOSearchVO> providerSOSearchVOs =null;
		try{
			providerSOSearchVOs =queryForList("providerSOListMobile.query",providerParamVO);
		}catch (Exception e) {
			LOGGER.error("Exception occured while fetching service orders:"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return providerSOSearchVOs;
	}

	/**Fetching filter details associated with a provider
	 * @param providerId
	 * @return List <FiltersVO>
	 * @throws DataServiceException
	 */
	public List<FiltersVO> getSearchFilterDetails(Integer providerId)throws DataServiceException{
		
		List<FiltersVO> filters = null;
		
		try {
			filters = queryForList("query.get.search.filter", providerId);
			
		}catch(Exception e){
			logger.debug("Exception in saving search filter"+e.getMessage());
			throw new DataServiceException(e.getMessage()); 
		}
		return filters;
	}


	
	
	/**
	 * @param criteriaVO
	 * @return recievedOrdersCount
	 * @throws DataServiceException
	 */
	public Integer getRecievedOrdersCount(RecievedOrdersCriteriaVO criteriaVO) throws DataServiceException{
		Integer recievedOrdersCount=0;
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("firmId", criteriaVO.getFirmId());
		if(!criteriaVO.isAdmin()){
			params.put("resourceId", criteriaVO.getResourceId());
		}else{
			params.put("resourceId", null);
		}
		// Set BidOnly Ind as true after checking in VO, else set as NULL
        if(criteriaVO.isBidOnlyInd()){
             params.put("bidOnlyInd", true);
        }else{
             params.put("bidOnlyInd", null);
        }
		try{
			recievedOrdersCount =(Integer) queryForObject("getRecievedOrdersCount.query",params);
		}catch (Exception e) {
			LOGGER.error("Exception occured while getting recieved orders count:"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return recievedOrdersCount;

	}
	
	/**
	 * @param criteriaVO
	 * @return List<RecievedOrdersVO>
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<RecievedOrdersVO> getRecievedOrdersList(RecievedOrdersCriteriaVO criteriaVO) throws DataServiceException{
		List<RecievedOrdersVO> recievedOrdersList = null;
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("firmId", criteriaVO.getFirmId());
		params.put("pageLimit", criteriaVO.getPageLimit());
		params.put("pageSize", criteriaVO.getPageSize());
		if(!criteriaVO.isAdmin()){
			params.put("resourceId", criteriaVO.getResourceId());
		}else{
			params.put("resourceId", null);
		}
		// Set BidOnly Ind as true after checking in VO, else set as NULL
        if(criteriaVO.isBidOnlyInd()){
             params.put("bidOnlyInd", true);
        }else{
             params.put("bidOnlyInd", null);
        }
		try{
			 if(criteriaVO.isBidOnlyInd()){
                 recievedOrdersList = queryForList("getBidOrders.query",params);
			 }else{
                 recievedOrdersList = queryForList("getRecievedOrders.query",params);
			 }
		}catch (Exception e) {
			LOGGER.error("Exception occured while getting recieved orders list:"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return recievedOrdersList;
	}
	/**
	 * Method to check whether resource is authorized to view SO details in received status
	 * @param detailsVO
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAuthorizedToViewSOInPosted(SoDetailsVO detailsVO)throws DataServiceException {
		int count = 0;
		boolean isAuthorized = Boolean.FALSE;
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("soId", detailsVO.getSoId());
		param.put("resourceId", detailsVO.getProviderId());
		param.put("firmId", detailsVO.getFirmId());
		try{
			if(MPConstants.ROLE_LEVEL_TWO.equals(detailsVO.getRoleId())){
				count = (Integer) queryForObject("isAuthToViewPostedSORoleTwo.query",param);
				if (count > 0) {
					isAuthorized = true;
				}
			}
			else if(MPConstants.ROLE_LEVEL_THREE.equals(detailsVO.getRoleId())){
				count = (Integer) queryForObject("isAuthToViewPostedSORoleThree.query",param);
				if (count > 0) {
					isAuthorized = true;
				}
			}
		}
		catch(Exception e){
			LOGGER.error("Exception occured in isAuthorizedToViewSOInPosted():"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isAuthorized;
	}
	/**
	 * Method to check whether resource is authorized to view SO details in accepted,active,problem status
	 * @param detailsVO
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAuthorizedToViewBeyondPosted(SoDetailsVO detailsVO)throws DataServiceException {
		int count = 0;
		boolean isAuthorized = Boolean.FALSE;
		AssignVO assignVO = new AssignVO();	
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("soId", detailsVO.getSoId());
		param.put("resourceId", detailsVO.getProviderId());
		param.put("firmId", detailsVO.getFirmId());
		Integer firmId = 0;
		if(!StringUtils.isEmpty(detailsVO.getFirmId())){
			firmId = Integer.parseInt(detailsVO.getFirmId());
		}
		try{
			if(MPConstants.ROLE_LEVEL_ONE.equals(detailsVO.getRoleId())){
				count = (Integer) queryForObject("isAuthToViewSORoleTwo.query",param);
				if (count > 0) {
					isAuthorized = true;
				}
			}
			else if(MPConstants.ROLE_LEVEL_TWO.equals(detailsVO.getRoleId())){
				assignVO =(AssignVO) queryForObject("getProviderSODetails.query",detailsVO.getSoId());
				if(null != assignVO.getAcceptedVendorId() && assignVO.getAcceptedVendorId().equals(firmId)){
					if(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM.equals(assignVO.getAssignmentType())){
						count = (Integer) queryForObject("isAuthToViewPostedSORoleTwo.query",param);
						if (count > 0) {
							isAuthorized = true;
						}					
					}
					else if(OrderConstants.SO_ASSIGNMENT_TYPE_PROVIDER.equals(assignVO.getAssignmentType())){
						count = (Integer) queryForObject("isAuthToViewSORoleTwo.query",param);
						if (count > 0) {
							isAuthorized = true;
						}
					}
				}
			}
			else if(MPConstants.ROLE_LEVEL_THREE.equals(detailsVO.getRoleId())){
				count = (Integer) queryForObject("isAuthToViewSORoleThree.query",param);
				if (count > 0) {
					isAuthorized = true;
				}
			}
		}
		catch(Exception e){
			LOGGER.error("Exception occured in isAuthorizedToViewBeyondPosted():"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isAuthorized;
	}
	/**
	 * Method to fetch the service order details for mobile version 3.0
	 * @param detailsVO
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrderDetailsVO getServiceOrderDetailsWithTrip(SoDetailsVO detailsVO) throws DataServiceException {
		ServiceOrderDetailsVO orderDetailsVO = null;
		Map<String, Object> param = new HashMap<String, Object>();
		try{
			if(null!=detailsVO.getSoId()&&null!= detailsVO.getProviderId()){
				param.put("soId", detailsVO.getSoId());
				param.put("resourceId", detailsVO.getProviderId());
				orderDetailsVO = (ServiceOrderDetailsVO)queryForObject("getServiceOrderDetailsWithReceived.query", param);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception occured in getServiceOrderDetailsWithTrip(detailsVO):"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return orderDetailsVO;
	}
	public Integer getTotalCountForServiceOrderAdvanceSearchResults(
			SOAdvanceSearchCriteriaVO searchRequestVO)  throws DataServiceException {
		Integer totalCount = null;
		try{
			totalCount = (Integer)queryForObject("getCountForAdvanceSearchCriteria.query", searchRequestVO);
		}
		catch(Exception ex){
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return totalCount;
	}
	public List<SOSearchResultVO> getServiceOrderAdvanceSearchResults(
			SOAdvanceSearchCriteriaVO searchRequestVO)   throws DataServiceException {

		List<SOSearchResultVO> searchResultVOs = null;

		try{				

			searchResultVOs	= queryForList("getServiceOrderForAdvanceSearchCriteria.query", searchRequestVO);

		} catch (Exception ex) {
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return searchResultVOs;
	}
	/**
	 * method to get the wfstateId,routed resources,and assignment type of the SO
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public AssignVO getProviderSODetails(String soId)throws DataServiceException {
		AssignVO assignVO = null;
		try{
		assignVO = (AssignVO)queryForObject("getProviderSODetails.query",soId);
		}
		catch(Exception e){
			LOGGER.error("Exception occured in getProviderSODetails(soId):"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return assignVO;
	}

	public void saveFilter(FiltersVO filtersVO) throws DataServiceException{
		Integer filterId = null;
		List<FilterCriteriaVO> criteriaList = null;
		
		try{
			//Save new filter
			if(null == filtersVO.getFilterId()){
				//insert filters
				filterId = (Integer) insert("saveFilter.insert", filtersVO);
				//get criteria list updated with the new filter id
				if(null != filterId && filterId.intValue()>0){
					criteriaList = getCriteriaListToInsert(filterId, filtersVO.getCriteriaList());
				}
			}else{//update an existing filter
				//delete all child records
				delete("deleteFilterCriteria.delete",filtersVO.getFilterId());
				//update parent table
				update("saveFilter.update",filtersVO);
				//get the criteria list from vo
				criteriaList = filtersVO.getCriteriaList();
			}
			//insert into child table
			if(null != criteriaList && criteriaList.size()>0){
				batchInsert("saveFilter.criteria.insert", criteriaList);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new DataServiceException(e.getMessage());
		}
	}
	
	private List<FilterCriteriaVO> getCriteriaListToInsert(Integer filterId, List<FilterCriteriaVO> criteriaList){
		if(null != criteriaList && criteriaList.size()>0){
			for(FilterCriteriaVO criteria: criteriaList){
				criteria.setFilterId(filterId);
			}
		}
		return criteriaList;
	}
	
	public boolean isFilterExists(Integer resourceId, String filterName) throws DataServiceException{
		boolean isFilterExists = false;
		int count = 0;
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("resourceId", resourceId);
		param.put("filterName", filterName);
		count = (Integer) queryForObject("getFilterCount.query",param);
		
		if(count >0){
			isFilterExists = true;
		}
		
		return isFilterExists;
	}
	public MobileDashboardVO getDashboardCount(MobileDashboardVO dashboardVO)throws DataServiceException {
		List<DashBoardCountVO> countVO =null;
		try{
			countVO = queryForList("getDashboardCount.query",dashboardVO);
		}catch (Exception e) {
        	LOGGER.error("error occured in MobileGenericBOImpl.getDashboardCount()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		if(null != countVO && !countVO.isEmpty()){
			dashboardVO.setCountVO(countVO);
		}
		return dashboardVO;
	}
	/**
	 * Method to fetch the search criteria's like market,service providers,status and subStatus
	 * @param roleId
	 * @param resourceId
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public SoSearchCriteriaVO getSearchCriteria(Integer roleId,Integer resourceId, Integer vendorId) throws DataServiceException {

		Map<String, Object> param = new HashMap<String, Object>();
		List<SearchCriteriaVO> searchCriteriaVOs = null;
		List<SoStatusVO>soStatusVOs = null;
		List<Integer> wfStatusIdList =  new ArrayList<Integer>();
		SoSearchCriteriaVO  soSearchCriteriaVO = null;
		if(MPConstants.ROLE_LEVEL_ONE.equals(roleId)){
			wfStatusIdList = Arrays.asList(MPConstants.STATUS_ARRAY_WITH_OUT_RECIEVED);
		}
		else if(MPConstants.ROLE_LEVEL_TWO.equals(roleId)||MPConstants.ROLE_LEVEL_THREE.equals(roleId)){
			wfStatusIdList = Arrays.asList(MPConstants.STATUS_ARRAY_WITH_RECIEVED);
		}

		param.put("roleId", roleId);
		param.put("resourceId", resourceId);
		param.put("vendorId",vendorId);
		param.put("wfStatusIdList", wfStatusIdList);
		try{
			soStatusVOs =  queryForList("getSoSearchCriteriaStatus.query",param);
			searchCriteriaVOs = queryForList("getSoSearchCriteria.query", param);
			soSearchCriteriaVO = new SoSearchCriteriaVO();
			soSearchCriteriaVO.setSoStatusVOs(soStatusVOs);
			soSearchCriteriaVO.setSearchCriteriaVOs(searchCriteriaVOs);
		}
		catch(Exception e){
			LOGGER.error("Exception occured in getSearchCriteria():"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return soSearchCriteriaVO;
	}
	public String getVendorIdFromVendorResource(String resourceId)throws DataServiceException{
		String vendorId = null;
		try{
			vendorId = (String) queryForObject("getVendorIdFromVendorResource.query", resourceId);
		}catch (Exception e) {
			LOGGER.error("Exception inside getVendorIdFromVendorResource() inside MobileGenericDaoImpl.java : "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return vendorId;
	}
	
	public RescheduleDetailsVO getRescheduleDetailsForSO(String soId) throws DataServiceException{
		RescheduleDetailsVO rescheduleDetailsVO = null;
		Map parameterMap = new HashMap();
		parameterMap.put("soId", soId);
		parameterMap.put("actionId", MPConstants.RESCHEDUL_LOG_ID);
		try{
			rescheduleDetailsVO = (RescheduleDetailsVO)queryForObject("getRescheduleSoDetails.query",parameterMap);
		}
		catch(Exception e){
			LOGGER.error("Exception occured in getRescheduleDetailsForSO(soId):"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return rescheduleDetailsVO;
	}
	
	/**
	 * This method gets the counter offered routed providers for a specific SO and provider firm, 
	 * @param String soId, String vendorId
	 * @return List <ProviderResultVO> 
	 * throws DataServiceException
	 */
	public List<ProviderResultVO> getCounteredResourceDetailsList(String soId, String firmId) throws DataServiceException{
		List<ProviderResultVO> counteredResourceDetailsList = null;
		
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soId);
			param.put("vendorId", firmId);
			counteredResourceDetailsList = queryForList("getCounteredResourceDetailsList.query", param);
			
		} catch (Exception ex) {
			logger.error("[MobileGenericDaoImpl.getCounteredResourceDetailsList - Exception] ", ex);
			throw new DataServiceException("Error getting countered offered resources for firm", ex);
		}
		return counteredResourceDetailsList;
	}
	
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public ProblemResolutionSoVO getProblemDesc(String soId)
			throws DataServiceException {
		ProblemResolutionSoVO problemResolutionSoVO = null;

		try{
			problemResolutionSoVO = (ProblemResolutionSoVO)queryForObject("getMobileSOProblemDetails.query",soId);
		}
		catch(Exception e){
			LOGGER.error("Exception occured in getProblemDesc(soId):"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return problemResolutionSoVO;
	}
	
	/***@Description: This method will either add or update a an Estimate for the service order
	 * @param estimateVO
	 * @param estimationId
	 * @return
	 * @throws DataServiceException
	 */
	public EstimateVO saveEstimationDetails(EstimateVO estimationVO,Integer estimationId) throws DataServiceException{	
			List<EstimateTaskVO> laborTasksList = null;
			List<EstimateTaskVO> partTasksList = null;
			List<EstimateTaskVO> otherEstimateServicesList=null;
			Integer estimationHistoryId=null;
			try{
				//When new estimate is inserted
				estimationId=estimationVO.getEstimationId();
				if(null == estimationId){	
					//insert new records for this estimationId in 'so_estimation' table
					estimationId = (Integer) insert("saveEstimation.insert", estimationVO);
					estimationVO.setEstimationId(estimationId);
					estimationVO.setAction("INSERTED");
					estimationHistoryId = (Integer) insert("saveEstimationHistory.insert", estimationVO);					
					laborTasksList=estimationVO.getEstimateTasks();
					partTasksList=estimationVO.getEstimateParts();
					otherEstimateServicesList=estimationVO.getEstimateOtherEstimateServices();
					//TODO to map estimation id -check estimation id is null/not
					mapEstimationIdToItems(laborTasksList,partTasksList,otherEstimateServicesList,estimationId,estimationHistoryId);
					//insert new records for this estimationId in 'so_estimation_items' table 
					if(null != laborTasksList && !laborTasksList.isEmpty()){
						for(EstimateTaskVO estimateTaskVO: laborTasksList){
						Integer itemId=(Integer) insert("saveEstimationLaborItems.insert", estimateTaskVO);
						estimateTaskVO.setItemId(itemId);
						estimateTaskVO.setAction("INSERTED");
						insert("saveEstimationLaborItemsHistory.insert", estimateTaskVO);
						}
					}
					
					if(null != partTasksList && !partTasksList.isEmpty()){
						for(EstimateTaskVO estimateTaskVO: partTasksList){
						Integer itemId=(Integer) insert("saveEstimationPartItems.insert", estimateTaskVO);
						estimateTaskVO.setItemId(itemId);
						estimateTaskVO.setAction("INSERTED");
						insert("saveEstimationPartItemsHistory.insert", estimateTaskVO);
						}
					}
					//SL-21385 -Adding other estimate services
					if(null != otherEstimateServicesList && !otherEstimateServicesList.isEmpty()){
						for(EstimateTaskVO estimateTaskVO: otherEstimateServicesList){
						Integer itemId=(Integer) insert("saveEstimationOtherEstimateServiceItems.insert", estimateTaskVO);
						estimateTaskVO.setItemId(itemId);
						estimateTaskVO.setAction("INSERTED");
						insert("saveEstimationOtherEstimateServiceItemsHistory.insert", estimateTaskVO);
						} 
					}
					//set variable to indicate success
					estimationVO.setAddEditestimationId(estimationId);
					estimationVO.setEstimateAdded(true);
			} else {

				EstimateVO existingEstimate = null;
				boolean estimateUpdate = false;
				boolean estimatePriceChange = false;
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("soId", estimationVO.getSoId());
				param.put("estimateId", estimationId);
				existingEstimate = (EstimateVO) queryForObject("fetchEstimate.query", param);

				if (null != existingEstimate) {
					
					//SL-21902-wrong webhook when estimate is submitted from draft
					if (("NEW").equals(estimationVO.getStatus()) && ("DRAFT").equals(existingEstimate.getStatus())) {
						estimationVO.setEstimateUpdatedFromDraftTONew(true);
					}
					
					if (StringUtils.isEmpty(estimationVO.getStatus())) {
						estimationVO.setStatus(existingEstimate.getStatus());
					}
					//SL-21908
					if(null == estimationVO.getVendorId()){
						estimationVO.setVendorId(existingEstimate.getVendorId());
						estimationVO.setResourceId(existingEstimate.getResourceId());
					}
					
					// SL-21654 : Do not change status or save estimate if no elements are changed.
					boolean diff = differenceExist(existingEstimate, estimationVO);
					if (!diff){
						// no diff 
						logger.info("No difference with existing and new estimate data");
						logger.debug("No difference with existing and new estimate data for estimation id: " + estimationId);
						estimationVO.setAddEditestimationId(estimationId);
						estimationVO.setEstimateUpdate(false);
						estimationVO.setEstimatePriceChange(false);
						return estimationVO;
					}
					
					// SL-21654 - Status should be changed from declined to updated
					if("soReleased".equals(estimationVO.getStatus())){
						estimationVO.setComments("SO is released from firm");
						estimationVO.setStatus("DECLINED");
					}else if (("ACCEPTED").equals(existingEstimate.getStatus()) || ("DECLINED").equals(existingEstimate.getStatus())) {
						estimationVO.setStatus("UPDATED");
					} else if (("NEW").equals(existingEstimate.getStatus()) && ("DRAFT").equals(estimationVO.getStatus())) {
						estimationVO.setStatus("NEW");
					}
					
					if (null == estimationVO.getEstimationExpiryDate()) {
						estimationVO.setEstimationExpiryDate(existingEstimate.getEstimationExpiryDate());
					}

					if (estimationVO.compareEstimation(existingEstimate) != 0) {
						estimateUpdate = true;
					}
					if (estimationVO.comparePrice(existingEstimate) != 0) {
						estimatePriceChange = true;
					}
					update("updateEstimateDetails.update", estimationVO);
					estimationVO.setAction("UPDATED");
					if (null == estimationVO.getCreatedBy())
						estimationVO.setCreatedBy(existingEstimate.getCreatedBy());

					estimationHistoryId = (Integer) insert("saveEstimationHistory.insert", estimationVO);
					List<EstimateTaskVO> existingLaborTasksList = null;
					List<EstimateTaskVO> existingPartTasksList = null;
					List<EstimateTaskVO> existingOtherEstimateServicesList = null;
					existingLaborTasksList = existingEstimate.getEstimateTasks();
					existingPartTasksList = existingEstimate.getEstimateParts();
					existingOtherEstimateServicesList = existingEstimate.getEstimateOtherEstimateServices();
					// considering sequence number as unique identifier
					List<Integer> laborItemIdList = new ArrayList<Integer>();
					List<Integer> partItemIdList = new ArrayList<Integer>();
					List<Integer> otherServicesItemIdList = new ArrayList<Integer>();
					List<Integer> existingLaborItemIdList = new ArrayList<Integer>();
					List<Integer> existingPartItemIdList = new ArrayList<Integer>();
					List<Integer> existingOtherServicesItemIdList = new ArrayList<Integer>();
					Map<Integer, EstimateTaskVO> existingLaborItemMap = new HashMap<Integer, EstimateTaskVO>();
					Map<Integer, EstimateTaskVO> existingPartItemMap = new HashMap<Integer, EstimateTaskVO>();
					Map<Integer, EstimateTaskVO> existingOtherServicesItemMap = new HashMap<Integer, EstimateTaskVO>();
					laborTasksList = estimationVO.getEstimateTasks();
					partTasksList = estimationVO.getEstimateParts();
					otherEstimateServicesList = estimationVO.getEstimateOtherEstimateServices();
					mapEstimationIdToItems(laborTasksList, partTasksList, otherEstimateServicesList, estimationId, estimationHistoryId);
					if (null != existingLaborTasksList && !existingLaborTasksList.isEmpty()) {
						for (EstimateTaskVO estimateTaskVO : existingLaborTasksList) {
							existingLaborItemMap.put(estimateTaskVO.getTaskSeqNumber(), estimateTaskVO);
						}
					}

					if (null != existingPartTasksList && !existingPartTasksList.isEmpty()) {
						for (EstimateTaskVO estimateTaskVO : existingPartTasksList) {
							existingPartItemMap.put(estimateTaskVO.getPartSeqNumber(), estimateTaskVO);
						}
					}

					if (null != existingOtherEstimateServicesList && !existingOtherEstimateServicesList.isEmpty()) {
						for (EstimateTaskVO estimateTaskVO : existingOtherEstimateServicesList) {
							existingOtherServicesItemMap.put(estimateTaskVO.getOtherServiceSeqNumber(), estimateTaskVO);
						}
					}
					existingLaborItemIdList = new ArrayList<Integer>(existingLaborItemMap.keySet());
					existingPartItemIdList = new ArrayList<Integer>(existingPartItemMap.keySet());
					existingOtherServicesItemIdList = new ArrayList<Integer>(existingOtherServicesItemMap.keySet());

					// insert new records for this estimationId in
					// 'so_estimation_items' table
					if (null != laborTasksList && !laborTasksList.isEmpty()) {
						for (EstimateTaskVO estimateTaskVO : laborTasksList) {
							laborItemIdList.add(estimateTaskVO.getTaskSeqNumber());
							if (!existingLaborItemIdList.contains(estimateTaskVO.getTaskSeqNumber())) {
								Integer itemId = (Integer) insert("saveEstimationLaborItems.insert", estimateTaskVO);
								estimateTaskVO.setItemId(itemId);
								estimateTaskVO.setAction("INSERTED");
								insert("saveEstimationLaborItemsHistory.insert", estimateTaskVO);
								estimateUpdate = true;
								estimatePriceChange = true;
							} else {
								boolean itemChange = false;
								EstimateTaskVO existingLaborItem = existingLaborItemMap.get(estimateTaskVO.getTaskSeqNumber());
								if (estimateTaskVO.compareItem(existingLaborItem) != 0) {
									estimateUpdate = true;
									itemChange = true;
								}
								if (estimateTaskVO.comparePrice(existingLaborItem) != 0) {
									estimatePriceChange = true;
									itemChange = true;
								}
								if (itemChange) {
									update("updateEstimationLaborItems.insert", estimateTaskVO);
									estimateTaskVO.setItemId(existingLaborItem.getItemId());
									estimateTaskVO.setAction("UPDATED");
									insert("saveEstimationLaborItemsHistory.insert", estimateTaskVO);
								}
							}

						}
					}
					if (null != partTasksList && !partTasksList.isEmpty()) {
						for (EstimateTaskVO estimateTaskVO : partTasksList) {
							partItemIdList.add(estimateTaskVO.getPartSeqNumber());

							if (!existingPartItemIdList.contains(estimateTaskVO.getPartSeqNumber())) {
								Integer itemId = (Integer) insert("saveEstimationPartItems.insert", estimateTaskVO);
								estimateTaskVO.setItemId(itemId);
								estimateTaskVO.setAction("INSERTED");
								insert("saveEstimationPartItemsHistory.insert", estimateTaskVO);
								estimateUpdate = true;
								estimatePriceChange = true;
							} else {
								boolean itemChange = false;
								EstimateTaskVO existingPartItem = existingPartItemMap.get(estimateTaskVO.getPartSeqNumber());
								if (estimateTaskVO.compareItem(existingPartItem) != 0) {
									estimateUpdate = true;
									itemChange = true;
								}
								if (estimateTaskVO.comparePrice(existingPartItem) != 0) {
									estimatePriceChange = true;
									itemChange = true;

								}
								if (itemChange) {
									update("updateEstimationPartItems.insert", estimateTaskVO);
									estimateTaskVO.setItemId(existingPartItem.getItemId());
									estimateTaskVO.setAction("UPDATED");
									insert("saveEstimationPartItemsHistory.insert", estimateTaskVO);
								}
							}

						}
					}
					// SL-21385 -Adding other estimate services
					if (null != otherEstimateServicesList && !otherEstimateServicesList.isEmpty()) {
						for (EstimateTaskVO estimateTaskVO : otherEstimateServicesList) {
							otherServicesItemIdList.add(estimateTaskVO.getOtherServiceSeqNumber());
							if (!existingOtherServicesItemIdList.contains(estimateTaskVO.getOtherServiceSeqNumber())) {
								Integer itemId = (Integer) insert("saveEstimationOtherEstimateServiceItems.insert", estimateTaskVO);
								estimateTaskVO.setItemId(itemId);
								estimateTaskVO.setAction("INSERTED");
								insert("saveEstimationOtherEstimateServiceItemsHistory.insert", estimateTaskVO);
								estimateUpdate = true;
								estimatePriceChange = true;
							} else {
								boolean itemChange = false;
								EstimateTaskVO existingOtherServicesItem = existingOtherServicesItemMap.get(estimateTaskVO
										.getOtherServiceSeqNumber());
								if (estimateTaskVO.compareItem(existingOtherServicesItem) != 0) {
									estimateUpdate = true;
									itemChange = true;
								}
								if (estimateTaskVO.comparePrice(existingOtherServicesItem) != 0) {
									estimatePriceChange = true;
									itemChange = true;
								}
								if (itemChange) {
									update("updateEstimationOtherEstimateServiceItems.insert", estimateTaskVO);
									estimateTaskVO.setItemId(existingOtherServicesItem.getItemId());
									estimateTaskVO.setAction("UPDATED");
									insert("saveEstimationOtherEstimateServiceItemsHistory.insert", estimateTaskVO);
								}
							}
						}
					}

					if (null != existingLaborTasksList && !existingLaborTasksList.isEmpty()) {
						for (EstimateTaskVO estimateTaskVO : existingLaborTasksList) {
							if (!laborItemIdList.contains(estimateTaskVO.getTaskSeqNumber())) {
								estimateTaskVO.setAction("DELETED");
								estimateTaskVO.setEstimationId(estimationId);
								estimateTaskVO.setEstimationHistoryId(estimationHistoryId);
								delete("deleteEstimationItems.delete", estimateTaskVO.getItemId());
								insert("saveEstimationLaborItemsHistory.insert", estimateTaskVO);
								estimateUpdate = true;
								estimatePriceChange = true;
							}
						}
					}

					if (null != existingPartTasksList && !existingPartTasksList.isEmpty()) {
						for (EstimateTaskVO estimateTaskVO : existingPartTasksList) {
							if (!partItemIdList.contains(estimateTaskVO.getPartSeqNumber())) {
								estimateTaskVO.setAction("DELETED");
								estimateTaskVO.setEstimationId(estimationId);
								estimateTaskVO.setEstimationHistoryId(estimationHistoryId);
								delete("deleteEstimationItems.delete", estimateTaskVO.getItemId());
								insert("saveEstimationPartItemsHistory.insert", estimateTaskVO);
								estimateUpdate = true;
								estimatePriceChange = true;
							}
						}
					}

					if (null != existingOtherEstimateServicesList && !existingOtherEstimateServicesList.isEmpty()) {
						for (EstimateTaskVO estimateTaskVO : existingOtherEstimateServicesList) {
							if (!otherServicesItemIdList.contains(estimateTaskVO.getOtherServiceSeqNumber())) {
								estimateTaskVO.setAction("DELETED");
								estimateTaskVO.setEstimationId(estimationId);
								estimateTaskVO.setEstimationHistoryId(estimationHistoryId);
								delete("deleteEstimationItems.delete", estimateTaskVO.getItemId());
								insert("saveEstimationOtherEstimateServiceItemsHistory.insert", estimateTaskVO);
								estimateUpdate = true;
								estimatePriceChange = true;
							}
						}
					}
					estimationVO.setAddEditestimationId(estimationId);
					estimationVO.setEstimateUpdate(estimateUpdate);
					estimationVO.setEstimatePriceChange(estimatePriceChange);

					if (!(estimateUpdate || estimatePriceChange)) {
						delete("deleteEstimationHistory.delete", estimationHistoryId);
					}
				}
			}
							
			}catch (Exception e) {
				logger.error("Error occured in MobileGenericBOImpl.saveEstimationDetails()"+e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return estimationVO;
		}
	
	/**
	 * find difference between existing estimate and new estimate
	 * 
	 * @param existingEstimate
	 * @param estimationVO
	 * @return
	 */
	private boolean differenceExist(EstimateVO existingEstimate, EstimateVO estimationVO) {

		if (existingEstimate.compareEstimationWODate(estimationVO) != 0
				|| existingEstimate.comparePrice(estimationVO) != 0
				|| (null != existingEstimate.getEstimateParts() ? existingEstimate.getEstimateParts().size() : 0) != (null != estimationVO
						.getEstimateParts() ? estimationVO.getEstimateParts().size() : 0)
				|| (null != existingEstimate.getEstimateItems() ? existingEstimate.getEstimateItems().size() : 0) != (null != existingEstimate
						.getEstimateItems() ? existingEstimate.getEstimateItems().size() : 0)
				|| (null != existingEstimate.getEstimateTasks() ? existingEstimate.getEstimateTasks().size() : 0) != (null != existingEstimate
						.getEstimateTasks() ? existingEstimate.getEstimateTasks().size() : 0)
				|| (null != existingEstimate.getEstimateOtherEstimateServices() ? existingEstimate.getEstimateOtherEstimateServices()
						.size() : 0) != (null != existingEstimate.getEstimateOtherEstimateServices() ? existingEstimate
						.getEstimateOtherEstimateServices().size() : 0)) {
			logger.info("differenceExist method: different between size of data in new and existing estimate");
			return true;
		}

		List<EstimateTaskVO> existingLaborTasksList = existingEstimate.getEstimateTasks();
		List<EstimateTaskVO> existingPartTasksList = existingEstimate.getEstimateParts();
		List<EstimateTaskVO> existingOtherEstimateServicesList = existingEstimate.getEstimateOtherEstimateServices();

		List<Integer> existingLaborItemIdList = new ArrayList<Integer>();
		List<Integer> existingPartItemIdList = new ArrayList<Integer>();
		List<Integer> existingOtherServicesItemIdList = new ArrayList<Integer>();

		Map<Integer, EstimateTaskVO> existingLaborItemMap = new HashMap<Integer, EstimateTaskVO>();
		Map<Integer, EstimateTaskVO> existingPartItemMap = new HashMap<Integer, EstimateTaskVO>();
		Map<Integer, EstimateTaskVO> existingOtherServicesItemMap = new HashMap<Integer, EstimateTaskVO>();

		List<EstimateTaskVO> laborTasksList = estimationVO.getEstimateTasks();
		List<EstimateTaskVO> partTasksList = estimationVO.getEstimateParts();
		List<EstimateTaskVO> otherEstimateServicesList = estimationVO.getEstimateOtherEstimateServices();

		List<Integer> laborItemIdList = new ArrayList<Integer>();
		List<Integer> partItemIdList = new ArrayList<Integer>();
		List<Integer> otherServicesItemIdList = new ArrayList<Integer>();

		if (null != existingLaborTasksList && !existingLaborTasksList.isEmpty()) {
			for (EstimateTaskVO estimateTaskVO : existingLaborTasksList) {
				existingLaborItemMap.put(estimateTaskVO.getTaskSeqNumber(), estimateTaskVO);
			}
		}

		if (null != existingPartTasksList && !existingPartTasksList.isEmpty()) {
			for (EstimateTaskVO estimateTaskVO : existingPartTasksList) {
				existingPartItemMap.put(estimateTaskVO.getPartSeqNumber(), estimateTaskVO);
			}
		}

		if (null != existingOtherEstimateServicesList && !existingOtherEstimateServicesList.isEmpty()) {
			for (EstimateTaskVO estimateTaskVO : existingOtherEstimateServicesList) {
				existingOtherServicesItemMap.put(estimateTaskVO.getOtherServiceSeqNumber(), estimateTaskVO);
			}
		}
		existingLaborItemIdList = new ArrayList<Integer>(existingLaborItemMap.keySet());
		existingPartItemIdList = new ArrayList<Integer>(existingPartItemMap.keySet());
		existingOtherServicesItemIdList = new ArrayList<Integer>(existingOtherServicesItemMap.keySet());

		if (null != laborTasksList && !laborTasksList.isEmpty()) {
			for (EstimateTaskVO estimateTaskVO : laborTasksList) {
				laborItemIdList.add(estimateTaskVO.getTaskSeqNumber());
				if (!existingLaborItemIdList.contains(estimateTaskVO.getTaskSeqNumber())) {
					logger.info("differenceExist method: new data in laborTasksList");
					return true;
				} else {
					EstimateTaskVO existingLaborItem = existingLaborItemMap.get(estimateTaskVO.getTaskSeqNumber());
					if (estimateTaskVO.compareItem(existingLaborItem) != 0 || estimateTaskVO.comparePrice(existingLaborItem) != 0) {
						logger.info("differenceExist method: different data in laborTasksList element");
						logger.debug("different data: " + estimateTaskVO.getTaskSeqNumber());
						return true;
					}
				}
			}
		}

		if (null != partTasksList && !partTasksList.isEmpty()) {
			for (EstimateTaskVO estimateTaskVO : partTasksList) {
				partItemIdList.add(estimateTaskVO.getPartSeqNumber());
				if (!existingPartItemIdList.contains(estimateTaskVO.getPartSeqNumber())) {
					logger.info("differenceExist method: new data in partTasksList");
					return true;
				} else {
					EstimateTaskVO existingPartItem = existingPartItemMap.get(estimateTaskVO.getPartSeqNumber());
					if (estimateTaskVO.compareItem(existingPartItem) != 0 || estimateTaskVO.comparePrice(existingPartItem) != 0) {
						logger.info("differenceExist method: different data in partTasksList element");
						logger.debug("different data: " + estimateTaskVO.getPartSeqNumber());
						return true;
					}
				}
			}
		}
		if (null != otherEstimateServicesList && !otherEstimateServicesList.isEmpty()) {
			for (EstimateTaskVO estimateTaskVO : otherEstimateServicesList) {
				otherServicesItemIdList.add(estimateTaskVO.getOtherServiceSeqNumber());
				if (!existingOtherServicesItemIdList.contains(estimateTaskVO.getOtherServiceSeqNumber())) {
					logger.info("differenceExist method: new data in otherEstimateServicesList");
					return true;
				} else {
					EstimateTaskVO existingOtherServicesItem = existingOtherServicesItemMap.get(estimateTaskVO.getOtherServiceSeqNumber());
					if (estimateTaskVO.compareItem(existingOtherServicesItem) != 0
							|| estimateTaskVO.comparePrice(existingOtherServicesItem) != 0) {
						logger.info("differenceExist method: different data in otherEstimateServicesList element");
						logger.debug("different data: " + estimateTaskVO.getOtherServiceSeqNumber());
						return true;
					}
				}
			}
		}

		if (null != existingLaborTasksList && !existingLaborTasksList.isEmpty()) {
			for (EstimateTaskVO estimateTaskVO : existingLaborTasksList) {
				if (!laborItemIdList.contains(estimateTaskVO.getTaskSeqNumber())) {
					logger.info("differenceExist method: different data in existingLaborTasksList element");
					logger.debug("removed data: " + estimateTaskVO.getTaskSeqNumber());
					return true;
				}
			}
		}

		if (null != existingPartTasksList && !existingPartTasksList.isEmpty()) {
			for (EstimateTaskVO estimateTaskVO : existingPartTasksList) {
				if (!partItemIdList.contains(estimateTaskVO.getPartSeqNumber())) {
					logger.info("differenceExist method: different data in existingPartTasksList element");
					logger.debug("removed data: " + estimateTaskVO.getPartSeqNumber());
					return true;
				}
			}
		}

		if (null != existingOtherEstimateServicesList && !existingOtherEstimateServicesList.isEmpty()) {
			for (EstimateTaskVO estimateTaskVO : existingOtherEstimateServicesList) {
				if (!otherServicesItemIdList.contains(estimateTaskVO.getOtherServiceSeqNumber())) {
					logger.info("differenceExist method: different data in existingOtherEstimateServicesList element");
					logger.debug("removed data: " + estimateTaskVO.getOtherServiceSeqNumber());
					return true;
				}
			}
		}

		return false;
	}
	
	/**@Description : Setting estimation id in estimation line items
	 * @param laborTasksList
	 * @param partTasksList
	 * @param otherEstimateServicesList 
	 * @param estimationId
	 */
	private void mapEstimationIdToItems(List<EstimateTaskVO> laborTasksList,List<EstimateTaskVO> partTasksList, List<EstimateTaskVO> otherEstimateServicesList, 
			Integer estimationId,Integer estimationHistoryId) {
		if(null!= laborTasksList && !laborTasksList.isEmpty() && null!= estimationId){
			for(EstimateTaskVO taskVO : laborTasksList){
				taskVO.setEstimationId(estimationId);
				taskVO.setEstimationHistoryId(estimationHistoryId);
			}
		}
        if(null!= partTasksList && !partTasksList.isEmpty() && null!= estimationId ){
			for(EstimateTaskVO partVO : partTasksList ){
				partVO.setEstimationId(estimationId);
				partVO.setEstimationHistoryId(estimationHistoryId);
			}
		}
        if(null!= otherEstimateServicesList && !otherEstimateServicesList.isEmpty() && null!= estimationId ){
			for(EstimateTaskVO OtherEstimateServiceVO : otherEstimateServicesList ){
				OtherEstimateServiceVO.setEstimationId(estimationId);
				OtherEstimateServiceVO.setEstimationHistoryId(estimationHistoryId);

			}
		}
	}
	/**
	 * @param soId
	 * @param estimationId
	 * @return boolean
	 * @throws DataServiceException
	 */
	public Boolean isEstimationIdExists(String soId, Integer estimationId) throws DataServiceException{
		Boolean isEstimationIdExists = null;
		Integer existingEstimationId;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("soId", soId);
		param.put("estimationId", estimationId);
		try{
			existingEstimationId=(Integer) queryForObject("getEstimationId.query",param);
			if(null != existingEstimationId){
				isEstimationIdExists = true;
			}
		}catch (Exception e) {
			logger.error("Exception in validating estimateId"+ e);
			throw new DataServiceException("Exception in validating estimateId due to "+e.getMessage());
		}
		return isEstimationIdExists;
	}
	
	/**
	 * B2C : method to fetch the estimate details 
	 * @param soId
	 * @param estimateId
	 * @return
	 * @throws BusinessServiceException
	 */
	public EstimateVO getEstimate(String soId, Integer estimateId)throws DataServiceException {
		
		EstimateVO estimateVO = null;
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soId);
			param.put("estimateId", estimateId);
			estimateVO =  (EstimateVO)queryForObject("getEstimate.query", param);
		}
		catch(Exception e){
			logger.error("Exception in ServiceOrderDaoImpl.getEstimate due to:" + e);
		}
		return estimateVO;
	}
    
	public EstimateVO getEstimateByVendorAndResource(Map<String, Object> param)throws DataServiceException {
		
		EstimateVO estimateVO = null;
		try{
			
			estimateVO =  (EstimateVO)queryForObject("getEstimateByVendorAndResource.query", param);
		}
		catch(Exception e){
			logger.error("Exception in ServiceOrderDaoImpl.getEstimateByVendorAndResource due to:" + e);
		}
		return estimateVO;
	}
	/**@Description : This method will fetch the status of estimate 
	 * @param soId
	 * @param estimationId
	 * @return
	 * @throws DataServiceException
	 */
	public String validateEstimateStatus(String soId, Integer estimationId)throws DataServiceException {
		String status =null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("soId", soId);
		param.put("estimationId", estimationId);
		try{
			status = (String) queryForObject("getEstimateStatus.query", param);
		}catch (Exception e) {
			logger.error("Exception in getting status for the  estimate"+ e);
			throw new DataServiceException("Exception in getting status for the  estimate due to "+e.getMessage());
		}
		return status;
	}
	
	/**@Description : This method will fetch the service location time zone for the service order.
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public String getServiceLocnTimeZone(String soId) throws DataServiceException {
		String timeZone =null;
		try{
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("soId", soId);
			timeZone = (String) queryForObject("getServiceLocnTimeZone.query", parameter);
		}catch (Exception e) {
			logger.error("Exception in getting serviceLocation timezone  for the  service order"+ e);
			throw new DataServiceException("Exception in getting serviceLocation timezone  for the  service order due to "+e.getMessage());
		}
		return timeZone;	
	}
	public Integer getServiceDateTimeZoneOffset(String soId)
			throws DataServiceException {
		Integer timeZone =null;
		try{
			timeZone = (Integer) queryForObject("getServiceDateTimeZoneOffset.query", soId);
		}catch (Exception e) {
			logger.error("Exception in getting serviceDate timezone offset for the  service order"+ e);
			throw new DataServiceException("Exception in getting serviceDate timezone offset for the  service order due to "+e.getMessage());
		}
		return timeZone;	
	}
	public Integer getVendorId(Integer bidResourceId)
			throws DataServiceException {
		Integer vendorId =null;
		try{
			vendorId = (Integer) queryForObject("getVendorIdForResource.query", bidResourceId);
		}catch (Exception e) {
			logger.error("Exception in getting vendor id for the bid resource id"+ e);
			throw new DataServiceException("Exception in getting vendor id for the bid resource id "+e.getMessage());
		}
		return vendorId;
	}
	
	/**SL-21451: fetching bidRequests
	 * @param dashboardVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getBidRequestCount(MobileDashboardVO dashboardVO)
			throws DataServiceException {
			Integer count =null;
			try{
				count = (Integer) queryForObject("getBidRequestCount.query", dashboardVO);
			}catch (Exception e) {
	        	LOGGER.error("error occured in MobileGenericBOImpl.getBidRequestCount()"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return count;
		}
	
	public Map<String,String> getOrderTypes(List<String> serviceOrderIds)throws DataServiceException{
		Map orderTypeMap=new HashMap <String, String>();
        try{
        	orderTypeMap =getSqlMapClient().queryForMap("getOrderTypes.query", serviceOrderIds, "soId","orderType");
        }
        catch(Exception e){
                logger.error("Exception occurred in getOrderTypes: "+e.getMessage());
                throw new DataServiceException("Exception occurred in getOrderTypes: "+e.getMessage(),e);
        }
        return orderTypeMap;	
	}
	
	public List<EstimateVO> getEstimationList(RecievedOrdersCriteriaVO criteriaVO)throws DataServiceException{
		
		List<EstimateVO> estimationList=new ArrayList<EstimateVO>();	
		try {
			estimationList = queryForList("getEstimationListForVendor.query",criteriaVO);
		} catch (Exception exception) {
			logger.info("Exception occurred in getEstimationList: "+exception);
			throw new DataServiceException("MobileGenericDao - mobileGenericDao.getEstimationList", exception);
		}		
		return estimationList;
	}
	
	public Integer isvalidSoState(String soId)
			throws DataServiceException {
			Integer count =null;
			try{
				count = (Integer) queryForObject("getValidSoCount.query", soId);
			}catch (Exception e) {
	        	LOGGER.error("error occured in MobileGenericBOImpl.isvalidSoState()"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return count;
		}


	
}
