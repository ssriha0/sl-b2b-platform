/**
 * 
 */
package com.servicelive.esb.integration.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author sahmed
 *
 */
public class BuyerNotification implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 6711678848171877834L;
	private long buyerNotificationId;
	private Long transactionId;
	private String notificationEvent;
	private String notificationEventSubType;
	private Long relatedServiceOrderId;

	public BuyerNotification() {}

	public BuyerNotification(long buyerNotificationId, Long transactionId,
			String notificationEvent, String notificationEventSubType,
			Long relatedServiceOrderId) {
		this.buyerNotificationId = buyerNotificationId;
		this.transactionId = transactionId;
		this.notificationEvent = notificationEvent;
		this.setNotificationEventSubType(notificationEventSubType);
		this.relatedServiceOrderId = relatedServiceOrderId;
	}

	public long getBuyerNotificationId() {
		return this.buyerNotificationId;
	}
	public String getNotificationEvent() {
		return this.notificationEvent;
	}
	public String getNotificationEventSubType() {
		return notificationEventSubType;
	}
	public Long getTransactionId() {
		return this.transactionId;
	}
	public Long getRelatedServiceOrderId() {
		return relatedServiceOrderId;
	}
	public void setBuyerNotificationId(long buyerNotificationId) {
		this.buyerNotificationId = buyerNotificationId;
	}
	public void setNotificationEvent(String notificationEvent) {
		this.notificationEvent = notificationEvent;
	}
	public void setNotificationEventSubType(String notificationEventSubType) {
		this.notificationEventSubType = notificationEventSubType;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public void setRelatedServiceOrderId(Long relatedServiceOrderId) {
		this.relatedServiceOrderId = relatedServiceOrderId;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
