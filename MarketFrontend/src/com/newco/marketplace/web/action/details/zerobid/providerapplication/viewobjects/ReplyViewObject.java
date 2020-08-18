package com.newco.marketplace.web.action.details.zerobid.providerapplication.viewobjects;

import java.io.Serializable;


public class ReplyViewObject implements Serializable {

	private static final long serialVersionUID = 1211660052894289464L;
	
	private Long parentMessageId;
	private String replyText;
	private Long buyerId;
	private Long orderId;
	private Long providerId;

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public String getReplyText() {
		return replyText;
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

	public void setParentMessageId(Long parentMessageId) {
		this.parentMessageId = parentMessageId;
	}

	public Long getParentMessageId() {
		return parentMessageId;
	}
	
}
