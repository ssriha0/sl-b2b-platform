package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class CheckAvailableBalanceCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder so = getServiceOrder(processVariables);
		Double availableBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
		if (availableBalance < so.getTotalSpendLimitWithPostingFee().doubleValue()){
			throw new ServiceOrderException("Buyer does not have enough money to post an order");
		}
	}

}
