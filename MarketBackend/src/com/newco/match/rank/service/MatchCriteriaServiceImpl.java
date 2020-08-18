package com.newco.match.rank.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.calendarPortal.Services.impl.VendorSlCalendarService;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.business.iBusiness.provider.IGeneralInfoBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.match.rank.performancemetric.dao.IMatchingCriteriaDao;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;
import com.newco.marketplace.vo.provider.TimeSlotDTO;

public class MatchCriteriaServiceImpl implements IMatchCriteriaService {
	
	private static Logger logger = Logger.getLogger(MatchCriteriaServiceImpl.class);
	
	private IMatchingCriteriaDao matchingCriteriaDao;
	private IGeneralInfoBO generalInfoBO;
	private VendorSlCalendarService vendorSlCalendarService;
	
	public IMatchingCriteriaDao getMatchingCriteriaDao() {
		return matchingCriteriaDao;
	}

	public void setMatchingCriteriaDao(IMatchingCriteriaDao matchingCriteriaDao) {
		this.matchingCriteriaDao = matchingCriteriaDao;
	}
	
	public IGeneralInfoBO getGeneralInfoBO() {
		return generalInfoBO;
	}

	public void setGeneralInfoBO(IGeneralInfoBO generalInfoBO) {
		this.generalInfoBO = generalInfoBO;
	}
	
	public VendorSlCalendarService getVendorSlCalendarService() {
		return vendorSlCalendarService;
	}

	public void setVendorSlCalendarService(VendorSlCalendarService vendorSlCalendarService) {
		this.vendorSlCalendarService = vendorSlCalendarService;
	}
	
	// d2cPortalAPIVO.getPrefIdAndStartEndDateTimeSlotMap()
	// dateTimePrefAndStartEndSlotMap = pref date and time slot
	// Key : 1 Value : TimeSlotDTO [startTime=Thu Oct 26 12:00:00 IST 2017, endTime=Thu Oct 26 17:00:00 IST 2017, hourInd24=false, matchedSoPrefDateTime=false]
	// Key : 2 Value : TimeSlotDTO [startTime=Fri Oct 27 16:00:00 IST 2017, endTime=Fri Oct 27 20:00:00 IST 2017, hourInd24=false, matchedSoPrefDateTime=false]
	// Key : 3 Value : TimeSlotDTO [startTime=Sat Oct 28 08:00:00 IST 2017, endTime=Sat Oct 28 12:00:00 IST 2017, hourInd24=false, matchedSoPrefDateTime=false]
	// --------------------------------------------------------
	// List<D2CProviderAPIVO> firmDetailsListVo
	// # firmId, 	 price, 	dailyLimit, buyerRetailPrice
	// '10202', 	'10.00', 	'6', 		'80.00'
	// '15897', 	'98.00', 	'2', 		'80.00'
	// '67225', 	'34.99', 	'3', 		'80.00'
	public void calculateAndSaveMatchingCriteria(D2CPortalAPIVORequest d2cPortalAPIVO, List<D2CProviderAPIVO> firmDetailsListVo) throws Exception {
		
		logger.info("----------START: calculateAndSaveMatchingCriteria() -------------------");
		
		// validate
		logger.info("----------validate(d2cPortalAPIVO, firmDetailsListVo) -------------------");
		boolean returnBack = validate(d2cPortalAPIVO, firmDetailsListVo);
		if (returnBack) {
			return;
		}
		
		// final score [firmId, score]
		Map<Integer, Integer> finalScoreFirmSoPrefDateTimeMap = new HashMap<Integer, Integer>();
		
		// dateTimePrefAndStartEndSlotMap = pref date and time slot
		// Key : 1 Value : TimeSlotDTO [startTime=Thu Oct 26 12:00:00 IST 2017, endTime=Thu Oct 26 17:00:00 IST 2017, hourInd24=false]
		// Key : 2 Value : TimeSlotDTO [startTime=Fri Oct 27 16:00:00 IST 2017, endTime=Fri Oct 27 20:00:00 IST 2017, hourInd24=false]
		// Key : 3 Value : TimeSlotDTO [startTime=Sat Oct 28 08:00:00 IST 2017, endTime=Sat Oct 28 12:00:00 IST 2017, hourInd24=false]
		Map<Integer, TimeSlotDTO> dateTimePrefAndStartEndSlotMap = d2cPortalAPIVO.getPrefIdAndStartEndDateTimeSlotMap();
				
		DateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		
		Date prefStartTime = null;
		Date prefEndTime = null;
		
		Calendar startTimeCal = null;
		Calendar endTimeCal = null;
		
		// firmResourceId
		List<String> providerFirmResourceList = null;

		// [firmId, List<firmResourceId>]
		Map<Integer, List<String>> firmIdAndResourceIdListMap = new HashMap<Integer, List<String>>();
		// for each firmId, fetch and store it's provider's list
		for (D2CProviderAPIVO vo : firmDetailsListVo) {
			
			if (null == (vo.getFirmId())) {
				continue;
			}
			
			// resource id list
			providerFirmResourceList = fetchProviderResourceListByProviderId(vo.getFirmId());
			
			logger.info("[FirmId] = " + vo.getFirmId() + " [PROVIDERS] = " + providerFirmResourceList);

			// map of firmId and it's provider's list
			firmIdAndResourceIdListMap.put(vo.getFirmId(), providerFirmResourceList);
		}
		
		// --------------------------------------------------------------
		// for each firmId's provider's list
		for (Integer firmId : firmIdAndResourceIdListMap.keySet()) {
			
			// List<firmResourceId>
			if ((firmIdAndResourceIdListMap.get(firmId)).size() == 0) {
				continue;
			}
			
			// itetrate over time pref. starting from 1 (LinkedHashmap)
			for (Integer prefInd : dateTimePrefAndStartEndSlotMap.keySet()) {
				
				logger.info("prefInd = " + prefInd 
						+ "_____firmId = " + (firmId) 
						+ "_____TimeSlotDTO = " + dateTimePrefAndStartEndSlotMap.get(prefInd));
				
				// ----- START: fetch capacity ----------------------------------
				startTimeCal = Calendar.getInstance();
				startTimeCal.setTime((dateTimePrefAndStartEndSlotMap.get(prefInd)).getStartTime());

				endTimeCal = Calendar.getInstance();
				endTimeCal.setTime((dateTimePrefAndStartEndSlotMap.get(prefInd)).getEndTime());
				
				// END: fetch capacity ----------------------------------
				// prefInd = 1, TimeSlotDTO [startTime=Thu Oct 26 12:00:00 IST 2017, endTime=Thu Oct 26 17:00:00 IST 2017, hourInd24=false]
				// firmId = 10202
				// providerCapacityTimeSlotsTemp
				// Key : 89187 Value : TimeSlotDTO [startTime=Thu Oct 26 12:00:00 IST 2017, endTime=Thu Oct 26 17:00:00 IST 2017, hourInd24=true]
				// Key : 19459 Value : TimeSlotDTO [startTime=Mon Jan 01 08:00:00 IST 2007, endTime=Mon Jan 01 20:00:00 IST 2007, hourInd24=false]
				// [providerId, TimeSlotDTO]
				
				logger.info("getCapacityAvailableTimeSlots => startTimeCal: " + startTimeCal + "   endTimeCal: " + endTimeCal);
				Map<String, TimeSlotDTO> providerCapacityTimeSlots = getCapacityAvailableTimeSlots(
						(firmIdAndResourceIdListMap.get(firmId)), startTimeCal, endTimeCal);
				// ----- END: fetch capacity ----------------------------------
				
				// ------------------------------------------------------
				// eg.: [ONLY_TIME]prefStartTime = Thu Jan 01 12:00:00 IST 1970
				prefStartTime = sdfTime.parse((((dateTimePrefAndStartEndSlotMap.get(prefInd)).getStartTime()).toString()).split(" ")[3]);
				
				// eg.: [ONLY_TIME]prefEndTime = Thu Jan 01 17:00:00 IST 1970
				prefEndTime = sdfTime.parse((((dateTimePrefAndStartEndSlotMap.get(prefInd)).getEndTime()).toString()).split(" ")[3]);
				
				// filtering only based on TIME
				logger.info("filterProviderCapacityTimeSlotsMapWithPrefTime => prefStartTime: " + prefStartTime + "   prefEndTime: " + prefEndTime);
				List<String> providerIdCapacityTimeSlotsFilteredList = filterProviderCapacityTimeSlotsMapWithPrefTime(
						providerCapacityTimeSlots, prefStartTime, prefEndTime);
				// ------------------------------------------------------
				
				logger.info("_______________FILTERED_LIST____________________________________________");
				logger.info(providerIdCapacityTimeSlotsFilteredList);
				
				// setting matched preference flag logic based on calender api -------------------------------
				checkProviderAvailabilityInPreferedDateAndTimeSlot(
						providerIdCapacityTimeSlotsFilteredList
						, (dateTimePrefAndStartEndSlotMap.get(prefInd)));
				
			}
			
			// LOGIC for percentage
			logger.info("------------------- LOGIC_FOR_PERCENTAGE -------------------");
			Integer per = calculatePercentageBasedOnSoPrefDateTime(dateTimePrefAndStartEndSlotMap);
			
			finalScoreFirmSoPrefDateTimeMap.put(firmId, per);
			
			initializeMatchedSoPrefDateTimeBooleanIndToFalse(dateTimePrefAndStartEndSlotMap);

		} // START: for each firmId's provider's list
		
		logger.info("\n\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		logger.info(finalScoreFirmSoPrefDateTimeMap);
		
		saveOrUpdateMatchingCriteria(finalScoreFirmSoPrefDateTimeMap, d2cPortalAPIVO.getBuyerId());
		
		logger.info("----------END: calculateAndSaveMatchingCriteria() -------------------");

	} // END: calculateAndSaveMatchingCriteria
	
	
	private void saveOrUpdateMatchingCriteria(Map<Integer, Integer> finalScoreFirmSoPrefDateTimeMap, String buyerID) {
		// [firmId, per]
		for (Integer firmID : finalScoreFirmSoPrefDateTimeMap.keySet()) {
			// buyerId, firmId, matchingScore
			getMatchingCriteriaDao().saveOrUpdateMatchingCriteria(buyerID, firmID, finalScoreFirmSoPrefDateTimeMap.get(firmID));
		}
	}
	
	
	private Map<String, TimeSlotDTO> getCapacityAvailableTimeSlots(List<String> providerList, Calendar startTimeCal, Calendar endTimeCal) throws Exception {
		return (generalInfoBO.getCapacityAvailableTimeSlots(providerList, startTimeCal, endTimeCal));
	}

	private boolean validate(D2CPortalAPIVORequest d2cPortalAPIVO, List<D2CProviderAPIVO> firmDetailsListVo) {
		
		if (null == firmDetailsListVo) {
			return true;
		}
		
		if (firmDetailsListVo.size() == 0) {
			return true;
		}
		
		if (null == (d2cPortalAPIVO.getPrefIdAndStartEndDateTimeSlotMap())) {
			return true;
		}
		
		if ((d2cPortalAPIVO.getPrefIdAndStartEndDateTimeSlotMap()).size() == 0) {
			return true;
		}
		
		return false;
	}
	
	private List<String> fetchProviderResourceListByProviderId(Integer firmId) {
		return getMatchingCriteriaDao().fetchProviderResourceListByProviderId(firmId);
	}
	
	private List<String> filterProviderCapacityTimeSlotsMapWithPrefTime(
			Map<String, TimeSlotDTO> providerCapacityTimeSlotsTemp
			, Date prefStartDateTime
			, Date prefEndDateTime) throws ParseException {
		
		DateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		
		Date providerStartDateTime = null;
		Date providerEndDateTime = null;
		
		List<String> providerIdCapacityTimeSlotsFilteredList = null;
		if ((null != providerCapacityTimeSlotsTemp) && (providerCapacityTimeSlotsTemp.size() > 0)) {
			providerIdCapacityTimeSlotsFilteredList = new ArrayList<String>();
		} else {
			return providerIdCapacityTimeSlotsFilteredList;
		}
		
		// filtering
		for (String resourceId : providerCapacityTimeSlotsTemp.keySet()) {
			
			TimeSlotDTO ts = providerCapacityTimeSlotsTemp.get(resourceId);
			
			if (ts.isHourInd24()) {
				// add it directly to filtered Map
				providerIdCapacityTimeSlotsFilteredList.add(resourceId);
			} else {
				// ignore if 24 hr ind is false, compare and filter for below formulae and add only ACCEPTED range.
				// (pref_start_time >= provider_start_time && provider_end_time >= pref_end_time)
				
				// eg.: prefStartDateTime = Thu Jan 01 12:00:00 IST 1970
				providerStartDateTime = sdfTime.parse(((ts.getStartTime()).toString()).split(" ")[3]);
				
				// eg.: prefEndDateTime = Thu Jan 01 17:00:00 IST 1970
				providerEndDateTime = sdfTime.parse(((ts.getEndTime()).toString()).split(" ")[3]);
				
				// (pref_start_time >= provider_start_time && provider_end_time >= pref_end_time)
				if (((prefStartDateTime.compareTo(providerStartDateTime)) >= 0) && ((providerEndDateTime.compareTo(prefEndDateTime)) >=0)) {
					
					providerIdCapacityTimeSlotsFilteredList.add(resourceId);
					
					logger.info("----START: ACCEPTED range----resourceId: " + resourceId);
					logger.info("prefStartDateTime = " + prefStartDateTime);
					logger.info("prefEndDateTime = " + prefEndDateTime);
					logger.info("providerStartDateTime = " + providerStartDateTime);
					logger.info("providerEndDateTime = " + providerEndDateTime);
					logger.info("----END: ACCEPTED range----");
				} else {
					logger.info("----START: NOT-ACCEPTED range----resourceId: " + resourceId);
					logger.info("prefStartDateTime = " + prefStartDateTime);
					logger.info("prefEndDateTime = " + prefEndDateTime);
					logger.info("providerStartDateTime = " + providerStartDateTime);
					logger.info("providerEndDateTime = " + providerEndDateTime);
					logger.info("----END: NOT-ACCEPTED range----");
				}
			}
			
		} // filtering: for (String resourceId : providerCapacityTimeSlots.keySet())
		
		return providerIdCapacityTimeSlotsFilteredList;
	}
	
	private void checkProviderAvailabilityInPreferedDateAndTimeSlot(
			List<String> providerIdCapacityTimeSlotsFilteredList,
			TimeSlotDTO prefTimeSlot) {
		
		logger.info("-------------------------START: checkProviderAvailabilityInPreferedTimeSlot--------------------");
		
		if (null == providerIdCapacityTimeSlotsFilteredList) {
			logger.info("returned as providerIdCapacityTimeSlotsFilteredList is null");
			return;
		}
		
		Date prefStartDateTime = prefTimeSlot.getStartTime();
		Date prefEndDateTime = prefTimeSlot.getEndTime();
		
		logger.info("prefStartDateTime: " + prefStartDateTime);
		logger.info("prefEndDateTime: " + prefEndDateTime);
		logger.info("----------------------------------------------------");
		
		String prefStartTime24Hr = (((prefStartDateTime).toString()).split(" ")[3]);
		String prefEndTime24Hr = (((prefEndDateTime).toString()).split(" ")[3]);
			
		logger.info("prefStartTime24Hr: " + prefStartTime24Hr);
		logger.info("prefEndTime24Hr: " + prefEndTime24Hr);
		
		Date prefStartDateTimeTemp = null;
		Date prefEndDateTimeTemp = null;
		
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			prefStartDateTimeTemp = sdf.parse((sdf.format(prefStartDateTime)));
			prefEndDateTimeTemp = sdf.parse((sdf.format(prefEndDateTime)));
			
			logger.info("prefStartDateTimeTemp: " + prefStartDateTimeTemp);
			logger.info("prefEndDateTimeTemp: " + prefEndDateTimeTemp);
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		List<CalendarEventVO> voList = null;
		for (String providerId : providerIdCapacityTimeSlotsFilteredList) {
			try {
				voList = getVendorSlCalendarService()
						.getProviderCalenderDetailForFreeTimeSlot(
								Integer.parseInt(providerId),
								prefStartDateTimeTemp, prefEndDateTimeTemp,
								prefStartTime24Hr, prefEndTime24Hr);
				
				if (null != voList) {
					// means [voList.size() > 0] = He can be available for our passed DateTime slot.
					if (voList.size() > 0) {
						// atleast 1 provider, make the pref.ind true
						logger.info("AVAILABLE providerID = " + providerId);
						prefTimeSlot.setMatchedSoPrefDateTime(true);
						break;
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (BusinessServiceException e) {
				e.printStackTrace();
				// if BusinessServiceException = break
				break;
			}
		}
		
		logger.info("----------------------------------------------------");
		logger.info("prefStartDateTime: " + prefStartDateTime);
		logger.info("prefEndDateTime: " + prefEndDateTime);
		logger.info("--------------------------------END: checkProviderAvailabilityInPreferedTimeSlot--------------------");
	
	}
	
	private int calculatePercentageBasedOnSoPrefDateTime(Map<Integer, TimeSlotDTO> dateTimePrefAndStartEndSlotMap) {
		
		boolean pref1 = false;
		boolean pref2 = false;
		boolean pref3 = false;
		
		for (Integer pref : dateTimePrefAndStartEndSlotMap.keySet()) {
			if (pref == 1) {
				pref1 = dateTimePrefAndStartEndSlotMap.get(pref).isMatchedSoPrefDateTime();
			} else if (pref == 2) {
				pref2 = dateTimePrefAndStartEndSlotMap.get(pref).isMatchedSoPrefDateTime();
			} else if (pref == 3) {
				pref3 = dateTimePrefAndStartEndSlotMap.get(pref).isMatchedSoPrefDateTime();
			}
		}
		
		
		if (pref1 && pref2 && pref3) {
			logger.info("100 %");
			return 100;
		} else if ((pref1 && pref2) || (pref1 && pref3) || (pref1)) {
			logger.info("75 %");
			return 75;
		} else if ((pref2 && pref3) || (pref2)) {
			logger.info("50 %");
			return 50;
		} else if (pref3) {
			logger.info("25 %");
			return 25;
		} else {
			logger.info("0 %");
			return 0;
		}
	}
	
	private static void initializeMatchedSoPrefDateTimeBooleanIndToFalse(Map<Integer, TimeSlotDTO> dateTimePrefAndStartEndSlotMap) {
		for (Integer prefNo : dateTimePrefAndStartEndSlotMap.keySet()) {
			TimeSlotDTO ts = dateTimePrefAndStartEndSlotMap.get(prefNo);
			ts.setMatchedSoPrefDateTime(false);
		}
	}
	
}
