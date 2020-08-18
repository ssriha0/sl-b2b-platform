package com.newco.marketplace.api.services.so;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.server.v1_1.SORequestProcessor;
import com.newco.marketplace.api.services.so.v1_1.SOCreateService;
import com.newco.marketplace.api.services.so.v1_1.SOPostService;
import com.newco.marketplace.api.services.so.v1_1.SORescheduleService;
import com.newco.marketplace.api.services.so.v1_1.SOUpdateService;
import com.newco.marketplace.api.services.so.v1_2.SOCancelService;
import com.newco.marketplace.api.services.so.v1_3.SORetrieveService;


/**
 * Test file to check JSON and JSON compatibility of REST APIs
 * 
 * 
 */
public class SORequestProcessorJSONTest {
	
	private static final String RESULT_SUCCESS = "{\"result\" : \"success\"}";
	private static final String SO_REQUEST = "{\"soRequest\" : {}}";
	private static final String SO_ID = "521-5513-5395-12";
	private static final String BUYER_ID = "9000";
	
	
	@InjectMocks
	private com.newco.marketplace.api.server.SORequestProcessor soReqProcessor
			= new com.newco.marketplace.api.server.SORequestProcessor();
	
	@InjectMocks
	private SORequestProcessor soReqProcessor_v1_1
			= new com.newco.marketplace.api.server.v1_1.SORequestProcessor();
	
	@InjectMocks
	private com.newco.marketplace.api.server.v1_2.SORequestProcessor soReqProcessor_v1_2
			= new com.newco.marketplace.api.server.v1_2.SORequestProcessor();
	
	@InjectMocks
	private com.newco.marketplace.api.server.v1_3.SORequestProcessor soReqProcessor_v1_3
			= new com.newco.marketplace.api.server.v1_3.SORequestProcessor();
	
	@Mock
	private HttpServletRequest httpRequest;
	@Mock
	private SOPostService soPostV1Service;
	@Mock
	private SOCreateService createService;
	@Mock
	private SOPostFirmService soPostFirmService;
	@Mock
	private SORetrieveService retrieveService_v1_3;
	@Mock
	private SORescheduleService rescheduleService;
	@Mock
	private SOCancelService soCancelService;
	@Mock
	private SOUpdateService updateService;
	
	@Before
	public void init() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		Mockito.when(httpRequest.getContentType()).thenReturn("application/json");
		Mockito.when(httpRequest.getHeader("Accept")).thenReturn("application/json");
	}

	@Test
	public void testGetResponseForCreateJSONSuccess() {

		String requestJSON = SO_REQUEST;
		String responseJSON = RESULT_SUCCESS;
		
		Mockito.when(createService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseJSON );

		Response response = soReqProcessor_v1_1.getResponseForCreate(BUYER_ID, requestJSON);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseJSON, response.getEntity());
	}

	@Test
	public void testGetResponseForPostJSONSuccess() {

		String requestJSON = SO_REQUEST;
		String responseJSON = RESULT_SUCCESS;

		Mockito.when(soPostV1Service.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseJSON);

		Response response = soReqProcessor_v1_1.getResponseForPost(requestJSON, BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseJSON, response.getEntity());
	}

	@Test
	public void testGetResponseForSoPostFirmJSONSuccess() {

		String requestJSON = SO_REQUEST;
		String responseJSON = RESULT_SUCCESS;

		Mockito.when(soPostFirmService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseJSON);

		Response response = soReqProcessor.getResponseForSoPostFirm(requestJSON, BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals( responseJSON, response.getEntity());
	}

	@Test
	public void testGetResponseForRetrieveJSONSuccess() {

		String responseJSON = RESULT_SUCCESS;

		Mockito.when(retrieveService_v1_3.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseJSON);

		Response response = soReqProcessor_v1_3.getResponseForRetrieve(SO_ID, BUYER_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseJSON, response.getEntity());
	}

	@Test
	public void testGetResponseForRescheduleJSONSuccess() {


		String requestJSON = SO_REQUEST;
		String responseJSON = RESULT_SUCCESS;

		Mockito.when(rescheduleService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseJSON);

		Response response = soReqProcessor_v1_1.getResponseForReschedule(SO_ID, BUYER_ID, requestJSON);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseJSON, response.getEntity());
	}

	@Test
	public void testGetResponseForCancelSOJSONSuccess() {

		String requestJSON = SO_REQUEST;
		String responseJSON = RESULT_SUCCESS;

		Mockito.when(soCancelService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseJSON);

		Response response = soReqProcessor_v1_2.getResponseForCancelSO(BUYER_ID, SO_ID, requestJSON);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseJSON, response.getEntity());
	}

	@Test
	public void testGetResponseForUpdateJSONSuccess() {

		String requestJSON = SO_REQUEST;
		String responseJSON = RESULT_SUCCESS;

		Mockito.when(updateService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseJSON);

		Response response = soReqProcessor_v1_1.getResponseForUpdate(requestJSON, BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals( responseJSON, response.getEntity());
	}
}
