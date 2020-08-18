package com.servicelive.orderfulfillment.orderprep.processor.assurant;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderScheduleEnhancer;

public class AssurantOrderScheduleEnhancer extends AbstractOrderScheduleEnhancer {

    @Override
    protected DateRange createServiceDateRange(ServiceOrder serviceOrder) {
    	return createDateRangeFromBusinessDayPad(serviceOrder);
    }

    @Override
    protected void setServiceScheduleType(ServiceOrder serviceOrder) {
        serviceOrder.getSchedule().setServiceDateTypeId(SOScheduleType.SINGLEDAY);
    }
}