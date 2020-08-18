package com.servicelive.orderfulfillment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;

public class MarkOrderProcessFinished extends SOCommand {

	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.info("Finishing service order process for soId " + ProcessVariableUtil.extractServiceOrderId(processVariables));
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(ProcessVariableUtil.extractServiceOrderId(processVariables));
		sop.setFinished(true);
		sop.setUpdatable(false);
		serviceOrderProcessDao.save(sop);
	}

}
