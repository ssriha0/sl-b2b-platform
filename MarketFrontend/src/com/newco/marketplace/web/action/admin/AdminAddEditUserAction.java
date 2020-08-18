package com.newco.marketplace.web.action.admin;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.dto.vo.permission.PermissionSetVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.common.UserProfilePermissionSetVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.dto.AdminAddEditUserDTO;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.thoughtworks.xstream.XStream;

public class AdminAddEditUserAction extends SLBaseAction implements Preparable, ModelDriven<AdminAddEditUserDTO>
{
	private static final long serialVersionUID = 1L;
	private static final String MANAGE_SL_ADMINS_ACTIVITY = "12";
	private AdminAddEditUserDTO adminAddEditUserDTO = new AdminAddEditUserDTO();
	private IManageUsersDelegate manageUsersDelegate;
	private IAuditLogDelegate auditLogDelegates;
	private static final String ACTIVITYKEY="activity_cb";
	

	public AdminAddEditUserAction(IManageUsersDelegate manageUsersDelegate)
	{
		this.manageUsersDelegate = manageUsersDelegate;
	}
	
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
	}	

	
	public String displayAddPage() throws Exception
	{		
		initActivities(true, false);
		initRoles(true);
	
		setAttribute("readOnly", false);
		setAttribute("editable", true);
		getSession().setAttribute("addEditUserMode", "add");
		
		return SUCCESS;
	}

	
	public String displayEditPage() throws Exception {				
		setAdminAddEditUserDTO(manageUsersDelegate.getAdminUser((String)getParameter("username")));
		
		// if the requesting user is the Super Admin we will need to bring
		// back inactive activities as well
		boolean getInactive = false;
		
		Map<String, UserActivityVO> activities = _commonCriteria.getSecurityContext().getRoleActivityIdList();
		LoginCredentialVO  lvo = _commonCriteria.getSecurityContext().getRoles();
		
		if (_commonCriteria.getRoleId().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID &&
			activities.containsKey(MANAGE_SL_ADMINS_ACTIVITY)) {
			getInactive = true;
		}
		
		boolean callerSuperAdmin  = lvo.isSuperAdmin();		
		
		initActivities(getInactive, callerSuperAdmin);
		initRoles(getModel().isEditable());
		
		setAttribute("showRemoveUserButton", true);
		setAttribute("readOnly", true);
		setAttribute("editable", getModel().isEditable());
		setAttribute("adminAddEditUserDTO",getModel());
		getSession().setAttribute("addEditUserMode", "edit");
		
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		if (securityContext != null) {
			boolean passwordResetForSLAdmin = securityContext.getRoles().isPasswordResetForSLAdmin();
			boolean passwordResetForAllExternalUsers = securityContext.getRoles().isPasswordResetForAllExternalUsers();            
			if (passwordResetForSLAdmin == true)
				setAttribute("passwordResetForSLAdmin", "true");
			if (passwordResetForAllExternalUsers == true)		
				setAttribute("passwordResetForAllExternalUsers", "true");
		}
		
		return SUCCESS;
	}
	
	public String saveAndResetPassword() throws Exception {
		save();
		return resetPassword();
	}
	
	
	public String resetPassword() throws Exception {
		String userName = (String)getParameter("username");		
		//SL-19704
		if(StringUtils.isNotBlank(userName)){
			userName = userName.trim();
		}
		boolean flag = manageUsersDelegate.resetPassword(userName);
		if (flag) {
			System.out.println("Password has been reset for admin-user:" + userName);
			setActionMessage(Config.getResouceBundle().getString("resetPassword.adminResetPassword.success"));
			return "reset_password";
		} else {
			System.out.println("Failed to reset Admin password:" + userName);
			setActionError(Config.getResouceBundle().getString("resetPassword.adminResetPassword.error"));
			return "reset_password";
		}		
	}
	
	private void initRoles(boolean excludeSA)
	{
		List<LookupVO> listToReturn = new ArrayList <LookupVO>();
		List<LookupVO> list = manageUsersDelegate.getCompanyRoles(get_commonCriteria().getRoleId());
		if (excludeSA) {
			for(LookupVO vo : list) {
				if(vo.getId().intValue() != Constants.UserManagement.SUPER_ADMIN_ROLE_ID) {
					listToReturn.add(vo);
				}
			}
		} else {
			listToReturn = list;
		}
		setAttribute("jobRoleList", listToReturn);			
	}
	
	private void initActivities(boolean getInactive, boolean callerSuperAdmin)
	{
		// hard code the company Id
		SecurityContext securityContext = (SecurityContext) _commonCriteria.getSecurityContext();
		UserProfilePermissionSetVO userProfilePermissionSetVO = new UserProfilePermissionSetVO();
		userProfilePermissionSetVO.setRoleId(get_commonCriteria().getRoleId());
		userProfilePermissionSetVO.setEntityId(new Integer(1));
		userProfilePermissionSetVO.setGetInactive(getInactive);
		userProfilePermissionSetVO.setUserName(securityContext.getUsername());
		userProfilePermissionSetVO.setVendBuyerResId(securityContext.getVendBuyerResId());
		userProfilePermissionSetVO.setCallerSuperAdmin(callerSuperAdmin);
		List<PermissionSetVO> permissionSets = manageUsersDelegate.getUserPermissionSets(userProfilePermissionSetVO);
		setAttribute("permissionSets", permissionSets);
	}
	
	
	public String removeUser() throws Exception
	{
		//SL-19704
		String username = getModel().getUsername();
		if(StringUtils.isNotBlank(username)){
			username = username.trim();
		}		
		manageUsersDelegate.removeUser(username, get_commonCriteria().getTheUserName());
		
		return "remove_user";		
	}
	
	public String save() throws Exception {
		
		adminAddEditUserDTO = getModel();
		//SL-19704
		if(StringUtils.isNotBlank(adminAddEditUserDTO.getUsername())){
			adminAddEditUserDTO.setUsername(adminAddEditUserDTO.getUsername().trim());
		}
		if(StringUtils.isNotBlank(adminAddEditUserDTO.getFirstName())){
			adminAddEditUserDTO.setFirstName(adminAddEditUserDTO.getFirstName().trim());
		}
		if(StringUtils.isNotBlank(adminAddEditUserDTO.getMiddleName())){
			adminAddEditUserDTO.setMiddleName(adminAddEditUserDTO.getMiddleName().trim());
		}
		if(StringUtils.isNotBlank(adminAddEditUserDTO.getLastName())){
			adminAddEditUserDTO.setLastName(adminAddEditUserDTO.getLastName().trim());
		}
		HttpServletRequest request = getRequest();
		Enumeration<String> e = request.getParameterNames();
		List<ActivityVO> activities = new ArrayList<ActivityVO>();
		ActivityVO activity = null;
		ActivityVO viewMMActivity = null;
		ActivityVO editMMActivity = null;
		String name = null;
		while (e.hasMoreElements()) {	
			name = (String)e.nextElement();
			if (StringUtils.contains(name, ACTIVITYKEY)){
				activity = new ActivityVO();
				activity.setActivityId(new Integer(request.getParameter(name)));
				
				// if EDIT MM  set some value to true, form editActivity
				// if VIEW MM  set some value to true, form viewActivity
				// "activity_cb97"
				String viewMMParamName = ACTIVITYKEY + ActivityRegistryConstants.ACTIVITY_ROLE_ID_ADMIN_VIEW_MEMBER_MANAGER ;
				String editMMParamName = ACTIVITYKEY + ActivityRegistryConstants.ACTIVITY_ROLE_ID_ADMIN_EDIT_MEMBER_MANAGER ;
				if(name.equals(viewMMParamName)){
					viewMMActivity = activity;
					continue; // add this activity later
				}
				if(name.equals(editMMParamName)){
					editMMActivity = activity;
					continue; // add this activity later
				}
				
				activities.add(activity);
			}
		}
		
		addViewEditMMActivities(activities, viewMMActivity,
				editMMActivity);
		adminAddEditUserDTO.setActivitiesList(activities);

		if(isFormValid(adminAddEditUserDTO))
		{
			manageUsersDelegate.adminSaveUser(getModel(), get_commonCriteria().getTheUserName());
			auditUserProfileLog(adminAddEditUserDTO);
			return "save";
		}
		else
		{
			boolean superAdminFlag = StringUtils.equals(getModel().getJobRole(),Constants.COMPANY_ROLE.SUPER_ADMIN);
			initActivities(superAdminFlag, superAdminFlag);
			
			initRoles(getModel().isEditable());
			
			setAttribute("readOnly", false);
			setAttribute("editable", true);			
			return SUCCESS;
		}
	}
	
	private void addViewEditMMActivities(List<ActivityVO> activities,
			ActivityVO viewMMActivity, ActivityVO editMMActivity) {
		// add view & edit MM activities
		if(editMMActivity!= null){
			if(viewMMActivity== null){
				viewMMActivity = new ActivityVO();
				viewMMActivity.setActivityId(ActivityRegistryConstants.ACTIVITY_ROLE_ID_ADMIN_VIEW_MEMBER_MANAGER);
			}
			activities.add(editMMActivity);
			activities.add(viewMMActivity);
		}else if (viewMMActivity!= null){
			activities.add(viewMMActivity);
		}
	}
	
	public String resendWelcomeMail() throws Exception {
		save();
		String email  = adminAddEditUserDTO.getEmail();
		//System.out.println("Email:--->" + email + "," + adminAddEditUserDTO.getUsername());
		boolean flag = false;
		
		if (StringUtils.isNotBlank(adminAddEditUserDTO.getUsername()))  {
			flag = manageUsersDelegate.resendWelcomeMail(adminAddEditUserDTO.getUsername().trim(), email);
		}
		
		if (flag) {
			setActionMessage("Welcome email has been resent.");
		} else {
			setActionError("Error in sending Welcome email.");
		}
		return "save";
	}

	private void auditUserProfileLog(AdminAddEditUserDTO adminAddEditUserDTO)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {AdminAddEditUserDTO.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(adminAddEditUserDTO);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("ADMIN_USER_PROFILE_EDIT");
		SecurityContext securityContext = (SecurityContext) _commonCriteria.getSecurityContext();
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
	
	private boolean isFormValid(AdminAddEditUserDTO dto)
	{
		//Test of validation handling
		SOWError error;
		List<IError> errors = new ArrayList<IError>();		
		
		// First Name
		if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(dto.getFirstName()))
		{
			error = new SOWError(getTheResourceBundle().getString("Admin_First_Name"),
					getTheResourceBundle().getString("Admin_First_Name_Validation_Msg"), OrderConstants.FM_ERROR);
			errors.add(error);
		}
		// Last Name
		if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(dto.getLastName()))
		{
			error = new SOWError(getTheResourceBundle().getString("Admin_Last_Name"),
					getTheResourceBundle().getString("Admin_Last_Name_Validation_Msg"), OrderConstants.FM_ERROR);
			errors.add(error);
		}
		
		// User Name
		if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(dto.getUsername()))
		{
			error = new SOWError(getTheResourceBundle().getString("Admin_User_Name"),
					getTheResourceBundle().getString("Admin_User_Name_Validation_Msg"), OrderConstants.FM_ERROR);
			errors.add(error);
		}
		else
		{	
			//User Name Length Check
			if((dto.getUsername().length() < 8) || (dto.getUsername().length() >30)){
				error = new SOWError(getTheResourceBundle().getString("Admin_User_Name"),getTheResourceBundle().getString("Admin_User_Name_Length_Validation_Msg"), OrderConstants.FM_ERROR);
				errors.add(error);
			}
		}

		String jobRole = getParameter("jobRole");
		if("-1".equals(jobRole))
		{
			error = new SOWError(getTheResourceBundle().getString("Admin_Job_Role"),
					getTheResourceBundle().getString("Admin_Job_Role_Validation_Msg"), OrderConstants.FM_ERROR);
			errors.add(error);			
		}
		
		// Handle Primary Email
		if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(dto.getEmail()))
		{
			error = new SOWError(getTheResourceBundle().getString("Admin_Email"),
					getTheResourceBundle().getString("Admin_Email_Validation_Msg"), OrderConstants.FM_ERROR);
			errors.add(error);										
		}
		else
		{
			if(com.newco.marketplace.web.utils.SLStringUtils.isEmailValid(dto.getEmail()) == false)
			{
				error = new SOWError(getTheResourceBundle().getString("Admin_PrimaryEmail"),
						getTheResourceBundle().getString("Admin_Email_Pattern_Validation_Msg"), OrderConstants.FM_ERROR);
				errors.add(error);										
			}
			else if(dto.getEmail().equals(dto.getEmailConfirm()) == false)
			{
				error = new SOWError(getTheResourceBundle().getString("Admin_PrimaryEmail"),
						getTheResourceBundle().getString("Admin_Email_Confirm_Validation_Msg"), OrderConstants.FM_ERROR);
				errors.add(error);														
			}
		}
		
		// Check for existing username, only in Add mode
		String mode = (String)getSession().getAttribute("addEditUserMode");
		if(mode != null)
		{
			if(mode.equals("add"))
			{
				if(manageUsersDelegate.getAdminUser(dto.getUsername()) != null || manageUsersDelegate.getBuyerUser(dto.getUsername()) != null)
				{
					error = new SOWError(getTheResourceBundle().getString("Admin_User_Name"), getTheResourceBundle().getString("Admin_Existing_User_Validation_Msg1")+" " + dto.getUsername()  +" " +getTheResourceBundle().getString("Admin_Existing_User_Validation_Msg2"), OrderConstants.FM_ERROR);
					errors.add(error);													
				}
			}
		}
				
		// If we have errors, put them in request.
		if(errors.size() > 0)
		{
			setAttribute("errors", errors);
			return false;
		}
		else
		{
			getSession().removeAttribute("addEditUserMode");
			getSession().removeAttribute("originalUsername");			
			return true;
		}
	}
	public AdminAddEditUserDTO getModel()
	{
		return adminAddEditUserDTO;
	}


	public AdminAddEditUserDTO getAdminAddEditUserDTO() {
		return adminAddEditUserDTO;
	}


	public void setAdminAddEditUserDTO(AdminAddEditUserDTO adminAddEditUserDTO) {
		this.adminAddEditUserDTO = adminAddEditUserDTO;
	}

	public ResourceBundle getTheResourceBundle() {
		return Config.getResouceBundle();
	}


	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}


	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}
	
	
}
