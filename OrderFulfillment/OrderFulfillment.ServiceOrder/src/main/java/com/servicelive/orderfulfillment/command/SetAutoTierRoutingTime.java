package com.servicelive.orderfulfillment.command;

import com.servicelive.orderfulfillment.command.util.TierRouteUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

import java.util.Map;

public class SetAutoTierRoutingTime extends SOCommand {
    TierRouteUtil tierRouteUtil;

    @Override
    public void execute(Map<String, Object> processVariables) {
        ServiceOrder serviceOrder = getServiceOrder(processVariables);
        tierRouteUtil.setupTierRoutingForCurrentProcess(processVariables, serviceOrder);
    }

    public void setTierRouteUtil(TierRouteUtil tierRouteUtil) {
        this.tierRouteUtil = tierRouteUtil;
    }
}

