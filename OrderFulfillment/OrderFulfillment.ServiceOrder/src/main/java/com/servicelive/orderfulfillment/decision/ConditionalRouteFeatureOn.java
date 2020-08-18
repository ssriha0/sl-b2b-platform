/**
 * 
 */
package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author madhup_chand
 *
 */
public  class ConditionalRouteFeatureOn extends AbstractServiceOrderDecision
{
	private static final long serialVersionUID = 9146711749919429739L;

	public String decide(OpenExecution execution) {
       boolean conditionRuleOn=getCarFeatureOfBuyer(execution);
       if(conditionRuleOn==true)
       {
        return "YES";
       }
        else
        {
        	return "NO";
        }
	}
	
}
