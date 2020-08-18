package com.newco.marketplace.persistence.iDao.externalcalendar;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;

public interface IExternalCalenarDAO {

	public ExternalCalendarDTO getExternalCalendarDetail(Integer personId)  throws DataServiceException;
	public List<ExternalCalendarDTO> fetchAllExternalCalendarDetails() throws DataServiceException;
	public ExternalCalendarDTO insertOrUpdateExternalCalendarDetail(ExternalCalendarDTO externalCalendarDTO) throws DataServiceException;
	public ExternalCalendarDTO updateExternalCalendarDetail(ExternalCalendarDTO externalCalendarDTO) throws DataServiceException;
	
}
