package com.servicelive.routingrulesengine.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.servicelive.domain.routingrules.RoutingRuleError;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.detached.RoutingRuleAutoAcceptHistoryVO;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.vo.RoutingRuleCacheStatusVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesConflictVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;


public interface RoutingRuleHdrDao {

	/**
	 * Get a RoutingRuleHdr by a service order ID
	 * @param soId
	 * @return RoutingRuleHdr
	 */
	public RoutingRuleHdr findBySoId(String soId);

	/**
	 * Get a RoutingRuleHdr by its ID
	 */
	public RoutingRuleHdr findByRoutingRuleHdrId(Integer routingRuleHdrId);

	/**
	 * Persist a RoutingRuleHdr.
	 * @param routingRuleHdr
	 * @return RoutingRuleHdr
	 * @throws Exception
	 */
	public RoutingRuleHdr save(RoutingRuleHdr routingRuleHdr) throws Exception;
	/**
	 * Delete a RoutingRuleHdr.
	 * @param routingRuleHdr
	 * @return RoutingRuleHdr
	 * @throws Exception
	 */
	public RoutingRuleError delete(RoutingRuleError routingRuleHdr) throws Exception;
	/**
	 * 
	 * @return List<RoutingRuleHdr>
	 */
	public List <RoutingRuleHdr> findAll();
	
	public List<RoutingRuleHdr> getRoutingRulesHeadersforBuyer(Integer buyerId, RoutingRulesPaginationVO pagingCriteria);
	public Integer getRoutingRulesCount(Integer buyerId);

	/**
	 * Method to fetch active rules whose jobcodes and zipcodes matches with the
	 * baserule
	 * 
	 * @param buyerId
	 * @param baseRuleVO
	 * @return List<RoutingRuleHdr>
	 */
	//public List<RoutingRuleHdr> getActiveRoutingRulesforBuyer(Integer buyerId,
	//		RoutingRulesConflictVO baseRuleVO);

	/**
	 * Method to fetch zip codes of market to which the car rule is associated
	 * 
	 * @param ruleId
	 * @return List<String>
	 */
	public List<String> getzipsForMarket(Integer ruleId);
	
	/**
	 * Return all ruleHdr for the list of ruleId.
	 * @param List<String> ruleIds
	 * @return List<RoutingRuleHdr>
	 */
	public List<RoutingRuleHdr> getRoutingRulesHeadersForRuleIds(List<Integer> routingRuleId);
	
	/**
	 * Return all ruleConflict for the rule 
	 * @param ruleId
	 * @return List<RoutingRuleError>
	 */
	public List<RoutingRuleError> getRoutingRulesConflictsForRuleId(Integer ruleId);
	
	/**
	 * Return all ruleError for the rule 
	 * @param ruleId
	 * @return List<RoutingRuleError>
	 */
	public List<RoutingRuleError> getRoutingRuleErrorForRuleId(Integer ruleId);

	/**
	 * Return the ruleHdr with the given ruleName
	 * @param String ruleName
	 * @return RoutingRuleHdr
	 * @throws Exception 
	 */
	public  RoutingRuleHdr getRoutingRuleByName(String ruleName, Integer buyerId);
	
	/**
	 * Updates the routing rule with the changes
	 * @param routingRuleHdr
	 * @return routingRuleHdr
	 * @throws Exception
	 */
	public RoutingRuleHdr update(RoutingRuleHdr routingRuleHdr) throws Exception;
	
	public List<Integer> findConflict(Integer buyerId, RoutingRulesConflictVO baseRuleVO);
	
	public List<String> findConflictingZips(Integer ruleId,Integer conflictingRuleId);
	
	public List<String> findConflictingJobCodes(Integer ruleId,Integer conflictingRuleId);
	
	public List<String> findConflictingPickupCodes(Integer ruleId,Integer conflictingRuleId);
		
	public HashMap<Integer, List<String>>  getConflictingZips(List<String> zips,Integer buyerId);
	
	public HashMap<Integer, List<String>>  getConflictingPickups(List<String> pickups,Integer buyerId);
	
	public HashMap<Integer, List<String>>  getConflictingJobcodes(List<String> jobcodes,Integer buyerId);
	
	public HashMap<Integer, List<Integer>>  getCriteriaCount(Set<Integer> routingRuleId);
	
	public List<Integer> getHeaderList(Integer buyerId);
	
	public List<String> getPickups(Integer hdrId);
	public List<String> getJobCodes(Integer hdrId);
	public List<String> getZips(Integer hdrId);
	
	public Integer getbuyerAssocId(Integer buyerId);
	
	public  List<Integer> getCarBuyers();
	
	public HashMap<Integer, HashMap<Integer, List<String>>> getMarketZips(Integer buyerId);
	
	public List<Integer> getAllRuleHdrIds(Integer buyerAssocId);
	
	public List<Integer> getAllRuleHdrIdsExceptActive(List<String> ruleIds);
	
	public void updateRuleStatusAndComment(List<String> ruleIds, String status, String comment, String userName);
	
	public List<Integer> getAllRuleHdrIdsExceptArchive(List<String> ruleIds);
	
	 public List<String>  getMarketIds(Integer hdrId);
	
	public List<String>  findMarketZips(Integer marketId);
	
	public void updateCache(List<RoutingRuleHdr> ruleHdrIds, String action,Integer buyerAssocId);
	
	public List<RoutingRuleCacheStatusVO> retrieveDirtyCache(Integer sourceOfRetrieve);
	
	public List<LabelValueBean> getRoutingRulesBuyer(Integer buyerId);	
	//SL-15642 Method to save the email id  for buyer from manage rule dashboard
	public void saveManageRuleBuyerEmailId(Integer indEmailNotifyRequire,String manageRuleBuyerEmailId,Integer buyerId);
	
	//SL -15642 Method to fetch saved email id for the buyer
	public String fetchSavedEmailId(Integer buyerId);
	
	//SL-15642 Method to auto_accept_status in the routing_rule_vendor table during Creation of CAR rule
	public void  saveRoutingRuleVendor(String autoAcceptStatCarRuleCreation,Integer ruleId);
	
	//SL 15642 Method to get history of a rule for a buyer
	public List<RoutingRuleAutoAcceptHistoryVO> getAutoAcceptHistoryBuyer(Integer ruleHdrId);

	//SL 15642 Method to get history of a rule for provider
	public List<RoutingRuleAutoAcceptHistoryVO> getAutoAcceptHistoryProvider(Integer ruleHdrId, Integer vendorId);
	
	//SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000 
	public Integer forceFulActiveRule(Map inputMap) throws Exception;
	
	
	/**
	 *  Check if the conflict finder is disabled
	 * @param buyerAssocId
	 * @return
	 * @throws Exception
	 */
	public boolean isConflictFinderDisabled(Integer buyerAssocId) throws Exception; 

}
