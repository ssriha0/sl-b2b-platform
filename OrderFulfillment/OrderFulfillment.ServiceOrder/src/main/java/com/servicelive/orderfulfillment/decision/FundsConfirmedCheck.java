package com.servicelive.orderfulfillment.decision;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class FundsConfirmedCheck extends AbstractServiceOrderDecision {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(FundsConfirmedCheck.class);
	private IWalletGateway walletGateway;

    public String decide(OpenExecution execution) {
		try{
			String messageId = (String) execution.getVariable(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID);
			logger.info("## --> debug log for message id before wallet call: " + messageId);
			WalletResponseVO response = walletGateway.getWalletMessageResult(messageId);
			logger.info("## --> debug log for walletGateway reply: " + response);
			if(response.getResult() != null){
				logger.info("## --> debug log walletGateway response result is " + response.getResult());
				if (response.getErrorMessages().isEmpty()){
					if (response.getTransactionId() != null) {
						execution.setVariable(ProcessVariableUtil.PVKEY_FUNDS_CONFIRMED_TX_ID, 
								response.getTransactionId().toString());		
					}
					Integer close = (Integer)execution.getVariable(ProcessVariableUtil.AUTO_CLOSE);
			        if (close!=null && close == 1) {
			            return "autoClose";
			        } 
					
					return "Success";
				}else{
					logger.info("## --> debug log walletGateway response error is " + response.getErrorMessages());
					return "Fail";
				}
			}
			else {
				Object cancelNoFee = execution.getVariable(ProcessVariableUtil.CANCEL_NO_FEE);
				logger.info("## --> debug log walletGateway response CANCEL_NO_FEE is " + cancelNoFee);
				
		        if (null != cancelNoFee && "noFee".equals(cancelNoFee.toString())) {
		            return "Success";
		        }
				return "Wait";
			}

		} catch (Exception e){
			//if exception happens we will wait
			//Because the cause of exception could be
			//that Wallet.RemoteService is down
			logger.error("## --> debug log Error happened while checking for the funds transfered successfully", e);
			return "Wait";
		}
	}

	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}
}
