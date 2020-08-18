package com.servicelive.routingrulesengine.dao.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.routingrulesengine.dao.BuyerDao;

/**
 * 
 * @author svanloon
 *
 */
public class BuyerDaoImpl extends AbstractBaseDao implements BuyerDao {

	/**
	 * 
	 * @param id
	 * @return Buyer
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public Buyer findById(Integer id) throws Exception {
		return (Buyer) super.findById(Buyer.class, id);
	}

}
