package com.servicelive.service.dataTokenization;

import java.util.List;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("ns2:TokenizeResponse")
public class TokenizeResponse {

	
	@XStreamAsAttribute
    final String xmlns = CommonConstants.RESPONSE_NAMESPACE;

    @XStreamAsAttribute 
    @XStreamAlias("xmlns:ns2")
    final String xlink=CommonConstants.RESPONSE_NAMESPACE_END;
	
	@XStreamAlias("CorrelationId")
	private String CorrelationId;
	
	@XStreamAlias("ResponseCode")
	private String ResponseCode;
	
	@XStreamAlias("ResponseMessage")
	private String ResponseMessage;
	
	@XStreamImplicit(itemFieldName="messages")
	private List<String> messages;
	
	
	
	@XStreamAlias("ns2:token")
	private String token;
	
	@XStreamAlias("ns2:mask")
	private String maskedAccount;
	
	
	
	public String getCorrelationId() {
		return CorrelationId;
	}
	public void setCorrelationId(String correlationId) {
		CorrelationId = correlationId;
	}
	
	
	public String getResponseCode() {
		return ResponseCode;
	}
	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}
	public String getResponseMessage() {
		return ResponseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		ResponseMessage = responseMessage;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMaskedAccount() {
		return maskedAccount;
	}
	public void setMaskedAccount(String maskedAccount) {
		this.maskedAccount = maskedAccount;
	}
	
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	
}
