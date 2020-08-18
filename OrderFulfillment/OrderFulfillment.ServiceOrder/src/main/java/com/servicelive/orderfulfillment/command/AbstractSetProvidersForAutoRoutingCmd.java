package com.servicelive.orderfulfillment.command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.orderfulfillment.command.util.AutoRouteHelper;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import org.apache.log4j.Logger;

public abstract class AbstractSetProvidersForAutoRoutingCmd extends SOCommand {
    protected final Logger logger = Logger.getLogger(getClass());

	AutoRouteHelper autoRouteHelper;

    @Override
    public void execute(Map<String, Object> processVariables) {
    	List<ProviderIdVO> providerIdVOList =new ArrayList<ProviderIdVO>();
        ServiceOrder serviceOrder = getServiceOrder(processVariables);
        String autoPost = (String) processVariables.get(ProcessVariableUtil.AUTO_POST_IND);
        String isRepost = (String) processVariables.get(ProcessVariableUtil.REPOST);
        String perfCriteriaLevel = (String)processVariables.get(ProcessVariableUtil.PERF_CRITERIA_LEVEL);
        Integer provInPrevTiers = (Integer)processVariables.get(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS);
        logger.info("Providers in previous tiers>>"+provInPrevTiers);
        long start = System.currentTimeMillis();
        //Non w2 priority Changes to fetch the provider for repeat Repair Order auto accept
        if(serviceOrder.isCreatedViaAPI() && serviceOrder.isCustomRefPresent() && OFConstants.INHOME_BUYER_LONG.equals(serviceOrder.getBuyerId())){
        	 providerIdVOList = getProvidersForInhomeAutoPost(serviceOrder, processVariables);
        }else{
        	 providerIdVOList = getProvidersForAutoRouting(serviceOrder, processVariables);
        }
        
        long end = System.currentTimeMillis();
        logger.info("Time taken inside AbstractSetProvidersForAutoRoutingCmd after fetching provider list for so::>>"+serviceOrder.getSoId()+"<<time>>"+(end-start));
        Integer tier = (Integer) processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER);
        String isOfEligible = (String)processVariables.get(ProcessVariableUtil.OVERFLOW_ELIGIBLE);
        Integer nextTier = (Integer) processVariables.get(ProcessVariableUtil.NEXT_TIER);
        logProviderSearchResults(providerIdVOList, serviceOrder.getSoId());
        long start1 = System.currentTimeMillis();
        autoRouteHelper.saveRoutedProviders(providerIdVOList, serviceOrder, tier, nextTier, isRepost, autoPost, perfCriteriaLevel, provInPrevTiers);
        //reseting the 4th tier if eligible for marketplace overflow, so that while reposting tier routing is enabled.
        //Reseting here so that correct tier_id is persisted in DB.
       
        if(null!=tier && tier.equals(4) && null!=isOfEligible && isOfEligible.equals("false")){
    		logger.info("AbstractSetProvidersForAutoRoutingCmd>>disabling tier");
    		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, 0);
     		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, 0);
     		Calendar farFutureDate = new GregorianCalendar(2100, Calendar.JANUARY, 1);
            String farFutureDateString = convertToJPDLDueDate(farFutureDate);
            processVariables.put(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, farFutureDateString);
     		ProcessVariableUtil.setFarFutureDateInProcessVariables(processVariables, ProcessVariableUtil.AUTO_TIER_ROUTE_DATE);
    		processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, null);
    	}
        long end1 = System.currentTimeMillis();
        logger.info("Time taken inside AbstractSetProvidersForAutoRoutingCmdfor after saving so::>>"+serviceOrder.getSoId()+"<<time>>"+(end1-start1));
    }

    private void logProviderSearchResults(List<ProviderIdVO> results, String soId) {
        if (logger.isDebugEnabled()){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName() + " getProvidersForAutoRouting search results for " + soId);
        sb.append("\nVendorID, ResourceID");
        if (results != null) {
            for (ProviderIdVO providerIdVO : results) {
                sb.append("\n" + providerIdVO.getVendorId() + ", " + providerIdVO.getResourceId());
            }
        }
            logger.debug(sb.toString());
    }
    }

    protected abstract List<ProviderIdVO> getProvidersForAutoRouting(ServiceOrder serviceOrder, Map<String, Object> processVariables);
    
    protected abstract List<ProviderIdVO> getProvidersForInhomeAutoPost(ServiceOrder serviceOrder, Map<String, Object> processVariables);

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
	public void setAutoRouteHelper(AutoRouteHelper autoRouteHelper) {
		this.autoRouteHelper = autoRouteHelper;
	}
	
	public static String convertToJPDLDueDate(Calendar calendar){
    	return String.format("date=%1$tH:%1$tM %1$tm/%1$td/%1$tY", calendar);
    }
}
