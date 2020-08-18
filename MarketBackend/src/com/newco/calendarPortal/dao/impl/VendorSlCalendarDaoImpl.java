package com.newco.calendarPortal.dao.impl;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.newco.calendarPortal.dao.VendorSlCalendarDao;
import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;
import com.newco.marketplace.vo.calendarPortal.CalendarProviderVO;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

public class VendorSlCalendarDaoImpl extends MPBaseDaoImpl implements
		VendorSlCalendarDao {

	private static final Logger logger = Logger
			.getLogger(VendorSlCalendarDaoImpl.class.getName());

	ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>(){

		public SimpleDateFormat get() {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			return df;
		}
	};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MultiMap getVendorSLCalender(Integer vendorId, Date startDate,
			Date endDate, String startTime, String endTime)
			throws DataServiceException {

		List<CalendarEventVO> calendarEventListFinal = new ArrayList<CalendarEventVO>();
		MultiMap multiMap = new MultiValueMap();

		try {

			List<CalendarEventVO> slCalendarList = getSlFirmCalender(vendorId,
					startDate, endDate, startTime, endTime);

			List<CalendarEventVO> calendarList = getFirmCalender(vendorId,
					startDate, endDate, startTime, endTime);

			if (null != slCalendarList && slCalendarList.size() > 0) {

				calendarEventListFinal.addAll(slCalendarList);
			}

			if (null != calendarList && calendarList.size() > 0) {
				calendarEventListFinal.addAll(calendarList);
			}
			
			Map<Integer, CalendarProviderVO> providerMap = new HashMap<Integer, CalendarProviderVO>();
			List<CalendarProviderVO> providerDetails = getVendorResourceDetails(vendorId);
			for (CalendarProviderVO providerVO : providerDetails) {
				providerMap.put(providerVO.getPersonId(), providerVO);
			}

			for (CalendarEventVO calendarEventVO : calendarEventListFinal) {
				calendarEventVO.setPersonDetail(providerMap.get(calendarEventVO.getPersonId()));
				multiMap.put(calendarEventVO.getPersonId(), calendarEventVO);
			}
			
			for (CalendarProviderVO providerVO : providerDetails) {
				if (!multiMap.containsKey(providerVO.getPersonId())) {
					CalendarEventVO calendarVO = new CalendarEventVO();
					calendarVO.setPersonId(providerVO.getPersonId());
					calendarVO.setPersonDetail(providerVO);
					calendarVO.setType("BLANK");
					multiMap.put(providerVO.getPersonId(), calendarVO);
				}
			}

		} catch (Exception e) {
			logger.error("VendorSoCalendarDaoImpl.getVendorSoCalender NoResultException"
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}

		return multiMap;
	}

	@SuppressWarnings("unchecked")
	public List<CalendarProviderVO> getVendorResourceDetails(Integer vendorId) throws DataServiceException {

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("vendorId", vendorId);
			return this.queryForList("calendar.providerDetails", params);
		} catch (Exception e) {
			logger.error("VendorSoCalendarDaoImpl.getVendorResourceDetails NoResultException" + StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CalendarEventVO> fetchProviderCalender(Integer providerId, Date startDate,
			Date endDate, String startTime, String endTime)
			throws DataServiceException {

		List<CalendarEventVO> calendarEventListFinal = new ArrayList<CalendarEventVO>();
		

		try {

			List<CalendarEventVO> slCalendarList = getSlProviderCalender(
					providerId, startDate, endDate, startTime, endTime);

			List<CalendarEventVO> calendarList = getProviderCalender(
					providerId, startDate, endDate, startTime, endTime);

			if (null != slCalendarList && slCalendarList.size() > 0) {

				calendarEventListFinal.addAll(slCalendarList);
			}

			if (null != calendarList && calendarList.size() > 0) {
				calendarEventListFinal.addAll(calendarList);
			}


		} catch (Exception e) {
			logger.error("VendorSoCalendarDaoImpl.fetchProviderCalender NoResultException"
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}

		return calendarEventListFinal;
	}

	public boolean providerCalendarInsertOrUpdate(CalendarEventVO calendarEventVO)
			throws DataServiceException {
		
			try {
				this.insert("calendarEventVO.insertOrUpdate", calendarEventVO);
				// calendarEventVO.setEventId(result);

			} catch (Exception e) {
				logger.error("VendorSoCalendarDaoImpl.getVendorSoCalender NoResultException"
						+ StackTraceHelper.getStackTrace(e));
				throw new DataServiceException("Error", e);
			}


		return true;
	}

	// public void addOrUpdateCalendar(CalendarEventVO calendarEvent) throws
	// DataServiceException {
	// try {
	// Integer eventId=(Integer) queryForObject("calendar.select",
	// calendarEvent);
	// if(eventId==null){
	//
	// insert("calendarEventVO.insert", calendarEvent);
	// }else{
	//
	// update("calendarEventVO.update", calendarEvent);
	// }
	//
	// } catch (Exception ex) {
	// logger.info("[VendorSoCalendarDaoImpl.addOrUpdateCalendar - Exception] "
	// + StackTraceHelper.getStackTrace(ex));
	// throw new DataServiceException("Error", ex);
	// }
	// }

	public int providerCalendarUpdate(CalendarEventVO calendarEventVO)
			throws DataServiceException {

		

			try {
				return this
						.update("calendarEventVO.update", calendarEventVO);
			} catch (Exception e) {
				logger.error("VendorSoCalendarDaoImpl.getVendorSoCalender NoResultException"
						+ StackTraceHelper.getStackTrace(e));
				throw new DataServiceException("Error", e);
			}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<CalendarEventVO> getFirmCalender(Integer vendorId,
			Date startDate, Date endDate, String startTime, String endTime)
			throws DataServiceException {

		List<CalendarEventVO> calendarList = new ArrayList<CalendarEventVO>();

		try {

			
			Map <String,Object> params = new HashMap<String, Object>();
			params.put("vendorId", vendorId);
			params.put("startDate", dateFormatter.get().format(startDate));
			params.put("startTime", startTime);
			params.put("endDate", dateFormatter.get().format(endDate));
			params.put("endTime", endTime);


			calendarList = queryForList("calendar.query", params);

		} catch (Exception e) {
			logger.error("VendorSoCalendarDaoImpl.getVendorSoCalender NoResultException"
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}

		return calendarList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<CalendarEventVO> getSlFirmCalender(Integer vendorId,
			Date startDate, Date endDate, String startTime, String endTime)
			throws DataServiceException {

		List<CalendarEventVO> calendarList = new ArrayList<CalendarEventVO>();

		try {
			
			Map <String,Object> params = new HashMap<String, Object>();
			params.put("vendorId", vendorId);
			params.put("startDate", dateFormatter.get().format(startDate));
			params.put("startTime", startTime);
			params.put("endDate", dateFormatter.get().format(endDate));
			params.put("endTime", endTime);

			calendarList = queryForList("slCalendar.query", params);

		} catch (Exception e) {
			logger.error("VendorSoCalendarDaoImpl.getSlCalender NoResultException"
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}

		return calendarList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CalendarEventVO> getProviderCalender(Integer providerId,
			Date startDate, Date endDate, String startTime, String endTime)
			throws DataServiceException {

		List<CalendarEventVO> calendarList = new ArrayList<CalendarEventVO>();

		try {
			
			Map <String,Object> params = new HashMap<String, Object>();
			params.put("providerId", providerId);
			params.put("startDate", dateFormatter.get().format(startDate));
			params.put("startTime", startTime);
			params.put("endDate", dateFormatter.get().format(endDate));
			params.put("endTime", endTime);

			calendarList = queryForList("calendar.event.query", params);

		} catch (Exception e) {
			logger.error("VendorSoCalendarDaoImpl.getProviderCalender NoResultException"
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}

		return calendarList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CalendarEventVO> getSlProviderCalender(Integer providerId,
			Date startDate, Date endDate, String startTime, String endTime)
			throws DataServiceException {

		List<CalendarEventVO> calendarList = new ArrayList<CalendarEventVO>();

		try {
			
			
			
			Map <String,Object> params = new HashMap<String, Object>();
			params.put("providerId", providerId);
			params.put("startDate", dateFormatter.get().format(startDate));
			params.put("startTime", startTime);
			params.put("endDate", dateFormatter.get().format(endDate));
			params.put("endTime", endTime);

			calendarList = queryForList("slCalendar.event.query", params);

		} catch (Exception e) {
			logger.error("VendorSoCalendarDaoImpl.getSlProviderCalender NoResultException"
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}

		return calendarList;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int providerCalendarEventDelete(Integer providerId, String eventId)throws DataServiceException {
		try {
			HashMap params = new HashMap();
			params.put("providerId", providerId);
			params.put("eventId", eventId);
			
			return this.delete("calendarEventVO.delete", params);
			

		} catch (Exception e) {
			logger.error("VendorSoCalendarDaoImpl.providerCalendarEventDelete NoResultException"
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}


	}

	// @Override
	public List<CalendarEventVO> getProviderCalenderDetailForFreeTimeSlot(
			Integer providerId, Date prefStartDateTimeTemp,
			Date prefEndDateTimeTemp, String prefStartTime24Hr,
			String prefEndTime24Hr) throws DataServiceException {

		List<CalendarEventVO> calendarEventListFinal = null;
		
		Map <String,Object> params = new HashMap<String, Object>();
		params.put("providerId", providerId);
		params.put("startDate", dateFormatter.get().format(prefStartDateTimeTemp));
		params.put("startTime", prefStartTime24Hr);
		params.put("endDate", dateFormatter.get().format(prefEndDateTimeTemp));
		params.put("endTime", prefEndTime24Hr);
		
		System.out.println("=====> params = " + params);
		
		try {
			// getSlProviderCalender
			List<CalendarEventVO> slCalendarList = (List<CalendarEventVO>) queryForList("slCalendar.free.event.query", params);
			
			// getProviderCalender
			List<CalendarEventVO> calendarList = (List<CalendarEventVO>) queryForList("calendar.free.event.query", params);
			
			calendarEventListFinal = new ArrayList<CalendarEventVO>();
			
			if (null != slCalendarList && slCalendarList.size() > 0) {
				calendarEventListFinal.addAll(slCalendarList);
			}

			if (null != calendarList && calendarList.size() > 0) {
				calendarEventListFinal.addAll(calendarList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return calendarEventListFinal;
		
	}
	
}