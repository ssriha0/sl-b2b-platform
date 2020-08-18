package com.servicelive.esb.dto;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Messages implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -7530102012055473190L;
	@XStreamImplicit(itemFieldName="Message")
	private List<Message> messages;

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
}
