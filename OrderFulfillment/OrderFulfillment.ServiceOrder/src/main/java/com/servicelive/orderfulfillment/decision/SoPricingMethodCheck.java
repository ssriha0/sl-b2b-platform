package com.servicelive.orderfulfillment.decision;



import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
 

public class SoPricingMethodCheck extends AbstractServiceOrderDecision {

	private static final long serialVersionUID = 100102764695148267L;

    public String decide(OpenExecution execution) {
    	String soPricing = "soLevel";
		String soPricingMethod=(String) execution.getVariable(OrderfulfillmentConstants.PVKEY_SO_PRICING_METHOD);
		ServiceOrder so = getServiceOrder(execution);
		
		if(null!=soPricingMethod && "TASK_LEVEL".equals(soPricingMethod)){
			soPricing = "taskLevel";
			return soPricing;
		}
	
		if(null!=so.getSOWorkflowControls() && null!=so.getSOWorkflowControls().getNonFundedInd()
				&&  so.getSOWorkflowControls().getNonFundedInd()){ 
			
			return "nonfunded";
		}
	return soPricing;
	}
	
}
