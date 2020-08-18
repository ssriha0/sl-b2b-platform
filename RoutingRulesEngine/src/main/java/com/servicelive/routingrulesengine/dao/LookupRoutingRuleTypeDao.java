package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.lookup.LookupRoutingRuleType;

/**
 * 
 * @author svanloon
 *
 */
public interface LookupRoutingRuleTypeDao {

	/**
	 * 
	 * @return List<LookupRoutingRuleType>
	 */
	public List<LookupRoutingRuleType> findAll();

	/**
	 * 
	 * @param id
	 * @return LookupRoutingRuleType
	 */
	public LookupRoutingRuleType findById(Integer id);
}
