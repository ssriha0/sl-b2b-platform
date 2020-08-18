package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("spnMonitor")
public class SpnMonitor {

	@XStreamAlias("spnBuyerDetailsList")
	private SpnBuyerDetailsList spnBuyerDetailsList;

	public SpnBuyerDetailsList getSpnBuyerDetailsList() {
		return spnBuyerDetailsList;
	}

	public void setSpnBuyerDetailsList(SpnBuyerDetailsList spnBuyerDetailsList) {
		this.spnBuyerDetailsList = spnBuyerDetailsList;
	}
}
