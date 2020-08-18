package com.newco.marketplace.business.iBusiness.orderGroup;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IRouteOrderGroupBO {
	
	/**
	 * route list of orders with orderType as Grouped or Orphan
	 * @param grpOrdersList
	 * @param tierId
	 * @return the number of providers the group routed to
	 * @throws BusinessServiceException
	 */
	public Integer processRouteOrderGroup(List<ServiceOrderSearchResultsVO> grpOrdersList,Integer tierId, SecurityContext securityContext) throws BusinessServiceException ;
	
	/**
	 * Un-routes all the children orders of given group from providers those children are routed to.
	 * 
	 * @param groupId
	 * @param buyerId
	 * @param securityContext
	 * @return true if a posted group was un-routed successfully, false otherwise
	 * @throws BusinessServiceException
	 */
	public boolean unRouteOrdersInPostedGroup(String groupId, Integer buyerId, SecurityContext securityContext) throws BusinessServiceException;
	
	/**
	 * get Providers for order Group, providers whose skills match all the orders in group
	 * @param providersList
	 * @return
	 */
	public List<Integer> getProviderForOrderGrp(List<List<Integer>> providersList);
	
	/**
	 * route single order which is not grouped
	 * @param orphanResultVO
	 * @param tierId
	 * @return the number of providers the order routed to
	 * @throws BusinessServiceException 
	 */
	public Integer routeIndividualOrder(ServiceOrderSearchResultsVO orphanResultVO, Integer tierId) throws BusinessServiceException;
	
	/**
	 * get providers list for service order
	 * @param so
	 * @param tierId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public List<Integer> getProvidersListForSO(ServiceOrder so, Integer tierId) throws BusinessServiceException;
	
	/**
	 * Route order to select providers -- this is called when posted from front end
	 * @param grpOrdersList
	 * @param orderGrpProvidersId
	 * @param groupId
	 * @param buyerId
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException 
	 */
	public ProcessResponse routeGroupToSelectedProviders(List<ServiceOrderSearchResultsVO> grpOrdersList ,List<Integer> orderGrpProvidersId, String groupId, Integer buyerId,
			SecurityContext securityContext) throws BusinessServiceException;
	
	/**
	 * Get the list of resource Ids from so_group_routed_providers
	 * @param groupId
	 * @return List of resource Ids
	 * @throws BusinessServiceException
	 */
	public List<Integer> getRoutedResourceIds(String groupId) throws BusinessServiceException;
	
	/**
	 * 
	 * @param so
	 * @return
	 */
	public BuyerSOTemplateDTO getTemplateForOrder(ServiceOrder so);
	
	/**
	 * validate if all orders has same spn & update if they are not same & only if isnewSpn of template is true
	 * @param postedParentGroupId
	 * @return
	 */
	public boolean validateAndUpdateSpnForGroupedOrders(String postedParentGroupId);
	
	/**
	 * get routed date of group for given resourceId
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Date getRoutedDateForResourceId(String groupId, Integer resourceId)throws BusinessServiceException;
	
	/**
	 * get the remaining time left for provider to wait to Accept So
	 * @param groupId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public int getTheRemainingTimeToAcceptGrpOrder(String groupId, Integer resourceId) throws BusinessServiceException;

	/**
	 * get the remaining time left for firm to wait to Accept So
	 * @param groupId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public int getTheRemainingTimeToAcceptGrpOrderFirm(String groupId,
			Integer vendorId) throws BusinessServiceException;

}
