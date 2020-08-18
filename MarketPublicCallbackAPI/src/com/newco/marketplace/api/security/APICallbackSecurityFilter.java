package com.newco.marketplace.api.security;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.business.iBusiness.IAPISecurityBO;
import com.newco.marketplace.interfaces.EventCallbackConstants;
import com.newco.marketplace.interfaces.SecurityConstants;





public class APICallbackSecurityFilter implements Filter{

	private final String[] applicationContextLocation = { "classpath*:**/mainApplicationContext.xml" };
	private static final Log logger = LogFactory.getLog(APICallbackSecurityFilter.class);	
	private ApplicationContext ctx;
	private IAPISecurityBO apiSecurityBO;
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		String authorization = "";
		StringTokenizer authTokenizer = null;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		RequestWrapper wrappedRequest = new RequestWrapper(request);
		request = wrappedRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String authHeader = request.getHeader(SecurityConstants.AUTHENTICATION_HEADER);
		
		if (StringUtils.isNotBlank(authHeader)) {
			authTokenizer = new StringTokenizer(authHeader, SecurityConstants.SPACE);
			authorization = authTokenizer.nextToken();
			// If basic http authentication
			if (SecurityConstants.BASIC.equalsIgnoreCase(authorization)) {			
				try {
					String authData = authTokenizer.nextToken();
					String buyerID = "";
					String key = "";
					buyerID = EventCallbackConstants.INHOME_BUYER_ID;
					key = apiSecurityBO.validateAuth(authData, buyerID);
					if (!(StringUtils.isNotBlank(authData) && authData.equals(key))) {
						logger.info("Authentication Failed");
						response.sendError(HttpServletResponse.SC_FORBIDDEN, SecurityConstants.BASICAUTHERROR);
						return;
					}
				} catch (IOException e) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, SecurityConstants.AUTHERRORSTRING);
					e.printStackTrace();
					return;
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, SecurityConstants.AUTHERRORSTRING);
					e.printStackTrace();
					return;
				}			
				chain.doFilter(request, response);
			}
			else{
				response.sendError(HttpServletResponse.SC_FORBIDDEN, SecurityConstants.AUTHERRORSTRING);
				return;
			}
		}
		else{
			response.sendError(HttpServletResponse.SC_FORBIDDEN, SecurityConstants.AUTHERRORSTRING);
			return;
		}
		
	}
	
	public void init(FilterConfig config) throws ServletException {
		apiSecurityBO = (IAPISecurityBO) loadBeanFromContext("apiSecurity");
		Enumeration<?> initParamNames = config.getInitParameterNames();
	}

	public void destroy() {
		// release any resources
	}

	protected Object loadBeanFromContext(String beanName) {
		if (ctx == null)
			ctx = new ClassPathXmlApplicationContext(applicationContextLocation);
		return ctx.getBean(beanName);
	}

	class RequestWrapper extends HttpServletRequestWrapper {
	     
	    private String _body;
	 
	    public RequestWrapper(HttpServletRequest request) throws IOException {
	        super(request);
	        _body = "";
	        /*BufferedReader bufferedReader = request.getReader(); 
	        String line;
	        while ((line = bufferedReader.readLine()) != null){
	            _body += line;
	        }*/
	        StringBuilder stringBuilder = new StringBuilder();
	        BufferedReader bufferedReader = null;
	        try {
	            InputStream inputStream = request.getInputStream();
	            if (inputStream != null) {
	                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	                char[] charBuffer = new char[128];
	                int bytesRead = -1;
	                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                    stringBuilder.append(charBuffer, 0, bytesRead);
	                }
	            } else {
	                stringBuilder.append("");
	            }
	        } catch (IOException ex) {
	            throw ex;
	        } finally {
	            if (bufferedReader != null) {
	                try {
	                    bufferedReader.close();
	                } catch (IOException ex) {
	                    throw ex;
	                }
	            }
	        }
	        //Store request pody content in 'body' variable

			_body = stringBuilder.toString();
	    }
	    
	    public ServletInputStream getInputStream() throws IOException {
	        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(_body.getBytes());
	        return new ServletInputStream() {
	            public int read() throws IOException {
	                return byteArrayInputStream.read();
	            }
	        };
	    }
	 
	    //@Override
	    public BufferedReader getReader() throws IOException {
	        return new BufferedReader(new InputStreamReader(this.getInputStream()));
	    }
	}
}

