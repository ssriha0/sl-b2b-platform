package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class RescheduleNullCommand extends SOCommand {

	
	
	@Override
	public void execute(Map<String, Object> processVariables) {
        logger.info("Starting RescheduleNullCommand.execute");
        long startTime = System.currentTimeMillis();
		ServiceOrder so = getServiceOrder(processVariables);
		 so.setReschedule(null);
		 so.setWfSubStatusId(null);
		 
		 //SL-19291 Deleting buyer preferred reschedule details when auto reject reschedule request.
		 so.getSOWorkflowControls().setBuyerSchedule(null);
		 
		 serviceOrderDao.update(so);
	
	}

	
}