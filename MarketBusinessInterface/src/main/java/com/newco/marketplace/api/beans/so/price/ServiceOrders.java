package com.newco.marketplace.api.beans.so.price;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("serviceOrders") 
public class ServiceOrders {
	
	@XStreamImplicit(itemFieldName="serviceOrder")
	private List<ServiceOrderPriceHistory> orderPriceHistories;

	public List<ServiceOrderPriceHistory> getOrderPriceHistories() {
		return orderPriceHistories;
	}

	public void setOrderPriceHistories(
			List<ServiceOrderPriceHistory> orderPriceHistories) {
		this.orderPriceHistories = orderPriceHistories;
	}
	
}
