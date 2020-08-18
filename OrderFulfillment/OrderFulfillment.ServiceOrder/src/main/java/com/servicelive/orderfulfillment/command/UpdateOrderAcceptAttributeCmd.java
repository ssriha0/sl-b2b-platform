/**
 * 
 */
package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;

/**
 * @author madhup_chand
 *
 */
public class UpdateOrderAcceptAttributeCmd extends SOCommand {
	
	
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder so = getServiceOrder(processVariables);
		
		String methodOfAcceptance= SOCommandArgHelper.extractStringArg(processVariables, 1);
		logger.info("Inside UpdateOrderAcceptAttributeCmd with method of acceptance as"+methodOfAcceptance);
		if(null!=so)
		{
			serviceOrderDao.setMethodOfAcceptanceOfSo(so.getSoId(),methodOfAcceptance);
		}
		
	}

}
