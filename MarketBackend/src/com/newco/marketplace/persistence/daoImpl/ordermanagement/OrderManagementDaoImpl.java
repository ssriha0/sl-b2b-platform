package com.newco.marketplace.persistence.daoImpl.ordermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CancellationsSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CurrentOrdersSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSORequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.JobDoneSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.MarketFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ProviderFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RevisitSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RoutedProvidersVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ScheduleStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.StatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.SubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.CallDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.ReasonCode;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo.ReleaseDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.dto.vo.FilterParamsVO;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.dao.ordermanagement.OrderManagementDao;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;
import com.newco.marketplace.vo.ordermanagement.so.AvailabilityDateVO;
import com.newco.marketplace.vo.ordermanagement.so.InvoicePartsVO;
import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.vo.ordermanagement.so.SOScope;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * 
 * $Revision: 1.128 $ $Author: awadhwa $ $Date: 2008/06/10 23:26:29 $
 */

public class OrderManagementDaoImpl extends ABaseImplDao implements OrderManagementDao {

	private static final Logger logger = Logger
			.getLogger(OrderManagementDaoImpl.class.getName());
	private final Integer ENTITY_TYPE_ID_PROVIDER = 20;
	private static final String SORT_COLUMN_KEY = "sort_column_key";
	private static final String SORT_ORDER_KEY = "sort_order_key";
	private static final String SORT_COLUMN_SET = "sort_column_set";
	
	@SuppressWarnings("unchecked")
	public List<OMServiceOrder> getOrders(FetchSORequest request,String providerId, Map<String, String> sortCriteria) {
		// logger.info("Entering OrderManagementDaoImpl.getOrders()...About to fetch Order list");
		
		boolean filterPresent = false;
		long startTime = System.currentTimeMillis();
		ArrayList<OMServiceOrder> soList= new ArrayList<OMServiceOrder>();
		String vendorId = providerId;
		Integer count = request.getEndIndex();
		String displayTab = request.getTabName();
		String groupId = request.getGroupId();
		Boolean viewOrderPricingPermission = request.getViewOrderPricing();
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("vendorId", vendorId);
		params.put("count", count);
		params.put("displayTab", displayTab);
		params.put("groupId", groupId);
		params.put("groupInd", request.getGroupInd());
		params.put("groupIds", request.getGroupIds());
		params.put("unassignedInd", request.getIncludeUnassignedInd());
		//Map<String, String> sort = ensureSort(request.getSortBy(), request.getSortOrder());
		params.put("sortBy", sortCriteria.get(SORT_COLUMN_KEY));
		params.put("sortOrder", sortCriteria.get(SORT_ORDER_KEY));
		params.put("sortFlag",  sortCriteria.get(SORT_COLUMN_SET));
		params.put("viewOrderPricingPermission", viewOrderPricingPermission);

		//for filter
		setParamForFilters(params, request);
		
		boolean filterOnProviders = false;
		// Check if sort is present and is on provider name
		if(null!=sortCriteria && !StringUtils.isBlank(sortCriteria.get(SORT_COLUMN_SET)) && 
				(!StringUtils.isBlank(sortCriteria.get(SORT_COLUMN_KEY))
				&&sortCriteria.get(SORT_COLUMN_KEY).equalsIgnoreCase("Providers"))){
			filterOnProviders = true;
		}
		
		List<String> tmpSubStatus = getSubStatusWithEstimationRequest(request);
		request.setSubstatus(tmpSubStatus);
		/*List<String> tmpSubStatus = new ArrayList<String>();
		//tmpSubStatus = request.getSubstatus();
		if(null!=request.getSubstatus()){
			for(String subStatus : request.getSubstatus() ){
				
				if(subStatus.equals("ESTIMATION REQUEST")){
					tmpSubStatus.add("ESTIMATION");
				}else{
					tmpSubStatus.add(subStatus);
				}
			}
			request.setSubstatus(tmpSubStatus);
		}*/
		
		
		
		// TODO This can be controlled by the value initial load
		filterPresent = checkFilters(request);
		
		// logger.info("Entering OrderManagementDaoImpl.getOrders()..displayTab::"+displayTab);
		try{
			// If the display tab is Inbox and there are no filters
			// TODO CAN WE USE THE SAME QUERY WHEN THERE IS NOT FILTER FOR ALL OTHER TABS AS WELL?
			if(!request.getGroupInd()){
				if(null!=displayTab && !displayTab.equalsIgnoreCase("Resolve Problem") && !filterPresent && !filterOnProviders){
					soList = (ArrayList<OMServiceOrder>) queryForList("orderManagement.fetchOrders.load", params);
				}else{
					soList = (ArrayList<OMServiceOrder>) queryForList("orderManagement.fetchOrders", params);
				}	
			}else{
				soList = (ArrayList<OMServiceOrder>) queryForList("orderManagement.fetchChildOrdersforGroup", params);
			}
			logger.info(String.format("Exiting OrderManagementDaoImpl.getOrders()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			return soList;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getOrders() due to "+e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * Sets filter criteria 
	 * */
	private void  setParamForFilters(HashMap<String, Object> params, FetchSORequest request){
		params.put("markets", request.getMarkets());
		params.put("providerIds", request.getProviderIds());
		params.put("routedTo", request.getRoutedProviderIds());
		params.put("status", request.getStatus());		
		params.put("substatus", getSubStatusWithEstimationRequest(request));		
		params.put("substatus", request.getSubstatus());
		params.put("scheduleStatus", request.getScheduleStatus());
		params.put("appointmentType", request.getAppointmentType());
		params.put("appointmentStartDate", request.getAppointmentStartDate());
		params.put("appointmentEndDate", request.getAppointmentEndDate());
		params.put("jobDoneSubsubstatus", request.getJobDoneSubsubstatus());
		params.put("currentOrdersSubStatus", request.getCurrentOrdersSubStatus());
		params.put("cancellationsSubStatus", request.getCancellationsSubStatus());
		params.put("revisitSubStatus", request.getRevisitSubStatus());
	}
	
	private List<String> getSubStatusWithEstimationRequest(FetchSORequest request){
		List<String> tmpSubStatus = new ArrayList<String>();
		//tmpSubStatus = request.getSubstatus();
		if(null!=request.getSubstatus()){
			for(String subStatus : request.getSubstatus() ){
				
				if(subStatus.equals("ESTIMATION REQUEST")){
					tmpSubStatus.add("ESTIMATION");
				}else{
					tmpSubStatus.add(subStatus);
				}
			}
			request.setSubstatus(tmpSubStatus);
		}
		
		return tmpSubStatus;
	}
	
	/**
	 * Sets filter criteria 
	 * */
	public boolean  checkFilters(FetchSORequest fetchSORequest){
		if((null!=fetchSORequest.getMarkets() && fetchSORequest.getMarkets().size()>0)
				|| (null!=fetchSORequest.getProviderIds() && fetchSORequest.getProviderIds().size()>0)
				|| (null!=fetchSORequest.getRoutedProviderIds() && fetchSORequest.getRoutedProviderIds().size() >0)
				|| (null!=fetchSORequest.getStatus() && fetchSORequest.getStatus().size()>0)
				|| (null!=fetchSORequest.getSubstatus() && fetchSORequest.getSubstatus().size()>0)
				|| (null!=fetchSORequest.getScheduleStatus() && fetchSORequest.getScheduleStatus().size()>0)
				|| (null!=fetchSORequest.getAppointmentType() && !StringUtils.isBlank(fetchSORequest.getAppointmentType()))
				|| (null!=fetchSORequest.getAppointmentStartDate() && !StringUtils.isBlank(fetchSORequest.getAppointmentStartDate()))
				|| (null!=fetchSORequest.getAppointmentEndDate() && !StringUtils.isBlank(fetchSORequest.getAppointmentEndDate()))
				|| (null!=fetchSORequest.getJobDoneSubsubstatus() && fetchSORequest.getJobDoneSubsubstatus().size()>0)
				|| (null!=fetchSORequest.getCurrentOrdersSubStatus() && fetchSORequest.getCurrentOrdersSubStatus().size()>0)
				|| (null!=fetchSORequest.getCancellationsSubStatus() && fetchSORequest.getCancellationsSubStatus().size()>0)
				|| (null!=fetchSORequest.getRevisitSubStatus() && fetchSORequest.getRevisitSubStatus().size()>0)){
			return true;
		}else{
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<MarketFilterVO> getMarkets(List<String> soIds,String providerId) {
		logger.info("Entering OrderManagementDaoImpl.getMarkets()...About to fetch Order list");
		long startTime = System.currentTimeMillis();
		List<MarketFilterVO> markets = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soIds);
		try{
		markets =   (ArrayList<MarketFilterVO>) queryForList("orderManagement.fetchMarketList", vo);
		logger.info(String.format("Exiting OrderManagementDaoImpl.getMarkets()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		return markets;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getMarkets() due to "+e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProviderFilterVO> getProviders(List<String> soIds,String providerId) {
		List<ProviderFilterVO> providers = null;
		FilterParamsVO vo = new FilterParamsVO();
		vo.setSoIds(soIds);
		vo.setVendorId(providerId);
		try{
			providers =  (ArrayList<ProviderFilterVO>) queryForList("orderManagement.fetchProviderList", vo);
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getProviders() due to "+e.getMessage());
		}
		return providers;
	}

	/**
	 * Fetch list of routed provider lsirt for for all SO in Assign provider Tab of Order Management 
	 * */
	public List<ProviderFilterVO> getRoutedProvidersForAssignTab(List<String> soIds,
			String providerId) {
		List<ProviderFilterVO> providers = null;
		FilterParamsVO vo = new FilterParamsVO();
		vo.setSoIds(soIds);
		vo.setVendorId(providerId);
		try{
			providers =  (ArrayList<ProviderFilterVO>) queryForList("orderManagement.routedProvidersForAssigned.query", vo);
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getRoutedProviders() due to "+e.getMessage());
		}
		return providers;
	}
	
	@SuppressWarnings("unchecked")
	public List<StatusFilterVO> getStatus(List<String> soIds,String providerId) {
		logger.info("Entering OrderManagementDaoImpl.getStatus()...About to fetch Order list");
		long startTime = System.currentTimeMillis();
		List<StatusFilterVO> status = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soIds);
		try{
			status =  (ArrayList<StatusFilterVO>) queryForList("orderManagement.fetchStatusList", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getStatus()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getStatus() due to "+e.getMessage());
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public List<SubStatusFilterVO> getSubStatus(List<String> soIds,String providerId) {
		logger.info("Entering OrderManagementDaoImpl.getSubStatus()...About to fetch Order list");
		long startTime = System.currentTimeMillis();
		List<SubStatusFilterVO> subStatus = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soIds);
		try{
			subStatus =  (ArrayList<SubStatusFilterVO>) queryForList("orderManagement.fetchSubStatusList", vo);
			
			// SL-18631 : Change the filter display here as the attributes are saved in UPPER CASE 
			// and the display should be in MIXED case - 
			if(null!= subStatus && subStatus.size()>0){
				for(SubStatusFilterVO substatusvo : subStatus){
					if(substatusvo.getDescr().equalsIgnoreCase("EXCLUSIVE")){
						substatusvo.setDescr("Exclusive");
					}
					if(substatusvo.getDescr().equalsIgnoreCase("GENERAL")){
						substatusvo.setDescr("General");
					}
				}
			}
			logger.info(String.format("Exiting OrderManagementDaoImpl.getSubStatus()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		return subStatus;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getSubStatus() due to "+e.getMessage());
		}
		return subStatus;
	}

	@SuppressWarnings("unchecked")
	public List<ScheduleStatusFilterVO> getScheduleStatus(List<String> soIds,String providerId) {
		logger.info("Entering OrderManagementDaoImpl.getScheduleStatus()...About to fetch Order list");
		long startTime = System.currentTimeMillis();
		List<ScheduleStatusFilterVO> scheduleStatus = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soIds);
		vo.setVendorId(providerId);
		try{
			scheduleStatus =  (ArrayList<ScheduleStatusFilterVO>) queryForList("orderManagement.fetchScheduleStatusList", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getScheduleStatus()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		return scheduleStatus;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getSubStatus() due to "+e.getMessage());
		}
		return scheduleStatus;
	}

	@SuppressWarnings("unchecked")
	public List<String> fetchAllOrders(FetchSORequest filterVO, String providerId) {
		logger.info("Entering OrderManagementDaoImpl.fetchAllOrders()...About to fetch All Orders");
		long startTime = System.currentTimeMillis();
		List<String> soIds= new ArrayList<String>();
		String vendorId = providerId;
		String displayTab = filterVO.getTabName();
		Boolean viewOrderPricingPermission = filterVO.getViewOrderPricing();

		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("vendorId", vendorId);
		params.put("displayTab", displayTab);
		params.put("viewOrderPricingPermission", viewOrderPricingPermission);

		try{
			soIds = (ArrayList<String>) queryForList("orderManagement.fetchAllOrders", params);
			logger.info(String.format("Exiting OrderManagementDaoImpl.fetchAllOrders()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			return soIds;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.fetchAllOrders() due to "+e.getMessage());
		}
		return null;
	}
	
	/*public List<String> fetchAllOrdersWithLimit(FetchSORequest request,String providerId, Map<String, String> sortCriteria) {
		logger.info("Entering OrderManagementDaoImpl.fetchAllOrders()...About to fetch All Orders");
		long startTime = System.currentTimeMillis();
		List<String> soIds= new ArrayList<String>();
		String vendorId = providerId;
		String displayTab = filterVO.getTabName();
		Boolean viewOrderPricingPermission = filterVO.getViewOrderPricing();

		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("vendorId", vendorId);
		params.put("displayTab", displayTab);
		params.put("viewOrderPricingPermission", viewOrderPricingPermission);

		try{
			soIds = (ArrayList<String>) queryForList("orderManagement.fetchAllOrdersWithLimit", params);
			logger.info(String.format("Exiting OrderManagementDaoImpl.fetchAllOrders()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			return soIds;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.fetchAllOrders() due to "+e.getMessage());
		}
		return null;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<JobDoneSubStatusFilterVO> getSubStatusForJobDone(List<String> soIds,String providerId) {
		logger.info("Entering OrderManagementDaoImpl.getSubStatusForJobDone()...About to fetch Order list");
		long startTime = System.currentTimeMillis();
		List<JobDoneSubStatusFilterVO> subStatus = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soIds);
		try{
			subStatus =  (ArrayList<JobDoneSubStatusFilterVO>) queryForList("orderManagement.fetchJobDoneSubStatusList", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getSubStatusForJobDone()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		return subStatus;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getSubStatusForJobDone() due to "+e.getMessage());
		}
		return subStatus;
	}

	public boolean hasViewOrderPricing(Integer resourceId) throws DataServiceException {
		Boolean canViewPricing = Boolean.FALSE;
		try{
			canViewPricing = (Boolean) queryForObject("", resourceId);
		}catch (Exception e) {
			logger.error(e);
			throw new DataServiceException("Failed to retrive permission for Viewing Order Pricing", e) ;
		}
		return canViewPricing;
	}

	public List<ProviderResultVO> getEligibleProviders(String firmId,String soId) {
		List <ProviderResultVO> providerList = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soId);
			param.put("vendorId", firmId);
			param.put("resourceId", null);
			providerList = (List <ProviderResultVO>) queryForList("orderManagement.availableProvidersFirm", param);
			
		} catch (Exception ex) {
			logger.error("[getRoutedResourcesForFirm - Exception] ", ex);
		}
		
		return providerList;
	}

	public List<ProviderResultVO> getEligibleProvidersForGroup(String firmId,String groupId) {
		List <ProviderResultVO> providerList = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("groupId", groupId);
			param.put("vendorId", firmId);
			param.put("resourceId", null);
			providerList = (List <ProviderResultVO>) queryForList("orderManagement.availableProvidersFirmForGroup", param);
			
		} catch (Exception ex) {
			logger.error("[getRoutedResourcesForFirm - Exception] ", ex);
		}
		return providerList;
	}
	
	public void assignRoutedProvider(Integer resourceId, String soId, SoLoggingVo loggingVo, ServiceOrderNote soNote) throws DataServiceException {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("resourceId", resourceId);
		params.put("entityType", ENTITY_TYPE_ID_PROVIDER);
		try{
			//update so_hdr
			update("orderManagement.assignResourceToSO", params);
			//update so_routed_providers
			update("orderManagement.updateRoutedResource", params);

			//insert notes
			insert("note.insert", soNote);
			
			//insert logging
			insert("saveReassignSO.insert", loggingVo);
			
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.assignRoutedProvider() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}

	public void saveContactInfoForAssign(Integer resourceId, String soId, Contact soContact, SoLocation location, PhoneVO phoneVo) throws DataServiceException{
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("resourceId", resourceId);
		params.put("entityType", ENTITY_TYPE_ID_PROVIDER);
		Integer contactId = 0;
		Integer locationId = 0;
		params.put("ContactLocTypeId", 50);
		params.put("createdDate", soContact.getCreatedDate());
		try{
			contactId = (Integer) insert("socontact.insert", soContact);
			locationId = (Integer) insert("solocation.insert", location);
			params.put("contactId", contactId);
			params.put("locationId", locationId);
			
			insert("socontactlocation.insert", params);
			phoneVo.setSoContactId(contactId);
			insert("contactPhone.insert", phoneVo);
		}catch (Exception exc) {
			logger.error(exc.getMessage());
			throw new DataServiceException(exc.getMessage());
		}
	}
	
	public void editSOLocNotes(String notes, String soId) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("notes", notes);
		try{
			//update follow up flag for so
			update("orderManagement.updateSoLocationNotes", params);
			
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.editSOLocNotes() due to "+e.getMessage());
		}
	}

	public void editSOAppointmentTime(UpdateScheduleVO scheduleVO) throws DataServiceException{
		
		try{
			//update appointment time in so_hdr
			update("orderManagement.updateAppointmentTime.query", scheduleVO);
			//update appointment time in so_schedule
			update("orderManagement.updateSchedule.query", scheduleVO);
			// R12.0 Sprint 2 update current appointment details with service date when editing appointment date.
			SOTripVO tripNo = (SOTripVO)queryForObject("mobileSOManageSchedule.fetchSOtrip.query", scheduleVO.getSoId());
			if(null != tripNo){
				scheduleVO.setTripNo(tripNo.getTripNo());
				update("orderManagement.updateSOTrip.query", scheduleVO);
			}
			//update so_schedule_history
			String soId=scheduleVO.getSoId();
			Integer scheduleStatusId = (Integer)queryForObject("orderManagement.scheduleStatusId",soId);
			if(null!=scheduleStatusId && scheduleStatusId.intValue()!=0){
			scheduleVO.setScheduleStatusId(scheduleStatusId.intValue());
			insert("orderManagement.insertPrecallhistory.query", scheduleVO);
			}
			
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.editSOAppointmentTime() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		
	}

	public CallDetails getCallDetails(String firmId, String soId) {
		// TODO Auto-generated method stub
		CallDetails callDetails = (CallDetails)queryForObject("orderManagement.getCallDetails.query", soId);
		return callDetails;
		
	}
	public PreCallHistoryDetails getPrecallHistoryDetails(String firmId, String soId,String source) {
		// TODO Auto-generated method stub
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("firmId", firmId);
/*		params.put("source", source);
*/		PreCallHistoryDetails preCallHistoryDetails = null;
		List <PreCallHistory> preCallHistoryList    = null;
		try{
			preCallHistoryDetails = new PreCallHistoryDetails();
			preCallHistoryList =  (List <PreCallHistory>) queryForList("orderManagement.getPrecallHistoryDetails.query", params);
			preCallHistoryDetails.setHistoryList(preCallHistoryList);
		
		}catch(Exception ex) {
			logger.error("[getPrecallHistoryDetails - Exception] ", ex);
		}
		return preCallHistoryDetails;
	}
	//get current priority of so
	public int getSOPriority(String soId, String firmId) {
		Integer flag = 0;
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("vendorId", firmId);
		//get the current priority for an SO
		try{
			flag = (Integer)queryForObject("orderManagement.getSOPriority", params);
			if(null == flag){
				flag = 0;
			}			
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getSOPriority() due to "+e.getMessage());
		}
		return flag;
	}

	//update priority of so
	public void updateSOPriority(String soId, String firmId, int flag) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("vendorId", firmId);
		params.put("flag", flag);
		try{
			//update follow up flag for so
			update("orderManagement.updateSOPriority", params);
			
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.updateSOPriority() due to "+e.getMessage());
		}
	}

	//get current priority of so
	public int getGroupPriority(String groupId, String firmId) {
		Integer flag = 0;
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("groupId", groupId);
		params.put("vendorId", firmId);
		//get the current priority for group
		try{
			flag = (Integer)queryForObject("orderManagement.getGroupPriority", params);
			if(null == flag){
				flag = 0;
			}			
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getGroupPriority() due to "+e.getMessage());
		}
		return flag;
	}

	//update priority of so
	public void updateGroupPriority(String groupId, String firmId, int flag) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("groupId", groupId);
		params.put("vendorId", firmId);
		params.put("flag", flag);
		try{
			//update follow up flag for group
			update("orderManagement.updateGroupPriority", params);
			
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.updateGroupPriority() due to "+e.getMessage());
		}
	}
	
	public List<ReasonCode> fetchReasonCodes(String reasonType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Integer> getCountOfTabs(List<String> tabList, Integer firmId,boolean viewOrderPricing) throws DataServiceException {
		LinkedHashMap<String, Integer> mapTabCount = new LinkedHashMap<String,Integer>();
		Integer count = Integer.valueOf(0);
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("companyId", firmId);
		params.put("viewOrderPricingPermission", viewOrderPricing);
		try{
			long startTime = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.inboxCounts.query", params);
			logger.info(String.format("Inbox tab Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			logger.info("INBOX_TAB:"+count);
			mapTabCount.put(OrderConstants.INBOX_TAB, count);
			
			if(tabList.contains(OrderConstants.RESPOND_TAB)){
				long startTime2 = System.currentTimeMillis();
				count = (Integer) queryForObject("orderManagement.respondCounts.query", params);
				logger.info(String.format("Respond tab Time taken is %1$d ms", System.currentTimeMillis() - startTime2));
				logger.info("RESPOND_TAB:"+count);
				mapTabCount.put(OrderConstants.RESPOND_TAB, count);
			}
			long startTime3 = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.ScheduleCounts.query", params);
			logger.info(String.format("Schedule tab Time taken is %1$d ms", System.currentTimeMillis() - startTime3));
			logger.info("SCHEDULE_TAB:"+count);
			mapTabCount.put(OrderConstants.SCHEDULE_TAB, count);
			long startTime4 = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.AssignProviderCounts.query", params);
			logger.info(String.format("Assign pro tab Time taken is %1$d ms", System.currentTimeMillis() - startTime4));
			logger.info("ASSIGN_PROVIDER_TAB:"+count);
			mapTabCount.put(OrderConstants.ASSIGN_PROVIDER_TAB, count);
			long startTime5 = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.ManageRouteCounts.query", params);
			logger.info(String.format("Manage Route tab Time taken is %1$d ms", System.currentTimeMillis() - startTime5));
			logger.info("MANAGE_ROUTE_TAB:"+count);
			mapTabCount.put(OrderConstants.MANAGE_ROUTE_TAB, count);
			long startTime6 = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.ConfirmAppointmentsCounts.query", params);
			logger.info(String.format("Confirm Appt tab Time taken is %1$d ms", System.currentTimeMillis() - startTime6));
			logger.info("CONFIRM_APPT_WDW_TAB:"+count);
			mapTabCount.put(OrderConstants.CONFIRM_APPT_WDW_TAB, count);
			long startTime7 = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.printPaperWorkCount.query", params);
			logger.info(String.format("Print Paerwork tab Time taken is %1$d ms", System.currentTimeMillis() - startTime7));
			logger.info("PRINT_PAPERWORK_TAB:"+count);
			mapTabCount.put(OrderConstants.PRINT_PAPERWORK_TAB, count);
			long startTime8 = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.currentOrderCount.query", params);
			logger.info(String.format("Current orders tab Time taken is %1$d ms", System.currentTimeMillis() - startTime8));
			logger.info("CURRENT_ORDERS_TAB:"+count);
			mapTabCount.put(OrderConstants.CURRENT_ORDERS_TAB, count);
			if(tabList.contains(OrderConstants.JOB_DONE_TAB)){
				long startTime9 = System.currentTimeMillis();
				count = (Integer) queryForObject("orderManagement.JobDoneCounts.query", params);
				logger.info(String.format("Job Done tab Time taken is %1$d ms", System.currentTimeMillis() - startTime9));
				logger.info("JOB_DONE_TAB:"+count);
				mapTabCount.put(OrderConstants.JOB_DONE_TAB, count);
			}

			// for R12_0 new tab revisit needed
			if(tabList.contains(OrderConstants.REVISIT_NEEDED_TAB)){
				count = (Integer) queryForObject("orderManagement.revisitNeededCounts.query", params);
				mapTabCount.put(OrderConstants.REVISIT_NEEDED_TAB, count);
			}
			long startTime10 = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.resolveProblem.query", params);
			logger.info(String.format("Resolve Problem tab Time taken is %1$d ms", System.currentTimeMillis() - startTime10));
			mapTabCount.put(OrderConstants.RESOLVE_PROBLEM_TAB, count);
			logger.info("RESOLVE_PROBLEM_TAB:"+count);
			long startTime11 = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.cancellationCount.query", params);
			logger.info(String.format("Cancellation tab Time taken is %1$d ms", System.currentTimeMillis() - startTime11));
			logger.info("CANCELLATIONS_TAB:"+count);
			mapTabCount.put(OrderConstants.CANCELLATIONS_TAB, count);
			long startTime12 = System.currentTimeMillis();
			count = (Integer) queryForObject("orderManagement.awaitingPaymentCount.query", params);
			logger.info(String.format("Awaiting payemet tab Time taken is %1$d ms", System.currentTimeMillis() - startTime12));
			logger.info("AWAITING_PAYMENT_TAB:"+count);
			mapTabCount.put(OrderConstants.AWAITING_PAYMENT_TAB, count);

		}catch (Exception e) {
			logger.error(e);
			throw new DataServiceException("Failed to ref=trieved the count for Order Management Tabs", e);
		}
		return mapTabCount;
	}

	public ReleaseDetails getReleaseDetails(Integer resourceId, String soId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void doRelease(Integer resourceId, String soId, String firmId) {
		// TODO Auto-generated method stub
		
	}

	public void updateScheduleStatus(String scheduleStatus) {/*
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("scheduleStatus", scheduleStatus);
		try{
			//update updateAppointmentTime  for so
			//update updateScheduleTime  for so
			update("orderManagement.updateScheduleStatus.query", params);
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.updateScheduleStatus() due to "+e.getMessage());
		}*/
		
	}

	public void insertPrecallhistory(PreCallHistory callHistory) {/*
		try{
			//update updateAppointmentTime  for so
			//update updateScheduleTime  for so
			insert("orderManagement.insertPrecallhistory.query", callHistory);
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.insertPrecallhistory() due to "+e.getMessage());
		}*/
		
	}

	public void editSOLocNotes(String soNotes) {
		// TODO Auto-generated method stub
		
	}

	public void editSpecialInstruction(String specialInstructions) {
		// TODO Auto-generated method stub
		
	}

	public void updateScheduleDetails(UpdateScheduleVO updateScheduleVO) {
		// TODO Auto-generated method stub
		try{
			//select record before update
			UpdateScheduleVO updateScheduleSelectVO = (UpdateScheduleVO)queryForObject("orderManagement.selectPrecallSchedule.query", updateScheduleVO);
			if(updateScheduleSelectVO!=null){
				if(updateScheduleVO.getScheduleStatusId()==4){
					updateScheduleVO.setCustomerConfirmedInd(updateScheduleSelectVO.getCustomerConfirmedInd());
				}
			}
			update("orderManagement.updatePrecallSchedule.query", updateScheduleVO);
			//update updateAppointmentTime  for so
			SOTripVO tripNo = (SOTripVO)queryForObject("mobileSOManageSchedule.fetchSOtrip.query", updateScheduleVO.getSoId());
			if(null != updateScheduleVO.getServiceTimeStart() && !updateScheduleVO.getServiceTimeStart().trim().equals("") ){
				update("orderManagement.updateAppointmentTime.query", updateScheduleVO);
				// Updating current appt date and time details of the so_trip table when a service date change occur.
				if(null != tripNo){
					updateScheduleVO.setTripNo(tripNo.getTripNo());
					update("orderManagement.updateSOTrip.query", updateScheduleVO);
				}
			}	
			//update updateScheduleTime  for so
			if(null != updateScheduleVO.getAppointStartDate()){
				update("orderManagement.updateAppointmentDate.query", updateScheduleVO);
				// Updating current appt date and time details of the so_trip table when a service date change occur.
				if(null != tripNo){
					updateScheduleVO.setTripNo(tripNo.getTripNo());
					update("orderManagement.updateSOTrip.query", updateScheduleVO);
				}
			}
			
			//update special instructions
			if(null != updateScheduleVO.getSpecialInstructions()){
				update("orderManagement.updateSpecialInstructions.query", updateScheduleVO);
			}
			//update service location notes
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("soId", updateScheduleVO.getSoId());
			params.put("notes", updateScheduleVO.getSoNotes());
			
			if(null != updateScheduleVO.getSoNotes()){
				update("orderManagement.updateSoLocationNotes", params);
			}
			
			//Select the record after update
			updateScheduleSelectVO = (UpdateScheduleVO)queryForObject("orderManagement.selectPrecallSchedule.query", updateScheduleVO);
			//update("orderManagement.insertPrecallSchedule.query", updateScheduleVO);
			update("orderManagement.insertPrecallhistory.query", updateScheduleSelectVO);
			
			//update("orderManagement.updateScheduleStatus.query", params);
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.updateScheduleDetails() due to "+e.getMessage());
		}
		
	}

	public EligibleProvider getAssignedResource(String soId){
		EligibleProvider provider = null;
		try{
			provider = (EligibleProvider)queryForObject("orderManagement.getAssignedResource", soId);
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getAssignedResource() due to "+e.getMessage());
		}
		return provider;
	}
	
	public String fetchAssignmentType(String soId){
		String assignmentType = null;
		try{
			assignmentType = (String)queryForObject("orderManagement.fetchAssigmentType", soId);
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.fetchAssignmentType() due to "+e.getMessage());
		}
		return assignmentType;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> fetchProviderList(String soId,String vendorId){
		List<Integer> providerList = null;
		try{
			@SuppressWarnings("rawtypes")
			HashMap input = new HashMap();
			input.put("soId", soId);
			input.put("vendorId", vendorId);
			providerList = queryForList("orderManagement.fetchProviders", input);
			}catch(Exception e){
				logger.error("Exception occured in OrderManagementDaoImpl.fetchProviderList",e);
			}
			return providerList;
	}
	
	public Integer getTotalCountForTab(FetchSORequest request, Integer firmId) throws DataServiceException {
		Integer count = 0;
		String tabName = request.getTabName();
		Boolean viewOrderPricingPermission = request.getViewOrderPricing();
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("companyId", firmId);
		params.put("viewOrderPricingPermission", viewOrderPricingPermission);
		params.put("unassignedInd", request.getIncludeUnassignedInd());
		setParamForFilters(params, request);
		try{
			if("Inbox".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.inboxCounts.query", params);
			}else if("Respond".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.respondCounts.query", params);
			}else if("Schedule".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.ScheduleCounts.query", params);
			}else if("Assign Provider".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.AssignProviderCounts.query", params);
			}else if("Manage Route".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.ManageRouteCounts.query", params);
			}else if("Confirm Appt window".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.ConfirmAppointmentsCounts.query", params);
			}else if("Print Paperwork".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.printPaperWorkCount.query", params);
			}else if("Current Orders".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.currentOrderCount.query", params);
			}else if("Job Done".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.JobDoneCounts.query", params);
			}
			// for R12_0
			else if("Revisit Needed".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.revisitNeededCounts.query", params);
			}
			
			else if("Resolve Problem".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.resolveProblem.query", params);
			}else if("Cancellations".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.cancellationCount.query", params);
			}else if("Awaiting Payment".equalsIgnoreCase(tabName)){
				count = (Integer) queryForObject("orderManagement.awaitingPaymentCount.query", params);
			}
		}catch (Exception e) {
			logger.error(e);
			throw new DataServiceException("Failed to ref=trieved the count for Order Management Tab - "+tabName, e);
		}
		return count;
	}
	
	public Contact getRoutedResources(String soId, Integer resourceId){
		//Since this object will be used widely in mapper methods,
		//the possibility of throwing nullPointerException is high. So
		//initialize the return obj
		Contact routedResourceContact = new Contact();
		Map<String, String> map = new HashMap<String, String>();
			try {
				map.put("soId", soId);
				map.put("resourceId", resourceId.toString());
				routedResourceContact = (Contact) queryForObject("orderManagement.routedResourcesContacts.query", map);
			} catch (Exception ex) {
				logger.info("OrderManagementDaoImpl.getRoutedResources"+ex.getMessage());
			}
			return routedResourceContact;
	}
	
	public List<RoutedProvidersVO> getProviderForAllSOs(List<String> soidListwithLimit, String vendorId){
		long startTime = System.currentTimeMillis();
		List<RoutedProvidersVO> routedProviderList = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soidListwithLimit);
		vo.setVendorId(vendorId);
		try{
			routedProviderList =  queryForList("orderManagement.fetchProvForAllSOs", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getProviderForAllSOs()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			return routedProviderList;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getProviderForAllSOs() due to "+e.getMessage());
		}		
		return null;
	}
	
	
	public List<RescheduleVO> getRescheduleRole(List<String> soidListwithLimit)
	{
		long startTime = System.currentTimeMillis();
		List<RescheduleVO> rescheduleList = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soidListwithLimit);
		
		try{
			rescheduleList =  queryForList("orderManagement.fetchScheduleRoleForAllSOs", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getProviderForAllSOs()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			return rescheduleList;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getProviderForAllSOs() due to "+e.getMessage());
		}		
		return null;
	}

	public List<SOScope> getScopeForAllSOs(List<String> soidListwithLimit, String vendorId){
		long startTime = System.currentTimeMillis();
		List<SOScope> soScopeList = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soidListwithLimit);
		try{
			soScopeList =  queryForList("orderManagement.getScopeForAllSOs", vo);
		logger.info(String.format("Exiting OrderManagementDaoImpl.getScopeForAllSOs()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		return soScopeList;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getScopeForAllSOs() due to "+e.getMessage());
		}		
		return null;
	}
	
	/**
	 * Fetches so_hdr.accepted_resource_id and so_hdr.assignment_type for a given so_id.
	 * @param soId :L Service Order Id.
	 * @return {@link OMServiceOrder} : Only assignmentType and acceptedResourceId will be populated. <br>
	 * Returns null on exception.
	 * @exception DataServiceException
	 * **/
	public OMServiceOrder checkIfResourceAcceptedServiceOrder(String soId) throws DataServiceException {
		OMServiceOrder assignedResource = null; 
		try{
			assignedResource = (OMServiceOrder) queryForObject("orderManagement.acceptedResourceId", soId);
		}catch (Exception exc) {
			logger.error(exc.getMessage());
			throw new DataServiceException(exc.getMessage());
		}
		return assignedResource;
	}
	
	public List<OMServiceOrder> getJobDoneForAllSOs(List<String> soidListwithLimit){
		long startTime = System.currentTimeMillis();
		List<OMServiceOrder> soJobDoneList = null;
		FilterParamsVO vo = new FilterParamsVO();
		vo.setSoIds(soidListwithLimit);
		try{
			soJobDoneList =  queryForList("orderManagement.getJobDoneForAllSOs", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getJobDoneForAllSOs()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			return soJobDoneList;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getJobDoneForAllSOs() due to "+e.getMessage());
		}		
		return null;
	}
	
	/**
	 * returns 
	 * **/
	public List<AvailabilityDateVO> getProductAvailabilityDate(List<String> soIdList){
		List<AvailabilityDateVO> availabilityDateList = new ArrayList<AvailabilityDateVO>();
		if(null != soIdList && !soIdList.isEmpty()){
			try{
				availabilityDateList = queryForList("productAvailabilityDate.query", soIdList);
			}catch(Exception e){
				logger.error("Exception occured in OrderManagementDaoImpl.getProductAvailabilityDate() due to "+e.getMessage());
			}
		}			
		return availabilityDateList;
	}
	
	@SuppressWarnings("unchecked")
	public List<CurrentOrdersSubStatusFilterVO> getSubStatusForCurrentOrders(List<String> soIds,String providerId) {
		logger.info("Entering OrderManagementDaoImpl.getSubStatusForCurrentOrders()");
		long startTime = System.currentTimeMillis();
		List<CurrentOrdersSubStatusFilterVO> subStatus = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soIds);
		try{
			subStatus =  (ArrayList<CurrentOrdersSubStatusFilterVO>) queryForList("orderManagement.fetchCurrentOrdersSubStatusList", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getSubStatusForCurrentOrders()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		return subStatus;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getSubStatusForCurrentOrders() due to "+e.getMessage());
		}
		return subStatus;
	}
	
	//R12.0 Sprint3 : get substatus filter for orders in cancellations tab
	
	@SuppressWarnings("unchecked")
	public List<CancellationsSubStatusFilterVO> getSubStatusForCancellations(List<String> soIds,String providerId) {
		logger.info("Entering OrderManagementDaoImpl.getSubStatusForCancellations()");
		long startTime = System.currentTimeMillis();
		List<CancellationsSubStatusFilterVO> subStatus = null;
		FilterParamsVO vo =new FilterParamsVO();
		vo.setSoIds(soIds);
		try{
			subStatus =  (ArrayList<CancellationsSubStatusFilterVO>) queryForList("orderManagement.fetchCancellationsSubStatusList", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getSubStatusForCancellations()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		return subStatus;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getSubStatusForCancellations() due to "+e.getMessage());
		}
		return subStatus;
	}
	
	
	//R12.0 Sprint4 : get substatus filter for orders in revisit needed tab
	
		@SuppressWarnings("unchecked")
		public List<RevisitSubStatusFilterVO> getSubStatusForRevisit(List<String> soIds,String providerId) {
			logger.info("Entering OrderManagementDaoImpl.getSubStatusForRevisit()");
			long startTime = System.currentTimeMillis();
			List<RevisitSubStatusFilterVO> subStatus = null;
			FilterParamsVO vo =new FilterParamsVO();
			vo.setSoIds(soIds);
			try{
				subStatus =  (ArrayList<RevisitSubStatusFilterVO>) queryForList("orderManagement.fetchRevisitSubStatusList", vo);
				logger.info(String.format("Exiting OrderManagementDaoImpl.getSubStatusForRevisit()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			return subStatus;
			}catch(Exception e){
				logger.error("Exception occured in OrderManagementDaoImpl.getSubStatusForRevisit() due to "+e.getMessage());
			}
			return subStatus;
		}
		
	
	public void insertHistory(ProviderHistoryVO hisVO) throws DataServiceException{
		try{
			insert("insertHistory.insert",hisVO);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in history due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}
	
	/**
	 * @param soidListwithLimit
	 * @return
	 * 
	 * get last trip date for all SOs
	 */
	public List<OMServiceOrder> getLastTripForAllSOs(List<String> soidListwithLimit){
		long startTime = System.currentTimeMillis();
		List<OMServiceOrder> soTripList = null;
		FilterParamsVO vo = new FilterParamsVO();
		vo.setSoIds(soidListwithLimit);
		try{
			soTripList =  queryForList("orderManagement.getLastTripForAllSOs", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getLastTripForAllSOs()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			return soTripList;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getLastTripForAllSOs() due to "+e.getMessage());
		}		
		return null;
	}


	/**
	 * @param soId
	 * @throws DataServiceException
	 */
	public void updateSOSubstatusToScheduleConfirmed(String soId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		try{
			update("updateSOSubstatus.update",soId);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateSOSubstatusToScheduleConfirmed due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	/**
	 * @param soidListwithLimit
	 * @return
	 * 
	 * get part details for all SOs
	 */
	public List<InvoicePartsVO> getPartDetails(List<String> soidListwithLimit){
		long startTime = System.currentTimeMillis();
		List<InvoicePartsVO> partsList = null;
		FilterParamsVO vo = new FilterParamsVO();
		vo.setSoIds(soidListwithLimit);
		try{
			partsList =  queryForList("orderManagement.getPartDetailsForAllSOs", vo);
			logger.info(String.format("Exiting OrderManagementDaoImpl.getPartDetails()...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			return partsList;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getPartDetails() due to "+e.getMessage());
		}		
		return null;
	}
	//SL-21645
	public List<Contact> getSOContactDetails(String serviceOrderID){
		List<Contact> contact = null;

		try{
			contact =  queryForList("getSOContactDetails.query", serviceOrderID);
			return contact;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getSOContactDetails() due to "+e.getMessage());
		}
		return contact;
	}

	public SoLocation getServiceLocDetails(String serviceOrderID){
		SoLocation serviceLocation = null;
		try{
			serviceLocation = (SoLocation) queryForObject("getServicelocation.query", serviceOrderID);
			return serviceLocation;
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.getServiceLocDetails() due to "+e.getMessage());
		}
		return serviceLocation;
	}

}