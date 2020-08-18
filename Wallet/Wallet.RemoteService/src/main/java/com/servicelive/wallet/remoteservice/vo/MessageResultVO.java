package com.servicelive.wallet.remoteservice.vo;

import java.io.Serializable;

public class MessageResultVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6922060955575390948L;

	private String messageId;
	private Boolean result;
	private String errorMessage;
	private Long transactionId;
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public Long getTransactionId() {
		return transactionId;
	}
}
