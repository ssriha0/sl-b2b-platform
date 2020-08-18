package com.newco.marketplace.business.iBusiness.calendar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;

public interface ICalendarPortalBO extends Serializable {

	Map<Integer, List<CalendarEventVO>> getVendorEvents(String vendorIdStr, Date startDate, Date endDate) throws BusinessServiceException;

}
