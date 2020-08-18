package com.newco.marketplace.utils;

public class RequestInfo {
	private String ipAddress;
	private Integer requestTimeOutSeconds;
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the requestTimeOutSeconds
	 */
	public Integer getRequestTimeOutSeconds() {
		return requestTimeOutSeconds;
	}
	/**
	 * @param requestTimeOutSeconds the requestTimeOutSeconds to set
	 */
	public void setRequestTimeOutSeconds(Integer requestTimeOutSeconds) {
		this.requestTimeOutSeconds = requestTimeOutSeconds;
	}
}
