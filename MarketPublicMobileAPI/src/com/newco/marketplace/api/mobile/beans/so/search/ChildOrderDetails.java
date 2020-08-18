package com.newco.marketplace.api.mobile.beans.so.search;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ChildOrderDetails {

	private List<OrderDetail> childOrderDetail;

	public List<OrderDetail> getChildOrderDetail() {
		return childOrderDetail;
	}

	public void setChildOrderDetail(List<OrderDetail> childOrderDetail) {
		this.childOrderDetail = childOrderDetail;
	}
	
}
