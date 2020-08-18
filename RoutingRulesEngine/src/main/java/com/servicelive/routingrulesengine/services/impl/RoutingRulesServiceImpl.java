package com.servicelive.routingrulesengine.services.impl;

import static com.servicelive.routingrulesengine.RoutingRulesConstants.CRITERIA_NAME_MARKET;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.CRITERIA_NAME_ZIP;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.DELIMITER;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.ROUTING_RULE_HDR_COMMENT_LEN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.interfaces.AlertConstants;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.domain.lookup.LookupRoutingRuleType;
import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.domain.lookup.LookupWfStates;
import com.servicelive.domain.lookup.LookupZipGeocode;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.routingrules.AutoAcceptHistory;
import com.servicelive.domain.routingrules.RoutingRuleBuyerAssoc;
import com.servicelive.domain.routingrules.RoutingRuleCriteria;
import com.servicelive.domain.routingrules.RoutingRuleError;
import com.servicelive.domain.routingrules.RoutingRuleErrorCause;
import com.servicelive.domain.routingrules.RoutingRuleFileHdr;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleHdrHist;
import com.servicelive.domain.routingrules.RoutingRulePrice;
import com.servicelive.domain.routingrules.RoutingRuleUploadRule;
import com.servicelive.domain.routingrules.RoutingRuleVendor;
import com.servicelive.domain.routingrules.detached.RoutingRuleAutoAcceptHistoryVO;
import com.servicelive.domain.routingrules.detached.RoutingRuleQuickViewVO;
import com.servicelive.domain.so.BuyerReferenceType;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.RoutingRuleType;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.dao.BuyerDao;
import com.servicelive.routingrulesengine.dao.BuyerReferenceTypeDao;
import com.servicelive.routingrulesengine.dao.BuyerSkuDao;
import com.servicelive.routingrulesengine.dao.BuyerSkuTaskAssocDao;
import com.servicelive.routingrulesengine.dao.ContactDao;
import com.servicelive.routingrulesengine.dao.LookupMarketDao;
import com.servicelive.routingrulesengine.dao.LookupRoutingRuleTypeDao;
import com.servicelive.routingrulesengine.dao.LookupStatesDao;
import com.servicelive.routingrulesengine.dao.LookupZipGeocodeDao;
import com.servicelive.routingrulesengine.dao.ProviderFirmDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleBuyerAssocDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleCriteriaDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleErrorDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleFileHdrDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrHistDao;
import com.servicelive.routingrulesengine.dao.RoutingRulePriceDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleUploadRuleDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleVendorDao;
import com.servicelive.routingrulesengine.services.RoutingRuleAlertService;
import com.servicelive.routingrulesengine.services.RoutingRulesConflictFinderService;
import com.servicelive.routingrulesengine.services.RoutingRulesService;
import com.servicelive.routingrulesengine.services.ValidationService;
import com.servicelive.routingrulesengine.vo.JobCodeVO;
import com.servicelive.routingrulesengine.vo.RoutingRuleErrorVO;
import com.servicelive.routingrulesengine.vo.RoutingRuleUploadDetailsVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesConflictVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesFirmVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesSearchVO;
import com.servicelive.routingrulesengine.vo.RuleConflictDisplayVO;
import com.servicelive.routingrulesengine.vo.RuleErrorDisplayVO;
import com.servicelive.routingrulesengine.vo.RuleErrorVO;

import flexjson.JSONSerializer;

public class RoutingRulesServiceImpl implements RoutingRulesService {

	private static final Logger logger = Logger.getLogger(RoutingRulesServiceImpl.class);

	private BuyerDao routingRuleBuyerDao;
	private BuyerReferenceTypeDao buyerReferenceTypeDao;
	private BuyerSkuTaskAssocDao buyerSkuTaskAssocDao;
	private ContactDao routingRuleContactDao;
	private LookupMarketDao lookupMarketDao;
	private LookupZipGeocodeDao lookupZipGeocodeDao;
	private LookupRoutingRuleTypeDao lookupRoutingRuleTypeDao;
	private LookupStatesDao lookupStatesDao;
	private ProviderFirmDao providerFirmDao;
	private RoutingRuleBuyerAssocDao routingRuleBuyerAssocDao;
	private RoutingRuleHdrDao routingRuleHdrDao;
	private RoutingRuleHdrHistDao routingRuleHdrHistDao;
	private RoutingRulePriceDao routingRulePriceDao;
	private RoutingRuleVendorDao routingRuleVendorDao;
	private RoutingRuleCriteriaDao routingRuleCriteriaDao;
	private RoutingRuleAlertService routingRuleAlertService;
	private RoutingRulesConflictFinderService routingRulesConflictFinderService;
	private BuyerSkuDao buyerSkuDao;
	private ValidationService validationService;
	private RoutingRuleErrorDao routingRuleErrorDao;
	private RoutingRuleUploadRuleDao routingRuleUploadRuleDao;
	private RoutingRuleFileHdrDao routingRuleFileHdrDao; 
	private ILookupBO lookupBO;
	
	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<RoutingRuleHdr> getRoutingRulesHeaderList(Integer buyerId, RoutingRulesPaginationVO pagingCriteria){
		List<RoutingRuleHdr> result = routingRuleHdrDao.getRoutingRulesHeadersforBuyer(buyerId, pagingCriteria);
		return result;
	}

	/**
	 * Given a buyer ID, return a list of its associated RoutingRuleHdr objects.
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleHdr> getRoutingRulesHeaders(Integer buyerId) {
		final RoutingRuleBuyerAssoc result = getRoutingRuleBuyerAssoc(buyerId);
		if (result != null) {
			return result.getRoutingRuleHeaders();
		}
		return null;
	}
	
	/**
	 * Return all rule names and id for this buyer.
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LabelValueBean> getRulesForBuyer(Integer buyerId) {
		List<LabelValueBean> ruleNameIdList = routingRuleHdrDao.getRoutingRulesBuyer(buyerId);
		return ruleNameIdList;
	}	
	
	/**
	 * Return all ruleHdr for the list of ruleId.
	 * @param List<String> ruleIds
	 * @return List<RoutingRuleHdr>
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleHdr> getRoutingRulesHeadersForRuleIds(List<String> ruleIds)
	{
		List<Integer> routingRuleIds=new ArrayList<Integer>();
		for (String rrId : ruleIds)
		{
			routingRuleIds.add(Integer.parseInt(rrId));
		}
		return routingRuleHdrDao.getRoutingRulesHeadersForRuleIds(routingRuleIds);
			
	}

	
	/**
	 * Made code change for Conflict Finder
	 * Return all ruleHdr for the rule Action.
	 * @param List<String> ruleIds
	 * @param String ruleAction
	 * @return List<RoutingRuleHdr>
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<Integer,Map<Integer,List<RoutingRuleError>>> getRoutingRulesHeaderListForRuleAction(
			List<RoutingRuleHdr> ruleHdrList, String ruleAction, String modifiedBy) {
		logger.info("Starting the conflict finder for the selected rules.");
		List<RoutingRuleHdr> ruleHdrConflictList = new ArrayList<RoutingRuleHdr>();
		Map<Integer,Map<Integer,List<RoutingRuleError>>> errorMap=new HashMap<Integer,Map<Integer,List<RoutingRuleError>>>();
		if (ruleHdrList != null) {
			for (RoutingRuleHdr ruleHdr : ruleHdrList) {
				if (ruleHdr != null) {
					
					/* If the rule is already active, we don't have to do anything with that rule.
					 * Otherwise add the rule to check the conflict */
					if(!RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE.
							equalsIgnoreCase(ruleHdr.getRuleStatus())){
						
						/* add to list so that we can check the conflict*/
						logger.info("Adding the rule to check the conflict::"+ruleHdr.getRoutingRuleHdrId());
						ruleHdrConflictList.add(ruleHdr);
					}
					/*if ((null != ruleHdr.getRuleSubstatus() && RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_ERROR.equalsIgnoreCase(ruleHdr.getRuleSubstatus()))
							|| (RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE.equalsIgnoreCase(ruleHdr.getRuleStatus()))) {
					} else {
						logger.info("Inside else ROUTING_RULE_STATUS_ACTIVE"+ruleHdr.getRoutingRuleHdrId());
						ruleHdrConflictList.add(ruleHdr);
					}*/
				}
			}
			Integer buyerAssocId=new Integer(1);
			if(ruleHdrList!=null){
				RoutingRuleHdr rulehdr = ruleHdrList.get(0);
				buyerAssocId = rulehdr.getRoutingRuleBuyerAssoc().getId();
				logger.info("Buyer assoc id is :"+buyerAssocId);
			}
			
			List<RoutingRulesConflictVO> ruleConflictListVo = null;
			//
			 boolean disableConflictFinder=false;
			try {
				disableConflictFinder = routingRuleHdrDao.isConflictFinderDisabled(buyerAssocId);
			} catch (Exception e) {
				logger.error("exception in disableConflictFinder "+e);
			}
			 logger.info("disableConflictFinder in getRoutingRulesHeaderListForRuleAction method:"+disableConflictFinder);
			 if(!disableConflictFinder){
			ruleConflictListVo=routingRulesConflictFinderService.
				findRoutingRuleConflictListForRuleHdr(ruleHdrConflictList);
			 }
			
			Map<Integer,List<RoutingRuleError>>	routingRuleErrorMap=new HashMap<Integer,List<RoutingRuleError>>();
			if(!disableConflictFinder){
			routingRuleErrorMap=routingRulesConflictFinderService.
				findConflictingRoutingRules(ruleConflictListVo, buyerAssocId, RoutingRulesConstants.UPDATE_FROM_FRONT);
			}
			logger.info("routingRuleErrorMap.size():::"+routingRuleErrorMap.size());
			
			Map<Integer,List<RoutingRuleError>>	inactiveRuleErrorMap = new HashMap<Integer, List<RoutingRuleError>>();
			if(!disableConflictFinder){
			List<RoutingRulesConflictVO> inactiveRuleListVO = new ArrayList<RoutingRulesConflictVO>();
			for (RoutingRulesConflictVO conflictVO : ruleConflictListVo) {
				List<RoutingRuleError> routingError = routingRuleErrorMap.get(conflictVO.getRuleId());
				if (routingError == null || routingError.size() == 0) {
					inactiveRuleListVO.add(conflictVO);
				}
			}
			logger.info("There are inactive rules which does not have conflicts " +
					"with other active rules:"+inactiveRuleListVO.size());
			for (RoutingRulesConflictVO inactiveConflictVO : inactiveRuleListVO) {
				List<RoutingRuleError> errorList  =routingRulesConflictFinderService.
					findConflictsAmongInactiveRules(inactiveConflictVO,inactiveRuleListVO);
				inactiveRuleErrorMap.put(inactiveConflictVO.getRuleId(), errorList);
			}
			}
			logger.info("selected inactive rules are in conflict with each other:"+inactiveRuleErrorMap.size());
			errorMap.put(1, routingRuleErrorMap);
			errorMap.put(2, inactiveRuleErrorMap);
			
		}
		logger.info("Completed the conflict finder and returing the error map::"+errorMap.size());
		return errorMap;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void persistErrors(Map<Integer,Map<Integer,List<RoutingRuleError>>> errorMap, 
			List<String> ruleIdsToActive,String comment, String modifiedBy){
		try{
			Map<Integer,List<RoutingRuleError>> activeConflict=errorMap.get(1);
			Map<Integer,List<RoutingRuleError>> inactiveConflict=errorMap.get(2);
			BuyerResource buyer = routingRuleHdrHistDao.getBuyerResource(modifiedBy);
			List<Integer> ruleIds=new ArrayList<Integer>();
			// To avoid NPE
			if(null == ruleIdsToActive){
				ruleIdsToActive = new ArrayList<String>();
			}			
			Set<Integer> keys = activeConflict.keySet();
			if(keys!=null) {
				for (Iterator i = keys.iterator(); i.hasNext();) {
					Integer key = (Integer) i.next();
					if(key!=null){
						ruleIds.add(key);
					}
				}
			}
			Set<Integer> inactivekeys = inactiveConflict.keySet();
			if(keys!=null) {
				for (Iterator i = inactivekeys.iterator(); i.hasNext();) {
					Integer key = (Integer) i.next();
					if(key!=null){
						ruleIds.add(key);
					}
				}
			}
			if(null!=ruleIds && ruleIds.size()>0){
			List<RoutingRuleHdr>ruleList=routingRuleHdrDao.getRoutingRulesHeadersForRuleIds(ruleIds);
			
			for (RoutingRuleHdr ruleHdr : ruleList) {
				if(ruleHdr!=null){
					routingRuleErrorDao.deletePersistentErrors(ruleHdr.getRoutingRuleHdrId());
					if(null != activeConflict && activeConflict.containsKey(ruleHdr.getRoutingRuleHdrId())){
						//if inactive map contains this rule updates will be done while iterating inactive map.
						if(!(inactivekeys.contains(ruleHdr.getRoutingRuleHdrId()))){
						List<RoutingRuleError> activeError=activeConflict.get(ruleHdr.getRoutingRuleHdrId());
						if (activeError != null && activeError.size() > 0) {
							for (RoutingRuleError ruleError : activeError) {
								ruleError.setErrorType(RoutingRulesConstants.PERSISTENT_ERROR);
								ruleError.setModifiedBy(modifiedBy);
							}
							ruleHdr.getRoutingRuleError().addAll(activeError);
							if(!(ruleHdr.getRuleSubstatus()!= null &&
									RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_CONFLICT.equalsIgnoreCase(ruleHdr.getRuleSubstatus()))){
								if (comment == null || "".equals(comment)) {
									ruleHdr.setRuleComment("");
								}else if (comment != null && comment.trim().length() > 0) {
									ruleHdr.setRuleComment(comment.trim());
								}
								if(!(ruleIdsToActive.contains(ruleHdr.getRoutingRuleHdrId())))
									updateRuleHistory(ruleHdr, RoutingRulesConstants.HISTORY_UPDATED, buyer);
								ruleHdr.setModifiedDate(new Date());
								ruleHdr.setModifiedBy(modifiedBy);
							}
							ruleHdr.setRuleSubstatus(RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_CONFLICT);
						}else{
							if(!(ruleHdr.getRuleSubstatus()!= null && 
									RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_VALID.equalsIgnoreCase(ruleHdr.getRuleSubstatus()))){
								if(!(ruleIdsToActive.contains(ruleHdr.getRoutingRuleHdrId()))){
									if (comment == null || "".equals(comment)) {
										ruleHdr.setRuleComment("");
									}else if (comment != null && comment.trim().length() > 0) {
										ruleHdr.setRuleComment(comment.trim());
									}
									updateRuleHistory(ruleHdr, RoutingRulesConstants.HISTORY_UPDATED, buyer);
									ruleHdr.setModifiedDate(new Date());
									ruleHdr.setModifiedBy(modifiedBy);

								}
							}
							ruleHdr.setRuleSubstatus(RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_VALID);
						}
						
						try 
						{
							routingRuleHdrDao.save(ruleHdr);
						} catch (Exception e) {
							logger.info("exception in saving rulehdr", e);
						}
						}//inactive check
					}else if(inactiveConflict.containsKey(ruleHdr.getRoutingRuleHdrId())){
						List<RoutingRuleError> inactiveError=inactiveConflict.get(ruleHdr.getRoutingRuleHdrId());
						if (inactiveError != null && inactiveError.size() > 0) {
							for (RoutingRuleError ruleError : inactiveError) {
								ruleError.setErrorType(RoutingRulesConstants.PERSISTENT_ERROR);
								ruleError.setModifiedBy(modifiedBy);
							}
							ruleHdr.getRoutingRuleError().addAll(inactiveError);
							//in case of conflcits among inactive rules update status and comment in hdr table.No updation in rule history.
							if (comment == null || "".equals(comment)) {
								ruleHdr.setRuleComment("");
							}else if (comment != null && comment.trim().length() > 0) {
								ruleHdr.setRuleComment(comment.trim());
							}
							ruleHdr.setRuleSubstatus(RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_CONFLICT);
							ruleHdr.setModifiedDate(new Date());
							ruleHdr.setModifiedBy(modifiedBy);
						}else{
							if(!(ruleHdr.getRuleSubstatus()!= null && 
									RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_VALID.equalsIgnoreCase(ruleHdr.getRuleSubstatus()))){
								if(null!=ruleHdr.getRoutingRuleHdrId() && !(ruleIdsToActive.contains(ruleHdr.getRoutingRuleHdrId().toString()))){
									if (comment == null || "".equals(comment)) {
										ruleHdr.setRuleComment("");
									}else if (comment != null && comment.trim().length() > 0) {
										ruleHdr.setRuleComment(comment.trim());
									}
									updateRuleHistory(ruleHdr, RoutingRulesConstants.HISTORY_UPDATED, buyer);
									ruleHdr.setModifiedDate(new Date());
									ruleHdr.setModifiedBy(modifiedBy);
								}
							}
							
							ruleHdr.setRuleSubstatus(RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_VALID);
						}
						try {
							// updateRuleHistory(ruleHdr, RoutingRulesConstants.HISTORY_UPDATED, buyer);
							routingRuleHdrDao.save(ruleHdr);
						} catch (Exception e) {
							logger.info("exception in saving rulehdr", e);
						}
					}
				}
			 }
			}	
		} catch (Exception e) {
			logger.info("exception in persistErrors", e);
		}
		
			
	}
	
	public void printMap(Map<Integer,List<RoutingRuleError>> routingRuleErrorMap) {
		
		Set<Integer> keys = routingRuleErrorMap.keySet();
		if(keys!=null) {
			for(Integer key:keys) {
				List<RoutingRuleError> errors = routingRuleErrorMap.get(key);
				if(errors!=null) {
					for(RoutingRuleError error : errors) {
						if((error!=null) && (error.getRoutingRuleHdr()!=null)) {
							logger.info("Id="+error.getRoutingRuleHdr().getRoutingRuleHdrId()+" :: errors="+error.getError()+" :: cause id="+error.getErrorCauseId()+" :: conflicting id="+error.getConflictingRuleId());
						}else {
							logger.info("error is null for key="+key);
						}			   
						
					}				
					
				}			
				
			}		
			
		}	
		
	}


	/**
	 * Return all ruleConflict for the rule
	 * 
	 * @param ruleId
	 * @return List<RuleConflictDisplayVO>
	 */
	public List<RuleConflictDisplayVO> getRoutingRulesConflictsForRuleId(
			Integer ruleId) {
		List<RoutingRuleError> routingRuleConflictList = routingRuleHdrDao
				.getRoutingRulesConflictsForRuleId(ruleId);
		List<RuleConflictDisplayVO> conflictList = new ArrayList<RuleConflictDisplayVO>();
		Map<Integer, RuleConflictDisplayVO> conflictMap = new HashMap<Integer, RuleConflictDisplayVO>();
		for (RoutingRuleError routingRuleConflict : routingRuleConflictList) {

			if (conflictMap.containsKey(routingRuleConflict
					.getConflictingRuleId())) {
				RuleConflictDisplayVO ruleDisplayConflictVo = conflictMap
						.get(routingRuleConflict.getConflictingRuleId());

				if (RoutingRulesConstants.CONFLICTING_JOBCODES
						.equals(routingRuleConflict.getErrorCauseId())) {
					ruleDisplayConflictVo.setJobCodes(routingRuleConflict
							.getError());

				}
				if (RoutingRulesConstants.CONFLICTING_ZIPCODES
						.equals(routingRuleConflict.getErrorCauseId())) {
					ruleDisplayConflictVo.setZipCodes(routingRuleConflict
							.getError());
				}
				if (RoutingRulesConstants.CONFLICTING_PICKUPLOCATIONS
						.equals(routingRuleConflict.getErrorCauseId())) {
					ruleDisplayConflictVo.setPickupLocation(routingRuleConflict
							.getError());

				}
				conflictMap.put(routingRuleConflict.getConflictingRuleId(),
						ruleDisplayConflictVo);
			} else {
				RuleConflictDisplayVO ruleDisplayConflictVo = new RuleConflictDisplayVO();

				if (RoutingRulesConstants.CONFLICTING_JOBCODES
						.equals(routingRuleConflict.getErrorCauseId())) {
					ruleDisplayConflictVo.setJobCodes(routingRuleConflict
							.getError());

				}
				if (RoutingRulesConstants.CONFLICTING_ZIPCODES
						.equals(routingRuleConflict.getErrorCauseId())) {
					ruleDisplayConflictVo.setZipCodes(routingRuleConflict
							.getError());

				}
				if (RoutingRulesConstants.CONFLICTING_PICKUPLOCATIONS
						.equals(routingRuleConflict.getErrorCauseId())) {
					ruleDisplayConflictVo.setPickupLocation(routingRuleConflict
							.getError());
}
				conflictMap.put(routingRuleConflict.getConflictingRuleId(),
						ruleDisplayConflictVo);
			}
		}	Set<Integer> keys = conflictMap.keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
			Integer key = (Integer) i.next();
			RuleConflictDisplayVO ruleDisplayConflictVo = conflictMap.get(key);
			RoutingRuleHdr rule = routingRuleHdrDao.findByRoutingRuleHdrId(key);
			ruleDisplayConflictVo.setRuleName(rule.getRuleName());
			conflictList.add(ruleDisplayConflictVo);
		}
	return conflictList;
	}
	
	/**
	 *  Return all ruleError for the rule 
	 * @param ruleId
	 * @return List<RuleErrorDisplayVO>
	 */
	public List<RuleErrorDisplayVO> getRoutingRuleErrorForRuleId(Integer ruleId) {
		Map<String, RuleErrorDisplayVO> errorMap = new HashMap<String, RuleErrorDisplayVO>();
		List<RoutingRuleError> routingRuleErrorList = routingRuleHdrDao
				.getRoutingRuleErrorForRuleId(ruleId);
		List<RuleErrorDisplayVO> errorList = new ArrayList<RuleErrorDisplayVO>();
		for (RoutingRuleError routingRuleError : routingRuleErrorList) {
			if (routingRuleError.getErrorCauseId().equals(
					RoutingRulesConstants.ZIPCODES_ADDED)
					|| routingRuleError.getErrorCauseId().equals(
							RoutingRulesConstants.ZIPCODES_REMOVED)
					|| routingRuleError.getErrorCauseId().equals(
							RoutingRulesConstants.JOBCODES_ADDED)
					|| routingRuleError.getErrorCauseId().equals(
							RoutingRulesConstants.JOBCODES_REMOVED)) {
				if (errorMap.containsKey("Update")) {
					RuleErrorDisplayVO errorVo = errorMap.get("Update");
					List<String> errorCause = errorVo.getErrorCaue();
					List<String> error = errorVo.getError();
					errorCause.add(routingRuleError.getErrorCauseId()
							.toString());
					error.add(routingRuleError.getError());
					errorVo.setErrorCaue(errorCause);
					errorVo.setError(error);
					errorMap.put("Update", errorVo);

				} else {
					RuleErrorDisplayVO errorVo = new RuleErrorDisplayVO();
					List<String> errorCause = new ArrayList<String>();
					List<String> error = new ArrayList<String>();
					errorCause.add(routingRuleError.getErrorCauseId()
							.toString());
					error.add(routingRuleError.getError());
					errorVo.setErrorCaue(errorCause);
					errorVo.setError(error);
					errorMap.put("Update", errorVo);

				}

			}

			if (routingRuleError.getErrorCauseId().equals(
					RoutingRulesConstants.MISSING_MANDATORY)
					|| routingRuleError.getErrorCauseId().equals(
							RoutingRulesConstants.INVALID_ZIP)
					|| routingRuleError.getErrorCauseId().equals(
							RoutingRulesConstants.INVALID_JOBCODE)
					|| routingRuleError.getErrorCauseId().equals(
							RoutingRulesConstants.INVALID_FIRMS)
					|| routingRuleError.getErrorCauseId().equals(
							RoutingRulesConstants.INVALID_CUST_REFS)) {
				if (errorMap.containsKey("Invalid Fields")) {
					RuleErrorDisplayVO errorVo = errorMap.get("Invalid Fields");
					List<String> errorCause = errorVo.getErrorCaue();
					List<String> error = errorVo.getError();
					errorCause.add(routingRuleError.getErrorCauseId()
							.toString());
					error.add(routingRuleError.getError());
					errorVo.setErrorCaue(errorCause);
					errorVo.setError(error);
					errorMap.put("Invalid Fields", errorVo);

				} else {
					RuleErrorDisplayVO errorVo = new RuleErrorDisplayVO();
					List<String> errorCause = new ArrayList<String>();
					List<String> error = new ArrayList<String>();
					errorCause.add(routingRuleError.getErrorCauseId()
							.toString());
					error.add(routingRuleError.getError());
					errorVo.setErrorCaue(errorCause);
					errorVo.setError(error);
					errorMap.put("Invalid Fields", errorVo);

				}
			}
		}
		Set<String> keys = errorMap.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
			String key = (String) i.next();
			RuleErrorDisplayVO ruleDisplayErrorVo = errorMap.get(key);
			ruleDisplayErrorVo.setErrorName(key);
			errorList.add(ruleDisplayErrorVo);
		}
		return errorList;
	}
	


	/**
	 * Given a buyer ID, return the respective Buyer Assoc.
	 * 
	 * @param buyerId
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleBuyerAssoc getRoutingRuleBuyerAssoc(Integer buyerId) {
		RoutingRuleBuyerAssoc result = routingRuleBuyerAssocDao.findByBuyerId(buyerId);
		return result;
	}

	/**
	 * @return the routingRulePriceDao
	 */
	public RoutingRulePriceDao getRoutingRulePriceDao() {
		return routingRulePriceDao;
	}

	/**
	 * @param routingRulePriceDao the routingRulePriceDao to set
	 */
	public void setRoutingRulePriceDao(RoutingRulePriceDao routingRulePriceDao) {
		this.routingRulePriceDao = routingRulePriceDao;
	}

	/**
	 * @return the routingRuleCriteriaDao
	 */
	public RoutingRuleCriteriaDao getRoutingRuleCriteriaDao() {
		return routingRuleCriteriaDao;
	}

	/**
	 * @param routingRuleCriteriaDao the routingRuleCriteriaDao to set
	 */
	public void setRoutingRuleCriteriaDao(RoutingRuleCriteriaDao routingRuleCriteriaDao) {
		this.routingRuleCriteriaDao = routingRuleCriteriaDao;
	}

	public void setRoutingRuleAlertService(RoutingRuleAlertService routingRuleAlertService) {
		this.routingRuleAlertService = routingRuleAlertService;
	}

	public void setRoutingRulesConflictFinderService(
			RoutingRulesConflictFinderService routingRulesConflictFinderService) {
		this.routingRulesConflictFinderService = routingRulesConflictFinderService;
	}

	/**
	 * @return the routingRuleVendorDao
	 */
	public RoutingRuleVendorDao getRoutingRuleVendorDao() {
		return routingRuleVendorDao;
	}

	/**
	 * @return RoutingRuleHdr associated with this ID, or null.
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleHdr getRoutingRulesHeader(Integer routingRuleHdrId) {
		return routingRuleHdrDao.findByRoutingRuleHdrId(routingRuleHdrId);
	}

	/**
	 * Return a provider firm given its ID, or null.
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ProviderFirm getProviderFirm(Integer firmId) {
		return providerFirmDao.findById(firmId);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LabelValueBean> getCustomReferences(Integer buyerId) {
		List<BuyerReferenceType> list = buyerReferenceTypeDao.findByBuyerId(buyerId);
		List<LabelValueBean> results = new ArrayList<LabelValueBean>();
		for (BuyerReferenceType item : list) {
			// SL-8062: TEMPORARY! Will soon revert back to:
			// LabelValueBean lvb = new LabelValueBean(item.getRefDescr(),
			// item.getRefType());
			LabelValueBean lvb = new LabelValueBean(item.getRefType(), item.getRefType());
			results.add(lvb);
		}
		Collections.sort(results);
		return results;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleHdr> getAllExistingRoutingRulesHeaders() {
		final List<RoutingRuleHdr> result = routingRuleHdrDao.findAll();
		return result;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LabelValueBean> getMarkets() {
		List<LookupMarket> list = lookupMarketDao.findActive();
		List<LabelValueBean> results = new ArrayList<LabelValueBean>(list.size());
		for (LookupMarket item : list) {
			LabelValueBean lvb = new LabelValueBean(item.getDescription(), item.getId().toString());
			results.add(lvb);
		}
		Collections.sort(results);
		return results;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String,String> getMarkets(List<String> markets){
		return lookupMarketDao.getMaprkets(markets);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LabelValueBean> getStates() {
		List<LookupStates> list = lookupStatesDao.findActive();
		List<LabelValueBean> results = new ArrayList<LabelValueBean>(list.size());
		for (LookupStates item : list) {
			LabelValueBean lvb = new LabelValueBean(item.getDescription(), item.getId());
			results.add(lvb);
		}
		Collections.sort(results);
		return results;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LabelValueBean> getZipcodesByStateAbbrev(String stateCd) {
		if (stateCd == null) {
			return null;
		}

		List<LookupZipGeocode> list = lookupZipGeocodeDao.findByState(stateCd);
		List<LabelValueBean> results = new ArrayList<LabelValueBean>(list.size());
		for (LookupZipGeocode item : list) {
			LabelValueBean lvb = new LabelValueBean(format(item), item.getZip());
			results.add(lvb);
		}
		return results;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public String getCityByZipcode(String zipCode) {
		if (zipCode == null) {
			return null;
		}

		try {
			LookupZipGeocode item = lookupZipGeocodeDao.findByZip(zipCode);
			return format(item);
		} catch (javax.persistence.NoResultException nre) {
			return null;
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String,String> getCityByZipcodeList(List<String> zips){
		return lookupZipGeocodeDao.findCityByZips(zips);
	}
	
	

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LabelValueBean> getSpecialtyCodes(Integer buyerId) {
		List<String> list = buyerSkuTaskAssocDao.findDistinctSpecialtyCodeByBuyerId(buyerId);
		Collections.sort(list);
		List<LabelValueBean> results = new ArrayList<LabelValueBean>(list.size());
		for (String item : list) {
			LabelValueBean lvb = new LabelValueBean(item, item);
			results.add(lvb);
		}
		return results;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LabelValueBean> getJobcodesForSpecialtyCode(Integer buyerId, String specialtyCode) {
		if (specialtyCode == null || buyerId == null) {
			return null;
		}

		List<String> list = buyerSkuDao.findBySpecialtyCodeAndBuyerID(buyerId, specialtyCode);
		List<LabelValueBean> results = new ArrayList<LabelValueBean>(list.size());

		for (String item : list) {
			LabelValueBean lvb = new LabelValueBean(item, item);
			results.add(lvb);
		}

		Collections.sort(results);
		return results;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void persistRoutingRule(RoutingRuleHdr routingRuleHdr) throws Exception {
		if (routingRuleHdr.getContact() != null && routingRuleHdr.getContact().getContactId() == null) {
			routingRuleContactDao.save(routingRuleHdr.getContact());
		}

		if (routingRuleHdr.getRoutingRuleBuyerAssoc() != null && routingRuleHdr.getRoutingRuleBuyerAssoc().getBuyer() != null
				&& routingRuleHdr.getRoutingRuleBuyerAssoc().getBuyer().getBuyerId() != null
				&& routingRuleHdr.getRoutingRuleBuyerAssoc().getBuyer().getBusinessName() == null) {
			Integer buyerId = routingRuleHdr.getRoutingRuleBuyerAssoc().getBuyer().getBuyerId();
			Buyer buyer = routingRuleBuyerDao.findById(buyerId);
			routingRuleHdr.getRoutingRuleBuyerAssoc().setBuyer(buyer);
		}

		if (routingRuleHdr.getRoutingRuleBuyerAssoc() != null && routingRuleHdr.getRoutingRuleBuyerAssoc().getId() == null) {
			routingRuleBuyerAssocDao.save(routingRuleHdr.getRoutingRuleBuyerAssoc());
		}

		routingRuleHdrDao.save(routingRuleHdr);
	}


	@Transactional(propagation = Propagation.SUPPORTS)
	public LookupRoutingRuleType getRoutingRuleType(RoutingRuleType type) {
		return lookupRoutingRuleTypeDao.findById(type.getId());
	}

	/**
	 * To update status of list of ruleIds
	 * @param List<String> ruleIds
	 * @param String comment
	 * @param String status
	 * @param String modifiedBy
	 * @return List<RoutingRuleHdr>
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<RoutingRuleHdr> updateRoutingRuleHdrStatusAndComment(
			List<String> ruleIds, String comment, String status,
			String modifiedBy) throws Exception {
		List<RoutingRuleHdr> ruleList = getRoutingRulesHeadersForRuleIds(ruleIds);
		Integer buyerAssocId = 0;
		if(ruleList!=null){
			RoutingRuleHdr rulehdr = ruleList.get(0);
			buyerAssocId= rulehdr.getRoutingRuleBuyerAssoc().getId();
			logger.info("rule.getRoutingRuleBuyerAssoc().getId():::"+buyerAssocId);
		}
		BuyerResource buyer = routingRuleHdrHistDao
				.getBuyerResource(modifiedBy);
		if (ruleList == null) {
			throw new Exception("rule id " + ruleIds + " not found");
		}
		List<RoutingRuleHdr> ruleIdList = new ArrayList<RoutingRuleHdr>();
		

		for (RoutingRuleHdr rule : ruleList) {
			if (rule.getRuleStatus().equalsIgnoreCase(status)) {
				
			}
			else
			{
				ruleIdList.add(rule);
			}
		}
		for (RoutingRuleHdr rule : ruleList) {
			if (rule.getRuleStatus().equalsIgnoreCase(status)) {
			} else {
				if (status
						.equalsIgnoreCase(RoutingRulesConstants.ROUTING_RULE_STATUS_ARCHIVED)) {
					rule.setRuleSubstatus(RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_VALID);
				}
				if (status
						.equalsIgnoreCase(RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE)
						|| status
								.equalsIgnoreCase(RoutingRulesConstants.ROUTING_RULE_STATUS_INACTIVE)) {
					rule
							.setRuleSubstatus(RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_VALID);
				}
				rule.setRuleStatus(status);
				rule.setModifiedDate(new Date());
				rule.setModifiedBy(modifiedBy);
				if (comment == null || "".equals(comment)) {
					rule.setRuleComment("");
				}
				if (comment != null && comment.trim().length() > 0) {
					rule.setRuleComment(comment.trim());
				}
				
				updateRuleHistory(rule, status, buyer);
				persistRoutingRule(rule);
				deletePersistentErrors(rule.getRoutingRuleHdrId());
				// SL-16913 : Delete the non-persistent error if the rule is activated
				if (status.equalsIgnoreCase(RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE)){
					routingRuleErrorDao.deleteNonPersistentErrors(rule.getRoutingRuleHdrId());
				}
				// SL-16913 .End
				}
		}
			try
			{
		String action="";
		if(status.equalsIgnoreCase("Active"))
		{
			action=RoutingRulesConstants.RULE_ACTION_ACTIVATE;
			
		}
		else if(status.equalsIgnoreCase("Inactive"))
		{
			action = RoutingRulesConstants.RULE_ACTION_INACTIVATE;
			
		}
		else
		{
			action = RoutingRulesConstants.RULE_ACTION_ARCHIVED;
		}
			
		logger.info("UPDATING hASMap");		
		logger.info("action"+action);
		if(null != action && !action.equalsIgnoreCase("")){
			if(ruleIdList!=null && ruleIdList.size()>0){
				routingRulesConflictFinderService.updateActiveRulesCache(ruleIdList, action, buyerAssocId);
				}
		}
		}
		catch(Exception e)
		{
			logger.info("Exception  UPDATING cache");
		}
		return ruleList;
	}
	
	/**
	 * To remove non persistent errors
	 * @param ruleId
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void removeErrors(Integer ruleId)
	{
		routingRuleErrorDao.deleteNonPersistentErrors(ruleId);
		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public RoutingRuleHdr updateRoutingRule(Map<String, Object> map, Integer contextBuyerId, String modifiedBy) throws Exception {
		RoutingRuleHdr rule = updateRoutingRuleHdr(map, contextBuyerId, modifiedBy);
		RoutingRuleBuyerAssoc routingRuleBuyerAssoc = getRoutingRuleBuyerAssoc(contextBuyerId);
        Integer buyerAssocId = routingRuleBuyerAssoc.getId();
		rule.setRuleSubstatus(RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_VALID);
		String makeInactive = (String) map.get("makeInactive");
		List<RoutingRuleError> routingRuleError = new ArrayList<RoutingRuleError>();
		if(makeInactive!=null && makeInactive.equalsIgnoreCase("makeInactive")){
			routingRuleError = routingRulesConflictFinderService
						.findRoutingRuleConflictsForRuleHdr(rule, buyerAssocId,RoutingRulesConstants.UPDATE_FROM_FRONT);
			rule.setRuleStatus(RoutingRulesConstants.ROUTING_RULE_STATUS_INACTIVE);
			rule
			.setRuleSubstatus(RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_CONFLICT);
				
		}
		Integer ruleId = rule.getRoutingRuleHdrId();
		String histAction = "";
		List<RoutingRuleHdr> ruleHdrs = new ArrayList<RoutingRuleHdr>();
		if(rule.getRoutingRuleHdrId()!=null){
			histAction = RoutingRulesConstants.HISTORY_UPDATED;
			ruleHdrs.add(rule);
			logger.info("Deleting Persist");
			
		}else{
			histAction = RoutingRulesConstants.HISTORY_CREATED;
		}
		persistRoutingRule(rule);
		if(rule.getRoutingRuleHdrId()!=null){
			deletePersistentErrors(ruleId);
			// Delete non-persistent errors 
			routingRuleErrorDao.deleteNonPersistentErrors(ruleId);
		}
		if(makeInactive!=null && makeInactive.equalsIgnoreCase("makeInactive")){
			logger.info("Makeinactive");
			if (routingRuleError != null
					&& routingRuleError.size() > 0) {
				for (RoutingRuleError ruleError : routingRuleError) {
					ruleError
							.setErrorType(RoutingRulesConstants.PERSISTENT_ERROR);
					ruleError.setRoutingRuleHdr(rule);
					ruleError.setModifiedBy(modifiedBy);
				}
				rule.getRoutingRuleError().addAll(
						routingRuleError);
				persistRoutingRule(rule);
			}
			routingRulesConflictFinderService.updateActiveRulesCache(ruleHdrs,RoutingRulesConstants.RULE_ACTION_INACTIVATE,buyerAssocId);
			histAction = RoutingRulesConstants.HISTORY_DEACTIVATED;
		}else if(rule.getRuleStatus().equals(RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE)){
			routingRulesConflictFinderService.updateActiveRulesCache(ruleHdrs,RoutingRulesConstants.RULE_ACTION_UPDATE,buyerAssocId);
		}
		BuyerResource buyer = routingRuleHdrHistDao.getBuyerResource(modifiedBy);
		updateRuleHistory(rule, rule.getRuleStatus(),histAction, buyer); 
		routingRuleAlertService.processAlertsForOneRule(rule);
		return rule;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RuleConflictDisplayVO> updateRoutingRuleAndCheckConflicts(
			Map<String, Object> map, Integer contextBuyerId, String modifiedBy)
			throws Exception {
		Integer ruleId= null;
        RoutingRuleHdr rule= null;
        String ruleIdVal;
        
		List<String> customRefsList = (List<String>) map.get("chosenCustomRefs");
		List<String> marketsZipsList = (List<String>) map.get("chosenMarketsZips");
		List<JobCodeVO> jobsList = (List<JobCodeVO>) map.get("chosenJobCodes");
		
		ruleIdVal = (String)map.get("ruleId");
        if (null != ruleIdVal) {
            ruleId= new Integer(ruleIdVal);
            rule= getRoutingRulesHeader(ruleId);		
            
        } else {
            rule= new RoutingRuleHdr();
    		rule.setRuleStatus(RoutingRulesConstants.ROUTING_RULE_STATUS_INACTIVE);
            rule.setCreatedDate(new Date());
        	
        }
        
        RoutingRuleBuyerAssoc routingRuleBuyerAssoc = getRoutingRuleBuyerAssoc(contextBuyerId);
        Integer buyerAssocId = routingRuleBuyerAssoc.getId();
        RoutingRulesConflictVO ruleConflictVo = new RoutingRulesConflictVO();
        List<String> zipCodes = new ArrayList<String>();
        List<String> jobCodes =new ArrayList<String>();
        List<String> markets =new ArrayList<String>();
    	List<String> pickupLocation = new ArrayList<String>();
    	Map<Integer,List<String>> marketZipsMap = new HashMap<Integer, List<String>>();
    	
        if (customRefsList != null) {
			for (String item : customRefsList) {
				final String[] nameValue = item.split(DELIMITER);
				if (nameValue == null || nameValue.length != 2) {
					logger.warn("Ignoring badly formed criteria name/val pair " + item);
					continue;
				}
				if( RoutingRulesConstants.CRITERIA_NAME_PICKUP_LOCATION.equals(nameValue[0])){
					pickupLocation.add(nameValue[1]);
				}
			}
		}
		if (marketsZipsList != null) {
			for (String item : marketsZipsList) {
				if (item.startsWith("zip")) {
					zipCodes.add(item.substring(3));
				} else if (item.startsWith("market")){
					markets.add(item.substring(6));
				}
			}
		}
		if (markets != null) {
			for(String marketId:markets){
				List<String> zips = routingRuleHdrDao.findMarketZips(Integer.parseInt(marketId));
				marketZipsMap.put(Integer.parseInt(marketId), zips);
				
			}
		}
		if(jobsList!=null){
			for (JobCodeVO jobCode : jobsList) {
				jobCodes.add(jobCode.getJobCode());
			}
		}
		
		ruleConflictVo.setZipCodes(zipCodes);
		ruleConflictVo.setJobCodes(jobCodes);
		ruleConflictVo.setPickupLocation(pickupLocation);
		ruleConflictVo.setMarketZipsMap(marketZipsMap);
		ruleConflictVo.setRuleId(ruleId);
		List<RoutingRulesConflictVO> baseRuleVOList = new ArrayList<RoutingRulesConflictVO>();
		baseRuleVOList.add(ruleConflictVo);
		Map<Integer,List<RoutingRuleError>> errorMap = new HashMap<Integer, List<RoutingRuleError>>();
        List<RoutingRuleError> errorList = new ArrayList<RoutingRuleError>();
        logger.info("baseRuleVOList from Edit:"+baseRuleVOList);
		if (rule.getRuleStatus().equals(
				RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE)) {
			errorMap = routingRulesConflictFinderService
					.findConflictingRoutingRules(baseRuleVOList, buyerAssocId,RoutingRulesConstants.UPDATE_FROM_FRONT);
			if(errorMap!=null && errorMap.size()>0){
				errorList = errorMap.get(ruleId);
			}
		}
		Integer conflictingRule = 0;
		RuleConflictDisplayVO displayVO = new RuleConflictDisplayVO();
		List<RuleConflictDisplayVO> ruleConflictDisplayList = new ArrayList<RuleConflictDisplayVO>();
		for(RoutingRuleError error :errorList){
			if(!conflictingRule.equals(error.getConflictingRuleId())){
			displayVO = new RuleConflictDisplayVO();
			RoutingRuleHdr conflictingHdr = routingRuleHdrDao.findByRoutingRuleHdrId(error.getConflictingRuleId());
			displayVO.setRuleName(conflictingHdr.getRuleName());
			displayVO.setRuleId(conflictingHdr.getRoutingRuleHdrId());
			ruleConflictDisplayList.add(displayVO);
			}
			if(error.getErrorCauseId().equals(RoutingRulesConstants.CONFLICTING_JOBCODES)){
				displayVO.setJobCodes(error.getError());
			}
			if(error.getErrorCauseId().equals(RoutingRulesConstants.CONFLICTING_ZIPCODES)){
				displayVO.setZipCodes(error.getError());
			}
			if(error.getErrorCauseId().equals(RoutingRulesConstants.CONFLICTING_PICKUPLOCATIONS)){
				displayVO.setPickupLocation(error.getError());
			}
			if(error.getErrorCauseId().equals(RoutingRulesConstants.CONFLICTING_MARKETS)){
				displayVO.setMarkets(error.getError());
			}
			conflictingRule = error.getConflictingRuleId();
		}
		return ruleConflictDisplayList;
		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleHdrHist> getRoutingRuleHeadersHist(Integer contextBuyerId, RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator) {
		List<RoutingRuleHdrHist> hists = routingRuleHdrHistDao.findByBuyerId(contextBuyerId,pagingCriteria,archiveIndicator);
		return hists;
	}

	/** {@inheritDoc} */
	public String getProviderFirmsJson(Integer ... firmIds) {	
		if (firmIds == null || firmIds.length == 0)
			return "null";

		List<RoutingRulesFirmVO> firmVOs = new ArrayList<RoutingRulesFirmVO>(firmIds.length);
		RoutingRulesFirmVO firmVO;
		ProviderFirm firm;

		for (Integer firmId : firmIds) {

			firm = getProviderFirm(firmId);
			if (firm == null) {
				logger.warn("Unable to getFirmDetails for Firm ID of " + firmId);
				continue; 
			}

			firmVO = new RoutingRulesFirmVO();
			firmVO.setBusinessName(firm.getBusinessName());
			if (firm.getId() != null) {
				firmVO.setId(firm.getId().toString());
			}

			LookupWfStates state = firm.getServiceLiveWorkFlowState();
			if (state != null) {
				firmVO.setState(state);
			}

			firmVOs.add(firmVO);
		}

		return getJSON(firmVOs);
	}

	/** {@inheritDoc} */
	public String getProviderFirmsJson(RoutingRuleHdr rule) {
		List<RoutingRulesFirmVO> firmVOs = new ArrayList<RoutingRulesFirmVO>();
		ProviderFirm firm;

		if (null != rule) {
			List<RoutingRuleVendor> vendors = rule.getRoutingRuleVendor();

			if (vendors != null) {
				for (RoutingRuleVendor vendor : vendors) {
					firm = vendor.getVendor();
					firmVOs.add(new RoutingRulesFirmVO(firm.getBusinessName(), firm.getId().toString(), 
							firm.getServiceLiveWorkFlowState(),vendor));
				}
			}
		}		
		return (getJSON(firmVOs));
	}	
	
	/** {@inheritDoc} */
	public String getRuleCriteriaJson(Integer ruleId) {
		RoutingRuleHdr rule= null;		
		if (ruleId!=null) {
			 rule= getRoutingRulesHeader(ruleId);
		}
		return getRuleCriteriaJson(rule);		
	}

	/** {@inheritDoc} */
	public String getRuleCriteriaJson(RoutingRuleHdr rule) {
		List<LabelValueBean> markets= null;
		List<Map<String,String>> list= null;
		
		if (null != rule) {
			List<RoutingRuleCriteria> critters= rule.getRoutingRuleCriteria();
			list= new ArrayList<Map<String,String>>(critters.size());
			Map<String,String> map;
			String name, value;
			
			for (RoutingRuleCriteria critter : critters) {
				map= new HashMap<String,String>(10);				
				name= critter.getCriteriaName();
				value= critter.getCriteriaValue();
				map.put("name",  name);
				
				if (RoutingRuleType.ZIP_MARKET.toString().equals(critter.getRuleType().getDescription())) {
					if (CRITERIA_NAME_MARKET.equals(name)) {
						map.put("value", value);
						if (markets==null) {
							markets= getMarkets();
						}
						for (LabelValueBean market : markets) {
							if (market.getValue().equals(value)) {
								map.put("label", market.getLabel());
								break;
							}
						}					
					} else if (CRITERIA_NAME_ZIP.equals(name)) {
						map.put("value", "zip" + value);
						map.put("label", getCityByZipcode(value));
					} else {
						logger.warn("Market/Zip type not matched: " + name);
					}
				} else if (RoutingRuleType.CUSTOM_REF.toString().equals(critter.getRuleType().getDescription())) {
					map.put("value", value);
					map.put("label", name);					
				} else {
					logger.warn("Rule type not matched: " + critter.getRuleType().getDescription());
				}
				list.add(map);
			}
		}		
		return getJSON(list);
	}
	/**
	 * The method is to get rule criteria for a rule
	 * @param ruleId
	 * @return
	 */
	public Map<String,List<String>> getRuleCriteriaForRuleId(Integer ruleId) {
		RoutingRuleHdr rule = getRoutingRulesHeader(ruleId);
		return getRuleCriteria(rule);
	}
	/**
	 * The method is to get rule criteria for a rule
	 * @param rule
	 * @return
	 */
	public Map<String,List<String>> getRuleCriteria(RoutingRuleHdr rule) {
		List<String> custReferenceList = new ArrayList<String>();
		List<String> zipMarketList = new ArrayList<String>();
		List<String> markets = new ArrayList<String>();
		List<String> zips = new ArrayList<String>();
		Map<String,List<String>> criteriaMap=new HashMap<String,List<String>>();
		if (null != rule) {
			List<RoutingRuleCriteria> critters= rule.getRoutingRuleCriteria();
			String name, value;
			
			for (RoutingRuleCriteria critter : critters) {
				name= critter.getCriteriaName();
				value= critter.getCriteriaValue();
				if (RoutingRuleType.ZIP_MARKET.toString().equals(critter.getRuleType().getDescription())) {
					if (CRITERIA_NAME_MARKET.equals(name)) {
						zipMarketList.add("market"+value);
						markets.add(value);
					} else if (CRITERIA_NAME_ZIP.equals(name)) {
						zipMarketList.add("zip"+value);
						zips.add(value);
					} else {
						logger.warn("Market/Zip type not matched: " + name);
					}
				} else if (RoutingRuleType.CUSTOM_REF.toString().equals(critter.getRuleType().getDescription())) {
					custReferenceList.add(name + DELIMITER + value);
				} else {
					logger.warn("Rule type not matched: " + critter.getRuleType().getDescription());
				}
			}
			criteriaMap.put("ZIP_LIST", zipMarketList);
			criteriaMap.put("CUST_REF_LIST", custReferenceList);
			criteriaMap.put("ZIPS", zips);
			criteriaMap.put("MARKETS", markets);
		}		
		return criteriaMap;
	}

	
	/**
	 * The method is to get job codes for a rule
	 * @param rule
	 * @return
	 */
	public List<JobCodeVO> getRuleJobCodes(RoutingRuleHdr rule) {
		
		List<JobCodeVO> jobList = new ArrayList<JobCodeVO>();
		if (null != rule) {
			List<RoutingRulePrice> rulePriceList = rule.getRoutingRulePrice();
			for (RoutingRulePrice rulePrice : rulePriceList) {
				String jobCode = rulePrice.getJobcode();
				Double jobCodePrice = null;
				if (null != rulePrice.getPrice()) {
					jobCodePrice = (Double) rulePrice.getPrice().doubleValue();
				}
				JobCodeVO jobPriceVO = new JobCodeVO(jobCode, jobCodePrice);
				jobList.add(jobPriceVO);
				//JobPriceVO jobPriceVO = new JobPriceVO(jobCode, jobCodePrice);
				//jobList.add(jobPriceVO);
			}
		}
		return jobList;
	}		
		
	
	/** {@inheritDoc} */
	public String getJSON(Object obj) {
		String rval= "null";
		JSONSerializer serializer = new JSONSerializer();

        if (obj != null) try {
			rval= serializer.exclude("*.class").serialize(obj); 
		} catch (Throwable e) {
			logger.error("Unable to generate JSON for object: " + obj.toString(), e);
		}				
		return rval;
	}
	
	private String format(LookupZipGeocode item) {
		return item.getZip() + " - " + item.getCity() + " " + item.getStateCd();
	}

	/**
	 * Create or update rule
	 * @return Populated rule
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected RoutingRuleHdr updateRoutingRuleHdr(Map<String, Object> map, Integer contextBuyerId, String modifiedBy) throws Exception {
		Integer ruleId= null;
        RoutingRuleHdr rule= null;

        String ruleIdVal;
		String ruleName = (String) map.get("ruleName");
		String makeInactive = (String) map.get("makeInactive");
		String ruleComment = (String) map.get("ruleComment");
		String firstName = (String) map.get("firstName");
		String lastName = (String) map.get("lastName");
		String email = (String) map.get("email");
		String[] vendorIds = (String[]) map.get("chosenProviderFirms");
		List<String> customRefsList = (List<String>) map.get("chosenCustomRefs");
		List<String> marketsZipsList = (List<String>) map.get("chosenMarketsZips");
		List<JobCodeVO> jobsList = (List<JobCodeVO>) map.get("chosenJobCodes");
		StringBuffer comment= new StringBuffer(ruleComment + "\n---\n");
		validate(vendorIds, customRefsList, marketsZipsList, jobsList);
		ruleIdVal = (String)map.get("ruleId");
        if (null != ruleIdVal) {
            ruleId= new Integer(ruleIdVal);
            rule= getRoutingRulesHeader(ruleId);		
            if (!ruleName.equals(rule.getRuleName())) {
                comment.append("/ Rule Name: " + rule.getRuleName() + " -> " + ruleName + "\n");
                rule.setRuleName(ruleName);
            }
        } else {
            rule= new RoutingRuleHdr();
    		rule.setRuleStatus(RoutingRulesConstants.ROUTING_RULE_STATUS_INACTIVE);
            rule.setRuleName(ruleName);
            rule.setCreatedDate(new Date());
        	comment.append("+ RuleName=" + ruleName + "\n");
        }

        rule.setModifiedDate(new Date());
        rule.setModifiedBy(modifiedBy);
        //SL 15642 To set the auto accept status on the basis of criteria of buyer
        String autoAcceptStatus = (String) map.get("autoAcceptStatCarRuleCreation");
		reconcileContactWithRule(rule, comment, firstName, lastName, email);
        //Added one more parameter to the method reconcileVendorwithRule to pass auto acceptance status
		//Passing the user action to change the auto accept status as per scenario
		String userActionType=(String) map.get("userAction");
		List<RoutingRuleVendor> vendorsToRemove= reconcileVendorsWithRule(rule, comment, vendorIds,autoAcceptStatus,userActionType,modifiedBy,map);
		if (vendorsToRemove != null) { 
			for (RoutingRuleVendor vendor : vendorsToRemove) {
				routingRuleVendorDao.delete(vendor);
				routingRuleVendorDao.deleteHistoryFromAutoAccpetHistory(Integer.parseInt(ruleIdVal),vendor.getVendor().getId());
			}
		}
		
        List<RoutingRuleCriteria> criteriaToRemove= reconcileCriterionWithRule (rule, comment, customRefsList, marketsZipsList);
        if (criteriaToRemove != null) {
	        for (RoutingRuleCriteria critter : criteriaToRemove) {
	        	routingRuleCriteriaDao.delete(critter);
	        }
        }
        List<RoutingRulePrice> jobCodeToRemove= reconcileJobCodeWithRule (rule, comment, jobsList);
        if (jobCodeToRemove != null) {
	        for (RoutingRulePrice price : jobCodeToRemove) {
	        	routingRulePriceDao.delete(price);
	        }
        }
        
       rule.setRuleComment((comment.length() > ROUTING_RULE_HDR_COMMENT_LEN) 
        	? comment.substring(0,ROUTING_RULE_HDR_COMMENT_LEN - 5) + " >>"
			: comment.toString());        	        

		RoutingRuleBuyerAssoc routingRuleBuyerAssoc = getRoutingRuleBuyerAssoc(contextBuyerId);
		if (routingRuleBuyerAssoc == null) {
			// First rule for this buyer; create assoc
			routingRuleBuyerAssoc = new RoutingRuleBuyerAssoc();
			routingRuleBuyerAssoc.setModifiedBy(modifiedBy);

			List<RoutingRuleHdr> rules = new ArrayList<RoutingRuleHdr>(1);
			rule.setRoutingRuleBuyerAssocId(routingRuleBuyerAssoc);

			rules.add(rule);
			routingRuleBuyerAssoc.setRoutingRuleHeaders(rules);

			Buyer buyer = new Buyer();
			buyer.setBuyerId(contextBuyerId);
			routingRuleBuyerAssoc.setBuyer(buyer);
		} else {
			List<RoutingRuleHdr> hdrs= routingRuleBuyerAssoc.getRoutingRuleHeaders();
			if (!hdrs.contains(rule)) {
				// SL-8562: commenting out to prevent superfluous "UPDATED" hdr_hist logfile entry:
				// routingRuleBuyerAssoc.getRoutingRuleHeaders().add(rule);
				rule.setRoutingRuleBuyerAssocId(routingRuleBuyerAssoc);
			}
		}
		return rule;
	}

    @SuppressWarnings("null")
	private void reconcileContactWithRule(RoutingRuleHdr rule, StringBuffer comment, String firstName, String lastName, String email) {
    	Contact contact= rule.getContact();
    	boolean hasChanges=false;
        boolean isNew= (contact==null);

        if (isNew) {
        	contact = new Contact();
        	rule.setContact(contact);
        } 
        
        if (!firstName.equals(contact.getFirstName())) {
            if (!isNew) { comment.append("/ FirstName: " + contact.getFirstName() + " -> " + firstName + "\n"); }
            contact.setFirstName(firstName);
            hasChanges=true;
        }
        if (!lastName.equals(contact.getLastName())) {
            if (!isNew) { comment.append("/ LastName: " + contact.getLastName() + " -> " + lastName + "\n"); }
            contact.setLastName(lastName);
            hasChanges=true;
        }
        if (!email.equals(contact.getEmail())) {
            if (!isNew) { comment.append("/ Email: " + contact.getEmail() + " -> " + email + "\n"); }
            contact.setEmail(email);
            hasChanges=true;
        }        
        if (hasChanges) {
        	contact.setModifiedBy(rule.getModifiedBy());
        }        
    }

	private List<RoutingRuleVendor> reconcileVendorsWithRule(RoutingRuleHdr rule, StringBuffer comment, String[] strNewVendorIds,String autoAcceptStatus,String userActionType, String modifiedBy,Map<String, Object> map) throws Exception {
		List<RoutingRuleVendor> ruleVendors= rule.getRoutingRuleVendor();
		List<Integer> joinedRuleIds= new ArrayList<Integer>(strNewVendorIds.length);
		List<RoutingRuleVendor> vendorsToRemove= new ArrayList<RoutingRuleVendor>();

		if (ruleVendors == null) {
			ruleVendors = new ArrayList<RoutingRuleVendor>();
			rule.setRoutingRuleVendor(ruleVendors);
		}

		List<Integer> newVendorIds = new ArrayList<Integer>();
		for (String strVendorId : strNewVendorIds) {
			newVendorIds.add(Integer.valueOf(strVendorId));
		}

		// DELETE vendor ID's in the rule that aren't in the newVendorIds list		
		for (Iterator<RoutingRuleVendor> ruleVendorsIter= ruleVendors.iterator(); ruleVendorsIter.hasNext(); ) {			
			RoutingRuleVendor vendor= ruleVendorsIter.next();
			Integer rvId= vendor.getVendor().getId();
			if (newVendorIds.contains(rvId)) {
				joinedRuleIds.add(rvId);  // these ID's are in common between old and new
			} else {
				comment.append("- Vendor=" + rvId.toString() + "\n");	
				vendorsToRemove.add(vendor);
				ruleVendorsIter.remove();
			}
		}
		
		//Condition to check user action for new rule or already existing rule
		//And update the auto accept status as per the scenario
		if(null!=userActionType && userActionType.equals("EDIT") && null!=autoAcceptStatus && !autoAcceptStatus.equalsIgnoreCase("NA"))
		{
		//Call to get the auto update status of already existing vendors
			@SuppressWarnings("unused")
			Map<Integer,String> autoAcceptListOfProviders=new TreeMap<Integer, String>();
			autoAcceptListOfProviders=routingRuleVendorDao.getProviderAutoAcceptanceStatus(joinedRuleIds,rule.getRoutingRuleHdrId());
			BuyerResource buyer = routingRuleHdrHistDao.getBuyerResource(modifiedBy);
			if(autoAcceptListOfProviders!=null)
			{
				for(Map.Entry<Integer,String> providerInfos:autoAcceptListOfProviders.entrySet())
				{
					if(providerInfos.getValue()!=null&&providerInfos.getValue().equalsIgnoreCase(RoutingRulesConstants.AUTO_ACCEPT_ON_STATUS))
					{
						routingRuleVendorDao.updateAutoStatusForRule(providerInfos.getKey(),rule.getRoutingRuleHdrId());
						//Changes to send mail to provider on auto accept status change
						AlertTask alertTask=new AlertTask();
						Date currentdate = new Date();
						alertTask.setAlertedTimestamp(null);
						alertTask.setAlertTypeId(AlertConstants.ALERT_TYPE_ID);
						alertTask.setCreatedDate(currentdate);
						alertTask.setModifiedDate(currentdate);
						//setting template_id
						alertTask.setTemplateId(AlertConstants.TEMPLATE_AUTO_ACCPET_CHANGE_PROVIDER_CHECK_EMAIL);
						//Method to fetch provider email id from DB
						String providerAdminEmailId= routingRuleVendorDao.getProviderAdminEmailId(providerInfos.getKey());
						alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
						alertTask.setAlertTo(providerAdminEmailId);
						alertTask.setAlertBcc(Constants.EMPTY_STRING);
						alertTask.setAlertCc(Constants.EMPTY_STRING);
						alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
						alertTask.setPriority(AlertConstants.PRIORITY);
						//generating template_input_value from Map
						Map<String, String> alertMap = new HashMap<String, String>();
						alertMap.put(Constants.FIRM_ID, Integer.toString(providerInfos.getKey()));
						//Method to get vendor detail to send email 
						ServiceProvider rrv=routingRuleVendorDao.getProviderDetail(providerInfos.getKey());
						alertMap.put(Constants.BUYER_NAME, buyer.getBuyer().getContact().getFullName());
						alertMap.put(Constants.PROVIDER_ADMIN_EMAIL_ID, providerAdminEmailId);
						alertMap.put(Constants.PROVIDER_FIRST_NAME, rrv.getContact().getFirstName());
						alertMap.put(Constants.PROVIDER_LAST_NAME, rrv.getContact().getLastName());
						alertMap.put(Constants.FIRM_NAME, rrv.getProviderFirm().getBusinessName());
						alertMap.put(Constants.RULE_NAME,rule.getRuleName());
						alertMap.put(Constants.BUYER_EMAIL, buyer.getBuyer().getContact().getEmail());
						alertTask.setTemplateInputValue(createMailMergeValueStringFromMap(alertMap));
						routingRuleVendorDao.saveAutoAcceptChangeProviderMailInfo(alertTask);
						updateAutoAcceptHistory(rule, RoutingRulesConstants.AUTO_ACCEPT_PENDING_STATUS,providerInfos.getKey(), modifiedBy, 3, RoutingRulesConstants.AUTO_ACCEPT_HISTORY_UPDATED,map);
						
					}
					else{
						//Don't make any changes in the routing rule vendor for this particular vendor belonging to this rule
						//If they are not the new vendors and they have auto acceptance status as PENDING
					}
					
				}
				
			}
		}
		
		// CREATE vendors for those in the newVendorIds list but not in the ruleVendors
		if(null!=userActionType && userActionType.equals("CREATE")&& null!=autoAcceptStatus && !autoAcceptStatus.equalsIgnoreCase("NA"))
		{
			BuyerResource buyer = routingRuleHdrHistDao.getBuyerResource(modifiedBy);
			updateAutoAcceptHistory(rule,autoAcceptStatus,null,modifiedBy,buyer.getUser().getRole().getId(),RoutingRulesConstants.AUTO_ACCEPT_HISTORY_CREATED,map);
			//Setting value to the auto acceptance bean class
		}
	    
		for (Integer newVendorId : newVendorIds) {
			if (!joinedRuleIds.contains(newVendorId)) {
				final RoutingRuleVendor vendor = new RoutingRuleVendor();
				final ProviderFirm providerFirm = getProviderFirm(newVendorId);
				comment.append("+ Vendor=" + newVendorId.toString() + "\n");
				vendor.setModifiedBy(rule.getModifiedBy());
				vendor.setRoutingRuleHdr(rule);
				vendor.setVendor(providerFirm);
				vendor.setAutoAcceptStatus(autoAcceptStatus);
				ruleVendors.add(vendor);
			
		}
			}
		return vendorsToRemove;
		
	}

	
	/**
	 * @param rule
	 * @param customRefsAry
	 * @param marketsZipsAry
	 * @return List of criteria to be removed from the existing rule  
	 */
	private List<RoutingRuleCriteria> reconcileCriterionWithRule (RoutingRuleHdr rule, StringBuffer comment, List<String> customRefsAry, List<String> marketsZipsAry) {
		List<RoutingRuleCriteria> ruleCriterion= rule.getRoutingRuleCriteria();
		List<String[]> joinedKeys= new ArrayList<String[]>();
		List<String[]> criterionNameVal= new ArrayList<String[]>();
		List<LookupVO> marketVOs = lookupBO.getMarkets();
		if (ruleCriterion==null) {
			ruleCriterion= new ArrayList<RoutingRuleCriteria>();
			rule.setRoutingRuleCriteria(ruleCriterion);
		}

		if (customRefsAry != null) {
			for (String item : customRefsAry) {
				final String[] nameValue = item.split(DELIMITER);
				if (nameValue == null || nameValue.length != 2) {
					logger.warn("Ignoring badly formed criteria name/val pair " + item);
					continue;
				}			
				criterionNameVal.add(new String[] { nameValue[0], nameValue[1] });
			}
		}

		if (marketsZipsAry != null) {
			for (String item : marketsZipsAry) {
				if (item.startsWith("zip")) {
					criterionNameVal.add(new String[] { CRITERIA_NAME_ZIP, item.substring(3) });
				} else if (item.startsWith("market")){
					criterionNameVal.add(new String[] { CRITERIA_NAME_MARKET, item.substring(6) });
				}
			}
		}
		
		List<RoutingRuleCriteria> criteriaToRemove = new ArrayList<RoutingRuleCriteria>();
		// DELETE items in the rule that aren't in criterion
		for (Iterator<RoutingRuleCriteria> critterIter= ruleCriterion.iterator(); critterIter.hasNext(); ) {
			RoutingRuleCriteria critter= critterIter.next();
			String[] ruleCritter= new String[] { critter.getCriteriaName(), critter.getCriteriaValue() };
			
			if (containsCriteria (criterionNameVal, ruleCritter)) {
				joinedKeys.add(ruleCritter);
			} else {
				String val = critter.getCriteriaValue();
				String name = critter.getCriteriaName();
				criteriaToRemove.add(critter);
				if(critter.getCriteriaName()!=null && critter.getCriteriaName().equals(CRITERIA_NAME_MARKET)){
					for(LookupVO marketVO:marketVOs){
						if(marketVO.getType().equals(val)){
							val = marketVO.getDescr();
							break;
						}
					}
					comment.append("- " + critter.getCriteriaName() + "=" + val + "\n");
				}else{
					comment.append("- " + critter.getCriteriaName() + "=" + val + "\n");
				}
                critterIter.remove(); 
			}				
		}


		// CREATE items for user added criterion that are NOT in the ruleCriterion		
		for (String[] critterNameVal : criterionNameVal) {
			// if (!joinedKeys.contains(critterNameVal)) {
			if (!containsCriteria (joinedKeys, critterNameVal)) {
				
				final RoutingRuleCriteria ruleCriteria = new RoutingRuleCriteria();
				ruleCriteria.setRoutingRuleHdr(rule);
				ruleCriteria.setModifiedBy(rule.getModifiedBy());
				ruleCriteria.setCriteriaName(critterNameVal[0]);
				ruleCriteria.setCriteriaValue(critterNameVal[1]);
				String name = critterNameVal[0];
				String val = critterNameVal[1];
				if (critterNameVal[0].equals(CRITERIA_NAME_MARKET) || critterNameVal[0].equals(CRITERIA_NAME_ZIP)) {
					ruleCriteria.setRuleTypeId(new LookupRoutingRuleType(RoutingRuleType.ZIP_MARKET.getId()));
					if(name.equals(CRITERIA_NAME_MARKET)){
						for(LookupVO marketVO:marketVOs){
							if(marketVO.getType().equals(val)){
								val = marketVO.getDescr();
								break;
							}
						}
					}
				} else {
					ruleCriteria.setRuleTypeId(new LookupRoutingRuleType(RoutingRuleType.CUSTOM_REF.getId()));
				}				
				ruleCriterion.add(ruleCriteria);
                comment.append("+ " + name + "=" + val + "\n");
			}
		}
		return criteriaToRemove;
	}
	
	/**
	 * @param rule
	 * @param customRefsAry
	 * @param marketsZipsAry
	 * @return List of criteria to be removed from the existing rule  
	 */
	private List<RoutingRulePrice> reconcileJobCodeWithRule(
			RoutingRuleHdr rule, StringBuffer comment,
			List<JobCodeVO> jobCodeList) {
		List<RoutingRulePrice> rulePriceList = rule.getRoutingRulePrice();
		List<RoutingRulePrice> joinedKeys = new ArrayList<RoutingRulePrice>();
		List<JobCodeVO> newJobcodeVal = new ArrayList<JobCodeVO>();

		if (rulePriceList == null) {
			rulePriceList = new ArrayList<RoutingRulePrice>();
			rule.setRoutingRulePrice(rulePriceList);
		}

		List<RoutingRulePrice> jobCodeToRemove = new ArrayList<RoutingRulePrice>();
		// DELETE items in the rule that aren't in criterion
		for (Iterator<RoutingRulePrice> jobCodeIter = rulePriceList.iterator(); jobCodeIter
				.hasNext();) {
			RoutingRulePrice jobCode = jobCodeIter.next();
			if (containsJobCodePrice(jobCodeList, jobCode)) {
				joinedKeys.add(jobCode);
			} else {
				jobCodeToRemove.add(jobCode);
				comment.append("- SKU " + jobCode.getJobcode() + "\n");
				jobCodeIter.remove();
			}
		}

		// CREATE items for user added criterion that are NOT in the
		// ruleCriterion
		if(jobCodeList!=null){
			for (JobCodeVO jobCode : jobCodeList) {
				Double newPrice = jobCode.getPrice();
				if (containsJobCode(joinedKeys, jobCode)) {
	
					for (RoutingRulePrice rulePrice : rulePriceList) {
						if (jobCode.getJobCode() != null
								&& jobCode.getJobCode().equals(
										rulePrice.getJobcode())) {
							// Custom ref value changed
							BigDecimal oldPrice = rulePrice.getPrice();
	
							double priceOld = 0.0;
							double priceNew = 0.0;
							if (oldPrice != null) {
								priceOld = oldPrice.doubleValue();
							}
							if (newPrice != null) {
								priceNew = newPrice.doubleValue();
							}
							if (priceOld != priceNew) {
								comment.append("/SKU " + jobCode.getJobCode()
										+ ": "
										+ (oldPrice == null ? "NULL" : oldPrice)
										+ " -> $"
										+ (newPrice == null ? "NULL" : newPrice)
										+ "\n");
								rulePrice.setModifiedBy(rule.getModifiedBy());
								if (null == newPrice) {
									rulePrice.setPrice(null);
								} else {
									BigDecimal price = BigDecimal.valueOf(newPrice
											.doubleValue());
									rulePrice.setPrice(price);
								}
							}else if(oldPrice==null && newPrice!=null && priceNew==0.0){
								comment.append("/SKU " + jobCode.getJobCode()
										+ ": "
										+ (oldPrice == null ? "NULL" : oldPrice)
										+ " -> $"
										+ (newPrice == null ? "NULL" : newPrice)
										+ "\n");
								rulePrice.setModifiedBy(rule.getModifiedBy());
								if (null == newPrice) {
									rulePrice.setPrice(null);
								} else {
									BigDecimal price = BigDecimal.valueOf(newPrice
											.doubleValue());
									rulePrice.setPrice(price);
								}
							}
						}
					}
				} else {
					RoutingRulePrice rulePrice = new RoutingRulePrice();
					rulePrice.setJobcode(jobCode.getJobCode());
					rulePrice.setModifiedBy(rule.getModifiedBy());
					rulePrice.setRoutingRuleHdr(rule);
					if (null == newPrice) {
						rulePrice.setPrice(null);
					} else {
						BigDecimal price = BigDecimal.valueOf(newPrice
								.doubleValue());
						rulePrice.setPrice(price);
					}
					rulePriceList.add(rulePrice);
					comment.append("+ SKU " + jobCode.getJobCode() + " $"
							+ (newPrice == null ? "NULL" : newPrice) + "\n");
				}
	
			}
		}
		return jobCodeToRemove;
	}
	
	/**
	 * @return true if market/zip name/val match, or if a custom ref name
	 *         matches
	 */
	private boolean containsCriteria (List<String[]>criterionNameVal, String[] ruleCritter) {
        if (ruleCritter!=null && criterionNameVal!=null) {
    		for (String[] elem : criterionNameVal) {
    			if (elem[0].equals(ruleCritter[0])) {
    				if (elem[1].equals(ruleCritter[1])) 
    				{
    					return true;
    				}
    			}
    		}
        }
		return false;
	}
	
	/** @return true if market/zip name/val match, or if a custom ref name matches */
	private boolean containsJobCodePrice (List<JobCodeVO> jobList, RoutingRulePrice jobCodeVO) {
		String jobCode = "";
		jobCode = jobCodeVO.getJobcode();
		if (jobCodeVO!=null && jobList!=null) {
    		for (JobCodeVO elem : jobList) {
    			if (elem.getJobCode()!=null && elem.getJobCode().equals(jobCode)) {
    				
    					return true;
    				
    			}
    		}
        }
		return false;
	}
	/** @return true if market/zip name/val match, or if a custom ref name matches */
	private boolean containsJobCode (List<RoutingRulePrice> jobList, JobCodeVO jobCodeVO) {
		String jobCode = "";
		jobCode = jobCodeVO.getJobCode();
		if (jobCodeVO!=null && jobList!=null) {
    		for (RoutingRulePrice elem : jobList) {
    			if (elem.getJobcode()!=null && elem.getJobcode().equals(jobCode)) {
    				
    					return true;
    				
    			}
    		}
        }
		return false;
	}
	
	
	private boolean containsSpecialty (String specialty, String[] specsJobs) {
		if (specialty!=null && specsJobs!=null) {
			for (String specJob : specsJobs) {
				String[] specJobAry= getSpecialtyNameJob(specJob);
				if (specJobAry!=null && specialty.equals(specJobAry[0])) {
					return true;
				}
			}
		}
		return false;		
	}

	private boolean containsSpecAndJobcode (String[] specsJobs, String specialty, String jobcode) {
		if (specialty!=null && specsJobs!=null && jobcode!=null) {
			for (String specJob : specsJobs) {
				String[] specJobAry= getSpecialtyNameJob(specJob);
				if (specJobAry!=null && specialty.equals(specJobAry[0]) && jobcode.equals(specJobAry[1])) {
					return true;
				}
			}
		}
		return false;		
	}

	private String[] getSpecialtyNameJob (String specJob) {
		String[] arySpecJob = specJob.split(DELIMITER);

		if (arySpecJob == null || arySpecJob.length < 2) {
			logger.error("Ignoring badly formed specialty " + specJob);
		} else {
			return (arySpecJob);
		}	
		return null;
	}

	private void validate(String[] chosenProviderFirmIds, List<String> customRefs, List<String> marketsZips, List<JobCodeVO> jobCodes) throws Exception {
		if (chosenProviderFirmIds == null || chosenProviderFirmIds.length == 0) {
			throw new Exception("chosenProviderFirmIds cannot be null.");
		}

		if ((customRefs == null || customRefs.size() == 0) && (marketsZips == null || marketsZips.size() == 0)
				&& (jobCodes == null || jobCodes.size() == 0)) {
			throw new Exception("must have at least one of customRef, marketsZip, or specsJobs filled in");
		}
	}

	/**
	 * @param routingRuleBuyerAssocDao the routingRuleBuyerAssocDao to set
	 */
	public void setRoutingRuleBuyerAssocDao(RoutingRuleBuyerAssocDao routingRuleBuyerAssocDao) {
		this.routingRuleBuyerAssocDao = routingRuleBuyerAssocDao;
	}

	/**
	 * @param providerFirmDao the providerFirmDao to set
	 */
	public void setProviderFirmDao(ProviderFirmDao providerFirmDao) {
		this.providerFirmDao = providerFirmDao;
	}

	/**
	 * @param routingRuleHdrDao the routingRuleHdrDao to set
	 */
	public void setRoutingRuleHdrDao(RoutingRuleHdrDao routingRuleHdrDao) {
		this.routingRuleHdrDao = routingRuleHdrDao;
	}

	/**
	 * @param lookupZipGeocodeDao the lookupZipGeocodeDao to set
	 */
	public void setLookupZipGeocodeDao(LookupZipGeocodeDao lookupZipGeocodeDao) {
		this.lookupZipGeocodeDao = lookupZipGeocodeDao;
	}

	/**
	 * @param lookupMarketDao the lookupMarketDao to set
	 */
	public void setLookupMarketDao(LookupMarketDao lookupMarketDao) {
		this.lookupMarketDao = lookupMarketDao;
	}

	/**
	 * @param lookupStatesDao the lookupStatesDao to set
	 */
	public void setLookupStatesDao(LookupStatesDao lookupStatesDao) {
		this.lookupStatesDao = lookupStatesDao;
	}

	/**
	 * @param buyerSkuTaskAssocDao the buyerSkuTaskAssocDao to set
	 */
	public void setBuyerSkuTaskAssocDao(BuyerSkuTaskAssocDao buyerSkuTaskAssocDao) {
		this.buyerSkuTaskAssocDao = buyerSkuTaskAssocDao;
	}

	/**
	 * @param buyerReferenceTypeDao the buyerReferenceTypeDao to set
	 */
	public void setBuyerReferenceTypeDao(BuyerReferenceTypeDao buyerReferenceTypeDao) {
		this.buyerReferenceTypeDao = buyerReferenceTypeDao;
	}

	/**
	 * @param lookupRoutingRuleTypeDao the routingRuleTypeDao to set
	 */
	public void setLookupRoutingRuleTypeDao(LookupRoutingRuleTypeDao lookupRoutingRuleTypeDao) {
		this.lookupRoutingRuleTypeDao = lookupRoutingRuleTypeDao;
	}

	/**
	 * @param contactDao the contactDao to set
	 */
	public void setRoutingRuleContactDao(ContactDao contactDao) {
		this.routingRuleContactDao = contactDao;
	}

	/**
	 * @param routingRuleHdrHistDao the routingRuleHdrHistDao to set
	 */
	public void setRoutingRuleHdrHistDao(RoutingRuleHdrHistDao routingRuleHdrHistDao) {
		this.routingRuleHdrHistDao = routingRuleHdrHistDao;
	}

	/**
	 * @param routingRuleBuyerDao the buyerDao to set
	 */
	public void setRoutingRuleBuyerDao(BuyerDao routingRuleBuyerDao) {
		this.routingRuleBuyerDao = routingRuleBuyerDao;
	}

	/**
	 * @param routingRuleVendorDao the routingRuleVendorDao to set
	 */
	public void setRoutingRuleVendorDao(RoutingRuleVendorDao routingRuleVendorDao) {
		this.routingRuleVendorDao = routingRuleVendorDao;
	}

	public void setBuyerSkuDao(BuyerSkuDao buyerSkuDao) {
		this.buyerSkuDao = buyerSkuDao;
	}
	
	public void setRoutingRuleErrorDao(RoutingRuleErrorDao routingRuleErrorDao) {
		this.routingRuleErrorDao = routingRuleErrorDao;
	}
	

	public void setRoutingRuleUploadRuleDao(
			RoutingRuleUploadRuleDao routingRuleUploadRuleDao) {
		this.routingRuleUploadRuleDao = routingRuleUploadRuleDao;
	}

	/** {@inheritDoc} */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RoutingRuleHdr> getRoutingRulesHeaderAfterSearch(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator) {
		int searchCriteria = routingRulesSearchVo.getSearchColumn().intValue();
		List<RoutingRuleHdr> result = null;
		switch (searchCriteria) {
		case RoutingRulesConstants.SEARCH_RULE_BY_FIRM_ID:
			result = routingRuleBuyerAssocDao.getRoutingRulesHeaderForFirmId(
					routingRulesSearchVo, pagingCriteria, archiveIndicator);
			break;
		case RoutingRulesConstants.SEARCH_RULE_BY_FIRM_NAME:
			result = routingRuleBuyerAssocDao.getRoutingRulesHeaderForFirmName(
					routingRulesSearchVo, pagingCriteria, archiveIndicator);
			break;
		case RoutingRulesConstants.SEARCH_RULE_BY_RULE_NAME:
			result = routingRuleBuyerAssocDao.getRoutingRulesHeaderForRuleName(
					routingRulesSearchVo, pagingCriteria, archiveIndicator);
			break;
		case RoutingRulesConstants.SEARCH_RULE_BY_UPLOADED_FILE_NAME:
			result = routingRuleBuyerAssocDao
					.getRoutingRulesHeaderForUploadFileName(
							routingRulesSearchVo, pagingCriteria,
							archiveIndicator);
			break;
		case RoutingRulesConstants.SEARCH_RULE_BY_RULE_ID:
			result = routingRuleBuyerAssocDao.getRoutingRulesHeaderForRuleId(
					routingRulesSearchVo, pagingCriteria, archiveIndicator);
			break;
		case RoutingRulesConstants.SEARCH_RULE_BY_PROCESS_ID:
			result = routingRuleBuyerAssocDao
					.getRoutingRulesHeaderForProcessId(routingRulesSearchVo,
							pagingCriteria, archiveIndicator);
			break;
		//SL 15642 Search by auto acceptance	
		case RoutingRulesConstants.SEARCH_RULE_BY_AUTO_ACCEPTANCE_STATUS:
			result = routingRuleBuyerAssocDao
					.getRoutingRulesHeaderForAutoAcceptanceStatus(routingRulesSearchVo,
			
							pagingCriteria, archiveIndicator);
			break;
		default:
			break;

		}
		return result;
	}
	
	
	/* to display quick view for each routing rule */
	public RoutingRuleQuickViewVO setQuickViewforRule(RoutingRuleHdr rule,List<LabelValueBean> markets) {
		
		RoutingRuleQuickViewVO quickViewVO = new RoutingRuleQuickViewVO();
		if (rule != null) {

			quickViewVO.setRuleName(rule.getRuleName());
			if(rule.getRuleStatus().equals("ACTIVE"))
			{
			quickViewVO.setRuleStatus("Active");
			}
			if(rule.getRuleStatus().equals("INACTIVE"))
			{
			quickViewVO.setRuleStatus("Inactive");
			}
			if(rule.getRuleStatus().equals("ARCHIVED"))
			{
				quickViewVO.setRuleStatus("Archived");
			}
			
			StringBuilder providerFirm = new StringBuilder();
			StringBuilder zipCode = new StringBuilder();
			StringBuilder market  = new StringBuilder();
			StringBuilder jobCodes = new StringBuilder();
			StringBuilder pickUplocationCodes = new StringBuilder();
			StringBuilder contact = new StringBuilder();
			String custReferenceValue="";
			Map<String,String> customReference=new HashMap<String,String>();
			
			List<RoutingRuleVendor> routingRuleVendor = rule
					.getRoutingRuleVendor();

			if (routingRuleVendor != null) {
				for (RoutingRuleVendor rrVendor : routingRuleVendor) {

					
					providerFirm = providerFirm.append(rrVendor.getVendor().getBusinessName())
							.append(", ")
							.append(rrVendor.getVendor().getId())
							.append(", ")
							.append(rrVendor.getVendor()
									.getServiceLiveWorkFlowState()
									.getWfState())
									.append(", ");
							

				}
			}

			List<RoutingRuleCriteria> routingRuleCriteria = rule
					.getRoutingRuleCriteria();

			if (routingRuleCriteria != null) {
				for (RoutingRuleCriteria rrCriteria : routingRuleCriteria) {
					if (rrCriteria.getCriteriaName().equals("ZIP")) {
						zipCode = zipCode.append(rrCriteria.getCriteriaValue()).append
								 (", ");
					} else if (rrCriteria.getCriteriaName().equals(
							"PICKUP LOCATION CODE")) {
						pickUplocationCodes = pickUplocationCodes.append(
								 rrCriteria.getCriteriaValue()) .append
								 (", ");
					}
					else if (rrCriteria.getCriteriaName().equals(
					"MARKET")) {
						for (LabelValueBean marketValue : markets) {
							if (marketValue.getValue().equals(rrCriteria.getCriteriaValue())) {
								 
								market = market.append(
										new StringBuilder(marketValue.getLabel())) .append
										 (", ");
								break;
							}
						}
				
			}else {
						
					if(customReference.containsKey(rrCriteria.getCriteriaName()))
					{
						custReferenceValue=customReference.get(rrCriteria.getCriteriaName());
						custReferenceValue=custReferenceValue+", "+rrCriteria.getCriteriaValue();
						
						customReference.put(rrCriteria.getCriteriaName(), custReferenceValue);
					}
					else
					{
						customReference.put(rrCriteria.getCriteriaName(), rrCriteria.getCriteriaValue());
						
					}
					

					}
				}
			}

			List<RoutingRulePrice> routingRulePrice = rule
					.getRoutingRulePrice();

			if (routingRulePrice != null) {
				for (RoutingRulePrice rrPrice : routingRulePrice) {
					jobCodes = jobCodes .append(rrPrice.getJobcode());
					if(rrPrice.getPrice()!=null){
						jobCodes = jobCodes .append( " ($")
						.append( rrPrice.getPrice()).append( ") ");
					}
					jobCodes = jobCodes .append(", ");
				}
			}

		
			
			if(zipCode.length()>1)
			{
				zipCode=new StringBuilder(zipCode.substring(0, zipCode.length()-2));
			}
			if(jobCodes.length()>1)
			{
				jobCodes=new StringBuilder(jobCodes.substring(0,jobCodes.length()-2));
			}
			if(pickUplocationCodes.length()>1)
			{
				pickUplocationCodes=new StringBuilder(pickUplocationCodes.substring(0,pickUplocationCodes.length()-2));	
			}
			if(providerFirm.length()>1)
			{
				providerFirm=new StringBuilder(providerFirm.substring(0,providerFirm.length()-2));
			}
			if(market.length()>1)
			{
				market=new StringBuilder(market.substring(0,market.length()-2));
			}
			
			quickViewVO.setContact(contact.toString());
			quickViewVO.setJobCodes(jobCodes.toString());
			quickViewVO.setProviderFirm(providerFirm.toString());
			quickViewVO.setZipCode(zipCode.toString());
			quickViewVO.setJobCodes(jobCodes.toString());
			quickViewVO.setPickUplocationCodes(pickUplocationCodes.toString());
			quickViewVO.setCustomReference(customReference);
			quickViewVO.setMarket(market.toString());
			quickViewVO.setLastName(rule.getContact().getLastName());
			quickViewVO.setFirstName(rule.getContact().getFirstName());
			quickViewVO.setEmail(rule.getContact().getEmail());
		}

		return quickViewVO;
	}
	
	
	/**
	 *  Return all ruleError for the rule 
	 * @param ruleId
	 * @return List<RoutingRuleErrorVO>
	 */
	public List<RoutingRuleErrorVO> getRoutingRuleErrors(Integer ruleId) {
		Map<Integer, Map<String, RoutingRuleErrorVO>> errorMap = new HashMap<Integer, Map<String, RoutingRuleErrorVO>>();
		Map<Integer, Map<Integer, RoutingRuleErrorVO>> conflictMap = new HashMap<Integer, Map<Integer, RoutingRuleErrorVO>>();
		Map<Integer, String> ruleCauseMap = new HashMap<Integer, String>();
		List<RoutingRuleErrorCause> errorCauseList = routingRuleUploadRuleDao
				.getErrorCause();
		for (RoutingRuleErrorCause errorCause : errorCauseList) {
			ruleCauseMap.put(errorCause.getRoutingRuleErrorCauseId(),
					errorCause.getErrorCauseDesc());
		}
		List<RoutingRuleError> routingRuleErrorList = routingRuleHdrDao
				.getRoutingRuleErrorForRuleId(ruleId);
		for (RoutingRuleError routingRuleError : routingRuleErrorList) {
			if (routingRuleError.getRoutingRuleUploadRuleId() == null) {
				routingRuleError.setRoutingRuleUploadRuleId(0);
			}
			if (routingRuleError.getConflictInd() == false) {
				if (errorMap.containsKey(routingRuleError
						.getRoutingRuleUploadRuleId())) {
					Map<String, RoutingRuleErrorVO> errorRuleMap = errorMap
							.get(routingRuleError.getRoutingRuleUploadRuleId());
					if (errorRuleMap.containsKey(routingRuleError
							.getErrorType())) {
						RoutingRuleErrorVO ruleErrorVo = errorRuleMap
								.get(routingRuleError.getErrorType());
						RuleErrorVO errorVo = new RuleErrorVO();
						errorVo.setErrorCause(routingRuleError
								.getErrorCauseId().toString());
						errorVo
								.setErrorDescription(routingRuleError
										.getError());
						List<RuleErrorVO> ruleErrorListVo = ruleErrorVo
								.getRuleErrorVo();
						ruleErrorListVo.add(errorVo);
						ruleErrorVo.setRuleErrorVo(ruleErrorListVo);
						ruleErrorVo.setErrorMessage(null);
						ruleErrorVo.setErrorType(routingRuleError
								.getErrorType());
						ruleErrorVo.setConflictInd(null);
						ruleErrorVo.setConflictRuleName(null);
						ruleErrorVo.setUploadFileHdrId(routingRuleError
								.getRoutingRuleUploadRuleId());
						errorRuleMap.put(routingRuleError.getErrorType(),
								ruleErrorVo);
					} else {
						RoutingRuleErrorVO ruleErrorVo = new RoutingRuleErrorVO();
						RuleErrorVO errorVo = new RuleErrorVO();
						errorVo.setErrorCause(routingRuleError
								.getErrorCauseId().toString());
						errorVo
								.setErrorDescription(routingRuleError
										.getError());
						List<RuleErrorVO> ruleErrorListVo = new ArrayList<RuleErrorVO>();
						ruleErrorListVo.add(errorVo);
						ruleErrorVo.setRuleErrorVo(ruleErrorListVo);
						ruleErrorVo.setErrorMessage(null);
						ruleErrorVo.setErrorType(routingRuleError
								.getErrorType());
						ruleErrorVo.setConflictInd(null);
						ruleErrorVo.setConflictRuleName(null);
						ruleErrorVo.setUploadFileHdrId(routingRuleError
								.getRoutingRuleUploadRuleId());
						errorRuleMap.put(routingRuleError.getErrorType(),
								ruleErrorVo);
					}
					errorMap.put(routingRuleError.getRoutingRuleUploadRuleId(),
							errorRuleMap);
				} else {
					Map<String, RoutingRuleErrorVO> errorRuleMap = new HashMap<String, RoutingRuleErrorVO>();
					RuleErrorVO errorVo = new RuleErrorVO();
					errorVo.setErrorCause(routingRuleError.getErrorCauseId()
							.toString());
					errorVo.setErrorDescription(routingRuleError.getError());
					List<RuleErrorVO> ruleErrorListVo = new ArrayList<RuleErrorVO>();
					ruleErrorListVo.add(errorVo);
					RoutingRuleErrorVO ruleErrorVo = new RoutingRuleErrorVO();
					ruleErrorVo.setRuleErrorVo(ruleErrorListVo);
					ruleErrorVo.setErrorMessage(null);
					ruleErrorVo.setErrorType(routingRuleError.getErrorType());
					ruleErrorVo.setConflictInd(null);
					ruleErrorVo.setUploadFileHdrId(routingRuleError
							.getRoutingRuleUploadRuleId());
					errorRuleMap.put(routingRuleError.getErrorType(),
							ruleErrorVo);
					errorMap.put(routingRuleError.getRoutingRuleUploadRuleId(),
							errorRuleMap);
				}
			} else {
				if (conflictMap.containsKey(routingRuleError
						.getRoutingRuleUploadRuleId())) {
					Map<Integer, RoutingRuleErrorVO> conflictRuleMap = conflictMap
							.get(routingRuleError.getRoutingRuleUploadRuleId());
					if (conflictRuleMap.containsKey(routingRuleError
							.getConflictingRuleId())) {
						RoutingRuleErrorVO ruleErrorVo = conflictRuleMap
								.get(routingRuleError.getConflictingRuleId());
						RuleErrorVO errorVo = new RuleErrorVO();
						errorVo.setErrorCause(routingRuleError
								.getErrorCauseId().toString());
						errorVo
								.setErrorDescription(routingRuleError
										.getError());
						List<RuleErrorVO> ruleErrorListVo = ruleErrorVo
								.getRuleErrorVo();
						ruleErrorListVo.add(errorVo);
						ruleErrorVo.setRuleErrorVo(ruleErrorListVo);
						ruleErrorVo.setErrorMessage(null);
						ruleErrorVo.setErrorType(routingRuleError
								.getErrorType());
						ruleErrorVo.setConflictInd("true");
						ruleErrorVo.setConflictRuleName(null);
						ruleErrorVo.setUploadFileHdrId(routingRuleError
								.getRoutingRuleUploadRuleId());
						conflictRuleMap.put(routingRuleError
								.getConflictingRuleId(), ruleErrorVo);
					} else {
						RoutingRuleErrorVO ruleErrorVo = new RoutingRuleErrorVO();
						RuleErrorVO errorVo = new RuleErrorVO();
						errorVo.setErrorCause(routingRuleError
								.getErrorCauseId().toString());
						errorVo
								.setErrorDescription(routingRuleError
										.getError());
						List<RuleErrorVO> ruleErrorListVo = new ArrayList<RuleErrorVO>();
						ruleErrorListVo.add(errorVo);
						ruleErrorVo.setRuleErrorVo(ruleErrorListVo);
						ruleErrorVo.setErrorMessage(null);
						ruleErrorVo.setErrorType(routingRuleError
								.getErrorType());
						ruleErrorVo.setConflictInd("true");
						ruleErrorVo.setUploadFileHdrId(routingRuleError
								.getRoutingRuleUploadRuleId());
						conflictRuleMap.put(routingRuleError
								.getConflictingRuleId(), ruleErrorVo);
					}
					conflictMap.put(routingRuleError
							.getRoutingRuleUploadRuleId(), conflictRuleMap);
				} else {
					Map<Integer, RoutingRuleErrorVO> conflictRuleMap = new HashMap<Integer, RoutingRuleErrorVO>();
					RuleErrorVO errorVo = new RuleErrorVO();
					errorVo.setErrorCause(routingRuleError.getErrorCauseId()
							.toString());
					errorVo.setErrorDescription(routingRuleError.getError());
					List<RuleErrorVO> ruleErrorListVo = new ArrayList<RuleErrorVO>();
					ruleErrorListVo.add(errorVo);
					RoutingRuleErrorVO ruleErrorVo = new RoutingRuleErrorVO();
					ruleErrorVo.setRuleErrorVo(ruleErrorListVo);
					ruleErrorVo.setErrorMessage(null);
					ruleErrorVo.setErrorType(routingRuleError.getErrorType());
					ruleErrorVo.setConflictInd("true");
					ruleErrorVo.setUploadFileHdrId(routingRuleError
							.getRoutingRuleUploadRuleId());
					conflictRuleMap.put(
							routingRuleError.getConflictingRuleId(),
							ruleErrorVo);
					conflictMap.put(routingRuleError
							.getRoutingRuleUploadRuleId(), conflictRuleMap);
				}
			}
		}
		List<RoutingRuleErrorVO> ruleErrorList = new ArrayList<RoutingRuleErrorVO>();
		List<RoutingRuleErrorVO> rulePersistentErrorList = new ArrayList<RoutingRuleErrorVO>();
		List<RoutingRuleErrorVO> ruleNonPersistentErrorList = new ArrayList<RoutingRuleErrorVO>();
		Set<Integer> errorKeys = errorMap.keySet();
		Map<Integer, RoutingRuleUploadDetailsVO> uploadRuleErrorMap = new HashMap<Integer, RoutingRuleUploadDetailsVO>();
		List<Integer> uploadRuleIds = new ArrayList<Integer>();
		for (Iterator i = errorKeys.iterator(); i.hasNext();) {
			Integer key = (Integer) i.next();
			uploadRuleIds.add(key);
		}
		List<RoutingRuleUploadRule> uploadRules = new ArrayList<RoutingRuleUploadRule>();
		if (uploadRuleIds != null && uploadRuleIds.size() > 0) {
			uploadRules = routingRuleUploadRuleDao
					.getUploadRules(uploadRuleIds);
		}

		for (RoutingRuleUploadRule uploadRule : uploadRules) {
			if (uploadRule != null) {
				RoutingRuleUploadDetailsVO uploadDetailsVo = new RoutingRuleUploadDetailsVO();
				if (null != uploadRule.getRoutingRuleFileHdr()) {
					uploadDetailsVo.setUploadAction(uploadRule.getAction());
					uploadDetailsVo.setFileName(uploadRule
							.getRoutingRuleFileHdr().getRoutingRuleFileName());
					uploadRuleErrorMap.put(uploadRule
							.getRoutingRuleUploadRuleId(), uploadDetailsVo);
				}
			}
		}
		for (Iterator i = errorKeys.iterator(); i.hasNext();) {
			Integer key = (Integer) i.next();
			Map<String, RoutingRuleErrorVO> routingRuleMap = errorMap.get(key);
			RoutingRuleErrorVO rulePersistentErrorVO = routingRuleMap
					.get("Persistent");
			RoutingRuleErrorVO ruleNonPersistentErrorVO = routingRuleMap
					.get("Non-Persistent");
			if (rulePersistentErrorVO != null) {
				rulePersistentErrorVO.setUploadFileHdrId(key);
				rulePersistentErrorList.add(rulePersistentErrorVO);
			}
			if (ruleNonPersistentErrorVO != null) {
				ruleNonPersistentErrorVO.setUploadFileHdrId(key);
				ruleNonPersistentErrorList.add(ruleNonPersistentErrorVO);
			}
		}
		List<RoutingRuleErrorVO> rulePersistentConflictList = new ArrayList<RoutingRuleErrorVO>();
		List<RoutingRuleErrorVO> ruleNonPersistenConflictList = new ArrayList<RoutingRuleErrorVO>();
		Set<Integer> conflictKeys = conflictMap.keySet();
		Map<Integer, RoutingRuleUploadDetailsVO> uploadRuleConflictMap = new HashMap<Integer, RoutingRuleUploadDetailsVO>();
		List<Integer> uploadRuleIdList = new ArrayList<Integer>();
		for (Iterator i = conflictKeys.iterator(); i.hasNext();) {
			Integer key = (Integer) i.next();
			uploadRuleIdList.add(key);
		}
		List<RoutingRuleUploadRule> uploadRuleList = new ArrayList<RoutingRuleUploadRule>();
		if (uploadRuleIdList != null && uploadRuleIdList.size() > 0) {
			uploadRuleList = routingRuleUploadRuleDao
					.getUploadRules(uploadRuleIdList);
		}

		for (RoutingRuleUploadRule uploadRule : uploadRuleList) {
			if (uploadRule != null) {
				RoutingRuleUploadDetailsVO uploadDetailsVo = new RoutingRuleUploadDetailsVO();
				if (null != uploadRule.getRoutingRuleFileHdr()) {
					uploadDetailsVo.setUploadAction(uploadRule.getAction());
					uploadDetailsVo.setFileName(uploadRule
							.getRoutingRuleFileHdr().getRoutingRuleFileName());
					uploadRuleConflictMap.put(uploadRule
							.getRoutingRuleUploadRuleId(), uploadDetailsVo);
				}
			}
		}
		for (Iterator i = conflictKeys.iterator(); i.hasNext();) {
			Integer key = (Integer) i.next();
			Map<Integer, RoutingRuleErrorVO> routingRuleMap = conflictMap
					.get(key);
			Set<Integer> conflictRuleIdKeys = routingRuleMap.keySet();
			for (Iterator j = conflictRuleIdKeys.iterator(); j.hasNext();) {
				Integer conflictRuleId = (Integer) j.next();
				RoutingRuleErrorVO ruleConflictVo = routingRuleMap
						.get(conflictRuleId);
				RoutingRuleHdr rule = routingRuleHdrDao
						.findByRoutingRuleHdrId(conflictRuleId);
				ruleConflictVo.setRoutingRuleHdrId(conflictRuleId);
				ruleConflictVo.setConflictRuleName(rule.getRuleName());
				ruleConflictVo.setUploadFileHdrId(key);
				if (ruleConflictVo.getErrorType().equals("Non-Persistent")) {
					ruleNonPersistenConflictList.add(ruleConflictVo);
				}
				if (ruleConflictVo.getErrorType().equals("Persistent")) {
					rulePersistentConflictList.add(ruleConflictVo);
				}
			}
		}
		for (RoutingRuleErrorVO nonPersistentError : ruleNonPersistentErrorList) {
			List<RuleErrorVO> ruleErrorVoList = nonPersistentError
					.getRuleErrorVo();
			for (RuleErrorVO ruleError : ruleErrorVoList) {
				ruleError.setErrorCause(ruleCauseMap.get(Integer
						.parseInt(ruleError.getErrorCause())));
			}
			nonPersistentError.setRuleErrorVo(ruleErrorVoList);

			RoutingRuleUploadDetailsVO uploadDetailsVo = uploadRuleErrorMap
					.get(nonPersistentError.getUploadFileHdrId());
			if (uploadDetailsVo != null) {
				StringBuilder fileName = new StringBuilder(uploadDetailsVo
						.getFileName());
				StringBuilder action = new StringBuilder(uploadDetailsVo
						.getUploadAction());
				StringBuilder errorMessage = new StringBuilder();
				if (action.toString().equalsIgnoreCase(
						RoutingRulesConstants.UPLOAD_ACTION_NEW)) {
					errorMessage = errorMessage
							.append(
									RoutingRulesConstants.UPLOAD_NEW_NONPERSISTENT_MESSAGE)
							.append(fileName);
				} else if (action.toString().equalsIgnoreCase(
						RoutingRulesConstants.UPLOAD_ACTION_INACTIVE)) {
					errorMessage = errorMessage
							.append(
									RoutingRulesConstants.UPLOAD_INACTIVE_NONPERSISTENT_MESSAGE)
							.append(fileName);
				} else if (action.toString().equalsIgnoreCase(
						RoutingRulesConstants.UPLOAD_ACTION_ADD)) {
					errorMessage = errorMessage
							.append(
									RoutingRulesConstants.UPLOAD_ADD_NONPERSISTENT_MESSAGE)
							.append(fileName);
				} else if (action.toString().equalsIgnoreCase(
						RoutingRulesConstants.UPLOAD_ACTION_DELETE)) {
					errorMessage = errorMessage
							.append(
									RoutingRulesConstants.UPLOAD_DELETE_NONPERSISTENT_MESSAGE)
							.append(fileName);
				} else if (action.toString().equalsIgnoreCase(
						RoutingRulesConstants.UPLOAD_ACTION_ARCHIVE)) {
					errorMessage = errorMessage
							.append(
									RoutingRulesConstants.UPLOAD_ARCHIVE_NONPERSISTENT_MESSAGE)
							.append(fileName);
				} else if (action.toString().equalsIgnoreCase(
						RoutingRulesConstants.UPLOAD_ACTION_ACTIVE)) {
					errorMessage = errorMessage
							.append(
									RoutingRulesConstants.UPLOAD_ACTIVE_NONPERSISTENT_MESSAGE)
							.append(fileName);
				}
				nonPersistentError.setErrorMessage(errorMessage.toString());
			}
		}
		int uploadId = 0;
		for (RoutingRuleErrorVO nonPersistenConflict : ruleNonPersistenConflictList) {
			List<RuleErrorVO> ruleErrorVoList = nonPersistenConflict
					.getRuleErrorVo();
			for (RuleErrorVO ruleError : ruleErrorVoList) {
				ruleError.setErrorCause(ruleCauseMap.get(Integer
						.parseInt(ruleError.getErrorCause())));
			}
			nonPersistenConflict.setRuleErrorVo(ruleErrorVoList);
			if (uploadId != nonPersistenConflict.getUploadFileHdrId()
					.intValue()) {
				RoutingRuleUploadDetailsVO uploadDetailsVo = uploadRuleConflictMap
						.get(nonPersistenConflict.getUploadFileHdrId());
				if (uploadDetailsVo != null) {
					StringBuilder fileName = new StringBuilder(uploadDetailsVo
							.getFileName());
					StringBuilder action = new StringBuilder(uploadDetailsVo
							.getUploadAction());
					StringBuilder errorMessage = new StringBuilder();
					if (RoutingRulesConstants.UPLOAD_ACTION_ADD
							.equalsIgnoreCase(action.toString())) {
						errorMessage = errorMessage
								.append(
										RoutingRulesConstants.UPLOAD_ADD_NONPERSISTENT_CONFLICT)
								.append(fileName)
								.append(
										RoutingRulesConstants.UPLOAD_ADD_NONPERSISTENT_CONFLICT_END);
					} else if (RoutingRulesConstants.UPLOAD_ACTION_DELETE
							.equalsIgnoreCase(action.toString())) {
						errorMessage = errorMessage
								.append(
										RoutingRulesConstants.UPLOAD_ADD_NONPERSISTENT_CONFLICT)
								.append(fileName)
								.append(
										RoutingRulesConstants.UPLOAD_ADD_NONPERSISTENT_CONFLICT_END);
					}
					nonPersistenConflict.setErrorMessage(errorMessage
							.toString());
				}
				uploadId = nonPersistenConflict.getUploadFileHdrId().intValue();
			}
		}
		for (RoutingRuleErrorVO persistentError : rulePersistentErrorList) {

			List<RuleErrorVO> ruleErrorVoList = persistentError
					.getRuleErrorVo();
			for (RuleErrorVO ruleError : ruleErrorVoList) {
				ruleError.setErrorCause(ruleCauseMap.get(Integer
						.parseInt(ruleError.getErrorCause())));
			}
			persistentError.setRuleErrorVo(ruleErrorVoList);
		}
		int uploadRuleId = 0;
		for (RoutingRuleErrorVO persistentConflict : rulePersistentConflictList) {
			List<RuleErrorVO> ruleErrorVoList = persistentConflict
					.getRuleErrorVo();
			for (RuleErrorVO ruleError : ruleErrorVoList) {
				ruleError.setErrorCause(ruleCauseMap.get(Integer
						.parseInt(ruleError.getErrorCause())));
			}
			persistentConflict.setRuleErrorVo(ruleErrorVoList);
			if (uploadRuleId != persistentConflict.getUploadFileHdrId()
					.intValue()) {
				RoutingRuleUploadDetailsVO uploadDetailsVo = uploadRuleConflictMap
						.get(persistentConflict.getUploadFileHdrId());
				if (uploadDetailsVo != null) {
					StringBuilder fileName = new StringBuilder(uploadDetailsVo
							.getFileName());
					StringBuilder action = new StringBuilder(uploadDetailsVo
							.getUploadAction());
					StringBuilder errorMessage = new StringBuilder();
					if (RoutingRulesConstants.UPLOAD_ACTION_ACTIVE
							.equalsIgnoreCase(action.toString())) {
						errorMessage = errorMessage
								.append(
										RoutingRulesConstants.UPLOAD_ACTIVE_PERSISTENT_CONFLICT)
								.append(fileName)
								.append(
										RoutingRulesConstants.UPLOAD_ACTIVE_PERSISTENT_END);
					}
					persistentConflict.setErrorMessage(errorMessage.toString());
				}
				uploadRuleId = persistentConflict.getUploadFileHdrId()
						.intValue();
			}
		}
		ruleErrorList.addAll(ruleNonPersistentErrorList);
		ruleErrorList.addAll(ruleNonPersistenConflictList);
		ruleErrorList.addAll(rulePersistentErrorList);
		ruleErrorList.addAll(rulePersistentConflictList);
		return ruleErrorList;
	}
	
	
	/**
	 * @param rule
	 * @param autoAcceptStatus
	 * @param newVendorId
	 * @param modifiedBy
	 * @param buyer
	 */
	public void updateAutoAcceptHistory(RoutingRuleHdr rule,String autoAcceptStatus,Integer newVendorId,String modifiedBy,Integer roleId,String action,Map<String, Object> map)
	{    
		SecurityContext securityContext = (SecurityContext) map.get("securityContextForRule");
		AutoAcceptHistory autoAcceptHistory=new AutoAcceptHistory();
		autoAcceptHistory.setRoutingRuleHdr(rule);
		autoAcceptHistory.setAutoAcceptStatus(autoAcceptStatus);
		autoAcceptHistory.setVendorId(newVendorId);
		autoAcceptHistory.setAction(action);
		autoAcceptHistory.setModifiedBy(modifiedBy);
		autoAcceptHistory.setRoledId(roleId);
				if(securityContext.getSLAdminInd()==true)
				{
					String slAdminFullName=securityContext.getSlAdminFName()+"  "+securityContext.getSlAdminFName();
					autoAcceptHistory.setAdoptedBy(slAdminFullName);
				}
				else
				{
					autoAcceptHistory.setAdoptedBy(null);
				}
				if(rule.getAutoAcceptHistory()==null)
				{
					 List<AutoAcceptHistory> autoAccpetHistoryList=new ArrayList<AutoAcceptHistory>();
					 autoAccpetHistoryList.add(autoAcceptHistory);
					 rule.setAutoAcceptHistory(autoAccpetHistoryList);
				}
				else
				{
					rule.getAutoAcceptHistory().add(autoAcceptHistory);
				}
		
	}
	/**
	 * To update rule history
	 * @param RoutingRuleHdr ruleHdr
	 * @param String action
	 * @param BuyerResource buyer
	 */
	public void updateRuleHistory(RoutingRuleHdr ruleHdr,
			String action, BuyerResource buyer) {
		
		RoutingRuleHdrHist ruleHist = new RoutingRuleHdrHist();
		ruleHist.setRoutingRuleHdr(ruleHdr);
		ruleHist.setRoutingRuleBuyerAssoc(ruleHdr.getRoutingRuleBuyerAssoc());
		ruleHist.setRuleName(ruleHdr.getRuleName());
		ruleHist.setRuleStatus(action);
		ruleHist.setRuleComment(ruleHdr.getRuleComment());
		String histAction;
		 if(action.equalsIgnoreCase(RoutingRulesConstants.UPLOAD_ACTION_ACTIVE)){
			histAction = RoutingRulesConstants.HISTORY_ACTIVATED;
		} else if(action.equalsIgnoreCase(RoutingRulesConstants.UPLOAD_ACTION_INACTIVE)){
			histAction = RoutingRulesConstants.HISTORY_DEACTIVATED;
		}else if(action.equalsIgnoreCase(RoutingRulesConstants.HISTORY_CREATED)){
			histAction = RoutingRulesConstants.HISTORY_CREATED;
		}else if(action.equalsIgnoreCase(RoutingRulesConstants.HISTORY_UPDATED)){
			histAction = RoutingRulesConstants.HISTORY_UPDATED;
		}else {
			histAction = RoutingRulesConstants.HISTORY_ARCHIVED;
		}
		ruleHist.setRuleAction(histAction);
		ruleHist.setModifiedBy(buyer);
		if(ruleHdr.getRoutingRuleHdrHist()==null){
			List<RoutingRuleHdrHist> historyList = new ArrayList<RoutingRuleHdrHist>();
			historyList.add(ruleHist);
			ruleHdr.setRoutingRuleHdrHist(historyList);
		}else{
			ruleHdr.getRoutingRuleHdrHist().add(ruleHist);
		}
	}
	
	/**
	 * To update rule history
	 * @param RoutingRuleHdr ruleHdr
	 * @param String action
	 * @param BuyerResource buyer
	 */
	public void updateRuleHistory(RoutingRuleHdr ruleHdr,String status,
			String action, BuyerResource buyer) {
		
		RoutingRuleHdrHist ruleHist = new RoutingRuleHdrHist();
		ruleHist.setRoutingRuleHdr(ruleHdr);
		ruleHist.setRoutingRuleBuyerAssoc(ruleHdr.getRoutingRuleBuyerAssoc());
		ruleHist.setRuleName(ruleHdr.getRuleName());
		ruleHist.setRuleStatus(status);
		ruleHist.setRuleComment(ruleHdr.getRuleComment());
		ruleHist.setRuleAction(action);
		ruleHist.setModifiedBy(buyer);
		if(ruleHdr.getRoutingRuleHdrHist()==null){
			List<RoutingRuleHdrHist> historyList = new ArrayList<RoutingRuleHdrHist>();
			historyList.add(ruleHist);
			ruleHdr.setRoutingRuleHdrHist(historyList);
		}else{
			ruleHdr.getRoutingRuleHdrHist().add(ruleHist);
		}
		
}
	/**
	 * @param userName
	 * @return
	 */
	public BuyerResource getBuyerResource(String userName)
	{
		BuyerResource buyer=routingRuleHdrHistDao.getBuyerResource(userName);
		return buyer;
	}
	
	/**
	 * Method to retrieve conflicts 
	 *@param ruleId
	 *@return List<RuleConflictDisplayVO>
	 */
		
	public List<RuleConflictDisplayVO> getRoutingRuleConflicts(Integer ruleId){
		List<RuleConflictDisplayVO> ruleConflictDisplayList = new ArrayList<RuleConflictDisplayVO>();
		List<RoutingRuleError> ruleErrorList = new ArrayList<RoutingRuleError>();
		ruleErrorList = routingRuleErrorDao.getRoutingRulesConflictsForRuleId(ruleId);
		Integer conflictingRule = 0;
		RuleConflictDisplayVO displayVO = new RuleConflictDisplayVO();
		for(RoutingRuleError error :ruleErrorList){
			if(!conflictingRule.equals(error.getConflictingRuleId())){
			displayVO = new RuleConflictDisplayVO();
			RoutingRuleHdr conflictingHdr = routingRuleHdrDao.findByRoutingRuleHdrId(error.getConflictingRuleId());
			displayVO.setRuleName(conflictingHdr.getRuleName());
			
			// Set the rule id to display on the conflict pop up
			displayVO.setRuleId(conflictingHdr.getRoutingRuleHdrId());
			ruleConflictDisplayList.add(displayVO);
			}
			if(error.getErrorCauseId().equals(RoutingRulesConstants.CONFLICTING_JOBCODES)){
				displayVO.setJobCodes(error.getError());
			}
			if(error.getErrorCauseId().equals(RoutingRulesConstants.CONFLICTING_ZIPCODES)){
				displayVO.setZipCodes(error.getError());
			}
			if(error.getErrorCauseId().equals(RoutingRulesConstants.CONFLICTING_PICKUPLOCATIONS)){
				displayVO.setPickupLocation(error.getError());
			}
			if(error.getErrorCauseId().equals(RoutingRulesConstants.CONFLICTING_MARKETS)){
				displayVO.setMarkets(error.getError());
			}
			conflictingRule = error.getConflictingRuleId();
		}
		return ruleConflictDisplayList;
	}
	
	public RoutingRuleFileHdrDao getRoutingRuleFileHdrDao() {
		return routingRuleFileHdrDao;
	}



	public void setRoutingRuleFileHdrDao(
			RoutingRuleFileHdrDao routingRuleFileHdrDao) {
		this.routingRuleFileHdrDao = routingRuleFileHdrDao;
	}


		/**
	 * Get the uploaded files for a user
	 * @param pagingCriteria
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleFileHdr> getRoutingRulesFileHeaderList(
			RoutingRulesPaginationVO pagingCriteria, Integer buyerId){
		List<RoutingRuleFileHdr> result = new ArrayList<RoutingRuleFileHdr>();
		try{
			result =  routingRuleFileHdrDao.
				getUploadedFilesForBuyer(pagingCriteria, buyerId);
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Check if the file exists in the database
	 * @param fileName
	 * @return boolean
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean checkIfFileExists(String fileName, Integer buyerId) {
		boolean fileExists = true;
		try{
			fileExists = routingRuleFileHdrDao.checkIfFileExists(fileName, buyerId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return fileExists;
	}
	/**
	 * Save the routing rule file details to the database
	 * @param routingRuleFileHdr
	 * @return
	 */
	public RoutingRuleFileHdr saveRoutingRuleFile(RoutingRuleFileHdr 
		routingRuleFileHdr){
		RoutingRuleFileHdr fileHdr = new RoutingRuleFileHdr();		
		try {
			 fileHdr = routingRuleFileHdrDao.save(routingRuleFileHdr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return fileHdr;
	}
	
	public void saveRoutingRuleHdr(RoutingRuleHdr 
			routingRuleHdr){
		
		try {
			routingRuleHdrDao.save(routingRuleHdr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//SL-15642 Method to save the email id  for buyer from manage rule dashboard
	public void saveManageRuleBuyerEmailId(Integer indEmailNotifyRequire,String manageRuleBuyerEmailId,Integer buyerId)
	{
		try {
			routingRuleHdrDao.saveManageRuleBuyerEmailId(indEmailNotifyRequire,manageRuleBuyerEmailId,buyerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//SL 15642 Method to fetch email Id for a particular buyer 
	public String fetchSavedEmailId(Integer buyerId)
	{
		String buyerEmailId=routingRuleHdrDao.fetchSavedEmailId(buyerId);	
		return buyerEmailId;
	}
	
	public void  deletePersistentErrors(Integer 
			routingRuleId){
		
		routingRuleErrorDao.deletePersistentErrors(routingRuleId);
	}
	
	/**
	 * Get the error cause
	 * @return  List<RoutingRuleErrorCause>
	 */
	public List<RoutingRuleErrorCause>  getErrorCause(){
		List<RoutingRuleErrorCause> errorCauseList = new ArrayList<RoutingRuleErrorCause>();
		try{
			errorCauseList = routingRuleUploadRuleDao.getErrorCause();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorCauseList;
	}
	
	//SL-15642 Method to auto_accept_status in the routing_rule_vendor table during Creation of CAR rule
	public void  saveRoutingRuleVendor(String autoAcceptStatCarRuleCreation,Integer ruleId)
	{
		try{
			routingRuleHdrDao.saveRoutingRuleVendor(autoAcceptStatCarRuleCreation,ruleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//SL 15642 Method to get history of a rule for a buyer
		public List<RoutingRuleAutoAcceptHistoryVO> getAutoAcceptHistoryBuyer(Integer ruleHdrId)
		{
			List<RoutingRuleAutoAcceptHistoryVO> history=null;
			try{
				history=routingRuleHdrDao.getAutoAcceptHistoryBuyer(ruleHdrId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return history;
		}
	
		//SL 15642 Method to get history of a rule for provider
		public List<RoutingRuleAutoAcceptHistoryVO> getAutoAcceptHistoryProvider(Integer ruleHdrId, Integer vendorId){
			List<RoutingRuleAutoAcceptHistoryVO> history = null;
			try{
				history = routingRuleHdrDao.getAutoAcceptHistoryProvider(ruleHdrId, vendorId);
			} catch (Exception e) {
				logger.debug("Exception occured in RoutingRulesServiceImpl.getAutoAcceptHistoryProvider() due to "+ e.getMessage());
			}
			return history;
		}
		
		//SL 15642 
		// return the template input value for alert_task
		public String createMailMergeValueStringFromMap(
				Map<String, String> templateMergeValueMap) {
			StringBuilder stringBuilder = new StringBuilder("");
			boolean isFirstKey = true;
			if (templateMergeValueMap != null) {
				Set<String> keySet = templateMergeValueMap.keySet();
				for (String key : keySet) {
					if (isFirstKey) {
						isFirstKey = !isFirstKey;
					} else {
						stringBuilder.append("|");
					}

					stringBuilder.append(key).append("=").append(
							templateMergeValueMap.get(key));
				}
			}
			return stringBuilder.toString();
		}
		
		//SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000
		public Integer forceFulActiveRule(Map inputMap) throws Exception{
			return routingRuleHdrDao.forceFulActiveRule(inputMap);
		}
}
