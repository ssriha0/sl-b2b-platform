package com.newco.marketplace.dto.vo.buyerCallbackEvent;

import com.sears.os.vo.SerializableBaseVO;

public class BuyerDetailsEventCallbackVO extends SerializableBaseVO{
	private static final long serialVersionUID = 1L;
	private Integer buyerId;
	private String url;
	private String method;
	private String httpHeaderParameters;
	private String authenticationType;
	private String apiKey;
	private String apiSecret;
	private String callbackType;
	//SLT-3836
	private String callbackBase;
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHttpHeaderParameters() {
		return httpHeaderParameters;
	}
	public void setHttpHeaderParameters(String httpHeaderParameters) {
		this.httpHeaderParameters = httpHeaderParameters;
	}
	public String getAuthenticationType() {
		return authenticationType;
	}
	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getApiSecret() {
		return apiSecret;
	}
	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
	public String getCallbackType() {
		return callbackType;
	}
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	//SLT-3836
	public String getCallbackBase() {
		return callbackBase;
	}
	public void setCallbackBase(String callbackBase) {
		this.callbackBase = callbackBase;
	}
}
