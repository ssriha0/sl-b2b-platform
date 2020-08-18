/**
 * 
 */
package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;

/**
 * @author madhup_chand
 *
 */
public class AutoAcceptFeatureCheck extends AbstractServiceOrderDecision
{
	private static final long serialVersionUID = 9146711749919429739L;

	public String decide(OpenExecution openExecution) {
		//Checking the status of auto acceptance status of buyer
		boolean conditionRuleOn=getAutoAcceptFeatureOfBuyer(openExecution);
       if(conditionRuleOn==true)
       {
        return "YES";
       }
        else
        {
        	// If it is OFF then to override AutoRoutingBehaviorCheckForPost decision, setting autoRoutingBehavior as conditional
        	openExecution.setVariable(ProcessVariableUtil.PVKEY_AUTO_ROUTING_BEHAVIOR,"Conditional");
        	return "NO";
        }
	}
}
