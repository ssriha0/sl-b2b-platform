package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;

public class DemoteCmd extends SOCommand {
	
	public void execute(Map<String,Object> processVariables) {
		logger.debug("*** jBPM Demote Command ***");
		String soId = getSoId(processVariables);
		if (soId != null) {
			ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
			if (sop != null && sop.isNewSo()) {
				sop.demoteSo();
				serviceOrderProcessDao.save(sop);
			}
		}
		logger.debug("----------------------------");
	}

}
