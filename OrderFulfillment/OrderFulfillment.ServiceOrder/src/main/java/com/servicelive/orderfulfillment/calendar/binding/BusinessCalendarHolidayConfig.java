package com.servicelive.orderfulfillment.calendar.binding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class BusinessCalendarHolidayConfig {
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final char dateSeparator = '-';
    private String periodString;

    public String getPeriodString() {
        return periodString;
    }

    public void setPeriodString(String periodString) {
        this.periodString = periodString;
    }

    public Date getFromPart() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.parse(extractFromPart());
    }

    public Date getToPart() throws ParseException {
        String toPart = extractToPart();
        if (toPart != null) {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            return dateFormat.parse(extractToPart());
        } else {
            return null;
        }
    }

    protected String extractFromPart() throws ParseException {
        if (StringUtils.contains(periodString, dateSeparator)) {
            String[] periodParts = StringUtils.split(periodString, dateSeparator);
            if (periodParts.length != 2) throw new ParseException("error splitting period string", 0);
            return periodParts[0].trim();
        } else {
            return periodString;
        }
    }

    protected String extractToPart() throws ParseException {
        if (StringUtils.contains(periodString, dateSeparator)) {
            String[] periodParts = StringUtils.split(periodString, dateSeparator);
            if (periodParts.length != 2) throw new ParseException("error splitting period string", 0);
            return periodParts[1].trim();
        } else {
            return null;
        }
    }
}
