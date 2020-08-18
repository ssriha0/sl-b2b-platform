package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Request")
public class LMSPostRequest {

	@XStreamAlias("Key")
	private String apiKey;

	@XStreamAlias("API_Action")
	private String apiAction;
	
	@XStreamAlias("Mode")
	private String mode;
	
	@XStreamAlias("Lead_ID")
	private String leadId;
	
	@XStreamAlias("TYPE")
	private String type;	
	
	@XStreamAlias("Data")
	private LMSPostData postData;

	@XStreamAlias("Match_Partner_List")
	private List<FirmIdPrice> firmIdPriceList;	
	
	
	public LMSPostRequest(String apiKey, String apiAction, String mode,
			String leadId, String type, LMSPostData postData, List<FirmIdPrice> firmIdPriceList) {
		this.apiKey = apiKey;
		this.apiAction = apiAction;
		this.mode = mode;
		this.leadId = leadId;
		this.type = type;
		this.postData = postData;
		this.firmIdPriceList = firmIdPriceList;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public LMSPostData getPostData() {
		return postData;
	}

	public void setPostData(LMSPostData postData) {
		this.postData = postData;
	}

	public List<FirmIdPrice> getFirmIdPriceList() {
		return firmIdPriceList;
	}

	public void setFirmIdPriceList(List<FirmIdPrice> firmIdPriceList) {
		this.firmIdPriceList = firmIdPriceList;
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

	
}
