package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class UpdateProblemTypeCmd extends SOCommand {

	
	
	@Override
	public void execute(Map<String, Object> processVariables) {
        logger.info("Starting UpdateProblemTypeCommand.execute");
		 ServiceOrder so = getServiceOrder(processVariables);
		 SOWorkflowControls soWorkflowControls = so.getSOWorkflowControls();
		 soWorkflowControls.setProblemType(processVariables
				 .get(OrderfulfillmentConstants.PVKEY_PROBLEM_DESC).toString());
		 so.setSOWorkflowControls(soWorkflowControls);
	     serviceOrderDao.update(so);
	    logger.info("Leaving UpdateProblemTypeCommand.execute");
	
	}

	
}