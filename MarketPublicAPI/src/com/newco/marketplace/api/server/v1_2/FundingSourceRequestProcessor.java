/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 
 * 
 */

package com.newco.marketplace.api.server.v1_2;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

/**
 * @author ndixit
 * Request processor for funding source related APIs. 
 * 
 */
@Path("v1.2")
public class FundingSourceRequestProcessor extends com.newco.marketplace.api.server.v1_1.FundingSourceRequestProcessor{
	
private Logger logger = Logger.getLogger(FundingSourceRequestProcessor.class);
	
		
	// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;

	/** 
	 * This method returns the funding sources for a given buyer ID.
	 * @param buyerId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyer_id}/fundingsources")
	public Response getFundingSources(@PathParam("buyer_id") String buyerId) {
		logger.info("Entering FundingSourceRequestProcessor.getFundingSources()");
		super.setHttpRequest(httpRequest);
		return(super.getFundingSources(buyerId));
		
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
}
