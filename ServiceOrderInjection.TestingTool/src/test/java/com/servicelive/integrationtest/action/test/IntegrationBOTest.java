package com.servicelive.integrationtest.action.test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.servicelive.integrationtest.bo.IntegrationBO;
import com.servicelive.integrationtest.dao.IIntegrationDao;
import com.servicelive.integrationtest.dao.IntegrationDao;



public class IntegrationBOTest {
   private static Logger logger = Logger.getLogger(IntegrationBOTest.class);
   private static String soId ="23-2345-4567-12";
   private IntegrationBO iIntegrationBO;
   @Test
   public void getServiceOrderTest(){
	   String actualSoId =null;
	   iIntegrationBO = new IntegrationBO();
	   IIntegrationDao integrationDao = mock(IntegrationDao.class);
	   DataSource dataSource =mock(DataSource.class);
	   iIntegrationBO.setIntegrationDao(integrationDao);
	   iIntegrationBO.setDataSource(dataSource);
	   try{
		   when(integrationDao.getSoIdByExternalOrderNum("infy190833")).thenReturn(soId);
		   actualSoId = iIntegrationBO.getSoIdByExternalOrderNum("infy190833");
	     }catch (Exception e) {
		logger.error("Exception in putting file in the proper location:"+ e.getMessage());
	}  
	   Assert.assertSame(actualSoId,soId);
	   
   }
}
