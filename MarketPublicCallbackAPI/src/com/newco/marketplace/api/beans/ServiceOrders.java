package com.newco.marketplace.api.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("serviceorders")
public class ServiceOrders {

	@XStreamImplicit(itemFieldName = "serviceorder")
	private List<RetrieveServiceOrder> serviceorderList;

	public List<RetrieveServiceOrder> getServiceorderList() {
		return serviceorderList;
	}

	public void setServiceorderList(List<RetrieveServiceOrder> serviceorderList) {
		this.serviceorderList = serviceorderList;
	}

	
}
