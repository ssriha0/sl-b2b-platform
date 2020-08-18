package com.servicelive.wallet.batch;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.wallet.batch.trans.TransFileProcessor;

// TODO: Auto-generated Javadoc
/**
 * Class TestTransFileProcessor.
 */
public class TestTransFileProcessor {

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

	/** transFileProcessor. */
	TransFileProcessor transFileProcessor;

	/**
	 * setup.
	 * 
	 * @return void
	 * @throws SLBusinessServiceException 
	 */
	@Before
	public void setup() throws SLBusinessServiceException {

		transFileProcessor = (TransFileProcessor) getAppContext().getBean("GRP_TRAN.TRAN_FILE_PROCESSOR");
		IApplicationProperties applicationProperties = (IApplicationProperties) getAppContext().getBean("testApplicationProperties");
		transFileProcessor.setApplicationProperties(applicationProperties);
	}

	/**
	 * testTransFileGeneration.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testTransFileGeneration() throws SLBusinessServiceException {

		transFileProcessor.process();
		
	}
}
