package com.newco.marketplace.api.beans.closedOrders;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("serviceorders")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClosedServiceOrders {
	
	@XStreamAlias("serviceorder")
	@XStreamImplicit(itemFieldName="serviceorder")
	private List<ClosedServiceOrder> serviceorder;

	public List<ClosedServiceOrder> getServiceorder() {
		return serviceorder;
	}

	public void setServiceorder(List<ClosedServiceOrder> serviceorder) {
		this.serviceorder = serviceorder;
	}

}
