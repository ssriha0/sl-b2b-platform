package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("response")
public class LMSPingResponse/* implements IAPIResponse */{

	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("error")
	private String error;	

	@XStreamAlias("lead_id")
	private String leadId;	
	
	@XStreamAlias("price")
	private String price;	
	
	@XStreamAlias("providers")
	private LMSProviders providers;
	
	@XStreamAlias("errors")
	private Errors errors;	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public LMSProviders getProviders() {
		return providers;
	}

	public void setProviders(LMSProviders providers) {
		this.providers = providers;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

}
