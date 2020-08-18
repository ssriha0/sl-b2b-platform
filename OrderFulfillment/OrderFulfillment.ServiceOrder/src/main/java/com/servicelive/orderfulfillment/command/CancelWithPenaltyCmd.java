package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class CancelWithPenaltyCmd extends AbstractCancelCmd {

    @Override
    void cancelServiceOrder(ServiceOrder serviceOrder, String userName, String buyerState, String providerState, Map<String, Object> processVariables) {
    	
    	
    	double amtToFund = serviceOrder.getTotalSpendLimit().doubleValue();
		double serviceOrderBalance = walletGateway.getCurrentSpendingLimit(serviceOrder.getSoId());
		
		
		double amount = serviceOrderBalance - amtToFund;
		
		if(amount>0)
		{
		WalletResponseVO response = walletGateway.decreaseSpendLimit(userName, serviceOrder, buyerState, amount);
		String messageId = response.getMessageId();
		String jmsMessageId = response.getJmsMessageId();
		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
        
		}
    	
    	
        WalletResponseVO resp = walletGateway.cancelServiceOrderWithPenalty(userName, serviceOrder, buyerState, providerState);
        
        String messageId = resp.getMessageId();
		String jmsMessageId = resp.getJmsMessageId();
		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
	    processVariables.put(ProcessVariableUtil.CANCEL_NO_FEE,"penaltyFee");

    }

    @Override
    void assertProcessVariablesContainsCancellationParameters(Map<String, Object> processVariables) {
        if (!processVariables.containsKey(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT)) {
            throw new ServiceOrderException("Cancellation comment was not provided with cancellation request.");
        }
    }
}