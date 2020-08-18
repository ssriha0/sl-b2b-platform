package com.servicelive.orderfulfillment.decision;



import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class ProviderVerbalAcknowledgmentCheck extends AbstractServiceOrderDecision {

	private static final long serialVersionUID = 100102764695148267L;

    public String decide(OpenExecution execution) {
    	String acknowledged = "false";
		String hasProviderAcknowledgedVerbally=(String) execution.getVariable(OrderfulfillmentConstants.PVKEY_CANCELLATION_PROVIDER_ACKNOWLEDGEMENT_IND);
	
		if(null!= hasProviderAcknowledgedVerbally && "true".equals(hasProviderAcknowledgedVerbally)){
		    acknowledged = "true";
			return acknowledged;	
		}
		return acknowledged;
	}
	
}
