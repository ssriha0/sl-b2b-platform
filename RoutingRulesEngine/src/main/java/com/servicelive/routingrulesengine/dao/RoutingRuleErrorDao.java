package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.routingrules.RoutingRuleError;

public interface RoutingRuleErrorDao {
	/**
	 * Save routing rule error
	 * @param routingRuleHdrId
	 */
	public void save(RoutingRuleError routingRuleError );
	
	/**
	 * to remove non persistent errors
	 * @param routingRuleHdrId
	 */
	public void deletePersistentErrors(Integer routingRuleHdrId);
	
	/**
	 * to remove non persistent errors
	 * @param routingRuleHdrId
	 */
	public void deleteNonPersistentErrors(Integer routingRuleHdrId);

	/**
	 * Return persistent conflicts for the rule 
	 * 
	 * @param ruleId
	 * @return List<RoutingRuleError>
	 */
	public List<RoutingRuleError> getRoutingRulesConflictsForRuleId(
			Integer routingRuleId);
}
