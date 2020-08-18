package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class CancelWithNoPenaltyCmd extends AbstractCancelCmd {
    @Override
	void cancelServiceOrder(ServiceOrder serviceOrder, String userName, String buyerState, String providerState, Map<String, Object> processVariables) {
    	
    	double amtToFund = serviceOrder.getTotalSpendLimit().doubleValue();
		double serviceOrderBalance = walletGateway.getCurrentSpendingLimit(serviceOrder.getSoId());
		
		
		double amount = serviceOrderBalance - amtToFund;
		
		if(amount>0)
		{
		WalletResponseVO resp = walletGateway.decreaseSpendLimit(userName, serviceOrder, buyerState, amount);
		String messageId = resp.getMessageId();
		String jmsMessageId = resp.getJmsMessageId();
		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
        
		}
    	
    	
		walletGateway.cancelServiceOrderWithoutPenalty(userName, serviceOrder, buyerState);
	}

    @Override
    void assertProcessVariablesContainsCancellationParameters(Map<String, Object> processVariables) {
        if (!processVariables.containsKey(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT)) {
            throw new ServiceOrderException("Cancellation comment was not provided with cancellation request.");
        }
    }
}