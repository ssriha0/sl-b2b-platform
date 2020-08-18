package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class OrderTypeCheck extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4573656126972882985L;

	public String decide(OpenExecution execution) {
		ServiceOrder so = getServiceOrder(execution);
		
		return so.getPriceModel().name();
		
//		if (so.getTotalSpendLimit().doubleValue() > 0) return "true";
//		else return "false";
	}

}
