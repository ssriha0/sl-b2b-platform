package com.servicelive.esb.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("CallCloseInstallationServiceOrders")
public class NPSCallCloseServiceOrders {

	@XStreamImplicit(itemFieldName="CallCloseInstallationServiceOrder")
	private List<NPSCallCloseServiceOrder> npsCallCloseServiceOrder;

	public List<NPSCallCloseServiceOrder> getNpsCallCloseServiceOrder() {
		return npsCallCloseServiceOrder;
	}

	public void setNpsCallCloseServiceOrder(
			List<NPSCallCloseServiceOrder> npsCallCloseServiceOrder) {
		this.npsCallCloseServiceOrder = npsCallCloseServiceOrder;
	}

}
