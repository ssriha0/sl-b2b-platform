package com.servicelive.orderfulfillment.orderprep.scheduling;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public interface IScheduleCalculator {
    DateRange calculateServiceDates(DateRange dateRange, ServiceOrder serviceOrder);
}
