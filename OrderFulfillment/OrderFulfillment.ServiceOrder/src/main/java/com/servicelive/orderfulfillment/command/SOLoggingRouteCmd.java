package com.servicelive.orderfulfillment.command;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class SOLoggingRouteCmd extends SOLoggingCmd {

    @Override
    public void execute(Map<String, Object> processVariables){
        Object islogRouting = processVariables.get(OrderfulfillmentConstants.PVKEY_LOG_ROUTING);
        AutoRoutingBehavior behavior = ProcessVariableUtil.getAutoRoutingBehavior(processVariables);
        Integer currentTier = null;
        if(processVariables.containsKey(ProcessVariableUtil.AUTO_ROUTING_TIER)){
            currentTier = (Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER);
        }
		String routingPriorityApplied = (String)processVariables.get(ProcessVariableUtil.ROUTING_PRIORITY_IND);
		String buyerId = (String)processVariables.get(ProcessVariableUtil.BUYER_ID);
		String overFlowTierInd = (String)processVariables.get(ProcessVariableUtil.OVERFLOW_TIER_IND);
		logger.info("islogRouting"+islogRouting);
        //if there is no flag in the process variable log routing
        //or only log routing if the flag is true
        logger.info("AUTO_ROUTING_TIER"+currentTier);
        logger.info("AutoRoutingBehavior"+behavior);
        logger.info("islogRouting"+islogRouting);
        logger.info("buyerId"+buyerId);
        logger.info("routingPriorityApplied"+routingPriorityApplied);
        logger.info("isOfEligible"+overFlowTierInd);
       /* if ((islogRouting == null || BooleanUtils.toBoolean(islogRouting.toString()))
            && ((!behavior.equals(AutoRoutingBehavior.Tier))        
                || (behavior.equals(AutoRoutingBehavior.Tier) && null == currentTier))){
        	super.execute(processVariables);
        }*/
        
        
        if(null!=buyerId && buyerId.equals("1000")){
        	if(null!=routingPriorityApplied && routingPriorityApplied.equals("true")&& null==overFlowTierInd){
            	currentTier = 1;
            }
        	logger.info("currentTier"+currentTier);
        	logger.info("INSIDE IF");
        //replacing old method for sears since unwanted entries are made while order injection
        //logging should be made only if 
        //1. not tier eligible (routingPriorityApplied=false) OR
        //2. tier eilgible order which completed routing (routingPriorityApplied=true && null==currentTier)
        if((null!=routingPriorityApplied && routingPriorityApplied.equals("false"))
        		|| ((null!=routingPriorityApplied && routingPriorityApplied.equals("true")) && null==currentTier)){
        	logger.info("LOGGING COUNT OF SO_ROUTED_PROVIDERS");
        	super.execute(processVariables);
        }}
        else{
        	logger.info("INSIDE ELSE");
        	 if ((islogRouting == null || BooleanUtils.toBoolean(islogRouting.toString()))
        	            && ((!behavior.equals(AutoRoutingBehavior.Tier))        
        	                || (behavior.equals(AutoRoutingBehavior.Tier) && null == currentTier))){
        	        	super.execute(processVariables);
        	        }
        }

        //now remove the flag since in one transition we are only calling this command once
        //so it does not stay for the other transitions
        if (islogRouting != null)
        	processVariables.put(OrderfulfillmentConstants.PVKEY_LOG_ROUTING, null);
    }

    @Override
    protected void addValuesToDataMap(HashMap<String, Object> soLogDataMap, Map<String, Object> processVariables) {
        super.addValuesToDataMap(soLogDataMap, processVariables);
        ServiceOrder serviceOrder = getServiceOrder(processVariables);
        int count = 0;
        logger.info("Inside addValuesToDataMap - before for loop");
        for (RoutedProvider provider : serviceOrder.getRoutedResources()) {
        	logger.info("Inside addValuesToDataMap - Inside for loop");
			if (provider.getProviderResponse() == null) {
				logger.info("Inside addValuesToDataMap - provider.getProviderResponse() : ");
				count++;
				logger.info("A Count  - Count : " + count );
			}
			else if(!(provider.getProviderResponse().equals(ProviderResponseType.REJECTED) || 
					provider.getProviderResponse().equals(ProviderResponseType.RELEASED) ||
					provider.getProviderResponse().equals(ProviderResponseType.RELEASED_BY_FIRM))){
				logger.info("Inside addValuesToDataMap - provider.getProviderResponse() : " + provider.getProviderResponse());
				count++;
				logger.info("B Count  - Count : " + count );
			}
			logger.info("B Inside addValuesToDataMap - count++ : " +  count);
        }
        logger.info("Final Inside addValuesToDataMap - after for loop count : " + count);
        soLogDataMap.put("PROVIDER_LIST_COUNT", count);
        }

    private boolean isProcessWithTierRouteBehavior(Map<String, Object> processVariables) {
        AutoRoutingBehavior autoRoutingBehavior = ProcessVariableUtil.getAutoRoutingBehavior(processVariables);
        return autoRoutingBehavior != null && autoRoutingBehavior == AutoRoutingBehavior.Tier;
    }
}
