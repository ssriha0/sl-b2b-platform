package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class AvailableBalanceCheck extends AbstractServiceOrderDecision {
	
	private IWalletGateway walletGateway;
	/**
	 * 
	 */
	private static final long serialVersionUID = 100102764695148267L;

    public String decide(OpenExecution execution) {
		ServiceOrder so = getServiceOrder(execution);
		double availableBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
		if (availableBalance >= so.getTotalSpendLimit().doubleValue()){
			return "true";
		}
		return "false";
	}

	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}
}
