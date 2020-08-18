package com.newco.marketplace.api.mobile.beans.Filter;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("serviceProName")
public class ServiceProNameCriteria {

	@XStreamImplicit(itemFieldName="serviceProName")
	private List<ServiceProName> serviceProName;

	public List<ServiceProName> getServiceProName() {
		return serviceProName;
	}

	public void setServiceProName(List<ServiceProName> serviceProName) {
		this.serviceProName = serviceProName;
	}
}
