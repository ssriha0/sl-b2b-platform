package com.newco.marketplace.web.action.details.zerobid.providerapplication.viewobjects;


public class MessageViewObject  {

	private static final long serialVersionUID = 1211660052894289464L;
	
	private String messageText;
	private Long buyerId;
	private Long orderId;
	private Long providerId;

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getMessageText() {
		return messageText;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}
	
}
