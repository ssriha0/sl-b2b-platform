package com.servicelive.orderfulfillment.command;

import com.servicelive.orderfulfillment.command.util.TierRouteUtil;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yburhani
 * Date: Sep 20, 2010
 * Time: 5:33:13 PM
 */
public class ResetAutoTierRoutingTimeCmd extends SOCommand {
    TierRouteUtil tierRouteUtil;

    @Override
    public void execute(Map<String, Object> processVariables) {
    	tierRouteUtil.resetTierRoutingProcess(processVariables, this.getServiceOrder(processVariables));
    }

    public void setTierRouteUtil(TierRouteUtil tierRouteUtil) {
        this.tierRouteUtil = tierRouteUtil;
    }
}
