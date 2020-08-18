package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ns2:LpnResponse")
public class LpnResponse {

	@XStreamAlias("CorrelationId")
	private String correlationId;

	@XStreamAlias("ResponseCode")
	private String responseCode;

	@XStreamAlias("ResponseMessage")
	private String responseMessage;

	@XStreamImplicit(itemFieldName="messages")
	private List<String> messages;

	@XStreamImplicit(itemFieldName="ns2:suppliers")
	private List<LpnSupplier> suppliers;

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
    public List<LpnSupplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<LpnSupplier> suppliers) {
		this.suppliers = suppliers;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
