package com.newco.marketplace.vo;

public class RescheduleRequest {
	Integer correlationId;
	ServiceOrderReschedule serviceOrder;
	public Integer getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(Integer correlationId) {
		this.correlationId = correlationId;
	}
	public ServiceOrderReschedule getServiceOrder() {
		return serviceOrder;
	}
	public void setServiceOrder(ServiceOrderReschedule serviceOrder) {
		this.serviceOrder = serviceOrder;
	}
	
}
