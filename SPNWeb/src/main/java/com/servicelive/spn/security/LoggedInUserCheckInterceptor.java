/**
 * 
 */
package com.servicelive.spn.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.servicelive.spn.services.buyer.BuyerServices;

/**
 * @author hoza
 *
 */
public class LoggedInUserCheckInterceptor extends AbstractInterceptor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BuyerServices buyerServices;
	static final String USER_OBJECT_IN_SESSION = "USER_OBJECT_IN_SESSION";
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// initialize request and response
		
		final ActionContext context = invocation.getInvocationContext();
		final HttpServletRequest request = (HttpServletRequest) context
				.get((String)StrutsStatics.HTTP_REQUEST);
		//final HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);
		final HttpSession session =  request.getSession (true);
		
		Object user = session.getAttribute (USER_OBJECT_IN_SESSION);
		//Object principal = request.getUserPrincipal();
		if(user == null ) {
			return "NOT_LOGGED_IN";
		}
		
		/*//If principal is not vailable  HOLD.. move to Login Page with App attribute as SPN
		if(principal == null ) {
			return "NOT_LOGGED_IN";
		}
		//If the principal is Available but the user is not available, create user in the session and move on
		else if(principal != null && user == null) {
			Principal p =  (Principal) principal;
			try {
				boolean result = buildUserObject(session,p.getName());
				if(result == false ) return "NOT_LOGGED_IN";
			} catch (Exception e) {
				
				e.printStackTrace();
				return "NOT_LOGGED_IN";
			}
			
			return invocation.invoke();
		}*/
		
		return invocation.invoke();
	}
	
	
/*	private boolean buildUserObject(HttpSession session , String principal) throws Exception {
		Buyer buyer = getBuyerServices().getBuyerForUserName(principal);
		if (buyer == null)
		{

			return false;
		}
		buyer.getUser().setUserId(buyer.getBuyerId());
		session.setAttribute(USER_OBJECT_IN_SESSION, buyer.getUser());
		return true;
	}*/
	


	/**
	 * @return the buyerServices
	 */
	public BuyerServices getBuyerServices() {
		return buyerServices;
	}


	/**
	 * @param buyerServices the buyerServices to set
	 */
	public void setBuyerServices(BuyerServices buyerServices) {
		this.buyerServices = buyerServices;
	}


}
