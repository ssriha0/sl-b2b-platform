/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs.GetTabsResponse;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.sears.os.service.ServiceConstants;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * This class would act as a Servicer class for Get Order Management Tabs
 * 
 * @author Infosys 
 * @version 1.0
 */
public class GetOrderManagementTabsService extends SOBaseService {
	private static final Logger LOGGER = Logger.getLogger(GetOrderManagementTabsService.class);
	private OrderManagementService managementService;
	private OrderManagementMapper omMapper;

	public GetOrderManagementTabsService() {
		super(null,
				PublicAPIConstant.GET_OM_TABS_REQUEST_XSD,
				PublicAPIConstant.TAB_REFRESH_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.REFRESH_RESPONSE_SCHEMALOCATION,
				null, GetTabsResponse.class);
	}
	
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		LOGGER.info("Inside GetOrderManagementTabsService.execute()");
		SecurityContext securityContext = null;
		GetTabsResponse getTabsResponse = new GetTabsResponse();
		String firmId = (String) apiVO
				.getProviderId();
		Integer resourceId = apiVO.getProviderResourceId();
		boolean viewOrderPricing = Boolean.FALSE;
		if (null != firmId && null != resourceId) {
			try {
				securityContext = getSecurityContextForVendor(Integer.valueOf(resourceId));
				String roleType = securityContext.getRole();
				Map<String, UserActivityVO> activities = securityContext.getRoleActivityIdList();
				if(roleType.equalsIgnoreCase(OrderConstants.PROVIDER) && activities != null && activities.containsKey(OrderConstants.ACTIVITY_ID_PROVIDER_ORDER_PRICING_VIEW.toString())){
					viewOrderPricing = true;
				}
			} catch (NumberFormatException nme) {
				LOGGER.error(nme);
			} 
			//List<String> tabList = managementService.getTablist(resourceId);
			List<String> tabList = getTablist(resourceId,viewOrderPricing);
			LinkedHashMap<String, Integer> tabsWithCount = null;
			try {
				tabsWithCount = (LinkedHashMap<String, Integer>) managementService.getCountOfTabs(tabList, Integer.valueOf(firmId),viewOrderPricing);
				LOGGER.info("Tab Count Map :"+tabsWithCount.size());
			} catch (NumberFormatException e) {
				LOGGER.error(e);
				return createErrorResponseForGetTabCount(e);
			} catch (DataServiceException e) {
				LOGGER.error(e);
				return createErrorResponseForGetTabCount(e);
			}
			getTabsResponse = omMapper.mapGetTabsResponse(tabsWithCount);
		}
		LOGGER.info("Leaving GetOrderManagementTabsService.execute()");
		return getTabsResponse;
	}
	
	/**
	 * Creates Error response for Get Tab Count API. This is called went wrong
	 * @param Exception
	 * @return IAPIResponse {@link GetTabsResponse}
	 * */
	private IAPIResponse createErrorResponseForGetTabCount(Exception error) {
		IAPIResponse getTabCountResponse = new GetTabsResponse();
		Results results = Results.getError(error.getMessage(),ServiceConstants.USER_ERROR_RC);
		getTabCountResponse.setResults(results);
		return getTabCountResponse;
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
	
	/**
	 * Returns the list of Tabs that is displayed in Order Management main tab for a Provider.
	 * */
	//TODO : Same method is duplicated. Remove this one if we have to fetch the list from DB.
	//Otherwise use this one.
	private List<String> getTablist(Integer resourceId, boolean viewOrderPricing) {
		//boolean hasPermission = Boolean.FALSE;
		List<String> tabList = new  ArrayList<String>(Arrays.asList(OrderConstants.INBOX_TAB, OrderConstants.SCHEDULE_TAB,OrderConstants.ASSIGN_PROVIDER_TAB,OrderConstants.MANAGE_ROUTE_TAB, OrderConstants.CONFIRM_APPT_WDW_TAB, OrderConstants.PRINT_PAPERWORK_TAB, OrderConstants.CURRENT_ORDERS_TAB,OrderConstants.RESOLVE_PROBLEM_TAB,OrderConstants.CANCELLATIONS_TAB, OrderConstants.AWAITING_PAYMENT_TAB));
		try{
			//hasPermission = managementService.hasViewOrderPricing(resourceId);
			if(viewOrderPricing){
				tabList.add( 1, OrderConstants.RESPOND_TAB);
				tabList.add( Integer.valueOf(8), OrderConstants.JOB_DONE_TAB);
			}
			// R12_0 adding new tab 
			tabList.add( Integer.valueOf(9), OrderConstants.REVISIT_NEEDED_TAB);

		}catch (Exception e) {
			LOGGER.error(e);
		}
		return tabList;
	}
}