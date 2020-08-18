package com.newco.calendarPortal.Services.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.log4j.Logger;

import com.newco.calendarPortal.dao.VendorSlCalendarDao;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.util.RandomGUIDThreadSafe;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;

public class VendorSlCalendarServiceImpl implements VendorSlCalendarService {

	private static final Logger logger = Logger.getLogger(VendorSlCalendarServiceImpl.class);
	RandomGUID randomNo = RandomGUIDThreadSafe.getInstance();
	private VendorSlCalendarDao vendorSlCalendarDao;

	public VendorSlCalendarDao getVendorSlCalendarDao() {
		return vendorSlCalendarDao;
	}

	public void setVendorSlCalendarDao(VendorSlCalendarDao vendorSlCalendarDao) {
		this.vendorSlCalendarDao = vendorSlCalendarDao;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, List<CalendarEventVO>> getCalenderDetail(
			Integer vendorId, Date startDate, Date endDate, String startTime,
			String endTime) throws BusinessServiceException {
		MultiMap multiMap = new MultiValueMap();
		Map<Integer, List<CalendarEventVO>> calendarDetailMap = new HashMap<Integer, List<CalendarEventVO>>();
		try {

			multiMap = vendorSlCalendarDao.getVendorSLCalender(vendorId,
					startDate, endDate, startTime, endTime);

			// get all the set of keys
			Set<Integer> keys = multiMap.keySet();

			for (Integer key : keys) {
				calendarDetailMap.put(key,
						(List<CalendarEventVO>) multiMap.get(key));

			}

		} catch (DataServiceException e) {
			logger.error("Exception in  VendorSlCalendarDao.getCalenderDetail()", e);
			// throw new BusinessServiceException(e);
		}
		return calendarDetailMap;
	}

	
	public List<CalendarEventVO> getProviderCalenderDetail(
			Integer providerId, Date startDate, Date endDate, String startTime,
			String endTime) throws BusinessServiceException {
		//MultiMap multiMap = new MultiValueMap();
		 List<CalendarEventVO> calendarEventVOs = new ArrayList<CalendarEventVO>();
		try {

			logger.info("Provider ID_->" + providerId +"StartDate->"+startDate+ "StartTime->"+startTime+ "EndDate->"+endDate+ "EndTime->"+endTime);
			 calendarEventVOs = vendorSlCalendarDao.fetchProviderCalender(providerId,
					startDate, endDate, startTime, endTime);



		} catch (DataServiceException e) {
			logger.error("Exception in  VendorSlCalendarDao.getCalenderDetail()", e);
			// throw new BusinessServiceException(e);
		}
		return calendarEventVOs;
	}
	
	
	public List<CalendarEventVO> getSlProviderCalenderDetail(
			Integer providerId, Date startDate, Date endDate, String startTime,
			String endTime) throws BusinessServiceException {
		
		List<CalendarEventVO> calendarDetailList = new ArrayList <CalendarEventVO>();
		try {

			calendarDetailList = vendorSlCalendarDao.getSlProviderCalender(providerId,
					startDate, endDate, startTime, endTime);

			

		} catch (DataServiceException e) {
			logger.error("Exception in  VendorSlCalendarDao.getCalenderDetail()", e);
			// throw new BusinessServiceException(e);
		}
		return calendarDetailList;
	}
	
	
	public List<CalendarEventVO> getNonSlProviderCalenderDetail(
			Integer providerId, Date startDate, Date endDate, String startTime,
			String endTime) throws BusinessServiceException {
		
		List<CalendarEventVO> calendarDetailList = new ArrayList <CalendarEventVO>();
		try {

			calendarDetailList = vendorSlCalendarDao.getProviderCalender(providerId,
					startDate, endDate, startTime, endTime);

			

		} catch (DataServiceException e) {
			logger.error("Exception in  VendorSlCalendarDao.getCalenderDetail()", e);
			// throw new BusinessServiceException(e);
		}
		return calendarDetailList;
	}

	public boolean providerCalendarInsertOrUpdate(CalendarEventVO calendarEventVO)
 throws BusinessServiceException {
		boolean result = false;
		try {

			try {
				if(calendarEventVO.getEventId() == null || calendarEventVO.getEventId() == "")
				{
					calendarEventVO.setEventId(randomNo.generateGUID().toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = vendorSlCalendarDao
					.providerCalendarInsertOrUpdate(calendarEventVO);

		} catch (DataServiceException e) {
			logger.error(
					"Exception in  VendorSlCalendarDao.providerCalendarInsert()",
					e);
			// throw new BusinessServiceException(e);
		}
		return result;
	}


	public int providerCalendarUpdate(CalendarEventVO calendarEventVO)
			throws BusinessServiceException {
		int result=0;
		try{
			
		return  vendorSlCalendarDao.providerCalendarUpdate(calendarEventVO);
	} catch (DataServiceException e) {
		logger.error("Exception in  VendorSlCalendarDao.providerCalendarUpdate()", e);
		// throw new BusinessServiceException(e);
	}

		return result;
	}
	
	public int providerCalendarEventDelete(Integer providerId, String eventId) throws BusinessServiceException {
		int result = 0;
		try {

			return vendorSlCalendarDao.providerCalendarEventDelete(providerId,
					eventId);

		} catch (DataServiceException e) {
			logger.error(
					"Exception in  VendorSlCalendarDao.providerCalendarDelete()",
					e);
			// throw new BusinessServiceException(e);
		}
		return result;
	}

	// @Override
	public List<CalendarEventVO> getProviderCalenderDetailForFreeTimeSlot(
			Integer providerId, Date prefStartDateTimeTemp, Date prefEndDateTimeTemp, String prefStartTime24Hr,
			String prefEndTime24Hr) throws BusinessServiceException {

		List<CalendarEventVO> calendarEventVOs = null;
		try {

			logger.info("Provider ID_->" + providerId 
			+ "prefStartDateTimeTemp->" + prefStartDateTimeTemp + "prefStartTime24Hr->" + prefStartTime24Hr 
			+ "prefEndDateTimeTemp->" + prefEndDateTimeTemp + "prefEndTime24Hr->" + prefEndTime24Hr);
			calendarEventVOs = vendorSlCalendarDao.getProviderCalenderDetailForFreeTimeSlot(providerId, prefStartDateTimeTemp, prefEndDateTimeTemp, prefStartTime24Hr, prefEndTime24Hr);

		} catch (DataServiceException e) {
			logger.error("Exception in  VendorSlCalendarDao.getCalenderDetail()", e);
		}
		return calendarEventVOs;
	}
}
