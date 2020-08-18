/*
 * @(#)BuyerMarketAjdBOImpl.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.business.businessImpl.buyer;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerMarketAdjBO;
import com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerMarketAdjDAO;
import com.newco.marketplace.utils.StackTraceHelper;


/**
 * This is the implementation class for Buyer Market Adjustment Business Object
 * @author Mahmud Khair
 *
 */
public class BuyerMarketAdjBOImpl implements IBuyerMarketAdjBO {
	private IBuyerMarketAdjDAO buyerMarketAdjDao;
	private static Logger logger = Logger.getLogger(BuyerMarketAdjBOImpl.class.getName());
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.buyer.IBuyerMarketAdjBO#getBuyerMarkets(com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO)
	 */
	public List<BuyerMarketAdjVO> getBuyerMarkets (
			String buyerId, String sortColumnName, String sortOrder) 
			throws BusinessServiceException{
		List<BuyerMarketAdjVO> buyerMarketList = null;
		try {
			BuyerMarketAdjVO buyerMarketAdjVO = new BuyerMarketAdjVO();
			buyerMarketAdjVO.setBuyerId(Integer.parseInt(buyerId));
			buyerMarketAdjVO.setSortColumnName(sortColumnName);
			buyerMarketAdjVO.setSortOrder(sortOrder);
			buyerMarketList = buyerMarketAdjDao.getBuyerMarkets(buyerMarketAdjVO);
		} catch (Exception ex) {
			logger.error("[BuyerMarketAdjBOImps.getBuyerMarkets - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new BusinessServiceException("Error", ex);
		
		}
		
		return buyerMarketList;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.buyer.IBuyerMarketAdjBO#updateAdjustmentRate(com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO)
	 */
	public void updateAdjustmentRate(BuyerMarketAdjVO buyerMarketAdjVO)
			throws DataServiceException {
		buyerMarketAdjDao.updateAdjustmentRate(buyerMarketAdjVO);
	}
	
	public IBuyerMarketAdjDAO getBuyerMarketAdjDao() {
		return buyerMarketAdjDao;
	}
	
	public void setBuyerMarketAdjDao(IBuyerMarketAdjDAO buyerMarketAdjDao) {
		this.buyerMarketAdjDao= buyerMarketAdjDao;
	}

}
