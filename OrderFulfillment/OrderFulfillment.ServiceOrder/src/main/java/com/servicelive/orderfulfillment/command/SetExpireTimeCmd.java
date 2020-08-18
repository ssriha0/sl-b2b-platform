package com.servicelive.orderfulfillment.command;

import java.util.Date;
import java.util.Map;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class SetExpireTimeCmd extends SOCommand {
    private static final long ONE_SECOND = 1000;

    @Override
    public void execute(Map<String, Object> processVariables) {
        ServiceOrder serviceOrder = getServiceOrder(processVariables);
        String procVarString = Long.toString(calcExpireIntervalInMillis(serviceOrder)) + " millis";
        processVariables.put("expireInterval", procVarString);
    }

    private long calcExpireIntervalInMillis(ServiceOrder serviceOrder) {
        Date now = new Date();
        long diffFromNow = serviceOrder.getServiceEndDateTimeCalendar().getTimeInMillis() - now.getTime();
        if (diffFromNow < ONE_SECOND)
            return ONE_SECOND;
        else
            return diffFromNow;
    }

}
