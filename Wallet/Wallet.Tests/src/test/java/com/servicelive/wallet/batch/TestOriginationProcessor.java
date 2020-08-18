package com.servicelive.wallet.batch;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.ach.balanced.BalancedFileProcessor;
import com.servicelive.wallet.batch.ach.reconciliation.ReconciliationProcessor;
import com.servicelive.wallet.batch.mocks.MockOriginationDao;

/**
 * Class TestOriginationProcessor.
 */
public class TestOriginationProcessor {

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


	/** origination. */
	private BalancedFileProcessor bfp;
	private ReconciliationProcessor rfp;
	
	/** nachaDao. */
	private MockOriginationDao nachaDao;
	
	/**
	 * testProcess.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testProcess() throws SLBusinessServiceException {
		bfp.process();	
		rfp.process();	
		//System.out.print(nachaDao.getAchProcessQueueEntryVO().size());
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
		//IApplicationProperties mock = (IApplicationProperties)context.getBean("mockApplicationProperties");
		//nachaDao = (MockOriginationDao)context.getBean("mockOriginationDao");
		bfp = (BalancedFileProcessor)context.getBean("balancedFileProcessor");
		//IApplicationProperties mock = (IApplicationProperties)context.getBean("mockApplicationProperties");
		//nachaDao = (MockOriginationDao)context.getBean("mockOriginationDao");
		rfp = (ReconciliationProcessor)context.getBean("GRP_ACH.ACH_RECONCILIATION");
		//bfp.setApplicationProperties(mock);
		//bfp.setNachaDao(nachaDao);
		//origination.setApplicationProperties(mock);
		//origination.setNachaDao(nachaDao);
	}
	
}
