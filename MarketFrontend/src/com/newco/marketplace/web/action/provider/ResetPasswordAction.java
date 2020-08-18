package com.newco.marketplace.web.action.provider;

import java.util.Map;

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

/**
 * 
 * @author Shekhar Nirkhe
 * 
 */
public class ResetPasswordAction extends ActionSupport implements SessionAware,
		ServletRequestAware, Preparable {
	private IForgetUsernameDelegate iForgetUsernameDelegate;
	private ForgotUsernameDto forgotUsernameDto;
	private IManageUsersDelegate manageUsersDelegate;
	private Map sSessionMap;
	private HttpServletRequest request;
	private String userZipCode;
	private String userPhoneNumber;
	private String userCompanyName;
	private boolean commercialUser;

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(ResetPasswordAction.class.getName());
	private static final int maxSecretQuestionAttempts = Integer
			.parseInt(PropertiesUtils
					.getPropertyValue(Constants.AppPropConstants.MAX_SECRET_QUESTION_ATTEMPTS_LIMIT));
	private final String LOCKED_ACCOUNT = "showAccountLockedPage";
	private final String HINT_PAGE = "showResetPwdHintPage";

	public ResetPasswordAction(IForgetUsernameDelegate iForgetUsernameDelegate,
			ForgotUsernameDto forgotUsernameDto,
			IManageUsersDelegate manageUsersDelegate) {
		this.iForgetUsernameDelegate = iForgetUsernameDelegate;
		this.forgotUsernameDto = forgotUsernameDto;
		this.manageUsersDelegate = manageUsersDelegate;
	}

	@SkipValidation
	public String loadResetPasswordPage() throws Exception {
		forgotUsernameDto.setEmail("");
		forgotUsernameDto.setUserName("");
		return "loadResetPasswordPage";
	}

	public String validateEmail() throws Exception {
		try {
			String email = forgotUsernameDto.getEmail();
			String username = forgotUsernameDto.getUserName();
			if (!ForgetUsernameAction.isEmailAddressValid(email)) {
				addFieldError(
						"forgotUsernameDto.email",
						Config.getResouceBundle().getString(
								"resetPassword.email.required.error"));
				return INPUT;
			}

			if (StringUtils.isEmpty(username)) {
				addFieldError(
						"forgotUsernameDto.userName",
						Config.getResouceBundle().getString(
								"resetPassword.username.required.error"));
				return INPUT;
			}

			forgotUsernameDto = iForgetUsernameDelegate
					.loadLiteLostUsereProfile(forgotUsernameDto);
			if (forgotUsernameDto == null) {
				addFieldError(
						"forgotUsernameDto.email",
						Config.getResouceBundle().getString(
								"resetPassword.emailLogin.error"));
				return INPUT;
			}

			logger.info("generated pwd:" + forgotUsernameDto.getPwdInd() + "--"
					+ email);
			if (forgotUsernameDto.getPwdInd() == 0
					&& (forgotUsernameDto.getQuestionId() == null || forgotUsernameDto
							.getQuestionId().equals("0")))
				forgotUsernameDto.setPwdInd(1);

			if (forgotUsernameDto.getPwdInd() == -1) {
				forgotUsernameDto.setEmail("");
				forgotUsernameDto.setUserName("");
				return emailNotFound();
			} else {
				if (forgotUsernameDto.isProfileLocked()) {
					return LOCKED_ACCOUNT;
				}

				if (forgotUsernameDto.getPwdInd() == 1) {
					if (email != null) {
						forgotUsernameDto.setEmail(email);
					}
					logger.info("inside else" + forgotUsernameDto.getEmail());
					forgotUsernameDto = iForgetUsernameDelegate
							.validateAns(forgotUsernameDto);

					if (forgotUsernameDto.isSuccess()) {
						String userName=forgotUsernameDto.getUserName();
						if (StringUtils.isNotBlank(userName)) {
							String resourceId = iForgetUsernameDelegate
									.getResourceIdFromUsername(userName);
							if (StringUtils.isNotBlank(resourceId)) {
								manageUsersDelegate.expireMobileTokenforFrontEnd(
										Integer.parseInt(resourceId),
										MPConstants.ACTIVE);
							}
						}
						return "showCongrts";
					} else {
						addActionError("Exception Occurred at ResetPasswordAction.validateEmail ");
						return ERROR;
					}
				} else
					return getSecQuestionForUserName();

			}
		} catch (DelegateException dle) {
			logger.info("Exception Occurred at ResetPasswordAction.validateEmail "
					+ dle.getMessage());
			addActionError("Exception Occurred at ResetPasswordAction.validateEmail "
					+ dle.getMessage());
			return ERROR;
		}
	}

	private String emailNotFound() {
		Integer count = (Integer) ActionContext.getContext().getSession()
				.get("rWrongEmailCount");
		if (count == null)
			count = new Integer(0);
		if (count.intValue() > 1) {
			ActionContext.getContext().getSession().remove("rWrongEmailCount");
			return "noUserForEmail";
		} else {
			ActionContext.getContext().getSession()
					.put("rWrongEmailCount", ++count);
			addActionError(Config.getResouceBundle().getString(
					"resetPassword.emailLogin.error"));// display error message
														// on page
			return INPUT;
		}
	}

	@SkipValidation
	public String getSecQuestionForUserName() throws Exception {
		try {
			forgotUsernameDto = iForgetUsernameDelegate
					.getSecQuestionForUserName(forgotUsernameDto);
			if (forgotUsernameDto.getUserName() != null)
				ActionContext.getContext().getSession()
						.put("userName", forgotUsernameDto.getUserName());

			ActionContext.getContext().getSession()
					.put("UserEmail", forgotUsernameDto.getEmail());
			ActionContext.getContext().getSession()
					.put("RoleId", forgotUsernameDto.getRoleId());

			return HINT_PAGE;
		} catch (DelegateException dle) {
			logger.info("Exception Occurred at ResetPasswordAction.getSecQuestionForUserName() "
					+ dle.getMessage());
			addActionError("Exception Occurred at ResetPasswordAction.getSecQuestionForUserName() "
					+ dle.getMessage());
			return ERROR;
		}

	}

	@SkipValidation
	public String validateAns() throws Exception {
		try {
			boolean flag = StringUtils.isBlank(forgotUsernameDto
					.getQuestionTxtAnswer());
			if (flag) {
				addActionError(Config.getResouceBundle().getString(
						"resetPassword.secretquestion.required.error"));
				return getSecQuestionForUserName();
			}

			if (forgotUsernameDto.getQuestionTxtAnswer().equals(
					"[Security Question Answer]")) {
				addActionError(Config.getResouceBundle().getString(
						"resetPassword.secretquestion.required.error"));
				return getSecQuestionForUserName();
			}

			forgotUsernameDto = iForgetUsernameDelegate
					.validateAns(forgotUsernameDto);
			String userName = forgotUsernameDto.getUserName();
			if (userName == null) {
				userName = (String) ActionContext.getContext().getSession()
						.get("userName");
			}

			int count = iForgetUsernameDelegate.getVerificationCount(userName);
			boolean success = false;
			if (forgotUsernameDto.isSuccess()) {
				success = true;
				count = 0;
			} else {
				count++;
			}

			iForgetUsernameDelegate.updateVerificationCount(userName, count);
			if (success) {
				if (StringUtils.isNotBlank(userName)) {
					String resourceId = iForgetUsernameDelegate
							.getResourceIdFromUsername(userName);
					if (StringUtils.isNotBlank(resourceId)) {
						manageUsersDelegate.expireMobileTokenforFrontEnd(
								Integer.parseInt(resourceId),
								MPConstants.ACTIVE);
					}
				}
				return "showCongrts";
			}
			logger.info("Now Count is :" + count);

			if (count >= maxSecretQuestionAttempts || count == -1) {
				logger.info("Secret Question attempt limit exceeded for user:"
						+ userName);
				// Now findout the role ID
				forgotUsernameDto = iForgetUsernameDelegate
						.loadLostUsername(forgotUsernameDto);

				int roleId = forgotUsernameDto.getRoleId();
				if (roleId == OrderConstants.PROVIDER_ROLEID
						|| roleId == OrderConstants.BUYER_ROLEID) {
					commercialUser = true;
					ActionContext.getContext().getSession()
							.put("commercialUser", true);
				}
				return "showVerificationZipPage";
			}

			addActionError(Config.getResouceBundle().getString(
					"resetPassword.secretquestion.error"));
			return HINT_PAGE;
		} catch (DelegateException dle) {
			logger.info("Exception Occurred at ResetPasswordAction.validateAns() "
					+ dle.getMessage());
			addActionError("Exception Occurred at ResetPasswordAction.validateAns() "
					+ dle.getMessage());
			return ERROR;
		}
	}

	/**
	 * doValidatePhoneAndZip function is never called. Instead
	 * ForgotUsernameAction function is used.
	 * 
	 * @return
	 * @throws Exception
	 */
	// public String doValidatePhoneAndZip() throws Exception {
	// }

	@SkipValidation
	public String doCancel() throws Exception {
		return "cancel";
	}

	@SkipValidation
	public String doCancelAnswer() throws Exception {
		return "cancelAnswer";
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

	public boolean isCommercialUser() {
		return commercialUser;
	}

	public void setCommercialUser(boolean commercialUser) {
		this.commercialUser = commercialUser;
	}
}