package com.newco.marketplace.api.server.leadsmanagement.v1_0;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.leadsmanagement.AssignOrReassignProviderService;
import com.newco.marketplace.api.services.leadsmanagement.CancelLeadService;
import com.newco.marketplace.api.services.leadsmanagement.CompleteLeadsService;
import com.newco.marketplace.api.services.leadsmanagement.FetchProvidersService;
import com.newco.marketplace.api.services.leadsmanagement.GetEligibleProviderService;
import com.newco.marketplace.api.services.leadsmanagement.GetProviderFirmDetailService;
import com.newco.marketplace.api.services.leadsmanagement.MembershipInfoService;
import com.newco.marketplace.api.services.leadsmanagement.PostLeadService;
import com.newco.marketplace.api.services.leadsmanagement.ProviderRatingAndReviewService;
import com.newco.marketplace.api.services.leadsmanagement.ScheduleAppointmentService;
import com.newco.marketplace.api.services.leadsmanagement.InsideSalesLeadsService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;

/**
 * This class handles all the Lead management related requests. This is the
 * resource class for all the Leads related exposed webservices
 * 
 * @author Infosys
 * @version 1.0
 */

// Lead Management Request Processor class
@Path("v1.0")
public class LeadManagementRequestProcessor {
	private Logger logger = Logger.getLogger(LeadManagementRequestProcessor.class);

	// Below are all the services
	// Service to fetch the lead matched Provider Firms
	protected FetchProvidersService fetchProvidersService;
	// Service to post a lead
	protected PostLeadService postleadService;
	//service to get provider firm detail
	protected GetProviderFirmDetailService getProviderFirmDetailService;

	
	//service to get all leads info for a firm
    protected MembershipInfoService	membershipInfoService;
    protected ProviderRatingAndReviewService providerRatingAndReviewService;
    protected ScheduleAppointmentService scheduleAppointmentService;
    protected AssignOrReassignProviderService assignOrReassignProviderService;
    //service to get all eligible providers under a firm
    protected GetEligibleProviderService getEligibleProviderService;
    //service for complete leads
    protected CompleteLeadsService completeLeadsService;
    
  //service for cancel lead
    protected CancelLeadService cancelLeadService;
    
    protected InsideSalesLeadsService insideSalesLeadsService;


	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;

	@POST
	@Path("/slleads/getLeadProviderFirms")
	public Response getLeadProviderFirms(String createRequest) {
		long start = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementRequestProcessor getLeadProviderFirms()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setRequestType(RequestType.Post);
		createResponse = fetchProvidersService.doSubmit(apiVO);
		createResponse = PublicAPIConstant.XML_VERSION + createResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving LeadManagementRequestProcessor getLeadProviderFirms()");
		}
		long end = System.currentTimeMillis();
    	if (logger.isInfoEnabled()) {
            logger.info("Inside LeadManagementRequestProcessor.getLeadProviderFirms()..>>" +
            		"Creation Time Taken for lead from processor>>"+(end-start));
		}
		return Response.ok(createResponse).build();
	}

	@POST
	@Path("/slleads/postMemberLead")
	public Response postMemberLead(String createRequest) {
		long start = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementRequestProcessor postMemberLead()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setRequestType(RequestType.Post);
		createResponse = postleadService.doSubmit(apiVO);
		createResponse = PublicAPIConstant.XML_VERSION + createResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving LeadManagementRequestProcessor postMemberLead()");
		}
		long end = System.currentTimeMillis();
    	if (logger.isInfoEnabled()) {
            logger.info("Inside LeadManagementRequestProcessor.postMemberLead()..>>" +
            		"Creation Time Taken for lead to post from processor>>"+(end-start));
		}
		return Response.ok(createResponse).build();
	}
	
	@POST
	@Path("/slleads/getProviderFirmDetail")
	public Response getProviderFirmDetail(String createRequest ) {
		long start = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementRequestProcessor getProviderFirmDetail()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setRequestType(RequestType.Post);
		createResponse = getProviderFirmDetailService.doSubmit(apiVO);
		createResponse = PublicAPIConstant.XML_VERSION + createResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving LeadManagementRequestProcessor getProviderFirmDetails()");
		}
				long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
            logger.info("Inside LeadManagementRequestProcessor.getProviderFirmDetail()..>>" +
            		"Time taken to get all provider Firm Details  from processor>>"+(end-start));
		}
		return Response.ok(createResponse).build();
	}
	/**This method returns all Eligible providers under a firm for 
	 * @param String 
	 *           firmId,
	 * @param String leadId          
	 * @return Response
	 */
	
	@GET
	@Path("/slleads/firmId/{firm_id}/leadId/{lead_id}/getEligibleProviders")
	public Response getEligibleProviders(@PathParam("firm_id")
	String firmId,@PathParam("lead_id")String leadId){
		if (logger.isInfoEnabled())
			logger.info("Entering LeadManagementRequestProcessor getEligibleProviders()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setFirmId(firmId);
		apiVO.setleadId(leadId);
		String responseXML=getEligibleProviderService.doSubmit(apiVO);
		return Response.ok(responseXML).build();
		
	}

	
	
	
    @PUT
	@Path("/slleads/updateMembershipInfo")
	public Response updateMembershipInfo(String updateRequest) {
		    	long start = System.currentTimeMillis();
		
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementRequestProcessor updateMembershipInfo()");
		}
		String updateResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(updateRequest);
		apiVO.setRequestType(RequestType.Put);
		updateResponse = membershipInfoService.doSubmit(apiVO);
		updateResponse = PublicAPIConstant.XML_VERSION + updateResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving LeadManagementRequestProcessor updateMembershipInfo()");
		}
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
            logger.info("Inside LeadManagementRequestProcessor.updateMembershipInfo()..>>" +
            		"Time taken to update membership info from processor>>"+(end-start));
		}
		return Response.ok(updateResponse).build();
	}
	
	@POST
	@Path("/slleads/addProviderReview")
	public Response addProviderReview(String createRequest) {
				long start = System.currentTimeMillis();
		
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementRequestProcessor addProviderReview()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setRequestType(RequestType.Put);
		createResponse = providerRatingAndReviewService.doSubmit(apiVO);
		createResponse = PublicAPIConstant.XML_VERSION + createResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving LeadManagementRequestProcessor addProviderReview()");
		}
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
            logger.info("Inside LeadManagementRequestProcessor.addProviderReview()..>>" +
            		"Time taken to add review  from processor>>"+(end-start));
		}
		return Response.ok(createResponse).build();
	}
	
	
	@POST
	@Path("/slleads/scheduleAppointment")
	public Response scheduleAppointment(String createRequest) {
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementRequestProcessor scheduleAppointment()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setRequestType(RequestType.Put);
		createResponse = scheduleAppointmentService.doSubmit(apiVO);
		createResponse = PublicAPIConstant.XML_VERSION + createResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving LeadManagementRequestProcessor addProviderReview()");
		}
		return Response.ok(createResponse).build();
	}
	
	@POST
	@Path("/slleads/assignProvider")
	public Response assignOrReassignProviderResponse(String createRequest) {
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementRequestProcessor assignOrReassignProviderResponse()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setRequestType(RequestType.Post);
		createResponse =assignOrReassignProviderService.doSubmit(apiVO);
		createResponse = PublicAPIConstant.XML_VERSION + createResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving LeadManagementRequestProcessor assignOrReassignProviderResponse()");
		}
		return Response.ok(createResponse).build();
	}
	
	@POST
	@Path("/slleads/completeLeads")
	public Response CompleteLeadsResponse(String createRequest) {
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementRequestProcessor CompleteLeadsResponse()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setRequestType(RequestType.Post);
		createResponse =completeLeadsService.doSubmit(apiVO);
		createResponse = PublicAPIConstant.XML_VERSION + createResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving LeadManagementRequestProcessor CompleteLeadsResponse()");
		}
		return Response.ok(createResponse).build();
	}
	
	@POST
	@Path("/slleads/cancelLead")
	public Response cancelLead(String createRequest) {
		
		if (logger.isInfoEnabled()) {
			logger.info("Entering LeadManagementRequestProcessor cancelLead()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setRequestType(RequestType.Post);
		//cancel lead service here
		createResponse = cancelLeadService.doSubmit(apiVO);
		createResponse = PublicAPIConstant.XML_VERSION + createResponse;
		if (logger.isInfoEnabled()) {
			logger.info("Leaving LeadManagementRequestProcessor cancelLead()");
		}
		return Response.ok(createResponse).build();
	}
	
	@POST
    @Path("/slleads/leads")
    @Consumes({"application/x-www-form-urlencoded","*/*"})
    public Response isLeads(@FormParam("XML") String xml, @FormParam("XML_EXPORT_REASON") String reason) {
        long start = System.currentTimeMillis();
        if (logger.isInfoEnabled()) {
            logger.info("Entering LeadManagementRequestProcessor putISLeads()");
        }
        String createResponse = insideSalesLeadsService.execute(xml,reason);              
       
        if (logger.isInfoEnabled()) {
            logger.info("Leaving LeadManagementRequestProcessor postMemberLead()");
        }
        long end = System.currentTimeMillis();       
        return Response.ok(createResponse).build();
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

	public FetchProvidersService getFetchProvidersService() {
		return fetchProvidersService;
	}

	public void setFetchProvidersService(
			FetchProvidersService fetchProvidersService) {
		this.fetchProvidersService = fetchProvidersService;
	}

	public PostLeadService getPostleadService() {
		return postleadService;
	}

	public void setPostleadService(PostLeadService postleadService) {
		this.postleadService = postleadService;
	}
	public GetProviderFirmDetailService getGetProviderFirmDetailService() {
		return getProviderFirmDetailService;
	}

	public void setGetProviderFirmDetailService(
			GetProviderFirmDetailService getProviderFirmDetailService) {
		this.getProviderFirmDetailService = getProviderFirmDetailService;
	}

	public MembershipInfoService getMembershipInfoService() {
		return membershipInfoService;
	}

	public void setMembershipInfoService(MembershipInfoService membershipInfoService) {
		this.membershipInfoService = membershipInfoService;
	}

    public ProviderRatingAndReviewService getProviderRatingAndReviewService() {
		return providerRatingAndReviewService;
	} 

	public void setProviderRatingAndReviewService(
			ProviderRatingAndReviewService providerRatingAndReviewService) {
		this.providerRatingAndReviewService = providerRatingAndReviewService;
	}

	public ScheduleAppointmentService getScheduleAppointmentService() {
		return scheduleAppointmentService;
	}

	public void setScheduleAppointmentService(
			ScheduleAppointmentService scheduleAppointmentService) {
		this.scheduleAppointmentService = scheduleAppointmentService;
	}
	
    public AssignOrReassignProviderService getAssignOrReassignProviderService() {
		return assignOrReassignProviderService;
	}

	public void setAssignOrReassignProviderService(
			AssignOrReassignProviderService assignOrReassignProviderService) {
		this.assignOrReassignProviderService = assignOrReassignProviderService;
	}

	public GetEligibleProviderService getGetEligibleProviderService() {
		return getEligibleProviderService;
	}

	public void setGetEligibleProviderService(
			GetEligibleProviderService getEligibleProviderService) {
		this.getEligibleProviderService = getEligibleProviderService;
	}
	
	public CompleteLeadsService getCompleteLeadsService() {
		return completeLeadsService;
	}

	public void setCompleteLeadsService(CompleteLeadsService completeLeadsService) {
		this.completeLeadsService = completeLeadsService;
	}
	public CancelLeadService getCancelLeadService() {
		return cancelLeadService;
	}
	public void setCancelLeadService(CancelLeadService cancelLeadService) {
		this.cancelLeadService = cancelLeadService;
	}
	
	public InsideSalesLeadsService getInsideSalesLeadsService() {
		return insideSalesLeadsService;
	}

	public void setInsideSalesLeadsService(
			InsideSalesLeadsService insideSalesLeadsService) {
		this.insideSalesLeadsService = insideSalesLeadsService;
	}
	
}