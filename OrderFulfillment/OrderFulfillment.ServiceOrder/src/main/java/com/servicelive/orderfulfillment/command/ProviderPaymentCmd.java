package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import com.servicelive.domain.pendingcancel.PendingCancelHistory;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;


public class ProviderPaymentCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM ProviderReleasePaymentCmd Command ***");
		ServiceOrder serviceOrder = getServiceOrder(processVariables);

        String userName = getUserName(processVariables);
        String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
        String providerState = ProcessVariableUtil.extractProviderState(processVariables);
        Double cancelAmount = 0.0;
        
		if(null != processVariables.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT)){
			cancelAmount = Double.valueOf((String)processVariables.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT));
		} 
		serviceOrder.setCancellationFee(new BigDecimal(cancelAmount));
        WalletResponseVO resp = walletGateway.cancelServiceOrderWithPenalty(userName, serviceOrder, buyerState, providerState);
        String messageId = resp.getMessageId();
        String jmsMessageId = resp.getJmsMessageId();
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
        
		logger.debug("finished with ProviderReleasePaymentCmd command");
	}
}