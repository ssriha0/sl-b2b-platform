package com.servicelive.wallet.batch.gl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.servicelive.wallet.batch.gl.vo.FiscalCalendarVO;

import junit.framework.TestCase;
import junitx.util.PrivateAccessor;

public class GLProcessorTest extends TestCase {
	
	private GLProcessor glProcessor = new GLProcessor();
	
	public void testCreateFiscalCaldenarVOForCheckFiscalWeek() throws Throwable {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse("2010-12-31");
		
		FiscalCalendarVO retVal = (FiscalCalendarVO)
			PrivateAccessor.invoke(glProcessor, "createFiscalCaldenarVOForCheckFiscalWeek", 
				new Class[] { Date.class }, new Object[] { date });
		
		assertEquals(new Integer(20101231), retVal.getCheckFiscalWeek());
	}

}
