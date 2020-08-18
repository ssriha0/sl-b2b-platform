package com.servicelive.orderfulfillment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;

public class MarkOrderNotUpdatable extends SOCommand {

private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.info("Marking service order " + ProcessVariableUtil.extractServiceOrderId(processVariables) + " as not updatable.");
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(ProcessVariableUtil.extractServiceOrderId(processVariables));
		sop.setUpdatable(false);
		serviceOrderProcessDao.save(sop);
	}

}
