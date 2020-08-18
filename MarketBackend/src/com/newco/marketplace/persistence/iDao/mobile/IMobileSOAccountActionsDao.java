package com.newco.marketplace.persistence.iDao.mobile;

import com.newco.marketplace.exception.core.DataServiceException;



public interface IMobileSOAccountActionsDao {

	/**
	 * 
	 * to get corresponding card type id for card type
	 * 
	 * @param ccType
	 * @return
	 */
	public Integer getCardType(String ccType) throws DataServiceException;
	
}
