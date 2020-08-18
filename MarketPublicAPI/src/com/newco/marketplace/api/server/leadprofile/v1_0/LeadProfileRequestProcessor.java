package com.newco.marketplace.api.server.leadprofile.v1_0;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.leadprofile.GetleadProjectService;
import com.newco.marketplace.api.beans.leadprofile.LeadInsertFiltersetService;
import com.newco.marketplace.api.beans.leadprofile.LeadProfileBillingService;
import com.newco.marketplace.api.beans.leadprofile.LeadProfileCreationService;
import com.newco.marketplace.api.beans.leadprofile.leadprofilerequest.LeadInsertFiltersetRequest;
import com.newco.marketplace.api.beans.leadprofile.leadprofilerequest.LeadProfileBillingRequest;
import com.newco.marketplace.api.beans.leadprofile.leadprofilerequest.LeadProfileCreationRequest;
import com.newco.marketplace.api.beans.leadprofile.leadprofileresponse.GetLeadProjectTypeResponse;
import com.newco.marketplace.api.beans.leadprofile.leadprofileresponse.LeadInsertFiltersetResponse;
import com.newco.marketplace.api.beans.leadprofile.leadprofileresponse.LeadProfileBillingResponse;
import com.newco.marketplace.api.beans.leadprofile.leadprofileresponse.LeadProfileCreationResponse;
import com.newco.marketplace.api.utils.utility.CommonUtility;


@Path("v1.0")
public class  LeadProfileRequestProcessor{
	
	private Logger logger = Logger.getLogger(LeadProfileRequestProcessor.class);
	protected LeadProfileCreationService leadProfileCreationService;
	protected LeadProfileBillingService leadProfileBillingService;
	protected GetleadProjectService getleadProjectService;
	protected LeadInsertFiltersetService leadInsertFiltersetService;
	
	@Resource
	protected HttpServletRequest httpRequest;
	
	@POST
	@Path("/leadprofile/createleadprofile")
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public LeadProfileCreationResponse createLeadProfile(LeadProfileCreationRequest request) {
		 logger.info(request);
		 return leadProfileCreationService.execute(request);
	}
	
	
	@POST
	@Path("/leadprofile/createleadprofilebilling")
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public LeadProfileBillingResponse createLeadProfileBillingAccount(LeadProfileBillingRequest request) {
		return leadProfileBillingService.execute(request);
	}
	
	
	@GET
	@Path("/leadprofile/getleadProject")
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public GetLeadProjectTypeResponse getLeadProjectType() {
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		return getleadProjectService.execute(reqMap);
	}
	
	@POST
	@Path("/leadprofile/createFilterSet")
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public LeadInsertFiltersetResponse competitiveInsertFilterSet(LeadInsertFiltersetRequest request) {
		 logger.info(request);
		 return leadInsertFiltersetService.execute(request);
	}
	 
	public LeadProfileCreationService getLeadProfileCreationService() {
		return leadProfileCreationService;
	}

	public void setLeadProfileCreationService(
			LeadProfileCreationService leadProfileCreationService) {
		this.leadProfileCreationService = leadProfileCreationService;
	}
	
	public LeadProfileBillingService getLeadProfileBillingService() {
		return leadProfileBillingService;
	}

	public void setLeadProfileBillingService(
			LeadProfileBillingService leadProfileBillingService) {
		this.leadProfileBillingService = leadProfileBillingService;
	}

	public GetleadProjectService getGetleadProjectService() {
		return getleadProjectService;
	}

	public void setGetleadProjectService(GetleadProjectService getleadProjectService) {
		this.getleadProjectService = getleadProjectService;
	}
	
	public LeadInsertFiltersetService getLeadInsertFiltersetService() {
		return leadInsertFiltersetService;
	}


	public void setLeadInsertFiltersetService(
			LeadInsertFiltersetService leadInsertFiltersetService) {
		this.leadInsertFiltersetService = leadInsertFiltersetService;
	}


	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}
	
	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
}


