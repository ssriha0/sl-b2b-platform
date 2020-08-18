package com.newco.marketplace.web.action.OrderManagement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.ReasonCodeVO;
import com.newco.marketplace.api.beans.so.ReasonCodes;
import com.newco.marketplace.api.beans.so.ReassignSORequest;
import com.newco.marketplace.api.beans.so.ReassignSOResponse;
import com.newco.marketplace.api.beans.so.Reschedule;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.beans.so.addNote.v1_1.NoteType;
import com.newco.marketplace.api.beans.so.addNote.v1_1.SOAddNoteRequest;
import com.newco.marketplace.api.beans.so.reschedule.SOProviderRescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SOProviderRescheduleResponse;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleInfo;
import com.newco.marketplace.api.beans.so.retrieve.v1_2.RetrieveServiceOrder;
import com.newco.marketplace.api.beans.so.retrieve.v1_2.SOGetResponse;
import com.newco.marketplace.api.beans.so.retrieve.v1_2.ServiceOrders;
import com.newco.marketplace.api.common.Identification;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.acceptSO.SOAcceptFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.acceptSO.SOAcceptResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.assignProvider.SOAssignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.assignProvider.SOAssignProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editAppTime.SOEditAppointmentTimeRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editAppTime.SOEditAppointmentTimeResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editSlNotes.SOEditSOLocationNotesRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editSlNotes.SOEditSOLocationNotesResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProviders;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.SOGetEligibleProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CancellationsSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.CurrentOrdersSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSORequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FetchSOResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.FilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.JobDoneSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.MarketFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ProviderFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RevisitSubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RoutedProvidersVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.ScheduleStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.StatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.SubStatusFilterVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs.GetTabsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs.Tab;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.GetReasonCodesResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.ReasonCode;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.ReasonCodeBean;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO.RejectSORequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO.RejectSOResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO.Resource;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateFlag.SOPriorityResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleDetailsRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleDetailsResponse;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.EstimateHistoryVO;
import com.newco.marketplace.dto.vo.EstimateTaskVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.util.TimeChangeUtil;
import com.newco.marketplace.util.TimestampUtils;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.TimezoneMapper;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.ordermanagement.so.ChildBidOrder;
import com.newco.marketplace.vo.ordermanagement.so.ChildBidOrderListType;
import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.constants.TabConstants;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.OrderManagementTabDTO;
import com.newco.marketplace.web.dto.OrderManagmentTabTitleCount;
import com.newco.marketplace.web.dto.RescheduleDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.ordermanagement.PrecallScheduleUpdateDTO;
import com.newco.marketplace.web.dto.ordermanagement.ServiceOrderListDTO;
import com.newco.marketplace.web.ordermanagement.api.services.OrderManagementRestClient;
import com.newco.marketplace.web.utils.TabsMapper;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.ordermanagement.services.OrderManagementService;

/**
 * This class is created for order management SL-15642
 * 
 */
public class OrderManagementControllerAction extends SLBaseAction implements
Preparable, ModelDriven<OrderManagementTabDTO> {
	private static final Logger LOGGER = Logger
			.getLogger(OrderManagementControllerAction.class);
	private static final long serialVersionUID = 1L;
	private static final String XSD = ".xsd";
	private static final String GROUPED_SO_IND = "1";
	private static final String GROUP_IND_PARAM = "groupInd";
	private static final String TAB_COUNT = "tabTitleCounts";
	private static final String ACTIVE_TAB = "omDisplayTab";
	private static final String JSON = "json";
	private static final String SO_ID = "soId"; 
	private List<SOWError> omErrors = new ArrayList<SOWError>();
	private OrderManagementTabDTO omTabDTO = new OrderManagementTabDTO();
	private OrderManagementRestClient restClient;
	private ILookupDelegate luDelegate = null;

	private static final String ASSIGN_PROVIDER_REQUEST_SCHEMA = "http://www.servicelive.com/namespaces/assignProviderRequest.xsd";
	private static final String UPDATE_TIME_REQUEST_SCHEMA = "http://www.servicelive.com/namespaces/soEditAppointmentRequest.xsd";
	private static final String ASSIGNED_ID = "assignProvId";
	private static final String ASSIGNED_NAME = "assignProvName";
	private static final String REASSIGN = "reassign";
	private static final String ERROR_ELIGIBLE_PROVIDER = "eligibleProvErr";
	private static final String SUCCESS_MSG = "omSuccessMsg";
	private static final String TIME_PARAMETER = "time";
	private static final String DATE_PARAMETER = "date";
	private static final String VIEW_ORDER_PRICE = "viewOrderPricingPermission";
	private static final String SO_COUNT = "count";
	private static final String SELECT_ITEM_DEFAULT = "-Select-";
	private static final String DATE_FORMAT_IN_DB = "yyyy-MM-dd";
	private static final String TIME_FORMAT_IN_DB = "HH:mm:ss";
	private static final String DATE_FORMAT_DISPLAYED_FOR_ACCEPT = "MM/dd/yy";
	private static final String DATE_FORMAT_APPENDED_WITH_TIME = "MM/dd/yy hh:mm:ss";			
	private static final String DATE_FORMAT_DISPLAYED = "MM/dd/yyyy";
	private static final String TIME_FORMAT_DISPLAYED = "hh:mm aa";
	private static final String TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR = "yyyy-MM-dd HH:mm:ss";
	private static final String TIME_STAMP_FORMAT_IN_DB_TWELVE_HOUR = "yyyy-MM-dd hh:mm:ss";
	private static final String TIME_STAMP_DISPLAYED = "MM/dd/yy hh:mm aa";
	private static final String TAKE_ACTION = "Take Action";
	private static final String REJECT_ORDER = "Reject Order";
	private static final String ACCEPT_BUTTON = "Accept Button";
	private static final String COUNTER_OFFER = "Counter offer";
	private static final String WITHDRAW_OFFER = "Withdraw offer";
	private static final String PRE_CALL_ACTION = "Pre-Call";
	private static final String ADD_NOTE = "Add Note";
	private static final String EDIT_LOCATION_NOTE = "Edit Service Location Notes";
	private static final String REQUEST_RESCHEDULE = "Request a Reschedule";
	private static final String ASSIGN_PROVIDER="Assign Provider";
	private static final String VIEW_ORDER = "View Order";
	private static final String UPDATE_TIME = "Update Service Window";
	private static final String CONFIRM_APPT = "Confirm Appt Window";
	private static final String RE_ASSIGN_PROVIDER = "Re-assign the provider";
	private static final String TIME_ON_SITE = "Time on Site";
	private static final String REPORT_A_PROBLEM = "Report a Problem";
	private static final String COMPLETE_FOR_PAYMENT ="Complete for Payment";
	private static final String ISSUE_RESOLUTION = "Issue Resolution";
	private static final String PRE_CALL_COMPLETED_REASON = "7";
	private static final String TIME_WINDOW_CALL_COMPLETED_REASON = "8";
	private static final String ACCEPT_RESCHEDULE = "acceptReschedule";
	private static final String CANCEL_RESCHEDULE = "cancelReschedule";
	private static final String REJECT_RESCHEDULE = "rejectReschedule";
	private static final String METHOD = "method";
	private static final String ASSIGNEE = "assignee";
	private static final String TYPE_PROVIDER = "typeProvider";
	private static final Integer ROLE_ID=1;

	//SL-21645
	private IMobileGenericBO mobileGenericBO;
	private EstimateVO estimateVO = new EstimateVO();
	private String fromServiceOD;
	private IRelayServiceNotification relayNotificationService;
	private IMobileSOActionsBO mobileSOActionsBO;
	private IBuyerFeatureSetBO buyerFeatureSetBO;



	private enum tabs {
		RESPOND(TabConstants.RESPOND), INBOX(TabConstants.INBOX), SCHEDULE(
				TabConstants.SCHEDULE), CONFIRMAPPTWINDOW(
						TabConstants.CONFIRM_APPT_WDW), PRINTPAPERWORK(
								TabConstants.PRINT_PAPERWORK), CURRENTORDERS(
										TabConstants.CURRENT_ORDERS), AWAITINGPAYMENT(
												TabConstants.AWAITING_PAYMENT), ASSIGNPROVIDER(
														TabConstants.ASSIGN_PROVIDER), MANAGEROUTE(
																TabConstants.MANAGE_ROUTE), JOBDONE(TabConstants.JOB_DONE), REVISITNEEDED(TabConstants.REVISIT_NEEDED),CANCELLATIONS(
																		TabConstants.CANCELLATIONS), RESOLVEPROBLEM(
																				TabConstants.RESOLVE_PROBLEM);

		private final String name;

		private tabs(String s) {
			name = s;
		}

		public String toString() {
			return name;
		}
	}

	private String omDisplayTab;
	private boolean filterFlag;
	private boolean initialLoadInd = false;
	private IApplicationProperties applicationProperties;

	private Integer count;
	private OrderManagementService managementService;

	private ISODetailsDelegate soDetailsManager;

	private INotificationService notificationService;




	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public ISODetailsDelegate getSoDetailsManager() {
		return soDetailsManager;
	}

	public void setSoDetailsManager(ISODetailsDelegate soDetailsManager) {
		this.soDetailsManager = soDetailsManager;
	}

	public OrderManagementControllerAction(ISOMonitorDelegate delegate) {
		this.soMonitorDelegate = delegate;
	}

	public void prepare() throws Exception {
		LOGGER.info("Inside OrderManagementControllerAction.prepare()");
		createCommonServiceOrderCriteria();
		getSession().removeAttribute("omApiErrors");
		LOGGER.info("Leaving OrderManagementControllerAction.prepare()");
	}

	private void setTimeInterval() {
		ServletContext application = getSession().getServletContext();
		List<LookupVO> timeIntervalList = (List<LookupVO>) application.getAttribute("time_intervals");
		List<LookupVO> updatedTimeInterval = new ArrayList<LookupVO>();
		if(null != timeIntervalList && !timeIntervalList.isEmpty() && 33 <= timeIntervalList.size()){
			updatedTimeInterval.addAll(timeIntervalList.subList(32, timeIntervalList.size()));
			updatedTimeInterval.addAll(timeIntervalList.subList(0,32));
		}
		getSession().setAttribute("time_intervals_updated", updatedTimeInterval);
	}

	public String execute() throws Exception {
		LOGGER.info("Inside OrderManagementControllerAction.execute()");
		this.initialLoadInd = true;
		LOGGER.info("Before OrderManagementControllerAction.loadOrderManagementTabInformation()");
		loadOrderManagementTabInformation();
		LOGGER.info("After OrderManagementControllerAction.loadOrderManagementTabInformation()");
		getSession().setAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT,
				Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT);
		LOGGER.info("Before OrderManagementControllerAction.setTimeInterval()");
		setTimeInterval();
		LOGGER.info("After OrderManagementControllerAction.setTimeInterval()");
		setAttribute("isOmPage", true);
		LOGGER.info("Leaving OrderManagementControllerAction.execute()");
		return SUCCESS;
	}

	private void loadOrderManagementTabInformation() {
		LOGGER.info("Inside OrderManagementControllerAction.loadOrderManagementTabInformation()");
		boolean viewOrderPricing = false;
		if (null != getSession().getAttribute("theOmtabList")
				|| null == _commonCriteria) {
			return;
		}
		AjaxCacheVO ajaxCacheVo = new AjaxCacheVO();
		String roleType = this._commonCriteria.getRoleType();

		if (NEWCO_ADMIN.equals(roleType)) {
			/* NewCo SL Admin Issue: When coming from the AdminSearchAction.navigateToProviderPage(), they are setting the
			 SecurityContext.roleId to the role that the "Search" is assuming...therefore,  we are picking the roleId off of the securitycontext role id
			 based on the type of user that is logged on. Refer to the
			 AdminSearchAction.navigateToProviderPage() code */
			Integer roleId = this._commonCriteria.getSecurityContext()
					.getRoleId();
			if (roleId != null && roleId.equals(PROVIDER_ROLEID)) {
				roleType = PROVIDER;
			}
		}
		getSession().setAttribute("roleType",
				this._commonCriteria.getSecurityContext().getRoleId());

		Integer vendorId = this._commonCriteria.getCompanyId();
		ajaxCacheVo.setCompanyId(vendorId);
		ajaxCacheVo.setRoleType(roleType);

		SecurityContext securityContext = this._commonCriteria
				.getSecurityContext();
		Map<String, UserActivityVO> activities = securityContext
				.getRoleActivityIdList();
		if (roleType.equalsIgnoreCase(PROVIDER)
				&& activities != null && activities.containsKey("59")) {
			viewOrderPricing = true;
			getSession().setAttribute(VIEW_ORDER_PRICE, true);
			ajaxCacheVo.setViewOrderPricing(viewOrderPricing);
			ajaxCacheVo.setVendBuyerResId(securityContext.getVendBuyerResId());
		} else {
			getSession().setAttribute(VIEW_ORDER_PRICE, false);
		}

		List<String> tabList = new ArrayList<String>();
		tabList.add(TabConstants.INBOX);
		if (viewOrderPricing) {
			tabList.add(TabConstants.RESPOND);
		}
		tabList.add(TabConstants.SCHEDULE);
		tabList.add(TabConstants.ASSIGN_PROVIDER);
		tabList.add(TabConstants.MANAGE_ROUTE);
		tabList.add(TabConstants.CONFIRM_APPT_WDW);
		tabList.add(TabConstants.PRINT_PAPERWORK);
		tabList.add(TabConstants.CURRENT_ORDERS);
		if (viewOrderPricing) {
			tabList.add(TabConstants.JOB_DONE);
		}
		// R12_0 for new tab sprint 3
		tabList.add(TabConstants.REVISIT_NEEDED);

		tabList.add(TabConstants.RESOLVE_PROBLEM);
		tabList.add(TabConstants.CANCELLATIONS);
		tabList.add(TabConstants.AWAITING_PAYMENT);

		// Set count of Service Orders in each Order Management Tabs
		getSession().setAttribute(TAB_COUNT, getSOCountForTabs());
		OrderManagmentTabTitleCount omTab = new OrderManagmentTabTitleCount();

		// From Other tabs - DashBoard, SOD, SOM..
		omDisplayTab = (String) getSession().getAttribute(ACTIVE_TAB);
		omDisplayTab = StringUtils.isBlank(omDisplayTab) ? TabConstants.INBOX : omDisplayTab;
		getSession().setAttribute(ACTIVE_TAB, omDisplayTab);
		if (getRequest().getParameter(SO_COUNT) == null) {
			count = (Integer) getSession().getAttribute(SO_COUNT);
		}
		String tabStatus = getRequest().getParameter("tabStatus");
		if (StringUtils.isNotBlank(tabStatus)) {
			getSession().setAttribute("tabStatus", tabStatus);
		}
		if (count == null) {
			if (getRequest().getParameter(SO_COUNT) != null) {
				count = Integer.parseInt(getRequest().getParameter(SO_COUNT));
			} else {
				count = 0;
			}
		}
		getSession().setAttribute(SO_COUNT, count);
		Integer cnt = (Integer) getSession().getAttribute(SO_COUNT);
		omTab.setTabCount(cnt);
		omTab.setTabTitle(omDisplayTab);

		ArrayList<String> filterList;
		filterList = TabsMapper.getFilters(omTab.getTabTitle());
		omTabDTO = createTabDTOWithStatusList(omTab, filterList);
		getSession().setAttribute("filterNames", omTabDTO.getFilterList());
		loadDataGrid(count, EMPTY_STR, SORT_ORDER_ASC, null);
		setTotalTabCount(omDisplayTab);
	}

	/**
	 * This method is used to load action list for SOs
	 * @param soList
	 * @param omDisplayTab
	 */
	private void loadActions(List<OMServiceOrder> soList, String omDisplayTab) {
		List<String> actions = new ArrayList<String>();

		tabs tabEnum = tabs.valueOf(omDisplayTab.toUpperCase().replace(SPACE,
				EMPTY_STR));
		switch (tabEnum) {
		case INBOX:
			loadActionsForInbox(actions, soList);
			break;
		case RESPOND:
			for (OMServiceOrder omServiceOrder : soList) {
				loadActionsForRespond(actions, omServiceOrder);
				omServiceOrder.setActions(actions);
				actions = new ArrayList<String>();
			}
			break;
		case SCHEDULE:
			loadActionsForSchedule(actions);
			setActionsToSoList(soList, actions);
			break;
		case ASSIGNPROVIDER:
			loadActionsForAssignProvider(actions);
			setActionsToSoList(soList, actions);
			break;
		case MANAGEROUTE:
			loadActionsForManageRoute(actions);
			setActionsToSoList(soList, actions);
			break;
		case CONFIRMAPPTWINDOW:
			loadActionsForConfirmApptWindow(actions);
			setActionsToSoList(soList, actions);
			break;
		case CURRENTORDERS:
			loadActionsForCurrentOrders(actions);
			setActionsToSoList(soList, actions);
			break;
		case JOBDONE:
			loadActionsForJobDone(actions);
			setActionsToSoList(soList, actions);
			break;
			//for R12_0
		case REVISITNEEDED:
			loadActionsForRevisitNeeded(actions);
			setActionsToSoList(soList, actions);
			break;
		case RESOLVEPROBLEM:
			loadActionsForResolveProblem(actions);
			setActionsToSoList(soList, actions);
			break;
		case CANCELLATIONS:
			loadActionsForCancel(actions);
			setActionsToSoList(soList, actions);
			break;
		case AWAITINGPAYMENT:
			loadActionsForAwaitingPayment(actions);
			setActionsToSoList(soList, actions);
			break;
		default:
			loadActionsForInbox(actions, soList);
			break;
		}
	}

	/**
	 * @param actions
	 * for R12_0 
	 * load actions for revisit needed
	 */
	private void loadActionsForRevisitNeeded(List<String> actions) {
		actions.add(VIEW_ORDER);
		actions.add(ADD_NOTE);
	}

	/**
	 * This method sets the action items to different SOs
	 * @param soList
	 * @param actions
	 * @return {@link Void}
	 */
	private void setActionsToSoList(List<OMServiceOrder> soList,
			List<String> actions) {
		for (OMServiceOrder omServiceOrder : soList) {
			omServiceOrder.setActions(actions);
		}
	}

	/**
	 * Sets action items for SOs in INBOX list
	 * @param actions
	 * @param soList
	 */
	private void loadActionsForInbox(List<String> actions,
			List<OMServiceOrder> soList) {
		try{
			for (OMServiceOrder omServiceOrder : soList) {
				String status = omServiceOrder.getSoStatusString();
				String subStatus = omServiceOrder.getSoSubStatusString();
				String scheduleStatus = omServiceOrder.getScheduleStatus();

				if (status != null && status.equals(TabConstants.PROVIDER_RECEIVED)) {
					loadActionsForRespond(actions, omServiceOrder);
				} else if (status != null
						&& status.equals(TabConstants.PROVIDER_ACCEPTED)
						&& (((scheduleStatus != null && scheduleStatus
						.equals("Schedule Needed")) || (scheduleStatus != null && scheduleStatus
						.equals("Pre-Call Attempted"))))) {
					loadActionsForSchedule(actions);
				} else if ((status != null
						&& (status.equals(TabConstants.PROVIDER_ACCEPTED)
								|| status.equals(TabConstants.PROVIDER_ACTIVE) || status
								.equals(TabConstants.PROVIDER_PROBLEM)) && omServiceOrder
								.getAcceptedResourceId() == null)) {
					loadActionsForAssignProvider(actions);
				} else if (status != null
						&& (status.equals(TabConstants.PROVIDER_ACCEPTED)
								|| status.equals(TabConstants.PROVIDER_ACTIVE) || status
								.equals(TabConstants.PROVIDER_PROBLEM))
								&& (scheduleStatus != null && !scheduleStatus
								.equals("Schedule Needed"))) {
					loadActionsForManageRoute(actions);
				} else if (status != null
						&& (status.equals(TabConstants.PROVIDER_ACCEPTED)
								|| status.equals(TabConstants.PROVIDER_ACTIVE) || status
								.equals(TabConstants.PROVIDER_PROBLEM))
								&& (scheduleStatus != null && !scheduleStatus
								.equals("Time window-Call Completed"))) {
					loadActionsForConfirmApptWindow(actions);
				} else if (status != null
						&& status.equals(TabConstants.PROVIDER_ACTIVE)
						&& (subStatus == null || (subStatus != null && !subStatus
						.equals("Job Done")))) {
					loadActionsForCurrentOrders(actions);
				} else if (status != null
						&& status.equals(TabConstants.PROVIDER_ACTIVE)
						&& subStatus != null && subStatus.equals("Job Done")) {
					loadActionsForJobDone(actions);
				} else if (status != null
						&& status.equals(TabConstants.PROVIDER_PROBLEM)) {
					loadActionsForResolveProblem(actions);
				} else if (status != null && status.equals(PENDING_CANCEL_STRING)) {
					loadActionsForCancel(actions);
				} else if (status != null && status.equals("Completed")) {
					loadActionsForAwaitingPayment(actions);
				}
				omServiceOrder.setActions(actions);
				actions = new ArrayList<String>();
			}
		}
		catch(Exception e){
			LOGGER.info(e.getMessage());

		}
	}

	/**
	 * Sets action items for SOs in Respond list
	 * @param actions
	 * @param so
	 */
	private void loadActionsForRespond(List<String> actions, OMServiceOrder so) {
		boolean isCounterOfferOff= false;
		try{
			List<RoutedProvidersVO> routedProviders = so.getRoutedProviders();
			int noOfRoutedProviders = routedProviders.size();
			int count = 0;
			for (RoutedProvidersVO prov : routedProviders) {
				if (null != prov.getRespId() && prov.getRespId().equals(2)) {
					count++;
				}
			}
			 isCounterOfferOff=buyerFeatureSetBO.validateFeature(Integer.parseInt(so.getBuyerID()), BuyerFeatureConstants.COUNTER_OFFER_OFF);
			String soAttribute = so.getSoAttribute();
			String priceModel = so.getPriceModel();
			if (priceModel != null && priceModel.equals("ZERO_PRICE_BID")) {
				actions.add(TAKE_ACTION);
				actions.add(REJECT_ORDER);
			} else if (soAttribute != null && soAttribute.equals("GENERAL")) {
				if (count != 0 && priceModel != null
						&& !priceModel.equals("ZERO_PRICE_BID")) {
					if (count < noOfRoutedProviders) {
						actions.add(ACCEPT_BUTTON);
						if (!isCounterOfferOff){
							actions.add(COUNTER_OFFER);
						}
						actions.add(WITHDRAW_OFFER);
					} else if (count == noOfRoutedProviders) {
						actions.add(WITHDRAW_OFFER);
					}
				}  else if(so.getIsEstimationRequest()){
					so.setSoAttribute("Estimation Request");
					if(so.getEstimationId()==null){
						actions.add("Add Estimate");
						
					}else{
						actions.add("View/Edit Estimate");
					}
					//added this code for estimation for the time being
				}else if (count == 0) {
					actions.add(ACCEPT_BUTTON);
					if (!isCounterOfferOff){
					actions.add(COUNTER_OFFER);
					}
					actions.add(REJECT_ORDER);
				}
			} /*else if(soAttribute != null && soAttribute.equals("Estimation Request")){
				actions.add("ADD ESTIMATE");
				//added this code for estimation for the time being
			}*/else {
				actions.add(ACCEPT_BUTTON);
				actions.add(REJECT_ORDER);
			}
		}
		catch(Exception e){
			LOGGER.info(e.getMessage());
		}
	}

	/**
	 * sets action items for SOs in Schedule list
	 * @param actions
	 */
	private void loadActionsForSchedule(List<String> actions) {
		actions.add(PRE_CALL_ACTION);
		actions.add(ADD_NOTE);
		actions.add(EDIT_LOCATION_NOTE);
		actions.add(REQUEST_RESCHEDULE);
		actions.add(UPDATE_TIME);
	}

	/**
	 * Sets action items for SOs in AssignProvider list
	 * @param actions
	 */
	private void loadActionsForAssignProvider(List<String> actions) {
		actions.add(ASSIGN_PROVIDER);
		actions.add(ADD_NOTE);
		actions.add(UPDATE_TIME);
		actions.add(REQUEST_RESCHEDULE);
	}

	/**
	 * Sets action items for SOs in ManageRoute list
	 * @param actions
	 */
	private void loadActionsForManageRoute(List<String> actions) {
		actions.add(VIEW_ORDER);
		actions.add(ADD_NOTE);
		actions.add(UPDATE_TIME);
		actions.add(REQUEST_RESCHEDULE);
	}

	/**
	 * Sets action items for SOs in ConfirmApptWindow list
	 * @param actions
	 */
	private void loadActionsForConfirmApptWindow(List<String> actions) {
		actions.add(CONFIRM_APPT);
		actions.add(ADD_NOTE);
		actions.add(UPDATE_TIME);
		actions.add(REQUEST_RESCHEDULE);
		actions.add(RE_ASSIGN_PROVIDER);
	}

	/**
	 *  Sets action items for SOs in CurrentOrders list
	 * @param actions
	 */
	private void loadActionsForCurrentOrders(List<String> actions) {
		actions.add(ADD_NOTE);
		actions.add(TIME_ON_SITE);
		actions.add(EDIT_LOCATION_NOTE);
		actions.add(REQUEST_RESCHEDULE);
		actions.add(REPORT_A_PROBLEM);
		actions.add(UPDATE_TIME);
	}

	/**
	 * Sets action items for SOs in JobDone list
	 * @param actions
	 */
	private void loadActionsForJobDone(List<String> actions) {
		actions.add(COMPLETE_FOR_PAYMENT);
		actions.add(ADD_NOTE);
	}

	/**
	 * Sets action items for SOs in ResolveProblem list
	 * @param actions
	 */
	private void loadActionsForResolveProblem(List<String> actions) {
		actions.add(ISSUE_RESOLUTION);
		actions.add(ADD_NOTE);
		actions.add(REQUEST_RESCHEDULE);
	}

	/**
	 * Sets action items for SOs in Cancel list
	 * 	 * @param actions
	 */
	private void loadActionsForCancel(List<String> actions) {
		actions.add(VIEW_ORDER);
		actions.add(ADD_NOTE);
	}

	/**
	 * sets action items for SOs in AwaitingPayment list
	 * @param actions
	 */
	private void loadActionsForAwaitingPayment(List<String> actions) {
		actions.add(VIEW_ORDER);
		actions.add(ADD_NOTE);
	}

	private OrderManagementTabDTO createTabDTOWithStatusList(
			OrderManagmentTabTitleCount titleAndCount,
			List<String> filterList) {
		OrderManagementTabDTO tabDTO = new OrderManagementTabDTO();
		tabDTO.setId(titleAndCount.getTabTitle());
		tabDTO.setTabTitle(titleAndCount.getTabTitle());
		tabDTO.setTabCount(titleAndCount.getTabCount());
		tabDTO.setFilterList(filterList);
		if (omDisplayTab != null
				&& tabDTO.getId().equalsIgnoreCase(omDisplayTab)) {
			tabDTO.setTabSelected(TRUE);
		} else {
			tabDTO.setTabSelected(FALSE);
		}
		return tabDTO;
	}

	/**
	 * this method is used to load data for assign provider pop up
	 */
	public String loadAssignProviderWidget() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String firmId = soContxt.getCompanyId().toString();
		String soId = getRequest().getParameter(SO_ID);
		String reassign = getRequest().getParameter(REASSIGN);
		setAttribute(REASSIGN, reassign);
		String groupInd = getRequest().getParameter(GROUP_IND_PARAM);
		String errMessage = EMPTY_STR;
		try {
			RetrieveServiceOrder retrieveServiceOrder = null;
			String responseFilterParam = "Schedule-Service%20Location-General";
			retrieveServiceOrder = getServiceOrder(soId, groupInd, firmId,
					responseFilterParam);
			if (null != retrieveServiceOrder) {
				setAttribute(SO_ID, soId);
				setAttribute("soTitle", retrieveServiceOrder.getSectionGeneral().getTitle());
				BigDecimal maxPrice = null;
				Pricing pricing = retrieveServiceOrder.getPricing();
				if (null != pricing) {
					maxPrice = BigDecimal.valueOf(Double.valueOf(pricing
							.getLaborSpendLimit()));
					maxPrice = maxPrice.add(BigDecimal.valueOf(Double
							.valueOf(pricing.getPartsSpendLimit())));
				}
				setAttribute("serviceTime",
						formatServiceTimeForAccept(retrieveServiceOrder
								.getSchedule()));
				//SL-18955 Full Address displayed for a received order in the "Accept Service Order" widget
				String acceptInd="0";
				//Passing the accept indicator to formatServiceLocation method
				setAttribute("serviceLocation",
						formatServiceLocation(retrieveServiceOrder
								.getServiceLocation(),acceptInd));
			} else {
				errMessage = "Invalid SO Id.";
				setAttribute(ERROR_ELIGIBLE_PROVIDER, errMessage);
				return SUCCESS;
			}
			errMessage = setEligibleProvidersInSession(firmId, soId, groupInd);
			Integer assignedResourceId = null;
			if (null != getAttribute(ASSIGNED_ID)) {
				assignedResourceId = Integer.valueOf((String) getAttribute(ASSIGNED_ID));
			}
			EligibleProvider acceptedResource = null;
			// Remove already assigned provider from Eligible provider list
			// while Re Assigning.
			if (TRUE.equals(reassign) && null != assignedResourceId) {
				List<EligibleProvider> assignProviderList = (List<EligibleProvider>) getSession().getAttribute("eligibleProviders");
				if (null != assignProviderList) {
					for (EligibleProvider eligibleProvider : assignProviderList) {
						if (eligibleProvider.getResourceId().intValue() == assignedResourceId.intValue()) {
							acceptedResource = eligibleProvider;
							setAttribute(ASSIGNED_NAME,
									eligibleProvider.getProviderFirstName()+ SPACE+ eligibleProvider.getProviderLastName());
							break;
						}
					}
					assignProviderList.remove(acceptedResource);
					getSession().setAttribute("eligibleProviders",
							assignProviderList);
				}
				else{
					SOWError error = new SOWError(null, PublicAPIConstant.API_TIME_OUT_ERROR, EMPTY_STR);
					if(!omErrors.contains(error)){
						omErrors.add(error);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return SUCCESS;
	}

	/**
	 * This method is used to load data in the Precall popup on click on Precall
	 * button
	 * 
	 */
	public String loadDataForPreCall() {

		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String providerId = soContxt.getCompanyId().toString();
		String soId = getRequest().getParameter(SO_ID);
		String source = getRequest().getParameter("source");
		String groupInd = getRequest().getParameter(GROUP_IND_PARAM);
		String vendorId = soContxt.getCompanyId().toString();
		List<EligibleProvider> assignProviderList = null;
		ReasonCodes rescheduleReasonCodes = null;
		ReasonCodes customerResponseCodes = null;
		ReasonCodes preCallReasonCodes = null;
		Reschedule reschedule=new Reschedule();

		List<ReasonCodeVO> rescheduleReasonCodesLst = null;
		List<ReasonCodeVO> customerResponseCodesLst = null;
		List<ReasonCodeVO> preCallReasonCodesLst = null;
		List<PreCallHistory> preCallHistoryLst = null;

		String sourceInd = EMPTY_STR;
		if (StringUtils.isBlank(groupInd)
				|| !GROUPED_SO_IND.equalsIgnoreCase(groupInd)) {
			groupInd = "0";
		}
		// Set the filter
		RetrieveServiceOrder retrieveServiceOrder = null;
		String responseFilterParam = "General-Schedule-Scope%20Of%20Work-Parts-Contacts-Service%20Location-Notes-Custom%20References-Fetch%20Reason%20Codes-Fetch%20PreCall%20Reason%20Codes";
		try {
			retrieveServiceOrder = getServiceOrder(soId, groupInd, vendorId,
					responseFilterParam);
			/*
			 * SOGetResponse soGetResponse =
			 * restClient.getResponseForGetInfoForCall(providerId,
			 * soId,groupInd,responseFilterParam); List<RetrieveServiceOrder>
			 * retrieveServiceOrderList = null; if(null != soGetResponse && null
			 * != soGetResponse.getServiceOrders()){ ServiceOrders serviceOrders
			 * = soGetResponse.getServiceOrders(); if(null != serviceOrders &&
			 * null!= serviceOrders.getServiceorders()){
			 * retrieveServiceOrderList = serviceOrders.getServiceorders(); } }
			 */
			if (null != retrieveServiceOrder) {
				// Service order details
				// Get reschedule reason codes
				if (null != retrieveServiceOrder.getReasonCodes()) {
					rescheduleReasonCodes = retrieveServiceOrder
							.getReasonCodes();
					if (null != rescheduleReasonCodes) {
						rescheduleReasonCodesLst = rescheduleReasonCodes
								.getReasonCode();
					}
				}
				if (null != retrieveServiceOrder.getCustomerResponseCodes()) {
					customerResponseCodes = retrieveServiceOrder
							.getCustomerResponseCodes();
					if (null != customerResponseCodes) {
						customerResponseCodesLst = customerResponseCodes
								.getReasonCode();
					}
				}
				if (null != retrieveServiceOrder.getPreCallReasonCodes()) {
					preCallReasonCodes = retrieveServiceOrder
							.getPreCallReasonCodes();
					if (null != preCallReasonCodes) {
						preCallReasonCodesLst = preCallReasonCodes
								.getReasonCode();
					}
				}
				if (null != retrieveServiceOrder.getSchedule()) {
					Schedule schedule = retrieveServiceOrder.getSchedule();
					reschedule=schedule.getReschedule();
					if (StringUtils.isNotBlank(schedule.getServiceDateTime1())) {
						schedule.setFormattedServiceDate1();
						schedule.setServiceTime1();
					}
					if (StringUtils.isNotBlank(schedule.getServiceDateTime2())) {

						schedule.setFormattedServiceDate2();
						schedule.setServiceTime2();
					}
					retrieveServiceOrder.setSchedule(schedule);
					if (schedule.getServiceDateTime1() != null) {
						Date now = new Date();
						DateFormat sdf1 = new SimpleDateFormat(DATE_FORMAT_IN_DB);
						String serviceDate1 = schedule.getServiceDateTime1();
						serviceDate1 = serviceDate1.replaceAll("T", SPACE);
						Date serviceDate = new Date();
						try {
							String nowDate = sdf1.format(now);
							serviceDate = sdf1.parse(serviceDate1);
							now = sdf1.parse(nowDate);
						} catch (ParseException e) {
							LOGGER.error("Error", e);
						}
						if ((serviceDate.after(now))
								|| (serviceDate.before(now))) {
							this.setAttribute("todayInd", false);
						} else {
							this.setAttribute("todayInd", true);
						}

					}
				}
				if ("PreCall".equals(source)) {
					sourceInd = "PRE_CALL";
				} else if ("ConfirmAppointment".equals(source)) {
					sourceInd = "CONFIRM_APPOINTMENT";
				}
				// Get providers eligible
				int resourceId = 0;
				int assignedResourceId = 0;
				SOWError error = null;
				SOGetEligibleProviderResponse providerResponse = restClient
						.getResponseForGetEligibleProviders(providerId, soId,
								groupInd);
				if (null != providerResponse
						&& null != providerResponse.getEligibleProviders()
						&& null != providerResponse.getEligibleProviders()
						.getEligibleProviderList()) {
					if (providerResponse.getEligibleProviders()
							.getEligibleProviderList().size() > 0) {
						assignProviderList = providerResponse
								.getEligibleProviders()
								.getEligibleProviderList();
						if (null != assignProviderList) {
							EligibleProvider acceptedResource = new EligibleProvider();
							for (EligibleProvider eligibleProvider : assignProviderList) {
								if (eligibleProvider != null
										&& eligibleProvider.getResourceId() != null) {
									resourceId = eligibleProvider
											.getResourceId().intValue();
									if (providerResponse
											.getAssignedResourceId() != null) {
										assignedResourceId = Integer
												.parseInt(providerResponse
														.getAssignedResourceId());
										if (resourceId == assignedResourceId) {
											acceptedResource = eligibleProvider;
											break;
										}
									}
								}
							}
							assignProviderList.remove(acceptedResource);
						}
						this.setAttribute("routedProviderList",
								assignProviderList);
						this.setAttribute("assignProviderRecourceId",
								providerResponse.getAssignedResourceId());
						this.setAttribute(ASSIGNED_NAME,
								providerResponse.getAssignedResource());
						if (providerResponse.getAssignedResource() != null) {
							this.setAttribute("assigned", true);
						}
					}

				}
				else if(null!=providerResponse && (null != providerResponse.getResults().getError())) {
					List<ErrorResult> errorResults = providerResponse
							.getResults().getError();
					if (null != errorResults) {
						for (ErrorResult result : errorResults) {
							LOGGER.info(result.getMessage());
							error = new SOWError(null, result.getMessage(), EMPTY_STR);
							if(!omErrors.contains(error)){
								omErrors.add(error);
							}
						}
					}
				}
				// Get precall history details
				PreCallHistoryResponse preCallHistoryResponse = restClient
						.getResponseForPrecallHistoryDetails(providerId, soId,
								sourceInd);
				if (null != preCallHistoryResponse
						&& null != preCallHistoryResponse
						.getPreCallHistoryDetails()) {
					PreCallHistoryDetails preCallHistoryDetails = preCallHistoryResponse
							.getPreCallHistoryDetails();
					if (null != preCallHistoryDetails
							&& null != preCallHistoryDetails.getHistoryList()
							&& preCallHistoryDetails.getHistoryList().size() > 0) {
						preCallHistoryLst = preCallHistoryDetails
								.getHistoryList();
						for (PreCallHistory callHistory : preCallHistoryLst) {
							if (StringUtils.isNotBlank(callHistory.getDate())) {
								callHistory.setFormattedDate();
								callHistory.setFormattedTime();
							}
						}
					}
				}
				else if(null!=preCallHistoryResponse && (null != preCallHistoryResponse.getResults().getError())) {
					List<ErrorResult> errorResults = preCallHistoryResponse
							.getResults().getError();
					if (null != errorResults) {
						for (ErrorResult result : errorResults) {
							LOGGER.info(result.getMessage());
							error = new SOWError(null, result.getMessage(), EMPTY_STR);
							if(!omErrors.contains(error)){
								omErrors.add(error);
							}
						}
					}
				}
			}

			/**SL 18896 , R8.2 START**/
			//Time intervals beyond (11:45PM - MAXTIME_WINDOW*4) should not be shown,
			//ie suppose if time interval is 8:00 PM and MAXTIME_WINDOW= 4,
			//then it is filtered out, max time interval shown here is 7:45 PM
			ServletContext application = getSession().getServletContext();
			List<LookupVO> updatedTimeInterval = (List<LookupVO>) application.getAttribute("time_intervals");
			List<LookupVO> newUpdatedTimeInterval = new ArrayList<LookupVO>();

			// the updatedTimeInterval will be 96(24*4)
			if(null != updatedTimeInterval && updatedTimeInterval.size() == 96){
				int MINTIME_WINDOW = 0;
				if(null != retrieveServiceOrder.getBuyer().getMinTimeWindow()){
					MINTIME_WINDOW = retrieveServiceOrder.getBuyer().getMinTimeWindow();
				}
				int newListCount = 96-MINTIME_WINDOW*4;

				if(null != updatedTimeInterval && !updatedTimeInterval.isEmpty() && 33 <= updatedTimeInterval.size()){

					//add Time intervals from 8:00 AM to Max Time
					newUpdatedTimeInterval.addAll(updatedTimeInterval.subList(32, newListCount));
					//add 12:00AM to 8:00 AM time intervals
					newUpdatedTimeInterval.addAll(updatedTimeInterval.subList(0,32));
				}

				getSession().setAttribute("time_intervals_updated_precall_new", newUpdatedTimeInterval);
			}
			/**SL 18896 , R8.2 END**/

			// Set the attributes
			this.setAttribute("reschedule", reschedule);
			this.setAttribute("precallDetails", retrieveServiceOrder);
			this.setAttribute("rescheduleReasonCodesLst",
					rescheduleReasonCodesLst);
			this.setAttribute("customerResponseCodesLst",
					customerResponseCodesLst);
			this.setAttribute("preCallReasonCodesLst", preCallReasonCodesLst);
			this.setAttribute("precallHistoryDetails", preCallHistoryLst);
			this.setAttribute("omApiErrors", omErrors);

		} catch (Exception e) {
			LOGGER.error(e);
		}
		return SUCCESS;

	}

	/**
	 * This method is used to save precall details
	 * 
	 * @param
	 * @return String.
	 * */
	public String savePrecallDetails() {
		String soId = null;
		SOWError error = null;
		OrderManagementTabDTO tabDTO = getModel();
		PrecallScheduleUpdateDTO precallScheduleUpdateDTO = null;
		RescheduleDTO rescheduleDTO = null;
		if (null != tabDTO) {
			precallScheduleUpdateDTO = tabDTO.getPrecallScheduleUpdateDTO();
			if (!StringUtils.isBlank(precallScheduleUpdateDTO.getSoId())) {
				soId = precallScheduleUpdateDTO.getSoId();
			}
			if (precallScheduleUpdateDTO.getTimeResponseWindow() == 3) {
				rescheduleDTO = tabDTO.getRescheduleDTO();
				rescheduleDTO.setSoId(soId);
				if (!validateRescheduleRequest(rescheduleDTO)) {
					return JSON;
				}
			}
		} else {
			return JSON;
		}

		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		// Set the Identification type
		Identification identification = new Identification();
		identification.setId(soContxt.getVendBuyerResId());
		identification.setType(Identification.ID_TYPE_PROVIDER_RESOURCE_ID);
		String resourceId = soContxt.getVendBuyerResId().toString();

		UpdateScheduleDetailsRequest updateScheduleDetailsRequest = new UpdateScheduleDetailsRequest();
		updateScheduleDetailsRequest.setIdentification(identification);
		updateScheduleDetailsRequest
		.setSchemaLocation(updateScheduleDetailsRequest.getNamespace()
				+ XSD);
		String providerId = soContxt.getCompanyId().toString();

		if ("PreCall".equals(precallScheduleUpdateDTO.getSource())) {
			updateScheduleDetailsRequest.setSource("PRE_CALL");
			// Get the provider Id
			if (null != precallScheduleUpdateDTO) {
				Integer resource;
				if (!StringUtils.isBlank(precallScheduleUpdateDTO
						.getAssignedResourceId())) {
					resource = Integer.parseInt(precallScheduleUpdateDTO
							.getAssignedResourceId());
					if (precallScheduleUpdateDTO.isReAssign()) {
						ReassignSORequest reAssignRequest = new ReassignSORequest();
						reAssignRequest.setResourceId(resource);
						reAssignRequest.setReassignComment(REASSIGN);
						reAssignRequest.setIdentification(identification);
						ReassignSOResponse reassignResponse = restClient
								.getResponseForReAssignProvider(
										reAssignRequest, providerId, soId);
						if (null == reassignResponse) {
							error = new SOWError(null, "Reassign API Failed",
									"ID");
						} else if (null != reassignResponse.getResults()
								.getError()) {
							List<ErrorResult> errorResults = reassignResponse
									.getResults().getError();
							for (ErrorResult result : errorResults) {
								LOGGER.info(result.getMessage());
								SOWError responseError = null;
								responseError = new SOWError(null,
										result.getMessage(), EMPTY_STR);
								omErrors.add(responseError);
								omTabDTO.setOmErrors(omErrors);
							}
						}
					} else {
						// setting the request object
						SOAssignProviderRequest assignRequest = new SOAssignProviderRequest();
						assignRequest.setIdentification(identification);
						assignRequest.setResourceId(resource);
						assignRequest.setSchemaLocation(ASSIGN_PROVIDER_REQUEST_SCHEMA);
						SOAssignProviderResponse assignProviderResponse = restClient.getResponseForAssignProvider(assignRequest,
								providerId, soId);
						if (null == assignProviderResponse) {
							error = new SOWError(null, "Assign API Failed",
									"ID");
						} else if (null != assignProviderResponse.getResults().getError()) {
							List<ErrorResult> errorResults = assignProviderResponse
									.getResults().getError();
							for (ErrorResult result : errorResults) {
								LOGGER.info(result.getMessage());
								SOWError responseError = null;
								responseError = new SOWError(null,
										result.getMessage(), EMPTY_STR);
								omErrors.add(responseError);
								omTabDTO.setOmErrors(omErrors);
							}
						}
					}
				}
				if (precallScheduleUpdateDTO.isSpecialInstructionEditFlag()) {
					if (null != precallScheduleUpdateDTO
							.getSpecialInstructions()) {
						updateScheduleDetailsRequest
						.setSpecialInstructions(precallScheduleUpdateDTO
								.getSpecialInstructions());
					}
				}
				if (precallScheduleUpdateDTO.isLocationNotesFlag()) {
					if (null != precallScheduleUpdateDTO.getLocationNotes()) {
						updateScheduleDetailsRequest
						.setSoNotes(precallScheduleUpdateDTO
								.getLocationNotes());
					}
				}
				if (!StringUtils.isBlank(precallScheduleUpdateDTO.getEta())) {
					updateScheduleDetailsRequest
					.setEta(precallScheduleUpdateDTO.getEta());
				}
				if (!StringUtils.isBlank(precallScheduleUpdateDTO
						.getCustomerAvailableFlag())) {
					if (precallScheduleUpdateDTO.getCustomerAvailableFlag()
							.equals("1")) {
						updateScheduleDetailsRequest
						.setCustomerConfirmInd(false);
						updateScheduleDetailsRequest.setScheduleStatus(2);
						updateScheduleDetailsRequest
						.setReason(precallScheduleUpdateDTO
								.getCustomerNotAvalableReasonCode());
						updateScheduleDetailsRequest.setModifiedByName(soContxt
								.getUsername());

					} else if (precallScheduleUpdateDTO
							.getCustomerAvailableFlag().equals("0")) {
						updateScheduleDetailsRequest.setScheduleStatus(3);
						updateScheduleDetailsRequest
						.setCustomerConfirmInd(false);
						updateScheduleDetailsRequest
						.setModifiedByName(soContxt
								.getUsername());
						// Set PRE_CALL_COMPLETED_REASON 
						updateScheduleDetailsRequest.setReason(PRE_CALL_COMPLETED_REASON);
						if (precallScheduleUpdateDTO.getTimeResponseWindow() > 0) {							
							updateScheduleDetailsRequest.setCustomerConfirmInd(true);
						} 
						if (precallScheduleUpdateDTO.getTimeResponseWindow() == 2) {
							if (!StringUtils.isBlank(precallScheduleUpdateDTO
									.getStartTime())
									&& !StringUtils
									.isBlank(precallScheduleUpdateDTO
											.getStartDate())) {
								// convert to GMT
								Timestamp startAppDate = TimestampUtils
										.getTimestampFromString(
												precallScheduleUpdateDTO
												.getStartDate(),
												DATE_FORMAT_IN_DB);
								String startTime = TimeUtils
										.convertToGMT(
												startAppDate,
												precallScheduleUpdateDTO
												.getStartTime(),
												precallScheduleUpdateDTO
												.getTimeZone())
												.get(TIME_PARAMETER).toString();
								updateScheduleDetailsRequest
								.setServiceTimeStart(startTime);

								//SL 18896 R8.2 START
								//pass the startDate parameter also with the request, along with the startTime
								//need to check whether date goes to the next day, hence appending time+12 to date for PM
								String startDate = "";
								startAppDate = TimestampUtils.
										getTimestampFromString(getDateAndTimeAppended(precallScheduleUpdateDTO.getStartDate(),
												precallScheduleUpdateDTO.getStartTime()), 
												TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR);

								startDate = TimeUtils.convertToGMT(startAppDate, 
										precallScheduleUpdateDTO.getStartTime(), 
										precallScheduleUpdateDTO.getTimeZone()).get(DATE_PARAMETER).toString();
								LOGGER.info("startTime:"+startTime+" startDate:"+startDate);
								updateScheduleDetailsRequest.setStartDate(startDate);
								//SL 18896 R8.2 END
							}
							if (!StringUtils.isBlank(precallScheduleUpdateDTO
									.getEndTime())
									&& !StringUtils
									.isBlank(precallScheduleUpdateDTO
											.getEndDate())) {
								// convert to GMT
								Timestamp endAppDate = TimestampUtils
										.getTimestampFromString(
												precallScheduleUpdateDTO
												.getEndDate(),
												DATE_FORMAT_IN_DB);
								String endTime = TimeUtils
										.convertToGMT(
												endAppDate,
												precallScheduleUpdateDTO
												.getEndTime(),
												precallScheduleUpdateDTO
												.getTimeZone())
												.get(TIME_PARAMETER).toString();
								if(StringUtils.isNotBlank(endTime)){
									updateScheduleDetailsRequest
									.setServiceTimeEnd(endTime);
								}

								//SL 18896 R8.2 START
								//pass the endDate parameter also with the request, along with the endTime
								//need to check whether date goes to the next day, hence appending time+12 to date for PM
								String endDate = "";
								endAppDate = TimestampUtils.
										getTimestampFromString(getDateAndTimeAppended(precallScheduleUpdateDTO.getEndDate(),
												precallScheduleUpdateDTO.getEndTime()), 
												TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR);

								endDate = TimeUtils.convertToGMT(endAppDate, 
										precallScheduleUpdateDTO.getEndTime(), 
										precallScheduleUpdateDTO.getTimeZone()).get(DATE_PARAMETER).toString();
								LOGGER.info("endTime:"+endTime+" endDate:"+endDate);
								if(StringUtils.isNotBlank(endDate)){
									updateScheduleDetailsRequest.setEndDate(endDate);
								}

								//SL 18896 R8.2 END

							}
						} else if (precallScheduleUpdateDTO
								.getTimeResponseWindow() == 3) {
							updateScheduleDetailsRequest.setCustomerConfirmInd(false);
							SORescheduleInfo soRescheduleInfo = new SORescheduleInfo();
							setRescheduleRequest(rescheduleDTO,
									soRescheduleInfo);
							SOProviderRescheduleRequest rescheduleRequest = new SOProviderRescheduleRequest();
							rescheduleRequest
							.setSoRescheduleInfo(soRescheduleInfo);
							rescheduleRequest.setIdentification(identification);
							rescheduleRequest
							.setSchemaLocation(rescheduleRequest
									.getNamespace() + XSD);
							SOProviderRescheduleResponse response = new SOProviderRescheduleResponse();
							response = restClient.getResponseForReschedule(
									rescheduleRequest, providerId, resourceId,
									soId);
							if (null == response) {
								error = new SOWError(null, "API Failed", "ID");
							} else if (null != response.getResults().getError()) {
								List<ErrorResult> errorResults = response
										.getResults().getError();
								for (ErrorResult result : errorResults) {
									LOGGER.info(result.getMessage());
									SOWError responseError = null;
									responseError = new SOWError(null,
											result.getMessage(), EMPTY_STR);
									omErrors.add(responseError);
									omTabDTO.setOmErrors(omErrors);
								}
							}
						}
					}
				}
			}
		} else if ("ConfirmAppointment".equals(precallScheduleUpdateDTO
				.getSource())) {
			updateScheduleDetailsRequest.setSource("CONFIRM_APPOINTMENT");
			saveConfirmApptDetails(updateScheduleDetailsRequest,
					precallScheduleUpdateDTO, soContxt, identification,
					rescheduleDTO, soId);
		}
		UpdateScheduleDetailsResponse updateScheduleDetailsResponse = restClient
				.getResponseForUpdateSheduleDetails(
						updateScheduleDetailsRequest, providerId, soId);
		if (null == updateScheduleDetailsResponse) {
			error = new SOWError(null, "API Failed", "ID");
		} else if (null != updateScheduleDetailsResponse.getResults()
				.getError()) {
			List<ErrorResult> errorResults = updateScheduleDetailsResponse
					.getResults().getError();
			for (ErrorResult result : errorResults) {
				LOGGER.info(result.getMessage());
				error = new SOWError(null, result.getMessage(), EMPTY_STR);
				if(!omErrors.contains(error)){
					omErrors.add(error);
					omTabDTO.setOmErrors(omErrors);
				}			
			}
		}
		if (null != error) {
			if(!omErrors.contains(error)){
				omErrors.add(error);
				omTabDTO.setOmErrors(omErrors);
			}
		} else if ("PreCall".equals(precallScheduleUpdateDTO.getSource())) {
			omTabDTO.setResult("Pre Call is success for service order - "
					+ soId);
		} else if ("ConfirmAppointment".equals(precallScheduleUpdateDTO
				.getSource())) {
			omTabDTO.setResult("Confirm Appointment is success for service order - "
					+ soId);
		}
		return JSON;
	}

	/**
	 * @param updateScheduleDetailsRequest
	 * @param precallScheduleUpdateDTO
	 * @param soContxt
	 * @param providerId
	 * @param resourceId
	 * @param identification
	 * @param rescheduleDTO
	 * @param soId
	 * @param omErrors2
	 */
	private void saveConfirmApptDetails(
			UpdateScheduleDetailsRequest updateScheduleDetailsRequest,
			PrecallScheduleUpdateDTO precallScheduleUpdateDTO,
			SecurityContext soContxt, Identification identification,
			RescheduleDTO rescheduleDTO, String soId) {
		SOWError error = null;
		String resourceId = soContxt.getVendBuyerResId().toString();
		String providerId = soContxt.getCompanyId().toString();

		if (null != precallScheduleUpdateDTO) {
			Integer resource;
			if (!StringUtils.isBlank(precallScheduleUpdateDTO
					.getAssignedResourceId())) {
				resource = Integer.parseInt(precallScheduleUpdateDTO
						.getAssignedResourceId());

				if (precallScheduleUpdateDTO.isReAssign()) {
					ReassignSORequest reAssignRequest = new ReassignSORequest();
					reAssignRequest.setIdentification(identification);
					reAssignRequest.setResourceId(resource);
					reAssignRequest.setReassignComment(REASSIGN);
					ReassignSOResponse reassignResponse = restClient
							.getResponseForReAssignProvider(reAssignRequest,
									providerId, soId);
					if (null == reassignResponse) {
						error = new SOWError(null, "Reassign API Failed", "ID");
					} else if (null != reassignResponse.getResults().getError()) {
						List<ErrorResult> errorResults = reassignResponse
								.getResults().getError();
						for (ErrorResult result : errorResults) {
							LOGGER.info(result.getMessage());
							SOWError responseError = null;
							responseError = new SOWError(null,
									result.getMessage(), EMPTY_STR);
							omErrors.add(responseError);
						}
					}
				} else {
					// setting the request object
					SOAssignProviderRequest assignRequest = new SOAssignProviderRequest();
					assignRequest.setIdentification(identification);
					assignRequest.setResourceId(resource);
					assignRequest
					.setSchemaLocation(ASSIGN_PROVIDER_REQUEST_SCHEMA);

					SOAssignProviderResponse assignProviderResponse = restClient
							.getResponseForAssignProvider(assignRequest,
									providerId, soId);
					if (null == assignProviderResponse) {
						error = new SOWError(null, "Assign API Failed", "ID");
					} else if (null != assignProviderResponse.getResults()
							.getError()) {
						List<ErrorResult> errorResults = assignProviderResponse
								.getResults().getError();
						for (ErrorResult result : errorResults) {
							LOGGER.info(result.getMessage());
							SOWError responseError = null;
							responseError = new SOWError(null,
									result.getMessage(), EMPTY_STR);
							omErrors.add(responseError);
						}
					}
				}

			}
			if (precallScheduleUpdateDTO.isSpecialInstructionEditFlag()) {
				if (null != precallScheduleUpdateDTO.getSpecialInstructions()) {
					updateScheduleDetailsRequest
					.setSpecialInstructions(precallScheduleUpdateDTO
							.getSpecialInstructions());
				}
			}
			if (precallScheduleUpdateDTO.isLocationNotesFlag()) {
				if (null != precallScheduleUpdateDTO.getLocationNotes()) {
					updateScheduleDetailsRequest
					.setSoNotes(precallScheduleUpdateDTO
							.getLocationNotes());
				}
			}
			if (!StringUtils.isBlank(precallScheduleUpdateDTO.getEta())) {
				updateScheduleDetailsRequest.setEta(precallScheduleUpdateDTO
						.getEta());
			}
			if (!StringUtils.isBlank(precallScheduleUpdateDTO
					.getCustomerAvailableFlag())) {
				if (precallScheduleUpdateDTO.getCustomerAvailableFlag().equals(
						"1")) {
					updateScheduleDetailsRequest.setCustomerConfirmInd(false);
					updateScheduleDetailsRequest.setScheduleStatus(4);
					updateScheduleDetailsRequest.setModifiedByName(soContxt
							.getUsername());
					updateScheduleDetailsRequest
					.setReason(precallScheduleUpdateDTO
							.getCustomerNotAvalableReasonCode());
				} else if (precallScheduleUpdateDTO.getCustomerAvailableFlag()
						.equals("0")) {
					updateScheduleDetailsRequest.setScheduleStatus(5);
					updateScheduleDetailsRequest.setCustomerConfirmInd(false);
					updateScheduleDetailsRequest.setModifiedByName(soContxt
							.getUsername());
					updateScheduleDetailsRequest.setReason(TIME_WINDOW_CALL_COMPLETED_REASON);
					if (precallScheduleUpdateDTO.getTimeResponseWindow() > 0) {
						updateScheduleDetailsRequest.setCustomerConfirmInd(true);
					}
					if (precallScheduleUpdateDTO.getTimeResponseWindow() == 2) {
						if (!StringUtils.isBlank(precallScheduleUpdateDTO
								.getStartTime())
								&& !StringUtils
								.isBlank(precallScheduleUpdateDTO
										.getStartDate())) {
							// convert to GMT
							Timestamp startAppDate = TimestampUtils
									.getTimestampFromString(
											precallScheduleUpdateDTO
											.getStartDate(),
											DATE_FORMAT_IN_DB);
							String startTime = TimeUtils
									.convertToGMT(
											startAppDate,
											precallScheduleUpdateDTO
											.getStartTime(),
											precallScheduleUpdateDTO
											.getTimeZone()).get(TIME_PARAMETER)
											.toString();
							updateScheduleDetailsRequest
							.setServiceTimeStart(startTime);


							//SL 18896 R8.2 START
							//pass the startDate parameter also with the request, along with the startTime
							//need to check whether date goes to the next day, hence appending time+12 to date for PM
							String startDate = "";
							startAppDate = TimestampUtils.
									getTimestampFromString(getDateAndTimeAppended(precallScheduleUpdateDTO.getStartDate(),
											precallScheduleUpdateDTO.getStartTime()), 
											TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR);

							startDate = TimeUtils.convertToGMT(startAppDate, 
									precallScheduleUpdateDTO.getStartTime(), 
									precallScheduleUpdateDTO.getTimeZone()).get(DATE_PARAMETER).toString();
							LOGGER.info("startTime:"+startTime+" startDate:"+startDate);
							updateScheduleDetailsRequest.setStartDate(startDate);
							//SL 18896 R8.2 END
						}
						if (!StringUtils.isBlank(precallScheduleUpdateDTO
								.getEndTime())
								&& !StringUtils
								.isBlank(precallScheduleUpdateDTO
										.getEndDate())) {
							// convert to GMT
							Timestamp endAppDate = TimestampUtils
									.getTimestampFromString(
											precallScheduleUpdateDTO
											.getEndDate(), DATE_FORMAT_IN_DB);
							String endTime = TimeUtils
									.convertToGMT(
											endAppDate,
											precallScheduleUpdateDTO
											.getEndTime(),
											precallScheduleUpdateDTO
											.getTimeZone()).get(TIME_PARAMETER)
											.toString();
							if(StringUtils.isNotBlank(endTime)){
								updateScheduleDetailsRequest
								.setServiceTimeEnd(endTime);
							}


							//SL 18896 R8.2 START
							//pass the endDate parameter also with the request, along with the endTime
							//need to check whether date goes to the next day, hence appending time+12 to date for PM
							String endDate = "";
							endAppDate = TimestampUtils.
									getTimestampFromString(getDateAndTimeAppended(precallScheduleUpdateDTO.getEndDate(),
											precallScheduleUpdateDTO.getEndTime()), 
											TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR);

							endDate = TimeUtils.convertToGMT(endAppDate, 
									precallScheduleUpdateDTO.getEndTime(), 
									precallScheduleUpdateDTO.getTimeZone()).get(DATE_PARAMETER).toString();
							LOGGER.info("endTime:"+endTime+" endDate:"+endDate);
							if(StringUtils.isNotBlank(endDate)){
								updateScheduleDetailsRequest.setEndDate(endDate);
							}
							//SL 18896 R8.2 END
						}
						updateScheduleDetailsRequest.setCustomerConfirmInd(true);
					} else if (precallScheduleUpdateDTO.getTimeResponseWindow() == 3) {
						updateScheduleDetailsRequest.setCustomerConfirmInd(false);
						SORescheduleInfo soRescheduleInfo = new SORescheduleInfo();
						setRescheduleRequest(rescheduleDTO, soRescheduleInfo);
						SOProviderRescheduleRequest rescheduleRequest = new SOProviderRescheduleRequest();
						rescheduleRequest.setSoRescheduleInfo(soRescheduleInfo);
						rescheduleRequest.setIdentification(identification);
						rescheduleRequest.setSchemaLocation(rescheduleRequest
								.getNamespace() + XSD);
						SOProviderRescheduleResponse response = new SOProviderRescheduleResponse();
						response = restClient
								.getResponseForReschedule(rescheduleRequest,
										providerId, resourceId, soId);
						if (null == response) {
							error = new SOWError(null, "API Failed", "ID");
						} else if (null != response.getResults().getError()) {
							List<ErrorResult> errorResults = response
									.getResults().getError();
							for (ErrorResult result : errorResults) {
								LOGGER.info(result.getMessage());
								SOWError responseError = null;
								responseError = new SOWError(null,
										result.getMessage(), EMPTY_STR);
								omErrors.add(responseError);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * this method is used to update schedule time
	 * 
	 * @param
	 * @return String.
	 */
	public String updateTime() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String firmId = soContxt.getCompanyId().toString();
		String soId = getRequest().getParameter(SO_ID);
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		String eta = getRequest().getParameter("eta");
		boolean confirmInd = Boolean.parseBoolean(getRequest().getParameter(
				"confirmInd"));
		String timeZone = getRequest().getParameter("zone");
		String startDate = getRequest().getParameter("startDate");
		String endDate = getRequest().getParameter("endDate");
		SOWError error = null;

		String startTimeOrg = "";
		String endTimeOrg = "";
		String historyDesc = "";
		String userName="";
		Integer entityId= 0;
		Integer roleId=0;
		String createdBy="";		

		if(soContxt!=null){
			userName = soContxt.getUsername();
			entityId = soContxt.getVendBuyerResId();
			roleId = soContxt.getRoleId();
			LoginCredentialVO lvRoles = soContxt.getRoles();
			createdBy = lvRoles.getFirstName() +" "+lvRoles.getLastName();
		}

		// get the service time on service order Timezone.
		if (StringUtils.isNotBlank(startTime) && null != startDate) {
			String startTimeForDate = startTime;
			Timestamp startAppDate = TimestampUtils.getTimestampFromString(startDate, DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
			startTime = TimeUtils.convertToGMT(startAppDate, startTime, timeZone).get(TIME_PARAMETER).toString();
			startTimeOrg = startTimeForDate;
			//SL 18896 R8.2
			//pass the startDate parameter also with the request, along with the startTime
			//need to check whether date goes to the next day, hence appending time+12 to date for PM
			startAppDate = TimestampUtils.
					getTimestampFromString( getDateAndTimeAppended(startDate,startTimeForDate), DATE_FORMAT_APPENDED_WITH_TIME);

			startDate = TimeUtils.convertToGMT(startAppDate, startTimeForDate, timeZone).get(DATE_PARAMETER).toString();
			LOGGER.info("startTime:"+startTime+" startDate:"+startDate);
		}
		if (StringUtils.isNotBlank(endTime) && null != endDate) {
			String endTimeForDate = endTime;
			Timestamp endAppDate = TimestampUtils.getTimestampFromString(endDate, DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
			endTime = TimeUtils.convertToGMT(endAppDate, endTime, timeZone).get(TIME_PARAMETER).toString();
			endTimeOrg = endTimeForDate;
			//SL 18896 R8.2
			//pass the endDate parameter also with the request, along with the endTime
			endAppDate = TimestampUtils.
					getTimestampFromString(getDateAndTimeAppended(endDate,endTimeForDate), DATE_FORMAT_APPENDED_WITH_TIME);

			endDate = TimeUtils.convertToGMT(endAppDate, endTimeForDate, timeZone).get(DATE_PARAMETER).toString();
			LOGGER.info("endTime:"+endTime+" endDate:"+endDate);
		}

		Identification identification = new Identification();
		identification.setId(soContxt.getVendBuyerResId());
		identification.setType(Identification.ID_TYPE_PROVIDER_RESOURCE_ID);

		SOEditAppointmentTimeRequest updateTimeRequest = new SOEditAppointmentTimeRequest();
		updateTimeRequest.setIdentification(identification);
		updateTimeRequest.setStartTime(startTime);
		if(StringUtils.isNotBlank(endTime)){
			updateTimeRequest.setEndTime(endTime);
		}
		updateTimeRequest.setEta(eta);
		updateTimeRequest.setCustomerConfirmedInd(confirmInd);
		updateTimeRequest.setSchemaLocation(UPDATE_TIME_REQUEST_SCHEMA);
		/**SL 18896 R8.2, pass the startDate & endDate parameter START**/
		updateTimeRequest.setStartDate(startDate);
		if(StringUtils.isNotBlank(endDate)){
			updateTimeRequest.setEndDate(endDate);
		}
		/**SL 18896 R8.2, pass the startDate & endDate parameter END**/
		try {
			SOEditAppointmentTimeResponse updateTimeResponse = restClient.getResponseForUpdateAppointmentTime(updateTimeRequest,
					firmId, soId);
			if (null != updateTimeResponse
					&& null != updateTimeResponse.getResults()) {
				List<ErrorResult> errorResults = updateTimeResponse.getResults().getError();
				if (null != errorResults) {
					for (ErrorResult result : errorResults) {
						LOGGER.info(result.getMessage());
						error = new SOWError(null, result.getMessage(), EMPTY_STR);
						if(!omErrors.contains(error)){
							omErrors.add(error);
							omTabDTO.setOmErrors(omErrors);
						}					}
					return JSON;
				}
			}
		} catch (Exception exc) {
			LOGGER.error(exc.getMessage());
			error = new SOWError(null, exc.getMessage(), EMPTY_STR);
		}
		if (null != error) {
			if(!omErrors.contains(error)){
				omErrors.add(error);
			}		} else {


				// SPM-1361 - Added history for update time
				// Update the history - new action
				if(StringUtils.isNotBlank(startTimeOrg)){
					historyDesc = PublicAPIConstant.UPDATE_TIME_ACTION_DESC +
							PublicAPIConstant.WHITE_SPACE+startTimeOrg;

				}
				if(StringUtils.isBlank(endTimeOrg)){
					historyDesc  = historyDesc +PublicAPIConstant.DOT;
				}else if(StringUtils.isNotBlank(endTimeOrg)){
					historyDesc = historyDesc + PublicAPIConstant.WHITE_SPACE+
							PublicAPIConstant.HYPHEN+PublicAPIConstant.WHITE_SPACE+
							endTimeOrg+PublicAPIConstant.DOT;
				}
				historyLogging(roleId, soId, userName,entityId,historyDesc,createdBy);

				omTabDTO.setResult("Appointment time updated successfully for Service Order #"
						+ soId);
			}
		return JSON;
	}


	/**
	 * @param roleId
	 * @param soId
	 * @param userName
	 * @param entityId
	 * @param desc
	 * @param createdBy
	 */
	private void historyLogging(Integer roleId, String soId, String userName,
			Integer entityId,String desc,String createdBy) {
		// TODO Auto-generated method stub
		try{
			Date d=new Date(System.currentTimeMillis());
			Timestamp date = new Timestamp(d.getTime());
			ProviderHistoryVO hisVO=new ProviderHistoryVO();
			hisVO.setSoId(soId);
			hisVO.setActionId(PublicAPIConstant.UPDATE_TIME_ACTION_ID);
			hisVO.setDescription(desc);
			hisVO.setCreatedDate(date);
			hisVO.setModifiedDate(date);
			hisVO.setCreatedBy(createdBy);
			hisVO.setRoleId(roleId);
			hisVO.setModifiedBy(userName);
			hisVO.setEnitityId(entityId);
			managementService.historyLogging(hisVO);
		}
		catch (Exception ex) {
			LOGGER.error("Exception Occured in MobileTimeOnSiteService-->historyLogging()");
		}
	}

	private String getDateAndTimeAppended(String date, String time){
		if(StringUtils.isNotBlank(date) && StringUtils.isNotBlank(time)){

			String hr = time.substring(0,2);
			int hrIntValue=0;
			try{
				hrIntValue = Integer.parseInt(hr);
			}catch (NumberFormatException e) {
				// Exception caught and nothing to do
			}
			if(time.contains("PM") && hrIntValue != 12){
				hrIntValue = hrIntValue+12;
			}
			String convertedHour = new Integer(hrIntValue).toString();
			if(convertedHour.length()<2){
				convertedHour = "0"+convertedHour;
			}

			return date+ " "+convertedHour+time.substring(2,5)+":00";
		}
		return null;
	}	

	/**
	 * This method is used to load service location notes in the Edit Service
	 * Location Notes widget
	 * 
	 * 
	 */
	public String loadDataForEditNotes() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String providerId = soContxt.getCompanyId().toString();
		String soId = getRequest().getParameter(SO_ID);

		String responseFilterParam = "Service%20Location";
		String groupInd = getRequest().getParameter(GROUP_IND_PARAM);
		SOWError error =null;
		if (StringUtils.isBlank(groupInd)
				|| !GROUPED_SO_IND.equalsIgnoreCase(groupInd)) {
			groupInd = "0";
		}
		SOGetResponse soGetResponse = restClient.getResponseForGetInfoForCall(
				providerId, soId, groupInd, responseFilterParam);
		List<RetrieveServiceOrder> retrieveServiceOrderList = null;
		if (null != soGetResponse && null != soGetResponse.getServiceOrders()) {
			ServiceOrders serviceOrders = soGetResponse.getServiceOrders();
			retrieveServiceOrderList = new ArrayList<RetrieveServiceOrder>();
			if (null != serviceOrders
					&& null != serviceOrders.getServiceorders()) {
				retrieveServiceOrderList = serviceOrders.getServiceorders();
				RetrieveServiceOrder retrieveServiceOrder = null;
				if (retrieveServiceOrderList.size() > 0) {
					retrieveServiceOrder = new RetrieveServiceOrder();
					retrieveServiceOrder = retrieveServiceOrderList.get(0);
				}
				if (retrieveServiceOrder != null
						&& retrieveServiceOrder.getServiceLocation() != null) {
					this.setAttribute("editNotesList", retrieveServiceOrder
							.getServiceLocation().getNote());
				}
			}
		}
		else if(null!=soGetResponse && (null != soGetResponse.getResults().getError())) {
			List<ErrorResult> errorResults = soGetResponse
					.getResults().getError();
			if (null != errorResults) {
				for (ErrorResult result : errorResults) {
					LOGGER.info(result.getMessage());
					error = new SOWError(null, result.getMessage(), EMPTY_STR);
					if(!omErrors.contains(error)){
						omErrors.add(error);
					}				}
			}
		}

		this.setAttribute("omApiErrors", omErrors);

		return SUCCESS;
	}

	/**
	 * This method is used to update data service location notes
	 */
	public String updateSOLocationNotes() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String providerId = soContxt.getCompanyId().toString();
		String soId = getRequest().getParameter(SO_ID);
		String locnNotes = getRequest().getParameter("locnNotes");

		Identification identification = new Identification();
		identification.setId(soContxt.getVendBuyerResId());
		identification.setType(Identification.ID_TYPE_PROVIDER_RESOURCE_ID);

		SOEditSOLocationNotesRequest editSlNoteRequest = new SOEditSOLocationNotesRequest();
		editSlNoteRequest.setNotes(locnNotes);
		editSlNoteRequest.setIdentification(identification);
		editSlNoteRequest.setSchemaLocation(editSlNoteRequest.getNamespace()
				+ XSD);

		SOEditSOLocationNotesResponse response = new SOEditSOLocationNotesResponse();
		response = restClient.getResponseForEditSLNotes(editSlNoteRequest,
				providerId, soId);
		SOWError error = null;
		if (null == response) {
			error = new SOWError(null, "API Failed", "ID");
		} else if (null != response.getResults().getError()) {
			List<ErrorResult> errorResults = response.getResults().getError();
			for (ErrorResult result : errorResults) {
				LOGGER.info(result.getMessage());
				SOWError responseError = null;
				responseError = new SOWError(null, result.getMessage(), EMPTY_STR);
				omErrors.add(responseError);
				omTabDTO.setOmErrors(omErrors);
			}
		}
		if (null != error) {
			if(!omErrors.contains(error)){
				omErrors.add(error);
				omTabDTO.setOmErrors(omErrors);
			}		}
		return JSON;
	}

	/**
	 * this method is used to load data for request reschedule pop up
	 */
	public String loadDataForRequestReschedule() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String vendorId = soContxt.getCompanyId().toString();
		String errMessage = EMPTY_STR;
		String soId = getRequest().getParameter(SO_ID);
		String groupInd = getRequest().getParameter(GROUP_IND_PARAM);
		if (StringUtils.isBlank(groupInd) || !GROUPED_SO_IND.equalsIgnoreCase(groupInd)) {
			groupInd = "0";
		}
		String responseFilterParam = "Schedule-Fetch%20Reason%20Codes-Service%20Location";
		RetrieveServiceOrder retrieveServiceOrder = null;
		try {
			retrieveServiceOrder = getServiceOrder(soId, groupInd, vendorId,
					responseFilterParam);
		} catch (Exception exc) {
			LOGGER.error(exc.getMessage());
			setAttribute("errorMessage", exc.getMessage());
		}
		if (null != retrieveServiceOrder) {
			setAttribute(SO_ID, soId);
			this.setAttribute("rescheduleDetails", retrieveServiceOrder);
			formatServiceTimeForReSchedule(retrieveServiceOrder.getSchedule());

		} else {
			errMessage = "Invalid SO Id.";
			setAttribute("errorMessage", errMessage);
		}
		getSession().setAttribute("omApiErrors",omErrors);

		return SUCCESS;
	}

	/**
	 * Invoked to format service Date and Time to display in Reschedule Pop up.
	 * @param schedule {@link Schedule}
	 * **/
	private void formatServiceTimeForReSchedule(Schedule schedule) {
		StringBuilder serviceDate = new StringBuilder(EMPTY_STR);
		StringBuilder serviceTime = new StringBuilder(EMPTY_STR);
		if (null != schedule) {
			String date1 = schedule.getServiceDateTime1();
			String date2 = schedule.getServiceDateTime2();
			this.setAttribute("rescheduleDate", null);
			String actualTimezone=schedule.getServiceLocationTimezone().substring(0, schedule.getServiceLocationTimezone().lastIndexOf(':'));
			String displayTimezone=schedule.getServiceLocationTimezone().substring(schedule.getServiceLocationTimezone().lastIndexOf(':')+1, schedule.getServiceLocationTimezone().length());
			displayTimezone = " ("+displayTimezone+")";
			if(null!=schedule.getReschedule())
			{
				formatServiceTimeForEditReSchedule(schedule.getReschedule(), displayTimezone);
			}
			if (StringUtils.isNotBlank(date1)) {
				String startArr[] = date1.split("T");
				String startDate = getTimeStampFromStr(startArr[0], DATE_FORMAT_IN_DB, "MMMMMMMM dd, yyyy");
				String startTime = getTimeStampFromStr(startArr[1], TIME_FORMAT_IN_DB,TIME_FORMAT_DISPLAYED);
				serviceDate.append(startDate);
				serviceTime.append(startTime);
				if (StringUtils.isNotBlank(date2)) {
					String endArr[] = date2.split("T");
					String endDate = EMPTY_STR;
					String endTime = EMPTY_STR;
					endDate = getTimeStampFromStr(endArr[0], DATE_FORMAT_IN_DB,
							"MMMMMMMM dd, yyyy");
					endTime = getTimeStampFromStr(endArr[1], TIME_FORMAT_IN_DB,
							TIME_FORMAT_DISPLAYED);
					serviceTime.append(" - ").append(endTime);
					serviceDate.append(" - ").append(endDate);
				} else {
					serviceDate.append(" at ").append(serviceTime);
				}
				//				// cst -substribg
				//				serviceTime.append(" (")
				//						.append(schedule.getServiceLocationTimezone())
				//						.append(")"); // TODO

				serviceTime.append(displayTimezone);

				getSession().setAttribute("actualTimeZone",actualTimezone);
			}
		}
		this.setAttribute("serviceTime", serviceTime.toString());
		this.setAttribute("serviceDate", serviceDate.toString());
	}

	/**
	 * Invoked to format reschedule service Date and Time to display in Reschedule Pop up.
	 * @param schedule {@link Schedule}
	 * **/

	private void formatServiceTimeForEditReSchedule(Reschedule reschedule, String displayTimeZone) {
		StringBuilder serviceDate = new StringBuilder(EMPTY_STR);
		StringBuilder serviceTime = new StringBuilder(EMPTY_STR);
		if (null != reschedule) {
			String date1 = reschedule.getRescheduleServiceDateTime1();
			String date2 = reschedule.getRescheduleServiceDateTime2();
			if (StringUtils.isNotBlank(date1)) {
				String startArr[] = date1.split("T");
				String startDate = getTimeStampFromStr(startArr[0], DATE_FORMAT_IN_DB, "MMMMMMMM dd, yyyy");
				String startTime = getTimeStampFromStr(startArr[1], TIME_FORMAT_IN_DB,TIME_FORMAT_DISPLAYED);
				serviceDate.append(startDate);
				serviceTime.append(startTime);
				if (StringUtils.isNotBlank(date2)) {
					String endArr[] = date2.split("T");
					String endDate = EMPTY_STR;
					String endTime = EMPTY_STR;
					endDate = getTimeStampFromStr(endArr[0], DATE_FORMAT_IN_DB,
							"MMMMMMMM dd, yyyy");
					endTime = getTimeStampFromStr(endArr[1], TIME_FORMAT_IN_DB,
							TIME_FORMAT_DISPLAYED);
					serviceTime.append(" - ").append(endTime);
					serviceDate.append(" - ").append(endDate);
					serviceDate.append(", at ").append(serviceTime);
					serviceDate.append(displayTimeZone);
				} else {
					serviceDate.append(", at ").append(serviceTime);
					serviceDate.append(displayTimeZone);
				}
			}
		}
		this.setAttribute("rescheduleTime", serviceTime.toString());
		this.setAttribute("rescheduleDate", serviceDate.toString());
	}

	/**
	 * Action method which calls the API for Request a reschedule for provider,
	 * after successful validation.
	 * */
	public String updateDataForRequestReschedule() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		SOWError error = null;
		String providerId = soContxt.getCompanyId().toString();
		String resourceId = soContxt.getVendBuyerResId().toString();
		OrderManagementTabDTO tabDTO = getModel();
		RescheduleDTO rescheduleDTO = null;
		if (null != tabDTO) {
			rescheduleDTO = tabDTO.getRescheduleDTO();
		} else {
			return JSON;
		}
		/*
		 * Call validation method
		 */
		if (!validateRescheduleRequest(rescheduleDTO)) {
			return JSON;
		}
		String soId = rescheduleDTO.getSoId();
		Identification identification = new Identification();
		identification.setId(soContxt.getVendBuyerResId());
		identification.setType(Identification.ID_TYPE_PROVIDER_RESOURCE_ID);
		SORescheduleInfo soRescheduleInfo = new SORescheduleInfo();
		setRescheduleRequest(rescheduleDTO, soRescheduleInfo);  
		SOProviderRescheduleRequest rescheduleRequest = new SOProviderRescheduleRequest();
		rescheduleRequest.setSoRescheduleInfo(soRescheduleInfo);
		rescheduleRequest.setIdentification(identification);
		rescheduleRequest.setSchemaLocation(rescheduleRequest.getNamespace()
				+ XSD);
		SOProviderRescheduleResponse response = new SOProviderRescheduleResponse();
		response = restClient.getResponseForReschedule(rescheduleRequest,
				providerId, resourceId, soId);
		if (null == response) {
			error = new SOWError(null, "API Failed", "ID");
		} else if (null != response.getResults().getError()) {
			List<ErrorResult> errorResults = response.getResults().getError();
			for (ErrorResult result : errorResults) {
				LOGGER.info(result.getMessage());
				SOWError responseError = null;
				responseError = new SOWError(null, result.getMessage(), EMPTY_STR);
				omErrors.add(responseError);
				omTabDTO.setOmErrors(omErrors);
			}
		}
		if (null != error) {
			if(!omErrors.contains(error)){
				omErrors.add(error);
				omTabDTO.setOmErrors(omErrors);
			}		}
		if (omErrors.size() == 0) {
			List<Result> successResults = response.getResults().getResult();
			for (Result result : successResults) {
				LOGGER.info(result.getMessage());
				omTabDTO.setResult(result.getMessage() + " for #" + soId);
				//				getSession().setAttribute(
				//						SUCCESS_MSG,
				//						result.getMessage() + " for #" + soId);
			}
		}
		return JSON;
	}

	/**
	 * Populates Reschedule request VO using the values from frond end
	 * */
	private void setRescheduleRequest(RescheduleDTO rescheduleDTO,
			SORescheduleInfo soRescheduleInfo) {
		String formatedDate = getTimeStampFromStr(rescheduleDTO.getNewStartDate(),"MM/dd/yyyy", "yyyy-MM-dd");
		String formatedTime = getTimeStampFromStr(rescheduleDTO.getNewStartTime(),"hh:mm aa", "HH:mm:ss");
		soRescheduleInfo.setServiceDateTime1(formatedDate+ "T" +formatedTime);
		if(rescheduleDTO.isSpecificDate()){
			soRescheduleInfo.setScheduleType(OrderConstants.DATETYPE_FIXED);
		}else{
			soRescheduleInfo.setScheduleType(OrderConstants.DATETYPE_RANGE);
			formatedDate = getTimeStampFromStr(rescheduleDTO.getNewEndDate(),"MM/dd/yyyy", "yyyy-MM-dd");
			formatedTime = getTimeStampFromStr(rescheduleDTO.getNewEndTime(),"hh:mm aa", "HH:mm:ss");
			soRescheduleInfo.setServiceDateTime2(formatedDate+ "T" +formatedTime);
		}
		soRescheduleInfo.setComments(rescheduleDTO.getRescheduleComments());
		soRescheduleInfo.setReasonCode(Integer.valueOf(rescheduleDTO.getReasonCode()));

	}



	/**
	 * Populates Reschedule request VO using the values from frond end
	 * */
	private void setRescheduleRequestChange(RescheduleDTO rescheduleDTO,
			SORescheduleInfo soRescheduleInfo) {
		HashMap<String, Object> dateTime = null;
		// convert to GMT
		String  newStartDate=rescheduleDTO.getNewStartDate();
		String  newStartTime= rescheduleDTO.getNewStartTime();
		String newEndDate=rescheduleDTO.getNewEndDate();
		String newEndTime=rescheduleDTO.getNewEndTime();
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		try
		{
			String timeZone = (String) getSession().getAttribute("actualTimeZone");

			//get the service start Date on service order Timezone.
			TimeZone timeZoneForSo=TimeZone.getTimeZone(timeZone);
			getSession().removeAttribute("actualTimeZone");

			Calendar startDateTimeSo = TimeChangeUtil.getCalTimeFromParts(defaultDateFormat.parse(newStartDate), newStartTime,TimeZone.getTimeZone("GMT"));
			Date serviceFromDateSo = TimeChangeUtil.getDate(startDateTimeSo, timeZoneForSo);
			String serviceTimeStartSo = TimeChangeUtil.getTimeString(startDateTimeSo, timeZoneForSo);

			String formatedDate = getTimeStampFromStr(defaultDateFormat.format(serviceFromDateSo), "MM/dd/yyyy", DATE_FORMAT_IN_DB); // format 
			String formatedTime = getTimeStampFromStr(serviceTimeStartSo, TIME_FORMAT_DISPLAYED,	TIME_FORMAT_IN_DB); // format 
			soRescheduleInfo.setServiceDateTime1(formatedDate + "T" + formatedTime); 
			if (rescheduleDTO.isSpecificDate()) {
				soRescheduleInfo.setScheduleType(DATETYPE_FIXED);
			} else {
				soRescheduleInfo.setScheduleType(DATETYPE_RANGE);


				//get the service end Date on service order Timezone.
				Calendar endDateTimeSo = null;
				if(null!=newEndDate && null!=newEndTime && !("".equals(newEndDate)) && !("".equals(newEndTime)) )
				{
					endDateTimeSo=TimeChangeUtil.getCalTimeFromParts(defaultDateFormat.parse(newEndDate), newEndTime,TimeZone.getTimeZone("GMT"));
				}

				Date serviceToDateSo = TimeChangeUtil.getDate(endDateTimeSo, timeZoneForSo);

				String serviceTimeEndSo="";
				if(null!=endDateTimeSo && !("".equals(endDateTimeSo)))
				{
					serviceTimeEndSo = TimeChangeUtil.getTimeString(endDateTimeSo, timeZoneForSo);
				}

				formatedDate = getTimeStampFromStr(defaultDateFormat.format(serviceToDateSo),  "dd/MM/yyyy",
						DATE_FORMAT_IN_DB);
				formatedTime = getTimeStampFromStr(serviceTimeEndSo, TIME_FORMAT_DISPLAYED, TIME_FORMAT_IN_DB);
				soRescheduleInfo.setServiceDateTime2(formatedDate + "T"
						+ formatedTime);
			}
			soRescheduleInfo.setComments(rescheduleDTO.getRescheduleComments());
			soRescheduleInfo.setReasonCode(Integer.valueOf(rescheduleDTO
					.getReasonCode()));   
		}
		catch(Exception e)
		{
			LOGGER.info("exception while setting reschedule request"+e);
		}

	}

	/**
	 * Method which validates Request-A_Reschedule request.
	 * 
	 * @param rescheduleDTO
	 * @return true: when request is valid, else false
	 */
	private boolean validateRescheduleRequest(RescheduleDTO rescheduleDTO) {
		SOWError error;
		omErrors.clear();
		if (null != rescheduleDTO) {
			List<SOWError> errorList = new ArrayList<SOWError>();
			// Validates date and time
			validateRescheduleDates(rescheduleDTO, errorList);
			// Reason code validation
			validateRescheduleResonCode(rescheduleDTO, errorList);
			if (StringUtils.isBlank(rescheduleDTO.getRescheduleComments())) {
				error = new SOWError(null, "Please enter comments.", EMPTY_STR);
				errorList.add(error);
			}
			String soId = rescheduleDTO.getSoId();
			if (StringUtils.isBlank(soId)) {
				error = new SOWError(null, "Invalid Service Order ID", EMPTY_STR);
				errorList.add(error);
			}
			omErrors.addAll(errorList);
		} else {
			error = new SOWError(null, "Please fill the form before submit.",EMPTY_STR);
			if(!omErrors.contains(error)){
				omErrors.add(error);
			}		}
		return omErrors.isEmpty() ? true : false;
	}

	/**
	 * Validation method for Reschedule reason code
	 * 
	 * @param rescheduleDTO
	 * @param errorList
	 */
	private void validateRescheduleResonCode(RescheduleDTO rescheduleDTO,
			List<SOWError> errorList) {
		String reasonCode = rescheduleDTO.getReasonCode();
		SOWError error = null;
		try {
			if (StringUtils.isBlank(reasonCode)
					|| 0 >= Integer.parseInt(reasonCode)) {
				error = new SOWError(null,
						"Please select a Reason for Rescheduling.", EMPTY_STR);
			}
		} catch (Exception e) {
			error = new SOWError(null,
					"Please select a valid Reason for Rescheduling.", EMPTY_STR);
		} finally {
			if (null != error) {
				errorList.add(error);
			}
		}
	}

	/**
	 * Validation method for Reschedule Dates Error/warning messages are added
	 * to errorList
	 * 
	 * @param rescheduleDTO
	 * @param errorList
	 */
	private void validateRescheduleDates(RescheduleDTO rescheduleDTO,
			List<SOWError> errorList) {
		SOWError error = null;
		String rescFromdate = rescheduleDTO.getNewStartDate();
		String rescTodate = rescheduleDTO.getNewEndDate();
		if (StringUtils.isBlank(rescFromdate)
				|| (!rescheduleDTO.isSpecificDate() && StringUtils
						.isBlank(rescTodate))) {
			error = new SOWError(null, "Please select Reschedule Date.", EMPTY_STR);
			errorList.add(error);
		} else if (!validateDate(rescFromdate)
				|| (!rescheduleDTO.isSpecificDate() && !validateDate(rescTodate))) {
			error = new SOWError(null,
					"Please provide a valid Reschedule Date.", EMPTY_STR);
			errorList.add(error);
		}
		String rescFromTime = rescheduleDTO.getNewStartTime();
		String rescToTime = rescheduleDTO.getNewEndTime();
		rescFromTime = "0".equals(rescFromTime) ? EMPTY_STR : rescFromTime;
		rescToTime = "0".equals(rescToTime) ? EMPTY_STR : rescToTime;
		if (StringUtils.isBlank(rescFromTime)
				|| (!rescheduleDTO.isSpecificDate() && StringUtils
						.isBlank(rescToTime))) {
			error = new SOWError(null, "Please select Reschedule Time.", EMPTY_STR);
			errorList.add(error);
		}
	}

	/**
	 * Checks whether a string is in valid date format
	 * @param strDate : Date as String
	 * @return true : for valid date.
	 * */
	private boolean validateDate(String strDate) {
		return (StringUtils.isNotBlank(strDate)	&& null != stringToDate(strDate, DATE_FORMAT_DISPLAYED));
	}

	/**
	 * @return
	 * method for loading tab data
	 */
	public String loadTabData() {
		String tabCount = getRequest().getParameter(SO_COUNT);
		OrderManagementTabDTO model = getModel();
		Integer count = 0;
		String sortCriteria = model.getCriteria();
		String sortOrder = model.getSortOrder();
		if (tabCount != null) {
			count = Integer.parseInt(tabCount);
		}
		String tab = getRequest().getParameter(ACTIVE_TAB);
		if (StringUtils.isBlank(tab)) {
			tab = (String) getSession().getAttribute(ACTIVE_TAB);
		}
		getSession().setAttribute(SO_COUNT, count);
		OrderManagmentTabTitleCount omTab = new OrderManagmentTabTitleCount();
		omTab.setTabCount(count);
		omTab.setTabTitle(tab);
		ArrayList<String> filterList = TabsMapper.getFilters(omTab
				.getTabTitle());
		omTabDTO = createTabDTOWithStatusList(omTab, filterList);
		getSession().setAttribute("filterNames", omTabDTO.getFilterList());
		OrderManagementTabDTO modelFilter = (OrderManagementTabDTO) getRequest().getAttribute("omModel");
		modelFilter = null == modelFilter ? model : modelFilter;
		// boolean isFilterOn = checkIfFilterApplied(modelFilter);

		if (count % DEAFULT_NUMBER_OF_ORDERS_OM != 0) {
			count -= count % DEAFULT_NUMBER_OF_ORDERS_OM;
			count += DEAFULT_NUMBER_OF_ORDERS_OM;
		}
		if (tab == null) {
			tab = TabConstants.INBOX;
		}
		getSession().setAttribute(ACTIVE_TAB, tab);
		omDisplayTab = tab;
		loadDataGrid(count, sortCriteria, sortOrder, model);
		return SUCCESS;
	}

	/**
	 * TODO not used.
	 * **/
	private boolean checkIfFilterApplied(OrderManagementTabDTO modelFilter) {
		if ((null == modelFilter.getSelectedMarkets() || modelFilter
				.getSelectedMarkets().size() == 0)
				&& (null == modelFilter.getSelectedProviders() || modelFilter
				.getSelectedProviders().size() == 0)) {
			if ((null == modelFilter.getSelectedStatus() || modelFilter
					.getSelectedStatus().size() == 0)
					&& (null == modelFilter.getSelectedScheduleStatus() || modelFilter
					.getSelectedScheduleStatus().size() == 0)) {
				if ((null == modelFilter.getSelectedSubStatus() || modelFilter
						.getSelectedSubStatus().size() == 0)
						&& StringUtils
						.isBlank(modelFilter.getAppointmentType())) {
					return false;
				}
			}
		}
		return true;
	}

	public void loadDataGrid(Integer soCount, String sortCriteria,
			String sortOrder, OrderManagementTabDTO model) {
		LOGGER.info("Entering OrderManagementControllerAction.loadDataGrid()...About to fetch Order list");
		long startTime = System.currentTimeMillis();

		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String providerId = soContxt.getCompanyId().toString();
		String resourceId = soContxt.getVendBuyerResId().toString();
		String modelTab = omDisplayTab.replaceAll(" ", "");
		OrderManagementTabDTO modelFromSession = null;
		model = getFilterValues(model);

		if(null!=model){
			if(null == model.getResetInd()){
				model.setResetInd(false);
			}
			if(model.getResetInd()){
				getSession().removeAttribute("model_"+modelTab);
			}
		}

		if(getSession().getAttribute("model_"+modelTab)!=null){
			modelFromSession = (OrderManagementTabDTO)getSession().getAttribute("model_"+modelTab);
		}
		if(null!=model && modelFromSession!=null){
			if(model.getMarketResetInd()!=null){
				modelFromSession.setMarketResetInd(model.getMarketResetInd());
			}
			if(model.getProviderResetInd()!=null){
				modelFromSession.setProviderResetInd(model.getProviderResetInd());
			}
			if(model.getRoutedProviderResetInd()!=null && model.getRoutedProviderResetInd()==1){
				modelFromSession.setRoutedProviderResetInd(model.getRoutedProviderResetInd());
			}

			if(model.getScheduleResetInd()!=null && model.getScheduleResetInd()==1){
				modelFromSession.setScheduleResetInd(model.getScheduleResetInd());
			}
			if(model.getStatusResetInd()!=null && model.getStatusResetInd()==1){
				modelFromSession.setStatusResetInd(model.getStatusResetInd());
			}
			if(model.getSubStatusResetInd()!=null && model.getSubStatusResetInd()==1){
				modelFromSession.setSubStatusResetInd(model.getSubStatusResetInd());
			}//For job done Substatus
			if(model.getJobDoneSubStatusResetInd()!=null){
				modelFromSession.setJobDoneSubStatusResetInd(model.getJobDoneSubStatusResetInd());
			}
			//For Current Order SubStatus
			if(model.getCurrentOrdersSubStatusResetInd()!=null){
				modelFromSession.setCurrentOrdersSubStatusResetInd(model.getCurrentOrdersSubStatusResetInd());
			}

			//R12.0 Sprint3 Cancellations substatus

			if(null != model.getCancellationsSubStatusResetInd()){
				modelFromSession.setCancellationsSubStatusResetInd(model.getCancellationsSubStatusResetInd());
			}

			//R12.0 Sprint4 Revisit needed substatus

			if(null != model.getRevisitSubStatusResetInd()){
				modelFromSession.setRevisitSubStatusResetInd(model.getRevisitSubStatusResetInd());
			}
		}
		if(!filterFlag && modelFromSession!=null){
			model = modelFromSession;
		}

		if(model!=null){

			if(model.getMarketResetInd()!=null && model.getMarketResetInd()==1){
				model.setSelectedMarkets(null);
			}
			if(model.getProviderResetInd()!=null && model.getProviderResetInd()==1){
				model.setSelectedProviders(null);
				model.setIncludeUnassigned(false);
			}
			if(model.getRoutedProviderResetInd()!=null && model.getRoutedProviderResetInd()==1){
				model.setSelectedRoutedProviders(null);
				model.setIncludeUnassigned(false);
			}
			if(model.getScheduleResetInd()!=null && model.getScheduleResetInd()==1){
				model.setSelectedScheduleStatus(null);
				model.setFilterScheduleStatus(SELECT_ITEM_DEFAULT);
			}
			if(model.getStatusResetInd()!=null && model.getStatusResetInd()==1){
				model.setSelectedStatus(null);
				model.setFilterStatus(SELECT_ITEM_DEFAULT);
			}
			if(model.getSubStatusResetInd()!=null && model.getSubStatusResetInd()==1){
				model.setSelectedSubStatus(null);
				model.setFilterSubStatus(SELECT_ITEM_DEFAULT);
			}
			if(model.getJobDoneSubStatusResetInd()!=null && model.getJobDoneSubStatusResetInd()==1){
				model.setSelectedJobDoneSubStatus(null);
				//model.setFilterJobDoneSubStatus(SELECT_ITEM_DEFAULT);
			}
			if(model.getCurrentOrdersSubStatusResetInd()!=null && model.getCurrentOrdersSubStatusResetInd()==1){
				model.setSelectedCurrentOrdersSubStatus(null);

			}

			//R12.0 Sprint3 Cancellations substatus
			if(null !=model.getCancellationsSubStatusResetInd() && model.getCancellationsSubStatusResetInd()==1){
				model.setSelectedCancellationsSubStatus(null);

			}
			//R12.0 Sprint4 Revisit substatus
			if(null !=model.getRevisitSubStatusResetInd() && model.getRevisitSubStatusResetInd()==1){
				model.setRevisitSubStatusResetInd(null);

			}

		}
		/*if(null!=modelFromSession){
			if(null == sortCriteria || StringUtils.isBlank(sortCriteria)){
				sortCriteria = modelFromSession.getCriteria();
			}
			if(null == sortOrder || StringUtils.isBlank(sortOrder)){
				sortOrder = modelFromSession.getSortOrder();
			}
		}*/

		Identification identification = new Identification();
		identification.setId(soContxt.getVendBuyerResId());
		identification.setType(Identification.ID_TYPE_PROVIDER_RESOURCE_ID);

		FetchSORequest fetchSoRequest = new FetchSORequest();
		fetchSoRequest.setTabName(omDisplayTab);
		fetchSoRequest.setEndIndex(soCount + DEAFULT_NUMBER_OF_ORDERS_OM);

		getSession().setAttribute("countLimitForTab", soCount);

		fetchSoRequest.setInitialLoadInd(this.initialLoadInd);
		if (StringUtils.isBlank(sortOrder)) {
			fetchSoRequest.setSortOrder(SORT_ORDER_ASC);
		} else {
			fetchSoRequest.setSortOrder(sortOrder);
		}
		if (StringUtils.isNotBlank(sortCriteria)) {
			fetchSoRequest.setSortBy(sortCriteria);
		}
		if (null != omTabDTO) {
			omTabDTO.setDisplayTab(omDisplayTab);
			omTabDTO.setCriteria(sortCriteria);
			omTabDTO.setSortOrder(sortOrder);
		}
		fetchSoRequest.setIdentification(identification);
		fetchSoRequest.setSchemaLocation(fetchSoRequest.getNamespace() + XSD);
		if (null != model) {
			setFilterParametersToRequest(model, fetchSoRequest);
		}
		Boolean viewOrderPricingPermission = (Boolean) getSession()
				.getAttribute(VIEW_ORDER_PRICE);
		if (viewOrderPricingPermission != null) {
			fetchSoRequest.setViewOrderPricing(viewOrderPricingPermission);
		} else {
			fetchSoRequest.setViewOrderPricing(false);
		}
		if(!filterFlag && modelFromSession!=null){
			fetchSoRequest = checkFilterAvailable(fetchSoRequest,providerId);
		}
		if(model!=null){
			model.setSelectedMarkets(fetchSoRequest.getMarkets());
			model.setSelectedScheduleStatus(fetchSoRequest.getScheduleStatus());
			model.setSelectedStatus(fetchSoRequest.getStatus());
			model.setSelectedSubStatus(fetchSoRequest.getSubstatus());
			model.setSelectedProviders(fetchSoRequest.getProviderIds());
			model.setSelectedRoutedProviders(fetchSoRequest.getRoutedProviderIds());
			/*	model.setCriteria(sortCriteria);
			model.setSortOrder(sortOrder);*/
			model.setSelectedJobDoneSubStatus(fetchSoRequest.getJobDoneSubsubstatus());
			//Setting Current Orders SubStatus into Model
			model.setSelectedCurrentOrdersSubStatus(fetchSoRequest.getCurrentOrdersSubStatus());

			//R12.0 Sprint3 setting cancellations substatus in to model
			model.setSelectedCancellationsSubStatus(fetchSoRequest.getCancellationsSubStatus());

			//R12.0 Sprint4 setting Revisit needed substatus in to model
			model.setSelectedRevisitSubStatus(fetchSoRequest.getRevisitSubStatus());
		}


		getRequest().setAttribute("omModel", model);

		getSession().setAttribute("model_"+modelTab, model);

		SOWError error =null;
		FetchSOResponse response = null;
		try {
			boolean apiIndicator = Boolean.parseBoolean(applicationProperties
					.getPropertyFromDB(MPConstants.OM_API_INDICATOR));
			LOGGER.info("apiIndicator="+apiIndicator);
			if (!apiIndicator) {
				LOGGER.info("backend call");
				response = managementService.fetchOrdersForFrontEnd(
						fetchSoRequest, providerId, resourceId);
			} else {
				LOGGER.info("api call");
				response = restClient.getResponseForFetchOrders(fetchSoRequest,
						providerId, resourceId);
				if(null!=response && (null != response.getResults().getError())) {
					List<ErrorResult> errorResults = response
							.getResults().getError();
					if (null != errorResults) {
						for (ErrorResult result : errorResults) {
							LOGGER.info(result.getMessage());
							error = new SOWError(null, result.getMessage(), EMPTY_STR);
							for(SOWError omError: omErrors){
								if(omError!=null){
									if(!(omError.getMsg().equals(result.getMessage()))){
										omErrors.add(omError);
									}
								}
							}							
							getSession().setAttribute("omApiErrors",omErrors);
						}
					}
				}

				if(null != response && response.getSoDetails() != null && !response.getSoDetails().getSoDetail().isEmpty()){
					LOGGER.info("response is not empty");
				}
			}
			// LOGGER.info(String.format("Inside OrderManagementControllerAction.loadDataGrid()...Time taken is %1$d ms",
			// System.currentTimeMillis() - startTime));
		} catch (Exception exc) {
			LOGGER.error(exc.getMessage());
		}

		ArrayList<OMServiceOrder> soList = new ArrayList<OMServiceOrder>();
		if (response != null && response.getSoDetails() != null) {
			soList = (ArrayList<OMServiceOrder>) response.getSoDetails()
					.getSoDetail();
			if (null != soList && soList.size() > 0) {
				for (OMServiceOrder order : soList) {
					ChildBidOrderListType childBidOrdersList = new ChildBidOrderListType();
					childBidOrdersList = order.getChildBidSoList();
					List<ChildBidOrder> childBidOrders = new ArrayList<ChildBidOrder>();
					if (childBidOrdersList != null) {
						childBidOrders = childBidOrdersList.getChildBidOrder();
						for (ChildBidOrder childBidOrder : childBidOrders) {
							if (StringUtils.isNotBlank(childBidOrder
									.getOfferExpirationDate())) {
								childBidOrder.setFormattedExpirationDate();
							}
						}
					}
				}
			}
			setAllFiltersInSession(response);

		}
		if (soList != null) {
			int size = soList.size();
			int parentSize=0;
			int childSize=0;
			if(soList.size() > 0)
			{
				for (OMServiceOrder order : soList) {
					if(null!=order.getChildOrderList()&& null!=order.getChildOrderList().getChildOrder() && order.getChildOrderList().getChildOrder().size()>0)
					{
						parentSize=parentSize+1;
						childSize=childSize+order.getChildOrderList().getChildOrder().size();
					}

				}
				size=size+childSize-parentSize;
			}

			getSession().setAttribute("currentSOCount", size);
		} else {
			getSession().setAttribute("currentSOCount", 0);
		}

		/* To show/hide View More Link */
		int countLimit = DEAFULT_NUMBER_OF_ORDERS_OM;
		if ((soCount % DEAFULT_NUMBER_OF_ORDERS_OM) == 0) {
			countLimit += soCount;
		} else {
			countLimit = (soCount - (soCount % DEAFULT_NUMBER_OF_ORDERS_OM)) + DEAFULT_NUMBER_OF_ORDERS_OM;
		}

		setAttribute("countLimit", countLimit);

		getSession().setAttribute(
				"totalTabCount",
				response.getTotalSOCount() == null ? 0 : response
						.getTotalSOCount());
		getSession().setAttribute(
				"totalTabCountWithoutFilters",
				response.getSoCountWithoutFilters() == null ? 0 : response
						.getSoCountWithoutFilters());
		
		/*//SL-21465
		//Below code is to set Order type as Estimation Request In case of It's Estimation Type of Order
		if(soList != null){
			for(OMServiceOrder serviceOrder : soList){
				if(serviceOrder.getIsEstimationRequest()){
					serviceOrder.setSoAttribute("Estimation Request");
				} 
			}
		}*/
		
		if (soList != null) {
			long startTime1 = System.currentTimeMillis();
			loadActions(soList, omDisplayTab);
			omTabDTO.setSoList(soList);
			omTabDTO.setViewOrderPricing((Boolean) getSession().getAttribute(
					VIEW_ORDER_PRICE));
			LOGGER.info(String.format(
					"Loading the actions...Time taken is %1$d ms",
					System.currentTimeMillis() - startTime1));
		}
		setAttribute("omTabDTO", omTabDTO);
		getSession().removeAttribute(SO_COUNT);
		// LOGGER.info("Clicked on the tab ::"+omDisplayTab);
		LOGGER.info(String
				.format("Exiting OrderManagementControllerAction.loadDataGrid()...Time taken is %1$d ms",
						System.currentTimeMillis() - startTime));

		//Narendra Test
		//saveSoEstimate();
		//displayAddEstimateSOPopUp();
		//saveSoEstimate();
	}

	private FetchSORequest checkFilterAvailable(FetchSORequest fetchSoRequest, String providerId) {

		FilterVO availableFilters = managementService.getFilters(fetchSoRequest, providerId);
		if(fetchSoRequest!=null && availableFilters!=null){
			if(fetchSoRequest.getMarkets()!=null){
				fetchSoRequest = checkMarketValueAvailable(fetchSoRequest,availableFilters);
			}
			if(fetchSoRequest.getProviderIds()!=null){
				List<Integer> providers = checkProviderValueAvailable(fetchSoRequest.getProviderIds(), availableFilters);
				fetchSoRequest.setProviderIds(providers);
			}
			if(fetchSoRequest.getRoutedProviderIds()!=null){
				List<Integer> providers = checkRoutedProviderValueAvailable(fetchSoRequest.getRoutedProviderIds(), availableFilters);
				fetchSoRequest.setRoutedProviderIds(providers);
			}
			if(fetchSoRequest.getScheduleStatus()!=null){
				fetchSoRequest = checkScheduleStatusAvailable(fetchSoRequest, availableFilters);
			}
			if(fetchSoRequest.getStatus()!=null){
				fetchSoRequest = checkStatusValueAvailable(fetchSoRequest, availableFilters);			
			}
			if(fetchSoRequest.getSubstatus()!=null){
				fetchSoRequest = checkSubStatusValueAvailable(fetchSoRequest, availableFilters);			
			}//Job DOne Substatus
			if(fetchSoRequest.getJobDoneSubsubstatus()!=null){
				fetchSoRequest = checkJobDoneSubStatusValueAvailable(fetchSoRequest, availableFilters);			
			}
			//Current Orders SubStatus
			if(fetchSoRequest.getCurrentOrdersSubStatus()!=null){
				fetchSoRequest = checkCurrentOrdersSubStatusValueAvailable(fetchSoRequest, availableFilters);			
			}
			//R12.0 Sprint3 Cancellations Substatus Filter
			if(null !=fetchSoRequest.getCancellationsSubStatus()){
				fetchSoRequest = checkCancellationsSubStatusValueAvailable(fetchSoRequest, availableFilters);			
			}
			//R12.0 Sprint4 Revisit Substatus Filter
			if(null !=fetchSoRequest.getRevisitSubStatus()){
				fetchSoRequest = checkRevisitSubStatusValueAvailable(fetchSoRequest, availableFilters);			
			}
		}
		return fetchSoRequest;
	}

	private List<Integer> checkRoutedProviderValueAvailable(
			List<Integer> routedProviderIds, FilterVO availableFilters) {
		Boolean available = false;
		if(availableFilters.getRoutedProviderList()!=null){
			if(routedProviderIds!=null)
				for(Integer routedProvider : routedProviderIds){
					for(ProviderFilterVO routedProviderFilterVO : availableFilters.getRoutedProviderList()){

						if(routedProvider!=null && routedProvider.equals(routedProviderFilterVO.getId())){
							available=true;
							break;
						}

					}
					if(!available){
						routedProviderIds.remove(routedProvider);
					}
					if(routedProviderIds== null){
						break;
					}

				}
		}
		else if(routedProviderIds!=null){
			routedProviderIds = null;
		}

		return routedProviderIds;
	}

	private FetchSORequest checkMarketValueAvailable(FetchSORequest fetchSoRequest,
			FilterVO availableFilters) {
		Boolean available = false;
		if(availableFilters.getMarketList()!=null){
			if(fetchSoRequest.getMarkets()!=null)
				for(String market : fetchSoRequest.getMarkets()){
					if(market!= null){
						Integer marketId= Integer.parseInt(market);
						for(MarketFilterVO marketFilterVO : availableFilters.getMarketList()){

							if(marketId!=null && marketId.equals(marketFilterVO.getId())){
								available=true;
								break;
							}

						}
						if(!available){
							fetchSoRequest.getMarkets().remove(market);
						}
						if(fetchSoRequest.getMarkets()== null || fetchSoRequest.getMarkets().isEmpty()){
							break;
						}
					}

				}
		}
		else if(fetchSoRequest.getMarkets()!=null){
			fetchSoRequest.setMarkets(null);
		}

		return fetchSoRequest;
	}
	private List<Integer> checkProviderValueAvailable(List<Integer> providers,
			FilterVO availableFilters) {
		Boolean available = false;
		if(availableFilters.getProviderList()!=null){
			if(providers!=null)
				for(Integer provider : providers){
					for(ProviderFilterVO providerFilterVO : availableFilters.getProviderList()){

						if(provider!=null && provider.equals(providerFilterVO.getId())){
							available=true;
							break;
						}

					}
					if(!available){
						providers.remove(provider);
					}
					if(providers== null || providers.isEmpty()){
						break;
					}

				}
		}
		else if(providers!=null){
			providers = null;
		}

		return providers;
	}
	private FetchSORequest checkScheduleStatusAvailable(FetchSORequest fetchSoRequest,
			FilterVO availableFilters) {
		Boolean available = false;
		if(availableFilters.getScheduleList()!=null){
			if(fetchSoRequest.getScheduleStatus()!=null)
				for(String scheduleStatus : fetchSoRequest.getScheduleStatus()){
					if(scheduleStatus!= null){
						Integer scheduleStatusId= Integer.parseInt(scheduleStatus);
						for(ScheduleStatusFilterVO scheduleStatusFilterVO : availableFilters.getScheduleList()){

							if(scheduleStatusId!=null && scheduleStatusId.equals(scheduleStatusFilterVO.getId())){
								available=true;
								break;
							}

						}
						if(!available){
							fetchSoRequest.getScheduleStatus().remove(scheduleStatus);
						}
						if(fetchSoRequest.getScheduleStatus()== null || fetchSoRequest.getScheduleStatus().isEmpty()){
							break;
						}
					}

				}
		}
		else if(fetchSoRequest.getScheduleStatus()!=null){
			fetchSoRequest.setScheduleStatus(null);
		}

		return fetchSoRequest;
	}
	private FetchSORequest checkStatusValueAvailable(FetchSORequest fetchSoRequest,
			FilterVO availableFilters) {
		Boolean available = false;
		if(availableFilters.getStatusList()!=null){
			if(fetchSoRequest.getStatus()!=null)
				for(String status : fetchSoRequest.getStatus()){
					if(status!= null){
						Integer statusId= Integer.parseInt(status);
						for(StatusFilterVO statusFilterVO : availableFilters.getStatusList()){

							if(statusId!=null && statusId.equals(statusFilterVO.getId())){
								available=true;
								break;
							}

						}
						if(!available){
							fetchSoRequest.getStatus().remove(status);
						}
						if(fetchSoRequest.getStatus()== null || fetchSoRequest.getStatus().isEmpty()){
							break;
						}
					}


				}
		}
		else if(fetchSoRequest.getStatus()!=null){
			fetchSoRequest.setStatus(null);
		}

		return fetchSoRequest;
	}
	private FetchSORequest checkSubStatusValueAvailable(FetchSORequest fetchSoRequest,
			FilterVO availableFilters) {
		Boolean available = false;
		if(availableFilters.getSubStatusList()!=null){
			if(fetchSoRequest.getSubstatus()!=null)
				for(String subStatus : fetchSoRequest.getSubstatus()){
					for(SubStatusFilterVO subStatusFilterVO : availableFilters.getSubStatusList()){

						if(subStatus != null && subStatus.equals("ZERO_PRICE_BID")){
							subStatus="Bid Request";
						}
						if(subStatus!=null && subStatus.equals(subStatusFilterVO.getDescr())){
							available=true;
							break;
						}

					}
					if(!available){
						fetchSoRequest.getSubstatus().remove(subStatus);
					}
					if(fetchSoRequest.getSubstatus()== null || fetchSoRequest.getSubstatus().isEmpty()){
						break;
					}

				}
		}
		else if(fetchSoRequest.getSubstatus()!=null){
			fetchSoRequest.setSubstatus(null);
		}

		return fetchSoRequest;
	}

	//JobDone Substatus list
	private FetchSORequest checkJobDoneSubStatusValueAvailable(FetchSORequest fetchSoRequest,
			FilterVO availableFilters) {
		Boolean available = false;
		if(null != availableFilters.getJobDoneSubStatusList()){
			if(null != fetchSoRequest.getJobDoneSubsubstatus())
				for(String subStatus : fetchSoRequest.getJobDoneSubsubstatus()){
					for(JobDoneSubStatusFilterVO filterVO : availableFilters.getJobDoneSubStatusList()){
						Integer subStatusId = Integer.parseInt(subStatus);
						if(null != subStatusId && subStatusId.equals(filterVO.getId())){
							available=true;
							break;
						}
					}
					if(!available){
						fetchSoRequest.getJobDoneSubsubstatus().remove(subStatus);
					}
					if(fetchSoRequest.getJobDoneSubsubstatus()== null || fetchSoRequest.getJobDoneSubsubstatus().isEmpty()){
						break;
					}

				}

		}
		else if(null != fetchSoRequest.getJobDoneSubsubstatus()){
			fetchSoRequest.setJobDoneSubsubstatus(null);
		}

		return fetchSoRequest;
	}

	//Current Orders SubStatus list
	private FetchSORequest checkCurrentOrdersSubStatusValueAvailable(
			FetchSORequest fetchSoRequest, FilterVO availableFilters) {
		Boolean available = false;
		if (null != availableFilters.getCurrentOrdersSubStatusList() ) {
			if (null != fetchSoRequest.getCurrentOrdersSubStatus() )
				for (String subStatus : fetchSoRequest
						.getCurrentOrdersSubStatus()) {
					for (CurrentOrdersSubStatusFilterVO filterVO : availableFilters
							.getCurrentOrdersSubStatusList()) {
						Integer subStatusId = Integer.parseInt(subStatus);
						if (null != subStatusId 
								&& subStatusId.equals(filterVO.getId())) {
							available = true;
							break;
						}
					}
					if (!available) {
						fetchSoRequest.getCurrentOrdersSubStatus().remove(
								subStatus);
					}
					if (fetchSoRequest.getCurrentOrdersSubStatus() == null
							|| fetchSoRequest.getCurrentOrdersSubStatus()
							.isEmpty()) {
						break;
					}

				}

		} else if (null != fetchSoRequest.getCurrentOrdersSubStatus() ) {
			fetchSoRequest.setCurrentOrdersSubStatus(null);
		}

		return fetchSoRequest;
	}
	//R12.0 Sprint3 Adding substatus filter to cancellations tab
	/**
	 * the method checks whether the filter is updating with SO availability changes
	 * @param fetchSoRequest
	 * @param availableFilters
	 * @return
	 */

	private FetchSORequest checkCancellationsSubStatusValueAvailable(
			FetchSORequest fetchSoRequest, FilterVO availableFilters) {
		Boolean available = false;
		if(null != availableFilters){
			if (null != availableFilters.getCancellationsSubStatusList()) {
				if(null != fetchSoRequest){
					if (null != fetchSoRequest.getCancellationsSubStatus())
						for (String subStatus : fetchSoRequest
								.getCancellationsSubStatus()) {
							for (CancellationsSubStatusFilterVO filterVO: availableFilters
									.getCancellationsSubStatusList()) {

								Integer subStatusId=Integer.parseInt(subStatus);

								if (null  != subStatusId 
										&& subStatusId.equals(filterVO.getId())) {
									available = true;
									break;
								}
							}
							if (!available) {
								fetchSoRequest.getCancellationsSubStatus().remove(
										subStatus);
							}
							if (fetchSoRequest.getCancellationsSubStatus() == null
									|| fetchSoRequest.getCancellationsSubStatus()
									.isEmpty()) {
								break;
							}

						}
				}

			} else if (null != fetchSoRequest.getCancellationsSubStatus()) {
				fetchSoRequest.setCancellationsSubStatus(null);
			}
		}

		return fetchSoRequest;
	}


	//R12.0 Sprint4 Adding substatus filter to Revisit tab
	/**
	 * the method checks whether the filter is updating with SO availability changes
	 * @param fetchSoRequest
	 * @param availableFilters
	 * @return
	 */

	private FetchSORequest checkRevisitSubStatusValueAvailable(
			FetchSORequest fetchSoRequest, FilterVO availableFilters) {
		Boolean available = false;
		if(null != availableFilters){
			if (null != availableFilters.getRevisitSubStatusList()) {
				if(null != fetchSoRequest){
					if (null != fetchSoRequest.getRevisitSubStatus())
						for (String subStatus : fetchSoRequest
								.getRevisitSubStatus()) {
							for (RevisitSubStatusFilterVO filterVO: availableFilters
									.getRevisitSubStatusList()) {

								Integer subStatusId=Integer.parseInt(subStatus);

								if (null  != subStatusId 
										&& subStatusId.equals(filterVO.getId())) {
									available = true;
									break;
								}
							}
							if (!available) {
								fetchSoRequest.getRevisitSubStatus().remove(
										subStatus);
							}
							if (fetchSoRequest.getRevisitSubStatus() == null
									|| fetchSoRequest.getRevisitSubStatus()
									.isEmpty()) {
								break;
							}

						}
				}

			} else if (null != fetchSoRequest.getRevisitSubStatus()) {
				fetchSoRequest.setRevisitSubStatus(null);
			}
		}

		return fetchSoRequest;
	}



	/*This method will set all the filter values in session scope.*/
	private void setAllFiltersInSession(FetchSOResponse response) {
		if (response.getMarketList() != null) {
			getSession().setAttribute("marketList",
					response.getMarketList().getMarkets());
			setFilterInSession("marketList", response.getMarketList().getMarkets()); 
		} else {
			getSession().setAttribute("marketList", null);
		}

		if (response.getProviderList() != null) {
			getSession().setAttribute("providerList",
					response.getProviderList().getProviders());
		} else {
			getSession().setAttribute("providerList", null);
		}

		if (response.getRoutedProviderList() != null) {
			getSession().setAttribute("routedProviderList",
					response.getRoutedProviderList().getProviders());
		} else {
			getSession().setAttribute("routedProviderList", null);
		}

		if (response.getStatusList() != null) {
			getSession().setAttribute("statusList",
					response.getStatusList().getStatus());
		} else {
			getSession().setAttribute("statusList", null);
		}
		if (response.getSubstatusList() != null) {
			getSession().setAttribute("subStatusList",
					response.getSubstatusList().getSubstatus());
		} else {
			getSession().setAttribute("subStatusList", null);
		}
		if (response.getScheduleFilterList() != null) {
			getSession().setAttribute("scheduleStatusList",
					response.getScheduleFilterList().getSchedulestatus());
		} else {
			getSession().setAttribute("scheduleStatusList", null);
		}
		if (response.getJobDoneSubsubstatusList() != null) {
			getSession().setAttribute("jobDoneSubStatusList",
					response.getJobDoneSubsubstatusList().getJobDonesubstatus());
		} else {
			getSession().setAttribute("jobDoneSubStatusList", null);
		}
		if (response.getCurrentOrdersSubStatusList() != null) {
			getSession().setAttribute("currentOrdersSubStatusList",
					response.getCurrentOrdersSubStatusList().getCurrentOrdersSubStatus());
		} else {
			getSession().setAttribute("currentOrdersSubStatusList", null);
		}

		//R12.0 Sprint3 cancellations substatus filter

		if (null != response.getCancellationsSubStatusList()) {
			getSession().setAttribute("cancellationsSubStatusList",
					response.getCancellationsSubStatusList().getCancellationsSubStatus());
		} else {
			getSession().setAttribute("cancellationsSubStatusList", null);
		}

		//R12.0 Sprint4 Revisit substatus filter
		if (null != response.getRevisitSubStatusList()) {
			getSession().setAttribute("revisitSubStatusList",
					response.getRevisitSubStatusList().getRevisitSubStatus());
		} else {
			getSession().setAttribute("revisitSubStatusList", null);
		}
	}



	/*Sets a filter in session scope*/
	private void setFilterInSession(String filterListName, List filter){
		if (filter != null) {
			getSession().setAttribute(filterListName, filter);
		} else {
			getSession().setAttribute(filterListName, null);
		}
	}

	/*Method to set filter parameters to API request*/
	private void setFilterParametersToRequest(OrderManagementTabDTO model,
			FetchSORequest fetchSoRequest) {
		fetchSoRequest.setMarkets(model.getSelectedMarkets());
		fetchSoRequest.setProviderIds(model.getSelectedProviders());
		fetchSoRequest.setRoutedProviderIds(model.getSelectedRoutedProviders());
		fetchSoRequest.setIncludeUnassignedInd(model.getIncludeUnassigned()); //When Include unassigned order check box is checked.
		fetchSoRequest.setJobDoneSubsubstatus(model.getSelectedJobDoneSubStatus()); 
		fetchSoRequest.setCurrentOrdersSubStatus(model.getSelectedCurrentOrdersSubStatus());

		//R12.0 Sprint3 Cancellations substatus filter
		fetchSoRequest.setCancellationsSubStatus(model.getSelectedCancellationsSubStatus());

		//R12.0 Sprint4 Revisit substatus filter
		fetchSoRequest.setRevisitSubStatus(model.getSelectedRevisitSubStatus());

		List<String> scheduleStatusList = model.getSelectedScheduleStatus();
		if (null != scheduleStatusList  && scheduleStatusList.size() == 1) {
			String scheduleStatus = scheduleStatusList.get(0);
			if (SELECT_ITEM_DEFAULT.equals(scheduleStatus) || "0".equals(scheduleStatus)) {
				fetchSoRequest.setScheduleStatus(null);
			} else {
				fetchSoRequest.setScheduleStatus(model
						.getSelectedScheduleStatus());
			}
		} else {
			fetchSoRequest.setScheduleStatus(model
					.getSelectedScheduleStatus());
		}

		List<String> subStatusList = model.getSelectedSubStatus();
		if (subStatusList != null && subStatusList.size() == 1) {
			String subStatus = subStatusList.get(0);
			if (SELECT_ITEM_DEFAULT.equals(subStatus) || "0".equals(subStatus)) {
				fetchSoRequest.setSubstatus(null);
			} else {
				fetchSoRequest.setSubstatus(model.getSelectedSubStatus());
			}
		} else {
			fetchSoRequest.setSubstatus(model.getSelectedSubStatus());
		}

		List<String> statusList = model.getSelectedStatus();
		if (statusList != null && statusList.size() == 1) {
			String status = statusList.get(0);
			if (SELECT_ITEM_DEFAULT.equals(status) || "0".equals(status)) {
				fetchSoRequest.setStatus(null);
			} else {
				fetchSoRequest.setStatus(model.getSelectedStatus());
			}
		} else {
			fetchSoRequest.setStatus(model.getSelectedStatus());
		}

		String appointmentType = model.getAppointmentType();
		if (appointmentType != null && SELECT_ITEM_DEFAULT.equals(appointmentType)) {
			fetchSoRequest.setAppointmentType(null);
		} else {
			fetchSoRequest.setAppointmentType(model.getAppointmentType());
			setAppointmentdateFilter(model, fetchSoRequest);
		}
		//JobDone substatus
		/*List<String> jobDoneSubStatusList = model.getSelectedJobDoneSubStatus();
		if (jobDoneSubStatusList != null && jobDoneSubStatusList.size() == 1) {
			String subStatus = jobDoneSubStatusList.get(0);
			if (SELECT_ITEM_DEFAULT.equals(subStatus) || "0".equals(subStatus)) {
				fetchSoRequest.setJobDoneSubsubstatus(null);
			} else {
				fetchSoRequest.setJobDoneSubsubstatus(model.getSelectedJobDoneSubStatus());
			}
		} else {
			fetchSoRequest.setJobDoneSubsubstatus(model.getSelectedJobDoneSubStatus());
		}*/
	}

	private void setAppointmentdateFilter(OrderManagementTabDTO model,
			FetchSORequest fetchSoRequest){
		String startDate = null;
		String endDate = null;
		if (null != model) {
			try {
				if (StringUtils.isNotBlank(model.getAppointmentStartDate())
						&& StringUtils
						.isNotBlank(model.getAppointmentEndDate())) {
					SimpleDateFormat format1 = new SimpleDateFormat(
							DATE_FORMAT_DISPLAYED);
					SimpleDateFormat format2 = new SimpleDateFormat(
							DATE_FORMAT_IN_DB);
					startDate = getTimeStampFromStr(
							model.getAppointmentStartDate(), DATE_FORMAT_DISPLAYED,
							DATE_FORMAT_IN_DB);
					startDate = format2.format(format1.parse(model
							.getAppointmentStartDate()));
					endDate = format2.format(format1.parse(model
							.getAppointmentEndDate()));
				}
			} catch (ParseException ex) {
				LOGGER.debug("ParseException in OrderManagementControllerAction.setAppointmentdateFilter() "
						+ "due to " + ex.getMessage());
			}
		}
		fetchSoRequest.setAppointmentStartDate(startDate);
		fetchSoRequest.setAppointmentEndDate(endDate);
	}
	// this method is used to set the filter values
	private OrderManagementTabDTO getFilterValues(OrderManagementTabDTO model) {
		if (null != model) {
			// setting markets
			if (null != model.getSelectedMarkets()
					&& !model.getSelectedMarkets().isEmpty()) {
				List<String> markets = new ArrayList<String>();
				for (String market : model.getSelectedMarkets()) {
					if (StringUtils.isNotBlank(market)) {
						markets.add(market);
						filterFlag = Boolean.TRUE;
					}
				}
				model.setSelectedMarkets(markets);
			}
			// setting providers
			if (null != model.getSelectedProviders()
					&& !model.getSelectedProviders().isEmpty()) {
				List<Integer> providers = new ArrayList<Integer>();
				for (Integer provider : model.getSelectedProviders()) {
					if (null != provider) {
						providers.add(provider);
						filterFlag = Boolean.TRUE;
					}
				}
				if(providers.isEmpty()){
					model.setIncludeUnassigned(Boolean.FALSE);
				}
				model.setSelectedProviders(providers);
			}
			//Routed provider for assign prov tab
			if (null != model.getSelectedRoutedProviders()
					&& !model.getSelectedRoutedProviders().isEmpty()) {
				List<Integer> providers = new ArrayList<Integer>();
				for (Integer provider : model.getSelectedRoutedProviders()) {
					if (null != provider) {
						providers.add(provider);
						filterFlag = Boolean.TRUE;
					}
				}
				if(!providers.isEmpty()){
					LOGGER.info("When providers selected :"+model.getIncludeUnassigned());
				}
				model.setSelectedRoutedProviders(providers);
			}

			// setting sub status
			if (null != model.getSelectedSubStatus()
					&& !model.getSelectedSubStatus().isEmpty()) {
				List<String> subStatus = new ArrayList<String>();
				for (String status : model.getSelectedSubStatus()) {
					if (StringUtils.isNotBlank(status)){
						if ("Bid Request".equals(status)) {
							filterFlag = Boolean.TRUE;
							subStatus.add("ZERO_PRICE_BID");
						} else if (!SELECT_ITEM_DEFAULT.equals(status)
								&& !"-1".equals(status)) {
							filterFlag = Boolean.TRUE;
							subStatus.add(status);
						}
					}
				}
				model.setSelectedSubStatus(subStatus);
			}

			// setting schedule status
			if (null != model.getSelectedScheduleStatus()
					&& !model.getSelectedScheduleStatus().isEmpty()) {
				List<String> scheduleStatus = new ArrayList<String>();
				for (String status : model.getSelectedScheduleStatus()) {
					if (StringUtils.isNotBlank(status)
							&& !SELECT_ITEM_DEFAULT.equals(status)
							&& !"-1".equals(status)) {
						filterFlag = Boolean.TRUE;
						scheduleStatus.add(status);
					}
				}
				model.setSelectedScheduleStatus(scheduleStatus);
			}

			// setting status
			if (null != model.getSelectedStatus()
					&& !model.getSelectedStatus().isEmpty()) {
				List<String> soStatus = new ArrayList<String>();
				for (String status : model.getSelectedStatus()) {
					if (StringUtils.isNotBlank(status)
							&& !SELECT_ITEM_DEFAULT.equals(status)
							&& !"-1".equals(status)) {
						filterFlag = Boolean.TRUE;
						soStatus.add(status);
					}
				}
				model.setSelectedStatus(soStatus);
			}
			// setting Jobdone Substatus
			if (null != model.getSelectedJobDoneSubStatus()
					&& !model.getSelectedJobDoneSubStatus().isEmpty()) {
				List<String> jobDoneSubStatus = new ArrayList<String>();
				for (String status : model.getSelectedJobDoneSubStatus()) {
					if (StringUtils.isNotBlank(status)) {
						filterFlag = Boolean.TRUE;
						jobDoneSubStatus.add(status);
					}
				}
				model.setSelectedJobDoneSubStatus(jobDoneSubStatus);
			}
			//setting Current Orders SubStatus
			if (null != model.getSelectedCurrentOrdersSubStatus()
					&& !model.getSelectedCurrentOrdersSubStatus().isEmpty()) {
				List<String> currentOrdersSubStatus = new ArrayList<String>();
				for (String status : model.getSelectedCurrentOrdersSubStatus()) {
					if (StringUtils.isNotBlank(status)) {
						filterFlag = Boolean.TRUE;
						currentOrdersSubStatus.add(status);
					}
				}
				model.setSelectedCurrentOrdersSubStatus(currentOrdersSubStatus);
			}

			//R12.0 Sprint3 setting cancellations substatus
			if (null != model.getSelectedCancellationsSubStatus()
					&& !model.getSelectedCancellationsSubStatus().isEmpty()) {
				List<String> cancellationsSubStatus = new ArrayList<String>();
				for (String status : model.getSelectedCancellationsSubStatus()) {
					if (StringUtils.isNotBlank(status)) {
						filterFlag = Boolean.TRUE;
						cancellationsSubStatus.add(status);
					}
				}
				model.setSelectedCancellationsSubStatus(cancellationsSubStatus);
			}

			//R12.0 Sprint4 setting revisit needed substatus
			if (null != model.getSelectedRevisitSubStatus()
					&& !model.getSelectedRevisitSubStatus().isEmpty()) {
				List<String> revisitSubStatus = new ArrayList<String>();
				for (String status : model.getSelectedRevisitSubStatus()) {
					if (StringUtils.isNotBlank(status)) {
						filterFlag = Boolean.TRUE;
						revisitSubStatus.add(status);
					}
				}
				model.setSelectedRevisitSubStatus(revisitSubStatus);
			}


			if (EMPTY_STR.equals(model.getAppointmentStartDate())) {
				model.setAppointmentStartDate(null);
			}
			else if(null!= model.getAppointmentStartDate()){
				filterFlag = Boolean.TRUE;
			}
			if (EMPTY_STR.equals(model.getAppointmentEndDate())) {
				model.setAppointmentEndDate(null);
			}
			else if(null!= model.getAppointmentEndDate()){
				filterFlag = Boolean.TRUE;
			}
			if (EMPTY_STR.equals(model.getAppointmentType())
					|| SELECT_ITEM_DEFAULT.equals(model.getAppointmentType())
					&& "-1".equals(model.getAppointmentType())) {
				model.setAppointmentType(null);
			}
			else if(null!= model.getAppointmentType()){
				filterFlag = Boolean.TRUE;
			}

		}
		return model;
	}

	/**
	 * This method returns the the count of Service Orders for each tabs in
	 * Order Management as a list for the the currently logged in provider.<br>
	 * The method will invoke getTabCount API.
	 * 
	 * @return List<OrderManagmentTabTitleCount>
	 * */
	private List<OrderManagmentTabTitleCount> getSOCountForTabs() {
		List<OrderManagmentTabTitleCount> tabTitleCounts = (List) getSession().getAttribute(TAB_COUNT);
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
		LOGGER.info("Current tab : " + getRequest().getParameter(ACTIVE_TAB));
		String providerId = soContxt.getCompanyId().toString();
		String resourceId = soContxt.getVendBuyerResId().toString();
		GetTabsResponse tabResponse = null;
		boolean viewOrderPricing = false;
		try {
			String roleType = soContxt.getRole();
			Map<String, UserActivityVO> activities = soContxt
					.getRoleActivityIdList();
			if (roleType.equalsIgnoreCase(PROVIDER)
					&& activities != null
					&& activities
					.containsKey(ACTIVITY_ID_PROVIDER_ORDER_PRICING_VIEW
							.toString())) {
				viewOrderPricing = true;
				LOGGER.info("View Pricing: "+viewOrderPricing);
			}
			boolean apiIndicator = Boolean.parseBoolean(applicationProperties
					.getPropertyFromDB(MPConstants.OM_API_INDICATOR));
			LOGGER.info("API Indicator: "+apiIndicator);
			if (!apiIndicator) {
				LOGGER.info("Not Calling API");
				tabResponse = managementService.getResponseForTablistForFrontEnd(providerId,
						resourceId, viewOrderPricing);
			} else {
				LOGGER.info("Calling API");
				tabResponse = restClient.getResponseForTablist(providerId,
						resourceId);
				LOGGER.info("Returning from API");
			}
			if (null != tabResponse && null != tabResponse.getTabCount()
					&& null != tabResponse.getTabCount().getTabCountList()) {
				tabTitleCounts = new ArrayList<OrderManagmentTabTitleCount>();
				List<Tab> tabListResponse = tabResponse.getTabCount()
						.getTabCountList();
				LOGGER.info("Tab Response not null");
				for (Tab tab : tabListResponse) {
					LOGGER.info("Inside for loop for set");
					OrderManagmentTabTitleCount orderManagementTab = new OrderManagmentTabTitleCount();
					orderManagementTab.setTabTitle(tab.getTabName());
					orderManagementTab.setTabCount(tab.getSoCount());
					tabTitleCounts.add(orderManagementTab);
					if (tab.getTabName().equals(omDisplayTab)) {
						int tabCnt = tab.getSoCount();
						getSession().setAttribute("totalTabCount", tabCnt);
						getSession().setAttribute(
								"totalTabCountWithoutFilters",tabCnt);
					}
				}
			}
			else if (null != tabResponse.getResults().getError()) {
				List<ErrorResult> errorResults = tabResponse.getResults()
						.getError();
				for (ErrorResult result : errorResults) {
					LOGGER.info(result.getMessage());
					SOWError responseError = null;
					responseError = new SOWError(null, result.getMessage(), EMPTY_STR);
					omErrors.add(responseError);
				}
			}
		}catch (Exception exc) {
			LOGGER.error(exc);
			exc.printStackTrace();
		}
		return tabTitleCounts;
	}

	/**
	 * Action method the refresh the count in Order management tabs.
	 * 
	 * */
	public String refreshCount() {
		String activeTab = (String) getRequest().getParameter(ACTIVE_TAB);
		getSession().setAttribute(ACTIVE_TAB, activeTab);
		List<OrderManagmentTabTitleCount> tabTitleCounts = getSOCountForTabs();
		getSession().setAttribute(TAB_COUNT, tabTitleCounts);
		return SUCCESS;
	}

	/**
	 * This method sets the total number of records under a given tab in Order
	 * Management.
	 * 
	 * @param tabName
	 *            : Name of the tab, The Current Tab.
	 * @return void.
	 * */
	private void setTotalTabCount(String tabName) {
		@SuppressWarnings("unchecked")
		List<OrderManagmentTabTitleCount> tabTitleCounts = (List<OrderManagmentTabTitleCount>) getSession()
		.getAttribute(TAB_COUNT);
		if(tabTitleCounts!=null && tabTitleCounts.size()>0){
			for (OrderManagmentTabTitleCount tabdetails : tabTitleCounts) {
				if(tabdetails!=null){
					if (tabdetails.getTabTitle().equals(tabName)) {
						int tabCnt = tabdetails.getTabCount();
						getSession().setAttribute("totalTabCount", tabCnt);
						getSession().setAttribute(
								"totalTabCountWithoutFilters",tabCnt);
						return;
					}
				}
			}
		}
	}


	/**
	 * This method sets the priority of follow up flag
	 * 
	 * @param
	 * @return String.
	 * */
	public String updateSOPriority() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String providerId = soContxt.getCompanyId().toString();
		String soId = getRequest().getParameter(SO_ID);
		String groupInd = getRequest().getParameter(GROUP_IND_PARAM);
		SOPriorityResponse priorityResponse = restClient
				.getResponseForflagUpdate(providerId, soId, groupInd);

		if (null != priorityResponse.getResults().getError()) {
			List<ErrorResult> errorResults = priorityResponse.getResults()
					.getError();
			for (ErrorResult result : errorResults) {
				LOGGER.info(result.getMessage());
				SOWError responseError = null;
				responseError = new SOWError(null, result.getMessage(), EMPTY_STR);
				omErrors.add(responseError);
			}
		}
		return SUCCESS;
	}

	/**
	 * This method is used to add notes
	 * 
	 * @param
	 * @return String.
	 * */
	public String addNotes() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String providerId = soContxt.getCompanyId().toString();
		String soId = getRequest().getParameter(SO_ID);
		String subject = getRequest().getParameter("subject").replaceAll(
				"\\s+", SPACE);
		String message = getRequest().getParameter("message").replaceAll(
				"\\s+", SPACE);
		boolean privateInd = Boolean.parseBoolean(getRequest().getParameter(
				"private"));

		// setting the input values in request
		NoteType note = new NoteType();
		note.setSubject(subject);
		note.setNoteBody(message);
		note.setPrivateInd(privateInd);
		note.setSupportInd(false);

		Identification identification = new Identification();
		identification.setId(soContxt.getVendBuyerResId());
		identification.setType(Identification.ID_TYPE_PROVIDER_RESOURCE_ID);

		SOAddNoteRequest addNoteRequest = new SOAddNoteRequest();
		addNoteRequest.setNoteType(note);
		addNoteRequest.setIdentification(identification);
		addNoteRequest.setSchemaLocation(addNoteRequest.getNamespace() + XSD);

		SOAddNoteResponse addNoteResponse = restClient.getResponseForAddNotes(addNoteRequest, providerId, soId);
		SOWError error = null;
		if (null == addNoteResponse) {
			error = new SOWError(null, "API Failed", "ID");
		} else if (null != addNoteResponse.getResults().getError()) {
			List<ErrorResult> errorResults = addNoteResponse.getResults()
					.getError();
			for (ErrorResult result : errorResults) {
				LOGGER.info(result.getMessage());
				SOWError responseError = null;
				responseError = new SOWError(null, result.getMessage(), EMPTY_STR);
				omErrors.add(responseError);
				omTabDTO.setOmErrors(omErrors);
			}
		}
		if (null != error) {
			if(!omErrors.contains(error)){
				omErrors.add(error);
				omTabDTO.setOmErrors(omErrors);
			}		
		}

		return JSON;
	}



	public void insertAddNoteNPSInhomeNotificationMessages(
			ServiceOrderNoteDTO soNoteDTO) {


	}

	/**
	 * This action method is for the view PDF pop-up load
	 * @return String
	 */
	public String loadPrintPaperWork() {
		String soIdCount;
		String maxCount;
		ServiceOrderListDTO serviceOrderListDTO = new ServiceOrderListDTO();
		List<ServiceOrderListDTO> serviceOrderListDTOs = new ArrayList<ServiceOrderListDTO>();
		if (null != getParameter(CHECKED_SO_ID_COUNT)) {
			soIdCount = getParameter(CHECKED_SO_ID_COUNT);
		} else {
			soIdCount = (String) getSession().getAttribute(
					CHECKED_SO_ID_COUNT);
		}
		if (null != getParameter(MAX_PERMISSIBLE_COUNT)) {
			maxCount = getParameter(MAX_PERMISSIBLE_COUNT);
		} else {
			maxCount = (String) getSession().getAttribute(
					MAX_PERMISSIBLE_COUNT);
		}

		if (null != maxCount && null != soIdCount) {
			Integer size = Integer.parseInt(soIdCount);
			Integer maximumCount = Integer.parseInt(maxCount);
			if (maximumCount.compareTo(size) < 0) {
				serviceOrderListDTO = new ServiceOrderListDTO();
				serviceOrderListDTO.setValue(SELECT_VALUE);
				serviceOrderListDTO.setDesc(SELECT_STRING);
				serviceOrderListDTOs.add(serviceOrderListDTO);
				int i;
				for (i = 0; i <= size - maximumCount; i = i + maximumCount) {
					serviceOrderListDTO = new ServiceOrderListDTO();
					serviceOrderListDTO.setDesc((i + 1) + TO
							+ (i + 20) + ORDERS);
					serviceOrderListDTO.setValue(i);
					serviceOrderListDTOs.add(serviceOrderListDTO);
				}
				if (i < size) {
					serviceOrderListDTO = new ServiceOrderListDTO();
					serviceOrderListDTO.setDesc((i + 1) + TO
							+ size + ORDERS);
					serviceOrderListDTO.setValue(i);
					serviceOrderListDTOs.add(serviceOrderListDTO);
				}
			}
		}
		getSession().setAttribute(SO_DROP_DOWN_OPTIONS,
				serviceOrderListDTOs);
		return SUCCESS;

	}

	/**
	 * This action method is for the view PDF button in print paperwork
	 * list
	 * @return String
	 */
	public String printPaperwork() {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String soIds;
		String options;
		List<String> checkedSoIds = new ArrayList<String>();
		List<String> checkedOptions = new ArrayList<String>();
		String[] checkedIds = null;
		String[] docOptions = null;
		if (null != getParameter(CHECKED_SO_IDS)) {
			soIds = getParameter(CHECKED_SO_IDS);
		} else {
			soIds = (String) getSession().getAttribute(
					CHECKED_SO_IDS);
		}
		checkedIds = StringUtils.split(soIds, COMMA_DELIMITER);
		for (String soId : checkedIds) {
			checkedSoIds.add(soId);
		}
		if (null != getParameter(CHECKED_OPTIONS)) {
			options = getParameter(CHECKED_OPTIONS);
		} else {
			options = (String) getSession().getAttribute(
					CHECKED_OPTIONS);
		}
		docOptions = StringUtils.split(options, COMMA_DELIMITER);

		for (String option : docOptions) {
			if (StringUtils.isNotBlank(option)) {
				if (option.equals(VIEW_CUSTOMER_COPY)) {
					checkedOptions.add(SOPDFConstants.CUSTOMER_COPY);
				} else if (option.equals(VIEW_PROVIDER_COPY)) {
					checkedOptions.add(SOPDFConstants.PROVIDER_COPY);
				}
			}
		}
		/*
		 * We need to look at the vendor_id , and buyer_id for this service
		 * order if admin than this is ok
		 */
		boolean isAssociated = false;
		List<Boolean> association = new ArrayList<Boolean>();
		String invalidSoIds = null;
		try {
			for (String soId : checkedSoIds) {
				if (_commonCriteria.getRoleId() == NEWCO_ADMIN_ROLEID) {
					isAssociated = true;
				} else {
					isAssociated = soMonitorDelegate.isAssociatedToViewSOAsPDF(
							soId, _commonCriteria.getRoleId(),
							_commonCriteria.getCompanyId());
					if (!isAssociated) {
						invalidSoIds = soId + SOPDFConstants.COMMA;
					}
				}
				association.add(isAssociated);

			}
			if (StringUtils.isNotBlank(invalidSoIds)) {
				int end = invalidSoIds.lastIndexOf(SOPDFConstants.COMMA);
				invalidSoIds = invalidSoIds.substring(0, end);
			}

			ServletOutputStream out = getResponse().getOutputStream();

			if (!(association.contains(false))) {
				Long start = System.currentTimeMillis();
				LOGGER.info("Started:" + start);
				baos = soMonitorDelegate.printPaperwork(checkedSoIds,
						checkedOptions);
				LOGGER.info("Ended:" + (System.currentTimeMillis() - start));
				int size = 0;
				if (baos != null) {
					size = baos.size();

					getResponse().setContentType("application/pdf");
					getResponse().setContentLength(size);
					getResponse().setHeader("Expires", "0");
					getResponse().setHeader("Cache-Control",
							"must-revalidate, post-check=0, pre-check=0");
					getResponse().setHeader("Pragma", "public");
					baos.writeTo(out);
				} else {
					out.println("<html><body><h3>PDF cannot be displayed at this moment</h3></body><html>");
				}
			} else {
				LOGGER.error("User is not authorized to view requested SO UserId -->"
						+ _commonCriteria.getCompanyId()
						+ "  Role Type -->"
						+ _commonCriteria.getRoleType()
						+ "  trying to view SO Id -->" + invalidSoIds);
				out.println("<html><body><h3>You are not authorized to view the requested Service Order</h3></body><html>");

			}

			out.flush();
		} catch (IOException e) {
			LOGGER.error("Unexpected error while generating print paperwork:"
					+ e);
		} catch (Exception e) {
			LOGGER.error("Unexpected error while generating print paperwork:"
					+ e);
		}
		return SUCCESS;

	}

	/**
	 * This method is used to assign provider
	 * 
	 * @param
	 * @return String.
	 * */
	public String assignProvider() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String firmId = soContxt.getCompanyId().toString();
		String soId = getRequest().getParameter(SO_ID);
		Integer resourceId = Integer.parseInt(getRequest().getParameter(
				"provider"));
		SOWError error = null;
		Identification identification = new Identification();
		identification.setId(soContxt.getVendBuyerResId());
		identification.setType(Identification.ID_TYPE_PROVIDER_RESOURCE_ID);
		if (TRUE.equalsIgnoreCase(getRequest().getParameter(REASSIGN))) {
			ReassignSORequest reAssignRequest = new ReassignSORequest();
			reAssignRequest.setResourceId(resourceId);
			reAssignRequest.setReassignComment(REASSIGN);
			reAssignRequest.setIdentification(identification);
			try {
				ReassignSOResponse reassignResponse = restClient
						.getResponseForReAssignProvider(reAssignRequest,
								firmId, soId);
				if (null != reassignResponse
						&& null != reassignResponse.getResults()) {
					List<ErrorResult> errorResults = reassignResponse
							.getResults().getError();
					if (null != errorResults) {
						for (ErrorResult result : errorResults) {
							LOGGER.info(result.getMessage());
							error = new SOWError(null, result.getMessage(), EMPTY_STR);
							if(!omErrors.contains(error)){
								omErrors.add(error);
								omTabDTO.setOmErrors(omErrors);
							}
						}
						return JSON;
					}
				}
			} catch (Exception exc) {
				LOGGER.error(exc.getMessage());
				error = new SOWError(null, exc.getMessage(), EMPTY_STR);
			}

		} else if (FALSE.equalsIgnoreCase(getRequest().getParameter(REASSIGN))) {
			// setting the request object
			SOAssignProviderRequest assignRequest = new SOAssignProviderRequest();
			assignRequest.setIdentification(identification);
			assignRequest.setResourceId(resourceId);
			assignRequest.setSchemaLocation(ASSIGN_PROVIDER_REQUEST_SCHEMA);
			try {
				SOAssignProviderResponse assignResponse = restClient.getResponseForAssignProvider(assignRequest, firmId,soId);
				if (null != assignResponse
						&& null != assignResponse.getResults()) {
					List<ErrorResult> errorResults = assignResponse
							.getResults().getError();
					if (null != errorResults) {
						for (ErrorResult result : errorResults) {
							LOGGER.info(result.getMessage());
							error = new SOWError(null, result.getMessage(), EMPTY_STR);
							if(!omErrors.contains(error)){
								omErrors.add(error);
								omTabDTO.setOmErrors(omErrors);
							}
						}
						return JSON;
					}
				}
			} catch (Exception exc) {
				LOGGER.error(exc.getMessage());
				error = new SOWError(null, exc.getMessage(), EMPTY_STR);
			}
		}
		if (null != error) {
			if(!omErrors.contains(error)){
				omErrors.add(error);
				omTabDTO.setOmErrors(omErrors);
			}
		} else if (TRUE.equalsIgnoreCase(getRequest().getParameter(REASSIGN))) {
			omTabDTO.setResult("Service Order #" + soId
					+ " is successfully Re-assigned");
		} else {
			omTabDTO.setResult("Service Order #" + soId
					+ " is Assigned successfully");
		}
		return JSON;
	}

	/**
	 * Method to fetch providers and reason codes to reject an order
	 * 
	 * @return String
	 */
	public String displayRejectPopup() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String vendorId = soContxt.getCompanyId().toString();
		setAttribute("vendorId", vendorId);
		String soId = getRequest().getParameter(SO_ID);
		getRequest().setAttribute("sorejectId", soId);
		// Setting grouped ORder indicator
		// 1 = Is a group SO
		// 0 = Individual SO
		String groupInd = getRequest().getParameter(GROUP_IND_PARAM);
		if (StringUtils.isBlank(groupInd)
				|| !GROUPED_SO_IND.equalsIgnoreCase(groupInd)) {
			groupInd = "0";
		}
		GetReasonCodesResponse reasonCoderesponse = null;
		List<EligibleProvider> eligibleProviders = null;
		List<ReasonCode> reasonCodes = null;
		try {
			String errMessage = setEligibleProvidersInSession(vendorId, soId,
					groupInd);
			reasonCoderesponse = restClient
					.getResponseForReasonCodes(SOConstants.PROVIDER_RESP_REJECTED
							.toString());
			if (null != reasonCoderesponse) {
				ReasonCodeBean reasonCodeBean = reasonCoderesponse
						.getReasonCodeBean();
				if (null != reasonCodeBean
						&& null != reasonCodeBean.getReasonList()
						&& 0 < reasonCodeBean.getReasonList().size()) {
					reasonCodes = reasonCodeBean.getReasonList();
					reasonCodes = null == reasonCodes ? new ArrayList<ReasonCode>()
							: reasonCodes;
				} else if (null != reasonCoderesponse.getResults().getError()) {
					try {
						errMessage = reasonCoderesponse.getResults().getError()
								.get(0).getMessage();
						SOWError error = new SOWError(null, errMessage, EMPTY_STR);
						omErrors.add(error);
					} catch (Exception e) {
						errMessage = e.getMessage();
					} finally {
						setAttribute("reasonCodeApiErr", errMessage);
					}
				}
				setAttribute("eligibleProviders", eligibleProviders);
				getSession().setAttribute("reasonCodes", reasonCodes);
			} else {
				LOGGER.error("API failed.");
				// TODO
			}
			this.setAttribute("omApiErrors", omErrors);

		} catch (Exception e) {
			LOGGER.error(e);
			// TODO
		}
		return SUCCESS;
	}

	/**
	 * Controller method that will be invoked when provider clicks Reject button
	 * in Order management- Reject Order pop up.
	 * <p>
	 * It calls API to reject SO. Error/warning messages are populated.
	 * */
	public String rejectServiceOrder() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		SOWError error = null;
		String resourceId = soContxt.getVendBuyerResId().toString();
		/* Call validation method */
		error = validateRejectSORequest();
		if (null != error) {
			if(!omErrors.contains(error)){
				omErrors.add(error);
				omTabDTO.setOmErrors(omErrors);
			}
			return JSON;
		}
		/* Format the resource Ids */
		List<Integer> resourceIds = getCheckedResourceID();
		RejectSORequest rejectSORequest = new RejectSORequest();
		String reasonCodeId = getParameter("rcId");
		String soId = getRequest().getParameter(SO_ID);
		// Reason code are set in session while loading the Reject Order Pop up.
		List<ReasonCode> reasonCodes = (List<ReasonCode>) getSession().getAttribute("reasonCodes");
		rejectSORequest.setReasonCommentDesc(getRequest().getParameter(
				"rejectCommentDesc"));
		LOGGER
		.info("Inside OrderManagementControllerAction.java method rejectSORequest.getReasonCommentDesc() returns: "
				+ rejectSORequest.getReasonCommentDesc());
		rejectSORequest.setReasonId(reasonCodeId);
		rejectSORequest.setReasonDesc(getReasonCodeDescription(reasonCodes,
				reasonCodeId));
		Resource resources = new Resource();
		resources.setResourceId(resourceIds);
		rejectSORequest.setResourceList(resources);
		String groupInd = getRequest().getParameter(GROUP_IND_PARAM);
		/* Calls Reject SO API */
		RejectSOResponse rejectSOResponse = restClient.getResponseForRejectSO(
				rejectSORequest, resourceId, soId, groupInd);
		if (null == rejectSOResponse) {
			error = new SOWError(null, "API call failed", "ID");
		} else if (null != rejectSOResponse.getResults().getError()) {
			List<ErrorResult> errorResults = rejectSOResponse.getResults()
					.getError();
			for (ErrorResult result : errorResults) {
				LOGGER.info(result.getMessage());
				error = new SOWError(null, result.getMessage(), EMPTY_STR);
				omErrors.add(error);
				omTabDTO.setOmErrors(omErrors);
			}
			return JSON;
		}
		if (null != error) {
			omErrors.add(error);
			omTabDTO.setOmErrors(omErrors);
		} else {
			omTabDTO.setResult("Service order rejected successfully");
		}
		return JSON;
	}

	/**
	 * Method which validates Reject Service Order Request parameters.
	 * 
	 * @return SOWError : Populated with error messages.
	 *         <p>
	 *         null returned when no error/warning
	 * */
	private SOWError validateRejectSORequest() {
		SOWError error = null;
		String resourceIds = getParameter("resources");
		String reasonCodeId = getParameter("rcId");
		// Resourced should be selected
		if (StringUtils.isBlank(resourceIds)) {
			error = new SOWError(null,
					"Please select 1 or more providers first", "pro_id");
		} else if (StringUtils.isBlank(reasonCodeId)
				|| "-1".equals(reasonCodeId)) {
			// Reason for rejection should be provided.
			error = new SOWError(null, "Please select reason to reject",
					"pro_id");
		}
		return error;
	}

	/**
	 * Method to get the Reason code description from the list of Reason code
	 * objects for a given reason code ID.
	 * 
	 * @param reasonCodes
	 *            : List of {@link ReasonCode}
	 * @param reasonCodeId
	 *            : Reason code ID - String
	 * @return The reason code description. Returns empty string when reason
	 *         code ID cann't be found
	 * */
	private String getReasonCodeDescription(List<ReasonCode> reasonCodes,
			String reasonCodeId) {
		if (null != reasonCodes) {
			int rcId = Integer.valueOf(reasonCodeId);
			for (ReasonCode reasonCode : reasonCodes) {
				if (reasonCode.getReasonCodeId() == rcId) {
					return reasonCode.getReasonCode();
				}
			}
		}
		return EMPTY_STR;
	}

	/**
	 * Convert the selected list of resource Ids which is a Comma seperated
	 * string, to List of Integers.
	 * 
	 * @return List<Integer> resource Ids
	 * **/
	private List<Integer> getCheckedResourceID() {
		List<Integer> resourceIDs = new ArrayList<Integer>();
		String resourceId = null;
		resourceId = getParameter("resources");
		String[] items = resourceId.split(COMMA_DELIMITER);
		for (String resourceids : items) {
			if ((StringUtils.isNotBlank(resourceids))
					&& (StringUtils.isNumeric(resourceids))) {
				int resid = Integer.valueOf(resourceids);
				resourceIDs.add(resid);
			}
		}
		return resourceIDs;
	}

	/**
	 * Controller method for displaying Accept Order Pop Up.
	 **/
	public String displayAcceptSOPopUp() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String vendorId = soContxt.getCompanyId().toString();
		setAttribute("vendorId", vendorId);
		String soId = getRequest().getParameter(SO_ID);
		// Set group Indicator
		String groupInd = getRequest().getParameter(GROUP_IND_PARAM);
		if (StringUtils.isBlank(groupInd)
				|| !GROUPED_SO_IND.equalsIgnoreCase(groupInd)) {
			groupInd = "0";
		}
		String errMessage = EMPTY_STR;
		try {
			RetrieveServiceOrder retrieveServiceOrder = null;
			String responseFilterParam = "Schedule-Service%20Location-General-Pricing";
			retrieveServiceOrder = getServiceOrder(soId, groupInd, vendorId,
					responseFilterParam);
			if (null != retrieveServiceOrder) {
				setAttribute(SO_ID, soId);
				if (null != retrieveServiceOrder.getSectionGeneral()) {
					setAttribute("soTitle", retrieveServiceOrder
							.getSectionGeneral().getTitle());
				}
				BigDecimal maxPrice = null;
				Pricing pricing = retrieveServiceOrder.getPricing();
				if (null != pricing) {
					maxPrice = BigDecimal.valueOf(Double.valueOf(pricing
							.getLaborSpendLimit()));
					maxPrice = maxPrice.add(BigDecimal.valueOf(Double
							.valueOf(pricing.getPartsSpendLimit())));
				}
				setAttribute("maxPrice", maxPrice);
				setAttribute("serviceTime",
						formatServiceTimeForAccept(retrieveServiceOrder
								.getSchedule()));
				//SL-18955 Full Address displayed for a received order in the "Accept Service Order" widget
				String acceptInd="1";
				//Passing the accept indicator to formatServiceLocation method
				setAttribute("serviceLocation",
						formatServiceLocation(retrieveServiceOrder
								.getServiceLocation(),acceptInd));

				//check whether the order is CAR routed or not
				boolean isCarSO = false;
				if("0".equalsIgnoreCase(groupInd)){
					isCarSO = soDetailsManager.isCARroutedSO(soId);
				}
				setAttribute("routeMethod", isCarSO);
			} else {
				errMessage = "Invalid SO Id.";
				setAttribute(ERROR_ELIGIBLE_PROVIDER, errMessage);
				this.setAttribute("omApiErrors", omErrors);
				return SUCCESS;
			}
			errMessage = setEligibleProvidersForAccept(vendorId, soId, groupInd);
			this.setAttribute("omApiErrors", omErrors);

		} catch (Exception e) {
			LOGGER.error(e);
		}
		return SUCCESS;
	}


	private String setEligibleProvidersForAccept(String vendorId, String soId,
			String groupInd) {
		SOGetEligibleProviderResponse providerResponse = new SOGetEligibleProviderResponse();
		List<EligibleProvider> eligibleProviders = null;
		String errMessage = EMPTY_STR;
		try {
			providerResponse = restClient.getResponseForGetEligibleProviders(
					vendorId, soId, groupInd);
			if (null != providerResponse) {
				EligibleProviders providers = providerResponse.getEligibleProviders();
				if (null != providers) {
					eligibleProviders = providers.getEligibleProviderList();
					eligibleProviders = null == eligibleProviders ? new ArrayList<EligibleProvider>(): eligibleProviders;
				} else if (null != providerResponse.getResults().getError()) {
					try {
						errMessage = providerResponse.getResults().getError().get(0).getMessage();
						SOWError error = new SOWError(null, errMessage, EMPTY_STR);
						omErrors.add(error);
					} catch (Exception e) {
						errMessage = e.getMessage();
					} finally {
						setAttribute(ERROR_ELIGIBLE_PROVIDER, errMessage);
					}
				}
				List<EligibleProvider> eligibleProviderList=new ArrayList<EligibleProvider>();

				if(null!=providers.getEligibleProviderList() && providers.getEligibleProviderList().size()>0)
				{
					for (EligibleProvider pro : providers.getEligibleProviderList()) {
						if(!(null!=pro.getProviderRespid() && pro.getProviderRespid().intValue()==2))
						{
							eligibleProviderList.add(pro);
						}

					}
				}
				getSession().setAttribute("eligibleProviders",eligibleProviderList);
				setAttribute(ASSIGNED_ID,providerResponse.getAssignedResourceId());
			}
		} catch (Exception e) {
			setAttribute(ERROR_ELIGIBLE_PROVIDER, e.getMessage());
		}
		return errMessage;
	}

	/**
	 * The method to set Eligible providers is session.
	 * 
	 * @param vendorId
	 *            : Company ID
	 * @param soId
	 *            : Service Order Id
	 * @param groupInd
	 *            : 0 = Child SO, 1 = Group SO
	 * @return ErrorMessage : Empty String when provider list is set in session
	 *         successfully
	 * **/
	private String setEligibleProvidersInSession(String vendorId, String soId,
			String groupInd) {
		SOGetEligibleProviderResponse providerResponse = new SOGetEligibleProviderResponse();
		List<EligibleProvider> eligibleProviders = null;
		String errMessage = EMPTY_STR;
		try {
			providerResponse = restClient.getResponseForGetEligibleProviders(
					vendorId, soId, groupInd);
			if (null != providerResponse) {
				EligibleProviders providers = providerResponse.getEligibleProviders();
				if (null != providers) {
					eligibleProviders = providers.getEligibleProviderList();
					eligibleProviders = null == eligibleProviders ? new ArrayList<EligibleProvider>(): eligibleProviders;
				} else if (null != providerResponse.getResults().getError()) {
					try {
						errMessage = providerResponse.getResults().getError().get(0).getMessage();
					} catch (Exception e) {
						errMessage = e.getMessage();
					} finally {
						setAttribute(ERROR_ELIGIBLE_PROVIDER, errMessage);
					}
				}
				getSession().setAttribute("eligibleProviders",eligibleProviders);
				setAttribute(ASSIGNED_ID,providerResponse.getAssignedResourceId());
			}
		} catch (Exception e) {
			setAttribute(ERROR_ELIGIBLE_PROVIDER, e.getMessage());
		}
		return errMessage;
	}

	/**
	 * Method to fetch SO details through API call. This method will use
	 * Provider Retrieve Order API <br>
	 * to get the SO details.
	 * 
	 * @param soId
	 * @param groupInd
	 *            : 1 = Group Order, 0 = Child SO
	 * @param vendorId
	 * @param responseFilterParam
	 *            : Separated by hyphen.
	 * @return {@link RetrieveServiceOrder}, null will be returned on failure.
	 * @exception : Throws general exception. This can be thrown from the API
	 *            call.
	 * **/
	private RetrieveServiceOrder getServiceOrder(String soId, String groupInd,
			String vendorId, String responseFilterParam) throws Exception {
		RetrieveServiceOrder retrieveServiceOrder = null;
		SOWError error =null;
		try {
			responseFilterParam = null == responseFilterParam ? EMPTY_STR
					: responseFilterParam;
			SOGetResponse soGetResponse = restClient
					.getResponseForGetInfoForCall(vendorId, soId, groupInd,
							responseFilterParam);
			if (null != soGetResponse) {
				List<RetrieveServiceOrder> retrieveServiceOrderList = null;
				ServiceOrders serviceOrders = soGetResponse.getServiceOrders();
				if (null != serviceOrders
						&& null != serviceOrders.getServiceorders()) {
					retrieveServiceOrderList = serviceOrders.getServiceorders();
					if (null != retrieveServiceOrderList
							&& retrieveServiceOrderList.size() > 0) {
						retrieveServiceOrder = new RetrieveServiceOrder();
						retrieveServiceOrder = retrieveServiceOrderList.get(0);
						// TODO null check
						if (null != retrieveServiceOrder
								&& null != retrieveServiceOrder
								.getServiceLocation()
								&& null != retrieveServiceOrder.getSchedule()) {
							String zipCode = retrieveServiceOrder
									.getServiceLocation().getZip();
							String serviceStartDate = retrieveServiceOrder
									.getSchedule().getServiceDateTime1();
							String timeZone = retrieveServiceOrder
									.getSchedule().getServiceLocationTimezone();
							String actualZone = getTimeZoneFromDate(zipCode,
									serviceStartDate, timeZone);
							retrieveServiceOrder.getSchedule()
							.setServiceLocationTimezone(
									timeZone + ":" + actualZone);
						}
					}
				}
				if (null != soGetResponse.getResults().getError()) {
					List<ErrorResult> errorResults = soGetResponse
							.getResults().getError();
					if (null != errorResults) {
						for (ErrorResult result : errorResults) {
							LOGGER.info(result.getMessage());
							error = new SOWError(null, result.getMessage(), EMPTY_STR);
							omErrors.add(error);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return retrieveServiceOrder;
	}

	/**
	 * Gets the time zone (String) of the service location based on the day
	 * light saving.
	 * 
	 * @param zipCode
	 *            :
	 * @param serviceStartDate
	 *            : yyyy-MM-dd hh:mm:ss should be the format
	 * @return timeZone
	 * */
	private String getTimeZoneFromDate(String zipCode, String serviceStartDate,
			String timeZone) {
		String dlsFlag = EMPTY_STR;
		String resultTimeZone = timeZone;
		String strArr[] = serviceStartDate.split("T");
		String servDate = strArr[0];
		String servTime = strArr[1];
		try {
			dlsFlag = luDelegate.getDaylightSavingsFlg(zipCode);
		} catch (BusinessServiceException e1) {
			LOGGER.error(e1);
			dlsFlag = "N";
		}
		if ("Y".equals(dlsFlag)) {
			TimeZone tz = TimeZone.getTimeZone(timeZone);
			Timestamp timeStampDate = null;
			if (StringUtils.isNotBlank(servDate)
					&& StringUtils.isNotBlank(servTime)) {
				Date date = stringToDate(serviceStartDate.replace("T", SPACE),
						TIME_STAMP_FORMAT_IN_DB_TWELVE_HOUR);
				timeStampDate = new Timestamp(date.getTime());
			}
			if (null != timeStampDate) {
				boolean isDLSActive = tz.inDaylightTime(timeStampDate);
				resultTimeZone = isDLSActive ? TimezoneMapper
						.getDSTTimezone(timeZone) : TimezoneMapper
						.getStandardTimezone(timeZone);
			}
		} else {
			resultTimeZone = TimezoneMapper.getStandardTimezone(timeZone);
		}
		return resultTimeZone;
	}

	/**
	 * Method for formating Service Location to display in Accept Pop Up
	 * 
	 * @param serviceLocation
	 *            {@link Location}
	 * @return Formatted address eg: Grayslake IL, 60030
	 * */
	private String formatServiceLocation(Location serviceLocation,String acceptInd) {
		StringBuilder completeAddress = new StringBuilder(EMPTY_STR);
		//SL-18955 Full Address displayed for a received order in the "Accept Service Order" widget
		if(null!=acceptInd && acceptInd.equalsIgnoreCase("1"))
		{
			if (null != serviceLocation) {
				completeAddress = appendDelimiterForAddress(completeAddress, serviceLocation.getCity());
				completeAddress = appendDelimiterForAddress(completeAddress, serviceLocation.getState());
				completeAddress = appendDelimiterForAddress(completeAddress, serviceLocation.getZip());

			}

		}
		else
		{
			if (null != serviceLocation) {

				completeAddress = appendDelimiterForAddress(completeAddress, serviceLocation.getAddress1());
				completeAddress = appendDelimiterForAddress(completeAddress, serviceLocation.getAddress2());
				completeAddress = appendDelimiterForAddress(completeAddress, serviceLocation.getCity());
				completeAddress = appendDelimiterForAddress(completeAddress, serviceLocation.getState());
				completeAddress = appendDelimiterForAddress(completeAddress, serviceLocation.getZip());

			}
		}
		return completeAddress.toString();
	}

	private StringBuilder appendDelimiterForAddress(StringBuilder sourceAddress, String addressField){
		StringBuilder address;
		if(null == sourceAddress){
			address = new StringBuilder(EMPTY_STR);
		} else {
			address= new StringBuilder(sourceAddress.toString());
		}
		if (StringUtils.isNotBlank(addressField)) {
			if (StringUtils.isNotEmpty(address.toString())) {
				address.append(", ");
			}
			address.append(addressField);
		}

		return address;
	}

	/**
	 * This method will format Service Time using the {@link Schedule} in the
	 * format required for displaying accept Order, Assign/Re Assign.
	 * 
	 * @param schedule
	 *            {@link Schedule}
	 * @return Service Start time - End time eg: 4/15/13 - 4/20/13 at 7:30 AM -
	 *         12:00 PM (EST)
	 **/
	private String formatServiceTimeForAccept(Schedule schedule) {
		StringBuilder serviceDate = new StringBuilder(EMPTY_STR);
		StringBuilder serviceTime = new StringBuilder(EMPTY_STR);
		if (null != schedule) {
			String date1 = schedule.getServiceDateTime1();
			String date2 = schedule.getServiceDateTime2();
			if (StringUtils.isNotBlank(date1)) {
				String startArr[] = date1.split("T");
				String startDate = getTimeStampFromStr(startArr[0],
						DATE_FORMAT_IN_DB, DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
				String startTime = getTimeStampFromStr(startArr[1], TIME_FORMAT_IN_DB,
						TIME_FORMAT_DISPLAYED);
				serviceDate.append(startDate);
				serviceTime.append(startTime);
				if (StringUtils.isNotBlank(date2)) {
					String endArr[] = date2.split("T");
					String endDate = EMPTY_STR;
					String endTime = EMPTY_STR;
					endDate = getTimeStampFromStr(endArr[0], DATE_FORMAT_IN_DB,
							DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
					endTime = getTimeStampFromStr(endArr[1], TIME_FORMAT_IN_DB,
							TIME_FORMAT_DISPLAYED);
					serviceTime.append(" - ").append(endTime);
					serviceDate.append(" - ").append(endDate).append(" at ")
					.append(serviceTime);
				} else {
					serviceDate.append(" at ").append(serviceTime);
				}
				String displayTimezone=schedule.getServiceLocationTimezone().substring(schedule.getServiceLocationTimezone().lastIndexOf(':')+1, schedule.getServiceLocationTimezone().length());
				serviceDate.append(" (").append(displayTimezone).append(")");

			}
		}
		return serviceDate.toString();
	}

	/**
	 * Controller method for accepting Service Order
	 * */
	public String acceptServiceOrder() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		SOWError error = null;
		SOAcceptFirmRequest acceptToFirmRequest;
		SOAcceptResponse acceptResponse;
		Integer vendorId = soContxt.getCompanyId();
		String selectedResource = getParameter("provider");
		String groupInd = getParameter(GROUP_IND_PARAM);
		String soId = getParameter(SO_ID);
		/* Call validation method */
		boolean assignFirm = StringUtils.isBlank(selectedResource);
		error = validateAcceptRequest(selectedResource, soId, assignFirm);
		if (null != error) {
			omErrors.add(error);
			omTabDTO.setOmErrors(omErrors);
			return JSON;
		}
		acceptToFirmRequest = new SOAcceptFirmRequest();
		acceptToFirmRequest.setAcceptByFirmInd(assignFirm);
		if (!assignFirm) {
			acceptToFirmRequest
			.setResourceId(Integer.valueOf(selectedResource));
		}
		Identification identification = new Identification();
		identification.setId(soContxt.getVendBuyerResId());
		identification.setType(Identification.ID_TYPE_PROVIDER_RESOURCE_ID);
		acceptToFirmRequest.setIdentification(identification);
		try {
			acceptResponse = restClient.getResponseForAcceptOrder(
					acceptToFirmRequest, vendorId.toString(), soId, groupInd);
			if (null == acceptResponse) {
				error = new SOWError(null, "API Failed", "ID");
			} else if (null != acceptResponse.getResults().getError()) {
				List<ErrorResult> errorResults = acceptResponse.getResults()
						.getError();
				for (ErrorResult result : errorResults) {
					LOGGER.info(result.getMessage());
					error = new SOWError(null, result.getMessage(), EMPTY_STR);
					omErrors.add(error);
					omTabDTO.setOmErrors(omErrors);
				}
				return JSON;
			}
		} catch (Exception e) {
			LOGGER.error(e);
			error = new SOWError(null, e.getMessage(), "exception");
		}
		if (null != error) {
			omErrors.add(error);
			omTabDTO.setOmErrors(omErrors);
		} else if (assignFirm) {
			omTabDTO.setResult("Service Order #" + soId
					+ " is Accepted and provider is NOT yet assigned.");
		} else {
			omTabDTO.setResult("Service Order #" + soId
					+ " is Accepted and Assigned");
		}
		return JSON;
	}

	/**
	 * Validation method for Service Order Accept Request. <br>
	 * Validate:
	 * <ol>
	 * <li>soId : To be epmty</li>
	 * <li>resource Id</li>
	 * </ol>
	 * 
	 * @param selectedResource
	 *            : Resource Id for whom SO to be accepted.
	 * @param soId
	 * @param assignFirm
	 *            : True when accept SO for Firm, and False when accepting for
	 *            resource.
	 * @return {@link SOWError} : Null on successful validation
	 * */
	private SOWError validateAcceptRequest(String selectedResource,
			String soId, boolean assignFirm) {
		SOWError error = null;
		if (StringUtils.isBlank(soId)) {
			error = new SOWError(null, "Invalid SO Id", "id");
		} else if (!assignFirm
				&& !validateResourceId(Integer.valueOf(selectedResource))) {
			error = new SOWError(null, "Invalid Resource Id.", "id");
		}
		return error;
	}

	/**
	 * Method which validates the resource Id against the soId. <br>
	 * It uses the Eligible Providers which has been set in session while
	 * loading the pop up for validation
	 * 
	 * @param resourseId
	 *            : resource Id
	 * @param soId
	 * @return True : Valid resource Id and False when invalid resource ID
	 * */
	private boolean validateResourceId(Integer resourseId) {
		List<EligibleProvider> eligibleProviders = (List<EligibleProvider>) getSession()
				.getAttribute("eligibleProviders");
		if (null != eligibleProviders) {
			for (EligibleProvider resource : eligibleProviders) {
				if (resource.getResourceId().equals(resourseId)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Controller method for sorting the Service Order List using different
	 * criteria.
	 **/
	public String sortServiceOrders() {
		// The current display tab, Sort Criteria and sort order are passed as request
		// Consider the filter parameters
		OrderManagementTabDTO model = getModel();
		String currentTab = model.getDisplayTab();
		String sortCriteria = model.getCriteria();
		String sortOrder = model.getSortOrder();
		Integer soCount = model.getCurrentOrderCount();
		// Set default tab as Inbox
		currentTab = StringUtils.isBlank(currentTab) ? TabConstants.INBOX : currentTab;
		this.omDisplayTab = currentTab;
		/* After sorting only the current number of orders should be displayed */
		int targetLimit = soCount;
		if (targetLimit <= DEAFULT_NUMBER_OF_ORDERS_OM) {
			targetLimit = 0;
		} else {
			targetLimit -= targetLimit % DEAFULT_NUMBER_OF_ORDERS_OM;
		}
		Integer countLimitForTab = (Integer)getSession().getAttribute("countLimitForTab");
		if(null!= countLimitForTab){
			targetLimit = countLimitForTab;
		}
		loadDataGrid(targetLimit, sortCriteria, sortOrder, model);
		getSession().setAttribute(ACTIVE_TAB, currentTab);
		OrderManagementTabDTO modelFilter = (OrderManagementTabDTO) getRequest()
				.getAttribute("omModel");
		modelFilter = null == modelFilter ? model : modelFilter;
		if ((null == modelFilter.getSelectedMarkets() || modelFilter
				.getSelectedMarkets().size() == 0)
				&& (null == modelFilter.getSelectedProviders() || modelFilter
				.getSelectedProviders().size() == 0)) {
			if ((null == modelFilter.getSelectedStatus() || modelFilter
					.getSelectedStatus().size() == 0)
					&& (null == modelFilter.getSelectedScheduleStatus() || modelFilter
					.getSelectedScheduleStatus().size() == 0)) {
				if ((null == modelFilter.getSelectedSubStatus() || modelFilter
						.getSelectedSubStatus().size() == 0)
						&& StringUtils
						.isBlank(modelFilter.getAppointmentType())) {
					setTotalTabCount(currentTab);
				}
			}
		}
		return selectViewAfterSort(currentTab);
	}

	private String selectViewAfterSort(String currentTab) {
		// Display the appropriate page
		if (TabConstants.CANCELLATIONS.equalsIgnoreCase(currentTab)
				|| TabConstants.MANAGE_ROUTE.equalsIgnoreCase(currentTab)
				|| TabConstants.CURRENT_ORDERS.equalsIgnoreCase(currentTab)) {
			return TabConstants.CANCELLATIONS.toLowerCase();
		} else if (TabConstants.ASSIGN_PROVIDER.equalsIgnoreCase(currentTab)
				|| TabConstants.AWAITING_PAYMENT.equalsIgnoreCase(currentTab)
				|| TabConstants.PRINT_PAPERWORK.equalsIgnoreCase(currentTab)) {
			return "assign";
		} else {
			return TabConstants.INBOX.toLowerCase();
		}
	}
	/**
	 * Format a String date to any required valid format
	 * 
	 * @param date
	 *            : Date as String
	 * @param format
	 *            : Format of the source date
	 * @param requiredFormat
	 *            : The expected format of the date
	 * @return : Date in the required Format as a string. Empty string will be
	 *         returned on parse exception
	 * */
	private String getTimeStampFromStr(String date, String format,
			String requiredFormat) {
		if (StringUtils.isBlank(date)) {
			return EMPTY_STR;
		}
		String strDate = date;
		Date dt1 = stringToDate(strDate, format);
		DateFormat formatter = new SimpleDateFormat(requiredFormat);
		String strFotrmated = EMPTY_STR;
		strFotrmated = formatter.format(dt1);
		return strFotrmated;
	}

	/**
	 * Converts String to Date using the supplied Format.
	 * 
	 * @param strDate
	 *            : Date in String
	 * @param format
	 *            : Format of the source date.
	 * @return : Date : Null will be returned for any parse exception
	 * */
	private Date stringToDate(String strDate, String format) {
		DateFormat formatter;
		Date date = null;
		formatter = new SimpleDateFormat(format);
		try {
			date = (Date) formatter.parse(strDate);
		} catch (ParseException e) {
			LOGGER.error(e);
		}
		return date;
	}

	/**
	 * this method is used to load data for update time pop up
	 * 
	 * @param
	 * @return String.
	 */
	public String loadUpdateTimeWidget() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String firmId = soContxt.getCompanyId().toString();
		String soId = getRequest().getParameter(SO_ID);
		String groupInd = getRequest().getParameter(GROUP_IND_PARAM);
		SOWError error =null;
		if (StringUtils.isBlank(groupInd)
				|| !GROUPED_SO_IND.equalsIgnoreCase(groupInd)) {
			groupInd = "0";
		}
		// Set the filter

		String responseFilterParam = "General-Schedule-Contacts-Service%20Location";
		SOGetResponse soGetResponse = restClient.getResponseForGetInfoForCall(
				firmId, soId, groupInd, responseFilterParam);
		List<RetrieveServiceOrder> retrieveServiceOrderList = null;
		if (null != soGetResponse && null != soGetResponse.getServiceOrders()) {
			ServiceOrders serviceOrders = soGetResponse.getServiceOrders();
			if (null != serviceOrders
					&& null != serviceOrders.getServiceorders()) {
				retrieveServiceOrderList = serviceOrders.getServiceorders();
			}
		}
		if(null!=soGetResponse && (null != soGetResponse.getResults().getError())) {
			List<ErrorResult> errorResults = soGetResponse
					.getResults().getError();
			if (null != errorResults) {
				for (ErrorResult result : errorResults) {
					LOGGER.info(result.getMessage());
					error = new SOWError(null, result.getMessage(), EMPTY_STR);
					omErrors.add(error);
				}
			}
		}
		RetrieveServiceOrder retrieveServiceOrder = null;
		// Service order details
		if (null != retrieveServiceOrderList
				&& retrieveServiceOrderList.size() > 0) {
			SimpleDateFormat format1 = new SimpleDateFormat(
					TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR);
			SimpleDateFormat format2 = new SimpleDateFormat(TIME_STAMP_DISPLAYED);
			retrieveServiceOrder = retrieveServiceOrderList.get(0);
			try {
				if (null != retrieveServiceOrder
						&& null != retrieveServiceOrder.getSchedule()) {
					if (null != retrieveServiceOrder.getServiceLocation()
							&& null != retrieveServiceOrder
							.getServiceLocation().getZip()
							&& null != retrieveServiceOrder.getSchedule()
							.getServiceDateTime1()
							&& null != retrieveServiceOrder.getSchedule()
							.getServiceLocationTimezone()) {
						// to set the time zone
						String zipCode = retrieveServiceOrder
								.getServiceLocation().getZip();
						String serviceStartDate = retrieveServiceOrder
								.getSchedule().getServiceDateTime1();
						String timeZone = retrieveServiceOrder.getSchedule()
								.getServiceLocationTimezone();
						String actualZone = getTimeZoneFromDate(zipCode,
								serviceStartDate, timeZone);
						retrieveServiceOrder.getSchedule().setServiceLocationTimezone(timeZone + ":" + actualZone);
					}
					if (null != retrieveServiceOrder.getSchedule()
							.getServiceDateTime1()) {
						retrieveServiceOrder.getSchedule().setServiceDateTime1(
								format2.format(format1
										.parse(retrieveServiceOrder
												.getSchedule()
												.getServiceDateTime1()
												.replace("T", SPACE))));
					}
					if (null != retrieveServiceOrder.getSchedule()
							.getServiceDateTime2()) {
						retrieveServiceOrder.getSchedule().setServiceDateTime2(
								format2.format(format1.parse(retrieveServiceOrder
										.getSchedule()
										.getServiceDateTime2()
										.replace("T", SPACE))));
					}
				}
				if(null != retrieveServiceOrder.getBuyer()){
					setAttribute("MINTIME_WINDOW", retrieveServiceOrder.getBuyer().getMinTimeWindow());
					setAttribute("MAXTIME_WINDOW", retrieveServiceOrder.getBuyer().getMaxTimeWindow());
				}

				/**SL 18896 , R8.2 START**/
				//Time intervals beyond (11:45PM - MAXTIME_WINDOW*4) should not be shown,
				//ie suppose if time interval is 8:00 PM and MAXTIME_WINDOW= 4,
				//then it is filtered out, max time interval shown here is 7:45 PM
				ServletContext application = getSession().getServletContext();
				List<LookupVO> updatedTimeInterval = (List<LookupVO>) application.getAttribute("time_intervals");
				List<LookupVO> newUpdatedTimeInterval = new ArrayList<LookupVO>();

				// the updatedTimeInterval will be 96(24*4)
				if(null != updatedTimeInterval && updatedTimeInterval.size() == 96){
					int MINTIME_WINDOW = 0;
					if(null != retrieveServiceOrder.getBuyer().getMinTimeWindow()){
						MINTIME_WINDOW = retrieveServiceOrder.getBuyer().getMinTimeWindow();
					}
					int newListCount = 96-MINTIME_WINDOW*4;

					if(null != updatedTimeInterval && !updatedTimeInterval.isEmpty() && 33 <= updatedTimeInterval.size()){

						//add Time intervals from 8:00 AM to Max Time
						newUpdatedTimeInterval.addAll(updatedTimeInterval.subList(32, newListCount));
						//add 12:00AM to 8:00 AM time intervals
						newUpdatedTimeInterval.addAll(updatedTimeInterval.subList(0,32));
					}

					getSession().setAttribute("time_intervals_updated_new", newUpdatedTimeInterval);
				}
				/**SL 18896 , R8.2 END**/

			} catch (ParseException ex) {
				LOGGER.debug("ParseException in OrderManagementControllerAction.loadUpdateTimeWidget() due to "
						+ ex.getMessage());
			}

		}
		this.setAttribute("timeDetails", retrieveServiceOrder);
		this.setAttribute("omApiErrors", omErrors);

		return SUCCESS;
	}

	/**
	 * Controller method for cancelling Service Order
	 * */
	public String cancelReschedule() {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);

		LOGGER
		.info("Entering the OrderManagementControllerAction.cancelRequestReschedule()");

		String soID = getRequest().getParameter(SO_ID);
		String method = getRequest().getParameter(METHOD);

		int roleId = get_commonCriteria().getRoleId().intValue();
		boolean isError = false;
		SOWError error = null;
		if (soID != null) {
			RescheduleDTO reschedule = new RescheduleDTO();

			if (!isError) {
				reschedule.setSoId(soID);
				reschedule.setRequestorRole(roleId);
				reschedule.setCompanyId(get_commonCriteria().getCompanyId());


				if(method.equalsIgnoreCase(CANCEL_RESCHEDULE))
				{
					ProcessResponse success = soDetailsManager
							.cancelRescheduleRequest(reschedule);

					if (!success.getCode().equals(ServiceConstants.VALID_RC)) {
						omTabDTO.setResult("Cancel Reschedule request cannot be completed.");
						getSession().setAttribute(
								ERROR,
								"Cancel Reschedule request cannot be completed for soId:"+soID);
						error = new SOWError(null, "Cancel Reschedule request cannot be completed.", EMPTY_STR);
						omErrors.add(error);
						omTabDTO.setOmErrors(omErrors);
					} else {
						omTabDTO.setResult(
								"Reschedule request has been successfully canceled.");
						getSession().setAttribute(
								SUCCESS_MSG,
								"Reschedule request has been successfully canceled for soId:"+soID);
					}

				}
				else if(method.equalsIgnoreCase(REJECT_RESCHEDULE))
				{
					reschedule.setRescheduleAccepted(false);
					ProcessResponse success = soDetailsManager
							.respondToRescheduleRequest(reschedule);

					if (!success.getCode().equals(ServiceConstants.VALID_RC)) {
						omTabDTO.setResult("Reject Reschedule request cannot be completed.");
						getSession().setAttribute(
								ERROR,
								"Reject Reschedule request cannot be completed for soId:"+soID);
						error = new SOWError(null, "Reject Reschedule request cannot be completed.", EMPTY_STR);
						omErrors.add(error);
						omTabDTO.setOmErrors(omErrors);
					} else {
						omTabDTO.setResult(
								"Reschedule request has been successfully rejected.");
						getSession().setAttribute(
								SUCCESS_MSG,
								"Reschedule request has been successfully rejected for soId:"+soID);
					}

				}	
				else if(method.equalsIgnoreCase(ACCEPT_RESCHEDULE))
				{
					reschedule.setRescheduleAccepted(true);

					ProcessResponse success = soDetailsManager
							.respondToRescheduleRequest(reschedule);

					if (!success.getCode().equals(ServiceConstants.VALID_RC)) {
						omTabDTO.setResult("Accept Reschedule request cannot be completed.");
						getSession().setAttribute(
								ERROR,
								"Accept Reschedule request cannot be completed for soId:"+soID);
						error = new SOWError(null, "Accept Reschedule request cannot be completed.", EMPTY_STR);
						omErrors.add(error);
						omTabDTO.setOmErrors(omErrors);
					} else {
						omTabDTO.setResult(
								"Reschedule request has been successfully accepted.");
						getSession().setAttribute(
								SUCCESS_MSG,
								"Reschedule request has been successfully accepted for soId:"+soID);


						// InHome NPS Notification for accept reschedule reschedule.
						//InHomeRescheduleVO input=new InHomeRescheduleVO();
						// accepted_vendor_id is empId
						// input.setSoId(reschedule.getSoId());
						//InHomeRescheduleVO result=new InHomeRescheduleVO();
						//try{
						//	result=notificationService.getSoDetailsForReschedule(input);
						//	}catch(Exception e){
						//		LOGGER.info("exception in getting SO details"+e);
						//	}

						//  insertRescheduleNPSInhomeNotificationMessages(result);					

					}

				}		

			}
		}
		return JSON;
	}	

	public void insertRescheduleNPSInhomeNotificationMessages(InHomeRescheduleVO inHomeRescheduleVO){
		String soId=inHomeRescheduleVO.getSoId();
		Integer buyerId =inHomeRescheduleVO.getBuyerId();

		boolean isEligibleForNPSNotification=false;
		try {
			isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,soId);
		} catch (BusinessServiceException e) {
			LOGGER.info("Exception in validatiing nps notification eligibility"+ e.getMessage());
		}
		if(isEligibleForNPSNotification){
			//Call Insertion method
			try {
				notificationService.insertNotification(inHomeRescheduleVO);
			}catch (BusinessServiceException e){
				LOGGER.info("Caught Exception while insertNotification",e);				
			}
		}
	}

	public String loadTimer(){
		Integer acceptedResourceId = 0;
		String assignee = (String)(getParameter(ASSIGNEE));
		String groupInd = getParameter(GROUP_IND_PARAM);
		String soId = getParameter(SO_ID);
		int remainingTimeToAcceptSO = 0;

		try{
			if ("1".equalsIgnoreCase(groupInd)) {				
				if(TYPE_PROVIDER.equals(assignee)){
					acceptedResourceId = Integer.parseInt(getParameter("provider"));
					remainingTimeToAcceptSO = soDetailsManager.getTheRemainingTimeToAcceptGrpOrder(soId, acceptedResourceId);
				}else{
					remainingTimeToAcceptSO = soDetailsManager.getTheRemainingTimeToAcceptGrpOrderFirm(soId, get_commonCriteria().getCompanyId());
				}

			} else {
				if(TYPE_PROVIDER.equals(assignee)){
					acceptedResourceId = Integer.parseInt(getParameter("provider"));
					remainingTimeToAcceptSO = soDetailsManager.getTheRemainingTimeToAcceptSO(soId, acceptedResourceId);
				}else{
					remainingTimeToAcceptSO = soDetailsManager.getTheRemainingTimeToAcceptSOForFirm(soId, get_commonCriteria().getCompanyId());
				}
			}
		}
		catch(Exception e){
			LOGGER.error("Exception in OrderManagementControllerAction.loadTimer() due to "+ e);
		}
		setAttribute("xTimerSeconds",remainingTimeToAcceptSO);		
		return SUCCESS;
	}

	//SL-21645

	public String displayAddEstimateSOPopUp(){
		
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
		Integer vendorIdStr = securityContext.getCompanyId();
		String userNameStr = securityContext.getUsername();
		Integer resourceIdStr = securityContext.getVendBuyerResId();
		String soId = getRequest().getParameter(SO_ID);
		String userRoleType = get_commonCriteria().getRoleType();
		String soStatus = getRequest().getParameter("sostatus");
		
		if(null == soStatus || StringUtils.isEmpty(soStatus)){
			try{
			soStatus=String.valueOf(mobileGenericBO.getServiceOrderStatus(soId));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		getRequest().setAttribute("soStatusCode", soStatus);
		//String resId = getRequest().getParameter("resId");
		fromServiceOD = getRequest().getParameter("sodPage");
		List<EstimateHistoryVO> estimateHistoryVo=new ArrayList<EstimateHistoryVO>();
		Integer estimationId = null;
		if(StringUtils.isNotBlank(getRequest().getParameter("estimationId"))){
			estimationId=Integer.parseInt(getRequest().getParameter("estimationId"));
		}
		
		//Integer estimateId = Integer.parseInt(getRequest().getParameter("estimateId"));
		
		try{
		estimateVO = mobileGenericBO.getEstimateDetails(soId,estimationId);
		}catch(Exception e){
			LOGGER.error("Exception thrown in execute of SavingSoEstimation "+ e.getMessage());
		}

		SoLocation soLocation=managementService.getServiceLocDetails(soId);
		List<Contact> contact = managementService.getSOContactDetails(soId);

		ServiceOrder so = new ServiceOrder();
		so.setContactInfo(contact);
			
		try{
			if(userRoleType.equalsIgnoreCase("Buyer")){
				estimateHistoryVo = soMonitorDelegate.getServiceOrderEstimationHistoryDetailsForBuyer(soId);

			}else{
				estimateHistoryVo = soMonitorDelegate.getServiceOrderEstimationHistoryDetailsForVendor(soId,vendorIdStr,resourceIdStr);
			}
		}catch(Exception e){
			LOGGER.error("Exception thrown while getting estimate history "+ e.getMessage());
			e.printStackTrace();
		}
		
		if(null == estimateVO){
			estimateVO=new EstimateVO();
			estimateVO.setResourceId(resourceIdStr);
			//estimateVO.setCreatedBy(userNameStr);
			//estimateVO.setModifiedBy(userNameStr);
			//estimateVO.setModifiedDate(new Date());
			estimateVO.setTotalLaborPrice(0.00);
			estimateVO.setTotalPartsPrice(0.00);
			estimateVO.setTotalPrice(0.00);
			
		}
		estimateVO.setEstimationId(estimationId);
		estimateVO.setVendorId(vendorIdStr);
		estimateVO.setServiceContact(so.getServiceContact());
		estimateVO.setServiceLocation(soLocation);
		estimateVO.setSoId(soId);	
		if(null != estimateVO.getEstimateHistoryList()){
			estimateVO.setEstimateHistoryList(null);
		}
		estimateVO.setEstimateHistoryList(estimateHistoryVo);

		
		setAttribute("estimateVO", estimateVO);
		//setAttribute("fromSOD", fromSOD);
		//this.soId =soId;
		//this.resId=resId;
		
		return SUCCESS;
	}


	/*  Method to save Estimate details, It will call method of MobileGenericDaoImpl
	 * 
	 * */
	public String saveSOEstimate(){
		boolean relayServicesNotifyFlag = false;
		Integer buyerId = null;
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
		
		try{
			if(null != estimateVO){
				if(StringUtils.isBlank(estimateVO.getDiscountType())){
					estimateVO.setDiscountedAmount(0.00);
					estimateVO.setDiscountedPercentage(0.00);
				}
				
              if(null == estimateVO.getTaxPrice()  || null == estimateVO.getTaxRate()){
            	  estimateVO.setTaxPrice(0.00);
            	  estimateVO.setTaxRate(0.00);
            	}
				
				
			}
			 estimateVO.setAcceptSource("Web");
			 estimateVO.setModifiedBy(securityContext.getUsername());
			 estimateVO = mobileGenericBO.saveEditEstimate(estimateVO, estimateVO.getEstimationId());
			
			if (null != estimateVO && null != estimateVO.getStatus() && !(estimateVO.getStatus().equals("DRAFT"))) {
				ServiceOrder serviceOrder = mobileSOActionsBO.getServiceOrder(estimateVO.getSoId());
				if (null != serviceOrder) {
					buyerId = serviceOrder.getBuyerId();
					relayServicesNotifyFlag = relayNotificationService.isRelayServicesNotificationNeeded(buyerId, estimateVO.getSoId());

					if (relayServicesNotifyFlag) {
						if (estimateVO.isEstimateAdded() || estimateVO.isEstimateUpdatedFromDraftTONew()) {
							Map<String, String> params = new HashMap<String, String>();

							if (null != estimateVO.getVendorId()) {
								List<Integer> vendorIds = new ArrayList<Integer>();
								vendorIds.add(estimateVO.getVendorId());
								String vendorDetails = mobileSOActionsBO.getVendorBNameList(vendorIds);

								params.put("firmsdetails", vendorDetails);
							}
							
							params.put("resourceId", null != estimateVO.getResourceId() ? estimateVO.getResourceId().toString() : "0");

							relayNotificationService
									.sentNotificationRelayServices(MPConstants.ADD_ESTIMATE_API_EVENT, estimateVO.getSoId(), params);
						} else {
							if (estimateVO.isEstimateUpdate() && !(estimateVO.isEstimatePriceChange())) {
								relayNotificationService.sentNotificationRelayServices(MPConstants.UPDATE_ESTIMATE_API_EVENT,
										estimateVO.getSoId());
							}
							if (estimateVO.isEstimatePriceChange()) {

								Map<String, String> params = new HashMap<String, String>();

								if (null != estimateVO.getVendorId()) {
									List<Integer> vendorIds = new ArrayList<Integer>();
									vendorIds.add(estimateVO.getVendorId());
									String vendorDetails = mobileSOActionsBO.getVendorBNameList(vendorIds);

									params.put("firmsdetails", vendorDetails);
								}
								
								params.put("resourceId", null != estimateVO.getResourceId() ? estimateVO.getResourceId().toString() : "0");

								relayNotificationService.sentNotificationRelayServices(MPConstants.UPDATE_ESTIMATE_PRICE_API_EVENT,
										estimateVO.getSoId(), params);
							}
						}
					}
				}
			}
			
		}catch(Exception e){
			LOGGER.error("Exception thrown in execute of SavingSoEstimation "+ e.getMessage());
		}
		if(fromServiceOD.equals("true")){
			
			return "estimateDetails";
			
		}else{
			
			return SUCCESS;
		
		}
	}



	//End of SL-21645
	public OrderManagementTabDTO getModel() {
		return omTabDTO;
	}

	public void setModel(OrderManagementTabDTO omTabDTO) {
		this.omTabDTO = omTabDTO;
	}

	public OrderManagementRestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(OrderManagementRestClient restClient) {
		this.restClient = restClient;
	}

	public List<SOWError> getOmErrors() {
		return omErrors;
	}

	public void setOmErrors(List<SOWError> omErrors) {
		this.omErrors = omErrors;
	}

	public OrderManagementTabDTO getOmTabDTO() {
		return omTabDTO;
	}

	public void setOmTabDTO(OrderManagementTabDTO omTabDTO) {
		this.omTabDTO = omTabDTO;
	}

	public ILookupDelegate getLuDelegate() {
		return luDelegate;
	}

	public void setLuDelegate(ILookupDelegate luDelegate) {
		this.luDelegate = luDelegate;
	}

	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}

	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public EstimateVO getEstimateVO() {
		return estimateVO;
	}

	public void setEstimateVO(EstimateVO estimateVO) {
		this.estimateVO = estimateVO;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getFromServiceOD() {
		return fromServiceOD;
	}

	public void setFromServiceOD(String fromServiceOD) {
		this.fromServiceOD = fromServiceOD;
	}
}
