package com.servicelive.wallet.valuelink.sharp.iso;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO;

// TODO: Auto-generated Javadoc
/**
 * Class IsoRequestProcessorTest.
 */
public class IsoRequestProcessorTest {

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
	 * testProcessRequest.
	 * 
	 * @return void
	 * 
	 * @throws UnknownMessageTypeException 
	 * @throws StringParseException 
	 * @throws DataServiceException 
	 * @throws Exception 
	 */
	@Test
	public void testProcessRequest() throws UnknownMessageTypeException, StringParseException, DataServiceException, Exception {

		// this.applicationProperties
		// .getPropertyValue(FullfillmentConstants.SL_STORE_NO);

		IsoMessageVO isoReq = new IsoMessageVO();
		isoReq.setMessageIdentifier(CommonConstants.ACTIVATION_RELOAD_REQUEST);
		isoReq.setFullfillmentEntryId(12345l);
		isoReq.setTransAmount(0.0d);
		isoReq.setStoreNo("12345");
		IIsoRequestProcessor iso = (IIsoRequestProcessor) getAppContext().getBean("isoRequestProcessor");
		iso.processRequest(isoReq);
	}
	
	
	@Test
	public void testProcessResponse() throws DataServiceException, SLBusinessServiceException {
		
		ValueLinkEntryVO vo = new ValueLinkEntryVO();
		
		System.out.println(vo.getTimeStamp());
		Assert.assertNotNull(vo.getTimeStamp());
	}
}
