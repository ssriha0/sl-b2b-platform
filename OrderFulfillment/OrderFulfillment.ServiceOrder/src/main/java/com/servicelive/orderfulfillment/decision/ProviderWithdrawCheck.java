package com.servicelive.orderfulfillment.decision;

import java.util.List;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Apr 15, 2010
 * Time: 5:03:14 PM
 */
public class ProviderWithdrawCheck extends AbstractServiceOrderDecision {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5758858102666017230L;

	private Logger logger = Logger.getLogger(getClass());
    /**
     * the name of the selected outgoing transition
     */
	@SuppressWarnings("unchecked")
    public String decide(OpenExecution execution) {
		
		String subStatus=(String) execution.getVariable(OrderfulfillmentConstants.PREVIOUS_SUBSTATUS);
		
        return subStatus;
    }
    
	
}
