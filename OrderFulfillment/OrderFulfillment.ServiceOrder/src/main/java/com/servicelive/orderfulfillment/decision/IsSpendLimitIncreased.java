/*
 * this is the decision class code for spend limit increase
 * 
 */


package com.servicelive.orderfulfillment.decision;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;

public class IsSpendLimitIncreased  extends AbstractServiceOrderDecision{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6145737254049968774L;
    /**
     * the name of the selected outgoing transition
     */
    public String decide(OpenExecution execution) {
    	String isIncreased = (String)execution.getVariable(ProcessVariableUtil.IS_SPENDLIMIT_INC);
        if(StringUtils.isNotEmpty(isIncreased)){
        	 return isIncreased ;
        }
        else
        {
        	return "false";
        }
    }
}
