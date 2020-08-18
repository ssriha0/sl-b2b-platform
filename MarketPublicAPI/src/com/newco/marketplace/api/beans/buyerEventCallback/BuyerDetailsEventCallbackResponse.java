package com.newco.marketplace.api.beans.buyerEventCallback;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.common.BaseResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="buyerDetailsEventCallbackResponse.xsd", path="/resources/schemas/buyerEventCallback/")
@XStreamAlias("buyerDetailsEventCallbackResponse")
public class BuyerDetailsEventCallbackResponse extends BaseResponse{
	
	
	@XStreamAlias("url")
	private String url;
	
	@XStreamAlias("method")
	private String method;
	
	@XStreamAlias("httpHeaderParameters")
	private String httpHeaderParameters;
	
	@XStreamAlias("authenticationType")
	private String authenticationType;
	
	@XStreamAlias("apiKey")
	private String apiKey;
	
	@XStreamAlias("apiSecret")
	private String apiSecret;
	
	@XStreamAlias("callbackType")
	private String callbackType;
		

	public BuyerDetailsEventCallbackResponse() {
		super();
	}
	
	public BuyerDetailsEventCallbackResponse(Results results) {
		super(results);
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
}
