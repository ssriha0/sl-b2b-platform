package com.newco.marketplace.web.delegates;

import java.util.List;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.ordergroup.OrderGroupDTO;

public interface IOrderGroupDelegate {

	public List<OrderGroupDTO> getOrderGroupsByAddress(String buyerId, String address, Integer status, Integer substatus) throws DataServiceException;
	public List<OrderGroupDTO> getOrderGroupsByCustomerName(String buyerId, String name, Integer status, Integer substatus) throws DataServiceException;
	public List<OrderGroupDTO> getOrderGroupsByPhone(String buyerId, String phone, Integer status, Integer substatus) throws DataServiceException;
	public List<OrderGroupDTO> getOrderGroupsBySOId(String buyerId, String soId, Integer status, Integer substatus) throws DataServiceException;
	public List<OrderGroupDTO> getOrderGroupsByZip(String buyerId, String zip, Integer status, Integer substatus) throws DataServiceException;

	/**
	 * Creates new group, add children corresponding to given soIds; populate soGroupPrice, removes existing providers of these children
	 * 
	 * @param buyerId
	 * @param soIdList
	 * @param securityContext
	 * @return
	 * @throws DelegateException
	 */
	public OrderGroupVO addServiceOrdersToNewGroup(Integer buyerId, List<String> soIdList, SecurityContext securityContext) throws DelegateException;
	
	/**
	 * Invokes appropriate OrderGroupBO and RouteOrderGroupBO methods for adding children to existing group and re-pricing and un-routing children orders.
	 * @param orderGroupId
	 * @param soIdList
	 * @param buyerId
	 * @param securityContext
	 * @return true if a posted group was un-routed successfully, false otherwise
	 * @throws DelegateException
	 */
	public boolean addServiceOrdersToExistingGroup(String orderGroupId, List<String> soIdList, Integer buyerId, SecurityContext securityContext) throws DelegateException;
	
	/**
	 * This method delegates to OrderGroupBO to apply trip charge and update prices for child/group level
	 * 
	 * @param grpOrdersList
	 * @throws DelegateException
	 */
	public void priceOrderGroup(List<ServiceOrderSearchResultsVO> grpOrdersList) throws DelegateException;
	
	public void ungroupOrderGroup(String groupId, SecurityContext securityContext) throws DelegateException;
	
	public List<ServiceOrderSearchResultsVO> getChildServiceOrders(String groupID);
	
	public void updateServiceOrderLocationForList(List<String> soIdList, SOWContactLocationDTO sowContact, SecurityContext securityContext);	
	
	public void updateGroupServiceDate(String groupId, String serviceDate1, String serviceDate2, String startTime, String endTime, SecurityContext securityContext);
	
	public void routeGroupToSelectedProviders(List<ServiceOrderSearchResultsVO> grpOrdersList ,List<Integer> orderGrpProvidersId, String groupId, Integer buyerId, SecurityContext securityContext) throws DelegateException;
	
	public OrderGroupVO getOrderGroupByGroupId(String groupId);
	
	/**
	 * <p>This method does following:
	 * <ol>
	 * 	<li>Proportionally distributes the grouped price changes into children prices in SO_HDR</li>
	 * 	<li>Updates original price for children in SO_PRICE</li>
	 * 	<li>Updates group original and final price in SO_GROUP_PRICE</li>
	 * </ol>
	 * 
	 * @param soParentGroupId
	 * @param currentSpendLimitLabor
	 * @param currentSpendLimitParts
	 * @param totalSpendLimitLabor
	 * @param totalSpendlimitParts
	 * @param increasedSpendLimitComment
	 * @param buyerId
	 * @param validate
	 * @param securityContext
	 */
	public void updateGroupSpendLimit(String soParentGroupId,
			Double currentSpendLimitLabor, Double currentSpendLimitParts,
			Double totalSpendLimitLabor, Double totalSpendlimitParts,
			String increasedSpendLimitComment, int buyerId, boolean validate,
			SecurityContext securityContext);
	/**
	 * Retrieves the custom reference fields associated with the buyer, 
	 * to be displayed in Order Group Manager search criteria
	 * @param buyerId
	 * @return buyerRefs
	 */
	public List<BuyerReferenceVO> getCustomReferenceForOGMSearch(Integer buyerId) throws DelegateException;
	
}
