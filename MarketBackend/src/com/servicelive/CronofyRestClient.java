package com.servicelive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

public class CronofyRestClient {

	private String base_url;
	private String api_path;
	private HttpsURLConnection HttpsURLConnection = null;

	private String POST = "POST";
	private String GET = "GET";
	private String ACCEPT = "Accept";
	private String CONTENT_TYPE = "Content-type";
	private String TYPE_JSON = "application/json";
	private String CONTENT_LENGTH = "Content-Length";
	private String AUTHORIZATION = "Authorization";
	private Logger logger = Logger.getLogger(CronofyRestClient.class);

	public CronofyRestClient(String base_url) {
		super();
		this.base_url = base_url;
	}

	@SuppressWarnings("deprecation")
	public <T> T get(String api_ath, Map<String, String> headers,
			Map<String, String> urlParams, String access_token)
			throws MalformedURLException, IOException {
		String queryString = urlParams == null ? "" : getQueryString(urlParams);
		String full_url = base_url + api_ath;
		full_url = queryString.length() == 0 ? full_url : full_url+"?"+queryString;
		logger.info("Full URL:: " + full_url);
		HttpsURLConnection = (HttpsURLConnection) new URL(full_url).openConnection();
		HttpsURLConnection.setUseCaches(false);
		HttpsURLConnection.setConnectTimeout(5000);
		HttpsURLConnection.setRequestMethod(GET);
		appendRequestHeaders(headers ,access_token);
		HttpsURLConnection.connect();
		String responseValue;
		StringBuffer responseBuffer = new StringBuffer();
		DataInputStream dataInputStream = new DataInputStream(
				HttpsURLConnection.getInputStream());
		while (null != ((responseValue = dataInputStream.readLine()))) {
			responseBuffer.append(responseValue);
		}
		logger.info("sGet Response is::::" + responseBuffer.toString());
		HttpsURLConnection.disconnect();
		return (T) responseBuffer.toString();
	}
	
	public String post(String api_ath,
			Map<String, String> headers, Map<String, String> urlParams,
			String access_token, String payload,String... pathParams)
			throws MalformedURLException, IOException {
		int statusCode = 0;
		String queryString = urlParams == null ? "" : getQueryString(urlParams);
		String full_url = base_url + api_ath;
		full_url = queryString.length() == 0 ? full_url : full_url+"?"+queryString;
		full_url = addPathParams(full_url, pathParams);
		//full_url = full_url.replace("{cal_id}", calendar_id);
		logger.info("Full URL:: " + full_url);
		HttpsURLConnection = (HttpsURLConnection) new URL(full_url).openConnection();
		HttpsURLConnection.setUseCaches(false);
		appendRequestHeaders(headers, access_token);
		HttpsURLConnection.setDoOutput(true);
		HttpsURLConnection.setDoInput(true);
		HttpsURLConnection.setUseCaches(false);
		HttpsURLConnection.setRequestProperty(CONTENT_TYPE, TYPE_JSON);
		HttpsURLConnection.setRequestProperty(CONTENT_LENGTH,
				Integer.toString(payload.length()));
		HttpsURLConnection.setRequestMethod(POST);
		HttpsURLConnection.setConnectTimeout(10000);
		HttpsURLConnection.setReadTimeout(10000);
		HttpsURLConnection.connect();
		logger.info("payload::" + payload);
		DataOutputStream printout = new DataOutputStream(
				HttpsURLConnection.getOutputStream());
		printout.writeBytes(payload);
		printout.flush();
		printout.close();
		statusCode = HttpsURLConnection.getResponseCode();
		logger.info(HttpsURLConnection.getResponseMessage());
		StringBuffer responseBuffer = new StringBuffer();
		if(statusCode == 200){
			String responseValue;
			DataInputStream dataInputStream = new DataInputStream(
					HttpsURLConnection.getInputStream());
			while (null != ((responseValue = dataInputStream.readLine()))) {
				responseBuffer.append(responseValue);
			}
			logger.info("sGet Response is::::" + responseBuffer.toString());	
		}
		HttpsURLConnection.disconnect();
		/*
		 * Possible response codes are 1. 202 : Event Created in Apple, Google
		 * or Outlook 2. 500 : * Internal Server error 3. 400 : Data is
		 * incorrect or lacking 4. 401 : unauthorized - accesstoken expired 4.
		 * 422 : payload is missing mandetory paramaters
		 */
		return responseBuffer.toString();
	}
	
	private String addPathParams(String full_url, String... pathParams) {
		int i =1;
		for(String pathParam : pathParams){
			full_url = full_url.replace("{pathParam"+i+"}", pathParam);
			if(pathParams.length>i){
				full_url = full_url + "/";
			}
			i++;
		}
		return full_url;
	}
	
	private void appendRequestHeaders(Map<String, String> headers,
			String access_token) {
		HttpsURLConnection.setRequestProperty(ACCEPT, TYPE_JSON);
		if(access_token != null && access_token != "")
			HttpsURLConnection.setRequestProperty(AUTHORIZATION, access_token);
		if (headers == null || headers.isEmpty()) {
			return;
		}
		Iterator<Entry<String, String>> iterator = headers.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> pairs = iterator.next();
			String keyStr = pairs.getKey().toString();
			String value = pairs.getValue().toString();
			HttpsURLConnection.setRequestProperty(keyStr, value);
		}
	}

	private String getQueryString(Map<String, String> params) {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			if (first)
				first = false;
			else
				result.append("&");
			Entry<String, String> pairs = iterator.next();
			String keyStr = pairs.getKey().toString();
			String value = pairs.getValue().toString();
			result.append(keyStr).append("=").append(value);
		}
		return result.toString();
	}

	public String getApi_path() {
		return api_path;
	}

	public void setApi_path(String api_path) {
		this.api_path = api_path;
	}
}
