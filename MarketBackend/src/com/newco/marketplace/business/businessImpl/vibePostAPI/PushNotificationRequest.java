package com.newco.marketplace.business.businessImpl.vibePostAPI;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author
 *
 */
public class PushNotificationRequest {

	@XStreamAlias("clientName")
	private String clientName;
	
	@XStreamAlias("message")
	private String message;
	
	@XStreamAlias("metaData")
	private Map<String, String> metaData;
	
	@XStreamAlias("userID")
	private String userID;
	
	@XStreamAlias("event")
	private String event;
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Map<String, String> getMetaData() {
		return metaData;
	}
	public void setMetaData(Map<String, String> metaData) {
		this.metaData = metaData;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
}
