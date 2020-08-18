package com.newco.marketplace.api.beans.so.cancel.v1_2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class maps the work order SKU in Cancellation request (API v1.2)
 * */
@XStreamAlias("workOrder")
public class WorkOrderSKU {
	
	@XStreamAlias("cancellationSKU")
	private String cancellationSKU;
	
	@XStreamAlias("cancellationAmount")
	private	Double cancellationAmount;

	public String getCancellationSKU() {
		return cancellationSKU;
	}

	public void setCancellationSKU(String cancellationSKU) {
		this.cancellationSKU = cancellationSKU;
	}

	public Double getCancellationAmount() {
		return cancellationAmount;
	}

	public void setCancellationAmount(Double cancellationAmount) {
		this.cancellationAmount = cancellationAmount;
	}
	
}
