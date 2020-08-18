package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class DecreaseSpendingCmdFromPostToDraft extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM DecreaseSpendingCmdFromPostToDraft Command ***");
		
		ServiceOrder so = getServiceOrder(processVariables);
		boolean isCurrentlyPosted = false;
		if(null!=processVariables.get(ProcessVariableUtil.IS_CURRENTLY_POSTED)){
			isCurrentlyPosted = (Boolean)processVariables.get(ProcessVariableUtil.IS_CURRENTLY_POSTED);
			logger.info("isCurrentlyPosted val for SOId "+so.getSoId()+" : " +isCurrentlyPosted);
			removeCurrrentlyPostedVariable(processVariables);
		}
		if(isCurrentlyPosted){
			logger.info("DecreaseSpendingCmdFromPostToDraft : Order is in posted/expired status");
			String userName = getUserName(processVariables);
			String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
			
			//double buyerBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
			double amtToFund = so.getTotalSpendLimit().doubleValue();
			double serviceOrderBalance = walletGateway.getCurrentSpendingLimit(so.getSoId());
			
			
				if(serviceOrderBalance>0){
					WalletResponseVO resp = walletGateway.decreaseSpendLimit(userName, so, buyerState, serviceOrderBalance);
					String messageId = resp.getMessageId();
					String jmsMessageId = resp.getJmsMessageId();
					processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
					processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
				}
				
			logger.info("DecreaseSpendingCmdFromPostToDraft : Order is in posted status decrease finishe");
		}
		

		logger.debug("finished with DecreaseSpendingCmdFromPostToDraft command");
	}
	 private void removeCurrrentlyPostedVariable(Map<String, Object> processVariables){
			processVariables.put(ProcessVariableUtil.IS_CURRENTLY_POSTED,false);
	        logger.info("removing iscurrentlyPosted var");
	    }


}
