package com.newco.marketplace.persistence.daoImpl.ledger;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ledger.FiscalCalendarVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.ledger.IFiscalCalendarDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class FiscalCalendarDAOImpl extends ABaseImplDao implements IFiscalCalendarDAO{
	
	private static final Logger logger = Logger.getLogger(FiscalCalendarDAOImpl.class);
	
	public FiscalCalendarVO getFiscalCalendar(FiscalCalendarVO fiscalVO) throws DataServiceException 
	{
			
		try {
			fiscalVO = (FiscalCalendarVO) queryForObject("fiscal_calendar.query",fiscalVO);
			
		} catch (Exception ex) {
			logger.info("[FiscalCalendarDAOImpl.getFiscalCalendar - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return fiscalVO;
	}

}
