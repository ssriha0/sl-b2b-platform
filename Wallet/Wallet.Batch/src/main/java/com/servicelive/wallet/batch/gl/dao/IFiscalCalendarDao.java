package com.servicelive.wallet.batch.gl.dao;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.gl.vo.FiscalCalendarVO;

// TODO: Auto-generated Javadoc
/**
 * The Interface IFiscalCalendarDao.
 */
public interface IFiscalCalendarDao {

	/**
	 * Gets the fiscal calendar.
	 * 
	 * @param fiscalVO 
	 * 
	 * @return the fiscal calendar
	 * 
	 * @throws DataServiceException 
	 */
	public FiscalCalendarVO getFiscalCalendar(FiscalCalendarVO fiscalVO) throws DataServiceException;

}
