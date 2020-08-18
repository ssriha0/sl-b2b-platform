package com.servicelive.common.properties;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.exception.SLBusinessServiceException;
/**
* Test for application properties class
*/
public class ApplicationTest {

	/** _context. */
	private static ApplicationContext _context = null;

	
	/**
	 * getAppContext.
	 * 
	 * @return ApplicationContext
	 * 
	 * @throws BeansException 
	 */
	private static ApplicationContext getAppContext() throws BeansException {

		if (_context == null) {
			_context = new ClassPathXmlApplicationContext("com/servicelive/wallet/batch/testWalletBatchApplicationContext.xml");
		}
		return _context;
	}
	
	IApplicationProperties mock = null;
	/**
	 * setup.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Before
	public void setup() throws SLBusinessServiceException{
		ApplicationContext context = getAppContext();
		mock = (IApplicationProperties)context.getBean("applicationPropertiesWallet");
		
	}
	
	@Test
	public void testQueryFromDB(){
		try {
			Assert.assertNotNull(mock.getPropertyFromDB("daily_reconciliation_email_body"));
		} catch (DataNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
