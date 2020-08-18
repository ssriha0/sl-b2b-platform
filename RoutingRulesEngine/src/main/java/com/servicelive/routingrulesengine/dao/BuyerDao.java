package com.servicelive.routingrulesengine.dao;

import com.servicelive.domain.buyer.Buyer;

/**
 * 
 * @author svanloon
 *
 */
public interface BuyerDao {

	/**
	 * 
	 * @param id
	 * @return Buyer
	 * @throws Exception
	 */
	public Buyer findById(Integer id) throws Exception;
}
