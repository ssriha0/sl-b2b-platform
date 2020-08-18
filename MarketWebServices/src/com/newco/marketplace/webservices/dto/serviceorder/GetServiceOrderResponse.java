package com.newco.marketplace.webservices.dto.serviceorder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("soResponse")
public class GetServiceOrderResponse {
	@XStreamAlias("orderStatus")
	private OrderStatus orderStatus;

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
