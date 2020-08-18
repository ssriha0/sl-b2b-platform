/*
 * @(#)ByerHoldTimeVO.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.vo.buyer;

import com.sears.os.vo.SerializableBaseVO;

public class BuyerHoldTimeVO extends SerializableBaseVO{
	/**
	 * 
	 * This field corresponds to the database column
	 * buyer_hold_time.hold_time_id
	 */
	private Integer holdTimeId;

	/**
	 * 
	 * This field corresponds to the database column buyer_hold_time.day_diff
	 */
	private Integer dayDiff;

	/**
	 * 
	 * This field corresponds to the database column buyer_hold_time.hold_time
	 */
	private Integer holdTime;

	/**
	 * 
	 * This field corresponds to the database column buyer_hold_time.buyer_id
	 */
	private Integer buyerId;

	/**
	 * This method returns the value of the database column buyer_hold_time.hold_time_id
	 * 
	 * @return the value of buyer_hold_time.hold_time_id
	 */
	public Integer getHoldTimeId() {
		return holdTimeId;
	}

	/**
	 * This method sets the value of the database column buyer_hold_time.hold_time_id
	 * 
	 * @param holdTimeId
	 *            the value for buyer_hold_time.hold_time_id
	 */
	public void setHoldTimeId(Integer holdTimeId) {
		this.holdTimeId = holdTimeId;
	}

	/**
	 * This method returns the value of the database column buyer_hold_time.day_diff
	 * 
	 * @return the value of buyer_hold_time.day_diff
	 */
	public Integer getDayDiff() {
		return dayDiff;
	}

	/**
	 * This method sets the value of the database column buyer_hold_time.day_diff
	 * 
	 * @param dayDiff
	 *            the value for buyer_hold_time.day_diff
	 */
	public void setDayDiff(Integer dayDiff) {
		this.dayDiff = dayDiff;
	}

	/**
	 * This method returns the value of the database column buyer_hold_time.hold_time
	 * 
	 * @return the value of buyer_hold_time.hold_time in minutes
	 */
	public Integer getHoldTime() {
		return holdTime;
	}

	/**
	 * This method sets the value of the database column buyer_hold_time.hold_time
	 * 
	 * @param holdTime
	 *            the value for buyer_hold_time.hold_time in minutes
	 */
	public void setHoldTime(Integer holdTime) {
		this.holdTime = holdTime;
	}

	/**
	 * This method returns the value of the database column buyer_hold_time.buyer_id
	 * 
	 * @return the value of buyer_hold_time.buyer_id
	 */
	public Integer getBuyerId() {
		return buyerId;
	}

	/**
	 * This method sets the value of the database column buyer_hold_time.buyer_id
	 * 
	 * @param buyerId
	 *            the value for buyer_hold_time.buyer_id
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
}