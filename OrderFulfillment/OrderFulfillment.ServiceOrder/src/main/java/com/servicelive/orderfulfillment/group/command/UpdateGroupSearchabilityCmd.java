package com.servicelive.orderfulfillment.group.command;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import org.apache.commons.lang.BooleanUtils;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Sep 25, 2010
 * Time: 5:58:45 PM
 */
public class UpdateGroupSearchabilityCmd extends SOGroupCmd {
    @Override
    protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
        for(ServiceOrder so : soGroup.getServiceOrders()){
            handleServiceOrder(so, processVariables);
        }
    }

    @Override
    protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
        ServiceOrderProcess sop = so.getServiceOrderProcess();
        sop.setGroupingSearchable(getGroupSearchability(processVariables));
        serviceOrderProcessDao.update(sop);
    }

    private boolean getGroupSearchability(Map<String, Object> processVariables){
        String searchability = SOCommandArgHelper.extractStringArg(processVariables, 1);
        return BooleanUtils.toBoolean(searchability);
    }
}
