/**
 * 
 */
package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author Gireesh_Thadayil
 *
 */
public class AvailableBalanceCheckAutoFunded extends AbstractServiceOrderDecision {
    private static final long serialVersionUID = 100102764695148267L;
    
	private IWalletGateway walletGateway;
	public String decide(OpenExecution execution) {
		ServiceOrder so = getServiceOrder(execution);
		double availableBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
		if(null!= so.getFundingTypeId() && 
				(so.getFundingTypeId().intValue() == 40 || so.getFundingTypeId().intValue()==90)){
			return "autofunded";
		}
		else if (availableBalance >= so.getTotalSpendLimit().doubleValue()){
			return "true";
		}
		   return "false";
	
	}
	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}
}
