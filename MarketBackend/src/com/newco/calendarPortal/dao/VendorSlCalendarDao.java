package com.newco.calendarPortal.dao;


import java.util.Date;
import java.util.List;

import org.apache.commons.collections.MultiMap;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;

public interface VendorSlCalendarDao {

	public MultiMap  getVendorSLCalender (Integer vendorId, Date startDate, Date endDate, String startTime, String endTime)throws DataServiceException; 
	
	public List<CalendarEventVO>  fetchProviderCalender(Integer providerId, Date startDate, Date endDate, String startTime, String endTime)throws DataServiceException; 
	
	public  List<CalendarEventVO>  getSlProviderCalender(Integer providerId, Date startDate, Date endDate, String startTime, String endTime)throws DataServiceException; 
	public  List<CalendarEventVO>  getProviderCalender(Integer providerId, Date startDate, Date endDate, String startTime, String endTime)throws DataServiceException; 
	
	public boolean providerCalendarInsertOrUpdate(CalendarEventVO calendarEventVO)
			throws DataServiceException;
	
	public int providerCalendarUpdate(CalendarEventVO calendarEventVO)
			throws DataServiceException ;

	
	public int providerCalendarEventDelete(Integer providerId, String EventId)throws DataServiceException ;

	public List<CalendarEventVO> getProviderCalenderDetailForFreeTimeSlot(
			Integer providerId, Date prefStartDateTimeTemp,
			Date prefEndDateTimeTemp, String prefStartTime24Hr,
			String prefEndTime24Hr) throws DataServiceException;
		

}
