package com.servicelive.orderfulfillment.group.command;

import com.servicelive.orderfulfillment.command.util.TierRouteUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

import java.util.Map;

public class SetGroupAutoTierRoutingTime extends SOGroupCmd {
    TierRouteUtil tierRouteUtil;

    @Override
    protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
        ServiceOrder so = soGroup.getFirstServiceOrder();
        handleServiceOrder(so, processVariables);
    }

    @Override
    protected void handleServiceOrder(ServiceOrder serviceOrder, Map<String, Object> processVariables) {
        tierRouteUtil.setupTierRoutingForCurrentProcess(processVariables, serviceOrder);
    }

    public void setTierRouteUtil(TierRouteUtil tierRouteUtil) {
        this.tierRouteUtil = tierRouteUtil;
    }
}
