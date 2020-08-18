package com.servicelive.orderfulfillment.decision;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

/**
 * User: Mustafa Motiwala Date: Mar 18, 2010 Time: 11:57:35 AM
 */
public class AutoAcceptCheckBuyerReschedule extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(AutoAcceptCheckBuyerReschedule.class);

	private static final long serialVersionUID = -1685517934179070617L;
	public String decide(OpenExecution openExecution) {
		
		String reasonCode="";
		if(openExecution.getVariable(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_REASON_CODE)!=null){
			reasonCode = (String)openExecution.getVariable(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_REASON_CODE);
		}
		logger.info("AUTO ACCEPT RESCHEDULE BUYER REASON"+reasonCode);
		if(null!=reasonCode && reasonCode.equals(" Reason: "+OrderfulfillmentConstants.AUTO_ACCEPT_BUYER_RESCHEDULE)){
		return "true";
		}else{
		return "false";	
		}

	}

}
