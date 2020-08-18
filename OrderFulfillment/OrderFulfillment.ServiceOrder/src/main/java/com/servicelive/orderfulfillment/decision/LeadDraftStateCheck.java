package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.LeadHdr;;

/**
 * Author: Renjith R Nair
 */
public class LeadDraftStateCheck extends AbstractLeadDecision  {
    
	public String decide(OpenExecution openExecution) {
		//LeadHdr lead = getLeadObject(openExecution);
		return "forward";
    }
}
