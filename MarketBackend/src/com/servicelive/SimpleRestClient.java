package com.servicelive;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.inhomeoutboundnotification.vo.InhomeResponseVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.leadoutboundnotification.vo.LeadResponseVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.vibes.VibesResponseVO;
import com.servicelive.response.SimpleRestResponse;
import com.sun.jersey.api.client.ClientHandlerException;

public class SimpleRestClient {
	
	private URL url;
	private String userName = null;
	private String password = null;
	private HttpsURLConnection HttpsURLConnection = null;
	private HttpURLConnection httpURLConnection = null;
	private String POST = "POST";
	private String GET = "GET";
	private String CONTENT_TYPE_ENCODED = "application/x-www-form-urlencoded";
	private String CONTENT_TYPE_XML = "application/xml";
	private String CONTENT_TYPE = "Content-type";
	private String CONTENT_LENGTH = "Content-Length";
	private String AUTH = "Authorization";
	private String AUTH_BASIC = "Basic ";
	//R16_1: SL-18979: Add Participant API vibes Changes
	private String CONTENT_TYPE_JSON = "application/json";
	private String DELETE = "DELETE";
	private static final Integer SUCCESS_CODE[] = { 200, 201, 204, 202};
	
	private Logger logger = Logger.getLogger(SimpleRestClient.class);
	
    public SimpleRestClient(String URL,String usrName,String passwd,boolean authRequired) throws MalformedURLException{
    	url = new URL(URL);
    	if(authRequired && null!= usrName && null!= passwd){
    		userName = usrName;
			password = passwd;
    	}	
    }
	
    /* Post method which returns the HTTP code
     * */
    @SuppressWarnings("deprecation")
	public int post(String payload) {
        int statusCode = 0;
         try {
             HttpsURLConnection = (HttpsURLConnection) url.openConnection();
             // I provide the input
             HttpsURLConnection.setDoInput(true);               
             
             // Requires output
             HttpsURLConnection.setDoOutput(true);
               
             // No caching
             HttpsURLConnection.setUseCaches(false);
             
//             HttpsURLConnection.setConnectTimeout(5000);
               
             // Set authentication if required
             logger.info("User Name::"+userName);
             logger.info("Password::"+password);
             
             if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
            	 String authStr = userName+":"+password;
            	 HttpsURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
             }
               
             HttpsURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_ENCODED);               
             HttpsURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(payload.getBytes().length));                           
             HttpsURLConnection.setRequestMethod(POST);
             DataOutputStream dataOutputStream = null;
             
             // Verify the host Name 
             // This will ignore the SSL certificate warning. Should be done only for test ENV             
             HttpsURLConnection.setHostnameVerifier(new HostnameVerifier() {        
                public boolean verify(String hostname, SSLSession session)  
                {  
                    return true;  
                }  
             });
             HttpsURLConnection.setConnectTimeout(10000); 
             //Fix for timeout issue
             HttpsURLConnection.setReadTimeout(10000);
             HttpsURLConnection.connect();
             logger.info("payload::"+payload);
               
             dataOutputStream = new DataOutputStream(HttpsURLConnection.getOutputStream());
             dataOutputStream.writeBytes(payload);
             dataOutputStream.flush();
             dataOutputStream.close();
               
             statusCode = HttpsURLConnection.getResponseCode();
             /* Possible response codes are
              * 1. 201 : Lead created in CRM
              * 2. 500 : Internal Server error
              * 3. 400 : Data is incorrect or lacking required */
	    	
	    	
		}catch (SocketTimeoutException e) {
			statusCode = 999;
		}  
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statusCode;
    }
	
	public SimpleRestResponse post(String payload, HashMap<String, String> hearderMap) {
        int responseCode = 0;
        SimpleRestResponse response = new SimpleRestResponse();
         try {
             HttpsURLConnection = (HttpsURLConnection) url.openConnection();
             // I provide the input
             HttpsURLConnection.setDoInput(true);
             // Requires output
             HttpsURLConnection.setDoOutput(true);
             // No caching
             HttpsURLConnection.setUseCaches(false);
               
             if(hearderMap!= null){
            	 for (Map.Entry<String,String> entry : hearderMap.entrySet())  {
                	 HttpsURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                 }  
             }                       
             HttpsURLConnection.setRequestMethod(POST);
             
             // Verify the host Name 
             // This will ignore the SSL certificate warning. Should be done only for test ENV             
             HttpsURLConnection.setHostnameVerifier(new HostnameVerifier() {        
                public boolean verify(String hostname, SSLSession session)  
                {  
                    return true;  
                }  
             });
             HttpsURLConnection.setConnectTimeout(10000); 
             //Fix for timeout issue
             HttpsURLConnection.setReadTimeout(10000);
             HttpsURLConnection.connect();
			
			 DataOutputStream wr = new DataOutputStream(HttpsURLConnection.getOutputStream());
			 wr.write(payload.getBytes("UTF8"));
			 wr.flush();
			 wr.close();
				
			 responseCode = HttpsURLConnection.getResponseCode();
			 response.setResponseCode(responseCode);
			 DataInputStream dataInputStream = null;
			 String responseValue;  
			 StringBuffer responseBuffer = new StringBuffer();
			 dataInputStream = new DataInputStream(HttpsURLConnection.getInputStream());
             while(null != ((responseValue = dataInputStream.readLine()))){
                 responseBuffer.append(responseValue);
             }
             response.setResponse(responseBuffer.toString());
		}catch (SocketTimeoutException e) {
			logger.error("Socket Error Occured : "+ e.getMessage());
		}catch (IOException e) {
        	logger.error("I/O Error Occured : "+ e.getMessage());
		} catch (Exception e) {
			logger.error("Error Occured : "+ e.getMessage());
		}
		return response;
    }
    
    /* Post method which returns the data
     * */
    @SuppressWarnings("deprecation")
	public String postData(String payload) {
        StringBuffer responseBuffer = new StringBuffer();
         try {
             HttpsURLConnection = (HttpsURLConnection) url.openConnection();
             // I provide the input
             HttpsURLConnection.setDoInput(true);               
             
             // Requires output
             HttpsURLConnection.setDoOutput(true);
               
             // No caching
             HttpsURLConnection.setUseCaches(false);
               
             HttpsURLConnection.setConnectTimeout(5000);
             // Set authentication if required
             if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
            	 String authStr = userName+":"+password;
            	 HttpsURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
             }
               
             HttpsURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_XML);               
             HttpsURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(payload.getBytes().length));                           
             HttpsURLConnection.setRequestMethod(POST);
             DataOutputStream dataOutputStream = null;
             DataInputStream dataInputStream = null;
             
             // Verify the host Name 
             // This will ignore the SSL certificate warning. Should be done only for test ENV
             HttpsURLConnection.setHostnameVerifier(new HostnameVerifier() {        
                public boolean verify(String hostname, SSLSession session)  
                {  
                    return true;  
                }  
             });
               
             HttpsURLConnection.connect();               
               
             dataOutputStream = new DataOutputStream(HttpsURLConnection.getOutputStream());
             dataOutputStream.writeBytes(payload);
             dataOutputStream.flush();
             dataOutputStream.close();
             
             String responseValue;           
             dataInputStream = new DataInputStream(HttpsURLConnection.getInputStream());
             while(null != ((responseValue = dataInputStream.readLine()))){
                 responseBuffer.append(responseValue);
             }
		} catch (IOException e) {
			logger.error("I/O Error Occured : "+ e.getMessage());
		} catch (Exception e) {
			logger.error("Error Occured : "+ e.getMessage());
		}
		return responseBuffer.toString();
    }
    
    
    @SuppressWarnings("deprecation")
	public VibesResponseVO postDataJSON(String payload) {
        StringBuffer responseBuffer = new StringBuffer();
        VibesResponseVO response = new VibesResponseVO();
         try {
             HttpsURLConnection = (HttpsURLConnection) url.openConnection();
             // I provide the input
             HttpsURLConnection.setDoInput(true);               
             
             // Requires output
             HttpsURLConnection.setDoOutput(true);
               
             // No caching
             HttpsURLConnection.setUseCaches(false);
               
             HttpsURLConnection.setConnectTimeout(5000);
             // Set authentication if required
             if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
            	 String authStr = userName+":"+password;
            	 HttpsURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
             }
               
             HttpsURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_JSON);               
             HttpsURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(payload.getBytes().length));                           
             HttpsURLConnection.setRequestMethod(POST);
             DataOutputStream dataOutputStream = null;
             DataInputStream dataInputStream = null;
             
             // Verify the host Name 
             // This will ignore the SSL certificate warning. Should be done only for test ENV
             
             HttpsURLConnection.setHostnameVerifier(new HostnameVerifier() {        
                public boolean verify(String hostname, SSLSession session)  
                {  
                    return true;  
                }  
             });
               
             HttpsURLConnection.connect();
               
               
             dataOutputStream = new DataOutputStream(HttpsURLConnection.getOutputStream());
             dataOutputStream.writeBytes(payload);
             dataOutputStream.flush();
             dataOutputStream.close();
             
             
             String responseValue;
             
             response.setStatusCode(HttpsURLConnection.getResponseCode());
             response.setStatusText(HttpsURLConnection.getResponseMessage());
             
             int statusCode = HttpsURLConnection.getResponseCode();
             
             //Checking if success response is obtained success response is saved, else error response is saved
             //if (OrderConstants.STATUS_CODE_SUCCESS1 == statusCode || OrderConstants.STATUS_CODE_SUCCESS2 == statusCode 
             //		|| AlertConstants.VIBES_CREATE_EVENT_API_SUCCESS_CODE == statusCode){
             if (OrderConstants.STATUS_CODE_SUCCESS1 == statusCode){  //200 for success response
              dataInputStream = new DataInputStream(HttpsURLConnection.getInputStream());
            }
            else {
           	 dataInputStream = new DataInputStream(HttpsURLConnection.getErrorStream());
            }
             
             while(null != ((responseValue = dataInputStream.readLine()))){
                 responseBuffer.append(responseValue);
             }
             
             response.setResponse(responseBuffer.toString());   
             
             
             
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
    }
    
    @SuppressWarnings("deprecation")
   	public VibesResponseVO postDataJSON(String payload, String token)  {
           StringBuffer responseBuffer = new StringBuffer();
           StringBuffer errorBuffer = new StringBuffer();
           VibesResponseVO response = new VibesResponseVO();
            try {
                HttpsURLConnection = (HttpsURLConnection) url.openConnection();
                // I provide the input
                HttpsURLConnection.setDoInput(true);               
                
                // Requires output
                HttpsURLConnection.setDoOutput(true);
                  
                // No caching
                HttpsURLConnection.setUseCaches(false);
                  
                HttpsURLConnection.setConnectTimeout(5000);
                // Set authentication if required
                if(!StringUtils.isEmpty(token)){
               	 HttpsURLConnection.setRequestProperty(AUTH,AUTH_BASIC + token);
                }
                  
                HttpsURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_JSON);               
                HttpsURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(payload.getBytes().length));                           
                HttpsURLConnection.setRequestMethod(POST);
                DataOutputStream dataOutputStream = null;
                DataInputStream dataInputStream = null;
                
                // Verify the host Name 
                // This will ignore the SSL certificate warning. Should be done only for test ENV
                
                HttpsURLConnection.setHostnameVerifier(new HostnameVerifier() {        
                   public boolean verify(String hostname, SSLSession session)  
                   {  
                       return true;  
                   }  
                });
                  
                HttpsURLConnection.connect();
                  
                  
                dataOutputStream = new DataOutputStream(HttpsURLConnection.getOutputStream());
                dataOutputStream.writeBytes(payload);
                dataOutputStream.flush();
                dataOutputStream.close();
                
                
                String responseValue;
                String errorValue;
                response.setStatusCode(HttpsURLConnection.getResponseCode());
                response.setStatusText(HttpsURLConnection.getResponseMessage());
                
                int statusCode = HttpsURLConnection.getResponseCode();
                
                //Checking if success response is obtained success response is saved, else error response is saved
                //if (OrderConstants.STATUS_CODE_SUCCESS1 == statusCode || OrderConstants.STATUS_CODE_SUCCESS2 == statusCode 
                //		|| AlertConstants.VIBES_CREATE_EVENT_API_SUCCESS_CODE == statusCode){
                if (OrderConstants.STATUS_CODE_SUCCESS1 == statusCode){  //200 for success response
                 dataInputStream = new DataInputStream(HttpsURLConnection.getInputStream());
               }
                else {
				logger.info("error state");
				// SLT-1676
				dataInputStream = new DataInputStream(HttpsURLConnection.getErrorStream());
				while (null != ((errorValue = dataInputStream.readLine()))) {
					errorBuffer.append(errorValue);
				}
				response.setError(errorBuffer.toString());
			}
                              
                while(null != ((responseValue = dataInputStream.readLine()))){
                    responseBuffer.append(responseValue);
                }
                
                response.setResponse(responseBuffer.toString());   
                
                
                
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		return response;
       }
       
    /* Get method which returns data
     * Payload is not mandatory
     * */
    @SuppressWarnings("deprecation")
	public String get(Map<String, String> urlParams) {
    	 StringBuffer responseBuffer = new StringBuffer();
    	try {
    	HttpsURLConnection = (HttpsURLConnection) url.openConnection();
    	// I provide the input
        HttpsURLConnection.setDoInput(true);               
        
        // Requires output
        HttpsURLConnection.setDoOutput(true);
          
        // No caching
        HttpsURLConnection.setUseCaches(false);
          
        HttpsURLConnection.setConnectTimeout(5000);
        
        // Set authentication if required
        if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
       	 String authStr = userName+":"+password;
       	 HttpsURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
        }
        HttpsURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_ENCODED);        
                                
        HttpsURLConnection.setRequestMethod(GET);
        
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        
        // Verify the host Name 
        // This will ignore the SSL certificate warning. Should be done only for test ENV
        
        HttpsURLConnection.setHostnameVerifier(new HostnameVerifier() {        
           public boolean verify(String hostname, SSLSession session)  
           {  
               return true;  
           }  
        });
          
        HttpsURLConnection.connect();
        
        dataOutputStream = new DataOutputStream(HttpsURLConnection.getOutputStream());
        
        String queryString = getQueryString(urlParams).replaceAll("\\+", "%20");
        dataOutputStream.writeBytes(queryString);
        
        logger.info("sGet Request is::::"+queryString);
        dataOutputStream.flush();
        dataOutputStream.close();
        
        String responseValue;
      
        dataInputStream = new DataInputStream(HttpsURLConnection.getInputStream());
        while(null != ((responseValue = dataInputStream.readLine()))){
            responseBuffer.append(responseValue);
        }
    	
        logger.info("sGet Response is::::"+responseBuffer.toString());
        
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return responseBuffer.toString();
    }
    /* Get method which returns data
     * Payload is not mandatory
     * */
    @SuppressWarnings("deprecation")
	public String sGet(Map<String, String> urlParams) {
    	 StringBuffer responseBuffer = new StringBuffer();

    	 try {
    	
    	httpURLConnection = (HttpURLConnection) url.openConnection();
    	// I provide the input
    	httpURLConnection.setDoInput(true);               
        
        // Requires output
    	httpURLConnection.setDoOutput(true);
          
        // No caching
    	httpURLConnection.setUseCaches(false);
          
    	httpURLConnection.setConnectTimeout(5000);
        
        // Set authentication if required
        if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
       	 String authStr = userName+":"+password;
       	httpURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
        }
        httpURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_ENCODED);        
                                
        httpURLConnection.setRequestMethod(GET);
        
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        
          
        httpURLConnection.connect();
        
        dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        String queryString = getQueryString(urlParams).replaceAll("\\+", "%20");
        
        dataOutputStream.writeBytes(queryString);
        
        logger.info("get http Request is::::"+queryString);
        dataOutputStream.flush();
        dataOutputStream.close();
        
        String responseValue;
      
        dataInputStream = new DataInputStream(httpURLConnection.getInputStream());
        while(null != ((responseValue = dataInputStream.readLine()))){
            responseBuffer.append(responseValue);
        }
        
        logger.info("get http Response is::::"+responseBuffer.toString());
        
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return responseBuffer.toString();
    }
    
    /* Post method which returns data
     * No content for the request.
     * Written for NS - 39 Lead Outbound Notification Batch
     * */
    @SuppressWarnings("deprecation")
	public LeadResponseVO sPost(Map<String, String> urlParams) throws Exception{
    	 StringBuffer responseBuffer = new StringBuffer();
    	 LeadResponseVO leadResponseVO = new LeadResponseVO();
    	 try {
    	
    	httpURLConnection = (HttpURLConnection) url.openConnection();
    	// I provide the input
    	httpURLConnection.setDoInput(true);               
        
        // Requires output
    	httpURLConnection.setDoOutput(true);
          
        // No caching
    	httpURLConnection.setUseCaches(false);
          
    	httpURLConnection.setConnectTimeout(5000);
        
        // Set authentication if required
        if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
       	 String authStr = userName+":"+password;
       	httpURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
        }
        httpURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_ENCODED);        
                                
        httpURLConnection.setRequestMethod(POST);
        
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        
          
        httpURLConnection.connect();
        
        dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        String queryString = getQueryString(urlParams).replaceAll("\\+", "%20");
        
        dataOutputStream.writeBytes(queryString);
        
        logger.info("post http Request is::::"+queryString);
        dataOutputStream.flush();
        dataOutputStream.close();
        
        String responseValue;
        
        leadResponseVO.setStatusCode(httpURLConnection.getResponseCode());
        
        leadResponseVO.setStatusText(httpURLConnection.getResponseMessage());
      
        dataInputStream = new DataInputStream(httpURLConnection.getInputStream());
        while(null != ((responseValue = dataInputStream.readLine()))){
            responseBuffer.append(responseValue);
        }
        leadResponseVO.setResponseXml(responseBuffer.toString());
        
        logger.info("post http Response is::::"+responseBuffer.toString());
        
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e);
		}
    	return leadResponseVO;
    }
    
    /* Post method which returns inhome notification response
     * Written for InHome Outbound Notification Batch
     * Used to send call close & Send message API notifications
     * */
    @SuppressWarnings("deprecation")
	public InhomeResponseVO postMethod(String request, Map<String, String> headers) throws Exception{
    	 /*
			* As part of SLT-4575 disabling TLSv1 as push notification URL is supporting the TLSv1.2 
			*commenting the same.Because jdk 1.8 by default it supports TLSv1.2
			* 
			*/
		//System.setProperty("https.protocols", "TLSv1");
    	 StringBuffer responseBuffer = new StringBuffer();
    	 InhomeResponseVO inHomeResponseVO = new InhomeResponseVO();
    	 logger.info("SimpleRestClint.postMethod: Begin");
         try {
             HttpsURLConnection = (HttpsURLConnection) url.openConnection();
             // I provide the input
             HttpsURLConnection.setDoInput(true);               
             
             // Requires output
             HttpsURLConnection.setDoOutput(true);
               
             // No caching
             HttpsURLConnection.setUseCaches(false);
               
             HttpsURLConnection.setConnectTimeout(5000);
             // Set authentication if required
             if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
            	 String authStr = userName+":"+password;
            	 HttpsURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
             }
             HttpsURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_XML);               
             HttpsURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(request.getBytes().length));                           
             HttpsURLConnection.setRequestMethod(POST);
             
             @SuppressWarnings("rawtypes")
             Iterator iterator = headers.entrySet().iterator();
             while (iterator.hasNext()) {
                 @SuppressWarnings("rawtypes")
                 Map.Entry pairs = (Map.Entry)iterator.next();
                 logger.debug("SimpleRestClint.postMethod:"+pairs.getKey().toString()+"="+pairs.getValue().toString());
                 HttpsURLConnection.setRequestProperty(pairs.getKey().toString(), pairs.getValue().toString());
             }
             DataOutputStream dataOutputStream = null;
             DataInputStream dataInputStream = null;
             
             // Verify the host Name 
             // This will ignore the SSL certificate warning. Should be done only for test ENV
             
             SSLContext ctx = SSLContext.getInstance("SSL");
             ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
             HttpsURLConnection.setSSLSocketFactory(ctx.getSocketFactory());
             
             HttpsURLConnection.setHostnameVerifier(new HostnameVerifier() {        
                public boolean verify(String hostname, SSLSession session)  
                {  
                    return true;  
                }  
             });
             HttpsURLConnection.connect();
             dataOutputStream = new DataOutputStream(HttpsURLConnection.getOutputStream());
             dataOutputStream.writeBytes(request);
             dataOutputStream.flush();
             dataOutputStream.close();
             
             String responseValue;
             inHomeResponseVO.setStatusCode(HttpsURLConnection.getResponseCode());
 	        
	         inHomeResponseVO.setStatusText(HttpsURLConnection.getResponseMessage());
           
             dataInputStream = new DataInputStream(HttpsURLConnection.getInputStream());
             while(null != ((responseValue = dataInputStream.readLine()))){
                 responseBuffer.append(responseValue);
             }
             inHomeResponseVO.setResponseXml(responseBuffer.toString());
 	        
	         logger.debug("post http Response is::::"+responseBuffer.toString());
	    	
		} 
        catch (IOException e) {
			logger.error("IO Exception occured in postMethod():"+e);
		} 
        catch (Exception e) {
			logger.error("Exception occured in postMethod():"+e);
		}
        logger.info("SimpleRestClint.postMethod: End");
        return inHomeResponseVO;
    }
    /* Post method which returns inhome notification response
     * Written for InHome Outbound Notification Batch
     * Used to send call close & Send message API notifications
     * */
    @SuppressWarnings("deprecation")
	public InhomeResponseVO postMethodLpn(String request, Map<String, String> headers) throws Exception{
    	
    	 StringBuffer responseBuffer = new StringBuffer();
    	 InhomeResponseVO inHomeResponseVO = new InhomeResponseVO();
    	 logger.info("SimpleRestClint.postMethod: Begin");
         try {
             httpURLConnection = (HttpURLConnection) url.openConnection();
             // I provide the input
             httpURLConnection.setDoInput(true);               
             
             // Requires output
             httpURLConnection.setDoOutput(true);
               
             // No caching
             httpURLConnection.setUseCaches(false);
               
             httpURLConnection.setConnectTimeout(5000);
             // Set authentication if required
             if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
            	 String authStr = userName+":"+password;
            	 httpURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
             }
             httpURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_XML);               
             httpURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(request.getBytes().length));                           
             httpURLConnection.setRequestMethod(POST);
             
             @SuppressWarnings("rawtypes")
             Iterator iterator = headers.entrySet().iterator();
             while (iterator.hasNext()) {
                 @SuppressWarnings("rawtypes")
                 Map.Entry pairs = (Map.Entry)iterator.next();
                 logger.debug("SimpleRestClint.postMethod:"+pairs.getKey().toString()+"="+pairs.getValue().toString());
                 httpURLConnection.setRequestProperty(pairs.getKey().toString(), pairs.getValue().toString());
             }
             DataOutputStream dataOutputStream = null;
             DataInputStream dataInputStream = null;
             httpURLConnection.connect();
             dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
             dataOutputStream.writeBytes(request);
             dataOutputStream.flush();
             dataOutputStream.close();
             
             String responseValue;
             inHomeResponseVO.setStatusCode(httpURLConnection.getResponseCode());
 	        
	         inHomeResponseVO.setStatusText(httpURLConnection.getResponseMessage());
           
             dataInputStream = new DataInputStream(httpURLConnection.getInputStream());
             while(null != ((responseValue = dataInputStream.readLine()))){
                 responseBuffer.append(responseValue);
             }
             inHomeResponseVO.setResponseXml(responseBuffer.toString());
 	         logger.debug("post http Response is::::"+responseBuffer.toString());
	    	
		} 
        catch (IOException e) {
			logger.error("IO Exception occured in postMethod():"+e);
		} 
        catch (Exception e) {
			logger.error("Exception occured in postMethod():"+e);
		}
        logger.info("SimpleRestClint.postMethod: End");
        return inHomeResponseVO;
    }
    
    @SuppressWarnings("unchecked")
	private String getQueryString(Map<String,String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator iterator = params.entrySet().iterator();
        
        while (iterator.hasNext()) {
        	if (first)
                first = false;
            else
                result.append("&");
            Map.Entry pairs = (Map.Entry)iterator.next();            
            String keyStr = pairs.getKey().toString();
            String value = pairs.getValue().toString();
            if(value.equalsIgnoreCase("PROJECTDESC")){
            	result.append(keyStr);
            }else{
            	result.append(URLEncoder.encode(keyStr, "UTF-8"));            
            } 
            //logger.info("Key : " + keyStr);
            //logger.info("Key Value : " + value);
            if(!value.equalsIgnoreCase("PROJECTDESC")){
            	//logger.info("Key Value Dont : " + value);
            	result.append("=");
                result.append(URLEncoder.encode(value, "UTF-8"));
            }
        }
        return result.toString();
    }
    
   // http get method to fetch details from a third party system.
    public String getApiResponse() throws Exception{ 	
    	String responseBufferString=null;
    	StringBuffer responseBuffer=new StringBuffer();
    	HttpURLConnection httpUrlConnection=null;	
		try{
				httpUrlConnection = (HttpURLConnection) url.openConnection();
				httpUrlConnection.setRequestMethod(MPConstants.GET);
				httpUrlConnection.setRequestProperty(MPConstants.ACCEPT, MPConstants.APP_XML);
			if (httpUrlConnection.getResponseCode() != MPConstants.SUCESS_CODE) {
				logger.error("HTTP GET Request Failed with Error code : "+ httpUrlConnection.getResponseCode());
			}else{
				BufferedReader responseBufferReader = new BufferedReader(new InputStreamReader((httpUrlConnection.getInputStream())));
				String output;
				while ((output = responseBufferReader.readLine()) != null) {
				   responseBuffer.append(output);
				}
              if(responseBuffer.length()>0){
            	  responseBufferString=responseBuffer.toString();
                 }
			}
		  } catch(Exception e){
			logger.error("Exception in getApiResponse "+e.getStackTrace());
			throw e;
		  }		  
		  finally{
			  httpUrlConnection.disconnect();
		  }
		  return responseBufferString;
  }
    
    //This will ignore the SSL certificate warning.
    private static class DefaultTrustManager implements X509TrustManager {
    	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {}
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {}
    }
    
    /* R16_1: SL-18979: Post method which returns 
     * add participant api response from vibes
     * */
    @SuppressWarnings("deprecation")
	public VibesResponseVO postMethodVibes(String request, Map<String, String> headers) throws Exception{
    	
    	 StringBuffer responseBuffer = new StringBuffer();
    	 VibesResponseVO response = new VibesResponseVO();
    	 logger.info("SimpleRestClint.postMethodVibes: Begin");
    	 try{
    		HttpsURLConnection = (HttpsURLConnection) url.openConnection();
 			HttpsURLConnection.setDoInput(true);               
 			HttpsURLConnection.setDoOutput(true);
 			HttpsURLConnection.setUseCaches(false);
 			HttpsURLConnection.setConnectTimeout(5000);
 			
 			if(headers !=null && !(headers.isEmpty())){
 			userName = headers.get("username");
            password = headers.get("password");
 			}
            
            if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
           	 String authStr = userName+":"+password;
           	 HttpsURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
            }
            
            HttpsURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_JSON);               
            HttpsURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(request.getBytes().length));
            HttpsURLConnection.setRequestMethod(POST);
            
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;
            
            HttpsURLConnection.connect();
            dataOutputStream = new DataOutputStream(HttpsURLConnection.getOutputStream());
            
            logger.info("JSON request for Sendign SMS --->"+request+"--------->"+request.toString());
            dataOutputStream.writeBytes(request);
            dataOutputStream.flush();
            dataOutputStream.close();
            
            String responseValue;
            
            response.setStatusCode(HttpsURLConnection.getResponseCode());
            response.setStatusText(HttpsURLConnection.getResponseMessage());
            
            int statusCode = HttpsURLConnection.getResponseCode();
            
            logger.info("status code-------->"+statusCode);
            
            //Checking if success response is obtained success response is saved, else error response is saved
            //if (OrderConstants.STATUS_CODE_SUCCESS1 == statusCode || OrderConstants.STATUS_CODE_SUCCESS2 == statusCode 
            //		|| AlertConstants.VIBES_CREATE_EVENT_API_SUCCESS_CODE == statusCode){
            if (OrderConstants.STATUS_CODE_SUCCESS1 == statusCode){  //200 for success response
             dataInputStream = new DataInputStream(HttpsURLConnection.getInputStream());
           }
           else {
          	 dataInputStream = new DataInputStream(HttpsURLConnection.getErrorStream());
           }
            
            while(null != ((responseValue = dataInputStream.readLine()))){
                responseBuffer.append(responseValue);
            }
            
            response.setResponse(responseBuffer.toString());   
            logger.info("post http Response is::::"+responseBuffer.toString());
    	 }
    	 catch (IOException e) {
 			logger.error("IO Exception occured in postMethod():"+e);
 			response = null;
 			throw new Exception(e);
 		} 
 		catch (Exception e) {
 			logger.error("Exception occured in postMethod():"+e);
 			response = null;
 			throw new Exception(e);
 		}
 		logger.info("SimpleRestClint.postMethodVibes: End");
        return response;
    }

    /* R16_1: SL-18979: Delete method which returns 
     * delete participant api response from vibes
     * */
	public VibesResponseVO deleteMethodVibes(Map<String, String> headers) throws ClientHandlerException,IOException{
    	
    	 VibesResponseVO responseVO = new VibesResponseVO();
    	 logger.info("SimpleRestClient.deleteMethodVibes: Begin");
             HttpsURLConnection = (HttpsURLConnection) url.openConnection();
             // I provide the input
             HttpsURLConnection.setDoInput(true);               
             
             // Requires output
             HttpsURLConnection.setDoOutput(false);
               
             // No caching
             HttpsURLConnection.setUseCaches(false);
               
             HttpsURLConnection.setConnectTimeout(5000);
             // Set authentication if required
             userName = headers.get("username");
             password = headers.get("password");
             if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
            	 String authStr = userName+":"+password;
            	 HttpsURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
             }
             HttpsURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_ENCODED);               
             HttpsURLConnection.setRequestMethod(DELETE);
             HttpsURLConnection.connect();
             
             responseVO.setStatusCode(HttpsURLConnection.getResponseCode());
             responseVO.setStatusText(HttpsURLConnection.getResponseMessage()); 
             
        return responseVO;
    }
	
	public int postWebHooks(String payload){
		int statusCode = 0;
        try {
       	 httpURLConnection = (HttpURLConnection) url.openConnection();
     
       	 httpURLConnection.setDoInput(true);               
          
       	 httpURLConnection.setDoOutput(true);
              
       	 httpURLConnection.setUseCaches(false);
            
          httpURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_ENCODED);               
          httpURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(payload.getBytes().length));                           
          httpURLConnection.setRequestMethod(POST);
          DataOutputStream dataOutputStream = null;
            
          httpURLConnection.setConnectTimeout(10000); 
          httpURLConnection.setReadTimeout(10000);
          httpURLConnection.connect();
              
          dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
          dataOutputStream.writeBytes(payload);
          dataOutputStream.flush();
          dataOutputStream.close();
              
          statusCode = httpURLConnection.getResponseCode();
	    	
		}catch (SocketTimeoutException e) {
			statusCode = 999;
		}  
       catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusCode;
	}
	
	@SuppressWarnings("deprecation")
   	public VibesResponseVO postDataCallBack(String payload, String token) throws Exception {
           StringBuffer responseBuffer = new StringBuffer();
           StringBuffer errorBuffer = new StringBuffer();
           VibesResponseVO response = new VibesResponseVO();
            try {
            	httpURLConnection = (HttpURLConnection) url.openConnection();
                // I provide the input
            	httpURLConnection.setDoInput(true);               
                
                // Requires output
            	httpURLConnection.setDoOutput(true);
                  
                // No caching
            	httpURLConnection.setUseCaches(false);
                  
            	httpURLConnection.setConnectTimeout(5000);
                // Set authentication if required
                if(!StringUtils.isEmpty(token)){
                	httpURLConnection.setRequestProperty(AUTH,AUTH_BASIC + token);
                }
                  
                httpURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_JSON);               
                httpURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(payload.getBytes().length));                           
                httpURLConnection.setRequestMethod(POST);
                DataOutputStream dataOutputStream = null;
                DataInputStream dataInputStream = null;
                
              
                  
                httpURLConnection.connect();
                  
                  
                dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(payload);
                dataOutputStream.flush();
                dataOutputStream.close();
                
                
                String responseValue;
                String errorValue;
                response.setStatusCode(httpURLConnection.getResponseCode());
                response.setStatusText(httpURLConnection.getResponseMessage());
                
                int statusCode = httpURLConnection.getResponseCode();
                
                //Checking if success response is obtained success response is saved, else error response is saved
                //if (OrderConstants.STATUS_CODE_SUCCESS1 == statusCode || OrderConstants.STATUS_CODE_SUCCESS2 == statusCode 
                //		|| AlertConstants.VIBES_CREATE_EVENT_API_SUCCESS_CODE == statusCode){
                if (OrderConstants.STATUS_CODE_SUCCESS1 == statusCode){  //200 for success response
                 dataInputStream = new DataInputStream(httpURLConnection.getInputStream());
               }
                else {
				logger.info("error state");
				// SLT-1676
				dataInputStream = new DataInputStream(httpURLConnection.getErrorStream());
				while (null != ((errorValue = dataInputStream.readLine()))) {
					errorBuffer.append(errorValue);
				}
				response.setError(errorBuffer.toString());
			}
                              
                while(null != ((responseValue = dataInputStream.readLine()))){
                    responseBuffer.append(responseValue);
                }
                
                response.setResponse(responseBuffer.toString());   
                
                
                
   		} catch (Exception e) {
   			e.printStackTrace();
   			throw new Exception("Exception occurred while calling callback API due to "
					+ e.getMessage());
   		}
   		return response;
       }
}
