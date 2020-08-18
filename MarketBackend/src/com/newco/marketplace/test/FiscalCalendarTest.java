package com.newco.marketplace.test;


import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.ledger.FiscalCalendarVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.ledger.IFiscalCalendarDAO;
import com.newco.marketplace.util.TimestampUtils;


public class FiscalCalendarTest {
	
	public static void main(String args[])
	{
		
		FiscalCalendarVO fisVo = null;
		IFiscalCalendarDAO fiscalCalendarDAO = (IFiscalCalendarDAO)MPSpringLoaderPlugIn.getCtx().getBean("fiscalCalendarBO");
		try {
			fisVo = TimestampUtils.getSearsFiscalCalendarWeek(fiscalCalendarDAO);
			
			System.out.println("Calendar Start Week "+fisVo.getCalendarStartWeek()+" Calendar End Week "
					+fisVo.getCalendarEndWeek()+" Calendar Quater Fiscal Week "+fisVo.getQuarterFiscalWeek()
					+" Calendar Year Fiscal Week "+fisVo.getYearFiscalWeek());
			
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		
	}

}
