package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("leadOrderStatistics")
public class LeadOrderStatistics {

	@XStreamAlias("leadDetails")
	private LeadDetails leadDetails;

	public LeadDetails getLeadDetails() {
		return leadDetails;
	}

	public void setLeadDetails(LeadDetails leadDetails) {
		this.leadDetails = leadDetails;
	}
}
