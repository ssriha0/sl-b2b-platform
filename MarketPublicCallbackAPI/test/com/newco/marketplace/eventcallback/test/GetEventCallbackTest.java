package com.newco.marketplace.eventcallback.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.newco.marketplace.business.businessImpl.EventCallbackBOImpl;
import com.newco.marketplace.persistence.iDao.IEventCallbackDao;
import com.newco.marketplace.persistence.daoImpl.EventCallbackDaoImpl;


public class GetEventCallbackTest {
	private EventCallbackBOImpl eventCallbackBO;
	private IEventCallbackDao eventCallbackDao;
	Map<String, String> techHubdetails;
	private static final Logger LOGGER = Logger.getLogger(GetEventCallbackTest.class);

	@Before
	public void setUp() {
		eventCallbackBO = new EventCallbackBOImpl();
		eventCallbackDao = mock(EventCallbackDaoImpl.class);
		eventCallbackBO.setEventCallbackDao(eventCallbackDao);
		techHubdetails = new HashMap<String, String>();
		techHubdetails.put("techhub.auth.token", "TU1IU2VydmljZTpUVzFvSkdWU2RpRmpKZz09");
		techHubdetails.put("techhub.auth.url", "https://api-st.shs-core.com/sis/v1/auth");
		techHubdetails.put("techhub.call-close.url",
				"https://api-st.shs-core.com/sis/v1/orders/soId/afterattempt/attempt");

	}

	@Test
	public void getEventCallbackData() {

		Map<String, String> results = new HashMap<String, String>();
		List<String> appKeys = new ArrayList<String>();
		appKeys.add("techhub.auth.token");
		appKeys.add("techhub.auth.url");
		appKeys.add("techhub.call-close.url");
		try {
			when(eventCallbackDao.getEventCallbackDetails(appKeys)).thenReturn(techHubdetails);
			results = eventCallbackBO.getEventCallbackDetails(appKeys);
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for getEventCallbackData" + e.getMessage());
		}
		Assert.assertEquals("TU1IU2VydmljZTpUVzFvSkdWU2RpRmpKZz09", results.get("techhub.auth.token"));

		Assert.assertEquals("https://api-st.shs-core.com/sis/v1/auth", results.get("techhub.auth.url"));

		Assert.assertEquals("https://api-st.shs-core.com/sis/v1/orders/soId/afterattempt/attempt",
				results.get("techhub.call-close.url"));

	}

	@Test
	public void getEventCallbackDataIfKeyNotFound() {

		Map<String, String> results = new HashMap<String, String>();
		List<String> appKeys = new ArrayList<String>();
		appKeys.add("techhub.auth.token1");
		appKeys.add("techhub.auth.url2");
		appKeys.add("techhub.call-close.url3");
		try {
			when(eventCallbackDao.getEventCallbackDetails(appKeys)).thenReturn(techHubdetails);
			results = eventCallbackBO.getEventCallbackDetails(appKeys);
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for getEventCallbackDataIfKeyNotFound" + e.getMessage());
		}
		Assert.assertNull(results.get("techhub.auth.token1"));

		Assert.assertNull(results.get("techhub.auth.url2"));

		Assert.assertNull(results.get("techhub.call-close.url3"));

	}
}
