package com.servicelive.orderfulfillment.command;

import com.servicelive.orderfulfillment.command.util.TierRouteUtil;

import java.util.Map;

public class DisableTierRouteCmd extends SOCommand {
    TierRouteUtil tierRouteUtil;

    @Override
    public void execute(Map<String, Object> processVariables) {
        tierRouteUtil.disableTierTimer(processVariables);
    }

    public void setTierRouteUtil(TierRouteUtil tierRouteUtil) {
        this.tierRouteUtil = tierRouteUtil;
    }
}
