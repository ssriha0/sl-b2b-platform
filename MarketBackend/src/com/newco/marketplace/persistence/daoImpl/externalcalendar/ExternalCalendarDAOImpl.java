package com.newco.marketplace.persistence.daoImpl.externalcalendar;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.externalcalendar.IExternalCalenarDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;
import com.sears.os.dao.impl.ABaseImplDao;

public class ExternalCalendarDAOImpl extends ABaseImplDao implements IExternalCalenarDAO {

	private static final Logger logger = Logger.getLogger(ExternalCalendarDAOImpl.class.getName());
	
	public ExternalCalendarDTO getExternalCalendarDetail(Integer personId)  throws DataServiceException {
		ExternalCalendarDTO externalCalendarDTO = null;
		try{
			externalCalendarDTO = (ExternalCalendarDTO) queryForObject("externalCalendarByPersonID.query", personId);
			return externalCalendarDTO;
		}
		catch (Exception e) {
			logger.info("[Exception thrown inserting background resource] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("dataaccess.failedinsert", e);
		}	
	}
	
	public ExternalCalendarDTO insertOrUpdateExternalCalendarDetail(ExternalCalendarDTO externalCalendarDTO) throws DataServiceException {
		try {
			 insert("externalCalendar.insertOrUpdate", externalCalendarDTO);
			 return externalCalendarDTO;
			}// end try
			catch (Exception e) {
				logger.info("[Exception thrown inserting background resource] "
						+ StackTraceHelper.getStackTrace(e));
				throw new DataServiceException("dataaccess.failedinsert", e);
			}	
	}

	public ExternalCalendarDTO updateExternalCalendarDetail(
			ExternalCalendarDTO externalCalendarDTO)
			throws DataServiceException {
		try {
			 insert("externalCalendarAccessToken.update", externalCalendarDTO);
			 return externalCalendarDTO;
			}// end try
			catch (Exception e) {
				logger.info("[Exception thrown inserting background resource] "
						+ StackTraceHelper.getStackTrace(e));
				throw new DataServiceException("dataaccess.failedinsert", e);
			}	
	}
	
	
	public List<ExternalCalendarDTO> fetchAllExternalCalendarDetails() throws DataServiceException{
		try {
			List<ExternalCalendarDTO> externalCalendarDetails = (List<ExternalCalendarDTO>) queryForList("fetchAllExternalCalendars.query");
			 return externalCalendarDetails;
			}// end try
			catch (Exception e) {
				logger.info("[Exception in retriving external calendars] "
						+ StackTraceHelper.getStackTrace(e));
				throw new DataServiceException("dataaccess.failedinsert", e);
			}
	}

}
