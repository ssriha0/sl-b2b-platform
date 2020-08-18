package com.newco.marketplace.persistence.iDao.orderGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.newco.marketplace.vo.orderGroup.ServiceOrderGroupMatchVO;


public interface IOrderGroupDao {
	
	public String  getTestString();
	
	public String getParentMatch(String soID) throws Exception;
	
	public List<ServiceOrderGroupMatchVO> getParentMatchForAddressAndCategory(ServiceOrderGroupMatchVO soToMatch)
    											throws DataServiceException;

	public void createOrderGroup(String parentGrpId, String groupTitle)throws DataServiceException;

	public void addToOrderGroup(String soId, String parentGroupId) throws DataServiceException;

	public void updateSubStatusToQForGrouping(String soId) throws DataServiceException;

	public List<OrderGroupVO> getOrderGroupsByAddress(String buyerId, String address, Integer status, Integer substatus) throws DataServiceException;
	public List<OrderGroupVO> getOrderGroupsByCustomerName(String buyerId, String name, Integer status, Integer substatus) throws DataServiceException;
	public List<OrderGroupVO> getOrderGroupsByPhone(String buyerId, String phone, Integer status, Integer substatus) throws DataServiceException;
	public List<OrderGroupVO> getOrderGroupsBySOId(String buyerId, String soId, Integer status, Integer substatus) throws DataServiceException;
	public List<OrderGroupVO> getOrderGroupsByZip(String buyerId, String zip, Integer status, Integer substatus) throws DataServiceException;
	
	/**
	 * Returns all order groups for given buyer; each grouped order containing child orders in it
	 * 
	 * @param buyerId
	 * @return List<OrderGroupVO> each VO containing child order ServiceOrderSearchResultsVO
	 * @throws DataServiceException
	 */
	public List<OrderGroupVO> getOrderGroups(String buyerId) throws DataServiceException;
	
	/**
	 * Returns the grouped order information (except the child orders list in it) for a given group id
	 * 
	 * @param groupId
	 * @return OrderGroupVO
	 * @throws DataServiceException
	 */
	public OrderGroupVO getOrderGroupByGroupId(String groupId) throws DataServiceException;
	
	//SL-21465
	public EstimateVO getServiceOrderEstimationDetails(String soID,int vendorId,int resourceId)
			throws DataServiceException;
	
	public EstimateVO getServiceOrderEstimationDetails(String soID)
			throws DataServiceException;
	
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForVendor(String soID,int vendorId,int ResourceId)
			throws DataServiceException;
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForBuyer(String soID)
			throws DataServiceException;
	
	/**
	 * This method finds all the service order in the specified group
	 * 
	 * @param parentGroupId
	 * @return List of ServiceOrderSearchResultsVO
	 * @throws DataServiceException
	 */
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForGroup(String parentGroupId)throws DataServiceException;

	/**
	 * This method finds all the service order in the specified group
	 * Gets basic info of child orders from so_hdr 
	 * @param parentGroupId
	 * @return List of ServiceOrderSearchResultsVO
	 * @throws DataServiceException
	 */
	public List<ServiceOrderSearchResultsVO> getSOSearchResultsVOsForGroup(String parentGroupId)throws DataServiceException;
	/**
	 * This method finds all the service order in the specified group
	 * 
	 * @param parentGroupId
	 * @return List of ServiceOrder
	 * @throws DataServiceException
	 */
	public List<ServiceOrder> getServiceOrderVOsForGroup(String parentGroupId)throws DataServiceException;
	
	
	/**
	 * Reusable method: Updates order group price in so_group_price table.
	 * 
	 * @param soGroupVO
	 * @throws DataServiceException
	 */
	public int updateOrderGroupPrice(OrderGroupVO soGroupVO) throws DataServiceException;
	
	/**
	 * Reusable method: Updates providers response for a grouped order - reject, accept etc
	 * 
	 * @param soGroupResponseVO
	 * @return
	 * @throws DataServiceException
	 */
	public int updateProviderResponse(ResponseSoVO soGroupResponseVO) throws DataServiceException;
	
	/**
	 * Updates all the orders of a group with new group ID
	 * 
	 * @param oldGroupId
	 * @param newGroupId
	 * @throws DataServiceException
	 */
	public void updateGroupParentId(String oldGroupId, String newGroupId)throws DataServiceException;
	
	/**
	 * get the list of routed provider Ids for the specified SO group
	 * @param groupId
	 * @return
	 */
	public ArrayList<Integer> getRoutedResourceIds(String groupId)throws DataServiceException;

	/**
	 * update so Group status
	 * @param resourceId
	 * @param routedAL
	 * @return
	 */
	public void updateSOGroupStatus(String groupId,String status)throws DataServiceException;

	/**
	 * update so_group_routed_providers provider response 
	 * @param resourceId
	 * @throws DataServiceException
	 */
	public void updateAccepted(Integer resourceId, String groupId)throws DataServiceException ;
	
	/**
	 * ungroup order group -- set group id of all orders in Group to NULL
	 * @param soIdsInGroup
	 * @throws DataServiceException
	 */
	public void ungroupOrderGrp(ServiceOrderPriceVO soPrice, String groupId)throws DataServiceException ;

	/**
	 * insert into so_group_price the price info when group is created
	 * @param soGroupPrice
	 * @throws DataServiceException
	 */
	public void insertIntoGroupPrice(OrderGroupVO soGroupPrice)throws DataServiceException ;
	
	/**
	 * insert routed records into so_group-routed_providers
	 * @param soId
	 * @param orderGrpProvidersId
	 * @throws DataServiceException
	 */
	public void insertGroupRoutedProviders(String groupId,
			List<Integer> orderGrpProvidersId)throws DataServiceException;
	
	
	/**
	 * delete routed records into so_group-routed_providers before re routing if any records exists already
	 * @param soId
	 * @param orderGrpProvidersId
	 * @throws DataServiceException
	 */
	public void deleteGroupRoutedProviders(String groupId)throws DataServiceException;
	
	/**
	 * Check if there is a conditional offer exists for the group
	 * 
	 * @param groupId
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer checkConditionalOfferResp(String groupId, Integer resourceId)throws DataServiceException;
	
	/**
	 * Updates the so_group_routed_providers table with the columns related to conditional offer
	 * 
	 * @param conditionalOffer
	 * @throws DataServiceException
	 */
	public void updateGroupRoutedResourcesForConditionalOffer(RoutedProvider conditionalOffer)throws DataServiceException;

	/**
	 * get Routed Provider's Responses
	 * @param groupId
	 * @return
	 * @throws DataServiceException
	 */
	public ArrayList<RoutedProvider> getRoutedProvidersResponse(String groupId)throws DataServiceException;

	/**
	 * get child orders for given orig group Id
	 * @param groupId
	 * @return
	 */
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForOrigGroup(String groupId) throws DataServiceException;
	
	/**
	 * get first soId for a group
	 * @param groupId
	 * @return
	 */
	public String getFirstSoIdForGroup(String groupId)throws DataServiceException;
	
	public int withdrawGroupConditionalAcceptance(RoutedProvider routedProvider)throws DataServiceException;
	
	/**
	 * Retrieves the custom refs-unit no & sales check number associated with the buyer
	 * @param buyerId
	 * @return buyerRefs
	 */
	public List<BuyerReferenceVO> getCustomReferenceForOGMSearch(Integer buyerId) throws DataServiceException;

	/**
	 * update so_hdr set spnId for given groupId
	 * @param soId
	 * @param spnId
	 * @throws DataServiceException
	 */
	public int updateServiceOrderSpnId(String groupId, Integer spnId) throws DataServiceException;
		
	
	/*
	 * get routed date for given resourceId & groupId
	 */
	public Date getRoutedDateForResourceId(String groupId, Integer resourceId) throws DataServiceException;
	/**
	 * get routed date for given vendorId & groupId
	 * @param groupId
	 * @param vendorId
	 * @throws DataServiceException
	 */
	public Date getRoutedDateForFirm(String groupId, Integer vendorId) throws DataServiceException;
	
}
