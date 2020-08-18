package com.servicelive.orderfulfillment.decision;



import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class PayProviderCheck extends AbstractServiceOrderDecision {

	private static final long serialVersionUID = 100102764695148267L;

    public String decide(OpenExecution execution) {
		String payProviderInd = (String) execution.getVariable(OrderfulfillmentConstants.PVKEY_PAY_PROVIDER_IND);
		ServiceOrder serviceOrder = getServiceOrder(execution);
		if(null!= serviceOrder && null!= serviceOrder.getSOWorkflowControls() 
				 &&serviceOrder.getSOWorkflowControls().getNonFundedInd()){
			return OrderfulfillmentConstants.NON_FUNDED_ORDER;
		}else if(null!=payProviderInd && "true".equals(payProviderInd)){		
			return OrderfulfillmentConstants.FEATURE_ON;	
		}
		    return OrderfulfillmentConstants.FEATURE_OFF;
	}
	
}
