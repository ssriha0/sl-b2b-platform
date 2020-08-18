/*
 * @(#)IBuyerMarketAdjBO.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.business.iBusiness.buyer;

//Service Live Library
import com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

//Java Library
import java.util.List;

/**
 * Interface for Buyer Market Adjustment Business Object
 * @author Mahmud Khair
 *
 */
public interface IBuyerMarketAdjBO {
	    /**
     * Retrieves a list of markets for for adjusting rates for the selected buyer
     * 
     * @param buyerId
     * @param sortColumnName
     * @param sortOrder
     * @return A list of BuyerMarketAdjVO
     * @throws DataServiceException
     */
    public List<BuyerMarketAdjVO> getBuyerMarkets(String buyerId, String sortColumnName, String sortOrder) throws BusinessServiceException;

    /**
     * Updates the adjustment rate for the selected market of the buyer
     * 
     * @param buyerMarketAdjVO
     * @throws DataServiceException
     */
    public void updateAdjustmentRate(BuyerMarketAdjVO buyerMarketAdjVO) throws DataServiceException;

}
