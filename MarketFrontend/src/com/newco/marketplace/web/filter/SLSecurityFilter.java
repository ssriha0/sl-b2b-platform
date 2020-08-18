package com.newco.marketplace.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.filters.SecurityWrapperResponse;

import com.newco.marketplace.auth.ActionActivityVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.security.ActivityMapper;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;

/**
 * This filter class handles the authentication/authorization
 * 
 * @author rambewa
 * 
 */
public final class SLSecurityFilter extends SLBaseFilter {

	private static final Log logger = LogFactory.getLog(SLSecurityFilter.class
			.getName());
	private FilterConfig filterConfig;
	private Pattern protectedPattern[];
	private Pattern openPattern[];
	private Pattern sessionPattern[];
	
	//Fix for Sears00047776 
	//Added to include edit URL
	//It holds the pattern of URLs allowed to be entered on the browser and should be redirected 
	//to the Dashboard.
	private Pattern editURLPattern[];
	
	private List<String> protectedURLs = new ArrayList<String>();
	private List<String> openURLs = new ArrayList<String>();
	private List<String> sessionPatternURLs = new ArrayList<String>();
	
	//Added to include edit URL
	private List<String> editPatternURLs = new ArrayList<String>();

	/**
	 * Check in the following order 1.If requested resource is not protected
	 * proceed as normal else check for below 2.If not authenticated, redirect
	 * to login page 3.If not authorized, redirect to access denied
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// override the doFilter
		logger.info("Validating the request SLSecurityFilter for secutiry filter.");
		if(!isContent(((HttpServletRequest)request).getRequestURI())){
			try {
				isSessionAccessed((HttpServletRequest) request);
				resetHeader((HttpServletRequest) request,(HttpServletResponse) response);
				if (logger.isDebugEnabled())
					log((HttpServletRequest) request,
							(HttpServletResponse) response);
				if (!isResourceExcluded((HttpServletRequest) request)
						&& isResourceProtected((HttpServletRequest) request)) {
					if (!isAuthenticated((HttpServletRequest) request)) {
						inValidate((HttpServletRequest) request);
						if(isAjaxAction((HttpServletRequest) request)){
							sendToAjaxCallback((HttpServletRequest) request,
									(HttpServletResponse) response);
						}else{
							sendRedirect((HttpServletRequest) request,
							(HttpServletResponse) response,
							Constants.REDIRECT.HOMEPAGE);
						}
						return;
					}
					if (!isAuthorized((HttpServletRequest) request)) {
						sendRedirect((HttpServletRequest) request,
								(HttpServletResponse) response,
								Constants.REDIRECT.DASHBOARD);
						return;
					}
												
				}else {
					//Fix for Sears00047776 
					//If the URL entered on the browser matches the editURL Pattern then
					//they are redirected to DASHBOARD
					if (isEditAllowed((HttpServletRequest) request)){
						sendRedirect((HttpServletRequest) request,
								(HttpServletResponse) response,
								Constants.REDIRECT.DASHBOARD);
						return;
					}
						
				}
			} catch (RuntimeException e) {
				logger.error(SLSecurityFilter.class.getName()
						+ " Failed while processing this request "
						+ ((HttpServletRequest) request).getServletPath(), e);
				if (logger.isDebugEnabled()) {
					log((HttpServletRequest) request,
							(HttpServletResponse) response);
				}
				throw new ServletException(SLSecurityFilter.class.getName()
						+ "Initalization Failed.Check web.xml");
			}
		}
		
		 SecurityWrapperResponse securityWrapperResponse = new SecurityWrapperResponse((HttpServletResponse) response, "sanitize");
		    Cookie[] cookies =  ((HttpServletRequest) request).getCookies();
		    if (cookies != null) {
		        for (int i = 0; i < cookies.length; i++) {
		            Cookie cookie = cookies[i];
		            if (cookie != null) {
		                
		                if (ESAPI.securityConfiguration().getHttpSessionIdName().equals(cookie.getName())) {
		                    securityWrapperResponse.addCookie(cookie);
		                }
		            }
		        }
		    }
		    HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper((HttpServletResponse) response) {
		    	public String encodeRedirectUrl(String url) {
		    	return url;
		    	}

		    	public String encodeRedirectURL(String url) {
		    	return url;
		    	}

		    	public String encodeUrl(String url) {
		    	return url;
		    	}

		    	public String encodeURL(String url) {
		    	return url;
		    	}
		    };
		chain.doFilter(request, wrappedResponse); 
	}

	private boolean isAjaxAction(HttpServletRequest request) {
		if(Constants.AJAXACTIONS.contains(request.getServletPath())){
			return true;
		}else{
			return false;
		}
	}

	private boolean sendToAjaxCallback(HttpServletRequest request,
			HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		if (logger.isDebugEnabled())
			log((HttpServletRequest) request, (HttpServletResponse) response);
		return true;
		
	}

	/**
	 * 
	 * @param uri
	 * @return
	 */
	public boolean isContent(String uri){
		
		
		if (logger.isDebugEnabled())
		  logger.debug("===================>>> checking content for " + uri);
		
		if(uri.contains(".css")||uri.contains(".htc")){
			if (logger.isDebugEnabled())
			  logger.debug("=========================>>> IS CONTENT for " + uri);
			
			return(true);
		}
		else{
			return(false);
		}
		
	}
	
	
	/**
	 * Initialize the filter
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// set the filter configuration
		try {
			filterConfig = arg0;
			Enumeration initParamNames = filterConfig.getInitParameterNames();
			while (initParamNames.hasMoreElements()) {
				String paramName = (String) initParamNames.nextElement();
				if (paramName.startsWith("protectedURL")) {
					protectedURLs.add((String) filterConfig
							.getInitParameter(paramName));
				} else if (paramName.startsWith("openURL")) {
					openURLs.add((String) filterConfig
							.getInitParameter(paramName));
				} else if (paramName.startsWith("sessionPatternURL")) {
					sessionPatternURLs.add((String) filterConfig
							.getInitParameter(paramName));
				} else if (paramName.startsWith("editURL")) {
					editPatternURLs.add((String) filterConfig
							.getInitParameter(paramName));
				}
			}
			sessionPattern=new Pattern[sessionPatternURLs.size()];
			for (int i = 0; i < sessionPatternURLs.size(); i++) {
				sessionPattern[i] = Pattern.compile(sessionPatternURLs.get(i),
						Pattern.CASE_INSENSITIVE);
			}			
			protectedPattern = new Pattern[protectedURLs.size()];
			for (int i = 0; i < protectedURLs.size(); i++) {
				protectedPattern[i] = Pattern.compile(protectedURLs.get(i),
						Pattern.CASE_INSENSITIVE);
			}
			openPattern = new Pattern[openURLs.size()];
			for (int i = 0; i < openURLs.size(); i++) {
				openPattern[i] = Pattern.compile(openURLs.get(i),
						Pattern.CASE_INSENSITIVE);
			}
			
			editURLPattern = new Pattern[editPatternURLs.size()];
			for (int i = 0; i < editPatternURLs.size(); i++) {
				editURLPattern[i] = Pattern.compile(editPatternURLs.get(i),
						Pattern.CASE_INSENSITIVE);
			}
			
		} catch (RuntimeException e) {
			// Catch any configuration exceptions
			logger.error(SLSecurityFilter.class.getName()
					+ " Initalization Failed.Check web.xml", e);
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			throw new ServletException(SLSecurityFilter.class.getName()
					+ "Initalization Failed.Check web.xml");
		}
	}

	/**
	 * Release any resources
	 */
	public void destroy() {
		// release any resources
		this.filterConfig = null;
		this.protectedPattern = null;
		this.protectedURLs = null;
	}

	/**
	 * Returns true if the user is already authenticated
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAuthenticated(HttpServletRequest request) {
	
		if (request.isRequestedSessionIdValid()) {

			HttpSession session = request.getSession(false);
			if (session != null) {

				SecurityContext securityCntxt = (SecurityContext) session
						.getAttribute(Constants.SESSION.SECURITY_CONTEXT);
				String sPath = request.getServletPath();
				if (securityCntxt != null){
					logger.info("isAuthenticated; securityCntxt is :"+securityCntxt.toString());
				}
				logger.info("isAuthenticated; sPath is :"+sPath);
				if (securityCntxt != null || StringUtils.contains(sPath,"login.jsp") || StringUtils.contains(sPath,"loginAction")) {

					return true;
				//check if its a reset password flow.	
				} else if (request.getSession().getAttribute(OrderConstants.DEEPLINK_PASSCODE) != null){

					return true;
				}
			}
		} else {
			/*
			HttpSession session = request.getSession(false);
			if (session != null && (session.getAttribute(OrderConstants.DEEPLINK_PASSCODE) != null))
				return true;*/
			//start
			HttpSession session = request.getSession(false);
			if (session != null && (session.getAttribute(OrderConstants.DEEPLINK_PASSCODE) != null)){
				return true;
			} 
			if (null != request.getQueryString()){
				if (request.getQueryString().contains("nonSecureRedirect=true")){
					return true;
				}
			}
			//end
			
		}
		return false;
	}


	/**
	 * Checks for authorization based on resource and
	 * user access matrix
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAuthorized(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {

			SecurityContext securityCntxt = (SecurityContext) session
					.getAttribute(Constants.SESSION.SECURITY_CONTEXT);
			if (securityCntxt != null) {
				// Check if you need to check some more authentication
				// data
				/**
				 * Here we are going to load a map of actions associated with
				 * user_activities They will then be checked for validity and
				 * redirected to the dashboardAction if they are invalid. It
				 * would also be nice to add a message to the dashboad
				 * indicating that it is unauthorized.
				 */
				// actionPermissionMap = (HashMap<String, UserActivityVO>)
				// securityCntxt.getRoleActivityIdList();
				

				String requestURI = request.getRequestURI();

				String strAction = requestURI.substring(requestURI
						.lastIndexOf("/") + 1, requestURI.indexOf("."));

			    StringTokenizer atokenizer = new StringTokenizer(strAction,"_");
			    
			    strAction = atokenizer.nextToken();
			    
				ActionActivityVO theAction = ActivityMapper.getActionActivityVO(strAction+"-"+securityCntxt.getRoleId());

				if (theAction != null) {
					
					//logger.info(" I am looking at activity "
							//+ theAction.getActionName() + ":"
							//+ theAction.getActivityId());
					if (theAction.getActivityId() > 0) {
						//logger.info(" I am securing activity" + theAction);

						if (securityCntxt.getRoleActivityIdList().containsKey(
								theAction.getActivityId() + "")) {

							//logger.info(" we have an entry for activity"
								//	+ theAction);
							if (securityCntxt.getRoleActivityIdList().get(
									theAction.getActivityId() + "").isChecked()) {
								//logger.info(" all is well in security thing");
								return true;
							} else {
								//logger
									//	.info(" all is NOT well in security thing");
								return false;
							}
						} else {
							return false;
						}

					}
				}

				/**
				 * if the hash map is null load it ( interface to a BO I guess
				 * or a DAO )
				 * 
				 * 
				 * String theAAction.string (
				 * http://whatever:8080/MarketFrontEnd/aplace/somewhere/theAction.action?aparameter=thing ) =
				 * theAction
				 * 
				 * If UserActivityVO = actionPermissionMap.get(theAction)!=
				 * null{ if
				 * (securityContext.getPermissions().get(UserActivityVO.getActivityid()) !=
				 * null ) all is well else redirect to dashboardAction
				 * 
				 */

				return true;
			//check if its a reset password flow.	
			}else if (request.getSession().getAttribute(OrderConstants.DEEPLINK_PASSCODE) != null || StringUtils.contains(request.getServletPath(),"login.jsp")|| StringUtils.contains(request.getServletPath(),"loginAction")){
				return true;
			}
		}else{
			if (null != request.getQueryString()){
				if (request.getQueryString().contains("nonSecureRedirect=true")){
					logger.info("security filter - isAuthorized 1");
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns True if the requested resource is protected
	 * 
	 * @param request
	 * @return
	 */
	private boolean isResourceProtected(HttpServletRequest request) {
		String inputPath = request.getServletPath();
		Matcher matcher = null;
		for (int i = 0; i < protectedPattern.length; i++) {
			matcher = protectedPattern[i].matcher(inputPath);
			if (matcher.matches())
				return true;
			else
				matcher.reset();
		}
		return false;
	}

	/**
	 * Returns True if the requested resource is public
	 * 
	 * @param request
	 * @return
	 */
	private boolean isResourceExcluded(HttpServletRequest request) {
		String inputPath = request.getServletPath();
		Matcher matcher = null;
		for (int i = 0; i < openPattern.length; i++) {
			matcher = openPattern[i].matcher(inputPath);
			if (matcher.matches())
				return true;
			else
				matcher.reset();

		}
		return false;
	}
	
	/**
	 * Returns true if the URL entered on the browser which should be redirected to the DASHBOARD.
	 * If the URL matches the pattern + session is active + security context is not null then the function
	 * returns TRUE.
	 * @param request
	 * @return
	 */
	private boolean isEditAllowed(HttpServletRequest request) {
		//Fix for Sears00047776
		String inputPath = request.getServletPath();
		Matcher matcher = null;
		for (int i = 0; i < editURLPattern.length; i++) {
			matcher = editURLPattern[i].matcher(inputPath);
			if (matcher.matches())
			{	
				HttpSession session = request.getSession(false);
				if (session != null)
				{
					//Check if request coming from forgot user name link,even if previous user is logged in, redirect to the login page. 
					if(StringUtils.isNotEmpty((String)request.getSession().getAttribute(OrderConstants.DEEPLINK_USERNAME))){
						return false;
					}
					SecurityContext securityContext = (SecurityContext)session.getAttribute(Constants.SESSION.SECURITY_CONTEXT);
					if (securityContext != null)
						return true;
				}	
			}	
			else
				matcher.reset();
		}
		return false;
	}
	/**
	 * Redirects the user to Login/Session Timeout/Home Page
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean sendRedirect(HttpServletRequest request,
			HttpServletResponse response, String redirect)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath()+"/"+redirect);
		response.flushBuffer();
		if (logger.isDebugEnabled())
			log((HttpServletRequest) request, (HttpServletResponse) response);
		return true;
	}

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	private void isSessionAccessed(HttpServletRequest request) {

		String inputPath = request.getServletPath();
		Matcher matcher = null;
		HttpSession session = request.getSession(false);
		
		if (session==null) {
			if (StringUtils.isNotEmpty(request.getParameter("key")) || StringUtils.isNotEmpty(request.getParameter("uname"))){ //Forgot password flow
				session = request.getSession(true);				
			} else {
				return; 
			}
		}
			
		Long access=(Long)session.getAttribute(OrderConstants.SERVICELIVESESSIONTIMEOUT);
		
		//Retrieve passcode or user name from the URL if the URL is from reset/forgot password or user name email. 
		if(StringUtils.isNotEmpty(request.getParameter("key"))){ //Forgot password flow
			request.getSession().setAttribute(OrderConstants.DEEPLINK_PASSCODE, (String)request.getParameter("key"));
		}else if (StringUtils.isNotEmpty(request.getParameter("uname"))){ //Forgot user name flow
			request.getSession().setAttribute(OrderConstants.DEEPLINK_USERNAME, (String)request.getParameter("uname"));
		//Clear the passcode and user name from the session, if it exists in the session.	
		}
		
		if(access==null){
			return;
		}

		if(System.currentTimeMillis() - access.longValue()>OrderConstants.SERVICELIVESESSIONTIMEINTERVAL){

			session.invalidate();
			return;
		}
		for (int i = 0; i < sessionPattern.length; i++) {
			matcher = sessionPattern[i].matcher(inputPath);
			if (matcher.matches()){
				return;
			}
			else
				matcher.reset();

		}
		session.setAttribute(OrderConstants.SERVICELIVESESSIONTIMEOUT, new Long(System.currentTimeMillis()));
		return;
	}	
	/**
	 * Invalidates the current session
	 * 
	 * @param request
	 */
	private void inValidate(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	/**
	 * Print Session info
	 * 
	 * @param request
	 * @param response
	 */
	private void log(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		logger.debug(request.getServletPath());
		logger.debug(((session = request.getSession(false)) != null) ? session
				.getId() : null);
	}
	
	private void resetHeader(HttpServletRequest request, HttpServletResponse response){
		if(isResourceProtected(request)){
			response.setHeader("Cache-Control","no-cache"); 
			response.setHeader("Cache-Control","no-store"); 
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma","no-cache");
		
		    
		}
	}
}
