package com.newco.marketplace.webservices.dto.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.webservices.response.WSProcessResponse;

public class CancelSOResponse extends WSProcessResponse {
	
	private String orderStatus;
	private double penalityAmount;
	private boolean hasError = false;
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		    .append("ProcessCode", getProcessCode())
			.append("ProcessStatus", getProcessStatus())
			.append("SLServiceOrderId()", getSLServiceOrderId())
			.append("ClientServiceOrderId",getClientServiceOrderId())
			.append("Errors",getErrorList())
			.toString();
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public double getPenalityAmount() {
		return penalityAmount;
	}

	public void setPenalityAmount(double penalityAmount) {
		this.penalityAmount = penalityAmount;
	}
	

}
