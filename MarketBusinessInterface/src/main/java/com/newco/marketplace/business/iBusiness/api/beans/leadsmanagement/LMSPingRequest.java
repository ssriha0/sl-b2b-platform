package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Request")
public class LMSPingRequest {

	@XStreamAlias("Key")
	private String apiKey;

	@XStreamAlias("API_Action")
	private String apiAction;
	
	@XStreamAlias("Mode")
	private String mode;
	
	@XStreamAlias("TYPE")
	private String type;	
	
	@XStreamAlias("Data")
	private LMSPingData pingData;
	
	public LMSPingRequest() {

	}

	public LMSPingRequest(String apiKey, String apiAction, String mode,
			String type, LMSPingData pingData) {
		this.apiKey = apiKey;
		this.apiAction = apiAction;
		this.mode = mode;
		this.type = type;
		this.pingData = pingData;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiAction() {
		return apiAction;
	}

	public void setApiAction(String apiAction) {
		this.apiAction = apiAction;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LMSPingData getPingData() {
		return pingData;
	}

	public void setPingData(LMSPingData pingData) {
		this.pingData = pingData;
	}	
	
}
