package com.newco.marketplace.dto.vo.buyerCallbackEvent;

import java.io.Serializable;

public class BuyerCallbackEvent  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer eventId;
	private String eventName;
	private Integer serviceId;
	private Integer actionId;
	private Integer buyerId;
	private Integer filterId;
	private String filterNames;
	/**
	 * 
	 * @return filterNames
	 */
	public String getFilterNames() {
		return filterNames;
	}
	/**
	 * 
	 * @param filterNames
	 */
	public void setFilterNames(String filterNames) {
		this.filterNames = filterNames;
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
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}
	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	/**
	 * @return the serviceId
	 */
	public Integer getServiceId() {
		return serviceId;
	}
	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
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
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	/**
	 * @return the filterId
	 */
	public Integer getFilterId() {
		return filterId;
	}
	/**
	 * @param filterId the filterId to set
	 */
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	
	
	

}

