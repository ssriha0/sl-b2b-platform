package com.servicelive.spn.dao.buyer.impl;

import java.util.List;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.buyer.BuyerDao;

/**
 * @author hoza
 *
 */
public class BuyerDaoImpl extends AbstractBaseDao implements BuyerDao {

	public Buyer findById(Integer id) throws Exception {
		return (Buyer) super.findById(Buyer.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Buyer> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception {
		return ( List<Buyer> )super.findByProperty("Buyer", propertyName, value, rowStartIdxAndCount);
	}

}
