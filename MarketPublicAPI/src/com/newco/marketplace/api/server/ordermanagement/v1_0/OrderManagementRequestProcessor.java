

package com.newco.marketplace.api.server.ordermanagement.v1_0;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.ordermanagement.v1_0.FetchSOService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.GetCallInfoService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.GetOrderManagementTabsService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.GetPrecallHistoryService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.GetReasonCodesService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.GetReleaseInfoService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.SOAssignProviderService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.SOEditAppointmentTimeService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.SOEditSOLocationNotesService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.SOGetEligibleProvidersService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.SOProviderCallService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.SOReleaseService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.SetSOPriorityService;
import com.newco.marketplace.api.services.ordermanagement.v1_0.UpdateScheduleDetailsService;
import com.newco.marketplace.api.services.so.SOReassignService;
import com.newco.marketplace.api.services.so.v1_1.SOAcceptService;
import com.newco.marketplace.api.services.so.v1_1.SOAddNoteService;
import com.newco.marketplace.api.services.so.v1_1.SOCreateConditionalOfferService;
import com.newco.marketplace.api.services.so.v1_1.SORescheduleService;
import com.newco.marketplace.api.services.so.v1_1.SOWithdrawCondOfferService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import common.Logger;

/**
 * This class would act as an intercepter for all the public api services in the
 * application for order management.
 * 
 * @author Infosys
 * @version 1.0
 */
//routing all incoming requests to the OrderManagementRequestProcessor class
@Path("om/v1.0")
public class OrderManagementRequestProcessor {
	private Logger logger = Logger.getLogger(OrderManagementRequestProcessor.class);

	// services
	protected FetchSOService fetchSoService;
	protected SOGetEligibleProvidersService eligibleProvidersService;
	protected SOAssignProviderService assignProviderService;
	protected SOReassignService reassignService;
	protected SOAcceptService acceptService;
	protected SOAddNoteService addNoteService;
	protected SORescheduleService rescheduleService;
	protected SOCreateConditionalOfferService conditionalOfferService;
	protected SOEditSOLocationNotesService editSOLocationNotesService;
	protected SOEditAppointmentTimeService appointmentTimeService;
	protected UpdateScheduleDetailsService scheduleDetailsService;
	protected GetCallInfoService callInfoService;
	protected GetPrecallHistoryService precallHistoryService;
	protected SetSOPriorityService priorityService;
	protected SOProviderCallService callService;
	protected GetReasonCodesService reasonCodesService;
	protected GetOrderManagementTabsService orderManagementTabsService;
	protected GetReleaseInfoService releaseInfoService;
	protected SOReleaseService releaseService;
	protected SOWithdrawCondOfferService withdrawCondOfferService;

	// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;

	

	/**
	 * This method returns a response for a Fetch service orders request.
	 * 
	 */
	/**
	 * @param providerId
	 * @param resourceId
	 * @param fetchRequest
	 * @return
	 */
	@PUT
	@Path("/providers/{provider_id}/resources/{resource_id}/ordermanagement/serviceorders")

	public Response getResponseForFetchOrders (@PathParam("provider_id")
	String providerId, @PathParam("resource_id")
	String resourceId, String fetchRequest) {
		logger.info("Entering OrderManagementRequestProcessor.getResponseForFetchOrders()");
		String fetchSoResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(fetchRequest);
		apiVO.setProviderId(providerId);
		apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		apiVO.setRequestType(RequestType.Put);
		fetchSoResponse = fetchSoService.doSubmit(apiVO);
		fetchSoResponse = PublicAPIConstant.XML_VERSION + fetchSoResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForFetchOrders()");
		return Response.ok(fetchSoResponse).build();
	}
	
	
	/**
	 * This method returns a response for a Fetch Eligible Providers request.
	 * 
	 */
	/**
	 * @param firmId
	 * @param soId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{firm_Id}/serviceorders/{so_id}/soproviders")

	public Response getResponseForGetEligibleProviders (@PathParam("firm_Id")
	String firmId, @PathParam("so_id")
	String soId) {
		logger.info("Entering OrderManagementRequestProcessor.getResponseForGetEligibleProviders()");
		String eligibleProvidersResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setProviderId(firmId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Get);
		eligibleProvidersResponse = eligibleProvidersService.doSubmit(apiVO);
		eligibleProvidersResponse = PublicAPIConstant.XML_VERSION + eligibleProvidersResponse;
		logger.info("Leaving OrderManagementRequestProcessor.getResponseForGetEligibleProviders()");
		return Response.ok(eligibleProvidersResponse).build();
	}
	
	/**
	 * This method returns a response for a Assign Provider request.
	 *
	 */
	/**
	 * @param firmId
	 * @param soId
	 * @param assignProviderRequest
	 * @return
	 */
	@PUT
	@Path("/providers/{firm_Id}/serviceorders/{so_id}/assign")

	public Response getResponseForAssignProvider(@PathParam("firm_Id")
	String firmId, @PathParam("so_id")
	String soId, String assignProviderRequest) {
		logger.info("Entering OrderManagementRequestProcessor.getResponseForAssignProvider()");
		String assignProviderResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(assignProviderRequest);
		apiVO.setProviderId(firmId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Put);
		assignProviderResponse = assignProviderService.doSubmit(apiVO);
		assignProviderResponse = PublicAPIConstant.XML_VERSION + assignProviderResponse;
		logger.info("Leaving OrderManagementRequestProcessor.getResponseForAssignProvider()");
		return Response.ok(assignProviderResponse).build();
	}
	
	
	/**
	 * This method returns a response for a Edit Service Location Notes request.
	 * @param firmId
	 * @param soId
	 * @param editSLNotesRequest
	 * @return
	 */
	@PUT
	@Path("/providers/{firm_Id}/serviceorders/{so_Id}/soLocationNotes")
	public Response getResponseForEditServiceLocationNotes (@PathParam("firm_Id")
	String firmId, @PathParam("so_Id")
	String soId, String editSLNotesRequest) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForEditServiceLocationNotes()");
		String editSLNotesResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		apiVO.setRequestFromPostPut(editSLNotesRequest);
		apiVO.setProviderId(firmId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Put);
		editSLNotesResponse = editSOLocationNotesService.doSubmit(apiVO);
		editSLNotesResponse = PublicAPIConstant.XML_VERSION + editSLNotesResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForEditServiceLocationNotes()");
		return Response.ok(editSLNotesResponse).build();
	}
	
	/**
	 * This method returns a response for a Update Appointment Time request.
	 * 
	 * @param firmId
	 * @param soId
	 * @param updateAppointmentTimeRequest
	 * @return
	 */
	@PUT
	@Path("/providers/{firm_Id}/serviceorders/{so_Id}/appointmentTime")

	public Response getResponseForUpdateAppointmentTime (@PathParam("firm_Id")
	String firmId, @PathParam("so_Id")
	String soId, String updateAppointmentTimeRequest) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForUpdateAppointmentTime()");
		String updateAppointmentTimeResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		apiVO.setRequestFromPostPut(updateAppointmentTimeRequest);
		apiVO.setProviderId(firmId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Put);
		updateAppointmentTimeResponse = appointmentTimeService.doSubmit(apiVO);
		updateAppointmentTimeResponse = PublicAPIConstant.XML_VERSION + updateAppointmentTimeResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForUpdateAppointmentTime()");
		return Response.ok(updateAppointmentTimeResponse).build();
	}
	
	/**
	 * This method returns a response for a Update schedule details request.
	 * 
	 * @param firmId
	 * @param soId
	 * @param updateScheduleDetailsRequest
	 * @return
	 */
	
	@PUT
	@Path("/providers/{firm_Id}/serviceorders/{so_Id}/updateScheduleDetails")
	public Response getResponseForUpdateScheduleDetails(@PathParam("firm_Id")
	String firmId, @PathParam("so_Id")
	String soId, String updateScheduleDetailsRequest) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForUpdateScheduleDetails()");
		String updateScheduleDetailsResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(updateScheduleDetailsRequest);
		apiVO.setProviderId(firmId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Put);
		updateScheduleDetailsResponse = scheduleDetailsService.doSubmit(apiVO);
		updateScheduleDetailsResponse = PublicAPIConstant.XML_VERSION + updateScheduleDetailsResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForUpdateScheduleDetails()");
		return Response.ok(updateScheduleDetailsResponse).build();
	}
	
	
	/**
	 * This method returns a response for a Get Info For Call request.
 	 * @param firmId
	 * @param soId
	 * @param getInfoForCallRequest
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{firm_Id}/serviceorders/{so_Id}/call")
	public Response getResponseForGetInfoForCall  (@PathParam("firm_Id")
	String firmId, @PathParam("so_Id")
	String soId) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForGetInfoForCall()");
		String getInfoForCallResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setProviderId(firmId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Get);
		getInfoForCallResponse = callInfoService.doSubmit(apiVO);
		getInfoForCallResponse = PublicAPIConstant.XML_VERSION + getInfoForCallResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForGetInfoForCall()");
		return Response.ok(getInfoForCallResponse).build();
	}
	/**
	 * This method returns a response for a Get precall history request.
 	 * @param firmId
	 * @param soId
	 * @param getInfoForCallRequest
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{firm_Id}/serviceorders/{so_Id}/preCallHistory")
	public Response getResponseForPrecallHistoryDetails  (@PathParam("firm_Id")
	String firmId, @PathParam("so_Id")
	String soId) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForPrecallHistoryDetails()");
		String getPrecallHistoryDetailsResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setProviderId(firmId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Get);
		getPrecallHistoryDetailsResponse = precallHistoryService.doSubmit(apiVO);
		getPrecallHistoryDetailsResponse = PublicAPIConstant.XML_VERSION + getPrecallHistoryDetailsResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForPrecallHistoryDetails()");
		return Response.ok(getPrecallHistoryDetailsResponse).build();
	}
	
	
	/**
	 * This method returns a response for a flag Update request.
	 * @param firmId
	 * @param soId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{firm_Id}/serviceorders/{so_Id}/flag")
	public Response getResponseForflagUpdate  (@PathParam("firm_Id")
	String firmId, @PathParam("so_Id")
	String soId) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForflagUpdate()");
		String flagUpdateResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setProviderId(firmId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Get);
		flagUpdateResponse = priorityService.doSubmit(apiVO);
		flagUpdateResponse = PublicAPIConstant.XML_VERSION + flagUpdateResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForflagUpdate()");
		return Response.ok(flagUpdateResponse).build();
	}
	
	/**
	 * 
	 * This method returns a response for Call request.
	 * 
	 * @param firmId
	 * @param soId
	 * @return
	 */
	@PUT
	@Path("/providers/{firm_Id}/serviceorders/{so_Id}/preCall")
	public Response getResponseForCall  (@PathParam("firm_Id")
	String firmId, @PathParam("so_Id")
	String soId,String providerCallRequest) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForCall()");
		String providerCallResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(providerCallRequest);
		apiVO.setProviderId(firmId);
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Put);
		providerCallResponse = callService.doSubmit(apiVO);
		providerCallResponse = PublicAPIConstant.XML_VERSION + providerCallResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForCall()");
		return Response.ok(providerCallResponse).build();
	}
	
	/**
	 * 
	 * This method returns a response for Reason Codes request.
	 * 
	 * @param reasonType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/reasoncodes/{reasonType}")
	public Response getResponseForReasonCodes  (@PathParam("reasonType")
	String reasonType) {
		logger.info("Entering OrderManagementRequestProcessor.getResponseForReasonCodes()");
		String reasonCodesResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.addProperties("reasonType", reasonType);
		apiVO.setRequestType(RequestType.Get);
		reasonCodesResponse = reasonCodesService.doSubmit(apiVO);
		reasonCodesResponse = PublicAPIConstant.XML_VERSION + reasonCodesResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForReasonCodes()");
		return Response.ok(reasonCodesResponse).build();
	}
	

	/**
	 * 
	 * This method returns a response for Tab list request.
	 * 
	 * @param providerId
	 * @param resourceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{provider_Id}/resources/{resource_Id}/tabs")
	public Response getResponseForTablist  (@PathParam("provider_Id")
	String providerId, @PathParam("resource_Id")
	String resourceId) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForTablist()");
		String tablistResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setProviderId(providerId);
		apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		
		apiVO.setRequestType(RequestType.Get);
		tablistResponse = orderManagementTabsService.doSubmit(apiVO);
		tablistResponse = PublicAPIConstant.XML_VERSION + tablistResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForTablist()");
		return Response.ok(tablistResponse).build();
	}
	
	/**
	 * 
	 * This method returns a response for Info For Release request.
	 * 
	 * @param providerId
	 * @param resourceId
	 * @param soId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{provider_Id}/resources/{resource_Id}/serviceorders/{so_Id}/releaseInfo")
	public Response getResponseForGetInfoForRelease(@PathParam("provider_Id")
	String providerId, @PathParam("resource_Id")
	String resourceId,@PathParam("so_Id")
	String soId) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForGetInfoForRelease()");
		String tablistResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setProviderId(providerId);
		apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		apiVO.setSOId(soId);
		
		apiVO.setRequestType(RequestType.Get);
		tablistResponse = releaseInfoService.doSubmit(apiVO);
		tablistResponse = PublicAPIConstant.XML_VERSION + tablistResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForGetInfoForRelease()");
		return Response.ok(tablistResponse).build();
	}
	/**
	 * @param providerId
	 * @param resourceId
	 * @param soId
	 * @param releaseRequest
	 * @return
	 */
	@PUT
	@Path("/providers/{provider_Id}/resources/{resource_Id}/serviceorders/{so_Id}/release")
	public Response getResponseForRelease (@PathParam("provider_Id")
	String providerId, @PathParam("resource_Id")
	String resourceId,@PathParam("so_Id")
	String soId, String releaseRequest) {
			logger.info("Entering OrderManagementRequestProcessor.getResponseForRelease()");
		String releaseResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		
		apiVO.setRequestFromPostPut(releaseRequest);
		apiVO.setProviderId(providerId);
		apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		apiVO.setSOId(soId);
		
		apiVO.setRequestType(RequestType.Put);
		releaseResponse = releaseService.doSubmit(apiVO);
		releaseResponse = PublicAPIConstant.XML_VERSION + releaseResponse;
			logger.info("Leaving OrderManagementRequestProcessor.getResponseForRelease()");
		return Response.ok(releaseResponse).build();
	}
	
	/**
	 * @param providerId
	 * @param resourceId
	 * @param soId
	 * @param releaseRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/providers/{providerId}/resources/{resourceId}/serviceorders/{soId}/withdraw")
	public Response getResponseForWithdrawConditionalOffer(@PathParam("soId")
	String soId, @PathParam("providerId")
	String providerId, @PathParam("resourceId")
	String resourceId) {
		logger.info("Entering OrderManagementRequestProcessor.getResponseForWithdrawConditionalOffer()");
		String withdrawOfferResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setSOId(soId);
		apiVO.setProviderId(providerId);
		apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);
		withdrawOfferResponse = withdrawCondOfferService.doSubmit(apiVO);
		withdrawOfferResponse = PublicAPIConstant.XML_VERSION + withdrawOfferResponse;
		logger.info("Leaving SORequestProcessor.getResponseForWithdrawConditionalOffer()");
		return Response.ok(withdrawOfferResponse).build();
	}
		
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	public SOGetEligibleProvidersService getEligibleProvidersService() {
		return eligibleProvidersService;
	}

	public void setEligibleProvidersService(
			SOGetEligibleProvidersService eligibleProvidersService) {
		this.eligibleProvidersService = eligibleProvidersService;
	}

	public SOAssignProviderService getAssignProviderService() {
		return assignProviderService;
	}

	public void setAssignProviderService(
			SOAssignProviderService assignProviderService) {
		this.assignProviderService = assignProviderService;
	}

	public FetchSOService getFetchSoService() {
		return fetchSoService;
	}

	/**
	 * @param fetchSoService
	 */
	/**
	 * @param fetchSoService
	 */
	public void setFetchSoService(FetchSOService fetchSoService) {
		this.fetchSoService = fetchSoService;
	}

	public SOReassignService getReassignService() {
		return reassignService;
	}

	public void setReassignService(SOReassignService reassignService) {
		this.reassignService = reassignService;
	}

	public SOAcceptService getAcceptService() {
		return acceptService;
	}

	public void setAcceptService(SOAcceptService acceptService) {
		this.acceptService = acceptService;
	}

	public SOAddNoteService getAddNoteService() {
		return addNoteService;
	}

	public void setAddNoteService(SOAddNoteService addNoteService) {
		this.addNoteService = addNoteService;
	}

	public SORescheduleService getRescheduleService() {
		return rescheduleService;
	}

	public void setRescheduleService(SORescheduleService rescheduleService) {
		this.rescheduleService = rescheduleService;
	}

	public SOCreateConditionalOfferService getConditionalOfferService() {
		return conditionalOfferService;
	}

	public void setConditionalOfferService(
			SOCreateConditionalOfferService conditionalOfferService) {
		this.conditionalOfferService = conditionalOfferService;
	}

	public GetCallInfoService getCallInfoService() {
		return callInfoService;
	}

	public void setCallInfoService(GetCallInfoService callInfoService) {
		this.callInfoService = callInfoService;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public SOProviderCallService getCallService() {
		return callService;
	}

	public void setCallService(SOProviderCallService callService) {
		this.callService = callService;
	}

	public GetReasonCodesService getReasonCodesService() {
		return reasonCodesService;
	}

	public void setReasonCodesService(GetReasonCodesService reasonCodesService) {
		this.reasonCodesService = reasonCodesService;
	}

	public GetOrderManagementTabsService getOrderManagementTabsService() {
		return orderManagementTabsService;
	}

	public void setOrderManagementTabsService(
			GetOrderManagementTabsService orderManagementTabsService) {
		this.orderManagementTabsService = orderManagementTabsService;
	}

	public GetReleaseInfoService getReleaseInfoService() {
		return releaseInfoService;
	}

	public void setReleaseInfoService(GetReleaseInfoService releaseInfoService) {
		this.releaseInfoService = releaseInfoService;
	}

	public SOReleaseService getReleaseService() {
		return releaseService;
	}

	public void setReleaseService(SOReleaseService releaseService) {
		this.releaseService = releaseService;
	}
	public SOEditSOLocationNotesService getEditSOLocationNotesService() {
		return editSOLocationNotesService;
	}

	public void setEditSOLocationNotesService(
			SOEditSOLocationNotesService editSOLocationNotesService) {
		this.editSOLocationNotesService = editSOLocationNotesService;
	}

	public SOEditAppointmentTimeService getAppointmentTimeService() {
		return appointmentTimeService;
	}

	public void setAppointmentTimeService(
			SOEditAppointmentTimeService appointmentTimeService) {
		this.appointmentTimeService = appointmentTimeService;
	}

	public SetSOPriorityService getPriorityService() {
		return priorityService;
	}

	public void setPriorityService(SetSOPriorityService priorityService) {
		this.priorityService = priorityService;
	}

	public SOWithdrawCondOfferService getWithdrawCondOfferService() {
		return withdrawCondOfferService;
	}

	public void setWithdrawCondOfferService(
			SOWithdrawCondOfferService withdrawCondOfferService) {
		this.withdrawCondOfferService = withdrawCondOfferService;
	}
	public GetPrecallHistoryService getPrecallHistoryService() {
		return precallHistoryService;
	}

	public void setPrecallHistoryService(
			GetPrecallHistoryService precallHistoryService) {
		this.precallHistoryService = precallHistoryService;
	}
	public UpdateScheduleDetailsService getScheduleDetailsService() {
		return scheduleDetailsService;
	}

	public void setScheduleDetailsService(
			UpdateScheduleDetailsService scheduleDetailsService) {
		this.scheduleDetailsService = scheduleDetailsService;
	}
	

	
}
