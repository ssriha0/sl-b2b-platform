package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public abstract class AbstractCancelCmd extends SOCommand {
    @Override
    public void execute(Map<String, Object> processVariables) {
        assertProcessVariablesContainsCancellationParameters(processVariables);
        ServiceOrder serviceOrder = getServiceOrder(processVariables);

        String userName = getUserName(processVariables);
        String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
        String providerState = ProcessVariableUtil.extractProviderState(processVariables);
        cancelServiceOrder(serviceOrder, userName, buyerState, providerState, processVariables);
    }

    void assertProcessVariablesContainsCancellationParameters(Map<String, Object> processVariables) {}

    abstract void cancelServiceOrder(ServiceOrder serviceOrder, String userName, 
    		String buyerState, String providerState, Map<String, Object> processVariables);
}
