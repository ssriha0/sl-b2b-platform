/*
 * @(#)OrderGroupRoutingProcessor.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.batch.serviceorder;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.conditionalautorouting.IConditionalAutoRoutingBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.serviceOrderTabsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.util.TimestampUtils;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.buyer.BuyerHoldTimeVO;
import com.servicelive.routing.tiered.services.TierRouteController;

/**
 * @author Mahmud Khair
 *
 */
public class OrderGroupRoutingProcessor {
	private static final Logger logger = Logger.getLogger(OrderGroupRoutingProcessor.class);
	private IServiceOrderSearchBO searchBO;
	private IBuyerBO buyerBO;
	private IOrderGroupBO orderGroupBO;
	private IRouteOrderGroupBO routeOrderGroupBO;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private TierRouteController trRouteController;
	private IServiceOrderBO serviceOrderBO;
	private IConditionalAutoRoutingBO conditionalAutoRoutingBO;
	
	public int execute() throws BusinessServiceException {
		
    	// Process orders that are waiting for routing
		Map<String,List<ServiceOrderSearchResultsVO>> groupMap = getOrdersHoldForRouting();
		
		if (groupMap.size() > 0) {
			for (String groupId : groupMap.keySet()) {
            	if (groupId.equals(OrderConstants.INDIVIDUAL_ORDER)) {
					processIndividualOrders(groupMap.get(OrderConstants.INDIVIDUAL_ORDER));
				} else 
					if (groupId.equals(OrderConstants.INDIVIDUAL_CONDITIONAL_ORDERS)) {
					processConditionalOrders(groupMap.get(OrderConstants.INDIVIDUAL_CONDITIONAL_ORDERS));
				} else  if (groupId.equals(OrderConstants.READY_FOR_POSTING_ORDERS)) {
					routeIndividualOrders(groupMap.get(OrderConstants.READY_FOR_POSTING_ORDERS));
				} else {
					//Route the grouped orders
					/* send tier route reason starting, this reason is used only for Tier rotuing */
	        		processGroupedOrders(groupMap.get(groupId),SPNConstants.TR_REASON_ID_START_TIER_ROUTING);
	        	}
			}
		}
        return (0);
	}

	private void processIndividualOrder(ServiceOrderSearchResultsVO searchResultsVO) {
		try {
			String groupId = null;
			List<ServiceOrderSearchResultsVO> groupedOrders;
			// If there is matching order(s) in posted status, group this order with the posted order(s)
			groupId = orderGroupBO.getParentMatchForSO(searchResultsVO.getSoId(), String.valueOf(OrderConstants.ROUTED_STATUS));

			// If matching order(s) found in posted status, get all the orders posted status for the group and this order for reposting
			if (!StringUtils.isBlank(groupId)) {
				groupedOrders = orderGroupBO.getServiceOrdersForGroup(groupId);
				logger.info("individual order is grouped with group#" + groupId);
				/*
				 * send tier route reason as restart, in this case it would be
				 * added to Grp, this reason is used for Tier routing
				 */
				processGroupedOrders(groupedOrders, SPNConstants.TR_REASON_ID_RESTART_TIER_ROUTING);
			} else {
				// Post the individual order
				logger.info("Routing individual order " + searchResultsVO.getSoId());
				routeIndividualOrder(searchResultsVO);
			}
		} catch (Exception e) {
			logger.error("Auto routing failed for individual service order#" + searchResultsVO.getSoId(), e);
		}
	}

	private void routeIndividualOrder(
			ServiceOrderSearchResultsVO searchResultsVO)
			throws com.newco.marketplace.exception.core.BusinessServiceException,
			BusinessServiceException {
		String soId = searchResultsVO.getSoId();
		ServiceOrder so = serviceOrderBO.getServiceOrder(soId);
		boolean isTierRoutingEnabled = trRouteController.isTierRoutingRequried(so.getSpnId(), so.getBuyer().getBuyerId());

		if (isTierRoutingEnabled) {
			trRouteController.route(soId, Boolean.FALSE, SPNConstants.TR_REASON_ID_START_TIER_ROUTING);
		} else {
			routeOrderGroupBO.routeIndividualOrder(searchResultsVO, OrderConstants.NON_TIER);
		}
	}

	/**
	 * Processes a list of individual orders in draft status
	 * 
	 * @param individualOrders
	 * @throws BusinessServiceException
	 */
	private void processIndividualOrders(List<ServiceOrderSearchResultsVO> individualOrders) 
			throws BusinessServiceException {
		logger.info("===> STARTED INDIVIDUAL ROUTING...");				
		for (ServiceOrderSearchResultsVO searchResultsVO : individualOrders) {
			processIndividualOrder(searchResultsVO);
			
		}
		logger.info("===> FINISHED INDIVIDUAL ROUTING...");
	}
	
	/**
	 * 
	 * @param individualOrders
	 */
	private void processConditionalOrders(List<ServiceOrderSearchResultsVO> individualOrders) {
		for (ServiceOrderSearchResultsVO searchResultsVO : individualOrders) {
			try {
				conditionalAutoRoutingBO.routeIndividualConditionalOrder(searchResultsVO);
			} catch (Exception e) {
				logger.error("Auto routing failed for conditional service order#" + searchResultsVO.getSoId(), e);
			}
		}
	}

	
	private void routeIndividualOrders(List<ServiceOrderSearchResultsVO> list) {
		for (ServiceOrderSearchResultsVO searchResultsVO : list) {
			try {
				routeIndividualOrder(searchResultsVO);
			} catch (Exception e) {
				logger.error("Auto routing failed for ready for posting service order#" + searchResultsVO.getSoId(), e);
			}
		}	
	}
	
	/**
	 * Processes a list of grouped orders in draft status
	 * 
	 * @param groupedOrders
	 */
	public  void processGroupedOrders(List<ServiceOrderSearchResultsVO> groupedOrders, Integer tierReasonId) {
		if(groupedOrders == null || groupedOrders.isEmpty()) {
			return;
		}

		try {
			//Calculate the price for Order Group
			orderGroupBO.priceOrderGroup(groupedOrders);
			String groupId = groupedOrders.get(0).getParentGroupId();
			/* spnId will be updated only in case if grouped orders had different spnId which would be rare */
			boolean isValidSpnForTier = routeOrderGroupBO.validateAndUpdateSpnForGroupedOrders(groupId);
			
			//Repost the orders
			logger.info("Routing orders for group#" + groupedOrders.get(0).getParentGroupId());
			ServiceOrderSearchResultsVO firstOrderInGroup = groupedOrders.get(0);
			ServiceOrder firstSo = serviceOrderBO.getServiceOrder(firstOrderInGroup.getSoId());
			SecurityContext  securityContext = ServiceOrderUtil.getSecurityContextForBuyer(firstSo.getBuyer().getBuyerId());
			if ( isValidSpnForTier &&
					trRouteController.isTierRoutingRequried(firstSo.getSpnId(), firstSo.getBuyer().getBuyerId()) ) {
				trRouteController.route(firstOrderInGroup.getParentGroupId(), Boolean.TRUE, tierReasonId);
			} else { 
				routeOrderGroupBO.processRouteOrderGroup(groupedOrders, OrderConstants.NON_TIER, securityContext);
			}
		} catch (Exception e) {
			logger.error("Auto routing failed for group service order#" + groupedOrders.get(0).getParentGroupId(), e);
		}
	}

	/**
     * This method returns a list of service orders that is waiting for routing with
     * a substatus "Queued for Grouping". 
     *
     * The criteria for routing the order is - 
     * 		Status has to be DRAFT
     *      Substatus has to be "Queued for Grouping"
     *      The Hold time needs to pass the configured hold time in buyer_hold_time table     
     *      
     *      
     * @return List of service orders that are in hold for routing
	 * 
	 */
	
	public Map<String,List<ServiceOrderSearchResultsVO>> getOrdersHoldForRouting() {
    	Map<String,List<ServiceOrderSearchResultsVO>> groupMap = new Hashtable<String,List<ServiceOrderSearchResultsVO>>();
		serviceOrderTabsVO request = new  serviceOrderTabsVO();
		
		try {
			List<Integer> buyerIdsForAutoRouting = buyerFeatureSetBO.getBuyerIdsForFeature(BuyerFeatureConstants.AUTO_ROUTE);
			groupMap.put(OrderConstants.INDIVIDUAL_ORDER, new ArrayList<ServiceOrderSearchResultsVO>());
			request.setSoStatus(String.valueOf(OrderConstants.DRAFT_STATUS));
			request.setSoSubStatus(Integer.valueOf(OrderConstants.QUEUED_FOR_GROUPING_SUBSTATUS));
			
			for (ServiceOrderSearchResultsVO searchResultsVO : searchBO.findServiceOrderByStatusForBatch(request)) {
				//Check if this buyer is eligible for auto routing
				if (buyerIdsForAutoRouting.contains(searchResultsVO.getBuyerID())) {
					makeAvailableForAutoRouting(groupMap, searchResultsVO);
				}
			}
			
			request.setSoSubStatus(Integer.valueOf(OrderConstants.READY_FOR_POSTING_SUBSTATUS));
			groupMap.put(OrderConstants.READY_FOR_POSTING_ORDERS, new ArrayList<ServiceOrderSearchResultsVO>());
			for (ServiceOrderSearchResultsVO searchResultsVO : searchBO.findServiceOrderByStatusForBatch(request)) {
				if (buyerIdsForAutoRouting.contains(searchResultsVO.getBuyerID()) && !searchResultsVO.getBuyerID().equals(BuyerConstants.OMS_BUYER_ID)) {
					groupMap.get(OrderConstants.READY_FOR_POSTING_ORDERS).add(searchResultsVO);
				}
			}
			
			// get list of orders qualified for conditional routing and add to the map
			// these orders are not queued for grouping, so need to check for buyer hold time
			groupMap.put(OrderConstants.INDIVIDUAL_CONDITIONAL_ORDERS, new ArrayList<ServiceOrderSearchResultsVO>());
			List<Integer> buyerIdsForConditionalRouting = buyerFeatureSetBO.getBuyerIdsForFeature(BuyerFeatureConstants.CONDITIONAL_ROUTE);
			for (ServiceOrderSearchResultsVO searchResultsVO : searchBO.findServiceOrderForConditionalRouting(request)) {
				if (buyerIdsForAutoRouting.contains(searchResultsVO.getBuyerID())
						&& buyerIdsForConditionalRouting.contains(searchResultsVO.getBuyerID())) {
					groupMap.get(OrderConstants.INDIVIDUAL_CONDITIONAL_ORDERS).add(searchResultsVO);
				}
			}
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			groupMap.clear();
		}		
		return groupMap;

    }

	/**
	 * If the hold time of the order is expired, put the order in the map so that it gets auto routed
	 * 
	 * @param groupMap
	 * @param searchResultsVO
	 * @throws ParseException
	 * @throws BusinessServiceException
	 */
	private void makeAvailableForAutoRouting(
			Map<String, List<ServiceOrderSearchResultsVO>> groupMap,
			ServiceOrderSearchResultsVO searchResultsVO) throws ParseException,
			BusinessServiceException {
		//If this is order does not belong to any group and hold time expired, put into individual Orders queue
		if (searchResultsVO.getParentGroupId() == null) {
			if (isHoldTimeExpired(searchResultsVO)) {
				groupMap.get(OrderConstants.INDIVIDUAL_ORDER).add(searchResultsVO);
			}
		}
		//If this group is not already put into list for routing and hold time expired, get all the orders
		//in the group and put into queue for routing
		else if (!groupMap.containsKey(searchResultsVO.getParentGroupId())) {
			if (isHoldTimeExpired(searchResultsVO)) {
				groupMap.put(searchResultsVO.getParentGroupId(), 
						orderGroupBO.getServiceOrdersForGroup(searchResultsVO.getParentGroupId()));
			}
		}
	}
    
    /**
     * This method checks if the hold time is expired for specified service order
     * 
     * @param searchResultsVO
     * @return true if the hold time expired, otherwise false
     */
    public boolean isHoldTimeExpired(ServiceOrderSearchResultsVO searchResultsVO) {
    	try {
    		Timestamp createdDate = searchResultsVO.getCreatedDate();
    		Timestamp currentDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
    		if (searchResultsVO.getServiceDate1() == null) {
    			throw new Exception("Service order " + searchResultsVO.getSoId() 
    					+ " will not be auto routed since start service date is null");    					
    		}
    		Timestamp serviceDate = new Timestamp(TimeUtils.combineDateTime(searchResultsVO.getServiceDate1(),searchResultsVO.getServiceTimeStart()).getTime());
        	Integer dayDiff = Integer.valueOf(TimestampUtils.getDayDifference(createdDate, serviceDate));
        	//If service date1 is expired do not route
        	if (dayDiff.intValue() < 0) {
        		throw new Exception("Service order " + searchResultsVO.getSoId() 
    					+ " will not be auto routed since start service date is expired");    
    		}
        	Integer maxDayDiff = buyerBO.getMaxDayDiff(searchResultsVO.getBuyerID());
        	//There is no hold time configured for this buyer, so route the order
        	if (maxDayDiff == null) {
    			return true;
    		}
        	//Service date is after the configured day difference, so take the maxDayDiff for getting hold time
        	if (dayDiff.intValue() > maxDayDiff.intValue()) {
    			dayDiff = maxDayDiff;
    		}
        	
    		BuyerHoldTimeVO buyerHoldTime =	buyerBO.getBuyerHoldTimeByDayDiffAndBuyerID(dayDiff, searchResultsVO.getBuyerID());
    		
    		return TimeUtils.isPastXTime(createdDate, currentDate, buyerHoldTime.getHoldTime().intValue()*Constants.TIME_CONSTANTS.SECONDS_IN_MINUTE*Constants.TIME_CONSTANTS.MILISECONDS_IN_SECOND);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;			
		}    	
    }

	/**
	 * @return the searchBO
	 */
	public IServiceOrderSearchBO getSearchBO() {
		return searchBO;
	}

	/**
	 * @param searchBO the searchBO to set
	 */
	public void setSearchBO(IServiceOrderSearchBO searchBO) {
		this.searchBO = searchBO;
	}

	/**
	 * @return the buyerBO
	 */
	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	/**
	 * @param buyerBO the buyerBO to set
	 */
	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	/**
	 * @return the orderGroupBO
	 */
	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}

	/**
	 * @param orderGroupBO the orderGroupBO to set
	 */
	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}

	public IRouteOrderGroupBO getRouteOrderGroupBO() {
		return routeOrderGroupBO;
	}

	public void setRouteOrderGroupBO(IRouteOrderGroupBO routeOrderGroupBO) {
		this.routeOrderGroupBO = routeOrderGroupBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}


    public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IConditionalAutoRoutingBO getConditionalAutoRoutingBO() {
		return conditionalAutoRoutingBO;
	}

	public void setConditionalAutoRoutingBO(
			IConditionalAutoRoutingBO conditionalAutoRoutingBO) {
		this.conditionalAutoRoutingBO = conditionalAutoRoutingBO;
	}

	public TierRouteController getTrRouteController() {
		return trRouteController;
	}

	public void setTrRouteController(TierRouteController trRouteController) {
		this.trRouteController = trRouteController;
	}
}
