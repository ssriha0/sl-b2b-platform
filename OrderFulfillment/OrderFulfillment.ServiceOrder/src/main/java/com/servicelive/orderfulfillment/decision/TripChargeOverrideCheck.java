package com.servicelive.orderfulfillment.decision;



import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class TripChargeOverrideCheck extends AbstractServiceOrderDecision {

	private static final long serialVersionUID = 100102764695148267L;

    public String decide(OpenExecution execution) {
		String tripChargeOverride=(String) execution.getVariable(OrderfulfillmentConstants.PVKEY_TRIP_CHARGE_OVERRIDE_FEATURE);
		if(null!=tripChargeOverride && tripChargeOverride.equals("true")){
			return OrderfulfillmentConstants.FEATURE_ON;	
		}
		return OrderfulfillmentConstants.FEATURE_OFF;
	}
	
}
