/**
 * 
 */
package com.newco.marketplace.web.action.nonSecureRedirect;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.security.NonSecurePage;

/**
 * This is the action class responsible for redirecting non secure redirects to the correct action.
 */
@NonSecurePage
public class NonSecureRedirectAction extends SLBaseAction {

	static HashMap<String,SecurityContext> sessionMap = new HashMap<String,SecurityContext>();
	
	public static void putToCache(String key,SecurityContext value ) {
		sessionMap.put(key, value);
	}
	
	public static SecurityContext getFromCache(String key) {
		SecurityContext secContext = sessionMap.get(key);
		return secContext;		
	}
	
	public static void deleteFromCache(String key) {
		sessionMap.remove(key);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private static final Logger logger = Logger.getLogger(NonSecureRedirectAction.class);
	
	private String resultUrl = null;
	
	public String redirectRequest() throws Exception {
		logger.info("inside redirect request method");
		
		String queryString = getRequest().getQueryString();
		int idx1 = queryString.indexOf("$");
		String sessionId = queryString.substring(0,idx1);
		logger.info("sessionId in NonSecureRedirectAction "+sessionId);
		SecurityContext secContext = getFromCache(sessionId);
		getRequest().getSession().setAttribute(Constants.SESSION.SECURITY_CONTEXT, secContext);
		queryString =queryString.substring(idx1+1);
		logger.info("queryString ="+queryString);
		resultUrl = queryString.substring(27);
		deleteFromCache(sessionId);
		logger.info("resultURL ="+resultUrl);
		return SUCCESS;
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

}
