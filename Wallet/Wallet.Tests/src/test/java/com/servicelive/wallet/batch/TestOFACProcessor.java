package com.servicelive.wallet.batch;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.wallet.batch.ofac.OFACFileProcessor;

/**
 * Class TestOFACProcessor.
 */
public class TestOFACProcessor {

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


	/** ofacProcessor. */
	private OFACFileProcessor ofacProcessor;

	/**
	 * testProcess.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testProcess() throws SLBusinessServiceException {
		ofacProcessor.process();	

	}
	
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
		IApplicationProperties mock = (IApplicationProperties)context.getBean("mockApplicationProperties");
		ofacProcessor = (OFACFileProcessor)context.getBean("ofacFileProcessor");
		
		ofacProcessor.setApplicationProperties(mock);
		
	}

}
