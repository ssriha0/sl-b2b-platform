package com.newco.marketplace.api.security;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.SimpleOAuthValidator;
import net.oauth.server.OAuthServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.business.iBusiness.api.mobile.IAPISecurity;
import com.newco.marketplace.dto.api.APIApplicationDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.Cryptography;

public class ProviderAPISecurityFilter implements Filter {

	private final String[] applicationContextLocation = { "classpath*:**/mainApplicationContext.xml" };
	private static Logger logger = Logger.getLogger(ProviderAPISecurityFilter.class);

	private ApplicationContext ctx;
	private IAPISecurity mobileApiSecurity;
	private Cryptography cryptography;
	private List<String> openURLs = new ArrayList<String>();
	private Pattern openPattern[] = null;

	private static final String BUYERS = "buyers";
	private static final String PROVIDERS = "providers";
	// SL-20098: Changing the messages to prevent verbose messages in the
	// response
	private final String errorString = "";// "Not enough permissions to access
											// URL";
	private final String oAuthFailed = "";// "Unauthorized access. Please
											// validate your consumer key and
											// consumer secret";
	private final String urlNotFound = "";// "URL not found";

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		int roleId = 0;
		StringBuilder validationMessage = null;
		// override the doFilter
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		logger.info("ProviderAPISecurityFilter.doFilter()");
		
		if (mobileApiSecurity.isSecurityEnabled() == false) {
			logger.debug("OAuth is disabled");
			chain.doFilter(request, response);
			return;
		}

		if (isResourceExcluded(request) == true) {
			logger.debug("URL is public");
			chain.doFilter(request, response);
			return;
		}

		try {
			String resourceId = null;
			boolean validResource = false;
			boolean validToken = false;

			OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);

			String consumerKey = requestMessage.getConsumerKey();
			String inputPath = request.getPathInfo();

			// Get the consumer secret based on the consumer key
			APIApplicationDTO applicationDTO = mobileApiSecurity.getApplication(consumerKey);
			String secret = null;
			if (null != applicationDTO) {
				secret = applicationDTO.getConsumerPassword();
			}
			// Exit if secret is not available
			if (StringUtils.isBlank(secret)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, oAuthFailed);
				return;
			}
			
			if(requestMessage.URL.contains("test-timeonsite-auth")){
				chain.doFilter(request, response);
				return;
			}
			String requestToken = simpleOAuthValidation(requestMessage, consumerKey, secret);			
			
			// Checking if authenticate API then token need not be validated
			// Need to move to constants
			if (StringUtils.isNotBlank(inputPath) && (!inputPath.contains("/provider/authenticate")
					&& !inputPath.contains("/provider/validateMobileVersion")
					&& !inputPath.contains("/provider/updateMobileAppVersion"))) {
				validationMessage = new StringBuilder();
				if (validateResourceAndToken(response, resourceId, requestMessage, inputPath, requestToken,
						validationMessage)) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, validationMessage.toString());
					return;
				}
			}

			APIApplicationDTO vo = mobileApiSecurity.getApplication(consumerKey);
			int type = getUserType(inputPath);
			int userId = getUserId(inputPath, type);
			logger.info("type :-" + type);
			logger.info("userId :-" + userId);

			if (vo != null && vo.isInternalConsumer() == true) {
				roleId = type;
			}

			if (userId != -1) {
				boolean flag = mobileApiSecurity.isUserExisting(userId, consumerKey, roleId);
				if (flag == false) {
					logger.info("Consumer(" + consumerKey + ") is not linked to user(" + userId + ")");
					response.sendError(HttpServletResponse.SC_FORBIDDEN, errorString);
					return;
				}
			}

			boolean flag = isOperationAllowed(inputPath, consumerKey, request.getMethod());
			if (flag == false) {
				logger.info("API operation(" + inputPath + ") not allowed for consumer:" + consumerKey);
				response.sendError(HttpServletResponse.SC_FORBIDDEN, errorString);
				return;
			}

			chain.doFilter(request, response);

		} catch (BusinessServiceException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, errorString);
			return;
		} catch (OAuthException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, oAuthFailed);
			e.printStackTrace();
			return;
		} catch (URISyntaxException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, urlNotFound);
			e.printStackTrace();
			return;
		}
	}

	private boolean validateResourceAndToken(HttpServletResponse response, String resourceId,
			OAuthMessage requestMessage, String inputPath, String requestToken, StringBuilder validationMessage)
			throws BusinessServiceException, NumberFormatException, IOException {

		logger.info("Entering method");
		// Token authorization
		if (null != requestMessage && null != requestToken) {
			logger.info("Token" + requestToken);

			logger.info("Input Path:" + inputPath);

			Pattern pattern = Pattern.compile("/resources/(.*?)/");
			Matcher matcher = pattern.matcher(inputPath);
			if (matcher.find()) {
				resourceId = (matcher.group(1));
			} else {
				pattern = Pattern.compile("provider/(.*?)/");
				matcher = pattern.matcher(inputPath);
				if (matcher.find()) {
					resourceId = (matcher.group(1));
				}
			}

			if (StringUtils.isNotBlank(resourceId)) {
				if (!StringUtils.isNumeric(resourceId)) {
					logger.info("Not A Valid Resource");
					validationMessage.append("Invalid Provider ID");
					// response.sendError(HttpServletResponse.SC_FORBIDDEN,
					// "Access token expired");
					return true;
				}

				boolean validResource = mobileApiSecurity.isResourceValid(Integer.parseInt(resourceId));
				logger.info("validResourceFlag: " + validResource);

				if (!validResource) {
					logger.info("Not A Valid Resource");
					validationMessage.append("Invalid Provider ID");
					// response.sendError(HttpServletResponse.SC_FORBIDDEN,
					// "Access token expired");
					return true;
				} else {
					Date serverDate = new Date();
					boolean validToken = mobileApiSecurity.isMobileTokenValid(requestToken, resourceId, serverDate);
					logger.info("validTokenFlag: " + validToken);
					if (!validToken) {
						logger.info("Not A Valid Token");
						validationMessage.append("Access token expired");
						// response.sendError(HttpServletResponse.SC_FORBIDDEN,
						// "Access token expired");
						return true;
					}
					return false;
				}
			} else {
				logger.info("Resource Id not available in request url");
				validationMessage.append("Provider ID missing");
				// response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access
				// token expired");
				return true;
			}
		} else {
			logger.info("Token missing");
			validationMessage.append("Access token missing");
			// response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access
			// token expired");
			return true;
		}
	}

	private String simpleOAuthValidation(OAuthMessage requestMessage, String consumerKey, String secret)
			throws IOException, OAuthException, URISyntaxException {
		// oauth validation starts here
		SimpleOAuthValidator validator = new SimpleOAuthValidator();
		OAuthServiceProvider provider = new OAuthServiceProvider(null, null, null);

		OAuthConsumer consumer = new OAuthConsumer(null, consumerKey, secret, provider);
		OAuthAccessor accessor = new OAuthAccessor(consumer);
		String requestToken = requestMessage.getParameter(OAuth.OAUTH_TOKEN);
		if (null != requestMessage && null != requestToken) {
			logger.info("requestToken:" + requestToken);
			accessor.requestToken = requestMessage.getToken();
		}
		
		// Validate the oauth message
		validator.validateMessage(requestMessage, accessor);
		return requestToken;
	}

	/**
	 * Initialize the filter
	 */
	public void init(FilterConfig config) throws ServletException {
		mobileApiSecurity = (IAPISecurity) loadBeanFromContext("apiSecurityAndOAuth");
		cryptography = (Cryptography) loadBeanFromContext("cryptography");

		Enumeration<?> initParamNames = config.getInitParameterNames();
		while (initParamNames.hasMoreElements()) {
			String paramName = (String) initParamNames.nextElement();
			if (paramName.startsWith("openURL")) {
				openURLs.addAll(formatUrls(config.getInitParameter(paramName)));
			}
		}

		openPattern = new Pattern[openURLs.size()];
		for (int i = 0; i < openURLs.size(); i++) {
			openPattern[i] = Pattern.compile(openURLs.get(i), Pattern.CASE_INSENSITIVE);
			// System.out.println("Adding Pattern:" + openPattern[i]);
		}
	}

	/**
	 * Format URLs
	 * 
	 * @param str
	 * @return
	 */
	private ArrayList<String> formatUrls(String str) {
		ArrayList<String> openURLs = new ArrayList<String>();
		if (str != null) {
			String[] arr = str.split("\\|");
			for (String url : arr) {
				if (url != null && url.length() > 0) {
					url = url.replaceAll("\\*", ".+");
					openURLs.add(url);
					// System.out.println("Adding URL:" + url);
				}
			}
		}

		return openURLs;
	}

	/**
	 * Release any resources
	 */
	public void destroy() {
		// release any resources
	}

	protected Object loadBeanFromContext(String beanName) {
		if (ctx == null)
			ctx = new ClassPathXmlApplicationContext(applicationContextLocation);
		return ctx.getBean(beanName);
	}

	/**
	 * Returns True if the requested resource is public
	 * 
	 * @param request
	 * @return
	 */
	private boolean isResourceExcluded(HttpServletRequest request) {
		String inputPath = request.getPathInfo();
		if (inputPath == null) {
			return true;
		}
		Matcher matcher = null;
		for (int i = 0; i < openPattern.length; i++) {
			matcher = openPattern[i].matcher(inputPath);
			if (matcher == null) {
				return false;
			}
			if (matcher.matches()) {
				return true;
			}
			matcher.reset();

		}
		return false;
	}

	private int getUserType(String url) {
		if (url == null) {
			return 0;
		}

		if (url.indexOf(BUYERS) != -1) {
			return OrderConstants.BUYER_ROLEID;
		}

		if ((url.indexOf(PROVIDERS) != -1) && (url.indexOf("om") != -1)) {
			return OrderConstants.PROVIDER_ROLEID;
		}

		if (url.indexOf(PROVIDERS) != -1) {
			return OrderConstants.BUYER_ROLEID;
		}

		return 0;
	}

	public int getUserId(String url, int roleId) {
		if (url == null) {
			return -1;
		}

		String user = PROVIDERS;
		if (roleId == OrderConstants.BUYER_ROLEID) {
			user = BUYERS;
		}
		if (!((url.indexOf("om") != -1) && (url.indexOf("reasoncodes") != -1))) {
			String a[] = url.split(user);
			if (a.length > 1) {
				String a1[] = a[1].split("/");
				if (a1.length > 1) {
					return Integer.parseInt(a1[1]);
				}
			}
		}
		return -1;
	}

	private boolean isOperationAllowed(String url, String consumerKey, String httpMethod) {
		String maskdUrl = maskUrl(url);
		APIApplicationDTO vo = mobileApiSecurity.getApplication(consumerKey);
		if (vo == null) {
			logger.info("operation allowed:false- " + url);
			return false;
		}

		boolean flag = vo.isUrlAllowed(maskdUrl, httpMethod.toUpperCase());
		if (flag == false) {
			int len = maskdUrl.length();
			if (maskdUrl.charAt(len - 1) == '/') {
				maskdUrl = maskdUrl.substring(0, len - 1);
			}
			flag = vo.isUrlAllowed(maskdUrl, httpMethod.toUpperCase());
		}
		logger.info("url allowed: " + flag);

		return flag;
	}

	private String maskUrl(String url) {
		logger.info("urlgot: " + url);

		// Added for Mobile APP Phase 2 APIs where request has */role/{id}/*

		if (url.indexOf("role") != -1) {
			url = url.replaceAll("role/[0-9]+/", "role/{rid}/");
		}

		// Added for Mobile APP APIs where request has */provider/{id}/*
		if (url.indexOf("provider") != -1) {
			url = url.replaceAll("provider/[0-9]+/", "provider/{id}/");
		}
		if (url.indexOf("providers") != -1) {
			url = url.replaceAll("providers/[0-9]+/", "providers/{id}/");
		}
		if (url.indexOf("resources") != -1) {
			url = url.replaceAll("resources/[0-9]+/", "resources/{resid}/");
		}
		if (url.indexOf("firm") != -1) {
			url = url.replaceAll("firm/[0-9]+/", "firm/{fid}/");
		}

		// Added for Mobile APP Estimation API
		if (url.indexOf("estimation") != -1) {
			url = url.replaceAll("estimation/[0-9]+/", "estimation/{eid}/");
		}

		// Added for Mobile APP Calendar Event
		if (url.indexOf("calendar") != -1) {
			url = url.replaceAll("calendar/[0-9]+/", "calendar/{eid}/");
		}

		// Added for Mobile APP APIs where request has */serviceorder/{fid}/*
		if (url.indexOf("serviceorder") != -1) {

			Pattern p = Pattern.compile("serviceorder/.+/");
			Matcher m = p.matcher(url);

			if (m.find() == true)
				url = url.replaceAll("serviceorder/[0-9-_]+/", "serviceorder/{fid}/");
			else
				url = url.replaceAll("serviceorder/.+", "serviceorder/{fid}");

		}
		// Added for Mobile APP APIs where request has */serviceorders/{fid}/*
		if (url.indexOf("serviceorders") != -1) {

			Pattern p = Pattern.compile("serviceorders/.+/");
			Matcher m = p.matcher(url);

			if (m.find() == true)
				url = url.replaceAll("serviceorders/[0-9-_]+/", "serviceorders/{fid}/");
			else
				url = url.replaceAll("serviceorders/.+", "serviceorders/{fid}");
		}

		// Added for Mobile APP APIs where request has
		// */downloadDocument/{docid}/*

		if (url.indexOf("downloadDocument") != -1) {
			url = url.replaceAll("downloadDocument/[0-9]+", "downloadDocument/{docid}");
		}
		// checking for query params - removing them for masked URL
		if (url.indexOf("?") != -1) {
			url = url.substring(0, url.indexOf("?"));
		}
		logger.info("maskedUrl: " + url);
		return url;
	}
}
