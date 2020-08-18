package com.newco.marketplace.api.mobile.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("otherServices")
@XmlAccessorType(XmlAccessType.FIELD)
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
