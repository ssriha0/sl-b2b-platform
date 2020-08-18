package com.newco.marketplace.api.service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;


public class TechHubClient {
private Logger logger = Logger.getLogger(TechHubClient.class);
	
	public String invokeTechHubAPI(String apiUrl, String authCode) {
		String response=null;
		try {
			GetMethod get = new GetMethod(apiUrl);
			logger.info("Sending request = " + apiUrl);
			HttpClient httpclient = new HttpClient();
			get.setRequestHeader("Authorization", authCode);
			httpclient.executeMethod(get);
			response = get.getResponseBodyAsString();
			/*if (get.getStatusCode() == 200) {
				return response;
			} else {
				logger.error("Bad request to " + apiUrl);
				try{
					JSONObject responseJson = new JSONObject(response);
					String responseStatus = responseJson.getString("status");
					String responseMessage = responseJson.getString("message");
					response = createJsonObjString(responseStatus, responseMessage);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return response;
			}*/
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	
	/*public String invokeTechHubAPI(String apiUrl, String authCode, String requestBody) {
		String response =null;
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestBody, "application/json", null); 
			PostMethod post = new PostMethod(apiUrl);
			logger.info("Sending request = " + apiUrl);
			HttpClient httpclient = new HttpClient();
			post.setRequestHeader("Authorization", authCode);
			post.setRequestEntity(requestEntity);
			httpclient.executeMethod(post);
			response = post.getResponseBodyAsString();
			if (post.getStatusCode() == 200) {
				return response;
			} else {
				logger.error("Bad request to " + apiUrl);
				try{
					JSONObject responseJson = new JSONObject(response);
					String responseStatus = responseJson.getString("status");
					String responseMessage = responseJson.getString("message");
					response = createJsonObjString(responseStatus, responseMessage);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return response;
			}
			return response;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}
	*/
	/*public String createJsonObjString(String errorCode,String errorMessage){
		String jsonString = null;
		
	    try {
	    	
			jsonString = new JSONObject()
					.put("soResponse",
						new JSONObject().put("results",
							new JSONObject().put("error",
								new JSONObject().put("code", errorCode).
									put("messsage", errorMessage))))
					.toString();
   	
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	    return jsonString;
	}*/
	

}
