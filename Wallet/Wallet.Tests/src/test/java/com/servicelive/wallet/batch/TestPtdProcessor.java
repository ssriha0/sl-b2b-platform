package com.servicelive.wallet.batch;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.wallet.batch.mocks.MockPTDDao;
import com.servicelive.wallet.batch.ptd.PTDProcessor;

/**
 * Class TestPtdProcessor.
 */
public class TestPtdProcessor {

	/** ptdProcessor. */
	private PTDProcessor ptdProcessor;
	
	/** mockPtdDao. */
	private MockPTDDao mockPtdDao;
	
	
	/**
	 * testProcess.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testProcess() throws SLBusinessServiceException{

		ptdProcessor.process();
		
		Assert.assertEquals(41, mockPtdDao.getPtdEntries().size());
		Assert.assertEquals(41, mockPtdDao.getPtdErrorEntries().size());

	}

	/**
	 * setup.
	 * 
	 * @return void
	 */
	@Before
	public void setup(){
		ApplicationContext context = getAppContext();
		
		IApplicationProperties mock = (IApplicationProperties)context.getBean("mockApplicationProperties");
		mockPtdDao = (MockPTDDao)context.getBean("mockPTDDao");
		
		ptdProcessor = (PTDProcessor)context.getBean("ptdProcessor");
		ptdProcessor.setApplicationProperties(mock);
		ptdProcessor.setPtdDao(mockPtdDao);
		
	}
	
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

}
