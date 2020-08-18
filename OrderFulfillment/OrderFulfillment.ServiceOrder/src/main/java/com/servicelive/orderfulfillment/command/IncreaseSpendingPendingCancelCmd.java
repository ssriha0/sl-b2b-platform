package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class IncreaseSpendingPendingCancelCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM IncreaseSpendingCmd Command ***");

		ServiceOrder so = getServiceOrder(processVariables);
		String userName = getUserName(processVariables);
		String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
		
		//double buyerBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
		double spendLimiPerSo=40000.00;
		Object maxSpendLimitPerSo = processVariables
		.get(OrderfulfillmentConstants.PVKEY_MAX_SPENDLIMIT_PER_SO);
         if (maxSpendLimitPerSo != null) {

	    spendLimiPerSo= Double.parseDouble(maxSpendLimitPerSo.toString());
        }


		double amtToFund = so.getTotalSpendLimit().doubleValue();
		double serviceOrderBalance = walletGateway.getCurrentSpendingLimit(so.getSoId());
		double amount = amtToFund - serviceOrderBalance;
		// throws an Exception if the transaction Amount exceeds the buyer limit.
		if(amount>spendLimiPerSo)
		{
			throw new ServiceOrderException("Buyer has requested a transaction amount that exceeds the limit");
		}
        WalletResponseVO resp = walletGateway.increaseSpendLimit(userName, so, buyerState, amount);
        String messageId = resp.getMessageId();
        String jmsMessageId = resp.getJmsMessageId();
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
        
		logger.debug("finished with IncreaseSpendingCmd command");
	}

}
