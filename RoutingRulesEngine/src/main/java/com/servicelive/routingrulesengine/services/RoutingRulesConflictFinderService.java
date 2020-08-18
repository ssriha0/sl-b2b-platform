package com.servicelive.routingrulesengine.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.routingrules.RoutingRuleError;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.routingrulesengine.vo.RoutingRulesConflictVO;

/**
 * 
 * Interface to hold methods related to conflict finder
 * 
 */
public interface RoutingRulesConflictFinderService {
	/**
	 * This method is used to check if a rule is in conflict with the existing
	 * active car rules
	 * 
	 * @param baseRuleVO
	 * @param buyerId
	 * @return List<RoutingRuleError>
	 */

	/*public HashMap<Integer,List<RoutingRuleError>>  findRoutingRuleConflicts(
				List<RoutingRulesConflictVO> baseRuleVOList, Integer buyerId);*/

	/**
	 * This method is used to get conflict for a ruleHdr
	 * 
	 * @param RoutingRuleHdr
	 *            ruleHdr
	 * @return List<RoutingRuleError>
	 */
	public List<RoutingRuleError> findRoutingRuleConflictsForRuleHdr(
			RoutingRuleHdr ruleHdr, Integer buyerId,Integer source);
	
	/**
	 * This method is used to get conflict for a ruleHdr
	 * 
	 * @param RoutingRuleHdr
	 *            ruleHdr
	 * @return List<RoutingRuleError>
	 */
	public List<RoutingRulesConflictVO> findRoutingRuleConflictListForRuleHdr(
			List<RoutingRuleHdr> ruleHdrList);
	
	public List<RoutingRuleError> findConflictsAmongInactiveRules(
			RoutingRulesConflictVO ruleVO, List<RoutingRulesConflictVO> rulesVOList);

	public HashMap<Integer,List<RoutingRuleError>>  findConflictingRoutingRules(
			  List<RoutingRulesConflictVO> baseRuleVOList, Integer buyerAssocId,Integer source);
	
	public void updateActiveRulesCache(List<RoutingRuleHdr> ruleHdrIds, String action,Integer buyerAssocId);
}
