/*
 * @(#)IBuyerMarketAdjDelegate.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.web.delegates;

//Service Live Library
import com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;



//Java Library
import java.util.List;

/**
 * Interface for Buyer Market Adjustment Business Object
 * @author Mahmud Khair/Carlos Garcia
 *
 */

public interface IBuyerMarketAdjDelegate {
	
	/**
	 * @param buyerID
	 * @param sortColumnName
	 * @param sortOrder
	 * @return A list of BuyerMarketAdjVO
	 * @throws BusinessServiceException
	 */
	public List<BuyerMarketAdjVO> getBuyerMarkets(String buyerID, String sortColumnName, 
			String sortOrder) throws BusinessServiceException;
	/**
	 * @param buyerMarketAdjVO
	 * @throws DataServiceException
	 */
	public void updateAdjustmentRate(BuyerMarketAdjVO buyerMarketAdjVO) throws DataServiceException;
	
}