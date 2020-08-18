package com.newco.marketplace.api.beans.searchCriteria;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/07/03
 * the response for SOGetSearchCriteria
 *
 */
@XStreamAlias("orderStatuses")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderStatuses {
	
	@XStreamImplicit(itemFieldName="orderStatus")
	private List<OrderStatus> orderStatus;

	public List<OrderStatus> getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(List<OrderStatus> orderStatus) {
		this.orderStatus = orderStatus;
	}

	
	
}
