package com.servicelive.routingrulesweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import com.newco.marketplace.auth.SecurityContext;

import com.servicelive.routingrulesengine.services.RoutingRulesPaginationService;
import com.servicelive.routingrulesengine.services.RoutingRulesService;


/**
 * 
 *
 */
public abstract class RoutingRulesBaseAction
	extends ActionSupport
{	
	public static final String ERR_JSON_GENERATE = "Unable to generate JSON";
	public static final String S_JSON_RESULT = "jsonResult";
	public static final String S_NULL = "null";
	
	private static final long serialVersionUID = 20090820L;

	private static final String SECURITY_KEY = "SecurityContext";

	private RoutingRulesService routingRulesService;
	private RoutingRulesPaginationService routingRulesPaginationService;
	protected final Logger log = Logger.getLogger(this.getClass());
	
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/** @return First/Last name of currently logged in user, or null */
	public String getLoggedInName() {
		SecurityContext securityContext = getSecurityContext();
		if(securityContext == null) {
			log.warn("security context is NULL");
			return "UNKNOWN";
		}

		return securityContext.getUsername();
	}

	/**
	 * Get buyer ID for currently logged in user, via Common Criteria's SecurityContext's companyId.
	 * @return Buyer ID, or null.
	 */
	public Integer getContextBuyerId() {
		SecurityContext securityContext = getSecurityContext();
		if (securityContext == null) {
			log.warn("security context is NULL");
			return null;
		}
		return securityContext.getCompanyId();
	}

	protected SecurityContext getSecurityContext() {
		HttpSession session = getSession();
		return (SecurityContext) session.getAttribute(SECURITY_KEY);
	}

	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * @return the routingRulesService
	 */
	public RoutingRulesService getRoutingRulesService()
	{
		return routingRulesService;
	}


	/**
	 * @param routingRulesService
	 *            the routingRulesService to set
	 */
	public void setRoutingRulesService(RoutingRulesService routingRulesService)
	{
		this.routingRulesService = routingRulesService;
	}

	public RoutingRulesPaginationService getRoutingRulesPaginationService() {
		return routingRulesPaginationService;
}

	public void setRoutingRulesPaginationService(
			RoutingRulesPaginationService routingRulesPaginationService) {
		this.routingRulesPaginationService = routingRulesPaginationService;
	}

}
