package com.servicelive.orderfulfillment.domain.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TimeChangeUtilTest extends Assert {
    private static TimeZone timeZoneGMTm5;
    private static TimeZone timeZoneGMTm9;
    private static Date datePart;
    private static String dateString;
    private static String timeString;
    private static int expectedYear;
    private static int expectedMonth;
    private static int expectedDayOfMonth;
    private static int expectedHourOfDay;
    private static int expectedMin;
    private static int expectedSec;
    private static Calendar expectedCal;

    
    @BeforeClass
    public static void initialize() {
        timeZoneGMTm5 = TimeZone.getTimeZone("GMT-05:00");
        timeZoneGMTm9 = TimeZone.getTimeZone("GMT-09:00");

        dateString = "2000-12-25";
        expectedYear = 2000;
        expectedMonth = Calendar.DECEMBER;
        expectedDayOfMonth = 25;

        timeString = "8:30 PM";
        expectedHourOfDay = 20;
        expectedMin = 30;
        expectedSec = 0;

        expectedCal = new GregorianCalendar(timeZoneGMTm5);
        expectedCal.clear();
        expectedCal.set(expectedYear, expectedMonth, expectedDayOfMonth, expectedHourOfDay, expectedMin, expectedSec);
        datePart = expectedCal.getTime();
    }

    @Test
    public void testExtractYear() {
        int result = TimeChangeUtil.extractYear(dateString);
        assertEquals("extractYear test", expectedYear, result);
    }

    @Test
    public void testExtractMonth() {
        int result = TimeChangeUtil.extractMonth(dateString);
        assertEquals("extractMonth test", expectedMonth+1, result);
    }

    @Test
    public void testExtractDayOfMonth() {
        int result = TimeChangeUtil.extractDayOfMonth(dateString);
        assertEquals("extractDayOfMonth test", expectedDayOfMonth, result);
    }

    @Test
    public void testDetermineHourOfDay() {
        int result = TimeChangeUtil.determineHourOfDay(timeString);
        assertEquals("determineHourOfDay test", expectedHourOfDay, result);
    }

    @Test
    public void testExtractMinutes() {
        int result = TimeChangeUtil.extractMin(timeString);
        assertEquals("extractMin test", expectedMin, result);
    }

    @Test
    public void testExtractSeconds() {
        int result = TimeChangeUtil.extractSec(timeString);
        assertEquals("extractSec test", expectedSec, result);
    }
   
}
