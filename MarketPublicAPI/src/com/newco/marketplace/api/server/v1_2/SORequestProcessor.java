/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 12-Mar-2010	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.server.v1_2;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.so.v1_2.SOCancelService;
import com.newco.marketplace.api.services.so.v1_2.SOCreateService;
import com.newco.marketplace.api.services.so.v1_2.SORetrieveService;
import com.newco.marketplace.api.services.so.v1_2.SOUpdateService;
import com.newco.marketplace.api.services.so.v1_2.SmsUnSubscribeService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;

/**
 * This class would act as an intercepter for all the public api services in the
 * application.It implements the "versioning" logic and it extends the base
 * SORequestProcessor class
 * 
 * @author Infosys
 * @version 1.0
 */
// routing all incoming requests for version v1.1 to the SORequestProcessor class
@Path("v1.2")
public class SORequestProcessor extends
		com.newco.marketplace.api.server.v1_1.SORequestProcessor{
	private Logger logger = Logger.getLogger(SORequestProcessor.class);
	 private static final String MEDIA_TYPE_XML_STR = "application/xml";
	 private static final String MEDIA_TYPE_JSON_STR = "application/json";
	protected SORetrieveService retrieveService;
	protected SmsUnSubscribeService unSubscribeService;

	// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;	
	
	protected SOCreateService createService_v1_2;
	protected SOUpdateService updateService_v1_2;

	/*Package : com.newco.marketplace.api.services.so.v1_2.SOCancelService
	 * Added as part of 18041.
	 * */
	protected SOCancelService soCancelService;
	
	
	/**
	 * This method returns a response for a Create service order request with multiple time slots.
	 * 
	 * @param String
	 *            buyerId
	 * @param String
	 *            createRequestXML
	 * @return Response
	 */
	@POST
	@Path("/buyers/{buyer_id}/serviceorderslots")
	// Removed '/' at the end since this URL is conflicting with the Edit SO's
	// URL
	public Response getResponseForCreateWithTimeslots(@PathParam("buyer_id") String buyerId,
			String createRequest) {
		long start = System.currentTimeMillis();

		if (logger.isInfoEnabled()) {
			logger.info("Entering SORequestProcessor.getResponseForCreateWithTimeslots()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setBuyerId(buyerId);
		apiVO.setAcceptHeader(MEDIA_TYPE_XML_STR);
		// Adding the code for enabling the json support
		if (getHttpRequest().getContentType() != null) {
			String contentType = getHttpRequest().getContentType().split(";")[0];
			apiVO.setContentType(contentType);

		}

		if (getHttpRequest().getHeader("Accept") != null
				&& getHttpRequest().getHeader("Accept").equalsIgnoreCase(
						MEDIA_TYPE_JSON_STR)) {
			apiVO.setAcceptHeader(MEDIA_TYPE_JSON_STR);
		}

		apiVO.setRequestType(RequestType.Post);
		createResponse = createService_v1_2.doSubmit(apiVO);
		if (MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader())) {
			createResponse = PublicAPIConstant.XML_VERSION + createResponse;
			}
		if (logger.isInfoEnabled()) {
			logger.info("Leaving SORequestProcessor.getResponseForCreateWithTimeslots()");
		}
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("Inside SORequestProcessor.getResponseForCreateWithTimeslots()..>>Creation Time Taken>>"
					+ (end - start));
		}
		return Response.ok(createResponse).build();
	}

	/**
	 * This method returns a response for a update service order request with multiple time slots.
	 * 
	 * @param String
	 *            soUpdateRequest
	 * @return soUpdateResponse
	 */
	@PUT
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/update")
	public Response getResponseForUpdate(String requestXML,
			@PathParam("buyer_id") String buyerId,
			@PathParam("so_id") String soId) {
		logger.info("Entering SORequestProcessor.getResponseForUpdateWithSlots()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		apiVO.setAcceptHeader(MEDIA_TYPE_XML_STR);
		// Adding the code for enabling the json support
		if (getHttpRequest().getContentType() != null) {
			String contentType = getHttpRequest().getContentType().split(";")[0];
			apiVO.setContentType(contentType);

		}

		if (getHttpRequest().getHeader("Accept") != null
				&& getHttpRequest().getHeader("Accept").equalsIgnoreCase(
						MEDIA_TYPE_JSON_STR)) {
			apiVO.setAcceptHeader(MEDIA_TYPE_JSON_STR);
		}

		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Put);
		String responseXML = updateService_v1_2.doSubmit(apiVO);
		if (MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader())) {
			responseXML = PublicAPIConstant.XML_VERSION + responseXML;
			}
		logger.info("ResponseXml:" + responseXML);
		return Response.ok(responseXML).build();
	}
	
	
	/**
	 * This method returns a response for a get / retrieve service order
	 * request.
	 * 
	 * @param String
	 *            buyerId, String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyer_id}/serviceorders/{so_ids}")
	public Response getResponseForRetrieve(@PathParam("so_ids")
	String soIds, @PathParam("buyer_id")
	String buyerId) {
		if (logger.isInfoEnabled()){
			logger.info("getResponseForRetrieve method started");
		}
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setBuyerId(buyerId);
		apiVO.addProperties(PublicAPIConstant.SO_ID_LIST,soIds);
		apiVO.setAcceptHeader(MEDIA_TYPE_XML_STR);
		// Adding the code for enabling the  json support
		if (getHttpRequest().getContentType()!=null){
		String contentType =getHttpRequest().getContentType().split(";")[0];
		apiVO.setContentType(contentType);
		
		}
		
		if(getHttpRequest().getHeader("Accept") !=null && getHttpRequest().getHeader("Accept").equalsIgnoreCase(MEDIA_TYPE_JSON_STR)){
			apiVO.setAcceptHeader(MEDIA_TYPE_JSON_STR);
		}
		String responseXML = retrieveService.doSubmit(apiVO);
		return Response.ok(responseXML).build();

	}

	/**
	 * This method returns a response for a cancel service order request.
	 * 
	 * @param String
	 *            buyerId, String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@DELETE
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}")
	public Response getResponseForCancel(@PathParam("buyer_id")
	String buyerId, @PathParam("so_id")
	String soId) {
		super.setHttpRequest(httpRequest);
		return super.getResponseForCancel(buyerId, soId);
	}
	/**
	 * This method returns a response for a cancel reschedule service order request.
	 * The request processor delegates the processing to the rescheduleService
	 * class
	 * 
	 * @return
	 */
	@DELETE
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/reschedulerequest")
	public Response getResponseForCancelReschedule(@PathParam("so_id")
	String soId, @PathParam("buyer_id")
	String buyerId) {
		if (logger.isInfoEnabled()){
			logger.info("Entering SORequestProcessor.getResponseForReschedule()");
		}
		super.setHttpRequest(httpRequest);
		return(super.getResponseForCancelReschedule(soId,buyerId));
	}
	/**
	 * This method returns a response for a cancel reschedule service order request.
	 * The request processor delegates the processing to the rescheduleService
	 * class -- This is provider API
	 * 
	 * @return
	 */
	@DELETE
	@Path("/providers/{providerId}/serviceorders/{soId}/reschedulerequest")
	public Response getResponseForProviderCancelReschedule(@PathParam("soId")
	String soId, @PathParam("providerId")
	String providerId) {
		if (logger.isInfoEnabled()){
			logger.info("Entering SORequestProcessor.getResponseForProviderCancelReschedule()");
		}
		super.setHttpRequest(httpRequest);
		return(super.getResponseForProviderCancelReschedule(soId,providerId));
	}
	/**
	 * This method returns a response for a search service order request.
	 * 
	 * @param String buyerResourceId
	 * @return Response
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyer_id}/serviceorders/search/filters")
	public Response getResponseForSearch(@PathParam("buyer_id")
	String buyerId) {
		logger.info("Entering SORequestProcessor.getResponseForSearch()");
		super.setHttpRequest(httpRequest);
		return(super.getResponseForSearch(buyerId));
	}
	/**
	 * This method returns a response for a get / retrieve service order
	 * request.
	 * 
	 * @param String
	 *            buyerResourceId, String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{providerId}/serviceorders/{soId}")
	public Response getResponseForProviderRetrieve(@PathParam("soId")
	String soId, @PathParam("providerId")
	String providerId) {
		if (logger.isInfoEnabled()){
			logger.info("getResponseForRetrieve method started");
		}
		super.setHttpRequest(httpRequest);
		return(super.getResponseForProviderRetrieve(soId,providerId));

	}
	/**
	 * This method returns the response for deleting documents from a SO.	
	 * @return Response
	 * URL: /serviceorders/100-0039-3120-19/attachments/test1.gif
	 */
	@SuppressWarnings("unchecked")
	@DELETE
	@Path("/buyers/{buyer_id}/serviceorders/{soid}/attachments/{fileid}")
	public Response getResponseForDeleteSODoc(@PathParam("buyer_id")
	String buyerId, @PathParam("soid")
	String soId, @PathParam("fileid")
	String fileName) {
		logger.info("Entering SORequestProcessor.getResponseForDeleteSODoc()");
		super.setHttpRequest(httpRequest);
		return(super.getResponseForDeleteSODoc(buyerId,soId,fileName));
	}

	/**
	 * This method reads documents from SO.	
	 * @param String buyerId
	 * @param soId
	 * @param fileName
	 *  
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyer_id}/serviceorders/{soid}/attachments/{fileId}")
	public void getResponseForReadSODoc(@PathParam("buyer_id")
	String buyerId, @PathParam("soid")
	String soId, @PathParam("fileId")
	String fileName) throws IOException {
		logger.info("Entering SORequestProcessor.getResponseForReadSODoc()");
		super.setHttpRequest(httpRequest);
		super.getResponseForReadSODoc(buyerId,soId,fileName);
		logger.info("Leaving SORequestProcessor.getResponseForReadSODoc()");
	}

	
	/**
	 * This method returns a response for a search service order request by provider.
	 * 
	 * @param String providerResourceId
	 * @return Response
	 * Request URL : http://localhost/public/v1_1/providerresources/22203/serviceorders/search&pageSize=10&status=Closed&pageNumber=1&page_size_set=10&createdStartDate=11042009&createdEndDate=12092009
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{providerId}/resources/{resourceId}/serviceorders/search/filters")
	//providers/{providerId}/serviceorders/search
	public Response getResponseForSearchbyProvider(@PathParam("providerId")
	String providerId,@PathParam("resourceId") String resourceId) {
		logger
				.info("Entering SORequestProcessor.getResponseForSearchbyProvider()");
		super.setHttpRequest(httpRequest);
		return(super.getResponseForSearchbyProvider(providerId,resourceId));
	}
	/**
	 * This method returns a response for a Create service order request.
	 * 
	 * @param String buyerId 
	 * @param String createRequestXML
	 * @return Response
	 */
	@POST
	@Path("/providers/sms/unsubscription")
	
	public Response getResponseForSmsUnSubscription(String unSubscribeRequest) {
		logger.info("Entering SORequestProcessor.getResponseForSMSUnSubscription()-->Request xml:"+unSubscribeRequest);
		String unsubscribeResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(unSubscribeRequest);
		apiVO.setRequestType(RequestType.Post);
		unsubscribeResponse = unSubscribeService.doSubmit(apiVO);
		unsubscribeResponse = PublicAPIConstant.XML_VERSION + unsubscribeResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving SORequestProcessor.getResponseForSmsUnSubscription()");
		}
		return Response.ok(unsubscribeResponse).build();
	}
	
	
	/**
	 * Handles the API request for Cancel SO request. (SL 18041)
	 * @param buyerId : buyer Id passed through request url
	 * 	       soId : service order id passed through request url
	 *         requestXML : Request XML which will actually contains all the
	 *         parameters to cancel the SO.
	 *  @return : Response - response object after processing cancel request processing.
	 * */
	@PUT
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/cancelSO")
	public Response getResponseForCancelSO(@PathParam("buyer_id") String buyerId, @PathParam("so_id") String soId, String requestXML){
		if (logger.isInfoEnabled()) {
			logger.info("Entering SORequestProcessor.getResponseForCancel()");
		}
		String cancelResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		
		apiVO.setAcceptHeader(MEDIA_TYPE_XML_STR);
		// Adding the code for enabling the  json support
		if (getHttpRequest().getContentType()!=null){
		String contentType =getHttpRequest().getContentType().split(";")[0];
		apiVO.setContentType(contentType);
		
		}
		
		if(getHttpRequest().getHeader("Accept") !=null && getHttpRequest().getHeader("Accept").equalsIgnoreCase(MEDIA_TYPE_JSON_STR)){
			apiVO.setAcceptHeader(MEDIA_TYPE_JSON_STR);
		}
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Put);
		cancelResponse = soCancelService.doSubmit(apiVO);
		if(MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader()))
		cancelResponse = PublicAPIConstant.XML_VERSION + cancelResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving SORequestProcessor.getResponseForCancel()");
		}
		return Response.ok(cancelResponse).build();
	}
	
	public SORetrieveService getRetrieveService() {
		return retrieveService;
	}

	public void setRetrieveService(SORetrieveService retrieveService) {
		this.retrieveService = retrieveService;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public SmsUnSubscribeService getUnSubscribeService() {
		return unSubscribeService;
	}

	public void setUnSubscribeService(SmsUnSubscribeService unSubscribeService) {
		this.unSubscribeService = unSubscribeService;
	}
	
	public SOCancelService getSoCancelService() {
		return soCancelService;
	}

	public void setSoCancelService(SOCancelService soCancelService) {
		this.soCancelService = soCancelService;
	}



	public SOCreateService getCreateService_v1_2() {
		return createService_v1_2;
	}



	public void setCreateService_v1_2(SOCreateService createService_v1_2) {
		this.createService_v1_2 = createService_v1_2;
	}

	public SOUpdateService getUpdateService_v1_2() {
		return updateService_v1_2;
	}

	public void setUpdateService_v1_2(SOUpdateService updateService_v1_2) {
		this.updateService_v1_2 = updateService_v1_2;
	}
}
