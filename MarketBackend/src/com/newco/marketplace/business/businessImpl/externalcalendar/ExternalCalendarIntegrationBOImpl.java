package com.newco.marketplace.business.businessImpl.externalcalendar;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.externalcalendar.IExternalCalendarIntegrationBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.externalcalendar.IExternalCalenarDAO;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;

public class ExternalCalendarIntegrationBOImpl implements
		IExternalCalendarIntegrationBO {
	
	private static final Logger logger = Logger.getLogger(ExternalCalendarIntegrationBOImpl.class.getName());
	private IExternalCalenarDAO externalCalendarDAO;
	
	
	public ExternalCalendarDTO saveOrUpdateExternalCalendarDetail(
			ExternalCalendarDTO externalCalendarDTO) throws BusinessServiceException {
		ExternalCalendarDTO savedExternalCalendarDTO = null;
		try {
			logger.info("persising external calendar: "+externalCalendarDTO);
			savedExternalCalendarDTO = externalCalendarDAO.insertOrUpdateExternalCalendarDetail(externalCalendarDTO);
		} catch (DataServiceException e) {
			logger.error("Exceptino in persisting external calendar detail: "+e);
			throw new BusinessServiceException(e);
		}
		return savedExternalCalendarDTO;
	}

	public ExternalCalendarDTO saveUpdatedAccessToken(ExternalCalendarDTO externalCalendarDTO) throws BusinessServiceException {
		ExternalCalendarDTO savedExternalCalendarDTO = null;
		try {
			logger.info("Updating access_ token: "+externalCalendarDTO.getAccess_token());
			savedExternalCalendarDTO = externalCalendarDAO.updateExternalCalendarDetail(externalCalendarDTO);
		} catch (DataServiceException e) {
			logger.error("Exceptino in updating calendar detail: "+e);
			throw new BusinessServiceException(e);
		}
		return savedExternalCalendarDTO;
	}
	
	public List<ExternalCalendarDTO> getExternalCalendarDetailsForSync()
			throws BusinessServiceException {
			List<ExternalCalendarDTO> externalCalendars = null;
		try {
			logger.info("getting all available calendar for syncing..");
			externalCalendars = externalCalendarDAO.fetchAllExternalCalendarDetails();
			logger.info("count of external calendars: "+externalCalendars.size());
		} catch (DataServiceException e) {
			logger.error("Exceptino in updating calendar detail: "+e);
			throw new BusinessServiceException(e);
		}
		return externalCalendars;
	}
	
	public ExternalCalendarDTO getExternalCalendarDetail(Integer personID)
			throws BusinessServiceException{
			ExternalCalendarDTO externalCalendarDTO = null;
		try{
			logger.info("getting external calendar detial for: "+personID);
			externalCalendarDTO = externalCalendarDAO.getExternalCalendarDetail(personID);
			logger.info(externalCalendarDTO);
			return externalCalendarDTO;
		}catch (DataServiceException e) {
			logger.error("Exceptino in updating calendar detail: "+e);
			throw new BusinessServiceException(e);
		}
	}
	

	public IExternalCalenarDAO getExternalCalendarDAO() {
		return externalCalendarDAO;
	}

	public void setExternalCalendarDAO(IExternalCalenarDAO externalCalendarDAO) {
		this.externalCalendarDAO = externalCalendarDAO;
	}

	
}
