package com.newco.marketplace.buyercallbacknotificationscheduler.vo;

public class BuyerCallBackNotificationSchedulerVO {
	

	private long notificationId;
	private String buyerId;
	private String soId;
	private Integer eventId;
	private Integer actionId;
	private String requestData;
	private long noOfRetries; 
	
	
	/**
	 * @return the notificationId
	 */
	public long getNotificationId() {
		return notificationId;
	}
	/**
	 * @param notificationId the notificationId to set
	 */
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	/**
	 * @return the buyerId
	 */
	public String getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}
	/**
	 * @param soId the soId to set
	 */
	public void setSoId(String soId) {
		this.soId = soId;
	}
	/**
	 * @return the eventId
	 */
	public Integer getEventId() {
		return eventId;
	}
	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	/**
	 * @return the actionId
	 */
	public Integer getActionId() {
		return actionId;
	}
	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	/**
	 * @return the requestData
	 */
	public String getRequestData() {
		return requestData;
	}
	/**
	 * @param requestData the requestData to set
	 */
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	
	public long getNoOfRetries() {
		return noOfRetries;
	}
	
	public void setNoOfRetries(long noOfRetries) {
		this.noOfRetries = noOfRetries;
	}
}
