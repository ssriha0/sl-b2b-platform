package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class AddBuyerFundCmdForBid extends SOCommand {
	protected final Logger logger = Logger.getLogger(getClass());
	@Override
	public void execute(Map<String, Object> processVariables) {
		
		ServiceOrder so = getServiceOrder(processVariables);
		String userName = getUserName(processVariables);
		String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
		Double availableBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
		BigDecimal amount = so.getTotalSpendLimitWithPostingFee().subtract(new BigDecimal(availableBalance));
		logger.info("availableBalance: "+availableBalance);
		logger.info("amount: "+ amount);
		if (amount.doubleValue() > 0){
			throw new ServiceOrderException("Your wallet does not have enough funding to cover this new combined maximum");
		}
	}

	}

