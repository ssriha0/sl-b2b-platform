package com.newco.marketplace.api.mobile.beans.viewDashboard;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("spnDetails")
public class SpnDetails {

	@XStreamImplicit(itemFieldName ="spnDetail")
	private List<SpnDetail> spnDetail;

	public List<SpnDetail> getSpnDetail() {
		return spnDetail;
	}

	public void setSpnDetail(List<SpnDetail> spnDetail) {
		this.spnDetail = spnDetail;
	}
}
