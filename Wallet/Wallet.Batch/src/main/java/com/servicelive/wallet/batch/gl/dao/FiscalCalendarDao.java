package com.servicelive.wallet.batch.gl.dao;

import org.apache.log4j.Logger;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.wallet.batch.gl.vo.FiscalCalendarVO;

// TODO: Auto-generated Javadoc
/**
 * The Class FiscalCalendarDao.
 */
public class FiscalCalendarDao extends ABaseDao implements IFiscalCalendarDao {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(FiscalCalendarDao.class.getName());

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IFiscalCalendarDao#getFiscalCalendar(com.servicelive.wallet.batch.gl.vo.FiscalCalendarVO)
	 */
	public FiscalCalendarVO getFiscalCalendar(FiscalCalendarVO fiscalVO) throws DataServiceException {

		try {
			fiscalVO = (FiscalCalendarVO) queryForObject("fiscal_calendar.query", fiscalVO);

		} catch (Exception ex) {
			logger.info("[FiscalCalendarDAOImpl.getFiscalCalendar - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return fiscalVO;
	}

}
