package com.newco.marketplace.business.businessImpl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.EmailAlertBOImpl;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.EmailAlertDao;

public class EmailAlertBOImplTest {
	 private EmailAlertDao emailAlertDao;
	 private EmailAlertBOImpl emailAlertBOImpl;
	 
	 @Test
	 public void testInsertToAlertTask() throws DataServiceException {
		 AlertTask alertTask = new AlertTask();
		  
		 emailAlertDao = mock(EmailAlertDao.class);
		 emailAlertBOImpl = new EmailAlertBOImpl();
		 emailAlertBOImpl.setEmailAlertDao(emailAlertDao);
		 
		 when(emailAlertDao.insertToAlertTask(alertTask)).thenReturn(true);
         /**Whenever we make a call to insertToAlertTask method,
          * it will always return true */
		 boolean inserted = emailAlertBOImpl.insertToAlertTask(alertTask);
		 assertTrue(inserted);

	 }
}
