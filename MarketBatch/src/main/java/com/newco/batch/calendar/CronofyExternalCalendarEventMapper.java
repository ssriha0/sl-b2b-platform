/*package com.newco.batch.calendar;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;
import com.sfl.cronofy.api.model.EventModel;

public class CronofyExternalCalendarEventMapper<T, V> implements ExternalCalendarEventMapper<T, V> {
	
	private static final Logger logger = Logger
			.getLogger(CronofyExternalCalendarEventMapper.class);
	

	public List<T> mapToExternalEvents(List<V> slEventList) throws BusinessServiceException {
		logger.info("Mapping to External events");
		List<EventModel> cronofyEventModelList = new ArrayList<EventModel>();
		for(V slEvent: slEventList){
			EventModel cronofyEventModel = new EventModel();
			CalendarEventVO slEventModel = (CalendarEventVO) slEvent;
			cronofyEventModel.setStart(slEventModel.getStartDate());
			cronofyEventModel.setEnd(slEventModel.getEndDate());
			cronofyEventModel.setEventId(slEventModel.getEventId().toString());
			cronofyEventModel.setDescription(slEventModel.getEventName());
			cronofyEventModelList.add(cronofyEventModel);
		}
		logger.info("got external events");
		return (List<T>) cronofyEventModelList;
	}

	public List<T> mapToSLEvents(List<V> externalEventList) throws BusinessServiceException {
		logger.info("Mapping to SLevents");
		List<CalendarEventVO> slEventModelList = new ArrayList<CalendarEventVO>();
		for(V externalEvent:externalEventList){
			CalendarEventVO slEventModel = new CalendarEventVO();
			EventModel cronofyEventModel = (EventModel) externalEvent;
			slEventModel.setStartDate(cronofyEventModel.getStart());
			slEventModel.setEndDate(cronofyEventModel.getEnd());			
			slEventModel.setEventId(cronofyEventModel.getEventId());
			slEventModel.setEventName(cronofyEventModel.getDescription());
			slEventModelList.add(slEventModel);
		}
		logger.info("got SLevents");
		return (List<T>) slEventModelList;
	}

}
*/