package com.newco.marketplace.inhomeoutboundnotification.vo;


public class InHomeOutBoundDetailsVO {
	
	private String serviceName;
	private String serviceVersion;
	private String clientId;
	private String userId;
	private String currentDateTime;
	private String correlationId;
	private String orderType;
	private Integer unitNum;
	private Integer orderNum;
	private String fromFunction;
	private String toFunction;
	private String empId;
	private String Message;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCurrentDateTime() {
		return currentDateTime;
	}
	public void setCurrentDateTime(String currentDateTime) {
		this.currentDateTime = currentDateTime;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getUnitNum() {
		return unitNum;
	}
	public void setUnitNum(Integer unitNum) {
		this.unitNum = unitNum;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getFromFunction() {
		return fromFunction;
	}
	public void setFromFunction(String fromFunction) {
		this.fromFunction = fromFunction;
	}
	public String getToFunction() {
		return toFunction;
	}
	public void setToFunction(String toFunction) {
		this.toFunction = toFunction;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	
	
}
