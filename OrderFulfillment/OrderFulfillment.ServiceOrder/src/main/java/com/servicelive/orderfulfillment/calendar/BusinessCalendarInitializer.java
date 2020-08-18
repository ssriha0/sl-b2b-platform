package com.servicelive.orderfulfillment.calendar;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.calendar.binding.BusinessCalendarConfig;
import com.servicelive.orderfulfillment.calendar.binding.BusinessCalendarDayConfig;
import com.servicelive.orderfulfillment.calendar.binding.BusinessCalendarHolidayConfig;
import com.servicelive.orderfulfillment.common.ResourceLoader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class BusinessCalendarInitializer implements IBusinessCalendarInitializer {
    protected final Logger logger = Logger.getLogger(getClass());

    private String resourcePath;

    public void initialize(BusinessCalendar businessCalendar) {
        businessCalendar.name = resourcePath;
        initializeBusinessCalendarDays(businessCalendar);
        initializeFromXmlCfg(businessCalendar, loadXmlConfig(resourcePath));
    }

    private void initializeBusinessCalendarDays(BusinessCalendar businessCalendar) {
        businessCalendar.businessCalDays = new BusinessCalendarDay[8];
        for (int i=1; i<8; i++) {
            businessCalendar.businessCalDays[i] = new BusinessCalendarDay();
        }
    }

    private String loadXmlConfig(String resourcePath) {
        String xmlCfg = null;
        try {
            xmlCfg = ResourceLoader.loadContentFromResourcePath(resourcePath);
        } catch (IOException e) {
            logger.error("Unable to initialize business calendar.", e);
        }
        return xmlCfg;
    }

    private void initializeFromXmlCfg(BusinessCalendar businessCalendar, String xmlCfg) {
        if (xmlCfg == null) return;
        BusinessCalendarConfig config = createConfigFromXml(xmlCfg);
        initializeBusinessDays(businessCalendar, config);
        initializeHolidays(businessCalendar, config);
    }

    private void initializeBusinessDays(BusinessCalendar businessCalendar, BusinessCalendarConfig config) {
        for (int i=0; i<8; i++) {
            BusinessCalendarDayConfig dayConfig = config.getDay(i);
            if (dayConfig != null) {
                BusinessCalendarDayTimeRange timeRange = new BusinessCalendarDayTimeRange();
                try {
                    timeRange.setStartTimeInMinutes(dayConfig.getFromPartInMinutesFromMidnight());
                    timeRange.setEndTimeInMinutes(dayConfig.getToPartInMinutesFromMidnight());
                    businessCalendar.businessCalDays[i].timeRange = timeRange;
                } catch (ParseException e) {
                    logger.error("Failed to configure business calendar day - " + i, e);
                }
            }
        }
    }

    private void initializeHolidays(BusinessCalendar businessCalendar, BusinessCalendarConfig config) {
        if (config.getHolidayConfigList() == null) return;
        List<HolidayPeriod> holidayPeriodList = new ArrayList<HolidayPeriod>();
        for (BusinessCalendarHolidayConfig holidayConfig : config.getHolidayConfigList()) {
            try {
                HolidayPeriod holidayPeriod = new HolidayPeriod();
                DateRange dateRange = new DateRange(holidayConfig.getFromPart(), holidayConfig.getToPart());
                holidayPeriod.setDateRange(dateRange);
                holidayPeriodList.add(holidayPeriod);
            } catch (ParseException e) {
                logger.error("Failed to configure holiday.", e);
            }
        }
        if (holidayPeriodList.size() > 0) {
            businessCalendar.holidays = holidayPeriodList.toArray(new HolidayPeriod[0]);
        }
    }

    private BusinessCalendarConfig createConfigFromXml(String xmlCfg) {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("business-calendar", BusinessCalendarConfig.class);
        xstream.alias("holiday", BusinessCalendarHolidayConfig.class);
        xstream.aliasAttribute(BusinessCalendarDayConfig.class, "hoursString", "hours");
        xstream.addImplicitCollection(BusinessCalendarConfig.class, "holidayConfigList");
        xstream.aliasAttribute(BusinessCalendarHolidayConfig.class, "periodString", "period");
        return (BusinessCalendarConfig)xstream.fromXML(xmlCfg);
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
}
