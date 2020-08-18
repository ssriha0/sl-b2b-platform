/*
 * Created on Apr 12, 2007
 *
 * Author: dgold1
 * 
 * Revisions:
 * 
 */
package com.sears.os.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {

    public static String DB_TIMESTAMP_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static Log logger = LogFactory.getLog(DateUtils.class);
    
    public static boolean isValidDate(String strDate, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            Date date = sdf.parse(strDate);
            logger.debug ("Date validation in DateUtils" + date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }//isValidDate

    public static String getCurrentDateTimeString() {

        /*
         * * on some JDK, the default TimeZone is wrong * we must set the
         * TimeZone manually!!! * Calendar cal =
         * Calendar.getInstance(TimeZone.getTimeZone("EST"));
         */
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                DB_TIMESTAMP_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(cal.getTime()));
    }//
}
