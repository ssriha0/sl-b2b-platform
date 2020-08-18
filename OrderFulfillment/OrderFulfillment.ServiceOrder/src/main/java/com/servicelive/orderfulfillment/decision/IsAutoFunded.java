package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * User: Yunus Burhani
 * Date: Apr 5, 2010
 * Time: 11:09:06 AM
 */
public class IsAutoFunded  extends AbstractServiceOrderDecision{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6145737254049968774L;
	private IWalletGateway walletGateway;
    /**
     * the name of the selected outgoing transition
     */
    public String decide(OpenExecution execution) {
        ServiceOrder serviceOrder = getServiceOrder(execution);
        if(null!=serviceOrder.getFundingTypeId())
        {
        	if(serviceOrder.getFundingTypeId().intValue()==40 || serviceOrder.getFundingTypeId().intValue()==90){
        		return "true";
        	}
        	else
        	{
        		return "false";
        	}
        }
        return walletGateway.isBuyerAutoFunded(serviceOrder.getBuyerId()).toString().toLowerCase();
    }

	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}
}
