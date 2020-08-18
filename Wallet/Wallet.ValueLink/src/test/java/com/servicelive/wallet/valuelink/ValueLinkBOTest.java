package com.servicelive.wallet.valuelink;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.valuelink.dao.IValueLinkDao;


/**
 * Class ValueLinkBO.
 */
public class ValueLinkBOTest{
	private ValueLinkBO bo;
	private IValueLinkDao valueLinkDao;
	
	@Test
	public void testIsACHTransPending(){
		bo = new ValueLinkBO();
		valueLinkDao = mock(IValueLinkDao.class);
		bo.setValueLinkDao(valueLinkDao);
		String soId = "541-5430-5650-17";
		boolean isACHTransPending = false;
		try {
			when(valueLinkDao.isACHTransPending(soId)).thenReturn(0);
				isACHTransPending = bo.isACHTransPending(soId);
			Assert.assertTrue(isACHTransPending);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}catch (SLBusinessServiceException e) {
			e.printStackTrace();
		}
		
	}
}
