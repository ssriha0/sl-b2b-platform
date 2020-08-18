package com.servicelive.orderfulfillment.command;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class CancellationRequestCmd extends SOCommand {

	
	@Override
	public void execute(Map<String, Object> processVariables) {
        logger.info("Starting CancellationRequestCmd.execute");
        
        ServiceOrder so = getServiceOrder(processVariables);
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        so.setCancellationRequestDate(now);
        logger.info("Finished with CancellationRequestCmd.execute method.");
	}

	
  
}