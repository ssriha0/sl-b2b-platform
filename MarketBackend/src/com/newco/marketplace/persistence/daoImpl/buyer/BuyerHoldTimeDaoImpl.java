/*
 * @(#)ByerHoldTimeDaoImpl.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.persistence.daoImpl.buyer;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerHoldTimeDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.buyer.BuyerHoldTimeVO;
import com.sears.os.dao.impl.ABaseImplDao;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;


public class BuyerHoldTimeDaoImpl extends ABaseImplDao implements IBuyerHoldTimeDao {

	//Create the logger
	private static final Logger logger = Logger.getLogger(BuyerHoldTimeDaoImpl.class.getName());
	
	/**
     * This method corresponds to the database table buyer_hold_time
     */
    public BuyerHoldTimeDaoImpl() {
        super();
    }



	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyer.IByerHoldTimeDao#getBuyerHoldTimebyDayDiffAndBuyerID(java.lang.Integer, java.lang.Integer)
	 */
	public BuyerHoldTimeVO getBuyerHoldTimeByDayDiffAndBuyerID(Integer dayDiff,
			Integer buyerId) throws DataServiceException {
		BuyerHoldTimeVO buyerHoldTime = new BuyerHoldTimeVO();
		buyerHoldTime.setDayDiff(dayDiff);
		buyerHoldTime.setBuyerId(buyerId);
		try{
			buyerHoldTime = (BuyerHoldTimeVO)queryForObject("buyerHoldTime.getBuyerHoldTimeByDayDiffAndBuyerID", buyerHoldTime);
			return buyerHoldTime;
		} catch (Exception ex) {
			logger.error("[ByerHoldTimeDaoImpl.getBuyerHoldTimeByDayDiffAndBuyerID() - Exception] " + StackTraceHelper.getStackTrace(ex));
		            throw new DataServiceException("Error", ex);
		}
	}



	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyer.IBuyerHoldTimeDao#insert(com.newco.marketplace.vo.buyer.BuyerHoldTimeVO)
	 */
	public Integer insert(BuyerHoldTimeVO buyerHoldTime)
			throws DataServiceException {
		Integer buyerHoldTimeId = null;
		try {
			buyerHoldTimeId = (Integer) insert("buyerHoldTime.insert", buyerHoldTime);
		} catch (DataAccessException e) {
			logger.error("Error returned trying to insert BuyerHoldTime",e);
			throw new DataServiceException("Error returned trying to insert buyer hold time",e);
		}
		return buyerHoldTimeId;
	}



	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyer.IBuyerHoldTimeDao#getMaxDayDiff(com.newco.marketplace.vo.buyer.BuyerHoldTimeVO)
	 */
	public Integer getMaxDayDiff(BuyerHoldTimeVO buyerHoldTime)
			throws DataServiceException {
		
		Integer maxDayDiff = null;
		try {
			maxDayDiff = (Integer) queryForObject("buyerHoldTime.getMaxDayDiff", buyerHoldTime);
		} catch (DataAccessException e) {
			logger.error("Error returned trying to get maximum of day difference",e);
			throw new DataServiceException("Error returned trying to get maximum of day difference",e);
		}
		return maxDayDiff;
	}
}