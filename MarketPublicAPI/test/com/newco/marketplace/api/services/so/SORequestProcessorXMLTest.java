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
import com.newco.marketplace.api.services.so.v1_1.GetSOPriceDetailsService;
import com.newco.marketplace.api.services.so.v1_1.SOAddNoteService;
import com.newco.marketplace.api.services.so.v1_1.SOCancelRescheduleService;
import com.newco.marketplace.api.services.so.v1_1.SOCompleteSOService;
import com.newco.marketplace.api.services.so.v1_1.SOCreateService;
import com.newco.marketplace.api.services.so.v1_1.SOEditService;
import com.newco.marketplace.api.services.so.v1_1.SOPostService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderRescheduleResponseService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderRescheduleService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderRetrieveService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderTimeOnSiteService;
import com.newco.marketplace.api.services.so.v1_1.SORescheduleService;
import com.newco.marketplace.api.services.so.v1_1.SORetrieveSpendLimitService;
import com.newco.marketplace.api.services.so.v1_1.SOSearchByProviderService;
import com.newco.marketplace.api.services.so.v1_1.SOSearchService;
import com.newco.marketplace.api.services.so.v1_1.SOUpdateEstimateStatusService;
import com.newco.marketplace.api.services.so.v1_1.SOUpdateService;
import com.newco.marketplace.api.services.so.v1_2.SOCancelService;
import com.newco.marketplace.api.services.so.v1_3.SORetrieveService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;

/**
 * 
 * Test file to check XML and XML compatibility of REST APIs
 * 
 */
public class SORequestProcessorXMLTest {
	
	private static final String SO_ID = "521-5513-5395-12";
	private static final String BUYER_ID = "9000";
	private static final String PROVIDER_ID = "1111";
	private static final String RESOURCE_ID = "1000";

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
	@Mock
	private SORetrieveSpendLimitService retrieveSpendLimitService;
	@Mock
	private SOCancelRescheduleService cancelRescheduleService;
	@Mock
	private SOProviderRescheduleService providerRescheduleService;
	@Mock
	private SOEditService soEditService;
	@Mock
	private SOCancelService cancelService;
	@Mock
	private SOSearchService searchService;
	@Mock
	private SOProviderRetrieveService soProviderRetrieveService;
	@Mock
	private SOCompleteSOService completeSOService;
	@Mock
	private SOAddNoteService addNoteService;
	@Mock
	private SOUpdateEstimateStatusService soUpdateEstimateStatusService;
	@Mock
	private GetSubStatusService subStatusService;
	@Mock
	private GetSOPriceDetailsService soPriceService;
	@Mock
	private SOSearchByProviderService searchByProviderService;
	@Mock
	private SOProviderRescheduleResponseService soProviderRescheduleResponseService;
	@Mock
	private SOReassignService soReassignService;
	@Mock
	private DeleteSODocService deleteSODocService;
	@Mock
	private AddSODocService addSODocService;
	@Mock
	private SOProviderTimeOnSiteService timeOnSiteService;
	
	@Before
	public void init() throws Exception {
	
		MockitoAnnotations.initMocks(this);
		Mockito.when(httpRequest.getContentType()).thenReturn("application/xml");
		Mockito.when(httpRequest.getHeader("Accept")).thenReturn("application/xml");
	}

	@Test
	public void testGetResponseForCreate() {

		String requestXML = "<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "<identification type=\"BuyerResourceId\"><id>10389</id></identification><serviceorder version=\"1.0\">"
				+ "<sectionGeneral><overview>Determine the feasibility, operational issues, duration workarounds and cost involved with installation of the Z-wave water shut-off from Dynaquip at customers home."
				+ " The plumber will arrive at the scheduled time and answer the attached questions based on observations at the individual home. Estimate 1 hour or less including standard drive time.</overview>"
				+ "<buyerTerms>Provider must arrive at customers home at the scheduled date and time and can not re-schedule.&nbsp; All survey questions must be completed."
				+ " Scan and upload:(1) the completed survey and (2) the signed Service order within 2 business days of completion."
				+ " Provider must use IVR to check in and Check out of job site.</buyerTerms><specialInstructions/></sectionGeneral>"
				+ "<scopeOfWork><mainCategoryID>1</mainCategoryID><skus><sku>RepeatRepair</sku></skus></scopeOfWork><serviceLocation><locationType>Commercial</locationType><locationName/><address1>5075 Eves Place</address1><city>dallas</city>"
				+ "<state>IL</state><zip>60179</zip><note/></serviceLocation><schedule><scheduleType>range</scheduleType><serviceDateTime1>2017-11-02T10:00:00Z</serviceDateTime1>"
				+ "<serviceDateTime2>2017-11-21T12:00:00Z</serviceDateTime2><confirmWithCustomer>true</confirmWithCustomer></schedule>"
				+ "<pricing><priceModel>NAME_PRICE</priceModel><laborSpendLimit>40</laborSpendLimit><partsSpendLimit>50</partsSpendLimit></pricing><contacts><contact><contactLocnType>Service</contactLocnType>"
				+ "<firstName>Charles</firstName><lastName>Gonzales</lastName><primaryPhone><phoneType>Mobile</phoneType><number>4578789791</number><extension>2345622221</extension></primaryPhone><altPhone><phoneType>Other</phoneType><number>4578789791</number>"
				+ "<extension>2345622211</extension></altPhone></contact></contacts><customReferences><customRef><name>OrderNumber</name><value>Gireesh@1</value></customRef><customRef><name>UnitNumber</name>"
				+ "<value>Gireesh@1</value></customRef><customRef><name>ISP_ID</name><value>190834</value></customRef><customRef><name>CoverageTypeLabor</name><value>IW</value></customRef><customRef>"
				+ "<name>CoverageTypeParts</name><value>IW</value></customRef><customRef><name>PaymentMethod</name><value>IW</value></customRef><customRef><name>StatusCode</name>"
				+ "<value>y</value></customRef><customRef><name>RepeatRepair</name><value>y</value></customRef><customRef><name>RepeatRepairUnitNumber</name><value>y</value></customRef>"
				+ "<customRef><name>RepeatRepairServiceOrderNumber</name><value>y</value></customRef><customRef><name>StatusCode</name><value>y</value></customRef></customReferences></serviceorder></soRequest>";
		String responseXML = "<result>Success</result>";

		Mockito.when(createService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);

		Response response = soReqProcessor_v1_1.getResponseForCreate(BUYER_ID, requestXML);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(PublicAPIConstant.XML_VERSION + responseXML, response.getEntity());
	}

	@Test
	public void testGetResponseForPost() {

		String requestXML = "<postRequest version=\"1.0\" xsi:schemaLocation=\"http://www.servicelive.com/namespaces/postRequest postRequest.xsd\" xmlns=\"http://www.servicelive.com/namespaces/postRequest\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<providerRouteInfo template=\"\" version=\"1.0\"><maxDistance>50</maxDistance><minRating>1.0</minRating>"
				+ "<languages><language>English</language><language>Spanish</language></languages>"
				+ "<specificProviders><resourceID>38747</resourceID></specificProviders></providerRouteInfo></postRequest>";
		String responseXML = "<result>Success</result>";

		Mockito.when(soPostV1Service.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForPost(requestXML, BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(PublicAPIConstant.XML_VERSION + responseXML, response.getEntity());
	}

	@Test
	public void testGetResponseForSoPostFirm() {

		String requestXML = "<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "</soRequest>";
		String responseXML = "<result>Success</result>";

		Mockito.when(soPostFirmService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor.getResponseForSoPostFirm(requestXML, BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(PublicAPIConstant.XML_VERSION + responseXML, response.getEntity());
	}

	@Test
	public void testGetResponseForRetrieve() {

		String responseXML = "<result>Success</result>";
		Mockito.when(retrieveService_v1_3.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_3.getResponseForRetrieve(SO_ID, BUYER_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForReschedule() {

		String requestXML = "<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "</soRequest>";
		String responseXML = "<result>Success</result>";

		Mockito.when(rescheduleService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForReschedule(SO_ID, BUYER_ID, requestXML);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}

	@Test
	public void testGetResponseForCancelSO() {

		String requestXML = "<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "</soRequest>";
		String responseXML = "<result>Success</result>";
		Mockito.when(soCancelService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_2.getResponseForCancelSO(BUYER_ID, SO_ID, requestXML);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(PublicAPIConstant.XML_VERSION + responseXML, response.getEntity());
	}

	@Test
	public void testGetResponseForUpdate() {

		String requestXML = "<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "</soRequest>";
		String responseXML = "<result>Success</result>";
		Mockito.when(updateService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForUpdate(requestXML, BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(PublicAPIConstant.XML_VERSION + responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForSpendLimitIncrease() {

		String responseXML = "<result>Success</result>";
		Mockito.when(retrieveSpendLimitService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForSpendLimitIncrease(BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForCancelReschedule() {

		String responseXML = "<result>Success</result>";
		Mockito.when(cancelRescheduleService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForCancelReschedule(BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForProviderCancelReschedule() {

		String responseXML = "<result>Success</result>";
		Mockito.when(cancelRescheduleService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForProviderCancelReschedule(BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForProviderReschedule() {

		String requestXML = "<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "</soRequest>";
		String responseXML = "<result>Success</result>";
		Mockito.when(providerRescheduleService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForProviderReschedule(BUYER_ID, SO_ID, requestXML);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForEdit() {

		String requestXML = "<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "</soRequest>";
		String responseXML = "<result>Success</result>";
		Mockito.when(soEditService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForEdit(requestXML, BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(PublicAPIConstant.XML_VERSION + responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForSearch() {

		String responseXML = "<result>Success</result>";
		Mockito.when(searchService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForSearch(BUYER_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(PublicAPIConstant.XML_VERSION + responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForProviderRetrieve() {

		String responseXML = "<result>Success</result>";
		Mockito.when(soProviderRetrieveService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForProviderRetrieve(SO_ID, PROVIDER_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForCompleteSO() {

		String responseXML = "<result>Success</result>";
		Mockito.when(completeSOService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForCompleteSO(SO_ID, PROVIDER_ID,"completeSORequest");
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForAddNote() {

		String requestXML = "<addNoteRequest version=\"1.0\" xsi:schemaLocation=\"http://www.servicelive.com/namespaces/addNoteRequest.xsd\""
				+ " xmlns=\"http://www.servicelive.com/namespaces/addNoteRequest\" "
				+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><identification type=\"BuyerResourceId\">      "
				+ "<id>14</id>   </identification>   <note private=\"false\">      <subject>TestSubject</subject>     "
				+ " <noteBody>Note Added</noteBody>   </note></addNoteRequest>";
		
		String responseXML = "<result>Success</result>";
		Mockito.when(addNoteService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForAddNote(PROVIDER_ID, SO_ID, requestXML);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testUpdateEstimateStatus() {

		String requestXML = "<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "</soRequest>";
		String responseXML = "<result>Success</result>";
		Mockito.when(soUpdateEstimateStatusService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.updateEstimateStatus(SO_ID, BUYER_ID, requestXML);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(PublicAPIConstant.XML_VERSION + responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForSubstatus() {

		String responseXML = "<result>Success</result>";
		Mockito.when(subStatusService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForSubstatus(BUYER_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetPriceDetails() {

		String responseXML = "<result>Success</result>";
		Mockito.when(soPriceService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getPriceDetails(BUYER_ID, SO_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForSearchbyProvider() {

		String responseXML = "<result>Success</result>";
		Mockito.when(searchByProviderService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForSearchbyProvider(PROVIDER_ID,RESOURCE_ID);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForProviderRescheduleResponse() {

		String soproviderRescheduleRequest ="<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "</soRequest>";
		String responseXML = "<result>Success</result>";
		Mockito.when(soProviderRescheduleResponseService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForProviderRescheduleResponse(SO_ID, PROVIDER_ID, soproviderRescheduleRequest);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testReassignSO() {

		String soPosrequestXml ="<soRequest xmlns=\"http://www.servicelive.com/namespaces/soRequest\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ "</soRequest>";
		String responseXML = "<result>Success</result>";
		Mockito.when(soReassignService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.reassignSO(SO_ID, PROVIDER_ID, soPosrequestXml);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForDeleteSODoc() {

		String responseXML = "<result>Success</result>";
		Mockito.when(deleteSODocService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForDeleteSODoc(SO_ID, PROVIDER_ID, "fileName");
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testGetResponseForAddSODoc() {

		String responseXML = "<result>Success</result>";
		Mockito.when(addSODocService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.getResponseForAddSODoc(BUYER_ID, SO_ID, "soAddDocRequest");
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
	
	@Test
	public void testAddTimeOnSite() {

		String responseXML = "<result>Success</result>";
		Mockito.when(timeOnSiteService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		Response response = soReqProcessor_v1_1.addTimeOnSite(SO_ID, PROVIDER_ID, "soTimeOnSiteRequest");
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(responseXML, response.getEntity());
	}
}