package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("LeadInfoResponse")
public class LeadInfoResponse  {
	

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("totalLeadCount")
	private Integer totalLeadCount;
	
	public Integer getTotalLeadCount() {
		return totalLeadCount;
	}

	public void setTotalLeadCount(Integer totalLeadCount) {
		this.totalLeadCount = totalLeadCount;
	}

	@XStreamAlias("LeadDetails")
	private LeadDetails leadDetails;


	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public LeadDetails getLeadDetails() {
		return leadDetails;
	}

	public void setLeadDetails(LeadDetails leadDetails) {
		this.leadDetails = leadDetails;
	}

	
}
