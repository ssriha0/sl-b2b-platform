package com.newco.marketplace.api.mobile.beans.viewDashboard;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("spnBuyerDetailsList")
public class SpnBuyerDetailsList {
	
	@XStreamImplicit(itemFieldName ="spnBuyerDetail")
	private List<SpnBuyerDetail> spnBuyerDetail;

	public List<SpnBuyerDetail> getSpnBuyerDetail() {
		return spnBuyerDetail;
	}

	public void setSpnBuyerDetail(List<SpnBuyerDetail> spnBuyerDetail) {
		this.spnBuyerDetail = spnBuyerDetail;
	}
}
