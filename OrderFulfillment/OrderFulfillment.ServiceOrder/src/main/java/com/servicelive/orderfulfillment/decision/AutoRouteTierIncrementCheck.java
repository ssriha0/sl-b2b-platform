package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;

public class AutoRouteTierIncrementCheck extends AbstractServiceOrderDecision {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5950383306718332608L;
	private int MAX_TIER_VALUE = 3;
    public String decide(OpenExecution openExecution) {
        Integer tier = (Integer)openExecution.getVariable(ProcessVariableUtil.AUTO_ROUTING_TIER);
        if (tier == null || tier.intValue() >= MAX_TIER_VALUE) {
            return "stay";
        } else {
            openExecution.setVariable(ProcessVariableUtil.AUTO_ROUTING_TIER, Integer.valueOf(tier + 1));
            return "reroute";
        }
    }
}
