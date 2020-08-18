package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("otherServices")
public class OtherServices {

	@XStreamImplicit(itemFieldName="otherService")
	private List<OtherService> otherService;

	public List<OtherService> getOtherService() {
		return otherService;
	}

	public void setOtherService(List<OtherService> otherService) {
		this.otherService = otherService;
	}

}