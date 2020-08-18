package com.newco.marketplace.api.server.leadsmanagement.v2_0;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.leadsmanagement.v2_0.FetchProvidersService;
import com.newco.marketplace.api.services.leadsmanagement.v2_0.PostLeadService;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest;

/**
 * This class handles all the Lead management related requests. This is the
 * resource class for all the Leads related exposed webservices
 * 
 * @author Infosys
 * @version 1.0
 */

// Lead Management Request Processor class
@Path("v2.0")
public class LeadManagementRequestProcessor extends BaseRequestProcessor{
	private Logger logger = Logger.getLogger(LeadManagementRequestProcessor.class);

	// Below are all the services
	// Service to fetch the lead matched Provider Firms
	protected FetchProvidersService fetchProvidersService;
	// Service to post a lead
	protected PostLeadService postleadService;
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;

	@POST
	@Path("/slleads/getLeadProviderFirms")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public FetchProviderFirmResponse getLeadProviderFirms(FetchProviderFirmRequest request) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		FetchProviderFirmResponse response = new FetchProviderFirmResponse();
		String requestXML = null;
		String responseXML = null;
		
		requestXML = convertReqObjectToXMLString(request, FetchProviderFirmRequest.class); 
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(RequestType.Post);
		
		responseXML = fetchProvidersService.doSubmit(apiVO);
		response = (FetchProviderFirmResponse) convertXMLStringtoRespObject(responseXML, FetchProviderFirmResponse.class);
		return response; 
	}

	@POST
	@Path("/slleads/postMemberLead")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public FetchProviderFirmResponse postMemberLead(LeadRequest request) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		FetchProviderFirmResponse response = new FetchProviderFirmResponse();
		String requestXML = null;
		String responseXML = null;
		
		requestXML = convertReqObjectToXMLString(request, LeadRequest.class); 
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(RequestType.Post);
		responseXML = postleadService.doSubmit(apiVO);
		response = (FetchProviderFirmResponse) convertXMLStringtoRespObject(responseXML, FetchProviderFirmResponse.class);
		return response; 
	}

	public FetchProvidersService getFetchProvidersService() {
		return fetchProvidersService;
	}

	public void setFetchProvidersService(FetchProvidersService fetchProvidersService) {
		this.fetchProvidersService = fetchProvidersService;
	}

	public PostLeadService getPostleadService() {
		return postleadService;
	}

	public void setPostleadService(PostLeadService postleadService) {
		this.postleadService = postleadService;
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
	
	
}