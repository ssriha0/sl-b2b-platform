package com.newco.marketplace.api.client;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.newco.marketplace.interfaces.EventCallbackConstants;
import com.newco.marketplace.vo.SOMResponse;

public class SOMClient {
	private Logger logger = Logger.getLogger(SOMClient.class);

	public String invokeTechHubAPI(String apiUrl, String authCode) {
		String response = null;
		try {
			GetMethod get = new GetMethod(apiUrl);
			logger.info("Sending request = " + apiUrl);
			HttpClient httpclient = new HttpClient();
			get.setRequestHeader("Authorization", authCode);
			httpclient.executeMethod(get);
			response = get.getResponseBodyAsString();
			if (get.getStatusCode() == 200) {
				logger.debug("SOM API Response for " + apiUrl + "is SUCCESS");
			} else {
				logger.error("Error response from " + apiUrl);
			}

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public SOMResponse invokeTechHubAPI(String apiUrl, String authCode, String requestBody) {
		String response = null;
		SOMResponse respObj = null;
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestBody, "application/json", null);
			PostMethod post = new PostMethod(apiUrl);
			logger.info("Sending request = " + apiUrl);
			logger.info("SOM Request body = " + requestBody);
			HttpClient httpclient = new HttpClient();
			post.setRequestHeader("Authorization", authCode);
			post.setRequestHeader("clientid", EventCallbackConstants.TH_API_HEADER_CLIENT_ID_VALUE);
			post.setRequestHeader("userid", EventCallbackConstants.TH_API_HEADER_USER_ID_VALUE);

			logger.info("Header clientid:" + EventCallbackConstants.TH_API_HEADER_CLIENT_ID_VALUE);
			logger.info("Header userid:" + EventCallbackConstants.TH_API_HEADER_USER_ID_VALUE);

			post.setRequestEntity(requestEntity);
			httpclient.executeMethod(post);
			response = post.getResponseBodyAsString();
			if (post.getStatusCode() == 200) {
				logger.debug("SOM API Response for " + apiUrl + "is SUCCESS");
			} else {
				logger.error("Error response from " + apiUrl);
			}
			logger.info("SOM Response body = " + response);

			if (response == null) {
				return null;
			} else {
				return convertResponseToObj(response);
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return respObj;
	}

	public SOMResponse convertResponseToObj(String response) {
		Gson gson = new Gson();
		SOMResponse respObj = gson.fromJson(response, SOMResponse.class);
		return respObj;
	}
}
