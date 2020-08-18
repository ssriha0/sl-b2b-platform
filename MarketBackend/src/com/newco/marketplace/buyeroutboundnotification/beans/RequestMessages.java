package com.newco.marketplace.buyeroutboundnotification.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("MESSAGES")
public class RequestMessages {
	
	@XStreamImplicit(itemFieldName="MESSAGE")
	private List<RequestMessage> requestMessage;

	public List<RequestMessage> getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(List<RequestMessage> requestMessage) {
		this.requestMessage = requestMessage;
	}
	

}
