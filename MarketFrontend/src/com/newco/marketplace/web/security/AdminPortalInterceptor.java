/**
 * 
 */
package com.newco.marketplace.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.web.constants.SOConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author hoza
 *
 */
public class AdminPortalInterceptor extends AbstractInterceptor {
	
	private static final Logger log = Logger.getLogger(AdminPortalInterceptor.class);

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		final ActionContext context = invocation.getInvocationContext();
		
		final HttpServletRequest request = (HttpServletRequest) context
				.get(StrutsStatics.HTTP_REQUEST);
		final HttpSession session =  request.getSession (true);
		
		//Object isLoggedIn = session.getAttribute (SOConstants.IS_LOGGED_IN);
		Object securityContextObj = session.getAttribute (SOConstants.SECURITY_KEY);
		
		Class<? extends Object> actionClass = invocation.getAction().getClass();
		log.debug("Action class is: " + actionClass.getName());
		SecurityContext securityContext = null;
		if (actionClass.isAnnotationPresent(AdminPageAction.class)) {
				if(securityContextObj != null) {
					//Is there possibiliyt of class cast .. hell who knows
					securityContext = (SecurityContext) securityContextObj;
					
					if(!securityContext.isSlAdminInd()){
						log.warn("User " + securityContext.getUsername() + " with Id = "+ securityContext.getVendBuyerResId() + "tried to access resources which are not allowed for theeri role");
						return "ADMIN_SECURITY_NOT_AVAILABLE";
					}
				}
		}
		return invocation.invoke();
	}

}
