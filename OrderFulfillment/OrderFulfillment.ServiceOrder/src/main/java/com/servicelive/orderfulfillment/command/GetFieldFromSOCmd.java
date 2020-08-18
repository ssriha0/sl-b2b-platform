package com.servicelive.orderfulfillment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class GetFieldFromSOCmd extends SOCommand {

	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("inside getFieldFromSOCmd ------------");
		ServiceOrder so = getServiceOrder(processVariables);
		
		String fieldName = (String) processVariables.get(SOCommandArgHelper.resolveArgName(1));
		
		if (fieldName.equals(ProcessVariableUtil.PVKEY_FUNDING_TYPE)){
			processVariables.put(ProcessVariableUtil.PVKEY_FUNDING_TYPE, so.getFundingTypeId());
		}
		logger.debug("leaving getFieldFromSOCmd ------------");
	}

}
