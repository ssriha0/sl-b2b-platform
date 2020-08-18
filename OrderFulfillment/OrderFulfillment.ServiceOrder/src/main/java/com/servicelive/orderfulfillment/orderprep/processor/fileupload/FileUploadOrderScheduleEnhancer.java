package com.servicelive.orderfulfillment.orderprep.processor.fileupload;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderScheduleEnhancer;

public class FileUploadOrderScheduleEnhancer extends AbstractOrderScheduleEnhancer {

	protected DateRange createServiceDateRange(ServiceOrder serviceOrder) {
		if (serviceOrder.getSchedule().getServiceDate1() == null) {
			validationUtil.addErrors(serviceOrder.getValidationHolder(), ProblemType.MissingServiceDates);
			return null;
		} else {
		if(serviceOrder.isServiceStartDateTimePast()){
				return createDateRangeFromBusinessDayPad(serviceOrder);
		} else {
			return createDateRangeFromSOScheduleWithTimeZone(serviceOrder);
		}
	}
	}
}
