package com.newco.marketplace.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.common.exception.SLBusinessServiceException;

public class ServiceOrderDAOTest {
	/** _context. */
	private static ApplicationContext appContext = null;
	IServiceOrderBO soBO;

	
	/**
	 * getAppContext. 
	 * @return ApplicationContext
	 * @throws BeansException 
	 */
	private static ApplicationContext getAppContext() throws BeansException {

		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext("resources/spring/backendApplicationContext.xml");
		}
		return appContext;
	}
	

	/**
	 * setup.
	 * @return void
	 * @throws SLBusinessServiceException 
	 */
	@Before
	public void setup() throws SLBusinessServiceException{
		ApplicationContext context = getAppContext();
		soBO = (IServiceOrderBO)context.getBean("soBOAOP");
		
	}

	
	@Test
	public void _testAcceptServiceOrder(){
		SecurityContext securityContext = null;
			ProcessResponse resp = soBO.processAcceptServiceOrder("142-0135-0875-16", 13, 3, null, true, false, true, securityContext);
			Assert.assertTrue(resp.isError());
	}

	@Test
	public void testFundConsumerOrder() throws BusinessServiceException, DataServiceException{
		SecurityContext securityContext = null;
		ProcessResponse processResp = null;
		List<String> errorMessages = null;
			soBO.fundConsumerFundedOrder(processResp, "101-0374-0086-17", errorMessages);
			Assert.assertTrue(true);
	}

}
