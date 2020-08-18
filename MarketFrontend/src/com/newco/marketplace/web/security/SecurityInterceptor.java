package com.newco.marketplace.web.security;

import java.lang.reflect.Method;
import java.net.URI;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;


import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.web.action.nonSecureRedirect.NonSecureRedirectAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SecurityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(SecurityInterceptor.class);
	private static String httpPort = null;
	private static String httpsPort = null;
	private static boolean isRedirectorEnabled = false;
	private Cryptography cryptography;

	static {
		httpPort = System.getProperty("http.port");
		httpsPort = System.getProperty("https.port");
		isRedirectorEnabled = new Boolean(System
				.getProperty("security.redirector.enabled"));
	}
	final static String HTTP_GET = "get";
	final static String HTTP_POST = "post";
	final static String SCHEME_HTTP = "http";
	final static String SCHEME_HTTPS = "https";

	public SecurityInterceptor() {
		super();
		log.info("Initializing ServiceLive SecurityInterceptor. httpPort: "
				+ httpPort + ", httpsPort: " + httpsPort
				+ ", isRedirectorEnabled: " + isRedirectorEnabled);
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		boolean nonSecureRedirect = false;
		StringBuilder queryString = new StringBuilder();
		// initialize request and response
		final ActionContext context = invocation.getInvocationContext();
		final HttpServletRequest request = (HttpServletRequest) context
				.get(StrutsStatics.HTTP_REQUEST);
		final HttpServletResponse response = (HttpServletResponse) context
				.get(StrutsStatics.HTTP_RESPONSE);

		// check scheme
		String scheme = request.getScheme().toLowerCase();

		// check method
		String method = request.getMethod().toLowerCase();
		SecurityChecker sc = new SecurityChecker();
		
		if (isRedirectorEnabled) {
			Class<? extends Object> actionClass = invocation.getAction()
					.getClass();
			//start
			if (actionClass.isAnnotationPresent(NonSecurePage.class)) {
				if ((HTTP_GET.equals(method) || HTTP_POST.equals(method))
						&& SCHEME_HTTPS.equals(scheme)) {
					URI uri = new URI(
							SCHEME_HTTP,
							null,
							request.getServerName(),
							Integer.parseInt(httpPort),
							response.encodeRedirectURL(request.getRequestURI()),
							request.getQueryString(), null);
					
					SecurityContext secContext = (SecurityContext) request.getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
					String sessionId = null;
					if (null != secContext){
						sessionId = request.getSession().getId();
						NonSecureRedirectAction.putToCache(sessionId, secContext);
					}
					nonSecureRedirect = true;
					queryString = queryString.append(sessionId).append("$").append("nonSecureRedirect=").append(nonSecureRedirect).append("&uri=").append(uri.toString());
					URI tempUri = new URI(
							SCHEME_HTTP,
							null,
							request.getServerName(),
							Integer.parseInt(httpPort),
							response.encodeRedirectURL("/MarketFrontend/nonSecureAction_redirectRequest.action"),
							queryString.toString(), null);

					String redirectPath= sc.securityCheck(tempUri.toString());
					response.sendRedirect(redirectPath);
					return null;
				}
			//}
			} 
			//End
			// Otherwise, check to see if we need to redirect to the SSL version
			// of this page
			else {

				if ((HTTP_GET.equals(method) || HTTP_POST.equals(method))
						&& SCHEME_HTTP.equals(scheme)) {

					URI uri = new URI(
							SCHEME_HTTPS,
							null,
							request.getServerName(),
							Integer.parseInt(httpsPort),
							response.encodeRedirectURL(request.getRequestURI()),
							request.getQueryString(), null);
			
					String redirectPath= sc.securityCheck(uri.toString());
					response.sendRedirect(redirectPath);
					return null;
				}

			}
		}

        // protection against CSRF
        String methodName = invocation.getProxy().getMethod();
        Method actionMethod = null;
        try {
        	actionMethod = invocation.getAction().getClass().getMethod(methodName, (Class[]) null);
        }catch(NoSuchMethodException e) { 
            actionMethod = invocation.getAction().getClass().getMethod("do"+StringUtils.capitalize(methodName), (Class[]) null);
        }
        
        SecuredAction methodAnnotation = actionMethod.getAnnotation(SecuredAction.class);
        if (methodAnnotation != null && methodAnnotation.securityTokenEnabled()) {
			String securityToken = (String) invocation.getInvocationContext().getSession().get(SOConstants.SECURITY_TOKEN_SESSION_KEY);
			String reqSecurityToken;
			try {
				reqSecurityToken = ((String[]) invocation.getInvocationContext().getParameters().get("ss"))[0];
				if(!securityToken.equalsIgnoreCase(reqSecurityToken)){
					int val = reqSecurityToken.indexOf("?");
					reqSecurityToken = reqSecurityToken.substring(0, val);
				}
				
			} catch (Exception e) {
				reqSecurityToken = null;
			}
			if (securityToken == null || !securityToken.equalsIgnoreCase(reqSecurityToken)) {
				log.info("SecurityToken in session is NULL or not matching");
			}
        }
		
		return invocation.invoke();
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}
}
