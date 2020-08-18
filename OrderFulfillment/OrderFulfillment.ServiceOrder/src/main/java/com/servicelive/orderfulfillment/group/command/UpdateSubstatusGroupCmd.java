package com.servicelive.orderfulfillment.group.command;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;

import java.util.Map;

/**
 * User: yburhani
 * Date: Jun 21, 2010
 * Time: 4:48:22 PM
 */
public class UpdateSubstatusGroupCmd extends SOGroupCmd {
    @Override
    protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
        for(ServiceOrder so : soGroup.getServiceOrders())
            handleServiceOrder(so, processVariables);
    }

    @Override
    protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
        String subStatus = SOCommandArgHelper.extractStringArg(processVariables, 1);
        LegacySOSubStatus soSubStatus = LegacySOSubStatus.valueOf(subStatus.toUpperCase());
        so.setWfSubStatusId(soSubStatus.getId());
        serviceOrderDao.update(so);        
    }
}
