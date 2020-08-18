package com.newco.marketplace.api.mobile.beans.so.search;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("orderDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDetails {
	
	

	@XStreamImplicit(itemFieldName="orderDetail")
	private List<OrderDetail> orderDetail;

	public List<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}
	
}
