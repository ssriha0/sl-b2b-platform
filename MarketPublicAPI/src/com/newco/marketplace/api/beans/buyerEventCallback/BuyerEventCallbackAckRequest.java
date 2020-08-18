package com.newco.marketplace.api.beans.buyerEventCallback;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.HttpResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="buyerEventCallbackAckRequest.xsd", path="/resources/schemas/buyerEventCallback/")
@XStreamAlias("buyerEventCallbackAckRequest")
public class BuyerEventCallbackAckRequest{

	@XStreamAlias("result")
	private HttpResult httpResult;
	

	public BuyerEventCallbackAckRequest() {
	}

	public BuyerEventCallbackAckRequest(HttpResult httpResult) {
		super();
		this.httpResult = httpResult;
	}


	public HttpResult getHttpResult() {
		return httpResult;
	}


	public void setHttpResult(HttpResult httpResult) {
		this.httpResult = httpResult;
	}

	
	
}
