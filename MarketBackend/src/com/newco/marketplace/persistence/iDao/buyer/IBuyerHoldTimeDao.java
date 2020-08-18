/*
 * @(#)IByerHoldTimeDao.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.persistence.iDao.buyer;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.buyer.BuyerHoldTimeVO;

public interface IBuyerHoldTimeDao {
	

	 /**
     * Retrieves a BuyerHoldTime object for the specified day difference and buyer ID
     * 
     * @param dayDiff
     * @param buyerId
     * @return BuyerHoldTime
     * @throws DataServiceException
     */
	BuyerHoldTimeVO getBuyerHoldTimeByDayDiffAndBuyerID(Integer dayDiff, Integer buyerId) throws DataServiceException;
	
	/**
     * Inserts a BuyerHoldTime object into the buyer_hold_time table
     * 
     * @param BuyerHoldTime
     * @return BuyerHoldTime ID
     * @throws DataServiceException
     */
	Integer insert(BuyerHoldTimeVO buyerHoldTime) throws DataServiceException;
	
	/**
     * Gets the maximum number of days of difference for holding a order for a specific buyer
     * 
     * @param buyerHoldTime
     * @return Maximum of Day_diff
     * @throws DataServiceException
     */
	Integer getMaxDayDiff(BuyerHoldTimeVO buyerHoldTime) throws DataServiceException;
	
}