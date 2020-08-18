package com.servicelive.wallet;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.wallet.serviceinterface.IWalletBO;

/**
 * This test verifies that spring can load all contexts successfully
 * 
 * @author jjohnson
 * 
 */
public class SpringContextTest {
	
	/**
	 * SpringContextTest.
	 */
	public SpringContextTest() {
		super();
	}
	/**
	 * getAppContext.
	 * 
	 * @return ApplicationContext
	 */
	protected ApplicationContext context = null;
	public ApplicationContext getApplicationContext() {

		MQQueueConnectionFactory x = new MQQueueConnectionFactory();
		
		if (context == null) context = new ClassPathXmlApplicationContext("classpath:/resources/spring/mainApplicationContext.xml");
		return context;
	}
	
	
	@Test
	public void testLoadApplicationContext() {
		IWalletBO wallet = (IWalletBO)this.getApplicationContext().getBean("walletTx");
		Assert.assertNotNull(wallet);
		ILookupBO lookup = (ILookupBO)this.getApplicationContext().getBean("lookup");
		Assert.assertNotNull(lookup);
		IApplicationProperties appProps = (IApplicationProperties)
			this.getApplicationContext().getBean("walletApplicationProperties");
		Assert.assertNotNull(appProps);
	}
}
