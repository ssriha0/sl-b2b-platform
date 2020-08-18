package com.newco.marketplace.service.serviceImpl;

import java.util.Properties;

import org.apache.commons.httpclient.Cookie;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.service.EmailAlertService;

public class CheetahServiceImpl implements EmailAlertService {

	public static final long serialVersionUID = 1L;
	public static final String SECURE_PROTOCOL = "https://";
	public static final String GOOD_RESPONSE = "OK";
	
	public static final int SECURE_PORT = 443;
	public static final String HOST_NAME_KEY = "cheetah.host.name";
	public static final String MAIL_TEST_MODE_KEY = "mail.test.mode";
	public static final String LOGIN_URI_KEY = "cheetah.login.uri";
	public static final String SEND_URI_KEY = "cheetah.send.uri";

   // private MailQueryStringFormatter mailQueryStringFormatter;

    private static Properties emailProperties;

    private static Cookie cookie;

    public void addEmailProperty(String key , String value){
    	if(emailProperties == null){
    		emailProperties=new Properties();
    	}
    	emailProperties.put(key, value);
   
    }
    
    public void setEmailProperties(Properties emailProperties) {
        this.emailProperties = emailProperties;
    }

    /*public boolean send(int  eid, Map<String, String> parameters) {

    	 HttpMethodRetryHandler customRetryHandler = new HttpMethodRetryHandler() {
    	    public boolean retryMethod(
    	        final HttpMethod method, 
    	        final IOException exception, 
    	        int executionCount) {
    	        if (executionCount >= 5) {
    	            // Do not retry if over max retry count
    	            return false;
    	        }
    	        if (exception instanceof NoHttpResponseException) {
    	            // Retry if the server dropped connection on us
    	            return true;
    	        }
    	        if (!method.isRequestSent()) {
    	            // Retry if the request has not been sent fully or
    	            // if it's OK to retry methods that have been sent
    	            return false;
    	        }
    	        // otherwise do not retry
    	        return false;
    	    }
    	};
    	
    	boolean success = false;
        HttpClient httpClient = new HttpClient();
        httpClient.getState().addCookie(getCookie());

        String testModeParam = "";
        String testModeFlag = emailProperties.getProperty(MAIL_TEST_MODE_KEY);
        
        if(Boolean.valueOf(testModeFlag)){
        	testModeParam = "&test=1";
        }
     
        PostMethod postMethod = new PostMethod(SECURE_PROTOCOL
                + emailProperties.getProperty(HOST_NAME_KEY)
                + emailProperties.getProperty(SEND_URI_KEY)
                + "?"
                + mailQueryStringFormatter.getSendEmailHeaderString(eid,
                        parameters) + testModeParam);
        postMethod.setDoAuthentication(true);
        postMethod.getParams().
        setParameter(HttpMethodParams.RETRY_HANDLER, customRetryHandler);  
        NameValuePair[] data = mailQueryStringFormatter.getNameValuePairs(eid,parameters);
    
        postMethod.setRequestBody(data);

        try {
            int result = httpClient.executeMethod(postMethod);
            logger.info(" result was " + result);
            String response = postMethod.getResponseBodyAsString();
            logger.info(" response body :" + response);
            if(!response.contains(GOOD_RESPONSE)){
            	cookie = null;
            	success = false;
            	
            }else{
            	success = true;
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        	postMethod.releaseConnection();
            return success;
        }
    }*/

/*    private void login() {
        HttpClient httpClient = new HttpClient();
        httpClient.getState().setCredentials(
                new AuthScope(emailProperties.getProperty(HOST_NAME_KEY),
                        SECURE_PORT, AuthScope.ANY_REALM),
                new UsernamePasswordCredentials(emailProperties.getProperty(MailQueryStringFormatter.LOGIN_NAME_KEY), 
                								emailProperties.getProperty(MailQueryStringFormatter.LOGIN_PASSWORD_KEY)));

        GetMethod getMethod = new GetMethod(SECURE_PROTOCOL
                + emailProperties.getProperty(HOST_NAME_KEY)
                + emailProperties.getProperty(LOGIN_URI_KEY) + "?"
                + mailQueryStringFormatter.getLoginQueryString());
        getMethod.setDoAuthentication(true);

        try {
            httpClient.executeMethod(getMethod);
            
            cookie = httpClient.getState().getCookies()[0];
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            getMethod.releaseConnection();
        }
    }*/

    private Cookie getCookie() {
        if (cookie == null || cookie.isExpired()) {
           // login();
        }
        return cookie;
    }
/*
    public void setMailQueryStringFormatter(
            MailQueryStringFormatter mailQueryStringFormatter) {
        this.mailQueryStringFormatter = mailQueryStringFormatter;
    }
*/
	public void processAlert() throws DataServiceException {
		// TODO Auto-generated method stub
		
	}
}
