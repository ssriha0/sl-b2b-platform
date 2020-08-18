package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class ReversingSpendLimitCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM ReversingSpendLimitCmd Command ***");
		ServiceOrder serviceOrder = getServiceOrder(processVariables);

        String userName = getUserName(processVariables);
        String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
        
        
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
                
		logger.debug("finished with ReversingSpendLimitCmd command");
	}

}
