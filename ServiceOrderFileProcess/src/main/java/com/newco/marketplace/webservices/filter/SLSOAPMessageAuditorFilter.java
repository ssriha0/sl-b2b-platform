package com.newco.marketplace.webservices.filter;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

import java.util.Enumeration;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>SLSOAPMessageAuditorFilter</code> is responsible for recording request/response information from the servlet api. 
 * It's mainly configured to log SOAP Messages for WebService calls.
 */

/*
 * Maintenance History
 * $Log: SLSOAPMessageAuditorFilter.java,v $
 * Revision 1.1  2008/02/05 18:17:33  pbhinga
 * Added code to capture/audit SOAP Messages (XML request & response) for the calls made to the Service Live WebServices. This would help debug the WebService code issue. Reviewed by Gordon Jackson.
 *
 */

public class SLSOAPMessageAuditorFilter implements Filter {

	private static final Logger logger = Logger.getLogger(SLSOAPMessageAuditorFilter.class);

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
     * Initializes the <code>SLSOAPMessageAuditorFilter</code>.
     * @param filterConfig the filter configuration.
     */
	public void init(FilterConfig filterConfig) {
		// intentionally blank
	}

	/**
     * Attempts to retrieve the request/response info and log it somewhere.
     * @param sreq the request.
     * @param sres the response.
     * @param filterChain the filter chain.
     * @throws java.io.IOException if an error occurs.
     * @throws javax.servlet.ServletException if an error occurs.
     */
	public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain filterChain) throws IOException, ServletException {
		boolean logFlag = logger.isDebugEnabled();		
		
		if (logFlag) {			
			NDC.push(getServletNDC(sreq, sres));
			if ((sreq instanceof HttpServletRequest) && (sres instanceof HttpServletResponse)) {
				RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) sreq);
				ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) sres);
				Throwable throwable = null;

				try {
					filterChain.doFilter(requestWrapper, responseWrapper);
				} catch (Throwable t) {
					throwable = t;
				} finally {
					logRequestResponse(requestWrapper, responseWrapper);

					if (throwable != null) {
						logger.error(throwable);
					}
				}
			}
		} else {
			filterChain.doFilter(sreq, sres);
		}
		
		if (logFlag) {
			NDC.pop();
			NDC.remove();
		}
	}

	/**
     * Tidies up the filter prior to destruction.
     */
	public void destroy() {
		// intentionally blank
	}

	/**
     * You can extend from this class and just override this method for custom request/response logic.
     * @param responseWrapper the request.
     * @param requestWrapper the response.
     */
	public void logRequestResponse(RequestWrapper requestWrapper, ResponseWrapper responseWrapper) {
		responseWrapper.finishResponse();
		
		if (logger.isDebugEnabled()) {
			String methodName = "logRequestResponse";
			logger.debug(methodName + ": Entering");

			String responseContent = null;
			StringBuilder logString = new StringBuilder();

			// Log the request
			try {				
				responseContent = new String(responseWrapper.getData());

				logString.setLength(0);
				logString.append("\n------| Request content begin |---------\n");
				logString.append("Request url=[" + requestWrapper.getRequestURL() + "]\n");
				logString.append("Request remoteAddr=[" + requestWrapper.getRemoteAddr() + "]\n");

				for (Enumeration en = requestWrapper.getHeaderNames(); en.hasMoreElements();) {
					String name = (String) en.nextElement();

					if (!name.equals("authorization") && !name.equals("cookie")) {
						logString.append("Request " + name + "=[" + requestWrapper.getHeader(name) + "]\n");
					}
				}
				logString.append("Request date time=[" + dateFormat.format(requestWrapper.getInitDate()) + "]\n");

				InputStream is = requestWrapper.getInputStream();
				byte[] contentBuffer = new byte[0];
				if (requestWrapper.getContentLength() > 0) {
					contentBuffer = new byte[requestWrapper.getContentLength()];
				}
				is.read(contentBuffer);
				String contentString = new String(contentBuffer);

				// Don't log the xmlfragment part of the request, which makes the log huge and ugly
				String xmlFragmentTag = "xmlFragment>";
				int tillXMLFragmentStartIndex = contentString.indexOf(xmlFragmentTag);
				if (tillXMLFragmentStartIndex >= 0) {
					tillXMLFragmentStartIndex+= xmlFragmentTag.length();
					String contentStringPartBeforeXMLFragment = contentString.substring(0, tillXMLFragmentStartIndex);
					int fromXMLFragmentEndIndex = contentString.indexOf(xmlFragmentTag, tillXMLFragmentStartIndex+1)+xmlFragmentTag.length();
					String contentStringPartAfterXMLFragment = contentString.substring(fromXMLFragmentEndIndex);
					contentString = contentStringPartBeforeXMLFragment + contentStringPartAfterXMLFragment;
				}

				logString.append("[");
				logString.append(contentString);
				logString.append("]");
				logString.append("\n------Request content end-----------");

				logger.debug(logString);
			} catch (Throwable t) {
				logger.error(methodName + ": Error while logging http request", t);
			}

			// Log the response
			try {
				logString.setLength(0);
				logString.append("\n------Response content begin--------\n");
				logString.append("[");
				logString.append(responseContent);
				logString.append("]");
				logString.append("\n------Response content end----------");
				logger.debug(logString);
			} catch (Throwable t) {
				logger.error(methodName + ": Error while logging http response", t);
			}

			logger.debug(methodName + ": Exiting");
		}
	}

	/**
	 * Get the request parameter values
	 * @param sreq
	 * @param sres
	 * @return
	 */
	private String getServletNDC(ServletRequest sreq, ServletResponse sres) {
		StringBuffer logInfo = new StringBuffer();
		if (sreq instanceof HttpServletRequest) {
			getRequestDetails((HttpServletRequest) sreq, logInfo);
		}
		return logInfo.toString();
	}

	/**
	 * Return values of various request parameters from HttpServletRequest into a StringBuffer for logging
	 * @param request
	 * @param stringBuffer
	 */
	public static void getRequestDetails(HttpServletRequest request, StringBuffer stringBuffer) {
		// Lets display details to the page....
		stringBuffer.append("+========================================\n");
		stringBuffer.append("REQUEST\n");

		stringBuffer.append("\tmethod=" + request.getMethod() + "\n");
		stringBuffer.append("\tauthType=" + request.getAuthType() + "\n");
		try {
			stringBuffer.append("\tcontextPath=" + request.getContextPath() + "\n");
			stringBuffer.append("\tpathInfo=" + request.getPathInfo() + "\n");
			stringBuffer.append("\tpathTranslated=" + request.getPathTranslated() + "\n");
			stringBuffer.append("\tqueryString=" + request.getQueryString() + "\n");
			stringBuffer.append("\trequestUrl=" + request.getRequestURL() + "\n");
			stringBuffer.append("\trequestUri=" + request.getRequestURI() + "\n");
			stringBuffer.append("\tservletPath=" + request.getServletPath() + "\n");
		} catch (Exception ex) {// oc4j bug?
		}
		stringBuffer.append("\tremoteUser=" + request.getRemoteUser() + "\n");
		stringBuffer.append("\tprotocol=" + request.getProtocol() + "\n");
		stringBuffer.append("\tserverName=" + request.getServerName() + "\n");
		stringBuffer.append("\tserverPort=" + request.getServerPort() + "\n");
		stringBuffer.append("\tscheme=" + request.getScheme() + "\n");
		stringBuffer.append("\tremoteHost=" + request.getRemoteHost() + "\n");
		stringBuffer.append("\tremoteAddr=" + request.getRemoteAddr() + "\n");
		stringBuffer.append("\tcharacterEncoding=" + request.getCharacterEncoding() + "\n");
		stringBuffer.append("\tcontentLength=" + request.getContentLength() + "\n");
		stringBuffer.append("\tcontentType=" + request.getContentType() + "\n");
		stringBuffer.append("\tcookies=" + request.getCookies() + "\n");
		/* Methods Below require the addition of J2EE 1.4 */
		// stringBuffer.append("\tlocalAddr=" + request.getLocalAddr() + "\n");
		// stringBuffer.append("\tlocalName=" + request.getLocalName() + "\n");
		// stringBuffer.append("\tlocalPort=" + request.getLocalPort() + "\n");
		// stringBuffer.append("\tremotePort=" + request.getRemotePort() + "\n");

		Enumeration headerNames = request.getHeaderNames();
		stringBuffer.append("\n+========================================\n");
		stringBuffer.append("REQUEST HEADERS\n");
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			if (headerName.toUpperCase().indexOf("AUTHORIZATION") < 0) {
				stringBuffer.append("\t" + headerName + "=" + request.getHeader(headerName) + "\n");
			}
		}

		Enumeration parameterNames = request.getParameterNames();
		stringBuffer.append("+========================================\n");
		stringBuffer.append("REQUEST PARAMETERS argument size=" + request.getParameterMap().size() + "\n");
		while (parameterNames.hasMoreElements()) {
			String parameterName = (String) parameterNames.nextElement();
			// Dont log passwords
			if (parameterName.toUpperCase().indexOf("PASS") < 0) {
				stringBuffer.append("\t" + parameterName + "=" + request.getParameter(parameterName) + "\n");
			}
		}

		Enumeration attributeNames = request.getAttributeNames();
		stringBuffer.append("+========================================\n");
		stringBuffer.append("REQUEST ATTRIBUTES\n");
		while (attributeNames.hasMoreElements()) {
			String attributeName = (String) attributeNames.nextElement();
			stringBuffer.append("\t" + attributeName + "=" + request.getAttribute(attributeName) + "\n");
		}

		if (request.getSession() != null) {
			Enumeration sessionAttributeNames = request.getSession().getAttributeNames();
			stringBuffer.append("+========================================\n");
			stringBuffer.append("SESSION ATTRIBUTES\n");
			while (sessionAttributeNames.hasMoreElements()) {
				String sessionAttributeName = (String) sessionAttributeNames.nextElement();
				try {
					Object sessionAttribute = request.getSession().getAttribute(sessionAttributeName);
					// try and see what's in the session
					stringBuffer.append("\t" + sessionAttributeName + "=" + sessionAttribute + "\n");
				} catch (Throwable t) {
					stringBuffer.append("\t" + sessionAttributeName + "=" + t.getMessage() + "\n");
				}
			}
		}
	}

}
