package com.newco.marketplace.webservices.dto.serviceorder;

import com.newco.marketplace.webservices.response.WSProcessResponse;

public class RouteResponse extends WSProcessResponse {
	
	private String orderId;
	private String errorCode;
	private String errorMessage;
	private boolean hasError = false;
	
//	public String toString() {
//		return new ToStringBuilder(this)
//		    .append("processId", getProcessId())
//			.append("code", getCode())
//			.append("subCode", getSubCode())
//			.append("messages", getMessages())
//			.append("orderId", getOrderId())
//			.toString();
//	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	
	
	
	

}
