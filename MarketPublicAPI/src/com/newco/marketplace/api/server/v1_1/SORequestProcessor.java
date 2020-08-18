/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.server.v1_1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.buyerskus.ServiceCategoryResponse;
import com.newco.marketplace.api.beans.closedOrders.RetrieveClosedOrdersResponse;
import com.newco.marketplace.api.beans.firmDetail.GetFirmDetailsRequest;
import com.newco.marketplace.api.beans.firmDetail.GetFirmDetailsResponse;
import com.newco.marketplace.api.beans.serviceOrderDetail.GetServiceOrdersResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.so.AddSODocService;
import com.newco.marketplace.api.services.so.DeleteSODocService;
import com.newco.marketplace.api.services.so.GetSubStatusService;
import com.newco.marketplace.api.services.so.ReadSODocService;
import com.newco.marketplace.api.services.so.SOReassignService;
import com.newco.marketplace.api.services.so.SOReleaseService;
import com.newco.marketplace.api.services.so.v1_1.BuyerSkuService;
import com.newco.marketplace.api.services.so.v1_1.GetAcceptedSODetailService;
import com.newco.marketplace.api.services.so.v1_1.GetFirmDetailsService;
import com.newco.marketplace.api.services.so.v1_1.GetSOPriceDetailsService;
import com.newco.marketplace.api.services.so.v1_1.SOGetClosedOrdersService;
import com.newco.marketplace.api.services.so.v1_1.ResolveProblemByProviderService;
import com.newco.marketplace.api.services.so.v1_1.SOAcceptRejectReleaseService;
import com.newco.marketplace.api.services.so.v1_1.SOAcceptService;
import com.newco.marketplace.api.services.so.v1_1.SOAddNoteService;
import com.newco.marketplace.api.services.so.v1_1.SOAddProviderNoteService;
import com.newco.marketplace.api.services.so.v1_1.SOCancelRescheduleService;
import com.newco.marketplace.api.services.so.v1_1.SOCancelService;
import com.newco.marketplace.api.services.so.v1_1.SOCompleteSOService;
import com.newco.marketplace.api.services.so.v1_1.SOCreateConditionalOfferService;
import com.newco.marketplace.api.services.so.v1_1.SOCreateService;
import com.newco.marketplace.api.services.so.v1_1.SOEditService;
import com.newco.marketplace.api.services.so.v1_1.SOPostService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderRejectRescheduleService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderReportProblemService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderRescheduleResponseService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderRescheduleService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderRetrieveService;
import com.newco.marketplace.api.services.so.v1_1.SOProviderTimeOnSiteService;
import com.newco.marketplace.api.services.so.v1_1.SORescheduleResponseService;
import com.newco.marketplace.api.services.so.v1_1.SORescheduleService;
import com.newco.marketplace.api.services.so.v1_1.SORetrieveService;
import com.newco.marketplace.api.services.so.v1_1.SORetrieveSpendLimitService;
import com.newco.marketplace.api.services.so.v1_1.SOSearchByProviderService;
import com.newco.marketplace.api.services.so.v1_1.SOSearchService;
import com.newco.marketplace.api.services.so.v1_1.SOUpdateService;
import com.newco.marketplace.api.services.so.v1_1.SOUpdateEstimateStatusService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.newco.marketplace.api.services.so.v1_1.SOUpdateEstimateService;

/**
 * This class would act as an intercepter for all the public api services in the
 * application.It implements the "versioning" logic and it extends the base
 * SORequestProcessor class
 * 
 * @author Infosys
 * @version 1.0
 */
// routing all incoming requests for version v1.1 to the SORequestProcessor
// class
@Path("v1.1")
public class SORequestProcessor extends
		com.newco.marketplace.api.server.SORequestProcessor {
	private Logger logger = Logger.getLogger(SORequestProcessor.class);

	// services
	protected SOCreateService createService;
	protected SORetrieveService retrieveService;
	protected SORetrieveSpendLimitService retrieveSpendLimitService;
	protected SOCancelService cancelService;
	protected SOPostService soPostV1Service;
	protected SOAddNoteService addNoteService;
	protected SORescheduleService rescheduleService;
	protected SOProviderRescheduleService providerRescheduleService;
	protected SOEditService soEditService;
	protected SOSearchService searchService;
	protected SOSearchByProviderService searchByProviderService;
	protected SOAcceptService acceptService;
	protected SOCancelRescheduleService cancelRescheduleService;
	protected SORescheduleResponseService soRescheduleResponseService;
	protected SOAddProviderNoteService soAddProviderNoteService;
	protected SOProviderRetrieveService soProviderRetrieveService;
	protected SOCreateConditionalOfferService createConditionalOfferService;
	protected SOProviderReportProblemService providerReportProblemService;
	protected ResolveProblemByProviderService resolveProviderProblemService;
	protected SOProviderTimeOnSiteService timeOnSiteService;
	protected SOReassignService soReassignService;
	protected SOReleaseService soReleaseService;
	protected AddSODocService addSODocService;
	protected DeleteSODocService deleteSODocService;
	protected ReadSODocService readSODocService;
	protected SOProviderRejectRescheduleService providerRejectRescheduleService;
	protected SOProviderRescheduleResponseService soProviderRescheduleResponseService;
	protected SOCompleteSOService completeSOService;
	protected SOAcceptRejectReleaseService acceptRejectReleaseService;
	protected SOUpdateService updateService;
	protected GetSOPriceDetailsService soPriceService;
	// SL-21086
	protected GetSubStatusService subStatusService;
	private static final String MEDIA_TYPE_XML_STR = "application/xml";
	private static final String MEDIA_TYPE_JSON_STR = "application/json";
	//B2C
	protected SOUpdateEstimateStatusService soUpdateEstimateStatusService;
	
	protected SOUpdateEstimateService soUpdateEstimateService;

	//B2C : API to fetch the firm details
	protected GetFirmDetailsService getFirmDetailsService;
	//B2C: R16_0_2: Get Closed Service Orders For Firm 
	protected SOGetClosedOrdersService soGetClosedOrdersService;
	//SL-21377: Service class for fetching buyer skus
	protected BuyerSkuService buyerSkuService;
	
	protected GetAcceptedSODetailService getAcceptedSODetailService;
	//Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;

	/**
	 * This method returns a response for a Create service order request.
	 * 
	 * @param String
	 *            buyerId
	 * @param String
	 *            createRequestXML
	 * @return Response
	 */
	@POST
	@Path("/buyers/{buyer_id}/serviceorders")
	// Removed '/' at the end since this URL is conflicting with the Edit SO's
	// URL
	public Response getResponseForCreate(@PathParam("buyer_id") String buyerId,
			String createRequest) {
		long start = System.currentTimeMillis();

		if (logger.isInfoEnabled()) {
			logger.info("Entering SORequestProcessor.getResponseForCreate()");
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
		createResponse = createService.doSubmit(apiVO);
		if (MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader())) {
			createResponse = PublicAPIConstant.XML_VERSION + createResponse;
			}
		if (logger.isInfoEnabled()) {
			logger.info("Leaving SORequestProcessor.getResponseForCreate()");
		}
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("Inside SORequestProcessor.getResponseForCreate()..>>Creation Time Taken>>"
					+ (end - start));
		}
		return Response.ok(createResponse).build();
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
	public Response getResponseForCancel(@PathParam("buyer_id") String buyerId,
			@PathParam("so_id") String soId) {
		if (logger.isInfoEnabled()) {
			logger.info("Entering SORequestProcessor.getResponseForCancel()");
		}
		String cancelResponse = null;
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
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Delete);
		cancelResponse = cancelService.doSubmit(apiVO);
		if (MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader())) {
			cancelResponse = PublicAPIConstant.XML_VERSION + cancelResponse;
			}
		if (logger.isInfoEnabled()) {
			logger.info("Leaving SORequestProcessor.getResponseForCancel()");
		}
		return Response.ok(cancelResponse).build();
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
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}")
	public Response getResponseForRetrieve(@PathParam("so_id") String soId,
			@PathParam("buyer_id") String buyerId) {
		if (logger.isInfoEnabled())
			logger.info("getResponseForRetrieve method started");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());

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

		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setSOId(soId);
		apiVO.setBuyerId(buyerId);
		apiVO.setLocale(getHttpRequest().getLocale());
		String responseXML = retrieveService.doSubmit(apiVO);
		return Response.ok(responseXML).build();
	}

	/**
	 * This method returns the response for fetching spend limit history Data
	 * from a SO.
	 * 
	 * @param String
	 *            buyerId, String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/spendlimit")
	public Response getResponseForSpendLimitIncrease(
			@PathParam("so_id") String soIds,
			@PathParam("buyer_id") String buyerId) {
		if (logger.isInfoEnabled())
			logger.info("getResponseForSpendLimitIncrease method started");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.addProperties(PublicAPIConstant.SO_ID_LIST, soIds);
		apiVO.setBuyerId(buyerId);
		String responseXML = retrieveSpendLimitService.doSubmit(apiVO);
		return Response.ok(responseXML).build();

	}

	/**
	 * This method returns a response for a post service order request.
	 * 
	 * @param String
	 *            soPostRequest
	 * @return Response
	 */
	@PUT
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/routedproviders")
	public Response getResponseForPost(String requestXML,
			@PathParam("buyer_id") String buyerId,
			@PathParam("so_id") String soId) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForPost()");
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
		String responseXML = soPostV1Service.doSubmit(apiVO);
		if (MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader())) {
			responseXML = PublicAPIConstant.XML_VERSION + responseXML;
			}
		if (logger.isInfoEnabled())
			logger.info("LeavingSORequestProcessor.getResponseForPost()");
		return Response.ok(responseXML).build();
	}

	/**
	 * This method returns a response for Complete SO to service order request.
	 * 
	 * @param String
	 *            providerResourceId, String soId, String completeSORequest
	 * @return Response
	 */
	@PUT
	@Path("/providers/{providerId}/serviceorders/{soId}/completion")
	public Response getResponseForCompleteSO(
			@PathParam("providerId") String providerId,
			@PathParam("soId") String soId, String completeSORequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForCompleteSO()");
		String completeSOResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(completeSORequest);
		apiVO.setRequestType(RequestType.Put);
		apiVO.setProviderId(providerId);
		apiVO.setSOId(soId);
		completeSOResponse = completeSOService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor.getResponseForCompleteSO()");
		return Response.ok(completeSOResponse).build();
	}

	/**
	 * This method returns a response for an Add Notes to service order request.
	 * 
	 * @param String
	 *            buyer_id, String soId, String addNoteRequest
	 * @return Response
	 */
	@POST
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/notes")
	public Response getResponseForAddNote(
			@PathParam("buyer_id") String buyerId,
			@PathParam("so_id") String soId, String addNoteRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForAddNote()");
		String addNoteResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(addNoteRequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		addNoteResponse = addNoteService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor.getResponseForAddNote()");
		return Response.ok(addNoteResponse).build();
	}

	/**
	 * This method returns a response for a reschedule service order request.
	 * The request processor delegates the processing to the rescheduleService
	 * class
	 * 
	 * @return
	 */
	@POST
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/reschedulerequest")
	public Response getResponseForReschedule(@PathParam("so_id") String soId,
			@PathParam("buyer_id") String buyerId, String soRescheduleRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForReschedule()");
		String soRescheduleResponse = null;
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
		apiVO.setSOId(soId);
		apiVO.setBuyerId(buyerId);
		apiVO.setRequestFromPostPut(soRescheduleRequest);
		apiVO.setRequestType(RequestType.Post);
		soRescheduleResponse = rescheduleService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor.getResponseForReschedule()");
		return Response.ok(soRescheduleResponse).build();
	}

	/**
	 * This method returns a response for a cancel reschedule service order
	 * request. The request processor delegates the processing to the
	 * rescheduleService class
	 * 
	 * @return
	 */
	@DELETE
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/reschedulerequest")
	public Response getResponseForCancelReschedule(
			@PathParam("so_id") String soId,
			@PathParam("buyer_id") String buyerId) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForReschedule()");
		String soCancelRescheduleResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Delete);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setSOId(soId);
		apiVO.setBuyerId(buyerId);
		apiVO.addProperties(PublicAPIConstant.ServiceOrder.RESOURCE_TYPE,
				PublicAPIConstant.ServiceOrder.RESOURCE_TYPE_BUYER);

		soCancelRescheduleResponse = cancelRescheduleService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor.getResponseForReschedule()");
		return Response.ok(soCancelRescheduleResponse).build();
	}

	/**
	 * This method returns a response for a cancel reschedule service order
	 * request. The request processor delegates the processing to the
	 * rescheduleService class -- This is provider API
	 * 
	 * @return
	 */
	@DELETE
	@Path("/providers/{providerId}/serviceorders/{soId}/reschedulerequest")
	public Response getResponseForProviderCancelReschedule(
			@PathParam("soId") String soId,
			@PathParam("providerId") String providerId) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForProviderCancelReschedule()");
		String soCancelRescheduleResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setSOId(soId);
		apiVO.setProviderId(providerId);
		apiVO.addProperties(APIRequestVO.SOID, soId);
		apiVO.addProperties(PublicAPIConstant.ServiceOrder.RESOURCE_TYPE,
				PublicAPIConstant.ServiceOrder.RESOURCE_TYPE_PROVIDER);

		soCancelRescheduleResponse = cancelRescheduleService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor.getResponseForReschedule()");
		return Response.ok(soCancelRescheduleResponse).build();
	}

	/**
	 * This method returns a response for a reschedule service order request.
	 * The request processor delegates the processing to the rescheduleService
	 * class - This is in case of Provider
	 * 
	 * @return
	 */
	@POST
	@Path("/providers/{providerId}/serviceorders/{soId}/reschedulerequest")
	public Response getResponseForProviderReschedule(
			@PathParam("soId") String soId,
			@PathParam("providerId") String providerId,
			String soRescheduleRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForProviderReschedule");
		String soRescheduleResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setSOId(soId);
		apiVO.setProviderId(providerId);
		apiVO.setRequestFromPostPut(soRescheduleRequest);
		apiVO.setRequestType(RequestType.Post);
		soRescheduleResponse = providerRescheduleService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor.getResponseForProviderReschedule");
		return Response.ok(soRescheduleResponse).build();
	}

	/**
	 * This method returns a response for a edit service order request.
	 * 
	 * @param String
	 *            soPostRequest
	 * @return Response
	 */
	@PUT
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}")
	public Response getResponseForEdit(String requestXML,
			@PathParam("buyer_id") String buyerId,
			@PathParam("so_id") String soId) {
		logger.info("Entering SORequestProcessor.getResponseForEdit()");
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
		String responseXML = soEditService.doSubmit(apiVO);
		if (MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader())) {
			responseXML = PublicAPIConstant.XML_VERSION + responseXML;
			}
		logger.info("ResponseXml:" + responseXML);
		return Response.ok(responseXML).build();
	}

	/**
	 * This method returns a response for a update service order request.
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
		logger.info("Entering SORequestProcessor.getResponseForUpdate()");
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
		String responseXML = updateService.doSubmit(apiVO);
		if (MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader())) {
			responseXML = PublicAPIConstant.XML_VERSION + responseXML;
			}
		logger.info("ResponseXml:" + responseXML);
		return Response.ok(responseXML).build();
	}

	/**
	 * This method returns a response for a search service order request.
	 * 
	 * @param String
	 *            buyerResourceId
	 * @return Response
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyer_id}/serviceorders/search/filters")
	public Response getResponseForSearch(@PathParam("buyer_id") String buyerId) {
		logger.info("Entering SORequestProcessor.getResponseForSearch()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setBuyerId(buyerId);
		apiVO.setRequestType(RequestType.Get);
		String responseXML = searchService.doSubmit(apiVO);
		responseXML = PublicAPIConstant.XML_VERSION + responseXML;
		return Response.ok(responseXML).build();
	}

	/**
	 * This method returns a response for an getResponseForAcceptRejectReleaseSO
	 * request.
	 * 
	 * @param String
	 *            getResponseForAcceptRejectReleaseSO, String soId
	 * @return Response
	 */

	@PUT
	@Path("/providers/{providerId}/serviceorders/{soId}")
	public Response getResponseForAcceptRejectReleaseSO(
			@PathParam("soId") String soId,
			@PathParam("providerId") String providerId, String requestXML) {
		if (logger.isInfoEnabled())
			logger.info("getResponseForAccept method started");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Put);
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setProviderId(providerId);
		apiVO.setSOId(soId);
		String responseXML = acceptRejectReleaseService.doSubmit(apiVO);
		return Response.ok(responseXML).build();

	}

	/**
	 * This method returns a response for accept / reject reschedule service
	 * order. Here, reschedule request is requested by the provider.
	 * 
	 * @param String
	 *            buyerId, String soId
	 * @return Response
	 */
	@PUT
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/reschedulerequest")
	public Response getResponseForAcceptRejectReschedule(
			@PathParam("so_id") String soId,
			@PathParam("buyer_id") String buyerId,
			String acceptRescheduleRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor."
					+ "getResponseForAcceptRejectReschedule()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Put);
		apiVO.setRequestFromPostPut(acceptRescheduleRequest);
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		String responseXML = soRescheduleResponseService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor."
					+ "getResponseForAcceptRejectReschedule()");
		return Response.ok(responseXML).build();

	}

	/**
	 * This method returns a response for an Add Notes to service order request
	 * for provider.
	 * 
	 * @param String
	 *            vendorResourceId, String soId, String addNoteRequest
	 * @return Response
	 */
	@POST
	@Path("/providers/{providerId}/serviceorders/{soId}/notes")
	public Response getResponseForProviderAddNote(
			@PathParam("soId") String soId,
			@PathParam("providerId") String providerId, String addNoteRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor."
					+ "getResponseForProviderAddNote()");
		String addNoteResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(addNoteRequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.setProviderId(providerId);
		apiVO.setSOId(soId);
		addNoteResponse = soAddProviderNoteService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor."
					+ "getResponseForProviderAddNote()");
		return Response.ok(addNoteResponse).build();
	}

	/**
	 * This method returns a response for creating a conditional offer for
	 * provider. The request processor delegates the processing to the
	 * rescheduleService class
	 * 
	 * @return
	 */
	@POST
	@Path("/providers/{providerId}/serviceorders/{soId}/offers")
	public Response getResponseForCreateConditionalOffer(
			@PathParam("soId") String soId,
			@PathParam("providerId") String providerId,
			String soConditionalRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForCreateConditionalOffer()");
		String conditionalOfferResponse = null;
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setSOId(soId);
		apiVO.setProviderId(providerId);
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, reqMap);
		apiVO.setRequestFromPostPut(soConditionalRequest);
		apiVO.setRequestType(RequestType.Post);
		conditionalOfferResponse = createConditionalOfferService
				.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor.getResponseForCreateConditionalOffer()");
		return Response.ok(conditionalOfferResponse).build();
	}

	/**
	 * This method reports a problem on active SO for provider.
	 * 
	 * @param requestXML
	 * @param providerId
	 * @param providerResourceId
	 * @param soId
	 * @return
	 */
	@POST
	@Path("/providers/{providerId}/serviceorders/{soId}/problem")
	public Response getResponseForProviderReportProblem(String requestXML,
			@PathParam("providerId") String providerId,
			@PathParam("soId") String soId) {
		logger.info("Entering SORequestProcessor."
				+ "getResponseForProviderReportProblem()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(BaseService.RequestType.Post);
		apiVO.setProviderId(providerId);
		apiVO.setSOId(soId);
		String responseXML = providerReportProblemService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor."
				+ "getResponseForProviderReportProblem()");
		return Response.ok(responseXML).build();
	}

	/**
	 * This method returns the response for resolving the issue in a SO by a
	 * provider.
	 * 
	 * @param String
	 *            vendorResourceId String soId String soResolveIssueRequest
	 * @return Response
	 */
	@PUT
	@Path("/providers/{providerId}/serviceorders/{soId}/problem")
	public Response getResponseForResolveProviderProblem(
			@PathParam("providerId") String providerId,
			@PathParam("soId") String soId, String soResolveIssueRequest) {
		logger.info("Entering SORequestProcessor."
				+ "getResponseForProviderResolveProblem()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String soResolveIssueResponse = null;
		apiVO.setRequestFromPostPut(soResolveIssueRequest);
		apiVO.setRequestType(RequestType.Put);
		apiVO.setSOId(soId);
		apiVO.setProviderId(providerId);

		soResolveIssueResponse = resolveProviderProblemService.doSubmit(apiVO);

		logger.info("Leaving SORequestProcessor."
				+ "getResponseForProviderResolveProblem()");
		return Response.ok(soResolveIssueResponse).build();
	}

	/**
	 * This method returns a response for a add visit Time On Site request. The
	 * request processor delegates the processing to the timeOnSiteService class
	 * - This is in case of Provider
	 * 
	 * @return
	 */
	@POST
	@Path("/providers/{providerId}/serviceorders/{soId}/timeonsite")
	public Response addTimeOnSite(@PathParam("soId") String soId,
			@PathParam("providerId") String providerId,
			String soTimeOnSiteRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.addTimeOnSite");
		String soTimeOnSiteResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.addProperties(APIRequestVO.SOID, soId);
		apiVO.setProviderId(providerId);
		apiVO.setRequestFromPostPut(soTimeOnSiteRequest);
		apiVO.setRequestType(RequestType.Post);
		soTimeOnSiteResponse = timeOnSiteService.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor.addTimeOnSite");
		return Response.ok(soTimeOnSiteResponse).build();
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
	@Path("/providers/{providerId}/serviceorders/{so_ids}")
	public Response getResponseForProviderRetrieve(
			@PathParam("so_ids") String soIds,
			@PathParam("providerId") String providerId) {
		if (logger.isInfoEnabled())
			logger.info("getResponseForProviderRetrieve method started");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setProviderId(providerId);
		apiVO.addProperties(PublicAPIConstant.SO_ID_LIST, soIds);
		String responseXML = soProviderRetrieveService.doSubmit(apiVO);
		return Response.ok(responseXML).build();

	}

	/**
	 * This method returns the response for attaching documents to a SO.
	 * 
	 * @param String
	 *            buyerResourceId String soId String soAddSODocRequest
	 * @return Response
	 */
	@POST
	@Path("/buyers/{buyer_id}/serviceorders/{soid}/attachments/")
	public Response getResponseForAddSODoc(
			@PathParam("buyer_id") String buyerId,
			@PathParam("soid") String soId, String soAddDocRequest) {
		logger.info("Entering SORequestProcessor.getResponseForAddSODoc()");
		String soAddSODocResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(soAddDocRequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		soAddSODocResponse = addSODocService.doSubmit(apiVO);

		logger.info("Leaving SORequestProcessor.getResponseForAddSODoc()");
		return Response.ok(soAddSODocResponse).build();
	}

	/**
	 * This method returns the response for deleting documents from a SO.
	 * 
	 * @return Response URL:
	 *         /serviceorders/100-0039-3120-19/attachments/test1.gif
	 */
	@SuppressWarnings("unchecked")
	@DELETE
	@Path("/buyers/{buyer_id}/serviceorders/{soid}/attachments/{fileid}")
	public Response getResponseForDeleteSODoc(
			@PathParam("buyer_id") String buyerId,
			@PathParam("soid") String soId, @PathParam("fileid") String fileName) {
		logger.info("Entering SORequestProcessor.getResponseForDeleteSODoc()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> requestMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		apiVO.setFileName(fileName);

		apiVO.setRequestFromGetDelete(requestMap);
		apiVO.setRequestType(RequestType.Delete);
		String responseXML = deleteSODocService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.getResponseForDeleteSODoc()");
		return Response.ok(responseXML).build();
	}

	/**
	 * This method reads documents from SO.
	 * 
	 * @param String
	 *            buyerId
	 * @param soId
	 * @param fileName
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyer_id}/serviceorders/{soid}/attachments/{fileId}")
	public void getResponseForReadSODoc(@PathParam("buyer_id") String buyerId,
			@PathParam("soid") String soId, @PathParam("fileId") String fileName)
			throws IOException {
		logger.info("Entering SORequestProcessor.getResponseForReadSODoc()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> requestMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		apiVO.setFileName(fileName);
		try {
			apiVO.setRequestFromGetDelete(requestMap);
			apiVO.setRequestType(RequestType.Get);
			DocumentVO docVO = readSODocService.dispatchReadSODocRequest(apiVO);
			if (null != docVO.getBlobBytes()) {
				String header = PublicAPIConstant.ATTACHMENT_FILENAME
						+ docVO.getFileName() + PublicAPIConstant.BACK_SLASH;
				httpResponse.setContentType(docVO.getFormat());
				httpResponse.setHeader(PublicAPIConstant.CONTENT_DISPOSITION,
						header);
				byte[] mybytes = docVO.getBlobBytes();
				writeResponse(httpResponse, mybytes);
			} else {
				// display blank screen if blobbytes is null
				// set empty string output stream
				String errorMsg = "";
				byte[] mybytes = errorMsg.getBytes();
				writeResponse(httpResponse, mybytes);
			}
		}/*
		 * catch (BusinessServiceException serviceException) { String msg =
		 * serviceException.getMessage(); byte[] mybytes = msg.getBytes();
		 * writeResponse(httpResponse, mybytes);
		 * logger.error("Business Service Exception: " +
		 * serviceException.getMessage()); }
		 */catch (Exception e) {
			String msg = CommonUtility
					.getMessage(PublicAPIConstant.EXCEPTION_MESSAGE);
			byte[] mybytes = msg.getBytes();
			writeResponse(httpResponse, mybytes);
			logger.error("Exception Occurred: " + e);
		}
		logger.info("Leaving SORequestProcessor.getResponseForReadSODoc()");
	}

	/**
	 * This method write the response to output stream.
	 * 
	 * @param HttpServletResponse
	 * @param byte[]
	 * @param InputStream
	 * @param OutputStream
	 * @return Response
	 */
	private void writeResponse(HttpServletResponse httpResponse, byte[] mybytes)
			throws IOException {
		InputStream in = new ByteArrayInputStream(mybytes);
		OutputStream out = httpResponse.getOutputStream();
		int bit = 256;
		while ((bit) >= 0) {
			bit = in.read();
			if ((bit) != -1)
				out.write(bit);
		}
		out.flush();
		out.close();
		in.close();
	}

	@PUT
	@Path("/providers/{providerId}/providerresources/{providerResourceId}/serviceorders/{soId}/release")
	public Response releaseSO(@PathParam("providerId") Integer providerId,
			@PathParam("providerResourceId") String providerResourceId,
			@PathParam("soId") String soId, String soPosrequestXml) {
		logger.info("Entering SORequestProcessor.releaseSO()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(soPosrequestXml);
		// apiVO.addProperties(APIRequestVO.PROVIDER_RESOURCE_ID,
		// providerResourceId);
		Integer providerResourceIdInt = APIRequestVO
				.StringToInt(providerResourceId);
		apiVO.setProviderResourceId(providerResourceIdInt);
		apiVO.addProperties(APIRequestVO.SOID, soId);
		// apiVO.addProperties(APIRequestVO.PROVIDER_RESOURCE_ID,
		// providerResourceId);
		apiVO.setRequestType(RequestType.Put);
		String responseXML = soReleaseService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.releaseSO()");
		return Response.ok(responseXML).build();
	}

	/**
	 * This method returns a response for a get / retrieve service order
	 * request.
	 * 
	 * @param String
	 *            buyerResourceId, String soId
	 * @return Response
	 */
	@PUT
	@Path("/providers/{providerId}/serviceorders/{soId}/assignment")
	public Response reassignSO(@PathParam("providerId") String providerId,
			@PathParam("soId") String soId, String soPosrequestXml) {
		logger.info("Entering SORequestProcessor.releaseSO()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(soPosrequestXml);
		apiVO.setProviderId(providerId);
		apiVO.addProperties(APIRequestVO.SOID, soId);
		apiVO.setRequestType(RequestType.Post);
		String responseXML = soReassignService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.reassignSO()");
		return Response.ok(responseXML).build();

	}

	/**
	 * This method returns a response for reject reschedule service order. Here,
	 * reschedule request is requested by the provider.
	 * 
	 * @param String
	 *            buyerResourceId, String soId
	 * @return Response
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @DELETE
	 * 
	 * @Path(
	 * "/provider/{providerId}/providerresources/{providerResourceId}/serviceorder/rejectReschedule/{so_id}"
	 * ) public Response
	 * getResponseForProviderRejectReschedule(@PathParam("so_id") String soId,
	 * 
	 * @PathParam("providerResourceId") String providerResourceId) { if
	 * (logger.isInfoEnabled()) logger.info(
	 * "Entering SORequestProcessor.getResponseForProviderRejectReschedule()");
	 * APIRequestVO apiVO = new APIRequestVO(); Map<String, String> reqMap =
	 * CommonUtility.getRequestMap( getHttpRequest().getParameterMap());
	 * apiVO.setRequestType(BaseService.RequestType.Get);
	 * apiVO.setRequestFromGetDelete(reqMap);
	 * apiVO.addProperties(APIRequestVO.PROVIDER_RESOURCE_ID,
	 * providerResourceId); apiVO.addProperties(APIRequestVO.SOID, soId); String
	 * responseXML = providerRejectRescheduleService.doSubmit(apiVO); if
	 * (logger.isInfoEnabled()) logger.info(
	 * "Leaving SORequestProcessor.getResponseForProviderRejectReschedule()");
	 * return Response.ok(responseXML).build();
	 * 
	 * }
	 */
	/**
	 * This method returns a response for reject reschedule service order. Here,
	 * reschedule request is requested by the provider.
	 * 
	 * @param String
	 *            buyerResourceId, String soId
	 * @return Response
	 */
	// @SuppressWarnings("unchecked")
	@PUT
	@Path("/providers/{providerId}/serviceorders/{soId}/reschedulerequest")
	public Response getResponseForProviderRescheduleResponse(
			@PathParam("soId") String soId,
			@PathParam("providerId") String providerId,
			String soproviderRescheduleRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering SORequestProcessor.getResponseForProviderRejectReschedule()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Put);
		apiVO.setRequestFromPostPut(soproviderRescheduleRequest);
		apiVO.setProviderId(providerId);
		apiVO.setSOId(soId);
		String responseXML = soProviderRescheduleResponseService
				.doSubmit(apiVO);
		if (logger.isInfoEnabled())
			logger.info("Leaving SORequestProcessor.getResponseForProviderRejectReschedule()");
		return Response.ok(responseXML).build();

	}

	/**
	 * This method returns a response for a search service order request by
	 * provider.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{providerId}/resources/{resourceId}/serviceorders/search/filters")
	// providers/{providerId}/serviceorders/search
	public Response getResponseForSearchbyProvider(
			@PathParam("providerId") String providerId,
			@PathParam("resourceId") String resourceId) {
		logger.info("Entering SORequestProcessor.getResponseForSearchbyProvider()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.addProperties(APIRequestVO.PROVIDER_RESOURCE_ID, resourceId);
		//SLT-4491 Added providerId as Path Param and adding to apiVO for validation
		apiVO.addProperties(APIRequestVO.PROVIDERID, providerId);
		apiVO.setRequestType(RequestType.Get);
		String responseXML = searchByProviderService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.getResponseForSearchbyProvider()");
		return Response.ok(responseXML).build();
	}

	/**
	 * This method returns a response for a search service order request by
	 * provider.
	 * 
	 * @param String
	 *            firmId
	 * @param soId
	 * @param acceptSORequest
	 *            : requestXML
	 * @return Response
	 */
	@POST
	@Path("/providers/{firmId}/serviceorders/{soId}/accept")
	public Response getResponseForAcceptOrder(
			@PathParam("firmId") String firmId, @PathParam("soId") String soId,
			String acceptSORequest) {
		logger.info("Entering SORequestProcessor.getResponseForAcceptOrder()");
		Map<String, String> requestMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(acceptSORequest);
		apiVO.addProperties(APIRequestVO.SOID, soId);
		apiVO.addProperties(APIRequestVO.PROVIDERID, firmId);
		apiVO.setRequestType(RequestType.Post);
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, requestMap);
		String responseXML = acceptService.doSubmit(apiVO);
		logger.info("Leaving SORequestProcessor.getResponseForAcceptOrder()");
		return Response.ok(responseXML).build();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyer_id}/serviceorders/{so_ids}/priceHistory/")
	public Response getPriceDetails(@PathParam("buyer_id") String buyerId,
			@PathParam("so_ids") String soIds) {
		if (logger.isInfoEnabled()) {
			logger.info("getPriceDetails method started");
		}
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setBuyerId(buyerId);
		apiVO.addProperties(PublicAPIConstant.SO_ID_LIST, soIds);
		apiVO.setLocale(getHttpRequest().getLocale());
		String responseXML = soPriceService.doSubmit(apiVO);
		return Response.ok(responseXML).build();
	}

	/**
	 * SL-21086 This method returns a response for sub-status request
	 * 
	 * @param String
	 *            buyerId
	 * @return Response
	 */
	@GET
	@Path("/buyers/{buyer_id}/getSubstatus")
	public Response getResponseForSubstatus(@PathParam("buyer_id") String buyerId) {
		if (logger.isInfoEnabled()) {
			logger.info("getResponseForSubstatus method started");
		}
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setBuyerId(buyerId);
		String responseXML = subStatusService.doSubmit(apiVO);
		return Response.ok(responseXML).build();
	}

	/**
	 * This method returns accept/decline an estimate for the buyer - customer
	 * request.
	 * @param String buyerId, String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/estimateStatus")
	public Response updateEstimateStatus(@PathParam("so_id")
	String soId, @PathParam("buyer_id")
	String buyerId,String requestXML) {
		if (logger.isInfoEnabled()){
			logger.info("updateEstimateStatus method started");
		}
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Put);
		String response = null;

		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Put);
		logger.info("getResponseForAcceptDeclineEstimate method doSubmit::");
		response = soUpdateEstimateStatusService.doSubmit(apiVO);
		response = PublicAPIConstant.XML_VERSION + response;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving SORequestProcessor.updateEstimateStatus()");
		}
		return Response.ok(response).build();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/buyers/{buyer_id}/serviceorders/{so_id}/updateEstimate")
	public Response addEstimate(@PathParam("so_id")
	String soId, @PathParam("buyer_id")
	String buyerId,String requestXML) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Post);
		String response = null;
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Post);
		logger.info("getResponseForaddEstimate method doSubmit::");
		response = soUpdateEstimateService.doSubmit(apiVO);
		response = PublicAPIConstant.XML_VERSION + response;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving SORequestProcessor.addEstimate()");
		}
		return Response.ok(response).build();	
	}
	
	/**
	 *R16_0_2: Get Closed Service Orders For Firm 
	 * This method returns a response for Get Closed Service Orders For Firm API
	 * 
	 * @param String
	 *            buyerResourceId, String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{providerId}/closedorders")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public RetrieveClosedOrdersResponse getResponseForClosedOrders(@PathParam("providerId")
	String providerId) {
		if (logger.isInfoEnabled())
			logger.info("getResponseForClosedOrders method started");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setProviderId(providerId);
		String responseXML = soGetClosedOrdersService.doSubmit(apiVO);
		responseXML = PublicAPIConstant.XML_VERSION + responseXML;

		return (RetrieveClosedOrdersResponse) convertXMLStringtoRespObject(responseXML,
				RetrieveClosedOrdersResponse.class);
	}

	/**
	 * method to fetch the details of firms
	 * @param getFirmDetailsRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getFirmDetails")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public GetFirmDetailsResponse getFirmDetails(GetFirmDetailsRequest getFirmDetailsRequest) {
		String firmDetailsXML = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String firmDetailsRequest = convertReqObjectToXMLString(getFirmDetailsRequest, GetFirmDetailsRequest.class); 
		apiVO.setRequestFromPostPut(firmDetailsRequest);
		apiVO.setRequestType(RequestType.Post);
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, reqMap);

		firmDetailsXML = getFirmDetailsService.doSubmit(apiVO);
		firmDetailsXML = PublicAPIConstant.XML_VERSION + firmDetailsXML;

		return (GetFirmDetailsResponse) convertXMLStringtoRespObject(firmDetailsXML,
				GetFirmDetailsResponse.class);
	}
	
	@GET
	@Path("/buyers/{buyer_id}/buyerServices")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public ServiceCategoryResponse getBuyerSkus( @PathParam("buyer_id")String buyerId) {
		if (logger.isInfoEnabled()){
			logger.info("getBuyerSkus method started");
		}
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(RequestType.Get);
        apiVO.setBuyerId(buyerId);
        response = buyerSkuService.doSubmit(apiVO);
        response = PublicAPIConstant.XML_VERSION + response;
		return (ServiceCategoryResponse) convertXMLStringtoRespObject(response,ServiceCategoryResponse.class);
	}
	
	@GET
	@Path("/buyers/{buyer_id}/serviceOrders")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public GetServiceOrdersResponse getProviderDayBeforeServiceSO( @PathParam("buyer_id")String buyerId, @QueryParam("noOfdays")String noOfdays) {
		if (logger.isInfoEnabled()){
			logger.info("getProviderDayBeforeServiceSO method started");
		}
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());

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

		apiVO.setRequestType(BaseService.RequestType.Get);
		
		apiVO.setRequestFromGetDelete(reqMap);
        apiVO.setBuyerId(buyerId);
        response = getAcceptedSODetailService.doSubmit(apiVO);
        response = PublicAPIConstant.XML_VERSION + response;
		return (GetServiceOrdersResponse) convertXMLStringtoRespObject(response,GetServiceOrdersResponse.class);
	}
	
	
	
	
	// Setter methods for Spring Injection {References in Spring Configuration
	// file: apiApplicationContext.xml}
	
	

	public GetAcceptedSODetailService getGetAcceptedSODetailService() {
		return getAcceptedSODetailService;
	}

	public void setGetAcceptedSODetailService(
			GetAcceptedSODetailService getAcceptedSODetailService) {
		this.getAcceptedSODetailService = getAcceptedSODetailService;
	}

	public void setTimeOnSiteService(
			SOProviderTimeOnSiteService timeOnSiteService) {
		this.timeOnSiteService = timeOnSiteService;
	}

	

	public SOProviderTimeOnSiteService getTimeOnSiteService() {
		return timeOnSiteService;
	}

	public void setRetrieveService(SORetrieveService retrieveService) {
		this.retrieveService = retrieveService;
	}

	public void setSoPostV1Service(SOPostService soPostV1Service) {
		this.soPostV1Service = soPostV1Service;
	}

	public void setAddNoteService(SOAddNoteService addNoteService) {
		this.addNoteService = addNoteService;
	}

	public void setCancelService(SOCancelService cancelService) {
		this.cancelService = cancelService;
	}

	public void setCreateService(SOCreateService createService) {
		this.createService = createService;
	}

	public void setRescheduleService(SORescheduleService rescheduleService) {
		this.rescheduleService = rescheduleService;
	}

	public void setCancelRescheduleService(
			SOCancelRescheduleService cancelRescheduleService) {
		this.cancelRescheduleService = cancelRescheduleService;
	}

	public SOProviderRescheduleService getProviderRescheduleService() {
		return providerRescheduleService;
	}

	public void setProviderRescheduleService(
			SOProviderRescheduleService providerRescheduleService) {
		this.providerRescheduleService = providerRescheduleService;
	}

	public void setSoEditService(SOEditService soEditService) {
		this.soEditService = soEditService;
	}

	public void setSearchService(SOSearchService searchService) {
		this.searchService = searchService;
	}

	public void setAcceptService(SOAcceptService acceptService) {
		this.acceptService = acceptService;
	}

	public void setSoAddProviderNoteService(
			SOAddProviderNoteService soAddProviderNoteService) {
		this.soAddProviderNoteService = soAddProviderNoteService;
	}

	public void setCreateConditionalOfferService(
			SOCreateConditionalOfferService createConditionalOfferService) {
		this.createConditionalOfferService = createConditionalOfferService;
	}

	public void setProviderReportProblemService(
			SOProviderReportProblemService providerReportProblemService) {
		this.providerReportProblemService = providerReportProblemService;
	}

	public void setResolveProviderProblemService(
			ResolveProblemByProviderService resolveProviderProblemService) {
		this.resolveProviderProblemService = resolveProviderProblemService;
	}

	public SOReleaseService getSoReleaseService() {
		return soReleaseService;
	}

	public void setSoReleaseService(SOReleaseService soReleaseService) {
		this.soReleaseService = soReleaseService;
	}

	public void setSoProviderRetrieveService(
			SOProviderRetrieveService soProviderRetrieveService) {
		this.soProviderRetrieveService = soProviderRetrieveService;
	}

	public SOReassignService getSoReassignService() {
		return soReassignService;
	}

	public void setSoReassignService(SOReassignService soReassignService) {
		this.soReassignService = soReassignService;
	}

	public void setAddSODocService(AddSODocService addSODocService) {
		this.addSODocService = addSODocService;
	}

	public void setDeleteSODocService(DeleteSODocService deleteSODocService) {
		this.deleteSODocService = deleteSODocService;
	}

	public void setReadSODocService(ReadSODocService readSODocService) {
		this.readSODocService = readSODocService;
	}

	public void setSearchByProviderService(
			SOSearchByProviderService searchByProviderService) {
		this.searchByProviderService = searchByProviderService;
	}

	public void setCompleteSOService(SOCompleteSOService completeSOService) {
		this.completeSOService = completeSOService;
	}

	public void setProviderRejectRescheduleService(
			SOProviderRejectRescheduleService providerRejectRescheduleService) {
		this.providerRejectRescheduleService = providerRejectRescheduleService;
	}

	public void setSoRescheduleResponseService(
			SORescheduleResponseService soRescheduleResponseService) {
		this.soRescheduleResponseService = soRescheduleResponseService;
	}

	public void setSoProviderRescheduleResponseService(
			SOProviderRescheduleResponseService soProviderRescheduleResponseService) {
		this.soProviderRescheduleResponseService = soProviderRescheduleResponseService;
	}

	public void setAcceptRejectReleaseService(
			SOAcceptRejectReleaseService acceptRejectReleaseService) {
		this.acceptRejectReleaseService = acceptRejectReleaseService;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public SORetrieveSpendLimitService getSpendLimitIncreaseretrieveService() {
		return retrieveSpendLimitService;
	}

	public void setSpendLimitIncreaseretrieveService(
			SORetrieveSpendLimitService spendLimitIncreaseretrieveService) {
		this.retrieveSpendLimitService = spendLimitIncreaseretrieveService;
	}

	public GetSubStatusService getSubStatusService() {
		return subStatusService;
	}

	public void setSubStatusService(GetSubStatusService subStatusService) {
		this.subStatusService = subStatusService;
	}

	public GetFirmDetailsService getGetFirmDetailsService() {
		return getFirmDetailsService;
	}

	public void setGetFirmDetailsService(GetFirmDetailsService getFirmDetailsService) {
		this.getFirmDetailsService = getFirmDetailsService;
    }
	
	public SOGetClosedOrdersService getSoGetClosedOrdersService() {
		return soGetClosedOrdersService;
	}
	public void setSoGetClosedOrdersService(
			SOGetClosedOrdersService soGetClosedOrdersService) {
		this.soGetClosedOrdersService = soGetClosedOrdersService;
	}

	public SOUpdateEstimateStatusService getSoUpdateEstimateStatusService() {
		return soUpdateEstimateStatusService;
	}

	public void setSoUpdateEstimateStatusService(
			SOUpdateEstimateStatusService soUpdateEstimateStatusService) {
		this.soUpdateEstimateStatusService = soUpdateEstimateStatusService;
	}
	
	public SOUpdateEstimateService getSoUpdateEstimateService() {
		return soUpdateEstimateService;
	}

	public void setSoUpdateEstimateService(
			SOUpdateEstimateService soUpdateEstimateService) {
		this.soUpdateEstimateService = soUpdateEstimateService;
	}

	public GetSOPriceDetailsService getSoPriceService() {
		return soPriceService;
	}

	public void setSoPriceService(GetSOPriceDetailsService soPriceService) {
		this.soPriceService = soPriceService;
	}

	public SOUpdateService getUpdateService() {
		return updateService;
	}

	public void setUpdateService(SOUpdateService updateService) {
		this.updateService = updateService;
	}

	//converting XML to Object and vice versa
	protected String convertReqObjectToXMLString(Object request, Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return xstream.toXML(request).toString();
	}

	protected Object convertXMLStringtoRespObject(String request,
			Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return (Object) xstream.fromXML(request);
	}

	protected XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter(
				PublicAPIConstant.DATE_FORMAT_YYYY_MM_DD, new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}

	public BuyerSkuService getBuyerSkuService() {
		return buyerSkuService;
	}

	public void setBuyerSkuService(BuyerSkuService buyerSkuService) {
		this.buyerSkuService = buyerSkuService;
	}

}

