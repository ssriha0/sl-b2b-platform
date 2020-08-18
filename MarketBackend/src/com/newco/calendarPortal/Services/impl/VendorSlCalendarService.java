package com.newco.calendarPortal.Services.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;

public interface VendorSlCalendarService {

	public Map<Integer, List<CalendarEventVO>> getCalenderDetail(
			Integer vendorId, Date startDate, Date endDate, String startTime,
			String endTime) throws BusinessServiceException;

	public List<CalendarEventVO> getProviderCalenderDetail(
			Integer providerId, Date startDate, Date endDate, String startTime,
			String endTime) throws BusinessServiceException;

	public List<CalendarEventVO> getSlProviderCalenderDetail(
			Integer providerId, Date startDate, Date endDate, String startTime,
			String endTime) throws BusinessServiceException;

	public List<CalendarEventVO> getNonSlProviderCalenderDetail(
			Integer providerId, Date startDate, Date endDate, String startTime,
			String endTime) throws BusinessServiceException;

	public boolean providerCalendarInsertOrUpdate(CalendarEventVO calendarEventVO)
			throws BusinessServiceException;

	public int providerCalendarUpdate(CalendarEventVO calendarEventVO)
			throws BusinessServiceException;
	
	public int providerCalendarEventDelete(Integer providerId, String EventId)
			throws BusinessServiceException;

	public List<CalendarEventVO> getProviderCalenderDetailForFreeTimeSlot(
			Integer providerId, Date prefStartDateTimeTemp, Date prefEndDateTimeTemp, String prefStartTime24Hr,
			String prefEndTime24Hr) throws BusinessServiceException;
}
