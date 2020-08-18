package com.servicelive.orderfulfillment.integration;

import org.junit.Assert;
import org.junit.Test;

import com.servicelive.orderfulfillment.integration.IntegrationBO;
import com.servicelive.orderfulfillment.integration.IntegrationDao;
import static org.mockito.Mockito.*;

public class IntegrationBOTest {
	
	private IntegrationBO  bo;
	 private IntegrationDao dao;
	@Test
	public void testAcquireWork(){
		bo = new IntegrationBO();
		Integer maxRecords = 10; 
		bo.setMaxOrders(10);
		
		dao = mock(IntegrationDao.class);
		bo.setDao(dao);
		String workerId = "12345";
		boolean acquireWork;
		
		when(dao.acquireWork(workerId, maxRecords)).thenReturn(true);
		acquireWork = bo.acquireWork(workerId);
		Assert.assertTrue(acquireWork);
		
	}
}