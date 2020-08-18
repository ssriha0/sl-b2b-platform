package com.newco.marketplace.web.action.provider;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.delegates.provider.IForgetUsernameDelegate;
import com.newco.marketplace.web.dto.provider.ForgotUsernameDto;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * 
 * @author Shekhar Nirkhe
 * 
 */

@Validation
public class ForgetUsernameAction extends ActionSupport implements
		SessionAware, ServletRequestAware, Preparable {
	private IForgetUsernameDelegate iForgetUsernameDelegate;
	private ForgotUsernameDto forgotUsernameDto;
	private IManageUsersDelegate manageUsersDelegate;
	private Map sSessionMap;
	private HttpServletRequest request;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(ForgetUsernameAction.class.getName());
	private String userSecretQuestionAnswer = "";// MTedder
	private String userZipCode;
	private String userPhoneNumber;
	private String userCompanyName;
	private boolean commercialUser;
	private String resourceId;
	private String selectedUserId;
	private final String LOCKED_ACCOUNT = "showAccountLockedPage";

	private static final int maxSecretQuestionAttempts = Integer
			.parseInt(PropertiesUtils
					.getPropertyValue(Constants.AppPropConstants.MAX_SECRET_QUESTION_ATTEMPTS_LIMIT));

	public ForgetUsernameAction(
			IForgetUsernameDelegate iForgetUsernameDelegate,
			ForgotUsernameDto forgotUsernameDto,
			IManageUsersDelegate manageUsersDelegate) {
		this.iForgetUsernameDelegate = iForgetUsernameDelegate;
		this.forgotUsernameDto = forgotUsernameDto;
		this.manageUsersDelegate = manageUsersDelegate;
	}

	@SkipValidation
	public String loadForgetUserPage() throws Exception {
		return "loadUserPage";
	}

	@SkipValidation
	public String sendEmail() throws Exception {
		logger.info("-----Send Email---:" + resourceId);
		if (resourceId.equals("-1")) {
			addActionError(Config.getResouceBundle().getString(
					"fogotUsername.selectUserName"));// display error message on
														// page
			forgotUsernameDto.setEmail((String) (ActionContext.getContext()
					.getSession().get("UserEmail")));
			return validateEmail();
		}
		// forgotUsernameDto.setResourceId(getResourceId());
		// forgotUsernameDto.setEmail((String)ActionContext.getContext().getSession().get("UserEmail"));
		// forgotUsernameDto.setRoleId((Integer)ActionContext.getContext().getSession().get("RoleId"));
		// forgotUsernameDto=iForgetUsernameDelegate.loadLostUsername(forgotUsernameDto);

		// Dont know the user name, So load user from resourceId
		forgotUsernameDto = loadForgotUsernameDto(false);

		if (forgotUsernameDto == null)
			return ERROR;

		if (forgotUsernameDto.isProfileLocked()) {
			return LOCKED_ACCOUNT;
		}

		ActionContext.getContext().getSession().put("forgotUserAction", "true");

		boolean statusEmail;
		try {
			// if (forgotUsernameDto.getPwdInd()==1) {
			// return dispatchEmail(forgotUsernameDto);
			// }

			if (forgotUsernameDto.getQuestionId() == null
					|| forgotUsernameDto.getQuestionId().equals("0")) {
				return showVerificationZipPage();
			} else {
				return showHintPage();
			}

		} catch (Exception dle) {
			logger.info("Exception Occurred at ForgetUsernameAction.sendEmail "
					+ dle.getMessage());
			addActionError("Exception Occurred at ForgetUsernameAction.sendEmail "
					+ dle.getMessage());
			return ERROR;
		}
	}

	private String showHintPage() throws Exception {
		int count = iForgetUsernameDelegate
				.getVerificationCount(forgotUsernameDto.getUserName());
		logger.info("Current Count is :" + count);

		if (count >= (maxSecretQuestionAttempts) || count == -1) {
			logger.info("Secret Question attempt limit exceeded for user:"
					+ forgotUsernameDto.getUserName());
			return showVerificationZipPage();
		}
		return "showHintPage";
	}

	private String dispatchEmail(ForgotUsernameDto forgotUsernameDto) {
		boolean statusEmail = false;
		String success_page = "successPage";

		String forgotUserAction = (String) ActionContext.getContext()
				.getSession().get("forgotUserAction");

		if (forgotUserAction != null && forgotUserAction.equals("true")) {
			statusEmail = iForgetUsernameDelegate
					.sendForgotUsernameMail(forgotUsernameDto);
		} else { // resetpassword action
			statusEmail = iForgetUsernameDelegate.resetPassword(
					forgotUsernameDto.getUserName(), null, null, null);
			logger.info("Password has been reset");
			success_page = "successResetPassword";
		}

		ActionContext.getContext().getSession().put("forgotUserAction", null);

		if (statusEmail) {// email successfully sent
			String resourceId = null;
			String userName = forgotUsernameDto.getUserName();
			if (StringUtils.isNotBlank(userName)) {

				try {
					resourceId = iForgetUsernameDelegate
							.getResourceIdFromUsername(userName);
					if (StringUtils.isNotBlank(resourceId)) {
						manageUsersDelegate.expireMobileTokenforFrontEnd(
								Integer.parseInt(resourceId),
								MPConstants.ACTIVE);
					}
				} catch (DelegateException e) {
					logger.info("Exception Occurred at ForgetUsernameAction.dispatchEmail() "
							+ e.getMessage());
					addActionError(Config.getResouceBundle().getString(
							"fogotUsername.emailSend.error"));// display error
																// message on
																// page

					return "loadUserPage";
				}

			}
			return success_page;
		} else
			addActionError(Config.getResouceBundle().getString(
					"fogotUsername.emailSend.error"));// display error message
														// on page

		return "loadUserPage";
	}

	public String validateEmail() throws Exception {
		try {
			// forgotUsernameDto.setRoleId(roleId);
			if (!isEmailAddressValid(forgotUsernameDto.getEmail())) {
				addActionError(Config.getResouceBundle().getString(
						"fogotUsername.invalidemailaddress.error"));// display
																	// error
																	// message
																	// on page
				return "loadUserPage";
			}

			forgotUsernameDto = iForgetUsernameDelegate
					.loadLostUsernameList(forgotUsernameDto);
			ActionContext.getContext().getSession()
					.put("UserEmail", forgotUsernameDto.getEmail());
			ActionContext.getContext().getSession()
					.put("RoleId", forgotUsernameDto.getRoleId());

			if (forgotUsernameDto.getListUsers() == null) {
				return emailNotFound();
			} else if (forgotUsernameDto.getListUsers().size() == 0) {
				return emailNotFound();
			} else if (forgotUsernameDto.getListUsers() == null
					|| forgotUsernameDto.getListUsers().size() > 1) {
				return "moreUsersForEmail";
			} else {
				// check if user is locked
				if (forgotUsernameDto.isProfileLocked()) {
					return LOCKED_ACCOUNT;
				}

				// if (forgotUsernameDto.getPwdInd()==1) {
				// boolean statusEmail =
				// iForgetUsernameDelegate.sendForgotUsernameMail(forgotUsernameDto);
				// if (statusEmail){//email successfully sent
				// return "successPage";
				// }
				// else
				// addActionError(Config.getResouceBundle().getString("fogotUsername.emailSend.error"));//display
				// error message on page
				// return "loadUserPage";
				// }

				ActionContext.getContext().getSession()
						.put("userName", forgotUsernameDto.getUserName());
				ActionContext.getContext().getSession()
						.put("forgotUserAction", "true");

				if (forgotUsernameDto.getQuestionId() == null
						|| forgotUsernameDto.getQuestionId().equals("0")) {
					return showVerificationZipPage();
				} else {
					return showHintPage();
				}
			}
		} catch (DelegateException dle) {
			logger.info("Exception Occurred at ForgetUsernameAction.validateEmail "
					+ dle.getMessage());
			addActionError("Exception Occurred at ForgetUsernameAction.validateEmail "
					+ dle.getMessage());
			return ERROR;
		}

	}

	private String emailNotFound() {
		Integer count = (Integer) ActionContext.getContext().getSession()
				.get("wrongEmailCount");
		if (count == null)
			count = new Integer(0);
		if (count.intValue() > 1) {
			ActionContext.getContext().getSession().remove("wrongEmailCount");
			return "noUserForEmail";
		} else {
			ActionContext.getContext().getSession()
					.put("wrongEmailCount", ++count);
			addActionError(Config.getResouceBundle().getString(
					"fogotUsername.emailaddress.notfound.error"));// display
																	// error
																	// message
																	// on page
			return "loadUserPage";
		}
	}

	@SkipValidation
	public String doCancel() throws Exception {
		return "cancel";
	}

	/*
	 * MTedder@covansys.com This method: 1. Uses the valid given email and
	 * userID to retrieve the encrypted answer_txt from the "user_profile_table"
	 * 2. Sets the DTO with the secret question to be displayed on the
	 * login_forgot_username2.jsp
	 */
	@SkipValidation
	public String doLoadSecretQuestion() throws Exception {
		logger.info("---------------loadSecretQuestion--------------");
		// forgotUsernameDto =
		// iForgetUsernameDelegate.loadLostUsername(forgotUsernameDto);
		// System.out.println("forgotUsernameDto.getUserName(): " +
		// forgotUsernameDto.getListUsers().get(0).getUserName());

		// load showHintPage - login_forgot_username2.jsp
		return "showHintPage";
	}

	/**
	 * This function is called for both forgotUser and resetPassword actions.
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String doValidateSecretQuestion() throws Exception {
		forgotUsernameDto = loadForgotUsernameDto(true);
		if (forgotUsernameDto == null)
			return ERROR;

		String userName = forgotUsernameDto.getUserName();

		if (StringUtils.isBlank(userSecretQuestionAnswer) == false) {
			if (forgotUsernameDto.getQuestionTxtAnswer() == null) {

			}
			if (forgotUsernameDto.getQuestionTxtAnswer() != null
					&& forgotUsernameDto.getQuestionTxtAnswer()
							.equalsIgnoreCase(userSecretQuestionAnswer.trim())) {
				if (iForgetUsernameDelegate
						.sendForgotUsernameMail(forgotUsernameDto))// email
																	// successfully
																	// sent
				{
					iForgetUsernameDelegate.updateVerificationCount(
							forgotUsernameDto.getUserName(), 0);
					
					String resourceId = null;
					if (StringUtils.isNotBlank(userName)) {

						try {
							resourceId = iForgetUsernameDelegate
									.getResourceIdFromUsername(userName);
							if (StringUtils.isNotBlank(resourceId)) {
								manageUsersDelegate.expireMobileTokenforFrontEnd(
										Integer.parseInt(resourceId),
										MPConstants.ACTIVE);
							}
						} catch (DelegateException e) {
							logger.info("Exception Occurred at ForgetUsernameAction.doValidateSecretQuestion() "
									+ e.getMessage());
							addActionError(Config.getResouceBundle().getString(
									"fogotUsername.emailSend.error"));
							return "showHintPage";
						}
					}	
					return "successPage";
				}
				addActionError(Config.getResouceBundle().getString(
						"fogotUsername.emailSend.error"));// display error
															// message on page
				return "showHintPage";// email send failure
			}
		} else {
			addActionError(Config.getResouceBundle().getString(
					"fogotUsername.emptySecretQuestionAnswer"));// display error
																// message on
																// page
			return "showHintPage";
		}

		// invalid secret question answer
		// update retry count

		int count = iForgetUsernameDelegate
				.getVerificationCount(forgotUsernameDto.getUserName());
		iForgetUsernameDelegate.updateVerificationCount(
				forgotUsernameDto.getUserName(), ++count);
		logger.info("Now Count is :" + count);

		if (count >= (maxSecretQuestionAttempts) || count == -1) {
			logger.info("Secret Question attempt limit exceeded for user:"
					+ forgotUsernameDto.getUserName());
			return showVerificationZipPage();
		}

		addActionError(Config.getResouceBundle().getString(
				"fogotUsername.invalidSecretQuestionAnswer"));// display error
																// message on
																// page
		return "showHintPage";
	}

	/**
	 * This function takes care of Forget User as well as Reset Password
	 * scenarios.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String doValidatePhoneAndZip() throws Exception {
		forgotUsernameDto = loadForgotUsernameDto(true);
		if (forgotUsernameDto == null)
			return ERROR;

		String userName = forgotUsernameDto.getUserName();

		if (userPhoneNumber.equals(""))
			userPhoneNumber = null;

		if (userZipCode.equals(""))
			userZipCode = null;

		if (forgotUsernameDto == null) { // Display Internal server error
			logger.info("forgotUsernameDto is null");
			return ERROR;
		} else if (userPhoneNumber == null) {
			logger.info("phone is null");
			addFieldError("userPhoneNumber", Config.getResouceBundle()
					.getString("verification.phone.required.error"));
			return showVerificationZipPage();
		} else if (userZipCode == null) {
			logger.info("zip is null");
			addFieldError(
					"userZipCode",
					Config.getResouceBundle().getString(
							"verification.zip.required.error"));
			return showVerificationZipPage();
		}

		int count = iForgetUsernameDelegate.getVerificationCount(userName);
		iForgetUsernameDelegate.updateVerificationCount(userName, ++count);

		boolean flag = iForgetUsernameDelegate.doValidatePhoneAndZip(
				forgotUsernameDto, userPhoneNumber, userZipCode,
				userCompanyName);

		if (flag) {
			logger.info("Zip Validation successful");
			return dispatchEmail(forgotUsernameDto);
		} else {
			commercialUser = false;
			if (count < (maxSecretQuestionAttempts * 2) && count != -1) {
				addFieldError("verificationError", Config.getResouceBundle()
						.getString("verification.unmatch.error"));
				return showVerificationZipPage();
			} else {
				iForgetUsernameDelegate.lockProfile(userName);
				return LOCKED_ACCOUNT;
				// addFieldError("verificationError",
				// Config.getResouceBundle().getString("verification.lock.error"));
				// return showVerificationZipPage();
			}
		}
	}

	public void setSession(Map ssessionMap) {
		// TODO Auto-generated method stub
		this.sSessionMap = ssessionMap;
	}

	public Map getSession() {
		// TODO Auto-generated method stub
		return this.sSessionMap;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request = arg0;
	}

	public void prepare() throws Exception {

	}

	public IForgetUsernameDelegate getIForgetUsernameDelegate() {
		return iForgetUsernameDelegate;
	}

	public void setIForgetUsernameDelegate(
			IForgetUsernameDelegate forgetUsernameDelegate) {
		iForgetUsernameDelegate = forgetUsernameDelegate;
	}

	public ForgotUsernameDto getForgotUsernameDto() {
		return forgotUsernameDto;
	}

	public void setForgotUsernameDto(ForgotUsernameDto forgotUsernameDto) {
		this.forgotUsernameDto = forgotUsernameDto;
	}

	/**
	 * MTedder
	 * 
	 * @return the userSecretQuestionAnswer
	 */
	public String getUserSecretQuestionAnswer() {
		return userSecretQuestionAnswer;
	}

	/**
	 * MTedder
	 * 
	 * @param userSecretQuestionAnswer
	 *            the userSecretQuestionAnswer to set
	 */
	public void setUserSecretQuestionAnswer(String userSecretQuestionAnswer) {
		this.userSecretQuestionAnswer = userSecretQuestionAnswer;
	}

	public String getUserZipCode() {
		return userZipCode;
	}

	public void setUserZipCode(String userZipCode) {
		this.userZipCode = userZipCode;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserCompanyName() {
		return userCompanyName;
	}

	public void setUserCompanyName(String userCompanyName) {
		this.userCompanyName = userCompanyName;
	}

	public boolean isCommercialUser() {
		return commercialUser;
	}

	public void setCommercialUser(boolean commercialUser) {
		this.commercialUser = commercialUser;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
		ActionContext.getContext().getSession().put("resId", resourceId);
	}

	private String showVerificationZipPage() {
		if (forgotUsernameDto == null)
			return ERROR;

		Boolean commerical = (Boolean) ActionContext.getContext().getSession()
				.get("commercialUser");

		if (commerical != null && commerical.booleanValue() == true) {
			commercialUser = true;
		} else {
			int roleId = forgotUsernameDto.getRoleId();
			if (roleId == OrderConstants.PROVIDER_ROLEID
					|| roleId == OrderConstants.BUYER_ROLEID) {
				commercialUser = true;
				ActionContext.getContext().getSession()
						.put("commercialUser", true);
			}
		}

		logger.info("Commercial User:" + commercialUser);
		return "showVerificationZipPage";
	}

	public static boolean isEmailAddressValid(String emailAddress) {
		if (emailAddress != null) {
			Pattern email = Pattern.compile("^\\S+@\\S+$");
			Matcher fit = email.matcher(emailAddress);
			if (fit.matches())
				return true;
		}
		return false;
	}

	private ForgotUsernameDto loadForgotUsernameDto(boolean loadUserName)
			throws Exception {
		String userName = null;
		String resId = (String) ActionContext.getContext().getSession()
				.get("resId");
		if (forgotUsernameDto == null)
			return null;

		forgotUsernameDto.setRoleId((Integer) ActionContext.getContext()
				.getSession().get("RoleId"));
		forgotUsernameDto.setEmail((String) ActionContext.getContext()
				.getSession().get("UserEmail"));
		forgotUsernameDto.setResourceId(resId);
		if (loadUserName) {
			userName = (String) ActionContext.getContext().getSession()
					.get("userName");
			forgotUsernameDto.setUserName(userName);
		}
		forgotUsernameDto = iForgetUsernameDelegate
				.loadLostUsername(forgotUsernameDto);

		if (userName == null && forgotUsernameDto.getUserName() != null)
			ActionContext.getContext().getSession()
					.put("userName", forgotUsernameDto.getUserName());

		if (resId == null && forgotUsernameDto.getResourceId() != null)
			ActionContext.getContext().getSession()
					.put("resId", forgotUsernameDto.getResourceId());

		return forgotUsernameDto;
	}

	public String getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserId(String selectedUserId) {
		this.selectedUserId = selectedUserId;
		if (selectedUserId != null) {
			String arr[] = selectedUserId.split(":");
			if (arr != null) {
				setResourceId(arr[0]);
				if (arr.length > 1) {
					Integer roleId = new Integer(arr[1]);
					ActionContext.getContext().getSession()
							.put("RoleId", roleId);
				}
			}
		}
	}

}