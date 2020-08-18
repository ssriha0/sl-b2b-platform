package com.newco.marketplace.web.action.details;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.business.iBusiness.survey.ExtendedSurveyBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.provider.ProviderFirmVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.delegates.provider.ITermsAndCondDelegate;
import com.newco.marketplace.web.dto.SOCompleteCloseDTO;
import com.newco.marketplace.web.dto.SODetailsQuickLinksDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.WFMBuyerQueueDTO;
import com.newco.marketplace.web.service.ManageScopeService;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.web.utils.SLStringUtils;

import static com.newco.marketplace.web.constants.QuickLinksConstants.*;
import static com.newco.marketplace.constants.Constants.SESSION.*;

import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.74 $ $Author: nsanzer $ $Date: 2008/06/02 22:56:59 $
 */

/*
 * Maintenance History - See bottom of file
 */
public class SODetailsQuickLinksAction extends SLDetailsBaseAction implements
		Preparable {
	private static final Logger logger = Logger.getLogger(SODetailsQuickLinksAction.class);
	static final long serialVersionUID = 10002;// arbitrary number to get rid
	private ISODetailsDelegate detailsDelegate;
	private ITermsAndCondDelegate iTermsAndCondDelegate;
	private boolean buyerHasRatedProvider;
	private boolean providerHasRatedBuyer;
	private boolean rescheduleRequest;
	private Integer rescheduleRequestRole;
	private int soOrderType;
	IServiceOrderUpsellBO  serviceOrderUpsellBO;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private ManageScopeService manageScopeService;
	private SOCompleteCloseDTO soCloseDtoComplete = new SOCompleteCloseDTO();
	//SL-19820
	private ISOMonitorDelegate soMonitorDelegate;
	private ExtendedSurveyBO extendedSurveyBO;
	private boolean isOldSurvey;

	public ISOMonitorDelegate getSoMonitorDelegate() {
		return soMonitorDelegate;
	}

	public void setSoMonitorDelegate(ISOMonitorDelegate soMonitorDelegate) {
		this.soMonitorDelegate = soMonitorDelegate;
	}

	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}
	
	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}
	
	public ExtendedSurveyBO getExtendedSurveyBO() {
		return extendedSurveyBO;
	}	

	public void setExtendedSurveyBO(ExtendedSurveyBO extendedSurveyBO) {
		this.extendedSurveyBO = extendedSurveyBO;
	}

	public SODetailsQuickLinksAction(ISODetailsDelegate delegate,ITermsAndCondDelegate termsAndCondDelegate) {
		this.detailsDelegate = delegate;
		this.iTermsAndCondDelegate = termsAndCondDelegate;
		// Initialize the list of all possible buttons for referencing later.
		if (dtoList == null) {
			dtoList = new HashMap<String, SODetailsQuickLinksDTO>();
			dtoList.put(GIF_ACCEPT_RESCHEDULE_REQUEST,
					new SODetailsQuickLinksDTO(JS_ACCEPT_RESCHEDULE_REQUEST,
							ACT_ACCEPT_RESCHEDULE_REQUEST,
							GIF_ACCEPT_RESCHEDULE_REQUEST, HEIGHT_TALL,
							TALL_BUTTON_STYLE));
			dtoList.put(GIF_REJECT_RESCHEDULE_REQUEST,
					new SODetailsQuickLinksDTO(JS_REJECT_RESCHEDULE_REQUEST,
							ACT_REJECT_RESCHEDULE_REQUEST,
							GIF_REJECT_RESCHEDULE_REQUEST, HEIGHT_TALL,
							TALL_BUTTON_STYLE));
			dtoList.put(GIF_CANCEL_RESCHEDULE_REQUEST,
					new SODetailsQuickLinksDTO(JS_CANCEL_RESCHEDULE_REQUEST,
							ACT_CANCEL_RESCHEDULE_REQUEST,
							GIF_CANCEL_RESCHEDULE_REQUEST, HEIGHT_TALL,
							TALL_BUTTON_STYLE));
			//For Edit reschedule
			dtoList.put(GIF_EDIT_RESCHEDULE_REQUEST,
					new SODetailsQuickLinksDTO(
							JS_REQUEST_RESCHEDULE, ACT_REQUEST_RESCHEDULE,
							GIF_EDIT_RESCHEDULE_REQUEST, HEIGHT_TALL, TALL_BUTTON_STYLE));
			
			dtoList.put(GIF_ACCEPT_SERVICE_ORDER, new SODetailsQuickLinksDTO(
					JS_ACCEPT_SERVICE_ORDER, ACT_ACCEPT_SERVICE_ORDER,
					GIF_ACCEPT_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));
			
			dtoList.put(GIF_ACCEPT_WITH_CONDITIONS,
							new SODetailsQuickLinksDTO(
									JS_ACCEPT_WITH_CONDITIONS,
									ACT_ACCEPT_WITH_CONDITIONS,
									GIF_ACCEPT_WITH_CONDITIONS, HEIGHT_SHORT,
									STD_BUTTON_STYLE));
			dtoList.put(GIF_BUX_ACCEPT_WITH_CONDITIONS,
					new SODetailsQuickLinksDTO(
							JS_BUX_ACCEPT_WITH_CONDITIONS,
							ACT_ACCEPT_WITH_CONDITIONS,
							GIF_ACCEPT_WITH_CONDITIONS, HEIGHT_SHORT,
							STD_BUTTON_STYLE));
			dtoList.put(GIF_BUX_WARN_ACCEPT_WITH_CONDITIONS,
					new SODetailsQuickLinksDTO(
							JS_BUX_WARN_ACCEPT_WITH_CONDITIONS,
							ACT_ACCEPT_WITH_CONDITIONS,
							GIF_ACCEPT_WITH_CONDITIONS, HEIGHT_SHORT,
							STD_BUTTON_STYLE));
			dtoList.put(GIF_ADD_AND_VIEW_NOTES, new SODetailsQuickLinksDTO(
					JS_ADD_AND_VIEW_NOTES, ACT_ADD_AND_VIEW_NOTES,
					GIF_ADD_AND_VIEW_NOTES, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_CANCEL_SERVICE_ORDER, new SODetailsQuickLinksDTO(
					JS_CANCEL_SERVICE_ORDER, ACT_CANCEL_SERVICE_ORDER,
					GIF_CANCEL_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_CLOSE_AND_PAY, new SODetailsQuickLinksDTO(
					JS_CLOSE_ORDER_AND_PAY, ACT_CLOSE_ORDER_AND_PAY,
					GIF_CLOSE_AND_PAY, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_COMPLETE_FOR_PAYMENT, new SODetailsQuickLinksDTO(
					JS_COMPLETE_FOR_PAYMENT, ACT_COMPLETE_FOR_PAYMENT,
					GIF_COMPLETE_FOR_PAYMENT, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_COPY_SERVICE_ORDER, new SODetailsQuickLinksDTO(
					JS_COPY_SERVICE_ORDER, ACT_COPY_SERVICE_ORDER,
					GIF_COPY_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_DELETE_DRAFT_ORDER, new SODetailsQuickLinksDTO(
					JS_DELETE_DRAFT_ORDER, ACT_DELETE_DRAFT_ORDER,
					GIF_DELETE_DRAFT_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_ISSUE_RESOLUTION, new SODetailsQuickLinksDTO(
					JS_ISSUE_RESOLUTION, ACT_ISSUE_RESOLUTION,
					GIF_ISSUE_RESOLUTION, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_MANAGE_DOCS_PHOTOS, new SODetailsQuickLinksDTO(
					JS_MANAGE_DOCS_PHOTOS, ACT_MANAGE_DOCS_PHOTOS,
					GIF_MANAGE_DOCS_PHOTOS, HEIGHT_TALL, TALL_BUTTON_STYLE));
			dtoList.put(GIF_RATE_PROVIDER, new SODetailsQuickLinksDTO(
					JS_RATE_PROVIDER, ACT_RATE_PROVIDER, GIF_RATE_PROVIDER,
					HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_RATE_BUYER, new SODetailsQuickLinksDTO(
					JS_RATE_BUYER, ACT_RATE_BUYER, GIF_RATE_BUYER,
					HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_REJECT_SERVICE_ORDER, new SODetailsQuickLinksDTO(
					JS_REJECT_SERVICE_ORDER, ACT_REJECT_SERVICE_ORDER,
					GIF_REJECT_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_RELEASE_SERVICE_ORDER, new SODetailsQuickLinksDTO(
					JS_RELEASE_SERVICE_ORDER, ACT_RELEASE_SERVICE_ORDER,
					GIF_RELEASE_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_REPORT_A_PROBLEM, new SODetailsQuickLinksDTO(
					JS_REPORT_A_PROBLEM, ACT_REPORT_A_PROBLEM,
					GIF_REPORT_A_PROBLEM, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_TIME_ON_SITE, new SODetailsQuickLinksDTO(
					JS_TIME_ON_SITE, ACT_TIME_ON_SITE,
					GIF_TIME_ON_SITE, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_REQUEST_RESCHEDULE, new SODetailsQuickLinksDTO(
					JS_REQUEST_RESCHEDULE, ACT_REQUEST_RESCHEDULE,
					GIF_REQUEST_RESCHEDULE, HEIGHT_TALL, TALL_BUTTON_STYLE));
			dtoList.put(GIF_VIEW_ORDER_HISTORY, new SODetailsQuickLinksDTO(
					JS_VIEW_ORDER_HISTORY, ACT_VIEW_ORDER_HISTORY,
					GIF_VIEW_ORDER_HISTORY, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_VIEW_PRINT_PDF, new SODetailsQuickLinksDTO(
					JS_VIEW_PRINT_PDF, ACT_VIEW_PRINT_PDF, GIF_VIEW_PRINT_PDF,
					HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_VOID_SERVICE_ORDER, new SODetailsQuickLinksDTO(
					JS_VOID_SERVICE_ORDER, ACT_VOID_SERVICE_ORDER,
					GIF_VOID_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_SERVICE_LIVE_SUPPORT, new SODetailsQuickLinksDTO(
					JS_SERVICE_LIVE_SUPPORT, ACT_SERVICE_LIVE_SUPPORT,
					GIF_SERVICE_LIVE_SUPPORT, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_WITHDRAW_COND_ACCEPT, new SODetailsQuickLinksDTO(
					JS_WITHDRAW_COND_ACCEPT, ACT_WITHDRAW_COND_ACCEPT,
					GIF_WITHDRAW_COND_ACCEPT, HEIGHT_TALL, TALL_BUTTON_STYLE));
			dtoList.put(GIF_ASSIGN_SERVICE_ORDER, new SODetailsQuickLinksDTO(
					JS_ASSIGN_SERVICE_ORDER, ACT_ASSIGN_SERVICE_ORDER,
					GIF_ASSIGN_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));	
			dtoList.put(GIF_REASSIGN_SERVICE_ORDER, new SODetailsQuickLinksDTO(
					JS_REASSIGN_SERVICE_ORDER, ACT_REASSIGN_SERVICE_ORDER,
					GIF_REASSIGN_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));			
			dtoList.put(GIF_MANAGE_SCOPE, new SODetailsQuickLinksDTO(
					JS_MANAGE_SCOPE,null, GIF_MANAGE_SCOPE,HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_VIEW_COMPLETION_RECORD,
							new SODetailsQuickLinksDTO(
									JS_VIEW_COMPLETION_RECORD,
									ACT_VIEW_COMPLETION_RECORD,
									GIF_VIEW_COMPLETION_RECORD, HEIGHT_TALL,
									TALL_BUTTON_STYLE));
			dtoList.put(GIF_EDIT_SERVICE_ORDER, new SODetailsQuickLinksDTO(
					JS_EDIT_SERVICE_ORDER, ACT_EDIT_SERVICE_ORDER,
					GIF_EDIT_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));
			dtoList.put(GIF_VIEW_PROVIDER_RESPONSES,
					new SODetailsQuickLinksDTO(JS_VIEW_PROVIDER_RESPONSES,
							ACT_VIEW_PROVIDER_RESPONSES,
							GIF_VIEW_PROVIDER_RESPONSES, HEIGHT_SHORT,
							STD_BUTTON_STYLE));
		}
		
	}

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		//contactList wont be available in session anymore; need to fetch
		//Sl-19820
		//ArrayList contactList = (ArrayList) getSession().getAttribute("contactList");
		String soId = getParameter("soId");
		setAttribute(OrderConstants.SO_ID, soId);
		String groupId = getParameter("groupId");
		setAttribute(GROUP_ID, groupId);
		setAttribute(Constants.SESSION.SOD_ROUTED_RES_ID, getParameter("resId"));
		String id = null;
		if(org.apache.commons.lang.StringUtils.isNotBlank(groupId)){
			id = groupId;
		}else{
			id = soId;
		}
		
		String surveyStatus = extendedSurveyBO.getServiceOrderFlowType(soId);
		isOldSurvey = SurveyConstants.CSAT_NPS_OLDFLOW.equalsIgnoreCase(surveyStatus);
		
		String displayTab = (String)getSession().getAttribute(OrderConstants.DISPLAY_TAB+"_"+id);
		setAttribute(OrderConstants.DISPLAY_TAB, displayTab);
		
		String fromOM = (String)getSession().getAttribute(Constants.FROM_ORDER_MANAGEMENT+"_"+id);
		setAttribute(Constants.FROM_ORDER_MANAGEMENT, fromOM);
		
		String isfromOM = (String)getSession().getAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT+"_"+id);
		if(org.apache.commons.lang.StringUtils.isBlank(isfromOM) && null != getSession().getAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT)){
			isfromOM = (String) getSession().getAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT);
		}
		setAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT, isfromOM);
		
		String isfromWFM = (String)getSession().getAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR+"_"+id);
		if(org.apache.commons.lang.StringUtils.isBlank(isfromWFM) && null != getSession().getAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR)){
			isfromWFM = (String) getSession().getAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
		}
		setAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR, isfromWFM);
		if(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR.equals(isfromWFM)){
			setAttribute("widgetProResultsDTOObj", getSession().getAttribute("widgetProResultsDTOObj_"+id));
		}
		
		setAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO, getSession().getAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO+"_"+id));
		getSession().removeAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO+"_"+id);
		
		setAttribute("tab", getParameter("tab"));
		ArrayList contactList = getDetailsDelegate().getRoutedResources(soId);
		if (contactList != null) {
			getRequest().setAttribute("contactList", contactList);
			//getSession().setAttribute("contactList", contactList);
		}
		HttpSession session  = getSession();
		Map<String, UserActivityVO> activities = ((SecurityContext) getSession().getAttribute(
		"SecurityContext")).getRoleActivityIdList();
		boolean incSpendLimit = false;
		if(activities != null && activities.containsKey("56")){
			incSpendLimit = true;
		}
		session.setAttribute("incSpendLimit", incSpendLimit);
		
		//SL 15642 Start-Changes for permission based price display in Order Quick Links
				if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER))
				{
					
					if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"))
					{
						session.setAttribute("viewOrderPricing",true);
					}
				}
	    //SL 15642 End-Changes for permission based price display in Order Quick Links
	}
	
	
	
	
	public void agreeBucks() {
		SecurityContext securityContext = (SecurityContext) getSession()
												.getAttribute(SECURITY_KEY);
		try {
			iTermsAndCondDelegate.save(securityContext.getCompanyId());
			getSession().setAttribute(VENDOR_BUCK_INDICATOR, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String execute() throws Exception {
		populateDTO();
		return SUCCESS;
	}

	private void populateDTO() {
		boolean isCounterOfferOff=false;
		int orderType = checkCurrentOrderType(); // returns OrderConstants.ORDER_TYPE_xxxxxx
		setSoOrderType(orderType);
		// Set text above buttons here.
		if (orderType == OrderConstants.ORDER_TYPE_GROUP) {
			setGroupOrderQuickInfo();
		} else {
			setSimpleOrderQuickInfo();
		}
		
		
		
		final SecurityContext securityContext = ((SecurityContext) getSession().getAttribute("SecurityContext")); 
		
		String role = securityContext.getRole();
		
		if((securityContext.getSLAdminInd() || "provider".equalsIgnoreCase(role)) && getAttribute("buyerID")!=null){
			isCounterOfferOff=buyerFeatureSetBO.validateFeature(new Integer(getAttribute("buyerID").toString()), BuyerFeatureConstants.COUNTER_OFFER_OFF);
			setAttribute("isCounterOfferOff", isCounterOfferOff);	
			logger.info("The counter offer is disabled "+isCounterOfferOff);
		}
		// String status = (Integer)getSession().getAttribute("detailsStatus");
		//Sl-19820
		//Integer status = (Integer) getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE);
		Integer status = (Integer) getAttribute(THE_SERVICE_ORDER_STATUS_CODE);
		if ("buyer".equalsIgnoreCase(role) || OrderConstants.SIMPLE_BUYER.equalsIgnoreCase(role)) {
			String priceType = null;
			//Sl-19820
			//ServiceOrderDTO serviceOrderDTO = getCurrentServiceOrderFromSession();
			ServiceOrderDTO serviceOrderDTO = getCurrentServiceOrderFromRequest();
			String assignmentType = null;
			if(null != serviceOrderDTO && org.apache.commons.lang.StringUtils.isNotBlank(serviceOrderDTO.getAssignmentType())){
				if(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM.equalsIgnoreCase(serviceOrderDTO.getAssignmentType())){
					assignmentType = OrderConstants.SO_ASSIGNMENT_TYPE_FIRM;
				}else{
					assignmentType = OrderConstants.SO_ASSIGNMENT_TYPE_PROVIDER;
				}
			}
			setAttribute(Constants.SESSION.SO_ASSIGNMENT_TYPE, assignmentType);	
			
			if(serviceOrderDTO != null){
				priceType=serviceOrderDTO.getPriceType();
			}
			setBuyerButtons(status, orderType, priceType);
		} else if ("provider".equalsIgnoreCase(role)) {
			boolean isProviderAdmin = securityContext.isPrimaryInd();
			boolean isDispatchInd = securityContext.isDispatchInd();
			getSession().setAttribute("isProviderAdmin", isProviderAdmin);			
			getSession().setAttribute("isDispatchInd",isDispatchInd);
			setProviderButtons(status, orderType);
		}
		// TODO : Check if we want to include buyers SO details.
		if( ( securityContext.getSLAdminInd() || "buyer".equalsIgnoreCase(role)))
		{
			String buyerId = null;
			String groupId=null;
			//SL-19820
			//String soId= (String)getSession().getAttribute(OrderConstants.SO_ID);
			String soId= (String) getAttribute(OrderConstants.SO_ID);

			if(org.apache.commons.lang.StringUtils.isBlank(soId)){
				//SL-19820
				//groupId = (String)getSession().getAttribute(GROUP_ID);
				groupId = (String) getAttribute(GROUP_ID);
			}
			
			List<WFMBuyerQueueDTO> wfmBuyerQueueDTOList = new ArrayList<WFMBuyerQueueDTO>();
			WFMBuyerQueueDTO wfmBuyerQueueDTO = new WFMBuyerQueueDTO();
			// If its the service live admin. Change the company Id to 9 ( It is set as 
			// the company id to which SO belongs to in SODetailsControllerAction class) 
			if (securityContext.isSlAdminInd() && !securityContext.isAdopted()) {
				buyerId = String.valueOf(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
			} else {
				buyerId = securityContext.getCompanyId().toString();
			}
			try {
				wfmBuyerQueueDTOList = getWFMQueueAndTasks(buyerId , soId, groupId);
				wfmBuyerQueueDTO = getWFMCallBackQueueAndTasks(buyerId);
			} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
				e.printStackTrace();
				logger.error("Error receiving the queues for buyer= "+buyerId+"  ,soid= "+soId+" and/or groupid ="+groupId+"");
			}
			setAttribute("wfmBuyerQueueDTOList",wfmBuyerQueueDTOList);
			setAttribute("wfmBuyerCBQueueDTO",wfmBuyerQueueDTO);
			
		}
		
		// Counter Offer reasons
		try
		{
			setAttribute("rescheduleDateReasons", detailsDelegate.getReasonsForSelectedCounterOffer(OrderConstants.RESCHEDULE_SERVICE_DATE));
			setAttribute("increaseMaxPriceReasons", detailsDelegate.getReasonsForSelectedCounterOffer(OrderConstants.SPEND_LIMIT));					
		}
		catch (DelegateException e)
		{
			logger.error("getReasonsForSelectedCounterOffer() failed in SODetailsQuickLinks.populateDTO()");
		}
		

	}
	
	private void setSimpleOrderQuickInfo() {
		//SL-19820
		//String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		//Integer statusId = (Integer) (getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE));
		String soID = (String) getAttribute(OrderConstants.SO_ID);
		Integer statusId =null;
		if(null!=getCurrentServiceOrderFromRequest()){
		 statusId = getCurrentServiceOrderFromRequest().getStatus();
		}
		String status = null;
		Integer subStatusId =null;
		String title = null;
		Double spendLimit = null;
		Double finalPrice = null;
		String provider = null;
		String providerAlternatePhoneNumber = null;
		String providerMainPhoneNumber = null;
		String buyer = null;
		String endCustomer = null;
		String endCustomerPrimaryPhoneNumber=null;
		ServiceOrderDTO serviceOrderDTO = null;
		List<ProviderResultVO> routedFrimResources=new ArrayList<ProviderResultVO>();
		List<ProviderResultVO> routedResourcesList=new ArrayList<ProviderResultVO>();
		List<ProviderResultVO> counterOfferedResources=new ArrayList<ProviderResultVO>();
		String location = null;
		String distanceInMiles = null;
		String appointmentDates = null;
		String serviceWindow = null;
		List<AssociatedIncidentVO> associatedIncidents = null;
		String zip = null;
		String fromLocation = null;
		Boolean priceModelBid = null;
		Boolean carSO=null;
		String soId=null;
		String buyerPrice =null;
		String balance="";
		String buyerComments=null;
		String buyerEntryDate=null;
		String providerPrice=null;
		String providerComments=null;
		String providerEntryDate=null;
		String pendingCancelSubstatus=null;
		String buyerPvsAmount ="";
		String firmBuissinessName=null;
		String firmBusinessPhoneNumber=null;
		Integer acceptedVendorId=null;
		boolean isSoLevelAutoACH=false;
		String firmName = null;
		String firmPhoneNumber = null;
		boolean isNonFunded=false;
		Boolean recallProvider = null;
		String originalSoId=null;
		if (soID != null) {

			try {
				//serviceOrderDTO = getCurrentServiceOrderFromSession();
				serviceOrderDTO = getCurrentServiceOrderFromRequest();
				if (serviceOrderDTO != null)
				{
					carSO=serviceOrderDTO.isCarSO();
					routedResourcesList=serviceOrderDTO.getRoutedResourcesForFirm();
					if(routedResourcesList!=null){
						for(ProviderResultVO resource : routedResourcesList){
							if(resource.getProviderRespid() == 2){
								counterOfferedResources.add(resource);
							}else{
								routedFrimResources.add(resource);
							}
						}
					}
					//SL-19820
					//statusId = getCurrentServiceOrderStatusFromSession();
					//setCurrentSOStatusCodeInSession(statusId);
					statusId = serviceOrderDTO.getStatus();
					setCurrentSOStatusCodeInRequest(statusId);
                    subStatusId = serviceOrderDTO.getSubStatus();
                	status = serviceOrderDTO.getPrimaryStatus();
                	serviceOrderDTO.getSubStatusString();
					title = serviceOrderDTO.getTitle();
					
					//Sl-21645
					/*if("Accepted".equals(status)){
						EstimateVO estimateVo = soMonitorDelegate.getServiceOrderEstimationDetails(soID,get_commonCriteria().getCompanyId(),get_commonCriteria().getVendBuyerResId());						
						if(null != estimateVo && null != estimateVo.getBuyerRefValue() && estimateVo.getBuyerRefValue().equals("ESTIMATION")){
							serviceOrderDTO.setTotalSpendLimit(estimateVo.getTotalPrice());
							//Routed
							 //Accepted
							  //Active
						}
					} */
					
					EstimateVO estimateVo =null;
					if(get_commonCriteria().getRoleType().equalsIgnoreCase("Buyer")){
						estimateVo = soMonitorDelegate.getServiceOrderEstimationDetails(soID);
					}else{
						estimateVo = soMonitorDelegate.getServiceOrderEstimationDetails(soID,get_commonCriteria().getCompanyId(),get_commonCriteria().getVendBuyerResId());	
					}
											
					if(null!=status && !status.equals("Routed") && !status.equals("Draft") && null != estimateVo && null != estimateVo.getBuyerRefValue() && estimateVo.getBuyerRefValue().equals("ESTIMATION")){
						serviceOrderDTO.setTotalSpendLimit(estimateVo.getTotalPrice());
						}
					
					if (serviceOrderDTO.getTotalSpendLimit() != null){
						spendLimit = serviceOrderDTO.getTotalSpendLimit();
					}else
					{
						spendLimit = 0.0;
					}
					
					if (statusId == 165)
					{
						
							
						soId=soID;
						DateFormat providerDateFormat = new SimpleDateFormat("MM/dd/yyyy");
						NumberFormat formatter = new DecimalFormat("###0.00");

							
						if(null!= serviceOrderDTO.getBuyerPendingCancelPrice() &&  null!= serviceOrderDTO.getBuyerPendingCancelPrice().getPrice())
						{
							buyerPrice=formatter.format(serviceOrderDTO.getBuyerPendingCancelPrice().getPrice());
						}
						if(null!= serviceOrderDTO.getBuyerPendingCancelPrice() &&  null!= serviceOrderDTO.getBuyerPendingCancelPrice().getComments())
						{
							buyerComments=serviceOrderDTO.getBuyerPendingCancelPrice().getComments();
						}
						if(null!= serviceOrderDTO.getBuyerPendingCancelPrice() &&  null!= serviceOrderDTO.getBuyerPendingCancelPrice().getEntryDate())
						{
							buyerEntryDate=providerDateFormat.format(serviceOrderDTO.getBuyerPendingCancelPrice().getEntryDate());
						}
						if(null!= serviceOrderDTO.getProviderPendingCancelPrice() &&  null!= serviceOrderDTO.getProviderPendingCancelPrice().getPrice())
						{
							providerPrice=formatter.format(serviceOrderDTO.getProviderPendingCancelPrice().getPrice());
						}
						if(null!= serviceOrderDTO.getProviderPendingCancelPrice() &&  null!= serviceOrderDTO.getProviderPendingCancelPrice().getComments())
						{
							providerComments=serviceOrderDTO.getProviderPendingCancelPrice().getComments();
						}
						if(null!= serviceOrderDTO.getProviderPendingCancelPrice() &&  null!= serviceOrderDTO.getProviderPendingCancelPrice().getEntryDate())
						{
							providerEntryDate=providerDateFormat.format(serviceOrderDTO.getProviderPendingCancelPrice().getEntryDate());
						}
							
						if(serviceOrderDTO.getSubStatus()==null)
						{
							pendingCancelSubstatus=null;
						}
						else if(serviceOrderDTO.getSubStatus().intValue()==68)
						{
							pendingCancelSubstatus="pendingReview";
							
							try
							{
							// fetch fundingtype Id from so_hdr 
							Integer soFundingTypeId=manageScopeService.getSoLevelFundingTypeId(soId);
					        if(soFundingTypeId.intValue()==40){
					            isSoLevelAutoACH=true;
					        }	
							BigDecimal availableBalance = manageScopeService.getAvailableBalance(new Integer(serviceOrderDTO.getBuyerID()));
							if(null!=availableBalance)
							{
								balance= formatter.format( availableBalance.doubleValue());
							}
							}catch(Exception e)
							{
								
							}
								
						}
						else if(serviceOrderDTO.getSubStatus().intValue()==69)
						{
							pendingCancelSubstatus="pendingResponse";
							double prevBuyerAmt = 0.0d;
							try
							{
								// fetch fundingtype Id
								Integer soFundingTypeId=manageScopeService.getSoLevelFundingTypeId(soId);
						        if(soFundingTypeId.intValue()==40){
						            isSoLevelAutoACH=true;
						        }	
								BigDecimal availableBalance = manageScopeService.getAvailableBalance(new Integer(serviceOrderDTO.getBuyerID()));
								if(null!=availableBalance)
								{
									balance= formatter.format( availableBalance.doubleValue());	                     
								}

								PendingCancelPriceVO providerPrevPriceDetails  = getDetailsDelegate().getPendingCancelBuyerPriceDetails(soId);
								if(null != providerPrevPriceDetails && null != providerPrevPriceDetails.getPrice()) {
									prevBuyerAmt = providerPrevPriceDetails.getPrice().doubleValue();
								}
								buyerPvsAmount = formatter.format(prevBuyerAmt);
							
							}catch(Exception e)
							{
								
							}
						
						}
						
						
						
					}
					
					if (statusId == OrderConstants.CANCELLED_STATUS || statusId == OrderConstants.CLOSED_STATUS || statusId == OrderConstants.COMPLETED_STATUS ){
						//Check if its already in DTO, saves one DB call.
						if(serviceOrderDTO.getUpsellInfo() == null){
							getUpsellInfo(serviceOrderDTO);
						}
						
						double totalFinalPrice=0.0;
						try{
						if("3000".equals(serviceOrderDTO.getBuyerID()))
						{	
							for (ProviderInvoicePartsVO providerInvoiceParts : serviceOrderDTO.getInvoiceParts()) {
								if(providerInvoiceParts!=null && providerInvoiceParts.getPartStatus()!=null
										&& providerInvoiceParts.getPartStatus().equalsIgnoreCase(Constants.PART_STATUS_INSTALLED)){
								 totalFinalPrice+= providerInvoiceParts.getFinalPrice().doubleValue();
							}
							}
						}
						logger.info("The total Price is "+totalFinalPrice);
						finalPrice = serviceOrderDTO.getFinalPartsPrice() + serviceOrderDTO.getFinalLaborPrice() 
						              + serviceOrderDTO.getUpsellInfo().getProviderPaidTotal()+totalFinalPrice;
						}catch(Exception totalExc)
						{
							logger.info("Exception in calculating Final Price"+totalExc);
						}
						
					}
					priceModelBid = (Constants.PriceModel.ZERO_PRICE_BID.equals(serviceOrderDTO.getPriceModel()));
					
					/*For Checking Non Funded Feature to enable/disable pricing
					 * Approach 1: fetching the indicator from SO directly, assuming it is set during So creation
					 * Approach 2: fetching the indicator by querying the DB*/
					
					//Approach 1
					isNonFunded=serviceOrderDTO.getNonFundedInd();
					//Approach 2
					//isNonFunded=detailsDelegate.checkNonFunded(soID);
					buyer = serviceOrderDTO.getBuyerWidget();
					provider = serviceOrderDTO.getProviderWidget();
					int isUserSearsBuyer = Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SEARS_BUYER_ID));
					int currentBuyerId = Integer.parseInt(serviceOrderDTO.getBuyerID());
					boolean isSLAdmin = get_commonCriteria().getSecurityContext().isSlAdminInd();
					boolean isProviderAdmin =  get_commonCriteria().getSecurityContext().isPrimaryInd();
					boolean isDispatchInd = get_commonCriteria().getSecurityContext().isDispatchInd();
					Integer roleId = get_commonCriteria().getSecurityContext().getRoleId();					
					//if(OrderConstants.PROVIDER_ROLEID != roleId.intValue()) { 
						providerAlternatePhoneNumber = serviceOrderDTO.getProviderAlternatePhoneNumber();
						providerMainPhoneNumber = serviceOrderDTO.getProviderMainPhoneNumber();
					//}
					endCustomer = serviceOrderDTO.getEndCustomerWidget();
					endCustomerPrimaryPhoneNumber=serviceOrderDTO.getEndCustomerPrimaryPhoneNumberWidget();
					location = serviceOrderDTO.getLocationWidget();
					//SL-18360:Set FirmLevel Details in DTO for BuyerOnly ,so status >ACCEPTED.
					/*if(OrderConstants.BUYER_ROLEID == roleId.intValue() && statusId.intValue() >=110){
						ProviderFirmVO providerFirmVO = new ProviderFirmVO();
						providerFirmVO = getDetailsDelegate().getAcceptedFirmDetails(serviceOrderDTO.getSoId());
						firmBuissinessName = providerFirmVO.getBusinessName();
						firmBusinessPhoneNumber = providerFirmVO.getBusinessPhoneNumber();
						if(null != providerFirmVO.getVendorID()){
						acceptedVendorId=providerFirmVO.getVendorID().toString();
						}
					}*/
					if (serviceOrderDTO.getAppointmentDates() != null){
						appointmentDates = serviceOrderDTO.getAppointmentDates();
					}
					if (serviceOrderDTO.getServiceWindow() != null){
						serviceWindow = serviceOrderDTO.getServiceWindow();
					}

					if(OrderConstants.BUYER_ROLEID == roleId.intValue()) {
						associatedIncidents = serviceOrderDTO.getAssociatedIncidents();
					}
					zip = serviceOrderDTO.getZip();
					if(OrderConstants.PROVIDER_ROLEID == roleId.intValue()) {
						if((isProviderAdmin!=true && isDispatchInd!=true) ||(statusId != OrderConstants.ROUTED_STATUS )){
							fromLocation = serviceOrderDTO.getResourceDispatchAddress();
							distanceInMiles = serviceOrderDTO.getDistanceInMiles().toString();
						}
						if((isProviderAdmin==true||isDispatchInd==true) && statusId == OrderConstants.ROUTED_STATUS && priceModelBid==true){
							fromLocation = serviceOrderDTO.getResourceDispatchAddress();
							distanceInMiles = serviceOrderDTO.getDistanceInMiles().toString();
					}
					}
					firmName = serviceOrderDTO.getFirmName();
					acceptedVendorId = serviceOrderDTO.getAcceptedVendorId();
					firmPhoneNumber = serviceOrderDTO.getFirmPhoneNumber();
				} else {
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			soID = "Error: id not found";
		}
		if(null!=soCloseDtoComplete && null!=serviceOrderDTO){
			soCloseDtoComplete.setInvoiceParts(serviceOrderDTO.getInvoiceParts());			
		}
		if(null!=serviceOrderDTO && StringUtils.isNotBlank(serviceOrderDTO.getOriginalSoId())){
			originalSoId = serviceOrderDTO.getOriginalSoId();
			recallProvider = serviceOrderDTO.isRecallProvider();
		}
		setAttribute("soCloseDtoComplete", soCloseDtoComplete);		
		String closeAndPay= getRequest().getParameter("closeAndPayTab");
		setAttribute("closePayTab", closeAndPay);
		setAttribute("soID", soID);
		setAttribute("status", status);
		setAttribute("title", title);
		//Sl-19820
		//getSession().setAttribute("spendLimit", spendLimit);
		//getSession().setAttribute("finalPrice", finalPrice);
		setAttribute("spendLimit", spendLimit);
		setAttribute("finalPrice", finalPrice);
		setAttribute("priceModelBid", priceModelBid);
		setAttribute("provider", provider);
		setAttribute("providerAlternatePhoneNumber", providerAlternatePhoneNumber);
		setAttribute("providerMainPhoneNumber", providerMainPhoneNumber);
		setAttribute("buyer", buyer);
		setAttribute("endCustomer", endCustomer);
		setAttribute("endCustomerPrimaryPhoneNumber", endCustomerPrimaryPhoneNumber);
		setAttribute("location", location);
		setAttribute("distanceInMiles", distanceInMiles);
		setAttribute("appointmentDates", appointmentDates);
		setAttribute("serviceWindow", serviceWindow);
		setAttribute("associatedIncidents", associatedIncidents);
		setAttribute("buyerPvsAmount", buyerPvsAmount);
		setAttribute("buyerPrice", buyerPrice);
		setAttribute("buyerComments", buyerComments);
		setAttribute("buyerEntryDate", buyerEntryDate);
		setAttribute("providerPrice", providerPrice);
		setAttribute("providerComments", providerComments);
		setAttribute("providerEntryDate", providerEntryDate);
		setAttribute("pendingCancelSubstatus", pendingCancelSubstatus);
		setAttribute("balance", balance);
		setAttribute("firmName",firmName );
		setAttribute("acceptedVendorId",acceptedVendorId);
		setAttribute("firmPhoneNumber",firmPhoneNumber);
		setAttribute("firmBuissinessName",firmBuissinessName);
		setAttribute("firmBusinessPhoneNumber", firmBusinessPhoneNumber);
		setAttribute("acceptedVendorId", acceptedVendorId);
		setAttribute("originalSoId", originalSoId);
		setAttribute("recallProvider", recallProvider);
		//SL-19820
		if(null!=serviceOrderDTO){
			setAttribute("buyerID", serviceOrderDTO.getBuyerID());
		}
		//Non Funded Feature Set
		setAttribute("isNonFunded", isNonFunded);
		if(isSoLevelAutoACH)
		{
			setAttribute("isSoLevelAutoACH","true");
		}
		else
		{
			setAttribute("isSoLevelAutoACH","false");
		}
		//set New attribute foe achSo
		if(null!=serviceOrderDTO)
		{
			setAttribute("maximumParts", serviceOrderDTO.getFinalPartsPrice());
			setAttribute("maximumLabor", serviceOrderDTO.getFinalLaborPrice());
			if(serviceOrderDTO.getUpsellInfo()!=null)
			{
				setAttribute("providerPaidTotal", serviceOrderDTO.getUpsellInfo().getProviderPaidTotal());
			}
		}
		
		
		
		//for Google direction
		setAttribute("fromLoc", fromLocation);
		setAttribute("zip", zip);
		String locationNoComma = "";
		if(null != location){
			locationNoComma = location.replace(",", "");
		}
		setAttribute("toLocation", locationNoComma);
		setAttribute("firmResource",routedFrimResources);
		setAttribute("counteredResources",counterOfferedResources);
		//SL-19820
		//getSession().setAttribute("carSO", carSO);
		setAttribute("carSO", carSO);
		// Rate flags - to check for Rate Provider/Rate Buyer button
		if (serviceOrderDTO != null
				&& serviceOrderDTO.isBuyerHasRatedProvider()) {
			setBuyerHasRatedProvider(serviceOrderDTO.isBuyerHasRatedProvider());
		}
		if (serviceOrderDTO != null
				&& serviceOrderDTO.isProviderHasRatedBuyer()) {
			setProviderHasRatedBuyer(serviceOrderDTO.isProviderHasRatedBuyer());
		}

		// For Reschedule
		if (statusId != null && (OrderConstants.ACCEPTED_STATUS == statusId
				|| OrderConstants.ACTIVE_STATUS == statusId
				|| OrderConstants.PROBLEM_STATUS == statusId)) {

			if (serviceOrderDTO != null
					&& serviceOrderDTO.getRescheduleDates() != null
					&& (!"".equals(serviceOrderDTO.getRescheduleDates()))) {
				setRescheduleRequest(true);
				ServiceOrderRescheduleVO rescheduleVO = getDetailsDelegate()
						.getRescheduleRequestInfo(soID);
				if (rescheduleVO != null && rescheduleVO.getUserRole() != null) {
					setRescheduleRequestRole(rescheduleVO.getUserRole());
				} else {
					setRescheduleRequestRole(-1);
				}
			} else {
				setRescheduleRequest(false);
				setRescheduleRequestRole(-1);
			}
		} else {
			setRescheduleRequest(false);
			setRescheduleRequestRole(-1);
		}

		
		
		
	}
	
	private void setGroupOrderQuickInfo() {
		//SL-19820 : group order will not be available in session 
		loadOrderGroup((String)getAttribute(GROUP_ID));
		//OrderGroupVO orderGroupVO = (OrderGroupVO)getSession().getAttribute(THE_GROUP_ORDER);
		OrderGroupVO orderGroupVO = (OrderGroupVO)getAttribute(THE_GROUP_ORDER);
		String soID = orderGroupVO.getGroupId();
		String status = orderGroupVO.getStatus();
		String title = orderGroupVO.getTitle();
		Double spendLimit = new Double(0.0);
		Boolean carSO=null;
		if (orderGroupVO.getFinalSpendLimitLabor() != null) {
			spendLimit = orderGroupVO.getFinalSpendLimitLabor();
			if (orderGroupVO.getFinalSpendLimitParts() != null) {
				spendLimit += orderGroupVO.getFinalSpendLimitParts();
			}
		}
		String buyerWidget = orderGroupVO.getBuyerWidget();
		String endCustomerWidget = orderGroupVO.getEndCustomerWidget();
		String endCustomerPrimaryPhoneNumberWidget = orderGroupVO.getEndCustomerPrimaryPhoneNumberWidget();
		System.out.println("endCustomerPrimaryPhoneNumberWidget"+endCustomerPrimaryPhoneNumberWidget); 
		String locationWidget = orderGroupVO.getLocationWidget();
		String appointmentDates = orderGroupVO.getAppointmentDates();
		String serviceWindow = orderGroupVO.getServiceWindow();
		Double finalPrice = null;	// Group will never have final price on Group-SOD
		String provider = null;		// Group will never have provider on Group-SOD
		String distanceInMiles = null;	// Group will never have distance on Group-SOD
		String zip = orderGroupVO.getZip();
		Integer roleId = get_commonCriteria().getSecurityContext().getRoleId();
		String fromLocation = null;
		List<ProviderResultVO> routedFrimResources=new ArrayList<ProviderResultVO>();
		List<ProviderResultVO> routedResourcesList=orderGroupVO.getRoutedResourcesForFirm();
		List<ProviderResultVO> counterOfferedResources=new ArrayList<ProviderResultVO>();
		if(routedResourcesList!=null){
			for(ProviderResultVO resource : routedResourcesList){
				if(resource.getProviderRespid() == 2){
					counterOfferedResources.add(resource);
				}else{
					routedFrimResources.add(resource);
				}
			}
		}
		carSO=orderGroupVO.isCarSO();
		
		if(OrderConstants.PROVIDER_ROLEID == roleId.intValue()){
			fromLocation = orderGroupVO.getResourceDispatchAddress();
		}
		
		// the buyer id doesn't show up at times.
		
		setAttribute("soID", soID);
		setAttribute("status", status);
		setAttribute("title", title);
		setAttribute("spendLimit", spendLimit);
		setAttribute("buyer", buyerWidget);
		setAttribute("endCustomer", endCustomerWidget);
		setAttribute("endCustomerPrimaryPhoneNumber", endCustomerPrimaryPhoneNumberWidget);
		setAttribute("location", locationWidget);
		setAttribute("appointmentDates", appointmentDates);
		setAttribute("serviceWindow", serviceWindow);
		setAttribute("finalPrice", finalPrice);
		setAttribute("provider", provider);
		setAttribute("distanceInMiles", distanceInMiles);
		setAttribute("fromLoc", fromLocation);
		setAttribute("zip", zip);
		String locationNoComma = "";
		if(null != locationWidget){
			locationNoComma = locationWidget.replace(",", "");
		}
		setAttribute("toLocation", locationNoComma);
		setAttribute("firmResource",routedFrimResources);
		setAttribute("counteredResources",counterOfferedResources);
		setAttribute("isNonFunded", false);
		//SL-19820
		setAttribute("buyerID", buyerWidget);
	}
	
	private int checkCurrentOrderType() {
		int orderType = 0;
		//SL-19820
		//String groupID = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
		String groupID = (String)getAttribute(OrderConstants.GROUP_ID);
		if (org.apache.commons.lang.StringUtils.isNotBlank(groupID)) {
			return OrderConstants.ORDER_TYPE_GROUP;
		} else {
			String soID = (String)getAttribute(OrderConstants.SO_ID);
			if (soID != null) {
				//SL-19820
				//ServiceOrderDTO serviceOrderDTO = getCurrentServiceOrderFromSession();
				ServiceOrderDTO serviceOrderDTO = null;
				try{
					if(org.apache.commons.lang.StringUtils.isNotBlank(getParameter("resId"))) {
						Integer resId = Integer.parseInt(getParameter("resId"));
						serviceOrderDTO = getDetailsDelegate().getServiceOrder(soID, get_commonCriteria().getRoleId(), resId);
					}
					else {
						serviceOrderDTO = getDetailsDelegate().getServiceOrder(soID, get_commonCriteria().getRoleId(), null);
					}
					//SL-19820	Added  	for Assurent  buyer Incident Tracker.
					Integer roleId = get_commonCriteria().getSecurityContext().getRoleId();
					if (OrderConstants.BUYER_ROLEID == roleId.intValue()) {

						List<AssociatedIncidentVO> incidents = null;
						incidents = detailsDelegate
								.getAssociatedIncidents(serviceOrderDTO.getId());
						if (null != incidents) {
							serviceOrderDTO.setAssociatedIncidents(incidents);
						}
					}
					
				}catch(Exception e){
					logger.error("Exception while trying to fetch SO Details");
				}
				setAttribute(THE_SERVICE_ORDER, serviceOrderDTO);
				
				if (serviceOrderDTO != null && org.apache.commons.lang.StringUtils.isNotBlank(serviceOrderDTO.getParentGroupId())) {
					return OrderConstants.ORDER_TYPE_CHILD;
				} else {
					return OrderConstants.ORDER_TYPE_INDIVIDUAL;
				}
			} else {
				logger.error("SO_ID not present in session; return order type = 0 !!");
			}
		}
		logger.info("Order Type = ["+orderType+"]");
		return orderType;
	}
	
	private Double getAddonPrice(String soId) {
		// Fetch the Addons related to ServiceOrder
		
		//For Scalability : Just get the required fields from So_addon and only if it has quantity > 0.
		List<ServiceOrderAddonVO> addons = serviceOrderUpsellBO.getAddonswithQtybySoId(soId);
		
		// Convert Raw Data to DTO
		Double providerPaidTotal = 0.0;	
		for(ServiceOrderAddonVO vo : addons)
		{
			if(vo.getQuantity() > 0){
				Double providerPaid = vo.getRetailPrice() - MoneyUtil.getRoundedMoney((vo.getMargin() * vo.getRetailPrice()));
				providerPaid = providerPaid * vo.getQuantity();
				providerPaidTotal = providerPaidTotal + providerPaid;
			}
		}
		return MoneyUtil.getRoundedMoney(providerPaidTotal);	
	}

	private void setBuyerButtons(Integer status, int orderType, String priceType) {
		/*To remove all the buttons for the Order Quick Links in SOW(Edit Mode) get the value 
		 * of action parameter. If the value is not null, then it is SOW(Edit Mode)*/
		String action = getParameter("action");
		if(SLStringUtils.isNullOrEmpty(action))
			action = (String)getSession().getAttribute("action");
		if (StringUtils.isEmpty(action)) {
			List<SODetailsQuickLinksDTO> linkList = new ArrayList<SODetailsQuickLinksDTO>();

			setBuyerCommonButtons(linkList,status,orderType);
			if (OrderConstants.ACCEPTED_STATUS == status) {
				// Set Reschedule buttons
				setReschduleButtons(linkList, get_commonCriteria().getRoleId());
				linkList.add(dtoList.get(GIF_CANCEL_SERVICE_ORDER));
				if(priceType != null && priceType.equals(TASK_LEVEL_PRICING)){
					linkList.add(dtoList.get(GIF_MANAGE_SCOPE));
				}
			} else if (OrderConstants.ACTIVE_STATUS == status) {
				// Set Reschedule buttons
				setReschduleButtons(linkList, get_commonCriteria().getRoleId());

				linkList.add(dtoList.get(GIF_REPORT_A_PROBLEM));
				linkList.add(dtoList.get(GIF_CANCEL_SERVICE_ORDER));
				if(priceType != null && priceType.equals(TASK_LEVEL_PRICING)){
					linkList.add(dtoList.get(GIF_MANAGE_SCOPE));
				}
			} else if (OrderConstants.CANCELLED_STATUS == status) {

			} else if (OrderConstants.CLOSED_STATUS == status) {
				if (isOldSurvey && !isBuyerHasRatedProvider()) {
					linkList.add(dtoList.get(GIF_RATE_PROVIDER));
				}
				linkList.add(dtoList.get(GIF_VIEW_COMPLETION_RECORD));
			} else if (OrderConstants.COMPLETED_STATUS == status) {
				linkList.add(dtoList.get(GIF_REPORT_A_PROBLEM));
				linkList.add(dtoList.get(GIF_CLOSE_AND_PAY));
			} else if (OrderConstants.EXPIRED_STATUS == status) {
				if (orderType != OrderConstants.ORDER_TYPE_GROUP) { // not allowed at group level
					linkList.add(dtoList.get(GIF_EDIT_SERVICE_ORDER));
					linkList.add(dtoList.get(GIF_VOID_SERVICE_ORDER));
				}
			} else if (OrderConstants.PROBLEM_STATUS == status) {
				linkList.add(dtoList.get(GIF_CANCEL_SERVICE_ORDER));
				setReschduleButtons(linkList, get_commonCriteria().getRoleId());
				if(priceType != null && priceType.equals(TASK_LEVEL_PRICING)){
					linkList.add(dtoList.get(GIF_MANAGE_SCOPE));
				}
			} else if (OrderConstants.ROUTED_STATUS == status) {
				if (orderType != OrderConstants.ORDER_TYPE_GROUP) { // not allowed at group level
					linkList.add(dtoList.get(GIF_EDIT_SERVICE_ORDER));
					linkList.add(dtoList.get(GIF_VOID_SERVICE_ORDER));
				}
				linkList.add(dtoList.get(GIF_VIEW_PROVIDER_RESPONSES));
			} else if (OrderConstants.VOIDED_STATUS == status) {
				// No extra non-common buttons
			}else if (OrderConstants.DRAFT_STATUS == status){
				// No extra non-common buttons
				// Fix for SL-6723 : show the edit "Edit Service Order" button for draft SOs.
				// http://jira.intra.sears.com/jira/browse/SL-6723
				if (orderType != OrderConstants.ORDER_TYPE_GROUP) { // not allowed at group level
					linkList.add(dtoList.get(GIF_EDIT_SERVICE_ORDER));
					linkList.add(dtoList.get(GIF_DELETE_DRAFT_ORDER));
				}
			}else if (OrderConstants.PENDING_CANCEL_STATUS == status){
				linkList.remove(dtoList.get(GIF_MANAGE_DOCS_PHOTOS));
				linkList.remove(dtoList.get(GIF_COPY_SERVICE_ORDER));
				linkList.remove(dtoList.get(GIF_ADD_AND_VIEW_NOTES));
			}

			
			setAttribute("linkList", linkList);
		}
		
	}

	private void setBuyerCommonButtons(
		List<SODetailsQuickLinksDTO> linkList,Integer status, int orderType) {
		if(StringUtils.isEmpty(action)){	
			if (OrderConstants.DRAFT_STATUS == status) {
				if (orderType != OrderConstants.ORDER_TYPE_GROUP) { // not allowed at group level
					linkList.add(dtoList.get(GIF_MANAGE_DOCS_PHOTOS));
					linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
				}
				linkList.add(dtoList.get(GIF_ADD_AND_VIEW_NOTES));
				linkList.add(dtoList.get(GIF_SERVICE_LIVE_SUPPORT));
			} else {
				if (orderType != OrderConstants.ORDER_TYPE_GROUP) { // not allowed at group level
					linkList.add(dtoList.get(GIF_VIEW_PRINT_PDF));
					linkList.add(dtoList.get(GIF_MANAGE_DOCS_PHOTOS));
					linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
					linkList.add(dtoList.get(GIF_COPY_SERVICE_ORDER));
				}
				linkList.add(dtoList.get(GIF_ADD_AND_VIEW_NOTES));
				linkList.add(dtoList.get(GIF_SERVICE_LIVE_SUPPORT));
			}
		}	
	}

	private void setProviderCommonButtons(
			List<SODetailsQuickLinksDTO> linkList, int orderType) {
		if (orderType != OrderConstants.ORDER_TYPE_GROUP) { // not allowed at group level
			linkList.add(dtoList.get(GIF_MANAGE_DOCS_PHOTOS));
		}
	}

	private void setProviderButtons(Integer status, int orderType) {
		List<SODetailsQuickLinksDTO> linkList = new ArrayList<SODetailsQuickLinksDTO>();
		Integer primaryInd = 0;
		boolean assignedToFirm = Boolean.FALSE;
		Integer adminResId = ((SecurityContext) getSession().getAttribute(
		"SecurityContext")).getAdminResId();
		Integer resourceId = ( (SecurityContext)getSession().getAttribute("SecurityContext")).getVendBuyerResId();
		//SL-21645
		int vendorId = ( (SecurityContext)getSession().getAttribute("SecurityContext")).getCompanyId();
		String soId = getParameter("soId");
		EstimateVO estimateVo = soMonitorDelegate.getServiceOrderEstimationDetails(soId,vendorId,resourceId);
		//SL-21645
		
		setProviderCommonButtons(linkList, orderType);
		/******************************  CHANGED FOR SL - 5037 **********************************/
		Map<String, UserActivityVO> activities = ((SecurityContext) getSession().getAttribute(
		"SecurityContext")).getRoleActivityIdList();
		boolean canManageSO = false;
		//Checking whether the user has the rights to manage SO[4 is the Activity Id for the Activity-Manage Service Orders]
		if(activities != null && activities.containsKey("4")){
			canManageSO = true;
		}
		//Showing the 'ReAssign SO' button if the user has the rights to manage SO
		if(adminResId.equals(resourceId)|| ((SecurityContext)getSession().getAttribute("SecurityContext")).getSLAdminInd() || canManageSO)
		{
			/******************************  CHANGED FOR SL - 5037 **********************************/
			primaryInd = 1;
		}
		//SL-19820
		//ServiceOrderDTO dto = getCurrentServiceOrderFromSession();
		ServiceOrderDTO dto = getCurrentServiceOrderFromRequest();
		String assignmentType = OrderConstants.SO_ASSIGNMENT_TYPE_PROVIDER;
		if(null != dto && org.apache.commons.lang.StringUtils.isNotBlank(dto.getAssignmentType()) && OrderConstants.SO_ASSIGNMENT_TYPE_FIRM.equalsIgnoreCase(dto.getAssignmentType())){
			assignedToFirm = Boolean.TRUE;
			assignmentType = OrderConstants.SO_ASSIGNMENT_TYPE_FIRM;
		}
		//SL-19820
		//getSession().getServletContext().setAttribute(Constants.SESSION.SO_ASSIGNMENT_TYPE, assignmentType);
		setAttribute(Constants.SESSION.SO_ASSIGNMENT_TYPE, assignmentType);
		if (OrderConstants.ACCEPTED_STATUS == status) {
			linkList.add(dtoList.get(GIF_VIEW_PRINT_PDF));
			linkList.add(dtoList.get(GIF_ADD_AND_VIEW_NOTES));
			linkList.add(dtoList.get(GIF_RELEASE_SERVICE_ORDER));
			linkList.add(dtoList.get(GIF_TIME_ON_SITE));
			setReschduleButtons(linkList, OrderConstants.PROVIDER_ROLEID);

			linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
			linkList.add(dtoList.get(GIF_SERVICE_LIVE_SUPPORT));		
			if(primaryInd > 0){
				if(assignedToFirm){
					linkList.add(dtoList.get(GIF_ASSIGN_SERVICE_ORDER));
				}else{
					linkList.add(dtoList.get(GIF_REASSIGN_SERVICE_ORDER));
				}	
			}
			
		} else if (OrderConstants.ACTIVE_STATUS == status) {
			linkList.add(dtoList.get(GIF_VIEW_PRINT_PDF));
			linkList.add(dtoList.get(GIF_ADD_AND_VIEW_NOTES));
			linkList.add(dtoList.get(GIF_RELEASE_SERVICE_ORDER));
			linkList.add(dtoList.get(GIF_REPORT_A_PROBLEM));
			linkList.add(dtoList.get(GIF_TIME_ON_SITE));
			linkList.add(dtoList.get(GIF_COMPLETE_FOR_PAYMENT));
			setReschduleButtons(linkList, OrderConstants.PROVIDER_ROLEID);
			linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
			linkList.add(dtoList.get(GIF_SERVICE_LIVE_SUPPORT));
			if(primaryInd > 0){
				if(assignedToFirm){
					linkList.add(dtoList.get(GIF_ASSIGN_SERVICE_ORDER));
				}else{
					linkList.add(dtoList.get(GIF_REASSIGN_SERVICE_ORDER));
				}	
			}
		} else if (OrderConstants.CLOSED_STATUS == status) {
			linkList.add(dtoList.get(GIF_VIEW_PRINT_PDF));
			linkList.add(dtoList.get(GIF_ADD_AND_VIEW_NOTES));
			if (!isProviderHasRatedBuyer()) {
				linkList.add(dtoList.get(GIF_RATE_BUYER));
			}
			linkList.add(dtoList.get(GIF_VIEW_COMPLETION_RECORD));
			linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
			linkList.add(dtoList.get(GIF_SERVICE_LIVE_SUPPORT));
		} else if (OrderConstants.COMPLETED_STATUS == status) {
			linkList.add(dtoList.get(GIF_VIEW_PRINT_PDF));
			linkList.add(dtoList.get(GIF_ADD_AND_VIEW_NOTES));
			linkList.add(dtoList.get(GIF_REPORT_A_PROBLEM));
			linkList.add(dtoList.get(GIF_TIME_ON_SITE));
			linkList.add(dtoList.get(GIF_VIEW_COMPLETION_RECORD));
			linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
			linkList.add(dtoList.get(GIF_SERVICE_LIVE_SUPPORT));
		} else if (OrderConstants.PROBLEM_STATUS == status) {
			linkList.add(dtoList.get(GIF_VIEW_PRINT_PDF));
			linkList.add(dtoList.get(GIF_ADD_AND_VIEW_NOTES));
			linkList.add(dtoList.get(GIF_RELEASE_SERVICE_ORDER));
			setReschduleButtons(linkList, OrderConstants.PROVIDER_ROLEID);
			if (orderType != OrderConstants.ORDER_TYPE_GROUP) { // not allowed at group level
				linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
			}
			linkList.add(dtoList.get(GIF_SERVICE_LIVE_SUPPORT));
			linkList.add(dtoList.get(GIF_TIME_ON_SITE));
			if(primaryInd > 0){
				if(assignedToFirm){
					linkList.add(dtoList.get(GIF_ASSIGN_SERVICE_ORDER));
				}else{
					linkList.add(dtoList.get(GIF_REASSIGN_SERVICE_ORDER));
				}	
			}
		} else if (OrderConstants.ROUTED_STATUS == status) {
			if (hasConditionalOfferPending()) {
				if (orderType != OrderConstants.ORDER_TYPE_CHILD) { // not allowed at child level; allowed either at group level or for individual order
					linkList.add(dtoList.get(GIF_WITHDRAW_COND_ACCEPT));
				}
				setAttribute("hasCounterOffer",true);
			} 
				Integer indcator =2;
				SecurityContext securityContext = (SecurityContext) getSession()
				.getAttribute(SECURITY_KEY);
				try {
					indcator = iTermsAndCondDelegate.getData(securityContext.getCompanyId());
				} catch (Exception e) {
					logger.error("Unexpected exception in SODetailsQuickLinksAction.setProviderButtons()", e);
				}
				
				//if((null != estimateVo && null != estimateVo.getBuyerRefValue() && estimateVo.getBuyerRefValue().equals("ESTIMATION")) || (( "3333".equals((String) getAttribute("buyerID"))))){
				if((null != estimateVo && null != estimateVo.getBuyerRefValue() && estimateVo.getBuyerRefValue().equals("ESTIMATION")) ){
					logger.info("estimate Request so don't show GIF_ACCEPT_SERVICE_ORDER");
				}else{
					
					if (orderType != OrderConstants.ORDER_TYPE_CHILD) { // not allowed at child level; allowed either at group level or for individual order
						if(adminResId.equals(resourceId)){
							linkList.add( new SODetailsQuickLinksDTO(
									JS_ADMIN_ACCEPT_SERVICE_ORDER, ACT_ACCEPT_SERVICE_ORDER,
									GIF_ACCEPT_SERVICE_ORDER, HEIGHT_SHORT, STD_BUTTON_STYLE));
						}else{
							linkList.add(dtoList.get(GIF_ACCEPT_SERVICE_ORDER));
						}					
					}
				}
							
				if (orderType != OrderConstants.ORDER_TYPE_CHILD) { // not allowed at child level; allowed either at group level or for individual order
					linkList.add(dtoList.get(GIF_REJECT_SERVICE_ORDER));
				}
				
				if (indcator == 1){
					if (orderType != OrderConstants.ORDER_TYPE_CHILD) { // not allowed at child level; allowed either at group level or for individual order
						linkList.add(dtoList.get(GIF_ACCEPT_WITH_CONDITIONS));
					}
				}
				else{
					if(securityContext.isPrimaryInd()){
						if (orderType != OrderConstants.ORDER_TYPE_CHILD) { // not allowed at child level; allowed either at group level or for individual order
							linkList.add(dtoList.get(GIF_BUX_ACCEPT_WITH_CONDITIONS));
						}
					}
					else{
						if (orderType != OrderConstants.ORDER_TYPE_CHILD) { // not allowed at child level; allowed either at group level or for individual order
							linkList.add(dtoList.get(GIF_BUX_WARN_ACCEPT_WITH_CONDITIONS));
						}
					}
				}
			
			if (orderType != OrderConstants.ORDER_TYPE_GROUP) { // not allowed at group level
				linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
			}
		}else if (OrderConstants.CANCELLED_STATUS == status) {			
			linkList.add(dtoList.get(GIF_VIEW_PRINT_PDF));
			linkList.add(dtoList.get(GIF_ADD_AND_VIEW_NOTES));
			linkList.add(dtoList.get(GIF_SERVICE_LIVE_SUPPORT));
			linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
		}
		else if (OrderConstants.PENDING_CANCEL_STATUS == status) {			
			linkList.remove(dtoList.get(GIF_MANAGE_DOCS_PHOTOS));
			linkList.add(dtoList.get(GIF_VIEW_PRINT_PDF));
			linkList.add(dtoList.get(GIF_VIEW_ORDER_HISTORY));
			linkList.add(dtoList.get(GIF_SERVICE_LIVE_SUPPORT));
		}

		setAttribute("linkList", linkList);
	}

	/**
	 * 
	 * getWFMQueueDetails
	 * List<WFMBuyerQueueDTO> 
	 * @param buyerId
	 * @param soId
	 * @return
	 * @throws com.newco.marketplace.exception.core.BusinessServiceException
	 */
	public List<WFMBuyerQueueDTO> getWFMQueueDetails(String buyerId,String soId) throws com.newco.marketplace.exception.core.BusinessServiceException
	{
		List<WFMBuyerQueueDTO> wfmBuyerQueueDTOList = detailsDelegate.getWFMQueueDetails(buyerId,soId);
		return wfmBuyerQueueDTOList;
	}
	
	
	/**
	 * 
	 * getWFMQueueAndTasks
	 * List<WFMBuyerQueueDTO> 
	 * @param buyerId
	 * @param soId
	 * @param groupId
	 * @return
	 * @throws com.newco.marketplace.exception.core.BusinessServiceException
	 */
	public List<WFMBuyerQueueDTO> getWFMQueueAndTasks(String buyerId,String soId, String groupId) throws com.newco.marketplace.exception.core.BusinessServiceException
	{
		List<WFMBuyerQueueDTO> wfmBuyerQueueDTOList = detailsDelegate.getWFMQueueAndTasks(buyerId, soId, groupId);
		return wfmBuyerQueueDTOList;
	}
	
	public WFMBuyerQueueDTO getWFMCallBackQueueAndTasks(String buyerId) throws com.newco.marketplace.exception.core.BusinessServiceException
	{
		WFMBuyerQueueDTO wfmBuyerQueueDTOList = detailsDelegate.getWFMCallBackQueueAndTasks(buyerId);
		return wfmBuyerQueueDTOList;
	}
	
	
	private boolean hasConditionalOfferPending() {
		//Sl-19820
		//String soId = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		Integer routedResourceId = new Integer(0);
		//SL-19820
		//String groupId = (String)getSession().getAttribute(GROUP_ID);
		String groupId = (String)getAttribute(GROUP_ID);
		/*if (getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID) != null) {
			routedResourceId = Integer.valueOf((String) getSession()
					.getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID));
		}*/
		if (!SLStringUtils.isNullOrEmpty((String) getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID))) {
			routedResourceId = Integer.valueOf((String) getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID));
		}
	
		
		if (SLStringUtils.isNullOrEmpty(soId) == false) {
			if((Boolean)getSession().getAttribute("isProviderAdmin")==true||(Boolean)getSession().getAttribute("isDispatchInd")==true){
				//SL-19820
				//List<ProviderResultVO> routedResources=getCurrentServiceOrderFromSession().getRoutedResourcesForFirm();
				if(null!=getCurrentServiceOrderFromRequest()){
					
					List<ProviderResultVO> routedResources=getCurrentServiceOrderFromRequest().getRoutedResourcesForFirm();
					
					for(ProviderResultVO routedResource:routedResources){
						if(routedResource.getProviderRespid()==2){
							return true;
						}
					}	
				}
				
			}else{
				return detailsDelegate.hasCondOfferPending(soId, routedResourceId);
			}
		}
		else if(SLStringUtils.isNullOrEmpty(groupId) == false)
		{
			if((Boolean)getSession().getAttribute("isProviderAdmin")==true||(Boolean)getSession().getAttribute("isDispatchInd")==true){
				//Sl-19820
				//OrderGroupVO groupOrder=(OrderGroupVO)getSession().getAttribute(THE_GROUP_ORDER);
				OrderGroupVO groupOrder=(OrderGroupVO)getAttribute(THE_GROUP_ORDER);
				List<ProviderResultVO> routedResources=groupOrder.getRoutedResourcesForFirm();
				for(ProviderResultVO routedResource:routedResources){
					if(routedResource.getProviderRespid()==2){
						return true;
					}
				}
			}else{
				// retain one child orderId to return to the child details after group acceptance
				String firstChildSoId = detailsDelegate.getFirstChildInGroup(groupId);
				return detailsDelegate.hasCondOfferPending(firstChildSoId, routedResourceId);
			}
		}
		return false;
	}

	private void setReschduleButtons(
			List<SODetailsQuickLinksDTO> linkList, int userRole) {
		if (isRescheduleRequest()) {
			if (userRole == getRescheduleRequestRole().intValue()) {
				linkList.add(dtoList.get(GIF_CANCEL_RESCHEDULE_REQUEST));
				linkList.add(dtoList.get(GIF_EDIT_RESCHEDULE_REQUEST));
			} else {
				linkList.add(dtoList.get(GIF_ACCEPT_RESCHEDULE_REQUEST));
				linkList.add(dtoList.get(GIF_REJECT_RESCHEDULE_REQUEST));
			}
		} else {
			linkList.add(dtoList.get(GIF_REQUEST_RESCHEDULE));
		}
	}
	
	public String loadQuickLinks(){
		Integer acceptedResourceId= Integer.parseInt(getRequest().getParameter("acceptedResourceId"));
		int orderType = checkCurrentOrderType(); // returns OrderConstants.ORDER_TYPE_xxxxxx
		// Set text above buttons here.
		if (orderType == OrderConstants.ORDER_TYPE_GROUP) {
			setGroupOrderQuickInfo();
			//Sl-19820
			//OrderGroupVO orderGroupVO = (OrderGroupVO)getSession().getAttribute(THE_GROUP_ORDER);
			OrderGroupVO orderGroupVO = (OrderGroupVO)getAttribute(THE_GROUP_ORDER);
			List<ProviderResultVO> firmResources = orderGroupVO.getRoutedResourcesForFirm();
			for(ProviderResultVO resource : firmResources ){
				if(resource.getResourceId()== acceptedResourceId){
					setAttribute("fromLoc", resource.getResourceDispatchAddr());
					setAttribute("distanceInMiles", resource.getDistanceFromBuyer());
					return SUCCESS;
				}
			}
		} else {
			//SL-19820
			//List<ProviderResultVO> firmResources=getCurrentServiceOrderFromSession().getRoutedResourcesForFirm();
			if (null != getCurrentServiceOrderFromRequest()) {
				List<ProviderResultVO> firmResources = getCurrentServiceOrderFromRequest().getRoutedResourcesForFirm();
				setSimpleOrderQuickInfo();
				for (ProviderResultVO resource : firmResources) {
					if (resource.getResourceId() == acceptedResourceId) {
						setAttribute("distanceInMiles",	resource.getDistanceFromBuyer());
						setAttribute("fromLoc",	resource.getResourceDispatchAddr());
						return SUCCESS;
					}
				}

			}
		}
		
		return SUCCESS;
	}

	//Sl-19820 : method to fetch details of group order
	private void loadOrderGroup(String groupId)
	{
		OrderGroupVO orderGroupVO = null;
		orderGroupVO=soMonitorDelegate.getOrderGroupById(groupId);
		List<ServiceOrderSearchResultsVO> soList = soMonitorDelegate.getServiceOrdersForGroup(groupId);
		ServiceOrderDTO childOrder=null;
		// Set extra OrderGroupVO widget attributes from order child SO       
			Integer roleId = ((SecurityContext) getSession().getAttribute("SecurityContext")).getRoleId();
		if(soList!=null){
			ServiceOrderDTO serviceOrderDTO = ((ArrayList<ServiceOrderDTO>)ObjectMapper.convertVOToDTO(soList.subList(0, 1), roleId)).get(0);
			try{
				childOrder=getDetailsDelegate().getServiceOrder(serviceOrderDTO.getSoId(), get_commonCriteria().getRoleId(), null);
			}
			catch (DataServiceException e)
			{
				e.printStackTrace();
			}
			
			if(null!=orderGroupVO && null!=serviceOrderDTO)
			 {
				orderGroupVO.setStatus(serviceOrderDTO.getPrimaryStatus());
				orderGroupVO.setBuyerWidget(serviceOrderDTO.getBuyerWidget());
				orderGroupVO.setEndCustomerWidget(serviceOrderDTO.getEndCustomerWidget());
				orderGroupVO.setLocationWidget(serviceOrderDTO.getLocationWidget());
				orderGroupVO.setAppointmentDates(serviceOrderDTO.getAppointmentDates());
				orderGroupVO.setServiceWindow(serviceOrderDTO.getServiceWindow());
				orderGroupVO.setEndCustomerPrimaryPhoneNumberWidget(serviceOrderDTO.getEndCustomerPrimaryPhoneNumberWidget());
				orderGroupVO.setZip(serviceOrderDTO.getZip5());
				orderGroupVO.setResourceDispatchAddress(serviceOrderDTO.getResourceDispatchAddress());
				// Set these session variables for use in QuickLinks and various tabs.
				//Sl-19820
				//setCurrentSOStatusCodeInSession(serviceOrderDTO.getStatus());
				setCurrentSOStatusCodeInRequest(serviceOrderDTO.getStatus());
				if (null != childOrder) {
					orderGroupVO.setRoutedResourcesForFirm(childOrder.getRoutedResourcesForFirm());
					orderGroupVO.setCarSO(childOrder.isCarSO());
				}
			 }
			//Commenting code as part of SL-19820
			/*getSession().setAttribute(OrderConstants.GROUP_ID, groupId);
			getSession().setAttribute(THE_GROUP_ORDER, orderGroupVO);
			getSession().setAttribute(NUMBER_OF_ORDERS_IN_GROUP, soList.size());
			getSession().setAttribute(OrderConstants.SO_ID, null);
			getSession().setAttribute(THE_SERVICE_ORDER, null);*/
			//Setting the values in request scope
			setAttribute(OrderConstants.GROUP_ID, groupId);
			setAttribute(THE_GROUP_ORDER, orderGroupVO);
			setAttribute(NUMBER_OF_ORDERS_IN_GROUP, soList.size());
			setAttribute(OrderConstants.SO_ID, null);
			setAttribute(THE_SERVICE_ORDER, null);
        }
	}
	
	static private HashMap<String, SODetailsQuickLinksDTO> dtoList = null;

	public ServiceOrderDTO getModel() {
		return null;
	}

	public boolean isBuyerHasRatedProvider() {
		return buyerHasRatedProvider;
	}

	public void setBuyerHasRatedProvider(boolean buyerHasRatedProvider) {
		this.buyerHasRatedProvider = buyerHasRatedProvider;
	}

	public boolean isProviderHasRatedBuyer() {
		return providerHasRatedBuyer;
	}

	public void setProviderHasRatedBuyer(boolean providerHasRatedBuyer) {
		this.providerHasRatedBuyer = providerHasRatedBuyer;
	}

	public boolean isRescheduleRequest() {
		return rescheduleRequest;
	}

	public void setRescheduleRequest(boolean rescheduleRequest) {
		this.rescheduleRequest = rescheduleRequest;
	}

	public Integer getRescheduleRequestRole() {
		return rescheduleRequestRole;
	}

	public void setRescheduleRequestRole(Integer rescheduleRequestRole) {
		this.rescheduleRequestRole = rescheduleRequestRole;
	}

	public ITermsAndCondDelegate getITermsAndCondDelegate() {
		return iTermsAndCondDelegate;
	}

	public void setITermsAndCondDelegate(ITermsAndCondDelegate termsAndCondDelegate) {
		iTermsAndCondDelegate = termsAndCondDelegate;
	}

	public IServiceOrderUpsellBO getServiceOrderUpsellBO() {
		return serviceOrderUpsellBO;
	}

	public void setServiceOrderUpsellBO(IServiceOrderUpsellBO serviceOrderUpsellBO) {
		this.serviceOrderUpsellBO = serviceOrderUpsellBO;
	}

	public int getSoOrderType() {
		return soOrderType;
	}

	public void setSoOrderType(int soOrderType) {
		this.soOrderType = soOrderType;
	}

	public ManageScopeService getManageScopeService() {
		return manageScopeService;
	}

	public void setManageScopeService(ManageScopeService manageScopeService) {
		this.manageScopeService = manageScopeService;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}
	
	

}
/*
 * Maintenance History 
 * $Log: SODetailsQuickLinksAction.java,v $
 * Revision 1.74  2008/06/02 22:56:59  nsanzer
 * Fix to show final price(final parts + final labor) on service order details when order status is completed cancelled or closed.
 *
 * if (statusId == OrderConstants.CANCELLED_STATUS || statusId == OrderConstants.CLOSED_STATUS || statusId == 		OrderConstants.COMPLETED_STATUS ){
 * 						finalPrice = serviceOrderDTO.getFinalPartsPrice() + serviceOrderDTO.getFinalLaborPrice();
 * }
 *
 * Revision 1.73  2008/05/30 21:29:22  schavda
 * Bucks Warning for Provider Resource
 *
 * Revision 1.72  2008/05/28 17:13:16  rgurra0
 * fix defect #58 --Quick Links for SB
 *
 * Revision 1.71  2008/05/21 23:33:10  akashya
 * I21 Merged
 *
 * Revision 1.69.2.2  2008/05/19 20:43:37  sgopala
 * accept with condition chk
 *
 * Revision 1.69.2.1  2008/05/18 18:55:42  sgopala
 * quick links accepting service order
 *
 * Revision 1.69  2008/05/02 14:35:43  paugus2
 * Sears00052573 - Provider reassignment button
 *
 * Revision 1.68  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.64.4.1  2008/04/23 11:41:35  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.67  2008/04/23 05:19:30  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.65  2008/04/15 17:57:47  glacy
 * Shyam: Merged I18_THREE_ENH branch
 *
 * Revision 1.64.16.5  2008/04/11 14:01:45  paugus2
 * CR # 50053
 *
 * Revision 1.64.16.4  2008/04/08 21:11:24  paugus2
 * CR # 50053
 *
 * Revision 1.64.16.3  2008/04/02 22:05:59  araveen
 * cleaning up
 *
 * Revision 1.64.16.2  2008/04/02 22:00:07  araveen
 * Changes made to add TimeOnSite Link in QuickLinks for CR Sears00049114
 *
 * Revision 1.64.16.1  2008/04/02 19:27:23  sgopin2
 * Sears00050060 - Code change to set attributes for appointmentDates and serviceWindow
 *
 * Revision 1.64  2008/02/27 22:11:40  cgarc03
 * labor/parts spend limit variables have all been changed to Double(or double). Parsing and formatting has been removed.
 *
 * Revision 1.63  2008/02/26 18:18:02  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.62.10.1  2008/02/25 23:15:19  iullah2
 * distance calculation on SOM,SOD
 *
 * Revision 1.62  2008/01/28 20:24:57  sali030
 * Draft quick link Enhancement
 *
 * Revision 1.61  2008/01/18 23:54:54  bgangaj
 * Fixed --> Sears00045452. Added the required buttons for "CANCELLED_STATUS" in SOD
 *
 * Revision 1.60  2008/01/16 17:47:24  usawant
 * *** empty log message ***
 *
 * Revision 1.59  2008/01/16 16:51:03  usawant
 * Added a javascript function in details template and called in SODetailsQuickLinkAction.java for defect fix Sears00044587.
 *
 * Revision 1.58  2008/01/07 17:39:34  usawant
 * Added formatting for totalSpendLimit for defect no Sears00044594
 *
 * Revision 1.57  2007/12/13 23:53:24  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.56  2007/12/08 17:42:09  blars04 modified jump to documents command to include name of anchor tag that will be jumped to.
 * 
 * Revision 1.55  2007/12/07 23:39:05  mhaye05 cleaned up format and added the setting of SODetailsQuickLinksDTO.cssStyle
 * 
 * Revision 1.54 2007/12/06 15:58:28 bgangaj Copy Service Order on SOM and SOD
 * 
 * Revision 1.53 2007/12/04 16:17:53 usawant Just did some cleanup by removing
 * some unnecessary commented code
 * 
 * Revision 1.52 2007/12/04 14:49:45 pbhinga Changes done to populate correct
 * information in Quick Links section in SOD
 * 
 * Revision 1.51 2007/12/01 19:21:58 bgangaj conditional offer modal popup
 * 
 * Revision 1.50 2007/11/28 17:14:05 bgangaj Fixed --> Edit Service Order Button
 * on SOD.
 * 
 * Revision 1.49 2007/11/27 20:31:14 schavda Issue Resolution javascript
 * 
 * Revision 1.48 2007/11/15 23:07:59 zizrale add resched buttons to some screens
 * 
 * Revision 1.47 2007/11/15 21:42:45 schavda Provider - Received/Posted removed
 * Notes Tab
 * 
 * Revision 1.46 2007/11/14 23:30:10 mhaye05 added logic for document quick link
 * 
 * Revision 1.45 2007/11/14 21:58:51 mhaye05 changed reference to SOW_SO_ID or
 * THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 * 
 */
