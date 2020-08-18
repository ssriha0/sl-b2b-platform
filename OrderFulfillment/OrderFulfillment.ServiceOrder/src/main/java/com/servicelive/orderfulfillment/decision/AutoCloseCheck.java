package com.servicelive.orderfulfillment.decision;



import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class AutoCloseCheck extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 100102764695148267L;

    public String decide(OpenExecution execution) {
		ServiceOrder so = getServiceOrder(execution);
		/*
		logic to check  auto close rules are validated should come here
		*/
		
		
		
		
		String autoClosedSo=(String) execution.getVariable(OrderfulfillmentConstants.PVKEY_AUTOCLOSE_SO);
		if(autoClosedSo!=null && autoClosedSo.toString().equals("autoClosed"))
		{
    		execution.setVariable(ProcessVariableUtil.AUTO_CLOSE, Integer.valueOf(1));

			return"true";
			
		}
		
		
		
		return "false";
		
		
		/*if(so.getFinalPriceTotal().intValue()>100)
    	{
    		return "false";
    	}
		else if(so.getFinalPriceTotal().intValue()<50)
		{
			return "cancelled";
		}
    	else
    	{
    		execution.setVariable(ProcessVariableUtil.AUTO_CLOSE, Integer.valueOf(1));
    		return "true";
    	} */
	}
	
}
