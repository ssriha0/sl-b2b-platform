package com.newco.marketplace.api.beans.serviceOrderDetail;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("serviceOrders")
public class ServiceOrders {
	
	@XStreamImplicit(itemFieldName="serviceOrder")
	private List<ServiceOrder> serviceOrder;

	public List<ServiceOrder> getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(List<ServiceOrder> serviceOrder) {
		this.serviceOrder = serviceOrder;
	}
}
