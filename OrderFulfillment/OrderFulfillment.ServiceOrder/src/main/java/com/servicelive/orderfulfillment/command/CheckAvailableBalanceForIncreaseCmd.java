package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class CheckAvailableBalanceForIncreaseCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder so = getServiceOrder(processVariables);
		Double availableBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
        double projectBalance = walletGateway.getCurrentSpendingLimit(so.getSoId());
		if (availableBalance < (so.getTotalSpendLimit().doubleValue() - projectBalance)){
			throw new ServiceOrderException("Buyer does not have enough money to Increase spend limit");
		}
	}

}
