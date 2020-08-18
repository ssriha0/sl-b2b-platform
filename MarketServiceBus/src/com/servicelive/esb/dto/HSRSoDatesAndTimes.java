package com.servicelive.esb.dto;

import java.io.Serializable;

public class HSRSoDatesAndTimes implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orderTakenTime;
	
	private String orderTakenDate;
	
	private String promisedDate;
	
	private String promisedTimeFrom;
	
	private String promisedTimeTo;
	
	private String originalSchedDate;
	
	private String originalTimeFrom;
	
	private String originalTimeTo;
	
	private String purchaseDate;
	
	private String originalDeliveryDate;
	
	private String modifiedDate;
	
	private String modifiedTime;

	/**
	 * @return the orderTakenTime
	 */
	public String getOrderTakenTime() {
		return orderTakenTime;
	}

	/**
	 * @param orderTakenTime the orderTakenTime to set
	 */
	public void setOrderTakenTime(String orderTakenTime) {
		this.orderTakenTime = orderTakenTime;
	}

	/**
	 * @return the orderTakenDate
	 */
	public String getOrderTakenDate() {
		return orderTakenDate;
	}

	/**
	 * @param orderTakenDate the orderTakenDate to set
	 */
	public void setOrderTakenDate(String orderTakenDate) {
		this.orderTakenDate = orderTakenDate;
	}

	/**
	 * @return the promisedDate
	 */
	public String getPromisedDate() {
		return promisedDate;
	}

	/**
	 * @param promisedDate the promisedDate to set
	 */
	public void setPromisedDate(String promisedDate) {
		this.promisedDate = promisedDate;
	}

	/**
	 * @return the promisedTimeFrom
	 */
	public String getPromisedTimeFrom() {
		return promisedTimeFrom;
	}

	/**
	 * @param promisedTimeFrom the promisedTimeFrom to set
	 */
	public void setPromisedTimeFrom(String promisedTimeFrom) {
		this.promisedTimeFrom = promisedTimeFrom;
	}

	/**
	 * @return the promisedTimeTo
	 */
	public String getPromisedTimeTo() {
		return promisedTimeTo;
	}

	/**
	 * @param promisedTimeTo the promisedTimeTo to set
	 */
	public void setPromisedTimeTo(String promisedTimeTo) {
		this.promisedTimeTo = promisedTimeTo;
	}

	/**
	 * @return the originalSchedDate
	 */
	public String getOriginalSchedDate() {
		return originalSchedDate;
	}

	/**
	 * @param originalSchedDate the originalSchedDate to set
	 */
	public void setOriginalSchedDate(String originalSchedDate) {
		this.originalSchedDate = originalSchedDate;
	}

	/**
	 * @return the originalTimeFrom
	 */
	public String getOriginalTimeFrom() {
		return originalTimeFrom;
	}

	/**
	 * @param originalTimeFrom the originalTimeFrom to set
	 */
	public void setOriginalTimeFrom(String originalTimeFrom) {
		this.originalTimeFrom = originalTimeFrom;
	}

	/**
	 * @return the originalTimeTo
	 */
	public String getOriginalTimeTo() {
		return originalTimeTo;
	}

	/**
	 * @param originalTimeTo the originalTimeTo to set
	 */
	public void setOriginalTimeTo(String originalTimeTo) {
		this.originalTimeTo = originalTimeTo;
	}

	/**
	 * @return the purchaseDate
	 */
	public String getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return the originalDeliveryDate
	 */
	public String getOriginalDeliveryDate() {
		return originalDeliveryDate;
	}

	/**
	 * @param originalDeliveryDate the originalDeliveryDate to set
	 */
	public void setOriginalDeliveryDate(String originalDeliveryDate) {
		this.originalDeliveryDate = originalDeliveryDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
}
