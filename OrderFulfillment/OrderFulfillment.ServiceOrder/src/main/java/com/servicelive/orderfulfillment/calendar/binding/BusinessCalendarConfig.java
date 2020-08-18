package com.servicelive.orderfulfillment.calendar.binding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusinessCalendarConfig {
    BusinessCalendarDayConfig sunday;
    BusinessCalendarDayConfig monday;
    BusinessCalendarDayConfig tuesday;
    BusinessCalendarDayConfig wednesday;
    BusinessCalendarDayConfig thursday;
    BusinessCalendarDayConfig friday;
    BusinessCalendarDayConfig saturday;

    private List<BusinessCalendarHolidayConfig> holidayConfigList = new ArrayList<BusinessCalendarHolidayConfig>();

    public BusinessCalendarDayConfig getDay(int weekDayIdx) {
        switch (weekDayIdx) {
            case Calendar.SUNDAY :
                return sunday;
            case Calendar.MONDAY :
                return monday;
            case Calendar.TUESDAY :
                return tuesday;
            case Calendar.WEDNESDAY :
                return wednesday;
            case Calendar.THURSDAY :
                return thursday;
            case Calendar.FRIDAY :
                return friday;
            case Calendar.SATURDAY :
                return saturday;
        }
        return null;
    }

    public BusinessCalendarDayConfig getSunday() {
        return sunday;
    }

    public void setSunday(BusinessCalendarDayConfig sunday) {
        this.sunday = sunday;
    }

    public BusinessCalendarDayConfig getMonday() {
        return monday;
    }

    public void setMonday(BusinessCalendarDayConfig monday) {
        this.monday = monday;
    }

    public BusinessCalendarDayConfig getTuesday() {
        return tuesday;
    }

    public void setTuesday(BusinessCalendarDayConfig tuesday) {
        this.tuesday = tuesday;
    }

    public BusinessCalendarDayConfig getWednesday() {
        return wednesday;
    }

    public void setWednesday(BusinessCalendarDayConfig wednesday) {
        this.wednesday = wednesday;
    }

    public BusinessCalendarDayConfig getThursday() {
        return thursday;
    }

    public void setThursday(BusinessCalendarDayConfig thursday) {
        this.thursday = thursday;
    }

    public BusinessCalendarDayConfig getFriday() {
        return friday;
    }

    public void setFriday(BusinessCalendarDayConfig friday) {
        this.friday = friday;
    }

    public BusinessCalendarDayConfig getSaturday() {
        return saturday;
    }

    public void setSaturday(BusinessCalendarDayConfig saturday) {
        this.saturday = saturday;
    }

    public List<BusinessCalendarHolidayConfig> getHolidayConfigList() {
        return holidayConfigList;
    }

    public void setHolidayConfigList(List<BusinessCalendarHolidayConfig> holidayConfigList) {
        this.holidayConfigList = holidayConfigList;
    }
}
