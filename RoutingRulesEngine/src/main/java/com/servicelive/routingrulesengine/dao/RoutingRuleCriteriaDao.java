package com.servicelive.routingrulesengine.dao;

import com.servicelive.domain.routingrules.RoutingRuleCriteria;


public interface RoutingRuleCriteriaDao
{
	/**
	 * 
	 * @param vendor
	 * @throws Exception
	 */
	public void delete(RoutingRuleCriteria criteria) throws Exception;

}
