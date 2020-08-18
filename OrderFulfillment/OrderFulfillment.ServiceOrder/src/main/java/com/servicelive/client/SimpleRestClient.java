package com.servicelive.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;

public class SimpleRestClient {
	
	private URL url;
	private String userName = null;
	private String password = null;
	private HttpURLConnection httpURLConnection = null;
	private String POST = "POST";
	private String CONTENT_TYPE_ENCODED = "application/x-www-form-urlencoded";
	private String CONTENT_TYPE = "Content-type";
	private String CONTENT_LENGTH = "Content-Length";
	private String AUTH = "Authorization";
	private String AUTH_BASIC = "Basic ";

	
	private Logger logger = Logger.getLogger(SimpleRestClient.class);
	
    public SimpleRestClient(String URL,String usrName,String passwd,boolean authRequired) throws MalformedURLException{
    	url = new URL(URL);
    	if(authRequired && null!= usrName && null!= passwd){
    		userName = usrName;
			password = passwd;
    	}	
    }
	
    
    

	public int post(String payload) {
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
	
	public String post(String contentType, String payload) {
        int statusCode = 0;
        String json_response = "";
         try {
        	 httpURLConnection = (HttpURLConnection) url.openConnection();
     
        	 httpURLConnection.setDoInput(true);               
             
        	 httpURLConnection.setDoOutput(true);
               
        	 httpURLConnection.setUseCaches(false);
               
        	 logger.info("payload : " + payload);
        	 
             httpURLConnection.setRequestProperty(CONTENT_TYPE, contentType);               
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
             
             InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream());
             BufferedReader br = new BufferedReader(in);
             String text = "";
             while ((text = br.readLine()) != null) {
               json_response += text;
             }
             logger.info("response : " + json_response);
		}catch (SocketTimeoutException e) {
			statusCode = 999;
		}  
        catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json_response;
    }
    
}
