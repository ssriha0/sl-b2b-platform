package com.servicelive.routingrulesengine.dao;

import java.util.List;
import java.util.Map;

import com.servicelive.domain.lookup.LookupMarket;

/**
 * 
 * @author svanloon
 *
 */
public interface LookupMarketDao {

	/**
	 * 
	 * @return List<LookupMarket>
	 */
	public List<LookupMarket> findActive();
	public Map<String,String> getMaprkets(List<String> markets);
}
