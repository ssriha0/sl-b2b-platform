package com.newco.marketplace.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderWfStatesCountsVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.constants.TabConstants;
import com.newco.marketplace.web.dto.ServiceOrderMonitorTabTitleCount;

/*
 * Maintenance History: See bottom of file
 */
public class TabsMapper {

	private static final Logger logger = Logger.getLogger(TabsMapper.class);
	
	//BAD - do not use a static list of dynamic data, not thread safe.
	//Left here to remind everyone.
	//private static ArrayList<ServiceOrderMonitorTabTitleCount> soTabs = null;

	/**
	 * Takes an array of VOs and converts them into light weight DTOs
	 * @param sowfcvo
	 * @param roleName
	 * @return ArrayList of ServiceOrderMonitorTabTitleCount objects
	 */
	public static ArrayList<ServiceOrderMonitorTabTitleCount> convertVOToDTO(
			ArrayList<ServiceOrderWfStatesCountsVO> sowfcvo, AjaxCacheVO ajaxCacheVo) {
		List<ServiceOrderMonitorTabTitleCount> soTabs = null;
		String roleName=ajaxCacheVo.getRoleType();
		if (roleName.equalsIgnoreCase(OrderConstants.BUYER)) {
			soTabs = doBuyerTabs(sowfcvo);
		}
		else if (roleName.equalsIgnoreCase(OrderConstants.SIMPLE_BUYER)) {
			soTabs = doSimpleBuyerTabs(sowfcvo);
		} 
		else if (roleName.equalsIgnoreCase(OrderConstants.PROVIDER_ROLE)) {
			soTabs = doProviderTabs(sowfcvo,ajaxCacheVo);
		}

		return (ArrayList<ServiceOrderMonitorTabTitleCount>) soTabs;

	}

	private static List<ServiceOrderMonitorTabTitleCount> doBuyerTabs(ArrayList<ServiceOrderWfStatesCountsVO> list) {
		List<ServiceOrderMonitorTabTitleCount> soTabs = new ArrayList<ServiceOrderMonitorTabTitleCount>();
		
		ServiceOrderMonitorTabTitleCount todayTab = new ServiceOrderMonitorTabTitleCount();
		todayTab.setTabTitle(TabConstants.BUYER_TODAY);
		ServiceOrderMonitorTabTitleCount draftTab = new ServiceOrderMonitorTabTitleCount();
		draftTab.setTabTitle(TabConstants.BUYER_DRAFT);
		ServiceOrderMonitorTabTitleCount postedTab = new ServiceOrderMonitorTabTitleCount();
		postedTab.setTabTitle(TabConstants.BUYER_POSTED);
		ServiceOrderMonitorTabTitleCount acceptedTab = new ServiceOrderMonitorTabTitleCount();
		acceptedTab.setTabTitle(TabConstants.BUYER_ACCEPTED);
		ServiceOrderMonitorTabTitleCount problemTab = new ServiceOrderMonitorTabTitleCount();
		problemTab.setTabTitle(TabConstants.BUYER_PROBLEM);
		ServiceOrderMonitorTabTitleCount inactiveTab = new ServiceOrderMonitorTabTitleCount();
		inactiveTab.setTabTitle(TabConstants.BUYER_INACTIVE);
		ServiceOrderMonitorTabTitleCount searchTab = new ServiceOrderMonitorTabTitleCount();
		searchTab.setTabTitle(TabConstants.BUYER_SEARCH);
		try {
			for (ServiceOrderWfStatesCountsVO soStateCount : list) {
				if (soStateCount.getTabType().equalsIgnoreCase(
						OrderConstants.TAB_ACTIVE)
						|| soStateCount.getTabType().equalsIgnoreCase(
								OrderConstants.TAB_EXPIRED)
						|| soStateCount.getTabType().equalsIgnoreCase(
								OrderConstants.TAB_COMPLETED)
						|| OrderConstants.TAB_PENDING_CANCEL
								.equalsIgnoreCase(soStateCount.getTabType())) {

					todayTab.setTabCount(soStateCount.getSoCount());
				} else if (soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_DRAFT)) {
					draftTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_ROUTED)) {
					postedTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType().equalsIgnoreCase(
						OrderConstants.TAB_ACCEPTED)) {
					acceptedTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType()
						.equalsIgnoreCase(OrderConstants.TAB_PROBLEM)) {
					problemTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType().equalsIgnoreCase(
						OrderConstants.TAB_INACTIVE)
						|| soStateCount.getTabType().equalsIgnoreCase(
								OrderConstants.TAB_CANCELLED)
						|| soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_CLOSED)
						|| soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_VOIDED)
						|| soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_DELETED)) {
					inactiveTab.setTabCount(soStateCount.getSoCount());
				}
			}
			soTabs.add(todayTab);
			soTabs.add(draftTab);
			soTabs.add(postedTab);
			soTabs.add(acceptedTab);
			soTabs.add(problemTab);
			soTabs.add(inactiveTab);
			soTabs.add(searchTab);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return soTabs;
	}

	private static List<ServiceOrderMonitorTabTitleCount> doProviderTabs(ArrayList<ServiceOrderWfStatesCountsVO> list,AjaxCacheVO ajaxCacheVo) {
		List<ServiceOrderMonitorTabTitleCount> soTabs = new ArrayList<ServiceOrderMonitorTabTitleCount>();		

		ServiceOrderMonitorTabTitleCount todayTab = new ServiceOrderMonitorTabTitleCount();
		//SL-15642 Start Setting tabs based on the View Order Pricing permission
		ServiceOrderMonitorTabTitleCount postedTab = new ServiceOrderMonitorTabTitleCount();
		if(ajaxCacheVo.isViewOrderPricing())
		{
		todayTab.setTabTitle(TabConstants.PROVIDER_TODAY);
		postedTab.setTabTitle(TabConstants.PROVIDER_RECEIVED);
		}
		//SL-15642 End Setting tabs based on the View Order Pricing permission
		ServiceOrderMonitorTabTitleCount bidRequestsTab = null;
		ServiceOrderMonitorTabTitleCount bulletinBoardTab = null;
		if(OrderConstants.SHOW_B2C_PROVIDER_SCREENS)
		{
			bidRequestsTab = new ServiceOrderMonitorTabTitleCount();
			bulletinBoardTab = new ServiceOrderMonitorTabTitleCount();
			//SL-15642 Start Setting tabs based on the View Order Pricing permission
			if(ajaxCacheVo.isViewOrderPricing())
			{
			bidRequestsTab.setTabTitle(TabConstants.PROVIDER_BID_REQUESTS);
			bulletinBoardTab.setTabTitle(TabConstants.PROVIDER_BULLETIN_BOARD);
			}
			//SL-15642 End Setting tabs based on the View Order Pricing permission
		}
		
		ServiceOrderMonitorTabTitleCount acceptedTab = new ServiceOrderMonitorTabTitleCount();
		acceptedTab.setTabTitle(TabConstants.PROVIDER_ACCEPTED);
		ServiceOrderMonitorTabTitleCount problemTab = new ServiceOrderMonitorTabTitleCount();
		problemTab.setTabTitle(TabConstants.PROVIDER_PROBLEM);
		ServiceOrderMonitorTabTitleCount inactiveTab = new ServiceOrderMonitorTabTitleCount();
		inactiveTab.setTabTitle(TabConstants.PROVIDER_INACTIVE);
		ServiceOrderMonitorTabTitleCount searchTab = new ServiceOrderMonitorTabTitleCount();
		searchTab.setTabTitle(TabConstants.PROVIDER_SEARCH);
		try {
			for(ServiceOrderWfStatesCountsVO soStateCount : list) {


				if (soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_ACTIVE)
						|| soStateCount.getTabType()
								.equalsIgnoreCase(OrderConstants.TAB_EXPIRED)
						|| soStateCount.getTabType().equalsIgnoreCase(
								OrderConstants.TAB_COMPLETED)
						|| soStateCount.getTabType()
								.equalsIgnoreCase(OrderConstants.TAB_PENDING_CANCEL)) {

					todayTab.setTabCount(soStateCount.getSoCount());
				} else if (soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_RECEIVED)) {
					postedTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType().equalsIgnoreCase(
						OrderConstants.TAB_ACCEPTED)) {
					acceptedTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType()
						.equalsIgnoreCase(OrderConstants.TAB_PROBLEM)) {
					problemTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType().equalsIgnoreCase(
						OrderConstants.TAB_INACTIVE)
						|| soStateCount.getTabType().equalsIgnoreCase(
								OrderConstants.TAB_CANCELLED)
						|| soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_CLOSED)
						|| soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_VOIDED)
						|| soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_DELETED)) {
					inactiveTab.setTabCount(soStateCount.getSoCount());
				} else if (soStateCount.getTabType().equalsIgnoreCase(
						OrderConstants.TAB_BID_REQUESTS)) {
					bidRequestsTab.setTabCount(soStateCount.getSoCount());

				}else if (soStateCount.getTabType().equalsIgnoreCase(
						OrderConstants.TAB_BULLETIN_BOARD)) {
					// Munish Update the call here.
					bulletinBoardTab.setTabCount(soStateCount.getSoCount());

				}
				
				
			}
			soTabs.add(todayTab);
			soTabs.add(postedTab);
			
			if(OrderConstants.SHOW_B2C_PROVIDER_SCREENS)
			{
				soTabs.add(bidRequestsTab);
				soTabs.add(bulletinBoardTab);
				
			}
			soTabs.add(acceptedTab);
			soTabs.add(problemTab);
			soTabs.add(inactiveTab);
			soTabs.add(searchTab);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return soTabs;
	}
	
	private static List<ServiceOrderMonitorTabTitleCount> doSimpleBuyerTabs(ArrayList<ServiceOrderWfStatesCountsVO> list) {
		List<ServiceOrderMonitorTabTitleCount> soTabs = new ArrayList<ServiceOrderMonitorTabTitleCount>();
		
		ServiceOrderMonitorTabTitleCount draftTab = new ServiceOrderMonitorTabTitleCount();
		draftTab.setTabTitle(TabConstants.SIMPLE_BUYER_DRAFT);
		ServiceOrderMonitorTabTitleCount todayTab = new ServiceOrderMonitorTabTitleCount();
		todayTab.setTabTitle(TabConstants.SIMPLE_BUYER_TODAY);
		ServiceOrderMonitorTabTitleCount postedTab = new ServiceOrderMonitorTabTitleCount();
		postedTab.setTabTitle(TabConstants.SIMPLE_BUYER_POSTED);
		ServiceOrderMonitorTabTitleCount acceptedTab = new ServiceOrderMonitorTabTitleCount();
		acceptedTab.setTabTitle(TabConstants.SIMPLE_BUYER_ACCEPTED);
		ServiceOrderMonitorTabTitleCount problemTab = new ServiceOrderMonitorTabTitleCount();
		problemTab.setTabTitle(TabConstants.SIMPLE_BUYER_PROBLEM);
		ServiceOrderMonitorTabTitleCount inactiveTab = new ServiceOrderMonitorTabTitleCount();
		inactiveTab.setTabTitle(TabConstants.SIMPLE_BUYER_INACTIVE);
		ServiceOrderMonitorTabTitleCount searchTab = new ServiceOrderMonitorTabTitleCount();
		searchTab.setTabTitle(TabConstants.SIMPLE_BUYER_SEARCH);
		try {
			for (ServiceOrderWfStatesCountsVO soStateCount : list) {


				if (soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_ACTIVE)
						|| soStateCount.getTabType()
								.equalsIgnoreCase(OrderConstants.TAB_EXPIRED)
						|| soStateCount.getTabType().equalsIgnoreCase(
								OrderConstants.TAB_COMPLETED)) {

					todayTab.setTabCount(soStateCount.getSoCount());
				} else if (soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_DRAFT)) {
					draftTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_ROUTED)) {
					postedTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType().equalsIgnoreCase(
						OrderConstants.TAB_ACCEPTED)) {
					acceptedTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType()
						.equalsIgnoreCase(OrderConstants.TAB_PROBLEM)) {
					problemTab.setTabCount(soStateCount.getSoCount());

				} else if (soStateCount.getTabType().equalsIgnoreCase(
						OrderConstants.TAB_INACTIVE)
						|| soStateCount.getTabType().equalsIgnoreCase(
								OrderConstants.TAB_CANCELLED)
						|| soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_CLOSED)
						|| soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_VOIDED)
						|| soStateCount.getTabType().equalsIgnoreCase(OrderConstants.TAB_DELETED)) {
					inactiveTab.setTabCount(soStateCount.getSoCount());
				}
			}
			soTabs.add(draftTab);
			soTabs.add(todayTab);
			soTabs.add(postedTab);
			soTabs.add(acceptedTab);
			soTabs.add(problemTab);
			soTabs.add(inactiveTab);
			soTabs.add(searchTab);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return soTabs;
	}
	
	public static ArrayList<Integer> getTabStatesBuyer(String tab) {
		ArrayList<Integer> stateIntArray = new ArrayList();
		if (tab.equalsIgnoreCase(TabConstants.BUYER_TODAY)) {
			stateIntArray.add(TabConstants.ACTIVE_STATE);
			stateIntArray.add(TabConstants.EXPIRED_STATE);
			stateIntArray.add(TabConstants.COMPLETED_STATE);
			stateIntArray.add(TabConstants.PENDING_CANCEL_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.BUYER_DRAFT)) {
			stateIntArray.add(TabConstants.DRAFT_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.BUYER_POSTED)) {
			stateIntArray.add(TabConstants.ROUTED_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.BUYER_ACCEPTED)) {
			stateIntArray.add(TabConstants.PROBLEM_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.BUYER_INACTIVE)) {
			stateIntArray.add(TabConstants.CANCELLED_STATE);
			stateIntArray.add(TabConstants.CLOSED_STATE);
			stateIntArray.add(TabConstants.VOIDED_STATE);
			stateIntArray.add(TabConstants.DELETED_STATE);
		}
		return stateIntArray;
	}
	
	public static ArrayList<Integer> getTabStatesProvider(String tab) {
		ArrayList<Integer> stateIntArray = new ArrayList();
		if (tab.equalsIgnoreCase(TabConstants.PROVIDER_TODAY)) {
			stateIntArray.add(TabConstants.ACTIVE_STATE);
			stateIntArray.add(TabConstants.EXPIRED_STATE);
			stateIntArray.add(TabConstants.COMPLETED_STATE);
			stateIntArray.add(TabConstants.PENDING_CANCEL_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.PROVIDER_RECEIVED)) {
			stateIntArray.add(TabConstants.ROUTED_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.PROVIDER_ACCEPTED)) {
			stateIntArray.add(TabConstants.PROBLEM_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.PROVIDER_INACTIVE)) {
			stateIntArray.add(TabConstants.CANCELLED_STATE);
			stateIntArray.add(TabConstants.CLOSED_STATE);
			stateIntArray.add(TabConstants.VOIDED_STATE);
			stateIntArray.add(TabConstants.DELETED_STATE);
		}
		return stateIntArray;
	}

	public static ArrayList<Integer> getTabStatesSimpleBuyer(String tab) {
		ArrayList<Integer> stateIntArray = new ArrayList();
		if (tab.equalsIgnoreCase(TabConstants.BUYER_TODAY)) {
			stateIntArray.add(TabConstants.ACTIVE_STATE);
			stateIntArray.add(TabConstants.EXPIRED_STATE);
			stateIntArray.add(TabConstants.COMPLETED_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.BUYER_DRAFT)) {
			stateIntArray.add(TabConstants.DRAFT_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.BUYER_POSTED)) {
			stateIntArray.add(TabConstants.ROUTED_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.BUYER_ACCEPTED)) {
			stateIntArray.add(TabConstants.PROBLEM_STATE);
		}
		if (tab.equalsIgnoreCase(TabConstants.BUYER_INACTIVE)) {
			stateIntArray.add(TabConstants.CANCELLED_STATE);
			stateIntArray.add(TabConstants.CLOSED_STATE);
			stateIntArray.add(TabConstants.VOIDED_STATE);
			stateIntArray.add(TabConstants.DELETED_STATE);
		}
		return stateIntArray;
	}

	//for sl-15642(for setting filters)
	
	public static ArrayList<String> getFilters(String tab) {
		ArrayList<String> stateIntArray = new ArrayList<String>();
		if (tab.equalsIgnoreCase(TabConstants.INBOX)) {
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.PROVIDERS);
			stateIntArray.add(TabConstants.STATUS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);
		}
		else if (tab.equalsIgnoreCase(TabConstants.RESPOND)) {
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.SUB_STATUS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);
		}

		else if (tab.equalsIgnoreCase(TabConstants.SCHEDULE)) {
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.PROVIDERS);
			stateIntArray.add(TabConstants.SCHEDULE_STATUS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);	
				
		}
		else if (tab.equalsIgnoreCase(TabConstants.ASSIGN_PROVIDER)) {
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.ROUTED_TO);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);		
		}
		else if (tab.equalsIgnoreCase(TabConstants.MANAGE_ROUTE)) {
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.PROVIDERS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);
		}
		else if (tab.equalsIgnoreCase(TabConstants.CONFIRM_APPT_WDW)) {
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.PROVIDERS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);
		}
		else if (tab.equalsIgnoreCase(TabConstants.JOB_DONE)){
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.PROVIDERS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);
			stateIntArray.add(TabConstants.JOB_DONE_SUBSTATUS);
		}
		
		//R12.0 sprint3 : adding new tab revisit needed
		else if (tab.equalsIgnoreCase(TabConstants.REVISIT_NEEDED)){
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.PROVIDERS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);
			//R12.0 Sprint4 adding substatus filter
			stateIntArray.add(TabConstants.REVISIT_SUBSTATUS);
		}		
		else if (tab.equalsIgnoreCase(TabConstants.CURRENT_ORDERS)){
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.PROVIDERS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);
			stateIntArray.add(TabConstants.CURRENT_ORDERS_SUBSTATUS);
		}
		//R12.0 sprint3 : adding new filter 'substatus' for cancellations tab
		else if(TabConstants.CANCELLATIONS.equalsIgnoreCase(tab)){
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.PROVIDERS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);
			stateIntArray.add(TabConstants.CANCELLATIONS_SUBSTATUS);
		}
		else{
			stateIntArray.add(TabConstants.MARKETS);
			stateIntArray.add(TabConstants.PROVIDERS);
			stateIntArray.add(TabConstants.APPOINTMENT_DATE);
		}


		return stateIntArray;
	}
}
/* MAINTENANCE HISTORY
 * $Log: TabsMapper.java,v $
 * Revision 1.11  2008/05/21 23:32:52  akashya
 * I21 Merged
 *
 * Revision 1.10.6.2  2008/05/17 20:37:42  gjacks8
 * added simple buyer role
 *
 * Revision 1.10.6.1  2008/05/14 14:30:50  gjacks8
 * added logger and simple buyer logic for monitor
 *
 * Revision 1.10  2008/04/26 01:13:45  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.8.30.1  2008/04/23 11:41:32  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.9  2008/04/23 05:19:56  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.8  2007/10/19 23:13:45  akashya
 * Removed string literals and replaced with constants
 *
 * Revision 1.7  2007/10/19 20:48:53  akashya
 * Added Maintenance History
 *
*/