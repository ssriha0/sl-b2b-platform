package com.servicelive.wallet.valuelink;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.wallet.serviceinterface.requestbuilder.ValueLinkRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;
import com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao;

// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkBOTest.
 */
public class ValueLinkBOTest {

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

	
	@Test
	public void testNewBuyerPosting() throws SLBusinessServiceException, DataServiceException {
		
		IValueLinkBO valueLink = (IValueLinkBO)getAppContext().getBean("valueLink");
		ILookupBO lookup = (ILookupBO)getAppContext().getBean("lookup");
/*
		IValueLinkDao valueLinkDao = (IValueLinkDao)getAppContext().getBean("valueLinkDao");
		
		boolean pending = valueLinkDao.isActivationRequestPendingForEntity(1L);
		Assert.assertTrue(pending);
		*/
		IValueLinkQueueDao valueLinkQueueDao = (IValueLinkQueueDao)getAppContext().getBean("valueLinkQueueDao");
		valueLinkQueueDao.getValueLinkEntriesReadyForQueue();
		
		AdminLookupVO adminLU = lookup.lookupAdmin();
		
		ValueLinkRequestBuilder builder = new ValueLinkRequestBuilder();
		ValueLinkVO depositFundsRequest = builder.depositBuyerFunds(
				CommonConstants.FUNDING_TYPE_PRE_FUNDED, 
				"SO_TEST", 2L, 
				null, null, "MI", 500.00d);
		depositFundsRequest.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_V_MX);
		
		ValueLinkVO postServiceOrderRequest = builder.postServiceOrder(
				CommonConstants.FUNDING_TYPE_PRE_FUNDED, 
				"SO_TEST", 2L, 
				null, null, "MI", 
				adminLU.getSl1AccountNumber(), 
				450.00d, 50.00d, 0.00d);
		postServiceOrderRequest.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_POST_SO);
		
		valueLink.sendValueLinkRequest(depositFundsRequest);
		valueLink.sendValueLinkRequest(postServiceOrderRequest);
		
	}
}
