package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("leadDetail")
public class LeadDetail {

	@XStreamAlias("leadOrderStatus")
	private String leadOrderStatus;
	
	@XStreamAlias("leadOrderCount")
	private Integer leadOrderCount;
	
	public String getLeadOrderStatus() {
		return leadOrderStatus;
	}
	public void setLeadOrderStatus(String leadOrderStatus) {
		this.leadOrderStatus = leadOrderStatus;
	}
	public Integer getLeadOrderCount() {
		return leadOrderCount;
	}
	public void setLeadOrderCount(Integer leadOrderCount) {
		this.leadOrderCount = leadOrderCount;
	}

}
