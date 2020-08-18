/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSORequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSOResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.InvoicePartVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RoutedProvidersVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.Task;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.TimeChangeUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.TimezoneMapper;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.ordermanagement.so.AvailabilityDateVO;
import com.newco.marketplace.vo.ordermanagement.so.ChildBidOrder;
import com.newco.marketplace.vo.ordermanagement.so.ChildBidOrderListType;
import com.newco.marketplace.vo.ordermanagement.so.ChildOrderListType;
import com.newco.marketplace.vo.ordermanagement.so.InvoicePartsVO;
import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.vo.ordermanagement.so.SOScope;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * This class would act as a Servicer class for Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
public class FetchSOService extends SOBaseService {
	private static final Logger LOGGER = Logger.getLogger(FetchSOService.class);

	private OrderManagementService managementService;
	private OrderManagementMapper omMapper ;

	public FetchSOService() {
		super(PublicAPIConstant.SO_FETCH_REQUEST_XSD,
				PublicAPIConstant.SO_FETCH_RESPONSE_XSD,
				PublicAPIConstant.SO_FETCH_RESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SO_FETCH_RESPONSE_SCHEMALOCATION,
				FetchSORequest.class, FetchSOResponse.class);
	}

	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		// logger.info("Inside FetchSOService.execute()");
		FetchSOResponse soFetchResponse = new FetchSOResponse();
		FilterVO filters = null;
		String providerId = (String) apiVO.getProviderId();
		// logger.info("Entering FetchSOService.execute()...About to fetch Order list");
		long startTime = System.currentTimeMillis();
		if (null != providerId) {
			FetchSORequest fetchSORequest = (FetchSORequest) apiVO.getRequestFromPostPut();
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
			List<OMServiceOrder> orders = managementService.getOrders(fetchSORequest,providerId);
			Map<String,OMServiceOrder> orderMap = new HashMap<String, OMServiceOrder>();
			
			// List<String> soidListwithLimit = new ArrayList<String>();
			if(null!=orders){
				// long startTime1 = System.currentTimeMillis();
				for(OMServiceOrder omOrder: orders){
					omOrder.setRoutedProviders(new ArrayList<RoutedProvidersVO>());
					omOrder.setScope(new ArrayList<Task>());
					orderMap.put(omOrder.getSoId(), omOrder);
					// soidListwithLimit.add(omOrder.getSoId());
				}
				// logger.info(String.format("Exiting FetchSOService.preparing SO List)...Time taken is %1$d ms", System.currentTimeMillis() - startTime1));
				if(null!=orderMap && orderMap.keySet().size()>0){
				// if(!(soidListwithLimit.isEmpty())){
					// long startTimequery = System.currentTimeMillis();
					List<RoutedProvidersVO> routedProviderList = new ArrayList<RoutedProvidersVO>();
					List<SOScope> soScopeList = new ArrayList<SOScope>();
					List<String> soidListwithLimit = new ArrayList<String>(orderMap.keySet());
					List<RescheduleVO> rescheduleList=new ArrayList<RescheduleVO>();
					rescheduleList=managementService.getRescheduleRole(soidListwithLimit);
					routedProviderList = managementService.getProviderForAllSOs(soidListwithLimit,providerId);
					soScopeList = managementService.getScopeForAllSOs(soidListwithLimit,providerId);
					List<AvailabilityDateVO> availabilityDateList = managementService.getProductAvailabilityDate(soidListwithLimit);
					// LOGGER.info(String.format("Exiting FetchSOService.QUERY...Time taken is %1$d ms", System.currentTimeMillis() - startTimequery));
					// long startTime2 = System.currentTimeMillis();
					if(null != availabilityDateList && !availabilityDateList.isEmpty()){
						//iterating over keys only
						for(AvailabilityDateVO dateVO: availabilityDateList){							
							if(orderMap.containsKey(dateVO.getSoId())){
								OMServiceOrder omOrder = orderMap.get(dateVO.getSoId());
								omOrder.setAvailabilityDate(dateVO.getAvailabilityDate());
								managementService.formatAvailabilityDate(omOrder);
								orderMap.put(omOrder.getSoId(), omOrder);
							}
						}
					}
					if(null!=rescheduleList)
					{
						for(RescheduleVO reschedule:rescheduleList)
						{
							if(orderMap.containsKey(reschedule.getSoId()))
									{
								if(orderMap.get(reschedule.getSoId()).getRescheduleRole()==null)
								{
								orderMap.get(reschedule.getSoId()).setRescheduleRole(reschedule.getRoleId())	;
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
					// logger.info(String.format("Exiting FetchSOService.preparing routedProviderList SO List)...Time taken is %1$d ms", System.currentTimeMillis() - startTime2));
					// long startTime3 = System.currentTimeMillis();
					if(null != soScopeList){
						for(SOScope scopeVO: soScopeList){
							if(orderMap.containsKey(scopeVO.getSoId())){
								Task task = new Task();
								task.setServiceType(scopeVO.getScope());
								orderMap.get(scopeVO.getSoId()).getScope().add(task);
							}
						}
					}
					// logger.info(String.format("Exiting FetchSOService.preparing soScopeList SO List)...Time taken is %1$d ms", System.currentTimeMillis() - startTime3));
					soidListwithLimit = new ArrayList<String>(orderMap.keySet());
					if("Job Done".equalsIgnoreCase(tabName)){
						List<OMServiceOrder> jobDoneList = new ArrayList<OMServiceOrder>();
						jobDoneList = managementService.getJobDoneForAllSOs(soidListwithLimit);
						if(null != jobDoneList){
							String soId = "";
							for(OMServiceOrder jobeDoneVO: jobDoneList){
								if(orderMap.containsKey(jobeDoneVO.getSoId()) && !soId.equals(jobeDoneVO.getSoId())){
									orderMap.get(jobeDoneVO.getSoId()).setJobDoneOn(jobeDoneVO.getJobDoneOn());
								}
								soId = jobeDoneVO.getSoId();
							}
						}
					}
					
					// for R12_0
					if( ("Revisit Needed").equalsIgnoreCase(tabName)){
						List<OMServiceOrder> lastTripList = new ArrayList<OMServiceOrder>();
						// List<InvoicePartsVO> partsList = new ArrayList<InvoicePartsVO>();
						lastTripList = managementService.getLastTripForAllSOs(soidListwithLimit);
						// partsList = managementService.getPartDetails(soidListwithLimit);
						if(null != lastTripList){
							String soId = "";
							for(OMServiceOrder lstTripVO: lastTripList){
								soId = lstTripVO.getSoId();
								if(orderMap.containsKey(soId)){
									orderMap.get(soId).setLastTripOn(lstTripVO.getLastTripOn());
								}
							}
						}
						
						/*if(null != partsList){
							for(InvoicePartsVO partsVO: partsList){
								if(orderMap.containsKey(partsVO.getSoId())){
									InvoicePartVO invoicePartVO = new InvoicePartVO();
									invoicePartVO.setPartDesc(partsVO.getPartDescription());
									orderMap.get(partsVO.getSoId()).getParts().add(invoicePartVO);
								}
							}
						}*/
					}
				}
			}
			// logger.info(String.format("Initial fetch completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			
			
			//Grouped So will be considered only under Inbox and Respond Tab in Order Management.
			if(("Inbox".equalsIgnoreCase(tabName) || "Respond".equalsIgnoreCase(tabName))){
				/* The list orders will contain grouped SOs, but we have to fing oll the child SOs ang group them.*/
				List<String> listGroupIds = getGroupIds(orders);
				if(null != listGroupIds && listGroupIds.size() > 0){
					fetchSORequest.setGroupInd(Boolean.TRUE);
					fetchSORequest.setGroupIds(listGroupIds);
					//Fetch all child SOs for the list of group Ids.
					List<OMServiceOrder> groupedOrders = managementService.getOrders(fetchSORequest,providerId);
					//Group the child SOs and add them to the list of SOs.
	 				groupChildOrders(orders, groupedOrders);
	 				// logger.info(String.format("Grouped Order processing completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
				}
			}
			
			if(("Inbox".equalsIgnoreCase(tabName)) || ("Respond".equalsIgnoreCase(tabName))){
				List<ChildBidOrder> bidChildOrders = findBidChildOrders(orders);
				groupBidChildOrders(orders,bidChildOrders);
				// logger.info(String.format("Bid Order processing completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			}
			
			/**Sorting service order List**/
			managementService.sortOrderList(orders,fetchSORequest);
			
			if(null != orders && orders.size()!=0){
					convertGMTToGivenTimeZone(orders);
				}
			// logger.info(String.format("Time zone Conversion inside FetchSOService completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			if(fetchSORequest.getLoadFiltersInd()){
				filters = managementService.getFilters(fetchSORequest,providerId);
			}
			// logger.info(String.format("Filter data fetch completed...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
			if(filters!=null){
			soFetchResponse = omMapper.mapFetchOrdersResponse(orders, filters);
			}
			soFetchResponse.setTotalSOCount(totalCount);
			boolean filterPresent = false;
			filterPresent = managementService.checkFilters(fetchSORequest);
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
				fetchSORequest.setCurrentOrdersSubStatus(null);
				//R12.0 Sprint3 adding Cancellation substatus filter
				fetchSORequest.setCancellationsSubStatus(null);
				//R12.0 Sprint4 adding Revisit substatus filter
				fetchSORequest.setRevisitSubStatus(null);
				
				int countWithoutFilter = getTotalCount(fetchSORequest, providerId);
				soFetchResponse.setSoCountWithoutFilters(countWithoutFilter);
			}
			else{
				soFetchResponse.setSoCountWithoutFilters(totalCount);
			}
		}
		LOGGER.info(String.format("Exiting fetch so service...Time taken is %1$d ms", System.currentTimeMillis() - startTime));
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
		if(!(fetchSORequest.getInitialLoadInd())){	
		try {
				totalCount = managementService.getTotalSOCountForTab(fetchSORequest, providerId);
			} catch (BusinessServiceException e) {
				LOGGER.error(e);
				totalCount = 0;
			}catch(Exception e){
				LOGGER.error(e);
				totalCount = 0;
			}
//		}
		}
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
			HashMap<String, String> formattedResheduleDates;
			if(null!=order.getParentGroupId())
			{
				if(order.getChildOrderList() != null){
				List<OMServiceOrder> childSos = order.getChildOrderList().getChildOrder();
				for(OMServiceOrder childSo : childSos){
				 formattedDates =	convertServiceDates(childSo);
				 if(!childSo.getServiceLocationTimezone().equals(OrderConstants.GMT_ZONE)){
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
			if(("ZERO_PRICE_BID").equals(order.getPriceModel()) && (order.getChildBidSoList() != null)) {
					List<ChildBidOrder> childBidSos = order.getChildBidSoList().getChildBidOrder();
					for(ChildBidOrder childBidSo : childBidSos){
						if(childBidSo.getOfferExpirationDate()!=null){
							DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							try {
								Date d = sdf1.parse(childBidSo.getOfferExpirationDate());
								TimeZone timeZone = TimeZone.getTimeZone(order.getServiceLocationTimezone());
								Calendar expirationDate = TimeChangeUtil.getCalTimeFromParts(d, timeZone);
								Date expiration = expirationDate.getTime();
								String date = sdf1.format(expiration);
								childBidSo.setOfferExpirationDate(date);
							} catch (ParseException e) {
								LOGGER.error(e.getMessage());
							}
						}
				}
			}
				 formattedDates =	convertServiceDates(order);
				//Formatting resheduleDates
					if(null != order.getResheduleStartDate() && null != order.getResheduleStartTime()){
							formattedResheduleDates = convertResheduleDates(order);
							order.setResheduleStartDateString(formattedResheduleDates.get("date1"));
							order.setResheduleEndDateString(formattedResheduleDates.get("date2"));
							order.setResheduleStartTime(formattedResheduleDates.get("time1"));
							order.setResheduleEndTime(formattedResheduleDates.get("time2"));
						
						
					}
				 if(!(order.getServiceLocationTimezone().equals(OrderConstants.GMT_ZONE)) && !(order.getServiceLocationTimezone().equals(OrderConstants.CST_ZONE)) && !(order.getServiceLocationTimezone().equals(OrderConstants.EST_ZONE))){
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
			//Making FirstLetter Capital of EndCustomers firstName and LastName.
			if(StringUtils.isNotBlank(order.getEndCustomerFirstName())){
				String lFirstName = StringUtils.lowerCase(order.getEndCustomerFirstName());
				String eFirstName = StringUtils.capitalize(lFirstName);
				order.setEndCustomerFirstName(eFirstName);
			}
			if(StringUtils.isNotBlank(order.getEndCustomerLastName())){
				String lLastName = StringUtils.lowerCase(order.getEndCustomerLastName());
				String eLastName = StringUtils.capitalize(lLastName);
				order.setEndCustomerLastName(eLastName);
			}
			//Formatting phone number
			String primaryNum = UIUtils.formatPhoneNumber(order.getEndCustomerPrimaryPhoneNumber());
			
			//if primary and alternate no are same,we display primary only,makes alternate null.
			/*if(StringUtils.isNotBlank(order.getEndCustomerPrimaryPhoneNumber()) &&
					StringUtils.isNotBlank(order.getEndCustomerAlternatePhoneNumber())){
				if(order.getEndCustomerPrimaryPhoneNumber().equals(order.getEndCustomerAlternatePhoneNumber())){
					order.setEndCustomerAlternatePhoneNumber(null);
				}else{
					String alternateNum = UIUtils.formatPhoneNumber(order.getEndCustomerAlternatePhoneNumber());
					order.setEndCustomerAlternatePhoneNumber(alternateNum);
				}
				
			}
			*/
			/*Code change to show primary and secondary phone numbers if the ext is different and primary phone number and alternative phone numbers are same*/
			if(StringUtils.isNotBlank(order.getEndCustomerPrimaryPhoneNumber())){
				if(StringUtils.isNotBlank(order.getEndCustomerAlternatePhoneNumber())){
					String alternateNum = UIUtils.formatPhoneNumber(order.getEndCustomerAlternatePhoneNumber());
					if(order.getEndCustomerPrimaryPhoneNumber().equals(order.getEndCustomerAlternatePhoneNumber())){
						if(StringUtils.isNotBlank(order.getPrimaryExtension()) && StringUtils.isNotBlank(order.getAlternateExtension())){
							if(order.getPrimaryExtension().equals(order.getAlternateExtension())){
								order.setEndCustomerAlternatePhoneNumber(null);
								order.setAlternateExtension(null);
							}else{
								order.setEndCustomerAlternatePhoneNumber(alternateNum);
							}
						}else{
							if(StringUtils.isBlank(order.getAlternateExtension()) && StringUtils.isNotBlank(order.getPrimaryExtension())){
								order.setEndCustomerAlternatePhoneNumber(alternateNum);
							}else if(StringUtils.isBlank(order.getPrimaryExtension()) && StringUtils.isNotBlank(order.getAlternateExtension())){
								order.setEndCustomerAlternatePhoneNumber(alternateNum);
							}else if(StringUtils.isBlank(order.getPrimaryExtension()) && StringUtils.isBlank(order.getAlternateExtension())){
								order.setEndCustomerAlternatePhoneNumber(null);
							}
							
						
						}
					}else{
						order.setEndCustomerAlternatePhoneNumber(alternateNum);
					}
				}else{
					order.setEndCustomerAlternatePhoneNumber(null);
					order.setAlternateExtension(null);
				}
			}
			/**/
			order.setEndCustomerPrimaryPhoneNumber(primaryNum);
			
		}
	}
	
	private static HashMap<String, String> convertResheduleDates(OMServiceOrder order) {
		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		String date1 = "";
		String date2 = "";
		String time1 = "";
		String time2 = "";
		HashMap<String, String> formattedDates = new HashMap<String, String>();
		serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(order.getResheduleStartDate(), order.getResheduleStartTime(), order.getServiceLocationTimezone());
		if (serviceDate1 != null && !serviceDate1.isEmpty()) {
			DateFormat sdfReturn = new SimpleDateFormat("MM/dd/yy");
			date1 = sdfReturn.format((serviceDate1.get(OrderConstants.GMT_DATE)));
			time1 = (String) serviceDate1.get(OrderConstants.GMT_TIME);
			formattedDates.put("date1", date1);
			formattedDates.put("time1", time1);
		}
		if (order.getResheduleEndDate() != null && order.getResheduleEndTime() != null) {
			serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(order.getResheduleEndDate(), order.getResheduleEndTime(), order.getServiceLocationTimezone());
			if (serviceDate2 != null && !serviceDate2.isEmpty()) {
				DateFormat sdfReturn = new SimpleDateFormat("MM/dd/yy");
				date2 = sdfReturn.format(serviceDate2.get(OrderConstants.GMT_DATE));
				time2 = (String) serviceDate2.get(OrderConstants.GMT_TIME);
				formattedDates.put("date2", date2);
				formattedDates.put("time2", time2);
			}	
		}
		return formattedDates;
	}

	private static HashMap<String, String> convertServiceDates(OMServiceOrder childSo){
		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		String date1 = "";
		String date2 = "";
		String time1 = "";
		String time2 = "";
		HashMap<String, String> formattedDates = new HashMap<String, String>();
		serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(childSo.getServiceStartDate(), childSo.getServiceTimeStart(), childSo.getServiceLocationTimezone());
		if (serviceDate1 != null && !serviceDate1.isEmpty()) {
			DateFormat sdfReturn = new SimpleDateFormat("MM/dd/yy");
			date1 = sdfReturn.format((serviceDate1.get(OrderConstants.GMT_DATE)));
			time1 = (String) serviceDate1.get(OrderConstants.GMT_TIME);
			formattedDates.put("date1", date1);
			formattedDates.put("time1", time1);
		}
		if (childSo.getServiceEndDate() != null && childSo.getServiceTimeEnd() != null) {
			serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(childSo.getServiceEndDate(), childSo.getServiceTimeEnd(), childSo.getServiceLocationTimezone());
			if (serviceDate2 != null && !serviceDate2.isEmpty()) {
				DateFormat sdfReturn = new SimpleDateFormat("MM/dd/yy");
				date2 = sdfReturn.format(serviceDate2.get(OrderConstants.GMT_DATE));
				time2 = (String) serviceDate2.get(OrderConstants.GMT_TIME);
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
	
	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}

	public OrderManagementMapper getOmMapper() {
		return omMapper;
	}

	public void setOmMapper(OrderManagementMapper omMapper) {
		this.omMapper = omMapper;
	}

	@Override
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}

}