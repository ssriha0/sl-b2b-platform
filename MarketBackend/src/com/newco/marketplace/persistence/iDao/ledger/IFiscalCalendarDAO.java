package com.newco.marketplace.persistence.iDao.ledger;


import com.newco.marketplace.dto.vo.ledger.FiscalCalendarVO;
import com.newco.marketplace.exception.core.DataServiceException;


public interface IFiscalCalendarDAO {
	
	public FiscalCalendarVO getFiscalCalendar(FiscalCalendarVO fiscalVO) throws DataServiceException;

}
