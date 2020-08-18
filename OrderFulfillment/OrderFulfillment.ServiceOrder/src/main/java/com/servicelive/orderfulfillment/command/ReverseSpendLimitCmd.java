package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class ReverseSpendLimitCmd extends AbstractCancelCmd {
    @Override
	void cancelServiceOrder(ServiceOrder serviceOrder, String userName, String buyerState, String providerState, Map<String, Object> processVariables) {
		 walletGateway.cancelServiceOrderWithoutPenalty(userName, serviceOrder, buyerState);
	}

    @Override
    void assertProcessVariablesContainsCancellationParameters(Map<String, Object> processVariables) {
        if (!processVariables.containsKey(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT)) {
            throw new ServiceOrderException("Cancellation comment was not provided with cancellation request.");
        }
    }
}