package com.newco.marketplace.api.security;

import java.io.IOException;
import java.util.ArrayList;
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


import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.newco.marketplace.business.iBusiness.api.IAPISecurity;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.dto.api.APIApplicationDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class APISecurityFilter implements Filter {

	private final String[] applicationContextLocation = {"classpath*:**/mainApplicationContext.xml"};
	private static final Log logger = LogFactory.getLog(APISecurityFilter.class);

	private ApplicationContext ctx;
	private IAPISecurity apiSecurity;
	private List<String> openURLs = new ArrayList<String>();
	private Pattern openPattern[] = null;

	private static final String BUYERS = "buyers";
	private static final String PROVIDERS = "providers";
	private final String errorString = "Not enough permissions to access URL";

	/**
	 *
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		int roleId = 0;
		// override the doFilter
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (apiSecurity.isSecurityEnabled() == false) {
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
			OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
			String consumerKey = requestMessage.getConsumerKey();
			String inputPath = request.getPathInfo();

			APIApplicationDTO vo = apiSecurity.getApplication(consumerKey);
			int type = getUserType(inputPath);
			int userId = getUserId(inputPath, type);
			logger.info("type :-"+type);
			logger.info("userId :-"+userId);

			if (vo != null && vo.isInternalConsumer() == true) {
				roleId = type;
			}

			if (userId != -1) {
				boolean flag = apiSecurity.isUserExisting(userId, consumerKey, roleId);
				if (flag == false) {
					logger.info("Consumer(" + consumerKey +") is not linked to user(" + userId + ")");
					response.sendError(HttpServletResponse.SC_FORBIDDEN, errorString);
					return;
				}
			}

			boolean flag = isOperationAllowed(inputPath, consumerKey, request.getMethod());
			if (flag == false) {
				logger.info("API operation(" + inputPath +") not allowed for consumer:" + consumerKey );
				response.sendError(HttpServletResponse.SC_FORBIDDEN, errorString);
				return;
			}

			chain.doFilter(request, response);

		} catch (BusinessServiceException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, errorString);
			return;
		}
	}

	/**
	 * Initialize the filter
	 */
	public void init(FilterConfig config) throws ServletException {
		apiSecurity = (IAPISecurity)loadBeanFromContext("apiSecurityAndOAuth");

		Enumeration<?> initParamNames = config.getInitParameterNames();
		while (initParamNames.hasMoreElements()) {
			String paramName = (String) initParamNames.nextElement();
			if (paramName.startsWith("openURL")) {
				openURLs.addAll(formatUrls(config.getInitParameter(paramName)));
			}
		}

		openPattern = new Pattern[openURLs.size()];
		for (int i = 0; i < openURLs.size(); i++) {
			openPattern[i] = Pattern.compile(openURLs.get(i),
					Pattern.CASE_INSENSITIVE);
			//System.out.println("Adding Pattern:" + openPattern[i]);
		}
	}

	private ArrayList<String> formatUrls(String str) {
		ArrayList<String> openURLs = new ArrayList<String>();
		if (str != null) {
			String []arr = str.split("\\|");
			for (String url:arr) {
				if (url != null && url.length() > 0) {
					url = url.replaceAll("\\*", ".+");
					openURLs.add(url);
					//System.out.println("Adding URL:" + url);
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

	private int getUserType(String url){
		if (url == null) {
			return 0;
		}

		if (url.indexOf(BUYERS) != -1) {
			return OrderConstants.BUYER_ROLEID;
		}

		if ((url.indexOf(PROVIDERS) != -1)  &&  (url.indexOf("om")!=-1)) {
			return OrderConstants.PROVIDER_ROLEID;
		}
		
		if(url.indexOf(PROVIDERS) != -1){
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
		if(!((url.indexOf("om")!=-1) && (url.indexOf("reasoncodes")!=-1))){
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

	private  boolean isOperationAllowed(String url, String consumerKey, String httpMethod ) {
		String maskdUrl = maskUrl(url);
		APIApplicationDTO vo = apiSecurity.getApplication(consumerKey);
		if (vo == null) {
			logger.info("operation allowed:false- "+url);
			return false;
		}

		boolean flag = vo.isUrlAllowed(maskdUrl, httpMethod.toUpperCase());
		if (flag == false) {
			int len = maskdUrl.length();
			if (maskdUrl.charAt(len -1) == '/') {
				maskdUrl = maskdUrl.substring(0, len -1);
			}
			flag = vo.isUrlAllowed(maskdUrl, httpMethod.toUpperCase());
		}
		logger.info("url allowed: "+flag);

		return flag;
	}


	private String maskUrl(String url) {
		logger.info("urlgot: "+url);

		if(url.indexOf("/om/")==-1){
			url = url.replaceAll("/v[0-9]+\\.[0-9]+/", "/v1.1/");
		}

		if (url.indexOf("providers") != -1) {
			url = url.replaceAll("providers/[0-9]+/", "providers/{id}/");	

			if (url.indexOf("us") != -1) {				
				url = url.replaceAll("providers/us?.+", "providers/us/{fid}");	
			}
			if (url.indexOf("category") != -1) {				
				url = url.replaceAll("providers/category?.+", "providers/category/{fid}");
			}
		}

		if (url.indexOf("providerResource") != -1) {
			url = url.replaceAll("providerResource/[0-9]+/", "providerResource/{id}/");
		}
		
		if (url.indexOf("buyers") != -1)
			url = url.replaceAll("buyers/[0-9]+/", "buyers/{id}/");

		if (url.indexOf("buyerresources") != -1)
			url = url.replaceAll("buyerresources/[0-9]+", "buyerresources/{fid}");
		
		if(url.indexOf("resources")!=-1){
			url = url.replaceAll("resources/[0-9]+", "resources/{fid}");
		}
		
		if(url.indexOf("reasoncodes")!=-1){
			url = url.replaceAll("reasoncodes/[0-9]+", "reasoncodes/{fid}");
		}
		
		if(url.indexOf("reject")!=-1){
			url = url.replaceAll("reject/[0-9]+", "reject/{fid}");
		}

		if (url.indexOf("serviceorders") != -1) {
			//url = url.replaceAll("serviceorders/[0-9]+-[0-9]+-[0-9]+-[0-9]+", "serviceorders/{fid}");		
			
			if (url.indexOf("serviceorders/search") == -1) {
				Pattern p =  Pattern.compile("serviceorders/.+/");
				Matcher m = p.matcher(url);	

				if (m.find() == true)									
				    url = url.replaceAll("serviceorders/[0-9-_]+/", "serviceorders/{fid}/");
				else 
					url = url.replaceAll("serviceorders/.+", "serviceorders/{fid}");
			}
		}
		
		if (url.indexOf("leadId") != -1) {
			//url = url.replaceAll("serviceorders/[0-9]+-[0-9]+-[0-9]+-[0-9]+", "serviceorders/{fid}");		
			
				Pattern p =  Pattern.compile("leadId/.+/");
				Matcher m = p.matcher(url);	

				if (m.find() == true){									
				    url = url.replaceAll("leadId/[0-9-_]+/", "leadId/{fid}/");
				}
				else {
					url = url.replaceAll("leadId/.+", "leadId/{fid}");
				}
		}
		
		if (url.indexOf("firmId") != -1) {
		
				Pattern p =  Pattern.compile("firmId/.+/");
				Matcher m = p.matcher(url);	

				if (m.find() == true){									
				    url = url.replaceAll("firmId/[0-9-_]+/", "firmId/{id}/");
				}
				else {
					url = url.replaceAll("firmId/.+", "firmId/{id}");
				}
		}

		if (url.indexOf("uploads") != -1)
			url = url.replaceAll("uploads/.+", "uploads/{fid}");

		if (url.indexOf("fundingsources") != -1)
			url = url.replaceAll("fundingsources/[0-9]+", "fundingsources/{fid}");

		if (url.indexOf("attachments") != -1)
			url = url.replaceAll("attachments/.+", "attachments/{fid}");

		if (url.indexOf("funds") != -1)
			url = url.replaceAll("funds/[0-9]+", "funds/{fid}");
		logger.info("maskedUrl: "+url);
		return url;
	}
}

