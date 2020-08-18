package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.lookup.LookupZipGeocode;

/**
 * 
 * @author svanloon
 *
 */
public interface LookupZipMarketDao {
	/**
	 * 
	 * @param marketId
	 * @return List<LookupZipMarket>
	 */
	public List<LookupZipGeocode> findByMarketId(Integer marketId);
}
