package com.newco.marketplace.web.action.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO;
import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNetCommMonitorVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.ISODashBoardDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.delegates.adminlogging.IAdminLoggingDelegate;
import com.newco.marketplace.web.dto.CommMonitorRowDTO;
import com.newco.marketplace.web.dto.LeadDashboardVitalStatsDTO;
import com.newco.marketplace.web.dto.ServiceOrderDashboardVitalStatsDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.adminlogging.AdminLoggingDto;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class DashboardAction extends SLBaseAction implements Preparable
{
	private static final long serialVersionUID = 10002;// arbitrary number to
	// get rid of warning
	private static final Logger logger = Logger.getLogger("DashboardAction");
	public static final String  ADMIN_FROM_JS =  "-1";

	private ISOWizardFetchDelegate fetchDelegate; 
	private final ISODashBoardDelegate dashboardDelegate;
	private AdminLoggingDto adminLoggingDto;
	private final IAdminLoggingDelegate adminLoggingDelegate;
	private ISPNBuyerDelegate spnBuyerDelegate;
	private ISPNMonitorBO spnMonitorBO;
	private final ISelectProviderNetworkBO spnCreateUpdateBO;
	private ILookupDelegate lookupDelegate;
	
	
	
	//this is safe action are always prototype
	//needed for redirect simple to monitor
	private String displayTab;
	private Map sSessionMap;
	private String resourceId;
	private String spEditProfile;
	private List<SPNMonitorVO> miniSPNList;
	private Integer isSPNMonitorAvailable = 0;
	private Integer showLeadsSignUp = 0;
	private Integer newLeadsTCIndicator = null;
	private String newTandCContent;

	public ISPNBuyerDelegate getSpnBuyerDelegate() {
		return spnBuyerDelegate;
	}

	public void setSpnBuyerDelegate(ISPNBuyerDelegate spnBuyerDelegate) {
		this.spnBuyerDelegate = spnBuyerDelegate;
	}

	public DashboardAction(ISODashBoardDelegate delegate,
			IAdminLoggingDelegate adminLoggingDelegate, ISOMonitorDelegate sodelegate,
			ISelectProviderNetworkBO selectProviderNetworkBO, ILookupDelegate lookupDelegate)
	{
		this.dashboardDelegate = delegate;
		this.adminLoggingDelegate = adminLoggingDelegate;
		super.soMonitorDelegate = sodelegate;
		this.spnCreateUpdateBO = selectProviderNetworkBO;
		this.lookupDelegate = lookupDelegate;
	}

	public ISOWizardFetchDelegate getFetchDelegate()
	{
		return fetchDelegate;
	}

	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate)
	{
		this.fetchDelegate = fetchDelegate;
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception
	{
		String isFromPA = (String)getRequest().getSession().getAttribute(OrderConstants.IS_FROM_PA);
		if (OrderConstants.TRUE.equals(isFromPA))
		{
			SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
			securityContext.setRoleId(OrderConstants.NEWCO_ADMIN_ROLEID);
			securityContext.setUsername(securityContext.getSlAdminUName());
			securityContext.setRole(OrderConstants.NEWCO_ADMIN);
			//Refer to LoginAction where companyId is set to 99 by default
			securityContext.setCompanyId(99);
			//Set by default
			securityContext.setVendBuyerResId(-1);

			ActionContext.getContext().getSession().remove(OrderConstants.VENDOR_ID);
			ActionContext.getContext().getSession().remove(OrderConstants.RESOURCE_ID);
			ActionContext.getContext().getSession().remove(OrderConstants.AUDIT_TASK);
			ActionContext.getContext().getSession().remove(OrderConstants.IS_FROM_PA);
			ActionContext.getContext().getSession().remove("skuCountOfPrimaryIndustry");

		}
		createCommonServiceOrderCriteria();
		// Clearing the session objects of Service Order Wizard
		logger.debug("Invoking getStartPointAndInvalidate()" );
		//Added for  Incidents 4131093 (Sl-19820)
		String soId = getParameter("soId");
		setAttribute(OrderConstants.SO_ID, soId);
		SOWSessionFacility.getInstance().getStartPointAndInvalidate(fetchDelegate, get_commonCriteria().getSecurityContext());

	}

	@Override
	public String execute() throws Exception
	{

		String userRole = get_commonCriteria().getRoleType();
		SecurityContext securityContext = (SecurityContext) getSession()
				.getAttribute(OrderConstants.SECURITY_CONTEXT);

		logger.info(get_commonCriteria().getRoleType() + "< Role Type  :  Role Id >"
				+ securityContext.getRoleId());
		logger.info(OrderConstants.PROVIDER_ROLEID
				+ "< Constant Role Type : > Vendorrscthing"
				+ securityContext.getVendBuyerResId());
		logger.info(OrderConstants.PROVIDER_ROLEID
				+ "< Constant Role Type : > Company_id "
				+ securityContext.getCompanyId());
logger.info("adopted or not :"+securityContext.isAdopted());
logger.info("sladmin or not :"+securityContext.isSlAdminInd());
logger.info("what is the role :"+securityContext.getRole());
logger.info("viewOrderPricing"+get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"));

      //SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000
		String forceActivePropertyIndicator = PropertiesUtils
				.getPropertyValue(OrderConstants.FORCE_ACTIVE_PROPERTY_INDICATOR);
		if (null != forceActivePropertyIndicator
				&& forceActivePropertyIndicator.equalsIgnoreCase("true")) {
			getSession().setAttribute("System_Property_ForceActiveInd",
					Boolean.TRUE);
		} else {
			getSession().setAttribute("System_Property_ForceActiveInd",
					Boolean.FALSE);
		}

		logger.info("(Table: application_properties) ForceActiveInd is: "
				+ getSession().getAttribute(
						"System_Property_ForceActiveInd"));
		// Ending for Forceful CAR Activation
		
		// Added for D2C provider Permission start.
		String countVal = buyerSkuPrimaryIndustry();
		Integer count = Integer.parseInt(countVal);
		logger.info("skuCount Value is:"+count);
		if (count > 0) {
			getSession().setAttribute("skuCountOfPrimaryIndustry", true);
		}
		// Added for D2C provider Permission End.
	Integer roleId = securityContext.getRoleId();

		if(roleId == null)
		{
			return "failure";
		}

		else if (roleId == OrderConstants.PROVIDER_ROLEID)
		{
			// Need to add the delagate here to redirect
			// insert admin logging
			if (securityContext.isSlAdminInd())
				insertAdminLogging();
			userRole = OrderConstants.ROLE_PROVIDER;
			logger.info("I think Im a provider ");
			if (dashboardDelegate.isIncompleteProvider(securityContext
					.getCompanyId()))
			{
				logger.info("redirecting to incomplete provider");

				return "redirectIncompleteProvider";
			}
			//SL-17698: checking whether the provider is logging in for the first time 
			//after member offers went live
			int indicator = 0;
			boolean isAdoptedProvider = this._commonCriteria.getSecurityContext().isAdopted();
			if(!isAdoptedProvider){
				Integer vendorId = get_commonCriteria().getVendBuyerResId();
				indicator = dashboardDelegate.showMemberOffers(vendorId).intValue();
			}			
			getSession().setAttribute("indicator", indicator);
			//loads CAR details of providers for auto acceptance
			this.loadProviderDetails();
			//SL-16934: checking whether it is a non-leads firm
			Integer vendorId = get_commonCriteria().getCompanyId();
			showLeadsSignUp = dashboardDelegate.showLeadsSignUp(vendorId).intValue();
			//SL-19293: to fetch leads T&C version from vendor_lead_profile. If version is 0 popup will be displayed.
			//If version is 1 SL Leads Addendum link will be displayed in footer and Lead Gen. Fees link will be displayed in ServiceLive Wallet.
			if(showLeadsSignUp >= OrderConstants.CONSTANT_1){
				Integer tcIndicator = dashboardDelegate.showLeadsTCIndicator(vendorId);
				TermsAndConditionsVO termsAndConditionsVO = lookupDelegate.getTermsConditionsContent(OrderConstants.SL_LEADS_ADDENDUM_NEW_TANDC);
				if(null != termsAndConditionsVO){
					newTandCContent = termsAndConditionsVO.getTermsCondContent();
				}
				if(null != tcIndicator){
					newLeadsTCIndicator = tcIndicator.intValue();
					//No need to display T&C popup when provider is adopted by admin.
					if(isAdoptedProvider && newLeadsTCIndicator == OrderConstants.LEADS_TC_VERSION_0){
						newLeadsTCIndicator = null;
					}
				}
				
			}
			String leadsAddendumLink = PropertiesUtils.getPropertyValue(OrderConstants.SL_LEADS_ADDENDUM_LINK);
			getSession().removeAttribute("slLeadsAddendumLink");
			getSession().removeAttribute("newLeadsTCIndicator");
			
			getSession().setAttribute("slLeadsAddendumLink", leadsAddendumLink);
			getSession().setAttribute("newLeadsTCIndicator", newLeadsTCIndicator);
			getSession().setAttribute("showLeadsSignUp", showLeadsSignUp);
			//set the content to request scope
			setAttribute("newTandCContent", newTandCContent);
			
			//SLT-2235:
			try{
				ActionContext.getContext().getSession().put("termsLegalNoticeChecked", dashboardDelegate.isLegalNoticeChecked(vendorId));
				ActionContext.getContext().getSession().put("isSLAdmin",securityContext.isSlAdminInd());
				ActionContext.getContext().getSession().put("isPrimaryResource", securityContext.isPrimaryInd());
			}
			catch(Exception e){
				logger.error("DashboardAction->Exception in isLegalNoticeChecked() method: "+e);
			}
		}

		else if (roleId == OrderConstants.NEWCO_ADMIN_ROLEID)
		{
			logger.info("redirecting to admin");

			return "redirectAdmin";
		}

		else if(roleId == OrderConstants.BUYER_ROLEID)
		{
			if (securityContext.isSlAdminInd())
				insertAdminLogging();
			userRole = OrderConstants.ROLE_BUYER;
		} else if(roleId == OrderConstants.SIMPLE_BUYER_ROLEID) {
			userRole = OrderConstants.ROLE_SIMPLE_BUYER;			
		}
		
		setAttribute("userRole", userRole);
		
		// Only Show Communications Monitor for Provider Admin - for now
		//R16_0 SL-20548: Show Communications Monitor for all providers having permission 66- Manage SPN Invitations
		//For provider admins by default this permission will be present
		if(get_commonCriteria() != null && get_commonCriteria().getRoleId() == OrderConstants.PROVIDER_ROLEID && (securityContext.isPrimaryInd() || (null!= securityContext.getRoleActivityIdList() && securityContext.getRoleActivityIdList().containsKey(OrderConstants.SPN_INVITE_PERMISSION))))
		{
			setAttribute(OrderConstants.DISPLAY_COMMUNICATION_MONITOR, true);
		}
		Integer vendorId = get_commonCriteria().getSecurityContext().getCompanyId();
		//checking whether SPN applicant and setting it to SecurityContext
		if (userRole.equalsIgnoreCase(OrderConstants.ROLE_PROVIDER) && (securityContext.isPrimaryInd() || (null!= securityContext.getRoleActivityIdList() && securityContext.getRoleActivityIdList().containsKey(OrderConstants.SPN_INVITE_PERMISSION))) ){
		try{
			isSPNMonitorAvailable = spnMonitorBO.isVendorSPNApplicant(vendorId);
			securityContext.setSpnMonitorAvailable(isSPNMonitorAvailable);
		}catch(Exception e){
			logger.error("Caught exception and ignoring.."+e);
		}
		}
		// If we get here we are either a 'Buyer' or 'Provider'
		initModules(userRole);
		initVitalStatistics(userRole);
		
		//R16_0 SL-20548: Show Communications Monitor for all providers having permission 66- Manage SPN Invitations
		//For provider admins by default this permission will be present
		if(securityContext.isPrimaryInd() || (null!= securityContext.getRoleActivityIdList() && securityContext.getRoleActivityIdList().containsKey(OrderConstants.SPN_INVITE_PERMISSION)))
		{
			initCommunicationsMonitorForPA(userRole);
		}
		initDate();
		
		if (ADMIN_FROM_JS.equals(this.resourceId) && (OrderConstants.SP_EDIT_PROFILE_2).equals(this.spEditProfile))
		{
			return "gotoEditCompanyProfile";
		}
		else if (this.resourceId != null && (OrderConstants.SP_EDIT_PROFILE_1).equals(this.spEditProfile))
		{
			setAttribute(OrderConstants.RESOURCE_ID, this.resourceId);
			return "gotoEditProfile";
		}
		getSession().removeAttribute("omDisplayTab");
		
		return SUCCESS;
//		if(roleId == OrderConstants.SIMPLE_BUYER_ROLEID) {
//			return "simpleBuyerDashboard";
//		} else {		
//			return SUCCESS;
//		}
		
	}
	/**
	 * updates the accepted New T&C Indicator version
	 */
	public void updateNewTandC() {
		
		Integer vendorId = get_commonCriteria().getCompanyId();
		String userName = get_commonCriteria().getTheUserName();
		try {
			dashboardDelegate.updateNewTandC(vendorId, userName);
		} catch (Exception bse) {
			logger.error("Exception in DashboardAction --> updateNewTandC()"
					+ bse.getMessage());
		}
	}

	private void initVitalStatistics(String userRole)
	{

		String companyId = null;
		String resourceId = null;
		Integer roleIdInt = null;

		LoginCredentialVO context = get_commonCriteria().getSecurityContext()
				.getRoles();

		logger.info("inside dashaction" + context.getRoleId());
		if (context != null)
		{
			// Extract info about our user
			companyId = get_commonCriteria().getSecurityContext().getCompanyId()
					+ "";
			// Don't set Resource ID for Buyer

			if (get_commonCriteria().getSecurityContext().isSlAdminInd())
			{
				resourceId = get_commonCriteria().getSecurityContext()
						.getVendBuyerResId()
						+ "";
				roleIdInt = get_commonCriteria().getSecurityContext().getRoleId();


				if(roleIdInt == OrderConstants.PROVIDER_ROLEID)
				{
					boolean regComplete = !(dashboardDelegate
							.isIncompleteProvider(get_commonCriteria()
									.getSecurityContext().getCompanyId()));
					get_commonCriteria().getSecurityContext()
							.setRegComplete(regComplete);
				}


				logger.info("details for sladmin as*** " + companyId + "---"
						+ roleIdInt + "--"
						+ get_commonCriteria().getSecurityContext().getUsername());
				context.setRoleId(roleIdInt);
				context.setCompanyId(get_commonCriteria().getSecurityContext()
						.getCompanyId());
				context.setVendBuyerResId(get_commonCriteria().getSecurityContext()
						.getVendBuyerResId());
				get_commonCriteria().getSecurityContext().setRoles(context);

				get_commonCriteria().setRoleId(roleIdInt);
			}
			else
			{
				if (context.getRoleId() == OrderConstants.PROVIDER_ROLEID)
				{
					resourceId = get_commonCriteria().getSecurityContext()
							.getVendBuyerResId()
							+ "";
				}

				roleIdInt = context.getRoleId();
			}
		}
		else
		{
			companyId = OrderConstants.COMPANY_ID_2;
			resourceId = null;
			roleIdInt = 1;
		}

		
		SurveyRatingsVO surveyRatingsVO = (SurveyRatingsVO)getSession().getAttribute(OrderConstants.LIFETIME_RATINGS_VO);
		
		SecurityContext secContxt = get_commonCriteria().getSecurityContext();
		Map<String, UserActivityVO> activities = secContxt.getRoleActivityIdList();
		boolean manageSOFlag = false;
		//Checking whether the user has the rights to manage SO[4 is the Activity Id for the Activity-Manage Service Orders]
		// and not an admin, then search for specific resourceId only
		if(activities != null && activities.containsKey("4") && !secContxt.isPrimaryInd() && !secContxt.isDispatchInd()){
			manageSOFlag = true;
		}
		
		boolean leadMapflag=true;
		if(null!=getSession().getAttribute("providerLeadManagementPermission")){
			
			Object providerLeadManagementPermission=getSession().getAttribute("providerLeadManagementPermission");
			
		}
		
		
		SODashboardVO dashboardVO = dashboardDelegate.getDashBoardWidgetDetails(companyId,
				resourceId, roleIdInt, surveyRatingsVO, manageSOFlag,leadMapflag);
		/**Checking buyerFeture set for the buyer to display pending cancel order*/
		boolean isNonFunded=false;
		if(null != dashboardVO && roleIdInt == 3 ){
			isNonFunded = dashboardDelegate.isNonFundedBuyer(companyId);
		
		}
		
		
		
		if (dashboardVO == null)
			return;
		// Set stars lists
		/*
		 * Commented the old logic to determine how to show the rating using
		 * star shape images
		 * dto.setCurrentStars(getStarsFromRating(dto.getCurrentRating()));
		 * dto.setGivenStars(getStarsFromRating(dto.getGivenRating()));
		 * dto.setLifetimeStars(getStarsFromRating(dto.getLifetimeRating()));
		 */

		// Calculate the image postfix number associated for each decimal rating
		// and set it in the DTO.
		// This number will then be used in JSP to show appropriate star image.
		if (dashboardVO.getCurrentRating() != null)
			dashboardVO.setCurrentStarsNumber(UIUtils.calculateScoreNumber(dashboardVO
					.getCurrentRating()));
		if (dashboardVO.getGivenRating() != null)
			dashboardVO.setGivenStarsNumber(UIUtils.calculateScoreNumber(dashboardVO
					.getGivenRating()));
		if (dashboardVO.getLifetimeRating() != null)
			dashboardVO.setLifetimeStarsNumber(UIUtils.calculateScoreNumber(dashboardVO
					.getLifetimeRating()));

		// To convert amount into two decimal places
		double totalAmt = dashboardVO.getTotalDollars();
		String totalAmtInDecimal = UIUtils.formatDollarAmount(totalAmt);
		setAttribute(OrderConstants.TOTAL_AMOUNT_IN_DECIMAL, totalAmtInDecimal);

		setAttribute(OrderConstants.DASHBOARD_DTO, dashboardVO);
		double curr  = dashboardVO.getCurrentBalance();
		double avail = dashboardVO.getAvailableBalance();
		dashboardVO.setCurrentBalanceFormat(java.text.NumberFormat.getCurrencyInstance().format(curr));
		dashboardVO.setAvailableBalanceFormat(java.text.NumberFormat.getCurrencyInstance().format(avail));

		ServiceOrderDashboardVitalStatsDTO vsDTO = null;
		if (userRole.indexOf(OrderConstants.ROLE_SIMPLE_BUYER) >= 0){   
			vsDTO = new ServiceOrderDashboardVitalStatsDTO();
			vsDTO.setBuyerLabels();
			// TODO, extract these values from the dto.
			vsDTO.getValues().add(dashboardVO.getDrafted());
			vsDTO.getValues().add(dashboardVO.getAccepted());
			vsDTO.getValues().add(dashboardVO.getPosted());
			vsDTO.getValues().add(dashboardVO.getProblem());
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("color: #F00");
			vsDTO.getIdList().add(OrderConstants.DRAFTED_STRING);
			vsDTO.getIdList().add(OrderConstants.ACCEPTED_STRING);
			vsDTO.getIdList().add(OrderConstants.POSTED_STRING);
			vsDTO.getIdList().add(OrderConstants.PROBLEM_STRING);
		} else if (userRole.indexOf(OrderConstants.ROLE_BUYER) >= 0) {
			if(isNonFunded){
				vsDTO = new ServiceOrderDashboardVitalStatsDTO("nonFundedBuyer");
				vsDTO.setIsNonFundedBuyer("true");
			}else{
				vsDTO = new ServiceOrderDashboardVitalStatsDTO();
			}
			vsDTO.setBuyerLabels();
			// TODO, extract these values from the dto.
			vsDTO.getValues().add(dashboardVO.getDrafted());
			vsDTO.getValues().add(dashboardVO.getAccepted());
			vsDTO.getValues().add(dashboardVO.getPosted());
			vsDTO.getValues().add(dashboardVO.getProblem());
			
			if(!isNonFunded){
			   vsDTO.getValues().add(dashboardVO.getPendingCancel());
			}
			//R12_1
			//SL-20362
			vsDTO.getValues().add(dashboardVO.getPendingReschedule());
			
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("color: #F00");
			vsDTO.getTextColors().add("");
			//R12_1
			//SL-20362
			vsDTO.getTextColors().add("");
			vsDTO.getIdList().add(OrderConstants.DRAFTED_STRING);
			vsDTO.getIdList().add(OrderConstants.ACCEPTED_STRING);
			vsDTO.getIdList().add(OrderConstants.POSTED_STRING);
			vsDTO.getIdList().add(OrderConstants.PROBLEM_STRING);
			vsDTO.getIdList().add(OrderConstants.TODAY_STRING);
			vsDTO.getIdList().add(OrderConstants.FM_SEARCH);
			vsDTO.getTabStatus().add("");
			vsDTO.getTabStatus().add("");
			vsDTO.getTabStatus().add("");
			vsDTO.getTabStatus().add("");
			if(!isNonFunded){
			   vsDTO.getTabStatus().add(String.valueOf(OrderConstants.PENDING_CANCEL_STATUS));
			}
			//R12_1
			//SL-20362
			vsDTO.getTabStatus().add("");
		}
		if (userRole.indexOf("Provider") >= 0){
			vsDTO=new ServiceOrderDashboardVitalStatsDTO();
			vsDTO.setProviderLabels();
			vsDTO.getValues().add(dashboardVO.getTodays());
			vsDTO.getValues().add(dashboardVO.getAccepted());			
			vsDTO.getValues().add(dashboardVO.getReceived());
			vsDTO.getValues().add(dashboardVO.getBid());			
			vsDTO.getValues().add(dashboardVO.getProblem());
			vsDTO.getValues().add(dashboardVO.getBulletinBoard());			
			vsDTO.getValues().add(dashboardVO.getPendingCancel());
			//R12_1
			//SL-20362
			vsDTO.getValues().add(dashboardVO.getPendingReschedule());
			//SL-21645
			vsDTO.getValues().add(dashboardVO.getEstimationRequest());
			
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("color: #F00");
			vsDTO.getTextColors().add("");
			vsDTO.getTextColors().add("");
			//R12_1
			//SL-20362
			vsDTO.getTextColors().add("");
			
			
			vsDTO.getIdList().add(OrderConstants.TODAYS_STRING);
			vsDTO.getIdList().add(OrderConstants.RECEIVED_STRING);			
			vsDTO.getIdList().add(OrderConstants.ACCEPTED_STRING);
			vsDTO.getIdList().add(OrderConstants.BID_REQUESTS_STRING);
			vsDTO.getIdList().add(OrderConstants.PROBLEM_STRING);
			vsDTO.getIdList().add(OrderConstants.BULLETIN_BOARD_STRING);			
			vsDTO.getIdList().add(OrderConstants.PENDING_CANCEL_STRING);
			//R12_1
			//SL-20362
			vsDTO.getIdList().add(OrderConstants.FM_SEARCH);
			//SL-21645
			vsDTO.getIdList().add(OrderConstants.ESTIMATION_REQUEST_STRING);
			
			
			vsDTO.getTabStatus().add("");
			vsDTO.getTabStatus().add("");
			vsDTO.getTabStatus().add("");
			vsDTO.getTabStatus().add("");
			vsDTO.getTabStatus().add("");
			vsDTO.getTabStatus().add("");
			vsDTO.getTabStatus().add(String.valueOf(OrderConstants.PENDING_CANCEL_STATUS));
			///R12_1
			//SL-20362
			vsDTO.getTabStatus().add("");
			
			
			// Total Value Received
			vsDTO.setTotalValueReceived(dashboardVO.getTotalDollars());
		}
		
		
		
		setAttribute(OrderConstants.VITAL_STATS_DTO, vsDTO);
		
	// if 	leadMapflag is true. 
		if(leadMapflag){
		LeadDashboardVitalStatsDTO leadDashboardVitalStatsDTO=new LeadDashboardVitalStatsDTO();
		
		if (userRole.indexOf("Provider") >= 0)
		{
			leadDashboardVitalStatsDTO.setProviderLabels();
			
			
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getStatusNew());
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getCompleted());
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getWorking());
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getCancelled());
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getScheduled());
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getStale());
			
			// set Total Leads,Conversion Rate and Average Response Time.
			
			leadDashboardVitalStatsDTO.setTotalLeads(dashboardVO.getTotalLeads());
			leadDashboardVitalStatsDTO.setConversionRate(dashboardVO.getConversionRate());
			leadDashboardVitalStatsDTO.setAverageResponseTime(dashboardVO.getAverageResponse());
			leadDashboardVitalStatsDTO.setGoal(dashboardVO.getGoal());
		
			
			leadDashboardVitalStatsDTO.getTextColors().add("");
			leadDashboardVitalStatsDTO.getTextColors().add("");
			leadDashboardVitalStatsDTO.getTextColors().add("");
			leadDashboardVitalStatsDTO.getTextColors().add("");
			//vsDTO.getTextColors().add("color: #F00");
			leadDashboardVitalStatsDTO.getTextColors().add("");
			leadDashboardVitalStatsDTO.getTextColors().add("");
			
			leadDashboardVitalStatsDTO.getIdList().add("new");
			leadDashboardVitalStatsDTO.getIdList().add("completed");			
			leadDashboardVitalStatsDTO.getIdList().add("working");
			leadDashboardVitalStatsDTO.getIdList().add("cancelled");
			leadDashboardVitalStatsDTO.getIdList().add("scheduled");
			leadDashboardVitalStatsDTO.getIdList().add("stale");
			
			leadDashboardVitalStatsDTO.getTabStatus().add("");
			leadDashboardVitalStatsDTO.getTabStatus().add("");
			leadDashboardVitalStatsDTO.getTabStatus().add("");
			leadDashboardVitalStatsDTO.getTabStatus().add("");
			leadDashboardVitalStatsDTO.getTabStatus().add("");
			leadDashboardVitalStatsDTO.getTabStatus().add("");
			
			// Total Value Received
		//	vsDTO.setTotalValueReceived(dashboardVO.getTotalDollars());
		}else if (userRole.indexOf(OrderConstants.ROLE_BUYER) >= 0) {
			
			
              leadDashboardVitalStatsDTO.setBuyerLabels();
			
			
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getBuyerUnMatched());
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getBuyerMatched());
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getBuyerCompleted());
			leadDashboardVitalStatsDTO.getValues().add(dashboardVO.getBuyerCancelled());
			
			
			// set Total Leads,Conversion Rate and Average Response Time.
			
			leadDashboardVitalStatsDTO.setTotalLeads(dashboardVO.getTotalLeads());
			leadDashboardVitalStatsDTO.setConversionRate(dashboardVO.getConversionRate());
			leadDashboardVitalStatsDTO.setAverageResponseTime(dashboardVO.getAverageResponse());
			leadDashboardVitalStatsDTO.setGoal(dashboardVO.getGoal());
		
			
			leadDashboardVitalStatsDTO.getTextColors().add("");
			leadDashboardVitalStatsDTO.getTextColors().add("");
			leadDashboardVitalStatsDTO.getTextColors().add("");
			leadDashboardVitalStatsDTO.getTextColors().add("");
			//vsDTO.getTextColors().add("color: #F00");
			
			
			leadDashboardVitalStatsDTO.getIdList().add("unmatched");
			leadDashboardVitalStatsDTO.getIdList().add("matched");			
			leadDashboardVitalStatsDTO.getIdList().add("completed");
			leadDashboardVitalStatsDTO.getIdList().add("cancelled");
			
			leadDashboardVitalStatsDTO.getTabStatus().add("");
			leadDashboardVitalStatsDTO.getTabStatus().add("");
			leadDashboardVitalStatsDTO.getTabStatus().add("");
			leadDashboardVitalStatsDTO.getTabStatus().add("");

			
		}
		
		setAttribute("leadDashboardVitalStatsDTO", leadDashboardVitalStatsDTO);
		
	}

		
		//SL 15642 Start-Changes for permission based price display in Dash board
	
		if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey(VIEW_ORDER_PRICING))
				{
			
					setAttribute("viewOrderPricing",true);
					getSession().setAttribute("viewOrderPricing",true);
					
				}
				

		//SL 15642 End-Changes for permission based price display in Dash board
		Integer vendorId = get_commonCriteria().getSecurityContext().getCompanyId();

		SODashboardVO soDashboardVO = dashboardDelegate.getApprovedUnapprovedCountsAndFirmStatus(vendorId);

		setAttribute(OrderConstants.PROVIDER_FIRM_DETAILS,soDashboardVO);
		//for SPN Monitor display		
		try{
			if(isSPNMonitorAvailable > 0){
				List<SPNMonitorVO> spnList = spnMonitorBO.getSPNMonitorList(vendorId);
				setMiniSPNList(spnList);		
			}	
		}catch(Exception e){
			logger.error("Caught Exception and ignoring"+e);
		}
	}

	private void initModules(String userRole)
	{
		if (userRole.equalsIgnoreCase(OrderConstants.ROLE_BUYER))
		{
			initAdminBuyerModules();
		}
		else if (userRole.equalsIgnoreCase(OrderConstants.ROLE_ASSOC_BUYER))
		{
			initAssocBuyerModules();
		}
		else if (userRole.equalsIgnoreCase(OrderConstants.ROLE_PROVIDER))
		{
			initAdminProviderModules();
		}
		else if (userRole.equalsIgnoreCase(OrderConstants.ROLE_DISPATCHER_PROVIDER))
		{
			initDispatcherProviderModules();
		}
		else if (userRole.equalsIgnoreCase(OrderConstants.ROLE_TECHNICIAN_PROVIDER))
		{
			initTechnicianProviderModules();
		}
		else if (userRole.equalsIgnoreCase(OrderConstants.ROLE_SIMPLE_BUYER))
		{
			initSimpleBuyerModules();
		}
		else
		{
			// TODO Handle unknown role here
		}
		
		

	}

	private void initCommunicationsMonitor(String userRole)
	{
		List<CommMonitorRowDTO> commMonitorList = new ArrayList<CommMonitorRowDTO>();

		addSPNInvitesToCommMonitorList(commMonitorList);


		setAttribute(OrderConstants.COMMUNICATION_MONITOR_LIST, commMonitorList);
	}

	private void addSPNInvitesToCommMonitorList(List<CommMonitorRowDTO> commMonitorList)
	{
		if(commMonitorList == null)
			return;


		ServiceOrdersCriteria criteria = get_commonCriteria();


		Integer resourceId = criteria.getVendBuyerResId();
		Integer companyId = criteria.getCompanyId();

		List<CommMonitorRowDTO> invitesList = null;
		try
		{
			invitesList = spnBuyerDelegate.loadInviteSPNNetwork(resourceId, companyId);
		}
		catch(Exception e)
		{
		}

		if(invitesList != null && invitesList.size() > 0)
			commMonitorList.addAll(invitesList);
	}

	private void initAdminBuyerModules()
	{
		ArrayList<String> moduleList = new ArrayList<String>();
		moduleList.add(BUYER_SERVICE_ORDERS);
		moduleList.add(BUYER_FINANCE_MANAGER);
		/*
		 * Commented out for 12/15/07 release COMMENT START-
		 * moduleList.add(BUYER_OVERALL_STATUS_MONITOR);
		 * moduleList.add(BUYER_PROFILE_PREFS); COMMENT END-
		 */
		moduleList.add(BUYER_ADMIN_OFFICE);

		setAttribute(OrderConstants.MODULE_LIST, moduleList);

	}

	private void initAssocBuyerModules()
	{
		ArrayList<String> moduleList = new ArrayList<String>();
		moduleList.add(BUYER_SERVICE_ORDERS);
		moduleList.add(BUYER_OVERALL_STATUS_MONITOR);
		moduleList.add(BUYER_FINANCE_MANAGER);
		moduleList.add(BUYER_MY_FAVORITES);
		moduleList.add(BUYER_REPORTS);
		moduleList.add(BUYER_PROFILE_PREFS);
		setAttribute(OrderConstants.MODULE_LIST, moduleList);
	}
	
	private void initSimpleBuyerModules()
	{
		ArrayList<String> moduleList = new ArrayList<String>();
		moduleList.add(BUYER_SERVICE_ORDERS);		
		moduleList.add(BUYER_FINANCE_MANAGER);		
		setAttribute(OrderConstants.MODULE_LIST, moduleList);
	}

	private void initAdminProviderModules()
	{
		ArrayList<String> moduleList = new ArrayList<String>();
		moduleList.add(PROVIDER_RECEIVED_SERVICE_ORDERS);
		/* SL-18869 : Need not show Available balance on provider Dashboard on left side */
		// moduleList.add(PROVIDER_FINANCE_MANAGER);

		/*
		 * Commented out for 12/15/07 release COMMENT START-
		 * moduleList.add(PROVIDER_OVERALL_STATUS_MONITOR);
		 *
		 * COMMENT END-
		 */
		moduleList.add(PROVIDER_ADMIN_OFFICE);
		moduleList.add(PROVIDER_OVERALL_STATUS_MONITOR);
		//D2C Portel
		if(null != getSession().getAttribute("skuCountOfPrimaryIndustry") && getSession().getAttribute("skuCountOfPrimaryIndustry").equals(true)){
			moduleList.add(PROVIDER_D2C_PORTEL);
		}
		//for non-leads providers
		
		if(showLeadsSignUp == 0){
			moduleList.add(PROVIDER_LEADS_SIGNUP);
		}
		
		//for member offers
		moduleList.add(PROVIDER_MEMBER_OFFERS);
		// for Mini SPN Monitor
		if(isSPNMonitorAvailable > 0 ){
			moduleList.add(PROVIDER_MINI_SPN_MONITOR);
		}
		/*
		 * Commented out for 12/15/07 release COMMENT START-
		 * moduleList.add(PROVIDER_PROFILE_PREFS);
		 * moduleList.add(PROVIDER_WEATHER); COMMENT END-
		 */
		setAttribute(OrderConstants.MODULE_LIST, moduleList);
	}

	private void initDispatcherProviderModules()
	{
		ArrayList<String> moduleList = new ArrayList<String>();
		moduleList.add(PROVIDER_RECEIVED_SERVICE_ORDERS);
		moduleList.add(PROVIDER_OVERALL_STATUS_MONITOR);
		//for member offers
		moduleList.add(PROVIDER_MEMBER_OFFERS);
		moduleList.add(PROVIDER_MY_FAVORITES);
		moduleList.add(PROVIDER_REPORTS);
		moduleList.add(PROVIDER_PROFILE_PREFS);
		moduleList.add(PROVIDER_WEATHER);
		setAttribute(OrderConstants.MODULE_LIST, moduleList);
	}

	private void initTechnicianProviderModules()
	{
		ArrayList<String> moduleList = new ArrayList<String>();
		moduleList.add(PROVIDER_RECEIVED_SERVICE_ORDERS);
		moduleList.add(PROVIDER_OVERALL_STATUS_MONITOR);
		//for member offers
		moduleList.add(PROVIDER_MEMBER_OFFERS);
		moduleList.add(PROVIDER_MY_FAVORITES);
		moduleList.add(PROVIDER_PROFILE_PREFS);
		moduleList.add(PROVIDER_WEATHER);
		setAttribute(OrderConstants.MODULE_LIST, moduleList);
	}

	private void initDate()
	{
		String dateString = DateUtils.getHeaderDate();
		setAttribute(OrderConstants.DATE_STRING, dateString);
	}
	
	/**
	 * GGanapathy **############################## Added for Admin Logging
	 * -starts#################################**
	 */

	private void insertAdminLogging()
	{
		SecurityContext securityContext = (SecurityContext) getSession()
				.getAttribute(OrderConstants.SECURITY_CONTEXT);
		adminLoggingDto = new AdminLoggingDto();
		adminLoggingDto.setUserId(securityContext.getSlAdminUName());
		adminLoggingDto.setCompanyId(get_commonCriteria().getCompanyId());
		// adminLoggingDto.setActivityId(0);
		adminLoggingDto.setRoleId(securityContext.getRoleId());
		adminLoggingDto = adminLoggingDelegate.insertAdminLogging(adminLoggingDto);
		ActionContext.getContext().getSession().put(OrderConstants.DASHBOARD_LOG,
				adminLoggingDto.getLoggId());
	}
	
	
	/**
	 * gets the SPN details for provider admin
	 * @param userRole
	 */
	private void initCommunicationsMonitorForPA(String userRole)
	{
		List<SPNetCommMonitorVO> spmCommMonitorList = new ArrayList<SPNetCommMonitorVO>();
		addSPNetCommToCommMonitorListForPA(spmCommMonitorList);
		setAttribute(SPNConstants.SPN_COMM_MONITOR_LIST, spmCommMonitorList);
	}
	
	/**
	 * gets the SPN details for provider admin
	 * @param spmCommMonitorList
	 * @return List<SPNetCommMonitorVO>
	 */
	private void addSPNetCommToCommMonitorListForPA(List<SPNetCommMonitorVO> spmCommMonitorList)
	{
		if(spmCommMonitorList == null){
			return;
		}

		ServiceOrdersCriteria criteria = get_commonCriteria();		
		Integer companyId = criteria.getCompanyId();
		List<SPNetCommMonitorVO> commList = null;		
		try
		{
			commList = spnCreateUpdateBO.loadSPNetCommDetailsForPA(companyId);
		}
		catch(Exception e)
		{
			logger.error("Error retriving SPN Details for Provider Admin in addSPNetCommToCommMonitorListForPA: ",e);
		}

		if(commList != null && commList.size() > 0)
			spmCommMonitorList.addAll(commList);
	}
	
	//for auto acceptance tab & notification
	private void loadProviderDetails() {
		Integer vendorId = get_commonCriteria().getCompanyId();
		
		//checks whether provider has any active pending CAR rules
		int activeCount = dashboardDelegate.getActivePendingCARRulesCount(vendorId);
		this._commonCriteria.getSecurityContext().setActivePendingCARRulesPresent(activeCount);
	}
	
	/**
	 * Added for D2C provider. 
	 * buyerSkuPrimaryIndustry();
	 * parameter vendorId.
    **/
	public String buyerSkuPrimaryIndustry(){
		logger.info("Inside DashBoardAction.buyerSkuPrimaryIndustry");
		Integer vendorIds = get_commonCriteria().getCompanyId();
		String count = "";
		count = dashboardDelegate.buyerSkuPrimaryIndustry(vendorIds);
		return count;
	}
	/**
	 * ############################## Added for Admin Logging
	 * -ends#################################*
	 */

	public Map getSSessionMap()
	{
		return sSessionMap;
	}

	public void setSSessionMap(Map sessionMap)
	{
		sSessionMap = sessionMap;
	}

	public String getDisplayTab() {
		return displayTab;
	}

	public void setDisplayTab(String displayTab) {
		this.displayTab = displayTab;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getSpEditProfile() {
		return spEditProfile;
	}

	public void setSpEditProfile(String spEditProfile) {
		this.spEditProfile = spEditProfile;
	}

	public ISPNMonitorBO getSpnMonitorBO() {
		return spnMonitorBO;
	}

	public void setSpnMonitorBO(ISPNMonitorBO spnMonitorBO) {
		this.spnMonitorBO = spnMonitorBO;
	}

	public List<SPNMonitorVO> getMiniSPNList() {
		return miniSPNList;
	}

	public void setMiniSPNList(List<SPNMonitorVO> miniSPNList) {
		this.miniSPNList = miniSPNList;
	}

	public Integer getIsSPNMonitorAvailable() {
		return isSPNMonitorAvailable;
	}

	public void setIsSPNMonitorAvailable(Integer isSPNMonitorAvailable) {
		this.isSPNMonitorAvailable = isSPNMonitorAvailable;
	}

	public Integer getShowLeadsSignUp() {
		return showLeadsSignUp;
	}

	public void setShowLeadsSignUp(Integer showLeadsSignUp) {
		this.showLeadsSignUp = showLeadsSignUp;
	}

	public Integer getNewLeadsTCIndicator() {
		return newLeadsTCIndicator;
	}

	public void setNewLeadsTCIndicator(Integer newLeadsTCIndicator) {
		this.newLeadsTCIndicator = newLeadsTCIndicator;
	}

	public ILookupDelegate getLookupDelegate() {
		return lookupDelegate;
	}

	public void setLookupDelegate(ILookupDelegate lookupDelegate) {
		this.lookupDelegate = lookupDelegate;
	}

	public String getNewTandCContent() {
		return newTandCContent;
	}

	public void setNewTandCContent(String newTandCContent) {
		this.newTandCContent = newTandCContent;
	}
}	