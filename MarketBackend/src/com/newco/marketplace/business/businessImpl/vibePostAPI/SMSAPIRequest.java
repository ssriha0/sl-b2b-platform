package com.newco.marketplace.business.businessImpl.vibePostAPI;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author 
 * Create SMS API Request
 *
 */
public class SMSAPIRequest {
	
	@XStreamAlias("message")
	private String message;
	
	@XStreamAlias("metadata")
	private String metadata;	
	
	@XStreamAlias("phoneNumber")
	private String phoneNumber;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMetadata() {
		return metadata;
	}
	
	public void setMetadata(String Metadata) {
		this.metadata = metadata;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
