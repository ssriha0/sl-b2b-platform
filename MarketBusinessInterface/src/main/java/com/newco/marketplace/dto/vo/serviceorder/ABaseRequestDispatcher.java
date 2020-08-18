package com.newco.marketplace.dto.vo.serviceorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.service.invoker.AbstractInvoker;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.security.ISecurityBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.webservices.CommonServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.SimpleCache;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.webservices.base.AbstractServiceInvoker;
import com.newco.marketplace.webservices.base.response.ABaseResponse;
import com.newco.marketplace.webservices.base.response.CommonSystemErrorResponse;
import com.newco.marketplace.webservices.base.response.SecurityResponse;
import com.sears.os.service.ServiceConstants;
import com.sears.os.vo.request.ABaseServiceRequestVO;

public abstract class ABaseRequestDispatcher extends AbstractServiceInvoker {

	/**
	 *  Description of the Field
	 */
	public final static String COMMON_NOT_AUTH_MSG = "Username and password error";
	public final static String ORDER_DETAILS_BUSINESS_BEAN_REF = "soOrderDetailsBo";
	
	public final static String SKILL_TREE_BUSINESS_BEAN_REF = "SkillAssignBusinessBean";
	public final static String SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE = "soBOAOP";
	public final static String ORDER_GROUP_BUSINESS_OBJECT_REFERENCE = "soGroupBO";
	public final static String PROVIDER_SEARCH_BUSINESS_OBJECT_REFERENCE = "providerSearchBO";
	public final static String MASTER_CALCULATOR_BUSINESS_OBJECT_REFERENCE = "masterCalculatorBO";
	public final static String SERVICE_ORDER_EVENT_BUSINESS_OBJECT = "soEventBO";
	public final static String BUYER_TEMPLATE_BO = "buyerSOTemplateBO";	
	public final static String DOCUMENT_BUSINESS_OBJECT = "documentBO";
	public final static String WSPAYLOAD_DAO = "wsQueueDao";
	public final static String BUYER_BUSINESS_OBJECT = "buyerBo";
	public final static String GROUP_ORDER_BUSINESS_OBJECT = "soOrderGroupBOAOP";
	public final static String BUYER_FEATURE_SET_BUSINESS_OBJECT = "buyerFeatureSetBO";
	public final static String ROUTE_ORDER_GROUP_BUSINESS_OBJECT = "soOrderGroupRouteAOP";
	public static final String CLIENT_INVOICE_BUSINESS_OBJECT = "clientInvoiceBO";
	public static final String SERVICE_ORDER_UPSELL_BUSINESS_OBJECT_REFERENCE = "soUpsellBO";
	public final static String FINANCE_MANAGER_BUSINESS_OBJECT = "financeManagerBO";
	public final static String INCIDENT_BUSINESS_OBJECT_REFERENCE = "incidentBO";
	public final static String TRCONTROLLER_BUSINESS_OBJECT_REFERENCE = "trRouteController";
	
	
	
	private MessageContext ctx = AbstractInvoker.getContext();
	private ISecurityBO accessSecurity;


	/**
	 *  Gets the sOAPMessageCxt attribute of the ABaseRequestDispatcher object
	 *
	 *@return    The sOAPMessageCxt value
	 */
	public MessageContext getSOAPMessageCxt() {
		return ctx;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  request  Description of the Parameter
	 *@return          Description of the Return Value
	 */
	protected ABaseResponse doServiceAccessAuthenication(ABaseServiceRequestVO request) {
		boolean canAccessService = true;
		
		try {
			LoginCredentialVO login = createLoginCredential(request.getUserId(), request.getPassword());
			canAccessService = getAccessSecurity().authenticate(login);
		} catch (BusinessServiceException e) {
			return getFailureResponse();
		} catch (CommonServiceException svx) {
			return getFailureResponse();
		}
		
		if (canAccessService) {
			return getPassResponse();
		} else {
			return getFailureResponse();
		}
		
	}


	/**
	 *  Description of the Method
	 *
	 *@param  userName                    Description of the Parameter
	 *@param  password                    Description of the Parameter
	 *@return                             Description of the Return Value
	 *@exception  CommonServiceException  Description of the Exception
	 */
	protected LoginCredentialVO createLoginCredential(String userName, String password)
			 throws CommonServiceException {
		if (userName != null && !userName.equals("") &&
				password != null && !password.equals("")) {
			LoginCredentialVO vo = new LoginCredentialVO(userName.trim(), password.trim());
			return vo;
		} else {
			throw new CommonServiceException(null, "username or password incorrect");
		}
	}
	
	public BuyerSOTemplateDTO getBuyerSoTemplate(Integer buyerId, String templateName) {
		final String key = "BuyerSoTemplate_buyerId_" + buyerId + "_tname_" + templateName;
		BuyerSOTemplateDTO template = (BuyerSOTemplateDTO) SimpleCache.getInstance().get(key);
		if (template == null) {
			IBuyerSOTemplateBO templateBO = (IBuyerSOTemplateBO) getBusinessBeanFacility(BUYER_TEMPLATE_BO);
			template = templateBO.loadBuyerSOTemplate(buyerId, templateName);
			SimpleCache.getInstance().put(key, template, SimpleCache.FIVE_MINUTES);
		}
		return template;
	}

	protected SecurityContext createSecurityContext(String username,String password){
		SecurityContext securityContext = new SecurityContext(username);
		LoginCredentialVO lvo = new LoginCredentialVO();
		lvo.setUsername(username);
		lvo.setPassword(password);
		//getAccessSecurity().getActivitiesByUserName(username);
		LoginCredentialVO lcvo = getAccessSecurity().getUserRoles(lvo);
		
		securityContext.setRoles(lcvo);
		securityContext.setRoleId(lcvo.getRoleId());
		securityContext.setRole(lcvo.roleName);
		
		switch (lcvo.getRoleId().intValue()) {
		case OrderConstants.BUYER_ROLEID:
			loadBuyerDetails(securityContext);
			break;
		case OrderConstants.PROVIDER_ROLEID:
			loadProviderDetails(securityContext);
			break;
		default:
			break;
		}
		
		return securityContext;
	}

	private void loadBuyerDetails(SecurityContext securityContext){
		Map<String,?> buyer = getAccessSecurity().getBuyerId(securityContext.getUsername());
		if(buyer != null && buyer.get("buyerId") != null){
			securityContext.setCompanyId(((Long)buyer.get("buyerId")).intValue());
		}
		if(buyer != null && buyer.get("buyerResourceId") != null){
			securityContext.setVendBuyerResId(((Long)buyer.get("buyerResourceId")).intValue());
		}
		if (buyer != null && buyer.get("clientId") != null) {
			Integer clientId = (Integer)buyer.get("clientId");
			securityContext.setClientId(clientId);
		}
	}
	
	private void loadProviderDetails(SecurityContext securityContext){
		
		Map<String,Long> vendor = getAccessSecurity().getVendorId(securityContext.getUsername());		
		
		if(vendor != null && vendor.get("vendorId") != null){
			//FIXME WHY WHY WHY WHY!
			securityContext.setCompanyId(vendor.get("vendorId").intValue());
		}
		
		if(vendor != null && vendor.get("vendorResourceId") != null){
			//FIXME WHY WHY WHY WHY!
			securityContext.setVendBuyerResId(vendor.get("vendorResourceId").intValue());	
		}
	}


	/**
	 *  Gets the accessSecurity attribute of the ABaseRequestDispatcher object
	 *
	 *@return    The accessSecurity value
	 */
	public ISecurityBO getAccessSecurity() {
		ApplicationContext appCtx = MPSpringLoaderPlugIn.getCtx();
		accessSecurity = (ISecurityBO) appCtx.getBean("securityBO");
		return accessSecurity;
	}


	/**
	 *  Sets the accessSecurity attribute of the ABaseRequestDispatcher object
	 *
	 *@param  accessSecurity  The new accessSecurity value
	 */
	public void setAccessSecurity(ISecurityBO accessSecurity) {
		this.accessSecurity = accessSecurity;
	}


	/**
	 *  Gets the failureResponse attribute of the ABaseRequestDispatcher object
	 *
	 *@return    The failureResponse value
	 */
	protected ABaseResponse getFailureResponse() {
		SecurityResponse secure = new SecurityResponse();
		secure.setCode(ServiceConstants.USER_ERROR_RC);
		return secure;
	}


	/**
	 *  Gets the passResponse attribute of the ABaseRequestDispatcher object
	 *
	 *@return    The passResponse value
	 */
	protected ABaseResponse getPassResponse() {
		SecurityResponse secure = new SecurityResponse();
		secure.setCode(ServiceConstants.VALID_RC);
		return secure;
	}
	
	
	protected ABaseResponse getCommonSystemErrorResponse() {
		CommonSystemErrorResponse secure = new CommonSystemErrorResponse();
		secure.setCode(ServiceConstants.SYSTEM_ERROR_RC);
		return secure;
	}
	
	/**
	 *  Gets the orderBusinessFacility attribute of the OrderDispatchRequestor
	 *  object
	 *
	 *@return    The orderBusinessFacility value
	 */
	protected Object getBusinessBeanFacility(String beanName ) {
		Object beanFacility = null;
				try {
					beanFacility = MPSpringLoaderPlugIn.getCtx().getBean( beanName );
				} catch (BeansException e) {
					e.printStackTrace();
				}
		return beanFacility;
	}
		
	/**
	 *  Loads the securityContext based on the user for PublicAPI
	 * @param username String
	 *@param password String
	 *@return SecurityContext
	 */

	protected SecurityContext createAPISecurityContext(String username,String password){
		SecurityContext securityContext = new SecurityContext(username);
		LoginCredentialVO lvo = new LoginCredentialVO();
		if (StringUtils.isBlank(username))
			return null;
		
		lvo.setUsername(username);
		lvo.setPassword(password);
		
		LoginCredentialVO lcvo = getAccessSecurity().getUserRoles(lvo);
		
		securityContext.setRoles(lcvo);
		securityContext.setRoleId(lcvo.getRoleId());
		securityContext.setRole(lcvo.roleName);
		
		/*--SL-15642 Begin--
		 *To set Role Activity (only for view order pricing) to Security Context for API 
		 */
		List<UserActivityVO> activityList = getAccessSecurity().getUserActivityRolesList(username);
		for (UserActivityVO userActivityVO : activityList) {
			int activityId = Integer.valueOf(userActivityVO.getActivityId());
			if (activityId == 59) {
				Map<String, UserActivityVO> activityMap = new HashMap<String, UserActivityVO>();
				activityMap.put(String.valueOf(activityId), userActivityVO);
				securityContext.setRoleActivityIdList(activityMap);
				break;
			}
		}
		/*--END SL-15642--*/
		
		switch (lcvo.getRoleId().intValue()) {
		case OrderConstants.BUYER_ROLEID:
			loadBuyerDetails(securityContext);
			break;
		case OrderConstants.SIMPLE_BUYER_ROLEID:
			loadBuyerDetails(securityContext);
			break;
		case OrderConstants.PROVIDER_ROLEID:
			loadProviderDetails(securityContext);
			break;
		default:
			break;
}

		return securityContext;
	}
}

