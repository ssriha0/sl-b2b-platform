/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	pgangra   SHC				1.0
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
 * This class would act as an intercepter for all the user-management APIs in
 * the application. This class will be used for creating, modifying BuyerAccounts.
 */
@Path("v1.2")
public class UMSRequestProcessor extends com.newco.marketplace.api.server.v1_1.UMSRequestProcessor{
	private Logger logger = Logger.getLogger(UMSRequestProcessor.class);
	
	// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;
	
	/**
	 * 
	 * This method returns the buyer account details
	 * 
	 * @return Response URL: /buyers/<buyerid>/buyerresources/{buyerResourceId}
	 */
	@GET
	@Path("/buyers/{buyer_id}/buyerresources/{buyer_resource_id}")
	public Response getBuyerAccount(@PathParam("buyer_id") String buyerId, @PathParam("buyer_resource_id") String buyerResourceId) {

		if (logger.isInfoEnabled()) {
			logger.info("getBuyerAccount method started");
		}
		super.setHttpRequest(httpRequest);
		return(super.getBuyerAccount(buyerId,buyerResourceId));
		
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
}
