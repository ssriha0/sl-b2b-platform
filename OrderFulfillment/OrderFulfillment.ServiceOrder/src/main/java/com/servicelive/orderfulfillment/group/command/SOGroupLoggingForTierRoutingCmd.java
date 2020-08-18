package com.servicelive.orderfulfillment.group.command;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class SOGroupLoggingForTierRoutingCmd extends SOGroupLoggingCmd {

    protected void logServiceOrderActivity(ServiceOrder so, Map<String, Object> processVariables) {
        AutoRoutingBehavior behavior = ProcessVariableUtil.getAutoRoutingBehavior(processVariables);
        Integer currentTier = null;
        if(processVariables.containsKey(ProcessVariableUtil.AUTO_ROUTING_TIER)){
            currentTier = (Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER);
        }
        if(!behavior.equals(AutoRoutingBehavior.Tier)
                || (behavior.equals(AutoRoutingBehavior.Tier) && null == currentTier)){
            super.logServiceOrderActivity(so, processVariables);
    }
    }
    @Override
    protected void addValuesToDataMap(ServiceOrder so, HashMap<String,Object> soLogDataMap, Map<String, Object> processVariables){
        super.addValuesToDataMap(so, soLogDataMap, processVariables);
        int count = 0;
        logger.info("Inside addValuesToDataMap SOGroupLoggingForTierRoutingCmd - before for loop");
        for (RoutedProvider provider : so.getRoutedResources()) {
        	logger.info("Inside addValuesToDataMap SOGroupLoggingForTierRoutingCmd- Inside for loop");
			if (provider.getProviderResponse() == null) {
				logger.info("Inside addValuesToDataMap SOGroupLoggingForTierRoutingCmd- provider.getProviderResponse() : ");
				count++;
				logger.info("A Count  - Count : " + count );
			}
			else if(!(provider.getProviderResponse().equals(ProviderResponseType.REJECTED) || 
					provider.getProviderResponse().equals(ProviderResponseType.RELEASED) ||
					provider.getProviderResponse().equals(ProviderResponseType.RELEASED_BY_FIRM))){
				logger.info("Inside addValuesToDataMap SOGroupLoggingForTierRoutingCmd- provider.getProviderResponse() : " + provider.getProviderResponse());
				count++;
				logger.info("B Count  - Count : " + count );
			}
        }
        logger.info("Final Count Inside addValuesToDataMap SOGroupLoggingForTierRoutingCmd - Count : " + count );
        soLogDataMap.put("PROVIDER_LIST_COUNT", count);
    }

}
