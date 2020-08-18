package com.newco.marketplace.web.action.buyeradmin;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.dto.vo.permission.PermissionSetVO;
import com.newco.marketplace.dto.vo.permission.UserRoleVO;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.common.UserProfilePermissionSetVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.dto.BuyerAdminManageUsersAddEditDTO;
import com.newco.marketplace.web.utils.Config;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.thoughtworks.xstream.XStream;

public class BAAddEditAction extends SLBaseAction implements Preparable, ModelDriven<BuyerAdminManageUsersAddEditDTO> 
{

	private static final long serialVersionUID = 20090925L;
	private IManageUsersDelegate manageUsersDelegate;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private BuyerAdminManageUsersAddEditDTO buyerAdminManageUsersAddEditDTO = new BuyerAdminManageUsersAddEditDTO();
	private IAuditLogDelegate auditLogDelegates;
	private static final String COMPANYROLEKEY="companyrole";
	private static final String ACTIVITYKEY="activity_cb";
    private String selectedUser; 
    public static final String emailAddressPattern =
    	"\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
		

	public BAAddEditAction(IManageUsersDelegate manageUsersDelegate)
	{
		this.manageUsersDelegate = manageUsersDelegate;
	}
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		
		
		getCompanyRolesAndActivities(get_commonCriteria().getSecurityContext().getRoleId(),get_commonCriteria().getSecurityContext().getCompanyId());		
	}	

	
	public String execute() throws Exception {
		
		return SUCCESS;
	}
	@SkipValidation
	public String displayAddPage() throws Exception{
		
		TermsAndConditionsVO terms = getLookupManager().getTermsConditionsContent(Constants.UserManagement.BUYER_TERMS_CONDITIONS_AGREEMENT);
		getModel().setTermsCondId(terms.getTermsCondId());
		getModel().setMaxSpendLimit("0.00");
		setAttribute("termsContent", terms.getTermsCondContent());
		setAttribute("readOnly", false);
		setAttribute("editable", true);
		getSession().setAttribute("mode", "add");
		return "success_add";
	}
	@SkipValidation
	public String displayEditPage() throws Exception{
		
		String username;
		
		username = getParameter("username");
		username = URLDecoder.decode( username , "UTF-8");
        username = username.replaceAll("-prcntg-", "%");
		
		//Handle missing username
		if(username == null)
			return "success_edit";
		Integer buyerId = get_commonCriteria().getSecurityContext().getCompanyId();
		boolean isAuthorized = manageUsersDelegate.isUserAssociatedWithLoggedUser(username,buyerId);
		if(!isAuthorized){
			ActionContext.getContext().getSession().put(ACTION_ERROR, "Not Authorized");
			return ERROR;
		}
		BuyerAdminManageUsersAddEditDTO user = manageUsersDelegate.getBuyerUser(username);
		user.setUserNameConfirmation(username);
		//Handle invalid username
		if(user == null)
			return "success_edit";
		
		setBuyerAdminManageUsersAddEditDTO(user);
		
		TermsAndConditionsVO terms = getLookupManager().getTermsConditionsContent(Constants.UserManagement.BUYER_TERMS_CONDITIONS_AGREEMENT);
		getModel().setTermsCondId(terms.getTermsCondId());
		setAttribute("termsContent", terms.getTermsCondContent());
		setAttribute("readOnly", true);
		setAttribute("editable", getModel().isEditable());
		setAttribute("showRemoveUserButton", true);
		setAttribute("neverLoggedIn", user.isNeverLoggedIn());
		
		getSession().setAttribute("mode", "edit");	
		
		return "success_edit";
	}

	private boolean isFormValid(BuyerAdminManageUsersAddEditDTO dto)
	{
		//Test of validation handling
				
		// First Name
		if(SLStringUtils.isNullOrEmpty(dto.getFirstName()))
		{
			addFieldError("buyerAdminManageUsersAddEditDTO.firstName", "First Name is required");
		}
		
		// Last Name
		if(SLStringUtils.isNullOrEmpty(dto.getLastName()))
		{
			addFieldError("buyerAdminManageUsersAddEditDTO.lastName", "Last Name is required");
		}

		// UserName
		if(SLStringUtils.isNullOrEmpty(dto.getUserName()))
		{
			addFieldError("buyerAdminManageUsersAddEditDTO.userName", "User Name is required");
		}
		
		// UserName Match
		if(!dto.getUserName().equals(dto.getUserNameConfirmation()))
		{
			addFieldError("buyerAdminManageUsersAddEditDTO.userNameConfirmation", "User Name must match the User Name Confirmation");
		}
		
		// Check for existing username, only in Add mode
		String mode = (String)getSession().getAttribute("mode");
		if(mode != null)
		{
			if(mode.equals("add"))
			{
				if(manageUsersDelegate.isUserAvailable(dto.getUserName()) == false)
				{
					addFieldError("buyerAdminManageUsersAddEditDTO.userName", "The username you have selected '" + dto.getUserName() + "' is taken. Please choose another username.");
				}
			}
		}
		
		
		
		// Job Role
		if(dto.getJobRoleList() == null || dto.getJobRoleList().size() == 0){
			addFieldError("buyerAdminManageUsersAddEditDTO.jobRole", "At least one Role must be checked");
		}
		
		
		// Handle Phone - Area Code
		if(SLStringUtils.isNullOrEmpty(dto.getPhoneBusinessAreaCode()))
		{
			addFieldError("buyerAdminManageUsersAddEditDTO.phoneAreaCode", "Business Phone Area Code is required");
		}
		else
		{
			if(dto.getPhoneBusinessAreaCode().length() != 3)
			{
				addFieldError("buyerAdminManageUsersAddEditDTO.phoneAreaCode", "Business Phone part 1 is not 3 digits");
			}			
			else if(SLStringUtils.IsParsableNumber(dto.getPhoneBusinessAreaCode()) == false)
			{
				addFieldError("buyerAdminManageUsersAddEditDTO.phoneAreaCode", "Business Phone part 1 is not an valid number");
			}			
		}
		// Handle Phone - Part 1
		if(SLStringUtils.isNullOrEmpty(dto.getPhoneBusinessPart1()))
		{
			addFieldError("buyerAdminManageUsersAddEditDTO.phonePart1", "Business Phone part 1 is required");
		}
		else
		{
			if(dto.getPhoneBusinessPart1().length() != 3)
			{
				addFieldError("buyerAdminManageUsersAddEditDTO.phonePart1", "Business Phone part 1 is not 3 digits");
			}
			else if(SLStringUtils.IsParsableNumber(dto.getPhoneBusinessPart1()) == false)
			{
				addFieldError("buyerAdminManageUsersAddEditDTO.phonePart1", "Business Phone part 1 is not a valid number");
			}
		}
		
		// Handle Phone - Part2 
		if(SLStringUtils.isNullOrEmpty(dto.getPhoneBusinessPart2()))
		{	
			addFieldError("buyerAdminManageUsersAddEditDTO.phonePart2", "Business Phone part 2 is required");
		}
		else
		{
			if(dto.getPhoneBusinessPart2().length() != 4)
			{		
				addFieldError("buyerAdminManageUsersAddEditDTO.phonePart2", "Business Phone part 2 is not 4 digits");
			}
			else if(SLStringUtils.IsParsableNumber(dto.getPhoneBusinessPart2()) == false)
			{
				addFieldError("buyerAdminManageUsersAddEditDTO.phonePart2", "Business Phone part 2 is not a valid number");
			}
		}
		
		//Handle Business Fax
		if(!SLStringUtils.isNullOrEmpty(dto.getFaxBusinessAreaCode())){	 	
			//Handle Fax area code
			if(dto.getFaxBusinessAreaCode().length() != 3)
			{
				addFieldError("buyerAdminManageUsersAddEditDTO.faxAreaCode", "Business Fax part 1 is not 3 digits");
			}			
			else if(SLStringUtils.IsParsableNumber(dto.getFaxBusinessAreaCode()) == false)
			{
				addFieldError("buyerAdminManageUsersAddEditDTO.faxAreaCode", "Business Fax part 1 is not an valid number");
			}	
			
			// Handle Fax - Part 1
			if(SLStringUtils.isNullOrEmpty(dto.getFaxBusinessPart1()))
			{
				addFieldError("buyerAdminManageUsersAddEditDTO.faxPart1", "Business Fax part 1 is required");
			}
			else
			{
				if(dto.getFaxBusinessPart1().length() != 3)
				{
					addFieldError("buyerAdminManageUsersAddEditDTO.faxPart1", "Business Fax part 1 is not 3 digits");
				}
				else if(SLStringUtils.IsParsableNumber(dto.getFaxBusinessPart1()) == false)
				{							
					addFieldError("buyerAdminManageUsersAddEditDTO.faxPart1", "Business Fax part 1 is not a valid number");
				}
			}
			
			// Handle Fax - Part2 
			if(SLStringUtils.isNullOrEmpty(dto.getFaxBusinessPart2()))
			{		
				addFieldError("buyerAdminManageUsersAddEditDTO.faxPart2", "Business Fax part 2 is required");
			}
			else
			{
				if(dto.getFaxBusinessPart2().length() != 4)
				{						
					addFieldError("buyerAdminManageUsersAddEditDTO.faxPart2", "Business Fax part 2 is not 4 digits");
				}
				else if(SLStringUtils.IsParsableNumber(dto.getFaxBusinessPart2()) == false)
				{						
					addFieldError("buyerAdminManageUsersAddEditDTO.faxPart2", "Business Fax part 2 is not a valid number");
				}
			}
		}
		
		// Handle Primary Email
		if(SLStringUtils.isNullOrEmpty(dto.getPriEmail()))
		{							
			addFieldError("buyerAdminManageUsersAddEditDTO.priEmail", "Email is required");
		}
		else if(dto.getPriEmail()!=null&& StringUtils.isNotBlank(dto.getPriEmail()))	
		{
		 Pattern pattern;
		 Matcher matcher;			
			pattern = Pattern.compile(emailAddressPattern);
	        matcher = pattern.matcher(dto.getPriEmail());
	         if (!matcher.matches()) {  
				addFieldError("buyerAdminManageUsersAddEditDTO.priEmail", "Email is not of valid pattern. Example abc@xyz.com");
			  }else if(dto.getPriEmail().equals(dto.getConfirmPriEmail()) == false)
				{													
					addFieldError("buyerAdminManageUsersAddEditDTO.confirmPriEmail", "Email does not match Confirmation Email");
				}			
		 }
		
		// Handle Alternate Email
		
		if(SLStringUtils.isNullOrEmpty(dto.getAltEmail()))
		{
			addFieldError("buyerAdminManageUsersAddEditDTO.altEmail", "Alternate Email is required");
		}
		else if(dto.getAltEmail()!=null&& StringUtils.isNotBlank(dto.getAltEmail()))	
		{
		 Pattern pattern1;
		 Matcher matcher1;						
			pattern1 = Pattern.compile(emailAddressPattern);
            matcher1 = pattern1.matcher(dto.getAltEmail());
            if (!matcher1.matches()) {  								
				addFieldError("buyerAdminManageUsersAddEditDTO.altEmail", "Alternate Email is not of valid pattern. Example abc@xyz.com");
			}
			else if(dto.getAltEmail().equals(dto.getConfirmAltEmail()) == false)
			{													
				addFieldError("buyerAdminManageUsersAddEditDTO.confirmAltEmail", "Altername Email does not match Confirmation Email");
			}
		  
		}
		
		// Handle Maximum Price
		if(SLStringUtils.isNullOrEmpty(dto.getMaxSpendLimit()) == true)
		{													
			addFieldError("buyerAdminManageUsersAddEditDTO.maxSpendLimit", "Maximum Price is required");
		}		
		else if(SLStringUtils.IsParsableNumber(dto.getMaxSpendLimit()) == false)
		{									
			addFieldError("buyerAdminManageUsersAddEditDTO.maxSpendLimit", "Maximum Price must be a decimal number.  Example: 123.45");
		}
		else if(SLStringUtils.IsSpendLimit(dto.getMaxSpendLimit()) == false)
		{									
			addFieldError("buyerAdminManageUsersAddEditDTO.maxSpendLimit", "Maximum Price must be a valid value (lesser than 10000000).  Example: 123.45");
		}
		else if(SLStringUtils.IsNotNegative(dto.getMaxSpendLimit()) == false)
		{									
			addFieldError("buyerAdminManageUsersAddEditDTO.maxSpendLimit", "Maximum Price cannot be a negative value.  Example: 123.45");
		}
		
		// Terms and Conditions
		String toc_answer = getParameter("acceptTermsAndConditions");
		if("1".equals(toc_answer) == false)
		{										
			addFieldError("buyerAdminManageUsersAddEditDTO.termsAndConditions", "You must accept the Buyer Terms and Conditions");
		}
		
		
		if(hasFieldErrors() || hasActionErrors())
		{
			return false;
		} else {
			return true;
		}
	}
	@SkipValidation
	public String saveUser() throws Exception
	{
		String modifierUsername = get_commonCriteria().getTheUserName();

		// Get form data
		buyerAdminManageUsersAddEditDTO = getModel();
		//SL-19704
		if(StringUtils.isNotBlank(buyerAdminManageUsersAddEditDTO.getUserName())){
			buyerAdminManageUsersAddEditDTO.setUserName(buyerAdminManageUsersAddEditDTO.getUserName().trim());
		}
		if(StringUtils.isNotBlank(buyerAdminManageUsersAddEditDTO.getFirstName())){
			buyerAdminManageUsersAddEditDTO.setFirstName(buyerAdminManageUsersAddEditDTO.getFirstName().trim());
		}
		if(StringUtils.isNotBlank(buyerAdminManageUsersAddEditDTO.getMiddleName())){
			buyerAdminManageUsersAddEditDTO.setMiddleName(buyerAdminManageUsersAddEditDTO.getMiddleName().trim());
		}
		if(StringUtils.isNotBlank(buyerAdminManageUsersAddEditDTO.getLastName())){
			buyerAdminManageUsersAddEditDTO.setLastName(buyerAdminManageUsersAddEditDTO.getLastName().trim());
		}
		
		if(StringUtils.isNotBlank(buyerAdminManageUsersAddEditDTO.getUserNameConfirmation())){
			buyerAdminManageUsersAddEditDTO.setUserNameConfirmation(buyerAdminManageUsersAddEditDTO.getUserNameConfirmation().trim());
		}
		
		// Extract roles checked from forms
		HttpServletRequest request = getRequest();
		Enumeration<String> e = request.getParameterNames();
		List<UserRoleVO> selectedRoles = new ArrayList<UserRoleVO>();
		UserRoleVO userRole = null;
		String name = null;
		List<String> roleParams = new ArrayList<String>();
		while (e.hasMoreElements()) {	
			name = (String)e.nextElement();
			// Additional roleParams filtering is added, because of some weird reason all parameters are coming twice in the request
			if (org.apache.commons.lang.StringUtils.contains(name, COMPANYROLEKEY) && !roleParams.contains(name)){
				userRole = new UserRoleVO();
				userRole.setCompanyRoleId(Integer.parseInt(request.getParameter(name)));
				selectedRoles.add(userRole);
				roleParams.add(name);
			}
		}
		
		buyerAdminManageUsersAddEditDTO.setJobRoleList(selectedRoles);
		
		
		// Extract Activities
		e = request.getParameterNames();
		List<ActivityVO> selectedActivities = new ArrayList<ActivityVO>();
		
		ActivityVO viewMMActivity = null;
		ActivityVO editMMActivity = null;
		ActivityVO activity = null;
		name = null;
		List<String> activityParams = new ArrayList<String>();
		while (e.hasMoreElements()) {	
			name = (String)e.nextElement();
			// Additional activityParams filtering is added, because of some weird reason all parameters are coming twice in the request
			if (org.apache.commons.lang.StringUtils.contains(name, ACTIVITYKEY) && !activityParams.contains(name)){
				activity = new ActivityVO();
				activity.setActivityId(new Integer(request.getParameter(name)));
				
				// if EDIT MM  set some value to true, form editActivity
				// if VIEW MM  set some value to true, form viewActivity
				// "activity_cb97"
				String viewMMParamName = ACTIVITYKEY + ActivityRegistryConstants.ACTIVITY_ROLE_ID_BUYER_VIEW_MEMBER_MANAGER ;
				String editMMParamName = ACTIVITYKEY + ActivityRegistryConstants.ACTIVITY_ROLE_ID_BUYER_EDIT_MEMBER_MANAGER ;
				if(name.equals(viewMMParamName)){
					viewMMActivity = activity;
					continue; // add this activity later
				}
				if(name.equals(editMMParamName)){
					editMMActivity = activity;
					continue; // add this activity later
				}
				
				selectedActivities.add(activity);
				activityParams.add(name);
			}
		}
		
		addViewEditMMActivities(selectedActivities, viewMMActivity,
				editMMActivity);
		

		buyerAdminManageUsersAddEditDTO.setActivitiesList(selectedActivities);
		buyerAdminManageUsersAddEditDTO.setBuyerId(get_commonCriteria().getCompanyId());
		if("add".equals(getSession().getAttribute("mode")))
		{
			setAttribute("editable", true);
			setAttribute("readOnly", false);				
		}
		else
		{
			setAttribute("editable", getModel().isEditable());
			setAttribute("readOnly", true);
			//If its editable and this value is empty, means it has been saved with no limit, so we do not need to 
			//display an error to the user again. initialising it to 0.00 as it should be
			if(SLStringUtils.isNullOrEmpty(getModel().getMaxSpendLimit()) == true)
			{
				getModel().setMaxSpendLimit("0.00");
			}
		}	
		if(isFormValid(buyerAdminManageUsersAddEditDTO))
		{
			manageUsersDelegate.buyerSaveUser(buyerAdminManageUsersAddEditDTO, modifierUsername);
			auditUserProfileLog(buyerAdminManageUsersAddEditDTO);
			return "save_user";
		}
		else
		{
			TermsAndConditionsVO terms = getLookupManager().getTermsConditionsContent(Constants.UserManagement.BUYER_TERMS_CONDITIONS_AGREEMENT);			
			getModel().setTermsCondId(terms.getTermsCondId());
			setAttribute("termsContent", terms.getTermsCondContent());			
			return "success_edit";
		}
	}

	private void addViewEditMMActivities(List<ActivityVO> selectedActivities,
			ActivityVO viewMMActivity, ActivityVO editMMActivity) {
		// add view & edit MM activities
		if(editMMActivity!= null){
			if(viewMMActivity== null){
				viewMMActivity = new ActivityVO();
				viewMMActivity.setActivityId(ActivityRegistryConstants.ACTIVITY_ROLE_ID_BUYER_VIEW_MEMBER_MANAGER);
			}
			selectedActivities.add(editMMActivity);
			selectedActivities.add(viewMMActivity);
		}else if (viewMMActivity!= null){
			selectedActivities.add(viewMMActivity);
		}
	}
	
	private void auditUserProfileLog(BuyerAdminManageUsersAddEditDTO buyerAdminManageUsersAddEditDTO)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {BuyerAdminManageUsersAddEditDTO.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(buyerAdminManageUsersAddEditDTO);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("BUYER_USER_PROFILE_EDIT");
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		if(securityContext!=null)
		{
			auditUserProfileVO.setLoginCompanyId(securityContext.getCompanyId());
			auditUserProfileVO.setLoginResourceId(securityContext.getVendBuyerResId());
			auditUserProfileVO.setRoleId(securityContext.getRoleId());
			if(this.get_commonCriteria().getSecurityContext().isSlAdminInd())
				auditUserProfileVO.setIsSLAdminInd(1);
			auditUserProfileVO.setModifiedBy(securityContext.getUsername());
			auditUserProfileVO.setUserProfileData(xmlContent);
			auditLogDelegates.auditUserProfile(auditUserProfileVO);
		}
	}
	@SkipValidation
	public String removeUser() throws Exception
	{
		//SL-19704
		String username = getParameter("userName");
		if(StringUtils.isNotBlank(username)){
			username = username.trim();
		}
		manageUsersDelegate.removeUser(username, get_commonCriteria().getTheUserName());
		return "remove_user";
	}
	@SkipValidation
	private void getCompanyRolesAndActivities (Integer roleId , Integer companyId) {
		SecurityContext securityContext = (SecurityContext) _commonCriteria.getSecurityContext();
		UserProfilePermissionSetVO userProfilePermissionSetVO = new UserProfilePermissionSetVO();
		userProfilePermissionSetVO.setRoleId(roleId);
		userProfilePermissionSetVO.setEntityId(companyId);
		userProfilePermissionSetVO.setGetInactive(false);
		userProfilePermissionSetVO.setCallerSuperAdmin(false);
		userProfilePermissionSetVO.setUserName(securityContext.getUsername());
		setAttribute("companyRoles", manageUsersDelegate.getCompanyRoles(roleId));

		List<PermissionSetVO> permissionSets = manageUsersDelegate.getUserPermissionSets(userProfilePermissionSetVO);
		List<PermissionSetVO> filteredPermissionSets = filteredPermissionSets(companyId, permissionSets);
		setAttribute("permissionSets", filteredPermissionSets);
		return;
	}

	private List<PermissionSetVO> filteredPermissionSets(Integer buyerId, List<PermissionSetVO> permissionSets) {
		boolean isCarOn = isFeatureOn(buyerId, BuyerFeatureConstants.CONDITIONAL_ROUTE);
		boolean isTieredRoutingOn = isFeatureOn(buyerId, BuyerFeatureConstants.TIER_ROUTE);
		boolean isPermitOn = isFeatureOn(buyerId, BuyerFeatureConstants.TASK_LEVEL);
		boolean isAutocloseOn=isFeatureOn(buyerId, BuyerFeatureConstants.AUTO_CLOSE);
		boolean isHSRAutocloseOn=isFeatureOn(buyerId, BuyerFeatureConstants.INHOME_AUTO_CLOSE);
		if(isCarOn && isTieredRoutingOn && isPermitOn && isAutocloseOn) {
			return permissionSets;
		}

		List<PermissionSetVO> list = new ArrayList<PermissionSetVO>();
		for(PermissionSetVO item: permissionSets) {

			//Note: This code is also related to the code in LoginAction

			for(java.util.Iterator<ActivityVO> iter = item.getActivities().iterator();iter.hasNext(); ) {
				ActivityVO activityVO = iter.next();
				int activityRoleId = activityVO.getActivityId();
				if(!isCarOn) {
					if(activityRoleId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_BUYER_ROUTING_RULES_VIEW || 
							activityRoleId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_BUYER_ROUTING_RULES_EDIT) {	
						iter.remove();
					}
				}

				if(!isTieredRoutingOn) {
					if(activityRoleId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_BUYER_ROUTING_TIERS ) {
						iter.remove();
					}
				}
				
				if(!isPermitOn){
 					if(activityRoleId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_INCREASE_SPEND_LIMIT ) {
 						iter.remove();
 					}
				}
				if(!isAutocloseOn){
 					if(activityRoleId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_MANAGE_AUTO_CLOSE_AND_PAY_RULES) {
 						iter.remove();
 					}
				}
				if(!isHSRAutocloseOn){
 					if(activityRoleId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_HSR_MANAGE_AUTO_CLOSE_AND_PAY_RULES) {
 						iter.remove();
 					}
				}
			}

			if(item.getActivities().size() != 0) {
				list.add(item);
			}
		}
		return list;
	}

	private boolean isFeatureOn(Integer buyerId, String feature) {
		return buyerFeatureSetBO.validateFeature(buyerId, feature).booleanValue();
	}

	public BuyerAdminManageUsersAddEditDTO getModel() {
		return buyerAdminManageUsersAddEditDTO;
	}

	public IManageUsersDelegate getManageUsersDelegate() {
		return manageUsersDelegate;
	}

	public void setManageUsersDelegate(IManageUsersDelegate manageUsersDelegate) {
		this.manageUsersDelegate = manageUsersDelegate;
	}

	public BuyerAdminManageUsersAddEditDTO getBuyerAdminManageUsersAddEditDTO() {
		return buyerAdminManageUsersAddEditDTO;
	}

	public void setBuyerAdminManageUsersAddEditDTO(
			BuyerAdminManageUsersAddEditDTO buyerAdminManageUsersAddEditDTO) {
		this.buyerAdminManageUsersAddEditDTO = buyerAdminManageUsersAddEditDTO;
	}


	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}

	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String saveAndResetPassword() throws Exception{
		String result = saveUser();
		resetPassword();
		return result;
	}
	
	/**
	 * Added by Shekhar to perform Reset Password functionality
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String resetPassword() throws Exception{
		String username = selectedUser;
		if (username == null) { 
			username = buyerAdminManageUsersAddEditDTO.getUserName();
		}
		//SL-19704
		if(StringUtils.isNotBlank(username)){
			username = username.trim();
		}
		if(username == null)
			return "reset_password";

		boolean flag = manageUsersDelegate.resetPassword(username);		
		if (flag) {
			System.out.println("Password has been reset for buyer-user:" + username);
			setActionMessage(Config.getResouceBundle().getString("resetPassword.adminResetPassword.success"));
		} else {
			setActionError(Config.getResouceBundle().getString("resetPassword.adminResetPassword.error"));
		}
		return "reset_password";
	}
	
	
	/**
	 * 
	 */
	public String saveAndResenedWelcomemail() throws Exception {
		String username = selectedUser;
		if (username == null) { 
			username = buyerAdminManageUsersAddEditDTO.getUserName();
		}
		String result = saveUser();
		String email  = buyerAdminManageUsersAddEditDTO.getPriEmail();
		//System.out.println("saveAndResenedWelcomemail:Email:" + email);
		
		boolean flag = false;
		
		if (StringUtils.isNotBlank(username))  {	
			//SL-19704
			username = username.trim();
			flag = manageUsersDelegate.resendWelcomeMail(username, email);
		}
		
		if (flag) {
			setActionMessage("Welcome email has been resent.");
		} else {
			setActionError("Error in sending Welcome email.");
		}
		return "reset_password";		
	}
	
	/**
	 * Added by Shekhar to perform Reset Password functionality
	 * @return
	 * @throws Exception
	 */	
	public String unlockUser() throws Exception{

		String username;

		username = getParameter("username");
		//SL-19704
		if(StringUtils.isNotBlank(username)){
			username = username.trim();
		}

		//Handle missing username
		if(username == null) return "reset_password";

		BuyerAdminManageUsersAddEditDTO user = manageUsersDelegate.getBuyerUser(username);
		
		//user.setUserNameConfirmation(username);
		//Handle invalid username
		if(user == null) return "reset_password";

		//String email = user.getConfirmPriEmail();

		
		setBuyerAdminManageUsersAddEditDTO(user);
        
		//not sure how to get CC address		
		manageUsersDelegate.unlockUser(username);
		
		TermsAndConditionsVO terms = getLookupManager().getTermsConditionsContent(Constants.UserManagement.BUYER_TERMS_CONDITIONS_AGREEMENT);
		getModel().setTermsCondId(terms.getTermsCondId());
		setAttribute("termsContent", terms.getTermsCondContent());
		setAttribute("readOnly", true);
		setAttribute("editable", getModel().isEditable());
		setAttribute("showRemoveUserButton", true);
		getSession().setAttribute("mode", "edit");
		return "reset_password";
	}
	
	public String getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(String selectedUser) {
		this.selectedUser = selectedUser;
	}

	/**
	 * @param buyerFeatureSetBO the buyerFeatureSetBO to set
	 */
	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}
}
