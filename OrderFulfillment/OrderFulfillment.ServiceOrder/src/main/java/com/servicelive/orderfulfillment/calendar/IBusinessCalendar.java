package com.servicelive.orderfulfillment.calendar;

import java.util.Date;
import java.util.TimeZone;

public interface IBusinessCalendar {
	Date findClosestStartOfBusinessDayFromDate(Date date, TimeZone targetTimeZone);
	Date findClosestEndOfBusinessDayFromDate(Date date, TimeZone targetTimeZone);
	Date addBusinessDaysToDate(Date date, int days, TimeZone targetTimeZone);
}

