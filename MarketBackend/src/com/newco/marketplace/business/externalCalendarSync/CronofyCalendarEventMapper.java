package com.newco.marketplace.business.externalCalendarSync;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;

public class CronofyCalendarEventMapper implements
		ExternalCalendarEventMapper{

	private static final Logger logger = Logger
			.getLogger(CronofyCalendarEventMapper.class);
	private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");	

	public List<CalendarEventVO> mapToCalendarEventVO(String externalCalendars)
			throws BusinessServiceException {

		List<CalendarEventVO> internalEvents = new ArrayList<CalendarEventVO>();
		JsonParser parser = new JsonParser();
		JsonElement cronofyEventTree = parser.parse(externalCalendars);
		if (cronofyEventTree.isJsonObject()) {
			logger.info("parsing json");
			JsonObject cronofyEventObject = cronofyEventTree.getAsJsonObject();
			JsonArray events = cronofyEventObject.get("events")
					.getAsJsonArray();
			for (JsonElement event : events) {
				CalendarEventVO calendarEventVO = new CalendarEventVO();
				JsonObject eventObject = event.getAsJsonObject();
				String event_id = eventObject.get("event_uid").getAsString();
				logger.info("Event UID: " + event_id);
				calendarEventVO.setEventId(event_id);

				String summary = eventObject.get("summary").getAsString();
				logger.info("summary: " + summary);
				calendarEventVO.setEventName(summary);

				String start = eventObject.get("start").getAsString();
				logger.info("start: " + start);
				try {
					calendarEventVO.setStartDate(defaultDateFormat.parse(start));
				} catch (ParseException e) {
					throw new BusinessServiceException(e);
				}
				String startTime = start.substring(start.indexOf('T') + 1,
						start.indexOf('Z'));
				logger.info("start: " + startTime);
				calendarEventVO.setStartTime(startTime);

				String end = eventObject.get("end").getAsString();
				logger.info("end: " + end);
				try {
					calendarEventVO.setEndDate(defaultDateFormat.parse(end));
				} catch (ParseException e) {
					throw new BusinessServiceException(e);
				}
				String endTime = end.substring(end.indexOf('T') + 1,
						end.indexOf('Z'));
				logger.info("end: " + endTime);
				calendarEventVO.setEndTime(endTime);
				internalEvents.add(calendarEventVO);
				
				calendarEventVO.setType("NON-SERVICE");
			}
		}
		return internalEvents;
	}
	
	public JsonObject mapToExternalEventVO(List<CalendarEventVO> internalEvents)
			throws BusinessServiceException {
		JsonObject eventObject = new JsonObject();
		for (CalendarEventVO internalEvent : internalEvents) {
			eventObject.addProperty("event_id", internalEvent.getEventId());
			eventObject.addProperty("summary", internalEvent.getEventName());
			eventObject.addProperty("description", "You can put notes in here");
			String start = defaultDateFormat.format(internalEvent.getStartDate());
			start = start + "T" + internalEvent.getStartTime() + "Z";
			eventObject.addProperty("start", start);
			String end = defaultDateFormat.format(internalEvent.getEndDate());
			end = end + "T" + internalEvent.getEndTime() + "Z";
			eventObject.addProperty("end", end);
			eventObject.addProperty("tzid", "Etc/UTC");
		}
		return eventObject;
	}

	public ExternalCalendarDTO mapToExternalCalenarDTO(String response) throws BusinessServiceException{
		JsonParser parser = new JsonParser();
		JsonElement jsonResponse = parser.parse(response);
		ExternalCalendarDTO externalCalendarDTO = new ExternalCalendarDTO();
		JsonObject jsonObject = jsonResponse.getAsJsonObject();

		String access_token = jsonObject.get("access_token").getAsString();
		logger.info("access_token: "+access_token);
		externalCalendarDTO.setAccess_token(access_token);
		
		String refresh_token = jsonObject.get("refresh_token").getAsString();
		logger.info("refresh_token: "+refresh_token);
		externalCalendarDTO.setRefresh_token(refresh_token);
		
		String account_id = jsonObject.get("account_id").getAsString();
		logger.info("account_id: "+account_id);
		externalCalendarDTO.setCronofyAccId(account_id);
		
		JsonElement profileInfo = jsonObject.get("linking_profile");
		if(profileInfo != null){
			JsonObject profileObject = profileInfo.getAsJsonObject();
			
			String provider_name = profileObject.get("provider_name").getAsString();
			logger.info("provider_name: "+provider_name);
			externalCalendarDTO.setCalendarSource(provider_name);
			
			String profile_name = profileObject.get("profile_name").getAsString();
			logger.info("profile_name: "+profile_name);
			externalCalendarDTO.setEmailId(profile_name);
		}
		return  externalCalendarDTO;
	}
	
	public String retriveAccessToken(String response) throws BusinessServiceException{
		JsonParser parser = new JsonParser();
		JsonElement jsonResponse = parser.parse(response);
		ExternalCalendarDTO externalCalendarDTO = new ExternalCalendarDTO();
		JsonObject jsonObject = jsonResponse.getAsJsonObject();

		String access_token = jsonObject.get("access_token").getAsString();
		logger.info("access_token: "+access_token);
		return access_token;
	}

}
