package com.servicelive.orderfulfillment.orderprep.scheduling;

import java.util.Calendar;

public interface ICurrentDayResolver {
    Calendar getCurrentCalendarDate(String timeZoneId);
}
