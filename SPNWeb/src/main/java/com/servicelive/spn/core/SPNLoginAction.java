package com.servicelive.spn.core;



import static com.servicelive.spn.constants.SPNActionConstants.USER_OBJECT_IN_SESSION;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.RequestInfo;
import com.newco.marketplace.utils.SharedSecret;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerFeatureSet;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.userprofile.SPNUser;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.common.RoleEnum;
import com.servicelive.spn.common.util.Cryptography;
import com.servicelive.spn.services.AuthenticationService;
import com.servicelive.spn.services.AuthorizationService;

/**
 * 
 *
 */
public class SPNLoginAction extends SPNBaseAction
implements Preparable
{
	private static final long serialVersionUID = -917491066328837072L;
	private static final String SPN_MONITOR_ACTION = "spnMonitorNetwork_display"; 
	String userName;
	String credential;
	String targetAction;
	String buyerId;
	String rememberUserId;
	
	private String resultUrl ;
	private AuthenticationService authenticationService;
	private AuthorizationService authorizationService;
	private Cryptography cryptography;
	private String vendorId;
	private String vendorName;
	private String vendorResourceId;
	private String firstName;
	private String lastName;
	private String sharedSecretString;
	
	/**
	 * @param authorizationService the authorizationService to set
	 */
	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}
	/**
	 * 
	 * @param authenticationService
	 */
	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	/**
	 * 
	 * @return String
	 * @throws Exception 
	 */
	public String display() throws Exception
	{

		if (StringUtils.isNotEmpty(getRequest().getParameter("buyerId"))
				&& StringUtils
						.isNotEmpty(getRequest().getParameter("username"))
				&& StringUtils.isNotEmpty(getRequest()
						.getParameter("isSlAdmin"))) {
			setDefaultTargetAction();
			Integer buyerId = Integer.parseInt(getRequest().getParameter(
					"buyerId"));
			String username = getRequest().getParameter("username");
			Boolean isSlAdmin = Boolean.valueOf(getRequest().getParameter(
					"isSlAdmin"));
			if (buyerId != null && username != null && isSlAdmin != null) {
				validateUserOrAdmin(buyerId, username, isSlAdmin);
				return "redirectAction";
			}
		}
		
		Cookie[] cookies = getRequest().getCookies();
		for (Cookie c : cookies) {
			if (c.getName().equals("spn_username")) {			
				if(c.getValue() != null &&
						!c.getValue().equalsIgnoreCase("null")){
						CryptographyVO cryptographyVO = new CryptographyVO();
						cryptographyVO.setInput(c.getValue());
						cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);						
						cryptographyVO = getCryptography().decryptKey(cryptographyVO);		
						if(cryptographyVO != null && 
								null != cryptographyVO.getResponse()){
							getRequest().setAttribute("cookieUserName", cryptographyVO.getResponse()) ;					
						}							
					}
				}
			if (c.getName().equals("spn_rememberId")) {
				if (c.getValue() != null
						&& !c.getValue().equalsIgnoreCase("null")) {
					getRequest().setAttribute("cookieRememberId", c.getValue());
				}

			}
			if (getRequest().getAttribute("cookieUserName") != null
					&& getRequest().getAttribute("cookieRememberId") != null)
				break;
		}
		if (getRequest().getAttribute("cookieUserName") == null)
					getRequest().setAttribute("cookieUserName", "") ;			
		if (getRequest().getAttribute("cookieRememberId") == null)
			getRequest().setAttribute("cookieRememberId", "checked");
		return SUCCESS;
	}
	
	public String ssoLogin() throws Exception {
		
		vendorId = getRequest().getParameter("vendorId");
		vendorName = getRequest().getParameter("vendorName");
		vendorResourceId = getRequest().getParameter("vendorResourceId");
		firstName = getRequest().getParameter("firstName");
		lastName = getRequest().getParameter("lastName");
		sharedSecretString = getRequest().getParameter("sharedSecret");
		
		if(sharedSecretString == null || sharedSecretString.trim().equals("")) {
			List<String> errors = new ArrayList <String>();
			errors.add(getText("errors.common.not.a.valid.user"));
			setActionErrors(errors);
			return "error";
		}

		sharedSecretString = sharedSecretString.replaceAll(" ", "+");		
		SharedSecret sharedSecret = (SharedSecret) CryptoUtil.decryptObject(sharedSecretString);
		if(!validateSharedSecret(sharedSecret)) {
			List<String> errors = new ArrayList <String>();
			errors.add(getText("errors.common.not.a.valid.user"));
			setActionErrors(errors);
			return "error";
		}

		BuyerResource buyerResource = getBuyerServices().getBuyerResourceForUserName(sharedSecret.getUserName());
		User authenticatedUser = buyerResource.getUser();
		SPNUser spnUser = createBuyerResourceUser(buyerResource, authenticatedUser);
		getRequest().getSession().setAttribute(USER_OBJECT_IN_SESSION, spnUser);

		if(vendorResourceId != null)
		{
			return "serviceProDetailsPage";
		}
		else
		{
			return "providerFirmDetailsPage";
		}
	}

	private boolean validateSharedSecret(SharedSecret ss) {
		if(ss == null) {
			return false;
		}
		int requestTimeOutSeconds = 6000;
		String ipAddress = getRequest().getRemoteAddr();
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setIpAddress(ipAddress);
		requestInfo.setRequestTimeOutSeconds(Integer.valueOf(requestTimeOutSeconds));
		return ss.validate(requestInfo);
	}

	private SPNUser createBuyerResourceUser(BuyerResource buyerResource, User authenticateUser) throws Exception {
		SPNUser spnUser = new SPNUser();
		spnUser.setUserDetails(authenticateUser);
		spnUser.setBuyer(buyerResource.getBuyer());
		authenticateUser.setUserId(buyerResource.getBuyer().getBuyerId());
		authenticateUser.setUserChildId(buyerResource.getResourceId());
		updateAuthorities(buyerResource.getBuyer(),authenticateUser);
		return spnUser;
	}
	/**
	 * 
	 * @return String
	 */
	//@SuppressWarnings("unchecked")
	public String loginUser()
	{
		try {
		//	String userName = getPrincipalProxy().getRemoteUser();
		
	
			List<String> errors = new ArrayList <String>();
			
		//	Buyer buyer = getBuyerServices().getBuyerForUserName(userName);
			User authenticateUser = authenticationService.authenticateUser( userName, credential);
			if(authenticateUser == null ) {
				errors.add(getText("errors.common.not.a.valid.user"));
				setActionErrors(errors);
				return "error";
			}
			if( !isValidRole(authenticateUser).booleanValue()){
				
				errors.add(getText("errors.common.not.a.valid.buyer"));
				setActionErrors(errors);
				
				return ERROR;
			}
			
			//getRequest().getSession().setAttribute("rememberUserId", rememberUserId);
			if(getRequest().getParameter("rememberUserId") != null &&
					getRequest().getParameter("rememberUserId").equalsIgnoreCase("true")){
				CryptographyVO cryptographyVO = new CryptographyVO();
				cryptographyVO.setInput(userName);
				cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);		
				cryptographyVO = cryptography.encryptKey(cryptographyVO);
				if(cryptographyVO != null && 
							null != cryptographyVO.getResponse() ){
					Cookie cookie =  new Cookie("spn_username",cryptographyVO.getResponse());
					cookie.setMaxAge(2678400); // one month
					getResponse().addCookie(cookie);
				}
				Cookie rememberIdcookie =  new Cookie("spn_rememberId","checked");
				rememberIdcookie.setMaxAge(2678400);
				getResponse().addCookie(rememberIdcookie);
			}
			else
			{
				Cookie rememberIdcookie =  new Cookie("spn_rememberId","unchecked");
				rememberIdcookie.setMaxAge(2678400); 
				Cookie cookie =  new Cookie("spn_username","");
				cookie.setMaxAge(2678400); 
				getResponse().addCookie(cookie);
				getResponse().addCookie(rememberIdcookie);
			}
			
			if(RoleEnum.ENTERPRISE_BUYER_ROLE.getRoleId().intValue() ==  authenticateUser.getRole().getId().intValue() ) {
				
				BuyerResource buyerResource = getBuyerServices().getBuyerResourceForUserName(userName);
				if(buyerResource  != null && buyerResource.getBuyer() != null) {
					SPNUser spnUser = createBuyerResourceUser(buyerResource, authenticateUser);
					getRequest().getSession().setAttribute(USER_OBJECT_IN_SESSION, spnUser);
					setDefaultTargetAction();
					return SUCCESS;
				}
			}else if (RoleEnum.SL_ADMIN_ROLE.getRoleId().intValue() ==  authenticateUser.getRole().getId().intValue() ) {
				if( buyerId == null) { 
					errors.add(getText("errors.common.not.a.loggedinadmin.notvalid.buyer"));
					setActionErrors(errors);
					return ERROR;
				}
				Buyer buyer = getBuyerServices().getBuyerForId(Integer.valueOf(buyerId));
				if(buyer == null ) {
					errors.add(getText("errors.common.not.a.loggedinadmin.notvalid.buyer"));
					setActionErrors(errors);
					return ERROR;
				}
				
				SPNUser spnUser = new SPNUser();
				spnUser.setUserDetails(authenticateUser);
				spnUser.setBuyer(buyer);
				authenticateUser.setUserId(buyer.getBuyerId());
				authenticateUser.setUserChildId(buyer.getBuyerId());
				updateAuthorities(buyer,authenticateUser);
				getRequest().getSession().setAttribute(USER_OBJECT_IN_SESSION, spnUser);
				setDefaultTargetAction();
				return SUCCESS;
				
				
			}
			
			
		
			return ERROR;
			
		} catch (Exception e) {
			logger.debug("Exception loggin in ",e);
			System.out.print(e);
		}
		
		return SUCCESS;
	}
	// method to validate user and create SPN user and set values for proceeding
	public void validateUserOrAdmin(Integer buyerId, String userName,
			boolean isSlAdmin) throws Exception {
		User user = authenticationService.getUser(userName);
		Buyer buyer = getBuyerServices().getBuyerForId(buyerId);
		if (!isSlAdmin) {
			BuyerResource buyerResource = getBuyerServices()
					.getBuyerResourceForUserName(userName);
			SPNUser spnUser = createBuyerResourceUser(buyerResource, user);
			getRequest().getSession().setAttribute(USER_OBJECT_IN_SESSION,
					spnUser);
		}
		// Creating spn user object for SLAdmin
		else {
			SPNUser spnUser = new SPNUser();
			spnUser.setUserDetails(user);
			spnUser.setBuyer(buyer);
			user.setUserId(buyer.getBuyerId());
			user.setUserChildId(buyer.getBuyerId());
			updateAuthorities(buyer, user);
			getRequest().getSession().setAttribute(USER_OBJECT_IN_SESSION,
					spnUser);
		}
	}
   private Boolean isValidRole(User authenticateUser) throws Exception { 
		Boolean result = Boolean.FALSE;
		Set<Integer> validRoles =  new TreeSet<Integer>();
		validRoles.add(RoleEnum.ENTERPRISE_BUYER_ROLE.getRoleId());
		validRoles.add(RoleEnum.SL_ADMIN_ROLE.getRoleId());
		
		if(authenticateUser.getRole() != null  && validRoles.contains(authenticateUser.getRole().getId())) result = Boolean.TRUE;
		
		return result;
	}

	private void updateAuthorities(Buyer currentBuyer, User user) throws Exception{
		if(currentBuyer.getBuyerfeatureSet() != null ) {
			for(BuyerFeatureSet fs : currentBuyer.getBuyerfeatureSet()){
				if(fs.getIsActive().booleanValue() == true){
					user.getAuthorities().add(fs.getFeatureSet());
				}
			}
		}
		user.getAuthorities().addAll(getPermissionForLoggedInUser(user.getUsername()));
	}
	
	public List<String> getPermissionForLoggedInUser(String authenticateUserName)  throws Exception{
		return authorizationService.getAuthoritiesForUser(authenticateUserName);
	}
	
	/**
	 * This method sets the default invocation target is not supplied during the login
	 */
	private void setDefaultTargetAction() {
		if(StringUtils.isEmpty(targetAction)) {
			targetAction = SPN_MONITOR_ACTION;
		}
	}
	
	
	@Override
	public void validate()
	{
		//int i=0;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getCredential() {
		return credential;
	}
	/**
	 * 
	 * @param credential
	 */
	public void setCredential(String credential) {
		this.credential = credential;
	}

	/*public String gotoMainLogin() {
		resultUrl = servletRequest.getScheme() + "://"+ servletRequest.getServerName() + ":" + servletRequest.getServerPort() + "/sso/secure/Login.action?targetApp=spn";
		return "GOTOMAINLOGIN";
	}*/

	/**
	 * 
	 * @return String
	 */
	public String logoutUser() {
		targetAction = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":"
				+ getRequest().getServerPort()
				+ "/MarketFrontend/doLogout.action";
		getRequest().getSession().invalidate();

		return SUCCESS;
	}
	
	public void prepare() throws Exception
	{
		// do nothing
	}
	/**
	 * 
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return the resultUrl
	 */
	public String getResultUrl() {
		return resultUrl;
	}
	/**
	 * @param resultUrl the resultUrl to set
	 */
	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}
	/**
	 * @return the targetAction
	 */
	public String getTargetAction() {
		return targetAction;
	}
	/**
	 * @param targetAction the targetAction to set
	 */
	public void setTargetAction(String targetAction) {
		this.targetAction = targetAction;
	}
	/**
	 * @return the buyerId
	 */
	public String getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getVendorId()
	{
		return vendorId;
	}
	public void setVendorId(String vendorId)
	{
		this.vendorId = vendorId;
	}
	public String getVendorResourceId()
	{
		return vendorResourceId;
	}
	public void setVendorResourceId(String vendorResourceId)
	{
		this.vendorResourceId = vendorResourceId;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getSharedSecretString()
	{
		return sharedSecretString;
	}
	public void setSharedSecretString(String sharedSecretString)
	{
		this.sharedSecretString = sharedSecretString;
	}
	public String getVendorName()
	{
		return vendorName;
	}
	public void setVendorName(String vendorName)
	{
		this.vendorName = vendorName;
	}
	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}	
	public String getRememberUserId() {
		return rememberUserId;
	}
	public void setRememberUserId(String rememberUserId) {
		this.rememberUserId = rememberUserId;
	}
	
}
