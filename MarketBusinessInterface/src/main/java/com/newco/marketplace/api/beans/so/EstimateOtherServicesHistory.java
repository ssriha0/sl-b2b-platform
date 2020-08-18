package com.newco.marketplace.api.beans.so;

import java.util.List;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("otherServices")
public class EstimateOtherServicesHistory {

	@XStreamImplicit(itemFieldName="otherService")
	private List<EstimateOtherServiceHistory> otherService;

	public List<EstimateOtherServiceHistory> getOtherService() {
		return otherService;
	}

	public void setOtherService(List<EstimateOtherServiceHistory> otherService) {
		this.otherService = otherService;
	}

}