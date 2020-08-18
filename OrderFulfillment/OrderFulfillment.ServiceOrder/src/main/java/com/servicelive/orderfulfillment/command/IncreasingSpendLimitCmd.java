package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class IncreasingSpendLimitCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM IncreaseSpendingCmd Command ***");

		ServiceOrder so = getServiceOrder(processVariables);
		String userName = getUserName(processVariables);
		String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
		double amtToFund = so.getTotalSpendLimit().doubleValue();
		
		
	// throws exception if buyer does not have enough money in case of non auto funded buyers.	
	/*	if(walletGateway.isBuyerAutoFunded(so.getBuyerId()).toString().toLowerCase().equals("false"))
		{
			Double availableBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
	        double projectBalance = walletGateway.getCurrentSpendingLimit(so.getSoId());
			if (availableBalance < (so.getTotalSpendLimit().doubleValue() - projectBalance)){
				throw new ServiceOrderException("Buyer does not have enough money to Increase spend limit");
			}	
		}   */
		
		if(amtToFund>0)
		{
        WalletResponseVO resp = walletGateway.increaseSpendLimit(userName, so, buyerState, amtToFund);
        String messageId = resp.getMessageId();
        String jmsMessageId = resp.getJmsMessageId();
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
        
		logger.debug("finished with IncreaseSpendingCmd command");
		}
	}

}
