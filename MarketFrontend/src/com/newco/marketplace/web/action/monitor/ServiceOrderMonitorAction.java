package com.newco.marketplace.web.action.monitor;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.ReasonCodeVO;
import com.newco.marketplace.dto.vo.serviceorder.ReasonLookupVO;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.action.details.SODetailsSessionCleanup;
import com.newco.marketplace.web.constants.TabConstants;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderMonitorTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderMonitorTabTitleCount;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.newco.marketplace.web.utils.TabsMapper;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.reasoncode.services.ManageReasonCodeService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ParameterAware;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.so.order.SoCancelVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServiceOrderMonitorAction extends SLBaseAction implements
		Preparable, ParameterAware{

	private static final long serialVersionUID = 4781452402648594752L;
	private static final Logger logger = Logger.getLogger(ServiceOrderMonitorAction.class.getName());

	private Map theMap = null;
	private Map statsFilters = null;
	private Map subStatusFilters = null; 

	private String serviceOrderId;
	private String filterByStatus = "";
	private String filterBySubStatus = "";
	private String filterByPriceModel = "";
	private String tab;
	private String displayTab;
	//SL-19820
	String message;
	String fromWFM;

	private ISOWizardFetchDelegate fetchDelegate;
	private IBuyerBO buyerBo;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private ManageReasonCodeService manageReasonCodeService;
	private List<BuyerReferenceVO> buyerRefs;
	private Integer buyerId;
	private boolean returnToAdminDashboard = false;
	private static ReasonCodeVO reasonCodeVO = null;
	private List<SearchFilterVO> userSearchFilters;
	private boolean buyerBidOrdersEnabled = false;

	private static final String CANCEL_REASONS = "cancelingreasons";
	private static final String CURRENT_SO = "currentSO";

		//R12_1
		//SL-20362
		String pendingReschedule;
		String dateNum;
		
		public String getDateNum() {
			return dateNum;
		}
		public void setDateNum(String dateNum) {
			this.dateNum = dateNum;
		}
		public String getPendingReschedule() {
			return pendingReschedule;
		}
		public void setPendingReschedule(String pendingReschedule) {
			this.pendingReschedule = pendingReschedule;
		}
	
	
	public ServiceOrderMonitorAction(ISOMonitorDelegate delegate) {
		super.soMonitorDelegate = delegate;
	}

	public ISOWizardFetchDelegate getFetchDelegate() {
		return fetchDelegate;
	}

	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}

	public void prepare() 
			throws Exception 
			{
		createCommonServiceOrderCriteria();
		buyerId = this.get_commonCriteria().getCompanyId();
		//boolean isSLAdmin = get_commonCriteria().getSecurityContext().isSlAdminInd();

		// Clearing the session objects of Service Order Wizard 
		logger.debug("Invoking getStartPointAndInvalidate()" );
		//Added for  Incidents 4131093 (Sl-19820)
		String soId = getParameter("soId");
		setAttribute(OrderConstants.SO_ID, soId);
		SOWSessionFacility.getInstance().getStartPointAndInvalidate(fetchDelegate, get_commonCriteria().getSecurityContext());
		getSession().removeAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
		//SL-15642 Removing Order Management Variable from session
		getSession().removeAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT);
		getSession().removeAttribute("omDisplayTab");
		//Clearing the session objects of Service Order Detail
		SODetailsSessionCleanup.getInstance().cleanUpSession();
		populateReasonForSpendLimit(buyerId);
		String skipChecks = getParameter("goD");

		if(skipChecks != null && skipChecks.length()> 0){
			logger.debug("skipping prepare going to details");
			return;
		}
		
		//SL-20379 : need to check again
		String tabToDisplay = getRequest().getParameter("displayTab");
		if(StringUtils.isBlank(tabToDisplay)){
			tabToDisplay = getRequest().getParameter("tab");
		}else{
			setDefaultCriteriaForSOM(tabToDisplay);
		}
		if(StringUtils.isBlank(tabToDisplay)){
			setDefaultCriteria();
		}else{
			setDefaultCriteriaForSOM(tabToDisplay);
		}
		//setDefaultCriteria();

		this.getRejectReasons();

		// Moved below function to execute as part of scalability
		//initPageHeader();
		//loadAvailableAndCurrentBalance();
		//loadCustomRefs(buyerId);

		//not doing anything
		//loadPaginationData();

		// Moved below function to execute as part of scalability
		//loadSOMonitorTabInformation();
		ServiceOrdersCriteria soContext = get_commonCriteria();
		if (soContext != null
				&& soContext.getSecurityContext() != null
				&& soContext.getSecurityContext().getRoleId().intValue() == OrderConstants.PROVIDER_ROLEID){
			loadProviderSoRejectReasons();
		}
		String tab = getRequest().getParameter("tab");
		HttpSession sess  = getSession();
		if (tab!=null) {
			//SL-20379
			//sess.setAttribute("tab", tab);
			getRequest().setAttribute("tab", tab);
			sess.removeAttribute("displayTab");//From Dashboard
		}
		else {
			if (soContext.getSecurityContext().getRoleId() != null && soContext.getSecurityContext().getRoleId().intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
				//SL-20379
				//sess.setAttribute("tab", "Saved");
				getRequest().setAttribute("tab", "Saved");
			}
			else {
				//SL-20379
				//sess.setAttribute("tab", "Today");
				getRequest().setAttribute("tab", "Today");
			}
		}
		Map<String, UserActivityVO> activities = ((SecurityContext) getSession().getAttribute(
				"SecurityContext")).getRoleActivityIdList();
		boolean canManageSO = false;
		boolean dispatchInd= soContext.getSecurityContext().isDispatchInd();
		boolean incSpendLimit = false;

		//Checking whether the user has the rights to manage SO[4 is the Activity Id for the Activity-Manage Service Orders]
		if(activities != null && activities.containsKey("4") && dispatchInd == false){
			canManageSO = true;
		}
		if((activities != null && activities.containsKey("56"))||soContext.getSecurityContext().isAdopted()){
			incSpendLimit = true;
		}
		sess.setAttribute("incSpendLimit", incSpendLimit);
		sess.setAttribute("canManageSO", canManageSO);
		sess.setAttribute("dispatchInd", dispatchInd);
		sess.setAttribute("isPrimaryInd", soContext.getSecurityContext().isPrimaryInd());
		sess.setAttribute(OrderConstants.SERVICE_ORDER_CRITERIA_KEY, soContext);
		sess.setAttribute("roleType", soContext.getSecurityContext().getRoleId());
		boolean tripChargeInd = buyerFeatureSetBO.validateFeature(buyerId, Constants.TRIP_CHARGE_OVERRIDE);
		getRequest().setAttribute(Constants.TRIP_CHARGE_OVERRIDE, tripChargeInd);
		buyerBidOrdersEnabled = buyerFeatureSetBO.validateFeature(buyerId, "ALLOW_BID_ORDERS");

		//check to see if autoclose on for this buyer
		boolean isautocloseOn=false;
		isautocloseOn=buyerFeatureSetBO.validateFeature(buyerId, BuyerFeatureConstants.AUTO_CLOSE);
		getSession().setAttribute("isAutocloseOn", isautocloseOn);
		//end of autoclose check;
		
		//SL 15642 Start-Changes for permission based price display in Service Order Monitor
		if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER))
		{
			if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"))
			{
				getSession().setAttribute("viewOrderPricing",true);
			}
		}
		//SL 15642 End-Changes for permission based price display in Service Order Monitor
		
		//SL-19820
		String msg = getParameter("msg");
		this.message = msg;
		setAttribute("msg", msg);
		
		String fromWFM = getParameter("fromWFM");
		setAttribute("fromWFM", fromWFM);
		this.fromWFM = fromWFM;
		
		//R12_1
		//SL-20362
		String pendingReschedule=getRequest().getParameter("pendingReschedule");
		this.pendingReschedule = pendingReschedule;

	}

	private void loadCustomRefs(Integer buyerId) {
		try {			
			buyerRefs = buyerBo.getBuyerReferences(buyerId);			
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Unable to loadCustomRefs(" + buyerId + ")", e);
		}
	}

	/*
	 * The execute method is the entry point method called the display the the
	 * initial users service order monitor
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		initPageHeader();
		loadAvailableAndCurrentBalance();		
		loadSOMonitorTabInformation();

		if (returnToAdminDashboard){
			return "adminDashboard"; 
		}

		final HttpSession session = getSession();		
		session.removeAttribute(OrderConstants.SO_ID);
		session.removeAttribute("routedResourceId");
		session.removeAttribute("AvailableBalance");
		session.removeAttribute(OrderConstants.SO_REJECT_REASONS);
		SecurityContext securityContext = (SecurityContext) getSession()
				.getAttribute(OrderConstants.SECURITY_CONTEXT);
		Integer roleId = securityContext.getRoleId();
		// if(StringUtils.isNotBlank((String)getSession().getAttribute("soDetails"))) {	
		//SL-19820
		/*if("1".equals(session.getAttribute("soDetails"))){
			session.setAttribute("soDetailsMessage", "soMessage");				
		}*/
		if("1".equals(getParameter("soDetails"))){
			getRequest().setAttribute("soDetailsMessage", "soMessage");	
			String somError = getParameter("somError");
			String somErrorMsg = getParameter("somErrorMsg");
			getRequest().setAttribute(somError, somErrorMsg);	
		}
		
		if (roleId == OrderConstants.PROVIDER_ROLEID){
			boolean isAdoptedProvider = this._commonCriteria.getSecurityContext().isAdopted();
			if(!isAdoptedProvider){
			if((null == session.getAttribute("initialSomLoadInd"))||("".equals(session.getAttribute("initialSomLoadInd")))){
				session.setAttribute("initialSomLoadInd", "true");
			}
			else{
				session.setAttribute("initialSomLoadInd", "false");
			}
			}
		}
		
		// }
		return SUCCESS;
	}

	public String goToSOMDetails() throws Exception
	{
		String so = getParameter("soId");
		logger.debug("goToSOMDetails to details");
		getSession().setAttribute(OrderConstants.SO_ID,so);
		getSession().setAttribute("routedResourceId",getRequest().getParameter("rid"));
		return TO_DETAILS_VIEW;
	}

	public String goToGridHolder() throws Exception {
		// I am not sure if this call is required here. Calling here just to make sure that
		// tabs are not showing wrong count.
		//SL-20379
		final HttpServletRequest request = getRequest();
		String tabAction = request.getParameter("tab");
		String dateNum = request.getParameter("dateNum");
		this.dateNum = dateNum;
		setAttribute("tabStatus", getSession().getAttribute("tabStatus_"+tabAction+"_"+dateNum));
		
		loadSOMonitorTabInformation();
		
		//SL-20379
		//final HttpServletRequest request = getRequest();
		//String tabAction = request.getParameter("tab");
		String wfFlag = request.getParameter("wfFlag");
		String pbFilterId=request.getParameter("pbFilterId");
		String pbFilterName=request.getParameter("pbFilterName");
		String pbFilterOpt=request.getParameter("pbFilterOpt");
		Integer buyerRefTypeId;
		String buyerRefValue;
		//sl-13522
		String wfmSodFlag=request.getParameter("wfmSodFlag");
		String soId=request.getParameter("soId");
		//Sl-19820
		String message = request.getParameter("msg");
		setAttribute("msg", message);
		this.message = message;
		
		try {
			buyerRefTypeId = Integer.valueOf(getRequest().getParameter("refType"));
			buyerRefValue = getRequest().getParameter("refVal");
		} catch (NumberFormatException e) {
			buyerRefTypeId = null;
			buyerRefValue = null;
		}

		//SL 15642 Start-Changes for permission based price display in Service Order Monitor
				if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER))
				{
					if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"))
					{
						getSession().setAttribute("viewOrderPricing",true);
					}
				}
				//SL 15642 End-Changes for permission based price display in Service Order Monitor
		getSession().removeAttribute("displayTab"); //From Dashboard
		if(wfFlag!=null && wfFlag.equals("1")) {
			getSession().setAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR, Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
			getSession().setAttribute("pbFilterOpt",pbFilterOpt);
		}

		this.redirectURL = null;

		if (tabAction != "" 
				&& (tabAction.equals("Today") || tabAction.equals("Current")
						|| tabAction.equals("Draft") || tabAction.equals("Accepted")
						|| tabAction.equals("Problem") || tabAction.equals("Inactive")
						|| tabAction.equals("Saved") || tabAction.equals("History"))) {
			this.redirectURL = "/jsp/so_monitor/common_grid.jsp";
		}
		else if (tabAction != "" && tabAction.equals("Search")) {
			if(wfFlag != null) {
				this.wfFlag =  wfFlag;
				if (pbFilterId != null && pbFilterName != null){
					this.pbFilterId = pbFilterId;
					this.pbFilterName = pbFilterName;	
					this.pbFilterOpt = pbFilterOpt;	
					this.buyerRefTypeId = buyerRefTypeId;
					this.buyerRefValue = buyerRefValue;
					//sl-13522
					if(wfmSodFlag!=null && soId!=null)
					{
						this.wfmSodFlag=wfmSodFlag;
						this.soId=soId;
					}
				}
			}
			List<LookupVO> autocloseRulesList=new ArrayList<LookupVO>();
			autocloseRulesList = soMonitorDelegate.getAutocloseRules();
			getSession().setAttribute("autocloseRulesList",autocloseRulesList);
			this.redirectURL = "/jsp/so_monitor/search_grid.jsp";
		}
		else if (tabAction != "" && tabAction.equals("Posted")) {
			this.redirectURL = "/jsp/so_monitor/realtime_grid.jsp";
		}
		else if (tabAction != "" && tabAction.equals("Received")) {
			this.redirectURL = "/jsp/so_monitor/p_realtime7_grid.jsp";
		}
		else if (tabAction != "" && tabAction.equals("Bid Requests")) {
			this.redirectURL = "/jsp/so_monitor/tab_bid_requests.jsp";
		}
		else if (tabAction != "" && tabAction.equals("Bulletin Board")) {
			this.redirectURL = "/jsp/so_monitor/tab_bulletin_board.jsp";
		}
		this.tab = tabAction;
		request.setAttribute("tab", tabAction);
		
		//SL-20379
		//getSession().setAttribute("tab", tabAction);

		validateLoadDataGridFilters(false);
		return TO_COMMON_GRID;
	}

	private void initPageHeader() {		
		String dateString = DateUtils.getHeaderDate();
		setAttribute("dateString", dateString);		
	}

	public String updateDataGrid() throws Exception {
		ArrayList<ServiceOrderDTO> dtoList = (ArrayList<ServiceOrderDTO>) getSession().getAttribute("dtoList");
		setAttribute("dtoList", dtoList);
		return "success";
	}

	public void loadAvailableAndCurrentBalance()  throws Exception {
		double availBalance = 0.0;
		double currentBalance = 0.0;	
		if(get_commonCriteria() == null)
			return;

		Integer i = get_commonCriteria().getCompanyId();
		String roleType = this._commonCriteria.getRoleType();
		roleType = roleType!=null?roleType.toUpperCase():roleType;

		AjaxCacheVO ajax = new AjaxCacheVO();

		ajax.setCompanyId(i);
		ajax.setRoleType(roleType);

		availBalance = soMonitorDelegate.getAvailableBalance(ajax);
		String abFormat = java.text.NumberFormat.getCurrencyInstance().format(
				availBalance);
		setAttribute("AvailableBalance", abFormat);
		currentBalance = soMonitorDelegate.getCurrentBalance(ajax);
		String currBalanceFormatted = java.text.NumberFormat.getCurrencyInstance().format(
				currentBalance);
		setAttribute("CurrentBalance",currBalanceFormatted);
	}


	private void loadProviderSoRejectReasons() {

		if(getSession().getAttribute(OrderConstants.SO_REJECT_REASONS) != null) {
			return;
		}

		HttpSession sess = getSession();

		if (reasonCodeVO != null) {
			sess.setAttribute(OrderConstants.SO_REJECT_REASONS, reasonCodeVO);
			return;
		}

		try {
			ArrayList arrReasonCodeList = soMonitorDelegate.getRejectReasonCodes();

			if (sess.getAttribute(OrderConstants.SO_REJECT_REASONS) == null) {
				logger.debug("Reject Reasons are null ----- Fetching reasons");
				ArrayList rejectReasons = arrReasonCodeList;
				for (int i = 0; i < rejectReasons.size(); i++) {
					ReasonLookupVO rjLkp = (ReasonLookupVO) rejectReasons.get(i);
					logger.debug(" rjLkp id : " + rjLkp.getId());
					logger.debug(" rjLkp desc : " + rjLkp.getDescr());
				}
				reasonCodeVO = new ReasonCodeVO();
				reasonCodeVO.setArrRejectReason(rejectReasons);
				sess.setAttribute(OrderConstants.SO_REJECT_REASONS, reasonCodeVO);
			}
		} catch (Exception e) {
			logger.debug("", e);
		}		
	}


	public String loadDataGrid() throws Exception {
		logger.debug("Entering loadDataGrid");
		String toStr="";
		ServiceOrdersCriteria commonCriteria = get_commonCriteria();
		commonCriteria.setVendBuyerResId(commonCriteria.getVendBuyerResId());
		String requestedTab = getRequest().getParameter("tab");
		//SL-20379
		setAttribute("tab", requestedTab);
		String dateNum = getRequest().getParameter("dateNum");
		setAttribute("tabStatus", getSession().getAttribute("tabStatus_"+requestedTab+"_"+dateNum));
		//SL-19820
		String message = getParameter("msg");
		setAttribute("msg", message);
	
		if (StringUtils.isNotBlank(requestedTab))
		{
			if ((requestedTab.equals("Today") || requestedTab.equals("Draft") || requestedTab.equals("Accepted")
					|| requestedTab.equals("Problem") || requestedTab.equals("Inactive") || requestedTab.equals("Current")
					|| requestedTab.equals("History") || requestedTab.equals("Saved")))
			{
				toStr = "toCGrid";
			}
			else if (requestedTab.equals("Search"))
			{
				toStr = "toSGrid";
			}
			else if (requestedTab.equals("Posted"))
			{
				toStr = "toRGrid";
			}
			else if (requestedTab.equals("Received"))
			{
				toStr = "toP7Grid";
			}
			else if (requestedTab.equals("Bid Requests") || requestedTab.equals("Bid"))
			{
				toStr = "toBidRequestsGrid";
			}
			else if (requestedTab.equals("Bulletin Board"))
			{
				toStr = "toBulletinBoardGrid";
			}
			else
			{
				logger.error("loadDataGrid() did not find a proper toStr for requestedTab=" + requestedTab);
			}
		}
		//TODO : Remove below call : DataGrid is already loaded by prepare call.
		validateLoadDataGridFilters(true);
		getSession().removeAttribute("tabStatus");
		//SL-20379
		getSession().removeAttribute("tabStatus_"+requestedTab+"_"+dateNum);
		//setAttribute("tab", requestedTab);
		//getSession().setAttribute("tab", requestedTab);
		return toStr;
	}

	public String loadDataForIncSL(){
		String returnVar="";
		String soId = getParameter("soId");
		ServiceOrder so  = soMonitorDelegate.getServiceOrder(soId);
		getSession().setAttribute("data", so);
		Integer stateId= so.getWfStateId();
		int buyer = so.getBuyerId();
		if(stateId.equals(OrderConstants.ACCEPTED_STATUS) ||
				stateId.equals(OrderConstants.PROBLEM_STATUS)||
				stateId.equals(OrderConstants.ACTIVE_STATUS)){
			populateReasonForSpendLimit(buyer);
		}
		if(OrderConstants.TASK_LEVEL_PRICING.equals(so.getPriceType()))
			getSession().setAttribute("taskLevelPriceInd", true);
		String wfFlag = getParameter("wfFlag");

		if(wfFlag.equals("1")){
			returnVar="wfm";
		}
		else{
			returnVar="som";
		}

		return returnVar;
	}
	private void populateReasonForSpendLimit(int buyerId){
		List<ReasonCode> SLIreasonCodes = manageReasonCodeService.getAllCancelReasonCodes(buyerId,OrderConstants.TYPE_SPEND_LIMIT);
		getRequest().setAttribute(Constants.SPEND_LIMIT_INCREASE_REASON_CODES, SLIreasonCodes);
	}

	public String loadDataForCancellation() {
		String returnVar = SUCCESS;
		String soId = getParameter("soId");
		buyerId = this.get_commonCriteria().getCompanyId();
		SoCancelVO so = soMonitorDelegate.getServiceOrderForCancel(soId);
		List<ReasonCode> reasonCodes = manageReasonCodeService.getAllCancelReasonCodes(buyerId,OrderConstants.TYPE_CANCEL);
		getSession().setAttribute(CURRENT_SO, so);
		boolean tripChargeInd = buyerFeatureSetBO.validateFeature(buyerId, BuyerFeatureConstants.TRIP_CHARGE_OVERRIDE);
		getRequest().setAttribute(BuyerFeatureConstants.TRIP_CHARGE_OVERRIDE, tripChargeInd);
		getRequest().setAttribute(Constants.CANCEL_REASON_CODES, reasonCodes);
		getSession().setAttribute(CANCEL_REASONS, reasonCodes);
		getSession().setAttribute(Constants.SCOPE_CHANGE_TASKS, new HashMap<Integer,ManageTaskVO>());
		preprocessFinTxnForPenalty(so);
		return returnVar;
	}
	private void preprocessFinTxnForPenalty(SoCancelVO soDTO) {
		boolean isWithin24Hours = false;
		if (null != soDTO && null != soDTO.getServiceDate1()) {

			//04:35 PM  //GMT timezone
			String strStartTime = soDTO.getServiceTimeStart();

			//2009-11-26 00:00:00.00 //GMT timezone
			Timestamp appointmentDate = soDTO.getServiceDate1();

			long millisecondsFor24Hours = 1000 * 60 * 60 * 24;

			// If successful perform financial transaction for penalty amount 
			// if service time is within 24 hours
			// else perform financial transaction without penalty
			if(TimeUtils.isPastXTime(appointmentDate, strStartTime, millisecondsFor24Hours)) {					
				isWithin24Hours = true;
			}
			getRequest().setAttribute(Constants.IS_WITHIN_24HRS, String.valueOf(isWithin24Hours));
		}
	}

	private void loadSOMonitorTabInformation(){
		boolean manageSOFlag = false;
		if(getSession().getAttribute("theSomtabList") != null)
		{
			return;
		}
		AjaxCacheVO ajaxCacheVo = new AjaxCacheVO();

		if(_commonCriteria == null)
			return;

		String roleType = this._commonCriteria.getRoleType();

		if (roleType.equals(OrderConstants.NEWCO_ADMIN)){
			//NewCo SL Admin Issue: When coming from the AdminSearchAction.navigateToProviderPage(), they are setting the 
			//SecurityContext.roleId to the role that the "Search" is assuming...therefore,
			//we are picking the roleId off of the securitycontext role id based on the type of 
			//user that is logged on.  Refer to the AdminSearchAction.navigateToProviderPage() code
			Integer roleId = this._commonCriteria.getSecurityContext().getRoleId();
			if (roleId.equals(OrderConstants.PROVIDER_ROLEID)){
				roleType = OrderConstants.PROVIDER;
			}else if(roleId.equals(OrderConstants.BUYER_ROLEID)){
				roleType = OrderConstants.BUYER;
			}else if (roleId.equals(OrderConstants.SIMPLE_BUYER_ROLEID)){
				roleType = OrderConstants.SIMPLE_BUYER;
			}else{
				roleType = OrderConstants.BUYER;
			}
		}
		ajaxCacheVo.setCompanyId(this._commonCriteria.getCompanyId());
		ajaxCacheVo.setRoleType(roleType);

		SecurityContext securityContext = this._commonCriteria.getSecurityContext();
		Map<String, UserActivityVO> activities = securityContext.getRoleActivityIdList();
		if(roleType.equalsIgnoreCase(OrderConstants.PROVIDER) && activities != null && activities.containsKey("4") && !securityContext.isPrimaryInd() && !securityContext.isDispatchInd()){
			manageSOFlag = true;
			ajaxCacheVo.setManageSoFlag(manageSOFlag);
			ajaxCacheVo.setVendBuyerResId(securityContext.getVendBuyerResId());
		}
		

		//SL 15642 Start-Changes for permission based price display in Service Order Monitor
				if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER))
				{
					if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"))
					{
						ajaxCacheVo.setViewOrderPricing(true);
					}
				}
		//SL 15642 End-Changes for permission based price display in Service Order Monitor
		
		ArrayList<ServiceOrderMonitorTabTitleCount> soTabs = soMonitorDelegate.getTabs(ajaxCacheVo);

		// Start of new stuff
		ArrayList<ServiceOrderMonitorTabDTO> tabList = new ArrayList<ServiceOrderMonitorTabDTO>();
		ServiceOrderMonitorTabDTO tabDTO;

		ArrayList<Integer> statusList;

		boolean isBuyer = isRole(OrderConstants.BUYER); 
		boolean isSimpleBuyer = isRole(OrderConstants.SIMPLE_BUYER);

		//From Dashboard
		if(getRequest().getParameter("displayTab")==null){
			displayTab =(String)getSession().getAttribute("displayTab");
		}
		//SL-19820
		else if(StringUtils.isNotBlank(getRequest().getParameter("displayTab"))){
			displayTab = getRequest().getParameter("displayTab");
		}
		else{
			displayTab =(String)getSession().getAttribute("displayTab");
		}
		String tabStatus = getRequest().getParameter("tabStatus");
		if(StringUtils.isNotBlank(tabStatus)){
			//SL-20379
			//getSession().setAttribute("tabStatus", tabStatus);
			String dateNum = String.valueOf(new Date().getTime());
			getSession().setAttribute("tabStatus_"+displayTab+"_"+dateNum, tabStatus);
			this.dateNum = dateNum;
			setAttribute("tabStatus", tabStatus);
		}

		if(displayTab == null){
			displayTab = getRequest().getParameter("displayTab");
			if (displayTab == null) {
				for (ServiceOrderMonitorTabTitleCount tabTitle : soTabs) {
					if (tabTitle.getTabCount() != null && tabTitle.getTabCount().intValue() > 0) {
						if (displayTab == null) {	
							displayTab = tabTitle.getTabTitle();
						}
					}
				}
			}
			//SL-19820
			//getSession().setAttribute("displayTab", displayTab);
			setAttribute("displayTab", displayTab);
		}

		if (soTabs != null) {
			for (ServiceOrderMonitorTabTitleCount tabTitle : soTabs) {
				//SL-15642 Checking null value of getTabTitle as it can be null after adding permission based price display
				if(null!=tabTitle.getTabTitle())
				{
				if (isBuyer) {
					statusList = TabsMapper.getTabStatesBuyer(tabTitle.getTabTitle());
				} else if (isSimpleBuyer) {
					statusList = TabsMapper.getTabStatesSimpleBuyer(tabTitle.getTabTitle());
				} else {
					statusList = TabsMapper.getTabStatesProvider(tabTitle.getTabTitle());
				}
				tabDTO = createTabDTOWithStatusList(get_commonCriteria().getVendBuyerResId(), tabTitle, statusList, null, displayTab);
				tabList.add(tabDTO);
			}
			}
		}

		Boolean oneDraft= false;
		for (ServiceOrderMonitorTabDTO mtabDTO : tabList) {
			if (TabConstants.SIMPLE_BUYER_DRAFT.equalsIgnoreCase(mtabDTO.getTitle())
					&& mtabDTO.getTabCount() != null && mtabDTO.getTabCount().intValue() == 1) {
				oneDraft= true;

				// SL-7663
				fetchServiceOrderResults(loadCriteria(true), displayTab);		
				validateLoadDataGridFilters(true);

				break;
			}
		}
		
		setAttribute("oneDraft", oneDraft);
		this.setAttribute("theSomtabList", tabList);
	}

	private ServiceOrderMonitorTabDTO createTabDTOWithStatusList(
			Integer entity, ServiceOrderMonitorTabTitleCount titleAndCount,
			ArrayList<Integer> statusList, Integer subStatus, String displayTab) {
		ServiceOrderMonitorTabDTO tabDTO = new ServiceOrderMonitorTabDTO();
		tabDTO = new ServiceOrderMonitorTabDTO();

		tabDTO.setId(titleAndCount.getTabTitle());
		tabDTO.setTitle(titleAndCount.getTabTitle());
		tabDTO.setTabCount(titleAndCount.getTabCount());
		if(displayTab != null && tabDTO.getId().equalsIgnoreCase(displayTab)){
			tabDTO.setTabSelected("true");
		} else {
			tabDTO.setTabSelected("false");
		}
		return tabDTO;
	}

	public ServiceOrderMonitorResultVO fetchServiceOrderResults(CriteriaHandlerFacility facility,String displayTab){
		return soMonitorDelegate.fetchServiceOrderResults(facility.getCriteria());
	}

	public String doAcceptServiceOrder() {
		LoginCredentialVO lvo = get_commonCriteria().getSecurityContext().getRoles();
		soMonitorDelegate.serviceOrderAccept(getServiceOrderId(),
				lvo.username, lvo.vendBuyerResId, lvo.companyId, null,true);
		return "success";
	}

	public void loadPaginationData(){ logger.warn("DEPRECATED METHOD CALL"); }

	private void loadSavedSearchFilters(){
		try {		
			Integer entityId = this.get_commonCriteria().getCompanyId();
			Integer roleId = this.get_commonCriteria().getRoleId();
			SearchFilterVO searchFilterVO = new SearchFilterVO();
			searchFilterVO.setEntityId(entityId);
			searchFilterVO.setRoleId(roleId);

			userSearchFilters = soMonitorDelegate.getSearchFilters(searchFilterVO);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to load Search Filter)", e);
		}
	}
	protected String getTab() {
		return tab;
	}

	protected void setDataType(String tab) {
		this.tab = tab;
	}

	public void setParameters(Map map) {
		this.theMap = map;
	}

	public Map getParameters() {
		return theMap;
	}

	public String getServiceOrderId() {
		return serviceOrderId;
	}

	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	public String getFilterByStatus() {
		return filterByStatus;
	}

	public void setFilterByStatus(String filterByStatus) {
		this.filterByStatus = filterByStatus;
	}

	public String getFilterBySubStatus() {
		return filterBySubStatus;
	}

	public void setFilterBySubStatus(String filterBySubStatus) {
		this.filterBySubStatus = filterBySubStatus;
	}

	protected Map getStatsFilters() {
		return statsFilters;
	}

	protected void setStatsFilters(HashMap statsFilters) {
		this.statsFilters = statsFilters;
	}

	protected Map getSubStatusFilters() {
		return subStatusFilters;
	}

	protected void setSubStatusFilters(HashMap subStatusFilters) {
		this.subStatusFilters = subStatusFilters;
	}

	public IBuyerBO getBuyerBo() {
		return buyerBo;
	}

	public void setBuyerBo(IBuyerBO buyerBo) {
		this.buyerBo = buyerBo;
	}

	/**
	 * Loading CustomRefs on demand is more efficient than
	 * loading it in prepare. It was getting loading unnecessary in every prepare.
	 * With on demand loading we will save around 1500 DB calls per hour. - Shekhar
	 * @return
	 */
	public List<BuyerReferenceVO> getBuyerRefs() {
		if (buyerRefs == null) {
			loadCustomRefs(buyerId);
			if (buyerRefs == null) {
				buyerRefs= new ArrayList<BuyerReferenceVO>();
			}
		}				
		return buyerRefs;
	}

	public void setBuyerRefs(List<BuyerReferenceVO> buyerRefs) {
		this.buyerRefs = buyerRefs;
	}

	public String getDisplayTab() {
		return displayTab;
	}

	public void setDisplayTab(String displayTab) {
		this.displayTab = displayTab;
	}

	public List<SearchFilterVO> getUserSearchFilters() {
		if (userSearchFilters == null) {
			loadSavedSearchFilters();
			if (userSearchFilters == null) {
				userSearchFilters= new ArrayList<SearchFilterVO>();
			}
		}				

		return userSearchFilters;
	}

	public void setUserSearchFilters(List<SearchFilterVO> userSearchFilters) {
		this.userSearchFilters = userSearchFilters;
	}

	public void setFilterByPriceModel(String filterByPriceModel) {
		this.filterByPriceModel = filterByPriceModel;
	}

	public String getFilterByPriceModel() {
		return filterByPriceModel;
	}

	public void setBuyerBidOrdersEnabled(boolean buyerBidOrdersEnabled) {
		this.buyerBidOrdersEnabled = buyerBidOrdersEnabled;
	}

	public boolean isBuyerBidOrdersEnabled() {
		return buyerBidOrdersEnabled;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public ManageReasonCodeService getManageReasonCodeService() {
		return manageReasonCodeService;
	}

	public void setManageReasonCodeService(
			ManageReasonCodeService manageReasonCodeService) {
		this.manageReasonCodeService = manageReasonCodeService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromWFM() {
		return fromWFM;
	}

	public void setFromWFM(String fromWFM) {
		this.fromWFM = fromWFM;
	}
	
}