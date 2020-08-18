package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class AreFundsRequired extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1682602618313501356L;

	public String decide(OpenExecution execution) {
		ServiceOrder so = getServiceOrder(execution);
		
		if (so.getFundingTypeId() == 70) return "false";
		else return "true";
	}

}
