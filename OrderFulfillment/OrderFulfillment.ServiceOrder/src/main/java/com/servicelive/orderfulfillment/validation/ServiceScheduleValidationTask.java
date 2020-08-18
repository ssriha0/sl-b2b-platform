package com.servicelive.orderfulfillment.validation;

import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ServiceScheduleValidationTask extends AbstractValidationTask {

    public void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder) {
        SOSchedule svcSchedule = serviceOrder.getSchedule();
        util.addWarningsIfTrue(isMissingServiceDates(svcSchedule), validationHolder, ProblemType.MissingServiceDates);

        if (serviceOrder.getSchedule() != null) {
            TimeZone svcTimeZone = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
            Calendar calStart = null;
            if (svcSchedule.getServiceDate1() != null && svcSchedule.getServiceTimeStart() != null) {
                calStart = TimeChangeUtil.getCalTimeFromParts(svcSchedule.getServiceDate1(), svcSchedule.getServiceTimeStart(), svcTimeZone);
            }
            Calendar calEnd = null;
            if (svcSchedule.getServiceDate2() != null && svcSchedule.getServiceTimeEnd() != null) {
                calEnd = TimeChangeUtil.getCalTimeFromParts(svcSchedule.getServiceDate2(), svcSchedule.getServiceTimeEnd(), svcTimeZone);
            }

            validateServiceDates(calStart, calEnd, svcTimeZone, validationHolder);
        }
    }

    private boolean isMissingServiceDates(SOSchedule svcSchedule) {
        return svcSchedule == null
                || (svcSchedule.getServiceTimeStart() != null && svcSchedule.getServiceDate1() == null)
                || (svcSchedule.getServiceTimeEnd() != null && svcSchedule.getServiceDate2() == null)
                || (svcSchedule.getServiceDate1() == null && svcSchedule.getServiceTimeStart() == null
                    && svcSchedule.getServiceDate2() == null && svcSchedule.getServiceTimeEnd() == null);
    }

    private void validateServiceDates(Calendar calStart, Calendar calEnd, TimeZone svcTimeZone, ValidationHolder validationHolder) {
        util.addWarningsIfTrue(endDateBeforeStartDate(calStart, calEnd), validationHolder, ProblemType.InvalidStartEndDates);
        util.addWarningsIfTrue(isStartDatePast(calStart, svcTimeZone), validationHolder, ProblemType.StartDatePast);
    }

    private boolean endDateBeforeStartDate(Calendar calStart, Calendar calEnd) {
        return calStart != null && calEnd != null && calEnd.before(calStart);
    }

    private boolean isStartDatePast(Calendar calStart, TimeZone svcTimeZone) {
        return calStart != null && calStart.before(new GregorianCalendar(svcTimeZone));
    }
}
