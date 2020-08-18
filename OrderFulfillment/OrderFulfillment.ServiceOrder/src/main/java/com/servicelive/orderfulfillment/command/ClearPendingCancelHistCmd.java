package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.domain.ServiceOrder;


public class ClearPendingCancelHistCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {		

		ServiceOrder so = getServiceOrder(processVariables);
		String soId = so.getSoId();
		
		serviceOrderDao.clearPendingCancelHist(soId);	
		
	}

}
