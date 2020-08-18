package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class IncreaseSpendingCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM IncreaseSpendingCmd Command ***");

		ServiceOrder so = getServiceOrder(processVariables);
		String userName = getUserName(processVariables);
		String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
		
		//double buyerBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
		double amtToFund = so.getTotalSpendLimit().doubleValue();
		double serviceOrderBalance = walletGateway.getCurrentSpendingLimit(so.getSoId());
		
		double amount = amtToFund - serviceOrderBalance;
        WalletResponseVO resp = walletGateway.increaseSpendLimit(userName, so, buyerState, amount);
        String messageId = resp.getMessageId();
        String jmsMessageId = resp.getJmsMessageId();
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
        
		logger.debug("finished with IncreaseSpendingCmd command");
	}

}
