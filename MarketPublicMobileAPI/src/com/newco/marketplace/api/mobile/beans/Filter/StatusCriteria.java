package com.newco.marketplace.api.mobile.beans.Filter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("orderStatuses")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusCriteria {

	@XStreamImplicit(itemFieldName="orderStatus")
	private List<StatusValue> orderStatus;

	public List<StatusValue> getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(List<StatusValue> orderStatus) {
		this.orderStatus = orderStatus;
	}

	
}
