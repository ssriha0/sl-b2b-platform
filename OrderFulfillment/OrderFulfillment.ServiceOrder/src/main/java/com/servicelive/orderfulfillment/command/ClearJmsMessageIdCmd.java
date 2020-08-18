package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;

public class ClearJmsMessageIdCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {

		ServiceOrder so = getServiceOrder(processVariables);
		ServiceOrderProcess proc = serviceOrderProcessDao.getServiceOrderProcess(so.getSoId());
		
		proc.setJmsMessageId( null );
		serviceOrderProcessDao.update(proc);
	}
}
