package com.newco.marketplace.business.iBusiness.externalcalendar;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;

public interface IExternalCalendarIntegrationBO {

	public ExternalCalendarDTO saveOrUpdateExternalCalendarDetail(ExternalCalendarDTO externalCalendarDTO) throws BusinessServiceException;
	public ExternalCalendarDTO saveUpdatedAccessToken(ExternalCalendarDTO externalCalendarDTO) throws BusinessServiceException;
	public List<ExternalCalendarDTO> getExternalCalendarDetailsForSync() throws BusinessServiceException;
	public ExternalCalendarDTO getExternalCalendarDetail(Integer personID) throws BusinessServiceException;
	
}
