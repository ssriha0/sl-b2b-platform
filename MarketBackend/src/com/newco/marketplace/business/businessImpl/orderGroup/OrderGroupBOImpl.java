package com.newco.marketplace.business.businessImpl.orderGroup;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpdateBO;
import com.newco.marketplace.dto.vo.EstimateHistoryVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ResponseSoVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.admin.IAdminTripChargeDAO;
import com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.orderGroup.ServiceOrderGroupMatchVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.business.ABaseBO;
import com.sears.os.service.ServiceConstants;
import com.servicelive.routingrulesengine.services.OrderProcessingService;

public class OrderGroupBOImpl extends ABaseBO implements IOrderGroupBO{

	private static final Logger logger = Logger.getLogger(OrderGroupBOImpl.class
			.getName());
	
	private IOrderGroupDao orderGroupDAO;
	private IServiceOrderBO serviceOrderBO;
	private ServiceOrderDao serviceOrderDAO;
	private IAdminTripChargeDAO adminTripChargeDAO;
	private ILookupDAO lookupDao;
	private IServiceOrderUpdateBO serviceOrderUpdateBO;
	private IFinanceManagerBO financeManagerBO;
	private ILedgerFacilityBO transBo;
	private OrderProcessingService orderProcessingService;
	
	public OrderProcessingService getOrderProcessingService() {
		return orderProcessingService;
	}
	public void setOrderProcessingService(
			OrderProcessingService orderProcessingService) {
		this.orderProcessingService = orderProcessingService;
	}
	public List<Long> findGroupableOrders() {
	
		List<Long> groupableOrders = new ArrayList<Long>();
		String result = getOrderGroupDao().getTestString();
		if(result.equals("Success")){
			groupableOrders.add(new Long(5) );
		}
		return groupableOrders;
	}
	public String getParentMatchForSO(String soId, String soStatus) throws BusinessServiceException{
		String parentGroupId = null;
		try{
			ServiceOrder currentSO = serviceOrderBO.getServiceOrder(soId);
			parentGroupId = getParentMatchForSO(currentSO, soStatus);
		}catch(Exception e){
			throw new BusinessServiceException("Exception occured while getting match for Service Order-->SearchOrderGroupBOImpl", e);
		}
		
		return parentGroupId;
		
	}
	
	public String getParentMatchForSO(ServiceOrder currentSO, String soStatus) throws BusinessServiceException{
		String groupId = null;
		List<ServiceOrderGroupMatchVO> soListWithMatch = new ArrayList<ServiceOrderGroupMatchVO>();
		try{
			
			if(currentSO.getWfSubStatusId()!= null && 
					currentSO.getWfSubStatusId() == OrderConstants.MISSING_INFORMATION_SUBSTATUS){
				return null;
			}
			ServiceOrderGroupMatchVO soToQuery = formSOObjToMatch(currentSO, soStatus);
			
			soListWithMatch = orderGroupDAO.getParentMatchForAddressAndCategory(soToQuery);
			
			// Filter out orders non-eligible for grouping from the list
			soListWithMatch = filterSoList(soListWithMatch);
			
			if(soListWithMatch!= null && soListWithMatch.size() > 0){
				groupId = findMatchForServiceDt(currentSO, soListWithMatch);
				logger.info("OrderGroupBOImpl-->Grouped Order to group with parent group ID & order no --> "
						+ groupId  +"," + currentSO.getSoId());
				
				//get the bo bean again for AOP to fire
				IOrderGroupBO bo = (IOrderGroupBO) MPSpringLoaderPlugIn.getCtx().getBean("soOrderGroupBOAOP");
				
				// update currentSO parent group Id
				bo.addToOrderGrp(currentSO, groupId);
				for(ServiceOrderGroupMatchVO matchSO : soListWithMatch){
					// the orders that wern't grouped earlier.
					if(matchSO.getParentGroupId() == null || "".equals(matchSO.getParentGroupId())){
						String matchSoId = matchSO.getSoId();
						if(matchSoId!= null && !matchSoId.equals(currentSO.getSoId())){		
							ServiceOrder  servcieOrderMatch = serviceOrderBO.getServiceOrder(matchSoId);
							bo.addToOrderGrp(servcieOrderMatch, groupId);							
						}

					}
				}
				populateSOGroupPrice(groupId);
				
			}else{  // if no match found sub-status queued for grouping
				setSubStatusToQForGrouping(currentSO.getSoId());
				logger.info("OrderGroupBOImpl-->Couldn't find order in " + soStatus + " to group --> no match Found for So Id " + currentSO.getSoId());
			}

		}catch(Exception e){
			throw new BusinessServiceException("Exception occured while getting match for Service Order-->SearchOrderGroupBOImpl", e);
		}
		
		return groupId;
	}

	private List<ServiceOrderGroupMatchVO> filterSoList(List<ServiceOrderGroupMatchVO> soListWithMatch) {
		List<ServiceOrderGroupMatchVO> resultList = new ArrayList<ServiceOrderGroupMatchVO>();
		
		for (ServiceOrderGroupMatchVO so : soListWithMatch) {
			Integer routingRuleId;
			try {
				// this will remove CAR orders from the groupping list
				routingRuleId = orderProcessingService.findRuleIdForSO(so.getSoId());
				if (routingRuleId == null) {
					resultList.add(so);
				}
			} catch (Exception e) {
				logger.error("OrderGoupBOImpl.filterSoList " + e.getMessage());
			}
			
		}
		
		return resultList;
	}
	
	public  void populateSOGroupPrice(String groupId) throws BusinessServiceException {
		List<ServiceOrderSearchResultsVO> serviceOrdersInGrp = this.getServiceOrdersForGroup(groupId); 
		double grouplaborPrice = 0.0;
		double groupPartsPrice = 0.0;
		double groupTotalPermitsPrice = 0.0;
		try{
			for(ServiceOrderSearchResultsVO serviceOrder : serviceOrdersInGrp){
				grouplaborPrice = serviceOrder.getOriginalSpendLimitLabor() + grouplaborPrice ;
				groupPartsPrice = serviceOrder.getOriginalSpendLimitParts() + groupPartsPrice ;
				groupTotalPermitsPrice = serviceOrder.getTotalPermitPrice() + groupTotalPermitsPrice;
			}
			OrderGroupVO soGroupVO = new OrderGroupVO();
			soGroupVO.setOriginalSpendLimitLabor(grouplaborPrice);
			soGroupVO.setOriginalSpendLimitParts(groupPartsPrice);
			soGroupVO.setFinalSpendLimitLabor(grouplaborPrice);
			soGroupVO.setFinalSpendLimitParts(groupPartsPrice);
			soGroupVO.setTotalPermitPrice(groupTotalPermitsPrice);
			soGroupVO.setGroupId(groupId);

			if(!isGrpPriceRecordExists(groupId)){
				orderGroupDAO.insertIntoGroupPrice(soGroupVO);
			}else{
				orderGroupDAO.updateOrderGroupPrice(soGroupVO);
			}
			
		}catch(Exception e){
			throw new BusinessServiceException("Exception occured while inserting so GroupPrice-->SearchOrderGroupBOImpl", e);
		}
		
		
	}
	
	private boolean isGrpPriceRecordExists(String groupId) throws BusinessServiceException {
		boolean soGrpPriceRecordExists = false;
		OrderGroupVO orderGrp = this.getOrderGroupByGroupId(groupId);
		if(orderGrp != null){
			soGrpPriceRecordExists = true;
		}
			
		return soGrpPriceRecordExists;
	}
	
	/**
	 * JIRA SL-9966: Making function synchronized
	 * @param soId
	 * @throws BusinessServiceException
	 */
	private synchronized void setSubStatusToQForGrouping(String soId) throws BusinessServiceException {
		
		try{
			orderGroupDAO.updateSubStatusToQForGrouping(soId);
		}catch(Exception e){
			logger.info("Exception -- update SO with sub Statua Q for Grp-->SearchOrderGroupBOImpl",e);
			throw new BusinessServiceException("Exception occured while updating SO with sub Status Q for Grp-->SearchOrderGroupBOImpl", e);
		}
		
		
	}

	/**
	 * find the match for SO service date from the list of SO which had address & main category match
	 * @param ServiceOrder currentSO -- which as to be grouped
	 * @param List<ServiceOrderGroupMatchVO> soListWithMatch -- so's which are matched for Address & main cat
	 */
	private String findMatchForServiceDt(ServiceOrder currentSO,
												List<ServiceOrderGroupMatchVO> soListWithMatch) {
		
		String parentGroupId = null;
		Timestamp groupServiceDate = null;
		ServiceOrderGroupMatchVO soOrderWithMatch = null;
		try{
			if(soListWithMatch!= null && soListWithMatch.size()> 0){
				
				/* iterate thru all order in case if order were not grouped earlier */
				for(ServiceOrderGroupMatchVO matchOrder : soListWithMatch){
					if(!matchOrder.getSoId().equals(currentSO.getSoId())){
						parentGroupId = matchOrder.getParentGroupId()!= null ?
			                    matchOrder.getParentGroupId().toString() : null;
			                    if(parentGroupId != null){
			                    	groupServiceDate = soOrderWithMatch.getServiceDate1();
			                    	break;	                    	
			                    }
					}
				}
 			}
			
			//if order group already exists
			if(parentGroupId != null && !"".equals(parentGroupId) && groupServiceDate!= null){
				return parentGroupId;
				
			}
			
			//if order group doesn't  exists
			if(parentGroupId == null || "".equals(parentGroupId) ){
				parentGroupId = createOrderGroup(currentSO);
				
			}
		}catch(Exception e){
			String msg = "Error occured while looking for match to group orders in findMatchForServiceDt";
			logger.error(msg);
		}
		
		return parentGroupId;

	}

	public void addToOrderGrp(ServiceOrder currentSO,
			String parentGroupId) throws BusinessServiceException{
		
		try{
			if (currentSO.getWfStateId().intValue() != OrderConstants.ROUTED_STATUS) {
				setSubStatusToQForGrouping(currentSO.getSoId());
			}
			String soId = currentSO.getSoId();
			orderGroupDAO.addToOrderGroup(soId, parentGroupId);
		}catch(Exception e){
			logger.info("Error occured while adding SO to order group -- SearchOrderGroupBOImpl.addToOrderGrp", e);
			throw new BusinessServiceException("Error occured", e);
		}
		
	}

	public String createOrderGroupByBuyerId(Integer buyerId, String groupTitle)
	{
		if(buyerId == null)
			return null;
		
		String parentGrpId = null;
		try
		{
			parentGrpId = serviceOrderBO.generateOrderNo(buyerId);
			if(parentGrpId != null)
				orderGroupDAO.createOrderGroup(parentGrpId, groupTitle);
		
		}
		catch(Exception e)
		{
			logger.info("Error occured while creating new order Group -- SearchOrderGroupBOImpl.createOrderGroup", e);
		}
		
		return parentGrpId;		
	}
	
	
	public String createOrderGroup(ServiceOrder currentSO) throws BusinessServiceException{
		
		String parentGrpId = null;
		try
		{
			String primarySkill = getMainCategoryDesc(currentSO.getPrimarySkillCatId());
			
			Buyer buyer = currentSO.getBuyer();
			if(buyer!= null && buyer.getBuyerId() != null)
			{	
				String groupTitle = "Grouped Order - " + primarySkill; 
				parentGrpId = createOrderGroupByBuyerId(buyer.getBuyerId(), groupTitle);
			}		
		}
		catch(Exception e)
		{
			logger.info("Error occured while creating new order Group -- SearchOrderGroupBOImpl.createOrderGroup", e);
		}
		
		return parentGrpId;
	}
	
	public String getMainCategoryDesc(Integer primarySkillCatId)  {
		String mainCategoryDesc= "";
		try{
			 List<LookupVO> primaryIndustryList = lookupDao.loadPrimaryIndustry();
			 for(LookupVO lookupVO: primaryIndustryList){
				 if(lookupVO.getId().equals(primarySkillCatId.toString())){
					 mainCategoryDesc = lookupVO.getDescr();
				 }
			 }
			
		}catch(Exception e){
			logger.error("Error while getting Main Category name for SKill Cat Is --> " + primarySkillCatId.toString());
		}

		return mainCategoryDesc;
	}
	
	public void addToOrderGroup(String soId, String parentGroupId) throws BusinessServiceException
	{
		try
		{
			ServiceOrder currentSO = serviceOrderBO.getServiceOrder(soId);
			if (currentSO.getWfStateId().intValue() != OrderConstants.ROUTED_STATUS) {
				setSubStatusToQForGrouping(soId);
			}
			orderGroupDAO.addToOrderGroup(soId, parentGroupId);
		}
		catch(Exception e){
			logger.info("Error occured while adding SO to order group -- OrderGroupBOImpl.addToOrderGrp", e);
			throw new BusinessServiceException("Error occured", e);
		}
	}

	private ServiceOrderGroupMatchVO formSOObjToMatch(ServiceOrder currentSO, String statusToCompare) {
		ServiceOrderGroupMatchVO soToQuery = new ServiceOrderGroupMatchVO();
		soToQuery.setPrimarySkillCatId(currentSO.getPrimarySkillCatId());
		soToQuery.setSoId(currentSO.getSoId());
		
		Buyer buyer = currentSO.getBuyer();
		if(buyer!= null && buyer.getBuyerId() != null){
			soToQuery.setBuyerId(buyer.getBuyerId());
		}
		
		SoLocation soLocation = currentSO.getServiceLocation();
		if(soLocation!= null){
			soToQuery.setCity(soLocation.getCity());
			soToQuery.setZip(soLocation.getZip());
			soToQuery.setStateCd(soLocation.getState());
			soToQuery.setStreetAdd1(soLocation.getStreet1());
			soToQuery.setStreetAdd2(soLocation.getStreet2());
		}
		
		soToQuery.setStatus(statusToCompare);
		soToQuery.setServiceDate1(currentSO.getServiceDate1());
		soToQuery.setLocationType(String.valueOf(OrderConstants.SERVICE_LOCATION_CONTACT_TYPE_ID));
		// orders to be grouped shouldn't have missing info substatus
		soToQuery.setSubStatusId(String.valueOf(OrderConstants.MISSING_INFORMATION_SUBSTATUS));
				
		return soToQuery;
	}
	
	public double priceOrderGroup(List<ServiceOrderSearchResultsVO> soSearchVOList)
		throws BusinessServiceException {
		
		logger.info("Entering OrderGroupBOImpl.priceOrderGroup()");
		if (soSearchVOList == null || soSearchVOList.isEmpty()) {
			throw new BusinessServiceException("The order group is empty, as there are no service orders in the group.");
		}
		
		ServiceOrderSearchResultsVO firstServiceOrder = soSearchVOList.get(0);
		int buyerId = firstServiceOrder.getBuyerID();
		List<Integer> mainCategoryIds = getUniqueMainCategoryIds(soSearchVOList);
		String groupId = firstServiceOrder.getParentGroupId();
		logger.info("BuyerId = "+buyerId+", mainCategoryId = "+mainCategoryIds+", groupId = "+groupId);
		
		// Let's first get trip charge for this buyer and this mainCategory
		double tripCharge = getTripCharge(buyerId, mainCategoryIds);
		
		// Let's calculate multiple order discount based on order count and trip charge
		int soCount = soSearchVOList.size();
		double multiOrderDiscount =  MoneyUtil.getRoundedMoney(( soCount - 1 ) * tripCharge / soCount );
		logger.info("MultiOrderDiscount = "+multiOrderDiscount);
		
		double groupedOrderTotalSpendLimit = 0.0d;
		for (ServiceOrderSearchResultsVO soSearchVO : soSearchVOList) {
			groupedOrderTotalSpendLimit = soSearchVO.getOriginalSpendLimitLabor() + groupedOrderTotalSpendLimit;
		}
		if(groupedOrderTotalSpendLimit < multiOrderDiscount*soCount){
			logger.info("No Trip charge will be applied as GroupId = "+groupId +"has total SpendLimit of " + groupedOrderTotalSpendLimit +
										"less than TripCharge to be applied " + multiOrderDiscount);
			multiOrderDiscount = MoneyUtil.DOUBLE_ZERO;
		}
		
		// Let's apply this discount on individual SO and calculate total spend limit for the order group
		double discountedGroupSpendLimitLabor = MoneyUtil.DOUBLE_ZERO;
		double groupSpendLimitParts = 0.0d;
		
		double tripChargeAdujustment = 0.0d;

		Collections.sort(soSearchVOList,new Comparator<ServiceOrderSearchResultsVO>() {
				public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
					return so1.getOriginalSpendLimitLabor().compareTo(so2.getOriginalSpendLimitLabor());
				}
			});
		
		for (ServiceOrderSearchResultsVO soSearchVO : soSearchVOList) {
			double soDiscountedSpendLimit = MoneyUtil.subtract(soSearchVO.getOriginalSpendLimitLabor(), multiOrderDiscount + tripChargeAdujustment);
			if(soDiscountedSpendLimit < 0.0){
				// if less than zero
				tripChargeAdujustment = -soDiscountedSpendLimit;
				soDiscountedSpendLimit = 0.0d;
				//tripChargeAdujustment = MoneyUtil.subtract(multiOrderDiscount + tripChargeAdujustment, soSearchVO.getOriginalSpendLimitLabor()) ;
				logger.info("GroupId = "+ groupId + "has some order with Spend limit less than trip charge to be applied");
			}else{
				tripChargeAdujustment = 0.0d;
			}
			String soId = soSearchVO.getSoId();
			updateIndividualSOSpendLimit(soId, soDiscountedSpendLimit);
			discountedGroupSpendLimitLabor = MoneyUtil.add(discountedGroupSpendLimitLabor, soDiscountedSpendLimit);
			groupSpendLimitParts = MoneyUtil.add(groupSpendLimitParts, soSearchVO.getSpendLimitParts());
		}
		
		// Update the discountedGroupSpendLimitLabor at group level
		// Final price should be same as discounted initially; later it is updated when buyer increases the spend limit at group level
		updateOrderGroupSpendLimit(groupId, null, null, discountedGroupSpendLimitLabor, groupSpendLimitParts, 
				discountedGroupSpendLimitLabor, groupSpendLimitParts, null);
		
		logger.info("Exiting OrderGroupBOImpl.priceOrderGroup()");
		return discountedGroupSpendLimitLabor;
	}
	
	private List<Integer> getUniqueMainCategoryIds(List<ServiceOrderSearchResultsVO> soSearchVOList) {
		Set<Integer> uniqueMainCategoryIds = new HashSet<Integer>();
		for (ServiceOrderSearchResultsVO so : soSearchVOList) {
			uniqueMainCategoryIds.add(so.getPrimarySkillCategoryId());
		}
		return new ArrayList<Integer>(uniqueMainCategoryIds);
	}
	
	private double getTripCharge(int buyerId, List<Integer> mainCategoryIds) throws BusinessServiceException {
		Double tripCharge = MoneyUtil.DOUBLE_ZERO;
		logger.info("Retrieving highest trip charge for given main category ids...");
		try {
			tripCharge = adminTripChargeDAO.getHighestByCategoryIds(buyerId, mainCategoryIds);
		} catch (DataServiceException dsEx) {
			logger.error("Unexpected error occured while retrieving trip charge for buyerId"+buyerId+" and categoryIds:"+mainCategoryIds, dsEx);
			throw new BusinessServiceException("Unexpected error occured while updating so_hdr for discounted spend limit", dsEx);
		}
		
		if (tripCharge != null) {
			logger.info("Trip charge = " + tripCharge.doubleValue());
		} else {
			logger.warn("Taking Trip charge as zero, since it's not defined in database for buyer id=[" + buyerId + "] and main categorys=[" + mainCategoryIds + "]");
		}
		return tripCharge;
	}
	
	private void updateIndividualSOSpendLimit(String soId, double soDiscountedSpendLimit) throws BusinessServiceException {
		logger.info("Updating SO = "+soId+", DiscountedSpendLimit = "+soDiscountedSpendLimit);
		try {
			// Reuse existing API, update spend limit for child order
			ServiceOrder so = new ServiceOrder();
			so.setSoId(soId);
			so.setSpendLimitLabor(soDiscountedSpendLimit);
			serviceOrderDAO.updateLimit(so);
			logger.info("Child SO spend limit - Update successful.");
			
			// New API to update SO_PRICE table
			ServiceOrderPriceVO soPriceVO = new ServiceOrderPriceVO();
			soPriceVO.setSoId(soId);
			soPriceVO.setDiscountedSpendLimitLabor(soDiscountedSpendLimit);
			serviceOrderDAO.updateSOPrice(soPriceVO);
			logger.info("Individual SO discounted spend limit - Update successful.");
		} catch (DataServiceException dataEx) {
			logger.error("Unexpected error occured while updating discounted spend limit on individual SO of the order group", dataEx);
			throw new BusinessServiceException("Unexpected error occured while updating so_hdr for discounted spend limit", dataEx);
		}
	}
	
	public void updateOrderGroupSpendLimit(String groupId, Double originalSpendLimitLabor, Double originalSpendLimitParts,
			Double finalSpendLimitLabor, Double finalSpendLimitParts, Double discountedSpendLimitLabor, 
			Double discountedSpendLimitParts, Double conditionalPrice) throws BusinessServiceException {
		try {
			logger.info("UpdateOrderGroupSpendLimit: finalPriceLabor = " + finalSpendLimitLabor);
			OrderGroupVO soGroupVO = new OrderGroupVO();
			soGroupVO.setGroupId(groupId);
			soGroupVO.setOriginalSpendLimitLabor(originalSpendLimitLabor);
			soGroupVO.setOriginalSpendLimitParts(originalSpendLimitParts);
			soGroupVO.setDiscountedSpendLimitLabor(discountedSpendLimitLabor);
			soGroupVO.setDiscountedSpendLimitParts(discountedSpendLimitParts);
			soGroupVO.setFinalSpendLimitLabor(finalSpendLimitLabor);
			soGroupVO.setFinalSpendLimitParts(finalSpendLimitParts);
			soGroupVO.setConditionalOfferPrice(conditionalPrice);
			int recordsUpdated = orderGroupDAO.updateOrderGroupPrice(soGroupVO);
			logger.info("Order Group total spend limit - Update successful. Records Updated = " + recordsUpdated);
		} catch (DataServiceException dataEx) {	
			logger.error("Unexpected error occured while updating discounted spend limit", dataEx);
			throw new BusinessServiceException("Unexpected error occured while updating so_group for total spend limit", dataEx);
		}
	}

	public void rePriceOrderGroup(String groupId, boolean applyTripCharge, boolean updateOriginalGroupPrice) throws BusinessServiceException {
		// Get the children orders of this group
		List<ServiceOrderSearchResultsVO> soSearchVOList = null;
		try {
			soSearchVOList = orderGroupDAO.getServiceOrdersForGroup(groupId);
		} catch (DataServiceException dsEx) {
			String errMsg = "DataServiceException in OrderGroupBOImpl.rePriceOrderGroup() while getting children orders of the group";
			logger.error(errMsg, dsEx);
			throw new BusinessServiceException(errMsg, dsEx);
		}
		int childrenCount = soSearchVOList.size();
		logger.info("Children count = " + childrenCount);
		
		if (childrenCount > 0) {
			
			if (updateOriginalGroupPrice) {
				// Update original price
				populateSOGroupPrice(groupId);
				logger.info("Group original price also updated successfully");
			}
			
			// Updates discounted price for individual child orders (so_hdr and so_price), updates group-level discounted and final price
			if (applyTripCharge) {
				// Complete re-pricing
				logger.info("Complete re-pricing; update spend limits in so_hdr, update discounted in so_price, update discounted and final in so_group_price");
				priceOrderGroup(soSearchVOList);
			} else {
				// Just update group's final price for labor and parts; dont update discounted price; i.e. do not apply trip charge to individual/group SO
				logger.info("Partial re-pricing; just update group's final spend limits");
				Double finalSpendLimitLabor = MoneyUtil.DOUBLE_ZERO;
				Double finalSpendLimitParts = MoneyUtil.DOUBLE_ZERO;
				for (ServiceOrderSearchResultsVO childSO : soSearchVOList) {
					finalSpendLimitLabor = MoneyUtil.add(finalSpendLimitLabor, childSO.getSpendLimitLabor());
					finalSpendLimitParts = MoneyUtil.add(finalSpendLimitParts, childSO.getSpendLimitParts());
				}
				updateOrderGroupSpendLimit(groupId, null, null, finalSpendLimitLabor, finalSpendLimitParts, null, null, null);
			}
			logger.info("Group re-priced successfully");
			
			// if child is one then in activate group
			if(childrenCount == 1) {
				ServiceOrderSearchResultsVO onlyChildSO = soSearchVOList.get(0);
				unGroupServiceOrder(onlyChildSO, groupId, childrenCount);
				/*
				ServiceOrderPriceVO soPrice = new ServiceOrderPriceVO();
				soPrice.setSoId(onlyChildSO.getSoId());
				try {
					orderGroupDAO.ungroupOrderGrp(soPrice, groupId);
				} catch (DataServiceException dsEx) {
					String errMsg = "DataServiceException in OrderGroupBOImpl.rePriceOrderGroup() while ungroup with only one child";
					logger.error(errMsg, dsEx);
					throw new BusinessServiceException(errMsg, dsEx);
				}
				inactivateGroupWithOneChild(groupId);
				*/
			}
			
		} else {			
			inactivateGroupWithOneChild(groupId);			
		}
	}
	
	
	public void unGroupServiceOrder(ServiceOrderSearchResultsVO onlyChildSO, String groupId, int groupSize) throws BusinessServiceException {		
		ServiceOrderPriceVO soPrice = new ServiceOrderPriceVO();
		soPrice.setSoId(onlyChildSO.getSoId());
		try {
			orderGroupDAO.ungroupOrderGrp(soPrice, groupId);
		} catch (DataServiceException dsEx) {
			String errMsg = "DataServiceException in OrderGroupBOImpl.rePriceOrderGroup() while ungroup with only one child";
			logger.error(errMsg, dsEx);
			throw new BusinessServiceException(errMsg, dsEx);
		}
		
		if (groupSize < 2) // 0 or 1
		  inactivateGroupWithOneChild(groupId);
	}
	
	/**
	 * make group Inactivate if there is less than 2 children in group
	 * @param groupId
	 * @throws BusinessServiceException
	 */
	private void inactivateGroupWithOneChild(String groupId)throws BusinessServiceException{
		
		// All children have been deleted/voided; In-activate the group
		Integer resourceId = null;
		int groupStatus = OrderConstants.INACTIVE_GROUP_STATUS;
		int providerResponse = 0;
		boolean validate = false;
		try {
			updateSoGroupStatus(groupId, resourceId, groupStatus, providerResponse, validate);
		} catch (DataServiceException dsEx) {
			//TODO Badness! A BO method should never throw data exception, for now living with it just by catching it and throwing BusinessServiceException
			String errMsg = "DataServiceException in OrderGroupBOImpl.rePriceOrderGroup() while updating group status";
			logger.error(errMsg, dsEx);
			throw new BusinessServiceException(errMsg, dsEx);
		}
	
		
		
	}
	
	public ProcessResponse increaseSpendLimit(String groupId,
			Double increasedGroupSpendLimitLabor, Double increasedGroupSpendLimitParts,
			String increasedSpendLimitComment, int buyerId, boolean validate,
			SecurityContext securityContext) throws BusinessServiceException {

		logger.info(new StringBuilder("INCREASE-SPEND-LIMIT-GROUPED-ORDER:-  GroupId=").append(groupId)
				.append(", Increased-SL-Labor=").append(increasedGroupSpendLimitLabor)
				.append(", Increased-SL-Parts=").append(increasedGroupSpendLimitParts)
				.append(", comments=").append(increasedSpendLimitComment)
				.append(", buyerId=").append(buyerId)
				.append(", validate=").append(validate)
				.append(", securityContext=").append(securityContext.getUsername()).toString());
		
		OrderGroupVO groupVO = getOrderGroupByGroupId(groupId);
		Double originalGroupSpendLimitLabor = groupVO.getOriginalSpendLimitLabor();
		Double originalGroupSpendLimitParts = groupVO.getOriginalSpendLimitParts();
		
		// Retrieve children service orders of given group and iterate all children to distribute increased spend limit
		String strMessage = null;
		List<ServiceOrderSearchResultsVO> soSearchResultsVOList = getServiceOrdersForGroup(groupId);
		
		// Iterate all child orders and calculate "proportional" child order level new spend limit for labor and parts
		int loopIndex = 0;
		int childrenSOCount = soSearchResultsVOList.size();
		double totalUpdatedSpendLimitLabor = 0.0d;
		double totalUpdatedSpendLimitParts = 0.0d;
		for (ServiceOrderSearchResultsVO searchResultsVO : soSearchResultsVOList) {
			
			loopIndex++;
			
			Double childToGroupLaborSLProportion = MoneyUtil.getProportion(searchResultsVO.getOriginalSpendLimitLabor(), originalGroupSpendLimitLabor);
			Double updatedSpendLimitLabor = MoneyUtil.getRoundedMoney(increasedGroupSpendLimitLabor * childToGroupLaborSLProportion);
			
			Double childToGroupPartsSLProportion = MoneyUtil.getProportion(searchResultsVO.getOriginalSpendLimitParts(), originalGroupSpendLimitParts);
			Double updatedSpendLimitParts = MoneyUtil.getRoundedMoney(increasedGroupSpendLimitParts * childToGroupPartsSLProportion);
			
			totalUpdatedSpendLimitLabor = MoneyUtil.add(totalUpdatedSpendLimitLabor, updatedSpendLimitLabor);
			totalUpdatedSpendLimitParts = MoneyUtil.add(totalUpdatedSpendLimitParts, updatedSpendLimitParts);
			
			searchResultsVO.setSpendLimitLabor(updatedSpendLimitLabor);
			searchResultsVO.setSpendLimitParts(updatedSpendLimitParts);
			
			// Pennies difference handling:
			// Tally total updated spend limit with group-level increased spend limit after updating last child SO spend limit
			// Adjust any pennies difference found; in the last order
			String soId = searchResultsVO.getSoId();
			if (loopIndex == childrenSOCount) {
				double diffSpendLimitLabor = MoneyUtil.subtract(increasedGroupSpendLimitLabor, totalUpdatedSpendLimitLabor);
				double diffSpendLimitParts = MoneyUtil.subtract(increasedGroupSpendLimitParts, totalUpdatedSpendLimitParts);
				if (diffSpendLimitLabor != 0.0) {
					updatedSpendLimitLabor = MoneyUtil.add(updatedSpendLimitLabor, diffSpendLimitLabor);
					logger.info("Adjusted labor price pennies difference " + diffSpendLimitLabor + " in last child Order: "+soId);
					searchResultsVO.setSpendLimitLabor(updatedSpendLimitLabor);
				}
				if (diffSpendLimitParts != 0.0) {
					updatedSpendLimitParts = MoneyUtil.add(updatedSpendLimitParts, diffSpendLimitParts);
					logger.info("Adjusted parts price pennies difference " + diffSpendLimitParts + " in last child Order: "+soId);
					searchResultsVO.setSpendLimitParts(updatedSpendLimitParts);
				}
			}
			logger.info("ORDER:"+soId+" NEW SLL:"+searchResultsVO.getSpendLimitLabor()+" NEW SLP:"+searchResultsVO.getSpendLimitParts());
		}
		
		// Update children order spend limit
		for (ServiceOrderSearchResultsVO searchResultsVO : soSearchResultsVOList) {
			// Call ServiceOrderBO API for increasing child so limit
			String soId = searchResultsVO.getSoId();
			try {
				logger.info("Going to update Order:"+soId+" LaborSL:" + searchResultsVO.getSpendLimitLabor() + " PartsSL:"+searchResultsVO.getSpendLimitParts());
				ProcessResponse pr = serviceOrderBO.updateSOSpendLimit(soId, searchResultsVO.getSpendLimitLabor(), searchResultsVO.getSpendLimitParts(),
								increasedSpendLimitComment, buyerId, true, false,securityContext);
		    	strMessage = pr.getMessages().get(0);
		    	if (pr.isError()) {
					strMessage = "Increasing spend limit failed for order: " + soId + " due to error: " + strMessage;
					logger.error(strMessage);
					throw new BusinessServiceException(strMessage);
				}
			} catch (com.newco.marketplace.exception.core.BusinessServiceException coreBSEx) {
				//TODO Badness! Obsolete core.BusinessServiceException being used, for now living with it just by catching/throwing correct BusinessServiceException
		    	strMessage = "Unexpected error in increasing spend limit for child order:" + soId + " due to error: " + coreBSEx.getMessage();
				logger.error(strMessage, coreBSEx);
				throw new BusinessServiceException(strMessage, coreBSEx);
			}
		}
		
		// Update group order spend limits
		updateOrderGroupSpendLimit(groupId, null, null, increasedGroupSpendLimitLabor, increasedGroupSpendLimitParts, null, null, null);
		
		// Generate success process response
		strMessage = "Spend limit successfully increased for group:" + groupId;
		ProcessResponse returnResponse = new ProcessResponse();
		returnResponse.setCode(ServiceConstants.VALID_RC);
		returnResponse.setMessage(strMessage);
		logger.info(strMessage);
		return returnResponse;
	}
	
	public ProcessResponse rejectGroupedOrder(int resourceId, String groupId,
			int reasonId, int responseId, String modifiedBy, String reasonDesc,
			SecurityContext securityContext) throws BusinessServiceException {
		
		logger.info(new StringBuilder("REJECT-GROUPED-ORDER:-  ResourceId=").append(resourceId)
				.append(", groupId=").append(groupId)
				.append(", reasonId=").append(reasonId)
				.append(", responseId=").append(responseId)
				.append(", modifiedBy=").append(modifiedBy)
				.append(", reasonDesc=").append(reasonDesc)
				.append(", securityContext=").append(securityContext.getUsername()).toString());

		// Retrieve children service orders of given group and iterate all children to reject each child order of this group
		String strMessage = null;
		List<ServiceOrderSearchResultsVO> soSearchResultsVOList = getServiceOrdersForGroup(groupId);

		for (ServiceOrderSearchResultsVO searchResultsVO : soSearchResultsVOList) {
			// Call ServiceOrderBO API for rejecting each child order
			String soId = searchResultsVO.getSoId();
			try {
				ProcessResponse pr = serviceOrderBO.rejectServiceOrder(resourceId, soId, reasonId, responseId, modifiedBy, reasonDesc, securityContext);
				strMessage = pr.getMessages().get(0);
				if(ServiceConstants.USER_ERROR_RC.equals(pr.getCode())) {
					strMessage = "Order: " + soId + " not rejected due to error: " + strMessage;
					logger.error(strMessage);
					throw new BusinessServiceException(strMessage);
				}
			} catch (com.newco.marketplace.exception.core.BusinessServiceException coreBSEx) {
				//TODO Badness! Obsolete core.BusinessServiceException being used, for now living with it just by catching/throwing correct BusinessServiceException
				strMessage = "Unexpected error in rejecting child order:"+soId + " due to error: " + coreBSEx.getMessage();
				logger.error(strMessage, coreBSEx);
				throw new BusinessServiceException(strMessage, coreBSEx);
			}
		}
		
		// If all children order rejected successfully, update group level provider response
		ResponseSoVO soGroupResponseVO = new ResponseSoVO();
		soGroupResponseVO.setResponseId(responseId);
		soGroupResponseVO.setReasonId(reasonId);
		soGroupResponseVO.setModifiedBy(modifiedBy);
		soGroupResponseVO.setSoId(groupId);
		soGroupResponseVO.setResourceId(resourceId);
		try {
			int updateCount = orderGroupDAO.updateProviderResponse(soGroupResponseVO);
			if (updateCount == 0) {
				strMessage = "Unexpected error in rejecting grouped order:" + groupId;
				logger.error(strMessage);
				throw new BusinessServiceException(strMessage);
			}
		} catch (DataServiceException dataEx) {
			strMessage = "Unknwon exception while rejecting group order with groupId:" + groupId + " due to data error: " + dataEx.getMessage();
			logger.error(strMessage, dataEx);
			throw new BusinessServiceException(strMessage, dataEx);
		}
		
		// Generate success process response
		strMessage = "Group:" + groupId + " successfully rejected.";
		ProcessResponse returnResponse = new ProcessResponse();
		returnResponse.setCode(ServiceConstants.VALID_RC);
		returnResponse.setMessage(strMessage);
		logger.info(strMessage);
		return returnResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO#getProportionateLaborAndParts(java.lang.String, java.lang.Double, java.lang.Double)
	 */
	public Hashtable<String, Double> getProportionateLaborAndParts(String groupId, Double newGroupSpendLimitLabor, Double newGroupSpendLimitParts)
			throws DataServiceException {
		
		// Get current spend limits for this group and calculate the proportion ratio for labor/parts spend limit to be used for dividing among child service orders
		OrderGroupVO orderGroupVO = orderGroupDAO.getOrderGroupByGroupId(groupId);
		Double currentSpendLimitLabor = orderGroupVO.getFinalSpendLimitLabor();
		Double currentSpendLimitParts = orderGroupVO.getFinalSpendLimitParts();
		
		Double spendLimitProportionLabor = 1.0;
		Double spendLimitProportionParts = 1.0;
		
		if (currentSpendLimitLabor != null && currentSpendLimitLabor > 0.0) {
			spendLimitProportionLabor = 1 + (MoneyUtil.getProportion((newGroupSpendLimitLabor-currentSpendLimitLabor),currentSpendLimitLabor));
		}
		if (currentSpendLimitParts != null && currentSpendLimitParts > 0.0) {
			spendLimitProportionParts = 1 + (MoneyUtil.getProportion((newGroupSpendLimitParts-currentSpendLimitParts),currentSpendLimitParts));
		}
		logger.info("Proportions:: LaborSL: " + spendLimitProportionLabor + " PartsSL: " + spendLimitProportionParts);
		
		Hashtable<String, Double> proportionateLimits = new Hashtable<String, Double>();
		proportionateLimits.put(OrderConstants.PROPORTIONATE_LABOR_SPEND_LIMIT, spendLimitProportionLabor);
		proportionateLimits.put(OrderConstants.PROPORTIONATE_PARTS_SPEND_LIMIT, spendLimitProportionParts);
		
		return proportionateLimits;
	}
	
	private ProcessResponse addErrorToResponse(ProcessResponse returnResponse, String strMessage, String soId) {
		returnResponse.setCode(ServiceConstants.USER_ERROR_RC);
		returnResponse.setSubCode(ServiceConstants.USER_ERROR_RC);
		List<String> messages = returnResponse.getMessages();
		if (messages == null) {
			messages = new ArrayList<String>();
			returnResponse.setMessages(messages);
		}
		messages.add(strMessage);
		returnResponse.setObj(soId);
		logger.error(strMessage);
		return returnResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO#getServiceOrdersForGroup(java.lang.String)
	 */
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForGroup(
			String groupId) throws BusinessServiceException {
		List<ServiceOrderSearchResultsVO> serviceOrders= null;
		try{
			serviceOrders = orderGroupDAO.getServiceOrdersForGroup(groupId);
		}catch(Exception e){
			throw new BusinessServiceException("Exception occured while getting service orders for gruop-->SearchOrderGroupBOImpl", e);
		}
		if (serviceOrders == null || serviceOrders.size()==0) {
			throw new BusinessServiceException("There is no order in the group " + groupId);
		}
		return serviceOrders;
	}
	
	
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForOrigGroup(
			String origGroupId) throws BusinessServiceException {
		List<ServiceOrderSearchResultsVO> serviceOrders= null;
		try{
			serviceOrders = orderGroupDAO.getServiceOrdersForOrigGroup(origGroupId);
		}catch(Exception e){
			throw new BusinessServiceException("Exception occured while getting service orders for gruop-->SearchOrderGroupBOImpl", e);
		}
		if (serviceOrders == null || serviceOrders.size()==0) {
			throw new BusinessServiceException("There is no order in the group " + origGroupId);
		}
		return serviceOrders;
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO#getServiceOrdersForGroup(java.lang.String)
	 */
	public List<ServiceOrderSearchResultsVO> getServiceOrdersVOForGroup(
			String groupId) throws BusinessServiceException {
		List<ServiceOrderSearchResultsVO> serviceOrders= null;
		try{
			serviceOrders = orderGroupDAO.getSOSearchResultsVOsForGroup(groupId);
		}catch(Exception e){
			throw new BusinessServiceException("Exception occured while getting service orders for gruop-->SearchOrderGroupBOImpl", e);
		}
		if (serviceOrders == null || serviceOrders.size()==0) {
			throw new BusinessServiceException("There is no order in the group " + groupId);
		}
		return serviceOrders;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO#updateGroupParentId(java.lang.String, java.lang.String)
	 */
	public void updateGroupParentId(String oldGroupId, String newGroupId)
			throws BusinessServiceException {
		try{
			orderGroupDAO.updateGroupParentId(oldGroupId, newGroupId);
		}catch(Exception e){
			throw new BusinessServiceException("Exception occured while updating group parent ID", e);
		}

	}
	
	public List<OrderGroupVO> getOrderGroupsByAddress(String buyerId, String address,  Integer status, Integer substatus) throws BusinessServiceException
	{
		try
		{
			return orderGroupDAO.getOrderGroupsByAddress(buyerId, address, status, substatus);
		}
		catch (DataServiceException e)
		{
			e.printStackTrace();
			return null;
		}		
	}
	
	public List<OrderGroupVO> getOrderGroupsByCustomerName(String buyerId, String name, Integer status, Integer substatus) throws BusinessServiceException
	{
		try
		{
			return orderGroupDAO.getOrderGroupsByAddress(buyerId, name, status, substatus);
		}
		catch (DataServiceException e)
		{
			e.printStackTrace();
			return null;
		}		
	}
	
	public List<OrderGroupVO> getOrderGroupsByPhone(String buyerId, String phone, Integer status, Integer substatus) throws BusinessServiceException
	{
		try
		{
			return orderGroupDAO.getOrderGroupsByAddress(buyerId, phone, status, substatus);
		}
		catch (DataServiceException e)
		{
			e.printStackTrace();
			return null;
		}		
	}
	
	public List<OrderGroupVO> getOrderGroupsBySOId(String buyerId, String soId, Integer status, Integer substatus) throws BusinessServiceException
	{
		try
		{
			return orderGroupDAO.getOrderGroupsByAddress(buyerId, soId, status, substatus);
		}
		catch (DataServiceException e)
		{
			e.printStackTrace();
			return null;
		}		
	}
	
	public List<OrderGroupVO> getOrderGroupsByZip(String buyerId, String zip, Integer status, Integer substatus) throws BusinessServiceException
	{
		try
		{
			return orderGroupDAO.getOrderGroupsByZip(buyerId, zip, status, substatus);
		}
		catch (DataServiceException e)
		{
			e.printStackTrace();
			return null;
		}		
	}
	
	public List<OrderGroupVO> getOrderGroups(String buyerId) throws BusinessServiceException {
		List<OrderGroupVO> orderGroups = null;
		try {
			orderGroups = orderGroupDAO.getOrderGroups(buyerId);
		} catch (DataServiceException dsEx) {
			String strMessage = "Unexpected error occured while retrieving order groups for buyerId:"+buyerId;
			logger.error(strMessage, dsEx);
			throw new BusinessServiceException(strMessage, dsEx);
		}
		return orderGroups;
	}
	
	public OrderGroupVO getOrderGroupByGroupId(String groupId) throws BusinessServiceException{
		
		OrderGroupVO orderGroupVO = new OrderGroupVO();
		try{
			orderGroupVO = orderGroupDAO.getOrderGroupByGroupId(groupId);
			
		}catch(DataServiceException e){
			throw new BusinessServiceException("error occured while getting Grouped Order By Grp Id : " + groupId);
		}
		
		return orderGroupVO;
	}
	
	//SL-21465
	public EstimateVO getServiceOrderEstimationDetails(String soID,int vendorId,int ResourceId) throws BusinessServiceException{
		
		EstimateVO estimateVO = new EstimateVO();
		try{
			estimateVO = orderGroupDAO.getServiceOrderEstimationDetails(soID,vendorId,ResourceId);
			
		}catch(DataServiceException e){
			throw new BusinessServiceException("error occured while getting Grouped Order By Grp Id : " + soID);
		}
		
		return estimateVO;
	}
	
   public EstimateVO getServiceOrderEstimationDetails(String soID) throws BusinessServiceException{
		
		EstimateVO estimateVO = new EstimateVO();
		try{
			estimateVO = orderGroupDAO.getServiceOrderEstimationDetails(soID);
			
		}catch(DataServiceException e){
			throw new BusinessServiceException("error occured while getting Grouped Order By Grp Id : " + soID);
		}
		
		return estimateVO;
	}
	
   public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForVendor(String soID,int vendorId,int ResourceId) throws BusinessServiceException{
		
	   List<EstimateHistoryVO> estimateHistoryVO = null;
		try{
			estimateHistoryVO = orderGroupDAO.getServiceOrderEstimationHistoryDetailsForVendor(soID,vendorId,ResourceId);
			
		}catch(DataServiceException e){
			throw new BusinessServiceException("error occured while getting Grouped Order By Grp Id : " + soID);
		}
		
		return estimateHistoryVO;
	}
	
  public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForBuyer(String soID) throws BusinessServiceException{
		
	  List<EstimateHistoryVO> estimateHistoryVO = null;
		try{
			estimateHistoryVO = orderGroupDAO.getServiceOrderEstimationHistoryDetailsForBuyer(soID);
			
		}catch(DataServiceException e){
			throw new BusinessServiceException("error occured while getting Grouped Order By Grp Id : " + soID);
		}
		
		return estimateHistoryVO;
	}
   
	/**
	 * Determines if the service order, corresponding to the to provided soID,
	 * is currently in Edit Mode
	 */
	private boolean isGroupInEditMode(String groupId) throws BusinessServiceException {
		OrderGroupVO orderGroup = null;

		try {
			orderGroup = this.getOrderGroupByGroupId(groupId);

		}// end try
		catch (BusinessServiceException bse) {
			logger.debug("Exception thrown while fetching group order("
					+ groupId + ").");
			throw bse;

		}// end catch

		if (orderGroup == null) {
			throw new BusinessServiceException(
					"Unable to locate group order(" + groupId + ").");

		}// end if
		else {
			if (orderGroup.getLockEditInd() != null
					&& orderGroup.getLockEditInd().compareTo(
							OrderConstants.SO_EDIT_MODE_FLAG) == 0) {
				return true;

			}// end if
			else {
				return false;

			}// end else

		}// end else

	}// end method isSOInEditMode
	
	
	public ProcessResponse processAcceptGroupOrder(String groupId,Integer resourceId, Integer companyId, Integer termAndCond,
			boolean validate, SecurityContext securityContext){
		

		ProcessResponse processResp = new ProcessResponse();
		OrderGroupVO orderGrpObj = new OrderGroupVO();
		List<String> errorMessages = new ArrayList<String>();
		boolean acceptable = false;

		try {
			if (this.isGroupInEditMode(groupId)) {
				processResp.setCode(ServiceConstants.USER_ERROR_RC);
				processResp
						.setMessage("Unable to complete action.  Grouped Order is currently being edited.");

			} else {
				orderGrpObj = this.getOrderGroupByGroupId(groupId);
				if (orderGrpObj.getWfStateId().equals(OrderConstants.ROUTED_STATUS)) {
					acceptable = true;
				} else if (orderGrpObj.getWfStateId().equals(
						OrderConstants.ACCEPTED_STATUS)){
					processResp.setCode(ServiceConstants.USER_ERROR_RC);
					processResp.setMessage(OrderConstants.ORDER_ACCEPTED_BY_ANOTHER_PROVIDER);
					acceptable = false;
				}  else if (orderGrpObj.getWfStateId().equals(OrderConstants.VOIDED_STATUS) || 
						orderGrpObj.getWfStateId().equals(OrderConstants.CANCELLED_STATUS) ) {
					processResp.setCode(ServiceConstants.USER_ERROR_RC);
					processResp.setMessage(OrderConstants.ORDER_IN_CANCELLED_STATUS);
					acceptable = false;
				}  
				List<RoutedProvider> routedAL = orderGroupDAO.getRoutedProvidersResponse(groupId);
				boolean isRoutedResource = this.isRoutedResource(resourceId,
						routedAL);
				// logger.info("isRoutedResource: " + isRoutedResource);
				if (!isRoutedResource) {
					logger.debug("ResourceID("
									+ resourceId
									+ ") does not match Routed resourceID for Group Order {"
									+ groupId + "}.");
					processResp.setCode(ServiceConstants.USER_ERROR_RC);
					errorMessages
							.add(new String(
									"ResourceID("
											+ resourceId
											+ ") does not match Routerd resourceID for Group Order {"
											+ groupId + "}."));
					processResp.setMessages(errorMessages);
				} else if (isRoutedResource && acceptable) {
					// update so_group status and so_group_routed_providers
					updateSoGroupStatus(groupId, resourceId, OrderConstants.ACCEPTED_STATUS, OrderConstants.ACCEPTED, validate);
					
					processResp.setCode(ServiceConstants.VALID_RC);
					errorMessages.add(ServiceConstants.VALID_MSG);
					processResp.setMessages(errorMessages);
				}
				if(processResp.isSuccess()){
					//Splits the grouped order
					splitOrderGroup(groupId);
					processResp.setCode(ServiceConstants.VALID_RC);
					errorMessages.add(ServiceConstants.VALID_MSG);
					processResp.setMessages(errorMessages);
					
				}
				
				
			}
			
			
		} catch (DataServiceException dse) {
			logger.debug("Exception thrown querying SO", dse);
			processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
			errorMessages.add(new String("Datastore error"));
			processResp.setMessages(errorMessages);
		} catch (BusinessServiceException bse) {
			logger.debug("Exception thrown accepting SO", bse);
			processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
			errorMessages.add(new String("BusinessService error"));
			processResp.setMessages(errorMessages);
		}

		return processResp;
		
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO#processCreateConditionalOfferForGroup(java.lang.String, java.lang.Integer, java.lang.Integer, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.Double, com.newco.marketplace.auth.SecurityContext)
	 */
	public ProcessResponse processCreateConditionalOfferForGroup(
			String groupId, Integer resourceId, Integer vendorOrBuyerId,
			Timestamp conditionalDate1, Timestamp conditionalDate2,
			String conditionalStartTime, String conditionalEndTime,
			Timestamp conditionalExpirationDate, Double conditionalPrice,List<Integer> selectedCounterOfferReasonsList,
			SecurityContext securityContext) throws BusinessServiceException {
		
		ProcessResponse processResp = new ProcessResponse();
		Hashtable<String, Double> proportionateLimits = null;
		
		
		if (this.isGroupInEditMode(groupId)) {
			return addErrorToResponse(processResp, "Unable to complete action.  Grouped Order is currently being edited.", groupId);
		}
		else 
		{
			try {
				List<ServiceOrderSearchResultsVO> serviceOrders = getServiceOrdersForGroup(groupId);
				String serviceLocationTimeZone =  serviceOrders.get(0).getServiceLocationTimezone();
				processResp = verifyConditionalOffer(groupId, resourceId, conditionalDate1, conditionalDate2, conditionalStartTime, 
						conditionalEndTime, conditionalExpirationDate, serviceLocationTimeZone);
				if (processResp.isError()) {
					logger.error(processResp.getMessages().get(0));
					throw new BusinessServiceException(processResp.getMessages().get(0));
				}
				if (conditionalPrice != null) {
					proportionateLimits = getProportionateLimits(groupId, conditionalPrice);
				}
				//Create conditional offer for each service order 
				for (ServiceOrderSearchResultsVO serviceOrder : serviceOrders) {
					Double individualCondPrice = getIndividualCondPrice(
							conditionalPrice, proportionateLimits, serviceOrder);
					processResp = serviceOrderBO.processCreateConditionalOffer(serviceOrder.getSoId(), resourceId, vendorOrBuyerId, 
							conditionalDate1, conditionalDate2, conditionalStartTime, conditionalEndTime, 
							conditionalExpirationDate, individualCondPrice ,selectedCounterOfferReasonsList, securityContext,null);
					if (processResp.isError()) {
						logger.error(processResp.getMessages().get(0));
						throw new BusinessServiceException(processResp.getMessages().get(0));
					}
				}
				//Create conditional offer for the group
				RoutedProvider conditionalOffer = serviceOrderBO.createConditionalOffer(groupId, resourceId, vendorOrBuyerId, conditionalDate1, 
						conditionalDate2, conditionalStartTime, conditionalEndTime, conditionalExpirationDate, conditionalPrice, 
						serviceLocationTimeZone);
				orderGroupDAO.updateGroupRoutedResourcesForConditionalOffer(conditionalOffer);
			} catch (DataServiceException e) {
				logger.error(e.getMessage());
				throw new BusinessServiceException(OrderConstants.SO_CONDITIONAL_OFFER_UPDATE_NOT_POSSIBLE);
			}
						
			processResp.setCode(ServiceConstants.VALID_RC);
			processResp.setObj(OrderConstants.SO_CONDITIONAL_OFFER_SUCCESS);
			return processResp;
		}
	}
	
	/**
	 * Gets the the proportionate limits for labor and parts from new group price 
	 * 
	 * @param groupId
	 * @param newGroupPrice
	 * @return
	 * @throws BusinessServiceException
	 * @throws DataServiceException
	 */
	private Hashtable<String, Double> getProportionateLimits(String groupId,
			Double newGroupPrice) throws BusinessServiceException,
			DataServiceException {
		double groupSpendLimitParts = getGroupFinalSpendLimitParts(groupId);
		Hashtable<String, Double> proportionateLimits = getProportionateLaborAndParts(groupId, newGroupPrice - groupSpendLimitParts, groupSpendLimitParts);
		return proportionateLimits;
	}
	/**
	 * @param groupId
	 * @return
	 * @throws BusinessServiceException
	 */
	private double getGroupFinalSpendLimitParts(String groupId)
			throws BusinessServiceException {
		OrderGroupVO orderGrpObj = new OrderGroupVO();
		orderGrpObj = this.getOrderGroupByGroupId(groupId);
		double groupSpendLimitParts = 0.0;
		if (orderGrpObj.getFinalSpendLimitParts() != null) {
			groupSpendLimitParts = orderGrpObj.getFinalSpendLimitParts();
		}
		return groupSpendLimitParts;
	}
	

	
	private ProcessResponse verifyConditionalOffer(String groupId, Integer resourceId, Timestamp conditionalDate1,
			Timestamp conditionalDate2,	String conditionalStartTime, String conditionalEndTime, Timestamp conditionalExpirationDate,
			String serviceLocationTimeZone) throws BusinessServiceException, DataServiceException {
		TimeZone tz = TimeZone.getTimeZone(OrderConstants.DEFAULT_ZONE);
		Calendar calendar = Calendar.getInstance(tz);
		OrderGroupVO orderGrpObj = new OrderGroupVO();
		orderGrpObj = this.getOrderGroupByGroupId(groupId);
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_RC);
		if (!orderGrpObj.getWfStateId().equals(OrderConstants.ROUTED_STATUS)) {
			if (orderGrpObj.getWfStateId().equals(OrderConstants.ACCEPTED_STATUS)) {
				return addErrorToResponse(processResp, OrderConstants.SO_CONDITIONAL_OFFER_WFSTATE_ERROR, groupId);
			} 
			else {
				return addErrorToResponse(processResp, OrderConstants.SO_CONDITIONAL_OFFER_CAN_NOT_COMPLETE, groupId);
			}
		} 
		
		Integer existingConditionalOffer = orderGroupDAO.checkConditionalOfferResp(groupId, resourceId);
		if (existingConditionalOffer != null) {
			return addErrorToResponse(processResp, OrderConstants.SO_CONDITIONAL_OFFER_ALREADY_EXISTS, groupId);
		}
		Long currentTime = calendar.getTimeInMillis();
		Long conditionalTime = null;
		Long conditionalSecondTime = null;
		Long expirationTime = null;
		if(conditionalExpirationDate != null){
			expirationTime = conditionalExpirationDate.getTime();
		}
		if (conditionalDate1 != null && conditionalStartTime != null
				&& expirationTime != null) {
			conditionalTime = TimeUtils.combineDateAndTime(conditionalDate1, conditionalStartTime, serviceLocationTimeZone).getTime();

			// Expiration DateTime should be greater than current datetime 
			if (currentTime.longValue() >= expirationTime.longValue()) {
				return addErrorToResponse(processResp, OrderConstants.SO_CONDITIONAL_EXPIRATION_DATE_ERROR, groupId);
			}
			
			// The expiration datetime should not exceed the conditional
			// offer start date
			if (expirationTime.longValue() >= conditionalTime.longValue()) {
				return addErrorToResponse(processResp, OrderConstants.SO_CONDITIONAL_EXPIRATION_DATE_CAN_NOT_BE_AFTER_START_DATE , groupId);
			}
			
			if (conditionalDate2 != null && conditionalEndTime != null) {
				conditionalSecondTime = TimeUtils.combineDateAndTime(conditionalDate2, conditionalEndTime, serviceLocationTimeZone).getTime();//conditionalDate2.getTime();

				if (conditionalTime.longValue() >= conditionalSecondTime.longValue()) {
					return addErrorToResponse(processResp, OrderConstants.SO_CONDITIONAL_OFFER_END_DATE_CAN_NOT_BE_AFTER_START_DATE, groupId);
				}					
			}
		}
		return processResp;
		
	}
	public  void updateSoGroupStatus(String groupId, Integer resourceId,
			int groupStatus, int providerResponse, boolean validate) throws DataServiceException{
		orderGroupDAO.updateSOGroupStatus(groupId, String.valueOf(groupStatus));
		// update so_group_routed_providers response
		if (validate == true) {
			ResponseSoVO soGroupResponseVO = new ResponseSoVO();
			soGroupResponseVO.setResponseId(providerResponse);
			soGroupResponseVO.setResourceId(resourceId);
			soGroupResponseVO.setSoId(groupId);
			orderGroupDAO.updateProviderResponse(soGroupResponseVO); 
		}
		
	}
	public  boolean isRoutedResource(Integer resourceId,
			List<RoutedProvider> routedAL) {
		boolean isRoutedResource = false;
		for(RoutedProvider routedPro : routedAL){
			if(routedPro.getResourceId().intValue() == resourceId.intValue()){
				isRoutedResource = true;
				return isRoutedResource;
			}
		}
	
		return isRoutedResource;
	}
	
	public void sendallProviderResponseExceptAccepted(String groupId,SecurityContext securityContext){
		// place holder --AOP call
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO#processUngroupOrderGrp(java.lang.String)
	 */
	public ProcessResponse processUngroupOrderGrp(String groupId) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		ArrayList<String> errorMessages = new ArrayList<String>();
		List<ServiceOrderSearchResultsVO>  groupOrdersList = this.getServiceOrdersForGroup(groupId);
		
		try{
			for(ServiceOrderSearchResultsVO serviceOrder : groupOrdersList){
				ServiceOrderPriceVO soPrice = serviceOrderDAO.getSoPrice(serviceOrder.getSoId());
				ServiceOrder soObj = serviceOrderDAO.getServiceOrder(serviceOrder.getSoId());
				ServiceOrderPriceVO soPriceupdate = new ServiceOrderPriceVO();
				soPriceupdate.setSoId(serviceOrder.getSoId());
				soPriceupdate.setDiscountedSpendLimitLabor(soPrice.getOrigSpendLimitLabor());
				soPriceupdate.setDiscountedSpendLimitParts(soPrice.getOrigSpendLimitParts());
				soPriceupdate.setOrigSpendLimitLabor(soPrice.getOrigSpendLimitLabor());
				soPriceupdate.setOrigSpendLimitParts(soPrice.getOrigSpendLimitParts());
				// update price ,group id , original_group id in so_hdr
				orderGroupDAO.ungroupOrderGrp(soPrice, groupId);
				serviceOrderDAO.updateSOPrice(soPriceupdate);
				int wfState = soObj.getWfStateId().intValue();
				// returns error if the work flow state has error
				if (wfState < 0)
					logger.info("Incorrect WFStateId for service Order" + soObj.getSoId());
				if(wfState != OrderConstants.DRAFT_STATUS && wfState != OrderConstants.EXPIRED_STATUS) {
					Double amt = (soPrice.getOrigSpendLimitLabor() + soPrice.getOrigSpendLimitParts()) - 
								 (soPrice.getDiscountedSpendLimitLabor()+soPrice.getDiscountedSpendLimitParts());
					if(amt > 0.0)
					increaseSpendLimitForSearsBuyer(serviceOrder.getBuyerID(),amt,soObj);
				}
			}
			// update so_group status
			orderGroupDAO.updateSOGroupStatus(groupId, String.valueOf(OrderConstants.INACTIVE_GROUP_STATUS));
			processResp.setCode(ServiceConstants.VALID_RC);
			errorMessages.add(ServiceConstants.VALID_MSG);
		} catch (Exception ex) {
			String message = "Error thrown while ungrouping Order Grp --> Grp Id-->" + groupId;
			logger.error(message, ex);
			throw new BusinessServiceException(message, ex);
		}
		
		return processResp;
	}
	
	public void increaseSpendLimitForSearsBuyer(Integer buyerId,Double amt,ServiceOrder soObj)  throws BusinessServiceException
	{
		try
		{
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			//Get Buyer attributes to move their funds into their escrow account on their behalf!
			Buyer buyer = getServiceOrderDao().getBuyerAttributes(buyerId);
			if (acct != null && acct.isActive_ind()){ //They are Auto_ach enabled
				MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(soObj, buyerId);
				marketVO.setUserName(buyer.getUserName());
				marketVO.setBuyerID(buyerId);
				marketVO.setAutoACHInd("true");
				marketVO.setAccountId(acct.getAccount_id());
				if (amt > 0.0){
					//Deposit funds necessary
					//financeManagerBO.depositfunds(buyerId, OrderConstants.BUYER_ROLE, fundVO, acct.getAccount_id(), buyer.getUserName(), "true");
					transBo.increaseSpendLimit(marketVO, amt, soObj.getSoId(),amt);
				}
			}
		}
		catch (Exception ex) {
			String message = "Error thrown while increaseSpendLimitForSearsBuyer for Group";
			logger.error(message, ex);
			throw new BusinessServiceException(message, ex);
		}
	}
	
	public void decreaseSpendLimitForSearsBuyer(Integer buyerId,Double amt,ServiceOrder soObj)  throws BusinessServiceException
	{
		try
		{
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			//Get Buyer attributes to move their funds into their escrow account on their behalf!
			Buyer buyer = getServiceOrderDao().getBuyerAttributes(buyerId);
			if (acct != null && acct.isActive_ind()){ //They are Auto_ach enabled
				MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(soObj, buyerId);
				marketVO.setUserName(buyer.getUserName());
				marketVO.setBuyerID(buyerId);
				marketVO.setAutoACHInd("true");
				marketVO.setAccountId(acct.getAccount_id());
				if (amt < 0.0){
					//Deposit funds necessary
					//financeManagerBO.depositfunds(buyerId, OrderConstants.BUYER_ROLE, fundVO, acct.getAccount_id(), buyer.getUserName(), "true");
					transBo.decreaseSpendLimit(marketVO, Math.abs(amt),Math.abs(amt));
				}
			}
		}
		catch (Exception ex) {
			String message = "Error thrown while increaseSpendLimitForSearsBuyer for Group";
			logger.error(message, ex);
			throw new BusinessServiceException(message, ex);
		}
	}
	
	private MarketPlaceTransactionVO getMarketPlaceTransactionVO(
			ServiceOrder serviceOrder, Integer buyerId) {
		MarketPlaceTransactionVO marketVO = new MarketPlaceTransactionVO();
		marketVO.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_POST_SO);
		marketVO.setBuyerID(buyerId);

		marketVO.setUserTypeID(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
		marketVO.setLedgerEntryRuleId(LedgerConstants.RULE_ID_APPLY_POSTING_FEE_VIRTUAL);
		marketVO.setServiceOrder(serviceOrder);
		return marketVO;

	}
	
	/*
	 * This method is called locally when a grouped order is accepted or conditional offer is accepted.
	 */
	protected void splitOrderGroup(String groupId)
			throws BusinessServiceException {
		try {
			List<ServiceOrderSearchResultsVO>  groupOrdersList = this.getServiceOrdersForGroup(groupId);
			for (ServiceOrderSearchResultsVO serviceOrder : groupOrdersList) {
				logger.info("Spliting service order " + serviceOrder.getSoId() + " from group " + groupId);
				ServiceOrderPriceVO soPrice = new ServiceOrderPriceVO();
				soPrice.setSoId(serviceOrder.getSoId());
				// update so_group status
				orderGroupDAO.updateSOGroupStatus(groupId, String.valueOf(OrderConstants.INACTIVE_GROUP_STATUS));
				// update group id , original_group id in so_hdr
				orderGroupDAO.ungroupOrderGrp(soPrice, groupId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
	}
	

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO#acceptConditionalOfferForGroup(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Date, java.util.Date, java.lang.String, java.lang.String, java.lang.Double, java.lang.Integer, com.newco.marketplace.auth.SecurityContext)
	 */
	public ProcessResponse acceptConditionalOfferForGroup(String groupId,
			Integer resourceId, Integer vendorId, Integer resReasonId,
			Date startDate, Date endDate, String startTime, String endTime,
			Double conditionalPrice, Integer buyerId, SecurityContext securityContext) throws BusinessServiceException{
		ProcessResponse processResp = new ProcessResponse();
		processResp.setCode(ServiceConstants.VALID_RC);
		Hashtable<String, Double> proportionateLimits = null;
				
		try {
			if (this.isGroupInEditMode(groupId)) {
				throw new BusinessServiceException("Unable to complete action.  Grouped Order is currently being edited.");
			}
			else 
			{
				List<ServiceOrderSearchResultsVO> serviceOrders = getServiceOrdersForGroup(groupId);
				OrderGroupVO orderGrpObj = new OrderGroupVO();
				orderGrpObj = this.getOrderGroupByGroupId(groupId);
				if (conditionalPrice != null) {
					proportionateLimits = getProportionateLimits(groupId, conditionalPrice);
				}
				Integer providerRespId = orderGroupDAO.checkConditionalOfferResp(groupId, resourceId);
				if (orderGrpObj.getWfStateId().equals(OrderConstants.ROUTED_STATUS)) {
					if (OrderConstants.CONDITIONAL_OFFER.equals(providerRespId)) {
						if (resReasonId.equals(OrderConstants.SPEND_LIMIT) || 
								resReasonId.equals(OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT)){
							//Verify if the buyer can spend the conditional offer for the group
							verifyBuyerFunding(orderGrpObj, serviceOrders, buyerId, conditionalPrice, securityContext);
						}
						
						//Create conditional offer for each service order 
						for (ServiceOrderSearchResultsVO serviceOrder : serviceOrders) {
							Double individualCondPrice = getIndividualCondPrice(
									conditionalPrice, proportionateLimits,
									serviceOrder);
							processResp = serviceOrderBO.acceptConditionalOffer(serviceOrder.getSoId(), resourceId, vendorId, 
									resReasonId, startDate, endDate, startTime, endTime, individualCondPrice, buyerId, false, securityContext);
							if (processResp.isError()) {
								throw new BusinessServiceException(processResp.getMessages().get(0));
							}
						}
						//Update SO_GROUP, SO_GROUP_PRICE and SO_GROUP_ROUTED_PROVIDERS
						updateGroupForConditionalOffer(groupId, resourceId, conditionalPrice);
						//Splits the orders
						splitOrderGroup(groupId);
						return processResp;
						
					}
					else{
						throw new BusinessServiceException("Provider withdraw conditional Offer");
					}
				}
				else{
					throw new BusinessServiceException("Service Order is not in routed state");
				}
				
			}
			
		} catch (Exception e) {
			throw new BusinessServiceException(e.getMessage());
		} 
	}
	
	/**
	 * Update SO_GROUP, SO_GROUP_PRICE and SO_GROUP_ROUTED_PROVIDERS with conditional price and accepted status
	 * 
	 * @param groupId
	 * @param resourceId
	 * @param conditionalPrice
	 * @param calendar
	 * @throws DataServiceException
	 * @throws BusinessServiceException
	 */
	private void updateGroupForConditionalOffer(String groupId,
			Integer resourceId, Double conditionalPrice)
			throws DataServiceException, BusinessServiceException {
		TimeZone tz = TimeZone.getTimeZone(OrderConstants.DEFAULT_ZONE);
		Calendar calendar = Calendar.getInstance(tz);
		RoutedProvider conditionalOffer = new RoutedProvider();
		conditionalOffer.setSoId(groupId);
		conditionalOffer.setResourceId(resourceId);
		conditionalOffer.setModifiedDate(new Timestamp(calendar.getTimeInMillis()));
		orderGroupDAO.updateGroupRoutedResourcesForConditionalOffer(conditionalOffer);
		updateSoGroupStatus(groupId, resourceId, OrderConstants.ACCEPTED_STATUS, OrderConstants.ACCEPTED, true);
		if(conditionalPrice != null){
			double groupSpendLimitParts = getGroupFinalSpendLimitParts(groupId);
			updateOrderGroupSpendLimit(groupId, null, null,conditionalPrice - groupSpendLimitParts, 
					groupSpendLimitParts, null, null, conditionalPrice);
		}
	}
	
	public ProcessResponse withdrawGroupedConditionalAcceptance(String groupId,
			Integer resourceID, Integer providerRespId, SecurityContext securityContext)
	throws BusinessServiceException {
		ProcessResponse pr = new ProcessResponse();
		RoutedProvider rop = new RoutedProvider();
		int chldOrdersUpdCnt,parentOrdUpdCnt;
		try {
			List<String> soIds = serviceOrderBO.getServiceOrderIDsForGroup(groupId);
			rop.setSoId(groupId);
			rop.setSoIds(soIds);
			rop.setResourceId(resourceID);
			rop.setProviderRespId(providerRespId);
			Integer wfState = getServiceOrderDao().checkWfState(
					soIds.get(0));
			if (wfState.equals(OrderConstants.ROUTED_STATUS)) {
				chldOrdersUpdCnt = getServiceOrderDao()
						.updateProvRespGroupedConditionalOffer(rop);
				parentOrdUpdCnt = orderGroupDAO.withdrawGroupConditionalAcceptance(rop);
				if (chldOrdersUpdCnt == 0 || parentOrdUpdCnt==0) {
					pr
							.setMessage(OrderConstants.NO_CONDITIONAL_OFFER_ASSOCIATED);
					pr.setCode(ServiceConstants.USER_ERROR_RC);
				} else {
					pr
							.setMessage(OrderConstants.SUCESSFULLY_WITH_DRAWN_OFFER);
					pr.setCode(ServiceConstants.VALID_RC);
				}
			} else {
				pr
						.setMessage(OrderConstants.NO_CONDITIONAL_OFFER_ASSOCIATED_WITH_WFSTATE);
				pr.setCode(ServiceConstants.USER_ERROR_RC);
			}
		}
		catch (Exception e) {
			logger.info("Could not withdraw conditional offer for grouped service order",e);
			throw new BusinessServiceException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * Verify if the buyer can spend the conditional price for the group
	 * 
	 * @param orderGrpObj
	 * @param serviceOrders
	 * @param buyerId
	 * @param conditionalPrice
	 * @param securityContext
	 * @throws BusinessServiceException
	 */
	private void verifyBuyerFunding(OrderGroupVO orderGrpObj, List<ServiceOrderSearchResultsVO> serviceOrders, Integer buyerId,
			Double conditionalPrice, SecurityContext securityContext) throws BusinessServiceException {
		double groupPostingFee = getGroupPostingFee(serviceOrders);
		try {
			if(serviceOrderBO.buyerOverMaxLimit(groupPostingFee, conditionalPrice, 0.0,securityContext)){
					throw new BusinessServiceException(OrderConstants.BUYER_OVER_MAX_SPEND_LIMIT);
			}
			else if(securityContext!=null)
			{
				if(securityContext.isIncreaseSpendLimitInd())
					throw new BusinessServiceException(OrderConstants.BUYER_OVER_MAX_SPEND_LIMIT);
			}
			// check available funds next
			AjaxCacheVO avo = new AjaxCacheVO();
			avo.setCompanyId(buyerId);
			avo.setRoleType("BUYER");
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setAccessFee(groupPostingFee);
			serviceOrder.setSpendLimitLabor(orderGrpObj.getFinalSpendLimitLabor());
			serviceOrder.setSpendLimitParts(orderGrpObj.getFinalSpendLimitParts());
			serviceOrder.setFundingTypeId(serviceOrderDAO.getServiceOrder(serviceOrders.get(0).getSoId()).getFundingTypeId());
			double groupSpendLimitParts = 0.0;
			if (orderGrpObj.getFinalSpendLimitParts() != null) {
				groupSpendLimitParts = orderGrpObj.getFinalSpendLimitParts();
			}
			double delta = conditionalPrice - (orderGrpObj.getFinalSpendLimitLabor() + groupSpendLimitParts);
			
			if (!serviceOrderBO.enoughBuyerFunds(avo, serviceOrder, null, delta)) {
				if (securityContext.getRoleId() == OrderConstants.SIMPLE_BUYER_ROLEID) {
					throw new BusinessServiceException(OrderConstants.SIMPLE_BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS);
				}
				else
					throw new BusinessServiceException(OrderConstants.BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS);
			}
		} catch (Exception e) {
			throw new BusinessServiceException(e.getMessage());
		}
		
	}
	
	/**
	 * Returns the posting fee for the group
	 * 
	 * @param serviceOrders
	 * @return
	 */
	private Double getGroupPostingFee(
			List<ServiceOrderSearchResultsVO> serviceOrders) {
		Double groupPostingFee = 0.0;
		for (ServiceOrderSearchResultsVO serviceOrder : serviceOrders) {
			if (serviceOrder.getAccessFee() != null) {
				groupPostingFee += serviceOrder.getAccessFee();
			}
		}
		return groupPostingFee;
	}

	public List<RoutedProvider> getRoutedProviderResponse(String groupId){
		List<RoutedProvider> providersList = new ArrayList<RoutedProvider>();
		try{
			providersList = orderGroupDAO.getRoutedProvidersResponse(groupId);
			
		}catch(DataServiceException e){
			logger.error(e.getMessage());
		}
		return providersList;
		
	}
	
	/**
	 * @param conditionalPrice
	 * @param proportionateLimits
	 * @param serviceOrder
	 * @return
	 */
	private Double getIndividualCondPrice(Double conditionalPrice,
			Hashtable<String, Double> proportionateLimits,
			ServiceOrderSearchResultsVO serviceOrder) {
		double spendLimitParts = 0.0;
		if (serviceOrder.getSpendLimitParts() != null) {
			spendLimitParts = serviceOrder.getSpendLimitParts();
		}
		Double individualCondPrice = null;
		if (conditionalPrice != null) {
			individualCondPrice = MoneyUtil.getRoundedMoney(serviceOrder.getSpendLimit() 
					* proportionateLimits.get(OrderConstants.PROPORTIONATE_LABOR_SPEND_LIMIT)) + spendLimitParts;
		}
		return individualCondPrice;
	}

	
	public Map<String, Object> getGroupOrderServiceDateTime(String groupId) throws BusinessServiceException{
		Map<String, Object> serviceDateTimeMap = new HashMap<String, Object>();
		Timestamp groupServiceDate1;
		Timestamp groupServiceDate2;
		String groupServiceTimeStart;
		String groupServiceTimeEnd;
		
		try{
			List<ServiceOrderSearchResultsVO> childOrdersList = this.getServiceOrdersForGroup(groupId);
			ServiceOrder firstSo = serviceOrderBO.getServiceOrder(childOrdersList.get(0).getSoId());
			groupServiceDate1= firstSo.getServiceDate1();
			groupServiceDate2 = firstSo.getServiceDate2();
			groupServiceTimeStart = firstSo.getServiceTimeStart();
			groupServiceTimeEnd = firstSo.getServiceTimeEnd();
			long groupServiceTimeStartMS = TimeUtils.convertStringTimeToMilliSeconds(groupServiceTimeStart);
			long groupServiceTimeEndMS = TimeUtils.convertStringTimeToMilliSeconds(groupServiceTimeEnd);
			Calendar groupEndDateTime = TimeUtils.getDateTime(groupServiceDate2, groupServiceTimeEnd, firstSo.getServiceLocationTimeZone());
			
			for(int i =1; i< childOrdersList.size();i++){
				ServiceOrder currentChildOrder = serviceOrderBO.getServiceOrder(childOrdersList.get(i).getSoId());
				// service date 2
				Timestamp currentServiceDate = currentChildOrder.getServiceDate2();
				/*if(currentServiceDate.before(groupServiceDate2)){
					groupServiceDate2 = currentServiceDate;
				}*/
				// start Time
				String currentServiceTimeStart = currentChildOrder.getServiceTimeStart();
				long currentTimeStartMS = TimeUtils.convertStringTimeToMilliSeconds(currentServiceTimeStart);
				if(currentTimeStartMS < groupServiceTimeStartMS){
					groupServiceTimeStartMS = currentTimeStartMS;
					groupServiceTimeStart = currentServiceTimeStart;
				}
				// end Date Time
				
				String currentServiceTimeEnd = currentChildOrder.getServiceTimeEnd();
				long currentTimeEndMS = TimeUtils.convertStringTimeToMilliSeconds(currentServiceTimeEnd);
				
				Calendar endDateTime = TimeUtils.getDateTime(currentServiceDate, currentServiceTimeEnd, currentChildOrder.getServiceLocationTimeZone());
				if(endDateTime != null && groupEndDateTime != null){
					if(endDateTime.before (groupEndDateTime)){
						groupEndDateTime = endDateTime;
						groupServiceDate2 = currentServiceDate;
						groupServiceTimeEnd = currentServiceTimeEnd;
						
					}
				}
				
			}
			
			serviceDateTimeMap.put(OrderConstants.GROUP_SERVICE_DATE1, groupServiceDate1);
			if(groupServiceDate2 != null){
				serviceDateTimeMap.put(OrderConstants.GROUP_SERVICE_DATE2, groupServiceDate2);
			}else{
				serviceDateTimeMap.put(OrderConstants.GROUP_SERVICE_DATE2, groupServiceDate1);
			}
			
			serviceDateTimeMap.put(OrderConstants.GROUP_SERVICE_START_TIME, groupServiceTimeStart);
			serviceDateTimeMap.put(OrderConstants.GROUP_SERVICE_END_TIME, groupServiceTimeEnd);
			
		
		}catch(Exception e){
			throw new BusinessServiceException("error while getting Group Service Date Time" + e.getMessage());
		}
		return serviceDateTimeMap;
	}

	
	
	public IOrderGroupDao getOrderGroupDao() {
		return orderGroupDAO;
	}

	public void setOrderGroupDao(IOrderGroupDao orderGroupDao) {
		this.orderGroupDAO = orderGroupDao;
	}

	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBO;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBO = serviceOrderBo;
	}
	
	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDAO;
	}
	
	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDAO = serviceOrderDao;
	}
	
	public IAdminTripChargeDAO getAdminTripChargeDao() {
		return adminTripChargeDAO;
	}
	
	public void setAdminTripChargeDao(IAdminTripChargeDAO adminTripChargeDao) {
		this.adminTripChargeDAO = adminTripChargeDao;
	}

	public ProcessResponse updateGroupServiceDate(String groupId, Timestamp serviceDate1, Timestamp serviceDate2, String startTime, String endTime, SecurityContext securityContext) throws BusinessServiceException{
		
		ProcessResponse processResp = new ProcessResponse();
		try{
			List<ServiceOrderSearchResultsVO> childorders = this.getServiceOrdersForGroup(groupId);
			for(ServiceOrderSearchResultsVO currentResult : childorders ){
				String soId = currentResult.getSoId();
				ServiceOrder currentSO = serviceOrderBO.getServiceOrder(soId);
				//TODO any better way to copy service order instead of making call to db again
				ServiceOrder updatedSO = serviceOrderBO.getServiceOrder(soId);
				updatedSO.setServiceDate1(serviceDate1);
				updatedSO.setServiceDate2(serviceDate2);
				updatedSO.setServiceTimeStart(startTime);
				updatedSO.setServiceTimeEnd(endTime);
				processResp = serviceOrderUpdateBO.updateSchedule(currentSO, updatedSO, securityContext);
			}
		}catch(Exception e){
			throw new BusinessServiceException("Error while updating child orders date & time" + e.getMessage());
		}
		
		return processResp;
		
	}
	
	public void updateServiceContact(String soId, Contact contact)
	{
		serviceOrderUpdateBO.updateContact(soId, contact);
	}
	
	public String getFirstSoIdForGroup(String groupId) throws BusinessServiceException
	{
		try{
			return orderGroupDAO.getFirstSoIdForGroup(groupId);
		}
		catch(Exception e){
			throw new BusinessServiceException("Error in getFirstSoIdForGroup(groupId)" + e.getMessage());
		}
	}
	
	public void sendallProviderResponseExceptAcceptedForGroup(String groupId,
			SecurityContext securityContext) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Retrieves the cutom refs-unit no & sales check number associated with the buyer
	 * @param buyerId
	 * @return buyerRefs
	 */
	public List<BuyerReferenceVO> getCustomReferenceForOGMSearch(Integer buyerId) throws BusinessServiceException {
		List<BuyerReferenceVO> buyerRefs = null;
		try {			
			buyerRefs = orderGroupDAO.getCustomReferenceForOGMSearch(buyerId);	 			
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Error while getting custom refs for OGM Search",e);
		}
		return buyerRefs;
	}
	
	
	public ILookupDAO getLookupDao() {
		return lookupDao;
	}
	public void setLookupDao(ILookupDAO lookupDao) {
		this.lookupDao = lookupDao;
	}
	public IServiceOrderUpdateBO getServiceOrderUpdateBO() {
		return serviceOrderUpdateBO;
	}
	public void setServiceOrderUpdateBO(IServiceOrderUpdateBO serviceOrderUpdateBO) {
		this.serviceOrderUpdateBO = serviceOrderUpdateBO;
	}
	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}
	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}
	public ILedgerFacilityBO getTransBo() {
		return transBo;
	}
	public void setTransBo(ILedgerFacilityBO transBo) {
		this.transBo = transBo;
	}


}
