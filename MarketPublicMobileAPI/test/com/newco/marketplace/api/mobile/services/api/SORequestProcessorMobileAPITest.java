package com.newco.marketplace.api.mobile.services.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.addNotes.AddNoteRequest;
import com.newco.marketplace.api.mobile.beans.addNotes.AddNoteResponse;
import com.newco.marketplace.api.mobile.beans.addNotes.NoteType;
import com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart.AddInvoiceSOProviderPartRequest;
import com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart.AddInvoiceSOProviderPartResponse;
import com.newco.marketplace.api.mobile.beans.authenticateUser.LoginProviderRequest;
import com.newco.marketplace.api.mobile.beans.authenticateUser.LoginProviderResponse;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.AddEstimateRequest;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.AddEstimateResponse;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.UpdateSOCompletionRequest;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.UpdateSOCompletionResponse;
import com.newco.marketplace.api.mobile.services.AddEstimateService;
import com.newco.marketplace.api.mobile.services.CreateNewPasswordForUserService;
import com.newco.marketplace.api.mobile.services.DocumentDownloadService;
import com.newco.marketplace.api.mobile.services.GetEstimateDetailsService;
import com.newco.marketplace.api.mobile.services.GetProviderSOListService;
import com.newco.marketplace.api.mobile.services.LoginProviderService;
import com.newco.marketplace.api.mobile.services.MobileTimeOnSiteService;
import com.newco.marketplace.api.mobile.services.ProviderAddNoteService;
import com.newco.marketplace.api.mobile.services.ProviderUploadDocumentService;
import com.newco.marketplace.api.mobile.services.SODetailsRetrieveService;
import com.newco.marketplace.api.mobile.services.SOUpdateAppointmentTimeService;
import com.newco.marketplace.api.mobile.services.UpdateSOCompletionService;
import com.newco.marketplace.api.mobile.services.v2_0.AddInvoiceSOProviderPartService;


public class SORequestProcessorMobileAPITest {
	private static final String SO_ID = "521-5513-5395-12";
	private static final String PROVIDER_ID = "1000";
	private static final Integer ROLE_ID = 2000;
	private static final String FIRM_ID = "3000";
	
	@InjectMocks
	private com.newco.marketplace.api.mobile.processor.MobileGenericRequestProcessor mobileGenericRequestProcessor 
			= new com.newco.marketplace.api.mobile.processor.MobileGenericRequestProcessor();
	
	@InjectMocks
	private com.newco.marketplace.api.mobile.processor.v2_0.MobileGenericRequestProcessor mobileGenericRequestProcessor_v2_0 
			= new com.newco.marketplace.api.mobile.processor.v2_0.MobileGenericRequestProcessor();
	
	@InjectMocks
	private com.newco.marketplace.api.mobile.processor.v3_0.MobileGenericRequestProcessor mobileGenericRequestProcessor_v3_0 
			= new com.newco.marketplace.api.mobile.processor.v3_0.MobileGenericRequestProcessor();
	
	@InjectMocks
	private com.newco.marketplace.api.mobile.processor.v3_1.MobileGenericRequestProcessor mobileGenericRequestProcessor_v3_1 
			= new com.newco.marketplace.api.mobile.processor.v3_1.MobileGenericRequestProcessor();
	
	@Mock
	private LoginProviderService loginProviderService;
	@Mock
	private ProviderUploadDocumentService providerUploadDocumentService;
	@Mock
	private CreateNewPasswordForUserService createNewPasswordForUserService;
	@Mock
	private HttpServletRequest httpRequest;	
	@Mock
	private HttpServletResponse httpResponse;
	@Mock
	private MobileTimeOnSiteService timeOnSiteService;
	@Mock
	private SOUpdateAppointmentTimeService updateAppointmentTimeService;
	@Mock
	private SODetailsRetrieveService soDetailsRetrieveService;
	@Mock
	private DocumentDownloadService soDocDownloadService;
	@Mock
	private GetProviderSOListService getProviderSOListService;
	@Mock
	private ProviderAddNoteService providerAddNoteService;
	@Mock
	private UpdateSOCompletionService updateSOCompletionService;
	@Mock
	private AddEstimateService addEstimateService;	
	@Mock
	private GetEstimateDetailsService getEstimateService;
	@Mock
	private AddInvoiceSOProviderPartService addInvoiceSOProviderPartService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);	
	}
	
	@Test
	public void testGetResponseForProviderLogin() {
		
		LoginProviderRequest loginProviderRequest = new LoginProviderRequest();
		loginProviderRequest.setUsername("homesweethometheatre");
		loginProviderRequest.setPassword("Test123!");
		loginProviderRequest.setDeviceId("12345345ggggg");
		
		String responseXML = "<loginProviderResponse><results><result code=\"0000\">User Logged in Successfully</result></results><userDetails>"
				+ "<firmId>10202</firmId><providerId>10254</providerId><firstName>Brian TESTUSERNAME WRAP</firstName><lastName>ScBrian TESTUSER NAME WRAP"
				+ "</lastName><firmName>Home Sweet Home Theatres</firmName><phoneNo>123-456-7891</phoneNo><email>nmanoh0@searshc.com</email><resourceLevel>2"
				+ "</resourceLevel><outhToken>RsTMhIMWY15++YWa9fK3fJibZkuheQMuREQMNLpmDVEx2PVigmnyijdqbUVaTHeI41ifaplnvi1Bq914lJJdZjMKSb3QO9+D1BAypgnkn4k="
				+ "</outhToken><temporaryPassword>false</temporaryPassword><locations> <location><locnType>Business Address</locnType><streetAddress1>"
				+ "PO Ser2viceLive Ave.</streetAddress1><streetAddress2>Suite 123</streetAddress2><aptNo></aptNo><city>Grayslake</city><state>IL</state>"
				+ "<zip>60030</zip><country></country> </location> <location><locnType>Mailing Address</locnType><streetAddress1>PO Ser2viceLive Ave."
				+ "</streetAddress1><streetAddress2>Suite 123</streetAddress2><aptNo></aptNo><city>Grayslake</city><state>IL</state><zip>60030</zip>"
				+ "<country></country> </location></locations></userDetails> </loginProviderResponse>";	

		Mockito.when(loginProviderService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);	
		LoginProviderResponse loginProviderResponse = 
				mobileGenericRequestProcessor.getResponseForProviderLogin(loginProviderRequest);	
		Assert.assertNotNull(loginProviderResponse);		
	}
	
	@Test
	public void testGetResponseForAddNote() {
			
		NoteType noteType = new NoteType();
		noteType.setSubject("Private Note");
		noteType.setNoteBody("Note Added");
		noteType.setPrivateInd(true);
		noteType.setSupportInd(false);
		
		AddNoteRequest addNoteRequest = new AddNoteRequest();	
		addNoteRequest.setNoteType(noteType);
		
		String responseXML = "<addNoteResponse><soId>516-2192-7895-11</soId><results>"
				+ "<result code=\"0000\">Note added successfully.</result></results> </addNoteResponse>";	
		Mockito.when(providerAddNoteService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);	
		AddNoteResponse addNoteResponse = 
				mobileGenericRequestProcessor.getResponseForAddNote(PROVIDER_ID, SO_ID, addNoteRequest);	
		Assert.assertNotNull(addNoteResponse);		
	}
	
	@Test
	public void testAddEstimate() {
			
		final Integer PROVIDER_Id = 9000;
		AddEstimateRequest addEstimateRequest = new AddEstimateRequest();
		String responseXML = "<addEstimateResponse>   <results>     "
				+ "<result code=\"0000\">Estimate Added/Updated successfully for the serviceorder.</result>   "
				+ "</results>   <estimationId>5</estimationId> </addEstimateResponse>";
		Mockito.when(addEstimateService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);
		AddEstimateResponse test = mobileGenericRequestProcessor.AddEstimate(ROLE_ID, FIRM_ID, PROVIDER_Id, SO_ID, addEstimateRequest);
		Assert.assertNotNull(test);	
	}
	
//	@Test
//	public void testGetResponseForSoCompletion() {
//			
//		UpdateSOCompletionRequest updateSOCompletionRequest = new UpdateSOCompletionRequest();
//		updateSOCompletionRequest.setCompletionStatus("Complete");
//		updateSOCompletionRequest.setUpdateAction("Work Started");
//		updateSOCompletionRequest.setResolutionComments("This is a test SOID");
//		
//		String responseXML = "<updateSOCompletionResponse><soId>570-6153-0926-17</soId><results>"
//				+ "<result code=\"0000\">The service order is updated successfully.</result>"
//				+ "</results> </updateSOCompletionResponse>";	
//		Mockito.when(updateSOCompletionService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);	
//		UpdateSOCompletionResponse updateSOCompletionResponse = 
//				mobileGenericRequestProcessor.getResponseForSoCompletion(SO_ID, PROVIDER_ID, updateSOCompletionRequest);	
//		Assert.assertNotNull(updateSOCompletionResponse);		
//	}
	
	@Test
	public void testGetResponseForaddInvoiceSOProviderPart() {
			
		AddInvoiceSOProviderPartRequest addInvoiceSOProviderPartRequest = new AddInvoiceSOProviderPartRequest();
		addInvoiceSOProviderPartRequest.setCurrentTripNo("1");
		
		String responseXML = "<addInvoiceSOProviderPartResponse><results><result code=\"0000\">Success</result>"
				+ "</results><soId>568-9412-3919-10</soId> </addInvoiceSOProviderPartResponse>";	
		Mockito.when(addInvoiceSOProviderPartService.doSubmit(Mockito.any(APIRequestVO.class))).thenReturn(responseXML);	
		AddInvoiceSOProviderPartResponse addInvoiceSOProviderPartResponse =
				mobileGenericRequestProcessor_v2_0.getResponseForaddInvoiceSOProviderPart(PROVIDER_ID, SO_ID, addInvoiceSOProviderPartRequest);	
		Assert.assertNotNull(addInvoiceSOProviderPartResponse);		
	}
}
