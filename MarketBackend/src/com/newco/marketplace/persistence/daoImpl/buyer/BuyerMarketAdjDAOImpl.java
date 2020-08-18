/*
 * @(#)BuyerMarketAdjDAOImpl.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.persistence.daoImpl.buyer;

import com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerMarketAdjDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Mahmud Khair
 *
 */
public class BuyerMarketAdjDAOImpl extends ABaseImplDao implements IBuyerMarketAdjDAO {
	//Create the logger
	private static final Logger logger = Logger.getLogger(BuyerMarketAdjDAOImpl.class.getName());
	
	/**
     * Retrieves a list of markets for for adjusting rates for the selected buyer
     * 
     * @param buyerId
     * @return A list of BuyerMarketAdjVO
     * @throws DataServiceException
     */
    public List<BuyerMarketAdjVO> getBuyerMarkets(BuyerMarketAdjVO buyerMarketAdjVO) throws DataServiceException {
    	List<BuyerMarketAdjVO> al = null;

		try{
			al = queryForList("buyerMarketAdj.getBuyerMarkets", buyerMarketAdjVO);
		} catch (Exception ex) {
			logger.error("[BuyerMarketAdjDAOImpl.selectByBuyerId() - Exception] " + StackTraceHelper.getStackTrace(ex));
		            throw new DataServiceException("Error", ex);
		}
		return al;
	}

    /**
     * Updates the adjustment rate for the selected market of the buyer
     * 
     * @param buyerMarketAdjVO 
     * @throws DataServiceException
     */
    public void updateAdjustmentRate(BuyerMarketAdjVO buyerMarketAdjVO) throws DataServiceException {
    	try
        {
            update("buyerMarketAdj.adjustment.update", buyerMarketAdjVO);

        }//end try
        catch (Exception ex)
        {
            logger.info("[Exception thrown updating adjustment rate] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
            
        }
	}	
}//End of BuyerMarketAdjDAOImpl