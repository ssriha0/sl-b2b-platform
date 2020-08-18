package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class VoidCmd extends AbstractCancelCmd {
    @Override
	void cancelServiceOrder(ServiceOrder serviceOrder, String userName, String buyerState, String providerState, Map<String, Object> processVariables) {
		walletGateway.voidServiceOrder(userName, serviceOrder, buyerState);
	}
}
