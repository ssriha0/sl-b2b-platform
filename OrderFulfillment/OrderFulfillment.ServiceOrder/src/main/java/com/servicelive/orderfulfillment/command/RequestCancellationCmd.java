package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class RequestCancellationCmd extends SOCommand {
    @Override
    public void execute(Map<String, Object> processVariables) {
        assertProcessVariablesContainsCancellationAmount(processVariables);

        ServiceOrder serviceOrder = getServiceOrder(processVariables);
        serviceOrder.setWfSubStatusId(LegacySOSubStatus.CANCELLATION_REQUEST.getId());
    }

    private void assertProcessVariablesContainsCancellationAmount(Map<String, Object> processVariables) {
        if (!processVariables.containsKey(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT)) {
            throw new ServiceOrderException("Cancellation amount was not specified with cancellation request.");
        }
    }
}