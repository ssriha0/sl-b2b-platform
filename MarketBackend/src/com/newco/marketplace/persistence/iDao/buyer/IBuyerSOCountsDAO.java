package com.newco.marketplace.persistence.iDao.buyer;

import com.newco.marketplace.exception.DBException;

public interface IBuyerSOCountsDAO {
	/**
     * Calculates SOM tab counts and updates them in the summary table
     * 
     * @return
     * @throws DBException
     */
    public void updateSOMCountsForBuyer() throws DBException;
    /**
     * updates number of service orders 
     * completed by different buyers in the buyer 
     * table
     * 
     * @return
     * @throws DBException
     */
    public void updateBuyerCompletedOrdersCount() throws DBException;
}
