package com.newco.marketplace.api.mobile.processor.v3_1;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.companyProfile.ViewCompanyProfileResponse;
import com.newco.marketplace.api.mobile.beans.dashboard.ViewDashboardResponse;
import com.newco.marketplace.api.mobile.beans.getTeamMembers.ViewTeamMembersResponse;
import com.newco.marketplace.api.mobile.beans.releaseServiceOrder.MobileReleaseSORequest;
import com.newco.marketplace.api.mobile.beans.releaseServiceOrder.MobileReleaseSOResponse;
import com.newco.marketplace.api.mobile.beans.reportProblem.ReportProblemRequest;
import com.newco.marketplace.api.mobile.beans.reportProblem.ReportProblemResponse;
import com.newco.marketplace.api.mobile.beans.resolveProblem.ResolveProblemOnSORequest;
import com.newco.marketplace.api.mobile.beans.resolveProblem.ResolveProblemResponse;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsRequest;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsResponse;
import com.newco.marketplace.api.mobile.beans.updateTimeWindow.UpdateTimeWindowResponse;
import com.newco.marketplace.api.mobile.beans.updateTimeWindow.UpdatetimeWindowRequest;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ViewAdvancedDashboardResponse;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.service.BaseService.RequestType;
import com.newco.marketplace.api.mobile.services.v3_1.MobileSOReleaseService;
import com.newco.marketplace.api.mobile.services.v3_1.ReportProblemService;
import com.newco.marketplace.api.mobile.services.v3_1.ResolveProblemService;
import com.newco.marketplace.api.mobile.services.v3_1.UpdateScheduleDetailsService;
import com.newco.marketplace.api.mobile.services.v3_1.UpdateTimeWindowService;
import com.newco.marketplace.api.mobile.services.v3_1.ViewCompanyProfileService;
import com.newco.marketplace.api.mobile.services.v3_1.ViewDashboardService;
import com.newco.marketplace.api.mobile.services.v3_1.ViewTeamMembersService;
import com.newco.marketplace.api.server.MobileBaseRequestProcessor;

/**
 * @author Infosys
 * $Revision: 1.0 $Date: 2015/12/16
 * Request Processor class for Mobile phase v3.1 API's
 */
@Path("v3.1/role/{roleId}")
@Consumes({ "application/xml", "application/json", "text/xml" })
@Produces({ "application/xml", "application/json", "text/xml" })
public class MobileGenericRequestProcessor extends MobileBaseRequestProcessor{
	@Resource
	protected HttpServletRequest httpRequest;
    protected HttpServletResponse httpResponse;
	
	// service reference for reportAProblem
	protected ReportProblemService reportProblemService;
	// service reference for resolving problem
	protected ResolveProblemService resolveProblemService;
	// service reference for release SO
	protected MobileSOReleaseService releaseOrderService ;
	// service for Pre-Call API
	protected UpdateScheduleDetailsService updateScheduleDetailsService;
	// service reference for fetching Dashboard Count.
	protected ViewDashboardService viewDashboardService;
	// service reference for fetching company profile details
	protected ViewCompanyProfileService viewCompanyProfileService;
	//service reference for fetching the team members details
	protected ViewTeamMembersService viewTeamMembersService;
	// service reference for Update time window API
	protected UpdateTimeWindowService updateTimeWindowService;
	

	@POST
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/reportProblem")
	public ReportProblemResponse getResponseForReportProblem (@PathParam("providerId") Integer providerId, @PathParam("soId")
	String soId,@PathParam("roleId")Integer roleId,@PathParam("firmId") String firmId, ReportProblemRequest reportProblemRequest) {
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String request=convertReqObjectToXMLString(reportProblemRequest,ReportProblemRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		
		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerId);
		apiVO.setRoleId(roleId);
		
		Integer loggingId=reportProblemService.logSOMobileHistory(request,PublicMobileAPIConstant.REPORT_PROBLEM_v3_1,PublicMobileAPIConstant.HTTP_POST,apiVO);
		response = reportProblemService.doSubmit(apiVO);
		reportProblemService.updateSOMobileResponse(loggingId, response);
		return (ReportProblemResponse)convertXMLStringtoRespObject(response,ReportProblemResponse.class);
	}
	
	@PUT
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/resolveProblem")
	public ResolveProblemResponse getResponseForResolveProblem (@PathParam("providerId") Integer providerId, @PathParam("soId")
	String soId,@PathParam("roleId")Integer roleId,@PathParam("firmId") String firmId, ResolveProblemOnSORequest resolveProblemRequest) {
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String request=convertReqObjectToXMLString(resolveProblemRequest,ResolveProblemOnSORequest.class);
		
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Put);
		
		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerId);
		apiVO.setRoleId(roleId);

		Integer loggingId=resolveProblemService.logSOMobileHistory(request,PublicMobileAPIConstant.RESOLVE_PROBLEM_v3_1,PublicMobileAPIConstant.HTTP_PUT,apiVO);
		response = resolveProblemService.doSubmit(apiVO);
		resolveProblemService.updateSOMobileResponse(loggingId, response);
		return (ResolveProblemResponse)convertXMLStringtoRespObject(response,ResolveProblemResponse.class);
	}
	
	@POST
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/release")
	public MobileReleaseSOResponse getResponseForRelease(@PathParam("roleId") Integer roleId, @PathParam("firmId") String firmId,
		@PathParam("providerId") Integer providerId, @PathParam("soId") String soId, MobileReleaseSORequest releaseSORequest) {
		String response = null;
		Integer loggingId=null;
		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String request = convertReqObjectToXMLString(releaseSORequest,MobileReleaseSORequest.class);

		apiVO.setRoleId(roleId);
		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerId);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		loggingId= releaseOrderService.logSOMobileHistory(request, PublicMobileAPIConstant.RELEASE_SO_v3_1, PublicMobileAPIConstant.HTTP_POST, apiVO);
		response = releaseOrderService.doSubmit(apiVO);
		releaseOrderService.updateSOMobileResponse(loggingId, response);
		return (MobileReleaseSOResponse) convertXMLStringtoRespObject(response, MobileReleaseSOResponse.class);
	}
	
	@POST
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/precallAndConfirmAppointment")
	public UpdateScheduleDetailsResponse getResponseForUpdateScheduleDetails(@PathParam("roleId") Integer roleId,@PathParam("firmId") String firmId,
			@PathParam("providerId") Integer providerId, @PathParam("soId") String soId, UpdateScheduleDetailsRequest updateScheduleDetailsRequest) {

		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String request = convertReqObjectToXMLString(updateScheduleDetailsRequest,UpdateScheduleDetailsRequest.class);

		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		
		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerId);
		apiVO.setRoleId(roleId);

		Integer loggingId=updateScheduleDetailsService.logSOMobileHistory(request,PublicMobileAPIConstant.UPDATE_SCHEDULE_DETAILS_v3_1,PublicMobileAPIConstant.HTTP_POST,apiVO);
		response = updateScheduleDetailsService.doSubmit(apiVO);
		updateScheduleDetailsService.updateSOMobileResponse(loggingId, response);
		return (UpdateScheduleDetailsResponse)convertXMLStringtoRespObject(response, UpdateScheduleDetailsResponse.class);
	}
	
	/**@Description: Method to fetch count of service orders for different Status
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @return
	 */
	@GET
	@Path("/firm/{firmId}/provider/{providerId}/viewDashboard")
	public ViewAdvancedDashboardResponse getResponseForDashBoardCount(
			      @PathParam("roleId") Integer roleId,
			      @PathParam("firmId") String firmId,
			      @PathParam("providerId") Integer providerId){
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Get);	
		//set provider Id
		apiVO.setProviderResourceId(providerId);
		apiVO.setProviderId(firmId);
		apiVO.setRoleId(roleId);
		Integer loggingId=viewDashboardService.logSOMobileHistory(PublicMobileAPIConstant.BLANK,PublicMobileAPIConstant.VIEW_DASHBOARD_v3_1, PublicMobileAPIConstant.HTTP_GET,apiVO);
		response = viewDashboardService.doSubmit(apiVO);
		viewDashboardService.updateSOMobileResponse(loggingId, response);
		return (ViewAdvancedDashboardResponse) convertXMLStringtoRespObject(response, ViewAdvancedDashboardResponse.class);
		
	}
	
	/**
	 * @Description:Method to fetch the company details of a resource
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @return
	 */
	@GET
	@Path("/firm/{firmId}/provider/{providerId}/viewCompanyProfile")
	public ViewCompanyProfileResponse getCompanyProfileDetailResponse (
			      @PathParam("roleId") Integer roleId,
			      @PathParam("firmId") String firmId,
			      @PathParam("providerId") Integer providerId){
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Get);	
		apiVO.setProviderResourceId(providerId);
		apiVO.setProviderId(firmId);
		apiVO.setRoleId(roleId);
		
		Integer loggingId=viewCompanyProfileService.logSOMobileHistory(PublicMobileAPIConstant.BLANK,PublicMobileAPIConstant.VIEW_COMPANY_PROFILE_v3_1, PublicMobileAPIConstant.HTTP_GET,apiVO);
		response = viewCompanyProfileService.doSubmit(apiVO);
		viewCompanyProfileService.updateSOMobileResponse(loggingId, response);
		return (ViewCompanyProfileResponse) convertXMLStringtoRespObject(response, ViewCompanyProfileResponse.class);
	}
	
	/**
	 * @Description:Method to fetch the team members details 
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @return
	 */
	@GET
	@Path("/firm/{firmId}/provider/{providerId}/getTeamMembers")
	public ViewTeamMembersResponse getTeamMemberDetailsResponse  (
			      @PathParam("roleId") Integer roleId,
			      @PathParam("firmId") String firmId,
			      @PathParam("providerId") Integer providerId){
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Get);	
		apiVO.setProviderResourceId(providerId);
		
		apiVO.setProviderId(firmId);
		apiVO.setRoleId(roleId);

		Integer loggingId=viewTeamMembersService.logSOMobileHistory(PublicMobileAPIConstant.BLANK,PublicMobileAPIConstant.VIEW_TEAM_MEMBERS_v3_1, PublicMobileAPIConstant.HTTP_GET,apiVO);
		response = viewTeamMembersService.doSubmit(apiVO);
		viewTeamMembersService.updateSOMobileResponse(loggingId, response);
		// convert the XML string to object
		return (ViewTeamMembersResponse) convertXMLStringtoRespObject(response, ViewTeamMembersResponse.class);
	}
	
	@PUT
	@Path("/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/updateTime")
	public UpdateTimeWindowResponse getResponseForUpdtTimeWindow (@PathParam("providerId") Integer providerId, @PathParam("soId")
	String soId,@PathParam("roleId")Integer roleId,@PathParam("firmId") String firmId, UpdatetimeWindowRequest updatetimeWindowRequest) {
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String request=convertReqObjectToXMLString(updatetimeWindowRequest,UpdatetimeWindowRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Put);
		
		apiVO.setSOId(soId);
		apiVO.setProviderId(firmId);
		apiVO.setProviderResourceId(providerId);
		apiVO.setRoleId(roleId);
		
		Integer loggingId=updateTimeWindowService.logSOMobileHistory(request,PublicMobileAPIConstant.UPDATE_TIME_WINDOW_v3_1,PublicMobileAPIConstant.HTTP_PUT,apiVO);
		response = updateTimeWindowService.doSubmit(apiVO);
		updateTimeWindowService.updateSOMobileResponse(loggingId, response);
		return (UpdateTimeWindowResponse)convertXMLStringtoRespObject(response,UpdateTimeWindowResponse.class);
	}
	
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
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
	

	public ReportProblemService getReportProblemService() {
		return reportProblemService;
	}

	public void setReportProblemService(ReportProblemService reportProblemService) {
		this.reportProblemService = reportProblemService;
	}

	public ResolveProblemService getResolveProblemService() {
		return resolveProblemService;
	}


	public void setResolveProblemService(ResolveProblemService resolveProblemService) {
		this.resolveProblemService = resolveProblemService;
	}
	
	public MobileSOReleaseService getReleaseOrderService() {
		return releaseOrderService;
	}


	public void setReleaseOrderService(MobileSOReleaseService releaseOrderService) {
		this.releaseOrderService = releaseOrderService;
	}

	public UpdateScheduleDetailsService getUpdateScheduleDetailsService() {
		return updateScheduleDetailsService;
	}


	public void setUpdateScheduleDetailsService(
			UpdateScheduleDetailsService updateScheduleDetailsService) {
		this.updateScheduleDetailsService = updateScheduleDetailsService;
	}

	public ViewDashboardService getViewDashboardService() {
		return viewDashboardService;
	}

	public void setViewDashboardService(ViewDashboardService viewDashboardService) {
		this.viewDashboardService = viewDashboardService;
	}

	public ViewCompanyProfileService getViewCompanyProfileService() {
		return viewCompanyProfileService;
	}

	public void setViewCompanyProfileService(
			ViewCompanyProfileService viewCompanyProfileService) {
		this.viewCompanyProfileService = viewCompanyProfileService;
	}

	public UpdateTimeWindowService getUpdateTimeWindowService() {
		return updateTimeWindowService;
	}

	public void setUpdateTimeWindowService(
			UpdateTimeWindowService updateTimeWindowService) {
		this.updateTimeWindowService = updateTimeWindowService;
	}
	
	public ViewTeamMembersService getViewTeamMembersService() {
		return viewTeamMembersService;
	}

	public void setViewTeamMembersService(
			ViewTeamMembersService viewTeamMembersService) {
		this.viewTeamMembersService = viewTeamMembersService;
	}
}
