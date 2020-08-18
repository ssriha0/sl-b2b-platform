package com.servicelive.orderfulfillment.decision;

import java.util.Map;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * @author Infosys
 *
 */
public class TransitionForGroupUpdateCheck extends AbstractServiceOrderDecision {
	private static final long serialVersionUID = 100102764695148267L;
	private Logger logger = Logger.getLogger(TransitionForGroupUpdateCheck.class);


    public String decide(OpenExecution execution) {
    	String isUpdate = "false";
		logger.info("InsideTransitionForGroupUpdateCheck"); 
		// to check if grouped order
		if (execution.getVariable(ProcessVariableUtil.PVKEY_GROUP_ID) != null){
			logger.info("Inside TransitionForGroupUpdateCheck : update check"); 
			// to check if grouped order update flow
			if(null != execution.getVariable(ProcessVariableUtil.PVKEY_IS_UPDATE_BATCH_GROUP)){
				logger.info("Inside TransitionForGroupUpdateCheck : update true"); 
				isUpdate = (String)execution.getVariable(ProcessVariableUtil.PVKEY_IS_UPDATE_BATCH_GROUP);
			}
		}
		return isUpdate;
    }
	
}
