/**
 * 
 */
package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author madhup_chand
 *
 */
public class UpdateOrderRouteAttributeCmd extends SOCommand{

	
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder so = getServiceOrder(processVariables);
		String methodOfRouting=SOCommandArgHelper.extractStringArg(processVariables, 1);
		//SL 18533 Start Car rule name and ID is not displayed in the notes tab for CAR routed orders for HSR buyer
		//Setting the rule name and rule id in process variable once we find a rule
		processVariables.put(ProcessVariableUtil.CONDITIONAL_AUTO_ROUTING_RULE_ID, so.getCondAutoRouteRuleId());
		processVariables.put(ProcessVariableUtil.CONDITIONAL_AUTO_ROUTING_RULE_NAME, so.getCondAutoRouteRuleName());
		//End SL 18533
		logger.info("Entry into UpdateOrderRouteAttributeCmd with so id and  methodOfRouting"+methodOfRouting+so.getSoId());
		if(null!=so)
		{
			serviceOrderDao.setMethodOfRoutingOfSo(so.getSoId(),methodOfRouting);
		}
		
	}

}
