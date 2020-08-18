package com.newco.marketplace.business.businessImpl.alert;

import java.util.Date;

public class BuyerCallbackNotification {

private String buyerId;
private String event;
private long notificationId;
private String data;
private int retries;

public String getBuyerId() {
	return buyerId;
}
public void setBuyerId(String buyerId) {
	this.buyerId = buyerId;
}
public String getEvent() {
	return event;
}
public void setEvent(String event) {
	this.event = event;
}
public long getNotificationId() {
	return notificationId;
}
public void setNotificationId(long notificationId) {
	this.notificationId = notificationId;
}
public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}
public int getRetries() {
	return retries;
}
public void setRetries(int retries) {
	this.retries = retries;
}

}
