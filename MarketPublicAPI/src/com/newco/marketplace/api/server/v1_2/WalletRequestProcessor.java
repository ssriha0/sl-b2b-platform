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

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;

/**
 * This class would act as an intercepter for all the Wallet APIs in the
 * application. It handles following APIs :
 * 
 * /buyers/{buyerId}/wallet/history/
 * /buyers/{buyerId}/wallet/balance/
 * /buyers/{buyerId}/wallet/funds/{fundingSource}
 * 
 * @author Seshu and Shekhar
 * 
 */

@Path("v1.2")
public class WalletRequestProcessor extends com.newco.marketplace.api.server.v1_1.WalletRequestProcessor{
	private Logger logger = Logger.getLogger(WalletRequestProcessor.class);
	
	@Resource
	protected HttpServletRequest httpRequest;

	/**
	 * This method returns the wallet history for a given date range.
	 * @param buyerId (Company Id)
	 * @return Response
	 * URL: /buyers/{buyerId}/wallet/history?startDate=<mmddyyyy>&endDate=<mmddyyyy>
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyerId}/wallet/history/")	
	public Response getWalletHistory(@PathParam("buyerId") String buyerId) {
		logger.info("Entering WalletRequestProcessor.getWalletHistory()");
		super.setHttpRequest(httpRequest);
		return(super.getWalletHistory(buyerId));
		
	}
		
	/**
	 * This method returns the wallet balance for a given user.
	 * @param buyerId (Company Id)
	 * @return Response
	 * URL: /buyers/{buyerId}/wallet/balance
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyerId}/wallet/balance/")
	public Response getWalletBalance(@PathParam("buyerId") String buyerId) {
		logger.info("Entering WalletRequestProcessor.getWalletBalance()");
		super.setHttpRequest(httpRequest);
		return(super.getWalletBalance(buyerId));
		
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyerId}/wallet/threshold/")
	public Response getBuyerWalletThreshold() {
		logger.info("Entering WalletRequestProcessor.getBuyerWalletThreshold()");
		super.setHttpRequest(httpRequest);
		return (super.getBuyerWalletThreshold());		
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
	
}
