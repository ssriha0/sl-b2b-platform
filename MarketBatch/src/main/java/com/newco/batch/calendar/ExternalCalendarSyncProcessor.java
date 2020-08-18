package com.newco.batch.calendar;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.business.externalCalendarSync.ExternalCalendarSyncService;
import com.newco.marketplace.business.iBusiness.externalcalendar.IExternalCalendarIntegrationBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;

/**
 * Purpose : This batch will synchronize the external calendar (Google /Apple
 * /Outlook) which providers has configured with SL application through
 * "Calendar Settings" option. Events which were created in past one year to next
 * one year are getting synchronized. This time frame can be changed through
 * 'cronofy_sync_period' property in supplier_prod.application_properties table.
 * 
 * Period : Batch will be executed at defined period mentioned as CRON
 * expression in 'TRG_EXTERNAL_CALENDAR_SYNC' trigger at
 * servicelive_QRTZ.QRTZ_CRON_TRIGGERS table.
 * 
 * External Dependency : It uses Cronofy apis to access different
 * external calendars "https://www.cronofy.com/developers/api/"
 * 
 * @author mbhupta
 * 
 */
public class ExternalCalendarSyncProcessor extends ABatchProcess {

	private static final Logger logger = Logger
			.getLogger(ExternalCalendarSyncProcessor.class.getName());
	
	private ExternalCalendarSyncService externalCalendarSyncService;
	private IExternalCalendarIntegrationBO externalCalendarIntegrationBO;
	
	@Override
	public int execute() throws BusinessServiceException {
		logger.info("External calendar batch sync started...");
		List<ExternalCalendarDTO> exteranlCalendars = externalCalendarIntegrationBO.getExternalCalendarDetailsForSync();
		for(ExternalCalendarDTO externalCalendar :exteranlCalendars){
			externalCalendar = externalCalendarSyncService.updateAccessToken(externalCalendar);
			externalCalendarSyncService.syncExternalEvents(externalCalendar);
		}
		logger.info("Completed External calendar sync.");
		return 1;
	}

	public ExternalCalendarSyncService getExternalCalendarSyncService() {
		return externalCalendarSyncService;
	}

	public void setExternalCalendarSyncService(
			ExternalCalendarSyncService externalCalendarSyncService) {
		this.externalCalendarSyncService = externalCalendarSyncService;
	}
	public IExternalCalendarIntegrationBO getExternalCalendarIntegrationBO() {
		return externalCalendarIntegrationBO;
	}

	public void setExternalCalendarIntegrationBO(
			IExternalCalendarIntegrationBO externalCalendarIntegrationBO) {
		this.externalCalendarIntegrationBO = externalCalendarIntegrationBO;
	}

	@Override
	public void setArgs(String[] args) {
		// TODO Auto-generated method stub

	}
}
