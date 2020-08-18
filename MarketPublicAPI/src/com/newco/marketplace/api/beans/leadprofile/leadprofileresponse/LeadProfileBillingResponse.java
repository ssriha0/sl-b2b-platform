package com.newco.marketplace.api.beans.leadprofile.leadprofileresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "leadProfileBillingResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class LeadProfileBillingResponse {
	
	@XStreamAlias("message")
	private String message;
	
	@XStreamAlias("results")
	private Results results;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
