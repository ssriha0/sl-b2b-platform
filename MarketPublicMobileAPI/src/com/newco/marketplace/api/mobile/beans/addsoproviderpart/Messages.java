package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("messages")
public class Messages {
    
	@XStreamImplicit(itemFieldName="message")
	private List<String> message;

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}
	
}
