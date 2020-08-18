/*
 * $Id: ChangePasswordAction.java,v 1.14 2008/05/30 21:14:40 gjacks8 Exp $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.newco.marketplace.web.action.provider;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.provider.LoginVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISODashBoardDelegate;
import com.newco.marketplace.web.delegates.ISecurityDelegate;
import com.newco.marketplace.web.delegates.provider.IChangePasswordDelegate;
import com.newco.marketplace.web.delegates.provider.IForgetUsernameDelegate;
import com.newco.marketplace.web.delegates.provider.ILoginDelegate;
import com.newco.marketplace.web.delegatesImpl.provider.ForgetUsernameDelegateImpl;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.ChangePasswordDto;
import com.newco.marketplace.web.dto.provider.ForgotUsernameDto;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validation;


/**
 * @author KSudhanshu
 */
@Validation
public class ChangePasswordAction extends SLBaseAction implements Preparable { 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7656424401504829695L;
	private IChangePasswordDelegate changePasswordDelegate;
	private  ChangePasswordDto changePasswordDto;
	private String id = null;
	private String password; //
	private String username; //
	private String tempPassword;
	private  String confirmPassword;//added by MTedder@covansys.com
	private  String secretQuestion;
	private  String secretAnswer;
	private boolean showSecretQuestion = false;
	private  Map questionMap = null;
	private IForgetUsernameDelegate forgetUserName;
	private ISecurityDelegate securityDel = null;
	private String resourceLastName;
	private String resourceFirstName;
	private ILoginDelegate iLoginDelegate;
	private ISODashBoardDelegate dashboardDelegate;
	private LoginCredentialVO lcvo = null;
	private IUserProfileDao iUserProfileDao = null;
	private LoginVO loginVO = null;
	private static String deepLinkCode; //Password in the email for change password.
	private boolean isPassworURLExpired = false;
	
	private static final Logger logger = Logger.getLogger(ChangePasswordAction.class
			.getName());


	/*
	 * (non-Javadoc)
	 *
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prepare() throws Exception
	{
		load();
		if(getVerificationCount(getUsername()) >= 3 || !isSecurityQuestionExists(getUsername())){
			setShowSecretQuestion(true);
		}
	}
	
	/**
	 * @param changePasswordDelegate
	 * @param changePasswordDto
	 */
	public ChangePasswordAction(IChangePasswordDelegate changePasswordDelegate,
			ChangePasswordDto changePasswordDto, ForgetUsernameDelegateImpl forgetUserName,
			ISecurityDelegate securityDel,ILoginDelegate loginDelegate, ISODashBoardDelegate dashboardDelegate, 
			IUserProfileDao iUserProfileDao) {
		this.changePasswordDelegate = changePasswordDelegate;
		this.changePasswordDto = changePasswordDto;
		this.forgetUserName = forgetUserName;
		this.securityDel = securityDel;
		this.iLoginDelegate = loginDelegate;
		this.dashboardDelegate = dashboardDelegate;
		this.iUserProfileDao = iUserProfileDao;
	}
	
	/**
	 * Authenticates the user and loads the security context.
	 * TODO : This logs in user before changing the password, needs to be re factored/removed once SSO is implemented.  
	 * @throws Exception
	 */
	private void loadSecurityContext() throws Exception 
	{
		
		if (securityDel.simpleAuthenticate(getUsername(), getTempPassword().trim())) {
			AccountProfile accountProfile = securityDel
					.getAccountProfile(getUsername());
			_commonCriteria = new ServiceOrdersCriteria(accountProfile);
			getSession().setAttribute(
					OrderConstants.SERVICE_ORDER_CRITERIA_KEY, _commonCriteria);
			resourceLastName = accountProfile.getLastName();
			resourceFirstName = accountProfile.getFirstName();
		}

		LoginCredentialVO lvo = new LoginCredentialVO();
		lvo.setUsername(getUsername());
		lvo.setPassword(getTempPassword().trim());

		SecurityContext securityCntxt = new SecurityContext(lvo.getUsername());
		lcvo = securityDel.getUserRoles(lvo);

		securityCntxt.setRoles(lcvo);
		securityCntxt.setCompanyId(99);
		securityCntxt.setRoleId(lcvo.getRoleId());
		securityCntxt.setRole(lcvo.roleName);

		switch (lcvo.getRoleId().intValue()) {
		case OrderConstants.BUYER_ROLEID:
			loadBuyerDetails(securityCntxt);
			break;
		case OrderConstants.SIMPLE_BUYER_ROLEID:
			loadBuyerDetails(securityCntxt);
			break;
		case OrderConstants.PROVIDER_ROLEID:
			loadProviderDetails(securityCntxt);
			break;
		case OrderConstants.NEWCO_ADMIN_ROLEID:
			loadSLAdminDetails(securityCntxt);
			break;
		default:
			break;
		}

	}


	public String execute() throws Exception 
	{
		logger.info("Inside ChangePasswordAction.execute() method");
		String userName = (String)ActionContext.getContext().getSession().get("username");
		logger.info("userName from ActionContext="+userName);
		setUsername((String) ActionContext.getContext().getSession().get("username"));
		
		//Do the basic password validation before checking if its a Registration or change password/reset password flow.
		if (isInvalid(getPassword())) {
			addFieldError("password", Config.getResouceBundle().getString("changeTempPassword.invalid.error"));
			return INPUT;
		}else if(!passWordCompare(getPassword(), getConfirmPassword())){
			addFieldError("password", Config.getResouceBundle().getString("changeTempPassword.unmatch.error"));	
			return INPUT;
		}
		
		//Check if its change password flow by checking password code in the URL. If it has password code then :
		//1.Get user name from interim_password table (its done in load()) 
		//2.Get password from user_profile based on the user name. 
		//3.Authenticate user.  
		//TODO : Need to find a better check for differentiating between first time login and change password flow.
		if(StringUtils.isNotEmpty(getDeepLinkCode())){
			setTempPassword(deepLinkCode);
			loginVO = iLoginDelegate.getPassword(getUsername());
			
			logger.info("get userName="+getUsername());
			if(StringUtils.isEmpty(getUsername())){
				addFieldError("username",Config.getResouceBundle().getString("changeTempPassword.wrong.error"));
				return INPUT;
			}else{
				setUsername(loginVO.getUsername());
				setTempPassword(loginVO.getPassword());
			}
			//User needs to be authenticated to get the security context, hence login the user. 	
			loadSecurityContext();	
		}else{
			setUsername((String) ActionContext.getContext().getSession().get("username"));
		}
		
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");

		boolean validPassword = isValidPasswordFormat(getUsername(), password);
		password = CryptoUtil.encrypt(password);
		if ( validPassword) {
		
			if(showSecretQuestion)
			{
				if(isInvalid(secretQuestion)){
					addFieldError("secretQuestion", Config.getResouceBundle().getString("changeTempPassword.secretQuestion.required.error"));	
					return INPUT;
				}
				else if(isInvalid(secretAnswer)){
					addFieldError("secretAnswer", Config.getResouceBundle().getString("changeTempPassword.secretAnswer.required.error"));	
					return INPUT;
				}
			}
			
			changePasswordDto.setUserName(getUsername());
			changePasswordDto.setPassword(password);
			changePasswordDto.setSecretQuestion(secretQuestion);
			changePasswordDto.setSecretAnswer(secretAnswer);

			boolean result = changePasswordDelegate
					.updatePassword(changePasswordDto);
			if(!result)
			{
				addFieldError("password",Config.getResouceBundle().getString("changepwd.last4.error"));
				return INPUT;
			}
			
			addActionMessage(Config.getResouceBundle().getString("success.save"));
			
			//Invalidate the pass code in interim table.This pass code is used to retrieve the user id from user 
			//profile table.Once we get it and successfully updated the new password in user profile, invalidate the code in Interim table.
			if(getDeepLinkCode() != null){
				forgetUserName.invalidatePassword(getDeepLinkCode());
			}
			
			if (securityContext.getRoleId() != null && securityContext.getRoleId().intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
				return "successSimple";
			}
			
			updateVerificationCount(getUsername(),0); //Reset the verification counter to 0 for zip code attempts.
			
			return SUCCESS;
		}else {
			
			load();
		 	return INPUT;
		}
		
	}

	//Added by Mayank for validating PASSWORD business rules - 03rdDec2007
	public boolean isValidPasswordFormat(String userName, String password) 
	{//throws Exception{
		if (userName == null || password == null)
			return false;

		boolean numeric = password.matches(".*[0-9].*");
		boolean letter = password.matches(".*[a-z,A-Z].*");
		boolean specialCharacter = password.matches(".*[@#_\\-!%&].*");
		
		if (password.equalsIgnoreCase(userName)) 
		{
			addFieldError("password", Config.getResouceBundle().getString("changeTempPassword.invalid.error"));
			return false;
		}
		
		if ((password.length() >= 8 && password.length() <= 30 && letter)&&(numeric ||
		specialCharacter )){
			
			return true;
		}
		addFieldError("password", Config.getResouceBundle().getString("changeTempPassword.invalid.error"));
		return false;

	}//End of method isValidPasswordFormat
	//END OF CHANGES  by Mayank for validating PASSWORD business rules - 03rdDec2007


	/**
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String doLoad() throws Exception {
		//Check if the change password link is expired, if it is, then redirect user to forgot password page with error. 
		if(isPassworURLExpired){
			addFieldError("forgotUsernameDto.email", 
					Config.getResouceBundle().getString("resetPassword.expire.link.error"));
			return "errorredirect";
		}else{
			getSessionMessages();
			return INPUT;
		}
	}

	/**
	 * @throws Exception
	 * Get the user name from interim password table based on the passcode passed in from email link.
	 */
	private void load() throws Exception {
		id = (String) ActionContext.getContext().getSession().get("vendorId");
		questionMap = changePasswordDelegate.getSecretQuestionList();
		
		setDeepLinkCode((String)getSession().getAttribute(OrderConstants.DEEPLINK_PASSCODE));
		String username = forgetUserName.getUserFromInterimPassword(getDeepLinkCode());
		//Check if the passcode in email link is expired (if the link is unused for certain time period set in DB or tried to use multiple times).
		//If yes, turn away to an error page.
		if(StringUtils.equalsIgnoreCase(username,"Expired")){
			isPassworURLExpired = true;
		}else if(StringUtils.isNotEmpty(username)){
			//Invalidate previous session before changing the password. This will clear off the context, if user is previously logged in.
			removeSecurityContext();
			//Create new session and set user name.
			ServletActionContext.getRequest().getSession(true).setAttribute("username",username);
			setUsername(username);
		}
	}

	private boolean isInvalid(String value) {
		return (value == null || value.length() == 0);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the secretQuestion
	 */
	public String getSecretQuestion() {
		return secretQuestion;
	}

	/**
	 * @param secretQuestion the secretQuestion to set
	 */
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	/**
	 * @return the secretAnswer
	 */
	public String getSecretAnswer() {
		return secretAnswer;
	}

	/**
	 * @param secretAnswer
	 *            the secretAnswer to set
	 */
	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	/**
	 * @return the questionMap
	 */
	public Map getQuestionMap() {
		return questionMap;
	}

	/**
	 * @param questionMap the questionMap to set
	 */
	public void setQuestionMap(Map questionMap) {
		this.questionMap = questionMap;
	}

	/*
	 * Checks that new password entered and confirm password fields are the same.
	 * If new password and confirm new password are different return false and
	 * add error.
	 * Added by MTedder@covansys.com
	 */
	private boolean passWordCompare(String password, String confirmPassword) {
		if(password.equals(confirmPassword)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	private void getSessionMessages() {
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
				.getSession().get("tabDto");
		// getting messages from session
		if (baseTabDto != null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap()); 
			if (!getFieldErrors().isEmpty())
				changePasswordDto = (ChangePasswordDto)baseTabDto.getDtObject();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	private void loadSLAdminDetails(SecurityContext securityCntxt) {
		getRoleActivities(securityCntxt);
		securityCntxt.setSlAdminInd(true);
		securityCntxt.setSlAdminFName(get_commonCriteria().getFName());
		securityCntxt.setSlAdminLName(get_commonCriteria().getLName());
		securityCntxt.setSlAdminUName(getUsername());
		securityCntxt.setVendBuyerResId(iLoginDelegate
				.retrieveResourceIDByUserName(getUsername()));
		securityCntxt
				.setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
		ActionContext.getContext().getSession().put("SecurityContext",
				securityCntxt);

		// Setting User Name in the Session
		ActionContext.getContext().getSession().put("username",
				getUsername());
	}

	private void loadBuyerDetails(SecurityContext securityCntxt) {
		Map<String, Object> sessionAttributes = ActionContext
				.getContext().getSession();
		Map<String, ?> buyer = securityDel.getBuyerId(getUsername());
		Account acct = new Account();
		try {
			acct = getAutoACHEnabledInd();
		} catch (Exception e) {
			logger.error("Error getting AutoACH indicator for "
					+ getUsername(), e);
		}
		if (buyer != null && buyer.get("buyerId") != null) {
			securityCntxt.setCompanyId(((Long) buyer.get("buyerId")).intValue());

		}
		if (buyer != null && buyer.get("buyerResourceId") != null) {
			securityCntxt.setVendBuyerResId(((Long) buyer
					.get("buyerResourceId")).intValue());
			securityCntxt.setAutoACH(acct.isEnabled_ind());
			securityCntxt.setAccountID(acct.getAccount_id());
		}
		if (buyer != null && buyer.get("clientId") != null) {
			Integer clientId = (Integer) buyer.get("clientId");
			securityCntxt.setClientId(clientId);
			logger.info("Client Id = [" + clientId + "]");
		}
		if (buyer != null && buyer.get(OrderConstants.BUYER_ADMIN_CONTACT_ID) != null) {
			Integer buyerAdminContactId = ((Long)buyer.get(OrderConstants.BUYER_ADMIN_CONTACT_ID)).intValue();
			securityCntxt.setBuyerAdminContactId(buyerAdminContactId);
			logger.info("buyerAdminContactId = [" + buyerAdminContactId + "]");
		}
		getRoleActivities(securityCntxt);
		securityCntxt.setRegComplete(true);
		// Get the user's lifetime rating and set it in session to show in
		// common header on each page
		SurveyRatingsVO surveyRatingsVO = securityDel
				.getLifetimeRatings(securityCntxt);
		Integer lifetimeRatings = UIUtils
				.calculateScoreNumber(surveyRatingsVO != null
						&& surveyRatingsVO.getHistoricalRating() != null ? surveyRatingsVO
						.getHistoricalRating()
						: 0);

		getSession().setAttribute(OrderConstants.LIFETIME_RATINGS_STAR_NUMBER,
				lifetimeRatings);

		getSession().setAttribute(OrderConstants.SERVICELIVESESSIONTIMEOUT,
				new Long(System.currentTimeMillis()));

		// Setting User Name in the Session
		ActionContext.getContext().getSession().put("username",
				getUsername());

		sessionAttributes.put("SecurityContext", securityCntxt);
	}

	private void loadProviderDetails(SecurityContext securityCntxt)
			throws Exception {
		Map<String, Object> sessionAttributes = ActionContext
				.getContext().getSession();
		Map<String, Long> vendor = securityDel.getVendorId(getUsername());

		if (vendor != null && vendor.get("vendorId") != null) {
			securityCntxt.setCompanyId(((Long) vendor.get("vendorId")).intValue());

			boolean regComplete = !(dashboardDelegate
					.isIncompleteProvider(securityCntxt.getCompanyId()));
			securityCntxt.setRegComplete(regComplete);
		}

		if (vendor != null && vendor.get("vendorResourceId") != null) {
			securityCntxt.setVendBuyerResId(((Long) vendor
					.get("vendorResourceId")).intValue());

		}

		getRoleActivities(securityCntxt);

		Map providerIndicators = securityDel
				.getProviderIndicators(securityCntxt.getVendBuyerResId());

		if (providerIndicators != null){
			if(providerIndicators.get("primaryIndicator") != null) {
				securityCntxt.setPrimaryInd(((Boolean) providerIndicators
						.get("primaryIndicator")));

				logger.debug("Primary Indicator from Security Context is ******"
						+ securityCntxt.isPrimaryInd());
			}
			if (providerIndicators.get("mktPlaceIndicator") != null) {
				securityCntxt.setMktPlaceInd(((Boolean) providerIndicators
						.get("mktPlaceIndicator")));
				logger
						.debug("Market Place Indicator from Security Context is ******"
								+ securityCntxt.isMktPlaceInd());
			}
			if(providerIndicators.get("providerAdminIndicator") != null) {
				securityCntxt.setProviderAdminInd(((Boolean) providerIndicators
				.get("providerAdminIndicator")));
				logger
				.debug("Provider Admin Indicator from Security Context is ******"
						+ securityCntxt.isProviderAdminInd());
			}
		}
		
		String adminUsername = securityDel.getAdminUserName(securityCntxt
				.getCompanyId());
		if (null != adminUsername) {
			logger.debug("admin user id ++++" + adminUsername);
			securityCntxt.setAdminResId(Integer.parseInt(adminUsername));
		}
		Boolean hasAdminPermissions = securityDel
				.hasResourceAdminPermission(securityCntxt.getVendBuyerResId());
		if (hasAdminPermissions != null) {
			securityCntxt.setHasProviderAdminPermissions(hasAdminPermissions
					.booleanValue());
		}

		ActionContext.getContext().getSession().put("vendorId",
				securityCntxt.getCompanyId() + "");
		ActionContext.getContext().getSession().put("providerName",
				iLoginDelegate.getProviderName(getUsername()));
		ActionContext.getContext().getSession().put("username",
				getUsername());

		sessionAttributes.put("SecurityContext", securityCntxt);
	}
	
	private void getRoleActivities(SecurityContext securityCntxt) {
		List<UserActivityVO> userActivityVO = securityDel
				.getUserActivityRolesList(getUsername());
		Map<String, UserActivityVO> activities = new HashMap<String, UserActivityVO>();

		if (userActivityVO != null) {
			for (int i = 0; i < userActivityVO.size(); ++i) {

				UserActivityVO theUserActivityVO = userActivityVO.get(i);
				activities.put(theUserActivityVO.getActivityId() + "",
						theUserActivityVO);
			}

		}
		securityCntxt.setRoleActivityIdList(activities);
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	private int getVerificationCount(String userName) {
		int count = 0;
		try {
		  count = forgetUserName.getVerificationCount(userName);
		}catch(DelegateException de){
			logger
			.info("SQL Exception @ChangePasswordAction.updateSecurityQuestion() while updating the sec question for user name"
					+ de.getMessage());
		}
		return count;
	}
	
	private void updateVerificationCount(String userName, int count) {
		try{
			forgetUserName.updateVerificationCount(userName,count);
		}catch(DelegateException de){
			logger
			.info("SQL Exception @ChangePasswordAction.updateSecurityQuestion() while updating the sec question for user name"
					+ de.getMessage());
		}
			
	}
	
	private boolean isSecurityQuestionExists(String userName) {
		ForgotUsernameDto forgotusernameDto = new ForgotUsernameDto();
		forgotusernameDto.setUserName(userName);
		boolean result = false;
		
		try {
			forgotusernameDto = forgetUserName.getSecQuestionForUserName(forgotusernameDto);
			if (forgotusernameDto.getQuestionId() == null)
				result = false;
			else
				result = true;
		} catch (DelegateException de) {
			logger
			.info("SQL Exception @ChangePasswordAction.getSecurityQuestion() while reading the sec question for user name"
					+ de.getMessage());
		}// end catch 
		return result;
	}
	

	public boolean isShowSecretQuestion() {
		return showSecretQuestion;
	}

	public void setShowSecretQuestion(boolean showSecretQuestion) {
		this.showSecretQuestion = showSecretQuestion;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 * Invalidate an existing session.
	 */
	private String removeSecurityContext() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession(
				false);
		if (session != null) {
			SecurityContext securityCntxt = (SecurityContext) session
					.getAttribute(Constants.SESSION.SECURITY_CONTEXT);
			if (securityCntxt != null) {
				securityCntxt.setLoginAuditId(0);
				securityCntxt.setLoginAuditId(0);
				session.removeAttribute(Constants.SESSION.SECURITY_CONTEXT);
			}
		}
		return SUCCESS;
	}

	public String getDeepLinkCode() {
		return deepLinkCode;
	}

	public void setDeepLinkCode(String deepLinkCode) {
		this.deepLinkCode = deepLinkCode;
	}





}