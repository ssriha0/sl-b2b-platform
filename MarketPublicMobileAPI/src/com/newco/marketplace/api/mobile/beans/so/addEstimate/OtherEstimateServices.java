package com.newco.marketplace.api.mobile.beans.so.addEstimate;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("otherEstimateServices")
public class OtherEstimateServices {

	@XStreamImplicit(itemFieldName="otherEstimateService")
	private List<OtherEstimateService> otherEstimateService;

	public List<OtherEstimateService> getOtherEstimateService() {
		return otherEstimateService;
	}

	public void setOtherEstimateService(
			List<OtherEstimateService> otherEstimateService) {
		this.otherEstimateService = otherEstimateService;
	}

	

}
