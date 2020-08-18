package com.newco.marketplace.business.iBusiness.orderGroup;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.EstimateHistoryVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * @author Mahmud Khair
 *
 */
public interface IOrderGroupBO {
	
	public String getParentMatchForSO(String soId, String soStatus) throws BusinessServiceException;

	public String getParentMatchForSO(ServiceOrder so, String draftStatus) throws BusinessServiceException;

	/**
	 * Updated the price for given children orders of a group (in SO header table) after applying trip charge multiple order discount.
	 * Also updates the discounted price in so_group_price table.
	 * 
	 * @param soSearchVOList
	 * @return group level discounted labor spend limit
	 * @throws BusinessServiceException
	 */
	public double priceOrderGroup(List<ServiceOrderSearchResultsVO> soSearchVOList)
	 	throws BusinessServiceException;
	
	/**
	 * Updates the different types of prices in so_group_price table.
	 * Only non-null values are updated in database; rest are ignored.
	 * 
	 * @param groupId
	 * @param originalSpendLimitLabor
	 * @param originalSpendLimitParts
	 * @param finalSpendLimitLabor
	 * @param finalSpendLimitParts
	 * @param discountedSpendLimitLabor
	 * @param discountedSpendLimitParts
	 * @param conditionalPrice
	 * @throws BusinessServiceException
	 */
	public void updateOrderGroupSpendLimit(String groupId, Double originalSpendLimitLabor, Double originalSpendLimitParts,
			Double finalSpendLimitLabor, Double finalSpendLimitParts, Double discountedSpendLimitLabor, 
			Double discountedSpendLimitParts, Double conditionalPrice) throws BusinessServiceException;
	
	/**
	 * When an order is deleted, voided or price-modified via SOW, the entire group has to be re-priced;
	 * If no child is remaining in the group; then the group has to be assigned 'Inactive' status
	 * If applyTripCharge is false; the trip charge is not applied and discounted prices are not updated; e.g. in case of Delete Draft
	 * If updateOriginalGroupPrice is false; the original group price is not updated; e.g. in case one of the child order price is increased in SOW
	 * 
	 * @param groupId
	 * @param applyTripCharge
	 * @param updateOriginalGroupPrice
	 * @throws BusinessServiceException
	 */
	public void rePriceOrderGroup(String groupId, boolean applyTripCharge, boolean updateOriginalGroupPrice) throws BusinessServiceException;
	
	/**
	 * Proportionately distributes the increased spend limit for labor and parts separately for given group in all it's children orders in SO header table.
	 * 
	 * @param soParentGroupId The parent group id
	 * @param totalSpendLimitLabor 
	 * @param totalSpendlimitParts
	 * @param increasedSpendLimitComment
	 * @param buyerId
	 * @param validate
	 * @param securityContext
	 * @return The process response object with code VALID_RC when successful, USER_ERROR_RC in case of an error
	 * @throws BusinessServiceException
	 */
	public ProcessResponse increaseSpendLimit(String soParentGroupId,
			Double increasedGroupSpendLimitLabor, Double increasedGroupSpendlimitParts,
			String increasedSpendLimitComment, int buyerId, boolean validate,
			SecurityContext securityContext) throws BusinessServiceException;
	
	/**
	 * Rejects all children order of given group; and also updates the provider response in so_group_routed_providers table.
	 * 
	 * @param resourceId
	 * @param groupId
	 * @param reasonId
	 * @param responseId
	 * @param modifiedBy
	 * @param reasonDesc
	 * @param securityContext
	 * @return The process response object with code VALID_RC when successful, USER_ERROR_RC in case of an error
	 * @throws BusinessServiceException
	 */
	public ProcessResponse rejectGroupedOrder(int resourceId, String groupId, int reasonId,
			int responseId, String modifiedBy, String reasonDesc, SecurityContext securityContext) throws BusinessServiceException;
	
	public String createOrderGroup(ServiceOrder serviceOrder)throws BusinessServiceException;
	
	public String createOrderGroupByBuyerId(Integer buyerId, String groupTitle)throws BusinessServiceException;
	
	public void addToOrderGroup(String soId, String parentGroupId) throws BusinessServiceException;
	/**
	 * method is being called locally within the class , added in the interface for triggering AOP
	 * @param currentSO
	 * @param parentGroupId
	 * @throws BusinessServiceException
	 */
	public void addToOrderGrp(ServiceOrder currentSO,
			String parentGroupId) throws BusinessServiceException;
	
	public OrderGroupVO getOrderGroupByGroupId(String groupId) throws BusinessServiceException;
	
	//SL-21465
	public EstimateVO getServiceOrderEstimationDetails(String soID,int vendorId,int resourceId) throws BusinessServiceException;
	public EstimateVO getServiceOrderEstimationDetails(String soID) throws BusinessServiceException;
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForVendor(String soID,int vendorId,int ResourceId) throws BusinessServiceException;
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForBuyer(String soID) throws BusinessServiceException;
	public List<OrderGroupVO> getOrderGroupsByAddress(String buyerId, String address, Integer status, Integer substatus) throws BusinessServiceException;
	public List<OrderGroupVO> getOrderGroupsByCustomerName(String buyerId, String name, Integer status, Integer substatus) throws BusinessServiceException;
	public List<OrderGroupVO> getOrderGroupsByPhone(String buyerId, String phone, Integer status, Integer substatus) throws BusinessServiceException;
	public List<OrderGroupVO> getOrderGroupsBySOId(String buyerId, String soId, Integer status, Integer substatus) throws BusinessServiceException;
	public List<OrderGroupVO> getOrderGroupsByZip(String buyerId, String zip, Integer status, Integer substatus) throws BusinessServiceException;
	
	
	/**
	 * Returns all order groups for given buyer; each grouped order containing child orders in it
	 * 
	 * @param buyerId
	 * @return List<OrderGroupVO> each VO containing child order ServiceOrderSearchResultsVO
	 * @throws BusinessServiceException
	 */
	public List<OrderGroupVO> getOrderGroups(String buyerId) throws BusinessServiceException;
	
	/**
	 * This method finds all the service order in the specified group
	 * 
	 * @param groupId
	 * @return List of ServiceOrderSearchResultsVO
	 * @throws BusinessServiceException
	 */
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForGroup(String groupId)throws BusinessServiceException;
	
	/**
	 * This method finds all the service order in the specified original group
	 * 
	 * @param groupId
	 * @return List of ServiceOrderSearchResultsVO
	 * @throws BusinessServiceException
	 */
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForOrigGroup(
			String origGroupId) throws BusinessServiceException;
			
	/**
	 * This method gets basic info of service orders in a group
	 * @param groupId
	 * @return List of ServiceOrderSearchResultsVO
	 * @throws BusinessServiceException
	 */
	public List<ServiceOrderSearchResultsVO> getServiceOrdersVOForGroup(String groupId) throws BusinessServiceException;
	/**
	 * This method finds all the service order in the specified group
	 * 
	 * @param groupId
	 * @return List of ServiceOrderSearchResultsVO
	 * @throws BusinessServiceException
	 */
	//public List<ServiceOrder> getServiceOrderVOsForGroup(String groupId)throws BusinessServiceException;
	
	/**
	 * Updates all the orders of a group with new group ID
	 * 
	 * @param oldGroupId
	 * @param newGroupId
	 * @throws DataServiceException
	 */
	public void updateGroupParentId(String oldGroupId, String newGroupId)throws BusinessServiceException;

	public ProcessResponse processAcceptGroupOrder(String groupId,Integer resourceId, Integer companyId, Integer termAndCond,
			boolean validate, SecurityContext securityContext);
	
	
	/**
	 * Gets the proportionate labor and parts spend limits for the new group labor and parts spend limits
	 * 
	 * @param groupId
	 * @param newGroupSpendLimitLabor
	 * @param newGroupSpendLimitParts
	 * @return A hashtable containing proportionate labor and parts spend limits
	 * @throws DataServiceException
	 */
	public Hashtable<String, Double> getProportionateLaborAndParts(String groupId, Double newGroupSpendLimitLabor, Double newGroupSpendLimitParts)throws DataServiceException;
	
	public  boolean isRoutedResource(Integer resourceId, List<RoutedProvider> routedAL);

	public void sendallProviderResponseExceptAccepted(String groupId,SecurityContext securityContext);
	
	public ProcessResponse processUngroupOrderGrp(String groupId)throws BusinessServiceException; 
	
	public  void updateSoGroupStatus(String groupId, Integer resourceId,
			int groupStatus, int providerResponse, boolean validate) throws DataServiceException;
	
	public  void populateSOGroupPrice(String groupId) throws BusinessServiceException;
	
	
	/**
	 *  Processes a condition offer for a grouped order.
	 * 
	 * @param groupId
	 * @param resourceId
	 * @param vendorOrBuyerId
	 * @param conditionalDate1
	 * @param conditionalDate2
	 * @param conditionalStartTime
	 * @param conditionalEndTime
	 * @param conditionalExpirationDate
	 * @param conditionalPrice
	 * @param securityContext
	 * @return ProcessResponse
	 * @throws BusinessServiceException
	 */
	//@Transactional (propagation=Propagation.REQUIRES_NEW, isolation=Isolation.DEFAULT, rollbackFor=BusinessServiceException.class)
	public ProcessResponse processCreateConditionalOfferForGroup(String groupId,
			Integer resourceId, Integer vendorOrBuyerId,
			Timestamp conditionalDate1, Timestamp conditionalDate2,
			String conditionalStartTime, String conditionalEndTime,
			Timestamp conditionalExpirationDate, Double conditionalPrice,List<Integer> selectedCounterOfferReasonsList,
			SecurityContext securityContext) throws BusinessServiceException;
	
	/**
	 * get provider responses
	 * @param groupId
	 * @return
	 */
	public List<RoutedProvider> getRoutedProviderResponse(String groupId);
	
	/**
	 * Accepts the conditional offer for the group
	 * 
	 * @param groupId
	 * @param resourceId
	 * @param vendorId
	 * @param resReasonId
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 * @param conditionalPrice
	 * @param buyerId
	 * @param securityContext
	 * @return ProcessResponse
	 * @throws BusinessServiceException
	 */
	//@Transactional (propagation=Propagation.REQUIRES_NEW, isolation=Isolation.DEFAULT, rollbackFor=BusinessServiceException.class)
	public ProcessResponse acceptConditionalOfferForGroup(String groupId,
			Integer resourceId, Integer vendorId, Integer resReasonId,
			Date startDate, Date endDate, String startTime, String endTime,
			Double conditionalPrice, Integer buyerId, SecurityContext securityContext)throws BusinessServiceException;

	/*
	 * get Primary category desc for given Id
	 */
	public String getMainCategoryDesc(Integer primarySkillCatId);  
	
	
	public Map<String, Object> getGroupOrderServiceDateTime(String groupId) throws BusinessServiceException;
	
	public ProcessResponse updateGroupServiceDate(String groupId, Timestamp serviceDate1, Timestamp serviceDate2, String startTime, String endTime, SecurityContext securityContext) throws BusinessServiceException;
	
	
	public void updateServiceContact(String soId, Contact contact);
	
	/**
	 * AOp placeholder to send emial fo rproviders once group Order is accepted
	 * @param groupId
	 * @param securityContext
	 */
	public void sendallProviderResponseExceptAcceptedForGroup(String groupId,
			SecurityContext securityContext);
	
	public String getFirstSoIdForGroup(String groupId) throws BusinessServiceException;
	
	public ProcessResponse withdrawGroupedConditionalAcceptance(String groupId,Integer resourceID, Integer providerRespId, SecurityContext securityContext) throws BusinessServiceException;
	
	/**
	 * Retrieves the custom refs-unit no & sales check number associated with the buyer
	 * @param buyerId
	 * @return buyerRefs
	 */
	public List<BuyerReferenceVO> getCustomReferenceForOGMSearch(Integer buyerId) throws BusinessServiceException;
	
	/**
	 * Added new function to un-group service order. It 
	 * @param onlyChildSO
	 * @param groupId
	 * @param groupSize
	 */
	public void unGroupServiceOrder(ServiceOrderSearchResultsVO onlyChildSO, String groupId, int groupSize) throws BusinessServiceException;
		
}
