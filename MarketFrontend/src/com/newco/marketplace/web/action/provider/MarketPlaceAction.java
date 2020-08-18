package com.newco.marketplace.web.action.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.IMarketPlaceDelegate;
import com.newco.marketplace.web.dto.AdminAddEditUserDTO;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.MarketPlaceDTO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.thoughtworks.xstream.XStream;
import javax.servlet.ServletContext;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

/**
 * $Revision: 1.33 $ $Author: glacy $ $Date: 2008/04/26 01:13:50 $
 */
public class MarketPlaceAction extends ActionSupport implements SessionAware,
		Preparable, ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2771275171347649623L;
	private static final Logger logger = Logger
			.getLogger(MarketPlaceAction.class.getName());
	private Map sSessionMap;
	private HttpServletRequest request;
	private MarketPlaceDTO marketPlaceDTO;
	private IMarketPlaceDelegate iMarketPlaceDelegate;
	private IActivityRegistryDelegate iActivityRegistryDelegate;
	private HttpSession session;
	private SecurityContext securityContext;
	private boolean primaryIndFlag = false;
	private IAuditLogDelegate auditLogDelegates;
	private IManageUsersDelegate manageUsersDelegate;
	private AdminAddEditUserDTO adminAddEditUserDTO;

	public boolean isPrimaryIndFlag() {
		return primaryIndFlag;
	}

	public void setPrimaryIndFlag(boolean primaryIndFlag) {
		this.primaryIndFlag = primaryIndFlag;
	}

	public MarketPlaceAction(IMarketPlaceDelegate iMarketPlaceDelegate,
			MarketPlaceDTO marketPlaceDTO,
			IActivityRegistryDelegate iActivityRegistryDelegate,
			IManageUsersDelegate manageUsersDelegate) {
		this.iMarketPlaceDelegate = iMarketPlaceDelegate;
		this.marketPlaceDTO = marketPlaceDTO;
		this.iActivityRegistryDelegate = iActivityRegistryDelegate;
		this.manageUsersDelegate = manageUsersDelegate;
	}

	@Override
	public void validate() {
		// super.validate();

		if (hasFieldErrors() || hasActionErrors()) {
			logger.debug("VALIDATION ERROR-------" + getFieldErrors());
			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
					.getSession().get("tabDto");
			baseTabDto.setDtObject(marketPlaceDTO);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getActionErrors().addAll(getActionErrors());
			baseTabDto.getTabStatus().put(
					ActivityRegistryConstants.RESOURCE_MARKETPLACE, "errorOn");
		}
	}

	@SkipValidation
	public String doInput() throws Exception {
		logger.debug("MarketPlaceAction.doInput()");
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
				.getSession().get("tabDto");
		securityContext = (SecurityContext) ActionContext.getContext()
				.getSession().get("SecurityContext");
		// getting messages from session
		getSessionMessages();
		if (baseTabDto != null) {

			marketPlaceDTO.setResourceID((String) ActionContext.getContext()
					.getSession().get("resourceId"));
			marketPlaceDTO.setEntityId(securityContext.getCompanyId()
					.toString());
			marketPlaceDTO.setRoleId(securityContext.getRoleId().toString());
			marketPlaceDTO.setLoggedInResourceId(securityContext
					.getVendBuyerResId());
			MarketPlaceDTO tempMarketPlaceDTO = iMarketPlaceDelegate
					.loadMarketPlace(marketPlaceDTO,
							securityContext.getUsername());
			if (baseTabDto.getDtObject() != null)
				marketPlaceDTO = (MarketPlaceDTO) baseTabDto.getDtObject();

			baseTabDto.setDtObject(null);
			if (marketPlaceDTO != null && tempMarketPlaceDTO != null) {
				marketPlaceDTO.setPrimaryContList(tempMarketPlaceDTO
						.getPrimaryContList());
				marketPlaceDTO.setSecondaryContList(tempMarketPlaceDTO
						.getSecondaryContList());
				if (marketPlaceDTO.getActivityList() == null)
					marketPlaceDTO.setActivityList(tempMarketPlaceDTO
							.getActivityList());
			}
		}
		// add for header
		String resourceName = (String) ActionContext.getContext().getSession()
				.get("resourceName");
		logger.debug("Marketplace resourceName : " + resourceName);
		marketPlaceDTO.setFullResoueceName(resourceName);
		return "load";
	}

	public String execute() throws Exception {
		String action = marketPlaceDTO.getAction();

		if ("Previous".equals(action)) {
			return doPrevious();
		} else if ("Save".equals(action)) {
			return doSave();
		} else if ("Next".equals(action)) {
			return doNext();
		} else if ("Update".equals(action)) {
			return updateProfile();
		} else if ("Welcome".equals(action)) {
			return doWelcome();
		} else if ("Reset".equals(action)) {
			return doReset();
		} else {
			return doLoad();
		}
	}

	private void loadData() throws Exception {

		logger.debug("This is doLoad() for MarketPlaceAction.java file");

		setContextDetails();

		String resourceId = (String) ActionContext.getContext().getSession()
				.get("resourceId");
		String userName = manageUsersDelegate.getUserNameFromResourceId(
				resourceId, OrderConstants.PROVIDER_ROLEID);

		// String userName = (String)(securityContext.getUsername());
		marketPlaceDTO.setEntityId(securityContext.getCompanyId().toString());
		marketPlaceDTO.setRoleId(securityContext.getRoleId().toString());
		marketPlaceDTO.setLoggedInResourceId(securityContext
				.getVendBuyerResId());
		logger.debug("This is from session, userName = " + userName);
		// Set the Admin add/edit DTO
		setAdminAddEditUserDTO(manageUsersDelegate.getProviderUser(userName));

		setResetPasswordPersmission();

		if (securityContext.isPrimaryInd()) {
			this.setPrimaryIndFlag(true);
			ActionContext.getContext().getSession()
					.put("primaryInd", this.primaryIndFlag);
		}

		logger.debug(" -----------  Resource Id on Load ------ " + resourceId);

		marketPlaceDTO.setResourceID(resourceId);

		// Loads the information for the new or already existing user.
		marketPlaceDTO = iMarketPlaceDelegate.loadMarketPlace(marketPlaceDTO,
				userName);

		ActionContext.getContext().getSession()
				.put("activityList", marketPlaceDTO.getActivityList());
		// add for header
		String resourceName = (String) ActionContext.getContext().getSession()
				.get("resourceName");
		logger.debug("Marketplace resourceName : " + resourceName);
		marketPlaceDTO.setFullResoueceName(resourceName);

		logger.debug("[MarketPlace] ----- Service Call ---- "
				+ marketPlaceDTO.getServiceCall());
	}

	@SkipValidation
	public String doLoad() throws Exception {
		String resourceId = (String) ActionContext.getContext().getSession()
				.get("resourceId");
		setContextDetails();

		if (resourceId == null || resourceId.trim().length() == 0) {
			addActionMessage("You can't create marketplace preferences without saving GeneralInfo");
			return "load";
		}

		loadData();
		getSessionMessages();
		return "load";
	}

	public String doSave() throws Exception {

		try {

			// New/Existing Provider User Name is retrieved from the Session.
			// String provUser = (String)getSession().get("ProviderUser");

			String retStatus = SaveDetails();
			if (INPUT.equals(retStatus)) {
				return INPUT;
			}
		} catch (Exception a_Ex) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * Reset the password for the user. Reset password button will conditionally
	 * change to send registration email, if user has never logged in.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String doReset() throws Exception {
		try {
			String result = doSave();
			resetPassword();
			return result;
		} catch (Exception ex) {
			return ERROR;
		}

	}

	/**
	 * Resend welcome email.Reset password button will conditionally change to
	 * send registration email, if user has never logged in.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String doWelcome() throws Exception {
		// Save any changes on this page first.
		doSave();
		String email = marketPlaceDTO.getEmail();
		String resourceId = (String) ActionContext.getContext().getSession()
				.get("resourceId");
		String username = manageUsersDelegate.getUserNameFromResourceId(
				resourceId, OrderConstants.PROVIDER_ROLEID);

		boolean flag = false;
		if (StringUtils.isNotBlank(username)) {
			flag = manageUsersDelegate.resendWelcomeMail(username, email);
		}

		if (flag) {
			setActionMessage("Welcome email has been resent.");
		} else {
			setActionError("Error in sending Welcome email.");
		}

		return SUCCESS;
	}

	/**
	 * Performs Reset Password functionality.
	 * 
	 * @return
	 * @throws Exception
	 */
	private String resetPassword() throws Exception {
		// String username = (String)(securityContext.getUsername());
		String resourceId = (String) ActionContext.getContext().getSession()
				.get("resourceId");
		String userName = manageUsersDelegate.getUserNameFromResourceId(
				resourceId, OrderConstants.PROVIDER_ROLEID);

		if (userName == null) { // very unlike, need to find suitable error
								// message for this case.
			logger.debug("Could not reset password as username is null");
			setActionError("Could not reset password as user name is not found.");
			return SUCCESS;
		}

		manageUsersDelegate.resetPassword(userName);
		setActionMessage("Password has been reset for user " + userName);
		logger.debug("Password has been reset for provider-user:" + userName);
		return SUCCESS;
	}

	private String SaveDetails() throws Exception {
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
				.getSession().get("tabDto");
		marketPlaceDTO.setActivityList((List) (ActionContext.getContext()
				.getSession().get("activityList")));
		
		// R16_1: SL-18979: Fetching userName from security context
		securityContext = (SecurityContext) ActionContext.getContext()
				.getSession().get("SecurityContext");
		marketPlaceDTO.setLoginUserName(securityContext.getUsername());
		try {

			validateBusinessPhone();
			validateMobile();
			validateMail();

			// Validating the SMS and Alternate Email Address
			validateSMS();
			validateAltEmail();

			if (null != ActionContext.getContext().getSession()
					.get("primaryInd")) {
				String primaryInd = ActionContext.getContext().getSession()
						.get("primaryInd").toString();
				if ("true".equals(primaryInd)) {
					this.setPrimaryIndFlag(true);
				} else {
					this.setPrimaryIndFlag(false);
				}

			}

			logger.debug(" before activities ---------->"
					+ marketPlaceDTO.getActivityList());

			
			//--Code Added for Mobile API-Login Provider
			String providerId = (String) ActionContext.getContext()
					.getSession().get("resourceId");
			String userName = manageUsersDelegate.getUserNameFromResourceId(
					providerId, OrderConstants.PROVIDER_ROLEID);

			// Fetching Current User Activities from Database
			List<ActivityVO> activityForProvider = manageUsersDelegate
					.getProviderActivityList(userName);
			// Flag for expiring mobile token
			boolean flag = false;
			// Flag to execute the code for mobile expiration only once
			boolean loopExit = false;

			// Iterating through the list of Activities displayed in FrontEnd
			for (int i = 0; i < marketPlaceDTO.getActivityList().size(); ++i) {
				logger.debug(" in activitylist ---------->"
						+ marketPlaceDTO.getActivityList());
				flag = false;
				String[] isCheckeds = (String[]) (request.getParameterMap()
						.get("isCheckedactivity_" + i));

				// When the checked Activity in FrontEnd is false
				if (isCheckeds == null) {
					logger.debug(" inside false");
					((UserActivityVO) marketPlaceDTO.getActivityList().get(i))
							.setChecked(false);

					if (!loopExit) {

						// Checking User Activity with the current List of
						// user activity in DB
						flag = checkForTokenExpiration(activityForProvider,
								flag, i);
						if (flag) {
							// Expiring Mobile Token once
							manageUsersDelegate.expireMobileTokenforFrontEnd(
									Integer.parseInt(providerId),
									MPConstants.ACTIVE);
							flag = false;
							loopExit = true;
						}
					}
				} else {
					String isChecked = isCheckeds[0];
					// When the checked Activity in FrontEnd is True
					if ((isChecked != null) && (isChecked.equals("true"))) {
						((UserActivityVO) marketPlaceDTO.getActivityList().get(
								i)).setChecked(true);

						if (!loopExit) {
							flag = checkForTokenExpiration(activityForProvider,
									flag, i);

							if (!flag) {
								manageUsersDelegate
										.expireMobileTokenforFrontEnd(
												Integer.parseInt(providerId),
												MPConstants.ACTIVE);
								loopExit = true;
							}
						}

					} else {
						logger.debug(" inside false");
						((UserActivityVO) marketPlaceDTO.getActivityList().get(
								i)).setChecked(false);

						if (!loopExit) {
							flag = checkForTokenExpiration(activityForProvider,
									flag, i);
							if (flag) {
								manageUsersDelegate
										.expireMobileTokenforFrontEnd(
												Integer.parseInt(providerId),
												MPConstants.ACTIVE);
								flag = false;
								loopExit = true;
							}
						}
					}
				}
				if (isPrimaryIndFlag()
						&& marketPlaceDTO.getPrimaryIndicator().equals("1")) {
					((UserActivityVO) marketPlaceDTO.getActivityList().get(i))
							.setChecked(true);
				}

			}

			if (hasFieldErrors() || hasActionErrors()) {
				logger.debug("VALIDATION ERROR-------" + getFieldErrors());
				baseTabDto = (BaseTabDto) ActionContext.getContext()
						.getSession().get("tabDto");
				baseTabDto.setDtObject(marketPlaceDTO);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getActionErrors().addAll(getActionErrors());
				baseTabDto.getTabStatus().put(
						ActivityRegistryConstants.RESOURCE_MARKETPLACE,
						"errorOn");
				return INPUT;
			}

			String resourceId = (String) ActionContext.getContext()
					.getSession().get("resourceId");

			if (resourceId == null || resourceId.trim().length() == 0) {
				logger.debug("You can't create marketplace preferences without saving GeneralInfo");
				addActionMessage("You can't create marketplace preferences without saving GeneralInfo");
				if (baseTabDto != null) {
					baseTabDto.getTabStatus().put(
							ActivityRegistryConstants.RESOURCE_MARKETPLACE,
							"errorOn");
					baseTabDto.getActionMessages().addAll(getActionMessages());
				}
				return ERROR;
			}

			marketPlaceDTO.setResourceID(resourceId);

			// Builds a list for finding the Permission to Add/Update/Delete
			List retList = buildList(marketPlaceDTO.getGeneralActivityList(),
					request);
			marketPlaceDTO.setResultList(retList);

			// Calls the Update Function
			iMarketPlaceDelegate.updateMarketPlace(marketPlaceDTO);
			
			//R16_1 SL-18979: Setting new error messages depending on vibes response
			validateSMSVibes();
			
			if (hasFieldErrors()) {
				logger.debug("VALIDATION ERROR-------" + getFieldErrors());
				baseTabDto = (BaseTabDto) ActionContext.getContext()
						.getSession().get("tabDto");
				baseTabDto.setDtObject(marketPlaceDTO);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(
						ActivityRegistryConstants.RESOURCE_MARKETPLACE,
						"errorOn");
				return INPUT;
			}

			auditUserProfileLog(marketPlaceDTO);

			/**
			 * Activity Registry Update
			 */
			logger.debug(" Inside Activity Registry Update ");
			if (baseTabDto != null) {
				baseTabDto.getTabStatus().put(
						ActivityRegistryConstants.RESOURCE_MARKETPLACE,
						"complete");
				baseTabDto.getActionMessages().addAll(getActionMessages());
			}

		} catch (DuplicateUserException due) {
			logger.debug("EXCEPTION--" + due.getMessage());

			addFieldError("marketPlaceDTO.email", due.getMessage());
			baseTabDto.getFieldsError().putAll(getFieldErrors());

			baseTabDto.setDtObject(marketPlaceDTO);

			baseTabDto.getTabStatus().put(
					ActivityRegistryConstants.RESOURCE_MARKETPLACE, "errorOn");
			return INPUT;
		} catch (Exception a_Ex) {
			logger.info(a_Ex.getMessage(), a_Ex);
			return ERROR;
		}

		return SUCCESS;
	}

	private boolean checkForTokenExpiration(
			List<ActivityVO> activityForProvider, boolean flag, int i) {
		for (ActivityVO activity : activityForProvider) {
			if (((UserActivityVO) marketPlaceDTO.getActivityList().get(i))
					.getActivityName().equals(activity.getActivityName())) {
				flag = true;
				break;

			}

		}
		return flag;
	}

	private void auditUserProfileLog(MarketPlaceDTO marketPlaceDTO) {
		XStream xstream = new XStream();
		Class[] classes = new Class[] { MarketPlaceDTO.class };
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(marketPlaceDTO);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("PROVIDER_USER_PROFILE_EDIT");
		SecurityContext securityContext = (SecurityContext) ActionContext
				.getContext().getSession().get("SecurityContext");
		if (securityContext != null) {
			auditUserProfileVO
					.setLoginCompanyId(securityContext.getCompanyId());
			auditUserProfileVO.setLoginResourceId(securityContext
					.getVendBuyerResId());
			auditUserProfileVO.setRoleId(securityContext.getRoleId());
			if (securityContext.isSlAdminInd())
				auditUserProfileVO.setIsSLAdminInd(1);
			auditUserProfileVO.setModifiedBy(securityContext.getUsername());
			auditUserProfileVO.setUserProfileData(xmlContent);
			auditLogDelegates.auditUserProfile(auditUserProfileVO);
		}
	}

	private List buildList(List list, HttpServletRequest request)
			throws Exception {
		List retList = new ArrayList();

		for (int count = 0; count < list.size(); count++) {
			LookupVO lookUp = (LookupVO) list.get(count);

			if (lookUp != null) {
				String key = "radio_" + lookUp.getId();
				String listValue = "";
				String value = request.getParameter(key);
				// If the value == null - It means the check box is not checked
				if (value == null)
					listValue = "false_" + lookUp.getId();
				else if (!value.equals("0")) // If the value == 0 then these are
												// permissions not shown on the
												// page
					listValue = "true_" + lookUp.getId();

				retList.add(listValue);
			}
		}

		return retList;
	}

	public String doNext() throws Exception {
		String retStr = SaveDetails();
		if (retStr != null && retStr.trim().equalsIgnoreCase(SUCCESS))
			return "next";

		return retStr;
	}

	public String doPrevious() throws Exception {
		String retStr = SaveDetails();
		if (retStr != null && retStr.trim().equalsIgnoreCase(SUCCESS))
			return "previous";

		return retStr;
	}

	/**
	 * GGanapathy Used to update profile after saving the Market Place
	 * Preference page
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateProfile() throws Exception {
		String retStr = SaveDetails();
		return "updateProfile";
	}

	@SkipValidation
	public String doCancel() throws Exception {
		return "cancel";
	}

	public void setSession(Map ssessionMap) {
		this.sSessionMap = ssessionMap;
	}

	public Map getSession() {
		return this.sSessionMap;
	}

	public MarketPlaceDTO getMarketPlaceDTO() {
		return marketPlaceDTO;
	}

	public void setMarketPlaceDTO(MarketPlaceDTO marketPlaceDTO) {
		this.marketPlaceDTO = marketPlaceDTO;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	private void getSessionMessages() {
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
				.getSession().get("tabDto");
		// getting messages from session
		if (baseTabDto != null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap());
			setActionErrors(baseTabDto.getActionErrors());
			baseTabDto.setActionErrors(new ArrayList<String>());
			setActionMessages(baseTabDto.getActionMessages());
			baseTabDto.setActionMessages(new ArrayList<String>());
		} else {
			String obj = (String) ActionContext.getContext().getSession()
					.get("action_msg");
			if (obj != null) {
				addActionMessage(obj);
				ActionContext.getContext().getSession().remove("action_msg");
			}

			obj = (String) ActionContext.getContext().getSession()
					.get("action_error");
			if (obj != null) {
				addActionError(obj);
				ActionContext.getContext().getSession().remove("action_error");
			}
		}
	}

	private void setContextDetails() {
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session
				.getAttribute("SecurityContext");
	}

	private void validateBusinessPhone() {
		String busPhone1 = null;
		String busPhone2 = null;
		String busPhone3 = null;

		List list1 = new ArrayList();

		try {
			busPhone1 = (marketPlaceDTO.getBusinessPhone1() != null) ? marketPlaceDTO
					.getBusinessPhone1().trim() : "";
			busPhone2 = (marketPlaceDTO.getBusinessPhone2() != null) ? marketPlaceDTO
					.getBusinessPhone2().trim() : "";
			busPhone3 = (marketPlaceDTO.getBusinessPhone3() != null) ? marketPlaceDTO
					.getBusinessPhone3().trim() : "";

			busPhone1 = busPhone1.trim() + busPhone2.trim() + busPhone3.trim();
			list1.add(busPhone1);
			isValidNumber(list1, "Business Phone ",
					"marketPlaceDTO.businessPhone1");

		} catch (Exception a_Ex) {
			addFieldError("marketPlaceDTO.businessPhone1",
					"Error while validating the Business Phone Number.");
		}
	}

	private void validateMobile() {
		String mobile1 = null;
		String mobile2 = null;
		String mobile3 = null;

		List list1 = new ArrayList();

		try {
			mobile1 = (marketPlaceDTO.getMobilePhone1() != null) ? marketPlaceDTO
					.getMobilePhone1().trim() : "";
			mobile2 = (marketPlaceDTO.getMobilePhone2() != null) ? marketPlaceDTO
					.getMobilePhone2().trim() : "";
			mobile3 = (marketPlaceDTO.getMobilePhone3() != null) ? marketPlaceDTO
					.getMobilePhone3().trim() : "";

			mobile1 = mobile1 + mobile2 + mobile3;
			if (mobile1 != null && mobile1.trim().length() > 0) {
				list1.add(mobile1);
				isValidNumber(list1, "Mobile Phone ",
						"marketPlaceDTO.mobilePhone1");
			}

		} catch (Exception a_Ex) {
			addFieldError("marketPlaceDTO.mobilePhone1",
					"Error while validating the Mobile Phone Number.");
		}
	}

	private void validateMail() {
		String email = null;
		String confirmEmail = null;

		boolean checkFlag1 = true;
		boolean checkFlag2 = true;

		Pattern pattern;
		Matcher matcher;
		String validEmailRegex = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

		try {
			if (marketPlaceDTO != null) {
				email = (marketPlaceDTO.getEmail() != null) ? marketPlaceDTO
						.getEmail() : "";
				confirmEmail = (marketPlaceDTO.getConfirmEmail() != null) ? marketPlaceDTO
						.getConfirmEmail() : "";

				if (email == null || email.trim().length() <= 0
						|| email.trim().equals("")) {
					addFieldError("marketPlaceDTO.email",
							"E-mail Address is required.");
					checkFlag1 = false;
				}

				if (confirmEmail == null || confirmEmail.trim().length() <= 0
						|| confirmEmail.trim().equals("")) {
					addFieldError("marketPlaceDTO.confirmEmail",
							"Confirm E-mail Address is required.");
					checkFlag2 = false;
				}

				if (checkFlag1 && checkFlag2) {
					pattern = Pattern.compile(validEmailRegex);
					matcher = pattern.matcher(marketPlaceDTO.getEmail());

					if (!matcher.matches()) {
						addFieldError("marketPlaceDTO.email",
								"Enter a valid email address.");
						checkFlag1 = false;
					}

					matcher = pattern.matcher(marketPlaceDTO.getConfirmEmail());

					if (!matcher.matches()) {
						addFieldError("marketPlaceDTO.confirmEmail",
								"Enter a valid confirm email address.");
						checkFlag2 = false;
					}

				}

				if (checkFlag1 && checkFlag2) {
					if (!email.trim().equals(confirmEmail.trim())) {
						addFieldError("marketPlaceDTO.confirmEmail",
								"Re-Enter Confirm E-mail Address.");
					}
				}

			}
		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			addFieldError("marketPlaceDTO.email",
					"Error Parsing Alternate E-mail");
		}
	}

	private void validateSMS() {
		String sms1 = null;
		String sms2 = null;
		String sms3 = null;

		String altSms1 = null;
		String altSms2 = null;
		String altSms3 = null;

		boolean checkFlag1 = false;
		boolean checkFlag2 = false;

		List list1 = new ArrayList();
		List list2 = new ArrayList();

		try {
			/**
			 * Validation for Selecting the SMS Address when the User does not
			 * perform Service Call.
			 */
			if (marketPlaceDTO != null) {
				// Modified to Show error when the user does not have the
				// privilege
				// to select SMS As Secondary Contact.
				String serviceCall = marketPlaceDTO.getServiceCall();
				if (StringUtils.isEmpty(serviceCall)
						|| StringUtils.equals("0", serviceCall)) {

					if (marketPlaceDTO.getSecondaryContact1() != null
							&& marketPlaceDTO.getSecondaryContact1().intValue() == 3) {
						addFieldError("marketPlaceDTO.secondaryContact1",
								"User cannot select SMS Address as Secondary Contact Method");
						return;
					}

					if (marketPlaceDTO.getPrimaryContact() != null
							&& marketPlaceDTO.getPrimaryContact().intValue() == 3) {
						addFieldError("marketPlaceDTO.primaryContact",
								"User cannot select SMS Address as Primary Contact Method");
						return;
					}
				}
			}

			if (marketPlaceDTO != null) {

				sms1 = (marketPlaceDTO.getSmsAddress1() != null) ? marketPlaceDTO
						.getSmsAddress1() : "";
				sms2 = (marketPlaceDTO.getSmsAddress2() != null) ? marketPlaceDTO
						.getSmsAddress2() : "";
				sms3 = (marketPlaceDTO.getSmsAddress3() != null) ? marketPlaceDTO
						.getSmsAddress3() : "";

				if (sms1.trim().length() > 0 || sms2.trim().length() > 0
						|| sms3.trim().length() > 0) {
					list1.add(sms1.trim());
					list1.add(sms2.trim());
					list1.add(sms3.trim());
					if (!validation4SMS(list1, "SMS Address",
							"marketPlaceDTO.smsAddress1"))
						return;
					else
						checkFlag1 = true;
				}

				altSms1 = (marketPlaceDTO.getConfirmSmsAddress1() != null) ? marketPlaceDTO
						.getConfirmSmsAddress1() : "";
				altSms2 = (marketPlaceDTO.getConfirmSmsAddress2() != null) ? marketPlaceDTO
						.getConfirmSmsAddress2() : "";
				altSms3 = (marketPlaceDTO.getConfirmSmsAddress3() != null) ? marketPlaceDTO
						.getConfirmSmsAddress3() : "";

				if (altSms1.length() > 0 || altSms2.length() > 0
						|| altSms3.length() > 0) {
					list2.add(altSms1.trim());
					list2.add(altSms2.trim());
					list2.add(altSms3.trim());
					if (!validation4SMS(list2, "Confirm SMS Address",
							"marketPlaceDTO.confirmSmsAddress1"))
						return;
					else
						checkFlag2 = true;
				}

				if (checkFlag1 == true || checkFlag2 == true) {

					if (!compareSMS(list1, list2)) {
						addFieldError("marketPlaceDTO.confirmSmsAddress1",
								"SMS Address does not match. Please Re-Enter SMS Address.");
						return;
					}
				}

				if (sms1.trim().length() <= 0 && sms2.trim().length() <= 0
						&& sms3.trim().length() <= 0
						&& altSms1.trim().length() <= 0
						&& altSms1.trim().length() <= 0
						&& altSms1.trim().length() <= 0) {
					if (marketPlaceDTO.getPrimaryContact() != null
							&& marketPlaceDTO.getPrimaryContact().intValue() == 3) {
						addFieldError("marketPlaceDTO.primaryContact",
								"Please enter SMS Address to select SMS as Primary Contact method");
					}

					if (marketPlaceDTO.getSecondaryContact1() != null
							&& marketPlaceDTO.getSecondaryContact1().intValue() == 3) {
						addFieldError("marketPlaceDTO.secondaryContact1",
								"Please enter SMS Address to select SMS as Secondary Contact Method");
					}
				}
				//R16_1: SL-18979: Commenting out code since Validating sms no is done through vibes call
				/*if (marketPlaceDTO.getSecondaryContact1() != null
						&& marketPlaceDTO.getSecondaryContact1().intValue() == 3) {
					if (!(iMarketPlaceDelegate.validateSmsNo(marketPlaceDTO))) {
						addFieldError("marketPlaceDTO.smsAddress1",
								"SMS Address entered is not valid. Please enter correct SMS Address");
						return;
					}
				}*/

			}
		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			addFieldError("marketPlaceDTO.Error.ParsingSMS",
					"Error Parsing Alternate E-mail");
		}

	}

	private boolean validation4SMS(List smsList, String errorMsg,
			String fieldName) {
		String sms1 = null;
		String sms2 = null;
		String sms3 = null;
		boolean flag = true;

		try {
			if (smsList == null || smsList.size() <= 0) {
				addFieldError(fieldName, errorMsg + " is incomplete");
				return false;
			}

			sms1 = (smsList.get(0) != null) ? (String) smsList.get(0) : "";
			sms2 = (smsList.get(1) != null) ? (String) smsList.get(1) : "";
			sms3 = (smsList.get(2) != null) ? (String) smsList.get(2) : "";

			// Validation for Incomplete SMS address field
			if (sms1.trim().length() <= 0 || sms2.trim().length() <= 0
					|| sms3.trim().length() <= 0 || sms1.trim().equals("")
					|| sms2.trim().equals("") || sms3.trim().equals(""))
				addFieldError(fieldName, errorMsg + " is incomplete");

			// Validating for the SMS type
			if (!StringUtils.isNumeric(sms1) || !StringUtils.isNumeric(sms2)
					|| !StringUtils.isNumeric(sms3)) {
				addFieldError(fieldName, errorMsg + " must be a number");
				return false;
			}

			// validation for length
			if (sms1.trim().length() < 3) {
				addFieldError(fieldName, errorMsg
						+ " Area Code can not be less than 3 characters");
				flag = false;
			}

			if (sms2.trim().length() < 3) {
				addFieldError(fieldName, errorMsg
						+ " can not be less than 3 characters");
				flag = false;
			}

			if (sms3.trim().length() < 3) {
				addFieldError(fieldName, errorMsg
						+ " can not be less than 4 characters");
				flag = false;
			}

		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			return false;
		}

		return flag;
	}

	private boolean compareSMS(List sms, List confirmSMS) {
		String sms1 = null;
		String sms2 = null;
		String sms3 = null;

		String altSms1 = null;
		String altSms2 = null;
		String altSms3 = null;

		try {
			if (sms != null && sms.size() > 0 && confirmSMS != null
					&& confirmSMS.size() > 0) {
				sms1 = (sms.get(0) != null) ? (String) sms.get(0) : "";
				sms2 = (sms.get(1) != null) ? (String) sms.get(1) : "";
				sms3 = (sms.get(2) != null) ? (String) sms.get(2) : "";

				altSms1 = (confirmSMS.get(0) != null) ? (String) confirmSMS
						.get(0) : "";
				altSms2 = (confirmSMS.get(1) != null) ? (String) confirmSMS
						.get(1) : "";
				altSms3 = (confirmSMS.get(2) != null) ? (String) confirmSMS
						.get(2) : "";

				String strSMS = sms1.trim().concat(sms2.trim())
						.concat(sms3.trim());
				String strConfirmSMS = altSms1.trim().concat(altSms2.trim())
						.concat(altSms3.trim());

				if (!strSMS.equals(strConfirmSMS))
					return false;
			} else
				return false;

		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean isValidNumber(List numberList, String errorMsg,
			String fieldName) {
		String num1 = null;
		String num2 = null;
		String num3 = null;
		boolean flag = true;

		try {
			if (numberList == null || numberList.size() <= 0) {
				addFieldError(fieldName, errorMsg + " is incomplete");
				return false;
			}

			num1 = (numberList.get(0) != null) ? (String) numberList.get(0)
					: "";

			// Validation for Incomplete SMS address field
			if (num1.trim().length() <= 0 || num1.trim().equals(""))
				addFieldError(fieldName, errorMsg + " is incomplete");

			// Validating for the NUMBER type
			try {
				long numInt1 = Long.parseLong(num1.trim());

			} catch (NumberFormatException a_Ex) {
				addFieldError(fieldName, errorMsg + " must be a number");
				return false;
			} catch (Exception a_Ex) {
				addFieldError(fieldName, errorMsg + " must be a number");
				return false;
			}

			// validation for length
			if (num1.trim().length() < 10) {
				addFieldError(fieldName, errorMsg
						+ "  can not be less than 10 characters");
				flag = false;
			}

		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			return false;
		}

		return flag;
	}

	private void validateAltEmail() {
		String altEmail = null;
		String confirmAltEmail = null;
		boolean checkFlag1 = true;
		boolean checkFlag2 = true;
		Pattern pattern;
		Matcher matcher;

		String validEmailRegex ="\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

		try {
			if (marketPlaceDTO != null) {
				altEmail = (marketPlaceDTO.getAltEmail() != null) ? marketPlaceDTO
						.getAltEmail() : "";
				confirmAltEmail = (marketPlaceDTO.getConfirmAltEmail() != null) ? marketPlaceDTO
						.getConfirmAltEmail() : "";

				if (altEmail.trim().length() <= 0
						&& confirmAltEmail.trim().length() <= 0) {
					if (marketPlaceDTO.getPrimaryContact() != null
							&& marketPlaceDTO.getPrimaryContact().intValue() == 2) {
						addFieldError("marketPlaceDTO.primaryContact",
								"User cannot select Alternate Email Address as Primary Contact Method");
						checkFlag1 = false;

					}

					if (marketPlaceDTO.getSecondaryContact1() != null
							&& marketPlaceDTO.getSecondaryContact1().intValue() == 2) {
						addFieldError("marketPlaceDTO.secondaryContact1",
								"User cannot select Alternate Email Address as Secondary Contact Method");
						checkFlag1 = false;
					}

				} else {
					if (altEmail.trim().length() > 0
							|| confirmAltEmail.trim().length() > 0) {
						if (checkFlag1) {
							pattern = Pattern.compile(validEmailRegex);
							matcher = pattern.matcher(altEmail.trim());

							if (!matcher.matches()) {
								addFieldError("marketPlaceDTO.altEmail",
										"Invalid alternate email address. Please enter a valid alternate email address");
								checkFlag1 = false;
							}
							matcher = pattern.matcher(confirmAltEmail
									.trim());
							if (!matcher.matches()) {
								addFieldError(
										"marketPlaceDTO.confirmAltEmail",
										"Invalid confirm alternate email address. Please enter a valid confirm alternate email address");
								checkFlag2 = false;
							}
						   }
							if (checkFlag1 && checkFlag2) {
								if (!altEmail.trim().equals(confirmAltEmail.trim())) {
									addFieldError("marketPlaceDTO.confirmAltEmail",
											"Alternate E-mail does not match. Please Re-Enter Alternate E-mail.");
								}
							}
						}
					}
				}
		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			addFieldError("marketPlaceDTO.Error.ParsingEmail",
					"Error Parsing Alternate E-mail");
		}
	}

	private boolean isValidEmail(String str) {

		String at = "@";
		String dot = ".";
		int lat = str.indexOf(at);
		int lstr = str.length();

		if (str.indexOf(at) == -1) {
			return false;
		}

		if (str.indexOf(at) == -1 || str.indexOf(at) == 0
				|| str.indexOf(at) == lstr) {
			return false;
		}

		if (str.indexOf(dot) == -1 || str.indexOf(dot) == 0
				|| str.indexOf(dot) == lstr) {
			return false;
		}

		if (str.indexOf(at, (lat + 1)) != -1) {
			return false;
		}

		if (str.substring(lat - 1, lat) == dot
				|| str.substring(lat + 1, lat + 2) == dot) {
			return false;
		}

		if (str.indexOf(dot, (lat + 2)) == -1) {
			return false;
		}

		if (str.indexOf(" ") != -1) {
			return false;
		}

		return true;
	}

	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}

	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}

	public void setAdminAddEditUserDTO(AdminAddEditUserDTO adminAddEditUserDTO) {
		this.adminAddEditUserDTO = adminAddEditUserDTO;
	}

	protected void setAttribute(String name, Object obj) {
		ServletActionContext.getRequest().setAttribute(name, obj);

	}

	public AdminAddEditUserDTO getAdminAddEditUserDTO() {
		return this.adminAddEditUserDTO;
	}

	/**
	 * This sets the reset password permissions. ProviderAdmin will always get
	 * this permission as his resetting password for his/her children. SLAdmin
	 * will get this permission only if super admin granted it. SuperAdmin will
	 * have always have this permission.
	 */
	private void setResetPasswordPersmission() {
		SecurityContext securityContext = (SecurityContext) getSession().get(
				"SecurityContext");

		// If SLAdmin, then check if he/she got a permission to reset password
		// for external users.
		if (securityContext != null && securityContext.getAdminRoleId() == 2) {
			if (securityContext.getRoles().isPasswordResetForAllExternalUsers())
				setAttribute("passwordResetForAllExternalUsers", "true");
			else
				setAttribute("passwordResetForAllExternalUsers", "false");
		} else { // For Provider Admin and super admin.
			setAttribute("passwordResetForAllExternalUsers", "true");
		}
	}

	private void setActionMessage(String msg) {
		addActionError(msg);
		ActionContext.getContext().getSession().put("action_msg", msg);
	}

	private void setActionError(String msg) {
		addActionError(msg);
		ActionContext.getContext().getSession().put("action_error", msg);
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}
	
	//R16_1: SL-18979: Setting error messages if any error response comes from vibes 
	private void validateSMSVibes() {
		if (null != marketPlaceDTO && StringUtils.isNotBlank(marketPlaceDTO.getVibesError())) {
				addFieldError("marketPlaceDTO.smsAddress1",
						getErrorMessage(marketPlaceDTO.getVibesError()));
		}
	}
	
	 /**R16_1:SL-18979: Fetching error message from property file
     * @param error
     * @return ErrorMessage
     */
	private String getErrorMessage(String error) {
  	  javax.servlet.ServletContext servletContext = ServletActionContext.getServletContext();
		  LocalizationContext localizationContext = (LocalizationContext)servletContext.getAttribute("serviceliveCopyBundle");
		  return localizationContext.getResourceBundle().getString(error);
	}
}
/*
 * Maintenance History $Log: MarketPlaceAction.java,v $ Revision 1.33 2008/04/26
 * 01:13:50 glacy Shyam: Merged I18_Fin to HEAD.
 * 
 * Revision 1.31.6.1 2008/04/23 11:41:40 glacy Shyam: Merged HEAD to finance.
 * 
 * Revision 1.32 2008/04/23 05:19:35 hravi Shyam: Reverting to build 247.
 * 
 * Revision 1.31 2008/02/26 18:18:06 mhaye05 Merged Iteration 17 Branch into
 * Head
 * 
 * Revision 1.29.2.2 2008/02/25 18:58:49 mhaye05 replaced system out println
 * with logger.debug statements and some general code cleanup
 */