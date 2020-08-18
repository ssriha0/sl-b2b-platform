package com.newco.marketplace.api.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.OAuthServiceProvider;
import net.oauth.consumer.IOAuthConsumer;
import net.oauth.example.provider.core.SampleOAuthProvider;
import net.oauth.example.consumer.webapp.Callback;
import net.oauth.server.OAuthServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.misc.BASE64Decoder;

import com.newco.marketplace.business.iBusiness.api.IAPISecurity;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.dto.api.APIApplicationDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;


public class APISecurityFilter implements Filter {

	private final String[] applicationContextLocation = {"classpath*:**/mainApplicationContext.xml"};
	private static final Log logger = LogFactory.getLog(APISecurityFilter.class);

	private ApplicationContext ctx;
	private IAPISecurity apiSecurity;
	private IOAuthConsumer oAuthConsumer;
    private OAuthServiceProvider serviceProvider;
	private List<String> openURLs = new ArrayList<String>();
	private Pattern openPattern[] = null;

	private static final String BUYERS = "buyers";
	private static final String PROVIDERS = "providers";
	private static final String SERVICELIVE = "servicelive";
	private final String errorString = "Not enough permissions to access URL";
	private static final String INVALIDBUYERID="Invalid Buyer ID";
	private static final String INVALIDPROVIDERID="Invalid Provider ID";
   

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		
		int roleId = 0;
		StringTokenizer authTokenizer =null;
		String authorization="";
		boolean basicAuthEnabled=apiSecurity.isBasicAuthEnabled();
		boolean oauthEnabled=apiSecurity.isSecurityEnabled();
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		RequestWrapper wrappedRequest = new RequestWrapper(request);
		request = wrappedRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			logger.debug("OPTION Request");
			response.setStatus(HttpServletResponse.SC_OK);
			return;
        }
		if(!(basicAuthEnabled || oauthEnabled)){
			logger.debug("Authentication is disabled");
			chain.doFilter(request, response);
			return;
		}
		
		if (isResourceExcluded(request) == true) {
			chain.doFilter(request, response);
			return;
		}
		boolean isBasicAuth=false;
		String consumerKey="";
		String password="";
		// if basic authentication is ON
		if (basicAuthEnabled) {
			String authHeader = request.getHeader(OrderConstants.AUTHENTICATION_HEADER);
			if(StringUtils.isNotBlank(authHeader)){
			authTokenizer = new StringTokenizer(
					 authHeader,OrderConstants.SPACE);
			 authorization = authTokenizer.nextToken();
			}
			//If basic http authentication
			if(OrderConstants.BASIC.equalsIgnoreCase(authorization)){
				isBasicAuth=true;
					try {
					//Code for Basic
					String decodedAuth = "";
			        // Header is in the format "Basic 5tyc0uiDat4"
			        // Extract data before decoding it back to original string
			        String authData = authTokenizer.nextToken();
			        // Decode the data back to original string
			       byte[] bytes = null;
			       bytes = new BASE64Decoder().decodeBuffer(authData);
			       decodedAuth = new String(bytes);
			       StringTokenizer tokenizer = new StringTokenizer(
			        		decodedAuth, OrderConstants.COLON);
			       consumerKey = tokenizer.nextToken();
				   password = tokenizer.nextToken();
				   String consumerPassword = oAuthConsumer.getConsumerSecret(consumerKey); 
				   // check if the password matches
				   if(!(StringUtils.isNotBlank(consumerPassword) && consumerPassword.equals(password))){
						logger.info("Authentication Failed");
						response.sendError(HttpServletResponse.SC_FORBIDDEN, OrderConstants.BASICAUTHERROR);
						return;  
				   }
					}
					catch (IOException e) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, OrderConstants.AUTHERRORSTRING);
			        e.printStackTrace();
			            return;
			        }
					catch (Exception e) {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, OrderConstants.AUTHERRORSTRING);
						e.printStackTrace();
						return;
					}
	
		   }				
			}
		// if Oauth is enabled 
		if (oauthEnabled && !isBasicAuth) {
			try { 
				OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
				consumerKey = requestMessage.getConsumerKey();			
				String pass = oAuthConsumer.getConsumerSecret(consumerKey);			
				validateOauthForAPI(requestMessage, consumerKey, pass);

			}catch (OAuthException e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, OrderConstants.OAUTHERRORSTRING);
				e.printStackTrace();
				return;
			} catch (URISyntaxException e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, OrderConstants.URL_NOT_FOUND); 
				e.printStackTrace();
				return;
			}	
			
		}
		try {
		boolean isApiOperationAllowed=checkApiPermission(roleId, request, response, consumerKey);
        if(!isApiOperationAllowed){
       	 return;
        }
		chain.doFilter(request, response);
		}catch (BusinessServiceException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, errorString);
			return;
		}catch(NumberFormatException e){
			response.sendError(HttpServletResponse.SC_FORBIDDEN, getUserTypErrorByUrl(request.getPathInfo()));
			return;
		}

	}

	private boolean checkApiPermission(int roleId, HttpServletRequest request,
			HttpServletResponse response, String username)
			throws BusinessServiceException, NumberFormatException, IOException {
		  String inputPath = request.getPathInfo();
		   APIApplicationDTO vo = apiSecurity.getApplication(username);
			int type = getUserType(inputPath);
			int userId = getUserId(inputPath, type);
			logger.info("type :-"+type);
			logger.info("userId :-"+userId);
			if (vo != null && vo.isInternalConsumer() == true) {
				roleId = type;
			}

			if (userId != -1) {
				boolean flag = apiSecurity.isUserExisting(userId, username, roleId);
				if (flag == false) {
					logger.info("Consumer(" + username +") is not linked to user(" + userId + ")");
					response.sendError(HttpServletResponse.SC_FORBIDDEN, getUserTypErrorByUrl(inputPath));
					return false;
				}
			}

			boolean flag = isOperationAllowed(inputPath, username, request.getMethod());
			if (flag == false) {
				logger.info("API operation(" + inputPath +") not allowed for consumer:" + username );
				response.sendError(HttpServletResponse.SC_FORBIDDEN, errorString);
				return false;
			}
			return true;
	}
	
	private void validateOauthForAPI(OAuthMessage requestMessage, String consumerKey,String pass) throws URISyntaxException, OAuthException, IOException  {
		if (consumerKey == null || pass == null) {     
            OAuthProblemException problem = new OAuthProblemException("token_rejected");
            throw problem;
        }
        //throw new Exception("Invalid consumer credentials");
        OAuthConsumer consumer = new OAuthConsumer(Callback.PATH, consumerKey, pass, serviceProvider);
        OAuthAccessor accessor = new OAuthAccessor(consumer);
        /*OAuthMessage request = null;
        logger.info("creating request like client --- with accessor");
        if ("GET".equalsIgnoreCase(requestMessage.method) || "DELETE".equalsIgnoreCase(requestMessage.method)){
        	request =  accessor.newRequestMessage(requestMessage.method,
    				requestMessage.URL, requestMessage.getParameters());
        }
        else{
        	request = accessor.newRequestMessage(requestMessage.method,
    				requestMessage.URL, requestMessage.getParameters(),requestMessage.getBodyAsStream());
        }
        logger.info("created request like client");
        logger.info("req body available "+request.getBodyAsStream() == null);
        if(request.getBodyAsStream() != null){
        	logger.info("req body available "+request.getBodyAsStream().available());
        	logger.info("...printing body...");
        	int i = 0;
        	while((i=request.getBodyAsStream().read())!=-1){
        	}
        }*/
        SampleOAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
        
      
	}
	/**
	 * Initialize the filter
	 */
	public void init(FilterConfig config) throws ServletException {
		apiSecurity = (IAPISecurity)loadBeanFromContext("apiSecurityAndOAuth");
		oAuthConsumer = (IOAuthConsumer)loadBeanFromContext("OAuthConsumerImpl");
        serviceProvider = 
                new OAuthServiceProvider("http://localhost:8080/dummy", "http://localhost:8080/dummy", "http://localhost:8080/dummy");

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

		//SLT-4206 Changed the role id from buyer role to provider role in below case
		//and removed check on om
		if(url.indexOf(PROVIDERS) != -1){
			return OrderConstants.PROVIDER_ROLEID;
		}

		return 0;
	}

	public int getUserId(String url, int roleId) throws NumberFormatException{
		if (url == null) {
			return -1;
		}
		
		if(url.indexOf(SERVICELIVE) != -1)
			return -1;

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

		if (url.indexOf("skuId") != -1) {
			url = url.replaceAll("skuId/[a-zA-Z0-9\\!\\_\\.\\-\\s]+/", "skuId/{skuId}/");	

			if (url.indexOf("zipCode") != -1) {				
				url = url.replaceAll("zipCode/[0-9]+", "zipCode/{zipCode}");	
			}
		}
		
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
		
		if (url.indexOf("servicelive") != -1)
			url = url.replaceAll("servicelive/[0-9]+/", "servicelive/{id}/");
		
		if (url.indexOf("survey") != -1){
			if (url.indexOf("getDetails") != -1) 
				url = url.replaceAll("survey/getDetails", "survey/getDetails/{key}");
			
			else if (url.indexOf("validate") != -1) 
				url = url.replaceAll("survey/validate", "survey/validate/{key}");
			
		}
		
		if (url.indexOf("buyers") != -1)
			url = url.replaceAll("buyers/[0-9]+/", "buyers/{id}/");
		
		if (url.indexOf("eventcallback") != -1)
			url = url.replaceAll("eventcallback/[0-9]+", "eventcallback/{actionId}");
			
		if (url.indexOf("notification") != -1)
			url = url.replaceAll("notification/[0-9]+", "notification/{notificationId}");

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
	
	private String getUserTypErrorByUrl(String url){
		if (url.indexOf("buyers") != -1) 
			return INVALIDBUYERID;
		else if(url.indexOf("providers") != -1)
			return INVALIDPROVIDERID;
		
		return "Invalid User";
		
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
	 
	    //@Override
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

