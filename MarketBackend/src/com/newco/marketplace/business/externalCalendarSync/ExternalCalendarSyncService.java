package com.newco.marketplace.business.externalCalendarSync;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;

public interface ExternalCalendarSyncService {
	
	 boolean twoWaySync() throws BusinessServiceException;
	 boolean syncExternalEvents(ExternalCalendarDTO externalCalendarDTO) throws BusinessServiceException;
	 boolean syncInternalEvents() throws BusinessServiceException;
	 ExternalCalendarDTO connectToNewExternalCalendar(int firmID, int personID, String oAuthCode, String contextPath) throws BusinessServiceException;
	 ExternalCalendarDTO updateAccessToken(ExternalCalendarDTO externalCalendarDTO) throws BusinessServiceException;

}
