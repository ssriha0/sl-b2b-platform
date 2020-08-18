package com.newco.marketplace.business.businessImpl.vibePostAPI;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author
 *
 */
public class BuyerCallbackNotificationStatusVo {

	private long notificationId;
	private long noOfRetries;
	private String status;
	private String response;
	private String exception;

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public long getNoOfRetries() {
		return noOfRetries;
	}

	public void setNoOfRetries(long noOfRetries) {
		this.noOfRetries = noOfRetries;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
}
