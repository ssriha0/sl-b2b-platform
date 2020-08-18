package com.servicelive.orderfulfillment.group.command;

import java.util.Calendar;
import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * User: Yunus Burhani
 * Date: Apr 14, 2010
 * Time: 1:00:56 PM
 */
public class SetGroupExpirationTimeCmd extends SOGroupCmd {

	@Override
    public void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
        ServiceOrder so = soGroup.getFirstServiceOrder();
        handleServiceOrder(so, processVariables);
    }

    @Override
    public void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
        Calendar serviceDate = Calendar.getInstance();
        if(so.getServiceEndDateTimeCalendar()!=null){
	        serviceDate.setTimeInMillis(so.getServiceEndDateTimeCalendar().getTimeInMillis());
	        String serviceDateAsString = String.format("date=%1$tH:%1$tM %1$tm/%1$td/%1$tY",serviceDate);
	        ProcessVariableUtil.addServiceDate(processVariables, serviceDateAsString);
        }
    }


}
