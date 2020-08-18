/*
 * @(#)IBuyerMarketAdjDAO.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.persistence.iDao.buyer;

import com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO;
import com.newco.marketplace.exception.core.DataServiceException;
import java.util.List;

/**
 * The interface for Buyer Market Adjustment DAO
 * @author Mahmud Khair
 *
 */
public interface IBuyerMarketAdjDAO {
    /**
     * Retrieves a list of markets for for adjusting rates for the selected buyer
     * 
     * @param buuerMarketAdjVO
     * @return A list of BuyerMarketAdjVO
     * @throws DataServiceException
     */
    public List<BuyerMarketAdjVO> getBuyerMarkets(BuyerMarketAdjVO buyerMarketAdjVO) throws DataServiceException;

    /**
     * Updates the adjustment rate for the selected market of the buyer
     * 
     * @param buyerMarketAdjVO
     * @throws DataServiceException
     */
    public void updateAdjustmentRate(BuyerMarketAdjVO buyerMarketAdjVO) throws DataServiceException;
   
} //End of IBuyerMarketAjdDAO