package com.newco.marketplace.web.delegates;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.web.utils.OFUtils;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.DateDisplayUtil;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWPhoneDTO;
import com.newco.marketplace.web.dto.ordergroup.OrderGroupDTO;
import com.newco.marketplace.web.utils.ObjectMapper;

public class OrderGroupDelegate implements IOrderGroupDelegate
{

	private IOrderGroupBO orderGroupBO;
	private IServiceOrderBO serviceOrderBO;
	private IRouteOrderGroupBO routeOrderGroupBO;
	private Logger logger = Logger.getLogger(OrderGroupDelegate.class);
    private OFHelper ofHelper = new OFHelper();

	public List<OrderGroupDTO> getOrderGroupsByAddress(String buyerId, String address,
			Integer status, Integer substatus) throws DataServiceException
	{

		List<OrderGroupVO> list = null;
		try
		{
			list = orderGroupBO.getOrderGroupsByAddress(buyerId, address, status, substatus);
		}
		catch (BusinessServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ObjectMapper.convertOrderGroupList(list);
	}

	public List<OrderGroupDTO> getOrderGroupsByCustomerName(String buyerId, String name,
			Integer status, Integer substatus) throws DataServiceException
	{
		List<OrderGroupVO> list = null;
		try
		{
			list = orderGroupBO.getOrderGroupsByCustomerName(buyerId, name, status, substatus);
		}
		catch (BusinessServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ObjectMapper.convertOrderGroupList(list);
	}

	public List<OrderGroupDTO> getOrderGroupsByPhone(String buyerId, String phone, Integer status,
			Integer substatus) throws DataServiceException
	{
		List<OrderGroupVO> list = null;
		try
		{
			list = orderGroupBO.getOrderGroupsByPhone(buyerId, phone, status, substatus);
		}
		catch (BusinessServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ObjectMapper.convertOrderGroupList(list);
	}

	public List<OrderGroupDTO> getOrderGroupsBySOId(String buyerId, String soId, Integer status,
			Integer substatus) throws DataServiceException
	{
		List<OrderGroupVO> list = null;
		try
		{
			list = orderGroupBO.getOrderGroupsBySOId(buyerId, soId, status, substatus);
		}
		catch (BusinessServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ObjectMapper.convertOrderGroupList(list);
	}

	public List<OrderGroupDTO> getOrderGroupsByZip(String buyerId, String zip, Integer status,
			Integer substatus) throws DataServiceException
	{
		List<OrderGroupVO> list = null;
		try
		{
			list = orderGroupBO.getOrderGroupsByZip(buyerId, zip, status, substatus);
		}
		catch (BusinessServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ObjectMapper.convertOrderGroupList(list);
	}

    private boolean allOrdersNew(List<String> soIdList){
        for(String soId : soIdList){
            if(!ofHelper.isNewSo(soId)) return false;
        }
        return true;
    }

    private boolean allOrdersOld(List<String> soIdList){
        for(String soId : soIdList){
            if(ofHelper.isNewSo(soId)) return false;
        }
        return true;
    }

	public OrderGroupVO addServiceOrdersToNewGroup(Integer buyerId, List<String> soIdList, SecurityContext securityContext) throws DelegateException {
		OrderGroupVO groupVO = new OrderGroupVO();
		boolean postedGroupUnrouted = false;
		String groupId = null;

        if(allOrdersNew(soIdList)){
            OrderFulfillmentRequest request = OFUtils.createAddOrdersToGroupRequest(soIdList, securityContext);
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess("-", SignalType.GROUP_ORDERS, request);
            if(response.isError()){
                throw new DelegateException(response.getErrorMessage());
            }
            groupVO.setGroupId(response.getGroupId());
            //call the unpost for the orders that are posted
            response = ofHelper.runOrderFulfillmentGroupProcess(response.getGroupId(), SignalType.UNPOST_GROUP, null, OFUtils.createOFIdentityFromSecurityContext(securityContext));
            if(response.isSignalAvailable() && response.isError()){
                throw new DelegateException(response.getErrorMessage());
            }
            if(response.isSignalAvailable()){
                groupVO.setWfStateId(OrderConstants.ROUTED_STATUS);
            }
            return groupVO;   
        } else if(allOrdersOld(soIdList)) {

		try {
			boolean firstOrder = true;
			for(String soID : soIdList) {
				if (firstOrder) {
					ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soID);
					groupId = orderGroupBO.createOrderGroup(serviceOrder);
					groupVO.setGroupId(groupId);
					firstOrder = false;
				}
                    orderGroupBO.addToOrderGroup(soID, groupId);
			}
			orderGroupBO.populateSOGroupPrice(groupId);

			// Un-route all the child orders from routed providers, in posted status
			postedGroupUnrouted = routeOrderGroupBO.unRouteOrdersInPostedGroup(groupId, buyerId, securityContext);
			if (postedGroupUnrouted) {
				groupVO.setWfStateId(OrderConstants.ROUTED_STATUS);
			}
		} catch (BusinessServiceException bsEx) {
			throw new DelegateException(bsEx);
		} catch (com.newco.marketplace.exception.core.BusinessServiceException coreBSEx) {
			throw new DelegateException(coreBSEx);
		}
		return groupVO;
        } else {
            throw new DelegateException("Please make sure that all the orders are either created using New OF system or Old system");
	}

	}
	
	public boolean addServiceOrdersToExistingGroup(String orderGroupId, List<String> soIdList, Integer buyerId, SecurityContext securityContext) throws DelegateException {
		
		boolean postedGroupUnrouted = false;
		if(orderGroupId == null || soIdList == null || soIdList.size() == 0) {
			return postedGroupUnrouted;
		}

		if (ofHelper.isNewGroup(orderGroupId) && allOrdersNew(soIdList)){
            OrderFulfillmentRequest request = OFUtils.createAddOrdersToGroupRequest(soIdList, securityContext);
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(orderGroupId, SignalType.GROUP_ORDERS, request);
            if(response.isError()){
                throw new DelegateException(response.getErrorMessage());
            }            
            //call the unpost for the orders that are posted
            response = ofHelper.runOrderFulfillmentGroupProcess(response.getGroupId(), SignalType.UNPOST_GROUP, null, OFUtils.createOFIdentityFromSecurityContext(securityContext));
            if(response.isSignalAvailable() && response.isError()){
                throw new DelegateException(response.getErrorMessage());
            }
            return response.isSignalAvailable();
        } else if(!ofHelper.isNewGroup(orderGroupId) && allOrdersOld(soIdList)) {
		try {

			for(String idStr : soIdList) {
				orderGroupBO.addToOrderGroup(idStr, orderGroupId);
			}

			orderGroupBO.populateSOGroupPrice(orderGroupId);

			// Un-route all the child orders from routed providers, in posted status
			postedGroupUnrouted = routeOrderGroupBO.unRouteOrdersInPostedGroup(orderGroupId, buyerId, securityContext);

		} catch (BusinessServiceException bsEx) {
			throw new DelegateException(bsEx);
		}
		return postedGroupUnrouted;
        } else {
            throw new DelegateException("Please make sure that all the orders are either created using New OF system or Old system");
	}
	}
	
	public void priceOrderGroup(List<ServiceOrderSearchResultsVO> serviceOrders)
			throws DelegateException {
		
		if (serviceOrders == null || serviceOrders.isEmpty()) {
			return;
		}

		try {
			// Update discounted if posted orders are grouped
			orderGroupBO.priceOrderGroup(serviceOrders);
		} catch (BusinessServiceException bsEx) {
			throw new DelegateException(bsEx);
		}
	}
	
	public void ungroupOrderGroup(String groupId, SecurityContext securityContext) throws DelegateException {
		
		if(StringUtils.isBlank(groupId)) {
			return;
		}

        if(ofHelper.isNewGroup(groupId)){
            //do processing for
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.UN_GROUP_ORDERS, null, OFUtils.createOFIdentityFromSecurityContext(securityContext));
            if(response.isError()){
                throw new DelegateException(response.getErrorMessage());
            }
            return;
        }

		try  {
			orderGroupBO.processUngroupOrderGrp(groupId);
		} catch (BusinessServiceException bsEx) {
			throw new DelegateException(bsEx);
		}
		
	}
	
	public List<ServiceOrderSearchResultsVO> getChildServiceOrders(String groupID)
	{
		try
		{
			return orderGroupBO.getServiceOrdersForGroup(groupID);
		}
		catch (BusinessServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void updateServiceOrderLocationForList(List<String> soIdList, SOWContactLocationDTO sowContact, SecurityContext securityContext)
	{
		if(sowContact == null)
			return;
		
		
		ServiceOrder so=null, updatedServiceOrder=null;
		Contact contact = null;		
		for(String soId : soIdList)
		{
			try
			{
				so = serviceOrderBO.getServiceOrder(soId);
				updatedServiceOrder = serviceOrderBO.getServiceOrder(soId);
				contact = so.getServiceContact();
			}
			catch (com.newco.marketplace.exception.core.BusinessServiceException e)
			{
				e.printStackTrace();
				return;
			}
			if(so != null && so.getServiceLocation() != null)
			{
				SoLocation location = so.getServiceLocation();
				location.setAptNo(sowContact.getAptNo());
				location.setCity(sowContact.getCity());
				location.setState(sowContact.getState());
				location.setZip(sowContact.getZip());
				location.setZip4(sowContact.getZip4());
				location.setStreet1(sowContact.getStreetName1());
				location.setStreet2(sowContact.getStreetName2());

				updatedServiceOrder.setServiceLocation(location);
				
				contact.setEmail(sowContact.getEmail());
				contact.setBusinessName(sowContact.getBusinessName());
				contact.setStreet_1(sowContact.getStreetName1());
				contact.setStreet_2(sowContact.getStreetName2());
				contact.setCity(sowContact.getCity());
				contact.setPhones(convertPhoneList(sowContact.getPhones(), soId, sowContact.getContactId()));
				
				updatedServiceOrder.setServiceContact(contact);
				
				
				serviceOrderBO.updateServiceContact(updatedServiceOrder, so, securityContext);
				
				serviceOrderBO.updateServiceLocation(soId, location);
			}
		}
	}
	
	
	private List<PhoneVO> convertPhoneList(List<SOWPhoneDTO> dtoList, String soId, Integer contactId)
	{
		List<PhoneVO> voList = new ArrayList<PhoneVO>();
		if(dtoList == null || dtoList.size() == 0)
			return voList;
		
		PhoneVO vo=null;
		for(SOWPhoneDTO dto : dtoList)
		{
			vo = new PhoneVO();
			vo.setPhoneNo(dto.getPhone());
			vo.setPhoneExt(dto.getExt());
			vo.setPhoneType(dto.getPhoneType());
			vo.setSoId(soId);
			vo.setSoContactId(contactId);
			vo.setClassId(dto.getPhoneClassId());
			voList.add(vo);
		}
		
		
		return voList;
	}


	/**
	 * @return the serviceOrderBO
	 */
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	/**
	 * @param serviceOrderBO the serviceOrderBO to set
	 */
	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
	
	public void updateGroupServiceDate(String groupId, String date1, String date2, String startTime, String endTime, SecurityContext securityContext)
	{
		if(groupId == null)
			return;
		
		if(securityContext == null)
			return;

		// Convert String dates to timestamps
		Timestamp serviceDate1 = null;
		Timestamp serviceDate2 = null;
		serviceDate1 = DateDisplayUtil.convertDateStringToTimestamp("yyy-MM-dd", date1);
		serviceDate2 = DateDisplayUtil.convertDateStringToTimestamp("yyy-MM-dd", date2);
		
		try
		{
			orderGroupBO.updateGroupServiceDate(groupId, serviceDate1, serviceDate2, startTime, endTime, securityContext);
		}
		catch (BusinessServiceException e)
		{
			e.printStackTrace();
		}
	}

	public void routeGroupToSelectedProviders(List<ServiceOrderSearchResultsVO> grpOrdersList ,List<Integer> orderGrpProvidersId, String groupId, Integer buyerId,
			SecurityContext securityContext) throws DelegateException
	{
		if(routeOrderGroupBO == null)
			return;
		
		try{
			
			routeOrderGroupBO.routeGroupToSelectedProviders(grpOrdersList, orderGrpProvidersId, groupId, buyerId, securityContext);
		}catch(BusinessServiceException e){
			String message = "error occured in RouteGroupToSelectedProviders " + e.getMessage();
			logger.error(message);
			throw new DelegateException(message);
			
		}
		
		
	}
	
	public OrderGroupVO getOrderGroupByGroupId(String groupId)
	{
		try
		{
			return orderGroupBO.getOrderGroupByGroupId(groupId);
		}
		catch (BusinessServiceException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IOrderGroupDelegate#updateGroupSpendLimit(java.lang.String, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.String, int, boolean, com.newco.marketplace.auth.SecurityContext)
	 */
	public void updateGroupSpendLimit(String soParentGroupId,
			Double currentSpendLimitLabor, Double currentSpendLimitParts,
			Double totalSpendLimitLabor, Double totalSpendlimitParts,
			String increasedSpendLimitComment, int buyerId, boolean validate,
			SecurityContext securityContext)
	{
		try
		{
			orderGroupBO.increaseSpendLimit(soParentGroupId, totalSpendLimitLabor,
							totalSpendlimitParts, increasedSpendLimitComment, buyerId, validate, securityContext);
		}
		catch (BusinessServiceException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves the cutom refs-unit no & sales check number associated with the buyer
	 * @param buyerId
	 * @return buyerRefs
	 */
	public List<BuyerReferenceVO> getCustomReferenceForOGMSearch(Integer buyerId) throws DelegateException{
		List<BuyerReferenceVO> buyerRefs = null;
		try {			
			buyerRefs = orderGroupBO.getCustomReferenceForOGMSearch(buyerId);	
		} catch (BusinessServiceException e) {
			throw new DelegateException("Error while getting custom refs for OGM Search",e);
		}
		return buyerRefs;
	}
	
	public IRouteOrderGroupBO getRouteOrderGroupBO()
	{
		return routeOrderGroupBO;
	}

	public void setRouteOrderGroupBO(IRouteOrderGroupBO routeOrderGroupBO)
	{
		this.routeOrderGroupBO = routeOrderGroupBO;
	}

	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}

	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}

    public void setOfHelper(OFHelper ofHelper) {
        this.ofHelper = ofHelper;
    }
}
