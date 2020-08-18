package com.newco.marketplace.vo.mobile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "ns2:itemSearchResponse")
@XStreamAlias("ns2:itemSearchResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartDetailsDTO {
	
	@XStreamAlias("processId")
	private String processId;
	
	@XStreamAlias("responseCode")
	private String responseCode;
	
	@XStreamAlias("numFound")
	private String numFound;
	
	@XStreamAlias("items")
	private Items items;
	
	@XStreamAlias("messages")
	private Messages messages;
	
	private String errorMessage;
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getNumFound() {
		return numFound;
	}
	public void setNumFound(String numFound) {
		this.numFound = numFound;
	}
	public Items getItems() {
		return items;
	}
	public void setItems(Items items) {
		this.items = items;
	}
	public Messages getMessages() {
		return messages;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
