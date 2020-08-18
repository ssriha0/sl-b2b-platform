package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;

public class IsWalletPostDone extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3575661490460879569L;
	private IWalletGateway walletGateway;
	
	public String decide(OpenExecution execution) {
		ServiceOrder serviceOrder = getServiceOrder(execution);
		ReceiptVO reciept = walletGateway.getBuyerSpendLimitRsrvReceipt(serviceOrder.getBuyerId(), serviceOrder.getSoId());
		return String.valueOf((reciept != null && reciept.getTransactionID() != null));
	}
	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}
}
