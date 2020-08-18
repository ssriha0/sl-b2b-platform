package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupMarketDao;

/**
 * @author Carlos Garcia
 *
 */
@Repository ("lookupMarketDao")
public class LookupMarketDaoImpl extends AbstractBaseDao implements LookupMarketDao {

	@SuppressWarnings("unchecked")
	public List<LookupMarket> findAll(int... rowStartIdxAndCount) throws Exception {
		List <LookupMarket> orderList = (List <LookupMarket>)super.findAllOrderByDesc("LookupMarket", "market_name", rowStartIdxAndCount);
		return orderList;
	}

	public LookupMarket findById(Integer id) throws Exception {
		return (LookupMarket) super.findById(LookupStates.class, id);
	}

	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LookupStates> findByProperty(String propertyName, Object value,	int... rowStartIdxAndCount) throws Exception {
		return (List<LookupStates>) super.findByProperty("LookupMarket",propertyName,value, rowStartIdxAndCount);
	}

	public List<LookupMarket> getMarkets() throws Exception
	{
		return findAll();
	}
}
