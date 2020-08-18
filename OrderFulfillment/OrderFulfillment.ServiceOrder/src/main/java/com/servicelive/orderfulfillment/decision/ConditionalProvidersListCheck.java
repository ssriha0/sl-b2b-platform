package com.servicelive.orderfulfillment.decision;

import java.util.ArrayList;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

public class ConditionalProvidersListCheck extends AbstractServiceOrderDecision {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9146711749919429739L;
	private static final Logger logger = Logger.getLogger(ConditionalProvidersListCheck.class);

	public String decide(OpenExecution execution) {
        ServiceOrder serviceOrder = getServiceOrder(execution);
        
        int count=0; 
        if (serviceOrder.getRoutedResources().size() > 0 || serviceOrder.getTierRoutedResources().size()>0) {
        	
        	for(RoutedProvider provider:serviceOrder.getRoutedResources()){    
        			logger.info(" provider.getProviderResponse() "+provider.getProviderResponse());
        		if(null!=provider.getProviderResponse() && provider.getProviderResponse().equals(ProviderResponseType.REJECTED)){
        			count=count+1;
        		}
    	   	}
          if(count!=0 && count==serviceOrder.getRoutedResources().size() && 
        		  serviceOrder.getSOWorkflowControls().getTierRouteInd() && serviceOrder.getSOWorkflowControls().getCurrentTier()==null){
              return "NoProviders";
 
          }
            return "HasProviders";
        } else {
            return "NoProviders";
        }
    }
}
