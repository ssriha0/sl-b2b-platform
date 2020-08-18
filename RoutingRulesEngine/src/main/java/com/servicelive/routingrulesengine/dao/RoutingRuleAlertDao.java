package com.servicelive.routingrulesengine.dao;

import com.servicelive.domain.routingrules.RoutingRuleAlert;

/**
 * 
 *
 */
public interface RoutingRuleAlertDao {
	
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void saveAndUpdate(RoutingRuleAlert entity) throws Exception;
	
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(RoutingRuleAlert entity) throws Exception;
	
}
