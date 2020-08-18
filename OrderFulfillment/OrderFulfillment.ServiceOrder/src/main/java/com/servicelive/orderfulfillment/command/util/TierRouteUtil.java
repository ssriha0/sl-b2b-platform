package com.servicelive.orderfulfillment.command.util;

import com.servicelive.marketplatform.common.vo.TierReleaseInfoVO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.lookup.TierReleaseInfoLookup;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

import org.apache.log4j.Logger;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TierRouteUtil {
    QuickLookupCollection quickLookupCollection;
    IMarketPlatformRoutingRulesBO marketPlatformRoutingRulesBO;
  

	protected final Logger logger = Logger.getLogger(getClass());

    public static Integer OVERFLOW_TIER = 9999;

    
    public void resetTierRoutingForCurrentProcess(Map<String, Object> processVariables, ServiceOrder serviceOrder){
    	logger.info("resetTierRoutingForCurrentProcess>>soId>>"+serviceOrder.getSoId());
        //remove the key from the process variable and let it restart from beginning
        processVariables.remove(ProcessVariableUtil.AUTO_ROUTING_TIER);
        processVariables.remove(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER);
        processVariables.remove(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS);
        setupTierRoutingForCurrentProcess(processVariables, serviceOrder);
    }
    // reset tier specific variables to enable tier routing from beginning
    public void resetTierRoutingProcess(Map<String, Object> processVariables, ServiceOrder serviceOrder){
    	String repost = (String)processVariables.get(OrderfulfillmentConstants.REPOST_FOR_TIER_FRONTEND);
    	if(null==repost){
    		repost = (String)processVariables.get(ProcessVariableUtil.REPOST);
    	}
    	logger.info("repost indicator while resetTierRoutingProcess"+repost);
    	logger.info("resetTierRoutingProcess>>soId>>"+serviceOrder.getSoId());
    	Integer tierId = (Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER);
    	logger.info("Inside resetTierRoutingProcess>>tierId>>"+tierId);
    	if(null!=repost && repost.equals("true")){
    		logger.info("Resetting..."+tierId);
    	if(null!=tierId){
	        processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, null);
	        processVariables.put(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, null);
	        processVariables.put(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, null);
    	}
    	}else if(null!=tierId){//to prevent clock reset if edited with no change from frontend.
    		logger.info("Inside ELSE>>");
    		Long minutesUntilNextTier = 0L;
    		Date nxtDate = null;
    		TierReleaseInfoLookup tierReleaseInfoLookup = new TierReleaseInfoLookup();
            initializeTierReleaseInfo(tierReleaseInfoLookup);
    		TierReleaseInfoVO currentTierReleaseInfo = tierReleaseInfoLookup.getTierReleaseInfo(serviceOrder.getSpnId(), tierId);
    		Date initialTierRoutedDate = serviceOrder.getSOWorkflowControls().getCurrentTierRoutedDate();
			logger.info("InitialTierRoutedDate:"+initialTierRoutedDate);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(initialTierRoutedDate);
					if(null!= currentTierReleaseInfo.getMinutesUntilNextTier() && currentTierReleaseInfo.getMinutesUntilNextTier().intValue()!=0){
						minutesUntilNextTier = currentTierReleaseInfo.getMinutesUntilNextTier();
					}
					nxtDate = org.apache.commons.lang.time.DateUtils.addMinutes(initialTierRoutedDate, minutesUntilNextTier.intValue());
    		    	calendar.setTime(nxtDate);
    		    	logger.info("Exact time to be routed to nect tier"+nxtDate);
    		    	String toBeRoutedDateString = ProcessVariableUtil.convertToJPDLDueDate(calendar);
					processVariables.put(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, toBeRoutedDateString);
			logger.info("minutesUntilNextTier:"+minutesUntilNextTier.intValue());
			logger.info("AUTO_TIER_ROUTE_DATE"+processVariables.get(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE));
    	}
    }

    public void setupTierRoutingForCurrentProcess(Map<String, Object> processVariables, ServiceOrder serviceOrder) {
        logger.info("Entering TierRouteUtil for soid>>"+serviceOrder.getSoId());
        long start1 = System.currentTimeMillis();
    	if (!isTierRoute(processVariables)) {
            logger.info("disabling tier timer for " + serviceOrder.getSoId());
            disableTierTimer(processVariables);
            return;
        }
        String frontEndPost = (String)processVariables.get(ProcessVariableUtil.POST_FROM_FRONTEND_ACTION);
        String isSaveAndAutoPost = (String)processVariables.get(ProcessVariableUtil.SAVE_AND_AUTOPOST);
        logger.info("Inside TierRouteUtil..>>FRONTEND_POSTING>>"+frontEndPost);
        logger.info("Inside TierRouteUtil..>>SAVE_AND_AUTOPOST>>"+isSaveAndAutoPost);
        Boolean isOverFlowEligible =  true;
        //overflow shouldn't be allowed unless it is order creation through injection/api/save and autopost. Prevent for normal frontend posting
        if(null!=frontEndPost && frontEndPost.equals("true") && null==isSaveAndAutoPost){
        	isOverFlowEligible = false;
        }
        logger.info("Inside TierRouteUtil..>>isOverFlowEligible>>"+isOverFlowEligible);
        Integer spnId = serviceOrder.getSpnId();
        long start = System.currentTimeMillis();
        
        TierReleaseInfoLookup tierReleaseInfoLookup = new TierReleaseInfoLookup();
        initializeTierReleaseInfo(tierReleaseInfoLookup);
        long end = System.currentTimeMillis();
        TierReleaseInfoVO tierReleaseInfo;
        if (isTierNotYetSet(processVariables)) {// starting with tier routing
            logger.info("Getting starting tier for " + serviceOrder.getSoId());
            tierReleaseInfo = tierReleaseInfoLookup.getStartingTier(spnId);
        } else { // already routed through 1 or more tiers
            logger.info("Getting next tier timer for " + serviceOrder.getSoId());
            
            // overriding for release action
          /* try{
                // ovreriding next tier for release scenario.
                if(null!=processVariables.get(ProcessVariableUtil.NEXT_TIER_TO_BE_ROUTED_IS)){
                	logger.info(" overriding tier to be routed");
                    Integer tierToBeRouted = (Integer)processVariables.get(ProcessVariableUtil.NEXT_TIER_TO_BE_ROUTED_IS);
                    logger.info("nextTierToBeRoutedIs is"+ tierToBeRouted);
                    processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, tierToBeRouted);
                    }
                logger.info("AUTO_ROUTING_TIER"+(Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER));

                }
                catch(Exception e){
                	logger.info("Exception in  setting tier route Id"+e);
                }*/
            
            Integer currentTier = (Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER);
            logger.info("currentTier in setupTierRoutingForCurrentProcess"+currentTier);
            tierReleaseInfo = getNextTierReleaseInfo(spnId, currentTier, isOverFlowEligible,tierReleaseInfoLookup);
            processVariables.put(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, serviceOrder.getRoutedResources().size());
        }
        if(null!= tierReleaseInfo && tierReleaseInfo.hasMarketOverflowIndicator() && isOverFlowEligible){ 
        	processVariables.put(ProcessVariableUtil.OVERFLOW_ELIGIBLE, "true");
        }else{
        	processVariables.put(ProcessVariableUtil.OVERFLOW_ELIGIBLE, "false");
        }
        logTierReleaseInfo(tierReleaseInfo, serviceOrder.getSoId());
        setupProcessVariablesForTierRouting(tierReleaseInfo, processVariables,serviceOrder.getSOWorkflowControls());
        long end1 = System.currentTimeMillis();
        logger.info("Inside TierRouteUtil..>>setupTierRoutingForCurrentProcess Time Taken>>"+(end1-start1));

    }

    private void initializeTierReleaseInfo(TierReleaseInfoLookup tierReleaseInfoLookup) {
       long start = System.currentTimeMillis();
    	List<TierReleaseInfoVO> tierReleaseInfoList = marketPlatformRoutingRulesBO.retrieveAllAvailableTierReleaseInfo();

        Map<Integer, SortedMap<Integer, TierReleaseInfoVO>> spnAndTierToTierReleaseInfoMap = new HashMap<Integer, SortedMap<Integer, TierReleaseInfoVO>>();
        Map<Integer, String> spnAndNetworkName = new HashMap<Integer, String>();
        if (tierReleaseInfoList != null) {
            for (TierReleaseInfoVO tierReleaseInfoVO : tierReleaseInfoList) {
                SortedMap<Integer, TierReleaseInfoVO> tierToTierReleaseInfoMap;
                if (spnAndTierToTierReleaseInfoMap.containsKey(tierReleaseInfoVO.getSpnId())) {
                    tierToTierReleaseInfoMap = spnAndTierToTierReleaseInfoMap.get(tierReleaseInfoVO.getSpnId());
                } else {
                    tierToTierReleaseInfoMap = new TreeMap<Integer, TierReleaseInfoVO>();
                    spnAndTierToTierReleaseInfoMap.put(tierReleaseInfoVO.getSpnId(), tierToTierReleaseInfoMap);
                    spnAndNetworkName.put(tierReleaseInfoVO.getSpnId(), tierReleaseInfoVO.getNetworkName());
                }
                tierToTierReleaseInfoMap.put(tierReleaseInfoVO.getCurrentTier(), tierReleaseInfoVO);
            }
        }

        tierReleaseInfoLookup.setSpnAndTierToTierReleaseInfoMap(spnAndTierToTierReleaseInfoMap);
        tierReleaseInfoLookup.setSpnAndNetworkName(spnAndNetworkName);
        tierReleaseInfoLookup.setInitialized(true);
        long end = System.currentTimeMillis();
        logger.info("inside initializeTierReleaseInfo time taken:"+(end-start));

    }

	private void logTierReleaseInfo(TierReleaseInfoVO tierReleaseInfo, String serviceOrderId) {
        if (logger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder("\nSetting up tier routing for " + serviceOrderId);
            if (tierReleaseInfo == null) {
                sb.append("\ntierReleaseInfo is null");
            } else {
                sb.append("\nspnId            : ").append(tierReleaseInfo.getSpnId());
                sb.append("\ncurrentTier      : ").append(tierReleaseInfo.getCurrentTier());
                sb.append("\nnextTier         : ").append(tierReleaseInfo.getNextTier());
                sb.append("\nminutesUntilNext : ").append(tierReleaseInfo.getMinutesUntilNextTier());
            }
            logger.info(sb.toString());
        }
    }

    private boolean isTierRoute(Map<String, Object> processVariables) {
        AutoRoutingBehavior autoRoutingBehavior = ProcessVariableUtil.getAutoRoutingBehavior(processVariables);
        String routingBehaviour = (String)processVariables.get(ProcessVariableUtil.ROUTING_BEHAVIOUR);
        if(autoRoutingBehavior == AutoRoutingBehavior.Tier||(null != routingBehaviour && routingBehaviour.equals("Tier"))){
        	return true;
        }else{
        	return false;
        }
    }

    public void disableTierTimer(Map<String, Object> processVariables) {
    	 logger.info("TierRouteUtil.disableTierTimer");
    	 Integer tierId =(Integer) processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER);
    	 if(null != tierId){
     		//processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, null);
     		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, 0);
     		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, 0);
     	}
    	 Boolean toDraft=(Boolean) processVariables.get(ProcessVariableUtil.TO_DRAFT);
    	 if(null != toDraft && toDraft){
    		 processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, null);
    		 processVariables.put(ProcessVariableUtil.TO_DRAFT, null);
    	 }
        ProcessVariableUtil.setFarFutureDateInProcessVariables(processVariables, ProcessVariableUtil.AUTO_TIER_ROUTE_DATE);
        processVariables.put(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION, null);
    }

    private boolean isTierNotYetSet(Map<String, Object> processVariables) {
    	logger.info("INSIDE TIERROUTEUTIL.isTierNotYetSet Check>>"+(Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER));
    	if(processVariables.containsKey(ProcessVariableUtil.AUTO_ROUTING_TIER) && null != (Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER)){
    		return false;
    	}else{
    		return true;
    	}
    }

    private TierReleaseInfoVO getNextTierReleaseInfo(Integer spnId, Integer currentTier, Boolean isOverFlowEligible, TierReleaseInfoLookup tierReleaseInfoLookup) {

        if(currentTier.equals(OVERFLOW_TIER)) return null;
        if(null != currentTier && null==spnId && isOverFlowEligible){
        	TierReleaseInfoVO overflowTierReleaseInfo = new TierReleaseInfoVO(null, OVERFLOW_TIER);
            return overflowTierReleaseInfo;
        }
        TierReleaseInfoVO currentTierReleaseInfo = tierReleaseInfoLookup.getTierReleaseInfo(spnId, currentTier);
        if (currentTierReleaseInfo.getNextTier() != null) {
            return tierReleaseInfoLookup.getTierReleaseInfo(spnId, currentTierReleaseInfo.getNextTier());
        } else if (currentTierReleaseInfo.hasMarketOverflowIndicator() && isOverFlowEligible) {
            logger.info("Setting overflow tier for next tier.");
            TierReleaseInfoVO overflowTierReleaseInfo = new TierReleaseInfoVO(spnId, OVERFLOW_TIER);
            return overflowTierReleaseInfo;
        }
        return null;
    }

    private void setupProcessVariablesForTierRouting(TierReleaseInfoVO tierReleaseInfo, Map<String, Object> processVariables, SOWorkflowControls soWorkflowControls) {
        if (tierReleaseInfo == null) {
            disableTierTimer(processVariables);
        } else {
        	String ofEligible = (String)processVariables.get(ProcessVariableUtil.OVERFLOW_ELIGIBLE);
        	//initializing the number of providers in current and previous tiers
        	if(null == processVariables.get(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER)){
            	logger.info("PROVIDERS_IN_CURRENT_TIER IS NULL");
            	processVariables.put(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, 0);
            }
        	if(null == processVariables.get(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS)){
            	logger.info("PROVIDERS_IN_PREVIOUS_TIERS IS NULL");
            	processVariables.put(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, 0);
            }
        	Integer noOfProvInCurentTier = (Integer)processVariables.get(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER);
        	
        	logger.info("tierReleaseInfo.getNumberOfProviders()###"+tierReleaseInfo.getNumberOfProviders());
        	/*if(null!=tierReleaseInfo.getNumberOfProviders() && (tierReleaseInfo.getNumberOfProviders()==0 && tierReleaseInfo.getCurrentTier().equals(4))){
        		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, 999999999);// this assignment not required since we are not using db fecthes
        	}*/
        	if(null!=processVariables.get(ProcessVariableUtil.COUNT_OF_PROV_FOR_CURRENT_TIER)){
            	Integer provCountFromReleaseAction=(Integer)processVariables.get(ProcessVariableUtil.COUNT_OF_PROV_FOR_CURRENT_TIER);

        		logger.info("Inside provCountFromReleaseAction"+provCountFromReleaseAction);
        		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, provCountFromReleaseAction);
        		processVariables.put(ProcessVariableUtil.COUNT_OF_PROV_FOR_CURRENT_TIER, null);

        	} 
        	else{
        		logger.info("Inside else"+tierReleaseInfo.getNumberOfProviders());
        		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, tierReleaseInfo.getNumberOfProviders());
        	}
            logger.info("PROVIDERS_IN_CURRENT_TIER"+(Integer)processVariables.get(ProcessVariableUtil.COUNT_OF_PROV_FOR_CURRENT_TIER));
            logger.info("PROVIDERS_IN_PREVIOUS_TIERS"+processVariables.get(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS));
            
            Integer oldVal = (Integer)processVariables.get(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS);
            logger.info("oldVal"+oldVal);
            if (isTierNotYetSet(processVariables)) {
           // Integer noOfProvInPreviousTiers = oldVal + noOfProvInCurentTier;
            processVariables.put(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, 0);
            }
          //setting tier id to process variable
        	processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, tierReleaseInfo.getCurrentTier());
            logger.info("AUTO_ROUTING_TIER"+(Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER));
            
            /* try{
            // ovreriding next tier for release scenario.
            if(null!=processVariables.get(ProcessVariableUtil.NEXT_TIER_TO_BE_ROUTED_IS)){
            	logger.info(" overriding tier to be routed");
                Integer tierToBeRouted = (Integer)processVariables.get(ProcessVariableUtil.NEXT_TIER_TO_BE_ROUTED_IS);
                logger.info("nextTierToBeRoutedIs is"+ tierToBeRouted);
                processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, tierToBeRouted);
        		processVariables.put(ProcessVariableUtil.NEXT_TIER_TO_BE_ROUTED_IS, null);    

                }
            logger.info("AUTO_ROUTING_TIER"+(Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER));

            }
            catch(Exception e){
            	logger.info("Exception in  setting tier route Id"+e);
            }*/
            logger.info("tierReleaseInfo.getCurrentTier()"+tierReleaseInfo.getCurrentTier());
            logger.info("tierReleaseInfo.getNextTier()"+tierReleaseInfo.getNextTier());
            processVariables.put(ProcessVariableUtil.NEXT_TIER, tierReleaseInfo.getNextTier());
            
            // overriding next tier routed date
          /*  logger.info(" overriding next tier routed date1");
            if(null!=processVariables.get(ProcessVariableUtil.NEXT_TIER_DUE_DATE)){
            String nextTierRouteDate = (String)processVariables.get(ProcessVariableUtil.NEXT_TIER_DUE_DATE);
            processVariables.put(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, nextTierRouteDate);
            }
            */
            
            if (tierReleaseInfo.getNextTier() != null || nextTierRequiresOverflow(tierReleaseInfo)) {
            	logger.info("Inside IF::"+tierReleaseInfo.getMinutesUntilNextTier());
                processVariables.put(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, tierReleaseInfo.getMinutesUntilNextTier() + " minutes");
                
                // overriding next tier routed date
                logger.info(" overriding next tier routed date");
                if(null!=processVariables.get(ProcessVariableUtil.NEXT_TIER_DUE_DATE)){
                String nextTierRouteDate = (String)processVariables.get(ProcessVariableUtil.NEXT_TIER_DUE_DATE);
                processVariables.put(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, nextTierRouteDate);
                processVariables.put(ProcessVariableUtil.NEXT_TIER_DUE_DATE,null);

                }

                
            } else if(null!=ofEligible && ofEligible.equals("true")){
            	logger.info("Inside ELSE::"+tierReleaseInfo.getMinutesUntilNextTier()); 
                disableTierTimer(processVariables);
            }
        }
    }

    private boolean nextTierRequiresOverflow(TierReleaseInfoVO tierReleaseInfo) {
        return !tierReleaseInfo.getCurrentTier().equals(OVERFLOW_TIER) && tierReleaseInfo.hasMarketOverflowIndicator();
    }

    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }

	public IMarketPlatformRoutingRulesBO getMarketPlatformRoutingRulesBO() {
		return marketPlatformRoutingRulesBO;
	}

	public void setMarketPlatformRoutingRulesBO(
			IMarketPlatformRoutingRulesBO marketPlatformRoutingRulesBO) {
		this.marketPlatformRoutingRulesBO = marketPlatformRoutingRulesBO;
	}
    
}
