package com.newco.marketplace.buyeroutboundnotification.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ORDERS")
public class ResponseOrders {

	@XStreamImplicit(itemFieldName="ORDER")
	private List<ResponseOrder> resOrder;

	public List<ResponseOrder> getResOrder() {
		return resOrder;
	}

	public void setResOrder(List<ResponseOrder> resOrder) {
		this.resOrder = resOrder;
	}

}
