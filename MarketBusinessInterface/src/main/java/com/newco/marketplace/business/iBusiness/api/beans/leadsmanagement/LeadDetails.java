package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("LeadDetails")
public class LeadDetails {
	
	@XStreamAlias("LeadDetail")
	private List<LeadDetail> leadDetail;

	public List<LeadDetail> getLeadDetail() {
		return leadDetail;
	}

	public void setLeadDetail(List<LeadDetail> leadDetail) {
		this.leadDetail = leadDetail;
	}
}
