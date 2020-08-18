package com.servicelive.wallet.valuelink.sharp;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// TODO: Auto-generated Javadoc
/**
 * Class SharpGatewayTest.
 */
public class SharpGatewayTest {

	/** context. */
	private static ApplicationContext context;

	/**
	 * getAppContext.
	 * 
	 * @return ApplicationContext
	 */
	public ApplicationContext getAppContext() {

		if (context == null) context = new ClassPathXmlApplicationContext("com/servicelive/wallet/valuelink/testValueLinkApplicationContext.xml");
		return context;
	}

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
	 * testSendMessage.
	 * 
	 * @return void
	 * 
	 * @throws InterruptedException 
	 */
	@Test
	public void testSendMessage() throws InterruptedException {

		ISharpGateway sharpGateway = (ISharpGateway) getAppContext().getBean("sharpGateway");

		byte[] message = new byte[] { 1, 2, 3, 4, 5 };
		sharpGateway.sendMessage(message);

		Thread.sleep(10000);
	}

}
