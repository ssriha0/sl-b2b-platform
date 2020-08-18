package com.newco.marketplace.api.common;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.newco.marketplace.api.services.BaseService.RequestType;

public class APIRequestVO {
	public final static String BUYER_RESOURCE_ID = "BUYER_RESOURCE_ID";
	public final static String PROVIDER_RESOURCE_ID = "PROVIDER_RESOURCE_ID";
	public final static String BUYERID = "BUYER_ID";
	public final static String FIRMID = "FIRM_ID";
	public final static String LEADID = "LEAD_ID";
	public final static String PROVIDERID = "PROVIDER_ID";
	public final static String SOID = "SOID";
	public final static String STATUSID = "STATUSID";
	public final static String RESPONSEFILTER_GENERAL = "RESPONSEFILTER_GENERAL";
	public final static String RESPONSEFILTER_SERVICE_LOCATION = "RESPONSEFILTER_SERVICE_LOCATION";
	public final static String RESPONSEFILTER_SCHEDULE = "RESPONSEFILTER_SCHEDULE";
	public final static String FILENAME = "FILENAME";
	public final static String CONSUMER_KEY = "oauth_consumer_key";
	public final static String DOCUMENTID="DOCUMENT_ID";
	public final static String SKU = "SKU";
	public final static String ZIP = "ZIP";
	public final static String DATE = "DATE";
	public final static String START_DATE = "START_DATE";
	public final static String END_DATE = "END_DATE";
	public final static String START_TIME = "START_TIME";
	public final static String END_TIME = "END_TIME";
	
	public final static String PROVIDERFIRMID = "PROVIDER_FIRM_ID";
	
	public final static String ACTION_ID="ACTION_ID";
	
	private Map <String, String> requestFromGetDelete;
	private Object requestFromPostPut;
	private RequestType requestType;
	private Map<String, Object> properties;
	private String consumerKey;
	private Locale locale;
	
	private String contentType;
	private String acceptHeader;
	
	
	public String getAcceptHeader() {
		return acceptHeader;
	}

	public void setAcceptHeader(String acceptHeader) {
		this.acceptHeader = acceptHeader;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	

	private APIRequestVO() {
		properties = new HashMap<String, Object>();
	}

	public APIRequestVO(HttpServletRequest httpRequest) {
		this();
		consumerKey = getConsumerKey(httpRequest);

	}

	public APIRequestVO(Map<String,String> map,Object obj, RequestType type, Integer buyerResourceId, HttpServletRequest httpRequest){
		this(httpRequest);
		this.requestFromGetDelete = map;
		this.requestFromPostPut = obj;
		this.requestType = type;
		setBuyerResourceId(buyerResourceId);
	}


	public Map<String, String> getRequestFromGetDelete() {
		return requestFromGetDelete;
	}

	public String getRequestParamFromGetDelete(String param) {
		if (param != null)
		  return requestFromGetDelete.get(param.toLowerCase());

		return null;
	}

	public void setRequestFromGetDelete(Map<String, String> requestFromGetDelete) {
		this.requestFromGetDelete = requestFromGetDelete;
	}
	public Object getRequestFromPostPut() {
		return requestFromPostPut;
	}
	public void setRequestFromPostPut(Object requestFromPostPut) {
		this.requestFromPostPut = requestFromPostPut;
	}
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	public RequestType getRequestType() {
		return requestType;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Integer getBuyerResourceId() {
		return (Integer)this.properties.get(BUYER_RESOURCE_ID);
	}

	public void setBuyerResourceId(Integer buyerResourceId) {
		addProperties(BUYER_RESOURCE_ID, buyerResourceId);
	}

	public Integer getBuyerResourceIdInteger() {
		if(this.properties.get(BUYER_RESOURCE_ID) instanceof Integer){
			return (Integer)this.properties.get(BUYER_RESOURCE_ID);
		}
		String str = (String)this.properties.get(BUYER_RESOURCE_ID);
		try {
			if (str != null) {
				return Integer.parseInt(str);
			}
		} catch (NumberFormatException e) {
			return -1;
		}
		return null;
	}

	public void setBuyerId(String id) {
		addProperties(BUYERID, id);
	}
	
	public void setActionId(String id) {
		addProperties(ACTION_ID, id);
	}
	
	public void setSku(String sku) {
		addProperties(SKU, sku);
	}
	public void setZip(String zip) {
		addProperties(ZIP, zip);
	}
	public void setDate(String date) {
		addProperties(DATE, date);
	}
	
	public void setStartDate(String startDate) {
		addProperties(START_DATE, startDate);
	}
	
	public void setEndDate(String endDate) {
		addProperties(END_DATE, endDate);
	}
	
	public void setFirmId(String id) {
		addProperties(FIRMID, id);
	}
    
	public void setleadId(String id) {
		addProperties(LEADID, id);
	}
	
	public void setProviderFirmId(String id) {
		addProperties(PROVIDERFIRMID, id);
	}
	
	
	public String getleadId() {
		return (String)this.properties.get(LEADID);
	}
	
	public String getBuyerId() {
		return (String)this.properties.get(BUYERID);
	}
	
	public String getActionId() {
		return (String)this.properties.get(ACTION_ID);
	}
	
	public String getSku() {
		return (String)this.properties.get(SKU);
	}
	public String getZip() {
		return (String)this.properties.get(ZIP);
	}
	public String getDate() {
		return (String)this.properties.get(DATE);
	}
	
	
	public String getStartDate() {
		return (String)this.properties.get(START_DATE);
	}

	public String getEndDate() {
		return (String)this.properties.get(END_DATE);
	}

	public String getFirmId() {
		return (String)this.properties.get(FIRMID);
	}
	
	public String getProviderFirmId() {
		return (String)this.properties.get(PROVIDERFIRMID);
	}

	public void setProviderId(String id) {
		addProperties(PROVIDERID, id);
	}

	public String getProviderId() {
		return (String)this.properties.get(PROVIDERID);
	}

	public Integer getProviderIdInteger() {
		String str = (String)this.properties.get(PROVIDERID);
		return StringToInt(str);
	}

	public Integer getBuyerIdInteger() {
		String str = (String)this.properties.get(BUYERID);
		return StringToInt(str);
	}
	public Integer getActionIdInteger() {
		String str = (String)this.properties.get(ACTION_ID);
		return StringToInt(str);
	}

	public void setDocumentId(String id) {
		addProperties(DOCUMENTID, id);
	}
	
	public void setEndTime(String endTime) {
		addProperties(END_TIME, endTime);
	}
	
	public void setStartTime(String startTime) {
		addProperties(START_TIME, startTime);
	}
	
	public String getStartTime() {
		return (String)this.properties.get(START_TIME);
	}

	public String getEndTime() {
		return (String)this.properties.get(END_TIME);
	}
	
	public String getDocumentId() {
		return (String)this.properties.get(DOCUMENTID);
	}
	
	public static Integer StringToInt(String str) {
		try {
			if (str != null) {
				return Integer.parseInt(str);
			}
		} catch (NumberFormatException e) {
			return -1;
		}
		return null;
	}



	public Integer getProviderResourceId() {
		String str = (String)this.properties.get(PROVIDER_RESOURCE_ID);
		return StringToInt(str);
		
	}

	public void setProviderResourceId(Integer providerResourceId) {
		String resourceId=providerResourceId.toString();
		addProperties(PROVIDER_RESOURCE_ID, resourceId);
	}

	public String getSOId() {
		return (String)this.properties.get(SOID);
	}

	public void setSOId(String SOId) {
		addProperties(SOID, SOId);
	}

	public String getGeneral() {
		return (String)this.properties.get(RESPONSEFILTER_GENERAL);
	}

	public void setGeneral(String GENERAL) {
		addProperties(RESPONSEFILTER_GENERAL, GENERAL);
	}

	public String getServiceLocation() {
		return (String)this.properties.get(RESPONSEFILTER_SERVICE_LOCATION);
	}

	public void setServiceLocation(String SERVICE_LOCATION) {
		addProperties(RESPONSEFILTER_SERVICE_LOCATION, SERVICE_LOCATION);
	}

	public String getSchedule() {
		return (String)this.properties.get(RESPONSEFILTER_SCHEDULE);
	}

	public void setSchedule(String SCHEDULE) {
		addProperties(RESPONSEFILTER_SCHEDULE, SCHEDULE);
	}

	public void addProperties(String Key, Object value) {
		this.properties.put(Key.toUpperCase(), value);
	}

	public Object getProperty(String key) {
		return properties.get(key.toUpperCase());
	}
	public String getFileName() {
		return (String)this.properties.get(FILENAME);
	}

	public void setFileName(String FileName) {
		addProperties(FILENAME, FileName);
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerKey(HttpServletRequest httpRequest) {
		if (httpRequest != null) {
			Object obj = httpRequest.getParameterMap().get(APIRequestVO.CONSUMER_KEY);
			String arr[] = (String [])obj;
			if (arr != null && arr.length > 0)
				return arr[0];
			else { // put or post
				String oauthHeader = httpRequest.getHeader("authorization");
				if (oauthHeader != null) {
					arr  = oauthHeader.split(",");
					if (arr != null) {
						for (String key: arr) {
							if (key.indexOf("oauth_consumer_key") != -1) {
								arr = key.split("=");
								if (arr != null && arr.length >1) {
									return stripLeadingAndTrailingQuotes(arr[1]);
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	String stripLeadingAndTrailingQuotes(String str) {
		if (str.startsWith("\"")) {
			str = str.substring(1, str.length());
		}
		if (str.endsWith("\"")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	
}
