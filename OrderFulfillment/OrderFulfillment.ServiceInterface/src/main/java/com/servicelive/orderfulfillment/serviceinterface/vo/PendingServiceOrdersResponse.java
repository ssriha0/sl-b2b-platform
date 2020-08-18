package com.servicelive.orderfulfillment.serviceinterface.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PendingServiceOrdersResponse {

	@XmlElementWrapper(name = "serviceOrders")
	@XmlElement(name = "serviceOrder")
	List<ServiceOrder> pendingServiceOrders = new ArrayList<ServiceOrder>();

	public List<ServiceOrder> getPendingServiceOrders() {
		return pendingServiceOrders;
	}

	public void setPendingServiceOrders(List<ServiceOrder> pendingServiceOrders) {
		this.pendingServiceOrders = pendingServiceOrders;
	}
}
