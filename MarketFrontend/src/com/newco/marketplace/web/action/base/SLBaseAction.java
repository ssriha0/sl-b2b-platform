package com.newco.marketplace.web.action.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.DisplayTabCriteria;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.criteria.SearchWordsCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceFilterCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceSearchCriteria;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.ProviderDetail;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.security.ActivityMapper;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.vo.PaginetVO;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.DropdownOptionDTO;
import com.newco.marketplace.web.dto.ETMSearchDTO;
import com.newco.marketplace.web.dto.FMManageAccountsTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.newco.marketplace.web.utils.ETMCriteriaHandlerFacility;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * $Revision: 1.75 $ $Author: awadhwa $ $Date: 2008/05/30 00:05:55 $
 */

/*
 * Maintenance History: See bottom of file
 */
public abstract class SLBaseAction extends ActionSupport implements SOConstants, OrderConstants {

	private static final Logger logger = Logger.getLogger("SLBaseAction");
	private static final String DISPLAY_CALENDAR = "DISPLAY_CALENDAR";
    protected ServiceOrdersCriteria _commonCriteria = null;
	private HashMap yesNoRadio = new HashMap();
    public String redirectURL = "";
    public String wfFlag = "";
	public String pbFilterId = "";
	public String pbFilterName = "";
	public String pbFilterOpt = "";
	public Integer buyerRefTypeId = null;
	public String buyerRefValue = null;
	public String action ="";
	public String pbSoId = "";
	public String tab = "";
	public String wfmSodFlag="";
	public String soId="";
	private boolean isSLAdmin = false;

	protected ISOMonitorDelegate soMonitorDelegate;
	protected ILookupDelegate lookupManager;
	protected ISODetailsDelegate detailsDelegate;
	protected IFinanceManagerDelegate financeManagerDelegate;
	protected INotificationService notificationService;
	static private ArrayList<ArrayList<String>> starsList = null;
    protected String ACTION_ERROR = "action_error";
    protected String ACTION_MSG = "action_msg";

	public SLBaseAction() {
		if (starsList == null)
			initStarsList();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	protected String getParameter(String param) {
		return ServletActionContext.getRequest().getParameter(param);
	}
	protected boolean isButtonPressed(String buttonID) {
		return (getParameter(buttonID) != null);
	}

	protected void setAttribute(String name, Object obj) {
		ServletActionContext.getRequest().setAttribute(name, obj);
	}

	protected Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	protected void createCommonServiceOrderCriteria() {
		long start = System.currentTimeMillis();
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
		boolean isLoggedIn=false;
		if (securityContext == null) {
			getSession().setAttribute(IS_LOGGED_IN, Boolean.FALSE);
			isLoggedIn = false;
			return;
		} else {
			getSession().setAttribute(IS_LOGGED_IN, Boolean.TRUE);
			isLoggedIn = true;
		}
		LoginCredentialVO lvo = securityContext.getRoles();
		lvo.setVendBuyerResId(securityContext.getVendBuyerResId());
		if (lvo == null) {
			// TODO somebody handle this better
			lvo = new LoginCredentialVO();
		}

		_commonCriteria = new ServiceOrdersCriteria();
		_commonCriteria.setCompanyId(securityContext.getCompanyId());
		
		if (lvo.getVendBuyerResId() != null && lvo.getVendBuyerResId().intValue() == -1) {
			// TODO:: Populate vendor resource id upon login when the schema is
			// updated.
			_commonCriteria.setVendBuyerResId(securityContext.getVendBuyerResId());
		//	_commonCriteria.setVendBuyerResId(9999); //temporarily for testing
		} else {
			_commonCriteria.setVendBuyerResId(lvo.getVendBuyerResId());
		}

		if (securityContext.isSlAdminInd()){
			_commonCriteria.setRoleId(securityContext.getRoleId());
			_commonCriteria.setTheUserName(securityContext.getUsername());
		}else{
			_commonCriteria.setRoleId(lvo.getRoleId());
			_commonCriteria.setTheUserName(lvo.getUsername());
		}

		_commonCriteria.setFName(lvo.getFirstName());
		_commonCriteria.setLName(lvo.getLastName());
		_commonCriteria.setRoleType(securityContext.getRole());
		_commonCriteria.setToday(Boolean.TRUE);
		securityContext.setBuyerLoggedInd(securityContext.isBuyer());
		_commonCriteria.setSecurityContext(securityContext); 

		// Set simple buyer flag in session
		if (isLoggedIn && _commonCriteria.getSecurityContext().getRoleId().intValue() == 5) {
			getSession().setAttribute(IS_SIMPLE_BUYER, Boolean.TRUE);
		} else {
			getSession().setAttribute(IS_SIMPLE_BUYER, Boolean.FALSE);
		}
		if(_commonCriteria.getSecurityContext().getRoles()!=null){
			if (isLoggedIn && _commonCriteria.getSecurityContext().getRoles().getRoleName().equals(OrderConstants.NEWCO_ADMIN)) {
				getSession().setAttribute(IS_ADMIN, Boolean.TRUE);
			} else {
				getSession().setAttribute(IS_ADMIN, Boolean.FALSE);
			}
		}
		
		//malhar : making display calendar providers of D2C and Techtalk Provider Firms - hard coded for now need to be changed.
		if (isLoggedIn && Constants.calendarProviders.contains(_commonCriteria.getCompanyId())) {
			getSession().setAttribute(DISPLAY_CALENDAR, Boolean.TRUE);
		} else {
			getSession().setAttribute(DISPLAY_CALENDAR, Boolean.FALSE);
		}
		
		getSession().setAttribute(OrderConstants.SERVICE_ORDER_CRITERIA_KEY, _commonCriteria);
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
		    logger.info("Time Taken to complete createCommonServiceOrderCriteria:>>>>>"+(end-start));
		}

	}

	protected void clearAccountDetailsFromSession(){
		//clear any account details that exists in the session
		if(getSession().getAttribute("ManageAccountsTabDTO") != null)
				getSession().removeAttribute("ManageAccountsTabDTO");
		if(getSession().getAttribute("accountTypeMap") != null)
			getSession().removeAttribute("accountTypeMap");
		if(getSession().getAttribute("creditCardTypeMap") != null)
			getSession().removeAttribute("creditCardTypeMap");
		if(getSession().getAttribute("FinancialProfileTabDTO") != null)
			getSession().removeAttribute("FinancialProfileTabDTO");
		if(getSession().getAttribute("manageFundsTabDTO") != null)
			getSession().removeAttribute("manageFundsTabDTO");
		}

	protected void clearProviderInfo(){
		//clear any provider details that exists in the session
		if(getSession().getAttribute("vendorId") != null)
				getSession().removeAttribute("vendorId");
		if(getSession().getAttribute("providerName") != null)
			getSession().removeAttribute("providerName");
		if(getSession().getAttribute("providerCompanyName") != null)
			getSession().removeAttribute("providerCompanyName");
		if(getSession().getAttribute("username") != null)
			getSession().removeAttribute("username");
		if(getSession().getAttribute("cityState") != null)
			getSession().removeAttribute("cityState");
		if(getSession().getAttribute("resourceName") != null)
			getSession().removeAttribute("resourceName");
		// SL-20117 : R10_3:SL-19503:Licences & Certification tab is not getting cleared
		// To avoid displaying of cached data when Add Cridential is clicked from Licences & Certification tab in Edit Provider Firm Profile.
		if(getSession().getAttribute("resourceCredId") != null)
			getSession().removeAttribute("resourceCredId");
	}

	protected void clearBuyerInfo(){
		//clear any buyer details that exists in the session
		if(getSession().getAttribute("buyerId") != null)
				getSession().removeAttribute("buyerId");
		if(getSession().getAttribute("buyerBusinessName") != null)
			getSession().removeAttribute("buyerBusinessName");
		if(getSession().getAttribute("buyerAdmin") != null)
			getSession().removeAttribute("buyerAdmin");
		if(getSession().getAttribute("buyerUsername") != null)
			getSession().removeAttribute("buyerUsername");
		if(getSession().getAttribute("buyerLocation") != null)
			getSession().removeAttribute("buyerLocation");
		}

	protected void clearInsuranceInfo()
	{
		ActionContext.getContext().getSession().remove( OrderConstants.INSURANCE_INFORMATION );
		ActionContext.getContext().getSession().remove( OrderConstants.INSURANCE_TYPELIST );
		ActionContext.getContext().getSession().remove( OrderConstants.FROM_POPUP_IND );
		ActionContext.getContext().getSession().remove( OrderConstants.INSURANCE_STATUS );
	}
	protected void clearSearchCriteria()
	{
		if(getSession().getAttribute("selectedSearchForSession") != null)
			getSession().removeAttribute("selectedSearchForSession");
		if(getSession().getAttribute("selectedSearchCriteriaSession") != null)
			getSession().removeAttribute("selectedSearchCriteriaSession");
	}
	
	//R10.3 SL-19503 Licenses & Certification Tab Not Clearing Cache START
	//Fix: Clearing the vendor cred id on click of return to search portal
	protected void clearVendorCredentials(){
		if(getSession().getAttribute("VendorCredId") != null){
			getSession().removeAttribute("VendorCredId");
		}
	}
	//R10.3 SL-19503 Licenses & Certification Tab Not Clearing Cache END
	
	protected void setDefaultCriteria() {
		CriteriaHandlerFacility facility = CriteriaHandlerFacility.getInstance();
		PagingCriteria pc = new PagingCriteria();
		SortCriteria sc = new SortCriteria();
		FilterCriteria fc = new FilterCriteria(null, null,null,null, null);
		OrderCriteria oc = new OrderCriteria();
		SearchCriteria searchCriteria = new SearchCriteria();
		SearchWordsCriteria searchWordsCriteria = new SearchWordsCriteria();
		DisplayTabCriteria dtc = new DisplayTabCriteria();
		facility.initFacility(fc, sc, pc, oc, searchCriteria,searchWordsCriteria,dtc);

	}

	protected void setDefaultETMCriteria() {
		ETMCriteriaHandlerFacility facility = ETMCriteriaHandlerFacility.getInstance();
		PagingCriteria pc = new PagingCriteria();
		SortCriteria sc = new SortCriteria();
		ExploreMktPlaceSearchCriteria searchCriteria = new ExploreMktPlaceSearchCriteria();
		ExploreMktPlaceFilterCriteria fc = new ExploreMktPlaceFilterCriteria(null,null);
		facility.initFilterFacility(fc, sc, pc, searchCriteria);

	}


	public void setStatusId(Integer statusId[]) {
		CriteriaHandlerFacility criteriaHandler = CriteriaHandlerFacility.getInstance();
		ServiceOrdersCriteria serviceOrderCriteria = criteriaHandler.getServiceOrdersCriteria();
		//R12_1
		//SL-20379
		String tab = getRequest().getParameter("displayTab");
		if(StringUtils.isBlank(tab)){
			tab = getRequest().getParameter("tab");
		}
		if (serviceOrderCriteria != null) {
			//SL-20379
			OrderCriteria orderCriteria;
			if(StringUtils.isNotBlank(tab)){
				orderCriteria = criteriaHandler.getOrderCriteria(tab);
			}
			else{
				orderCriteria = criteriaHandler.getOrderCriteria();
			}
			//OrderCriteria orderCriteria = criteriaHandler.getOrderCriteria();
			if(null != orderCriteria){
				orderCriteria.setStatusId(statusId);
				orderCriteria.setRoleId(serviceOrderCriteria.getRoleId());
				orderCriteria.setRoleType(serviceOrderCriteria.getRoleType());
				orderCriteria.setCompanyId(serviceOrderCriteria.getCompanyId());
				orderCriteria.setVendBuyerResId(serviceOrderCriteria.getVendBuyerResId());
				
				//SL-20379
				//String tab = (String)getSession().getAttribute("tab");
				tab = (String)getAttribute("tab");
				if(OrderConstants.BID_REQUESTS_STRING.equals(tab))
				{
					orderCriteria.setPriceModel(Constants.PriceModel.ZERO_PRICE_BID);
				}
			}
			
		}

	}

	/**
	 * 
	 This method is used to load pagination and sorting criteria in History Tab only
	 */
		
		protected CriteriaHandlerFacility loadCriteria() {
			CriteriaHandlerFacility criteriaHandler = CriteriaHandlerFacility.getInstance();
			handlePagingCriteria(criteriaHandler);
			handleSortCriteria(criteriaHandler);
			return criteriaHandler;

		}
	
	/**
	 * This method was called from two different access points.
	 *  1. prepare() method of SOMAction  2.execute method of SOMAction.
	 *  Its re factored to remove calls from prepare to save number of SQL calls.
	 * @param loadFilters
	 * @return
	 */
	protected CriteriaHandlerFacility loadCriteria(boolean loadFilters) {
		CriteriaHandlerFacility criteriaHandler = CriteriaHandlerFacility.getInstance();
		ServiceOrdersCriteria soContext = get_commonCriteria();
		Integer roleId = new Integer(0);
		//SL-20379
		String tab = getRequest().getParameter("displayTab");
		if(StringUtils.isBlank(tab)){
			tab = getRequest().getParameter("tab");
		}
		// This loads the status and sub status filters on the grid
		Integer statusId = getTabStatus().intValue();
		if (criteriaHandler != null) {
			if (criteriaHandler.getServiceOrdersCriteria() != null) {
					roleId = criteriaHandler.getServiceOrdersCriteria().getRoleId();
			}
			/**No Need to show pending cancel status in filter for non funded buyer.Hence validating details*/
			Integer buyerId = null;
			boolean isNonFunded = false;
			/**R12_1 SL-20554 Need to show method of closure for only inhomebuyer ******/
			boolean isInHomeBuyer = false;
			if (roleId == 3) {
				buyerId = criteriaHandler.getServiceOrdersCriteria().getCompanyId();
				isNonFunded = soMonitorDelegate.isNonFundedBuyer(buyerId);
					//R12_1 SL-20554
					if(buyerId.intValue() == OrderConstants.BUYERIDOFHSR){
						isInHomeBuyer = true;						
					}
				} 
			   setAttribute("isNonFundedBuyer",isNonFunded);
			   //R12_1 SL-20554
			   setAttribute("isInHomeBuyer",isInHomeBuyer); 
				
			   getSubStatusDetails(statusId, roleId);
		
			  /****** Code to load the Service Pro Name and Market Name Filters *****/
               Integer resourceId = soContext.getSecurityContext().getVendBuyerResId();
               loadServiceProviders(resourceId);
               loadServiceProviderMarkets(resourceId);
             /******* Code to load the Service Pro Name and Market Name Filters *********/

			Integer iArray[] = new Integer[1];
			iArray[0] = statusId;
			setStatusId(iArray);
		} else {
			//SL-20379: commenting this unwanted code
			/*FilterCriteria filterCriteria = handleFilterCriteria(criteriaHandler, tab);
			handlePagingCriteria(criteriaHandler, tab);
			handleSortCriteria(criteriaHandler, tab);
			setStatusId(filterCriteria.getStatus());*/
		}
		getSubStatusDetails(statusId, roleId);
		Integer iArray[] = new Integer[1];
		iArray[0] = statusId;
		setStatusId(iArray);
		//SL-20379
		/*FilterCriteria filterCriteria = handleFilterCriteria(criteriaHandler, tab);
		handlePagingCriteria(criteriaHandler, tab);
		handleSortCriteria(criteriaHandler, tab);
		setStatusId(filterCriteria.getStatus());*/
		if(StringUtils.isNotBlank(tab)){
			FilterCriteria filterCriteria = handleFilterCriteria(criteriaHandler, tab);
			handlePagingCriteria(criteriaHandler, tab);
			handleSortCriteria(criteriaHandler, tab);
			setStatusId(filterCriteria.getStatus());
		}else{
			FilterCriteria filterCriteria = handleFilterCriteria(criteriaHandler);
			handlePagingCriteria(criteriaHandler);
			handleSortCriteria(criteriaHandler);
			setStatusId(filterCriteria.getStatus());
		}
		return criteriaHandler;
	}

	protected ETMCriteriaHandlerFacility loadETMCriteria() {
		ETMCriteriaHandlerFacility criteriaHandler = ETMCriteriaHandlerFacility.getInstance();

		//handlePagingCriteria(criteriaHandler);
		//handleETMSortCriteria(criteriaHandler);
		return criteriaHandler;
	}

    protected CriteriaHandlerFacility loadOGMCriteria(Integer soStatus, String searchType, String searchValue) {

		CriteriaHandlerFacility criteriaHandler = CriteriaHandlerFacility.getInstance();

		// Setup OrderCriteria
		criteriaHandler.getOrderCriteria().setRoleId(get_commonCriteria().getRoleId());
		criteriaHandler.getOrderCriteria().setRoleType(get_commonCriteria().getRoleType());
		criteriaHandler.getOrderCriteria().setVendBuyerResId(get_commonCriteria().getVendBuyerResId());
		criteriaHandler.getOrderCriteria().setCompanyId(get_commonCriteria().getCompanyId());

		// Setup FilterCriteria
		criteriaHandler.getFilterCriteria().setStatus(new Integer[] {soStatus});

		// Setup SearchCriteria
		criteriaHandler.getSearchingCriteria().setSearchType(searchType);
		criteriaHandler.getSearchingCriteria().setSearchValue(searchValue);
		criteriaHandler.getSearchingCriteria().setSearchFromOrderGroupManager(true);

		// Setup PagingCriteria
		final int PAGE_SIZE = 1000;
		criteriaHandler.getPagingCriteria().setPageSize(PAGE_SIZE);
		criteriaHandler.getPagingCriteria().setStartIndex(1);
		criteriaHandler.getPagingCriteria().setEndIndex(PAGE_SIZE);

		return criteriaHandler;

	}

	protected void loadDataGridFilters(boolean loadFilters) {

		CriteriaHandlerFacility criteriaHandler = loadCriteria(loadFilters);
		if(criteriaHandler == null)
			return;

		//SL-20379
		String tab = getRequest().getParameter("displayTab");
		if(StringUtils.isBlank(tab)){
			tab = getRequest().getParameter("tab");
		}
		
		if (getRequest().getParameter("resetSort") != null) {
			String resetSort = (String) getRequest().getParameter("resetSort");
			if (!StringUtils.equals(resetSort, "false")) {
				//SL-20379
				//criteriaHandler.getSortingCriteria().reset();
				if(StringUtils.isNotBlank(tab)){
					criteriaHandler.getSortingCriteria(tab).reset();
				}else{
					criteriaHandler.getSortingCriteria().reset();
				}
			}
		}

		// Added criteria for Bulletin Board - Make sure to add search words criteria
		handleSearchWordsCriteria(criteriaHandler);
		handleDisplayTabCriteria(criteriaHandler);

		//SL-20379
		//CriteriaMap criteriaMap = criteriaHandler.getCriteria();
		CriteriaMap criteriaMap = null;
		if(StringUtils.isNotBlank(tab)){
			criteriaMap = criteriaHandler.getCriteria(tab);
		}else{
			criteriaMap = criteriaHandler.getCriteria();
		}
		 
		if(criteriaMap == null)
			return;

		if(loadFilters == true){
			ServiceOrderMonitorResultVO serviceOrderMonitorResultVO;
			//SL-20379
			//serviceOrderMonitorResultVO = soMonitorDelegate.fetchServiceOrderResults(criteriaMap);
			if(StringUtils.isNotBlank(tab)){
				serviceOrderMonitorResultVO = soMonitorDelegate.fetchServiceOrderResults(criteriaMap, tab);
			}else{
				serviceOrderMonitorResultVO = soMonitorDelegate.fetchServiceOrderResults(criteriaMap);
			}
			if(serviceOrderMonitorResultVO != null)
				setSOMonitorResult(serviceOrderMonitorResultVO);
		}
	}

	/**
	 * Add the Search Words Criteria to the criteria Handler.
	 * @param criteriaHandler
	 */
	private void handleSearchWordsCriteria(CriteriaHandlerFacility criteriaHandler) {

		String searchWords = getRequest().getParameter("searchWords");

		if(searchWords!=null && searchWords.trim().length()>0){
			SearchWordsCriteria swc = new SearchWordsCriteria(true, searchWords);
			criteriaHandler.addCriteria( OrderConstants.SEARCH_WORDS_CRITERIA_KEY, swc);
		}

	}



	/**
	 * Add the Search Words Criteria to the criteria Handler.
	 * @param criteriaHandler
	 */
	private void handleDisplayTabCriteria(CriteriaHandlerFacility criteriaHandler) {
		String displayTab = getRequest().getParameter("tab");
			if(displayTab!=null && displayTab.trim().length()>0){
			DisplayTabCriteria dtc = new DisplayTabCriteria(true, displayTab);
			criteriaHandler.addCriteria( OrderConstants.DISPLAY_TAB_CRITERIA_KEY, dtc);
		}

	}


	protected void setSOMonitorResult(ServiceOrderMonitorResultVO resultVO) {
		List serviceOrderResults = null;
		PaginationVO paginationVO = null;
		if (resultVO != null) {
			serviceOrderResults = resultVO.getServiceOrderResults();
			paginationVO = resultVO.getPaginationVO();
			List<PaginetVO> currentList = paginationVO.getCurrentResultSetBucket();

		}

		setAttribute("soOrderList", serviceOrderResults);
		setAttribute("paginationVO", paginationVO);

	}

	public void updateSOSubStatus(Integer subStatusIdChanged, String soId, Integer wfStateId) {
		//LMA.....Added this method here for SODetails and SOWReview Actions
		try{
			Map<String, Object> sessionMap = ActionContext.getContext().getSession();
			SecurityContext securityContext = (SecurityContext) sessionMap.get(Constants.SESSION.SECURITY_CONTEXT);
			detailsDelegate.updateSOSubStatus(soId, subStatusIdChanged, securityContext);
			ServiceOrderDTO serviceOrder=detailsDelegate.getServiceOrder(soId,_commonCriteria.getRoleId(),_commonCriteria.getVendBuyerResId());
			String buyerIdString = serviceOrder.getBuyerID();
			Integer buyerId=0;
			if(StringUtils.isNotBlank(buyerIdString)){
				buyerId = new Integer(buyerIdString);
			}
			Integer empId=serviceOrder.getAcceptedVendorId();
			boolean isEligibleForNPSNotification=false;
			try {
				isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,soId);
			} catch (BusinessServiceException e) {
				logger.info("Exception in validatiing nps notification eligibility"+ e.getMessage());
			}
			if(isEligibleForNPSNotification){
				//Call Insertion method
				try {
					notificationService.insertNotification(soId,wfStateId,subStatusIdChanged,empId);
				 }catch (BusinessServiceException e){
					logger.info("Caught Exception while insertNotification",e);				
					}
			}
		} catch (BusinessServiceException bse) {
			logger.error("updateSOSubStatus()-->EXCEPTION-->", bse);
		}catch(Exception e){
			logger.error("Exception in inhome NPS substatus change notification"+ e.getMessage());
		}
	}
   
    
	public void updateSOCustomReference(String refType, String refVal, String refValOld,  String soId) {
		
		try{
			Map<String, Object> sessionMap = ActionContext.getContext().getSession();
			SecurityContext securityContext = (SecurityContext) sessionMap.get(Constants.SESSION.SECURITY_CONTEXT);
			detailsDelegate.updateSOCustomReference(soId, refType, refVal, refValOld, securityContext);
		} catch (BusinessServiceException bse) {
			logger.error("updateSOSubStatus()-->EXCEPTION-->", bse);
		}
	}
	
	protected void getSubStatusDetails(Integer statusId, Integer roleId) {
		ArrayList<ServiceOrderStatusVO> serviceOrderStatusVOList = null;
		serviceOrderStatusVOList = (ArrayList<ServiceOrderStatusVO>)soMonitorDelegate.getSubStatusDetails(statusId, roleId);
		//SL-20379
		//if(getSession().getAttribute("tabStatus")!= null){
		if(StringUtils.isNotBlank((String)getAttribute("tabStatus"))){
			//SL-20379
			//Integer tabStatus = new Integer((String)getSession().getAttribute("tabStatus"));
			Integer tabStatus = new Integer((String)getAttribute("tabStatus"));
			for (ServiceOrderStatusVO vo : serviceOrderStatusVOList){
				if(vo.getStatusId() == tabStatus.intValue()){
					vo.setSelected(true);
					setAttribute("showSubStatus", true);
				}else{
					vo.setSelected(false);
				}
			}
		}
		setAttribute("serviceOrderStatusVOList", serviceOrderStatusVOList);
	}
	/**
	 * This method is used to fetch all the provider names under a firm to load the filter
	 * @param resourceId
	 */
	private void loadServiceProviders(Integer resourceId){
		ArrayList<ProviderDetail> arrServiceProvidersList = null;
		try {
			arrServiceProvidersList = soMonitorDelegate.loadServiceProviders(resourceId);
			setAttribute("ServiceProvidersList", arrServiceProvidersList);
		} catch (Exception e) {
			logger.error("Error in executing getServiceProviders :"
					+ e.getMessage());
		}
	}
	/**
	 * This method is used to fetch all the market names for the providers under a firm to load the filter
	 * @param resourceId
	 * @return ArrayList
	 */
	private void loadServiceProviderMarkets(Integer resourceId){
		ArrayList<ProviderDetail> arrMarketsList = null;
		try {
			arrMarketsList = soMonitorDelegate.loadServiceProviderMarkets(resourceId);
			setAttribute("ServiceProvidersMarketsList", arrMarketsList);
		} catch (Exception e) {
			logger.error("Error in executing getServiceProviders :"
					+ e.getMessage());
		}
	}
	protected void validateLoadDataGridFilters(boolean caller) {
		loadDataGridFilters(caller);
	}

	protected Integer getTabStatus()
	{
		Integer myStatus = null;
		String tabId = "";
		String status = getRequest().getParameter(SOConstants.SO_STATUS);
		if ((null != status) && StringUtils.isNumeric(status) && !StringUtils.equalsIgnoreCase(status, SOConstants.TODAY))
		{
			myStatus = Integer.parseInt(status);
			getRequest().setAttribute(SOConstants.SO_STATUS, myStatus);
		}
		else
		{
			//SL-20379
			//if (getSession().getAttribute("tab") != null)
			if (getAttribute("tab") != null)
			{
				// tab parameter represents tab type status
				//SL-20379
				//tabId = (String) getSession().getAttribute("tab");
				tabId = (String) getAttribute("tab");
			}
			else
			{
				// set it to TODAY.
				tabId = SOConstants.TODAY;
			}
			
			/*
			 * if (getRequest().getParameter("status")!= null){ // 'status'
			 * captures the user selected status from the 'Status Filter' pull
			 * down statusId = getRequest().getParameter("status"); }
			 */
			int i = ObjectMapper.statusCode(tabId);
			myStatus = new Integer(i);
			getRequest().setAttribute(SOConstants.SO_STATUS, "");
		}
		return myStatus;
	}

	public void getAcceptServiceOrderTermsAndCond() throws BusinessServiceException {
		logger.debug("Entering the SLBaseAction.getAcceptServiceOrderTermsAndCond()");

		/*
		 * Calling lookup delegate and puttingAccept terms and condition in to
		 * servletContext if not already
		 */
		TermsAndConditionsVO termsAndCond = (TermsAndConditionsVO)getSession().getAttribute("acceptSOTermsAndCond");
		// Load termsAndCond only if not already loaded
		if (termsAndCond == null) {
			termsAndCond = new TermsAndConditionsVO();
			try {
				termsAndCond = detailsDelegate
				.getAcceptServiceOrderTermsAndCond(SOConstants.ACCEPT_TERMS_AND_COND_FOR_AGREEMENT);
				SecurityContext securityContext = (SecurityContext) getSession()
				.getAttribute(SECURITY_KEY);
				TermsAndConditionsVO termsAndCondBucks = new TermsAndConditionsVO();

				termsAndCondBucks = detailsDelegate
				.getAcceptServiceOrderTermsAndCond(SOConstants.ACCEPT_TERMS_AND_COND_BUX_FOR_PROVIDER);
				getSession().setAttribute("acceptSOTermsAndCond", termsAndCond);
				getSession().setAttribute("acceptBucksSOTermsAndCond", termsAndCondBucks);
			} catch (Exception e) {
				logger
				.error("Error in  SLBaseAction.getAcceptServiceOrderTermsAndCond() while getting the terms and condition");
				e.printStackTrace();
			}
		}
	}

	public void getRejectReasons() throws BusinessServiceException {

		/*
		 * Calling lookup delegate and putting reject codes in to servletContext
		 * if not already
		 */
		if (getSession().getServletContext().getAttribute("rejectCodes") == null) {
			LuProviderRespReasonVO luReasonVO = new LuProviderRespReasonVO();
			luReasonVO.setSearchByResponse(SOConstants.PROVIDER_RESP_REJECTED);
			ArrayList<LuProviderRespReasonVO> al = new ArrayList();
			// refactor me. Put me in an abstract base class which the
			// details and monitor delegates extend.
			if (soMonitorDelegate != null) {
				al = soMonitorDelegate.getRejectReasons(luReasonVO);
			} else {
				al = detailsDelegate.getRejectReasons(luReasonVO);
			}
			getSession().getServletContext().setAttribute("rejectCodes", al);
		}
	}

	protected FilterCriteria handleFilterCriteria(CriteriaHandlerFacility facility) {
		FilterCriteria filterCriteria = null;

		if (facility == null)
			return null;

		filterCriteria = facility.getFilterCriteria();
		if (filterCriteria == null)
			return null;
		// Set Status in filterCriteria
		Integer statusInt = null;
		String statusStr = getRequest().getParameter("status");
		if(StringUtils.isBlank(statusStr) && null!=filterCriteria.getStatus())
		{
			statusStr = filterCriteria.getStatus()[0].toString();
		}
		
		//SL-20379
		//if(getSession().getAttribute("tabStatus")!=null){
		if(getAttribute("tabStatus")!=null){
			//SL-20379
			//statusStr = getSession().getAttribute("tabStatus").toString();
			statusStr = getAttribute("tabStatus").toString();
		}

		if(StringUtils.isNotBlank(statusStr) && !statusStr.equalsIgnoreCase("null"))
		{
			if (statusStr.equalsIgnoreCase("Today"))
			{
				statusInt = new Integer(OrderConstants.TODAY_STATUS);
			}
			else if (statusStr.equalsIgnoreCase("Inactive"))
			{
				statusInt = new Integer(OrderConstants.INACTIVE_STATUS);
			}
			else if (statusStr.equalsIgnoreCase("Posted")
					|| statusStr.equalsIgnoreCase("Received"))
			{
				statusInt = new Integer(OrderConstants.ROUTED_STATUS);
			}
			else
			{
				statusInt = new Integer(statusStr);
			}
			Integer iArray[] = new Integer[1];
			iArray[0] = statusInt;
			filterCriteria.setStatus(iArray);
			getRequest().setAttribute(SOConstants.SO_STATUS, statusStr);
		}
		else
		{
			statusInt = getTabStatus();
		}
		Integer iArray[] = new Integer[1];
		iArray[0] = statusInt;
		filterCriteria.setStatus(iArray);

		if((statusInt != null && OrderConstants.ROUTED_STATUS == statusInt.intValue()
				|| OrderConstants.SEARCH_STATUS == statusInt.intValue()) 
				&&  get_commonCriteria().getRoleId() == OrderConstants.PROVIDER_ROLEID) {
			
			boolean canManageSO = ((Boolean) getSession().getAttribute("canManageSO")).booleanValue();
			boolean primaryInd = ((Boolean) getSession().getAttribute("isPrimaryInd")).booleanValue();
			boolean dispatchInd = ((Boolean) getSession().getAttribute("dispatchInd")).booleanValue();
			//Checking whether the user has the rights to manage SO[4 is the Activity Id for the Activity-Manage Service Orders]
			// and not an admin, then search for specific resourceId only
			if(canManageSO && !primaryInd && !dispatchInd){
				filterCriteria.setManageSOFlag(canManageSO);
				filterCriteria.setResourceId(_commonCriteria.getVendBuyerResId().toString());
			}
		}

		// Set Substatus in filterCriteria
		String subStatus = getRequest().getParameter("subStatus");
		String soSubStatus = getRequest().getParameter(SOConstants.SO_SUBSTATUS);
		if(StringUtils.isNotBlank(subStatus)){
			if ((subStatus.equals("0")) || (subStatus.equals("null"))) {
					if(StringUtils.isNotBlank(soSubStatus)){
						filterCriteria.setSubStatus(soSubStatus);
					}else{
						filterCriteria.setSubStatus(null);
					}
				}
				else
				{
					filterCriteria.setSubStatus(getRequest().getParameter("subStatus"));
				}
		}else{
			if( (null!=soSubStatus) && (StringUtils.isNotBlank(soSubStatus))&& !(soSubStatus.equals("null"))){
				filterCriteria.setSubStatus(soSubStatus);
			}
		}
		getRequest().setAttribute(SOConstants.SO_SUBSTATUS, filterCriteria.getSubStatus());
		
		// Set Price Model in filterCriteria
		String priceModel = getRequest().getParameter(SOConstants.PRICE_MODEL);
		if(StringUtils.isNotBlank(priceModel)){
			if ((priceModel.equals("0")) || (priceModel.equals("null") || (priceModel.equals("all")))) {
					filterCriteria.setPriceModel(null);
				}
				else
				{
					filterCriteria.setPriceModel(getRequest().getParameter(SOConstants.PRICE_MODEL));
				}
		}
		

		String requestedTab = getRequest().getParameter("tab");

		if (!StringUtils.isEmpty(filterCriteria.getPriceModel())
				&& !StringUtils.equals(filterCriteria.getPriceModel(),
						OrderConstants.NULL_STRING)) {
				if (requestedTab != null ){
					getSession().setAttribute(SESSION_PRICE_MODEL + requestedTab,
							filterCriteria.getPriceModel());
				}else
				{
					getSession().setAttribute(SESSION_PRICE_MODEL,
							filterCriteria.getPriceModel());
				}
		}
		
		// SL-11776 - It's not clear if this code should be moved up to allow the Price Model to be set in the session or not.
		// The code seems to be running ok without doing that
		if(OrderConstants.BID_REQUESTS_STRING.equals(requestedTab)) {
			filterCriteria.setPriceModel(Constants.PriceModel.ZERO_PRICE_BID);
		}

		getRequest().setAttribute(SOConstants.PRICE_MODEL, filterCriteria.getPriceModel());
		
		/************** Set the Service Pro Name in filter Criteria *******************/
		String serviceProNameStr = getRequest().getParameter("serviceProName");
		if((serviceProNameStr != null && serviceProNameStr.equalsIgnoreCase("null")) || (serviceProNameStr != null && serviceProNameStr.equalsIgnoreCase("undefined"))){
			serviceProNameStr=null;
		}
		if(StringUtils.isBlank(serviceProNameStr) && null!=filterCriteria.getServiceProName())
		{
			serviceProNameStr = filterCriteria.getServiceProName().toString();
		}

		if(StringUtils.isNotBlank(serviceProNameStr))
		{
			filterCriteria.setServiceProName(serviceProNameStr);
			getRequest().setAttribute(SOConstants.SO_SERVICEPRONAME, serviceProNameStr);
		}

		String buyerRoleIdStr = getRequest().getParameter("buyerRoleId");
		if(StringUtils.isNotEmpty(buyerRoleIdStr) && StringUtils.isNumeric(buyerRoleIdStr))
		{
			Integer buyerRoleId = Integer.parseInt(buyerRoleIdStr);
			filterCriteria.setBuyerRoleId(buyerRoleId);
			getRequest().setAttribute(SOConstants.SO_ROLEID, buyerRoleId);
		}

		/************** Set the Market Name in filter Criteria ***********************/
		String marketNameStr = getRequest().getParameter("marketName");
		if((marketNameStr != null && marketNameStr.equalsIgnoreCase("null")) || (marketNameStr != null && marketNameStr.equalsIgnoreCase("undefined"))) {
			marketNameStr=null;
		}
		if(StringUtils.isBlank(marketNameStr) && null!=filterCriteria.getMarketName())
		{
			marketNameStr = filterCriteria.getMarketName().toString();
		}

		if(StringUtils.isNotBlank(marketNameStr))
		{
			filterCriteria.setMarketName(marketNameStr);
			getRequest().setAttribute(SOConstants.SO_MARKETNAME, marketNameStr);
		}
		// set filterId in filterCriteria
		String str = (String) getRequest().getAttribute("pbFilterId");
		filterCriteria.setPbfilterId(StringUtils.isBlank(str) ? null : Integer.valueOf(str));

		str = (String) getRequest().getAttribute("pbFilterOpt");
		filterCriteria.setPbfilterOpt(StringUtils.isBlank(str) ? null : str);

		str=(String) getRequest().getAttribute("wfmSodFlag");
		filterCriteria.setWfm_sodFlag(StringUtils.isBlank(str) ? null : str);
		
		str=(String) getRequest().getAttribute("soId");
		filterCriteria.setSoId(StringUtils.isBlank(str) ? null : str);

		return filterCriteria;
	}
	
	public SortCriteria handleSortCriteria(CriteriaHandlerFacility facility) {
		SortCriteria sortCriteria = null;
		String requestedTab = getRequest().getParameter("tab");

		if (null != facility) {
			sortCriteria = facility.getSortingCriteria();
			if (null != sortCriteria) {
				sortCriteria.setSortColumnName(getRequest().getParameter(
						SESSION_SORT_COLUMN));
				sortCriteria.setSortOrder(getRequest().getParameter(
						SESSION_SORT_ORDER));


		// set sort data into session for use by paging component
		if (!StringUtils.isEmpty(sortCriteria.getSortColumnName())
				&& !StringUtils.equals(sortCriteria.getSortColumnName(),
						OrderConstants.NULL_STRING)
				&& !StringUtils.isEmpty(sortCriteria.getSortOrder())
				&& !StringUtils.equals(sortCriteria.getSortOrder(),
						OrderConstants.NULL_STRING)) {
				if (requestedTab != null ){
					getSession().setAttribute(SESSION_SORT_COLUMN + requestedTab,
							sortCriteria.getSortColumnName());
					getSession().setAttribute(SESSION_SORT_ORDER + requestedTab,
							sortCriteria.getSortOrder());
				}else
				{
					getSession().setAttribute(SESSION_SORT_COLUMN,
							sortCriteria.getSortColumnName());
					getSession().setAttribute(SESSION_SORT_ORDER,
							sortCriteria.getSortOrder());
				}
		} else {
			if (requestedTab != null ){
				sortCriteria.setSortColumnName((String) getSession().getAttribute(
						SESSION_SORT_COLUMN + requestedTab));
				sortCriteria.setSortOrder((String) getSession().getAttribute(
						SESSION_SORT_ORDER + requestedTab));
			}else
			{
				sortCriteria.setSortColumnName((String) getSession().getAttribute(
						SESSION_SORT_COLUMN));
				sortCriteria.setSortOrder((String) getSession().getAttribute(
						SESSION_SORT_ORDER));
			}
		  }
		}
	  }
		return sortCriteria;
	}

	protected void maps(){
		yesNoRadio.put("1", "Yes");
		yesNoRadio.put("0", "No");
	}

	protected SearchCriteria _handleSearchCriteria(CriteriaHandlerFacility facility) {
		SearchCriteria searchCriteria = null;
		String type = null;

		if (null != facility) {
			searchCriteria = facility.getSearchingCriteria();
			if (null != searchCriteria) {
				/*type = getRequest().getParameter("searchType");
				if (!StringUtils.isEmpty(type)) {
					searchCriteria.setSearchType(type);
				}
				String searchValue = getRequest().getParameter("searchValue");
				if(searchValue != null)
					searchValue.trim();
				searchCriteria.setSearchValue(searchValue);
				*/
				//Code for Advance Search Filter
				String selectedFilter = getRequest().getParameter("selectedFilterName");
				if(null != selectedFilter && !StringUtils.isEmpty(selectedFilter)){
					SearchFilterVO searchFilterVO = new SearchFilterVO();
					searchFilterVO.setFilterName(selectedFilter);
					SecurityContext sContext = get_commonCriteria().getSecurityContext();
					searchFilterVO.setEntityId(sContext.getCompanyId());
					searchFilterVO = soMonitorDelegate.getSelectedSearchFilter(searchFilterVO);
					searchCriteria.reset();
					searchCriteria.setFilterNameSearch(true);
					searchCriteria.setFilterTemplate(searchFilterVO.getTemplateHtmlContent());
					searchCriteria = mapSavedFilterToSearchCriteria(searchFilterVO, searchCriteria);
				}else{				
					searchCriteria.setFilterNameSearch(false);
					searchCriteria = loadAdvanceSearchCriteria(searchCriteria);
				}	
			}
		}
		return searchCriteria;
	}
	
	public PagingCriteria handlePagingCriteria(CriteriaHandlerFacility facility) {
		PagingCriteria pagingCriteria = facility.getPagingCriteria();
		if (facility != null) {
			if (pagingCriteria != null) {
				if (getRequest().getParameter("startIndex") != null) {
					if (!getRequest().getParameter("startIndex").equals("")) {
						String l = getRequest().getParameter("startIndex");
						pagingCriteria.setStartIndex(new Integer(getRequest().getParameter("startIndex")).intValue());

					}
				}
				if (getRequest().getParameter("endIndex") != null) {
					if (!getRequest().getParameter("endIndex").equals("")) {
						String k = getRequest().getParameter("endIndex");
						pagingCriteria.setEndIndex(new Integer(getRequest().getParameter("endIndex")).intValue());
					}
				}
				if (getRequest().getParameter("pageSize") != null) {
					if (!getRequest().getParameter("pageSize").equals("")) {
						String k = getRequest().getParameter("pageSize");
						pagingCriteria.setPageSize(new Integer(getRequest().getParameter("pageSize")).intValue());
					}
				}

			}
		}
		return pagingCriteria;
	}

	public void resetStartingIndexPagingCriteria(CriteriaHandlerFacility facility) {
		if (facility != null) {
			PagingCriteria pagingCriteria  = facility.getPagingCriteria();
			if (pagingCriteria != null) {
				pagingCriteria.setStartIndex(new Integer(1));
			}
		}
	}
	public ArrayList<String> getStarsFromRating(Double rating) {
		if (rating == null)
			return new ArrayList<String>();

		if (rating >= 4.5)
			return starsList.get(9);
		if (rating >= 4.0)
			return starsList.get(8);
		if (rating >= 3.5)
			return starsList.get(7);
		if (rating >= 3.0)
			return starsList.get(6);
		if (rating >= 2.5)
			return starsList.get(5);
		if (rating >= 2.0)
			return starsList.get(4);
		if (rating >= 1.5)
			return starsList.get(3);
		if (rating >= 1.0)
			return starsList.get(2);
		if (rating >= 0.5)
			return starsList.get(1);
		if (rating >= 0.0)
			return starsList.get(0);

		return null;
	}

	protected void logSession() {
		if (logger.isDebugEnabled()) {
			java.util.Enumeration<String> en = getSession().getAttributeNames();
			while (en.hasMoreElements()) {
				String key = en.nextElement();
				logger.debug("Session Key: " + key + " Value: "
						+ getSession().getAttribute(key));
			}
		}
	}


	private void initStarsList() {
		if (starsList != null)
			return;

		starsList = new ArrayList<ArrayList<String>>();

		ArrayList<String> tmpList;

		// 0 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 1/2 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("half_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 1 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("full_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 1.5 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("full_star_gbg.gif");
			tmpList.add("half_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 2 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 2.5 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("half_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 3 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 3.5 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("half_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 4 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("empty_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 4.5 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("half_star_gbg.gif");
			starsList.add(tmpList);
		}
		// 5 stars
		{
			tmpList = new ArrayList<String>();
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			tmpList.add("full_star_gbg.gif");
			starsList.add(tmpList);
		}
	}

	public static ArrayList<ArrayList<String>> getStarsList() {
		return starsList;
	}

	public static void setStarsList(ArrayList<ArrayList<String>> starsList) {
		SLBaseAction.starsList = starsList;
	}

	protected boolean isRole(String roleName) {

		return this._commonCriteria.getRoleType().equalsIgnoreCase(roleName);
	}

	public ServiceOrdersCriteria get_commonCriteria() {
		return _commonCriteria;
	}

	public void set_commonCriteria(ServiceOrdersCriteria criteria) {
		_commonCriteria = criteria;
	}

	protected String getRedirectURL() {
		return redirectURL;
	}

	protected void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	protected String getTab() {
		return tab;
	}

	protected void setTab(String tab) {
		this.tab = tab;
	}

	public ISOMonitorDelegate getServiceOrderDelegate() {
		return soMonitorDelegate;
	}

	public void setServiceOrderDelegate(ISOMonitorDelegate serviceOrderDelegate) {
		this.soMonitorDelegate = serviceOrderDelegate;
	}

	// YYYY-MM-DD
	public String getDateString() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
	}

	/**
	 * Returns the time zone for the provided zip.  If the zip is invalid NULL is returned
	 * @param zip
	 * @return
	 */
	protected String getTimeZone (String zip) {
		LocationVO locationVo = null;
		String toReturn = null;

		if (StringUtils.isNotEmpty(zip)) {
			ILookupBO lookupBO = (ILookupBO)MPSpringLoaderPlugIn.getCtx().getBean("lookupBO");
			try {
				locationVo = lookupBO.checkIFZipExists(zip);
				if(locationVo != null){
					toReturn = locationVo.getTimeZone();
				}
			} catch (DataServiceException e) {
				logger.info("Unable to validate zip, returning null",e);
			}
		}
		return toReturn;
	}

	public ExploreMktPlaceSearchCriteria handleETMSearchCriteria(ETMCriteriaHandlerFacility facility, ETMSearchDTO etmSearchDto) {
		ExploreMktPlaceSearchCriteria searchCriteria = null;
		String strServiceCategory = null;
		String strZipCd = null;
		Integer intMarketReady = null;

		if (null != facility) {
			searchCriteria = facility.getETMSearchCriteria();
			if (null != searchCriteria) {
				strServiceCategory = etmSearchDto.getSkillTreeMainCat();
				intMarketReady = etmSearchDto.getMarketReadySelection()!=null?new Integer(etmSearchDto.getMarketReadySelection()):null;
				strZipCd = etmSearchDto.getZipCd();

				if (!StringUtils.isEmpty(strServiceCategory) && StringUtils.isNumeric(strServiceCategory)) {
					searchCriteria.setServiceCategoryId(strServiceCategory!=null?new Integer(strServiceCategory):null);
				}
				searchCriteria.setMktReady(intMarketReady);
				if (!StringUtils.isEmpty(strZipCd) && StringUtils.isNumeric(strZipCd)) {
					searchCriteria.setBuyerZipCodeStr(strZipCd);
				}
				facility.setExploreMktPlaceSearchCriteria(searchCriteria);
				//Assumption as dangerous : So STATING over here that ETM is used for BUYER ONLY ..so following state would ve
				Integer buyerId = get_commonCriteria().getSecurityContext().getCompanyId();
				if(buyerId != null) {
					searchCriteria.setBuyerID(buyerId);
				}
			}
		}
		return searchCriteria;
	}

	public SortCriteria handleETMSortCriteria(ETMCriteriaHandlerFacility facility) {
		SortCriteria sortCriteria = null;

		if (null != facility) {
			sortCriteria = facility.getSortingCriteria();
			if (null != sortCriteria) {
				sortCriteria.setSortColumnName(getRequest().getParameter(
						SESSION_SORT_COLUMN));
				sortCriteria.setSortOrder(getRequest().getParameter(
						SESSION_SORT_ORDER));


		// set sort data into session for use by paging component
		if (!StringUtils.isEmpty(sortCriteria.getSortColumnName())
				&& !StringUtils.equals(sortCriteria.getSortColumnName(),
						OrderConstants.NULL_STRING)
				&& !StringUtils.isEmpty(sortCriteria.getSortOrder())
				&& !StringUtils.equals(sortCriteria.getSortOrder(),
						OrderConstants.NULL_STRING)) {

			getSession().setAttribute(SESSION_SORT_COLUMN,
						sortCriteria.getSortColumnName());
			getSession().setAttribute(SESSION_SORT_ORDER,
						sortCriteria.getSortOrder());

		} else {

			sortCriteria.setSortColumnName((String) getSession().getAttribute(
					SESSION_SORT_COLUMN));
			sortCriteria.setSortOrder((String) getSession().getAttribute(
					SESSION_SORT_ORDER));
			}

		}
			 facility.setSortCriteria(sortCriteria);
	  }

		 return sortCriteria;
	}

	public void handleETMSearchKey(ETMCriteriaHandlerFacility facility){
		String searchKey = "";
		searchKey = facility.getSearchKey();
		if (null != facility) {
			facility.addSearchKey(searchKey);
		}
	}

	public ExploreMktPlaceFilterCriteria reversehandleETMFilterCriteria(ETMCriteriaHandlerFacility facility, ETMSearchDTO etmSearchDto) {
		ExploreMktPlaceFilterCriteria filterCriteria = null;

		Integer distance = 0;
		Integer credentialCategory = 0;
		Integer selectedCredential = 0;
		List<Integer> selectedLangs;
		Double rating = 0.0;

		if (null != facility && etmSearchDto != null) {
			filterCriteria = facility.getETMFilterCriteria();
			if (null != filterCriteria) {
				if(filterCriteria.getDistance() != null && "0".equals(filterCriteria.getDistance()))
					distance = null;
				else
					distance = filterCriteria.getDistance();

				if(filterCriteria.getCredentialCategory() != null && filterCriteria.getCredentialCategory().intValue() <= 0)
					credentialCategory = null;
				else
					credentialCategory = filterCriteria.getCredentialCategory();

				if(filterCriteria.getSelectedCredential() != null && filterCriteria.getSelectedCredential() <= 0)
					selectedCredential = null;
				else
					selectedCredential = filterCriteria.getSelectedCredential();


				if(filterCriteria.getSelectedLangs() != null && filterCriteria.getSelectedLangs().size() > 0)
					selectedLangs = filterCriteria.getSelectedLangs();
				else
					selectedLangs = null;

				if(filterCriteria.getRating() != null && filterCriteria.getRating() <= 0.0d)
					rating = null;
				else
					rating = (filterCriteria.getRating());

				etmSearchDto.setDistance(String.valueOf(distance));
				etmSearchDto.setCredentialCategory(String.valueOf(credentialCategory));
				etmSearchDto.setCredentials(String.valueOf(selectedCredential));
				etmSearchDto.setSelectedLanguagesList(selectedLangs);
				etmSearchDto.setRatings(String.valueOf(rating));

				if(filterCriteria.getSpnId() != null && filterCriteria.getSpnId().intValue() > 0 ) {
					etmSearchDto.setSpn(filterCriteria.getSpnId());
					if(filterCriteria.getPerflevels()!= null && filterCriteria.getPerflevels().size() > 0 ){
						etmSearchDto.setPerfLevels(filterCriteria.getPerflevels());
					}
					else {
						etmSearchDto.setPerfLevels(null);
					}
				}
				else {
					etmSearchDto.setSpn(null);
					etmSearchDto.setPerfLevels(null);
				}


			}
			//facility.setExploreMktPlaceFilterCriteria(filterCriteria);
		}

		return filterCriteria;
	}

	public ExploreMktPlaceFilterCriteria handleETMFilterCriteria(ETMCriteriaHandlerFacility facility, ETMSearchDTO etmSearchDto) {
		ExploreMktPlaceFilterCriteria filterCriteria = null;

		Integer distance = 0;
		Integer credentialCategory = 0;
		Integer selectedCredential = 0;
		List<Integer> selectedLangs;
		Double rating = 0.0;

		if (null != facility) {
			filterCriteria = facility.getETMFilterCriteria();
			if (null != filterCriteria) {
				if(etmSearchDto.getDistance() != null && "0".equals(etmSearchDto.getDistance()))
					distance = null;
				else
					distance = Integer.parseInt(etmSearchDto.getDistance());

				if(etmSearchDto.getCredentialCategory() != null && Integer.parseInt(etmSearchDto.getCredentialCategory()) <= 0)
					credentialCategory = null;
				else
					credentialCategory = Integer.parseInt(etmSearchDto.getCredentialCategory());

				if(etmSearchDto.getCredentials() != null && Integer.parseInt(etmSearchDto.getCredentials()) <= 0)
					selectedCredential = null;
				else
					selectedCredential = Integer.parseInt(etmSearchDto.getCredentials());


				if(etmSearchDto.getSelectedLanguagesList() != null && etmSearchDto.getSelectedLanguagesList().size() > 0)
					selectedLangs = etmSearchDto.getSelectedLanguagesList();
				else
					selectedLangs = null;

				if(etmSearchDto.getRatings() != null && Double.parseDouble(etmSearchDto.getRatings()) <= 0)
					rating = null;
				else
					rating = Double.parseDouble(etmSearchDto.getRatings());

				filterCriteria.setDistance(distance);
				filterCriteria.setCredentialCategory(credentialCategory);
				filterCriteria.setSelectedCredential(selectedCredential);
				filterCriteria.setSelectedLangs(selectedLangs);
				filterCriteria.setRating(rating);
				if(etmSearchDto.getSpn() != null && etmSearchDto.getSpn().intValue() > 0 ) {
					filterCriteria.setSpnId(etmSearchDto.getSpn());
					if(etmSearchDto.getPerfLevels()!= null && etmSearchDto.getPerfLevels().size() > 0 ){
						filterCriteria.setPerflevels(etmSearchDto.getPerfLevels());
					}
					else {
						filterCriteria.setPerflevels(null);
					}
				}
				else {
					filterCriteria.setSpnId(null);
					filterCriteria.setPerflevels(null);
				}


			}
			facility.setExploreMktPlaceFilterCriteria(filterCriteria);
		}

		return filterCriteria;
	}

	public PagingCriteria handleETMPagingCriteria(ETMCriteriaHandlerFacility facility) {
		PagingCriteria pagingCriteria = facility.getPagingCriteria();

		if (facility != null) {
			if (pagingCriteria != null) {
				if (getRequest().getParameter("startIndex") != null) {
					if (!getRequest().getParameter("startIndex").equals("")) {
						String l = getRequest().getParameter("startIndex");
						pagingCriteria.setStartIndex(new Integer(getRequest()
								.getParameter("startIndex")).intValue());

					}
				}
				if (getRequest().getParameter("endIndex") != null) {
					if (!getRequest().getParameter("endIndex").equals("")) {
						String k = getRequest().getParameter("endIndex");
						pagingCriteria.setEndIndex(new Integer(getRequest()
								.getParameter("endIndex")).intValue());
					}
				}
				if (getRequest().getParameter("pageSize") != null) {
					if (!getRequest().getParameter("pageSize").equals("")) {
						String k = getRequest().getParameter("pageSize");
						pagingCriteria.setPageSize(new Integer(getRequest()
								.getParameter("pageSize")).intValue());
					}
				}
				facility.setPagingCriteria(pagingCriteria);
			}
		}
		return pagingCriteria;
	}

	/**
	 * This method calls the business method to retrieve the value of Auto
	 * Funding Indicator - Used in jsp display and auto funding in SO creation and
     * increased spend limits
	 *
	 * @throws Exception
	 */
	protected Account getAutoACHEnabledInd() throws Exception {
		Account acct = new Account();
		acct.setEnabledInd(false);
		acct.setAccountId(new Long(0));
		if (get_commonCriteria()!=null){
			Integer roleId = get_commonCriteria().getRoleId();
			if ((roleId != null) && (OrderConstants.BUYER_ROLEID == roleId.intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue())) {
				acct = financeManagerDelegate.getAutoFundingIndicator(get_commonCriteria().getCompanyId());
				if (OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue()){
					FMManageAccountsTabDTO fmAccount = financeManagerDelegate.getActiveCreditCardDetails(get_commonCriteria().getCompanyId());
					if(null!=fmAccount){
						acct.setAccountId(fmAccount.getCardId());
					}
				}
			}
		}
		return acct;
	}

	public static List<DropdownOptionDTO> getMonthOptions() {
		String[] months = new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

		List<DropdownOptionDTO> monthOptions = new ArrayList<DropdownOptionDTO>();

		int monthIndex = 1;
		for (String month : months) {
			DropdownOptionDTO monthDTO = new DropdownOptionDTO(month, String.valueOf(monthIndex), null);
			monthOptions.add(monthDTO);
			monthIndex++;
		}

		return monthOptions;
	}

	public List<DropdownOptionDTO> getYearOptions(int startYear, int noOfYears) {

		List<DropdownOptionDTO> years = new ArrayList<DropdownOptionDTO>();

		int endYear = startYear + noOfYears;
		for(int year = startYear ; year < endYear ; ++year) {
			DropdownOptionDTO dto = new DropdownOptionDTO(String.valueOf(year), String.valueOf(year), null);
			years.add(dto);
		}

		return years;
	}

	public HashMap getYesNoRadio() {
		return yesNoRadio;
	}

	public void setYesNoRadio(HashMap yesNoRadio) {
		this.yesNoRadio = yesNoRadio;
	}

	public ILookupDelegate getLookupManager() {
		return lookupManager;
	}

	public void setLookupManager(ILookupDelegate lookupManager) {
		this.lookupManager = lookupManager;
	}

	protected void _configureForAjaxResponse() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * If you put an strut action error or strut action message in request and
	 * redirect the action, the message will be lost. This method is written to
	 * overcome this problem. Ideally we should use MessageStoreInterceptor when
	 * we have time. Until then use this method as workaround.
	 *
	 * @param msg
	 */
	protected void setActionMessage(String msg) {
		addActionError(msg);
		ActionContext.getContext().getSession().put(ACTION_MSG, msg);
	}

	protected void setActionError(String msg) {
		addActionError(msg);
		ActionContext.getContext().getSession().put(ACTION_ERROR, msg);
	}

	/**
	 * Read comments of setActionMessage
	 */
	protected void retrieveActionMessage() {
		String obj = (String)ActionContext.getContext().getSession().get(ACTION_MSG);
		if (obj != null) {
			addActionMessage(obj);
			ActionContext.getContext().getSession().remove(ACTION_MSG);
		}

		obj = (String)ActionContext.getContext().getSession().get(ACTION_ERROR);
		if (obj != null) {
			addActionError(obj);
			ActionContext.getContext().getSession().remove(ACTION_ERROR);
		}
	}
	public SearchCriteria loadAdvanceSearchCriteria(SearchCriteria searchCriteria){
		
		//Defaulting the search Type to 11 for Advanced Search
		searchCriteria.setSearchType("11");
		//Mapping States
		String stateCodes = getRequest().getParameter("stateVals");
		if(null != stateCodes){
			List<String> stateList = new ArrayList<String>();
			StringTokenizer stateStr = new StringTokenizer(stateCodes, "|");
			while (stateStr.hasMoreTokens()) {
				String stateCode = stateStr.nextToken();
				stateList.add(stateCode);
			}
			searchCriteria.setSelectedStates(stateList);
		}
		//Mapping Skills
		String skills = getRequest().getParameter("skillVals");
		if(null != skills){
			List<String> skillList = new ArrayList<String>();
			StringTokenizer skillStr = new StringTokenizer(skills, "|");
			while (skillStr.hasMoreTokens()) {
				String skill = skillStr.nextToken();
				skillList.add(skill);
			}
			searchCriteria.setSelectedSkills(skillList);
		}
		//Mapping Markets
		String markets = getRequest().getParameter("marketVals");
		if(null != markets){
			List<Integer> marketList = new ArrayList<Integer>();
			StringTokenizer marketStr = new StringTokenizer(markets, "|");
			while (marketStr.hasMoreTokens()) {
				String market = marketStr.nextToken();
				marketList.add(Integer.parseInt(market));
			}
			searchCriteria.setSelectedMarkets(marketList);
		}
		//SL-15642
		//Mapping order acceptance type
		String acceptanceType = getRequest().getParameter("acceptanceVals");
		
		if(null != acceptanceType){
			acceptanceType = acceptanceType.toUpperCase();
			List<String> accTypeList = new ArrayList<String>();
			StringTokenizer accTypeStr = new StringTokenizer(acceptanceType, "|");
			while (accTypeStr.hasMoreTokens()) {
				String accType = accTypeStr.nextToken();
				accTypeList.add(accType);
			}
			searchCriteria.setSelectedAcceptanceTypes(accTypeList);
		}
		//Mapping pricing type
		String pricingType = getRequest().getParameter("pricingVals");
		if(null != pricingType){
			pricingType = pricingType.toUpperCase();
			List<String> pricingTypeList = new ArrayList<String>();
			StringTokenizer pricingStr = new StringTokenizer(pricingType, "|");
			while (pricingStr.hasMoreTokens()) {
				String priceType = pricingStr.nextToken();
				pricingTypeList.add(priceType);
			}
			searchCriteria.setSelectedPricingTypes(pricingTypeList);
		}
		//Mapping assignment type
		String assignmentType = getRequest().getParameter("assignmetVals");
		
		if(null != assignmentType){
			assignmentType = assignmentType.toUpperCase();
			List<String> assgnmntList = new ArrayList<String>();
			StringTokenizer assgnmntStr = new StringTokenizer(assignmentType, "|");
			while (assgnmntStr.hasMoreTokens()) {
				String assgnmntType = assgnmntStr.nextToken();
				assgnmntList.add(assgnmntType);
			}
			searchCriteria.setSelectedAssignmentTypes(assgnmntList);
		}
		//Mapping posting method
		String postingType = getRequest().getParameter("postingVals");
	
		if(null != postingType){
			postingType = postingType.toUpperCase();	
			List<String> postingTypeList = new ArrayList<String>();
			StringTokenizer postingStr = new StringTokenizer(postingType, "|");
			while (postingStr.hasMoreTokens()) {
				String pstngType = postingStr.nextToken();
				postingTypeList.add(pstngType);
			}
			searchCriteria.setSelectedPostingMethods(postingTypeList);
		}
		
				//R12_1
				//SL-20362
				//Mapping pending reschedule
						String pendingReschedule = getRequest().getParameter("pendingRescheduleVals");
					
						if(null != pendingReschedule){
							pendingReschedule = pendingReschedule.toUpperCase();	
							List<String> pendingRescheduleList = new ArrayList<String>();
							StringTokenizer pendingRescheduleStr = new StringTokenizer(pendingReschedule, "|");
							while (pendingRescheduleStr.hasMoreTokens()) {
								String pendingRechdType = pendingRescheduleStr.nextToken();
								pendingRescheduleList.add(pendingRechdType);
							}
							searchCriteria.setSelectedPendingReschedule(pendingRescheduleList);
						}
		//R12_1
		//SL-20554
		//Mapping closure method
			String closureMethod = getRequest().getParameter("closureMethodVals");
							
			if(null != closureMethod){
				closureMethod = closureMethod.toUpperCase();	
				List<String> closureMethodList = new ArrayList<String>();
				StringTokenizer closureMethodStr = new StringTokenizer(closureMethod, "|");
					while (closureMethodStr.hasMoreTokens()) {
						String closureMethodType = closureMethodStr.nextToken();
						closureMethodList.add(closureMethodType);
					}
				searchCriteria.setSelectedClosureMethod(closureMethodList);
			}
		
		//mapping Custom reference
		String customRefs = getRequest().getParameter("custRefVals");
		if (null != customRefs) {
			//Priority 18 code changes
			Map<String,ArrayList<String>> customerRefMap = new
				HashMap<String,ArrayList<String>>();
			ArrayList<String> tempList = null;
			StringTokenizer strTok = new StringTokenizer(customRefs, "|");
			int noOfCustRefs = strTok.countTokens();
			for (int i = 1; i <= noOfCustRefs; i++) {
				String keyValuePair = strTok.nextToken();
				StringTokenizer keyOrValue = new StringTokenizer(
						keyValuePair,"#");
				String key=keyOrValue.nextToken();
				//Priority 18
				//To map multiple values for same custom reference
				if (customerRefMap.containsKey(key)) {
				      tempList = customerRefMap.get(key);
				      if(null == tempList)
				      {
				    	  tempList = new ArrayList<String>();
				      }
				      tempList.add(keyOrValue.nextToken());  
				   } else {
				      tempList = new ArrayList<String>();
				      tempList.add(keyOrValue.nextToken());               
				   }
				
				customerRefMap.put(key, tempList);
			}
			searchCriteria.setSelectedCustomRefs(customerRefMap);
		}
		//mapping checkNumber
		String checkNumbers = getRequest().getParameter("checkNumberVals");
		if(null != checkNumbers){
			List<String> checkNumberList = new ArrayList<String>();
			StringTokenizer checkNumberStr = new StringTokenizer(checkNumbers, "|");
			while (checkNumberStr.hasMoreTokens()) {
				String checkNumber = checkNumberStr.nextToken();
				checkNumberList.add(checkNumber);
			}
			searchCriteria.setSelectedCheckNumbers(checkNumberList);
		}


		//mapping customerName
		String customerNames = getRequest().getParameter("customerNameVals");
		if(null != customerNames){
			List<String> customerNameList = new ArrayList<String>();
			StringTokenizer customerNameListStr = new StringTokenizer(customerNames, "|");
			while (customerNameListStr.hasMoreTokens()) {
				String customerName = customerNameListStr.nextToken();
				customerNameList.add(customerName);
			}
			searchCriteria.setSelectedCustomerNames(customerNameList);
		}


		//mapping phone
		String phones = getRequest().getParameter("phoneVals");
		if(null != phones){
			List<String> phoneList = new ArrayList<String>();
			StringTokenizer phoneStr = new StringTokenizer(phones, "|");
			while (phoneStr.hasMoreTokens()) {
			String phone = phoneStr.nextToken();
				phoneList.add(phone);
			}
			searchCriteria.setSelectedPhones(phoneList);
		}


		//mapping providerFirmId
		String providerFirmIds = getRequest().getParameter("providerFirmIdVals");
		if(null != providerFirmIds){
			List<String> providerFirmIdList = new ArrayList<String>();
			StringTokenizer providerFirmIdStr = new StringTokenizer(providerFirmIds, "|");
			while (providerFirmIdStr.hasMoreTokens()) {
			String providerFirmId = providerFirmIdStr.nextToken();
				providerFirmIdList.add(providerFirmId);
			}
			searchCriteria.setSelectedProviderFirmIds(providerFirmIdList);
		}

		//mapping serviceOrderId
		String serviceOrderIds = getRequest().getParameter("serviceOrderIdVals");
		if(null != serviceOrderIds){
			List<String> serviceOrderIdList = new ArrayList<String>();
			StringTokenizer serviceOrderIdStr = new StringTokenizer(serviceOrderIds, "|");
			while (serviceOrderIdStr.hasMoreTokens()) {
				String serviceOrderId = serviceOrderIdStr.nextToken();
				serviceOrderIdList.add(serviceOrderId);
			}
			searchCriteria.setSelectedServiceOrderIds(serviceOrderIdList);
		}


		//mapping serviceProId
		String serviceProIds = getRequest().getParameter("serviceProIdVals");
		if(null != serviceProIds){
			List<String> serviceProIdList = new ArrayList<String>();
			StringTokenizer serviceProIdStr = new StringTokenizer(serviceProIds, "|");
			while (serviceProIdStr.hasMoreTokens()) {
				String serviceProId = serviceProIdStr.nextToken();
				serviceProIdList.add(serviceProId);
			}
			searchCriteria.setSelectedServiceProIds(serviceProIdList);
		}


		//mapping serviceProName
		String serviceProNames = getRequest().getParameter("serviceProNameVals");
		if(null != serviceProNames){
			List<String> serviceProNameList = new ArrayList<String>();
			StringTokenizer serviceProNameStr = new StringTokenizer(serviceProNames, "|");
			while (serviceProNameStr.hasMoreTokens()) {
				String serviceProName = serviceProNameStr.nextToken();
				serviceProNameList.add(serviceProName);
			}
			searchCriteria.setSelectedServiceProNames(serviceProNameList);
		}
		//mapping zipCode
		String zipCodes = getRequest().getParameter("zipCodeVals");
		if(null != zipCodes){
			List<String> zipCodeList = new ArrayList<String>();
			StringTokenizer zipCodeStr = new StringTokenizer(zipCodes, "|");
			while (zipCodeStr.hasMoreTokens()) {
				String zipCode = zipCodeStr.nextToken();
				zipCodeList.add(zipCode);
			}
			searchCriteria.setSelectedZipCodes(zipCodeList);
		}
		//mapping startdate
		String startDate = getRequest().getParameter("startDate");
		if(null != startDate){
			List<String> startDateList = new ArrayList<String>();
			StringTokenizer startDateStr = new StringTokenizer(startDate, "|");
			while (startDateStr.hasMoreTokens()) {
			String startDateVal = startDateStr.nextToken();
				startDateList.add(startDateVal);
			}
			searchCriteria.setStartDateList(startDateList);
		}
		//mapping enddate
		String endDate = getRequest().getParameter("endDate");
		if(null != endDate){
			List<String> endDateList = new ArrayList<String>();
			StringTokenizer endDateStr = new StringTokenizer(endDate, "|");
			while (endDateStr.hasMoreTokens()) {
				String endDateVal= endDateStr.nextToken();
				endDateList.add(endDateVal);
			}
			searchCriteria.setEndDateList(endDateList);
		}

		//mapping Main Category
		String mainCategoryId = getRequest().getParameter("mainCatId");
		List<String> mainCatIdList = new ArrayList<String>();
		if (!StringUtils.isEmpty(mainCategoryId)) {
			StringTokenizer token_main_cat = new StringTokenizer(mainCategoryId, "|");
			while(token_main_cat.hasMoreTokens()){
				mainCatIdList.add(token_main_cat.nextToken());
			}			
		}
		searchCriteria.setSelectedMainCatIdList(mainCatIdList);

		//mapping Category and SubCategory
		List<String> catIdList = new ArrayList<String>();
		String categoryId = getRequest().getParameter("catAndSubCatId");
		if (!StringUtils.isEmpty(categoryId)) {
			StringTokenizer token_cat = new StringTokenizer(categoryId, "|");
			while(token_cat.hasMoreTokens()){
				catIdList.add(token_cat.nextToken());
			}			
		}
		searchCriteria.setSelectedCatAndSubCatIdList(catIdList);
		
		//mapping autoclose rules
		String autocloseRules = getRequest().getParameter("autocloseRuleVals");
		if(null != autocloseRules){
			List<String> autocloseRulesList = new ArrayList<String>();
			StringTokenizer autocloseRulesStr = new StringTokenizer(autocloseRules, "|");
			while (autocloseRulesStr.hasMoreTokens()) {
				String autocloseRule = autocloseRulesStr.nextToken();
				autocloseRulesList.add(autocloseRule);
			}
			if(autocloseRulesList.size()>0)
				searchCriteria.setSearchType("13");
			searchCriteria.setAutocloseRuleList(autocloseRulesList);
		}

		//mapping Status and SubStatus
		String statuses = getRequest().getParameter("statusVals");

		if (null != statuses) {
			Map<String,String> statusMap = new
				HashMap<String,String>();
			StringTokenizer strTok = new StringTokenizer(statuses, "#");
			int noOfStatus = strTok.countTokens();
			for (int i = 1; i <= noOfStatus; i++) {
				String keyValuePair = strTok.nextToken();
				StringTokenizer keyOrValue = new StringTokenizer(keyValuePair,":");
				if(null != keyOrValue && keyOrValue.countTokens() > 1){
					statusMap.put(keyOrValue.nextToken(), keyOrValue.nextToken());
				}else{
					statusMap.put(keyOrValue.nextToken(),"0");
				}
			}
			searchCriteria.setSelectedStatusSubStatus(statusMap);
		}	
		return searchCriteria;
	}
	public IFinanceManagerDelegate getFinanceManagerDelegate() {
		return financeManagerDelegate;
	}

	public void setFinanceManagerDelegate(IFinanceManagerDelegate financeManagerDelegate) {
		this.financeManagerDelegate = financeManagerDelegate;
	}

	public boolean isSLAdmin() {
		isSLAdmin = (Boolean) getSession().getAttribute(IS_ADMIN);
		return isSLAdmin;
	}

	public void setSLAdmin(boolean isSLAdmin) {
		this.isSLAdmin = isSLAdmin;
	}

	/**
	 * Get buyer ID for currently logged in user, via Common Criteria's SecurityContext's companyId.
	 * @return Buyer ID, or null.
	 */
	public Integer getContextBuyerId() {
		ServiceOrdersCriteria criteria;
		SecurityContext context;
		Integer rval= null;

		createCommonServiceOrderCriteria();

		if (null == (criteria= get_commonCriteria()))
			logger.warn("commonCriteria is NULL");
		else if (null == (context= criteria.getSecurityContext()))
			logger.warn("security context is NULL");
		else rval= context.getCompanyId();

		return rval;
	}
	private SearchCriteria mapSavedFilterToSearchCriteria(SearchFilterVO filterVO,SearchCriteria searchCriteria ){
		String criteria = filterVO.getTemplateValue();
		//removing the { and } from start and end of criteria		
		String formattedCriteria = criteria.substring(1, criteria.length()-1);
		criteria = formattedCriteria;
		
		StringTokenizer criteriaToken = new StringTokenizer(criteria, ",");
		int noOfTokens = criteriaToken.countTokens();
		searchCriteria.setSearchType("11");
		String token = "";
		for (int i = 1; i <= noOfTokens; i++) {		
			token = criteriaToken.nextToken();
			StringTokenizer eachCriteria = new StringTokenizer(token, "=");
			
			String criteriaName = eachCriteria.nextToken();
			if(StringUtils.isNotBlank(criteriaName)){
				criteriaName = criteriaName.trim();
				if(eachCriteria.hasMoreTokens()){
					String criteriaVal = eachCriteria.nextToken();
					if(SEARCH_FILTER_STATE_LIST.equals(criteriaName)){
						List<String> selectedStates = new ArrayList<String>();
						StringTokenizer stateVals = new StringTokenizer(criteriaVal, "|");
						while(stateVals.hasMoreTokens()){
							selectedStates.add(stateVals.nextToken());
						}
						searchCriteria.setSelectedStates(selectedStates);
					}else if(SEARCH_FILTER_SKILL_LIST.equals(criteriaName)){
						List<String> selectedSkills = new ArrayList<String>();
						StringTokenizer skillVals = new StringTokenizer(criteriaVal, "|");
						while(skillVals.hasMoreTokens()){
							selectedSkills.add(skillVals.nextToken());
						}
						searchCriteria.setSelectedSkills(selectedSkills);
					}else if(SEARCH_FILTER_MARKET_LIST.equals(criteriaName)){
						List<Integer> selectedMarkets = new ArrayList<Integer>();
						StringTokenizer marketVals = new StringTokenizer(criteriaVal, "|");
						while(marketVals.hasMoreTokens()){
							selectedMarkets.add(Integer.parseInt(marketVals.nextToken()));
						}
						searchCriteria.setSelectedMarkets(selectedMarkets);
					}
					else if(SEARCH_FILTER_CHECKNUMBER_LIST.equals(criteriaName)){
						List<String> selectedCheckNum = new ArrayList<String>();
						StringTokenizer checkNumVals = new StringTokenizer(criteriaVal, "|");
						while(checkNumVals.hasMoreTokens()){
							selectedCheckNum.add(checkNumVals.nextToken());
						}
						searchCriteria.setSelectedCheckNumbers(selectedCheckNum);
					}else if(SEARCH_FILTER_CUSTOMER_NAME_LIST.equals(criteriaName)){
						List<String> selectedCustName = new ArrayList<String>();
						StringTokenizer custNameVals = new StringTokenizer(criteriaVal, "|");
						while(custNameVals.hasMoreTokens()){
							selectedCustName.add(custNameVals.nextToken());
						}
						searchCriteria.setSelectedCustomerNames(selectedCustName);
					}else if(SEARCH_FILTER_PHONE_LIST.equals(criteriaName)){
						List<String> selectedPhones = new ArrayList<String>();
						StringTokenizer phoneVals = new StringTokenizer(criteriaVal, "|");
						while(phoneVals.hasMoreTokens()){
							selectedPhones.add(phoneVals.nextToken());
						}
						searchCriteria.setSelectedPhones(selectedPhones);
					}else if(SEARCH_FILTER_PROVIDER_FIRM_ID_LIST.equals(criteriaName)){
						List<String> selectedProFirmIds = new ArrayList<String>();
						StringTokenizer proFirmIdVals = new StringTokenizer(criteriaVal, "|");
						while(proFirmIdVals.hasMoreTokens()){
							selectedProFirmIds.add(proFirmIdVals.nextToken());
						}
						searchCriteria.setSelectedProviderFirmIds(selectedProFirmIds);
					}else if(SEARCH_FILTER_SO_ID_LIST.equals(criteriaName)){
						List<String> selectedSoIds = new ArrayList<String>();
						StringTokenizer soIdVals = new StringTokenizer(criteriaVal, "|");
						while(soIdVals.hasMoreTokens()){
							selectedSoIds.add(soIdVals.nextToken());
						}
						searchCriteria.setSelectedServiceOrderIds(selectedSoIds);
					}else if(SEARCH_FILTER_SERVICEPRO_ID_LIST.equals(criteriaName)){
						List<String> selectedServiceProIds = new ArrayList<String>();
						StringTokenizer serviceProIdVals = new StringTokenizer(criteriaVal, "|");
						while(serviceProIdVals.hasMoreTokens()){
							selectedServiceProIds.add(serviceProIdVals.nextToken());
						}
						searchCriteria.setSelectedServiceProIds(selectedServiceProIds);
					}else if(SEARCH_FILTER_SERVICEPRO_NAME_LIST.equals(criteriaName)){
						List<String> selectedServiceProNames = new ArrayList<String>();
						StringTokenizer serviceProNameVals = new StringTokenizer(criteriaVal, "|");
						while(serviceProNameVals.hasMoreTokens()){
							selectedServiceProNames.add(serviceProNameVals.nextToken());
						}
						searchCriteria.setSelectedServiceProNames(selectedServiceProNames);
					}else if(SEARCH_FILTER_ZIP_CODE_LIST.equals(criteriaName)){
						List<String> selectedZipCodes = new ArrayList<String>();
						StringTokenizer zipVals = new StringTokenizer(criteriaVal, "|");
						while(zipVals.hasMoreTokens()){
							selectedZipCodes.add(zipVals.nextToken());
						}
						searchCriteria.setSelectedZipCodes(selectedZipCodes);
					}else if(SEARCH_FILTER_START_DATE_LIST.equals(criteriaName)){
						List<String> selectedStartDates = new ArrayList<String>();
						StringTokenizer startDateVals = new StringTokenizer(criteriaVal, "|");
						while(startDateVals.hasMoreTokens()){
							selectedStartDates.add(startDateVals.nextToken());
						}
						searchCriteria.setStartDateList(selectedStartDates);
					}else if(SEARCH_FILTER_END_DATE_LIST.equals(criteriaName)){
						List<String> selectedEndDates = new ArrayList<String>();
						StringTokenizer endDateVals = new StringTokenizer(criteriaVal, "|");
						while(endDateVals.hasMoreTokens()){
							selectedEndDates.add(endDateVals.nextToken());
						}
						searchCriteria.setEndDateList(selectedEndDates);
					}else if(SEARCH_FILTER_MAIN_CATEGORY_LIST.equals(criteriaName)){
						List<String> selectedMainCats = new ArrayList<String>();
						StringTokenizer mainCatVals = new StringTokenizer(criteriaVal, "|");
						while(mainCatVals.hasMoreTokens()){
							selectedMainCats.add(mainCatVals.nextToken());
						}
						searchCriteria.setSelectedMainCatIdList(selectedMainCats);
					}else if(SEARCH_FILTER_CATEGORY_LIST.equals(criteriaName)){
						List<String> selectedCatIds = new ArrayList<String>();
						StringTokenizer catIdVals = new StringTokenizer(criteriaVal, "|");
						while(catIdVals.hasMoreTokens()){
							selectedCatIds.add(catIdVals.nextToken());
						}
						searchCriteria.setSelectedCatAndSubCatIdList(selectedCatIds);
					}
					else if(SEARCH_FILTER_STATUS_LIST.equals(criteriaName)){
						//criteriaVal.replace("{", "");
						//criteriaVal.replace("}", "");
						StringTokenizer statusVals = new StringTokenizer(criteriaVal, "#");
						Map<String,String> statusMap = new HashMap<String,String>();
						int noOfStatus = statusVals.countTokens();
						for (int k = 1; k <= noOfStatus; k++) {
							String keyValuePair = statusVals.nextToken();
							StringTokenizer keyOrValue = new StringTokenizer(keyValuePair,":");
							if(null != keyOrValue && keyOrValue.countTokens() > 1){
								statusMap.put(keyOrValue.nextToken(), keyOrValue.nextToken());
							}else{
								statusMap.put(keyOrValue.nextToken(),"0"); 
							}				
						}
						searchCriteria.setSelectedStatusSubStatus(statusMap);
					}else if(SEARCH_FILTER_CUST_REF_LIST.equals(criteriaName)){
						criteriaVal.replace("{", "");
						criteriaVal.replace("}", "");
						StringTokenizer custRefVals = new StringTokenizer(criteriaVal, "|");
						//Priority 18 code changes
						Map<String,ArrayList<String>> custRefMap = new HashMap<String,ArrayList<String>>();
						ArrayList<String> tempList = null;
						int noOfStatus = custRefVals.countTokens();
						for (int j = 1; j <= noOfStatus; j++) {
							String keyValuePair = custRefVals.nextToken();
							StringTokenizer keyOrValue = new StringTokenizer(keyValuePair,"#");
							if(null != keyOrValue && keyOrValue.countTokens() > 1){
								//Priority 18
								//mapping multiple value for same custom reference for Search Filters.
								String key=keyOrValue.nextToken();
								if (custRefMap.containsKey(key)) {
								      tempList = custRefMap.get(key);
								      if(tempList == null)
								      {
								    	  tempList = new ArrayList<String>();
								      }
								      tempList.add(keyOrValue.nextToken());  
								   } else {
								      tempList = new ArrayList<String>();
								      tempList.add(keyOrValue.nextToken());               
								   }
								
								custRefMap.put(key, tempList);
							}else{
								custRefMap.put(keyOrValue.nextToken(),new ArrayList<String>(){{add("0");}});
							}				
						}
						searchCriteria.setSelectedCustomRefs(custRefMap);
					}else if(SEARCH_FILTER_AUTOCLOSE_RULES_LIST.equals(criteriaName)){
						List<String> selectedAutocloseRules = new ArrayList<String>();
						StringTokenizer AutocloseRuleVals = new StringTokenizer(criteriaVal, "|");
						while(AutocloseRuleVals.hasMoreTokens()){
							selectedAutocloseRules.add(AutocloseRuleVals.nextToken());
						}
						searchCriteria.setAutocloseRuleList(selectedAutocloseRules);
						if(selectedAutocloseRules.size()>0)
							searchCriteria.setSearchType("13");
					}
					
					
					
				}
			}
		}
		
		return searchCriteria;
	}
	
	/**
	 * @param activityname
	 */
	@SuppressWarnings("unchecked")
	protected Boolean isPermissionAvaialble(String activityname) {
		if(   getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT)!= null){
			SecurityContext sc  = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
			return Boolean.valueOf(ActivityMapper.canDoAction(activityname+"-"+sc.getRoleId(), sc));
        }
		return Boolean.FALSE;
	}

	
	public void setCurrentSOStatusCodeInSession(Integer statusCode)
	{
		getSession().setAttribute(THE_SERVICE_ORDER_STATUS_CODE, statusCode);
	}
	
	public void setCurrentSOStatusCodeInRequest(Integer statusCode)
	{
		setAttribute(THE_SERVICE_ORDER_STATUS_CODE, statusCode);
	}

	public String getSecurityToken() {
		return (String) getSession().getAttribute(SOConstants.SECURITY_TOKEN_SESSION_KEY);
	}

	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	//R12_1
	//SL-20379 : The following methods are added for resolving SOM session issue
	protected void setDefaultCriteriaForSOM(String tab) {
		CriteriaHandlerFacility facility = CriteriaHandlerFacility.getInstance();
		PagingCriteria pc = new PagingCriteria();
		SortCriteria sc = new SortCriteria();
		FilterCriteria fc = new FilterCriteria(null, null,null,null, null);
		OrderCriteria oc = new OrderCriteria();
		SearchCriteria searchCriteria = new SearchCriteria();
		SearchWordsCriteria searchWordsCriteria = new SearchWordsCriteria();
		DisplayTabCriteria dtc = new DisplayTabCriteria();
		facility.initFacility(fc, sc, pc, oc, searchCriteria,searchWordsCriteria,dtc,tab);

	}
	
	protected FilterCriteria handleFilterCriteria(CriteriaHandlerFacility facility, String tab) {
		FilterCriteria filterCriteria = null;

		if (facility == null)
			return null;
		if(StringUtils.isNotBlank(tab)){
			filterCriteria = facility.getFilterCriteria(tab);
		}else{
			filterCriteria = facility.getFilterCriteria();
		}
		if (filterCriteria == null)
			return null;
		// Set Status in filterCriteria
		Integer statusInt = null;
		String statusStr = getRequest().getParameter("status");
		if(StringUtils.isBlank(statusStr) && null!=filterCriteria.getStatus())
		{
			statusStr = filterCriteria.getStatus()[0].toString();
		}
		
		//SL-20379
		//if(getSession().getAttribute("tabStatus")!=null){
		if(getAttribute("tabStatus")!=null){
			//SL-20379
			//statusStr = getSession().getAttribute("tabStatus").toString();
			statusStr = getAttribute("tabStatus").toString();
		}

		if(StringUtils.isNotBlank(statusStr) && !statusStr.equalsIgnoreCase("null"))
		{
			if (statusStr.equalsIgnoreCase("Today"))
			{
				statusInt = new Integer(OrderConstants.TODAY_STATUS);
			}
			else if (statusStr.equalsIgnoreCase("Inactive"))
			{
				statusInt = new Integer(OrderConstants.INACTIVE_STATUS);
			}
			else if (statusStr.equalsIgnoreCase("Posted")
					|| statusStr.equalsIgnoreCase("Received"))
			{
				statusInt = new Integer(OrderConstants.ROUTED_STATUS);
			}
			else
			{
				statusInt = new Integer(statusStr);
			}
			Integer iArray[] = new Integer[1];
			iArray[0] = statusInt;
			filterCriteria.setStatus(iArray);
			getRequest().setAttribute(SOConstants.SO_STATUS, statusStr);
		}
		else
		{
			statusInt = getTabStatus();
		}
		Integer iArray[] = new Integer[1];
		iArray[0] = statusInt;
		filterCriteria.setStatus(iArray);

		if((statusInt != null && OrderConstants.ROUTED_STATUS == statusInt.intValue()
				|| OrderConstants.SEARCH_STATUS == statusInt.intValue()) 
				&&  get_commonCriteria().getRoleId() == OrderConstants.PROVIDER_ROLEID) {
			
			boolean canManageSO = ((Boolean) getSession().getAttribute("canManageSO")).booleanValue();
			boolean primaryInd = ((Boolean) getSession().getAttribute("isPrimaryInd")).booleanValue();
			boolean dispatchInd = ((Boolean) getSession().getAttribute("dispatchInd")).booleanValue();
			//Checking whether the user has the rights to manage SO[4 is the Activity Id for the Activity-Manage Service Orders]
			// and not an admin, then search for specific resourceId only
			if(canManageSO && !primaryInd && !dispatchInd){
				filterCriteria.setManageSOFlag(canManageSO);
				filterCriteria.setResourceId(_commonCriteria.getVendBuyerResId().toString());
			}
		}

		// Set Substatus in filterCriteria
		String subStatus = getRequest().getParameter("subStatus");
		String soSubStatus = getRequest().getParameter(SOConstants.SO_SUBSTATUS);
		if(StringUtils.isNotBlank(subStatus)){
			if ((subStatus.equals("0")) || (subStatus.equals("null"))) {
					if(StringUtils.isNotBlank(soSubStatus)){
						filterCriteria.setSubStatus(soSubStatus);
					}else{
						filterCriteria.setSubStatus(null);
					}
				}
				else
				{
					filterCriteria.setSubStatus(getRequest().getParameter("subStatus"));
				}
		}else{
			if( (null!=soSubStatus) && (StringUtils.isNotBlank(soSubStatus))&& !(soSubStatus.equals("null"))){
				filterCriteria.setSubStatus(soSubStatus);
			}
		}
		getRequest().setAttribute(SOConstants.SO_SUBSTATUS, filterCriteria.getSubStatus());
		
		// Set Price Model in filterCriteria
		String priceModel = getRequest().getParameter(SOConstants.PRICE_MODEL);
		if(StringUtils.isNotBlank(priceModel)){
			if ((priceModel.equals("0")) || (priceModel.equals("null") || (priceModel.equals("all")))) {
					filterCriteria.setPriceModel(null);
				}
				else
				{
					filterCriteria.setPriceModel(getRequest().getParameter(SOConstants.PRICE_MODEL));
				}
		}
		

		String requestedTab = getRequest().getParameter("tab");

		if (!StringUtils.isEmpty(filterCriteria.getPriceModel())
				&& !StringUtils.equals(filterCriteria.getPriceModel(),
						OrderConstants.NULL_STRING)) {
				if (requestedTab != null ){
					getSession().setAttribute(SESSION_PRICE_MODEL + requestedTab,
							filterCriteria.getPriceModel());
				}else
				{
					getSession().setAttribute(SESSION_PRICE_MODEL,
							filterCriteria.getPriceModel());
				}
		}
		
		// SL-11776 - It's not clear if this code should be moved up to allow the Price Model to be set in the session or not.
		// The code seems to be running ok without doing that
		if(OrderConstants.BID_REQUESTS_STRING.equals(requestedTab)) {
			filterCriteria.setPriceModel(Constants.PriceModel.ZERO_PRICE_BID);
		}

		getRequest().setAttribute(SOConstants.PRICE_MODEL, filterCriteria.getPriceModel());
		
		/************** Set the Service Pro Name in filter Criteria *******************/
		String serviceProNameStr = getRequest().getParameter("serviceProName");
		if((serviceProNameStr != null && serviceProNameStr.equalsIgnoreCase("null")) || (serviceProNameStr != null && serviceProNameStr.equalsIgnoreCase("undefined"))){
			serviceProNameStr=null;
		}
		if(StringUtils.isBlank(serviceProNameStr) && null!=filterCriteria.getServiceProName())
		{
			serviceProNameStr = filterCriteria.getServiceProName().toString();
		}

		if(StringUtils.isNotBlank(serviceProNameStr))
		{
			filterCriteria.setServiceProName(serviceProNameStr);
			getRequest().setAttribute(SOConstants.SO_SERVICEPRONAME, serviceProNameStr);
		}

		String buyerRoleIdStr = getRequest().getParameter("buyerRoleId");
		if(StringUtils.isNotEmpty(buyerRoleIdStr) && StringUtils.isNumeric(buyerRoleIdStr))
		{
			Integer buyerRoleId = Integer.parseInt(buyerRoleIdStr);
			filterCriteria.setBuyerRoleId(buyerRoleId);
			getRequest().setAttribute(SOConstants.SO_ROLEID, buyerRoleId);
		}

		/************** Set the Market Name in filter Criteria ***********************/
		String marketNameStr = getRequest().getParameter("marketName");
		if((marketNameStr != null && marketNameStr.equalsIgnoreCase("null")) || (marketNameStr != null && marketNameStr.equalsIgnoreCase("undefined"))) {
			marketNameStr=null;
		}
		if(StringUtils.isBlank(marketNameStr) && null!=filterCriteria.getMarketName())
		{
			marketNameStr = filterCriteria.getMarketName().toString();
		}

		if(StringUtils.isNotBlank(marketNameStr))
		{
			filterCriteria.setMarketName(marketNameStr);
			getRequest().setAttribute(SOConstants.SO_MARKETNAME, marketNameStr);
		}
		// set filterId in filterCriteria
		String str = (String) getRequest().getAttribute("pbFilterId");
		filterCriteria.setPbfilterId(StringUtils.isBlank(str) ? null : Integer.valueOf(str));

		str = (String) getRequest().getAttribute("pbFilterOpt");
		filterCriteria.setPbfilterOpt(StringUtils.isBlank(str) ? null : str);

		str=(String) getRequest().getAttribute("wfmSodFlag");
		filterCriteria.setWfm_sodFlag(StringUtils.isBlank(str) ? null : str);
		
		str=(String) getRequest().getAttribute("soId");
		filterCriteria.setSoId(StringUtils.isBlank(str) ? null : str);

		return filterCriteria;
	}
	
	public SortCriteria handleSortCriteria(CriteriaHandlerFacility facility, String tab) {
		SortCriteria sortCriteria = null;
		String requestedTab = getRequest().getParameter("tab");

		if (null != facility) {
			if(StringUtils.isNotBlank(tab)){
				sortCriteria = facility.getSortingCriteria(tab);
			}else{
				sortCriteria = facility.getSortingCriteria();
			}
			if (null != sortCriteria) {
				sortCriteria.setSortColumnName(getRequest().getParameter(
						SESSION_SORT_COLUMN));
				sortCriteria.setSortOrder(getRequest().getParameter(
						SESSION_SORT_ORDER));


		// set sort data into session for use by paging component
		if (!StringUtils.isEmpty(sortCriteria.getSortColumnName())
				&& !StringUtils.equals(sortCriteria.getSortColumnName(),
						OrderConstants.NULL_STRING)
				&& !StringUtils.isEmpty(sortCriteria.getSortOrder())
				&& !StringUtils.equals(sortCriteria.getSortOrder(),
						OrderConstants.NULL_STRING)) {
				if (requestedTab != null ){
					getSession().setAttribute(SESSION_SORT_COLUMN + requestedTab,
							sortCriteria.getSortColumnName());
					getSession().setAttribute(SESSION_SORT_ORDER + requestedTab,
							sortCriteria.getSortOrder());
				}else
				{
					getSession().setAttribute(SESSION_SORT_COLUMN,
							sortCriteria.getSortColumnName());
					getSession().setAttribute(SESSION_SORT_ORDER,
							sortCriteria.getSortOrder());
				}
		} else {
			if (requestedTab != null ){
				sortCriteria.setSortColumnName((String) getSession().getAttribute(
						SESSION_SORT_COLUMN + requestedTab));
				sortCriteria.setSortOrder((String) getSession().getAttribute(
						SESSION_SORT_ORDER + requestedTab));
			}else
			{
				sortCriteria.setSortColumnName((String) getSession().getAttribute(
						SESSION_SORT_COLUMN));
				sortCriteria.setSortOrder((String) getSession().getAttribute(
						SESSION_SORT_ORDER));
			}
		  }
		}
	  }
		return sortCriteria;
	}

	public PagingCriteria handlePagingCriteria(CriteriaHandlerFacility facility, String tab) {
		PagingCriteria pagingCriteria = null;
		if(StringUtils.isNotBlank(tab)){
			pagingCriteria = facility.getPagingCriteria(tab);
		}else{
			pagingCriteria = facility.getPagingCriteria();
		}
		if (facility != null) {
			if (pagingCriteria != null) {
				if (getRequest().getParameter("startIndex") != null) {
					if (!getRequest().getParameter("startIndex").equals("")) {
						String l = getRequest().getParameter("startIndex");
						pagingCriteria.setStartIndex(new Integer(getRequest().getParameter("startIndex")).intValue());

					}
				}
				if (getRequest().getParameter("endIndex") != null) {
					if (!getRequest().getParameter("endIndex").equals("")) {
						String k = getRequest().getParameter("endIndex");
						pagingCriteria.setEndIndex(new Integer(getRequest().getParameter("endIndex")).intValue());
					}
				}
				if (getRequest().getParameter("pageSize") != null) {
					if (!getRequest().getParameter("pageSize").equals("")) {
						String k = getRequest().getParameter("pageSize");
						pagingCriteria.setPageSize(new Integer(getRequest().getParameter("pageSize")).intValue());
					}
				}

			}
		}
		return pagingCriteria;
	}
	
	public void resetStartingIndexPagingCriteria(CriteriaHandlerFacility facility, String tab) {
		if (facility != null) {
			PagingCriteria pagingCriteria  = facility.getPagingCriteria(tab);
			if (pagingCriteria != null) {
				pagingCriteria.setStartIndex(new Integer(1));
			}
		}
	}
	
	protected SearchCriteria _handleSearchCriteria(CriteriaHandlerFacility facility, String tab) {
		SearchCriteria searchCriteria = null;
		String type = null;

		if (null != facility) {
			searchCriteria = facility.getSearchingCriteria(tab);
			if (null != searchCriteria) {
				/*type = getRequest().getParameter("searchType");
				if (!StringUtils.isEmpty(type)) {
					searchCriteria.setSearchType(type);
				}
				String searchValue = getRequest().getParameter("searchValue");
				if(searchValue != null)
					searchValue.trim();
				searchCriteria.setSearchValue(searchValue);
				*/
				//Code for Advance Search Filter
				String selectedFilter = getRequest().getParameter("selectedFilterName");
				if(null != selectedFilter && !StringUtils.isEmpty(selectedFilter)){
					SearchFilterVO searchFilterVO = new SearchFilterVO();
					searchFilterVO.setFilterName(selectedFilter);
					SecurityContext sContext = get_commonCriteria().getSecurityContext();
					searchFilterVO.setEntityId(sContext.getCompanyId());
					searchFilterVO = soMonitorDelegate.getSelectedSearchFilter(searchFilterVO);
					searchCriteria.reset();
					searchCriteria.setFilterNameSearch(true);
					searchCriteria.setFilterTemplate(searchFilterVO.getTemplateHtmlContent());
					searchCriteria = mapSavedFilterToSearchCriteria(searchFilterVO, searchCriteria);
				}else{				
					searchCriteria.setFilterNameSearch(false);
					searchCriteria = loadAdvanceSearchCriteria(searchCriteria);
				}	
			}
		}
		return searchCriteria;
	}

}
