package com.newco.marketplace.inhomeoutboundnotification.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing response information for 
 * the In Home Out bound Message Service
 * @author Infosys
 */
@XStreamAlias("SendMessageResponse")
public class SendMessageResponse {
	
	@XStreamAlias("CorrelationId")
	private String correlationId;
	
	@XStreamAlias("ResponseCode")
	private String responseCode;
	
	@XStreamAlias("ResponseMessage")
	private String responseMessage;
	
	@XStreamAlias("messages")
	private ErrorMessage messages;

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

	public ErrorMessage getMessages() {
		return messages;
	}

	public void setMessages(ErrorMessage messages) {
		this.messages = messages;
	}
}