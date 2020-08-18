package com.servicelive.orderfulfillment.group.command;

import java.util.Map;

import com.servicelive.orderfulfillment.command.SOCommand;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public abstract class SOGroupCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		if (processVariables.get(ProcessVariableUtil.PVKEY_GROUP_ID) != null){
			logger.info("SOGroupCmd if");
			String groupId = (String) processVariables.get(ProcessVariableUtil.PVKEY_GROUP_ID);
			SOGroup soGroup = serviceOrderDao.getSOGroup(groupId);
			handleGroup(soGroup, processVariables);
		} else {
			logger.info("SOGroupCmd else");
			String soId = null;
			soId = (String) processVariables.get(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS);
			if(null==soId){
			soId = (String) processVariables.get(ProcessVariableUtil.PVKEY_SVC_ORDER_ID);
			}
			ServiceOrder so = serviceOrderDao.getServiceOrder(soId);
			handleServiceOrder(so, processVariables);
		}
	}

	protected abstract void handleGroup(SOGroup soGroup, Map<String, Object> processVariables);
	
	protected abstract void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables);
		
}
