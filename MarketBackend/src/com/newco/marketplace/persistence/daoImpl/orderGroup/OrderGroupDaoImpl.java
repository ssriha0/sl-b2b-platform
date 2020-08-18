package com.newco.marketplace.persistence.daoImpl.orderGroup;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.EstimateHistoryVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO;
import com.newco.marketplace.dto.vo.serviceorder.ResponseSoVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.orderGroup.ServiceOrderGroupMatchVO;
import com.sears.os.dao.impl.ABaseImplDao;


/**
 * @author Mahmud Khair
 *
 */
public class OrderGroupDaoImpl extends ABaseImplDao implements IOrderGroupDao {
	
	private Logger logger = Logger.getLogger(OrderGroupDaoImpl.class.getName());
	
	public String  getTestString(){
		
		String result= "Success";
		return result;
		
	}
	
	public String getParentMatch(String soID) throws Exception{
		String matchGroup = "";
		
		
		
		return matchGroup;
		
	}

	public List<ServiceOrderGroupMatchVO> getParentMatchForAddressAndCategory(ServiceOrderGroupMatchVO soToMatch)
	                                       throws DataServiceException{
		String matchGroup = "";
		List<ServiceOrderGroupMatchVO> soListWithMatch = new ArrayList<ServiceOrderGroupMatchVO>();
		try{
			soListWithMatch = (ArrayList<ServiceOrderGroupMatchVO>)queryForList("so_group_match.query", soToMatch);
				
		}catch(Exception e){
			logger.info("[OrderGroupDaoImpl.getParentMatchForAddressAndCategory -queryList - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}
		
		
		return soListWithMatch;
		
	}
	
	public void createOrderGroup(String groupId, String groupTitle) throws DataServiceException{
		Map<String, String> paramMap = new HashMap<String, String>();
		try{
			paramMap.put("parentGrpId", groupId);
			paramMap.put("wfStateId", String.valueOf(OrderConstants.DRAFT_STATUS));
			paramMap.put("groupTitle", groupTitle);
			insert("soGroup.insert_order_grp", paramMap);
		}catch(Exception e){
			logger.info("[OrderGroupDaoImpl.createOrderGroup -insert into so_group - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Exception thrown while inserting new order group record into so_gorup", e);
		}
		
	
		
	}
	
	
	public void addToOrderGroup(String soId, String parentGroupId) throws DataServiceException{
		Map<String, String> paramMap = new HashMap<String, String>();
		try{
			paramMap.put("parentGrpId", parentGroupId);
			paramMap.put("soId", soId);
			update("update_so_hdr_parent_grp.query", paramMap);
		}catch(Exception e){
			logger.info("[OrderGroupDaoImpl.addToOrderGroup -update so_hdr - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Exception thrown while updating so _hdr with order grp id", e);
		}
		
		
	}
	
	public void updateSubStatusToQForGrouping(String soId) throws DataServiceException{
		Map<String, String> paramMap = new HashMap<String, String>();
		String subStatus = String.valueOf(OrderConstants.QUEUED_FOR_GROUPING_SUBSTATUS);
		try{
			paramMap.put("subStatus", subStatus);
			paramMap.put("soId", soId);
			update("soGroup.update_so_hdr_sub_status", paramMap);
		}catch(Exception e){
			logger.info("[OrderGroupDaoImpl.updateSubStatusToQForGrouping -update so_hdr - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Exception thrown while updating so _hdr with sub status -- Q For grping", e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao#getServiceOrdersForGroup(java.lang.String)
	 */
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForGroup(
			String parentGroupId) throws DataServiceException {
		List<ServiceOrderSearchResultsVO> serviceOrders = new ArrayList<ServiceOrderSearchResultsVO>();
		try{
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
					parameter.put("parentGroupId", parentGroupId);
			serviceOrders = (ArrayList<ServiceOrderSearchResultsVO>)queryForList("so_group_match.getServiceOrdersForGroup", parameter);
				
		}catch(Exception e){
			logger.info("[OrderGroupDaoImpl.getServiceOrdersForGroup -queryList - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}
		
		return serviceOrders;
	}
	
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForOrigGroup(String origGroupId) throws DataServiceException {
		List<ServiceOrderSearchResultsVO> serviceOrders = new ArrayList<ServiceOrderSearchResultsVO>();
		try{
			serviceOrders = (ArrayList<ServiceOrderSearchResultsVO>)queryForList("so_group_match.getServiceOrdersForOrigGroup", origGroupId);
				
		}catch(Exception e){
			logger.info("[OrderGroupDaoImpl.getServiceOrdersForGroup -queryList - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}
		
		return serviceOrders;
	}

	public List<ServiceOrder> getServiceOrderVOsForGroup(String parentGroupId)throws DataServiceException {

		List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
		
		try{
			serviceOrders = (ArrayList<ServiceOrder>)queryForList("so.group.query", parentGroupId);
				
		} catch(Exception e) {
			logger.info("[OrderGroupDaoImpl.getServiceOrderVOsForGroup -queryList - Exception] " + StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}
		
		return serviceOrders;
	}
	
	/** This method is used to get basic info of child orders **/
	public List<ServiceOrderSearchResultsVO> getSOSearchResultsVOsForGroup(String parentGroupId)throws DataServiceException {

		List<ServiceOrderSearchResultsVO> serviceOrders = new ArrayList<ServiceOrderSearchResultsVO>();
		try{
			serviceOrders = (ArrayList<ServiceOrderSearchResultsVO>)queryForList("so.group_resultVO.query", parentGroupId);
				
		} catch(Exception e) {
			logger.info("[OrderGroupDaoImpl.getServiceOrderVOsForGroup -queryList - Exception] " + StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}
		
		return serviceOrders;
	}
	
	
	

	public int updateOrderGroupPrice(OrderGroupVO soGroupVO)
			throws DataServiceException {
		int recordsUpdated = 0;
		try {
			logger.debug("Start of OrderGroupDaoImpl.updateOrderGroupPrice");
			logger.info("Update group prices for GroupId: " + soGroupVO.getGroupId());

			if(soGroupVO.getOriginalSpendLimitLabor() != null)
				logger.debug("originalSpendLimitLabor is being changed: " + soGroupVO.getOriginalSpendLimitLabor());
			
			
			recordsUpdated = update("soGroup.update_so_group_price", soGroupVO);
		} catch(Exception ex) {
			logger.error("[OrderGroupDaoImpl.updateOrderGroupPrice - update_so_group_price - Exception]", ex);
			throw new DataServiceException("[OrderGroupDaoImpl.updateOrderGroupPrice - update_so_group_price - Exception]", ex);
		}
		return recordsUpdated;
	}
	
	public int updateProviderResponse(ResponseSoVO soGroupResponseVO)
			throws DataServiceException {
		try {
			logger.debug("Start of OrderGroupDaoImpl.updateProviderResponse");
			logger.info("GroupId: " + soGroupResponseVO.getSoId());
			return update("soGroup.update_provider_response", soGroupResponseVO);
		} catch (Exception ex) {
			logger.error("[OrderGroupDaoImpl.updateProviderResponse - Exception] ", ex);
			throw new DataServiceException("Error", ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao#updateGroupParentId(java.lang.String, java.lang.String)
	 */
	public void updateGroupParentId(String oldGroupId, String newGroupId)
			throws DataServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("oldParentId", oldGroupId);
			paramMap.put("newParentId", newGroupId);
			update("soGroup.update_parentId_for_group", paramMap);
		} catch(Exception ex) {
			logger.error("[OrderGroupDaoImpl.updateGroupParentId - - Exception] ", ex);
			throw new DataServiceException("Exception thrown while updating group parent ID", ex);
		}		
	}
	
	// Create dummy Order Group data
	private List<OrderGroupVO> getDummyGroupOrder(String title)
	{
		Random rand = new Random();
		
		ServiceOrderSearchResultsVO serviceOrder;		
		List<OrderGroupVO> orderGroups = new ArrayList<OrderGroupVO>();
		OrderGroupVO orderGroup;
		
		int numOrderGroups = rand.nextInt(12) + 2;
		int i;
		
		for (i = 0; i < numOrderGroups; i++)
		{
			orderGroup = new OrderGroupVO();
			orderGroup.setGroupId(String.valueOf(rand.nextInt(99999999)));

			// Service Orders
			int numServiceOrders = rand.nextInt(4) +2;
			int j;
			List<ServiceOrderSearchResultsVO> soList = new ArrayList<ServiceOrderSearchResultsVO>();

			for(j=0 ; j<numServiceOrders ; j++)
			{
				serviceOrder = new ServiceOrderSearchResultsVO();
				serviceOrder.setSoId(rand.nextInt(999999999) +"");
				serviceOrder.setSoTitle("Home Electronics Title" +j);
				serviceOrder.setSoTitleDesc("Home Electronics Desc"+j);
				serviceOrder.setPrimarySkillCategoryId(1);
				serviceOrder.setSoId(rand.nextInt(99999999) + "");
				serviceOrder.setEndCustomerFirstName("Joe" +j);
				serviceOrder.setEndCustomerLastName("Schmoe"+j);
				serviceOrder.setServiceDate1(new Timestamp(1, 2, 3, 4, 5, 6, 7));
				serviceOrder.setStateCd("IL");
				serviceOrder.setCity("Hoffman Estates" +j);
				serviceOrder.setZip("60107");
				serviceOrder.setZip4("60107");
				soList.add(serviceOrder);
			}
			orderGroup.setServiceOrders(soList);
			

			orderGroups.add(orderGroup);
		}
		
		return orderGroups;
	}
	
	
	public List<OrderGroupVO> getOrderGroupsByAddress(String buyerId, String address, Integer status, Integer substatus) throws DataServiceException
	{
		return getDummyGroupOrder("Service Orders By Address");
	}
	
	public List<OrderGroupVO> getOrderGroupsByCustomerName(String buyerId, String name, Integer status, Integer substatus) throws DataServiceException
	{
		return getDummyGroupOrder("Service Orders by Customer Name");
	}
	
	public List<OrderGroupVO> getOrderGroupsByPhone(String buyerId, String phone, Integer status, Integer substatus) throws DataServiceException
	{
		return getDummyGroupOrder("Service Orders by Phone");
	}
	
	public List<OrderGroupVO> getOrderGroupsBySOId(String buyerId, String soId, Integer status, Integer substatus) throws DataServiceException
	{
		return getDummyGroupOrder("Service Orders by SOID");
	}
	
	public List<OrderGroupVO> getOrderGroupsByZip(String buyerId, String zip, Integer status, Integer substatus) throws DataServiceException
	{
		return getDummyGroupOrder("Service Orders by Zip");
	}

	public List<OrderGroupVO> getOrderGroups(String buyerId) throws DataServiceException {
		List<OrderGroupVO> orderGroups = null;
		try {
			orderGroups = queryForList("soGroup.query", buyerId);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while retrieving order groups for buyerID:"+buyerId+"; root cause + " + ex.getMessage();
			logger.error(strMessage);
			throw new DataServiceException(strMessage, ex);
		}
		return orderGroups;
	}

	public OrderGroupVO getOrderGroupByGroupId(String groupId)
			throws DataServiceException {
		OrderGroupVO orderGroupVO = null;
		try {
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("groupId", groupId);
			orderGroupVO = (OrderGroupVO)queryForObject("soGroup.query_by_group_id", parameter);
		} catch (Exception ex) {
			logger.error("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getOrderGroupByGroupId()", ex);
			throw new DataServiceException("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getOrderGroupByGroupId()", ex);
		}
		return orderGroupVO;
	}
	
	//SL-21465
	public EstimateVO getServiceOrderEstimationDetails(String soID,int vendorId,int ResourceId)
			throws DataServiceException {
		EstimateVO estimateVO = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soID);
			param.put("vendorId", vendorId);
			param.put("resourceId", ResourceId);
			estimateVO = (EstimateVO)queryForObject("soGroup.query_by_so_id_to_get_estimateDetails", param);
			if(null != estimateVO && estimateVO.getEstimationId()!=null ){
				//It will get the recently accepted total price for estimation  when accepted estimation is updated
				Double latestAccetedEstimationPrice=this.getEstimateLatestAccetedPrice(estimateVO.getEstimationId());
				if(null!=latestAccetedEstimationPrice){
					estimateVO.setTotalPrice(latestAccetedEstimationPrice);
				}
				
			}

		} catch (Exception ex) {
			logger.error("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getOrderGroupByGroupId()", ex);
			throw new DataServiceException("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getOrderGroupByGroupId()", ex);
		}
		return estimateVO;
	}
	
	public EstimateVO getServiceOrderEstimationDetails(String soID)
			throws DataServiceException {
		EstimateVO estimateVO = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soID);
			param.put("vendorId", null);
			param.put("resourceId", null);
			estimateVO = (EstimateVO)queryForObject("soGroup.query_by_so_id_to_get_estimateDetails", param);
			if(null != estimateVO && estimateVO.getEstimationId()!=null ){
				//It will get the recently accepted total price for estimation  when accepted estimation is updated
				Double latestAccetedEstimationPrice=this.getEstimateLatestAccetedPrice(estimateVO.getEstimationId());
				if(null!=latestAccetedEstimationPrice){
					estimateVO.setTotalPrice(latestAccetedEstimationPrice);
				}
				
			}
			
		} catch (Exception ex) {
			logger.error("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getOrderGroupByGroupId()", ex);
			throw new DataServiceException("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getOrderGroupByGroupId()", ex);
		}
		return estimateVO;
	}
	
	public Double getEstimateLatestAccetedPrice(Integer estimationId) throws DataServiceException{
		logger.info("Entering into getEstimateLatestAccetedPrice  ");
		Double latestAccetedEstimationPrice=null;
		try{
			latestAccetedEstimationPrice=(Double)queryForObject("getEstimateLatestAccetedPrice.query", estimationId);
			
		} catch (Exception ex) {
			logger.error("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getOrderGroupByGroupId()", ex);
			throw new DataServiceException("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getOrderGroupByGroupId()", ex);
		}
		return latestAccetedEstimationPrice;
		
		
	}

	
	@SuppressWarnings("unchecked")
	public ArrayList<Integer> getRoutedResourceIds(String groupId)throws DataServiceException {
		ArrayList<Integer> routedList = new ArrayList<Integer>();
		try {
		 routedList = (ArrayList<Integer>) queryForList("soGroup.getRoutedResources", groupId);
		
		}catch(Exception ex){
			throw new DataServiceException("Unexpected data exception while querying routed pro for group in OrderGroupDaoImpl.queryRoutedGroupSO(String groupId)", ex);
		}
		return routedList;
	}
	
	@SuppressWarnings("unchecked")
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForVendor(String soID,int vendorId,int ResourceId)
			throws DataServiceException {
		List<EstimateHistoryVO> estimateHistoryVO = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soID);
			param.put("vendorId", vendorId);
			param.put("resourceId", ResourceId);
			estimateHistoryVO = (List<EstimateHistoryVO>)queryForList("soGroup.query_by_so_id_to_get_estimateHistoryDetails", param);
		} catch (Exception ex) {
			logger.error("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getServiceOrderEstimationHistoryDetailsForVendor()", ex);
			throw new DataServiceException("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getServiceOrderEstimationHistoryDetailsForVendor()", ex);
		}
		return estimateHistoryVO;
	}
	
	@SuppressWarnings("unchecked")
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForBuyer(String soID)
			throws DataServiceException {
		List<EstimateHistoryVO> estimateHistoryVO = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soID);
			param.put("vendorId", null);
			param.put("resourceId", null);
			estimateHistoryVO = (List<EstimateHistoryVO>)queryForList("soGroup.query_by_so_id_to_get_estimateHistoryDetails", param);
		} catch (Exception ex) {
			logger.error("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getServiceOrderEstimationHistoryDetailsForBuyer()", ex);
			throw new DataServiceException("Unexpected data exception while querying order group by group id in OrderGroupDaoImpl.getServiceOrderEstimationHistoryDetailsForBuyer()", ex);
		}
		return estimateHistoryVO;
	}
	
	public void updateSOGroupStatus(String groupId, String status) throws DataServiceException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wfStatusId", status);
		paramMap.put("groupId", groupId);
		
		try{
			update("soGroup.update_status", paramMap);
		}catch(Exception ex){
			logger.error("Unexpected data exception while updating group Status in OrderGroupDaoImpl.updateSOGroupStatus(String groupId, String status)", ex);
			throw new DataServiceException("Unexpected data exception while updating group Status in OrderGroupDaoImpl.updateSOGroupStatus(String groupId, String status)", ex);
		}
		
	}
	
	public void updateAccepted(Integer resourceId, String groupId)throws DataServiceException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("responseId", OrderConstants.ACCEPTED);
		paramMap.put("groupId", groupId);
		paramMap.put("resourceId", resourceId);
		
		try{
			update("soGroup.update_routed_providers", paramMap);
		}catch(Exception ex){
			logger.error("Unexpected data exception while updating provider response  in OrderGroupDaoImpl.updateAccepted", ex);
			throw new DataServiceException("Unexpected data exception while updating provider response in OrderGroupDaoImpl.updateAccepted", ex);
		}
	}
	
	public void ungroupOrderGrp(ServiceOrderPriceVO soPrice, String groupId)throws DataServiceException{
		
		Double spendLimitLabor = null;
		Double spendLimitParts = null;
		
		try{
			spendLimitLabor = soPrice.getOrigSpendLimitLabor();
			spendLimitParts = soPrice.getOrigSpendLimitParts();
			
			ServiceOrderSearchResultsVO  serviceOrder = new ServiceOrderSearchResultsVO();
			serviceOrder.setSoId(soPrice.getSoId());
			serviceOrder.setSpendLimitLabor(spendLimitLabor);
			serviceOrder.setSpendLimitParts(spendLimitParts);	
			serviceOrder.setParentGroupId(groupId);
				
			update("soGroup.update_so_hdr_ungroup", serviceOrder);
		
			
		}catch(Exception ex){
			logger.error("Unexpected data exception while setting orderGrp Id = null in OrderGroupDaoImpl.ungroupOrderGrp", ex);
			throw new DataServiceException("Unexpected data exception while updating provider response in OrderGroupDaoImpl.updateAccepted", ex);
		}
		
	}
	
	public void insertIntoGroupPrice(OrderGroupVO soOrderGroup)throws DataServiceException{
		
		try{
			insert("soGroup.insert_so_group_price", soOrderGroup);
			
		}catch(Exception ex){
			logger.error("Unexpected data exception while inserting into grp Price in OrderGroupDaoImpl.insertIntoGroupPrice", ex);
			throw new DataServiceException("Unexpected data exception while inserting into grp Price in OrderGroupDaoImpl.insertIntoGroupPrice", ex);
		}
		
	}

	
	public void deleteGroupRoutedProviders(String groupId)throws DataServiceException{
		try{
			delete("soGroup.delete_so_group_routed_providers", groupId);
			
		}catch(Exception ex){
			logger.error("Unexpected data exception while deleting routed records from so_group_routed_providers in OrderGroupDaoImpl.deleteGroupRoutedProviders", ex);
			throw new DataServiceException("Unexpected data exception while deleting routed records from so_group_routed_providers in OrderGroupDaoImpl.deleteGroupRoutedProviders", ex);
		}
	}
	
	public void insertGroupRoutedProviders(String groupId,
			List<Integer> orderGrpProvidersId)throws DataServiceException{
		RoutedProvider routedProvider = null;
		Calendar calendar = Calendar.getInstance();
		Timestamp routedTs = new Timestamp(calendar.getTimeInMillis());
		try{
			for(Integer resourceId : orderGrpProvidersId){
				routedProvider = new RoutedProvider();
				routedProvider.setResourceId(resourceId);
				routedProvider.setSoId(groupId);
				routedProvider.setRoutedDate(routedTs);
				insert("sogrouproutedproviders.insert", routedProvider);
				
			}
			
			
		}catch(Exception ex){
			logger.error("Unexpected data exception while inserting routed records from so_group_routed_providers in OrderGroupDaoImpl.deleteGroupRoutedProviders", ex);
			throw new DataServiceException("Unexpected data exception while inserting routed records from so_group_routed_providers in OrderGroupDaoImpl.deleteGroupRoutedProviders", ex);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao#updateConditionalOffer(java.util.List)
	 */
	public void updateGroupRoutedResourcesForConditionalOffer(RoutedProvider routedProvider)
			throws DataServiceException {
		logger.debug("----Start of OrderGroupDaoImpl.updateGroupRoutedResourcesForConditionalOffer()----");
		try {
			update("soGroup.updateForConditionalOffer", routedProvider);
			logger.info("Group " + routedProvider.getSoId() + " is updated with conditional offer by provider" + routedProvider.getResourceId());
		}
		catch (Exception ex) {
			logger.error("[OrderGroupDaoImpl.updateGroupRoutedResourcesForConditionalOffer - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error updating so group routed providers for conditional offer", ex);
		}
		logger.debug("----End of ServiceOrderDaoImpl.insertRoutedResourcesForConditionalOffer()----");
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao#updateConditionalOffer(java.util.List)
	 */
	public int withdrawGroupConditionalAcceptance(RoutedProvider routedProvider)
			throws DataServiceException {
		logger.debug("----Start of OrderGroupDaoImpl.withdrawGroupConditionalAcceptance()---- START");
		try {
			return update("soGroup.withdrawGroupConditionalAcceptance", routedProvider);
		}
		catch (Exception ex) {
			logger.error("[OrderGroupDaoImpl.withdrawGroupConditionalAcceptance - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error updating so group routed providers for conditional offer", ex);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao#checkConditionalOfferResp(com.newco.marketplace.dto.vo.serviceorder.RoutedProvider)
	 */
	public Integer checkConditionalOfferResp(String groupId,Integer resourceId)
			throws DataServiceException {
		Integer respId;
		try {
			RoutedProvider routedProvider = new RoutedProvider();
			routedProvider.setSoId(groupId);
			routedProvider.setResourceId(resourceId);
			respId = (Integer) queryForObject("soGroup.checkconditionaloffer",
					routedProvider);
		} catch (Exception e) {
			throw new DataServiceException("Error checking Offer Response", e);
		}
		return respId;
	}


	public ArrayList<RoutedProvider> getRoutedProvidersResponse(String groupId)throws DataServiceException {
		ArrayList<RoutedProvider> routedList = new ArrayList<RoutedProvider>();
		try {
		 routedList = (ArrayList<RoutedProvider>) queryForList("soGroup.getRoutedProvidersResponse", groupId);
		
		}catch(Exception ex){
			logger.error("Unexpected data exception while querying routed pro for group in OrderGroupDaoImpl.queryRoutedGroupSO(String groupId)", ex);
			throw new DataServiceException("Unexpected data exception while querying routed pro for group in OrderGroupDaoImpl.queryRoutedGroupSO(String groupId)", ex);
		}
		return routedList;
	}
	
	public String getFirstSoIdForGroup(String groupId)throws DataServiceException {
		String firstSoId;
		try {
			firstSoId = (String) queryForObject("so_group_match.getSingleServiceOrdersIdForGroup", groupId);
		
		}catch(Exception ex){
			logger.error("Unexpected data exception in getFirstSoIdForGroup(groupId)", ex);
			throw new DataServiceException("Unexpected data exception getFirstSoIdForGroup(groupId)", ex);
		}
		return firstSoId;
	}
	
	/**
	 * Retrieves the custom refs-unit no & sales check number associated with the buyer
	 * @param buyerId
	 * @return buyerRefs
	 */
	public List<BuyerReferenceVO> getCustomReferenceForOGMSearch(Integer buyerId) throws DataServiceException {
		List<BuyerReferenceVO> buyerRefs = null;
		try{
			buyerRefs = (ArrayList<BuyerReferenceVO>)queryForList("buyerRefType.getCustomReferenceForOGMSearch", buyerId);			
		}catch(Exception e){
			logger.error("Unexpected data exception in OrderGroupDaoImpl.getCustomReferenceForOGMSearch(buyerId)", e);
			throw new DataServiceException("Exception caught in OrderGroupDaoImpl.getCustomReferenceForOGMSearch(buyerId)", e);
		}		
		return buyerRefs;
	}
	
	/*
	 * get routed date for given resourceId & groupId
	 */
	public Date getRoutedDateForResourceId(String groupId, Integer resourceId) throws DataServiceException {

	 		Date routedDate = null;
			try{
				Map paramMap = new HashMap<String, Object>();
				paramMap.put("groupId", groupId);
				paramMap.put("resourceId", resourceId);
				routedDate = (Date)queryForObject("select.soGroupRoutedProvider.routedDate", paramMap);
			}catch(Exception e){
				logger.error("Error occured in getRoutedDateForResourceId : " ,e);
				throw new DataServiceException("error ocurred while get RoutedDate For ResourceId", e);
			}
			return routedDate;
		}
		

	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#updateServiceOrderSpnId(java.lang.String, java.lang.Integer)
	 */
	public int updateServiceOrderSpnId(String groupId, Integer spnId) throws DataServiceException{
		Map paramMap = new HashMap();
		paramMap.put("groupId", groupId);
		paramMap.put("spnId", spnId);
		return update("so.spnId.update", paramMap);
	}
	
	/**
	 * get routed date for given vendorId & groupId
	 * @param groupId
	 * @param vendorId
	 * @throws DataServiceException
	 */
	public Date getRoutedDateForFirm(String groupId, Integer vendorId) throws DataServiceException{
		Date routedDate = null;
		try{
			Map paramMap = new HashMap<String, Object>();
			paramMap.put("groupId", groupId);
			paramMap.put("vendorId", vendorId);
			routedDate = (Date)queryForObject("select.soGroupRoutedFirm.routedDate", paramMap);
		}catch(Exception e){
			logger.error("Error occured in getRoutedDateForFirm : " ,e);
			throw new DataServiceException("error ocurred while get RoutedDate For Firm", e);
		}
		return routedDate;
	}
	

}
