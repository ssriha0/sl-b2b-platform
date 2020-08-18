package com.newco.marketplace.business.businessImpl.calendarportal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.newco.calendarPortal.Services.impl.VendorSlCalendarService;
import com.newco.marketplace.business.iBusiness.calendar.ICalendarPortalBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;
import com.sears.os.business.ABaseBO;

public class CalendarPortalBOImpl extends ABaseBO implements ICalendarPortalBO {
	private static final long serialVersionUID = 1L;
	
	private final DateFormat timeFormatter = new SimpleDateFormat("HH:mm");

	private VendorSlCalendarService calendarService;

	public Map<Integer, List<CalendarEventVO>> getVendorEvents(String vendorIdStr, Date startDate, Date endDate) throws BusinessServiceException {
		Map<Integer, List<CalendarEventVO>> response = null;
		try {
			timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			response = calendarService.getCalenderDetail((Integer) Integer.parseInt(vendorIdStr), new java.sql.Date(startDate.getTime()),
					new java.sql.Date(endDate.getTime()), timeFormatter.format(startDate), timeFormatter.format(endDate));
		} catch (BusinessServiceException excep) {
			logger.error("Error while getVendorEvents of CalendarPortalBOImpl", excep);
			throw new BusinessServiceException("Error while getVendorEvents of CalendarPortalBOImpl", excep);
		} catch (NumberFormatException excep) {
			logger.error("Error while getVendorEvents of D2CProviderPortalBOImpl", excep);
			throw new BusinessServiceException("Error while getVendorEvents of CalendarPortalBOImpl", excep);
		}

		return response;
	}

	public VendorSlCalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(VendorSlCalendarService calendarService) {
		this.calendarService = calendarService;
	}

}
