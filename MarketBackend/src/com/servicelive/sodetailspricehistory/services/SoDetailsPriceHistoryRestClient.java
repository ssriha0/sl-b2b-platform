package com.servicelive.sodetailspricehistory.services;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.price.SOPriceHistoryResponse;
import com.servicelive.JerseyRestClient;
import com.thoughtworks.xstream.XStream;

public class SoDetailsPriceHistoryRestClient {

	private Logger logger = Logger.getLogger(SoDetailsPriceHistoryRestClient.class);
	
	private String apiBaseUrl;
	private JerseyRestClient jerseyRestClient;
	private String oAuthEnabled;
	private String consumerKey;
	private String consumerSecret;
	
	/**
	 * 
	 * @param buyerId
	 * @param soIds
	 * @param infoLevel
	 * @return
	 */
	public SOPriceHistoryResponse getSoDetailsPriceHistory
			(String buyerId, String soIds, String infoLevel) {
		
		SOPriceHistoryResponse priceHistoryResponse =null;
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, consumerKey, consumerSecret, apiBaseUrl);
		if (StringUtils.isNotBlank(buyerId) && StringUtils.isNotBlank(soIds)
			&& StringUtils.isNotBlank(infoLevel)) {
			try {
				jerseyRestClient.setPath("/v1.1/buyers/" + buyerId);
				jerseyRestClient.setPath("serviceorders/" + soIds);
				jerseyRestClient.setPath("priceHistory");
				jerseyRestClient.addParameter("infoLevel",infoLevel);
				String response = jerseyRestClient.get(String.class);
				priceHistoryResponse = (SOPriceHistoryResponse)deserializeResponse(response);
			} catch (Exception e) {
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
			  //priceHistoryResponse.setResults(results);
				e.printStackTrace();
			} /*catch (IOException e) {
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
			  //priceHistoryResponse.setResults(results);
				e.printStackTrace();
			}*/
		}
		return priceHistoryResponse;
		
		/*RestClient restClient = new RestClient(apiBaseUrl);
		restClient.setPath("v1.1/buyers/" + buyerId);
        restClient.setPath("serviceorders/" + soIds);
        restClient.setPath("priceHistory");
        restClient.addParameter("infoLevel",infoLevel);
        return restClient.get(SOPriceHistoryResponse.class);*/
		/*Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		
		if (StringUtils.isNotBlank(buyerId) && StringUtils.isNotBlank(soIds)
			&& StringUtils.isNotBlank(infoLevel)) {
			try {
				GetMethod getMethod = new GetMethod(apiBaseUrl+"/v1.1/buyers/"+ buyerId + "/serviceorders/" + 
						soIds+ "/priceHistory?infoLevel=" + infoLevel);
				logger.info("Sending request = " + buyerId + ":" + soIds+ ":" + infoLevel);
				HttpClient httpclient = new HttpClient();
				httpclient.executeMethod(getMethod);
				String response = getMethod.getResponseBodyAsString();
				if (getMethod.getStatusCode() == 200) {
					priceHistoryResponse = (SOPriceHistoryResponse)deserializeResponse(response);
					return priceHistoryResponse;
				} else {
					errorResult.setMessage(response);
					results.addError(errorResult);
				  //priceHistoryResponse.setResults(results);
					return priceHistoryResponse;
				}
			} catch (HttpException e) {
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
			  //priceHistoryResponse.setResults(results);
				e.printStackTrace();
			} catch (IOException e) {
				errorResult.setMessage(e.getMessage());
				results.addError(errorResult);
			  //priceHistoryResponse.setResults(results);
				e.printStackTrace();
			}
		}
		return priceHistoryResponse;*/
	}
	/**
	 * @param objectXml
	 * @return
	 */
	private Object deserializeResponse( String objectXml) {
		logger.info("Eentering  deserializeResponse()");
		Object obj = new Object();
		try {		
			XStream xstream = new XStream();
			Class[] classes = new Class[] {SOPriceHistoryResponse.class};
			xstream.processAnnotations(classes);
			obj = (Object) xstream.fromXML(objectXml);
			logger.info("Exiting deserializeResponse()");		
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}
	public JerseyRestClient getJerseyRestClient() {
		return jerseyRestClient;
	}
	public void setJerseyRestClient(JerseyRestClient jerseyRestClient) {
		this.jerseyRestClient = jerseyRestClient;
	}
	public String getoAuthEnabled() {
		return oAuthEnabled;
	}
	public void setoAuthEnabled(String oAuthEnabled) {
		this.oAuthEnabled = oAuthEnabled;
	}
	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	public String getConsumerSecret() {
		return consumerSecret;
	}
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
}