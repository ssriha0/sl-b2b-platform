package com.servicelive.wallet.valuelink.dao;


import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;


/**
 * Class ValueLinkQueueDaoTest.
 */
public class ValueLinkQueueDaoTest {

	/** _context. */
	private static ApplicationContext _context = new ClassPathXmlApplicationContext("com/servicelive/wallet/valuelink/testValueLinkApplicationContext.xml");
	
	/**
	 * setUp.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {

	}
	
	/**
	 * testGetValueLinkEntriesReadyForQueue.
	 * 
	 * @return void
	 */
	@Test
	public void testGetValueLinkEntriesReadyForQueue() {
		
		IValueLinkQueueDao valueLinkQueueDao = (IValueLinkQueueDao)getContext().getBean("valueLinkQueueDao");
		List<ValueLinkEntryVO> entries = valueLinkQueueDao.getValueLinkEntriesReadyForQueue();
		for( ValueLinkEntryVO entry : entries ) {
			System.out.println( 
				entry.getFullfillmentEntryId() + " : "
				+ entry.getPrimaryAccountNumber() + " : "
				+ entry.getFullfillmentGroupId() + " : "
				+ entry.getActionCode() + " : " 
				+ entry.getReconsiledInd() + " : " 
				+ entry.getReconsiledDate());
		}
	}
	
	
	/**
	 * getContext.
	 * 
	 * @return ApplicationContext
	 */
	public static ApplicationContext getContext() {
	
		return _context;
	}

}
