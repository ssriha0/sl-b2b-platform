package com.newco.marketplace.buyeroutboundnotification.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ORDERS")
public class RequestOrders {
	
	@XStreamImplicit(itemFieldName="ORDER")
	private List<RequestOrder> order;

	public List<RequestOrder> getOrder() {
		return order;
	}

	public void setOrder(List<RequestOrder> order) {
		this.order = order;
	}
	
	

}
