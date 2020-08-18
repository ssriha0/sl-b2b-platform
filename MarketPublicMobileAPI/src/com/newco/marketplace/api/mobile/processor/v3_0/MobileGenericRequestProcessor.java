package com.newco.marketplace.api.mobile.processor.v3_0;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.searchCriteria.SOGetSearchCriteriaResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.Filter.GetFilterResponse;
import com.newco.marketplace.api.mobile.beans.GetReasonCodes.GetReasonCodesResponse;
import com.newco.marketplace.api.mobile.beans.UpdateServiceOrderFlag.UpdateServiceOrderFlagRequest;
import com.newco.marketplace.api.mobile.beans.UpdateServiceOrderFlag.UpdateServiceOrderFlagResponse;
import com.newco.marketplace.api.mobile.beans.assign.AssignServiceOrderRequest;
import com.newco.marketplace.api.mobile.beans.assign.AssignServiceOrderResponse;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferRequest;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.counterOffer.WithdrawCounterOfferRequest;
import com.newco.marketplace.api.mobile.beans.counterOffer.WithdrawCounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.deleteFilter.DeleteFilterResponse;
import com.newco.marketplace.api.mobile.beans.eligibleProviders.GetEligibleProviderResponse;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceRequest;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceResponse;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ValidateSecQuestAnsRequest;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ValidateSecQuestAnsResponse;
import com.newco.marketplace.api.mobile.beans.getRecievedOrders.RecievedOrdersResponse;
import com.newco.marketplace.api.mobile.beans.getprovidersolist.v3_0.GetProviderSOListResponse;
import com.newco.marketplace.api.mobile.beans.rejectServiceOrder.MobileRejectSORequest;
import com.newco.marketplace.api.mobile.beans.rejectServiceOrder.MobileRejectSOResponse;
import com.newco.marketplace.api.mobile.beans.rescheduleRespond.RescheduleRespondRequest;
import com.newco.marketplace.api.mobile.beans.rescheduleRespond.RescheduleResponse;
import com.newco.marketplace.api.mobile.beans.saveFilter.SaveFilterRequest;
import com.newco.marketplace.api.mobile.beans.saveFilter.SaveFilterResponse;
import com.newco.marketplace.api.mobile.beans.so.accept.MobileSOAcceptRequest;
import com.newco.marketplace.api.mobile.beans.so.accept.MobileSOAcceptResponse;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchResponse;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchResponse;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SOGetMobileDetailsResponse;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.MobileSOSubmitRescheduleRequest;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.MobileSOSubmitRescheduleResponse;
import com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3.MobileSOSubmitWarrantyHomeReasonCodeResponse;
import com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3.MobileSOSubmitWarrantyHomeReasonRequest;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionResponse;
import com.newco.marketplace.api.mobile.beans.v2_0.MobileTimeOnSiteRequest;
import com.newco.marketplace.api.mobile.beans.v2_0.MobileTimeOnSiteResponse;
//import com.newco.marketplace.api.mobile.beans.submitReschedule.MobileSOSubmitRescheduleRequest;
//import com.newco.marketplace.api.mobile.beans.submitReschedule.MobileSOSubmitRescheduleResponse;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ViewDashboardResponse;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.service.BaseService.RequestType;
import com.newco.marketplace.api.mobile.services.v3_0.AssignOrReassignSOService;
import com.newco.marketplace.api.mobile.services.v3_0.CounterOfferService;
import com.newco.marketplace.api.mobile.services.v3_0.DashboardRetrieveCountService;
import com.newco.marketplace.api.mobile.services.v3_0.DeleteFilterService;
import com.newco.marketplace.api.mobile.services.v3_0.ForgetUnamePwdService;
import com.newco.marketplace.api.mobile.services.v3_0.GetFilterService;
import com.newco.marketplace.api.mobile.services.v3_0.GetProviderSOListService;
import com.newco.marketplace.api.mobile.services.v3_0.GetReasonCodesService;
import com.newco.marketplace.api.mobile.services.v3_0.MobileAdvanceSearchSOService;
import com.newco.marketplace.api.mobile.services.v3_0.MobileSOAcceptService;
import com.newco.marketplace.api.mobile.services.v3_0.MobileSORejectService;
import com.newco.marketplace.api.mobile.services.v3_0.MobileSOSubmitRescheduleService;
import com.newco.marketplace.api.mobile.services.v3_0.MobileSOSubmitWarrantyHomeReasonCodeService;
import com.newco.marketplace.api.mobile.services.v3_0.MobileSearchSOService;
import com.newco.marketplace.api.mobile.services.v3_0.MobileTimeOnSiteService;
import com.newco.marketplace.api.mobile.services.v3_0.RecievedOrdersService;
import com.newco.marketplace.api.mobile.services.v3_0.RescheduleRespondService;
import com.newco.marketplace.api.mobile.services.v3_0.SODetailsRetrieveService;
import com.newco.marketplace.api.mobile.services.v3_0.SOGetEligibleProvidersService;
import com.newco.marketplace.api.mobile.services.v3_0.SaveFilterService;
import com.newco.marketplace.api.mobile.services.v3_0.SearchCriteriaService;
import com.newco.marketplace.api.mobile.services.v3_0.UpdateServiceOrderFlagService;
import com.newco.marketplace.api.mobile.services.v3_0.ValidateSecQuestAnsService;
import com.newco.marketplace.api.mobile.services.v3_0.WithdrawCounterOfferService;
import com.newco.marketplace.api.mobile.services.v3_0.UpdateSOCompletionService;
import com.newco.marketplace.api.server.MobileBaseRequestProcessor;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.mobile.constants.MPConstants;

/**
 * @author Infosys
 * $Revision: 1.0 $Date: 2015/04/10
 * Request Processor class for Mobile phase 2 API's
 */

@Path("v3.0")
@Consumes({ "application/xml", "application/json", "text/xml" })
@Produces({ "application/xml", "application/json", "text/xml" })
public class MobileGenericRequestProcessor extends MobileBaseRequestProcessor{
	@Resource
	protected HttpServletRequest httpRequest;
    protected HttpServletResponse httpResponse;
	private static final Logger LOGGER = Logger.getLogger(MobileGenericRequestProcessor.class);
	
	// service reference for GetSORouteList API
	protected SOGetEligibleProvidersService soEligibleProviderListService;
    // service reference for SOSearch API
	protected MobileSearchSOService mobileSearchSOService;
	// service reference for reportAProblem
	//protected ReportAProblemService reportAProblemService;
	//Service class for Reject order API
	protected MobileSORejectService rejectOrderService;
	// service reference for assignOrderService
	protected AssignOrReassignSOService assignOrReassignSOService;
	// service reference for SOAccept API
	protected MobileSOAcceptService mobileSOAcceptService;
	// service reference for resolving problem
	//protected ResolveProblemService resolveProblemService;
    // service reference for counterOffer
	protected CounterOfferService counterOfferService;
	// service reference for withdrawCcounterOffer
	protected WithdrawCounterOfferService withdrawCounterOfferService;
	// service reference for Place bid and Change bid APIs
	//protected MobilePlaceBidService mobilePlaceBidService;
	 // service reference for release SO
	//protected MobileSOReleaseService releaseOrderService ;
	//service to getReasonCodes
	protected GetReasonCodesService reasonCodesService;
	// service class for reassign provider for an SO
	//protected ReassignSOProviderService reassignSOProviderService;
	// service class for update schedule details
	//protected UpdateScheduleDetailsService updateScheduleDetailsService;
	// service for forgetUnamePwdService1
	protected ForgetUnamePwdService forgetUnamePwdService;
	
	// service for forgetUnamePwdService1
	protected ValidateSecQuestAnsService validateSecQuestAnsService;
	// Service for accept/cancel/reject reschedule request
	protected RescheduleRespondService respondRescheduleService;
	//service class for flag service orders for follow up by provider
	protected UpdateServiceOrderFlagService updateServiceOrderFlagService;
	// service class for submitting a reschedule request for an SO
	protected MobileSOSubmitRescheduleService mobileSOSubmitRescheduleService;
	// Service reference for filtering service orders for the provider.
	protected GetProviderSOListService getProviderSOListService;
	// Service reference for fetching the received orders list
	protected RecievedOrdersService recievedOrdersService;
	// Service reference for fetching the received orders list
	protected DeleteFilterService deleteFilterService;
	//Service reference for fetching filter names and search criteria for a provider
	protected GetFilterService getFilterService;
	// Service reference for fetching the SO details of received,accepted,active,problem status
	protected SODetailsRetrieveService soDetailsRetrieveService;
	// Service reference for ADVANCE search API
	protected MobileAdvanceSearchSOService mobileAdvanceSearchSOService;
	// Service reference for Save Filter API
	protected SaveFilterService saveFilterService;
	//Service reference for fetching Dashboard Count.
	protected DashboardRetrieveCountService retriveCountService;
	// Service reference for fetching the search criteria like market,provider,status and substatus
	protected SearchCriteriaService searchCriteriaService;
	// provider checkin checkout time update
	protected MobileTimeOnSiteService timeOnSiteV3Service;
	
	protected MobileSOSubmitWarrantyHomeReasonCodeService mobileSOSubmitWarrantyHomeReasonCodeService;
	
	protected UpdateSOCompletionService updateSOCompletionService;
	
	
	/**
	 * @param roleId
	 * @param firmId
	 * @param resourceId
	 * @param soId
	 * @return
	 * 
	 * to fetch eligible providers list
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorders/{soId}/soRouteList")
	
	public GetEligibleProviderResponse getSOEligibleProviders(@PathParam("roleId") Integer roleId,@PathParam("firmId") String firmId, @PathParam("providerId") String resourceId,@PathParam("soId")
	String soId) {
		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		apiVO.setRequestType(BaseService.RequestType.Get);		
		apiVO.setProviderId(firmId);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} else {
			apiVO.setProviderResourceId(0);
		}

		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setSOId(soId);
		apiVO.setRoleId(roleId);
		Integer loggingId=soEligibleProviderListService.logSOMobileHistory("",PublicMobileAPIConstant.GET_ROUTE_LIST,PublicMobileAPIConstant.HTTP_GET,apiVO);		
		String responseXML = soEligibleProviderListService.doSubmit(apiVO);
		soEligibleProviderListService.updateSOMobileResponse(loggingId, responseXML);
		return (GetEligibleProviderResponse) convertXMLStringtoRespObject(responseXML,
				GetEligibleProviderResponse.class);

	}

	
	public DashboardRetrieveCountService getRetriveCountService() {
		return retriveCountService;
	}


	public void setRetriveCountService(
			DashboardRetrieveCountService retriveCountService) {
		this.retriveCountService = retriveCountService;
	}


	/**
	 * @param roleId
	 * @param firmId
	 * @param resourceId
	 * @param soId
	 * @return
	 * 
	 * to fetch response for mobile SO search
	 * 
	 */

	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/search")
	public MobileSOSearchResponse getMobileSOSearchResponse(@PathParam("roleId") Integer roleId,@PathParam("firmId") String firmId,@PathParam("providerId") String resourceId,
			MobileSOSearchRequest mobileSOSearchRequest) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		apiVO.setRequestType(BaseService.RequestType.Post);		
		apiVO.setResourceId(resourceId);
		apiVO.setProviderId(firmId);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setRoleId(roleId);
		// log the request
		String searchRequest = convertReqObjectToXMLString(
				mobileSOSearchRequest, MobileSOSearchRequest.class); 
		apiVO.setRequestFromPostPut(searchRequest);
		Integer loggingId=mobileSearchSOService.logSOMobileHistory(searchRequest,PublicMobileAPIConstant.SEARCH_SO,PublicMobileAPIConstant.HTTP_POST,apiVO);		
		String responseXML = mobileSearchSOService.doSubmit(apiVO);
		mobileSearchSOService.updateSOMobileResponse(loggingId, responseXML);
		return (MobileSOSearchResponse) convertXMLStringtoRespObject(responseXML,
				MobileSOSearchResponse.class);

	}
	
	/**
	 * @param roleId
	 * @param firmId
	 * @param resourceId
	 * @param mobileSOAdvanceSearchRequest
	 * @return
	 * API for advance search
	 */
	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/advSearch")
	public MobileSOAdvanceSearchResponse getMobileSOAdvanceSearchResponse(@PathParam("roleId") Integer roleId,@PathParam("firmId") String firmId,@PathParam("providerId") Integer providerResourceId,
			MobileSOAdvanceSearchRequest mobileSOAdvanceSearchRequest) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		apiVO.setRequestType(BaseService.RequestType.Post);		
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerResourceId);
		apiVO.setRoleId(roleId);
		// log the request
		String advanceSearchReq = convertReqObjectToXMLString(
				mobileSOAdvanceSearchRequest, MobileSOAdvanceSearchRequest.class); 
		apiVO.setRequestFromPostPut(advanceSearchReq);
		Integer loggingId=mobileAdvanceSearchSOService.logSOMobileHistory(advanceSearchReq,PublicMobileAPIConstant.MOBILE_SO_ADVANCE_SEARCH,PublicMobileAPIConstant.HTTP_POST,apiVO);		
		String responseXML = mobileAdvanceSearchSOService.doSubmit(apiVO);
		mobileAdvanceSearchSOService.updateSOMobileResponse(loggingId, responseXML);
		return (MobileSOAdvanceSearchResponse) convertXMLStringtoRespObject(responseXML,
				MobileSOAdvanceSearchResponse.class);

	}
	/*@POST
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/reportProblem")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public ReportProblemResponse getResponseForReportAProblem (@PathParam("providerId") String providerId, @PathParam("soId")
	String soId,@PathParam("roleId")Integer roleId,@PathParam("firmId") String firmId, ReportProblemRequest reportProblemRequest) {
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		ReportProblemResponse reportProblemResponse = new ReportProblemResponse();
		String request=convertReqObjectToXMLString(reportProblemRequest,ReportProblemRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setResourceId(providerId);
		// log the request
		// get resource Id from vendor_resource	
		Integer resourceId=reportAProblemService.validateResourceId(providerId);
		apiVO.setProviderResourceId(resourceId);
		apiVO.setRoleId(roleId);
		Integer loggingId=reportAProblemService.logSOMobileHistory(request,PublicMobileAPIConstant.REPORT_A_PROBLEM,PublicMobileAPIConstant.HTTP_POST,apiVO);
		response = reportAProblemService.doSubmit(apiVO);
		reportProblemResponse=(ReportProblemResponse)convertXMLStringtoRespObject(response,ReportProblemResponse.class);
		//update response
		reportAProblemService.updateSOMobileResponse(loggingId, response);
		return reportProblemResponse;
	}*/
	
	/**
	 * 
	 * @param roleId
	 * @param providerId
	 * @param soId
	 * @param assignServiceOrderRequest
	 * @return
	 * Method to assign/reassign a provider for an so
	 */
	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/assign")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public AssignServiceOrderResponse getResponseForAssignProvider(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") Integer providerId,
			@PathParam("soId") String soId,
			AssignServiceOrderRequest assignServiceOrderRequest) {
		
		String assignServiceOrderResponseString = null;
		AssignServiceOrderResponse assignServiceOrderResponse = new AssignServiceOrderResponse();
		String request = convertReqObjectToXMLString(assignServiceOrderRequest,AssignServiceOrderRequest.class);

		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		
		apiVO.setProviderResourceId(providerId);	
		apiVO.setProviderId(firmId);			
		apiVO.setSOId(soId);
		apiVO.setRoleId(roleId);		
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		if(null != assignServiceOrderRequest){
			if(MPConstants.REASSIGN_SO.equals(assignServiceOrderRequest.getRequestFor())){
				apiVO.setAPISOValidationExcludeInd(true);
			}
		}
		Integer loggingId=assignOrReassignSOService.logSOMobileHistory(request,PublicMobileAPIConstant.ASSIGN_SERVICE_ORDER,PublicMobileAPIConstant.HTTP_POST,apiVO);
		assignServiceOrderResponseString = assignOrReassignSOService.doSubmit(apiVO);

		assignServiceOrderResponse = (AssignServiceOrderResponse) convertXMLStringtoRespObject(
				assignServiceOrderResponseString, AssignServiceOrderResponse.class);
		assignOrReassignSOService.updateSOMobileResponse(loggingId, assignServiceOrderResponseString);
		return assignServiceOrderResponse;
	}
	
	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/accept")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobileSOAcceptResponse mobileSOAccept(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") Integer resourceId,
			@PathParam("soId") String soId,
			MobileSOAcceptRequest mobileSOAcceptRequest) {
		
		// declare the response variables
		String mobileSOAcceptResponseString = null;
		MobileSOAcceptResponse mobileSOAcceptResponse = new MobileSOAcceptResponse();
		@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		// convert the request object to XML string- uses XStream toXML() method
		String request = convertReqObjectToXMLString(mobileSOAcceptRequest,MobileSOAcceptRequest.class);
        // create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		/*This reqmap is used to set groupind param for grouped so.This is not a mandatory param
		 * groupind = 1 --> grouped Order
		 * groupind = 0--> Normal Order*/
		apiVO.addProperties(PublicMobileAPIConstant.REQUEST_MAP, reqMap);
		// assign soId from the request URL
		apiVO.setSOId(soId);
		//set the firmId into providerId of API VO
		if(StringUtils.isNumeric(firmId)){
			apiVO.setProviderId(firmId);
		} 
		//Set provider resource Id into ProviderResourceId of API VO		
		apiVO.setProviderResourceId(resourceId);

		apiVO.setRoleId(roleId);
        // assign the XML string
		apiVO.setRequestFromPostPut(request);
		// assign the Request method as POST
		apiVO.setRequestType(RequestType.Post);
		// log the request
		Integer loggingId = mobileSOAcceptService.logSOMobileHistory(request, PublicMobileAPIConstant.MOBILE_SO_ACCEPT_V2, PublicMobileAPIConstant.HTTP_POST, apiVO);
        // call the doSubmit method of the SOBaseService--this call would call the execute method of mobileSOAcceptService
		mobileSOAcceptResponseString = mobileSOAcceptService.doSubmit(apiVO);
        // convert the XML string to object
		mobileSOAcceptResponse = (MobileSOAcceptResponse) convertXMLStringtoRespObject(mobileSOAcceptResponseString, MobileSOAcceptResponse.class);
        // update response 
		mobileSOAcceptService.updateSOMobileResponse(loggingId, mobileSOAcceptResponseString);
		return mobileSOAcceptResponse;
	}
	
	
	/**
	 * @param firmId
	 * @param soId
	 * @param providerId
	 * @param rejectSORequest
	 * @return
	 * 
	 *API to reject a SO from mobile
	 * 
	 */
	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/reject")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobileRejectSOResponse getResponseForRejectOrder(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") String providerId,
			@PathParam("soId") String soId,
			MobileRejectSORequest rejectSORequest) {

		
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		MobileRejectSOResponse rejectSOResponse = new MobileRejectSOResponse();
		Integer loggingId=null;
		Integer resourceId=null;
		String request = convertReqObjectToXMLString(rejectSORequest,
				MobileRejectSORequest.class);
		@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		
		apiVO.addProperties(PublicMobileAPIConstant.REQUEST_MAP, reqMap);
		
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);

		
		if (StringUtils.isNumeric(providerId)) {
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}			
		apiVO.setRoleId(roleId);

		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setResourceId(providerId);
		resourceId= rejectOrderService.validateResourceId(providerId);
		apiVO.setProviderResourceId(resourceId);
		
		loggingId= rejectOrderService.logSOMobileHistory(request,
				PublicMobileAPIConstant.REJECT_SO,
				PublicMobileAPIConstant.HTTP_POST, apiVO);
		response = rejectOrderService.doSubmit(apiVO);
		rejectSOResponse = (MobileRejectSOResponse) convertXMLStringtoRespObject(
				response, MobileRejectSOResponse.class);
	
		// update response
		rejectOrderService.updateSOMobileResponse(loggingId, response);
		return rejectSOResponse;
	}	
	/*@POST
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/resolveProblem")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public ResolveProblemResponse getResponseForResolveProblem (@PathParam("providerId") String providerId, @PathParam("soId")
	String soId,@PathParam("roleId")Integer roleId,@PathParam("firmId") String firmId, ResolveProblemOnSORequest resolveProblemRequest) {
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		ResolveProblemResponse resolveProblemResponse = new ResolveProblemResponse();
		String request=convertReqObjectToXMLString(resolveProblemRequest,ResolveProblemOnSORequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setResourceId(providerId);
		apiVO.setRoleId(roleId);

		// log the request
		// get resource Id from vendor_resource	
		Integer resourceId=resolveProblemService.validateResourceId(providerId);
		apiVO.setProviderResourceId(resourceId);
		Integer loggingId=resolveProblemService.logSOMobileHistory(request,PublicMobileAPIConstant.REPORT_A_PROBLEM,PublicMobileAPIConstant.HTTP_POST,apiVO);
		response = resolveProblemService.doSubmit(apiVO);
		resolveProblemResponse=(ResolveProblemResponse)convertXMLStringtoRespObject(response,ResolveProblemResponse.class);
		//update response
		reportAProblemService.updateSOMobileResponse(loggingId, response);
		return resolveProblemResponse;
	}*/
	
	/**
	 * R14.0 The WebService method for creating a counter offer
	 * 
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @param soId
	 * @param CounterOfferRequest
	 * @return CounterOfferResponse
	 */
	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/counterOffer")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public CounterOfferResponse getResponseForCounterOffer (@PathParam("roleId")Integer roleId, @PathParam("firmId") String firmId, 
			@PathParam("providerId") Integer providerId, @PathParam("soId") String soId, CounterOfferRequest counterOfferRequest) {
	
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		CounterOfferResponse counterOfferResponse = new CounterOfferResponse();
		String request=convertReqObjectToXMLString(counterOfferRequest,CounterOfferRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		/*@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.addProperties(PublicMobileAPIConstant.REQUEST_MAP, reqMap);*/
		//set provider Id
		apiVO.setProviderResourceId(providerId);
		//set soId
		apiVO.setSOId(soId);
		//set firmId
		apiVO.setProviderId(firmId);
		//set roleId
		apiVO.setRoleId(roleId);

		// log the request
		Integer loggingId=counterOfferService.logSOMobileHistory(request,PublicMobileAPIConstant.COUNTER_OFFER,PublicMobileAPIConstant.HTTP_POST,apiVO);
		response = counterOfferService.doSubmit(apiVO);
		counterOfferResponse=(CounterOfferResponse)convertXMLStringtoRespObject(response,CounterOfferResponse.class);
		  
		//update response in logging history
		counterOfferService.updateSOMobileResponse(loggingId, response);
		return counterOfferResponse;
	}
	
	

	/**
	 * R14.0 The WebService method for withdrawing a counter offer
	 * 
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @param soId
	 * @param WithdrawCounterOfferRequest
	 * @return WithdrawCounterOfferResponse
	 */
	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/withdrawOffer")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public WithdrawCounterOfferResponse getResponseForWithdrawCounterOffer (@PathParam("roleId")Integer roleId, @PathParam("firmId") String firmId, 
			@PathParam("providerId") Integer providerId, @PathParam("soId") String soId, WithdrawCounterOfferRequest withdrawCounterOfferRequest) {
	
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		WithdrawCounterOfferResponse withdrawCounterOfferResponse = new WithdrawCounterOfferResponse();
		String request=convertReqObjectToXMLString(withdrawCounterOfferRequest,WithdrawCounterOfferRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		//set provider Id
		apiVO.setProviderResourceId(providerId);
		//set soId
		apiVO.setSOId(soId);
		//set firmId
		apiVO.setProviderId(firmId);
		//set roleId
		apiVO.setRoleId(roleId);

		// log the request
		Integer loggingId=withdrawCounterOfferService.logSOMobileHistory(request,PublicMobileAPIConstant.WITHDRAW_COUNTER_OFFER,PublicMobileAPIConstant.HTTP_POST,apiVO);
		response = withdrawCounterOfferService.doSubmit(apiVO);
		withdrawCounterOfferResponse=(WithdrawCounterOfferResponse)convertXMLStringtoRespObject(response,WithdrawCounterOfferResponse.class);
		   
		//update response in logging history
		withdrawCounterOfferService.updateSOMobileResponse(loggingId, response);
		return withdrawCounterOfferResponse;
	}
	
	
	
	@GET
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/reasonCodes")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public GetReasonCodesResponse getResponseForReasonCodes(@PathParam("roleId") Integer roleId,
			 @PathParam("firmId") String firmId,@PathParam("providerId") String providerId) {
		GetReasonCodesResponse reasonCodeResponse = new GetReasonCodesResponse();
		@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		apiVO.setRequestType(BaseService.RequestType.Get);	
		/* This req map is used to set Query param 'reasontype'.
		 * Its value denotes the type of reasonCodes to be fetched.
		 * Multiple types can be fetched by passing available reasonCode type using delemitter '--'
		 * Example : reasonType=Reschedule_TypeofProblem_Reject
		 * */
		apiVO.setRequestFromGetDelete(reqMap);
		//set provider Id
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		//set firmId
		apiVO.setProviderId(firmId);
		//set roleId
		apiVO.setRoleId(roleId);

		// log the request
		Integer loggingId=reasonCodesService.logSOMobileHistory("",PublicMobileAPIConstant.GET_REASON_CODES, PublicMobileAPIConstant.HTTP_GET,apiVO);		
		// Getting reasoncodes for the reason types.
		String mobileReasonCodeResponseString = reasonCodesService.doSubmit(apiVO);
		// convert the XML string to object
		reasonCodeResponse = (GetReasonCodesResponse) convertXMLStringtoRespObject(mobileReasonCodeResponseString, GetReasonCodesResponse.class);
		reasonCodesService.updateSOMobileResponse(loggingId, mobileReasonCodeResponseString);
		return reasonCodeResponse;
	}

	/**
	 * @param firmId
	 * @param soId
	 * @param providerId
	 * @param releaseSORequest
	 * @return
	 * 
	 *API to release a SO from mobile
	 * 
	 */
	/*@POST
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/release")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobileReleaseSOResponse getResponseForRelease(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") String providerId,
			@PathParam("soId") String soId,
			MobileReleaseSORequest releaseSORequest) {

		
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		MobileReleaseSOResponse releaseSOResponse = new MobileReleaseSOResponse();
		Integer loggingId=null;
		Integer resourceId=null;
		String request = convertReqObjectToXMLString(releaseSORequest,
				MobileReleaseSORequest.class);

		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);

		if (StringUtils.isNumeric(providerId)) {
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		
		apiVO.setRoleId(roleId);

		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setResourceId(providerId);
		apiVO.setProviderResourceId(Integer.parseInt(providerId));
		resourceId= releaseOrderService.validateResourceId(providerId);
		apiVO.setProviderResourceId(resourceId);
		loggingId= releaseOrderService.logSOMobileHistory(request,
				PublicMobileAPIConstant.RELEASE_SO,
				PublicMobileAPIConstant.HTTP_POST, apiVO);
		response = releaseOrderService.doSubmit(apiVO);
		releaseSOResponse = (MobileReleaseSOResponse) convertXMLStringtoRespObject(
				response, MobileReleaseSOResponse.class);
		
		// update response
		releaseOrderService.updateSOMobileResponse(loggingId, response);
		return releaseSOResponse;
	}	*/	
	/*@POST
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/updateSchedule")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public UpdateScheduleDetailsResponse getResponseForUpdateScheduleDetails(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") String providerId,
			@PathParam("soId") String soId,
			UpdateScheduleDetailsRequest updateScheduleDetailsRequest) {

		String updateScheduleResponseString = null;
		UpdateScheduleDetailsResponse updateScheduleResponse = new UpdateScheduleDetailsResponse();
		String request = convertReqObjectToXMLString(updateScheduleDetailsRequest,UpdateScheduleDetailsRequest.class);

		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		
		apiVO.setResourceId(providerId);
		apiVO.setProviderId(firmId);			
		apiVO.setSOId(soId);
		apiVO.setRoleId(roleId);

		apiVO.setRequestFromPostPut(request);

		apiVO.setRequestType(RequestType.Post);

		Integer loggingId=updateScheduleDetailsService.logSOMobileHistory(request,PublicMobileAPIConstant.UPDATE_SCHEDULE_DETAILS,PublicMobileAPIConstant.HTTP_POST,apiVO);
		updateScheduleResponseString = updateScheduleDetailsService.doSubmit(apiVO);

		updateScheduleResponse = (UpdateScheduleDetailsResponse)convertXMLStringtoRespObject(updateScheduleResponseString, UpdateScheduleDetailsResponse.class);
		
		updateScheduleDetailsService.updateSOMobileResponse(loggingId, updateScheduleResponseString);

		return updateScheduleResponse;
	}*/
	
	/*@POST
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/placeBid")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobilePlaceBidResponse mobileSOPlaceBid(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") String resourceId,
			@PathParam("soId") String soId,
			MobilePlaceBidRequest mobilePlaceBidRequest) {
	
		// declare the response variables
		String mobilePlaceBidResponseString = null;
		MobilePlaceBidResponse mobilePlaceBidResponse = new MobilePlaceBidResponse();

		// convert the request object to XML string - uses XStream toXML() method
		String request = convertReqObjectToXMLString(mobilePlaceBidRequest,
				MobilePlaceBidRequest.class);

		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		// assign soId from the request URL
		apiVO.setSOId(soId);
		if(StringUtils.isNumeric(firmId)){
			apiVO.setResourceId(firmId);
		} 
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} 
		apiVO.setRoleId(roleId);
		
		// assign the XML string
		apiVO.setRequestFromPostPut(request);
		// assign the Request method as POST
		apiVO.setRequestType(RequestType.Post);

		// log the request
		// get resource Id from vendor_resource
		//Integer resourceId=mobileSOAcceptService.validateResourceId(providerId);
		Integer loggingId = mobilePlaceBidService.logSOMobileHistory(request, PublicMobileAPIConstant.PLACE_BID, PublicMobileAPIConstant.HTTP_POST, apiVO);

		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of RevisitNeededService
		mobilePlaceBidResponseString = mobilePlaceBidService.doSubmit(apiVO);

		// convert the XML string to object
		mobilePlaceBidResponse = (MobilePlaceBidResponse) convertXMLStringtoRespObject(
				mobilePlaceBidResponseString, MobilePlaceBidResponse.class);

		
		// update response 
		mobileSOAcceptService.updateSOMobileResponse(loggingId, mobilePlaceBidResponseString);
		return mobilePlaceBidResponse;
	}*/
	
	/*@POST
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/changeBid")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobilePlaceBidResponse mobileSOChangeBid(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") String resourceId,
			@PathParam("soId") String soId,
			MobilePlaceBidRequest mobileChangeBidRequest) {
		
		// declare the response variables
		String mobileChangeBidResponseString = null;
		MobilePlaceBidResponse mobileChangeBidResponse = new MobilePlaceBidResponse();

		// convert the request object to XML string
		// uses XStream toXML() method
		String request = convertReqObjectToXMLString(mobileChangeBidRequest,
				MobilePlaceBidRequest.class);

		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		// assign soId from the request URL
		apiVO.setSOId(soId);
		if(StringUtils.isNumeric(firmId)){
			apiVO.setResourceId(firmId);
		} 
		
		apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		
		apiVO.setRoleId(roleId);

		
		// assign the XML string
		apiVO.setRequestFromPostPut(request);
		// assign the Request method as POST
		apiVO.setRequestType(RequestType.Post);

		// log the request
		// get resource Id from vendor_resource
		//Integer resourceId=mobileSOAcceptService.validateResourceId(providerId);
		Integer loggingId = mobilePlaceBidService.logSOMobileHistory(request, PublicMobileAPIConstant.CHANGE_BID, PublicMobileAPIConstant.HTTP_POST, apiVO);

		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of RevisitNeededService
		mobileChangeBidResponseString = mobilePlaceBidService.doSubmit(apiVO);

		// convert the XML string to object
		mobileChangeBidResponse = (MobilePlaceBidResponse) convertXMLStringtoRespObject(
				mobileChangeBidResponseString, MobilePlaceBidResponse.class);

		// update response 
		mobileSOAcceptService.updateSOMobileResponse(loggingId, mobileChangeBidResponseString);
		return mobileChangeBidResponse;
	}*/
	
	@POST
	@Path("/provider/authenticate/forgetUserNameOrPwd/validateEmail")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public ForgetUnamePwdServiceResponse getResponseForForgetUNamePwdService (ForgetUnamePwdServiceRequest forgetUnamePwdService1Request) {
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		ForgetUnamePwdServiceResponse forgetUnamePwdServiceResponse = new ForgetUnamePwdServiceResponse();
		String request=convertReqObjectToXMLString(forgetUnamePwdService1Request,ForgetUnamePwdServiceRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		// log the request
		Integer loggingId=forgetUnamePwdService.logSOMobileHistory(request,PublicMobileAPIConstant.FORGET_UNAME_PWD_SERVICE1,PublicMobileAPIConstant.HTTP_POST,apiVO);
		response = forgetUnamePwdService.doSubmit(apiVO);
		forgetUnamePwdServiceResponse=(ForgetUnamePwdServiceResponse)convertXMLStringtoRespObject(response,ForgetUnamePwdServiceResponse.class);
		//update response in logging history
		forgetUnamePwdService.updateSOMobileResponse(loggingId, response);
		return forgetUnamePwdServiceResponse;
	}
	
	@POST
	@Path("/provider/authenticate/forgetUserNameOrPwd/validateSecQuestAns")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public ValidateSecQuestAnsResponse getResponseForValidateSecQuestAns(ValidateSecQuestAnsRequest validateSecQuestAnsRequest) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		apiVO.setRequestType(BaseService.RequestType.Post);		
		// log the request
		String validateSecQuestAnsRequestXML = convertReqObjectToXMLString(
				validateSecQuestAnsRequest, ValidateSecQuestAnsRequest.class); 
		apiVO.setRequestFromPostPut(validateSecQuestAnsRequestXML);
		Integer loggingId=validateSecQuestAnsService.logSOMobileHistory(validateSecQuestAnsRequestXML,PublicMobileAPIConstant.FORGET_UNAME_PWD_SERVICE2,PublicMobileAPIConstant.HTTP_POST,apiVO);		
		String responseXML = validateSecQuestAnsService.doSubmit(apiVO);
		validateSecQuestAnsService.updateSOMobileResponse(loggingId, responseXML);
		return (ValidateSecQuestAnsResponse) convertXMLStringtoRespObject(responseXML,
				ValidateSecQuestAnsResponse.class);

	}
	@PUT
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/respondToReschedule")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public RescheduleResponse getResponseForReschedule(@PathParam("providerId") String providerId, @PathParam("soId")
	String soId,@PathParam("roleId")Integer roleId,@PathParam("firmId") String firmId, RescheduleRespondRequest rescheduleRespondRequest) {
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		RescheduleResponse rescheduleResponse = new RescheduleResponse();
		String request=convertReqObjectToXMLString(rescheduleRespondRequest,RescheduleRespondRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Put);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setProviderResourceId(Integer.parseInt(providerId));
		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setResourceId(providerId);
		apiVO.setRoleId(roleId);

		// log the request
		// get resource Id from vendor_resource	
		Integer resourceId=respondRescheduleService.validateResourceId(providerId);
		apiVO.setProviderResourceId(resourceId);
		Integer loggingId=respondRescheduleService.logSOMobileHistory(request,PublicMobileAPIConstant.RESPOND_TO_RESCHEDULE,PublicMobileAPIConstant.HTTP_PUT,apiVO);
		response = respondRescheduleService.doSubmit(apiVO);
		rescheduleResponse=(RescheduleResponse)convertXMLStringtoRespObject(response,RescheduleResponse.class);
		//update response
		respondRescheduleService.updateSOMobileResponse(loggingId, response);
		return rescheduleResponse;
	}
	
	@PUT
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/updateFlag")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" }) 
	public UpdateServiceOrderFlagResponse getResponseForFlagSO(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") Integer providerResourceId,
			@PathParam("soId") String soId,
			UpdateServiceOrderFlagRequest updateFlagRequest) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Put);		
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerResourceId);
		apiVO.setRoleId(roleId);
		apiVO.setSOId(soId);
		// log the request
		String updateFlagRequestString = convertReqObjectToXMLString(
				updateFlagRequest, UpdateServiceOrderFlagRequest.class); 
		apiVO.setRequestFromPostPut(updateFlagRequestString);
		Integer loggingId=updateServiceOrderFlagService.logSOMobileHistory(updateFlagRequestString,PublicMobileAPIConstant.UPDATE_FLAG,PublicMobileAPIConstant.HTTP_PUT,apiVO);		
		String responseXML = updateServiceOrderFlagService.doSubmit(apiVO);
		updateServiceOrderFlagService.updateSOMobileResponse(loggingId, responseXML);
		return (UpdateServiceOrderFlagResponse) convertXMLStringtoRespObject(responseXML,
				UpdateServiceOrderFlagResponse.class);
		
	}
	/**
	 * 
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @param soId
	 * @param soSubmitRescheduleRequest
	 * @return
	 * API to submit a reschedule request as provider
	 */
	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/submitReschedule")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobileSOSubmitRescheduleResponse getResponseForSubmitReschedule(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") Integer providerId,
			@PathParam("soId") String soId,
			MobileSOSubmitRescheduleRequest soSubmitRescheduleRequest) {
		String rescheduleSOResponseString = null;
		MobileSOSubmitRescheduleResponse soSubmitRescheduleResponse = new MobileSOSubmitRescheduleResponse();
		String request = convertReqObjectToXMLString(soSubmitRescheduleRequest,MobileSOSubmitRescheduleRequest.class);
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		/*if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}*/
		
		//apiVO.setResourceId(providerId);
		apiVO.setProviderResourceId(providerId);
		apiVO.setProviderId(firmId);			
		apiVO.setSOId(soId);
		apiVO.setRoleId(roleId);

		apiVO.setRequestFromPostPut(request);
	
		apiVO.setRequestType(RequestType.Post);
	
		Integer loggingId=mobileSOSubmitRescheduleService.logSOMobileHistory(request,PublicMobileAPIConstant.SUBMIT_RESCHEDULE,PublicMobileAPIConstant.HTTP_POST,apiVO);
		rescheduleSOResponseString = mobileSOSubmitRescheduleService.doSubmit(apiVO);
	
		soSubmitRescheduleResponse = (MobileSOSubmitRescheduleResponse) convertXMLStringtoRespObject(rescheduleSOResponseString, MobileSOSubmitRescheduleResponse.class);
	
		mobileSOSubmitRescheduleService.updateSOMobileResponse(loggingId, rescheduleSOResponseString);
	
		return soSubmitRescheduleResponse;
	}
	@GET
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/getProviderSOList")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public GetProviderSOListResponse getProviderSOList(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") String providerId){
		
		GetProviderSOListResponse providerSOListResponse = new GetProviderSOListResponse();
		//Setting pageNo,pageSize and SoStaus in parameter map.
		@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		//Setting parameter Map in request
		apiVO.setRequestFromGetDelete(reqMap);
		//Setting Request type as GET
		apiVO.setRequestType(RequestType.Get);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		//Setting path params in API vo
		apiVO.setProviderId(firmId);
		apiVO.setResourceId(providerId);
	    apiVO.setRoleId(roleId);
		// log the request to mobile history table
		Integer loggingId=getProviderSOListService.logSOMobileHistory("",PublicMobileAPIConstant.GET_SO_PROVIDER_LIST,PublicMobileAPIConstant.HTTP_GET,apiVO);
		// Invoking the service class 
		String responseXML = getProviderSOListService.doSubmit(apiVO);
		//Converting response to string for logging purpose.
		providerSOListResponse = (GetProviderSOListResponse) convertXMLStringtoRespObject(responseXML, GetProviderSOListResponse.class);
		// update response 
		getProviderSOListService.updateSOMobileResponse(loggingId, responseXML);
		return providerSOListResponse;

	}
	
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/getRecievedOrder")
	
	public RecievedOrdersResponse getResponseForRecievedOrders(@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId, @PathParam("providerId") String resourceId) {
		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		apiVO.setRequestType(BaseService.RequestType.Get);		
		apiVO.setProviderId(firmId);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} else {
			apiVO.setProviderResourceId(0);
		}

		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRoleId(roleId);
		Integer loggingId=recievedOrdersService.logSOMobileHistory("",PublicMobileAPIConstant.GET_RECEIVED_ORDERS,PublicMobileAPIConstant.HTTP_GET,apiVO);		
		String responseXML = recievedOrdersService.doSubmit(apiVO);
		recievedOrdersService.updateSOMobileResponse(loggingId, responseXML);
		return (RecievedOrdersResponse) convertXMLStringtoRespObject(responseXML,
				RecievedOrdersResponse.class);

	}
	
	/**
	 * This is the delete filter API exposed method 
	 * 
	 * @param roleId
	 * @param firmId
	 * @param resourceId
	 * @param deleteFilterRequest
	 * @return DeleteFilterResponse
	 */
	@DELETE
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/deleteFilter")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public DeleteFilterResponse getResponseForDeleteFilter(@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId, @PathParam("providerId") String resourceId) {
		
		String deleteFilterResponseString = null;
		DeleteFilterResponse deleteFilterResponse = new DeleteFilterResponse();
		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(RequestType.Delete);
		apiVO.setProviderId(firmId);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setRoleId(roleId);
		
		@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		
		
		Integer loggingId=deleteFilterService.logSOMobileHistory("",PublicMobileAPIConstant.DELETE_FILTER,PublicMobileAPIConstant.HTTP_DELETE,apiVO);		
		deleteFilterResponseString = deleteFilterService.doSubmit(apiVO);
		deleteFilterService.updateSOMobileResponse(loggingId, deleteFilterResponseString);	
		
		deleteFilterResponse = (DeleteFilterResponse) convertXMLStringtoRespObject(deleteFilterResponseString,DeleteFilterResponse.class);
		
		return deleteFilterResponse;

	}
	/**
	 * Method to fetch the details of SO in received,accepted,active and problem status
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @param soId
	 * @return
	 */		
	@GET 
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/getSODetailsPro")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public SOGetMobileDetailsResponse getResponseForSoRetrieve(@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") Integer providerId,
			@PathParam("soId") String soId) {
		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRoleId(roleId);
		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerId);
			
		@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		
		// log the request
		Integer loggingId=soDetailsRetrieveService.logSOMobileHistory("",PublicMobileAPIConstant.GET_SO_DETAILS_PRO_V3,PublicMobileAPIConstant.HTTP_GET,apiVO);
		String responseXML = soDetailsRetrieveService.doSubmit(apiVO);
		// update response 
		soDetailsRetrieveService.updateSOMobileResponse(loggingId, responseXML);
		return (SOGetMobileDetailsResponse) convertXMLStringtoRespObject(responseXML,
				SOGetMobileDetailsResponse.class);
	}
	
	
	/**@Description: Method to fetch count of service orders for different Status
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @return
	 */
	@GET
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/viewDashboard")
	public ViewDashboardResponse getResponseForDashBoardCount(
			      @PathParam("roleId") Integer roleId,
			      @PathParam("firmId") String firmId,
			      @PathParam("providerId") String providerId){
		ViewDashboardResponse dashboardResponse =new ViewDashboardResponse();
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Get);	
		//set provider Id
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		//set firmId
		apiVO.setProviderId(firmId);
		//set roleId
		apiVO.setRoleId(roleId);
		// log the request
		Integer loggingId=retriveCountService.logSOMobileHistory("",PublicMobileAPIConstant.VIEW_DASHBOARD, PublicMobileAPIConstant.HTTP_GET,apiVO);
		String viewDashBoardCountResponsString = retriveCountService.doSubmit(apiVO);
		// convert the XML string to object
		dashboardResponse = (ViewDashboardResponse) convertXMLStringtoRespObject(viewDashBoardCountResponsString, ViewDashboardResponse.class);
		//Logging Response 
		retriveCountService.updateSOMobileResponse(loggingId, viewDashBoardCountResponsString);
		return dashboardResponse;
		
	}
	
	
	@PUT
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/saveFilter")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public SaveFilterResponse getResponseForSaveFilter(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") Integer providerResourceId,
			SaveFilterRequest saveFilterRequest) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		apiVO.setRequestType(BaseService.RequestType.Put);		
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerResourceId);
		apiVO.setRoleId(roleId);
		// log the request
		String saveFilterRequestString = convertReqObjectToXMLString(
				saveFilterRequest, SaveFilterRequest.class); 
		apiVO.setRequestFromPostPut(saveFilterRequestString);
		Integer loggingId=saveFilterService.logSOMobileHistory(saveFilterRequestString,PublicMobileAPIConstant.SAVE_FILTER,PublicMobileAPIConstant.HTTP_PUT,apiVO);		
		String responseXML = saveFilterService.doSubmit(apiVO);
		saveFilterService.updateSOMobileResponse(loggingId, responseXML);
		return (SaveFilterResponse) convertXMLStringtoRespObject(responseXML,
				SaveFilterResponse.class);

	}
	/**
	 * Method to fetch the search criterias like market,provider,status and subStatus for the logged in user
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @return
	 */
	@GET 
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/getCriteria")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public SOGetSearchCriteriaResponse getResponseForSearchCriteria(@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") Integer providerId) {
		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRoleId(roleId);
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerId);
			
		@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		
		// log the request
		Integer loggingId=searchCriteriaService.logSOMobileHistory("",PublicMobileAPIConstant.GET_SEARCH_CRITERIA,PublicMobileAPIConstant.HTTP_GET,apiVO);
		String responseXML = searchCriteriaService.doSubmit(apiVO);
		// update response 
		searchCriteriaService.updateSOMobileResponse(loggingId, responseXML);
		return (SOGetSearchCriteriaResponse) convertXMLStringtoRespObject(responseXML,
				SOGetSearchCriteriaResponse.class);
	}
	/**
	 * @param roleId
	 * @param firmId
	 * @param resourceId
	 * @return
	 * 
	 * to fetch filternames and search criterias for a provider
	 * 
	 */
	@GET
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/getFilter")
	public GetFilterResponse getResponseForGetFilter(
			@PathParam("roleId") String roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") String providerId)
	{
		GetFilterResponse getFilterResponse=new GetFilterResponse();
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Get);	
		//set provider Id
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		//set firmId
		apiVO.setProviderId(firmId);
		//set roleId
		if(StringUtils.isNumeric(roleId)){
			apiVO.setRoleId(Integer.parseInt(roleId));
		} else {
			apiVO.setRoleId(0);
		}
		
		// log the request
		Integer loggingId=getFilterService.logSOMobileHistory("",PublicMobileAPIConstant.GET_FILTER, PublicMobileAPIConstant.HTTP_GET,apiVO);
		String mobileGetFilterResponseString = getFilterService.doSubmit(apiVO);
		
		// convert the XML string to object
		getFilterResponse = (GetFilterResponse) convertXMLStringtoRespObject(mobileGetFilterResponseString, GetFilterResponse.class);
		getFilterService.updateSOMobileResponse(loggingId, mobileGetFilterResponseString);
		return getFilterResponse;
		
	}
	
	
	/**
	 * This method returns a response for a Time On Site request. The request
	 * processor delegates the processing to the timeOnSiteV3Service class 
	 * - This is in case of Provider
	 */
	@POST
	@Path("/provider/{providerId}/serviceorder/{soId}/timeonsite")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobileTimeOnSiteResponse addTimeOnSite(
			@PathParam("providerId") String providerId,
			@PathParam("soId") String soId,
			MobileTimeOnSiteRequest timeOnSiteRequest) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering MobileGenericRequestProcessor.addTimeOnSite");
		}
		long addTimeOnSiteStart = System.currentTimeMillis();		
		// declare the response variables
		String timeOnSiteResponse = null;
		MobileTimeOnSiteResponse mTimeOnSiteResponse = new MobileTimeOnSiteResponse();

		// convert the request object to XML string
		// uses XStream toXML() method
		String request = convertReqObjectToXMLString(timeOnSiteRequest,
				MobileTimeOnSiteRequest.class);

		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		// assign soId from the request URL
		apiVO.setSOId(soId);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}

		// assign the XML string
		apiVO.setRequestFromPostPut(request);
		// assign the Request method as POST
		apiVO.setRequestType(RequestType.Post);

		// log the request
		// get resource Id from vendor_resource
		Integer resourceId=timeOnSiteV3Service.validateResourceId(providerId);
		Integer loggingId=timeOnSiteV3Service.logSOMobileHistory(request,PublicMobileAPIConstant.TIME_ON_SITE_V3, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);

		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of AdvancedTimeOnSiteService
		timeOnSiteResponse = timeOnSiteV3Service.doSubmit(apiVO);

		// convert the XML string to object
		mTimeOnSiteResponse = (MobileTimeOnSiteResponse) convertXMLStringtoRespObject(
				timeOnSiteResponse, MobileTimeOnSiteResponse.class);

		mTimeOnSiteResponse.setSoId(soId);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Leaving MobileGenericRequestProcessor.addTimeOnSite");
		}
		// update response 
		timeOnSiteV3Service.updateSOMobileResponse(loggingId, timeOnSiteResponse);
		long addTimeOnSiteEnd = System.currentTimeMillis();
		LOGGER.info("Total time taken for timeonsite after logging in ms :::"+(addTimeOnSiteEnd-addTimeOnSiteStart));
		return mTimeOnSiteResponse;
	}
	
	@POST
	@Path("/provider/{providerId}/serviceorder/{soId}/submitWarrantyHomeReason")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobileSOSubmitWarrantyHomeReasonCodeResponse getResponseForSubmitWarrantyHomeReasonResponse(
			@PathParam("providerId") Integer providerId,
			@PathParam("soId") String soId,
			MobileSOSubmitWarrantyHomeReasonRequest submitWarrantyHomeReasonCodeRequest) {
		LOGGER.info("Entering MobileGenericRequestProcessor.getResponseForSubmitWarrantyHomeReasonResponse v1.3");
		long submitWarrantyHomeReasonStart = System.currentTimeMillis();
		String soSubmitWarrantyHomeReasonCodeResponseString = null;
		MobileSOSubmitWarrantyHomeReasonCodeResponse submitWarrantyHomeReasonCodeResponse = new MobileSOSubmitWarrantyHomeReasonCodeResponse();
		String request = convertReqObjectToXMLString(submitWarrantyHomeReasonCodeRequest,MobileSOSubmitWarrantyHomeReasonRequest.class);
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		/*if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}*/
		
		//apiVO.setResourceId(providerId);
		apiVO.setProviderResourceId(providerId);
		apiVO.setSOId(soId);
		apiVO.setRequestFromPostPut(request);
		apiVO.setAPISOValidationExcludeInd(true);
	
		apiVO.setRequestType(RequestType.Post);
		//Loggigng functionality has to be implemented 
		//Integer loggingId=mobileSOSubmitRescheduleService.logSOMobileHistory(request,1223,PublicMobileAPIConstant.HTTP_POST,apiVO);//put the 1223 for the time being.. need to be decided later
		soSubmitWarrantyHomeReasonCodeResponseString = mobileSOSubmitWarrantyHomeReasonCodeService.doSubmit(apiVO);
		submitWarrantyHomeReasonCodeResponse = (MobileSOSubmitWarrantyHomeReasonCodeResponse) convertXMLStringtoRespObject(soSubmitWarrantyHomeReasonCodeResponseString, MobileSOSubmitWarrantyHomeReasonCodeResponse.class);
		long submitWarrantyHomeReasonEnd = System.currentTimeMillis();
		LOGGER.info("Total time taken to submit home warrnaty reason codes in ms :::"+(submitWarrantyHomeReasonEnd-submitWarrantyHomeReasonStart));
		//mobileSOSubmitRescheduleService.updateSOMobileResponse(loggingId, soSubmitWarrantyHomeReasonCodeResponseString);
	
		return submitWarrantyHomeReasonCodeResponse;
	}
	
	//SLT-4658
	@POST
	@Path("/provider/{providerId}/serviceorder/{soId}/updateSOCompletion")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public UpdateSOCompletionResponse getResponseForSoCompletion(@PathParam("soId")
	String soId,@PathParam("providerId")
	String providerId,  UpdateSOCompletionRequest updateSOCompletionRequest) {
		if (LOGGER.isInfoEnabled())
			LOGGER.info("getResponseForSoCompletion method started");
		Integer loggingId = null;
		long completionStart = System.currentTimeMillis();		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Post);
		String completionRequest = convertReqObjectToXMLString(
				updateSOCompletionRequest, UpdateSOCompletionRequest.class);
		apiVO.setRequestFromPostPut(completionRequest);	
		apiVO.setSOId(soId);
		if(StringUtils.isNumeric(providerId)){
		apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}		// log the request
		
		// the indicator is set to exclude UpdateSOCompletion from the SOLevel validation in Base Service, the specific validation for Complete API will be handled in respective service class
		apiVO.setAPISOValidationExcludeInd(true);
		//SLT-1164
		String resolutionComments = updateSOCompletionRequest.getResolutionComments();
		String responseXMLWithCC = updateSOCompletionService.validateCC(resolutionComments);
		if (responseXMLWithCC != null) {
			UpdateSOCompletionResponse completionResponse = (UpdateSOCompletionResponse) convertXMLStringtoRespObject(
					responseXMLWithCC, UpdateSOCompletionResponse.class);
			return completionResponse;
		}

		Integer resourceId=updateSOCompletionService.validateResourceId(providerId);
		
		// to make the system PCI Compliant, hiding the completion request when it contains credit card details
		if(null != updateSOCompletionRequest && null != updateSOCompletionRequest.getAddOnPaymentDetails() && StringUtils.isNotBlank(updateSOCompletionRequest.getAddOnPaymentDetails().getCcNumber())){
			loggingId=updateSOCompletionService.logSOMobileHistory(MPConstants.SENSITIVE_INFORMATION,PublicMobileAPIConstant.UPDATE_SO_COMPLETION_V3, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);
		}
		else{
			loggingId=updateSOCompletionService.logSOMobileHistory(completionRequest,PublicMobileAPIConstant.UPDATE_SO_COMPLETION_V3, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);
		}
		String responseXML = updateSOCompletionService.doSubmit(apiVO);
		UpdateSOCompletionResponse completionResponse = (UpdateSOCompletionResponse) convertXMLStringtoRespObject(
				responseXML, UpdateSOCompletionResponse.class);
		if (LOGGER.isInfoEnabled())
			LOGGER.info("getResponseForSoCompletion method ended");
		// update response 
		updateSOCompletionService.updateSOMobileResponse(loggingId, responseXML);
		long completionEnd = System.currentTimeMillis();
		LOGGER.info("Total time taken for completion after logging in ms :::"+(completionEnd -completionStart));
		return completionResponse;

	}	
	
	
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public UpdateServiceOrderFlagService getUpdateServiceOrderFlagService() {
		return updateServiceOrderFlagService;
	}


	public void setUpdateServiceOrderFlagService(
			UpdateServiceOrderFlagService updateServiceOrderFlagService) {
		this.updateServiceOrderFlagService = updateServiceOrderFlagService;
	}


	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
	
	public MobileSearchSOService getMobileSearchSOService() {
		return mobileSearchSOService;
	}

	public void setMobileSearchSOService(MobileSearchSOService mobileSearchSOService) {
		this.mobileSearchSOService = mobileSearchSOService;
	}

	public SOGetEligibleProvidersService getSoEligibleProviderListService() {
		return soEligibleProviderListService;
	}

	public void setSoEligibleProviderListService(
			SOGetEligibleProvidersService soEligibleProviderListService) {
		this.soEligibleProviderListService = soEligibleProviderListService;
	}
	
	/*public ReportAProblemService getReportAProblemService() {
		return reportAProblemService;
	}

	public void setReportAProblemService(ReportAProblemService reportAProblemService) {
		this.reportAProblemService = reportAProblemService;
	}*/	

	public MobileSOAcceptService getMobileSOAcceptService() {
		return mobileSOAcceptService;
	}

	public MobileSORejectService getRejectOrderService() {
		return rejectOrderService;
	}


	public void setRejectOrderService(MobileSORejectService rejectOrderService) {
		this.rejectOrderService = rejectOrderService;
	}

	

	public void setMobileSOAcceptService(MobileSOAcceptService mobileSOAcceptService) {
		this.mobileSOAcceptService = mobileSOAcceptService;
	}


	public CounterOfferService getCounterOfferService() {
		return counterOfferService;
	}

	public void setCounterOfferService(CounterOfferService counterOfferService) {
		this.counterOfferService = counterOfferService;
	}

	public WithdrawCounterOfferService getWithdrawCounterOfferService() {
		return withdrawCounterOfferService;
	}

	public void setWithdrawCounterOfferService(
			WithdrawCounterOfferService withdrawCounterOfferService) {
		this.withdrawCounterOfferService = withdrawCounterOfferService;
	}


	/*public MobilePlaceBidService getMobilePlaceBidService() {
		return mobilePlaceBidService;
	}


	public void setMobilePlaceBidService(MobilePlaceBidService mobilePlaceBidService) {
		this.mobilePlaceBidService = mobilePlaceBidService;
	}*/


	/*public ResolveProblemService getResolveProblemService() {
		return resolveProblemService;
	}


	public void setResolveProblemService(ResolveProblemService resolveProblemService) {
		this.resolveProblemService = resolveProblemService;
	}*/
	
	public GetReasonCodesService getReasonCodesService() {
		return reasonCodesService;
	}
	
	
	public void setReasonCodesService(GetReasonCodesService reasonCodesService) {
		this.reasonCodesService = reasonCodesService;
	}


	/*public MobileSOReleaseService getReleaseOrderService() {
		return releaseOrderService;
	}


	public void setReleaseOrderService(MobileSOReleaseService releaseOrderService) {
		this.releaseOrderService = releaseOrderService;
	}*/

	public MobileSOSubmitRescheduleService getMobileSOSubmitRescheduleService() {
		return mobileSOSubmitRescheduleService;
	}
	
	
	public void setMobileSOSubmitRescheduleService(
			MobileSOSubmitRescheduleService mobileSOSubmitRescheduleService) {
		this.mobileSOSubmitRescheduleService = mobileSOSubmitRescheduleService;
	}

	/*public UpdateScheduleDetailsService getUpdateScheduleDetailsService() {
		return updateScheduleDetailsService;
	}


	public void setUpdateScheduleDetailsService(
			UpdateScheduleDetailsService updateScheduleDetailsService) {
		this.updateScheduleDetailsService = updateScheduleDetailsService;
	}*/


	public RescheduleRespondService getRespondRescheduleService() {
		return respondRescheduleService;
	}


	public void setRespondRescheduleService(
			RescheduleRespondService respondRescheduleService) {
		this.respondRescheduleService = respondRescheduleService;
	}


	public GetProviderSOListService getGetProviderSOListService() {
		return getProviderSOListService;
	}


	public void setGetProviderSOListService(
			GetProviderSOListService getProviderSOListService) {
		this.getProviderSOListService = getProviderSOListService;
	}


	public ForgetUnamePwdService getForgetUnamePwdService() {
		return forgetUnamePwdService;
	}


	public void setForgetUnamePwdService(
			ForgetUnamePwdService forgetUnamePwdService) {
		this.forgetUnamePwdService = forgetUnamePwdService;
	}


	public AssignOrReassignSOService getAssignOrReassignSOService() {
		return assignOrReassignSOService;
	}


	public void setAssignOrReassignSOService(
			AssignOrReassignSOService assignOrReassignSOService) {
		this.assignOrReassignSOService = assignOrReassignSOService;
	}

	public RecievedOrdersService getRecievedOrdersService() {
		return recievedOrdersService;
	}

	public void setRecievedOrdersService(RecievedOrdersService recievedOrdersService) {
		this.recievedOrdersService = recievedOrdersService;
	}


	public DeleteFilterService getDeleteFilterService() {
		return deleteFilterService;
	}


	public void setDeleteFilterService(DeleteFilterService deleteFilterService) {
		this.deleteFilterService = deleteFilterService;
	}


	public SODetailsRetrieveService getSoDetailsRetrieveService() {
		return soDetailsRetrieveService;
	}


	public void setSoDetailsRetrieveService(
			SODetailsRetrieveService soDetailsRetrieveService) {
		this.soDetailsRetrieveService = soDetailsRetrieveService;
	}


	public MobileAdvanceSearchSOService getMobileAdvanceSearchSOService() {
		return mobileAdvanceSearchSOService;
	}


	public void setMobileAdvanceSearchSOService(
			MobileAdvanceSearchSOService mobileAdvanceSearchSOService) {
		this.mobileAdvanceSearchSOService = mobileAdvanceSearchSOService;
	}
		
	public GetFilterService getGetFilterService() {
		return getFilterService;
	}

	public void setGetFilterService(GetFilterService getFilterService) {
		this.getFilterService = getFilterService;
	}

	public SaveFilterService getSaveFilterService() {
		return saveFilterService;
	}

	public void setSaveFilterService(SaveFilterService saveFilterService) {
		this.saveFilterService = saveFilterService;
	}


	public DashboardRetrieveCountService getRetriveCount() {
		return retriveCountService;
	}


	public void setRetriveCount(DashboardRetrieveCountService retriveCount) {
		this.retriveCountService = retriveCount;
	}


	public SearchCriteriaService getSearchCriteriaService() {
		return searchCriteriaService;
	}


	public void setSearchCriteriaService(SearchCriteriaService searchCriteriaService) {
		this.searchCriteriaService = searchCriteriaService;
	}


	public ValidateSecQuestAnsService getValidateSecQuestAnsService() {
		return validateSecQuestAnsService;
	}


	public void setValidateSecQuestAnsService(
			ValidateSecQuestAnsService validateSecQuestAnsService) {
		this.validateSecQuestAnsService = validateSecQuestAnsService;
	}


	public MobileTimeOnSiteService getTimeOnSiteV3Service() {
		return timeOnSiteV3Service;
	}


	public void setTimeOnSiteV3Service(MobileTimeOnSiteService timeOnSiteV3Service) {
		this.timeOnSiteV3Service = timeOnSiteV3Service;
	}


	public MobileSOSubmitWarrantyHomeReasonCodeService getMobileSOSubmitWarrantyHomeReasonCodeService() {
		return mobileSOSubmitWarrantyHomeReasonCodeService;
	}


	public void setMobileSOSubmitWarrantyHomeReasonCodeService(
			MobileSOSubmitWarrantyHomeReasonCodeService mobileSOSubmitWarrantyHomeReasonCodeService) {
		this.mobileSOSubmitWarrantyHomeReasonCodeService = mobileSOSubmitWarrantyHomeReasonCodeService;
	}

	/**
	 * @return the updateSOCompletionService
	 */
	public UpdateSOCompletionService getUpdateSOCompletionService() {
		return updateSOCompletionService;
	}

	/**
	 * @param updateSOCompletionService the updateSOCompletionService to set
	 */
	public void setUpdateSOCompletionService(
			UpdateSOCompletionService updateSOCompletionService) {
		this.updateSOCompletionService = updateSOCompletionService;
	}
	
	
	
}
