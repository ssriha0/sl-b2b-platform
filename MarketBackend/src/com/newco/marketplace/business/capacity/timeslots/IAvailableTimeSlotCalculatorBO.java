package com.newco.marketplace.business.capacity.timeslots;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.TimeSlotDTO;

public interface IAvailableTimeSlotCalculatorBO {
	
	Map<String,List<TimeSlotDTO>> getBlockedCalendarSlots(List<String> providers, Date date) throws BusinessServiceException;
	List<TimeSlotDTO> getAvailableTimeSlots(Date date,
	Map<String, TimeSlotDTO> capacity, Map<String, List<TimeSlotDTO>> calendar) throws BusinessServiceException;
	List<String> getApplicableProvidersForTechTalk(Integer buyerId, String skuId) throws BusinessServiceException;
	List<String> getValidProvidersForTechTalk(Integer buyerId, String skuId) throws BusinessServiceException;
}
