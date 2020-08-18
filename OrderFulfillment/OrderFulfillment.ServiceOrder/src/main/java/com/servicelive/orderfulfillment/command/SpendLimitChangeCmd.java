package com.servicelive.orderfulfillment.command;

import java.util.Map;


import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;  
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class SpendLimitChangeCmd extends SOCommand {

	
	public void execute(Map<String, Object> processVariables) {

		ServiceOrder so = getServiceOrder(processVariables);
		ReceiptVO reciept = walletGateway.getBuyerSpendLimitRsrvReceipt(so.getBuyerId(), so.getSoId());
		String value=String.valueOf((reciept != null && reciept.getTransactionID() != null));
		String userName = getUserName(processVariables);
		String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
		if(null!=value && ("true").equals(value))
		{
			//String isUpdate = (String)execution.getVariable(ProcessVariableUtil.ISUPDATE);
        double projectBalance = walletGateway.getCurrentSpendingLimit(so.getSoId());
    	
        if (projectBalance > so.getTotalSpendLimit().doubleValue()){
     
    		
    		//double buyerBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
    		double amtToFund = so.getTotalSpendLimit().doubleValue();
    		//double serviceOrderBalance = walletGateway.getCurrentSpendingLimit(so.getSoId());
    		
    		
    		double amount = projectBalance - amtToFund;
    		
    		WalletResponseVO resp = walletGateway.decreaseSpendLimit(userName, so, buyerState, amount);
    		String messageId = resp.getMessageId();
    		String jmsMessageId = resp.getJmsMessageId();
    		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
    		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);

    		logger.debug("finished with DecreaseSpendingCmd command");
        }else if (projectBalance < so.getTotalSpendLimit().doubleValue()){
    	
    		
    		//double buyerBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
    		double amtToFund = so.getTotalSpendLimit().doubleValue();
    		//double serviceOrderBalance = walletGateway.getCurrentSpendingLimit(so.getSoId());
    		
    		double amount = amtToFund - projectBalance;
            WalletResponseVO response = walletGateway.increaseSpendLimit(userName, so, buyerState, amount);
            String messageId = response.getMessageId();
            String jmsMessageId = response.getJmsMessageId();
            processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
            processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
            
    		logger.debug("finished with IncreaseSpendingCmd command");
        }
		
	}
		else
		{
			WalletResponseVO respVo = walletGateway.postServiceOrder(userName, so, buyerState);
			String messageId = respVo.getMessageId();
			String jmsMessageId = respVo.getJmsMessageId();
			processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
			processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);

			logger.debug("finished with post command");
		}
	
}
}
