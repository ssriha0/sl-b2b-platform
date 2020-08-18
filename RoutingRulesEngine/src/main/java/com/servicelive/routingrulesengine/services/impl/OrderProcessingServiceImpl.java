package com.servicelive.routingrulesengine.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.routingrules.RoutingRuleCriteria;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRulePrice;
import com.servicelive.domain.routingrules.RoutingRuleSwitches;
import com.servicelive.domain.routingrules.RoutingRuleVendor;
import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.dao.LookupZipMarketDao;
import com.servicelive.routingrulesengine.dao.OldSPNNetworkDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleBuyerAssocDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao;
import com.servicelive.routingrulesengine.dao.SORoutingRuleAssocDao;
import com.servicelive.routingrulesengine.dao.SPNServiceProviderStateDao;
import com.servicelive.routingrulesengine.dao.ServiceProviderDao;
import com.servicelive.routingrulesengine.services.OrderProcessingService;


/**
 * 
 *
 */
public class OrderProcessingServiceImpl implements OrderProcessingService {
	private static final Logger logger = Logger.getLogger(OrderProcessingServiceImpl.class);
	private LookupZipMarketDao lookupZipMarketDao;
	private OldSPNNetworkDao oldSPNNetworkDao;
	private RoutingRuleBuyerAssocDao routingRuleBuyerAssocDao;
	private RoutingRuleHdrDao routingRuleHdrDao;
	private ServiceProviderDao serviceProviderDao;
	private SPNServiceProviderStateDao spnServiceProviderStateDao;
	private SORoutingRuleAssocDao soRoutingRuleAssocDao;
	

	/**
	 * Integer checkConditionalRoutingQalification(ServiceOrder so, String jobcode);
     *       - this is new order - proceed with evaluation
     *       - return rule id or NULL when order is not CAR eligible
	 *  NOTE:  so_id is not available for the NEW order at this point, 
	 *  so the client will need to execute a separate call to update so_routing_rule_assoc
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer checkConditionalRoutingQualification(ServiceOrder so,String jobcode) throws Exception{
        RoutingRuleHdr routingRule = getConditionalRoutingRule(so,jobcode);
        return routingRule == null ? null : routingRule.getRoutingRuleHdrId();				 
	}

    @Transactional(propagation=Propagation.SUPPORTS)
    public RoutingRuleHdr getConditionalRoutingRule(ServiceOrder so,String jobcode) throws Exception {
    	try {
            if(so == null || so.getBuyer() == null || so.getBuyer().getBuyerId() == null) {
                return null;
            }
            if(so.isUpdate()){
            	Integer wfStateId = routingRuleBuyerAssocDao.findWfStateIdforSO(so.getSoId());
            	if(wfStateId>=RoutingRulesConstants.ACCEPTED_STATUS){
            		RoutingRuleHdr ruleHdr= routingRuleBuyerAssocDao.findRuleHrIdForSO(so.getSoId());
            			return ruleHdr; 
            	} 
            }
            List<RoutingRuleSwitches> resultList = routingRuleBuyerAssocDao.findSortOrder();
            RoutingRuleHdr hdr =null;
            String sortOrder = "ASC";
            boolean useExistingCode=false;
            for(RoutingRuleSwitches results:resultList){
         	   if(results.getSwitchName().equals("SortOrderSwitch")){
         		  if(results.getValue()==true){
         			 sortOrder = "DESC";
         		  }         		  		
         	   }else if(results.getSwitchName().equalsIgnoreCase("UseExistingCode")){
         		  if(results.getValue()==true){
         			 useExistingCode = true;
          		  } 
         	   }
            } 
                                  
            Integer buyerId = so.getBuyer().getBuyerId();
            Integer routingRuleBuyerAssocId = routingRuleBuyerAssocDao.findRoutingRuleBuyerAssocId(buyerId);
            String marketId=routingRuleBuyerAssocDao.findMarketId(so.getServiceLocation().getZip());  

            //step2..Retrieve all active CAR rules matching the SO's jobcode AND zipcode, sorted according to Switch 3.
            logger.info("JOB code and zip code before finding a suitable rule for SO: "+so.getSoId()+ " - "+jobcode+":"+so.getServiceLocation().getZip());
            logger.info("Before finding a suitable rule for SO:Routing Rule BuyerAssocId"+routingRuleBuyerAssocId+"JOB CODE"+jobcode+"Zip code"+so.getServiceLocation().getZip()+"Market id"+marketId+"Sort order"+sortOrder);
            
            //priority 4 issue changes
            //Get the ISP_ID custom reference values of the SO
            List<RoutingRuleHdr> routingRuleHdrs = null;
            Set<String> ispIds = new HashSet<String>();
            logger.info("Before finding rule for SO");
            if(null != buyerId && InHomeNPSConstants.HSRBUYER.intValue() == buyerId.intValue()){            	
            	if(null != so.getCustomRefs() && !so.getCustomRefs().isEmpty()){
            		for(ServiceOrderCustomRefVO customRef : so.getCustomRefs()){
                		if(null != customRef.getRefType() && InHomeNPSConstants.TECH_ID.equalsIgnoreCase(customRef.getRefType()) ){
                			ispIds.add(customRef.getRefValue());
                		}
                	}  
            	}
            	if(ispIds.isEmpty()){
            		logger.info("No ISP_ID for SO");
            		return hdr;
            	}
            	logger.info("Before finding a suitable rule for SO: '"+so.getSoId() +"for Inhome buyer");
            	//Retrieve all active CAR rules matching the SO's jobcode, zipcode and ISP_ID for Inhome buyer
            	routingRuleHdrs = routingRuleBuyerAssocDao.findRoutingRuleForInhome(routingRuleBuyerAssocId,jobcode,so.getServiceLocation().getZip(),marketId, ispIds, sortOrder);
            	if(null == routingRuleHdrs || (null != routingRuleHdrs && routingRuleHdrs.isEmpty())){
            		logger.info("No rule matching zip, jobcode and ISP_ID found for SO: "+ so.getSoId());
            		return hdr;
            	}
            	
            }else{
            	routingRuleHdrs = routingRuleBuyerAssocDao.findRoutingRuleHdrId(routingRuleBuyerAssocId,jobcode,so.getServiceLocation().getZip(),marketId,sortOrder);
            }
            logger.info("List size of routingRuleHdrs :"+(null != routingRuleHdrs ? routingRuleHdrs.size() : 0));
            hdr = checkCustomRefs(routingRuleHdrs,so,routingRuleBuyerAssocId,marketId,sortOrder,jobcode,useExistingCode);
            if(null!=hdr){
            	logger.info("Associated rule id with SO: "+so.getSoId()+"is::: "+hdr.getRoutingRuleHdrId());
            }else{
            	logger.info("No rule id associated with SO:"+so.getSoId());
            }
            
            return hdr;                       
    	}catch(Exception e) {
    		logger.info(e.getMessage(),e);
    		return null;
    	}
      }
    
    /** 
     * This method evaluates Rule with (Job code + Zip code) and (Job code + Zip code + Custom ref) with SO's criteria's
	 * @param List routingRuleHdr
	 * @param ServiceOrder
	 * @param routingRuleBuyerAssocId
	 * @param marketId
	 * @param String sortOrder
	 * @param jobcode
	 * @return RoutingRuleHdr
	 */
    @Transactional(propagation=Propagation.SUPPORTS)
    private RoutingRuleHdr checkCustomRefs(List<RoutingRuleHdr> routingRuleHdrs,ServiceOrder so,Integer routingRuleBuyerAssocId,
    		String marketId,String sortOrder,String jobcode, boolean useExistingCode) throws Exception{
    	List<RoutingRuleHdr> onlyJobandZip = new ArrayList<RoutingRuleHdr>();
        int matchingCustomRefsCount=0;
    	RoutingRuleHdr bestMatchedRule = null;
    	Map<String, String> soCustomRefMap = new HashMap<String, String>();
    	boolean multipleSOCustRef=false;
    	boolean isEligibleRule=true;
    	//if there are multiple values for the same custom reference in an SO, we won't use the MAP method
    	for(ServiceOrderCustomRefVO serviceOrderCustomRefVO:so.getCustomRefs()){
    		logger.info("Inside checkcustomref method");
    		if(!(soCustomRefMap.containsKey(serviceOrderCustomRefVO.getRefType()))){
    			soCustomRefMap.put(serviceOrderCustomRefVO.getRefType(), serviceOrderCustomRefVO.getRefValue());
    		}else{
    			logger.info("Inside checkcustomref method with multiple custom ref");
    			multipleSOCustRef=true;
    			break;
    		}
    	}
        for(RoutingRuleHdr routingRuleHdr:routingRuleHdrs){
        	List<RoutingRuleCriteria> routingRuleCriteriaList = routingRuleHdr.getRoutingRuleCriteria();
        	Set<String> ruleCustRefList = new HashSet<String>();
        	Set<String> matchedRuleCustRefList = new HashSet<String>();        	
        	//step3        	
        	for(RoutingRuleCriteria routingRuleCriteria:routingRuleCriteriaList) {
        		if(!(routingRuleCriteria.getCriteriaName().equals("ZIP")||routingRuleCriteria.getCriteriaName().equalsIgnoreCase("MARKET"))){
        			if(ruleCustRefList.isEmpty() || matchedRuleCustRefList.isEmpty() 
        					|| !(matchedRuleCustRefList.contains(routingRuleCriteria.getCriteriaName()))){ 
        				ruleCustRefList.add(routingRuleCriteria.getCriteriaName());
        				if(multipleSOCustRef){
        					for(ServiceOrderCustomRefVO serviceOrderCustomRefVO:so.getCustomRefs()){
            					if((routingRuleCriteria.getCriteriaName().equals(serviceOrderCustomRefVO.getRefType()) &&
                    					(routingRuleCriteria.getCriteriaValue().equals(serviceOrderCustomRefVO.getRefValue())))) {
            						matchedRuleCustRefList.add(routingRuleCriteria.getCriteriaName());
            						break;
            					}            					
            				}        					
        				}else{
        					if(!(soCustomRefMap.isEmpty()) && soCustomRefMap.containsKey(routingRuleCriteria.getCriteriaName())){
        						if(soCustomRefMap.get(routingRuleCriteria.getCriteriaName()).equalsIgnoreCase(routingRuleCriteria.getCriteriaValue())){
        							matchedRuleCustRefList.add(routingRuleCriteria.getCriteriaName());
        						}
        					}else{
        						isEligibleRule=false;
        						break;//Rule criteria is not present in SO custom reference itself. So rule is not a match
        					}
        				}
        				
        			}    				
    			}
        	}
        	if(isEligibleRule){
        		if(ruleCustRefList.isEmpty()){
            		onlyJobandZip.add(routingRuleHdr); //this is a job code and zip/market only rule       		
            	}else{
            		if(!(matchedRuleCustRefList.isEmpty())){
            			if(matchedRuleCustRefList.size()==ruleCustRefList.size() && matchedRuleCustRefList.size()>matchingCustomRefsCount){
            				bestMatchedRule=routingRuleHdr;
            				matchingCustomRefsCount=matchedRuleCustRefList.size();
            			}
            		}
            	}         		
        	}        	       	        		
        }
        if(null!=bestMatchedRule){
        	logger.info("bestMatchedRule as per Step3::: "+bestMatchedRule.getRoutingRuleHdrId());
        	return bestMatchedRule;
        }else if(!(onlyJobandZip.isEmpty())){
        	logger.info("onlyJobandZip rule as per Step3::: "+onlyJobandZip.get(0).getRoutingRuleHdrId());
        	return onlyJobandZip.get(0); 
        }else{
        	//priority 4 issue changes
        	//for HSR buyer, if no matching rule is found with zip, jobcode and isp_id, return
        	logger.info("Rule doesnot have matching custom reference: "+ so.getSoId());
        	if(null != so && null != so.getBuyer() && null != so.getBuyer().getBuyerId() 
        			&& InHomeNPSConstants.HSRBUYER.intValue() == so.getBuyer().getBuyerId().intValue()){
        		logger.info("No rule for SO: "+ so.getSoId());
        		return bestMatchedRule;
        	}
        	RoutingRuleHdr hdr = getRoutingRuleIds(so,routingRuleBuyerAssocId,marketId,sortOrder,jobcode,routingRuleHdrs,multipleSOCustRef,soCustomRefMap, useExistingCode);
        	return hdr;
        }
    }
    
    /** 
     * This method evaluates Rule with (Job code + Custom ref) and (Job code + Custom ref) and Job/Zip/Cust Ref Only with SO's criteria's
	 * @param List routingRuleHdr
	 * @param ServiceOrder
	 * @param routingRuleBuyerAssocId
	 * @param marketId
	 * @param String sortOrder
	 * @param jobcode
	 * @param boolean multipleSOCustRef
	 * @param Map soCustomRefMap
	 * @return RoutingRuleHdr
	 */
    @Transactional(propagation=Propagation.SUPPORTS)
    private RoutingRuleHdr getRoutingRuleIds(ServiceOrder so,Integer routingRuleBuyerAssocId,String marketId,String sortOrder,String jobcode,
    		List<RoutingRuleHdr> routingRuleHdrs,boolean multipleSOCustRef,Map<String, String> soCustomRefMap, boolean useExistingCode) 
    throws Exception {
    	RoutingRuleHdr finalRoutingRuleHdr = null;
    	List<ServiceOrderCustomRefVO> customRefList = new ArrayList<ServiceOrderCustomRefVO>();
    	List<String> mandatoryCustomRefsforBuyer = routingRuleBuyerAssocDao.findMandatoryCustomRefs(so.getBuyer().getBuyerId());
    	for(ServiceOrderCustomRefVO serviceOrderCustomRefVO:so.getCustomRefs()) {
    		if(!mandatoryCustomRefsforBuyer.isEmpty()){
    		for(String mandatoyCustomRefName:mandatoryCustomRefsforBuyer){
    			if(mandatoyCustomRefName.equals(serviceOrderCustomRefVO.getRefType())){
    				customRefList.add(serviceOrderCustomRefVO);
    			}
    		}
    	}
    		else{
    			customRefList.add(serviceOrderCustomRefVO);
    		}
    	}
    	//Step 4
    	List<RoutingRuleHdr> routingRuleHdrsList = new ArrayList<RoutingRuleHdr>();
    	logger.info("Value of useExistingCode in OrderProcessingServiceImpl::: "+useExistingCode);
    	if(useExistingCode){
    		routingRuleHdrsList = routingRuleBuyerAssocDao.findRoutingRuleHdrIdList(routingRuleBuyerAssocId,jobcode,so.getServiceLocation().getZip(),marketId,customRefList,sortOrder);
    		logger.info("Size of routingRuleHdrsList in OrderProcessingServiceImpl::: "+routingRuleHdrsList.size());
    	}else{
    		routingRuleHdrsList = routingRuleBuyerAssocDao.findRoutingRuleHdrIdListRefactored(routingRuleBuyerAssocId,jobcode,so.getServiceLocation().getZip(),marketId,customRefList,sortOrder);
    		logger.info("Size of routingRuleHdrsList in OrderProcessingServiceImpl::: "+routingRuleHdrsList.size());
    	}
    	
    	if(routingRuleHdrs.size()!=0){
    		routingRuleHdrsList.removeAll(routingRuleHdrs);
    	}
    	int matchingCustomRefs=0;
    	List<RoutingRuleHdr> onlyJoborZiporCustrefList= new ArrayList<RoutingRuleHdr>();
    	RoutingRuleHdr bestMatchedRule = null;	
    	for(RoutingRuleHdr routingRuleHdr:routingRuleHdrsList){
    		boolean isOnlyZIP = true;
    		boolean isCustRefSatisfied = false;          	
    		boolean isZipSatisfied = false;
    		boolean hasJobcodes= false;
    		boolean isEligibleRule=true;
    		List<RoutingRulePrice> routingRuleJobCodeList = routingRuleHdr.getRoutingRulePrice();
    		List<RoutingRuleCriteria> routingRuleCriteriaList = routingRuleHdr.getRoutingRuleCriteria();
    		Set<String> ruleCustRefList = new HashSet<String>();
        	Set<String> matchedRuleCustRefList = new HashSet<String>();  
    		if(!(routingRuleJobCodeList.isEmpty())){
    			hasJobcodes=true;
    		}
    		Integer criteriaCount= routingRuleCriteriaList.size();
    		int customRefsCount = 0;	
    		//Step5
    		for(RoutingRuleCriteria routingRuleCriteria:routingRuleCriteriaList) {
    			if(routingRuleCriteria.getCriteriaName().equals("ZIP")||routingRuleCriteria.getCriteriaName().equalsIgnoreCase("MARKET")) {
    				if(hasJobcodes){
    					isEligibleRule=false;
    					break; //Since this rule has both job code and zip code this is not eligible
    				}
    				if(so.getServiceLocation().getZip().equals(routingRuleCriteria.getCriteriaValue())){
    					isZipSatisfied = true;
    				}
    			}else{
    				isOnlyZIP = false;
    				customRefsCount++;
    				if(ruleCustRefList.isEmpty() || matchedRuleCustRefList.isEmpty() || !(matchedRuleCustRefList.contains(routingRuleCriteria.getCriteriaName()))){
    					ruleCustRefList.add(routingRuleCriteria.getCriteriaName());
    					if(multipleSOCustRef){
    						for(ServiceOrderCustomRefVO serviceOrderCustomRefVO:so.getCustomRefs()) {
            					if((routingRuleCriteria.getCriteriaName().equals(serviceOrderCustomRefVO.getRefType()) &&
            							(routingRuleCriteria.getCriteriaValue().equals(serviceOrderCustomRefVO.getRefValue())))) {			
            						matchedRuleCustRefList.add(routingRuleCriteria.getCriteriaName());    						
            						break;
            					}
            				}
    					}else{
    						if(!(soCustomRefMap.isEmpty()) && soCustomRefMap.containsKey(routingRuleCriteria.getCriteriaName())){
        						if(soCustomRefMap.get(routingRuleCriteria.getCriteriaName()).equalsIgnoreCase(routingRuleCriteria.getCriteriaValue())){
        							matchedRuleCustRefList.add(routingRuleCriteria.getCriteriaName());
        						}
        					}else{
        						isEligibleRule=false;
        						break;//Rule criteria is not present in SO custom reference itself. So rule is not a match
        					}
    					}    					 
    				}	       			
    				    			
    			}  
    		}
    		if(isEligibleRule && customRefsCount>0 && ruleCustRefList.size() == matchedRuleCustRefList.size()){
    			isCustRefSatisfied=true;
    		}else if(isEligibleRule && customRefsCount>0 && ruleCustRefList.size() != matchedRuleCustRefList.size()){
    			isEligibleRule=false;
    		}
    		if(isEligibleRule){
    			if(!(hasJobcodes)){
    				if((isOnlyZIP)){
            			onlyJoborZiporCustrefList.add(routingRuleHdr);            		
        			}else if((isCustRefSatisfied) && (isZipSatisfied)){
        				if(matchedRuleCustRefList.size()>matchingCustomRefs){
        					matchingCustomRefs= matchedRuleCustRefList.size();
        					bestMatchedRule = routingRuleHdr;
        				}
        			}else if((isCustRefSatisfied) && (customRefsCount==criteriaCount)){
        				onlyJoborZiporCustrefList.add(routingRuleHdr);
        			}
    			}else{
    				boolean isJobCodeSatisfied = checkJobCodes(routingRuleHdr, so, jobcode);
    				if(isJobCodeSatisfied && routingRuleCriteriaList.size()==0){
    					onlyJoborZiporCustrefList.add(routingRuleHdr);
    				}else if((isCustRefSatisfied) && (customRefsCount==criteriaCount) && (isJobCodeSatisfied)){
        				if(matchedRuleCustRefList.size()>matchingCustomRefs){
        					matchingCustomRefs= matchedRuleCustRefList.size();
        					bestMatchedRule = routingRuleHdr;
        				}
        			}
    			}    
    		}    		
    	}
    	if(null!=bestMatchedRule){
    		finalRoutingRuleHdr =  bestMatchedRule;
    		logger.info("bestMatchedRule as per Step4::: "+bestMatchedRule.getRoutingRuleHdrId());
    	}else if(onlyJoborZiporCustrefList.size()!=0){
    		finalRoutingRuleHdr = onlyJoborZiporCustrefList.get(0);
    		logger.info("onlyJoborZiporCustrefList rule as per Step4::: "+onlyJoborZiporCustrefList.get(0).getRoutingRuleHdrId());
    	}			
    	return finalRoutingRuleHdr;
    }
    /** 
	 * @param routingRuleHdr
	 * @param jobcode
	 * @param ServiceOrder
	 * @return Boolean
	 */
	private Boolean checkJobCodes(RoutingRuleHdr routingRuleHdr, ServiceOrder so, String jobcode) {
		
		List<RoutingRulePrice> routingRulePriceList= routingRuleHdr.getRoutingRulePrice();
		for(RoutingRulePrice price: routingRulePriceList) {
			if(price.getJobcode().equals(jobcode)){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * 
	 * @param routingRuleHdr
	 * @param jobcode
	 * @return true means rule applies, false means rule doesn't apply, null means unknown state
	 */	
	public  Boolean handleJobCode(RoutingRuleHdr routingRuleHdr, ServiceOrder so, String jobcode) {
		List<RoutingRulePrice> prices=routingRuleHdr.getRoutingRulePrice();
		for(RoutingRulePrice price: prices) {
			if(price.getJobcode().equals(jobcode)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
     * return key/value pair of the repriced job codes(aka skus) based on rule id and specialty code
	 * NOTE: only the job codes(aka skus) which have been overridden should be returned
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public Map<String, BigDecimal> applyConditionalPriceOverrides(List<String> itemsToPrice, Integer ruleId, String specialty) throws Exception {
		Map<String, BigDecimal> retVal = new HashMap<String, BigDecimal>();

		
		RoutingRuleHdr rrhdr=routingRuleHdrDao.findByRoutingRuleHdrId(ruleId);
		if(rrhdr == null) {
			return retVal;
		}
		List<RoutingRulePrice> priceList = rrhdr.getRoutingRulePrice();
		for(String jobcode :itemsToPrice) {
			if(jobcode == null) {
				continue;
			}
			for(RoutingRulePrice routingRulePrice: priceList) {
				String jobcodeInPrice = routingRulePrice.getJobcode();
				BigDecimal price = routingRulePrice.getPrice();
				if (jobcode.equals(jobcodeInPrice) && price != null) {
					retVal.put(jobcode, price);
				}
			}
		}

		return retVal;
	}	

	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer findRuleIdForSO(String soId) {
		SORoutingRuleAssoc soRoutingRuleAssoc = soRoutingRuleAssocDao.findBySoId(soId);

		if(soRoutingRuleAssoc == null) {
			return null;
		}
		//Note fix mapping to return the routing rule header instead of the id in the soRoutingRuleAssoc
		// this method needs to return the routingrulehdr.id
		return soRoutingRuleAssoc.getRoutingRuleHdrId();
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void associateRuleIdToSo(Integer ruleId, ServiceOrder so) throws Exception {
		SORoutingRuleAssoc soRoutingRuleAssoc = new SORoutingRuleAssoc();
		soRoutingRuleAssoc.setRoutingRuleHdrId(ruleId);
		soRoutingRuleAssoc.setSoId(so.getSoId());
		soRoutingRuleAssoc.setModifiedBy(RoutingRulesConstants.MODIFIED_BY);
		soRoutingRuleAssocDao.update(soRoutingRuleAssoc);
	}
	
	/**
	 * This method will be used to create routing rule SO association entries
     * @param ruleId
     * @param service order
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)///In case of UPDATE request
	public void updateRoutingRuleId(RoutingRuleHdr ruleHdr, ServiceOrder so) throws Exception {
        	Integer wfStateId = routingRuleBuyerAssocDao.findWfStateIdforSO(so.getSoId());
        	//After SO is being accepted we should not update the so and rule association
	    	if(wfStateId<RoutingRulesConstants.ACCEPTED_STATUS){
	    		SORoutingRuleAssoc oldSoRoutingRuleAssoc = soRoutingRuleAssocDao.findBySoId(so.getSoId());
	    		if(oldSoRoutingRuleAssoc!=null){
	    				soRoutingRuleAssocDao.delete(oldSoRoutingRuleAssoc);
	    		}
	    		if(null!=ruleHdr){
	    			SORoutingRuleAssoc soRoutingRuleAssoc = new SORoutingRuleAssoc();
		    		soRoutingRuleAssoc.setRoutingRuleHdrId(ruleHdr.getRoutingRuleHdrId());
		    		soRoutingRuleAssoc.setSoId(so.getSoId());
		    		soRoutingRuleAssoc.setModifiedBy(RoutingRulesConstants.SYSTEM);
		    		soRoutingRuleAssocDao.update(soRoutingRuleAssoc);
	    		}
	    	}
        	
	}
	
	 //SL 15642 Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
	@Transactional(propagation=Propagation.REQUIRES_NEW)///In case of UPDATE request
	public void setMethodOfAcceptanceOfSo(String soId,String methodOfAccept)
	{
		if(null!=soId)
		{
		soRoutingRuleAssocDao.setMethodOfAcceptanceOfSo(soId,methodOfAccept);
		}
	}
	
	public void setMethodOfRoutingOfSo (String soId,String methodOfRouting)
	{
		if(null!=soId)
		{
		soRoutingRuleAssocDao.setMethodOfAcceptanceOfSo(soId,methodOfRouting);
		}
		
	}
	//End SL 15642 
	
	 //SL 15642 Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
	@Transactional(propagation=Propagation.SUPPORTS)
	public RoutingRuleHdr getProvidersListOfRule(Integer ruleId)
	{
		
		RoutingRuleHdr routingRuleHdr = routingRuleHdrDao.findByRoutingRuleHdrId(ruleId);	
		return routingRuleHdr;
		 
	}
	//End Sl 15642
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getApprovedProviderList(Integer ruleId, Integer spnId, Boolean isNewSpn) {
		if(ruleId == null) {
			throw new RuntimeException("ruleId cannot be equal to null.");
		}

	    RoutingRuleHdr routingRuleHdr = routingRuleHdrDao.findByRoutingRuleHdrId(ruleId);

	    if(routingRuleHdr == null || routingRuleHdr.getRoutingRuleVendor().size() == 0) {
	    	return new ArrayList<Integer>();
	    }

	    if(spnId == null) {
	    	return handleNoSpn(routingRuleHdr);
		}

	    // handle new spn
	    if(isNewSpn != null && isNewSpn.booleanValue()) {
	    	return handleNewSpn(routingRuleHdr, spnId);
	    }

	    // handle old spn
	    return oldSPNNetworkDao.findBySpnIdAndRoutingRuleHdrId(spnId, routingRuleHdr.getRoutingRuleHdrId());
		
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getProviderForAutoAcceptance(Integer eligibleFirmId,Integer spnId)
	{
		List<Integer> retVal = new ArrayList<Integer>();
		//SL-19021 If spn id is null then follow existing functionality to find the eligible service providers considering CAR only
		logger.info("Spn id from getProviderForAutoAcceptance method is"+spnId);
		if(null==spnId)
		{
			logger.info("Inside if condition and spnId with null value");
			List<ServiceProvider> serviceProviders = serviceProviderDao.findByProviderFirmId(eligibleFirmId);
			logger.info("total providers under a provider firm"+serviceProviders.size());
			for(ServiceProvider serviceProvider: serviceProviders) {
				logger.info("getting the provider details under a firm");
			if(serviceProvider.getServiceLiveWorkFlowId().getId() == RoutingRulesConstants.PROVIDER_MARKET_READY.intValue()) {
					logger.info("Provider id under firm"+serviceProvider.getId());
					retVal.add(serviceProvider.getId());
				}
			}
		}
		//SL-19021 If spn id is present then first find the eligible firm and then find the eligible service providers considering CAR and SPN both
		else
		{
			logger.info("Inside else codition and spnId with other than null value");
			boolean providerFirmEligibleInd=fetchEligibleProviderFirm(eligibleFirmId,spnId);
			logger.info("Value of providerFirmEligibleInd"+providerFirmEligibleInd);
			if(providerFirmEligibleInd==true)
			{
				logger.info("Inside providerFirmEligibleInd with true value");
			List<ServiceProvider> results = spnServiceProviderStateDao.listBySpnIdSPNWorkflowStatesAndProviderFirmId(spnId, RoutingRulesConstants.SPN_APPROVED, eligibleFirmId);
			logger.info("Total size of service providers under the eligible provider firm"+results.size());
			for (ServiceProvider s : results) {
				if(!retVal.contains(s.getId())){ // SL-20804 - Avoid duplicate providers in the route list
					retVal.add(s.getId());
				}
			}
			}
		}
		logger.info("Total providers under the firm" +retVal.size());
		return retVal;
		
	}
	private List<Integer> handleNewSpn(RoutingRuleHdr routingRuleHdr, Integer spnId) {
    	List<Integer> retVal = new ArrayList<Integer>();
	    // The current rule is to apply the routed rule independent of it's state
	    // Note: this so was previously routed for this service order.
	    // if(routingRuleHdr.getRuleStatus().equals(RoutingRulesConstants.ROUTING_RULE_STATUS_INACTIVE)) {
	    //	 return retVal;
	    // }

	    //TODO need to verify functionality
    	
    	for(RoutingRuleVendor routingRuleVendor:  routingRuleHdr.getRoutingRuleVendor()) {
			Integer vendorId = routingRuleVendor.getVendor().getId();
			//SL-19021 Reverting the changes made in last release
//			boolean providerFirmEligibleInd=fetchEligibleProviderFirm(vendorId,spnId);
//			if(providerFirmEligibleInd==true)
//			{
			List<ServiceProvider> results = spnServiceProviderStateDao.listBySpnIdSPNWorkflowStatesAndProviderFirmId(spnId, RoutingRulesConstants.SPN_APPROVED, vendorId);
			for (ServiceProvider s : results) {
				retVal.add(s.getId());
			}
//			}
		}
		return retVal;
	}

	private boolean fetchEligibleProviderFirm(Integer vendorId, Integer spnId)
	{
		logger.info("Inside fetchEligibleProviderFirm with spn id and venor id"+vendorId+"and"+spnId);
		boolean providerFirmEligibleInd=spnServiceProviderStateDao.listProviderFirmIdBySpnIdAndSPNWorkflowStates(vendorId,RoutingRulesConstants.PF_SPN_MEMBER,spnId);
		logger.info("Result from listProviderFirmIdBySpnIdAndSPNWorkflowStates"+providerFirmEligibleInd);
		return providerFirmEligibleInd;
	}
	
	
	private List<Integer> handleNoSpn(RoutingRuleHdr routingRuleHdr) {
    	List<Integer> retVal = new ArrayList<Integer>();
		for(RoutingRuleVendor routingRuleVendor:  routingRuleHdr.getRoutingRuleVendor()) {
			ProviderFirm providerFirm = routingRuleVendor.getVendor();
			Integer serviceLiveWfStateId = providerFirm.getServiceLiveWorkFlowState().getId();

			if(serviceLiveWfStateId.intValue() == RoutingRulesConstants.PROVIDER_FIRM_SEARS_APPROVED.intValue() ||
					serviceLiveWfStateId.intValue() == RoutingRulesConstants.PROVIDER_FIRM_SERVICE_LIVE_APPROVED.intValue()) {
				Integer providerFirmId = providerFirm.getId();
				List<ServiceProvider> serviceProviders = serviceProviderDao.findByProviderFirmId(providerFirmId);
				for(ServiceProvider serviceProvider: serviceProviders) {
			
					if(serviceProvider.getServiceLiveWorkFlowId().getId().intValue() == RoutingRulesConstants.PROVIDER_MARKET_READY.intValue()) {
						retVal.add(serviceProvider.getId());
					}
				}
			}
		}
		return retVal;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public String getRoutedRuleName(String soId) {
		RoutingRuleHdr routingRuleHdr = routingRuleHdrDao.findBySoId(soId);
		if(routingRuleHdr == null) {
			return null;
		}

		return routingRuleHdr.getRuleName();
	}

	/**
	 * @param soRoutingRuleAssocDao the soRoutingRuleAssocDao to set
	 */
	public void setSoRoutingRuleAssocDao(SORoutingRuleAssocDao soRoutingRuleAssocDao) {
		this.soRoutingRuleAssocDao = soRoutingRuleAssocDao;
	}

	/**
	 * @param routingRuleHdrDao the routingRuleHdrDao to set
	 */
	public void setRoutingRuleHdrDao(RoutingRuleHdrDao routingRuleHdrDao) {
		this.routingRuleHdrDao = routingRuleHdrDao;
	}

	/**
	 * @param routingRuleBuyerAssocDao the routingRuleBuyerAssocDao to set
	 */
	public void setRoutingRuleBuyerAssocDao(
			RoutingRuleBuyerAssocDao routingRuleBuyerAssocDao) {
		this.routingRuleBuyerAssocDao = routingRuleBuyerAssocDao;
	}

	/**
	 * @param lookupZipMarketDao the lookupZipMarketDao to set
	 */
	public void setLookupZipMarketDao(LookupZipMarketDao lookupZipMarketDao) {
		this.lookupZipMarketDao = lookupZipMarketDao;
	}

	/**
	 * @param oldSPNNetworkDao the oldSPNNetworkDao to set
	 */
	public void setOldSPNNetworkDao(OldSPNNetworkDao oldSPNNetworkDao) {
		this.oldSPNNetworkDao = oldSPNNetworkDao;
	}

	/**
	 * @param spnServiceProviderStateDao the spnServiceProviderStateDao to set
	 */
	public void setSpnServiceProviderStateDao(SPNServiceProviderStateDao spnServiceProviderStateDao) {
		this.spnServiceProviderStateDao = spnServiceProviderStateDao;
	}

	public void setServiceProviderDao(ServiceProviderDao serviceProviderDao) {
		this.serviceProviderDao = serviceProviderDao;
	}
}
