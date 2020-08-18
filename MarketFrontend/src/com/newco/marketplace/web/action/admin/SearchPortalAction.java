package com.newco.marketplace.web.action.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import com.newco.marketplace.exception.core.BusinessServiceException; //SL-18330
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.searchportal.ISearchPortalBO;
import com.newco.marketplace.business.iBusiness.spn.ISPNetworkBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.permission.UserVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultForAdminVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.searchportal.SearchPortalBuyerVO;
import com.newco.marketplace.vo.searchportal.SearchPortalLocationVO;
import com.newco.marketplace.vo.searchportal.SearchPortalProviderFirmVO;
import com.newco.marketplace.vo.searchportal.SearchPortalServiceProviderVO;
import com.newco.marketplace.vo.searchportal.SearchPortalUserVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.AdminProviderSearchDelegate;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.delegates.ISODashBoardDelegate;
import com.newco.marketplace.web.delegates.ISecurityDelegate;
import com.newco.marketplace.web.security.AdminPageAction;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.common.properties.IApplicationProperties;

@AdminPageAction
@Validation
public class SearchPortalAction extends SLBaseAction implements ServletRequestAware
{
	private static final long serialVersionUID = -3533735745398646552L;
	private HttpServletRequest request;
	private HttpSession session;
	private SecurityContext securityContext;
	private static final Logger logger = Logger.getLogger(SearchPortalAction.class.getName());
	private IManageUsersDelegate manageUsersDelegate;
	
	private ILookupBO lookupBO;
	private ISPNetworkBO spnetworkBO;
	private ISearchPortalBO searchPortalBO;
	private ISecurityDelegate securityDel;
	protected AdminProviderSearchDelegate providerSearchDelegate;
	private ISODashBoardDelegate dashboardDelegate;

	private SearchPortalBuyerVO searchPortalBuyerVO = new SearchPortalBuyerVO();
	private List<SearchPortalBuyerVO> searchPortalBuyerResultsVO;

	private SearchPortalProviderFirmVO searchPortalProviderFirmVO = new SearchPortalProviderFirmVO();
	private List<SearchPortalProviderFirmVO> SearchPortalProviderFirmResultsVO;

	private SearchPortalServiceProviderVO searchPortalServiceProviderVO = new SearchPortalServiceProviderVO();
	private List<SearchPortalServiceProviderVO> SearchPortalServiceProviderResultsVO;

	private List<LookupVO> primaryIndustryList;
	private List<LookupVO> marketsList;
	private List<LookupVO> districtsList;
	private List<LookupVO> regionsList;
	private List<LookupVO> providerFirmStatusList;
	private List<LookupVO> serviceProviderStatusList;
	private List<LookupVO> backgroundStatusList;
	private List<LookupVO> providerNetworkList;
	private List<LookupVO> primarySkillsList;
	private List<LookupVO> referralsList;
	private List<LookupVO> statesList;
	private List<LookupVO> spnetList;
	private ArrayList<SkillNodeVO> secondaryList1;
	private ArrayList<SkillNodeVO> secondaryList2;
	private Integer tabInSession = new Integer(0);
	private String resourceId;
	private String spEditProfile;
	private boolean resetPasswordFlow = false;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private GeneralInfoVO bgCheckVO= new GeneralInfoVO();
	private boolean isReverseSharing;
	

	public static final Integer BUYER_TAB_ID = new Integer(2);
	public static final Integer PROVIDER_FIRM_TAB_ID = new Integer(1);
	public static final Integer SERVICE_PROVIDER_TAB_ID = new Integer(0);
	public static final String  ADMIN_FROM_JS =  "-1";
	public static final String FILTER_REQUIRED_MESSAGE = "Please enter search criteria";
	
	//SL-15642 inject application properties
	private IApplicationProperties applicationProperties;
	
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public SearchPortalAction(IManageUsersDelegate manageUsersDelegate) {
		this.manageUsersDelegate = manageUsersDelegate; 
	}


	public String input() {
		//initLookupDropdowns();
		retrieveActionMessage();
		primaryIndustryList = lookupBO.loadProviderPrimaryIndustry();
		marketsList = lookupBO.getMarkets();
		districtsList = lookupBO.getDistricts();
		regionsList = lookupBO.getRegions();
		providerFirmStatusList = lookupBO.getProviderFirmStatuses();
		serviceProviderStatusList = lookupBO.loadWorkFlowStatusForEntity(ProviderConstants.SERVICE_PROVIDER_WORKFLOW_ENTITY);
		backgroundStatusList = lookupBO.getBackgroundStatuses();
		providerNetworkList = lookupBO.getProviderNetworks();
		primarySkillsList = lookupBO.getPrimarySkills();
		referralsList = lookupBO.getReferral();
		statesList = lookupBO.getStateCodes();
		try{
			spnetList = spnetworkBO.getSpnetList();
		}catch(Exception e){
			logger.error("Exception occurred while getting spnet list due to "+ e.getMessage());
		}
		
		this.setSearchPortalBuyerVO((SearchPortalBuyerVO)ActionContext.getContext().getSession().get("searchPortalBuyerVO"));
		this.setSearchPortalProviderFirmVO((SearchPortalProviderFirmVO)ActionContext.getContext().getSession().get("searchPortalProviderFirmVO"));
		this.setSearchPortalServiceProviderVO((SearchPortalServiceProviderVO)ActionContext.getContext().getSession().get("searchPortalServiceProviderVO"));
		Integer tmpTabInSession = (Integer)ActionContext.getContext().getSession().get("searchPortalTabInSession");
		if (tmpTabInSession != null)
		{
			tabInSession = tmpTabInSession;
		}

		try {
			this.setSecondaryList1(lookupBO.getSkillTreeCategoriesOrSubCategories(searchPortalServiceProviderVO.getPrimaryVerticleId()));
			this.setSecondaryList2(lookupBO.getSkillTreeCategoriesOrSubCategories(searchPortalServiceProviderVO.getSecondarySkillId()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SUCCESS;
	}

	@SkipValidation
	public String execute() {
		return input();
	}

	public String searchBuyer() throws Exception
	{
		SearchPortalBuyerVO spbVO = this.getSearchPortalBuyerVO();

		if(spbVO.isFilterEmpty()){
			this.addActionError(FILTER_REQUIRED_MESSAGE);
			return INPUT;
		}
		ActionContext.getContext().getSession().put("searchPortalBuyerVO",spbVO);
		ActionContext.getContext().getSession().put("searchPortalTabInSession",BUYER_TAB_ID);
		this.setFieldsNullBuyerVO(spbVO);
		spbVO.getLocation().setPhoneNumber(stripPhoneDash(spbVO.getLocation().getPhoneNumber()));
		 //Prepare for querying by replacing "'" with "''"
	    spbVO.setSoId(prepareSOId(spbVO.getSoId()));
		prepareUserFilterFields(spbVO.getUser());
		prepareLocationFilterFields(spbVO.getLocation());
		
		String sortKey = getRequest().getParameter("sortKey");
        String sortOrder= getRequest().getParameter("sortOrder");
        List<String> sortKeyValues = Arrays.asList(
				OrderConstants.COLUMN_LASTNAME,
				OrderConstants.COLUMN_USERID,
				OrderConstants.COLUMN_LAST_ACTIVITY_DATE,
				OrderConstants.COLUMN_MARKETNAME,
				OrderConstants.COLUMN_STATE,
				OrderConstants.COLUMN_ROLETYPE,
				"");
		if (sortKey == null||!(sortKeyValues.contains(sortKey))) {
			sortKey = OrderConstants.COLUMN_USERID;
		}
		if (sortOrder == null||!(sortOrder.equalsIgnoreCase(OrderConstants.SORT_ORDER_ASC)||sortOrder.equalsIgnoreCase(OrderConstants.SORT_ORDER_DESC))) {
			sortOrder = OrderConstants.SORT_ORDER_ASC;
		}
        if((!StringUtils.isEmpty(sortKey)) && (!StringUtils.isEmpty(sortOrder))){
        	spbVO.setSortKey(sortKey);
        	spbVO.setSortOrder(sortOrder);
        }
		List<SearchPortalBuyerVO> resultsVO = searchPortalBO.searchBuyer(spbVO);
		
		//For child account of buyer (in case of flag 'check to show buyer agent'), gather display data for reset password modal box.  
		for(SearchPortalBuyerVO result: resultsVO){
			 UserVO buyerVO = manageUsersDelegate.getBuyerUserWithZip(result.getUser().getUserName(),result.getUser().getUserName());
			 result.getLocation().setBuyerEmailAddress(buyerVO.getEmail());
			 result.getLocation().setBuyerPhoneNumber(buyerVO.getBusinessPhone());
			 result.getLocation().setBuyerZip(buyerVO.getZip());
			 result.getUser().setAdminName(result.getUser().getAdminName().replace("'","\\'"));
             result.getUser().setUserName(result.getUser().getUserName().replace("'", "\\'"));
             if(StringUtils.isNotBlank(result.getUser().getBusinessName())){
            	 result.getUser().setBusinessName(result.getUser().getBusinessName().replace("'", "\\'"));
             }

		}
		
		this.setSearchPortalBuyerResultsVO(resultsVO);
		
		setPersmissionsForReset();

		return SUCCESS;
	}

	// SL-18330 changes made to update the provider names  -- START
	public String updateServiceProviderName() throws Exception
	{
		logger.info("Control comes here*****************************");
		String providerFirstName = (String)getParameter("providerFirstName");
		String providerMiddleName = (String)getParameter("ProviderMiddleName");
		String providerLastName = (String)getParameter("ProviderLastName");
		//String vendorId = (String)getParameter("vendorId");
		String resourceId = (String)getParameter("resourceId");
		String providerOldName = (String)getParameter("ProviderWholeName");
		String providerNewName= (String)providerFirstName+" "+providerMiddleName+" "+providerLastName;
		String modified_by=(String)getParameter("modified_by");
		
		//SLT-976
		Integer bgCheckId;
		Integer sharedBgCheckId;
		Integer sharedBgStateInfo;
		Integer existingBgStateInfo;
		Integer existingBGCheckId;
		try {
			logger.info("First name from parameter ----"+ providerFirstName);
			logger.info("MiddleName = "+providerMiddleName);
			logger.info("providerLastName = "+providerLastName);
			logger.info("resourceId = "+resourceId);
			logger.info("providerOldName = "+providerOldName);
			logger.info("modified_by = "+modified_by);
			
			//SLT-976
			String []provName=providerOldName.split(" ");
			logger.info(provName[0]);
			logger.info(provName[2]);
			String ssn=getProviderSsn(resourceId);
				        
			//getting the bgcheck id of the provider to be updated
			existingBGCheckId=searchPortalBO.getBGCheckId(resourceId);
			existingBgStateInfo = getBGStateInfo(existingBGCheckId);

			// checking if there exists any other resource with the same bgcheck id
			bgCheckVO = validateForExistingResources(resourceId, providerFirstName, providerLastName, ssn);
			if (null != bgCheckVO) {
				if (null != bgCheckVO.getBcCheckId()) {
					// Reverse sharing logic
					// sharedBgStateInfo = getBGStateInfo(bgCheckVO.getBackgroundCheckStatusId());
					setSharedBgDetails(bgCheckVO.getBackgroundCheckStatusId(), existingBgStateInfo, bgCheckVO, resourceId,existingBGCheckId);
					updateBgDetails(bgCheckVO.getBcCheckId(), bgCheckVO.getResourceId(),bgCheckVO.getBackgroundCheckStatusId(), isReverseSharing);
				}
			} else {
				bgCheckId = validateForProviderWithSameBGCheckDetails(resourceId, provName[0], provName[2], ssn);
				if (null != bgCheckId)
					updateAndInsertNewBgDetails(bgCheckId, resourceId, ssn);
			}
			searchPortalBO.updateServiceProviderName(providerFirstName,providerMiddleName,providerLastName,resourceId);
			
			searchPortalBO.insertServiceProviderName_Audit(providerOldName,providerNewName,resourceId,"Provider",modified_by);

		} catch (BusinessServiceException e) {
			addActionError(e.getMessage());
		}
		
		return SUCCESS;
	}
	
	private void setSharedBgDetails(Integer sharedBgStateInfo, Integer existingBgStateInfo,GeneralInfoVO pro,String resourceId,Integer bgCheckId){
		
		if(Constants.BG_STATE_NOT_STARTED.intValue()==sharedBgStateInfo.intValue() && Constants.BG_STATE_PENDING_SUBMISSION.intValue()== existingBgStateInfo.intValue()){
			pro.setBackgroundCheckStatusId(existingBgStateInfo);
			pro.setBcCheckId(bgCheckId);
			isReverseSharing=true;
		}
		else{
			pro.setBackgroundCheckStatusId(sharedBgStateInfo);
			pro.setResourceId(resourceId);
		}
	}
			
	private String getProviderSsn(String resourceId) throws Exception {
		GeneralInfoVO provider = new GeneralInfoVO();
		provider.setResourceId(resourceId);
		provider = searchPortalBO.getProviderDetails(provider);
		return provider.getSsn();
	}
		
	private Integer getBGStateInfo(Integer bgCheckId) throws Exception{
		return searchPortalBO.getBackgroundCheckInfo(bgCheckId);
	}

	private void updateBgDetails(Integer bgCheckId, String resourceId,Integer bgCheckStateId,boolean isReverseSharing) throws Exception {
		searchPortalBO.updateBGCheck_details(resourceId, bgCheckId, bgCheckStateId);
		if(isReverseSharing)
			searchPortalBO.updateBGStateInfo(bgCheckId,bgCheckStateId);
	}
			
	private void updateAndInsertNewBgDetails(Integer bgCheckId,String resourceId,String ssn) throws Exception{
		Integer bgCheckStateId=searchPortalBO.getBackgroundCheckInfo(bgCheckId);
		if(null!=bgCheckStateId && (Constants.BG_STATE_NOT_STARTED.intValue()==bgCheckStateId.intValue() ||Constants.BG_STATE_PENDING_SUBMISSION.intValue()== bgCheckStateId.intValue()))
			searchPortalBO.updateBackGroundCheckDetails(resourceId,ssn);
	}

	private Integer validateForProviderWithSameBGCheckDetails(String resourceId, String providerFirstName,String providerLastName, String ssn) throws Exception {
		GeneralInfoVO pro = new GeneralInfoVO();
		pro.setFirstName(providerFirstName);
		pro.setLastName(providerLastName);
		pro.setSsn(ssn);
		pro.setResourceId(resourceId);
		Integer bcCheckId = null;
		try {
			bcCheckId = searchPortalBO.getBackgroundCheckIdWithSameContact(pro);
		} catch (Exception e) {
			logger.error("Exception occurred while fetching bgCheckId");
		}
				/*
				 * if(null!=bcCheckId){
				 * bgCheckStateId=searchPortalBO.getBackgroundCheckInfo(bcCheckId);
				 * if(null!=bgCheckStateId && (bgCheckStateId.intValue()==7 ||
				 * bgCheckStateId.intValue()==28))
				 * searchPortalBO.updateBackGroundCheckDetails(resourceId,pro.getSsn());
				 * }
				 */
		return bcCheckId;
	}
				
	private GeneralInfoVO validateForExistingResources(String resourceId, String providerFirstName,String providerLastName, String ssn) throws Exception {
		GeneralInfoVO pro = new GeneralInfoVO();
		pro.setFirstName(providerFirstName);
		pro.setLastName(providerLastName);
		pro.setSsn(ssn);
		pro.setResourceId(resourceId);
		try {
			pro = searchPortalBO.getProviderDetailsWithSameContact(pro);
		} catch (Exception e) {
			logger.error("Exception occurred while fetching bgCheckId");
		}
		return pro;
			
	}
				
// 	SL-18330 changes made to update the provider names  -- END
	
	public String searchProviderFirm() throws Exception
	{
		SearchPortalProviderFirmVO pfVO = this.getSearchPortalProviderFirmVO();
		if(pfVO.isFilterEmpty()){
			this.addActionError(FILTER_REQUIRED_MESSAGE);
			return INPUT;
		}		
		
		
		ActionContext.getContext().getSession().put("searchPortalProviderFirmVO",pfVO);
		ActionContext.getContext().getSession().put("searchPortalTabInSession",PROVIDER_FIRM_TAB_ID);
		return searchProviderFirm(pfVO);
	}
	
	private String searchProviderFirm(SearchPortalProviderFirmVO pfVO) throws Exception {		
		this.setFieldsNullProviderFirmVO(pfVO);
		pfVO.getLocation().setPhoneNumber(stripPhoneDash(pfVO.getLocation().getPhoneNumber()));
		//Prepare for querying by replacing "'" with "''"
	    pfVO.setSoId(prepareSOId(pfVO.getSoId()));
	    prepareUserFilterFields(pfVO.getUser());
		prepareLocationFilterFields(pfVO.getLocation());
        String sortKey = getRequest().getParameter("sortKey");
 	    String sortOrder= getRequest().getParameter("sortOrder");
 	    List<String> sortKeyValues = Arrays.asList(
				OrderConstants.COLUMN_COMPANYID,
				OrderConstants.COLUMN_PRO_FIRM_STATUS,
				OrderConstants.COLUMN_LAST_ACTIVITY_DATE,
				OrderConstants.COLUMN_BUSINESSNAME,
				OrderConstants.COLUMN_MARKETNAME,
				OrderConstants.COLUMN_STATE,
				"");
		if (sortKey == null||!(sortKeyValues.contains(sortKey))) {
			sortKey = OrderConstants.COLUMN_COMPANYID;
		}
		if (sortOrder == null||!(sortOrder.equalsIgnoreCase(OrderConstants.SORT_ORDER_ASC)||sortOrder.equalsIgnoreCase(OrderConstants.SORT_ORDER_DESC))) {
			sortOrder = OrderConstants.SORT_ORDER_ASC;
		}
		if((!StringUtils.isEmpty(sortKey)) && (!StringUtils.isEmpty(sortOrder))){
			pfVO.setSortKey(sortKey);
		    pfVO.setSortOrder(sortOrder);
		}
		List<SearchPortalProviderFirmVO> resultsVO = searchPortalBO.searchProviderFirm(pfVO);
		this.setSearchPortalProviderFirmResultsVO(resultsVO);
		setPersmissionsForReset();
		if(resetPasswordFlow){
			resetPasswordFlow = false;
			return "reset";
		}else{
			return SUCCESS;
		}
	}
	

	public String searchServiceProvider() throws Exception
	{
		SearchPortalServiceProviderVO spVO = this.getSearchPortalServiceProviderVO();
		if(spVO.isFilterEmpty()){
			this.addActionError(FILTER_REQUIRED_MESSAGE);
			return INPUT;
		}		
		
		ActionContext.getContext().getSession().put("searchPortalServiceProviderVO",spVO);
		ActionContext.getContext().getSession().put("searchPortalTabInSession",SERVICE_PROVIDER_TAB_ID);
		this.setFieldsNullServiceProviderVO(spVO);
		spVO.getLocation().setPhoneNumber(stripPhoneDash(spVO.getLocation().getPhoneNumber()));
		//Prepare for querying by replacing "'" with "''"
		spVO.setSoId(prepareSOId(spVO.getSoId()));
	    prepareUserFilterFields(spVO.getUser());
	    prepareLocationFilterFields(spVO.getLocation());
		String sortKey = getRequest().getParameter("sortKey");
		String sortOrder= getRequest().getParameter("sortOrder");
		List<String> sortKeyValues = Arrays.asList(
				OrderConstants.COLUMN_USERID,
				OrderConstants.COLUMN_SERVICE_PRO_STATUS,
				OrderConstants.COLUMN_LAST_ACTIVITY_DATE,
				OrderConstants.COLUMN_FIRSTNAME,
				OrderConstants.COLUMN_MARKETNAME,
				OrderConstants.COLUMN_CITY,
				"");
		if (sortKey == null||!(sortKeyValues.contains(sortKey))) {
			sortKey = OrderConstants.COLUMN_USERID;
		}
		if (sortOrder == null||!(sortOrder.equalsIgnoreCase(OrderConstants.SORT_ORDER_ASC)||sortOrder.equalsIgnoreCase(OrderConstants.SORT_ORDER_DESC))) {
			sortOrder = OrderConstants.SORT_ORDER_ASC;
		}
		if((!StringUtils.isEmpty(sortKey)) && (!StringUtils.isEmpty(sortOrder))){
			spVO.setSortKey(sortKey);
		 	spVO.setSortOrder(sortOrder);
		}
		List<SearchPortalServiceProviderVO> resultsVO = searchPortalBO.searchServiceProvider(spVO);
		
		
		//Code Changes for SL 1602
		for (SearchPortalServiceProviderVO searchPortalServiceProviderVO : resultsVO) {
			searchPortalServiceProviderVO.setSlNameChange(false);
			if (searchPortalServiceProviderVO.getBgCheckStateId() == Constants.BG_STATE_CLEARED.intValue()
					|| searchPortalServiceProviderVO.getBgCheckStateId() == Constants.BG_STATE_NOT_CLEARED.intValue()
					|| searchPortalServiceProviderVO.getBgCheckStateId() == Constants.BG_STATE_IN_PROCESS.intValue())
				searchPortalServiceProviderVO.setSlNameChange(true);
		}
		this.setSearchPortalServiceProviderResultsVO(resultsVO);
		setPersmissionsForReset();

		return SUCCESS;
	}

	private String stripPhoneDash(String phone)
	{
		if (phone != null)
		{
			phone = phone.replace("-","");
		}
		
		return phone;
	}

	@SkipValidation
	public String navigateToBuyerPage() throws Exception {
		//SL20014-setting attribute for buyer to view provider firm uploaded compliance documents initially to false
		getSession().removeAttribute(SPNConstants.BUYER_SPNVIEWDOC_PERMISSION);
		//SL20014-end
		SearchPortalBuyerVO spbVO = this.getSearchPortalBuyerVO();
		boolean editProfile = false;
		String temp = getParameter("editProfile");
		if (temp !=null && temp.equals("true")) {
			editProfile = true;
		}
		
		setContextDetails();
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		
		securityContext.setAdopted(true); 

		securityContext.setAdminRoleId(securityContext.getRoleId());
		// add the admin resource Id to the security context when it needs to be return to the Admin WorkFlow tabs
		securityContext.setAdminResId(securityContext.getVendBuyerResId());
		securityContext.setClientId(null);
		//securityContext.setCompanyId(adminSearchFormDTO.getHidBuyerId());
		securityContext.setCompanyId(spbVO.getUser().getCompanyId());
		//securityContext.setVendBuyerResId(adminSearchFormDTO.getHidResourceId());
		securityContext.setVendBuyerResId(spbVO.getUser().getUserId());

		//securityContext.setSlAdminUName(securityContext.getUsername());
		//ActionContext.getContext().getSession().put("buyerId",adminSearchFormDTO.getHidBuyerId() +"");
		ActionContext.getContext().getSession().put("buyerId",spbVO.getUser().getCompanyId() +"");
		//ActionContext.getContext().getSession().put("buyerUsername",adminSearchFormDTO.getHidUserName());
		String userName = spbVO.getUser().getUserName();
		ActionContext.getContext().getSession().put("buyerUsername",userName);


		//sessionMap.put("buyerId",adminSearchFormDTO.getHidBuyerId());
		sessionMap.put("buyerId",spbVO.getUser().getCompanyId());
		sessionMap.put("vendorId", null);
		//sessionMap.put("buyerBusinessName",adminSearchFormDTO.getHidCompanyName());
		sessionMap.put("buyerBusinessName",spbVO.getUser().getBusinessName());
		//sessionMap.put("buyerAdmin",adminSearchFormDTO.getHidBuyerAdmin());
		sessionMap.put("buyerAdmin",spbVO.getUser().getAdminName());
		//sessionMap.put("buyerLocation",adminSearchFormDTO.getHidBuyerCityState());
		sessionMap.put("buyerLocation",spbVO.getLocation().getCity() + "," + spbVO.getLocation().getState());

		/*sessionMap.put("providerName", adminVO.getMemberName());
		sessionMap.put("providerCompanyName", adminVO.getBusinessName());
		sessionMap.put("cityState", getCityState(adminVO.getCompanyCity(), adminVO.getCompanyState()));*/

		//switch (adminSearchFormDTO.getHidRoleId()) {
		
		switch (spbVO.getUser().getRoleTypeId()) {
			case OrderConstants.BUYER_ROLEID:
					securityContext.setRole(OrderConstants.BUYER);
					securityContext.setRoleId(OrderConstants.BUYER_ROLEID);
					Map<String, ?> buyerDetails = securityDel.getBuyerId(userName);
					if (buyerDetails != null && buyerDetails.get("clientId") != null) {
						Integer clientId = (Integer)buyerDetails.get("clientId");
						securityContext.setClientId(clientId);
						logger.info("Client Id = [" + clientId + "]");
					}
					if (buyerDetails != null && buyerDetails.get(OrderConstants.BUYER_ADMIN_CONTACT_ID) != null) {
						Integer buyerAdminContactId = ((Long)buyerDetails.get(OrderConstants.BUYER_ADMIN_CONTACT_ID)).intValue();
						securityContext.setBuyerAdminContactId(buyerAdminContactId);
						logger.info("buyerAdminContactId = [" + buyerAdminContactId + "]");
					}
					break;
			case OrderConstants.SIMPLE_BUYER_ROLEID:
					securityContext.setRole(OrderConstants.SIMPLE_BUYER);
					securityContext.setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
					break;
			default:
				throw new Exception("Invalid role id for this method!");
		}

		//securityContext.setUsername(adminSearchFormDTO.getHidUserName());
		securityContext.setUsername(userName);
		securityContext.setPrimaryInd(true);
		this.createCommonServiceOrderCriteria();
		Account acct = getAutoACHEnabledInd();
		securityContext.setAutoACH(acct.isEnabled_ind());
		securityContext.setAccountID(acct.getAccount_id());
		//code to add  autoclose feature to the permission list of sl-admin when sl-admin adopts a buyer 
		boolean isAutocloseFeatureOn = isBuyerFeatureOn(securityContext, BuyerFeatureConstants.AUTO_CLOSE);	
		//R12_1 SL-20663
		boolean isHSRAutocloseFeatureOn = isBuyerFeatureOn(securityContext, BuyerFeatureConstants.INHOME_AUTO_CLOSE);

		boolean isCARFeatureOn = isBuyerFeatureOn(securityContext, BuyerFeatureConstants.CONDITIONAL_ROUTE);	
		//SL-15642 checking  auto accept status feature set for buyer
		//Setting required attribute in session
		boolean isAutoAcceptFeatureOn= isBuyerFeatureOn(securityContext, BuyerFeatureConstants.AUTO_ACCEPTANCE);
		getSession().setAttribute("autoAcceptStatus", isAutoAcceptFeatureOn);
		getSession().setAttribute("carFeatureOn", isCARFeatureOn);
		if(isAutoAcceptFeatureOn==true)
		{
			getSession().setAttribute("autoAcceptBuyerAdmin","true");
		}
		//Checking the buyer lead management permission status
		//Checking the lead management permission for a buyer
		//Checking the lead management permission for a buyer and provider
				boolean leadManagmentPermissionInd=checkLeadManagementPermission(securityContext.getRoleId());
				if(leadManagmentPermissionInd && securityContext.getRole().equals("buyer"))
				{	
					boolean isBuyerLeadManagementFeatureOn= isBuyerFeatureOn(securityContext, BuyerFeatureConstants.LEAD_MANAGEMENT);
						if(isBuyerLeadManagementFeatureOn==true)
						{
								getSession().setAttribute("buyerLeadManagementPermission","true");
						}
					
					
				}
				
		List<UserActivityVO> userActivityVoListAdopted = securityDel.getUserActivityRolesList(userName);
		Map<String , UserActivityVO> userActivityVoMap = securityContext.getRoleActivityIdList();
		if (userActivityVoListAdopted != null) {
			for(UserActivityVO useractivityVO:userActivityVoListAdopted){
				String activityId = useractivityVO.getActivityId();
				if(isAutocloseFeatureOn && activityId.equals("55")){
					userActivityVoMap.put(activityId, useractivityVO);
				}
				//r12_1 SL-20663
				if(isHSRAutocloseFeatureOn && activityId.equals("64")){
					userActivityVoMap.put(activityId, useractivityVO);
				}
				if(isCARFeatureOn && (activityId.equals("43") || activityId.equals("44"))){
					userActivityVoMap.put(activityId, useractivityVO);
				}
				if(activityId.equals("57")){
					userActivityVoMap.put(activityId, useractivityVO);
				}
				if(activityId.equals("58")){
					userActivityVoMap.put(activityId, useractivityVO);
				}
				if(useractivityVO.getActivityName().equalsIgnoreCase("Buyer Email Configuration")){
					userActivityVoMap.put(activityId, useractivityVO);
				}
				//SL20014-setting the attribute for buyer to view provider firm uploaded compliance documents if he as permission for the same.
				if(activityId.equals(ActivityRegistryConstants.ACTIVITY_ROLE_ID_BUYER_SPN_VIEWDOC_PERMISSION_STRING)){
					getSession().setAttribute(SPNConstants.BUYER_SPNVIEWDOC_PERMISSION, true);
				}
				//SL20014-end
			}
		}
		
		if (!isAutocloseFeatureOn && userActivityVoMap.containsKey("55")){
			userActivityVoMap.remove("55");
		}
		//R12_1 SL-20663
		if (!isHSRAutocloseFeatureOn && userActivityVoMap.containsKey("64")){
			userActivityVoMap.remove("64");
		}
		if (!isCARFeatureOn){
			if(userActivityVoMap.containsKey("44"))
				userActivityVoMap.remove("44");
			
			if(userActivityVoMap.containsKey("43"))
				userActivityVoMap.remove("43");
		}
		securityContext.setRoleActivityIdList(userActivityVoMap);
		getSession().setAttribute(SOConstants.SECURITY_KEY, securityContext);

		if (editProfile) {
			System.out.println("USERNAME:" +  userName);
			request.setAttribute("userName", userName);			
			return "gotoEditProfile";
		}
		
		return "gotoManageUsers";
	}
	
	private boolean isBuyerFeatureOn(SecurityContext securityContext, String feature) {

		//Note: This method isn't currently generic enough to handle all features.
		// It was originally implemented with Conditional Auto Routing in mind, but is expected to expand until a 
		// new project is used to limit permissions (aka activities) by feature set. 
		if (securityContext.getRoleId().intValue()==OrderConstants.BUYER_ROLEID) {
			Integer buyerId = securityContext.getCompanyId();
			if (buyerId == null) {
				throw new RuntimeException("Couldn't find the buyerId", new Exception());
			}

			// lookup buyer_feature_set
			return buyerFeatureSetBO.validateFeature(buyerId, feature).booleanValue();

		}
		return false;
	}


	/**
	 * Sets the buyer Role for adapted user
	 *
	 * @param spbVO
	 * @throws Exception
	 */
	private void setBuyerRole(SearchPortalBuyerVO spbVO) throws Exception {
		switch (spbVO.getUser().getRoleTypeId()) {
			case OrderConstants.BUYER_ROLEID:
					securityContext.setRole(OrderConstants.BUYER);
					securityContext.setRoleId(OrderConstants.BUYER_ROLEID);
					Map<String,?> buyer = securityDel.getBuyerId(spbVO.getUser().getUserName());
					if(buyer != null && buyer.get("maxSpendLimitPerSo") != null){
						securityContext.setMaxSpendLimitPerSO(((Double)buyer.get("maxSpendLimitPerSo")).doubleValue());
					}
					if (buyer != null && buyer.get("clientId") != null) {
						Integer clientId = (Integer)buyer.get("clientId");
						securityContext.setClientId(clientId);
						logger.info("Adopting user " + spbVO.getUser().getUserName());
					}
					break;
			case OrderConstants.SIMPLE_BUYER_ROLEID:
					securityContext.setRole(OrderConstants.SIMPLE_BUYER);
					securityContext.setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
					break;
			default:
				throw new Exception("Invalid role id for this method!");
		}
	}

	@SkipValidation
	public String navigateToProviderPage()
	{
		//SL20014-removing attribute for buyer to view provider firm uploaded compliance documents
		getSession().removeAttribute(SPNConstants.BUYER_SPNVIEWDOC_PERMISSION);
		//SL20014-end
		SearchPortalProviderFirmVO pfVO = this.getSearchPortalProviderFirmVO();
		setContextDetails();
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();

		// get primary user for company
		ProviderResultForAdminVO adminVO = providerSearchDelegate.getProviderAdmin(pfVO.getUser().getCompanyId());
		

		securityContext.setAdminRoleId(securityContext.getRoleId());
		securityContext.setAdminResId(securityContext.getVendBuyerResId());
		securityContext.setClientId(null);
		securityContext.setCompanyId(pfVO.getUser().getCompanyId());
		securityContext.setVendBuyerResId(adminVO.getResourceId());
		
		securityContext.setAdopted(true);
		//securityContext.setSlAdminUName(securityContext.getUsername());
		//need buyer id to be null for provider
		sessionMap.put("buyerId",null);
		sessionMap.put("vendorId",pfVO.getUser().getCompanyId()+"");
		sessionMap.put("buyerId", null);
		sessionMap.put("username",adminVO.getUsername());
		sessionMap.put("providerName", adminVO.getMemberName());
		sessionMap.put("providerCompanyName", adminVO.getBusinessName());
		sessionMap.put("cityState", adminVO.getCompanyCity() + "," + adminVO.getCompanyState());
		securityContext.setRoleId(OrderConstants.PROVIDER_ROLEID);
		securityContext.setUsername(adminVO.getUsername());
		//Changing this for SL-7476
		Map providerIndicators = securityDel
		.getProviderIndicators(securityContext.getVendBuyerResId());

		if (providerIndicators != null){
			if(providerIndicators.get("primaryIndicator") != null) {
				securityContext.setPrimaryInd(((Boolean) providerIndicators
				.get("primaryIndicator")));
			}
			if(providerIndicators.get("providerAdminIndicator") != null) {
				securityContext.setProviderAdminInd(((Boolean) providerIndicators
				.get("providerAdminIndicator")));
			}
		}
		

		securityContext.setRole(MPConstants.ROLE_PROVIDER);
		getSession().setAttribute(SOConstants.SECURITY_KEY, securityContext);
		this.createCommonServiceOrderCriteria();
		
		//SL-15642:checks whether provider has any un-archived CAR rules
		int unArchivedCount = dashboardDelegate.getUnarchivedCARRulesCount(securityContext.getCompanyId());
		securityContext.setUnArchivedCARRulesAvailable(unArchivedCount);

		if (ADMIN_FROM_JS.equals(this.resourceId))
		{
			request.setAttribute("resourceId", adminVO.getResourceId());
			request.setAttribute("spEditProfile", "2");
			this.setSpEditProfile("2");
			return "gotoEditCompanyProfile";
		}
		else if (pfVO.getUser().getUserId() != null)
		{
			request.setAttribute("resourceId", this.resourceId);
			request.setAttribute("spEditProfile", "1");
			this.setSpEditProfile("1");
			return "gotoEditProfile";
		}else{
			request.setAttribute("resourceId", this.resourceId);
		}
		boolean leadManagmentPermissionInd=checkLeadManagementPermission(securityContext.getRoleId());
		getSession().setAttribute("providerLeadManagementPermission",leadManagmentPermissionInd);
		
		//SL-15642:To display ordermanagement tab when admin adopts a provider firm who has order managemnet permission
		List<UserActivityVO> userActivityVoList = (List<UserActivityVO>) securityDel.getUserActivityRolesList(adminVO.getUsername());;
		for (UserActivityVO userActivityVo : userActivityVoList) {
			int activityId = Integer.parseInt(userActivityVo.getActivityId());
		if (activityId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_VIEW_ORDER_MANAGEMENT) {
			checkOrderManagementPermission(adminVO.getVendorId());
		}
		}
		
		return "gotoManageUsers";
	}

	@SkipValidation
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

		securityContext.setAdopted(false);

		///insert admin Logging
		if(securityContext.isSlAdminInd())
		//updateAdminLogging();
		securityContext.setRoleId(securityContext.getAdminRoleId());
		securityContext.setUsername(securityContext.getSlAdminUName());
		securityContext.setRole(OrderConstants.NEWCO_ADMIN);
		//Refer to LoginAction where companyId is set to 99 by default
		securityContext.setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
		securityContext.setAdminRoleId(-1);
		//Set by default
		securityContext.setVendBuyerResId(securityContext.getAdminResId());

		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		sessionMap.put("vendorId",null);
		sessionMap.put("buyerId",null);


		this.createCommonServiceOrderCriteria();

		this.clearBuyerInfo();
		this.clearProviderInfo();
		this.clearAccountDetailsFromSession();
		//if(isBuyerRole) return searchBuyer();
		clearInsuranceInfo();
		clearSearchCriteria();
		//R10.3 SL-19503 Licenses & Certification Tab Not Clearing Cache START
		//Fix: Clearing the vendor cred id on click of return to search portal
		clearVendorCredentials();
		//R10.3 SL-19503 Licenses & Certification Tab Not Clearing Cache END
		return execute();
	}

	private boolean setFieldsNullBuyerVO(SearchPortalBuyerVO spbVO)
	{
		Integer resourceId = (Integer) spbVO.getUser().getUserId();
		Integer companyId = (Integer) spbVO.getUser().getCompanyId();
		String name = (String) spbVO.getUser().getFnameOrLname();
		String username = (String) spbVO.getUser().getUserName();
		String businessName = (String) spbVO.getUser().getBusinessName();
		Date fromSignupDate = (Date) spbVO.getUser().getFromSignUpDate();
		Date toSignupDate = (Date) spbVO.getUser().getToSignUpDate();
		String phoneNumber = (String) spbVO.getLocation().getPhoneNumber();
		String emailAddress = (String) spbVO.getLocation().getEmailAddress();
		String state = (String) spbVO.getLocation().getState();
		String city = (String) spbVO.getLocation().getCity();
		String zip = (String) spbVO.getLocation().getZip();
		String soid = (String) spbVO.getSoId();

		if ("".equals(name))
		{
			spbVO.getUser().setFnameOrLname(null);
		}
		if ("".equals(username))
		{
			spbVO.getUser().setUserName(null);
		}
		if ("".equals(businessName))
		{
			spbVO.getUser().setBusinessName(null);
		}
		if ("".equals(phoneNumber))
		{
			spbVO.getLocation().setPhoneNumber(null);
		}
		if ("".equals(emailAddress))
		{
			spbVO.getLocation().setEmailAddress(null);
		}
		if ("".equals(state))
		{
			spbVO.getLocation().setState(null);
		}
		if ("".equals(city))
		{
			spbVO.getLocation().setCity(null);
		}
		if ("".equals(zip))
		{
			spbVO.getLocation().setZip(null);
		}
		if ("-1".equals(state))
		{
			spbVO.getLocation().setState(null);
		}
		if ("".equals(soid))
		{
			spbVO.setSoId(null);
		}

		return true;
	}

	private boolean setFieldsNullProviderFirmVO(SearchPortalProviderFirmVO pfVO)
	{
		Integer resourceId = (Integer) pfVO.getUser().getUserId();
		Integer companyId = (Integer) pfVO.getUser().getCompanyId();
		String adminName = (String) pfVO.getUser().getAdminName();
		String name = (String) pfVO.getUser().getFnameOrLname();
		String username = (String) pfVO.getUser().getUserName();
		String businessName = (String) pfVO.getUser().getBusinessName();
		Date fromSignupDate = (Date) pfVO.getUser().getFromSignUpDate();
		Date toSignupDate = (Date) pfVO.getUser().getToSignUpDate();
		String phoneNumber = (String) pfVO.getLocation().getPhoneNumber();
		String emailAddress = (String) pfVO.getLocation().getEmailAddress();
		String state = (String) pfVO.getLocation().getState();
		String city = (String) pfVO.getLocation().getCity();
		String zip = (String) pfVO.getLocation().getZip();
		String soid = (String) pfVO.getSoId();
		Integer primaryIndustryId = (Integer) pfVO.getPrimaryIndustryId();
		Integer workflowStateId = (Integer) pfVO.getWorkflowStateId();
		Integer marketId = (Integer) pfVO.getLocation().getMarketId();
		Integer districtId = (Integer) pfVO.getLocation().getDistrictId();
		Integer regionId = (Integer) pfVO.getLocation().getRegionId();
		Integer referralId = (Integer) pfVO.getReferralId();
		Integer spnetId = pfVO.getSpnetId();

		if ("".equals(username))
		{
			pfVO.getUser().setUserName(null);
		}
		if ("".equals(name))
		{
			pfVO.getUser().setFnameOrLname(null);
		}
		if ("".equals(businessName))
		{
			pfVO.getUser().setBusinessName(null);
		}
		if ("".equals(phoneNumber))
		{
			pfVO.getLocation().setPhoneNumber(null);
		}
		if ("".equals(emailAddress))
		{
			pfVO.getLocation().setEmailAddress(null);
		}
		if ("".equals(state))
		{
			pfVO.getLocation().setState(null);
		}
		if ("".equals(city))
		{
			pfVO.getLocation().setCity(null);
		}
		if ("".equals(zip))
		{
			pfVO.getLocation().setZip(null);
		}
		if ("-1".equals(state))
		{
			pfVO.getLocation().setState(null);
		}

		if (referralId != null && referralId == -1)
		{
			pfVO.setReferralId(null);
		}

		if ("".equals(soid))
		{
			pfVO.setSoId(null);
		}
		if (primaryIndustryId != null && primaryIndustryId == -1)
		{
			pfVO.setPrimaryIndustryId(null);
		}
		if (workflowStateId != null && workflowStateId == -1)
		{
			pfVO.setWorkflowStateId(null);
		}
		if (marketId != null && marketId == -1)
		{
			pfVO.getLocation().setMarketId(null);
		}
		if (districtId != null && districtId == -1)
		{
			pfVO.getLocation().setDistrictId(null);
		}
		if (regionId != null && regionId == -1)
		{
			pfVO.getLocation().setRegionId(null);
		}
		if(spnetId!= null && spnetId == -1){
			pfVO.setSpnetId(null);
		}

		return true;
	}

	private boolean setFieldsNullServiceProviderVO(SearchPortalServiceProviderVO spVO)
	{
		Integer resourceId = (Integer) spVO.getUser().getUserId();
//		Integer companyId = (Integer) spVO.getUser().getCompanyId();
//		String adminName = (String) spVO.getUser().getAdminName();
		String name = (String) spVO.getUser().getFnameOrLname();
		String username = (String) spVO.getUser().getUserName();
		String businessName = (String) spVO.getUser().getBusinessName();
		Date fromSignupDate = (Date) spVO.getUser().getFromSignUpDate();
		Date toSignupDate = (Date) spVO.getUser().getToSignUpDate();
		String phoneNumber = (String) spVO.getLocation().getPhoneNumber();
		String emailAddress = (String) spVO.getLocation().getEmailAddress();
		String state = (String) spVO.getLocation().getState();
		String city = (String) spVO.getLocation().getCity();
		String zip = (String) spVO.getLocation().getZip();
		String soid = (String) spVO.getSoId();
//		Integer primaryIndustryId = (Integer) spVO.getPrimaryIndustryId();
		Integer workflowStateId = (Integer) spVO.getWorkflowStateId();
		Integer marketId = (Integer) spVO.getLocation().getMarketId();
		Integer districtId = (Integer) spVO.getLocation().getDistrictId();
		Integer regionId = (Integer) spVO.getLocation().getRegionId();
		Integer backgroundCheck = (Integer) spVO.getBgCheckStateId();
		Integer spnId = (Integer) spVO.getSpnId();
		Integer spnetId = (Integer) spVO.getSpnetId();
		Integer primarySkillId = (Integer) spVO.getPrimaryVerticleId();
		Integer secondarySkill1 = (Integer) spVO.getSecondarySkillId();
		Integer secondarySkill2 = (Integer) spVO.getSkillCategoryId();

		if ("".equals(username))
		{
			spVO.getUser().setUserName(null);
		}
		if ("".equals(businessName))
		{
			spVO.getUser().setBusinessName(null);
		}
		if ("".equals(phoneNumber))
		{
			spVO.getLocation().setPhoneNumber(null);
		}
		if ("".equals(emailAddress))
		{
			spVO.getLocation().setEmailAddress(null);
		}
		if ("".equals(state))
		{
			spVO.getLocation().setState(null);
		}
		if ("".equals(city))
		{
			spVO.getLocation().setCity(null);
		}
		if ("".equals(zip))
		{
			spVO.getLocation().setZip(null);
		}
		if ("-1".equals(state))
		{
			spVO.getLocation().setState(null);
		}
		if ("".equals(soid))
		{
			spVO.setSoId(null);
		}
//		if (primaryIndustryId == -1)
//		{
//			spVO.setPrimaryIndustryId(null);
//		}
		if (workflowStateId != null && workflowStateId == -1)
		{
			spVO.setWorkflowStateId(null);
		}
		if (marketId != null && marketId == -1)
		{
			spVO.getLocation().setMarketId(null);
		}
		if (districtId != null && districtId == -1)
		{
			spVO.getLocation().setDistrictId(null);
		}
		if (regionId != null && regionId == -1)
		{
			spVO.getLocation().setRegionId(null);
		}
		if (backgroundCheck != null && backgroundCheck == -1)
		{
			spVO.setBgCheckStateId(null);
		}
		if (spnId != null && spnId == -1)
		{
			spVO.setSpnId(null);
		}
		if (primarySkillId != null && primarySkillId == -1)
		{
			spVO.setPrimaryVerticleId(null);
		}
		if (secondarySkill1 != null && secondarySkill1 == -1)
		{
			spVO.setSecondarySkillId(null);

		}
		if (secondarySkill2 != null && secondarySkill2 == -1)
		{
			spVO.setSkillCategoryId(null);

		}
		if (spnetId != null && spnetId == -1)
		{
			spVO.setSpnetId(null);
		}

		return true;
	}

	public String getSecondarySkill1()
	{
		try {
			SearchPortalServiceProviderVO spVO = this.getSearchPortalServiceProviderVO();
			this.setSecondaryList1(lookupBO.getSkillTreeCategoriesOrSubCategories(spVO.getPrimaryVerticleId()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "secondarySkill1";
	}

	public String getSecondarySkill2()
	{
		try {
			SearchPortalServiceProviderVO spVO = this.getSearchPortalServiceProviderVO();
			this.setSecondaryList2(lookupBO.getSkillTreeCategoriesOrSubCategories(spVO.getSecondarySkillId()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "secondarySkill2";
	}

	
	/**
	 *  This method replaces data in the SearchPortalUserVO with a single "'" with "''" to
     * prevent the sql search query from breaking
     * @param searchPortalUserVO
    */	  	          
    private void prepareUserFilterFields(SearchPortalUserVO searchPortalUserVO)
    {
		if(null!=searchPortalUserVO)
		{
		   if(null!=searchPortalUserVO.getFnameOrLname())
		   {
		  	 searchPortalUserVO.setFnameOrLname(searchPortalUserVO.getFnameOrLname().replaceAll("'", "''"));
		  	 //SL-19704
		  	 searchPortalUserVO.setFnameOrLname(searchPortalUserVO.getFnameOrLname().trim());
		   }
	 	   if(null!=searchPortalUserVO.getUserName())
	 	   {
		  	 searchPortalUserVO.setUserName(searchPortalUserVO.getUserName().replaceAll("'", "''"));
		  	 //SL-19704
		  	 searchPortalUserVO.setUserName(searchPortalUserVO.getUserName().trim());		  	 
 	  	   }
		   if(null!=searchPortalUserVO.getBusinessName())
		   {
		  	 searchPortalUserVO.setBusinessName(searchPortalUserVO.getBusinessName().replaceAll("'", "''"));
 	  	   }
		}
	 }
    /**
     * This method replaces data in the SearchPortalLocationVO with a single "'" with "''" to
     * prevent the sql search query from breaking
     * @param searchPortalLocationVO
     */
         	  	          
     private void prepareLocationFilterFields(SearchPortalLocationVO searchPortalLocationVO)
     {
       if(null!=searchPortalLocationVO)
       {
     	 if(null!=searchPortalLocationVO.getCity())
     	 {
   	  	    searchPortalLocationVO.setCity(searchPortalLocationVO.getCity().replaceAll("'", "''"));
         }
    	 if(null!=searchPortalLocationVO.getZip())
    	 {
    	   searchPortalLocationVO.setZip(searchPortalLocationVO.getZip().replaceAll("'", "''"));
     	 }
    	 if(null!=searchPortalLocationVO.getPhoneNumber())
    	 {
    	   searchPortalLocationVO.setPhoneNumber(searchPortalLocationVO.getPhoneNumber().replaceAll("'", "''"));
    	 }
     	 if(null!=searchPortalLocationVO.getEmailAddress())
     	 {
   	  	   searchPortalLocationVO.setEmailAddress(searchPortalLocationVO.getEmailAddress().replaceAll("'", "''"));
    	 }
       }
   	 }
     
     /**
      * This method replaces any single "'" with "''" present in the soId to
      * prevent the sql search query from breaking
      * @param soId
      * @return String soId
      */
     private String prepareSOId(String soId)
     {
       if(null!=soId)
       {
         soId=soId.replaceAll("'", "''");
       }
       return soId;
     }

	private void setContextDetails(){
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public SearchPortalBuyerVO getSearchPortalBuyerVO() {
		return searchPortalBuyerVO;
	}

	public void setSearchPortalBuyerVO(SearchPortalBuyerVO searchPortalBuyerVO) {
		this.searchPortalBuyerVO = searchPortalBuyerVO;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public ISearchPortalBO getSearchPortalBO() {
		return searchPortalBO;
	}

	public void setSearchPortalBO(ISearchPortalBO searchPortalBO) {
		this.searchPortalBO = searchPortalBO;
	}

	public List<SearchPortalBuyerVO> getSearchPortalBuyerResultsVO() {
		return searchPortalBuyerResultsVO;
	}

	public void setSearchPortalBuyerResultsVO(
			List<SearchPortalBuyerVO> searchPortalBuyerResultsVO) {
		this.searchPortalBuyerResultsVO = searchPortalBuyerResultsVO;
	}

	public SearchPortalProviderFirmVO getSearchPortalProviderFirmVO() {
		return searchPortalProviderFirmVO;
	}

	public void setSearchPortalProviderFirmVO(
			SearchPortalProviderFirmVO searchPortalProviderFirmVO) {
		this.searchPortalProviderFirmVO = searchPortalProviderFirmVO;
	}

	public List<SearchPortalProviderFirmVO> getSearchPortalProviderFirmResultsVO() {
		return SearchPortalProviderFirmResultsVO;
	}

	public void setSearchPortalProviderFirmResultsVO(
			List<SearchPortalProviderFirmVO> searchPortalProviderFirmResultsVO) {
		SearchPortalProviderFirmResultsVO = searchPortalProviderFirmResultsVO;
	}

	public List<LookupVO> getPrimarySkillsList() {
		return primarySkillsList;
	}

	public void setPrimarySkillsList(List<LookupVO> primarySkillsList) {
		this.primarySkillsList = primarySkillsList;
	}

	public List<LookupVO> getMarketsList() {
		return marketsList;
	}

	public void setMarketsList(List<LookupVO> marketsList) {
		this.marketsList = marketsList;
	}

	public List<LookupVO> getDistrictsList() {
		return districtsList;
	}

	public void setDistrictsList(List<LookupVO> districtsList) {
		this.districtsList = districtsList;
	}

	public List<LookupVO> getRegionsList() {
		return regionsList;
	}

	public void setRegionsList(List<LookupVO> regionsList) {
		this.regionsList = regionsList;
	}

	public List<LookupVO> getProviderFirmStatusList() {
		return providerFirmStatusList;
	}

	public void setProviderFirmStatusList(List<LookupVO> providerFirmStatusList) {
		this.providerFirmStatusList = providerFirmStatusList;
	}



	/**
	 * @return the providerSearchDelegate
	 */
	public AdminProviderSearchDelegate getProviderSearchDelegate() {
		return providerSearchDelegate;
	}

	/**
	 * @param providerSearchDelegate the providerSearchDelegate to set
	 */
	public void setProviderSearchDelegate(
			AdminProviderSearchDelegate providerSearchDelegate) {
		this.providerSearchDelegate = providerSearchDelegate;
	}

	public SearchPortalServiceProviderVO getSearchPortalServiceProviderVO() {
		return searchPortalServiceProviderVO;
	}

	public void setSearchPortalServiceProviderVO(
			SearchPortalServiceProviderVO searchPortalServiceProviderVO) {
		this.searchPortalServiceProviderVO = searchPortalServiceProviderVO;
	}

	public List<SearchPortalServiceProviderVO> getSearchPortalServiceProviderResultsVO() {
		return SearchPortalServiceProviderResultsVO;
	}

	public void setSearchPortalServiceProviderResultsVO(
			List<SearchPortalServiceProviderVO> searchPortalServiceProviderResultsVO) {
		SearchPortalServiceProviderResultsVO = searchPortalServiceProviderResultsVO;
	}

	public List<LookupVO> getPrimaryIndustryList() {
		return primaryIndustryList;
	}

	public void setPrimaryIndustryList(List<LookupVO> primaryIndustryList) {
		this.primaryIndustryList = primaryIndustryList;
	}

	public List<LookupVO> getBackgroundStatusList() {
		return backgroundStatusList;
	}

	public void setBackgroundStatusList(List<LookupVO> backgroundStatusList) {
		this.backgroundStatusList = backgroundStatusList;
	}

	public List<LookupVO> getProviderNetworkList() {
		return providerNetworkList;
	}

	public void setProviderNetworkList(List<LookupVO> providerNetworkList) {
		this.providerNetworkList = providerNetworkList;
	}

	public List<LookupVO> getServiceProviderStatusList() {
		return serviceProviderStatusList;
	}

	public void setServiceProviderStatusList(
			List<LookupVO> serviceProviderStatusList) {
		this.serviceProviderStatusList = serviceProviderStatusList;
	}

	public ArrayList<SkillNodeVO> getSecondaryList1() {
		return secondaryList1;
	}

	public void setSecondaryList1(ArrayList<SkillNodeVO> secondaryList1) {
		this.secondaryList1 = secondaryList1;
	}

	public ArrayList<SkillNodeVO> getSecondaryList2() {
		return secondaryList2;
	}

	public void setSecondaryList2(ArrayList<SkillNodeVO> secondaryList2) {
		this.secondaryList2 = secondaryList2;
	}

	public Integer getTabInSession() {
		return tabInSession;
	}

	public void setTabInSession(Integer tabInSession) {
		this.tabInSession = tabInSession;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */


	/**
	 * @return the referralsList
	 */
	public List<LookupVO> getReferralsList() {
		return referralsList;
	}


	/**
	 * @param referralsList the referralsList to set
	 */
	public void setReferralsList(List<LookupVO> referralsList) {
		this.referralsList = referralsList;
	}


	public List<LookupVO> getStatesList() {
		return statesList;
	}


	public void setStatesList(List<LookupVO> statesList) {
		this.statesList = statesList;
	}


	public String getSpEditProfile() {
		return spEditProfile;
	}


	public void setSpEditProfile(String spEditProfile) {
		this.spEditProfile = spEditProfile;
	}
	
	/**
	 * Sets the persmissions in the request and will be accessed in jsp 
	 * before displaying reset_password option in search portal. 
	 */
	private void setPersmissionsForReset(){
		//load resetPassword permission here
		//Boolean passwordResetForSLAdmin = (Boolean)getSession().getAttribute("passwordResetForSLAdmin");
		//Boolean passwordResetForAllExternalUsers = (Boolean) getSession().getAttribute("passwordResetForAllExternalUsers");
		securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		if (securityContext != null) {
			boolean passwordResetForSLAdmin = securityContext.getRoles().isPasswordResetForSLAdmin();
			boolean passwordResetForAllExternalUsers = securityContext.getRoles().isPasswordResetForAllExternalUsers();
			boolean permissionForProviderNameChange = securityContext.getRoles().isPermissionForProviderNameChange();  //SL-18330 for providername change
			
			if (passwordResetForSLAdmin == true)
				setAttribute("passwordResetForSLAdmin", "true");
			if (passwordResetForAllExternalUsers == true)		
				setAttribute("passwordResetForAllExternalUsers", "true");
			
			//SL-18330 For Provider Name Change START
			if (permissionForProviderNameChange == true)
				setAttribute("permissionForProviderNameChange", "true");				// For permission
			
			setAttribute("loginusername",securityContext.getRoles().getUsername());  // to update the modify by in audit table
			logger.info("Username value is - "+securityContext.getRoles().getUsername());
			//SL-18330 For Provider Name Change END
		}
	}
	
	/**
	 * Added by Shekhar to perform Reset Password functionality
	 * @return
	 * @throws Exception
	 */
	
	public String resetPasswordProvider() throws Exception{
		boolean flag = resetPassword(OrderConstants.PROVIDER_ROLEID);
		return "reset";
	}
	
	public String resetPasswordProviderFirm() throws Exception{
		boolean flag = resetPassword(OrderConstants.PROVIDER_ROLEID);
		SearchPortalProviderFirmVO pfVO = 
			(SearchPortalProviderFirmVO)ActionContext.getContext().getSession().get("searchPortalProviderFirmVO");
		return searchProviderFirm(pfVO);
	}
	
	public String resetPasswordBuyer() throws Exception{
		boolean flag = resetPassword(OrderConstants.BUYER_ROLEID);
		return "reset";
	}
	
	private boolean resetPassword(int roleId) throws Exception{
		if(!resetPasswordFlow){
			resetPasswordFlow = true;
		}
		if(resourceId == null)
			return true;
		String username = manageUsersDelegate.getUserNameFromResourceId(resourceId, roleId);
		boolean flag = manageUsersDelegate.resetPassword(username);
		if (flag) {
			System.out.println("Password has been reset for user:" + username);
			setActionMessage(Config.getResouceBundle().getString("resetPassword.adminResetPassword.success"));
			return true;
		} else {
			logger.info("Error in reseting password for user " + username);
			setActionError(Config.getResouceBundle().getString("resetPassword.adminResetPassword.error"));
			return false;
		}
	}
	//SL-15642 - For permission based display of Order Management tab.
	private void checkOrderManagementPermission(Integer vendorId) {
		try{
			//check for order management flag in appication_properties
			String omFlag = applicationProperties.getPropertyFromDB(Constants.AppPropConstants.ORDER_MANAGEMENT_FLAG);
			if (null != omFlag){
				if(omFlag.equals("ON")){
					//check for firm id in order_management_permission
					Integer result = securityDel.getOmPermissionForFirm(vendorId);
					if(result!=null){
						getSession().setAttribute("omTabView", "true");
					}else{
						getSession().setAttribute("omTabView", "false");
					}
				}
				else{
					getSession().setAttribute("omTabView", "true");
				}
			}
			
		}
		catch(Exception e){
			logger.error("Error occured while checking for order management permission", e);
		}
	}
	//NS-104 - For permission based display of lead Management tab.
	private boolean checkLeadManagementPermission(Integer roleId) {
		try{
			//check for lead management flag in appication_properties
			String leadManagmentFlag="";
			if(roleId==1)
			{
				 leadManagmentFlag=Constants.AppPropConstants.PROVIDER_LEAD_MANAGEMENT_FLAG;
			}
			else if(roleId==3)
			{
				leadManagmentFlag=Constants.AppPropConstants.BUYER_LEAD_MANAGEMENT_FLAG;
			}
			
				String lmFlag = applicationProperties.getPropertyFromDB(leadManagmentFlag);
				if (null != lmFlag){
					if(lmFlag.equals("ON")){
					return true;	
					}
					else
					{
					return false;	
					}
					}
				
			
		}
			catch(Exception e){
				logger.error("Error occured while checking for lead management permission", e);
			}
			return false;
		}
	/**
	 * @return the spnetworkBO
	 */
	public ISPNetworkBO getSpnetworkBO() {
		return spnetworkBO;
	}


	/**
	 * @param spnetworkBO the spnetworkBO to set
	 */
	public void setSpnetworkBO(ISPNetworkBO spnetworkBO) {
		this.spnetworkBO = spnetworkBO;
	}


	/**
	 * @return the spnetList
	 */
	public List<LookupVO> getSpnetList() {
		return spnetList;
	}


	/**
	 * @param spnetList the spnetList to set
	 */
	public void setSpnetList(List<LookupVO> spnetList) {
		this.spnetList = spnetList;
	}


	public ISecurityDelegate getSecurityDel() {
		return securityDel;
	}


	public void setSecurityDel(ISecurityDelegate securityDel) {
		this.securityDel = securityDel;
	}


	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}


	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public ISODashBoardDelegate getDashboardDelegate() {
		return dashboardDelegate;
	}

	public void setDashboardDelegate(ISODashBoardDelegate dashboardDelegate) {
		this.dashboardDelegate = dashboardDelegate;
	}
}
