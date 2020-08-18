package com.newco.marketplace.business.externalCalendarSync;

import java.util.List;

import com.google.gson.JsonObject;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;

public interface ExternalCalendarEventMapper {

	public List<CalendarEventVO> mapToCalendarEventVO(String externalCalendars) throws BusinessServiceException;;
	public JsonObject mapToExternalEventVO(List<CalendarEventVO> internalCalendars) throws BusinessServiceException;
	public ExternalCalendarDTO mapToExternalCalenarDTO(String response) throws BusinessServiceException;
	public String retriveAccessToken(String response) throws BusinessServiceException;
	
}
