package com.servicelive.orderfulfillment.decision;

import java.util.List;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Apr 15, 2010
 * Time: 5:03:14 PM
 */
public class BuyerAgreeCheck extends AbstractServiceOrderDecision {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5758858102666017230L;
	private IWalletGateway walletGateway;
	private Logger logger = Logger.getLogger(getClass());
    /**
     * the name of the selected outgoing transition
     */
	@SuppressWarnings("unchecked")
    public String decide(OpenExecution execution) {
		
    	ServiceOrder serviceOrder = getServiceOrder(execution);
    	
		double amtToFund = serviceOrder.getTotalSpendLimit().doubleValue();

        double projectBalance = walletGateway.getCurrentSpendingLimit(serviceOrder.getSoId());
        
        if (projectBalance > amtToFund){
            return "decrease";
        }else if (projectBalance <  amtToFund){
            return "increase";
        }
        return "noChangeinPrice";
    }
    
	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}
}
