package com.servicelive.integrationtest.domain;

public class ServiceOrder {
	private String orderId;
	private String externalOrderNum;
	private String label;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getExternalOrderNum() {
		return externalOrderNum;
	}
	public void setExternalOrderNum(String externalOrderNum) {
		this.externalOrderNum = externalOrderNum;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	
}
