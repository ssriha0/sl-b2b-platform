package com.servicelive.esb.dto;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Messages")
public class NPSAuditMessages implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XStreamImplicit(itemFieldName="Message")
	private List<String> messages;

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}


}
