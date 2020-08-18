package com.servicelive.spn.services.email;

import static com.servicelive.spn.common.SPNBackendConstants.EMAIL_TO_ADDRESS_SEPERATOR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.servicelive.domain.spn.detached.Email;

/**
 * 
 * 
 *
 */
public class CheetahEmailService implements EmailService {

	private static final long serialVersionUID = 20090408L;
	private static final String SECURE_PROTOCOL = "https://";
	private static final String GOOD_RESPONSE = "OK";
	
	private static final int SECURE_PORT = 443;
	private static final String HOST_NAME_KEY = "cheetah.host.name";
	private static final String MAIL_TEST_MODE_KEY = "cheetah.test.mode";
	private static final String LOGIN_URI_KEY = "cheetah.login.uri";
	private static final String SEND_URI_KEY = "cheetah.send.uri";
	private static final String EMAIL_TO_KEY = "email";
	private static final Logger logger = Logger.getLogger(CheetahEmailService.class.getName());

    private MailQueryStringFormatter mailQueryStringFormatter;

    private Properties emailProperties;

    private static Cookie cookie;

    /**
     * 
     * @param key
     * @param value
     */
    public void addEmailProperty(String key , String value){
    	if(emailProperties == null){
    		emailProperties=new Properties();
    	}
    	emailProperties.put(key, value);
   
    }
    /**
     * 
     * @param emailProperties
     */
    public void setEmailProperties(Properties emailProperties) {
        this.emailProperties = emailProperties;
    }

    public boolean sendEmail(Email email) {
    	//assert(email.getTemplate() == null, "A template is required for sending a Cheetah mail.");
    	int eid = email.getTemplate().getEid().intValue();
    	
    	Map<String, String> originalParams = email.getParams();
    	// clone originalParams
    	Map<String, String> params = new HashMap<String, String>();
    	for(Entry<String, String> entry: originalParams.entrySet()) {
    		params.put(entry.getKey(), entry.getValue());
    	}

    	// handle from
		/*
		String from  = email.getFrom();
		from = StringUtils.isNotBlank(from ) ? from : email.getTemplate().getTemplateFrom();
		if(StringUtils.isNotBlank(from)) {
			params.put("from_address", from);
		}
		*/

    	boolean result = true;
    	// handle to
		List<String> toList = email.getToList();
		toList  = toList.size() > 0 ? toList : getListOfToAddress(email);
		for(String to:toList) {
			params.put(EMAIL_TO_KEY,to); //Cheetah can only send one email at a time
			result &= send(eid, params);
		}


    	return result; 
    }

    /**
	 * 
	 * @param email
	 * @return all the to addresses stored in the database using ';' semicolon as the separator
	 */
	private List<String> getListOfToAddress(Email email) {
		List<String> result = new ArrayList<String> ();
		String src = email.getTemplate().getTemplateTo();
		StringTokenizer tokenizer = new StringTokenizer(src, EMAIL_TO_ADDRESS_SEPERATOR); 
		while(tokenizer.hasMoreTokens()) {
			String toaddress = tokenizer.nextToken();
			if(toaddress != null && toaddress.trim().equals("") == false) {
				result.add(toaddress);
			}
		}
		return result;
	}

    /**
     * 
     * @param eid
     * @param parameters
     * @return boolean
     */
    private boolean send(int  eid, Map<String, String> parameters) {

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

        String testModeFlag = emailProperties.getProperty(MAIL_TEST_MODE_KEY);

        final String testModeParam;
        if(Boolean.valueOf(testModeFlag).booleanValue()){
        	testModeParam = "&test=1";
        } else {
        	testModeParam = "";
        }

        PostMethod postMethod = new PostMethod(SECURE_PROTOCOL
                + emailProperties.getProperty(HOST_NAME_KEY)
                + emailProperties.getProperty(SEND_URI_KEY)
                + "?"
                + mailQueryStringFormatter.getSendEmailHeaderString(eid, parameters)
                + testModeParam
                );
        postMethod.setDoAuthentication(true);
        postMethod.getParams().
        setParameter(HttpMethodParams.RETRY_HANDLER, customRetryHandler);  
        NameValuePair[] data = mailQueryStringFormatter.getNameValuePairs(eid,parameters);
    
        postMethod.setRequestBody(data);

        try {
            int result = httpClient.executeMethod(postMethod);
            logger.info(" result was " + result);
            String response = postMethod.getResponseBodyAsString();
            String msg =  " response body :" + response;
            if(!response.contains(GOOD_RESPONSE)){
            	cookie = null;
            	success = false;
            	String email = parameters.get(EMAIL_TO_KEY);
            	if(email != null) {
            		msg += ", sending to " + email;
            	}
            }else{
            	success = true;
            }
            logger.info(msg);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        	postMethod.releaseConnection();
        }
        return success;
    }

    private void login() {
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
    }

    private Cookie getCookie() {
        if (cookie == null || cookie.isExpired()) {
            login();
        }
        return cookie;
    }

    /**
     * 
     * @param mailQueryStringFormatter
     */
    public void setMailQueryStringFormatter(MailQueryStringFormatter mailQueryStringFormatter) {
        this.mailQueryStringFormatter = mailQueryStringFormatter;
    }
}

