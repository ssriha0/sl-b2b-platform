package com.newco.marketplace.web.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.security.SecurityBusinessBean;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultForAdminVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.AdminBuyerSearchDelegate;
import com.newco.marketplace.web.delegates.AdminProviderSearchDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.adminlogging.IAdminLoggingDelegate;
import com.newco.marketplace.web.dto.AdminSearchFormDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsAllDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsDTO;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.adminlogging.AdminLoggingDto;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.53 $ $Author: cgarc03 $ $Date: 2008/06/04 20:22:05 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class AdminSearchAction extends SLBaseAction implements Preparable, OrderConstants, ModelDriven<AdminSearchFormDTO>
{

	private static final long serialVersionUID = 5734498034169109557L;
	
	private Logger logger = Logger.getLogger(AdminSearchAction.class);
	
	private AdminSearchFormDTO adminSearchFormDTO = new AdminSearchFormDTO();
	protected AdminProviderSearchDelegate providerSearchDelegate;
	protected AdminBuyerSearchDelegate buyerSearchDelegate;

	protected ILookupDelegate lookupManager;
	private HttpSession session;
	private SecurityContext securityContext;
	private AdminLoggingDto adminLoggingDto;
	private IAdminLoggingDelegate adminLoggingDelegate;
	private SecurityBusinessBean securityBean;
	private Map<Integer, String> searchByMap = new HashMap<Integer, String>();

	public AdminSearchAction(AdminProviderSearchDelegate providerSearchDelegate,
			 IAdminLoggingDelegate adminLoggingDelegate)
	{
		this.providerSearchDelegate = providerSearchDelegate;
		this.adminLoggingDelegate = adminLoggingDelegate;

	}

	public AdminSearchAction(AdminProviderSearchDelegate providerSearchDelegate,
			 IAdminLoggingDelegate adminLoggingDelegate, AdminBuyerSearchDelegate buyerSearchDelegate)
	{
		this.providerSearchDelegate = providerSearchDelegate;
		this.adminLoggingDelegate = adminLoggingDelegate;
		this.buyerSearchDelegate = buyerSearchDelegate;

	}
	public void prepare() throws Exception
	{
		//set the security context to the sladmin if it was mapped to buyer or provider

		createCommonServiceOrderCriteria();

		initLookupDropdowns();
		initRadioSelections();
		reloadSearchCriteria();
		setDefaultCriteria();
		}

	public String execute() {
		if (ActionContext.getContext().getSession() != null)
		{
			if (ActionContext.getContext().getSession().get("isFromPA") != null)
			{
				ActionContext.getContext().getSession().put("isFromPA","false");
			}
		}
		
		return "search";
	}
	private void initRadioSelections()
	{
		searchByMap.put(new Integer(1), "Search by Company");
		searchByMap.put(new Integer(0), "Search by Skill");

	}

	private void reloadSearchCriteria()
	{
		AdminSearchFormDTO dto = (AdminSearchFormDTO)getSession().getAttribute("adminCompanySearch");
		if(dto != null)
		{
			adminSearchFormDTO = dto;
		}
	}

	private void initLookupDropdowns()
	{
		if (lookupManager != null)
		{
			adminSearchFormDTO = getModel();

			adminSearchFormDTO.setMarketList(lookupManager.getMarkets());
			adminSearchFormDTO.setDistrictList(lookupManager.getDistricts());
			adminSearchFormDTO.setRegionList(lookupManager.getRegions());
			adminSearchFormDTO.setProviderFirmStatusList(lookupManager.getProviderFirmStatuses());
			adminSearchFormDTO.setPrimarySkillList(lookupManager.getPrimarySkills());
			adminSearchFormDTO.setAuditableItemsList(lookupManager.getAuditableItems());
			adminSearchFormDTO.setBackgroundStatusCheckList(lookupManager.getBackgroundStatuses());
			adminSearchFormDTO.setSelectProviderNetworkList(lookupManager.getProviderNetworks());
		}
	}
	public String iframeResults() throws Exception
	{


		if (org.apache.commons.lang.StringUtils.isNotEmpty((String)getSession().getAttribute("providersLoaded"))){
			getRequest().setAttribute("providersList", getSession().getAttribute("providersList"));
			getRequest().setAttribute("searchBySelection", getSession().getAttribute("searchBySelection"));

			getSession().removeAttribute("providersList");
			getSession().removeAttribute("searchBySelection");
			getSession().removeAttribute("providersLoaded");
		}


		return "iframe_results";
	}


	public void initBuyerResults()
	{
		if(hasBuyerSearchCriteria()){
			CriteriaHandlerFacility criteriaHandler = CriteriaHandlerFacility.getInstance();
			SortCriteria sortCriteria = handleSortCriteria(criteriaHandler);
			CriteriaMap newMap = new CriteriaMap();
		    newMap.put(SORT_CRITERIA_KEY, sortCriteria);
		    getSession().setAttribute("adminCompanySearch", getModel());
			AdminSearchResultsAllDTO results = buyerSearchDelegate.getSearchResultsForAdmin(getModel(), newMap);
			if (results != null)
			{

				//I wanted to reuse what is existing as we are going to display stuff at the same location as Provider
				List<AdminSearchResultsDTO> resultList = new ArrayList<AdminSearchResultsDTO>();
				resultList = results.getListOfProviders();
				modifyProvidersForDisplay(resultList);
				getRequest().setAttribute("resultsText", "Search Results: " + resultList.size() + " members");
				getSession().setAttribute("providersList", resultList);
				getSession().setAttribute("providersLoaded","1");
				adminSearchFormDTO.setBuyerProviderSelection("0");

			}
		}

	}

	public void initProvidersResults()
	{
		List<String> radioList = new ArrayList<String>();
		radioList.add("Buyer");
		radioList.add("Provider");
		setAttribute("radioList", radioList);
		if (adminSearchFormDTO.getBuyerProviderSelection() != null
				&& adminSearchFormDTO.getBuyerProviderSelection().trim()
						.equals("1")) {

			if (hasSearchCriteria()) {
				// Save all search criteria in session
				getSession().setAttribute("adminCompanySearch", getModel());
				adminSearchFormDTO.setBuyerProviderSelection("1");

				CriteriaHandlerFacility criteriaHandler = CriteriaHandlerFacility
						.getInstance();
				SortCriteria sortCriteria = handleSortCriteria(criteriaHandler);
				CriteriaMap newMap = new CriteriaMap();
				newMap.put(SORT_CRITERIA_KEY, sortCriteria);

				if (providerSearchDelegate != null) {
					/*
					 * This is being commented out as we are not handling buyer
					 * searches yet if
					 * (adminSearchFormDTO.getBuyerProviderSelection() != null &&
					 * adminSearchFormDTO.getBuyerProviderSelection().trim().equals("1")) {
					 */
					AdminSearchResultsAllDTO results = null;
					if (adminSearchFormDTO.getSearchBySelection()
							.equalsIgnoreCase("1")) {
						// Call Skill search if second skill search is selected
						// else default search is company search
						results = providerSearchDelegate
								.getProviderSearchResultsBySkill(getModel(),
										newMap);
					} else {
						results = providerSearchDelegate
								.getProviderSearchResults(getModel(), newMap);
					}

					if (results != null) {
						List<AdminSearchResultsDTO> resultList = new ArrayList<AdminSearchResultsDTO>();
						resultList = results.getListOfProviders();

						modifyProvidersForDisplay(resultList);

						getRequest().setAttribute(
								"resultsText",
								"Search Results: " + resultList.size()
										+ " members");
						getSession().setAttribute("providersList", resultList);
						getSession().setAttribute("providersLoaded", "1");
					}
					getSession().setAttribute("searchBySelection",
							adminSearchFormDTO.getSearchBySelection());
					/*
					 * This is being commented out as we are not handling buyer
					 * searches yet } else { AdminSearchResultsAllDTO results =
					 * providerSearchDelegate.getSearchResultsForAdmin(getModel(),
					 * newMap); if (results != null) { List<AdminSearchResultsDTO>
					 * resultList = new ArrayList<AdminSearchResultsDTO>();
					 * resultList = results.getListOfProviders();
					 * setAttribute("providersList", resultList);
					 * setAttribute("resultsText", "Search Results: " +
					 * resultList.size() + " members"); } }
					 */
				}
			}
		}
		else {
			initBuyerResults();
		}
	}

	public String search() throws Exception
	{
		String zip = adminSearchFormDTO.getZipPart1();
		String state = adminSearchFormDTO.getState1();
		List<IError> errorList = new ArrayList<IError>();
		adminSearchFormDTO.setErrors(errorList);
		int zipCheck = LocationUtils.checkIfZipAndStateValid(zip, state);
		switch (zipCheck) {
			case Constants.LocationConstants.ZIP_NOT_VALID:
				adminSearchFormDTO.getErrors().add(new SOWError("Zip", "Zip Not Valid", OrderConstants.FM_ERROR));
				break;
			case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
				adminSearchFormDTO.getErrors().add(new SOWError("Zip", "Zip State No Match", OrderConstants.FM_ERROR));
				break;
		}

		initProvidersResults();

		return "search";
	}

	public String searchBuyer() throws Exception {
		List<IError> errorList = new ArrayList<IError>();
		adminSearchFormDTO.setErrors(errorList);

		initBuyerResults();

		return "search";
	}

	public String clear() throws Exception
	{
		adminSearchFormDTO = getModel();
		setAttribute("searchBySelection",adminSearchFormDTO.getSearchBySelection());
		setAttribute("buyerProviderSelection",adminSearchFormDTO.getBuyerProviderSelection());
		getSession().setAttribute("providersLoaded","0");
		adminSearchFormDTO.clear();
		getSession().setAttribute("sortColumnName",null);
		getSession().setAttribute("sortOrder",null);
		return "search";
	}

	private boolean hasBuyerSearchCriteria()
	{
		adminSearchFormDTO = getModel();
		if (adminSearchFormDTO.getBuyerProviderSelection().equalsIgnoreCase("0")){
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBuyerId()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBuyer_city()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBuyer_email()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBuyer_orderNumber()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBuyer_phone()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBuyer_state()) == false )
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBuyer_zip()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBuyer_username()) == false)
				return true;
		}
		return false;

	}

	private boolean hasSearchCriteria()
	{
		adminSearchFormDTO = getModel();
		if (adminSearchFormDTO.getSearchBySelection().equalsIgnoreCase("0")){
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBusinessName()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getProviderFirmStatus()) == false && !adminSearchFormDTO.getProviderFirmStatus().equals("-1"))
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getCity()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getCompanyId()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getEmail()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getOrderNumber()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getPhone()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getState1()) == false && !adminSearchFormDTO.getState1().equals("-1"))
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getZipPart1()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getUserId()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getUsername()) == false)
				return true;
		}else
		{
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getMarket()) == false && !adminSearchFormDTO.getMarket().equals("-1"))
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getState2()) == false && !adminSearchFormDTO.getState2().equals("-1"))
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getZipPart2()) == false)
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getDistrict()) == false && !adminSearchFormDTO.getDistrict().equals("-1"))
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getRegion()) == false && !adminSearchFormDTO.getRegion().equals("-1"))
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getPrimarySkill()) == false && !adminSearchFormDTO.getPrimarySkill().equals("-1"))
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getAuditableItems()) == false && !adminSearchFormDTO.getAuditableItems().equals("-1"))
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getBackgroundCheckStatus()) == false && !adminSearchFormDTO.getBackgroundCheckStatus().equals("-1"))
				return true;
			if (SLStringUtils.isNullOrEmpty(adminSearchFormDTO.getSelectProviderNetwork()) == false && !adminSearchFormDTO.getSelectProviderNetwork().equals("-1"))
				return true;
		}
		return false;
	}

	public String navigateToProviderPage()
	{
		setContextDetails();
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();

		// get primary user for company
		ProviderResultForAdminVO adminVO = providerSearchDelegate.getProviderAdmin(adminSearchFormDTO.getHidVendorId());


		securityContext.setAdminRoleId(securityContext.getRoleId());
		securityContext.setAdminResId(securityContext.getVendBuyerResId());
		securityContext.setClientId(null);
		securityContext.setCompanyId(adminSearchFormDTO.getHidVendorId());
		securityContext.setVendBuyerResId(adminVO.getResourceId());

		//securityContext.setSlAdminUName(securityContext.getUsername());
		//need buyer id to be null for provider
		sessionMap.put("buyerId",null);
		sessionMap.put("vendorId",adminSearchFormDTO.getHidVendorId()+"");
		sessionMap.put("buyerId", null);
		sessionMap.put("username",adminVO.getUsername());
		sessionMap.put("providerName", adminVO.getMemberName());
		sessionMap.put("providerCompanyName", adminVO.getBusinessName());
		sessionMap.put("cityState", getCityState(adminVO.getCompanyCity(), adminVO.getCompanyState()));
		securityContext.setRoleId(OrderConstants.PROVIDER_ROLEID);
		securityContext.setUsername(adminVO.getUsername());
		securityContext.setPrimaryInd(true);

		securityContext.setRole(MPConstants.ROLE_PROVIDER);
		getSession().setAttribute(SOConstants.SECURITY_KEY, securityContext);
		this.createCommonServiceOrderCriteria();
		return "gotoManageUsers";
	}

	private String getCityState(String city, String state)
	{
		StringBuilder cityState= new StringBuilder();
		if(!SLStringUtils.isNullOrEmpty(city)){
			cityState.append(city);
		}

		if(!SLStringUtils.isNullOrEmpty(state)){
			if (cityState.length() > 0){
				cityState.append(", ");
			}
			cityState.append(state);
		}

		return cityState.toString();
	}
	public String navigateToBuyerPage() throws Exception {

		setContextDetails();
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();

		securityContext.setAdminRoleId(securityContext.getRoleId());
		// add the admin resource Id to the security context when it needs to be return to the Admin WorkFlow tabs
		securityContext.setAdminResId(securityContext.getVendBuyerResId());
		securityContext.setClientId(null);
		securityContext.setCompanyId(adminSearchFormDTO.getHidBuyerId());
		securityContext.setVendBuyerResId(adminSearchFormDTO.getHidResourceId());

		//securityContext.setSlAdminUName(securityContext.getUsername());
		ActionContext.getContext().getSession().put("buyerId",adminSearchFormDTO.getHidBuyerId() +"");
		String hidUserName = adminSearchFormDTO.getHidUserName();
		ActionContext.getContext().getSession().put("buyerUsername",hidUserName);

		sessionMap.put("buyerId",adminSearchFormDTO.getHidBuyerId());
		sessionMap.put("vendorId", null);
		sessionMap.put("buyerBusinessName",adminSearchFormDTO.getHidCompanyName());
		sessionMap.put("buyerAdmin",adminSearchFormDTO.getHidBuyerAdmin());
		sessionMap.put("buyerLocation",adminSearchFormDTO.getHidBuyerCityState());
		/*sessionMap.put("providerName", adminVO.getMemberName());
		sessionMap.put("providerCompanyName", adminVO.getBusinessName());
		sessionMap.put("cityState", getCityState(adminVO.getCompanyCity(), adminVO.getCompanyState()));*/

		switch (adminSearchFormDTO.getHidRoleId()) {
			case OrderConstants.BUYER_ROLEID:
					securityContext.setRole(OrderConstants.BUYER);
					securityContext.setRoleId(OrderConstants.BUYER_ROLEID);
					Map<String, ?> buyerDetails = securityBean.getBuyerId(hidUserName);
					if (buyerDetails != null && buyerDetails.get("clientId") != null) {
						Integer clientId = (Integer)buyerDetails.get("clientId");
						securityContext.setClientId(clientId);
						logger.info("Client Id = [" + clientId + "]");
					}
					break;
			case OrderConstants.SIMPLE_BUYER_ROLEID:
					securityContext.setRole(OrderConstants.SIMPLE_BUYER);
					securityContext.setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
					break;
			default:
				throw new Exception("Invalid role id for this method!");
		}

		securityContext.setUsername(hidUserName);
		securityContext.setPrimaryInd(true);
		this.createCommonServiceOrderCriteria();
		Account acct = getAutoACHEnabledInd();
		securityContext.setAutoACH(acct.isEnabled_ind());
		securityContext.setAccountID(acct.getAccount_id());
		getSession().setAttribute(SOConstants.SECURITY_KEY, securityContext);

		return "gotoManageUsers";
	}


	public String backToSearchPortal() throws Exception
	{
		boolean isBuyerRole = false;

		setContextDetails();
		//check wher u comming from.. buyer or provider ..populate iframe result based on that
		//Grab the details from the action
		Integer roleid = securityContext.getRoleId();
		if(roleid!= null && (roleid.intValue() == OrderConstants.BUYER_ROLEID || roleid.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID)) {
			isBuyerRole = true;
		}
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");


		///insert admin Logging
		if(securityContext.isSlAdminInd())
		updateAdminLogging();
		securityContext.setRoleId(securityContext.getAdminRoleId());
		securityContext.setUsername(securityContext.getSlAdminUName());
		securityContext.setRole(OrderConstants.NEWCO_ADMIN);
		//Refer to LoginAction where companyId is set to 99 by default
		securityContext.setCompanyId(99);
		//Set by default
		securityContext.setVendBuyerResId(-1);

		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		sessionMap.put("vendorId",null);
		sessionMap.put("buyerId",null);


		this.createCommonServiceOrderCriteria();

		this.clearBuyerInfo();
		this.clearProviderInfo();
		this.clearAccountDetailsFromSession();
		if(isBuyerRole) return searchBuyer();

		return search();
	}
	
	public String resetPassword() throws Exception{
		//String resourceId = request.getParameter("userId");	
		setContextDetails();
		String userID = adminSearchFormDTO.getUserId();
		System.out.println("resourceId:" + userID);
		if(userID == null){
			return "reset_success";
		}else{
		//String username = manageUsersDelegate.getUserNameFromResourceId(resourceId, UserRole.PROVIDER.getType());
		//boolean flag = manageUsersDelegate.resetPassword(username);
		//if (flag) {
		//	System.out.println("Password has been reset for user:" + username);
		//	return "reset_success";
		//} else {
		//	logger.info("Error in reseting password for user " + username);
			return ERROR;
		}
		//}
	}

	private void modifyProvidersForDisplay(List<AdminSearchResultsDTO> list)
	{
		for(AdminSearchResultsDTO provider : list)
		{
			if(provider.getName() != null)
			{
				int cutoff=15;
				if(provider.getName() != null)
				{
					if((provider.getName().length() > cutoff) && (provider.getName().indexOf(" ") < 0))
					{
						provider.setName(provider.getName().substring(0, cutoff));
					}
				}
				if(provider.getMarket() != null)
				{
					if((provider.getMarket().length() > cutoff) && (provider.getMarket().indexOf(" ") < 0))
					{
						provider.setMarket(provider.getMarket().substring(0, cutoff));
					}
				}
			}
		}
	}

	public String results() throws Exception
	{
		return "success";
	}

	public AdminSearchFormDTO getModel()
	{
		return adminSearchFormDTO;
	}

	public ILookupDelegate getLookupManager()
	{
		return lookupManager;
	}

	public void setLookupManager(ILookupDelegate lookupManager)
	{
		this.lookupManager = lookupManager;
	}
	private void setContextDetails(){
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
	}


	/**
	 * GGanapathy
	 *
	 */

	public String updateAdminLogging(){
		adminLoggingDto = new AdminLoggingDto();

		if(ActionContext.getContext() == null)
			return SUCCESS;

		if(ActionContext.getContext().getSession() == null)
			return SUCCESS;

		if(ActionContext.getContext().getSession().get("DashboardLog") == null)
			return SUCCESS;

		adminLoggingDto.setLoggId((Integer)ActionContext.getContext().getSession().get("DashboardLog"));
		adminLoggingDto = adminLoggingDelegate.updateAdminLogging(adminLoggingDto);
		return SUCCESS;
	}

	public Map<Integer, String> getSearchByMap() {
		return searchByMap;
	}

	public void setSearchByMap(Map<Integer, String> searchByMap) {
		this.searchByMap = searchByMap;
	}

	public AdminBuyerSearchDelegate getBuyerSearchDelegate() {
		return buyerSearchDelegate;
	}

	public void setBuyerSearchDelegate(AdminBuyerSearchDelegate buyerSearchDelegate) {
		this.buyerSearchDelegate = buyerSearchDelegate;
	}

	public SecurityBusinessBean getSecurityBean() {
		return securityBean;
	}

	public void setSecurityBean(SecurityBusinessBean securityBean) {
		this.securityBean = securityBean;
	}

}
