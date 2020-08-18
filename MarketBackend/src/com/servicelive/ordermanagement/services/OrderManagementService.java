package com.servicelive.ordermanagement.services;

import static com.newco.marketplace.interfaces.OrderConstants.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.business.businessImpl.vibePostAPI.PushNotificationAlertTask;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CancellationsSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CurrentOrdersSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSORequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSOResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.JobDoneSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.MarketFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ProviderFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RevisitSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RoutedProvidersVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ScheduleStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.StatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.SubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.CallDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.Task;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs.GetTabsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo.ReleaseDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.ordermanagement.comparator.JobDoneDateComparator;
import com.newco.marketplace.dto.vo.ordermanagement.comparator.PartComparator;
import com.newco.marketplace.dto.vo.ordermanagement.comparator.ProductAvailabilityComparator;
import com.newco.marketplace.dto.vo.ordermanagement.comparator.ReportedByNameComparator;
import com.newco.marketplace.dto.vo.ordermanagement.comparator.ReportedDateComparator;
import com.newco.marketplace.dto.vo.ordermanagement.comparator.SoPriceComparator;
import com.newco.marketplace.dto.vo.ordermanagement.comparator.SoScopeComparator;
import com.newco.marketplace.dto.vo.ordermanagement.comparator.TripOnDateComparator;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.AssignOrderException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.ordermanagement.OrderManagementDao;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.TimezoneMapper;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.ordermanagement.so.AvailabilityDateVO;
import com.newco.marketplace.vo.ordermanagement.so.ChildBidOrder;
import com.newco.marketplace.vo.ordermanagement.so.ChildBidOrderListType;
import com.newco.marketplace.vo.ordermanagement.so.ChildOrderListType;
import com.newco.marketplace.vo.ordermanagement.so.InvoicePartsVO;
import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.vo.ordermanagement.so.SOScope;
import com.sears.os.service.ServiceConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;

/**
 * 
 *
 */ 
public class OrderManagementService{
	private OrderManagementDao managementDao;
	private IRelayServiceNotification relayNotificationService;
	private ServiceOrderDao serviceOrderDao;
	private PushNotificationAlertTask pushNotificationAlertTask;

	private static final Logger LOGGER = Logger.getLogger(OrderManagementService.class);
	private static final String SORT_COLUMN_KEY = "sort_column_key";
	private static final String SORT_ORDER_KEY = "sort_order_key";
	private static final String SORT_COLUMN_SET = "sort_column_set";
	private static final Integer LOCATION_TYPE_ID = 50;
	private static final String SORT_COLUMN_NOT_SET = "not_set";
	
	public List< OMServiceOrder> getOrders(FetchSORequest request,String providerId){
		String appointmnetFilter = "";
		String tabName= request.getTabName();
		if(StringUtils.isNotBlank(tabName) && "Manage Route".equalsIgnoreCase(tabName)){
			appointmnetFilter = request.getAppointmentType();
		}
		Map<String, String> sortCriteria = ensureSort(request, appointmnetFilter);
		List<OMServiceOrder> orders = managementDao.getOrders(request, providerId, sortCriteria);
	/*	if(!request.getGroupInd()&& null != orders && orders.size() > 1 && StringUtils.isBlank(sortCriteria.get(SORT_COLUMN_SET))){
			long startTime = System.currentTimeMillis();
			String sortColumn = sortCriteria.get(SORT_COLUMN_KEY);
			if("Scope".equalsIgnoreCase(sortColumn)){
				Collections.sort(orders, new SoScopeComparator());
			} else if("ReportedOn".equalsIgnoreCase(sortColumn)){
				Collections.sort(orders, new ReportedDateComparator());
			} else if("ReportedBy".equalsIgnoreCase(sortColumn)){
				Collections.sort(orders, new ReportedByNameComparator());
			} else if("Availability".equalsIgnoreCase(sortColumn)){
				Collections.sort(orders, new ProductAvailabilityComparator());
			}
			LOGGER.info(String.format("Sorting in getOrders using comparator...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		}*/
		return orders;
	}
	
	public FilterVO getFilters (FetchSORequest filterVO, String providerId){
		String tabName = filterVO.getTabName();
		List<String> soIds= managementDao.fetchAllOrders(filterVO,providerId);
		FilterVO filters = new FilterVO();
		List<MarketFilterVO> marketFilterList = null;
		List<StatusFilterVO> statusFilterList = null;
		List<SubStatusFilterVO> subStatusFilterList = null;
		List<ScheduleStatusFilterVO> scheduleStatusFilterList = null;
		List<ProviderFilterVO> providerFilterList = null;
		List<ProviderFilterVO> routedProviderFilterList = null;
		//jobDone Substatus
		List<JobDoneSubStatusFilterVO> jobDoneSubStatusFilterList = null;
		List<CurrentOrdersSubStatusFilterVO> currentOrdersSubStatusFilterList = null;
		//R12.0 sprint3 cancellations substatus
		List<CancellationsSubStatusFilterVO> cancellationsSubStatusFilterList = null;
		//R12.0 sprint4 Revisit Needed substatus
		List<RevisitSubStatusFilterVO> revisitSubStatusFilterList = null;
		
		if(null!=soIds && soIds.size()>0){
			marketFilterList = managementDao.getMarkets(soIds,providerId);
		}
		filters.setMarketList(marketFilterList);
		if(!(tabName.equals(PublicAPIConstant.RESPOND)||(tabName.equals(PublicAPIConstant.ASSIGN_PROVIDER)))){
			if(null!=soIds && soIds.size()>0){
				providerFilterList = managementDao.getProviders(soIds,providerId);
			}
			filters.setProviderList(providerFilterList);
		}
		if(tabName.equals(PublicAPIConstant.INBOX)||(tabName.equals(PublicAPIConstant.SCHEDULE))){
			if(null!=soIds && soIds.size()>0){
				statusFilterList = managementDao.getStatus(soIds,providerId);
			}
			filters.setStatusList(statusFilterList);
		}
		if(tabName.equals(PublicAPIConstant.RESPOND)){
			if(null!=soIds && soIds.size()>0){
				subStatusFilterList = managementDao.getSubStatus(soIds,providerId);
			}
			filters.setSubStatusList(subStatusFilterList);
		}
		if(tabName.equals(PublicAPIConstant.SCHEDULE)){
			if(null!=soIds && soIds.size()>0){
				scheduleStatusFilterList = managementDao.getScheduleStatus(soIds,providerId);
			}
			filters.setScheduleList(scheduleStatusFilterList);
		}
		if(tabName.equals(PublicAPIConstant.ASSIGN_PROVIDER)){
			if(null!=soIds && soIds.size()>0){
				routedProviderFilterList = managementDao.getRoutedProvidersForAssignTab(soIds,providerId);
			}
			filters.setRoutedProviderList( routedProviderFilterList);
		}
		//Job DOne Substatus
		if(tabName.equals(PublicAPIConstant.JOB_DONE)){
			if(null!=soIds && soIds.size()>0){
				jobDoneSubStatusFilterList = managementDao.getSubStatusForJobDone(soIds,providerId);
			}
			filters.setJobDoneSubStatusList(jobDoneSubStatusFilterList);
		}
		
		//Current Orders SubStatus
		if(tabName.equals(PublicAPIConstant.CURRENT_ORDERS)){
					if(null!=soIds && soIds.size()>0){
						currentOrdersSubStatusFilterList = managementDao.getSubStatusForCurrentOrders(soIds,providerId);
			}
			filters.setCurrentOrdersSubStatusList(currentOrdersSubStatusFilterList);
		}
		//R12.0 Sprint3 Cancellations substatus
		if(tabName.equals(PublicAPIConstant.CANCELLATIONS)){
			if(null!=soIds && soIds.size()>0){
				cancellationsSubStatusFilterList = managementDao.getSubStatusForCancellations(soIds, providerId);
        	}
	        filters.setCancellationsSubStatusList(cancellationsSubStatusFilterList);
		}
		//R12.0 Sprint4 Revisit Needed substatus
	        if(tabName.equals(PublicAPIConstant.REVISIT_NEEDED)){
				if(null!=soIds && soIds.size()>0){
					revisitSubStatusFilterList = managementDao.getSubStatusForRevisit(soIds, providerId);
	        	}
		        filters.setRevisitSubStatusList(revisitSubStatusFilterList);
       }
		return filters;
		
	}
	/**
	 * 
	 * */
	public boolean hasViewOrderPricing(Integer resourceId) throws DataServiceException {
		boolean viewOrderPricing = Boolean.FALSE;
		try {
			viewOrderPricing = managementDao.hasViewOrderPricing(resourceId);
		} catch (DataServiceException e) {
			LOGGER.error(this.getClass().getName()+ " : hasViewOrderPricing() :" + e.getMessage());
			throw e;
		}
		return viewOrderPricing;
	}
	
	public List<ProviderResultVO> getEligibleProviders(String firmId, String soId){
		List<ProviderResultVO> providers = null;
		try{
			providers = managementDao.getEligibleProviders(firmId,soId);
		}		
		catch (Exception exp) {
			LOGGER.error(this.getClass().getName()+ " : routed providers :" + exp.getMessage());
		}
		return providers;
	}
	
	public List<ProviderResultVO> getEligibleProvidersForGroup(String firmId, String groupId){
		List<ProviderResultVO> providers = null;
		try{
			providers = managementDao.getEligibleProvidersForGroup(firmId,groupId);
		}		
		catch (Exception exp) {
			LOGGER.error(this.getClass().getName()+ " : routed providers :" + exp.getMessage());
		}
		return providers;
	}
	
	/**
	 * Assigns a Service Order to a resource. This method validates whether the assignmentType of SO
	 * is FIRM and is not already assigned to any resource. 
	 * @param resourceId
	 * @param soId
	 * @param loggingVo : {@link SoLoggingVo}, for so_logging.
	 * @param soNote : {@link ServiceOrderNote}, for so_notes.
	 * @param soContact : {@link Contact}
	 * @return {@link Void}
	 * @exception DataServiceException : On any SQL exception.
	 * @exception AssignOrderException : Thrown when the SO is already assigned to a resource.
	 * **/
	public void assignProvider(Integer resourceId, String soId, SoLoggingVo loggingVo, ServiceOrderNote soNote, Contact soContact) throws DataServiceException, AssignOrderException{
		try{
			if(isOrderAssigned(soId)){
				throw new AssignOrderException("Service Order is already assigned.");
			}
			managementDao.assignRoutedProvider(resourceId, soId, loggingVo, soNote); 
			String loggedInUserName = loggingVo.getModifiedBy();
			SoLocation location = getSOLocation(soContact,soId);
			location.setModifiedBy(loggedInUserName);
			PhoneVO phoneVo = createPhoneVOFromContact(soContact, soId);
			phoneVo.setModifiedBy(loggedInUserName);
			managementDao.saveContactInfoForAssign(resourceId, soId, soContact, location, phoneVo);
			try{
			//SL-21469   webhook event assign provider for relay buyer
			Integer buyerId=relayNotificationService.getBuyerId(soId); 
			boolean relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId); 
			if(relayServicesNotifyFlag){
		    relayNotificationService.sentNotificationRelayServices("order_assigned_to_provider", soId); 
			}
			}
			catch(Exception e){
			LOGGER.error(" exception in invoking webhook event for assign provider"+e);	
			}
			
		   pushNotificationAlertTask.AddAlert(getServiceOrderDao().getServiceOrder(soId),"Assigned Service Order - PUSH");			
		}catch (DataServiceException exp) {
			LOGGER.error(this.getClass().getName()+ " : assignProvider :" + exp.getMessage());
			throw exp;
		}				
	}
	
	/**
	 * Checks whether an SO with FIRM as assignment type is assigned to a resource or not.
	 * @param soId : Service Order Number
	 * @return true: When Service Order is already assigned <br>
	 *  false : When Service Order is not assigned (when so_hdr.accepted_resource_id is NULL)<br>
	 *  Returns false on exception.
	 * **/
	private boolean isOrderAssigned(String soId) {
		OMServiceOrder assignedResource = null;
		try {
			assignedResource  = managementDao.checkIfResourceAcceptedServiceOrder(soId);
			String assignmentType = assignedResource.getAssignmentType();
			if (null != assignedResource && ( StringUtils.isNotBlank(assignmentType) && SO_ASSIGNMENT_TYPE_PROVIDER.equalsIgnoreCase(assignmentType) && StringUtils.isNotBlank(assignedResource.getAcceptedResourceId()))){
				return true;
			} 
		} catch (DataServiceException exc) {
			LOGGER.error(exc.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * Create PhonVo from {@link SoContact} <br>
	 * Created date and Modified date will be set to now().
	 * Phone type id, class id = 1;
	 * @param soContact {@link SoContact}
	 * @param soId : Service Order Id
	 * @return {@link PhoneVO}
	 * **/
	private PhoneVO createPhoneVOFromContact(Contact soContact, String soId) {
		PhoneVO phoneVo = new PhoneVO();
		phoneVo.setSoId(soId);
		phoneVo.setPhoneType(1);
		phoneVo.setClassId(1);
		phoneVo.setCreatedDate(new Timestamp(new Date().getTime()));
		phoneVo.setModifiedDate(new Timestamp(new Date().getTime()));
		phoneVo.setPhoneNo(soContact.getPhoneNo());
		phoneVo.setPhoneExt(soContact.getPhoneNoExt());
		return phoneVo;
	}

	/**
	 * Creates {@link SoLocation} from {@link SoContact} <br>
	 * Location type id = 50.
	 * @param soContact {@link SoContact}
	 * @param soId
	 * @return {@link SoLocation}
	 * */
	private SoLocation getSOLocation(Contact soContact, String soId) {
		SoLocation location = new SoLocation();
		location.setSoId(soId);
		location.setLocnTypeId(LOCATION_TYPE_ID);
		location.setStreet1(soContact.getStreet_1());
		location.setStreet2(soContact.getStreet_2());
		location.setState(soContact.getStateCd());
		location.setCountry(soContact.getCountry());
		location.setZip(soContact.getZip());
		location.setLocName(soContact.getLocName());
		location.setCreatedDate(new Timestamp(new Date().getTime()));
		location.setModifiedDate(new Timestamp(new Date().getTime()));
		return location;
	}

	public void editSOLocNotes(String notes, String soId) {
		managementDao.editSOLocNotes(notes,soId); 
		
	}
	
	public void editSOAppointmentTime(UpdateScheduleVO scheduleVO) throws DataServiceException{
		managementDao.editSOAppointmentTime(scheduleVO); 
	}
	public CallDetails getCallDetails(String firmId, String soId) {
		return managementDao.getCallDetails(firmId,soId); 
	}
	
	public PreCallHistoryDetails getPrecallHistoryDetails(String firmId, String soId, String source) {
		return managementDao.getPrecallHistoryDetails(firmId,soId,source); 
		
	}
	//get the current priority of so
	public int getSOPriority(String soId, String firmId) {
		return managementDao.getSOPriority(soId, firmId);
		
	}
	//update priority of so
	public void updateSOPriority(String soId, String firmId, int flag) {
		managementDao.updateSOPriority(soId, firmId, flag);
		
	}	
	
	//get the current priority of group
	public int getGroupPriority(String groupId, String firmId) {
		return managementDao.getGroupPriority(groupId, firmId);
		
	}
	//update priority of group
	public void updateGroupPriority(String groupId, String firmId, int flag) {
		managementDao.updateGroupPriority(groupId, firmId, flag);
		
	}	
	
	public void providerCall(PreCallHistory callHistory, String soNotes,
			String specialInstructions) {
		managementDao.updateScheduleStatus(callHistory.getScheduleStatus());
		managementDao.insertPrecallhistory(callHistory);
		if(StringUtils.isNotBlank(soNotes)){
			managementDao.editSOLocNotes(soNotes);
		}
		if(StringUtils.isNotBlank(specialInstructions)){
			managementDao.editSpecialInstruction(specialInstructions);
		}
	}
	public List<com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.ReasonCode> fetchReasonCodes(String reasonType) {
		return managementDao.fetchReasonCodes(reasonType);		
		
	}
	
	//TODO this method is duplicated in GetOrderManagementTabsService as this method doesn't interact with DB
	//but only creates a list based on the values in Constants
	public List<String> getTablist(Integer resourceId) {
		boolean hasPermission = Boolean.FALSE;
		List<String> tabList = new  ArrayList<String>(Arrays.asList(INBOX_TAB, SCHEDULE_TAB,ASSIGN_PROVIDER_TAB,MANAGE_ROUTE_TAB, CONFIRM_APPT_WDW_TAB, PRINT_PAPERWORK_TAB, CURRENT_ORDERS_TAB,RESOLVE_PROBLEM_TAB,CANCELLATIONS_TAB, AWAITING_PAYMENT_TAB));
		try{
			hasPermission = managementDao.hasViewOrderPricing(resourceId);
			if(hasPermission){
				tabList.add(1, RESPOND_TAB);
				tabList.add(8, JOB_DONE_TAB);
			}
		}catch (Exception e) {
			LOGGER.error(e);
		}
		return tabList;
	}
	
	public Map<String, Integer> getCountOfTabs(List<String> tabList, Integer firmId, boolean viewOrderPricing) throws DataServiceException {
		return managementDao.getCountOfTabs(tabList, firmId,viewOrderPricing);		
	}
	
	public ReleaseDetails getReleaseDetails(Integer resourceId, String soId) {
		return managementDao.getReleaseDetails(resourceId,soId);
	}
	
	public void doRelease(Integer resourceId, String soId, String firmId) {
		managementDao.doRelease(resourceId,soId,firmId);
		
	}
	
	public String fetchAssignmentType(String soId) {
		return managementDao.fetchAssignmentType(soId);
	}
	
	public List<Integer> fetchProviderList(String soId,String vendorId) {
		return managementDao.fetchProviderList(soId,vendorId);
	}
	
//	@Override
//	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	public OrderManagementDao getManagementDao() {
		return managementDao;
	}
	public void setManagementDao(OrderManagementDao managementDao) {
		this.managementDao = managementDao;
	}
	

	public void updateScheduleDetails(UpdateScheduleVO updateScheduleVO) {
		// TODO Auto-generated method stub
		managementDao.updateScheduleDetails(updateScheduleVO); 
		
	}
	
	//to get assigned resource in case of re-assign provider
	public EligibleProvider getAssignedResource(String soId) {
		return managementDao.getAssignedResource(soId);
		
	}
	
	public Integer getTotalSOCountForTab(FetchSORequest request,String firmId) throws BusinessServiceException{
		Integer count=null;
		try {
			count = managementDao.getTotalCountForTab(request, Integer.valueOf(firmId));
		} catch (NumberFormatException e) {
			throw new BusinessServiceException(e);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		return count;
	}
	
	/**
	 * Creates a criteria map for Sorting in Order management Page.
	 * @param request 
	 * @param sortColumn : The column (front end display) on which Sort to be applied
	 * @param sortOrder : Sort Order ASC/DESC (Default is DESC)
	 * @param appointmentDateFilter 
	 * @return sort criteria map to be used in query
	 **/
	private Map<String, String> ensureSort ( FetchSORequest request, String appointmentDateFilter) {
		Map<String, String> sortMap = new HashMap<String, String>();
		boolean sortOrderSet = Boolean.FALSE;
		String sortColumn = request.getSortBy();
		String sortOrder = request.getSortOrder();
		//String displayTab = request.getTabName();
		sortMap.put(SORT_COLUMN_SET,"set");
		if( StringUtils.isNotEmpty(sortColumn) && !StringUtils.equals(sortColumn, NULL_STRING) ) {
				if("status".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_SET,SORT_COLUMN_NOT_SET);
					sortMap.put(SORT_COLUMN_KEY, "SoStatus");
				} else if("title".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_KEY, SORT_COLUMN_OM_SO_TITLE);
				} else if("ScheduleStatus".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_KEY, SORT_COLUMN_OM_SCHEDULE_STATUS);
				} else if("Location".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_SET,SORT_COLUMN_NOT_SET);
					sortMap.put(SORT_COLUMN_KEY, "Location");
				} else if("Providers".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_SET,SORT_COLUMN_NOT_SET);
					sortMap.put(SORT_COLUMN_KEY, "Providers");
				} else if("followup".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_KEY, SORT_COLUMN_OM_FOLLOW_UP);
				} else if("Price".equalsIgnoreCase(sortColumn)){
					String tabName = request.getTabName();
					if("Inbox".equalsIgnoreCase(tabName) || "Respond".equalsIgnoreCase(tabName)){
						sortMap.put(SORT_COLUMN_SET, null); // SQL sort and limit is not required
						sortMap.put(SORT_COLUMN_KEY, sortColumn);
					}else{
						sortMap.put(SORT_COLUMN_SET,SORT_COLUMN_NOT_SET);
						sortMap.put(SORT_COLUMN_KEY, sortColumn);
					}
				} else if("AppointmentDate".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_SET,SORT_COLUMN_NOT_SET);
					sortMap.put(SORT_COLUMN_KEY, "AppointmentDate");
				} else if("DateRecieved".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_KEY, "s.routed_date");
				} else if("Scope".equalsIgnoreCase(sortColumn) || "ReportedOn".equalsIgnoreCase(sortColumn) || "ReportedBy".equalsIgnoreCase(sortColumn)  || "Availability".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_SET, null); // SQL sort and limit is not required
					sortMap.put(SORT_COLUMN_KEY, sortColumn);
				} else if("JobDone".equalsIgnoreCase(sortColumn)){
					//sortMap.put(SORT_COLUMN_KEY, "s.service_date2"); 
					sortMap.put(SORT_COLUMN_SET, null); // SQL sort and limit is not required
					sortMap.put(SORT_COLUMN_KEY, sortColumn);
				} else if("OrderType".equalsIgnoreCase(sortColumn)){
					sortMap.put(SORT_COLUMN_SET,SORT_COLUMN_NOT_SET);
					sortMap.put(SORT_COLUMN_KEY, "OrderType");
				} else if("CustInfo".equalsIgnoreCase(sortColumn)){
					if( StringUtils.isEmpty(sortOrder) || StringUtils.equals(sortColumn, NULL_STRING) ){
						sortOrder = SORT_ORDER_DESC;
					}
					sortMap.put(SORT_COLUMN_KEY, "cont.last_name "+sortOrder +", cont.first_name");
					sortMap.put(SORT_ORDER_KEY, sortOrder);
					sortOrderSet = Boolean.TRUE;
				} else {
					sortMap.put(SORT_COLUMN_KEY, SORT_COLUMN_OM_APPOINTMENT_DT_TZ);
					sortMap.put(SORT_ORDER_KEY, SORT_ORDER_DESC);
					sortOrderSet = Boolean.TRUE;
				}
		} else if(StringUtils.isNotBlank(appointmentDateFilter)) {
			sortMap.put(SORT_COLUMN_SET,"set");
			sortMap.put(SORT_COLUMN_KEY, "vcontact.first_name ASC, vcontact.last_name ");		
			sortMap.put(SORT_ORDER_KEY, SORT_ORDER_ASC);
			sortOrderSet = Boolean.TRUE;
		} else {
			sortMap.put(SORT_COLUMN_KEY, SORT_COLUMN_OM_APPOINTMENT_DT_TZ);
			sortMap.put(SORT_ORDER_KEY, SORT_ORDER_ASC);
			sortOrderSet = Boolean.TRUE;
		}
		if (!sortOrderSet) {
			if( StringUtils.isNotEmpty(sortOrder) && !StringUtils.equals(sortColumn, NULL_STRING) ){
				sortMap.put(SORT_ORDER_KEY, sortOrder);
			} else {
				sortMap.put(SORT_ORDER_KEY, SORT_ORDER_DESC);
			}
		}
		return sortMap;
	}

	public Contact getRoutedResources(String soId, Integer resourceId) {
		Contact result = null;
		try {
			result = managementDao.getRoutedResources(soId, resourceId);
		} catch (Exception e) {
			LOGGER.error(this.getClass().getName()+ " : getRoutedResources() :" + e.getMessage());
		}
		return result;
	}
	
	public List<RoutedProvidersVO> getProviderForAllSOs(List<String> soidListwithLimit, String vendorId){
		return managementDao.getProviderForAllSOs(soidListwithLimit, vendorId);		 
	}
	
	public List<RescheduleVO> getRescheduleRole(List<String> soidListwithLimit){
		return managementDao.getRescheduleRole(soidListwithLimit);		 
	}
	
	public List<SOScope> getScopeForAllSOs(List<String> soidListwithLimit, String vendorId){
		return managementDao.getScopeForAllSOs(soidListwithLimit, vendorId);		 
	}
	
	public FetchSOResponse fetchOrdersForFrontEnd(FetchSORequest fetchSORequest, String providerId, String resourceId){
		OrderManagementMapper managementMapper = new OrderManagementMapper();
		// LOGGER.info("Inside FetchSOService.execute()");
		FetchSOResponse soFetchResponse = new FetchSOResponse();
		FilterVO filters = null;
	
		// String soId = (String) apiVO.getSOId();
		long startTime = System.currentTimeMillis();
		LOGGER.info(String.format("Entering managementService...Time taken is %1$d ms",startTime ));

		if (null != providerId) {
			/*boolean viewOrderPricing = Boolean.FALSE;
			fetchSORequest.setViewOrderPricing(viewOrderPricing);*/
			fetchSORequest.setLoadFiltersInd(true);
			fetchSORequest.setGroupInd(Boolean.FALSE);
			Integer totalCount = getTotalCount(fetchSORequest, providerId);
			String tabName = fetchSORequest.getTabName();
			LOGGER.info("Tab name is ::::::"+tabName);
			
			//Find the total so Count, when filters are applied.
			
			LOGGER.info("Total Count  : "+totalCount);
			//Fetch service orders satisfying the request criteria.
			List<OMServiceOrder> orders = getOrders(fetchSORequest,providerId);
			Map<String,OMServiceOrder> orderMap = new HashMap<String, OMServiceOrder>();
			
			// List<String> soidListwithLimit = new ArrayList<String>();
			if(null!=orders){
				// long startTime1 = System.currentTimeMillis();
				for(OMServiceOrder omOrder: orders){
					omOrder.setRoutedProviders(new ArrayList<RoutedProvidersVO>());
					omOrder.setScope(new ArrayList<Task>());
					//formatAvailabilityDate(omOrder);
					orderMap.put(omOrder.getSoId(), omOrder);
					// soidListwithLimit.add(omOrder.getSoId());
				}
				// LOGGER.info(String.format("Exiting FetchSOService.preparing SO List)...Time taken is %1$d ms", System.currentTimeMillis() - startTime1));
				if(null!=orderMap && orderMap.keySet().size()>0){
				// if(!(soidListwithLimit.isEmpty())){
					// long startTimequery = System.currentTimeMillis();
					List<RoutedProvidersVO> routedProviderList = new ArrayList<RoutedProvidersVO>();
					List<SOScope> soScopeList = new ArrayList<SOScope>();
					List<String> soidListwithLimit = new ArrayList<String>(orderMap.keySet());
					routedProviderList = getProviderForAllSOs(soidListwithLimit,providerId);
					soScopeList = getScopeForAllSOs(soidListwithLimit,providerId);
					List<RescheduleVO> rescheduleList=new ArrayList<RescheduleVO>();
					rescheduleList=getRescheduleRole(soidListwithLimit);
					List<AvailabilityDateVO> availabilityDateList = getProductAvailabilityDate(soidListwithLimit);
					// LOGGER.info(String.format("Exiting FetchSOService.QUERY...Time taken is %1$d ms", System.currentTimeMillis() - startTimequery));
					// long startTime2 = System.currentTimeMillis();
					if(null != availabilityDateList && !availabilityDateList.isEmpty()){
						//iterating over keys only
						for(AvailabilityDateVO dateVO: availabilityDateList){							
							if(orderMap.containsKey(dateVO.getSoId())){
								OMServiceOrder omOrder = orderMap.get(dateVO.getSoId());
								omOrder.setAvailabilityDate(dateVO.getAvailabilityDate());
								formatAvailabilityDate(omOrder);
								orderMap.put(omOrder.getSoId(), omOrder);
							}
						}
					}
					
					if (null != rescheduleList) {
						for (RescheduleVO reschedule : rescheduleList) {
							if (orderMap.containsKey(reschedule.getSoId())) {
								if (orderMap.get(reschedule.getSoId())
										.getRescheduleRole() == null) {
									orderMap.get(reschedule.getSoId())
											.setRescheduleRole(
													reschedule.getRoleId());
								}
							}
						}
					}
					if(null!=routedProviderList){	
						for(RoutedProvidersVO provVO: routedProviderList){
							if(orderMap.containsKey(provVO.getSoId())){
								orderMap.get(provVO.getSoId()).getRoutedProviders().add(provVO);
							}
						}
					}
					// LOGGER.info(String.format("Exiting FetchSOService.preparing routedProviderList SO List)...Time taken is %1$d ms", System.currentTimeMillis() - startTime2));
					// long startTime3 = System.currentTimeMillis();
					if(null!=soScopeList){
						for(SOScope scopeVO: soScopeList){
							if(orderMap.containsKey(scopeVO.getSoId())){
								Task task = new Task();
								task.setServiceType(scopeVO.getScope());
								orderMap.get(scopeVO.getSoId()).getScope().add(task);
							}
						}
					}
				}
			}
			// LOGGER.info(String.format("Initial fetch completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			/* The list orders will contain grouped SOs, but we have to fing oll the child SOs ang group them.*/
			List<String> listGroupIds = getGroupIds(orders);

			//Grouped So will be considered only under Inbox and Respond Tab in Order Management.
			if(("Inbox".equalsIgnoreCase(tabName) || "Respond".equalsIgnoreCase(tabName)) && null != listGroupIds && listGroupIds.size() > 0){
				fetchSORequest.setGroupInd(Boolean.TRUE);
				fetchSORequest.setGroupIds(listGroupIds);
				//Fetch all child SOs for the list of group Ids.
				List<OMServiceOrder> groupedOrders = getOrders(fetchSORequest,providerId);
				//Group the child SOs and add them to the list of SOs.
 				groupChildOrders(orders, groupedOrders);
 				// LOGGER.info(String.format("Grouped Order processing completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			}
			
			if(("Inbox".equalsIgnoreCase(tabName)) || ("Respond".equalsIgnoreCase(tabName))){
				List<ChildBidOrder> bidChildOrders = findBidChildOrders(orders);
				groupBidChildOrders(orders,bidChildOrders);
				// LOGGER.info(String.format("Bid Order processing completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			}
			
			/*Sorting service order list*/
			sortOrderList(orders,fetchSORequest);
			
			if(null != orders && orders.size()!=0){
					convertGMTToGivenTimeZone(orders);
				}
			// LOGGER.info(String.format("Time zone Conversion inside FetchSOService completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			if(fetchSORequest.getLoadFiltersInd()){
				filters = getFilters(fetchSORequest,providerId);
			}
			// LOGGER.info(String.format("Filter data fetch completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			if(filters!=null){
			soFetchResponse = managementMapper.mapFetchOrdersResponse(orders, filters);
			}
			soFetchResponse.setTotalSOCount(totalCount);
			boolean filterPresent = false;
			filterPresent = checkFilters(fetchSORequest);
			if(filterPresent){
				fetchSORequest.setMarkets(null);
				fetchSORequest.setProviderIds(null);
				fetchSORequest.setRoutedProviderIds(null);
				fetchSORequest.setStatus(null);
				fetchSORequest.setSubstatus(null);
				fetchSORequest.setScheduleStatus(null);
				fetchSORequest.setAppointmentType(null);
				fetchSORequest.setAppointmentStartDate(null);
				fetchSORequest.setAppointmentEndDate(null);
				fetchSORequest.setJobDoneSubsubstatus(null);
				int countWithoutFilter = getTotalCount(fetchSORequest, providerId);
				soFetchResponse.setSoCountWithoutFilters(countWithoutFilter);
			}
			else{
				soFetchResponse.setSoCountWithoutFilters(totalCount);
			}
		}
		LOGGER.info(String.format("Exiting managementService...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		return soFetchResponse;
		}
	
	/**
	 * Gets the total SOs satisfying the request criteria. This will return 0 when no filter is selected.
	 * This method is for displaying the total number of SOs in Order Management page while trying to narrow dowwn
	 * the list of SOs by applying filters. In general case, count can be fetched using the getTabcount API.
	 * */
	private int getTotalCount(FetchSORequest fetchSORequest, String providerId) {
		int totalCount = 0;
		/*boolean market = (null !=  fetchSORequest.getMarkets()&& fetchSORequest.getMarkets().size() != 0)?Boolean.TRUE:Boolean.FALSE;
		boolean providers = (null != fetchSORequest.getProviderIds() && fetchSORequest.getProviderIds().size() != 0 )?Boolean.TRUE:Boolean.FALSE;
		boolean status = (null != fetchSORequest.getStatus() && fetchSORequest.getStatus().size() != 0)?Boolean.TRUE:Boolean.FALSE;
		boolean scheduleStatus = (null != fetchSORequest.getScheduleStatus() && fetchSORequest.getScheduleStatus().size() != 0)? Boolean.TRUE:Boolean.FALSE;
		boolean subStatus = (null != fetchSORequest.getSubstatus() && fetchSORequest.getSubstatus().size() != 0)? Boolean.TRUE:Boolean.FALSE;
		if( market ||  providers || status ||  scheduleStatus || subStatus || StringUtils.isNotBlank(fetchSORequest.getAppointmentType())){*/
		try {
				totalCount = getTotalSOCountForTab(fetchSORequest, providerId);
			} catch (BusinessServiceException e) {
				LOGGER.error(e);
				totalCount = 0;
			}catch(Exception e){
				LOGGER.error(e);
				totalCount = 0;
			}
//		}
		return totalCount;
	}
	
	/**
	 * Find the the grouped SOs in the list of SOs fetched.
	 * @param orders : List of {@link OMServiceOrder}
	 * @return List of Group Ids
	 * */
	private List<String> getGroupIds(List<OMServiceOrder> orders) {
		List<String> listGroupIds = new ArrayList<String>();
		for(OMServiceOrder serviceOrder : orders){
			String groupId = serviceOrder.getParentGroupId();
			if(StringUtils.isNotBlank(groupId) && !listGroupIds.contains(groupId)){
				listGroupIds.add(groupId);
			}
		}
		return listGroupIds;
	}

	/**
	 * Filter the the bid SOs in the list of SOs fetched.
	 * @param orders : List of {@link OMServiceOrder}
	 * @return List of Group Ids
	 * */
	private List<ChildBidOrder> findBidChildOrders(List<OMServiceOrder> orders) {
		List<ChildBidOrder> listBidChildSos = new ArrayList<ChildBidOrder>();
		for(OMServiceOrder serviceOrder : orders){
			String priceType = serviceOrder.getPriceModel();
			List<RoutedProvidersVO> routedProvidersVOs = serviceOrder.getRoutedProviders();
			if((StringUtils.isNotBlank(priceType)) && (priceType.equals("ZERO_PRICE_BID"))){
				for(RoutedProvidersVO providersVO : routedProvidersVOs){
					if(providersVO.getRespId()== null || providersVO.getRespId()==2){
						ChildBidOrder childBidOrder = new ChildBidOrder();
						childBidOrder.setSoId(serviceOrder.getSoId());
						childBidOrder.setSoTitle(serviceOrder.getSoTitle());
						childBidOrder.setSoTitleDesc(serviceOrder.getSoTitleDesc());
						childBidOrder.setSpendLimit(providersVO.getSpendLimit());
						childBidOrder.setOfferExpirationDate(providersVO.getOfferExpirationDate());
						childBidOrder.setProviderFirstName(providersVO.getFirstName());
						childBidOrder.setProviderLastName(providersVO.getLastName());
						childBidOrder.setResourceId(providersVO.getId());
						childBidOrder.setRespId(providersVO.getRespId());
						listBidChildSos.add(childBidOrder);
					}
				}
			}
		}
		return listBidChildSos;
	}

	/**
	 * Method which will group the child Child SOs under its Parent SOs.
	 * @param orders List of {@link OMServiceOrder}. This is the main list of SOs.
	 * */
	private void groupChildOrders(List<OMServiceOrder> orders, List<OMServiceOrder> groupedOrders) {
		if(null != groupedOrders && groupedOrders.size() > 0){
			//After the iteration this Hash map will contain groupId - List of Child SO pair.
			Map<String, List<OMServiceOrder>> groupIdChildrenMap = new HashMap<String, List<OMServiceOrder>>();
			for(OMServiceOrder serviceOrder : groupedOrders){
				String groupId = serviceOrder.getParentGroupId();
				//If SO is a Group SO then do
				if (StringUtils.isNotBlank(groupId)) {
					/*Pop from the Hash Map, the child SO List.
					*It will be null, if this groupId (here it will be the key) 
					*is not encountered in the iteration so far
					*/
					List<OMServiceOrder> childSOList = groupIdChildrenMap.get(groupId);
					if (childSOList == null) {
						/*If the current group Id comes in the orders list for 
						 * the first time, create a Child SO List for this Group Id
						 */
						childSOList = new ArrayList<OMServiceOrder>();
						groupIdChildrenMap.put(groupId, childSOList);
					}
					//Populate the Child SO List
					childSOList.add(serviceOrder);
				}
			}
			//Finally, replace the first child SO with its Group SO in the main List
			replaceChildWithGroup(orders, groupIdChildrenMap);
		}
	}
	
	/**
	 * Method which will group the bid Child SOs under its Parent SOs.
	 * @param bidOrders 
	 * */
	private void groupBidChildOrders(List<OMServiceOrder> orders, List<ChildBidOrder> bidChildOrders) {
		if(null != orders && orders.size() > 0){
			//After the iteration this Hash map will contain groupId - List of Child SO pair.
			Map<String, List<ChildBidOrder>> groupBidChildrenMap = new HashMap<String, List<ChildBidOrder>>();
			for(ChildBidOrder bidChildOrder : bidChildOrders){
				String soId = bidChildOrder.getSoId();
				//If SO is a Group SO then do
				if (StringUtils.isNotBlank(soId)) {
					/*Pop from the Hash Map, the child SO List.
					*It will be null, if this groupId (here it will be the key) 
					*is not encountered in the iteration so far
					*/
					List<ChildBidOrder> childSOList = groupBidChildrenMap.get(soId);
					if (childSOList == null) {
						/*If the current group Id comes in the orders list for 
						 * the first time, create a Child SO List for this Group Id
						 */
						childSOList = new ArrayList<ChildBidOrder>();
						groupBidChildrenMap.put(soId, childSOList);
					}
					//Populate the Child SO List
					childSOList.add(bidChildOrder);
				}
			}
			//Finally, replace the first child SO with its Group SO in the main List
			replaceBidWithChildGroup(orders, groupBidChildrenMap);
		}
	}
	
	/**
	 * This method will replace the first occurrence of the <br>
	 * Grouped SO with its List of Child Orders.
	 * <p>The rest of the SO with same Group Id will be removed from the list</p>
	 * @param orders : List of {@link OMServiceOrder}: The main list of Service Orders
	 * @param groupChildrenMap : HashMap with Key = Group Id and Value = Lisst Of SOs for the group Id 
	 * */
	private void replaceChildWithGroup(List<OMServiceOrder> orders,
			Map<String, List<OMServiceOrder>> groupChildrenMap) {
		List<OMServiceOrder> listProcessedSOs = new ArrayList<OMServiceOrder>();
		List<OMServiceOrder> groupedOrderList = new ArrayList<OMServiceOrder>(); 
		for(OMServiceOrder serviceOrder : orders){
			String groupId = serviceOrder.getParentGroupId();
			if (StringUtils.isNotBlank(groupId)){
				if(groupChildrenMap.containsKey(groupId)) { 
					//Replace the first occurrence of the group id wwith the Child So List
					List<OMServiceOrder> childSOList = groupChildrenMap.get(groupId);
					serviceOrder.setSoId(groupId);
					serviceOrder.setGroupInd(Boolean.TRUE);
					if(null !=childSOList && childSOList.size()!=0){
						serviceOrder.setParentGroupTitle(childSOList.get(0).getParentGroupTitle());
						serviceOrder.setGroupSpendLimit(childSOList.get(0).getGroupSpendLimit());
						serviceOrder.setGroupSpendLimitLabor(childSOList.get(0).getGroupSpendLimitLabor());
						serviceOrder.setGroupSpendLimitParts(childSOList.get(0).getGroupSpendLimitParts());
					}
					ChildOrderListType childOrders = new ChildOrderListType();
					childOrders.setChildOrder(childSOList);
					serviceOrder.setChildOrderList(childOrders);
					groupChildrenMap.remove(groupId);
					groupedOrderList.add(serviceOrder);
				}else{
					/*If current iteration SO is a grouped SO and is already added 
					 * to the list as a child so of its parent
					 * the remove this from the so list */
					listProcessedSOs.add(serviceOrder);
				}
			}else{
				groupedOrderList.add(serviceOrder);
			}
		}
		//Removing all occurrence of grouped SO other than 
		//the first occurrence (for each Group Id)
		orders.removeAll(listProcessedSOs);
	}
	
	public List<OMServiceOrder> getJobDoneForAllSOs(List<String> soidListwithLimit) {
		return managementDao.getJobDoneForAllSOs(soidListwithLimit);	
	}
	
	/**
	 * This method will replace the occurrence of the
	 * Bid SO with its List of Child Bid Orders.
	 * <p>The rest of the SO with same Group Id will be removed from the list</p>
	 * @param orders : List of {@link OMServiceOrder}: The main list of Service Orders
	 * @param groupChildrenMap : HashMap with Key = Group Id and Value = Lisst Of SOs for the group Id 
	 * */
	private void replaceBidWithChildGroup(List<OMServiceOrder> orders,
			Map<String, List<ChildBidOrder>> groupBidChildrenMap) {
		for(OMServiceOrder serviceOrder : orders){
			String soId = serviceOrder.getSoId();
			if (StringUtils.isNotBlank(soId)){
				if(groupBidChildrenMap.containsKey(soId)) { 
					//Replace the first occurrence of the Bid SO id with the Child So List
					List<ChildBidOrder> childBidSOList = groupBidChildrenMap.get(soId);
					double price = 0.00;
					List<Double> prices = new ArrayList<Double>();
					double maxPrice = 0.00;
					double minPrice = 0.00;
					for(ChildBidOrder childBidOrder : childBidSOList){
						if(childBidOrder.getSpendLimit()!=null){
								price = Double.parseDouble(childBidOrder.getSpendLimit()) ;
						}
						else{
								price =0.00;
						}
						prices.add(price);
					}
					if(prices !=null && (!prices.isEmpty())){
						minPrice = prices.get(0);
					}
						for(double spendLimit : prices){
							if(spendLimit>0 && minPrice==0){
								minPrice = spendLimit;
							}
							if(spendLimit>maxPrice){
								maxPrice = spendLimit;
							}
							else if(spendLimit>0 && spendLimit<minPrice){
								minPrice = spendLimit;
							}
						}
						if(minPrice == maxPrice){
							minPrice=0.00;
						}
						
					ChildBidOrderListType childBidOrders = new ChildBidOrderListType();
					childBidOrders.setChildBidOrder(childBidSOList);
					serviceOrder.setChildBidSoList(childBidOrders);
					serviceOrder.setBidMinSpendLimit(Double.toString(minPrice));
					serviceOrder.setBidMaxSpendLimit(Double.toString(maxPrice));
					groupBidChildrenMap.remove(soId);
				}
			}
		}
	}

	//to convert from GMT time zone to so time zone
	private static void convertGMTToGivenTimeZone(List<OMServiceOrder> so){
		
		for(OMServiceOrder order : so){
			HashMap<String, String> formattedDates;
			if(null!=order.getParentGroupId())
			{
				if(order.getChildOrderList() != null){
				List<OMServiceOrder> childSos = order.getChildOrderList().getChildOrder();
				for(OMServiceOrder childSo : childSos){
				 formattedDates =	(HashMap<String, String>) convertServiceDates(childSo);
				 if(!childSo.getServiceLocationTimezone().equals(GMT_ZONE)){
					 String zone = convertTimeZone(childSo.getServiceLocationTimezone(), childSo.getDlsFlag(),childSo.getServiceStartDate(),childSo.getServiceTimeStart());
					 childSo.setServiceLocationTimezone(zone);
				 }
				 childSo.setAppointStartDate(formattedDates.get("date1"));
				 childSo.setAppointEndDate(formattedDates.get("date2"));
				 childSo.setServiceTimeStart(formattedDates.get("time1"));
				 childSo.setServiceTimeEnd(formattedDates.get("time2"));
				}
				}
			}
				 formattedDates =	(HashMap<String, String>) convertServiceDates(order);
				 if(!(order.getServiceLocationTimezone().equals(GMT_ZONE)) && !(order.getServiceLocationTimezone().equals(CST_ZONE)) && !(order.getServiceLocationTimezone().equals(EST_ZONE))){
					 String zone = convertTimeZone(order.getServiceLocationTimezone(), order.getDlsFlag(),order.getServiceStartDate(),order.getServiceTimeStart());
					 order.setServiceLocationTimezone(zone);
				 }
				 order.setAppointStartDate(formattedDates.get("date1"));
				 order.setAppointEndDate(formattedDates.get("date2"));
				 order.setServiceTimeStart(formattedDates.get("time1"));
				 order.setServiceTimeEnd(formattedDates.get("time2"));
				 //Formatting routed date
			if(order.getRoutedDate() != null){
				String routedDate = order.getRoutedDate();
				DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
				try {
					Date d = sdf1.parse(routedDate.substring(0, 10));
					String date = sdf2.format(d);
					order.setRoutedDate(date);
				} catch (ParseException e) {
					LOGGER.error(e.getMessage());
				}
			}
			//Formatting phone number
			String primaryNum = UIUtils.formatPhoneNumber(order.getEndCustomerPrimaryPhoneNumber());
			String alternateNum = UIUtils.formatPhoneNumber(order.getEndCustomerAlternatePhoneNumber());
			order.setEndCustomerPrimaryPhoneNumber(primaryNum);
			order.setEndCustomerAlternatePhoneNumber(alternateNum);
		}
	}
	
	private static Map<String, String> convertServiceDates(OMServiceOrder childSo){
		Map<String, Object> serviceDate1 = null;
		Map<String, Object> serviceDate2 = null;
		String date1 = "";
		String date2 = "";
		String time1 = "";
		String time2 = "";
		Map<String, String> formattedDates = new HashMap<String, String>();
		serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(childSo.getServiceStartDate(), childSo.getServiceTimeStart(), childSo.getServiceLocationTimezone());
		if (serviceDate1 != null && !serviceDate1.isEmpty()) {
			DateFormat sdfReturn = new SimpleDateFormat("MM/dd/yy");
			date1 = sdfReturn.format((serviceDate1.get(GMT_DATE)));
			time1 = (String) serviceDate1.get(GMT_TIME);
			formattedDates.put("date1", date1);
			formattedDates.put("time1", time1);
		}
		if (childSo.getServiceEndDate() != null && childSo.getServiceTimeEnd() != null) {
			serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(childSo.getServiceEndDate(), childSo.getServiceTimeEnd(), childSo.getServiceLocationTimezone());
			if (serviceDate2 != null && !serviceDate2.isEmpty()) {
				DateFormat sdfReturn = new SimpleDateFormat("MM/dd/yy");
				date2 = sdfReturn.format(serviceDate2.get(GMT_DATE));
				time2 = (String) serviceDate2.get(GMT_TIME);
				formattedDates.put("date2", date2);
				formattedDates.put("time2", time2);
			}	
		}
		return formattedDates;
	}
	
	private static String convertTimeZone(String zone, String flag, Timestamp date1, String time1){
		String timeZone = "";
		if ("Y".equals(flag)) {
			TimeZone tz = TimeZone.getTimeZone(zone);
			Timestamp timeStampDate = null;
			try {
				if (null != zone
						&& (StringUtils.isNotBlank(zone))) {
					java.util.Date dt = (java.util.Date) TimeUtils
							.combineDateTime(date1,time1);
					timeStampDate = new Timestamp(dt.getTime());
				}
			} catch (ParseException pe) {
				LOGGER.error(pe.getMessage());
			}
			if (null != timeStampDate) {
				boolean isDLSActive = tz.inDaylightTime(timeStampDate);
				if (isDLSActive) {
					timeZone = TimezoneMapper.getDSTTimezone(zone);
				} else {
					timeZone = TimezoneMapper.getStandardTimezone(zone);
				}
			}
		} else {
			timeZone = TimezoneMapper.getStandardTimezone(zone);
		}
		return timeZone;
	}
	
	public GetTabsResponse getResponseForTablistForFrontEnd(String firmId, String resourceId, boolean viewOrderPricing){
		LOGGER.info("Inside GetOrderManagementTabsService.execute()");
		GetTabsResponse getTabsResponse = new GetTabsResponse();
		OrderManagementMapper managementMapper = new OrderManagementMapper();
		if (null != firmId && null != resourceId) {
			List<String> tabList = getTablist(viewOrderPricing);
			LinkedHashMap<String, Integer> tabsWithCount = null;
			try {
				tabsWithCount = (LinkedHashMap<String, Integer>) getCountOfTabs(tabList, Integer.valueOf(firmId),viewOrderPricing);
				LOGGER.info("Tab Count Map :"+tabsWithCount.size());
			} catch (NumberFormatException e) {
				LOGGER.error(e);
				return createErrorResponseForGetTabCount(e);
			} catch (DataServiceException e) {
				LOGGER.error(e);
				return createErrorResponseForGetTabCount(e);
			}
			getTabsResponse = managementMapper.mapGetTabsResponse(tabsWithCount);
		}
		LOGGER.info("Leaving GetOrderManagementTabsService.execute()");
		return getTabsResponse;
	}
	/**
	 * Creates Error response for Get Tab Count API. This is called went wrong
	 * @param Exception
	 * @return IAPIResponse {@link GetTabsResponse}
	 * */
	private GetTabsResponse createErrorResponseForGetTabCount(Exception error) {
		GetTabsResponse getTabCountResponse = new GetTabsResponse();
		Results results = Results.getError(error.getMessage(),ServiceConstants.USER_ERROR_RC);
		getTabCountResponse.setResults(results);
		return getTabCountResponse;
	}
	
	/* Returns the list of Tabs that is displayed in Order Management main tab for a Provider.
	 * */
	private List<String> getTablist(boolean viewOrderPricing) {
		List<String> tabList = new  ArrayList<String>(Arrays.asList(INBOX_TAB, SCHEDULE_TAB,ASSIGN_PROVIDER_TAB,MANAGE_ROUTE_TAB, CONFIRM_APPT_WDW_TAB, PRINT_PAPERWORK_TAB, CURRENT_ORDERS_TAB,RESOLVE_PROBLEM_TAB,CANCELLATIONS_TAB, AWAITING_PAYMENT_TAB));
		try{
			if(viewOrderPricing){
				tabList.add(1, RESPOND_TAB);
				tabList.add(8, JOB_DONE_TAB);
			}
			// R12_0 adding new tab
			tabList.add(9, REVISIT_NEEDED_TAB);

		}catch (Exception e) {
			LOGGER.error(e);
		}
		return tabList;
	}

	/**
	 * Sort service order list if sorting is not handle by sql.
	 * @param orders : List of orders.
	 * @param fetchSORequest 
	 * @param sortColumn : Sort field
	 * @param sortOrder : Sort order ASC/DESC/ ASC will be default.
	 * @param endIndex : The limit to be applied on the list. The total number <br>
	 * of orders to be displayed in front end.
	 * **/
	public void sortOrderList(List<OMServiceOrder> orders, FetchSORequest fetchSORequest) {
		String sortColumn = fetchSORequest.getSortBy();
		String sortOrder = fetchSORequest.getSortOrder();
		Integer endIndex = fetchSORequest.getEndIndex();
		
		if(StringUtils.isNotBlank(sortColumn)){
			if(null != orders && orders.size() > 1){
				boolean javaSortFlag = Boolean.TRUE; //Sorting notification flag
				String displayTab = fetchSORequest.getTabName();
				if("Scope".equalsIgnoreCase(sortColumn)){
					Collections.sort(orders, new SoScopeComparator());
				} else if("ReportedOn".equalsIgnoreCase(sortColumn)){
					Collections.sort(orders, new ReportedDateComparator());
				} else if("ReportedBy".equalsIgnoreCase(sortColumn)){
					Collections.sort(orders, new ReportedByNameComparator());
				} else if("Availability".equalsIgnoreCase(sortColumn)){
					Collections.sort(orders, new ProductAvailabilityComparator());
				} else if("Price".equalsIgnoreCase(sortColumn) && ("Inbox".equalsIgnoreCase(displayTab) || "Respond".equalsIgnoreCase(displayTab))){
					Collections.sort(orders, new SoPriceComparator());
				} else if("JobDone".equalsIgnoreCase(sortColumn)){
					Collections.sort(orders, new JobDoneDateComparator());
				} 
				// for R12_0 new trip on date filter
				else if("TripOn".equalsIgnoreCase(sortColumn)){
					Collections.sort(orders, new TripOnDateComparator());
				} 
				/*else if("Part".equalsIgnoreCase(sortColumn)){
					Collections.sort(orders, new PartComparator());
				}*/ 
				
				else {
					javaSortFlag = Boolean.FALSE;
				}
				if(javaSortFlag){
					/*If DESC order sorting is needed, the reverse the ASC ordered list */
					if(StringUtils.isNotBlank(sortOrder) && SORT_ORDER_DESC.equalsIgnoreCase(sortOrder)){
						Collections.reverse(orders);
					}
					/*After sorting, retain only required number of Orders.
					 * This is called only when sorting is handled through JAVA. Other wise the list of
					 * orders will contain only the required number or orders. Limit is applied from query.
					 * */
					applyPagingLimitOnList(endIndex,orders);
				}
			}
		}
	}

	/**
	 * Method to trim the list of order so that only requested number of orders are displayed.
	 * This will be called when sorting of orders is done from Java side instead of SQL.
	 * @param limit : Number of orders to be displayed.
	 * @param orders : The list of Service Orders.
	 * **/
	private void applyPagingLimitOnList(Integer limit, List<OMServiceOrder> orders) {
		int listSize = orders.size();
		if(null == limit) {
			limit = Integer.valueOf(50);
		}
		limit = limit > listSize ? listSize : limit;
		orders.subList(limit,listSize).clear();
	}

	/**Sets formatted Availability Date**/
	public void formatAvailabilityDate(OMServiceOrder omOrder) {
		if(null == omOrder){
			return;
		}
		omOrder.setAvailabilityDate(formatDateInString(omOrder.getAvailabilityDate(), "yyyy-mm-dd", "mm/dd/yyyy"));
		if(StringUtils.isBlank(omOrder.getAvailabilityDate())){
			omOrder.setAvailabilityDate(formatDateInString(omOrder.getAvailabilityDate(), "mm/dd/yy", "mm/dd/yyyy"));
		}
	}
	
	/**
	 * Method which formats any a date in String from a given format to another given format.<br>
	 * @param date : Date in String
	 * @param inputFormat : Date format of argument date
	 * @param requiredFormate : Required format 
	 * @return Formatted date as String.
	 * **/
	private String formatDateInString(String date, String inputFormat, String requiredFormate){
		if (StringUtils.isBlank(date)) {
			return EMPTY_STR;
		}
		date = date.trim();
		String strDate = date;
		Date dt1 = stringToDate(strDate, inputFormat);
		DateFormat formatter = new SimpleDateFormat(requiredFormate);
		String formatedDate = EMPTY_STR;
		try{
			formatedDate = formatter.format(dt1);
		}catch (Exception exc) {
			LOGGER.error(exc.getMessage());
		}		
		return formatedDate;
	}
	
	/**
	 * Converts String to Date using the supplied Format.
	 * 
	 * @param strDate
	 *            : Date in String
	 * @param format
	 *            : Format of the source date.
	 * @return : Date : Null will be returned for any parse exception
	 * */
	private Date stringToDate(String strDate, String format) {
		DateFormat formatter;
		Date date = null;
		formatter = new SimpleDateFormat(format);
		try {
			date = (Date) formatter.parse(strDate);
		} catch (ParseException e) {
			LOGGER.error(e);
		}
		return date;
	}

	public boolean checkFilters(FetchSORequest fetchSORequest) {
		// TODO Auto-generated method stub
		return managementDao.checkFilters(fetchSORequest);
	}
	
	public List<AvailabilityDateVO> getProductAvailabilityDate(List<String> soidList){
		List<AvailabilityDateVO> availabilityDateList = new ArrayList<AvailabilityDateVO>();
		availabilityDateList =  managementDao.getProductAvailabilityDate(soidList);
		return availabilityDateList;
	}

	public void historyLogging(ProviderHistoryVO hisVO) {
		try {
			managementDao.insertHistory(hisVO);
		} catch (DataServiceException e) {
			LOGGER.error("Exception Occured in historyLogging");
			e.printStackTrace();
		}
		
	}

	/**
	 * @param soidListwithLimit
	 * @return
	 * 
	 * for fetching last trip date
	 */
	public List<OMServiceOrder> getLastTripForAllSOs(
			List<String> soidListwithLimit) {
		return managementDao.getLastTripForAllSOs(soidListwithLimit);	
		}

	public void updateSOSubstatusToScheduleConfirmed(String soId) {
		
		try {
			 managementDao.updateSOSubstatusToScheduleConfirmed(soId);	
		} catch (DataServiceException e) {
			LOGGER.error("Exception Occured in updateSOSubstatusToScheduleConfirmed");
			e.printStackTrace();
		}
		
	}

	public void updateTimeNotificationRelayServices(String soId) {
	try{
	//SL-21469   webhook event assign provider for relay buyer
	Integer buyerId=relayNotificationService.getBuyerId(soId); 
	boolean relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId); 
	if(relayServicesNotifyFlag){
    relayNotificationService.sentNotificationRelayServices("update_time_by_provider", soId); 
	}
	}
	catch(Exception e){
	LOGGER.error(" exception in invoking webhook event for updating time window"+e);	
	}
	
	}
	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}

	/**
	 * @param soidListwithLimit
	 * @return
	 * 
	 * for fetching PartDetails
	 */
	public List<InvoicePartsVO> getPartDetails(List<String> soidListwithLimit) {
		
		return managementDao.getPartDetails(soidListwithLimit);	
		
	}
	//SL-21645

	public List<Contact> getSOContactDetails(String serviceOrderID){
		return managementDao.getSOContactDetails(serviceOrderID);
	}
	
	public SoLocation getServiceLocDetails(String serviceOrderID){
		return managementDao.getServiceLocDetails(serviceOrderID);
	}
	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}
	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
	
	public PushNotificationAlertTask getPushNotificationAlertTask() {
		return pushNotificationAlertTask;
	}

	public void setPushNotificationAlertTask(PushNotificationAlertTask pushNotificationAlertTask) {
		this.pushNotificationAlertTask = pushNotificationAlertTask;
	}
}

