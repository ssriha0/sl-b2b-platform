package com.servicelive.wallet.batch;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.lookup.dao.IAccountDao;
import com.servicelive.wallet.batch.vl.BalanceInquiryProcessor;
import com.servicelive.wallet.valuelink.sharp.iso.IIsoRequestProcessor;
import com.servicelive.wallet.valuelink.sharp.mocks.MockSharpGateway;

/**
 * Class TestBalanceInquiry.
 */
public class TestBalanceInquiry {

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


	/** balanceInquiry. */
	private BalanceInquiryProcessor balanceInquiry;
	
	/** sharpGateway. */
	private MockSharpGateway sharpGateway;
	
	/** accountDao. */
	private IAccountDao accountDao;
	
	private IIsoRequestProcessor requestProcesor;

	/**
	 * testProcess.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testProcess() throws SLBusinessServiceException {
		balanceInquiry.process();	

		//expecting four messages in sharp gateway since we sent four messages
		Assert.assertTrue(sharpGateway.getMessages().size() == 4);
					
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
//		IApplicationProperties mock = (IApplicationProperties)context.getBean("mockApplicationProperties");
		sharpGateway = (MockSharpGateway)context.getBean("remoteSharpGateway");
		accountDao = (IAccountDao)context.getBean("mockAccountDao");
		
		balanceInquiry = (BalanceInquiryProcessor)context.getBean("balanceInquiryProcessor");
		
		balanceInquiry.setAccountDao(accountDao);
//		balanceInquiry.setApplicationProperties(mock);

		requestProcesor = (IIsoRequestProcessor)context.getBean("isoRequestProcessorWallet");
	}
	
	@Test
	public void testBalanceInquiryMessage() throws SLBusinessServiceException, IOException {
		long pan = 167777008380336504L;
		byte [] message = this.balanceInquiry.createBalanceInquiryMessage(pan);
		
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//		os.write(message);
//		
		System.out.println( new String(message) );
	}

}
