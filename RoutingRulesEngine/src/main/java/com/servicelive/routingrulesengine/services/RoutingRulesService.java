package com.servicelive.routingrulesengine.services;

import java.util.List;
import java.util.Map;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.lookup.LookupRoutingRuleType;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.routingrules.RoutingRuleBuyerAssoc;
import com.servicelive.domain.routingrules.RoutingRuleError;
import com.servicelive.domain.routingrules.RoutingRuleErrorCause;
import com.servicelive.domain.routingrules.RoutingRuleFileHdr;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleHdrHist;
import com.servicelive.domain.routingrules.detached.RoutingRuleAutoAcceptHistoryVO;
import com.servicelive.domain.routingrules.detached.RoutingRuleQuickViewVO;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.RoutingRuleType;
import com.servicelive.routingrulesengine.vo.JobCodeVO;
import com.servicelive.routingrulesengine.vo.RoutingRuleErrorVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesSearchVO;
import com.servicelive.routingrulesengine.vo.RuleConflictDisplayVO;
import com.servicelive.routingrulesengine.vo.RuleErrorDisplayVO;

/**
 * 
 *
 */
public interface RoutingRulesService {

	/**
	 * Given a buyer ID, return the respective Buyer Assoc.
	 * @param buyerId
	 */
	public RoutingRuleBuyerAssoc getRoutingRuleBuyerAssoc(Integer buyerId);

	/**
	 * Return all rules for this buyer.
	 * @param buyerId
	 * @return List<RoutingRuleHdr>
	 */
	public List<RoutingRuleHdr> getRoutingRulesHeaders(Integer buyerId);
	
	
	/**
	 * Return all rule names and id for this buyer.
	 * @param buyerId
	 * @return List<RoutingRuleHdr>
	 */
	public List<LabelValueBean> getRulesForBuyer(Integer buyerId);

	/**
	 * Return all ruleHdr for the list of ruleId.
	 * @param List<String> ruleIds
	 * @return List<RoutingRuleHdr>
	 */

	public List<RoutingRuleHdr> getRoutingRulesHeadersForRuleIds(
			List<String> ruleIds);

	/**
	 * Return all ruleHdr for the rule Action.
	 * @param List<String> ruleIds
	 * @param String ruleAction
	 * @return List<RoutingRuleHdr>
	 */
//	public List<RoutingRuleHdr> getRoutingRulesHeadersForRuleAction(List<String> ruleIds,String ruleAction,String modifiedBy);
	
	 /** Return all ruleHdr for the rule Action.
	 * @param List<String> ruleIds
	 * @param String ruleAction
	 * @return List<RoutingRuleHdr>
	 */
	public Map<Integer,Map<Integer,List<RoutingRuleError>>> getRoutingRulesHeaderListForRuleAction(List<RoutingRuleHdr> ruleHdrs,String ruleAction,String modifiedBy);
	
	
	
	/**
	 * Return all ruleConflict for the rule 
	 * @param ruleId
	 * @return List<RuleConflictDisplayVO>
	 */
	public List<RuleConflictDisplayVO> getRoutingRulesConflictsForRuleId(Integer ruleId);

	/**
	 *  Return all ruleError for the rule 
	 * @param ruleId
	 * @return List<RuleErrorDisplayVO>
	 */
	public List<RuleErrorDisplayVO> getRoutingRuleErrorForRuleId(Integer ruleId);
	
	/**
	 *  Return all ruleError for the rule 
	 * @param ruleId
	 * @return List<RoutingRuleErrorVO>
	 */
	public List<RoutingRuleErrorVO> getRoutingRuleErrors(Integer ruleId);

	
	/**
	 * @return RoutingRuleHdr associated with this ID, or null.
	 */
	public RoutingRuleHdr getRoutingRulesHeader(Integer routingRuleHdrId);

	/**
	 * 
	 * @return  all entries from RoutingRuleHdr table
	 */
	public List<RoutingRuleHdr> getAllExistingRoutingRulesHeaders();

	/**
	 * Return all RoutingRuleHdrHist rows for the given buyer.
	 * @param contextBuyerId 
	 */
	public List<RoutingRuleHdrHist> getRoutingRuleHeadersHist(
			Integer contextBuyerId, RoutingRulesPaginationVO pagingCriteria,
			boolean archiveIndicator);

	/** Return a provider firm given its ID, or null. */
	public ProviderFirm getProviderFirm(Integer firmId);

	/**
	 * @return List of custom reference labels that are relevant to conditional 
	 *         autorouting, with their ID's in the Value field.
	 */
	public List<LabelValueBean> getCustomReferences(Integer buyerId);

	/**
	 * @return List of LabelValueBean objects containing market names (label) and 
	 *        their ID's (value).
	 */
	public List<LabelValueBean> getMarkets();
	
	/**
	 * @return map containing market names (label) and 
	 *        their ID's (value).
	 */
	public Map<String,String> getMarkets(List<String> markets);

	/**
	 * @param stateAbbrev
	 *            The state to fetch zipcodes for, in 2-letter abbreviation form.
	 * @return List of String[] objects, containing zips[0] paired 
	 *        with their city names [1], or empty list on error.
	 */
	public List<LabelValueBean> getZipcodesByStateAbbrev(String stateAbbrev);

	/**
	 * @param zipcode
	 *            A 5-digit number.
	 * @return City name zip refers to, or null.
	 */
	public String getCityByZipcode(String zipcode);
	
	/**
	 * @param zips - List of zip codes
	 *            A 5-digit number.
	 * @return City name zip refers to, or null.
	 */
	public Map<String,String> getCityByZipcodeList(List<String> zips);

	/**
	 * @param buyerId
	 * @return all specialty codes for the given buyer, along with the value of their ID's.
	 */
	public List<LabelValueBean> getSpecialtyCodes(Integer buyerId);

	/**
	 * @param specialtyCode
	 * @param buyerId
	 * @return All jobcodes relevant to the given specialty code, 
	 *        along with the value of their ID's.
	 */
	public List<LabelValueBean> getJobcodesForSpecialtyCode(Integer buyerId,
			String specialtyCode);

	/**
	 * Return the reqeusted RoutingRuleType
	 */
	public LookupRoutingRuleType getRoutingRuleType(RoutingRuleType type);

	/**
	 * Persist the given rule.
	 * 
	 * @param routingRuleHdr
	 * @throws Exception
	 */
	public void persistRoutingRule(RoutingRuleHdr routingRuleHdr)
			throws Exception;

	/**
	 * 
	 * @param map
	 * @param contextBuyerId
	 * @param modifiedBy
	 * @throws Exception
	 */
	public RoutingRuleHdr updateRoutingRule(Map<String, Object> map,
			Integer contextBuyerId, String modifiedBy) throws Exception;
	//SL-15642 Method to auto_accept_status in the routing_rule_vendor table during Creation of CAR rule
	
	public void  saveRoutingRuleVendor(String autoAcceptStatCarRuleCreation,Integer ruleId) throws Exception;
	
	/**
	 * 
	 * @param map
	 * @param contextBuyerId
	 * @param modifiedBy
	 * @throws Exception
	 */
	public List<RuleConflictDisplayVO> updateRoutingRuleAndCheckConflicts(
			Map<String, Object> map, Integer contextBuyerId, String modifiedBy)
			throws Exception;


	/**
	 * Fetch list of states for front end
	 * @return 2 letter abbrev as value, name as label
	 */
	public List<LabelValueBean> getStates();

	/**
	 * To update status of list of ruleIds
	 * @param List<String> ruleIds
	 * @param String comment
	 * @param String status
	 * @param String modifiedBy
	 * @return List<RoutingRuleHdr> 
	 * @throws Exception
	 */
	public List<RoutingRuleHdr> updateRoutingRuleHdrStatusAndComment(List<String> ruleIds, String comment, String status, String modifiedBy) throws Exception;
	
	/**
	 * To remove non persistent errors
	 * @param ruleId
	 */
	public void removeErrors(Integer ruleId);
	
	
	/**
	 * Return a VO with the rule's vendor info.  CopyRules calls this asynchronously, 
	 * View/Edit call it at page load and set the result to the model.
	 * 
	 * @return JSON representation of a List of RRFirmVO's.
	 */
	public String getProviderFirmsJson(RoutingRuleHdr rule);

	/** 
	 * Fetch Provider Firm VO's by Provider Firm ID, and return as JSON.
	 * Front end calls this to validate and get details on user-entered firms. 
	 */
	public String getProviderFirmsJson(Integer... firmIds);

	/** Look up rule and call getRuleCriteriaJson(RoutingRuleHdr) */
	public String getRuleCriteriaJson(Integer ruleId);

	/**
	 * Return a VO with the rule's criteria.  CopyRules calls this asynchronously, 
	 * View/Edit call it at page load and set the result to the model.
	 * @param rule
	 * @return JSON, like so:<pre>
	 * [ { name : 'ZIP', label : '22309 - Alexandria VA', value : 'zip22309' }
	 *  ,{ name : 'MARKET', label : 'Nassau, TX', value : '2' }
	 *  ,{ name : 'DELIVERY_DATE', label : 'DELIVERY_DATE', value : 'NEVER' }];
	 * </pre>
	 */
	public String getRuleCriteriaJson(RoutingRuleHdr rule);

	
	/** @return JSON representation of given object, or "null" */
	public String getJSON(Object obj);

	public List<RoutingRuleHdr> getRoutingRulesHeaderList(Integer buyerId,
			RoutingRulesPaginationVO pagingCriteria);

	/**
	 * This method returns the rules data based on search/pagination criteria given
	 * @param routingRulesSearchVo - The Search Criteria
	 * @param pagingCriteria - The Pagination Criteria
	 * @return List<RoutingRuleHdr> Collection of Rules Data
	 */
	public List<RoutingRuleHdr> getRoutingRulesHeaderAfterSearch(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator);

	public RoutingRuleQuickViewVO setQuickViewforRule(RoutingRuleHdr rule,List<LabelValueBean> markets);
	
	/**
	 * The method is to get rule criteria for a rule
	 * @param rule
	 * @return
	 */
	public Map<String,List<String>> getRuleCriteria(RoutingRuleHdr rule);
	
	/**
	 * The method is to get rule criteria for a rule
	 * @param ruleId
	 * @return
	 */
	public Map<String,List<String>> getRuleCriteriaForRuleId(Integer ruleId);
		
	/**
	 * The method is to get job codes for a rule
	 * @param rule
	 * @return
	 */
	public List<JobCodeVO> getRuleJobCodes(RoutingRuleHdr rule);
	
	/**
	 * To update rule history
	 * @param RoutingRuleHdr ruleHdr
	 * @param String action
	 * @param BuyerResource buyer
	 */
	public void updateRuleHistory(RoutingRuleHdr ruleHdr,
			String action, BuyerResource buyer);
	
	/**
	 * @param userName
	 * @return
	 */
	public BuyerResource getBuyerResource(String userName);
	
	
	/**
	 * To retrieve conflicts
	 * @param ruleId
	 */
	public List<RuleConflictDisplayVO> getRoutingRuleConflicts(Integer ruleId);
		
	/**
	 * Get the uploaded files for a user
	 * @param pagingCriteria
	 * @return
	 */
	public List<RoutingRuleFileHdr> getRoutingRulesFileHeaderList
			(RoutingRulesPaginationVO pagingCriteria, Integer buyerId);
	
	/**
	 * Check if the file already exists in the database.
	 * @param fileName
	 * @return
	 */
	public boolean checkIfFileExists(String fileName, Integer buyerId);
	
	/**
	 * Save the routing rule file details to the database
	 * @param routingRuleFileHdr
	 * @return
	 */
	public RoutingRuleFileHdr saveRoutingRuleFile(RoutingRuleFileHdr 
		routingRuleFileHdr);
	
	public void saveRoutingRuleHdr(RoutingRuleHdr 
			routingRuleHdr);
	
	
	public void persistErrors(Map<Integer,Map<Integer,List<RoutingRuleError>>> errorMap, List<String> ruleIds, String comment, String modifiedBy);

	public void deletePersistentErrors(Integer 
			routingRuleId);
	
	//public List<RoutingRuleHdr> getRoutingRulesForRuleAction(
		//	List<String> ruleIds, String ruleAction, String modifiedBy);
	
	/**
	 * Get all the error cause from the DB
	 * @return
	 */
	public List<RoutingRuleErrorCause>  getErrorCause();
	//SL-15642 Method to save the email id  for buyer from manage rule dashboard
	public void saveManageRuleBuyerEmailId(Integer indEmailNotifyRequire,String manageRuleBuyerEmailId,Integer buyerId);
	
	//SL -15642 Method to fetch saved email id for the buyer
	public String fetchSavedEmailId(Integer buyerId);
	//SL 15642 Method to get history of a rule for a buyer
	public List<RoutingRuleAutoAcceptHistoryVO> getAutoAcceptHistoryBuyer(Integer ruleHdrId);

	//SL 15642 Method to get history of a rule for provider
	public List<RoutingRuleAutoAcceptHistoryVO> getAutoAcceptHistoryProvider(Integer ruleHdrId, Integer vendorId);
	
	public Integer forceFulActiveRule(Map inputMap) throws Exception;
}
