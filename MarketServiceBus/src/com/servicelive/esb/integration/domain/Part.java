package com.servicelive.esb.integration.domain;

public class Part {
	private Long serviceOrderId;
	private String manufacturer;
	private String partName;
	private String modelNumber;
	private String description;
	private Integer quantity;
	private String inboundCarrier;
	private String inboundTrackingNumber;
	private String outboundCarrier;
	private String outboundTrackingNumber;
	private String classCode;
	private String classComments;
	
	public Long getServiceOrderId() {
		return serviceOrderId;
	}
	public void setServiceOrderId(Long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getInboundCarrier() {
		return inboundCarrier;
	}
	public void setInboundCarrier(String inboundCarrier) {
		this.inboundCarrier = inboundCarrier;
	}
	public String getInboundTrackingNumber() {
		return inboundTrackingNumber;
	}
	public void setInboundTrackingNumber(String inboundTrackingNumber) {
		this.inboundTrackingNumber = inboundTrackingNumber;
	}
	public String getOutboundCarrier() {
		return outboundCarrier;
	}
	public void setOutboundCarrier(String outboundCarrier) {
		this.outboundCarrier = outboundCarrier;
	}
	public String getOutboundTrackingNumber() {
		return outboundTrackingNumber;
	}
	public void setOutboundTrackingNumber(String outboundTrackingNumber) {
		this.outboundTrackingNumber = outboundTrackingNumber;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassComments(String classComments) {
		this.classComments = classComments;
	}
	public String getClassComments() {
		return classComments;
	}
	
}
