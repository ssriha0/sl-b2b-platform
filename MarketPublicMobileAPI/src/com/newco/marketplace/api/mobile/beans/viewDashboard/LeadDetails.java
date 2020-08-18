package com.newco.marketplace.api.mobile.beans.viewDashboard;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("leadDetails")
public class LeadDetails {

	@XStreamImplicit(itemFieldName ="leadDetail")
	List<LeadDetail> leadDetail;

	public List<LeadDetail> getLeadDetail() {
		return leadDetail;
	}

	public void setLeadDetail(List<LeadDetail> leadDetail) {
		this.leadDetail = leadDetail;
	}
}
