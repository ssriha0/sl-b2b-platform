package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.servicelive.domain.lookup.LookupZipGeocode;
import com.servicelive.routingrulesengine.dao.LookupZipMarketDao;

public class LookupZipMarketDaoImpl extends AbstractBaseDao implements LookupZipMarketDao {

	@SuppressWarnings("unchecked")
	public List<LookupZipGeocode> findByMarketId(Integer marketId) {
		String hql = "select lzm.lookupZipMarketPk.zipGeocode from LookupZipMarket as lzm where lzm.lookupZipMarketPk.market.id = :marketId";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("marketId", marketId);
		return query.getResultList();
	}

}
