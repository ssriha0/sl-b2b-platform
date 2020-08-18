package com.servicelive.orderfulfillment.command;

import java.util.List;
import java.util.Map;

import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class SetProvidersForInHomeAutoAccept extends AbstractSetProvidersForAutoRoutingCmd {
    
	
	/**@Description : Non W2 priority 2 : Code to fetch eligible providers to filter out using warranty provider
     * @param serviceOrder
     * @param processVariables
     * @return
     */
    protected List<ProviderIdVO> getProvidersForInhomeAutoPost(ServiceOrder serviceOrder, Map<String, Object> processVariables) {
    	return autoRouteHelper.getProvidersForInhomeAutoPost(serviceOrder, processVariables);
    }

	protected List<ProviderIdVO> getProvidersForAutoRouting(ServiceOrder serviceOrder, Map<String, Object> processVariables) {
    	return autoRouteHelper.getProviders(serviceOrder, processVariables);
    }

}
