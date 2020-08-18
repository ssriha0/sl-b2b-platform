package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.routingrules.RoutingRuleErrorCause;
import com.servicelive.domain.routingrules.RoutingRuleUploadRule;

public interface RoutingRuleUploadRuleDao {
	public RoutingRuleUploadRule saveUploadRule(RoutingRuleUploadRule routingRuleUploadRule);

	
	/** 
	 * to get list of RoutingRuleUploadRule for a set of uploadRuleIds
	 * @param routingRuleUploadRule
	 * @return List<RoutingRuleUploadRule>
	 */
	public List<RoutingRuleUploadRule> getUploadRules(List<Integer> uploadRuleIds);
	
	/**
	 * to get list of error cause
	 * @return List<RoutingRuleErrorCause>
	 */
	public List<RoutingRuleErrorCause> getErrorCause();
	
	public void addUploadRule (Integer fileHdrId, Integer ruleId, String action);

}
