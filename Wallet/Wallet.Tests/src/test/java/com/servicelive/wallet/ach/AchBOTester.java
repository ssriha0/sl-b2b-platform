package com.servicelive.wallet.ach;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// TODO: Auto-generated Javadoc
/**
 * Class AchBOTester.
 */
public class AchBOTester {

	/** context. */
	private static ApplicationContext context;

	// @Test
	// public void testCreateAchQueue()throws Exception {
	// IAchRequestBuilder builder = (IAchRequestBuilder)getAppContext().getBean("RequestBuilder");
	// AchProcessQueueEntryVO queueEntry = builder.createACHDepositRequest(523619938, 22, 22.00, 10);
	//		
	// IAchBO achBO = (IAchBO)getAppContext().getBean("AchBO");
	//		
	// achBO.createACHEntry(queueEntry);
	// }

	/**
	 * getAppContext.
	 * 
	 * @return ApplicationContext
	 */
	public ApplicationContext getAppContext() {

		if (context == null) context = new ClassPathXmlApplicationContext("com/servicelive/wallet/ach/testAchQueueApplicationContext.xml");

		return context;
	}
}
