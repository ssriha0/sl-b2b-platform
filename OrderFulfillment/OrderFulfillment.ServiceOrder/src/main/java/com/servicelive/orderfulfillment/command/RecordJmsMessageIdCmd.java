package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;

public class RecordJmsMessageIdCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {

		ServiceOrder so = getServiceOrder(processVariables);
		ServiceOrderProcess proc = serviceOrderProcessDao.getServiceOrderProcess(so.getSoId());
		
		String jmsMessageId = (String)processVariables.get(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID);
		proc.setJmsMessageId( jmsMessageId );
		serviceOrderProcessDao.update(proc);
	}
}
