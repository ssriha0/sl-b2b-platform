package com.newco.marketplace.api.beans.so.retrieve.v1_7;

import java.util.List;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("serviceorders")
public class ServiceOrders {
	
	@XStreamImplicit(itemFieldName="serviceorder")
	private List<RetrieveServiceOrder> serviceorderList;

	public List<RetrieveServiceOrder> getServiceorders() {
		return serviceorderList;
	}

	public void setServiceorders(List<RetrieveServiceOrder> serviceorderList) {
		this.serviceorderList = serviceorderList;
	}

}
