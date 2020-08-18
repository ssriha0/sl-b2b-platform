package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("currentOrdersSubStatusFilterList")
public class CurrentOrdersSubStatusFilterList {
	
	@XStreamImplicit(itemFieldName="currentOrdersSubStatus")
	private List<CurrentOrdersSubStatusFilterVO> currentOrdersSubStatus;

	public List<CurrentOrdersSubStatusFilterVO> getCurrentOrdersSubStatus() {
		return currentOrdersSubStatus;
	}

	public void setCurrentOrdersSubStatus(
			List<CurrentOrdersSubStatusFilterVO> currentOrdersSubStatus) {
		this.currentOrdersSubStatus = currentOrdersSubStatus;
	}


	

	

	

}
