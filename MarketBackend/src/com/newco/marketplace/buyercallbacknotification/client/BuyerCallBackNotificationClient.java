package com.newco.marketplace.buyercallbacknotification.client;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.buyerCallBackEvent.BuyerCallbackEventsCache;
import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerCallbackEventResponseVO;
import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerKeysVO;
import com.servicelive.JerseyRestClient;
import com.thoughtworks.xstream.XStream;

public class BuyerCallBackNotificationClient {
	
private Logger logger = Logger.getLogger(BuyerCallBackNotificationClient.class);
	
	private String apiBaseUrl;
	private String oAuthEnabled;
	private BuyerCallbackEventsCache buyerCallbackEventsCache;
	/*private String consumerKey;
	private String consumerSecret;*/
	
	public String getBuyerCallbackFilters(String buyerId, Integer actionId){
		BuyerCallbackEventResponseVO buyerEventCallbackResponse=null;
		if (StringUtils.isNotBlank(buyerId) && actionId!=null){
			BuyerKeysVO buyerKeys=getBuyerKeyDetails(buyerId);
			if(buyerKeys!=null){
				JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, buyerKeys.getConsumerKey(), 
						buyerKeys.getSecretKey(), apiBaseUrl);
				try {
					jerseyRestClient.setPath("/v1.0/buyers/" + buyerId);
					jerseyRestClient.setPath("eventcallback/" + actionId);
					String response = jerseyRestClient.get(String.class);
					buyerEventCallbackResponse = (BuyerCallbackEventResponseVO)deserializeResponse(response);
					if(buyerEventCallbackResponse!=null)
						return buyerEventCallbackResponse.getFilterNames();
				} catch (Exception e) {
					logger.info("Exception occured while fetching filters through API call-->"+ e.getMessage());
				}
			}
		}
		return null;
	}
	
	public String getServiceOrderDetails(String buyerId,String soId,String responseFilter){
		if (StringUtils.isNotBlank(buyerId) && StringUtils.isNotBlank(soId) && StringUtils.isNotBlank(responseFilter)) {
			BuyerKeysVO buyerKeys=getBuyerKeyDetails(buyerId);
			if(buyerKeys!=null){
				JerseyRestClient jerseyRestClient = new JerseyRestClient(oAuthEnabled, buyerKeys.getConsumerKey(), 
						buyerKeys.getSecretKey(), apiBaseUrl);
				try {
					jerseyRestClient.setPath("/v1.7/buyers/" + buyerId);
					jerseyRestClient.setPath("serviceorders/" + soId);
					jerseyRestClient.addParameter("responseFilter",responseFilter);
					String response = jerseyRestClient.get(String.class);
					return response;
				} catch (Exception e) {
					logger.info("Exception occured while fetching getSO through API call-->"+ e.getMessage());
				}
			}
			
		}
		return null;
	}
	
	private BuyerKeysVO getBuyerKeyDetails(String buyerId){
		return buyerCallbackEventsCache.fetchBuyerKeyDetails(buyerId);
	}
	
	private Object deserializeResponse( String objectXml) {
		logger.info("Eentering  deserializeResponse()");
		Object obj = new Object();
		try {		
			XStream xstream = new XStream();
			Class[] classes = new Class[] {BuyerCallbackEventResponseVO.class};
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
	public String getoAuthEnabled() {
		return oAuthEnabled;
	}
	public void setoAuthEnabled(String oAuthEnabled) {
		this.oAuthEnabled = oAuthEnabled;
	}
	/*public String getConsumerKey() {
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
*/
	public BuyerCallbackEventsCache getBuyerCallbackEventsCache() {
		return buyerCallbackEventsCache;
	}

	public void setBuyerCallbackEventsCache(BuyerCallbackEventsCache buyerCallbackEventsCache) {
		this.buyerCallbackEventsCache = buyerCallbackEventsCache;
	}
	

}
