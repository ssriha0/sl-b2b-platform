package com.servicelive.orderfulfillment.group.command;

import com.servicelive.orderfulfillment.command.util.TierRouteUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yburhani
 * Date: Sep 20, 2010
 * Time: 5:33:13 PM
 */
public class ResetGroupAutoTierRoutingTime extends SOGroupCmd {
    TierRouteUtil tierRouteUtil;

    @Override
    protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
        ServiceOrder so = soGroup.getFirstServiceOrder();
        handleServiceOrder(so, processVariables);
    }

    @Override
    protected void handleServiceOrder(ServiceOrder serviceOrder, Map<String, Object> processVariables) {
        tierRouteUtil.resetTierRoutingForCurrentProcess(processVariables, serviceOrder);
    }

    public void setTierRouteUtil(TierRouteUtil tierRouteUtil) {
        this.tierRouteUtil = tierRouteUtil;
    }
}
