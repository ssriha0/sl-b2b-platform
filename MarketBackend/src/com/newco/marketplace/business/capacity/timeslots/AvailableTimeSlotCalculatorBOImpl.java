package com.newco.marketplace.business.capacity.timeslots;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.calendarPortal.Services.impl.VendorSlCalendarService;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.d2cproviderportal.ID2CProviderPortalDao;
import com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;
import com.newco.marketplace.vo.provider.TimeSlotDTO;

public class AvailableTimeSlotCalculatorBOImpl implements IAvailableTimeSlotCalculatorBO {

	private static final Logger logger = Logger.getLogger(AvailableTimeSlotCalculatorBOImpl.class.getName());
	private VendorSlCalendarService vendorSlCalendarService;
	private ID2CProviderPortalDao d2CProviderPortalDao;
	private ProviderSearchDao providerSearchDao;
	private static final String TIME_FORMAT = "HH:mm:ss"; 
	private static final int SLOT_INTERVAL = 30;
		
	public Map<String,List<TimeSlotDTO>> getBlockedCalendarSlots(List<String> providers, Date date) throws BusinessServiceException {
		try {
			Map<String,List<TimeSlotDTO>> bookedCalendars = new HashMap<String, List<TimeSlotDTO>>();
			for(String provider: providers){
				List<CalendarEventVO> calendarEventVOs = vendorSlCalendarService.getProviderCalenderDetail(Integer.valueOf(provider), date,
						date, "00:00:00", "23:59:59");
				List<TimeSlotDTO> calendarBlockedSlots = getTimeSlotList(calendarEventVOs,date);
				bookedCalendars.put(provider, calendarBlockedSlots);				
			}
			return bookedCalendars;
		} catch (NumberFormatException e) {
			logger.error("got exception in getBlockedCalendarSlots of AvailableTimeSlotCalculatorBOImpl: "+e);
			throw new BusinessServiceException(e);
		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			logger.error("got exception in getBlockedCalendarSlots of AvailableTimeSlotCalculatorBOImpl: "+e);
			throw new BusinessServiceException(e);}
	}

	public List<TimeSlotDTO> getAvailableTimeSlots(Date date,
			Map<String, TimeSlotDTO> capacity, Map<String, List<TimeSlotDTO>> calendar)  throws BusinessServiceException{
		int[] timeSlotInBits = new int[24*(60/SLOT_INTERVAL)];

		// Find free slots from capacity.
		Set<String> providers = capacity.keySet();
		for (String prov : providers) {
			TimeSlotDTO cap = capacity.get(prov);
			List<TimeSlotDTO> bookedTimeSlots = calendar.get(prov);
			Collections.sort(bookedTimeSlots,timeSlotComparator);
			
			logger.info("calculating availability for resourceID#"+prov);
			logger.info("capacity#"+cap);
			logger.info("bookedSlots#"+bookedTimeSlots);

			Date earlierEndTime = cap.getStartTime();
			for (TimeSlotDTO slot : bookedTimeSlots) {
				if(slot.getStartTime().compareTo(cap.getEndTime())>=0){
					break;
				}
				// If start time is not same as earlier end date, then time
				// slots between start time and earlier end date are free.
				if (slot.getStartTime().compareTo(earlierEndTime) > 0) {
					updateSlots(timeSlotInBits, earlierEndTime, slot.getStartTime());
				}
				if (slot.getEndTime().compareTo(earlierEndTime) > 0)
				earlierEndTime = slot.getEndTime();
			}

			// Calendar end time to capacity end time is free slot.
			if (earlierEndTime.compareTo(cap.getEndTime()) < 0) {
				updateSlots(timeSlotInBits, earlierEndTime, cap.getEndTime());
			}
		}

		return convertBitsToTimeSlots(date, timeSlotInBits);
	}

	private List<TimeSlotDTO> convertBitsToTimeSlots(Date date,
			int[] timeSlotInBits) {
		List<TimeSlotDTO> timeSlots = new ArrayList<TimeSlotDTO>();

		for (int i = 0; i < timeSlotInBits.length; i++) {
			if (timeSlotInBits[i] == 0) {
				continue;
			}
			int slotinmin = i * SLOT_INTERVAL;
			int hour = slotinmin / 60;
			int min = slotinmin % 60;

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, min);
			cal.set(Calendar.SECOND, 0);
			Date startTime = cal.getTime();
			cal.add(Calendar.MINUTE, SLOT_INTERVAL);
			Date endTime = cal.getTime();
			timeSlots.add(new TimeSlotDTO(startTime, endTime));
		}
		return timeSlots;
	}

	public List<String> getApplicableProvidersForTechTalk(Integer buyerId, String skuId) throws BusinessServiceException{
		List<String> applicableProviders = new ArrayList<String>();
		try{
			HashMap<String, Object> paramData = new HashMap<String, Object>();
			paramData.put("buyerId", buyerId);
			paramData.put("skuId", skuId);
			Integer spnId = d2CProviderPortalDao.getSpnIdBuyerSoTemplateBySkuBuyerId(paramData);
			applicableProviders = providerSearchDao.getApplicableProvidersForTechTalk(spnId);
			return applicableProviders;
		}catch(DataServiceException e){
			logger.error("unable to getApplicableProvidersForTechTalk from DB");
			throw new BusinessServiceException("getApplicableProvidersForTechTalk failed ",e);
		}
	}
	
	public List<String> getValidProvidersForTechTalk(Integer buyerId, String skuId) throws BusinessServiceException{
		List<String> applicableProviders = new ArrayList<String>();
		try{
			HashMap<String, Object> paramData = new HashMap<String, Object>();
			paramData.put("buyerId", buyerId);
			paramData.put("skuId", skuId);
			Integer spnId = d2CProviderPortalDao.getSpnIdBuyerSoTemplateBySkuBuyerId(paramData);
			applicableProviders = providerSearchDao.getValidProvidersForTechTalk(spnId);
			return applicableProviders;
		}catch(DataServiceException e){
			logger.error("unable to getApplicableProvidersForTechTalk from DB");
			throw new BusinessServiceException("getApplicableProvidersForTechTalk failed ",e);
		}
	}

	@SuppressWarnings("deprecation")
	private void updateSlots(int[] freeSlotsArray, Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		int startTimeSlot = (startTime.getHours() * 60 + startTime.getMinutes()) / SLOT_INTERVAL;
		int endTimeSlot = ((endTime.getHours() * 60 + endTime.getMinutes()) / SLOT_INTERVAL) - 1;

		if (startTime.getDate() != endTime.getDate()) {
			for (int i = 0; i <= endTimeSlot; i++) {
				freeSlotsArray[i] = 1;
			}

			for (int i = startTimeSlot; i < freeSlotsArray.length; i++) {
				freeSlotsArray[i] = 1;
			}

		} else {
			for (int i = startTimeSlot; i <= endTimeSlot; i++) {
				freeSlotsArray[i] = 1;
			}
		}
	}

	private List<TimeSlotDTO>  getTimeSlotList(List<CalendarEventVO> calendarEventVOs, Date date) {
		List<TimeSlotDTO> calendarBlockedSlots = new ArrayList<TimeSlotDTO>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for(CalendarEventVO calEventVo : calendarEventVOs){
			if(calEventVo.getStartTime()==null){
				calEventVo.setStartTime("00:00:00");
			}
			if(calEventVo.getEndTime()==null){
				calEventVo.setEndTime("23:59:59");
			}
			Date startDate = DateUtils.getDateFromString(calEventVo.getStartTime(), TIME_FORMAT);
			Date endDate = DateUtils.getDateFromString(calEventVo.getEndTime(), TIME_FORMAT);
			TimeSlotDTO timeSlot = new TimeSlotDTO(startDate,endDate,cal,cal);
			calendarBlockedSlots.add(timeSlot);
		}
		return calendarBlockedSlots;
	}
	
	private Comparator<TimeSlotDTO> timeSlotComparator = new Comparator<TimeSlotDTO>() {
		public int compare(TimeSlotDTO o1, TimeSlotDTO o2) {
			if(o1.getStartTime().compareTo(o2.getStartTime()) > 0){
				return 1;
			}else{
				return -1;
			}
		}
	};
	
	public VendorSlCalendarService getVendorSlCalendarService() {
		return vendorSlCalendarService;
	}

	public void setVendorSlCalendarService(
			VendorSlCalendarService vendorSlCalendarService) {
		this.vendorSlCalendarService = vendorSlCalendarService;
	}

	public ID2CProviderPortalDao getD2CProviderPortalDao() {
		return d2CProviderPortalDao;
	}

	public void setD2CProviderPortalDao(ID2CProviderPortalDao d2cProviderPortalDao) {
		d2CProviderPortalDao = d2cProviderPortalDao;
	}

	public ProviderSearchDao getProviderSearchDao() {
		return providerSearchDao;
	}

	public void setProviderSearchDao(ProviderSearchDao providerSearchDao) {
		this.providerSearchDao = providerSearchDao;
	}
	
	
}
