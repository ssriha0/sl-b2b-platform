/*
 * @(#)IBuyerMarketAdjDelegateImps.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */

//Service Live Library
package com.newco.marketplace.web.delegatesImpl;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerMarketAdjBO;
import com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.web.delegates.IBuyerMarketAdjDelegate;

//Java and other Library
import java.util.*;

import org.apache.log4j.Logger;



/**
 * Interface for Buyer Market Adjustment Business Object
 * @author Mahmud Khair/Carlos Garcia
 *
 */

public class BuyerMarketAdjDelegateImpl implements IBuyerMarketAdjDelegate
{
	private static final Logger logger = Logger.getLogger(BuyerMarketAdjDelegateImpl.class.getName());
	private IBuyerMarketAdjBO buyerMarketAdjBO;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IBuyerMarketAdjDelegate#getBuyerMarkets(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<BuyerMarketAdjVO> getBuyerMarkets(String buyerID, String sortColumn, 
			String sortOrder)throws BusinessServiceException
	{
		List<BuyerMarketAdjVO> buyerMarketAdjList = 
			buyerMarketAdjBO.getBuyerMarkets(buyerID, sortColumn, sortOrder);
		LinkedHashMap<Integer, BuyerMarketAdjVO> buyerMarketHash = new LinkedHashMap<Integer,BuyerMarketAdjVO>(); 
		for (BuyerMarketAdjVO buyerMarketAdjVO : buyerMarketAdjList) {
			if (!buyerMarketHash.containsKey(buyerMarketAdjVO.getMarketId())) {
				buyerMarketHash.put(buyerMarketAdjVO.getMarketId(), buyerMarketAdjVO);
			}
		}
		return new ArrayList<BuyerMarketAdjVO> (buyerMarketHash.values());
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IBuyerMarketAdjDelegate#updateAdjustmentRate(com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO)
	 */
	public void updateAdjustmentRate(BuyerMarketAdjVO buyerMarketAdjVO)
			throws DataServiceException {
		buyerMarketAdjBO.updateAdjustmentRate(buyerMarketAdjVO);
		
	}
		
	/**
	 * @return IBuyerMarketAdjBO
	 */
	public IBuyerMarketAdjBO getBuyerMarketAdjBO() {
		return buyerMarketAdjBO;
	}
	
	/**
	 * Sets the buyerMarketAdjBO property through spring
	 * 
	 */
	public void setBuyerMarketAdjBO(IBuyerMarketAdjBO buyerMarketAdjBO) {
		this.buyerMarketAdjBO= buyerMarketAdjBO;
	}
}