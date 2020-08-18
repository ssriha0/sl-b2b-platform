package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ServiceOrderException;

public class ValueLinkReconciliationCheckCmd extends SOCommand {
    @Override
    public void execute(Map<String, Object> processVariables) {
        String serviceOrderId = getSoId(processVariables);
        if (!(walletGateway.areAllValueLinkTransactionsReconciled(serviceOrderId))) {
            String errMsg = "ValueLinkReconciliationCheckCmd() error: value link transactions still unreconciled for service order - " + serviceOrderId;
            logger.error(errMsg);
            throw new ServiceOrderException(errMsg);
        }
    }
}
