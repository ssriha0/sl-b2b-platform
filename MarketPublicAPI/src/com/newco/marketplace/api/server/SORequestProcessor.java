/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mock.SOMockResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.search.SearchFirmsForAutoPostService;
import com.newco.marketplace.api.services.so.ReportProblemService;
import com.newco.marketplace.api.services.so.ResolveProblemOnSOService;
import com.newco.marketplace.api.services.so.SOAcceptOfferService;
import com.newco.marketplace.api.services.so.SOAddNoteService;
import com.newco.marketplace.api.services.so.SOCancelReasonService;
import com.newco.marketplace.api.services.so.SOCancelService;
import com.newco.marketplace.api.services.so.SOCloseService;
import com.newco.marketplace.api.services.so.SOCreateService;
import com.newco.marketplace.api.services.so.SOPostFirmService;
import com.newco.marketplace.api.services.so.SOPostService;
import com.newco.marketplace.api.services.so.SORejectService;
import com.newco.marketplace.api.services.so.SOReleaseService;
import com.newco.marketplace.api.services.so.SORescheduleReasonService;
import com.newco.marketplace.api.services.so.SORescheduleService;
import com.newco.marketplace.api.services.so.SORetrieveService;
import com.newco.marketplace.api.services.so.SOSearchService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.validators.RequestValidator;
import com.newco.marketplace.api.utils.validators.ResponseValidator;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CGetFirmDetailsResponse;
import com.sears.os.service.ServiceConstants;


/**
 * This class would act as an intercepter for all the public api services in the
 * application.
 * 
 * @author Infosys
 * @version 1.0
 */
//routing all incoming requests to the SORequestProcessor class
@Path("")
public class SORequestProcessor {
	private Logger logger = Logger.getLogger(SORequestProcessor.class);
	private static final String MEDIA_TYPE_XML_STR = "application/xml"; 
	private static final String MEDIA_TYPE_JSON_STR = "application/json";
	// services
	protected SOCreateService createService;
	protected SOSearchService searchService;
	protected SORetrieveService retrieveService;
	protected SOCancelService cancelService;
	protected SOPostService postService;
	protected SORescheduleService rescheduleService;
	protected SOAddNoteService addNoteService;
	protected SOAcceptOfferService acceptOfferService;
	protected ReportProblemService reportProblemService;
	// validators
	protected RequestValidator requestValidator;
	protected ResponseValidator responseValidator;
	protected SOCloseService soCloseService;
	protected SOReleaseService soReleaseService;
	protected ResolveProblemOnSOService resolveProblemService;
	protected SORejectService rejectSOService;
	// Required for retrieving client IP address. Commented in code since
	// 		functionality is not required for this release.
	//Required for retrieving attributes from Get/Delete URL
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;
	
	//SL-20678: Post SO to firms
    protected SOPostFirmService soPostFirmService;
    //SLT-324
    protected SORescheduleReasonService rescheduleReasonService;
    //SLT-325
    protected SOCancelReasonService soCancelReasonService;
    //SLBUYER-133
    protected SearchFirmsForAutoPostService searchFirmsForAutoPostService;
    
    
  
    
	/**
	 * This method returns the response for a create service order request.
	 * @param String soCreateRequest
	 * @return Response
	 */
	@POST
	@Path("/serviceorder/create/")
	public Response getResponseForCreate(String soCreateRequest) {
		logger.info("Entering SORequestProcessor.getResponseForCreate()");
		String soCreateResponse = null;
		HashMap<String, Object>	soCreateResponseMap =null;
		int logId = 0;
		try {
			//String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soCreateRequest, PublicAPIConstant.REQUEST_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				logId = requestValidator.logRequest(ipAddress, soCreateRequest);
				soCreateResponseMap = createService
								.dispatchCreateServiceRequest(soCreateRequest);

				if (null != soCreateResponseMap.get(PublicAPIConstant.ERROR_KEY)) {
					logger.error("An Invalid mapping case occurred for createMapper::"
						+ soCreateResponseMap.get(PublicAPIConstant.ERROR_KEY));
					soCreateResponse = responseValidator.getFailureResponse(
					(String) soCreateResponseMap.get(PublicAPIConstant.ERROR_KEY),
												ServiceConstants.USER_ERROR_RC);
				} else {
					soCreateResponse = (String) soCreateResponseMap
									.get(PublicAPIConstant.SERVICEORDER_KEY);
				}
			} else {
				soCreateResponse = responseValidator.getFailureResponse(
							validationStatus, ServiceConstants.USER_ERROR_RC);
			}
		}catch (NullPointerException nullException) {
			soCreateResponse = responseValidator.getFailureResponse(
					CommonUtility.getMessage(PublicAPIConstant.EXCEPTION_MESSAGE),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Null Pointer Exception Occurred:");
		}/* catch (BusinessServiceException serviceException) {
			soCreateResponse = responseValidator.getFailureResponse(
												serviceException.getMessage(),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Business Service Exception: "
												+ serviceException.getMessage());
		}*/ catch (Exception e) {
			soCreateResponse = responseValidator.getFailureResponse(
					CommonUtility.getMessage(PublicAPIConstant.EXCEPTION_MESSAGE),
											ServiceConstants.SYSTEM_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		} finally {
			logger.info("Going to validate the response xml"
					+ " for CreateServiceOrder: " + soCreateResponse);
			responseValidator.validateResponseXML(soCreateResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
											PublicAPIConstant.SORESPONSE_XSD);
			if (logId != 0) {
				responseValidator.logResponse(soCreateResponse, logId);
			}
		}
		logger.info("Leaving SORequestProcessor.getResponseForCreate()");
		return Response.ok(soCreateResponse).build();
	}


	/**
	 * This method returns a response for  a get service order request.
	 * @param String soRetrieveRequest, String soId
	 * @return Response
	 */

	@POST
	@Path("/serviceorder/get/{id}")
	public Response getResponseForRetrieve(@PathParam("id")
			String soId, String soRetrieveRequest) {
		logger.info("Entering SORequestProcessor.getResponseForRetrieve()");
		String soRetrieveResponse = null;
		HashMap<String, Object>	soRetrieveResponseMap =null;
		int logId = 0;
		try {
			//String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;
			//If soId not specified, show soId Missing Response
			if(!"".equals(soId)){
			String validationStatus = requestValidator
			.validateRequest(ipAddress, soRetrieveRequest,
					PublicAPIConstant.REQUEST_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				logId = requestValidator.logRequest(ipAddress,
						soRetrieveRequest);
				soRetrieveResponseMap = retrieveService
				.dispatchRetrieveServiceRequest(soRetrieveRequest, soId);
				if (null != soRetrieveResponseMap.get(PublicAPIConstant.ERROR_KEY)) {
					logger.error("An Invalid mapping case occurred for Retrieve Mapper::"
						+ soRetrieveResponseMap.get(PublicAPIConstant.ERROR_KEY));
					soRetrieveResponse = responseValidator.getFailureResponse(
					(String) soRetrieveResponseMap.get(PublicAPIConstant.ERROR_KEY),
												ServiceConstants.USER_ERROR_RC);
				} else {
					soRetrieveResponse = (String) soRetrieveResponseMap
									.get(PublicAPIConstant.SERVICEORDER_KEY);
				}
				} else {
				soRetrieveResponse = responseValidator
				.getFailureResponse(validationStatus,ServiceConstants.USER_ERROR_RC);
			}
			}else{
				soRetrieveResponse = responseValidator
						.getFailureResponse(CommonUtility
						.getMessage(PublicAPIConstant.SOID_MISSING_ERROR_CODE),
												ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Response for RetrieveServiceOrder " 
												+"is: " + soRetrieveResponse);
		} catch (NullPointerException nullException) {
			soRetrieveResponse = responseValidator.getFailureResponse(
					CommonUtility.getMessage(PublicAPIConstant.EXCEPTION_MESSAGE),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Null Pointer Exception Occurred:");
		}catch (Exception e) {
			soRetrieveResponse = responseValidator.getFailureResponse(
					CommonUtility.getMessage(PublicAPIConstant.EXCEPTION_MESSAGE),
											ServiceConstants.SYSTEM_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		} finally {
			logger.info("Going to validate the response xml " 
												+"for RetrieveServiceOrder: ");
			responseValidator.validateResponseXML(soRetrieveResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
					PublicAPIConstant.SORESPONSE_XSD);
			if (logId != 0) {
				responseValidator.logResponse(soRetrieveResponse, logId);
			}
		}
		logger.info("Leaving SORequestProcessor.getResponseForRetrieve()");
		return Response.ok(soRetrieveResponse).build();
	}


	/**
	 * This method returns a response for a search service order request.
	 * @param String soSearchRequest
	 * @return Response
	 */
	@POST
	@Path("/serviceorder/search/")
	public Response getResponseForSearch(String soSearchRequest) {
		logger.info("Entering SORequestProcessor.getResponseForSearch()");
		String soSearchResponse = null;
		int logId = 0;
		try {
			// String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soSearchRequest, PublicAPIConstant.SEARCH_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				logId = requestValidator.logRequest(ipAddress, soSearchRequest);
				soSearchResponse = searchService
						.dispatchSearchServiceRequest(soSearchRequest);
			} else {
				soSearchResponse = responseValidator
						.getSearchFailureResponse(validationStatus,
									ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Response for SearchServiceOrder is: "
					+ soSearchResponse);
		} catch (NullPointerException nullException) {
			soSearchResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
											ServiceConstants.USER_ERROR_RC);
			logger.error("Null Pointer Exception Occurred:");
		} catch (Exception e) {
			soSearchResponse = responseValidator
					.getSearchFailureResponse(CommonUtility
							.getMessage(PublicAPIConstant.EXCEPTION_MESSAGE),
											ServiceConstants.SYSTEM_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		} finally {
			logger.info("Going to validate the response xml " 
													+"for SearchServiceOrder:");
			responseValidator.validateResponseXML(soSearchResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
					PublicAPIConstant.SEARCHRESPONSE_XSD);
			if (logId != 0) {
				responseValidator.logResponse(soSearchResponse, logId);
			}
		}
		logger.info("Leaving SORequestProcessor.getResponseForSearch()");
		return Response.ok(soSearchResponse).build();
	}

	
	/**
	 * This method returns a response for a reschedule service order request.
	 * @param String soRescheduleRequest
	 * @return Response
	 */
	@POST
	@Path("/serviceorder/reschedule/{id}")
	public Response getResponseForReschedule(@PathParam("id")
										String soId, String soRescheduleRequest) {
		logger.info("Entering SORequestProcessor.getResponseForReschedule()");
		String soRescheduleResponse = null;
		int logId=0;
		try{
		//String ipAddress = httpRequest.getRemoteAddr();		
		String ipAddress = null;
		//If soId not specified, show soId Missing Response
		if(!"".equals(soId)){
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soRescheduleRequest,
					PublicAPIConstant.RESCHEDULE_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				logId = requestValidator.logRequest(ipAddress,
						soRescheduleRequest);
				soRescheduleResponse = rescheduleService
						.dispatchRescheduleServiceRequest(soId,
								soRescheduleRequest);
				logger.debug("Response for  RescheduleServiceOrder"
						+ soRescheduleResponse);
			} else {
				soRescheduleResponse = responseValidator
						.getFailureResponse(validationStatus,
												ServiceConstants.USER_ERROR_RC);
			}
		}else{
			soRescheduleResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
									PublicAPIConstant.SOID_MISSING_ERROR_CODE),
												ServiceConstants.USER_ERROR_RC);
		}
		}catch (NullPointerException nullException) {
			soRescheduleResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Null Pointer Exception Occurred:");
		} catch(Exception e){
			soRescheduleResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
											ServiceConstants.SYSTEM_ERROR_RC);
			logger.error("Exception Occurred: " + e);		
		}finally{
			logger.info("Going to validate the response xml for " 
													+"RescheduleServiceOrder");
			responseValidator.validateResponseXML(soRescheduleResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
					PublicAPIConstant.SORESPONSE_XSD);
			if(logId!=0){
				responseValidator.logResponse(soRescheduleResponse,logId);
			}
		}
		logger.info("Leaving SORequestProcessor.getResponseForReschedule()");
		return Response.ok(soRescheduleResponse).build();
	}
	
	/**
	 * This method returns a response for a cancel service order request.
	 * @param String soCancelRequest
	 * @return Response
	 */
	@POST
	@Path("/serviceorder/cancel/{id}")
	public Response getResponseForCancel(@PathParam("id")
	String soId, String soCancelRequest) {
		logger.info("Entering SORequestProcessor.getResponseForCancel()");
		String soCancelResponse = null;
		int logId = 0;
		try {
			//String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;
			//If soId not specified, show soId Missing Response
			if(!"".equals(soId)){
			String validationStatus = requestValidator.validateRequest(
													ipAddress, soCancelRequest,
												  PublicAPIConstant.CANCEL_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {

				logId = requestValidator.logRequest(ipAddress, soCancelRequest);
				soCancelResponse = cancelService.dispatchCancelServiceRequest(
						soCancelRequest, soId);
				if (null==soCancelResponse) {
					logger.error("Could not retrieve details " 
													+"for ServiceOrder"+soId);
					soCancelResponse = responseValidator
					.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.RETRIEVE_ERROR_CODE),
												ServiceConstants.USER_ERROR_RC);
				}
			} else {
				soCancelResponse = responseValidator
						.getFailureResponse(validationStatus,
												ServiceConstants.USER_ERROR_RC);
			}
			}else{
				soCancelResponse = responseValidator
				.getFailureResponse(CommonUtility.getMessage(
									PublicAPIConstant.SOID_MISSING_ERROR_CODE),
												ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Cancel Response xml is: " + soCancelResponse);
		}catch (NullPointerException nullException) {
			soCancelResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
											ServiceConstants.USER_ERROR_RC);
			logger.error("Null Pointer Exception Occurred:");
		} catch (Exception e) {
			soCancelResponse = responseValidator
					.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
											ServiceConstants.SYSTEM_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		} finally {
			logger.info("Going to validate the response xml " 
													+"for cancelServiceOrder");
			responseValidator.validateResponseXML(soCancelResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
					PublicAPIConstant.SORESPONSE_XSD);
			if (logId != 0) {
				responseValidator.logResponse(soCancelResponse, logId);
			}
		}
		logger.info("Leaving SORequestProcessor.getResponseForCancel()");
		return Response.ok(soCancelResponse).build();
	}
	/**
	 * This method is for posting a service order.This serves the
	 * requests coming for /serviceorder/post/{id} URL.
	 * 
	 * @param soId
	 *            String
	 * @param postSORequest
	 *            String
	 * @throws Exception
	 *             Exception
	 * @return Response
	 */
	@POST
	@Path("/serviceorder/post/{id}")
	public Response getResponseForPost(@PathParam("id")
	String soId, String soPostRequest) {
		logger.info("Entering SORequestProcessor.getResponseForPost()");
		String soPostResponse = null;
		int logId = 0;
		try {
			String ipAddress = null;
			//If soId not specified, show soId Missing Response
			if(!"".equals(soId)){
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soPostRequest,
					PublicAPIConstant.POST_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {

				logId = requestValidator.logRequest(ipAddress, soPostRequest);
				soPostResponse = postService.dispatchPostServiceRequest(
						soPostRequest, soId);
				if (null==soPostResponse) {
					logger.error("Could not retrieve details " 
													+"for ServiceOrder"+soId);
					soPostResponse = responseValidator
					.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.RETRIEVE_ERROR_CODE),
												ServiceConstants.USER_ERROR_RC);
				}
			} else {
				soPostResponse = responseValidator
						.getFailureResponse(validationStatus,
												ServiceConstants.USER_ERROR_RC);
			}
			}else{
				soPostResponse = responseValidator
				.getFailureResponse(CommonUtility.getMessage(
									PublicAPIConstant.SOID_MISSING_ERROR_CODE),
												ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Post Response xml is:" + soPostResponse);
		}  catch (NullPointerException exception) {
			soPostResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
								PublicAPIConstant.EXCEPTION_MESSAGE),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Null Pointer Exception: " + exception);
			}catch (Exception e) {
			soPostResponse = responseValidator
					.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
											ServiceConstants.SYSTEM_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		} finally {
			logger.info("Going to validate the response xml for postServiceOrder");
			responseValidator.validateResponseXML(soPostResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
					PublicAPIConstant.SORESPONSE_XSD);
			if (logId != 0) {
				responseValidator.logResponse(soPostResponse, logId);
			}
		}
		logger.info("Leaving SORequestProcessor.getResponseForPost()");
		return Response.ok(soPostResponse).build();
	}
	/**
	 * This method is for adding notes to a service order.This serves the
	 * requests coming for /serviceorder/addNote/{id} URL.
	 * 
	 * @param soId String
	 * @param addNoteRequest String
	 * @throws Exception Exception
	 * @return Response
	 */
	@POST
	@Path("/serviceorder/addNote/{id}")
	public Response getResponseForAddNote(@PathParam("id")
	String soId, String addNoteRequest) {
		logger.info("Entering SORequestProcessor.getResponseForAddNote()");
		String addNoteResponse = null;
		int logId = 0;
		try {
			String ipAddress = null;
			if(!"".equals(soId)){
			String validationStatus = requestValidator.validateRequest(
					ipAddress, addNoteRequest, PublicAPIConstant.ADDNOTE_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {

				logId = requestValidator.logRequest(ipAddress, addNoteRequest);
				addNoteResponse = addNoteService.dispatchAddNoteServiceRequest(
						addNoteRequest, soId);
				if (null == addNoteResponse) {
					addNoteResponse = responseValidator
							.getFailureResponse(CommonUtility.getMessage(
									PublicAPIConstant.RETRIEVE_ERROR_CODE),
												ServiceConstants.USER_ERROR_RC);
				}
			} else {
				addNoteResponse = responseValidator
						.getFailureResponse(validationStatus,
												ServiceConstants.USER_ERROR_RC);
			}
			}else{
				addNoteResponse = responseValidator
				.getFailureResponse(CommonUtility.getMessage(
									PublicAPIConstant.SOID_MISSING_ERROR_CODE),
												ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Add Note Response xml is:" + addNoteResponse);
			} catch (NullPointerException exception) {
				addNoteResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
								PublicAPIConstant.EXCEPTION_MESSAGE),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Null Pointer Exception: " + exception);
			} catch (Exception e) {
			addNoteResponse = responseValidator
					.getFailureResponse(CommonUtility
							.getMessage(PublicAPIConstant.EXCEPTION_MESSAGE),
											ServiceConstants.SYSTEM_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		} finally {
			logger.info("Going to validate the response xml for AddNote Service");
			responseValidator.validateResponseXML(addNoteResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
					PublicAPIConstant.SORESPONSE_XSD);
			if (logId != 0) {
				responseValidator.logResponse(addNoteResponse, logId);
			}
		}
		logger.info("Leaving SORequestProcessor.getResponseForAddNote()");
		return Response.ok(addNoteResponse).build();
	}
	
/**
	 * This method returns a response for accepting a conditional offer request.
	 * 
	 * @param soId  String
	 * @param buyerId String
	 * @param soCounterOfferRequest String
	 * @return Response
	 */
	@PUT
	@Path("/buyers/{buyer_id}/serviceorders/{soId}/offers")
	public Response getResponseForAcceptCounterOffer(@PathParam("buyer_id")
			String buyerId, @PathParam("soId")
	String soId, String soCounterOfferRequest) {
		
		logger.info("Entering the request processor for accept counter offer");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(soCounterOfferRequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.setSOId(soId);
		apiVO.setBuyerId(buyerId);
		String responseXML = acceptOfferService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.getResponseForAcceptCounterOffer()");
		return Response.ok(responseXML).build();		
	}
	
	/**
	 * 	/**
	 * This method closes the requested SO.
	 * 
	 * URL: /buyers/{buyerId}/serviceorders/{soId}/completion/	  
	 * Example: TBD
	 * Response XSD  : closeSOResponse.xsd
	 * Service Class : {@link SOCloseService}
	 *
	 * @param buyerId
	 * @param soId
	 * @return Response: It will either return success of error code.
	 */
	@PUT
	@Path("/buyers/{buyerId}/serviceorders/{soId}/completion/")
	public Response closeSO(@PathParam("buyerId") String buyerId,
										@PathParam("soId") String soId, String soPosrequestXml) {
		logger.info("Entering SORequestProcessor.closeSO()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
	    apiVO.setRequestFromPostPut(soPosrequestXml);
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Put);
		String responseXML = soCloseService.doSubmit(apiVO);		
		return Response.ok(responseXML).build();
	}
	
	
	/**
	 * 
	 * @param buyerId
	 * @param soId
	 * @return
	 */
	@POST
	@Path("/buyerresources/{buyerResourceId}/serviceorders/{soId}/release")
	public Response releaseSO(@PathParam("buyerresourceId") Integer resourceId,
										@PathParam("soId") String soId, 
										String soPosrequestXml) {
		logger.info("Entering SORequestProcessor.releaseSO()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(soPosrequestXml);
		apiVO.addProperties(APIRequestVO.BUYERID, resourceId);
		apiVO.addProperties(APIRequestVO.SOID, soId);
		apiVO.setRequestType(RequestType.Post);
		String responseXML = soReleaseService.doSubmit(apiVO);		
		return Response.ok(responseXML).build();
	}


	/**
	/**
	 * This method returns a mock response for a create service order request.
	 * @param String soCreateRequest
	 * @return Response
	 */
	@POST
	@Path("/training/serviceorder/create/")
	public Response getMockResponseForCreate(String soCreateRequest) {
		logger.info("Entering SORequestProcessor.getMockResponseForCreate()");
		String soCreateMockResponse = null;
		try {
			//String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soCreateRequest,
					PublicAPIConstant.REQUEST_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				soCreateMockResponse = SOMockResponse.getMockResponseForCreate();

			} else {
				soCreateMockResponse = responseValidator
				.getFailureResponse(validationStatus,ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Mock Response for CreateServiceOrder is: "
					+ soCreateMockResponse);
		} catch (Exception e) {
			soCreateMockResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		}
		responseValidator.validateResponseXML(soCreateMockResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SORESPONSE_XSD);
		logger.info("Leaving SORequestProcessor.getMockResponseForCreate()");
		return Response.ok(soCreateMockResponse).build();
	}


	/**
	 * This method returns a mock response for a search service order request.
	 * @param String soSearchRequest
	 * @return Response
	 */
	@POST
	@Path("/training/serviceorder/search/")
	public Response getMockResponseForSearch(String soSearchRequest,
			String xsdFileName) {
		logger.info("Entering SORequestProcessor.getMockResponseForSearch()");
		String soSearchMockResponse = null;
		try {
			//String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soSearchRequest, PublicAPIConstant.SEARCH_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				soSearchMockResponse = SOMockResponse.getMockResponseForSearch();
			} else {
				soSearchMockResponse = responseValidator
				.getSearchFailureResponse(validationStatus,
												ServiceConstants.USER_ERROR_RC);
			}
			logger.info("Mock Response for SearchServiceOrder is: " 
														+ soSearchMockResponse);
		} catch (Exception e) {
			soSearchMockResponse = responseValidator
			.getSearchFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		}
		responseValidator.validateResponseXML(soSearchMockResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SEARCHRESPONSE_XSD);
		logger.info("Leaving SORequestProcessor.getMockResponseForSearch()");
		return Response.ok(soSearchMockResponse).build();
	}


	/**
	 * This method returns a mock response for a retrieve service order request.
	 * @param String soId, String soRetrieveRequest
	 * @return Response
	 */
	@POST
	@Path("/training/serviceorder/get/{id}")
	public Response getMockResponseForRetrieve(@PathParam("id")
			String soId, String soRetrieveRequest) {
		logger.info("Entering SORequestProcessor.getMockResponseForRetrieve()");
		String soRetrieveMockResponse = null;
		try {
			//String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;
			String validationStatus = requestValidator
			.validateRequest(ipAddress, soRetrieveRequest,
					PublicAPIConstant.REQUEST_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				soRetrieveMockResponse = SOMockResponse.getMockResponseForRetrieve();
			} else {
				soRetrieveMockResponse = responseValidator
				.getFailureResponse(validationStatus,ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Mock Response for RetrieveServiceOrder is: "
					+ soRetrieveMockResponse);
		} catch (Exception e) {
			soRetrieveMockResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		}
		responseValidator.validateResponseXML(soRetrieveMockResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SORESPONSE_XSD);
		logger.info("Leaving SORequestProcessor.getMockResponseForRetrieve()");
		return Response.ok(soRetrieveMockResponse).build();
	}


	/**
	 * This method returns a mock response for a cancel service order request
	 * @param String soId, String soCancelRequest
	 * @return Response 
	 */
	@POST
	@Path("/training/serviceorder/cancel/{id}")
	public Response getMockResponseForCancel(@PathParam("id")
										String soId, String soCancelRequest) {
		logger.info("Entering SORequestProcessor.getMockResponseForCancel()");
		String soCancelMockResponse = null;
		try {
			//String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soCancelRequest,
					PublicAPIConstant.CANCEL_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				soCancelMockResponse = SOMockResponse.getMockResponseForCancel();

			} else {
				soCancelMockResponse = responseValidator.getFailureResponse(
												validationStatus,
												ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Mock Response for CancelServiceOrder is:"
					+ soCancelMockResponse);
		} catch (Exception e) {
			soCancelMockResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
										ServiceConstants.USER_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		}
		responseValidator.validateResponseXML(soCancelMockResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SORESPONSE_XSD);
		logger.info("Leaving SORequestProcessor.getMockResponseForCancel()");
		return Response.ok(soCancelMockResponse).build();
	}

	

	/**
	 * This method returns a mock response for post service order request.
	 * @param String soPostRequest 
	 * @return Response 
	 */
	@POST
	@Path("/training/serviceorder/post/{id}")
	public Response getMockResponseForPost(@PathParam("id")
			String soId, String soPostRequest) {
		logger.info("Entering SORequestProcessor.getMockResponseForPost()");
		String soPostMockResponse = null;
		try {
			//String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soPostRequest,
					PublicAPIConstant.POST_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				soPostMockResponse = SOMockResponse.getMockResponseForPost();

			} else {
				soPostMockResponse = responseValidator
				.getFailureResponse(validationStatus,ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Mock Response for PostServiceOrder is: "
					+ soPostMockResponse);
		} catch (Exception e) {
			soPostMockResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
										ServiceConstants.USER_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		}
		responseValidator.validateResponseXML(soPostMockResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SORESPONSE_XSD);
		logger.info("Leaving SORequestProcessor.getMockResponseForPost()");
		return Response.ok(soPostMockResponse).build();
	}


	/**
	 * This method returns a mock response for reschedule service order.
	 * @param String soId, String soRescheduleRequest
	 * @return Response 
	 */
	@POST
	@Path("/training/serviceorder/reschedule/{id}")
	public Response getMockResponseForReschedule(@PathParam("id")
									String soId, String soRescheduleRequest) {
		logger.info("Entering SORequestProcessor.getMockResponseForReschedule()");
		String soRescheduleMockResponse = null;
		try {
			//String ipAddress = httpRequest.getRemoteAddr();
			String ipAddress = null;			
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soRescheduleRequest,
					PublicAPIConstant.RESCHEDULE_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				soRescheduleMockResponse 
								= SOMockResponse.getMockResponseForReschedule();

			} else {
				soRescheduleMockResponse = responseValidator
				.getFailureResponse(validationStatus,ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Mock Response forReschduleServiceOrder is: "
					+ soRescheduleMockResponse);
		} catch (Exception e) {
			soRescheduleMockResponse = responseValidator
			.getFailureResponse(CommonUtility.getMessage(
										PublicAPIConstant.EXCEPTION_MESSAGE),
										ServiceConstants.USER_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		}
		responseValidator.validateResponseXML(soRescheduleMockResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SORESPONSE_XSD);
		logger.info("Leaving SORequestProcessor.getMockResponseForReschedule()");
		return Response.ok(soRescheduleMockResponse).build();
	}
	
	/**
	 * This method returns a mock response for a add note service order request
	 * 
	 * @param String
	 *            soId, String soAddNoteRequest
	 * @return Response
	 */
	@POST
	@Path("/training/serviceorder/addNote/{id}")
	public Response getMockResponseForAddNote(@PathParam("id")
	String soId, String soAddNoteRequest) {
		logger.info("Entering SORequestProcessor.getMockResponseForAddNote()");
		String soAddNoteMockResponse = null;
		try {
			String ipAddress = null;
			String validationStatus = requestValidator.validateRequest(
					ipAddress, soAddNoteRequest, PublicAPIConstant.ADDNOTE_XSD);
			if (validationStatus.equals(PublicAPIConstant.SUCCESS)) {
				soAddNoteMockResponse = SOMockResponse
						.getMockResponseForAddNote();

			} else {
				soAddNoteMockResponse = responseValidator
						.getFailureResponse(validationStatus,
												ServiceConstants.USER_ERROR_RC);
			}
			logger.debug("Mock Response for Add Note is:"
					+ soAddNoteMockResponse);
		} catch (Exception e) {
			soAddNoteMockResponse = responseValidator
					.getFailureResponse(CommonUtility
							.getMessage(PublicAPIConstant.EXCEPTION_MESSAGE),
												ServiceConstants.USER_ERROR_RC);
			logger.error("Exception Occurred: " + e);
		}
		responseValidator.validateResponseXML(soAddNoteMockResponse,PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SORESPONSE_XSD);
		logger.info("Leaving SORequestProcessor.getMockResponseForAddNote()");
		return Response.ok(soAddNoteMockResponse).build();
	}


	/**
	 * This method reports a problem on active SO.
	 * @param requestXML
	 * @param buyerId
	 * @param soId
	 * @return
	 */
	@POST
	@Path("/buyers/{buyer_id}/serviceorders/{soId}/problem")
	public Response reportProblem(String requestXML, 
			@PathParam("buyer_id") String buyerId,			
			@PathParam("soId") String soId) {

		logger.info("Entering SORequestProcessor.reportProblem method");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(BaseService.RequestType.Post);
		apiVO.setBuyerId(buyerId);				
		apiVO.setSOId(soId);
				
		String responseXML = reportProblemService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.reportProblem method");
		return Response.ok(responseXML).build();
	}
	
	
	/**
	 * SL-20678: API for posting a SO to a firm. It posts the SO to all eligible providers in the firm.
	 * This method returns a response for a post service order to firm request.
	 * 
	 * @param String
	 *            soPostRequest
	 * @return Response
	 */
	@PUT
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/soPostFirm")
	public Response getResponseForSoPostFirm(String requestXML,
			@PathParam("buyer_id")
			String buyerId, @PathParam("so_id")
			String soId) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForSoPostFirm()");
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
		String responseXML = soPostFirmService.doSubmit(apiVO);
		if(MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader()))
		responseXML = PublicAPIConstant.XML_VERSION + responseXML;
		if (logger.isInfoEnabled())
			logger.info("LeavingSORequestProcessor.getResponseForSoPostFirm()");
		return Response.ok(responseXML).build();
	}
	
	
	/**
	 * This method write the response to output stream.	
	 * @param HttpServletResponse
	 * @param byte[]
	 * @param InputStream
	 * @param OutputStream
	 * @return Response	 
	 */
	private void writeResponse(HttpServletResponse httpResponse,
			byte[] mybytes)
			throws IOException{		
		InputStream in = new ByteArrayInputStream(mybytes);		
		OutputStream out = httpResponse.getOutputStream();
		int bit = 256;
		while ((bit) >= 0 ) {
			bit = in.read();
			if((bit) != -1)
				out.write(bit);
		}
		out.flush();
		out.close();
		in.close();		
	}
	/**
	 * This method returns the response for resolving the issue in a SO. 
	 * @param String buyerResourceId
	 * String soId
	 * String soResolveIssueRequest
	 * @return Response
	 */
	@PUT
	@Path("/buyers/{buyer_id}/serviceorders/{soid}/problem")
	public Response getResponseForSOResolveProblem(
				@PathParam("buyer_id") String buyerId, 
				@PathParam("soid") String soId,				
				String soResolveIssueRequest) {
		logger.info("Entering SORequestProcessor." +
				"getResponseForSOResolveIssue()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String soResolveIssueResponse = null;
		apiVO.setRequestFromPostPut(soResolveIssueRequest);
		apiVO.setRequestType(RequestType.Put);
		apiVO.setSOId(soId);
		apiVO.setBuyerId(buyerId);
		
		soResolveIssueResponse = resolveProblemService.doSubmit(apiVO);
				
		logger.info("Leaving SORequestProcessor." +
				"getResponseForSOResolveProblem()");
		return Response.ok(soResolveIssueResponse).build();
	}
	/**
	 * This method returns the response for rejecting a SO. 
	 * @param String soId
	 * @param String vendorResourceId
	 * @param String rejectSORequest
	 * @return Response
	 */
	@POST
	@Path("/buyerresources/serviceorders/{soId}/reject/{vendorResourceId}")
	public Response getResponseForRejectSO(
				@PathParam("soId") String soId,
				@PathParam("vendorResourceId") String vendorResourceId,
				String rejectSORequest) {
		logger.info("Entering SORequestProcessor.getResponseForRejectSO()");
		Map<String, String> requestMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String soRejectSOResponse = null;
		apiVO.setRequestFromPostPut(rejectSORequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.addProperties(APIRequestVO.SOID, soId);
		apiVO.addProperties(APIRequestVO.PROVIDER_RESOURCE_ID,vendorResourceId);
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, requestMap);
		soRejectSOResponse = rejectSOService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.getResponseForRejectSO()");
		return Response.ok(soRejectSOResponse).build();
	}
	
	/**
	 * 
	 * @param soId
	 * @param vendorResourceId
	 * @param rejectSORequest
	 * @return
	 */
	
	
	@GET
	@Path("/buyers/{buyer_id}/rescheduleReasons")
	public Response getRescheduleReasonsForSO(@PathParam("buyer_id") String buyerId) {
		logger.info("Entering SORequestProcessor.getRescheduleReasonsForSO()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String soRescheduleReasonResponse = null;
		apiVO.setRequestType(RequestType.Get);
		apiVO.setBuyerId(buyerId);
		soRescheduleReasonResponse = rescheduleReasonService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.getRescheduleReasonsForSO()");
		return Response.ok(soRescheduleReasonResponse).build();
	}
	
	@GET
	@Path("/buyers/{buyer_id}/cancelReasons")
	public Response getCancelReasonsForSO(@PathParam("buyer_id") String buyerId) {
		logger.info("Entering SORequestProcessor.getCancelReasonsForSO()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String soCancelReasonResponse = null;
		apiVO.setRequestType(RequestType.Get);
		apiVO.setBuyerId(buyerId);
		soCancelReasonResponse = soCancelReasonService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.getCancelReasonsForSO()");
		return Response.ok(soCancelReasonResponse).build();
	}
	
	@GET
	@Path("/buyers/{buyer_id}/skuId/{skuId}/zipCode/{zipCode}/firms")
	public D2CGetFirmDetailsResponse getFirmsFOrAutoPost(@PathParam("buyer_id") String buyerId,
			@PathParam("skuId") String sku,
			@PathParam("zipCode") String zipCode) {
		logger.info("Entering SORequestProcessor.getFirmsFOrAutoPost()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		D2CGetFirmDetailsResponse searchFirmsForAutoPostResponse = null;
		apiVO.setRequestType(RequestType.Get);
		apiVO.setBuyerId(buyerId);
		apiVO.setSku(sku);
		apiVO.setZip(zipCode);
		searchFirmsForAutoPostResponse = searchFirmsForAutoPostService.getFirmsDetails(apiVO);
		logger.info("Leaving SORequestProcessor.getFirmsFOrAutoPost()");
		return searchFirmsForAutoPostResponse;
	}
	
	/**
	 * Setter methods for spring injection
	 */

	public void setCreateService(SOCreateService createService) {
		this.createService = createService;
	}

	public void setSearchService(SOSearchService searchService) {
		this.searchService = searchService;
	}

	public void setRetrieveService(SORetrieveService retrieveService) {
		this.retrieveService = retrieveService;
	}

	public void setRequestValidator(RequestValidator requestValidator) {
		this.requestValidator = requestValidator;
	}

	public void setResponseValidator(ResponseValidator responseValidator) {
		this.responseValidator = responseValidator;
	}
	
	public void setCancelService(SOCancelService cancelService) {
		this.cancelService = cancelService;
	}

	public SOPostService getPostService() {
		return postService;
	}

	public void setPostService(SOPostService postService) {
		this.postService = postService;
	}

	public void setRescheduleService(SORescheduleService rescheduleService) {
		this.rescheduleService = rescheduleService;
	}

	public void setAddNoteService(SOAddNoteService addNoteService) {
		this.addNoteService = addNoteService;
	}

	public void setAcceptOfferService(SOAcceptOfferService acceptOfferService) {
		this.acceptOfferService = acceptOfferService;
	}

	public SOCloseService getSoCloseService() {
		return soCloseService;
	}


	public void setSoCloseService(SOCloseService soCloseService) {
		this.soCloseService = soCloseService;
	}


	public ReportProblemService getReportProblemService() {
		return reportProblemService;
	}

	public void setReportProblemService(
			ReportProblemService reportProblemService) {
		this.reportProblemService = reportProblemService;
	}


	public ResolveProblemOnSOService getResolveProblemService() {
		return resolveProblemService;
	}


	public void setResolveProblemService(ResolveProblemOnSOService resolveProblemService) {
		this.resolveProblemService = resolveProblemService;
	}


	public SORejectService getRejectSOService() {
		return rejectSOService;
	}


	public void setRejectSOService(SORejectService rejectSOService) {
		this.rejectSOService = rejectSOService;
	}


	public SOReleaseService getSoReleaseService() {
		return soReleaseService;
	}


	public void setSoReleaseService(SOReleaseService soReleaseService) {
		this.soReleaseService = soReleaseService;
	}

    public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}


	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
	public SOPostFirmService getSoPostFirmService() {
		return soPostFirmService;
	}

	public void setSoPostFirmService(SOPostFirmService soPostFirmService) {
		this.soPostFirmService = soPostFirmService;
	}


	public SORescheduleReasonService getRescheduleReasonService() {
		return rescheduleReasonService;
	}


	public void setRescheduleReasonService(
			SORescheduleReasonService rescheduleReasonService) {
		this.rescheduleReasonService = rescheduleReasonService;
	}


	public SOCancelReasonService getSoCancelReasonService() {
		return soCancelReasonService;
	}


	public void setSoCancelReasonService(SOCancelReasonService soCancelReasonService) {
		this.soCancelReasonService = soCancelReasonService;
	}


	public SearchFirmsForAutoPostService getSearchFirmsForAutoPostService() {
		return searchFirmsForAutoPostService;
	}


	public void setSearchFirmsForAutoPostService(
			SearchFirmsForAutoPostService searchFirmsForAutoPostService) {
		this.searchFirmsForAutoPostService = searchFirmsForAutoPostService;
	}

}
