package com.servicelive.esb.integration.domain;

public class BuyerNotificationResponse {

	private Long buyerNotificationResponseId;
	private Long transactionId;
	private Long buyerNotificationId;
	private String returnCode;
	private Boolean responseStatusSuccess;
	private String responseMessage;
	
	public Long getBuyerNotificationResponseId() {
		return buyerNotificationResponseId;
	}
	public void setBuyerNotificationResponseId(Long buyerNotificationResponseId) {
		this.buyerNotificationResponseId = buyerNotificationResponseId;
	}
	public Long getBuyerNotificationId() {
		return buyerNotificationId;
	}
	public void setBuyerNotificationId(Long buyerNotificationId) {
		this.buyerNotificationId = buyerNotificationId;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public Boolean getResponseStatusSuccess() {
		return responseStatusSuccess;
	}
	public void setResponseStatusSuccess(Boolean responseStatusSuccess) {
		this.responseStatusSuccess = responseStatusSuccess;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public Long getTransactionId() {
		return transactionId;
	}
}
