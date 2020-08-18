package com.newco.marketplace.web.action.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;



import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.RequestInfo;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.SharedSecret;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.audit.LoginAuditVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ISODashBoardDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISecurityDelegate;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.ILoginDelegate;
import com.newco.marketplace.web.delegatesImpl.SecurityDelegateImpl;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.provider.LoginDto;
import com.newco.marketplace.web.utils.Config;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.newco.ofac.utils.OFACConstants;
import com.newco.ofac.vo.BuyerOfacVO;
import com.newco.ofac.vo.ProviderOfacVO;
import com.newco.ofac.webservice.OFACSDNClient;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.common.properties.IApplicationProperties;

public class LoginAction extends SLBaseAction implements Preparable, SessionAware, ModelDriven<LoginDto> {

	private static final long serialVersionUID = 4697350016584091820L;

	private static final Logger logger = Logger.getLogger(LoginAction.class);

	private String providerUrl, providerUrlSample;
	private String commercialUrl = "http://www.servicelive.com/";
	private String commercialUrlSample;
	private String professionalUrl;
	private String b2cUrl;
	private String url;

	private Integer timeout = Integer.valueOf(0);

	private ISecurityDelegate securityDel = null;
	private ILoginDelegate iLoginDelegate;
	private ISODashBoardDelegate dashboardDelegate;
	private IAuditLogDelegate auditLogDelegates;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private String email = "email";
	private String percentComplete = "";
	private LoginCredentialVO lcvo = null;
	private String destination = null;
	private String errDestination = null;
	private int buyerID;
	private int providerID;
	private boolean vendorAdmin = false;
	private boolean buyerAdmin = false;
	private String resourceLastName;
	private String resourceFirstName;
	private ProviderOfacVO providerOfacVO = new ProviderOfacVO();
	private BuyerOfacVO buyerOfacVO = new BuyerOfacVO();
	private LoginDto loginDto = new LoginDto();
	private Cryptography cryptography;
	private ISOWizardFetchDelegate fetchDelegate;
	
	//SL-15642 inject application properties
	private IApplicationProperties applicationProperties;
	
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public LoginDto getLoginDto() {
		return loginDto;
	}

	public void setLoginDto(LoginDto loginDto) {
		this.loginDto = loginDto;
	}

	/**
	 * 
	 */
	public LoginAction() {
		super();
	}

	/**
	 * 
	 * @param securityDel
	 * @param loginDelegate
	 * @param dashboardDelegate
	 */
	public LoginAction(ISecurityDelegate securityDel, ILoginDelegate loginDelegate, ISODashBoardDelegate dashboardDelegate) {
		this.securityDel = securityDel;
		this.iLoginDelegate = loginDelegate;
		this.dashboardDelegate = dashboardDelegate;
	}

	public void load() {
		// intentionally blank
	}

	public String doLoad() {
		return SUCCESS;
	}

	/* 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		doLoad();
	}

	private void setSecurityToken(HttpSession session) {
		
		try {
			MessageDigest md = MessageDigest.getInstance(SOConstants.SECURITY_TOKEN_DIGESTER);
			md.update(new Date().toString().getBytes());
			String securityToken = bytesToHexString(md.digest());
			session.setAttribute(SOConstants.SECURITY_TOKEN_SESSION_KEY, securityToken);
			
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}
	}
	
	private String bytesToHexString(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}
	
	private void loadSecurityContext() throws Exception {
		
		//SL-19704
		if(StringUtils.isNotBlank(loginDto.getUsername())){
			loginDto.setUsername(loginDto.getUsername().trim());
		}
		if (securityDel.authenticate(loginDto.getUsername(), loginDto.getPassword().trim())) {
			
			AccountProfile accountProfile = securityDel.getAccountProfile(loginDto.getUsername());
			_commonCriteria = new ServiceOrdersCriteria(accountProfile);
			getSession().setAttribute(OrderConstants.SERVICE_ORDER_CRITERIA_KEY, _commonCriteria);
			resourceLastName = accountProfile.getLastName();
			resourceFirstName = accountProfile.getFirstName();
		}
		
		LoginCredentialVO lvo = new LoginCredentialVO();
		lvo.setUsername(loginDto.getUsername());
		lvo.setPassword(loginDto.getPassword().trim());

		SecurityContext securityCntxt = new SecurityContext(lvo.getUsername());
		lcvo = securityDel.getUserRoles(lvo);

		securityCntxt.setRoles(lcvo);
		securityCntxt.setCompanyId(Integer.valueOf(99)); // why do we set this to 99?
		securityCntxt.setRoleId(lcvo.getRoleId());
		securityCntxt.setRole(lcvo.roleName);
		
		switch (lcvo.getRoleId().intValue()) {
		case OrderConstants.BUYER_ROLEID:
			loadBuyerDetails(securityCntxt);
			break;
		case OrderConstants.SIMPLE_BUYER_ROLEID:
			//loadBuyerDetails(securityCntxt);
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

		insertLoginAudit(securityCntxt);

	}

	// @EmailValidator(message = "Annotated Validation:This is not a valid
	// email!", fieldName="email", key = "i18n.key", shortCircuit = true)
	// @DoubleRangeFieldValidator(message = "Annotated Validation: range is from
	// 0.0 to 100.0", fieldName="percentComplete", key = "i18n.key",
	// shortCircuit = true, minInclusive = "0.0", maxInclusive = "100.00")
	@Override
	public String execute() throws Exception {
		
		// Initialize it to error condition.
		int userLoginStatus = -1;
		String toReturn = null;

		//If the request is coming directly from Email(forgot user name),send user to login page and remove session attribute. 
		if (StringUtils.isNotEmpty((String) getSession().getAttribute(OrderConstants.DEEPLINK_USERNAME))) {//|| ServletActionContext.getRequest().getAttribute("newRequest") ){
			
			getSession().invalidate();
			return INPUT;
		}

		String sharedSecretString = getRequest().getParameter("sharedSecret");
		if (sharedSecretString != null) {
			
			sharedSecretString = sharedSecretString.replaceAll(" ", "+");
			SharedSecret sharedSecret = (SharedSecret) CryptoUtil.decryptObject(sharedSecretString);

			int requestTimeOutSeconds = 6000;
			String ipAddress = getRequest().getRemoteAddr();

			RequestInfo requestInfo = new RequestInfo();
			requestInfo.setIpAddress(ipAddress);
			requestInfo.setRequestTimeOutSeconds(Integer.valueOf(requestTimeOutSeconds));

			boolean isValid = sharedSecret.validate(requestInfo);
			
			if(!isValid) {
				
				addFieldError("username", Config.getResouceBundle().getString("loginForm.login.failure.error"));
				return INPUT;
			}

			loginDto.setUsername(sharedSecret.getUserName());
			loginDto.setPassword(sharedSecret.getEncryptedPassword());
		}

		if (StringUtils.isEmpty(loginDto.getUsername())) {
			
			addFieldError("username", Config.getResouceBundle().getString("login.username.required.error"));
			return INPUT;
		}
		loginDto.setUsername(loginDto.getUsername().trim());
		getSession().setAttribute("legalHoldEnabledAdmin", loginDto.getUsername());
		

		if (StringUtils.isEmpty(loginDto.getPassword())) {
			
			addFieldError("password", Config.getResouceBundle().getString("login.password.required.error"));
			return INPUT;
		}

		if(sharedSecretString == null) {
			
			boolean temp = iLoginDelegate.getTempPasswordIndicator(loginDto.getUsername());
			if (temp) {
				loginDto.setPassword(CryptoUtil.generateHash(loginDto.getPassword().trim()));
			} else {
				loginDto.setPassword(CryptoUtil.generateHash(loginDto.getPassword()));
			}
		} else {
			
			// loginDto.getPassword is already set to the encrypted password in case of sharedsecret
		}

		userLoginStatus = iLoginDelegate.validatePassword(loginDto);
		

		switch (userLoginStatus) {
		case 4:
			// invalid state check for providers
			
			if (getSession() != null)
				getSession().invalidate();
			if (StringUtils.isNotEmpty(errDestination)) {
				toReturn = errDestination;
			} else {
				toReturn = "invalidStateLogin";
			}
			break;
		case 2:
			
			// password locked
			addFieldError("username", Config.getResouceBundle().getString("loginForm.userid.locked.error"));
			return INPUT;
		case -1:
			// third attempt
			
			addFieldError("username", Config.getResouceBundle().getString("loginForm.login.failure.error"));

			return INPUT;
		case 1:
			// Sysgen password
			// loading security
			
			loadSecurityContext();
			createCommonServiceOrderCriteria();
			toReturn = "changePassword";
			break;
		case 0:
			// Successful
			// loading security
			
			loadSecurityContext();
			setSecurityToken(getSession());
			String serverName = getRequest().getServerName();
			String path = getRequest().getContextPath();
			if (path.indexOf('/') == 0) {
				path = path.substring(1);
			}

			logger.info("===***=== DEBUG_UNCLAIM=== user logging in ");
			if (lcvo.getRoleId().intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
				
				timeout = Integer.valueOf(5);
				url = b2cUrl;
				toReturn = "b2c";
			} else if ((lcvo.getRoleId().intValue() == OrderConstants.BUYER_ROLEID)) {
				
				boolean updateDB = true; // flag to be used to decide
				// whether to update the db with
				// ofac details...
				int ofacFlag = OFACConstants.OFAC_IND_NOT_MATCHED;
				try {
					ofacFlag = performBuyerOfacCheck();
				} catch (Exception e) { // exception is thrown because of
					// the service issue..proceed without updating the db...
					updateDB = false; // do not update the db
				}

				if ((ofacFlag == OFACConstants.OFAC_IND_FULL_IRS_MATCHED) || (ofacFlag == OFACConstants.OFAC_IND_MATCH_IN_PROCESS)) {
					
					logout();
					return "accountLockedPage";
				}

				else if (ofacFlag == OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED) {
					
					if (buyerAdmin == true) {
						buyerOfacVO.setBuyerOfacIndicator(Integer.valueOf(OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED));
						toReturn = "loginAdditionalInfoPage";
					} else {
						
						logout();
						buyerOfacVO.setBuyerOfacIndicator(Integer.valueOf(OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED));
						toReturn = "accountLockedPage";
					}
				} else if (ofacFlag == OFACConstants.OFAC_IND_IRS_CLEARED) {
					
					toReturn = "buyerdashboard";
					updateDB = false;
				} else {
					
					buyerOfacVO.setBuyerOfacIndicator(Integer.valueOf(OFACConstants.OFAC_IND_NOT_MATCHED));
					toReturn = "buyerdashboard";
				}

				if (updateDB) {
					
					updateOfacDbFlag(OrderConstants.BUYER_ROLEID);
				}
				// provider ofac check below					
				if (serverName.equalsIgnoreCase(professionalUrl)) {
					
					timeout = Integer.valueOf(0);
					url = commercialUrl + path + "/dashboardAction.action";
					toReturn = "redirect";
					modifyTomcatCookie();
				} else if (serverName.equalsIgnoreCase(providerUrlSample)) {
					
					timeout = Integer.valueOf(0);
					url = commercialUrl + path + "/dashboardAction.action";
					toReturn = "redirect";
					modifyTomcatCookie();
				}
			} else if (lcvo.getRoleId().intValue() == OrderConstants.PROVIDER_ROLEID) {
				
				boolean updateDB = true; // flag to be used to decide
				// whether to update the db with
				// ofac details...
				int ofacFlag = OFACConstants.OFAC_IND_NOT_MATCHED;
				try {
					ofacFlag = performProviderOfacCheck();
				} catch (Exception e) { // exception is thrown because of
					// the service issue..proceed
					// without updating the db...
					logger.error("Call to OFAC webserice failed-->Exception-->", e);
					updateDB = false;
				}

				if (ofacFlag == OFACConstants.OFAC_IND_FULL_IRS_MATCHED || (ofacFlag == OFACConstants.OFAC_IND_MATCH_IN_PROCESS)) {
					logout();
					return "accountLockedPage";
				} else if (ofacFlag == OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED) {
					if (vendorAdmin == true) {
						providerOfacVO.setProviderOfacIndicator(Integer.valueOf(OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED));
						toReturn = "loginAdditionalInfoPage";
					} else {
						logout();
						providerOfacVO.setProviderOfacIndicator(Integer.valueOf(OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED));
						toReturn = "accountLockedPage";

					}
				} else if (ofacFlag == OFACConstants.OFAC_IND_IRS_CLEARED) {
					toReturn = "providerdashboard";
					updateDB = false;
				} else {
					providerOfacVO.setProviderOfacIndicator(Integer.valueOf(OFACConstants.OFAC_IND_NOT_MATCHED));
					toReturn = "providerdashboard";

				}
				if (updateDB) {
					updateOfacDbFlag(OrderConstants.PROVIDER_ROLEID);
				}

				if (serverName.equalsIgnoreCase(professionalUrl)) {
					timeout = Integer.valueOf(0);
					url = providerUrl + path + "/dashboardAction.action";
					toReturn = "redirect";
					modifyTomcatCookie();
				} else if (serverName.equalsIgnoreCase(commercialUrlSample)) {
					timeout = Integer.valueOf(0);
					url = providerUrl + path + "/dashboardAction.action";
					toReturn = "redirect";
					modifyTomcatCookie();
				}
			} else if (lcvo.getRoleId().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID) {// serviceLIve
				// Admin
				// ID,
				// so
				// no
				// ofac
				// check
				// for
				// them
				toReturn = "providerdashboard";

			}
			break;
		case -2:
			if (StringUtils.isNotEmpty(errDestination)) {
				toReturn = errDestination;
			} else {
				addFieldError("username", Config.getResouceBundle().getString("loginForm.login.failure.error"));

				toReturn = INPUT;
			}
			break;
		default:
			addActionError(Config.getResouceBundle().getString("loginForm.login.failure.error"));
			toReturn = INPUT;
		}
		if ((toReturn != null) && !toReturn.equals(errDestination) && StringUtils.isNotEmpty(destination)) {
			setAttribute("destination", destination);
			toReturn = destination;
		}	
		//SL-10675 
		if (getRequest().getParameter("rememberUserId") != null
				&& getRequest().getParameter("rememberUserId")
						.equalsIgnoreCase("true")) {
			
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(loginDto.getUsername());
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);		
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
			if (cryptographyVO != null && null != cryptographyVO.getResponse()) {
				Cookie cookie = new Cookie("username", cryptographyVO
						.getResponse());
				cookie.setMaxAge(2678400); // one month
				getResponse().addCookie(cookie);
			}						
			Cookie rememberIdcookie = new Cookie("rememberId", "checked");
			rememberIdcookie.setMaxAge(2678400);
			getResponse().addCookie(rememberIdcookie);
		} else {
			Cookie rememberIdcookie = new Cookie("rememberId", "unchecked");
			rememberIdcookie.setMaxAge(2678400);
			Cookie cookie = new Cookie("username", "");
			cookie.setMaxAge(2678400);
			getResponse().addCookie(cookie);
			getResponse().addCookie(rememberIdcookie);
		}

		return toReturn;
	}

	//SL-15642 - For permission based display of Order Management tab.
	private void checkOrderManagementPermission(int providerFirmId) {
		try{
			//check for order management flag in appication_properties
			String omFlag = applicationProperties.getPropertyFromDB(Constants.AppPropConstants.ORDER_MANAGEMENT_FLAG);
			Integer vendorId = Integer.valueOf(providerFirmId);
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
	private void getRoleActivities(SecurityContext securityContext, String userName) {
		List<UserActivityVO> userActivityVoList = securityDel.getUserActivityRolesList(userName);

		if (userActivityVoList == null) {
			securityContext.setRoleActivityIdList(new HashMap<String, UserActivityVO>());
			return;
		}

		Map<String, UserActivityVO> userActivityVoMapByActivityId = new HashMap<String, UserActivityVO>();
		LoginCredentialVO loginCredentialVo = securityContext.getRoles();
		boolean isCarFeatureOn = isBuyerFeatureOn(securityContext, BuyerFeatureConstants.CONDITIONAL_ROUTE);
		boolean isTieredRoutingFeatureOn = isBuyerFeatureOn(securityContext, BuyerFeatureConstants.TIER_ROUTE);
		boolean isAutocloseFeatureOn = isBuyerFeatureOn(securityContext, BuyerFeatureConstants.AUTO_CLOSE);
		boolean isHSRAutocloseFeatureOn = isBuyerFeatureOn(securityContext, BuyerFeatureConstants.INHOME_AUTO_CLOSE);
		//SL-15642 checking  auto accept status feature set for buyer admin role id=3 and entry in user_profile table
		boolean isAutoAcceptFeatureOn= isBuyerFeatureOn(securityContext, BuyerFeatureConstants.AUTO_ACCEPTANCE);
		//If buyer has auto acceptance feature set On then check for buyer admin  
		if(isAutoAcceptFeatureOn==true)
		{
				if((securityContext.getRoleId()!=null)&&(securityContext.getRoleId()==3))
				{
					if (securityDel.checkForBuyerAdmin(securityContext.getUsername())) 
					{
						getSession().setAttribute("autoAcceptBuyerAdmin","true");
					}
				}
		}
		//Checking the lead management permission for a buyer and provider
		boolean leadManagmentPermissionInd=checkLeadManagementPermission(securityContext.getRoleId());
		if(leadManagmentPermissionInd && securityContext.isBuyer())
		{	
			boolean isBuyerLeadManagementFeatureOn= isBuyerFeatureOn(securityContext, BuyerFeatureConstants.LEAD_MANAGEMENT);
				if(isBuyerLeadManagementFeatureOn==true)
				{
						getSession().setAttribute("buyerLeadManagementPermission","true");
				}
			
			
		}
		else {
			getSession().setAttribute("providerLeadManagementPermission",leadManagmentPermissionInd);
		}
		
		for (UserActivityVO userActivityVo : userActivityVoList) {
			int activityId = Integer.parseInt(userActivityVo.getActivityId());

			//Note: This code is also related to the code in BAAddEditAction

			// conditional auto routing checks
			if (securityContext.isBuyer()) {
				if (activityId == ActivityRegistryConstants.ACTIVITY_ID_VIEW_CONDITIONAL_AUTO_ROUTING_RULES || activityId == ActivityRegistryConstants.ACTIVITY_ID_MANAGE_CONDITIONAL_AUTO_ROUTING_RULES) {
					if (!isCarFeatureOn) {
						continue;
					}
				}
				if (activityId == ActivityRegistryConstants.ACTIVITY_ID_MANAGE_AUTO_CLOSE_AND_PAY_RULES) {
					if (!isAutocloseFeatureOn) {
						continue;
					}
				}
				if (activityId == ActivityRegistryConstants.ACTIVITY_ID_HSR_MANAGE_AUTO_CLOSE_AND_PAY_RULES) {
					if (!isHSRAutocloseFeatureOn) {
						continue;
					}
				}
				//At this time, these permission are not expected to be used in marketfrontend, but
				//rather this is here for completeness.
				if (activityId == ActivityRegistryConstants.ACTIVITY_ID_TIER_ROUTING) {
					if (!isTieredRoutingFeatureOn) {
						continue;
					}
				}
				//SL20014-to set buyer permission to view provider firm uploaded compliance documents
				if (activityId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_BUYER_SPN_VIEWDOC_PERMISSION) {
					getSession().setAttribute(SPNConstants.BUYER_SPNVIEWDOC_PERMISSION, true);
				}
				//SL20014-end
				
					getSession().setAttribute("autoAcceptStatus", isAutoAcceptFeatureOn);
					getSession().setAttribute("carFeatureOn",isCarFeatureOn);
				}

			userActivityVoMapByActivityId.put(String.valueOf(activityId), userActivityVo);
			
			if(null != userActivityVo.getActivityName() && userActivityVo.getActivityName().equalsIgnoreCase("Buyer Email Configuration")) {
				int buyerId = securityContext.getCompanyId();
				boolean isValid=securityDel.isFeatureAvailable(buyerId);
				if(!isValid) {
					userActivityVoMapByActivityId.remove((String.valueOf(activityId)));
				}
			}
			

			if (loginCredentialVo == null) {
				continue;
			}

			if (loginCredentialVo.getRoleInCompany() != null && loginCredentialVo.getRoleInCompany().intValue() == Constants.COMPANY_ROLE.SUPER_ADMIN_ID) {
				loginCredentialVo.setPasswordResetForSLAdmin(true);
			}

			if (activityId == ActivityRegistryConstants.ACTIVITY_ID_PASSWORD_RESET_FOR_ALL_EXTERNAL_USERS) {
				loginCredentialVo.setPasswordResetForAllExternalUsers(true);
			}
			//SL-15642 - For permission based display of Order Management tab.
			if (activityId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_VIEW_ORDER_MANAGEMENT) {
				checkOrderManagementPermission(providerID);
			}
			//SL-18330 -- For permission based display of Provider Name change Correction.
			if (activityId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_PROVIDER_NAME_CORRECTION) {
				loginCredentialVo.setPermissionForProviderNameChange(true);
			}
			
			if (activityId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_PROVIDER_ADMIN_NAME_CHANGE) { // // Provider Admin Name Chane
				loginCredentialVo.setPermissionForAdminNameChange(Boolean.TRUE);
			}
			
			//SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000
			if (activityId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_FORCEFUL_CAR_RULE_ACTIVATION) { // // CAR Force Activation
				loginCredentialVo.setPermissionForForcefulCarActivation(Boolean.TRUE);
				
			}
			//SL- changes starts
			if (activityId == ActivityRegistryConstants.ACTIVITY_ROLE_ID_BUYER_ADMIN_NAME_CHANGE) { // // Buyer Admin Name Chane
					loginCredentialVo.setPermissionForBuyerAdminNameChange(Boolean.TRUE);
				}
				//SL-20461 changes ends
		}
		securityContext.setRoleActivityIdList(userActivityVoMapByActivityId);
	}

	private boolean isBuyerFeatureOn(SecurityContext securityContext, String feature) {

		//Note: This method isn't currently generic enough to handle all features.
		// It was originally implemented with Conditional Auto Routing in mind, but is expected to expand until a 
		// new project is used to limit permissions (aka activities) by feature set. 
		if (securityContext.isBuyer()) {
			Integer buyerId = securityContext.getCompanyId();
			if (buyerId == null) {
				throw new RuntimeException("Couldn't find the buyerId", new Exception());
			}

			// lookup buyer_feature_set
			return buyerFeatureSetBO.validateFeature(buyerId, feature).booleanValue();

		}
		return false;
	}

	private void loadSLAdminDetails(SecurityContext securityCntxt) {
		getRoleActivities(securityCntxt, loginDto.getUsername());
		securityCntxt.setSlAdminInd(true);
		securityCntxt.setSlAdminFName(get_commonCriteria().getFName());
		securityCntxt.setSlAdminLName(get_commonCriteria().getLName());
		securityCntxt.setSlAdminUName(loginDto.getUsername());
		securityCntxt.setVendBuyerResId(iLoginDelegate.retrieveResourceIDByUserName(loginDto.getUsername()));
		securityCntxt.setCompanyId(Integer.valueOf(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION));
		ActionContext.getContext().getSession().put("SecurityContext", securityCntxt);

		//Setting User Name in the Session
		ActionContext.getContext().getSession().put("username", loginDto.getUsername());
	}

	private void loadBuyerDetails(SecurityContext securityCntxt) {
		Map<String, Object> sessionAttributes = ActionContext.getContext().getSession();
		Map<String, ?> buyer = securityDel.getBuyerId(loginDto.getUsername());
		Account acct = new Account();
		try {
			acct = getAutoACHEnabledInd();
		} catch (Exception e) {
			logger.error("Error getting AutoACH indicator" + e);
		}
		if (buyer != null && buyer.get("buyerId") != null) {
			buyerID = ((Long) buyer.get("buyerId")).intValue();
			securityCntxt.setCompanyId(Integer.valueOf(buyerID));
			if (securityDel.checkForBuyerAdmin(loginDto.getUsername())) {
				buyerAdmin = true;
			}

		}
		if (buyer != null && buyer.get("buyerResourceId") != null) {
			securityCntxt.setVendBuyerResId(Integer.valueOf(((Long) buyer.get("buyerResourceId")).intValue()));
			securityCntxt.setAutoACH(acct.isEnabled_ind());
			securityCntxt.setAccountID(acct.getAccount_id());
		}
		if (buyer != null && buyer.get("maxSpendLimitPerSo") != null) {
			securityCntxt.setMaxSpendLimitPerSO(((Double) buyer.get("maxSpendLimitPerSo")).doubleValue());
		}
		if (buyer != null && buyer.get("clientId") != null) {
			Integer clientId = (Integer) buyer.get("clientId");
			securityCntxt.setClientId(clientId);
			logger.info("Client Id = [" + clientId + "]");
		}
		if (buyer != null && buyer.get(OrderConstants.BUYER_ADMIN_CONTACT_ID) != null) {
			Integer buyerAdminContactId = Integer.valueOf(((Long) buyer.get(OrderConstants.BUYER_ADMIN_CONTACT_ID)).intValue());
			securityCntxt.setBuyerAdminContactId(buyerAdminContactId);
			logger.info("buyerAdminContactId = [" + buyerAdminContactId + "]");
		}
		getRoleActivities(securityCntxt, loginDto.getUsername());
		securityCntxt.setRegComplete(true);
		// Get the user's lifetime rating and set it in session to show in
		// common header on each page
		SurveyRatingsVO surveyRatingsVO = securityDel.getLifetimeRatings(securityCntxt);
		Integer lifetimeRatings = Integer.valueOf(UIUtils.calculateScoreNumber(surveyRatingsVO != null && surveyRatingsVO.getHistoricalRating() != null ? surveyRatingsVO.getHistoricalRating().doubleValue() : 0));

		getSession().setAttribute(OrderConstants.LIFETIME_RATINGS_STAR_NUMBER, lifetimeRatings);

		// Put it in session as cache so that We done have to read it from DB when we go to
		// dashboard . This will improve Dashborad loading time by 500ms.
		getSession().setAttribute(OrderConstants.LIFETIME_RATINGS_VO, surveyRatingsVO);

		getSession().setAttribute(OrderConstants.SERVICELIVESESSIONTIMEOUT, new Long(System.currentTimeMillis()));

		//Setting User Name in the Session
		ActionContext.getContext().getSession().put("username", loginDto.getUsername());

		sessionAttributes.put("SecurityContext", securityCntxt);
	}

	private void loadProviderDetails(SecurityContext securityCntxt) throws Exception {
		Map<String, Object> sessionAttributes = ActionContext.getContext().getSession();
		Map<String, Long> vendor = securityDel.getVendorId(loginDto.getUsername());

		if (vendor != null && vendor.get("vendorId") != null) {
			providerID = vendor.get("vendorId").intValue();
			securityCntxt.setCompanyId(Integer.valueOf(providerID));

			boolean regComplete = !(dashboardDelegate.isIncompleteProvider(securityCntxt.getCompanyId()));
			securityCntxt.setRegComplete(regComplete);
		}

		if (vendor != null && vendor.get("vendorResourceId") != null) {
			securityCntxt.setVendBuyerResId(Integer.valueOf(vendor.get("vendorResourceId").intValue()));

		}
		
		getRoleActivities(securityCntxt, loginDto.getUsername());
		
		Map providerIndicators = securityDel.getProviderIndicators(securityCntxt.getVendBuyerResId());

		if (providerIndicators != null ) {
			if(providerIndicators.get("primaryIndicator") != null){
			securityCntxt.setPrimaryInd(((Boolean) providerIndicators.get("primaryIndicator")).booleanValue());

			if (securityCntxt.isPrimaryInd()) {
				vendorAdmin = true;
			}
			logger.debug("Primary Indicator from Security Context is ******" + securityCntxt.isPrimaryInd());
		}
			if(providerIndicators.get("dispatchIndicator") != null){
				securityCntxt.setDispatchInd(((Boolean) providerIndicators.get("dispatchIndicator")).booleanValue());
			}
			if(providerIndicators.get("providerAdminIndicator") != null) {
				securityCntxt.setProviderAdminInd(((Boolean) providerIndicators.get("providerAdminIndicator")));
			}
			logger
			.debug("Provider Admin Indicator from Security Context is ******" + securityCntxt.isProviderAdminInd());
		}
		if (providerIndicators != null && providerIndicators.get("mktPlaceIndicator") != null) {
			securityCntxt.setMktPlaceInd(((Boolean) providerIndicators.get("mktPlaceIndicator")).booleanValue());
			logger.debug("Market Place Indicator from Security Context is ******" + securityCntxt.isMktPlaceInd());
		}
		String adminUsername = securityDel.getAdminUserName(securityCntxt.getCompanyId());
		if (null != adminUsername) {
			logger.debug("admin user id ++++" + adminUsername);
			securityCntxt.setAdminResId(Integer.valueOf(adminUsername));
		}
		Boolean hasAdminPermissions = securityDel.hasResourceAdminPermission(securityCntxt.getVendBuyerResId());
		if (hasAdminPermissions != null) {
			securityCntxt.setHasProviderAdminPermissions(hasAdminPermissions.booleanValue());
		}

		Location locn = securityDel.getProviderLocation(securityCntxt.getUsername());
		if (null != locn) {
			String proCityStateZip = ServiceLiveStringUtils.concatenateCityStateZip(locn.getCity(), locn.getStateCd(), locn.getZip(), locn.getZip4());
			securityCntxt.setProviderLocation(ServiceLiveStringUtils.concatentAllAddressFields(locn.getStreet1(), locn.getStreet2(), proCityStateZip));
		}
		
		//checks whether provider has Manage Business Profile Permission
		int permission = dashboardDelegate.getPermission(securityCntxt.getVendBuyerResId());
		getSession().setAttribute("permission", permission);
		
		//checks whether provider has any un-archived CAR rules
		int unArchivedCount = dashboardDelegate.getUnarchivedCARRulesCount(securityCntxt.getCompanyId());
		securityCntxt.setUnArchivedCARRulesAvailable(unArchivedCount);
		
		ActionContext.getContext().getSession().put("vendorId", securityCntxt.getCompanyId() + "");
		ActionContext.getContext().getSession().put("providerName", iLoginDelegate.getProviderName(loginDto.getUsername()));
		ActionContext.getContext().getSession().put("username", loginDto.getUsername());

		sessionAttributes.put("SecurityContext", securityCntxt);
	}

	public String loginAndForwardToReview() throws Exception {

		String loginUser = (String) getSession().getAttribute(SOConstants.USERNAME_WHILE_CREATING_SERVICE_ORDER);
		//Check if anonymous simple buyer login
		if (!StringUtils.isBlank(loginUser)) {
			// Set login details while creating SO for anonymous simple buyer
			loginDto.setUsername((String) getSession().getAttribute(SOConstants.USERNAME_WHILE_CREATING_SERVICE_ORDER));
			LoginCredentialVO lvo = new LoginCredentialVO();
			lvo.setUsername(loginDto.getUsername());
			lcvo = securityDel.getUserRoles(lvo);
			SecurityContext securityCntxt = new SecurityContext(loginDto.getUsername());
			securityCntxt.setRoles(lcvo);
			securityCntxt.setCompanyId(Integer.valueOf(99));
			securityCntxt.setRoleId(lcvo.getRoleId());
			securityCntxt.setRole(lcvo.roleName);
			loadBuyerDetails(securityCntxt);
			return "redirectReview";
		}

		loginDto.setUsername(getRequest().getParameter("username"));
		loginDto.setPassword(getRequest().getParameter("password"));

		if (StringUtils.isEmpty(loginDto.getUsername())) {
			addActionError(Config.getResouceBundle().getString("login.username.required.error.msg"));
			return INPUT;
		}

		if (StringUtils.isEmpty(loginDto.getPassword())) {
			addActionError(Config.getResouceBundle().getString("login.password.required.error.msg"));

			return INPUT;
		}

		loginDto.setPassword(CryptoUtil.encrypt(loginDto.getPassword()));
		if (securityDel.authenticate(loginDto.getUsername(), loginDto.getPassword().trim())) {
			LoginCredentialVO lvo = new LoginCredentialVO();
			lvo.setUsername(loginDto.getUsername());
			lcvo = securityDel.getUserRoles(lvo);

			SecurityContext securityCntxt = new SecurityContext(loginDto.getUsername());
			securityCntxt.setRoles(lcvo);
			securityCntxt.setCompanyId(Integer.valueOf(99));
			securityCntxt.setRoleId(lcvo.getRoleId());
			securityCntxt.setRole(lcvo.roleName);

			if (OrderConstants.SIMPLE_BUYER_ROLEID == lcvo.getRoleId().intValue()) {
				loadBuyerDetails(securityCntxt); // This stores the
				// security-context in the
				// session
				return "redirectReview";
			}
			throw new Exception("Ooops!  You are not authorized for this workflow.");
		}
		// add error message if username or password does not match 
		addActionError(Config.getResouceBundle().getString("loginForm.login.failure.error.msg"));
		return INPUT;
	}

	public LoginDto getModel() {
		return loginDto;
	}

	private int performBuyerOfacCheck() throws Exception {

		buyerOfacVO = securityDel.getBuyerOfacIndicators(buyerID);// get
		// BuyerOfacVO
		// from
		// buyer
		// table
		// based on
		// resourceID
		int ofacFlag = OFACConstants.OFAC_IND_NOT_MATCHED;

		if (buyerOfacVO.getBuyerOfacIndicator() != null && (buyerOfacVO.getLastOfacCheckDate() != null)) {
			if (buyerOfacVO.getBuyerOfacIndicator().intValue() == OFACConstants.OFAC_IND_FULL_IRS_MATCHED) {
				return OFACConstants.OFAC_IND_FULL_IRS_MATCHED;// send to
				// account
				// locked page
			} else if (buyerOfacVO.getBuyerOfacIndicator().intValue() == OFACConstants.OFAC_IND_MATCH_IN_PROCESS) {
				return OFACConstants.OFAC_IND_MATCH_IN_PROCESS;// send to
				// temporarily
				// account
				// locked page
			} else if (buyerOfacVO.getBuyerOfacIndicator().intValue() == OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED) {
				return OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED;// send to
				// additional
				// info page
			} else if (buyerOfacVO.getBuyerOfacIndicator().intValue() == OFACConstants.OFAC_IND_IRS_CLEARED) {
				ofacFlag = OFACConstants.OFAC_IND_IRS_CLEARED;

				if (DateUtils.workingDays(buyerOfacVO.getLastOfacCheckDate(), new java.util.Date()) > OFACConstants.OFAC_IRS_CLEARED_DURATION) {

					if (StringUtils.isNotBlank(buyerOfacVO.getBusinessName())) {
						ofacFlag = callOfacWebService(buyerOfacVO.getBusinessName());// call webservice() with
						// businessName
					} else {
						String resourceName = getResourceFullName();

						if (StringUtils.isNotBlank(resourceName)) {
							ofacFlag = callOfacWebService(resourceName); // call
							// webservice()
							// with
							// lastName,
							// FirstName
						}
					}

				}
				return ofacFlag;
			} else if (buyerOfacVO.getBuyerOfacIndicator().intValue() == OFACConstants.OFAC_IND_NOT_MATCHED) {
				if (DateUtils.workingDays(buyerOfacVO.getLastOfacCheckDate(), new java.util.Date()) > OFACConstants.OFAC_CHECK_DURATION) {
					if (StringUtils.isNotBlank(buyerOfacVO.getBusinessName())) {
						ofacFlag = callOfacWebService(buyerOfacVO.getBusinessName());// call webservice() with
						// businessName
					} else {
						String resourceName = getResourceFullName();

						if (StringUtils.isNotBlank(resourceName)) {
							ofacFlag = callOfacWebService(resourceName); // call
							// webservice()
							// with
							// lastName,
							// FirstName
						}
					}

				}

			}

			return ofacFlag;
		}

		if (StringUtils.isNotBlank(buyerOfacVO.getBusinessName())) {
			ofacFlag = callOfacWebService(buyerOfacVO.getBusinessName());// call
			// webservice()
			// with
			// businessName
		} else {
			String resourceName = getResourceFullName();

			if (StringUtils.isNotBlank(resourceName)) {
				ofacFlag = callOfacWebService(resourceName); // call
				// webservice()
				// with
				// lastName,
				// FirstName
			}
		}

		return ofacFlag;

	}

	private int performProviderOfacCheck() throws Exception {

		providerOfacVO = securityDel.getProviderOfacIndicators(providerID);
		// get ProviderOfacVO from vendor_hdr table based on providerID
		int ofacFlag = OFACConstants.OFAC_IND_NOT_MATCHED;

		if (providerOfacVO.getProviderOfacIndicator() != null && (providerOfacVO.getLastOfacCheckDate() != null)) {
			if (providerOfacVO.getProviderOfacIndicator().intValue() == OFACConstants.OFAC_IND_FULL_IRS_MATCHED) {
				return OFACConstants.OFAC_IND_FULL_IRS_MATCHED;// send to
				// account
				// locked page
			} else if (providerOfacVO.getProviderOfacIndicator().intValue() == OFACConstants.OFAC_IND_MATCH_IN_PROCESS) {
				return OFACConstants.OFAC_IND_MATCH_IN_PROCESS;// send to
				// temporarily
				// account
				// locked page
			} else if (providerOfacVO.getProviderOfacIndicator().intValue() == OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED) {
				return OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED;// send to
				// additional
				// info page
			} else if (providerOfacVO.getProviderOfacIndicator().intValue() == OFACConstants.OFAC_IND_IRS_CLEARED) {
				if (DateUtils.workingDays(providerOfacVO.getLastOfacCheckDate(), new java.util.Date()) > OFACConstants.OFAC_IRS_CLEARED_DURATION) {

					if (StringUtils.isNotBlank(providerOfacVO.getBusinessName())) {
						ofacFlag = callOfacWebService(providerOfacVO.getBusinessName());// call webservice() with
						// businessName
					} else {
						String resourceName = getResourceFullName();

						if (StringUtils.isNotBlank(resourceName)) {
							ofacFlag = callOfacWebService(resourceName); // call
							// webservice()
							// with
							// lastName,
							// FirstName
						}
					}
					return ofacFlag;
				}
				return OFACConstants.OFAC_IND_IRS_CLEARED;// send to DASHBOARD
			} else if (providerOfacVO.getProviderOfacIndicator().intValue() == OFACConstants.OFAC_IND_NOT_MATCHED) {
				if (DateUtils.workingDays(providerOfacVO.getLastOfacCheckDate(), new java.util.Date()) > OFACConstants.OFAC_CHECK_DURATION) {
					if (StringUtils.isNotBlank(providerOfacVO.getBusinessName())) {
						ofacFlag = callOfacWebService(providerOfacVO.getBusinessName());// call webservice() with
						// businessName
					} else {
						String resourceName = getResourceFullName();

						if (StringUtils.isNotBlank(resourceName)) {
							ofacFlag = callOfacWebService(resourceName); // call
							// webservice()
							// with
							// lastName,
							// FirstName
						}

					}

				}

			}

			return ofacFlag;
		}
		if (StringUtils.isNotBlank(providerOfacVO.getBusinessName())) {
			ofacFlag = callOfacWebService(providerOfacVO.getBusinessName());// call
			// webservice()
			// with
			// businessName
		}
		return ofacFlag;

	}

	private int callOfacWebService(String resourceName) throws Exception {
		int ofacIndicator = 0;

		try {
			OFACSDNClient ofacCheckerClient = new OFACSDNClient();
			boolean ofacMatched = ofacCheckerClient.isSDNAndBlockedPersons(resourceName.trim());
			// if found return send to more information page
			if (ofacMatched)
				ofacIndicator = OFACConstants.OFAC_IND_INITIAL_NAME_MATCHED;
			else
				ofacIndicator = OFACConstants.OFAC_IND_NOT_MATCHED;
		} catch (Exception e) {
			// Scott's Rule: When you receive exception while validaing user's record against OFAC, log error and set indicator to not matched
			// Suppress exception stack trace purposely; just show the error message
			logger.error("****** OFAC WebService Failed ******* Error Message: " + e.getMessage());
		}

		return ofacIndicator;

	}

	private void updateOfacDbFlag(int roleID) throws Exception {
		if ((roleID == OrderConstants.BUYER_ROLEID) || (roleID == OrderConstants.SIMPLE_BUYER_ROLEID)) {

			securityDel.updateBuyerOfacDbFlag(buyerOfacVO);// update buyer
			// table for ofac
			// indicator
		} else if (roleID == OrderConstants.PROVIDER_ROLEID) {
			securityDel.updateProviderOfacDbFlag(providerOfacVO);// update
			// provider
			// table for
			// ofac
			// indicator
		}

	}

	private String getResourceFullName() {

		String resourceFullName = "";

		if (StringUtils.isNotEmpty(resourceLastName)) {
			if (StringUtils.isNotEmpty(resourceFirstName)) {
				resourceFullName = resourceLastName + ", " + resourceFirstName;
			} else
				resourceFullName = resourceLastName;
		}
		return resourceFullName;
	}

	public void insertLoginAudit(SecurityContext securityCntxt) {
		LoginAuditVO loginAuditVO = new LoginAuditVO();
		loginAuditVO.setCompanyId(securityCntxt.getCompanyId().intValue());
		loginAuditVO.setResourceId(securityCntxt.getVendBuyerResId().intValue());
		loginAuditVO.setRoleId(securityCntxt.getRoleId().intValue());
		HashMap<String, Integer> auditIdMap = auditLogDelegates.insertLoginAudit(loginAuditVO);
		securityCntxt.setLoginAuditId(Integer.parseInt(auditIdMap.get("loginAuditId").toString()));
		securityCntxt.setActiveSessionAuditId(Integer.parseInt(auditIdMap.get("sessionAuditId").toString()));
	}

	/**
	 * @SkipValidation 
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		
		/*Added to destroy the cookie isDismissedBanner set in display method of
		 * LoginAction.java in another package.	*/
		HttpServletResponse bannerCookieResponse=ServletActionContext.getResponse();
	    Cookie[] cookies = getRequest().getCookies();
	    for (Cookie c : cookies) {
	    	if(c.getName().equals("isDismissedBanner")){
	    		//If banner is not dismissed yet,setting "No" to cookie.
	    		if(c.getValue().equals("No")){
	    		   c.setValue("No");
			       c.setMaxAge(-1);
			       c.setPath("/");
	    		}//Destroy cookie once banner is displayed onLogout.
	    		else if(c.getValue().equals("Yes")){
	    		   bannerCookieResponse.setContentType("text/html");
		    	   c.setValue("");
		    	   c.setMaxAge(0);
		    	   c.setPath("/");
	    		}
	    		bannerCookieResponse.addCookie(c);
	    		break;
	    	}
	    }
	    ServletActionContext.setResponse(bannerCookieResponse);
	    HttpSession session = ServletActionContext.getRequest().getSession(false);
		String returnVal = SUCCESS;
		synchronized (session) {
			if (session != null) {
				SecurityContext securityCntxt = (SecurityContext) session.getAttribute(Constants.SESSION.SECURITY_CONTEXT);

				if (securityCntxt != null) {

					int loginAuditId = securityCntxt.getLoginAuditId();
					int activeSessionAuditId = securityCntxt.getActiveSessionAuditId();
					logger.info("LoginLogging: Logout: LoginAuditId = [" + loginAuditId + "] activeSessionAuditId = [" + activeSessionAuditId + "]");
					LoginAuditVO loginAuditVO = new LoginAuditVO();
					loginAuditVO.setCompanyId(securityCntxt.getCompanyId().intValue());
					loginAuditVO.setResourceId(securityCntxt.getVendBuyerResId().intValue());
					loginAuditVO.setRoleId(securityCntxt.getRoleId().intValue());
					auditLogDelegates.updateLoginAudit(loginAuditVO, loginAuditId, activeSessionAuditId, 0);
					securityCntxt.setLoginAuditId(0);
					
					HttpServletResponse response = ServletActionContext.getResponse();
					if ((securityCntxt.getRoleId().intValue() == OrderConstants.BUYER_ROLEID)) {
						String staticBuyerURL = System.getProperty("business.Url");
						if(StringUtils.isNotBlank(staticBuyerURL)) {
							response.sendRedirect(staticBuyerURL);
							returnVal = null;
						} else {
							returnVal = "commercial";
						}
						
					} else if ((securityCntxt.getRoleId().intValue() == OrderConstants.PROVIDER_ROLEID)) {
						String staticProviderURL = System.getProperty("provider.Url");
						if(StringUtils.isNotBlank(staticProviderURL)) {
							response.sendRedirect(staticProviderURL);
							returnVal = null;
						} else {
							returnVal = "provider";
						}
					}
					//Added for Incident 4148358(Sl-19820)
					// Clearing the session objects of Service Order Wizard
					logger.debug("Invoking getStartPointAndInvalidate()" );
					String soId = getParameter("soId");
					setAttribute(OrderConstants.SO_ID, soId);
					SOWSessionFacility.getInstance().getStartPointAndInvalidate(fetchDelegate, securityCntxt);
				}
				session.invalidate();
			}
		}

		return returnVal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(String percentComplete) {
		this.percentComplete = percentComplete;
	}

	public void setSession(Map hm) {
		ActionContext.getContext().setSession(hm);

	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getErrDestination() {
		return errDestination;
	}

	public void setErrDestination(String errDestination) {
		this.errDestination = errDestination;
	}

	public int getBuyerID() {
		return buyerID;
	}

	public void setBuyerID(int buyerID) {
		this.buyerID = buyerID;
	}

	public int getProviderID() {
		return providerID;
	}

	public void setProviderID(int providerID) {
		this.providerID = providerID;
	}

	public String getResourceLastName() {
		return resourceLastName;
	}

	public void setResourceLastName(String resourceLastName) {
		this.resourceLastName = resourceLastName;
	}

	public String getResourceFirstName() {
		return resourceFirstName;
	}

	public void setResourceFirstName(String resourceFirstName) {
		this.resourceFirstName = resourceFirstName;
	}

	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}

	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
		this.providerUrlSample = makeSampleURL(providerUrl);
	}

	public String getCommercialUrl() {
		return commercialUrl;
	}

	public void setCommercialUrl(String commercialUrl) {
		this.commercialUrl = commercialUrl;
		this.commercialUrlSample = makeSampleURL(commercialUrl);
	}

	public String getProfessionalUrl() {
		return professionalUrl;
	}

	public void setProfessionalUrl(String professionalUrl) {
		this.professionalUrl = makeSampleURL(professionalUrl);
	}

	public String makeSampleURL(String pUrl) {
		if (pUrl == null) {
			return pUrl;
		}

		String temp[] = pUrl.split("//");
		if (temp.length > 1) { // now we have www.aaa.com:port/
			String[] temp2 = temp[1].split("/"); //removed /
			String[] temp3 = temp2[0].split(":");
			pUrl = temp3[0];
		}
		return pUrl;
	}

	public String getB2cUrl() {
		return b2cUrl;
	}

	public void setB2cUrl(String b2cUrl) {
		this.b2cUrl = b2cUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	/**
	 * Playing with system cookie to make it subdomain aware.
	 */
	private void modifyTomcatCookie() {
		Cookie[] cok = getRequest().getCookies();
		for (Cookie c : cok) {

			if (c.getName().equals("JSESSIONID")) {

				String[] domain = getRequest().getServerName().split("\\.");
				if (domain.length == 3) {
					c.setDomain("." + domain[1] + "." + domain[2]);
					getResponse().addCookie(c);
				}
			}
		}
	}
	/**
	 *Added to destroy the cookie isDismissedBaner set in display method of
	 * LoginAction.java in another package.
	 **/
	private void DeleteBannerCookie(){
		HttpServletResponse bannerCookieResponse=ServletActionContext.getResponse();
	    Cookie[] cookies = getRequest().getCookies();
	    for (Cookie c : cookies) {
	    	if(c.getName().equals("isDismissedBanner")){
	    		bannerCookieResponse.setContentType("text/html");
	    		c.setValue("");
	    		c.setMaxAge(0);
	    		c.setPath("/");
	    		bannerCookieResponse.addCookie(c);
	    		break;
	    	}
	    }
	    ServletActionContext.setResponse(bannerCookieResponse);
    }
	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	public ISOWizardFetchDelegate getFetchDelegate() {
		return fetchDelegate;
	}

	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}

}
