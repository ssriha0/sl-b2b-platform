package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class ProviderReleasePaymentCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM ProviderReleasePaymentCmd Command ***");
		ServiceOrder serviceOrder = getServiceOrder(processVariables);

        String userName = getUserName(processVariables);
        String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
        String providerState = ProcessVariableUtil.extractProviderState(processVariables);
        double amtToFund = serviceOrder.getTotalSpendLimit().doubleValue();
		serviceOrder.setCancellationFee(new BigDecimal(amtToFund));
		
        if(amtToFund>0)
        {
        WalletResponseVO resp = walletGateway.cancelServiceOrderWithPenalty(userName, serviceOrder, buyerState, providerState);
        String messageId = resp.getMessageId();
        String jmsMessageId = resp.getJmsMessageId();
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
        
        processVariables.put(ProcessVariableUtil.CANCEL_NO_FEE,"penaltyFee");
		logger.debug("finished with ProviderReleasePaymentCmd command");   

        }
        else
        {
        	processVariables.put(ProcessVariableUtil.CANCEL_NO_FEE,"noFee");	
        }
	}

}
