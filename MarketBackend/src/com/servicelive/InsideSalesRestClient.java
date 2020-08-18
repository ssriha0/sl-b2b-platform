package com.servicelive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.ProviderConstants;

public class InsideSalesRestClient {
	
	
	private HttpsURLConnection HttpsURLConnection = null;
	private String POST = "POST";
	private String CONTENT_TYPE_JSON = "application/json";

	private String CONTENT_TYPE = "Content-type";
	private String CONTENT_LENGTH = "Content-Length";
	
	private Logger logger = Logger.getLogger(InsideSalesRestClient.class);
	
  
   
    /* Post method which returns the data
     * */
	/**
	 * @param insideSalesLoginRequestJSON
	 * @param request
	 * @param isApiBaseUrl
	 * @return
	 */
	public String createInsideSalesProfile(String insideSalesLoginRequestJSON, String insideSalesAddLeadRequestJSON, String isApiBaseUrl) {
    	StringBuffer loginResponse =new StringBuffer(); 
    	String isResponse =null; 
        try {
        	if(StringUtils.isNotBlank(isApiBaseUrl)){
        		boolean isLogin=logInInsideSales(insideSalesLoginRequestJSON,isApiBaseUrl,loginResponse); 
	    			if(isLogin){
	    			 //  getting cookies which are required for addLead API
		        		List<String> set_cookies = HttpsURLConnection.getHeaderFields().get("Set-Cookie");
		                String cookies = "";
		                for (String cookie: set_cookies) {
			                cookie = cookie.substring(0, cookie.indexOf(";") + 1);
			                cookies = cookies.concat(cookie);
		              }       
		                HttpsURLConnection.disconnect();
	    				isResponse = addLeadToInsideSales(insideSalesAddLeadRequestJSON, isApiBaseUrl,cookies);
	    				
	    			}else{
	    				if(null== loginResponse || StringUtils.isBlank(loginResponse.toString()) || (ProviderConstants.INVALID_LOGIN_RESPONSE).equalsIgnoreCase(loginResponse.toString())){
		    				isResponse =ProviderConstants.IS_LOGIN_FAILED;
	    				}else{
	    				    isResponse = loginResponse.toString();
	    				}
	    			}
	        		/* Possible response codes are
	        		 * 1. true : Login Success
	        		 * 2. false : Login failure*/
	        		
        	}
	    	
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isResponse = e.getMessage();
		}
		return isResponse;
   }
    
    /**
     * @return
     * @throws IOException
     */
    private String readResponse() throws IOException {
    	String response = null;
    	DataInputStream dataInputStream = null;
    	StringBuffer responseBuffer = new StringBuffer();
    	String responseValue;
    	dataInputStream = new DataInputStream(HttpsURLConnection.getInputStream());
    	while(null != ((responseValue = dataInputStream.readLine()))){
    		responseBuffer.append(responseValue);
    	}
    	response = responseBuffer.toString();

    	return response;

    }
    
	/**
	 * @param isApiBaseUrl
	 * @param insideSalesLoginRequestJSON
	 * @return
	 * @throws IOException 
	 */
	private void openConnectionAndSetRequestProperty(
			String isApiBaseUrl, String insideSalesLoginRequestJSON) throws IOException {
		// TODO Auto-generated method stub
    	URL url;
		url = new URL(isApiBaseUrl);	
		
		   
		HttpsURLConnection = (HttpsURLConnection) url.openConnection();
		
		//TLS1.2
				
		HttpsURLConnection.setSSLSocketFactory(new TSLSocketConnectionFactory());
		// I provide the input
		HttpsURLConnection.setDoInput(true);               
		// Requires output
		HttpsURLConnection.setDoOutput(true);
		// No caching
		HttpsURLConnection.setUseCaches(false);
		//            HttpsURLConnection.setConnectTimeout(5000);
		HttpsURLConnection.setConnectTimeout(10000); 
		//Fix for timeout issue
		HttpsURLConnection.setReadTimeout(10000);
		HttpsURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_JSON);               
		HttpsURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(insideSalesLoginRequestJSON.getBytes().length));                           
		HttpsURLConnection.setRequestMethod(POST);		
		// Verify the host Name 
		// This will ignore the SSL certificate warning. Should be done only for test ENV             
		HttpsURLConnection.setHostnameVerifier(new HostnameVerifier() {        
			public boolean verify(String hostname, SSLSession session)  
			{  
				return true;  
			}  
		});
		
    }
	/* Post method which returns the data
     * */
    /**
     * @param payload
     * @param apiUrl
     * @param cookies
     * @return
     */
    public String addLeadToInsideSales(String payload, String apiUrl, String cookies) {

    	String addLeadResp = null;
    	try {
    		openConnectionAndSetRequestProperty(apiUrl,payload);
    			HttpsURLConnection.setRequestProperty("Cookie", cookies);
                HttpsURLConnection.connect();
        		logger.info("payload::"+payload);
        		setPayload(payload);
        		addLeadResp = readResponse();

    		// response either leadID or exception
    	}catch (MalformedURLException e) {
    		e.printStackTrace();
    		addLeadResp = "MalformedURLException in addLeadToInsideSales:"+e.getMessage();

    	}catch (SocketTimeoutException e) {
    		addLeadResp = "SocketTimeoutException in addLeadToInsideSales:"+e.getMessage();
    	}  
    	catch (IOException e) {
    		e.printStackTrace();
    		addLeadResp = "IOException in addLeadToInsideSales:"+e.getMessage();

    	} catch (Exception e) {
    		e.printStackTrace();
    		addLeadResp = "Exception in addLeadToInsideSales:"+e.getMessage();

    	}
    	return addLeadResp;
    }    
    
    
    
	/* Post method which returns the data
     * */
    /**
     * @param payload
     * @param apiUrl
     * @param cookies
     * @return
     */
    public boolean logInInsideSales(String loginRequest, String apiUrl,StringBuffer loginResponse) {

       boolean isLogin=false;
    	try {
    		openConnectionAndSetRequestProperty(apiUrl,loginRequest);
            HttpsURLConnection.connect();
        	logger.info("insideSalesLoginRequestJSON::"+loginRequest);
        	setPayload(loginRequest);
        	String loginResponseString = readResponse();                 		
        		if(StringUtils.isNotBlank(loginResponseString)){
    				isLogin = Boolean.parseBoolean(loginResponseString);
            		loginResponse=loginResponse.append(loginResponseString); 
    			}
    		// response either leadID or exception
    	}catch (MalformedURLException e) {
    		e.printStackTrace();
    		loginResponse = loginResponse.append("MalformedURLException in logInInsideSales:").append(e.getMessage());

    	}catch (SocketTimeoutException e) {
    		loginResponse = loginResponse.append("SocketTimeoutException in logInInsideSales:").append(e.getMessage());
    	}  
    	catch (IOException e) {
    		e.printStackTrace();
    		loginResponse = loginResponse.append("IOException in logInInsideSales:").append(e.getMessage());
    	} catch (Exception e) {
    		e.printStackTrace();
    		loginResponse = loginResponse.append("Exception in logInInsideSales:").append(e.getMessage());
    	}
    	return isLogin;
    } 
    /**
     * @param payload
     * @throws IOException
     */
    private void setPayload(String payload) throws IOException {
    	DataOutputStream dataOutputStream = null;

    	dataOutputStream = new DataOutputStream(HttpsURLConnection.getOutputStream());
		dataOutputStream.writeBytes(payload);
		dataOutputStream.flush();
		dataOutputStream.close();
	}
    
    /* Post method which returns the data
     * */
    /**
     * @param payload
     * @param apiUrl
     * @param cookies
     * @return
     */
    public String logInInsideSalesAndGetCookies(String loginRequest, String apiUrl) {
       boolean isLogin=false;
       String cookies = "";
    	try {
    		openConnectionAndSetRequestProperty(apiUrl,loginRequest);
            HttpsURLConnection.connect();
        	setPayload(loginRequest);
        	String loginResponseString = readResponse();
    		if(StringUtils.isNotBlank(loginResponseString)){
				isLogin = Boolean.parseBoolean(loginResponseString);
				logger.info("isLogin::"+isLogin);
        		if(isLogin){
    			 //  getting cookies which are required for other APIs
	        		List<String> set_cookies = HttpsURLConnection.getHeaderFields().get("Set-Cookie");
	                for (String cookie: set_cookies) {
		                cookie = cookie.substring(0, cookie.indexOf(";") + 1);
		                cookies = cookies.concat(cookie);
	              }       
	                  				
    			}
			}
    	}catch (MalformedURLException e) {
    		e.printStackTrace();
    	}catch (SocketTimeoutException e) {
    		e.printStackTrace();
    	}catch (IOException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return cookies;
    } 
    
    /**
     * @param payload
     * @param apiUrl
     * @param cookies
     * @return
     */
    public String postData(String payload, String apiUrl, String cookies) {

    	String response = null;
    	if(null!=apiUrl){
	    	try {
    			openConnectionAndSetRequestProperty(apiUrl,payload);
    			HttpsURLConnection.setRequestProperty("Cookie", cookies);
                HttpsURLConnection.connect();
        		setPayload(payload);
        		response = readResponse();
	    	}catch (MalformedURLException e) {
	    		e.printStackTrace();	
	    	}catch (SocketTimeoutException e) {
	    		e.printStackTrace();
	    	}catch (IOException e) {
	    		e.printStackTrace();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
    	}
    	return response;
    }    
}
