package com.servicelive.orderfulfillment.group.command;

import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.orderfulfillment.command.util.AutoRouteHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: yburhani
 * Date: Jun 21, 2010
 * Time: 4:34:39 PM
 */
public class SearchProvidersForAutoRouteCmd extends GroupSignalSOCmd {

    AutoRouteHelper autoRouteHelper;

    @Override
    protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
        handleServiceOrder(soGroup.getFirstServiceOrder(), processVariables);
    }

    @Override
    protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
        List<ProviderIdVO> providers = autoRouteHelper.getProviders(so, processVariables);
       
        processVariables.put(ProcessVariableUtil.PROVIDER_LIST, providers);
        //logger.info("Inside SearchProvidersForAutoRouteCmd>>proListSize>>"+providers.size());
        processVariables.put(ProcessVariableUtil.PROVIDER_LIST_SIZE, (providers == null)? 0: providers.size());
    }

    public void setAutoRouteHelper(AutoRouteHelper autoRouteHelper) {
        this.autoRouteHelper = autoRouteHelper;
    }
}
