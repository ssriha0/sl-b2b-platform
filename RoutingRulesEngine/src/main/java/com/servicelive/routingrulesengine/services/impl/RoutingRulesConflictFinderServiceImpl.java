package com.servicelive.routingrulesengine.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.domain.routingrules.RoutingRuleCriteria;
import com.servicelive.domain.routingrules.RoutingRuleError;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRulePrice;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao;
import com.servicelive.routingrulesengine.services.RoutingRulesConflictFinderService;
import com.servicelive.routingrulesengine.vo.RoutingRuleCacheStatusVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesCacheVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesConflictVO;

/**
 * 
 * This class is used to check if a rule is in conflict with the existing active
 * rules
 * 
 */
public class RoutingRulesConflictFinderServiceImpl implements RoutingRulesConflictFinderService,InitializingBean {
	
	private static final Logger logger = Logger.getLogger(RoutingRulesConflictFinderServiceImpl.class);	
	private RoutingRuleHdrDao routingRuleHdrDao;
	private ILookupBO lookupBO;
	//private static RoutingRulesConflictFinderServiceImpl thisInstance;
	private static boolean isCacheLoaded = false;
	private static ConcurrentHashMap<Integer,HashMap<Integer,RoutingRulesCacheVO>> routingCacheMapForBuyer = new ConcurrentHashMap<Integer,HashMap<Integer,RoutingRulesCacheVO>>();
	private static long fSLEEP_INTERVAL = 100;
	
	
	
	private IApplicationProperties applicationProperties;

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	 
	public void loadCacheData() {
		if(!isCacheLoaded) {
			logger.info("loading the data to cache");
		  this.findRoutingRuleForRuleHdr();
		}else {
			logger.info("Data is already loaded in to chache...");
		}
	}
	
	
	/**This method is used to get conflict for a ruleHdrs
	 * @param RoutingRuleHdr ruleHdr
	 * @return List<RoutingRuleError>
	 */
	 public List<RoutingRuleError> findRoutingRuleConflictsForRuleHdr(RoutingRuleHdr ruleHdr, Integer buyerAssocId,Integer source)
	 {
		 List<String> jobCodes = new ArrayList<String>();
		 List<String> zipCodes = new ArrayList<String>();
		 List<String> pickupLocation = new ArrayList<String>();
		 List<RoutingRuleError> routingRuleError = new ArrayList<RoutingRuleError>();
		 HashMap<Integer, List<String>> marketZips = new HashMap<Integer, List<String>>();
			if (ruleHdr != null) {
					RoutingRulesConflictVO ruleConflictVo = new RoutingRulesConflictVO();
					ruleConflictVo.setRuleId(ruleHdr.getRoutingRuleHdrId());
					List<RoutingRulePrice> routingRulePrice = ruleHdr
							.getRoutingRulePrice();
				
					for (RoutingRulePrice rulePrice : routingRulePrice) {
						jobCodes.add(rulePrice.getJobcode());
					}
					List<RoutingRuleCriteria> routingRuleCriteria = ruleHdr
							.getRoutingRuleCriteria();
					for (RoutingRuleCriteria ruleCriteria : routingRuleCriteria) {
						if (ruleCriteria.getCriteriaName()
								.equals(RoutingRulesConstants.CRITERIA_NAME_ZIP)) {
							zipCodes.add(ruleCriteria.getCriteriaValue());
						}
						if (ruleCriteria.getCriteriaName()
								.equals(RoutingRulesConstants.CRITERIA_NAME_PICKUP_LOCATION)) {
							pickupLocation.add(ruleCriteria.getCriteriaValue());
						}
					}
					List<String> marketIds = routingRuleHdrDao.getMarketIds(ruleHdr.getRoutingRuleHdrId());
					logger.info("marketIds:::"+marketIds+"of::"+ruleHdr.getRoutingRuleHdrId());
					for(String marketId:marketIds){
						List<String> zips = routingRuleHdrDao.findMarketZips(Integer.parseInt(marketId));
						marketZips.put(Integer.parseInt(marketId), zips);
					}
					ruleConflictVo.setJobCodes(jobCodes);
					ruleConflictVo.setZipCodes(zipCodes);
					ruleConflictVo.setPickupLocation(pickupLocation);
					ruleConflictVo.setMarketZipsMap(marketZips);
					List<RoutingRulesConflictVO> ruleConflictVoList = new ArrayList<RoutingRulesConflictVO>();   
					ruleConflictVoList.add(ruleConflictVo);
					logger.info("before findConflictingRoutingRules from findRoutingRuleConflictsForRuleHdr()");
					HashMap<Integer,List<RoutingRuleError>> errorMap = findConflictingRoutingRules(ruleConflictVoList, buyerAssocId,source);
					if(errorMap != null){
						return errorMap.get(ruleHdr.getRoutingRuleHdrId());
					}
				}
			return routingRuleError;	
	 }
	
	  private boolean isValidForCompare(RoutingRulesConflictVO conflictVO,RoutingRulesCacheVO cacheVO) {
		    Integer sizeOfCacheZip = -1;
			Integer sizeOfCachePL = -1;
			Integer sizeOfCacheJC = -1;
			Integer sizeOfConflictZip = -1;
			Integer sizeOfConflictPL = -1;
			Integer sizeOfConflictJC = -1;			
			
			if(((conflictVO!=null)&& (conflictVO.getZipCodes()!=null)&&(conflictVO.getZipCodes().size()>0)) ||(conflictVO !=null && conflictVO.getMarketZipsMap()!=null && conflictVO.getMarketZipsMap().size()>0)) {
				sizeOfConflictZip = 1;				
			}else {
				sizeOfConflictZip = 0;
			}
			
			if(((cacheVO!=null)&& (cacheVO.getZipCodes()!=null)&&(cacheVO.getZipCodes().size()>0)) || (cacheVO!=null && cacheVO.getMarketZipsMap()!=null && cacheVO.getMarketZipsMap().size()>0)) {
				sizeOfCacheZip = 1;				
			}else {
				sizeOfCacheZip = 0;
			}
			
			if((conflictVO!=null)&& (conflictVO.getPickupLocation()!=null)&&(conflictVO.getPickupLocation().size()>0)) {
				sizeOfConflictPL = 1;				
			}else {
				sizeOfConflictPL = 0;
			}
			
			if((cacheVO!=null)&& (cacheVO.getPickupLocation()!=null)&&(cacheVO.getPickupLocation().size()>0)) {
				sizeOfCachePL = 1;				
			}else {
				sizeOfCachePL = 0;
			}
			
			if((conflictVO!=null)&& (conflictVO.getJobCodes()!=null)&&(conflictVO.getJobCodes().size()>0)) {
				sizeOfConflictJC = 1;				
			}else {
				sizeOfConflictJC = 0;
			}
			
			if((cacheVO!=null)&& (cacheVO.getJobCodes()!=null)&&(cacheVO.getJobCodes().size()>0)) {
				sizeOfCacheJC = 1;				
			}else {
				sizeOfCacheJC = 0;
			}
			if((sizeOfCacheZip.equals(sizeOfConflictZip)) && (sizeOfCachePL.equals(sizeOfConflictPL))  && (sizeOfCacheJC.equals(sizeOfConflictJC))) {
				return true;
			}else {
				return false;
			}  
		  
	  }
	  
	  
	  private boolean checkIfValidForCompare(RoutingRulesConflictVO baseVO,RoutingRulesConflictVO activeVO) {
		    Integer sizeOfCacheZip = -1;
			Integer sizeOfCachePL = -1;
			Integer sizeOfCacheJC = -1;
			Integer sizeOfConflictZip = -1;
			Integer sizeOfConflictPL = -1;
			Integer sizeOfConflictJC = -1;			
			
			if(((baseVO!=null)&& (baseVO.getZipCodes()!=null)&&(baseVO.getZipCodes().size()>0)) ||(baseVO !=null && baseVO.getMarketZipsMap()!=null && baseVO.getMarketZipsMap().size()>0)) {
				sizeOfConflictZip = 1;				
			}else {
				sizeOfConflictZip = 0;
			}
			
			if(((activeVO!=null)&& (activeVO.getZipCodes()!=null)&&(activeVO.getZipCodes().size()>0)) || (activeVO!=null && activeVO.getMarketZipsMap()!=null && activeVO.getMarketZipsMap().size()>0)) {
				sizeOfCacheZip = 1;				
			}else {
				sizeOfCacheZip = 0;
			}
			
			if((baseVO!=null)&& (baseVO.getPickupLocation()!=null)&&(baseVO.getPickupLocation().size()>0)) {
				sizeOfConflictPL = 1;				
			}else {
				sizeOfConflictPL = 0;
			}
			
			if((activeVO!=null)&& (activeVO.getPickupLocation()!=null)&&(activeVO.getPickupLocation().size()>0)) {
				sizeOfCachePL = 1;				
			}else {
				sizeOfCachePL = 0;
			}
			
			if((baseVO!=null)&& (baseVO.getJobCodes()!=null)&&(baseVO.getJobCodes().size()>0)) {
				sizeOfConflictJC = 1;				
			}else {
				sizeOfConflictJC = 0;
			}
			
			if((activeVO!=null)&& (activeVO.getJobCodes()!=null)&&(activeVO.getJobCodes().size()>0)) {
				sizeOfCacheJC = 1;				
			}else {
				sizeOfCacheJC = 0;
			}
			if((sizeOfCacheZip.equals(sizeOfConflictZip)) && (sizeOfCachePL.equals(sizeOfConflictPL))  && (sizeOfCacheJC.equals(sizeOfConflictJC))) {
				return true;
			}else {
				return false;
			}  
		  
	  }
	 /**
		 * This method is used to check if a rule is in conflict with the
		 * existing active rules
		 * 
		 * @param baseRuleVO
		 * @param buyerId
		 * @return List<RoutingRuleError>
		 */
	public HashMap<Integer,List<RoutingRuleError>>  findConflictingRoutingRules(
			  List<RoutingRulesConflictVO> baseRuleVOList, Integer buyerAssocId,Integer source) {
		  try {
			  logger.info("entering ...findConflictingRoutingRules()...");
			  HashMap<Integer,List<RoutingRuleError>> conflictMap = new HashMap<Integer,List<RoutingRuleError>>(); 
				 boolean disableConflictFinder=routingRuleHdrDao.isConflictFinderDisabled(buyerAssocId);
				 logger.info("disableConflictFinder in findConflictingRoutingRules method:"+disableConflictFinder);
				 if(!disableConflictFinder){
			  List<LookupVO> marketVOs = lookupBO.getMarkets();
			  HashMap<Integer,String> allMarketZipsMap = new HashMap<Integer, String>();
			  allMarketZipsMap = convertMarketZipsToMap(marketVOs);
			  logger.info("active rules obtained...."+(marketVOs!=null?marketVOs.size():"null"));
			  int tempCount = 0;
			  long startTime = System.currentTimeMillis();
			  HashMap<Integer,RoutingRulesCacheVO> activeRulesMap = new HashMap<Integer,RoutingRulesCacheVO>();
			  checkCacheStatus(source);
			  activeRulesMap = routingCacheMapForBuyer.get(buyerAssocId);
			  if(activeRulesMap!=null){
			  logger.info("active rules obtained...."+(activeRulesMap!=null?activeRulesMap.size():"null"));
			  for(RoutingRulesConflictVO conflictVO:baseRuleVOList) {
				  List<RoutingRuleError> errorList = new ArrayList<RoutingRuleError>();
				  Set<Integer> rulesInCache = activeRulesMap.keySet();
				  //Set<Integer> rulesInCache = new HashSet<Integer>();
				// rulesInCache.add(10);
				 for(Integer key:rulesInCache) {
					  String conflictJCs = "EMPTY";
					  String conflictPLs ="EMPTY";
					  String conflictZips = "EMPTY";
					  String conflictMarkets = "EMPTY";
					  String conflictMarketZipsWithCacheZips = "EMPTY";
					  String conflictZipsWithCacheMarketZips = "EMPTY";
					  RoutingRulesCacheVO cacheVO = activeRulesMap.get(key);
					  if(!(cacheVO.getRuleId().equals(conflictVO.getRuleId()))){
					  boolean isValid = isValidForCompare(conflictVO,cacheVO);
					  if(isValid) {
						  logger.info("isValidForCompare"+key+" Count="+(++tempCount));
						  if(((conflictVO!=null)&& (conflictVO.getZipCodes().size()>0))||(conflictVO !=null && (conflictVO.getMarketZipsMap()!=null) && (conflictVO.getMarketZipsMap().size()>0)))
						  {
							  conflictZips = compareLists(cacheVO.getZipCodes(),conflictVO.getZipCodes());  
							  conflictMarkets = compareMarkets(conflictVO.getMarketZipsMap(),cacheVO.getMarketZipsMap(),allMarketZipsMap);
							  conflictMarketZipsWithCacheZips = compareMarketZipsWithCacheZips(conflictVO.getMarketZipsMap(),cacheVO.getZipCodes(),allMarketZipsMap);
							  conflictZipsWithCacheMarketZips = compareZipsWithCacheMarketZips(conflictVO.getZipCodes(),cacheVO.getMarketZipsMap(),allMarketZipsMap);
						  }
						  if((conflictVO!=null)&& (conflictVO.getJobCodes().size()>0)){
							  conflictJCs = compareLists(cacheVO.getJobCodes(),conflictVO.getJobCodes());
						  }
						  
						  if((conflictVO!=null)&& (conflictVO.getPickupLocation().size()>0)){
							  conflictPLs = compareLists(cacheVO.getPickupLocation(),conflictVO.getPickupLocation());
						  }
						  	//NC=no conflict even if data in input
						  //EMPTY=value is empty bcoz input doesnt have data.
						  if((!conflictZips.equals("NC")||!conflictMarkets.equals("NC")||!conflictMarketZipsWithCacheZips.equals("NC")||!conflictZipsWithCacheMarketZips.equals("NC")) 
								  && !conflictPLs.equals("NC") && !conflictJCs.equals("NC")) {
							 
							  long startzipsOrMarkets = System.currentTimeMillis();
							  if((!conflictZips.equals("EMPTY") && !conflictZips.equals("NC")) || (!conflictMarketZipsWithCacheZips.equals("EMPTY") && !conflictMarketZipsWithCacheZips.equals("NC"))){
								  if((!conflictZips.equals("EMPTY") && !conflictZips.equals("NC")) && (!conflictMarketZipsWithCacheZips.equals("EMPTY") && !conflictMarketZipsWithCacheZips.equals("NC"))){
									  conflictZips = conflictZips +","+conflictMarketZipsWithCacheZips;
								  }else if (!conflictMarketZipsWithCacheZips.equals("EMPTY") && !conflictMarketZipsWithCacheZips.equals("NC")){
									  conflictZips = conflictMarketZipsWithCacheZips;
								  }
								  RoutingRuleError error = new RoutingRuleError();							 
								  error = setRoutingRuleError(cacheVO.getRuleId(),conflictVO.getRuleId(),conflictZips,RoutingRulesConstants.CONFLICTING_ZIPCODES);
								  errorList.add(error);
							  }
							 
							  if(!conflictJCs.equals("EMPTY")){
								  RoutingRuleError error = new RoutingRuleError();							 
								  error = setRoutingRuleError(cacheVO.getRuleId(),conflictVO.getRuleId(),conflictJCs,RoutingRulesConstants.CONFLICTING_JOBCODES);
								  errorList.add(error);
							  }
							  
							  if(!conflictPLs.equals("EMPTY")){
								  RoutingRuleError error = new RoutingRuleError();							 
								  error = setRoutingRuleError(cacheVO.getRuleId(),conflictVO.getRuleId(),conflictPLs,RoutingRulesConstants.CONFLICTING_PICKUPLOCATIONS);
								  errorList.add(error);
							  }
							  if((!conflictMarkets.equals("EMPTY") && !conflictMarkets.equals("NC") && StringUtils.isNotEmpty(conflictMarkets)) 
									  || (!conflictZipsWithCacheMarketZips.equals("EMPTY") && !conflictZipsWithCacheMarketZips.equals("NC") && StringUtils.isNotEmpty(conflictZipsWithCacheMarketZips))){
								 
								  if(!conflictZipsWithCacheMarketZips.equals("EMPTY") && !conflictZipsWithCacheMarketZips.equals("NC") && !conflictMarkets.equals("EMPTY") && !conflictMarkets.equals("NC")){
									  conflictMarkets = conflictZipsWithCacheMarketZips+","+ conflictMarkets;
								  }else if(!conflictZipsWithCacheMarketZips.equals("EMPTY") && !conflictZipsWithCacheMarketZips.equals("NC")){
									  conflictMarkets = conflictZipsWithCacheMarketZips;
								  }
								  
								  RoutingRuleError error = new RoutingRuleError();							 
								  error = setRoutingRuleError(cacheVO.getRuleId(),conflictVO.getRuleId(),conflictMarkets,RoutingRulesConstants.CONFLICTING_MARKETS);
								  errorList.add(error);
							  }
						  }						  
					  }		
				 	} //a rule is in conflict with itself
				  }
				  if(errorList!=null && errorList.size()>0 ){
					  List<RoutingRuleError> conflictList = conflictMap.get(conflictVO.getRuleId());
					  if(conflictList == null){
						  conflictMap.put(conflictVO.getRuleId(), errorList);
					  }else{
						  conflictList.addAll(errorList);
						  conflictMap.put(conflictVO.getRuleId(), conflictList);
					  }
				  }
		      }
			  logger.info("End of all iterations..."+(System.currentTimeMillis()-startTime));
			}
		  }
			  return conflictMap;	
		  }catch(Exception e) {
			  logger.info("EXCEPTION:::..."+e.getMessage());
			  e.printStackTrace();
			  return null;
		  }				

	  }
	
	private HashMap<Integer,String> convertMarketZipsToMap(List<LookupVO> marketZipsList){
		HashMap<Integer,String> marketZipMsp = new HashMap<Integer,String>();
		for(LookupVO marketZip:marketZipsList){
			marketZipMsp.put(Integer.parseInt(marketZip.getType()),marketZip.getDescr());
		}
		return marketZipMsp;
	}	
	

	private String compareLists(List<String> fromList,List<String> toList){	
		List<String> newList = new ArrayList<String>(); 
		newList.addAll(toList);
		String conflictingPickups = "";
		if(fromList == null){
			return "NC";
		}
		newList.retainAll(fromList);
		conflictingPickups = StringUtils.join(newList, ", ");
		if(conflictingPickups == ""){
			return "NC";
		}else{
		return conflictingPickups;
		}
	}
	
	private String compareMarkets(Map<Integer, List<String>> fromMarketsMap,Map<Integer, List<String>> toMarketsMap,HashMap<Integer,String> allMarketZips){
		
		String conflictingMarkets = "";
		if(fromMarketsMap== null || toMarketsMap == null){
			return "NC";
		}
		Set<Integer> fromMarkets = new HashSet<Integer>();
		Set<Integer> toMarkets = new HashSet<Integer>();
		fromMarkets = fromMarketsMap.keySet();
		toMarkets = toMarketsMap.keySet();
		for(Integer toMarket:toMarkets){
			String marketName = allMarketZips.get(toMarket);
			if(fromMarkets.contains(toMarket)){
				if(conflictingMarkets == ""){
					conflictingMarkets = conflictingMarkets + marketName;
				}else{
					conflictingMarkets = conflictingMarkets + ", " + marketName;	
				}
			}
		}
		if(conflictingMarkets == ""){
			return "NC";
		}else{
		return conflictingMarkets;
		}
	}
	//Input rule:Market M3 has zipcode Z3	
	//rule in cache :Zip codes : Z3,Z4,Z5	
	//cnflict:Markets : Z3(M3)

	private String compareMarketZipsWithCacheZips(Map<Integer, List<String>> toMarketsMap,List<String> cacheZips,HashMap<Integer,String> allMarketZips){
		StringBuilder conflictingMarketZipsWithCacheZips = new StringBuilder();
		if(toMarketsMap== null || cacheZips == null ||toMarketsMap.size()==0 || cacheZips.size()==0){
			return "NC";
		}
		Set<Integer> inputMarkets = new HashSet<Integer>();
		inputMarkets = toMarketsMap.keySet();
		for(Integer inputMarket:inputMarkets){
			String marketName = allMarketZips.get(inputMarket);
			List<String> toMarketZips = toMarketsMap.get(inputMarket);
			for(String toMarketZip:toMarketZips){
				if(cacheZips.contains(toMarketZip)){
					if(conflictingMarketZipsWithCacheZips.length() == 0){
						conflictingMarketZipsWithCacheZips.append(toMarketZip).append("("+marketName+")");
					}else{
						conflictingMarketZipsWithCacheZips.append(", ").append(toMarketZip).append("("+marketName+")");
					}
				}
			}
		}
		if(conflictingMarketZipsWithCacheZips.length() == 0){
			return "NC";
		}else{
		return conflictingMarketZipsWithCacheZips.toString();
		}
	}
	//M1(z1)
	private String compareZipsWithCacheMarketZips(List<String> inputZips,Map<Integer, List<String>> cacheMarketZipsMap,HashMap<Integer,String> allMarketZips){
		StringBuffer conflictingZipsWithCacheMarketZips = new StringBuffer();
		if(cacheMarketZipsMap== null || inputZips == null){
			return "NC";
		}
		Set<Integer> cacheMarkets = new HashSet<Integer>();
		cacheMarkets = cacheMarketZipsMap.keySet();
		for(Integer cacheMarket:cacheMarkets){
			String marketName = allMarketZips.get(cacheMarket);
			List<String> cacheMarketZips = cacheMarketZipsMap.get(cacheMarket);
			for(String cacheMarketZip:cacheMarketZips){
				if(inputZips.contains(cacheMarketZip)){
					if(conflictingZipsWithCacheMarketZips.length() == 0){
						conflictingZipsWithCacheMarketZips.append(marketName).append("(").append(cacheMarketZip).append(")");
					}else{
					conflictingZipsWithCacheMarketZips.append(", ").append(marketName).append("(").append(cacheMarketZip).append(")");
					}
				}
			}
		}
		if(conflictingZipsWithCacheMarketZips .length()==0){
			return "NC";
		}else{
		return conflictingZipsWithCacheMarketZips.toString();
		}
	}
	private RoutingRuleError setRoutingRuleError(Integer conflictingRuleId,Integer ruleId,String criteria,Integer errorCauseId){
		RoutingRuleError error = new RoutingRuleError();
		error.setConflictingRuleId(conflictingRuleId);
		error.setError(criteria);
		error.setErrorCauseId(errorCauseId);
		error.setConflictInd(true);
		RoutingRuleHdr hdr = new RoutingRuleHdr();
		hdr.setRoutingRuleHdrId(ruleId);
		error.setRoutingRuleHdr(hdr);
		return error;
	}
	/**
	 * This method is used to check if a rule is in conflict with the set of
	 * rules
	 * 
	 * @param baseRuleVO
	 * @param rulesList
	 * @return List<RoutingRuleError>
	 */
	
	private List<RoutingRuleError> inactiveConflictFinder(
			RoutingRulesConflictVO baseRuleVO, List<RoutingRulesConflictVO> rulesVOList){
		List<RoutingRuleError> conflictingRulesList = new ArrayList<RoutingRuleError>();
		//get market names 
		List<LookupVO> marketVOs = lookupBO.getMarkets();
		HashMap<Integer, String> allMarketZipsMap = new HashMap<Integer, String>();
		allMarketZipsMap = convertMarketZipsToMap(marketVOs);
		  for(RoutingRulesConflictVO inactiveComapreRule:rulesVOList) {
			  if (inactiveComapreRule.getRuleId() != baseRuleVO.getRuleId()){
			  String conflictJCs = "EMPTY";
			  String conflictPLs ="EMPTY";
			  String conflictZips = "EMPTY";
			  String conflictMarkets = "EMPTY";
			  String conflictMarketZipsWithCacheZips = "EMPTY";
			  String conflictZipsWithCacheMarketZips = "EMPTY";
			  if (checkIfValidForCompare(baseRuleVO, inactiveComapreRule)) {
				  if(((baseRuleVO!=null)&& (baseRuleVO.getZipCodes().size()>0))||(baseRuleVO !=null && baseRuleVO.getMarketZipsMap()!=null && baseRuleVO.getMarketZipsMap().size()>0))
				  {
					  conflictZips = compareLists(inactiveComapreRule.getZipCodes(),baseRuleVO.getZipCodes());
					  conflictMarkets = compareMarkets(baseRuleVO.getMarketZipsMap(),inactiveComapreRule.getMarketZipsMap(),allMarketZipsMap);
					  conflictMarketZipsWithCacheZips = compareMarketZipsWithCacheZips(baseRuleVO.getMarketZipsMap(),inactiveComapreRule.getZipCodes(),allMarketZipsMap);
					  conflictZipsWithCacheMarketZips = compareZipsWithCacheMarketZips(baseRuleVO.getZipCodes(),inactiveComapreRule.getMarketZipsMap(),allMarketZipsMap);
				  }
				  if((baseRuleVO!=null)&& (baseRuleVO.getJobCodes().size()>0))
					  conflictJCs = compareLists(inactiveComapreRule.getJobCodes(),baseRuleVO.getJobCodes());
				  if((baseRuleVO!=null)&& (baseRuleVO.getPickupLocation().size()>0))
					  conflictPLs = compareLists(inactiveComapreRule.getPickupLocation(),baseRuleVO.getPickupLocation());
				  	//NC=no conflict even if data in input
				  //EMPTY=value is empty bcoz input doesnt have data.
				  if((!conflictZips.equals("NC")||!conflictMarkets.equals("NC")||!conflictMarketZipsWithCacheZips.equals("NC")||!conflictZipsWithCacheMarketZips.equals("NC")) 
						  && !conflictPLs.equals("NC") && !conflictJCs.equals("NC")) {
					 
					  if((!conflictZips.equals("EMPTY") && !conflictZips.equals("NC")) || (!conflictMarketZipsWithCacheZips.equals("EMPTY") && !conflictMarketZipsWithCacheZips.equals("NC"))){
						  if((!conflictZips.equals("EMPTY") && !conflictZips.equals("NC")) && (!conflictMarketZipsWithCacheZips.equals("EMPTY") && !conflictMarketZipsWithCacheZips.equals("NC"))){
							  conflictZips = conflictZips + conflictMarketZipsWithCacheZips;
						  }else if (!conflictMarketZipsWithCacheZips.equals("EMPTY") && !conflictMarketZipsWithCacheZips.equals("NC")){
							  conflictZips = conflictMarketZipsWithCacheZips;
						  }
						  RoutingRuleError error = new RoutingRuleError();							 
						  error = setRoutingRuleError(inactiveComapreRule.getRuleId(),baseRuleVO.getRuleId(),conflictZips,RoutingRulesConstants.CONFLICTING_ZIPCODES);
						  conflictingRulesList.add(error);
					  }
					
					  if(!conflictJCs.equals("EMPTY")){
						  RoutingRuleError error = new RoutingRuleError();							 
						  error = setRoutingRuleError(inactiveComapreRule.getRuleId(),baseRuleVO.getRuleId(),conflictJCs,RoutingRulesConstants.CONFLICTING_JOBCODES);
						  conflictingRulesList.add(error);
					  }
					  if(!conflictPLs.equals("EMPTY")){
						  RoutingRuleError error = new RoutingRuleError();							 
						  error = setRoutingRuleError(inactiveComapreRule.getRuleId(),baseRuleVO.getRuleId(),conflictPLs,RoutingRulesConstants.CONFLICTING_PICKUPLOCATIONS);
						  conflictingRulesList.add(error);
					  }
					  
					  if((!conflictMarkets.equals("EMPTY") && !conflictMarkets.equals("NC") && StringUtils.isNotEmpty(conflictMarkets)) 
							  || (!conflictZipsWithCacheMarketZips.equals("EMPTY") && !conflictZipsWithCacheMarketZips.equals("NC") && StringUtils.isNotEmpty(conflictZipsWithCacheMarketZips))){
						 
						  if(!conflictZipsWithCacheMarketZips.equals("EMPTY") && !conflictZipsWithCacheMarketZips.equals("NC") && !conflictMarkets.equals("EMPTY") && !conflictMarkets.equals("NC")){
							  conflictMarkets = conflictZipsWithCacheMarketZips + conflictMarkets;
						  }else if(!conflictZipsWithCacheMarketZips.equals("EMPTY") && !conflictZipsWithCacheMarketZips.equals("NC")){
							  conflictMarkets = conflictZipsWithCacheMarketZips;
						  }
						  
						  RoutingRuleError error = new RoutingRuleError();							 
						  error = setRoutingRuleError(inactiveComapreRule.getRuleId(),baseRuleVO.getRuleId(),conflictMarkets,RoutingRulesConstants.CONFLICTING_MARKETS);
						  conflictingRulesList.add(error);
					  }
				  }						  
			  }					  

		  }
	}
		  
		return conflictingRulesList;
	}
		
	/**
	 * This method finds the conflicts among a set of inactive rules.
	 * 
	 * @param rule
	 * @param inactiveRules
	 * @return List<RoutingRuleError>
	 */
	public List<RoutingRuleError> findConflictsAmongInactiveRules(
			RoutingRulesConflictVO ruleVO, List<RoutingRulesConflictVO> rulesVOList) {
		List<RoutingRuleError> conflictList = new ArrayList<RoutingRuleError>();
		conflictList = inactiveConflictFinder(ruleVO, rulesVOList);
		logger.info("Inactive rule :::"+ruleVO.getRuleId()+" in conflict with inactive rules::"+(conflictList!=null? conflictList.size():"null"));
		return conflictList;
	}
		/**
		 * getter method for RoutingRuleHdrDao
		 * 
		 * @return RoutingRuleHdrDao
		 */
		public RoutingRuleHdrDao getRoutingRuleHdrDao() {
			return routingRuleHdrDao;
		}
		/**
		 * Setter method for RoutingRuleHdrDao
		 * @param routingRuleHdrDao
		 */
		public void setRoutingRuleHdrDao(RoutingRuleHdrDao routingRuleHdrDao) {
			this.routingRuleHdrDao = routingRuleHdrDao;
		}	
		
		 /**
		 * Made code change for Conflict Finder
		  * This method is used to get conflict for a ruleHdr
		  * @param RoutingRuleHdr ruleHdr
		  * @return List<RoutingRuleError>
		  */
		 public List<RoutingRulesConflictVO> findRoutingRuleConflictListForRuleHdr(
				List<RoutingRuleHdr> ruleHdrList){
			 List<RoutingRulesConflictVO> ruleConflictListVo = new ArrayList<RoutingRulesConflictVO>();
				if (ruleHdrList != null) {
					for(RoutingRuleHdr ruleHdr : ruleHdrList){
						RoutingRulesConflictVO ruleConflictVo = new RoutingRulesConflictVO();
						List<String> jobCodes = new ArrayList<String>();
						List<String> zipCodes = new ArrayList<String>();
						List<String> pickupLocation = new ArrayList<String>();
						Set<String> tJobCodes = new HashSet<String>();
						Set<String> tZipCodes = new HashSet<String>();
						Set<String> tPickupLocation = new HashSet<String>();
						Set<String> tMarkets = new HashSet<String>();
						HashMap<Integer, List<String>> marketZips = new HashMap<Integer, List<String>>();
						List<RoutingRulePrice> routingRulePrice = ruleHdr.
							getRoutingRulePrice();
						for (RoutingRulePrice rulePrice : routingRulePrice) {
							tJobCodes.add(rulePrice.getJobcode());
						}
						List<RoutingRuleCriteria> routingRuleCriteria = ruleHdr.
							getRoutingRuleCriteria();
						for (RoutingRuleCriteria ruleCriteria : routingRuleCriteria) {
							if (ruleCriteria.getCriteriaName()
									.equals(RoutingRulesConstants.CRITERIA_NAME_ZIP)) {
								tZipCodes.add(ruleCriteria.getCriteriaValue());
							}
							if (ruleCriteria.getCriteriaName()
									.equals(RoutingRulesConstants.CRITERIA_NAME_PICKUP_LOCATION)) {
								tPickupLocation.add(ruleCriteria.getCriteriaValue());
							}
							if (ruleCriteria.getCriteriaName()
									.equals(RoutingRulesConstants.CRITERIA_NAME_MARKET)) {
								tMarkets.add(ruleCriteria.getCriteriaValue());
							}
						}
						jobCodes.addAll(tJobCodes);
						zipCodes.addAll(tZipCodes);
						pickupLocation.addAll(tPickupLocation);
						
						//set marketZips	
						//List<String> marketIds = routingRuleHdrDao.getMarketIds(ruleHdr.getRoutingRuleHdrId());
						for(String marketId:tMarkets){
							List<String> zips = routingRuleHdrDao.findMarketZips(Integer.parseInt(marketId));
							marketZips.put(Integer.parseInt(marketId), zips);
						}
						ruleConflictVo.setRuleId(ruleHdr.getRoutingRuleHdrId());
						ruleConflictVo.setJobCodes(jobCodes);
						ruleConflictVo.setZipCodes(zipCodes);
						ruleConflictVo.setPickupLocation(pickupLocation);
						ruleConflictVo.setMarketZipsMap(marketZips);
						ruleConflictListVo.add(ruleConflictVo);
					}
				}
				return ruleConflictListVo;
		 	}
		 
		 
		     /**
			 * Change made by Vinod
			  * Cache active rules of each buyer
			  * @param RoutingRuleHdr ruleHdr
			  * @return List<RoutingRuleError>
			  */
		public  void findRoutingRuleForRuleHdr(){
			 List<Integer> ruleHdrList = new ArrayList<Integer>();
			 //cache active rules of each buyer
			 List<Integer> buyerIds = routingRuleHdrDao.getCarBuyers();
			 for(Integer buyerId:buyerIds){				 
				 ruleHdrList = routingRuleHdrDao.getHeaderList(buyerId);
				 HashMap<Integer,RoutingRulesCacheVO> routingCacheMap = new HashMap<Integer,RoutingRulesCacheVO>();
				if (ruleHdrList != null) {
					for(Integer ruleHdrId : ruleHdrList){
						RoutingRulesCacheVO ruleConflictVo = new RoutingRulesCacheVO();
						List<String> jobCodes = new ArrayList<String>();
						List<String> zipCodes = new ArrayList<String>();
						List<String> pickupLocation = new ArrayList<String>();
	
						jobCodes = routingRuleHdrDao.getJobCodes(ruleHdrId);
						zipCodes = routingRuleHdrDao.getZips(ruleHdrId);
						pickupLocation = routingRuleHdrDao.getPickups(ruleHdrId);
	
						ruleConflictVo.setRuleId(ruleHdrId);
						ruleConflictVo.setJobCodes(jobCodes);
						ruleConflictVo.setZipCodes(zipCodes);
						ruleConflictVo.setPickupLocation(pickupLocation);
						routingCacheMap.put(ruleHdrId, ruleConflictVo);
					}
					isCacheLoaded = true;
				}
				HashMap<Integer, HashMap<Integer, List<String>>> marketZips = new HashMap<Integer, HashMap<Integer, List<String>>>();
				marketZips = routingRuleHdrDao.getMarketZips(buyerId);
				Set <Integer> ids = marketZips.keySet();
				for(Integer id :ids){
					RoutingRulesCacheVO cacheVO= routingCacheMap.get(id);
					cacheVO.setMarketZipsMap(marketZips.get(id));
				}
				routingCacheMapForBuyer.put(buyerId, routingCacheMap);
		 	}
		}
			 			 
			 /**
				 * Change made by Vinod
				  * This method is used to get conflict for a ruleHdr
				  * @param RoutingRuleHdr ruleHdr
				  * @return List<RoutingRuleError>
				  */
				 public  void findRoutingRuleForRuleHdrLocal(){	
					 File cacheData = null;
					 PrintWriter writer = null;
					 try {
						 cacheData = new File("./cachedata.dat");
						 writer = new PrintWriter(new FileWriter(cacheData),true);					 
					 }catch(Exception e) {
						 e.printStackTrace();
					 }					 
					 
					 long startTime = System.currentTimeMillis();
					 logger.info("findRoutingRuleForRuleHdr ");
					 List<Integer> ruleHdrList = new ArrayList<Integer>();
					 //cache active rules of each buyer
					 List<Integer> buyerIds = routingRuleHdrDao.getCarBuyers();
					 for(Integer buyerId:buyerIds){				 
					 ruleHdrList = routingRuleHdrDao.getHeaderList(buyerId);
					 HashMap<Integer,RoutingRulesCacheVO> routingCacheMap = new HashMap<Integer,RoutingRulesCacheVO>();
					 logger.info("ruleHdrList SIZE"+ruleHdrList.size());
					 int count = 0;
						if (ruleHdrList != null) {
								for (Integer ruleHdrId : ruleHdrList) {
									logger.info("getTimeCOUNT -->" + (++count));
									RoutingRulesCacheVO ruleConflictVo = new RoutingRulesCacheVO();
									List<String> jobCodes = new ArrayList<String>();
									List<String> zipCodes = new ArrayList<String>();
									List<String> pickupLocation = new ArrayList<String>();
									jobCodes = routingRuleHdrDao.getJobCodes(ruleHdrId);
									logger.info("getTimeJobCodes::: "
											+ (System.currentTimeMillis() - startTime));
									zipCodes = routingRuleHdrDao.getZips(ruleHdrId);
									logger.info("getTimeZips::: "
											+ (System.currentTimeMillis() - startTime));
									pickupLocation = routingRuleHdrDao.getPickups(ruleHdrId);
									logger.info("getTimePickups::: "
											+ (System.currentTimeMillis() - startTime));
									ruleConflictVo.setRuleId(ruleHdrId);
									ruleConflictVo.setJobCodes(jobCodes);
									ruleConflictVo.setZipCodes(zipCodes);
									ruleConflictVo.setPickupLocation(pickupLocation);
									// ruleConflictVo.setMarketZipsMap(marketZips);
									routingCacheMap.put(ruleHdrId, ruleConflictVo);
									logger.info("getTimeroutingCacheMap.put::: "
											+ (System.currentTimeMillis() - startTime));
								}
								isCacheLoaded = true;
								 logger.info("getTimecache loaded"+(System.currentTimeMillis()-startTime));				
						}
						HashMap<Integer, HashMap<Integer, List<String>>> marketZips = new HashMap<Integer, HashMap<Integer, List<String>>>();
						logger.info("start getMarketZipsCache"+(System.currentTimeMillis()-startTime));
						marketZips = routingRuleHdrDao.getMarketZips(buyerId);
						logger.info("end getMarketZipsCache"+(System.currentTimeMillis()-startTime));
						Set <Integer> ids = marketZips.keySet();
						for(Integer id :ids){
						RoutingRulesCacheVO cacheVO= routingCacheMap.get(id);
						cacheVO.setMarketZipsMap(marketZips.get(id));
						}
						routingCacheMapForBuyer.put(buyerId, routingCacheMap);
						logger.info("end of cache"+(System.currentTimeMillis()-startTime));
				 	}
					 writer.print(routingCacheMapForBuyer);
					 writer.flush();
					 try {
						 writer.close();
					 }catch(Exception e) {
						 
					 }
			 } 
			
			 private static void readFromLocalCache() {
				 try {
					 ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./cachedata.dat"));					 
					 routingCacheMapForBuyer = (ConcurrentHashMap<Integer,HashMap<Integer,RoutingRulesCacheVO>>)ois.readObject();
					 logger.info("SIZE if routingCacheMapForBuyer="+(routingCacheMapForBuyer!=null?routingCacheMapForBuyer.size():"null"));
				 }catch(Exception e) {
					 logger.info("EOF reached...");
				 }
				 
			 }
			 
		/**
		 * To check the memory usage : Only for testing
		 * 
		 * @return
		 */

			private static long getMemoryUse() {
				putOutTheGarbage();
				long totalMemory = Runtime.getRuntime().totalMemory();
				putOutTheGarbage();
				long freeMemory = Runtime.getRuntime().freeMemory();
				return (totalMemory - freeMemory);
			}
		
			private static void putOutTheGarbage() {
				collectGarbage();
				collectGarbage();
			}
		
			private static void collectGarbage() {
				try {
					System.gc();
					Thread.currentThread().sleep(fSLEEP_INTERVAL);
					System.runFinalization();
					Thread.currentThread().sleep(fSLEEP_INTERVAL);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
		
			}	
			  
	public synchronized void updateActiveRulesCache(List<RoutingRuleHdr> ruleHdrs,
			String action, Integer buyerAssocId) {
		logger.info("inside updateActiveRulesCache");
		routingRuleHdrDao.updateCache(ruleHdrs, action, buyerAssocId);
		logger.info("inside updateActiveRulesCache");
	}

	public void checkCacheStatus(Integer sourceOfRetrieve) {
		logger.info("inside checkCacheStatus");
		List<RoutingRuleCacheStatusVO> cacheStatusVOs = routingRuleHdrDao
				.retrieveDirtyCache(sourceOfRetrieve);
		if ((cacheStatusVOs != null) && (cacheStatusVOs.size() > 0)) {
			logger.info("going to call updateActiveRulesCacheLocal");
			logger.info("cacheStatusVOs size::" + cacheStatusVOs.size());
			updateActiveRulesCacheLocal(cacheStatusVOs);
		}
		logger.info("exiting checkCacheStatus");
	}

	public void updateActiveRulesCacheLocal(
			List<RoutingRuleCacheStatusVO> cacheStatusVOs) {
		logger.info("inside updateActiveRulesCacheLocal");
		Integer currentBuyerAssocId = -1;
		HashMap<Integer, RoutingRulesCacheVO> activeRulesMap = null;
		for (RoutingRuleCacheStatusVO cacheStatusVO : cacheStatusVOs) {
			Integer buyerAssocId = cacheStatusVO.getBuyerAssocId();
			String action = cacheStatusVO.getAction();
			logger.info("buyerAssocId updateActiveRulesCacheLocal"
					+ buyerAssocId);
			logger.info("action updateActiveRulesCacheLocal" + action);
			Integer ruleHdrId = cacheStatusVO.getRoutingRuleHdrId();
			if (buyerAssocId != currentBuyerAssocId) {
				currentBuyerAssocId = buyerAssocId;
				activeRulesMap = routingCacheMapForBuyer.get(buyerAssocId);
			}
			logger.info("currentBuyerAssocId" + currentBuyerAssocId);
			if (action.equals(RoutingRulesConstants.RULE_ACTION_ACTIVATE)) {
				if(!activeRulesMap.containsKey(ruleHdrId)){
					logger.info("inside RULE_ACTION_ACTIVATE");
					RoutingRulesCacheVO ruleConflictVo = new RoutingRulesCacheVO();
					List<String> jobCodes = new ArrayList<String>();
					List<String> zipCodes = new ArrayList<String>();
					List<String> pickupLocation = new ArrayList<String>();
					HashMap<Integer, List<String>> marketZips = new HashMap<Integer, List<String>>();
	
					jobCodes = routingRuleHdrDao.getJobCodes(ruleHdrId);
					zipCodes = routingRuleHdrDao.getZips(ruleHdrId);
					pickupLocation = routingRuleHdrDao.getPickups(ruleHdrId);
					// marketZips = routingRuleHdrDao.getMarketZips(ruleHdrId);
					List<String> marketIds = routingRuleHdrDao
							.getMarketIds(ruleHdrId);
					for (String marketId : marketIds) {
						List<String> zips = routingRuleHdrDao
								.findMarketZips(Integer.parseInt(marketId));
						marketZips.put(Integer.parseInt(marketId), zips);
					}
	
					ruleConflictVo.setRuleId(ruleHdrId);
					ruleConflictVo.setJobCodes(jobCodes);
					ruleConflictVo.setZipCodes(zipCodes);
					ruleConflictVo.setPickupLocation(pickupLocation);
					ruleConflictVo.setMarketZipsMap(marketZips);
					activeRulesMap.put(ruleHdrId, ruleConflictVo);
					logger.info("after activeRulesMap.put" + ruleHdrId + "zips::"
							+ zipCodes + "jobCodes" + jobCodes + "pickupLocation"
							+ pickupLocation);
				}
			} else if (action
					.equals(RoutingRulesConstants.RULE_ACTION_INACTIVATE) || 
					action.equals(RoutingRulesConstants.RULE_ACTION_ARCHIVED)) {
				logger.info("inside RULE_ACTION_INACTIVATE");
				if (activeRulesMap != null) {
					if(activeRulesMap.containsKey(ruleHdrId)){
						activeRulesMap.remove(ruleHdrId);
						logger.info("after activeRulesMap.remove::" + ruleHdrId);
					}
				}
			} else if (action.equals(RoutingRulesConstants.RULE_ACTION_UPDATE)) {
				logger.info("inside RULE_ACTION_UPDATE");
				RoutingRulesCacheVO ruleConflictVo = new RoutingRulesCacheVO();
				boolean needUpdate = true;
				// Check if the rule is already in cache
				if (activeRulesMap != null) {
					if(activeRulesMap.containsKey(ruleHdrId)){
						ruleConflictVo = activeRulesMap.get(ruleHdrId);
						if(null != ruleConflictVo
								&& null != cacheStatusVO
								&& null != ruleConflictVo.getUpdateTime()
								&& null != cacheStatusVO.getUpdateTime()
								&& (ruleConflictVo.getUpdateTime().equals(cacheStatusVO.getUpdateTime()))){
							// No need to update since there is no change after the last cache refresh.
							logger.info("No need to update the cache since dates are equal");
							needUpdate = false;
						}
					}
				}
				if(needUpdate){
					List<String> jobCodes = new ArrayList<String>();
					List<String> zipCodes = new ArrayList<String>();
					List<String> pickupLocation = new ArrayList<String>();
					HashMap<Integer, List<String>> marketZips = new HashMap<Integer, List<String>>();

					jobCodes = routingRuleHdrDao.getJobCodes(ruleHdrId);
					zipCodes = routingRuleHdrDao.getZips(ruleHdrId);
					pickupLocation = routingRuleHdrDao.getPickups(ruleHdrId);
					// marketZips = routingRuleHdrDao.getMarketZips(ruleHdrId);
					List<String> marketIds = routingRuleHdrDao
							.getMarketIds(ruleHdrId);
					for (String marketId : marketIds) {
						List<String> zips = routingRuleHdrDao
								.findMarketZips(Integer.parseInt(marketId));
						marketZips.put(Integer.parseInt(marketId), zips);
					}

					ruleConflictVo.setRuleId(ruleHdrId);
					ruleConflictVo.setJobCodes(jobCodes);
					ruleConflictVo.setZipCodes(zipCodes);
					ruleConflictVo.setPickupLocation(pickupLocation);
					ruleConflictVo.setMarketZipsMap(marketZips);
					if(null!=cacheStatusVO && null!=cacheStatusVO.getUpdateTime()){
						ruleConflictVo.setUpdateTime(cacheStatusVO.getUpdateTime());
					}
					// Update is nothing but a removal and addition.
					if (activeRulesMap != null) {
						if(activeRulesMap.containsKey(ruleHdrId)){
							activeRulesMap.remove(ruleHdrId);
						}
					}
					activeRulesMap.put(ruleHdrId, ruleConflictVo);
					logger.info("after activeRulesMap.put" + ruleHdrId + "zips::"
							+ zipCodes + "jobCodes" + jobCodes + "pickupLocation"
							+ pickupLocation);
				}else {
					logger.info("No need to update cache, as it has the latest criteria for "+ruleHdrId);
				}
			}
		}	
		if(getSwitch()){
			// Write to file
			File cacheData = null;
			PrintWriter writer = null;
			try {
				String fileName = getFileName(RoutingRulesConstants.UPDATE_CACHE_FILENAME);
				cacheData = new File(fileName);
				writer = new PrintWriter(new FileWriter(cacheData),true);	
				writer.print(routingCacheMapForBuyer);
				writer.flush();
				writer.close();
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
		}
	}


	public ILookupBO getLookupBO() {
		return lookupBO;
	}


	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public void afterPropertiesSet() throws Exception {
		logger.info("Before loadCacheData...");
		loadCacheData();			
		// Write to file
		File cacheData = null;
		PrintWriter writer = null;
		try {
			
			 String fileName = getFileName(RoutingRulesConstants.INITIAL_CACHE_FILENAME);
			 cacheData = new File(fileName);
			 writer = new PrintWriter(new FileWriter(cacheData),true);	
			 writer.print(routingCacheMapForBuyer);
			 writer.flush();
			 writer.close();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }		
		logger.info("After loadCacheData...");
	}
	
	/**
	 *  Get file name
	 * 
	 * @param when
	 * @return
	 */
	private String getFileName(String when){
		String fileName = null;		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss:SSS"); 
		
		if(RoutingRulesConstants.INITIAL_CACHE_FILENAME.equalsIgnoreCase(when)){
			try{
			fileName = applicationProperties
				.getPropertyValue(RoutingRulesConstants.INITIAL_CACHE_FILENAME);
			}catch (DataNotFoundException e) {
				fileName = RoutingRulesConstants.INITIAL_CACHE_NO_FILENAME;					
			}
		}else if(RoutingRulesConstants.UPDATE_CACHE_FILENAME.equalsIgnoreCase(when)){
			try{
			fileName = applicationProperties
				.getPropertyValue(RoutingRulesConstants.UPDATE_CACHE_FILENAME);
			}catch (DataNotFoundException e) {
				fileName = RoutingRulesConstants.UPDATE_CACHE_NO_FILENAME;
			}
		}
		if(null != fileName){
			fileName = fileName+df.format(new Date())+".dat";
		}
		return fileName;
		
	}
	
	private boolean getSwitch(){
		String switchOn = null;
		boolean writeRequired = false;
		try{
			switchOn = applicationProperties
				.getPropertyFromDB(RoutingRulesConstants.CACHE_WRITE_SWITCH_ON);
		}catch (DataNotFoundException e) {
			switchOn = RoutingRulesConstants.CACHE_WRITE_SWITCH_ON_DEFAULT;					
		}
		if("1".equalsIgnoreCase(switchOn)){
			writeRequired = true;
		}		
		return writeRequired;
	}
		 
}
