/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 
 * 
 */

package com.newco.marketplace.api.server.v1_1;

import java.util.Map;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.wallet.AddFundsToWalletService;
import com.newco.marketplace.api.services.wallet.WalletBalanceService;
import com.newco.marketplace.api.services.wallet.WalletHistoryService;
import com.newco.marketplace.api.services.wallet.WalletThresholdService;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.validators.RequestValidator;
import com.newco.marketplace.api.utils.validators.ResponseValidator;

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

@Path("v1.1")
public class WalletRequestProcessor {
	private Logger logger = Logger.getLogger(WalletRequestProcessor.class);
	protected ResponseValidator responseValidator;
	protected RequestValidator requestValidator;
	protected CommonUtility commonUtility;
	protected AddFundsToWalletService addFundsToWalletService;
	//final protected MediaType mediaType = MediaType.parse("text/xml");
	final protected String mediaType = MediaType.TEXT_XML;
	// services
	protected WalletHistoryService walletHistoryService;
	protected WalletBalanceService walletBalanceService;
	protected WalletThresholdService walletThresholdService;
	// Required for retrieving attributes from Get URL
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
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setBuyerId(buyerId);
		//reqMap.put(PublicAPIConstant.Wallet.BUYER_ID, buyerId);
		
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);
		String responseXML = walletHistoryService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
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
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setBuyerId(buyerId);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);
		String responseXML = walletBalanceService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();		
	}

	
	/**
	 * This method add funds to the wallet  for a given ID.
	 * @param buyerId (Company Id), fundingSource (Account Num.)
	 * 
	 * URL: /buyers/{buyerId}/wallet/funds/{fundingSource}	  
	 * Request XSD   : addFundsToWalletRequest.xsd
	 * Response XSD  : addFundsToWalletResponse.xsd
	 * Service Class : AddFundsToWalletService
	 * 
	 * @return Response
	 */
	@PUT
	@Path("/buyers/{buyerId}/wallet/funds/{fundingSource}")
	public Response addFundsToWallet(String requestXML, @PathParam("buyerId") String buyerId, @PathParam("fundingSource") Integer fundingSource) {		
		logger.info("Entering WalletRequestProcessor.addFundsToWallet()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(RequestType.Put);
		apiVO.setBuyerId(buyerId);
		apiVO.addProperties("fundingSource", fundingSource);
		String responseXML = addFundsToWalletService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
	}
	
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyerId}/wallet/threshold/")
	public Response getBuyerWalletThreshold() {
		logger.info("Entering WalletRequestProcessor.getBuyerWalletThreshold()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(RequestType.Get);
		String responseXML = walletThresholdService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();		
	}
	
	public WalletHistoryService getWalletHistoryService() {
		return walletHistoryService;
	}

	public void setWalletHistoryService(WalletHistoryService walletHistoryService) {
		this.walletHistoryService = walletHistoryService;
	}

	public ResponseValidator getResponseValidator() {
		return responseValidator;
	}

	public void setResponseValidator(ResponseValidator responseValidator) {
		this.responseValidator = responseValidator;
	}

	public CommonUtility getCommonUtility() {
		return commonUtility;
	}

	public void setCommonUtility(CommonUtility commonUtility) {
		this.commonUtility = commonUtility;
	}

	public RequestValidator getRequestValidator() {
		return requestValidator;
	}

	public void setRequestValidator(RequestValidator requestValidator) {
		this.requestValidator = requestValidator;
	}

	public AddFundsToWalletService getAddFundsToWalletService() {
		return addFundsToWalletService;
	}

	public void setAddFundsToWalletService(
			AddFundsToWalletService addFundsToWalletService) {
		this.addFundsToWalletService = addFundsToWalletService;
	}
	
	public WalletBalanceService getWalletBalanceService() {
		return walletBalanceService;
	}

	public void setWalletBalanceService(WalletBalanceService walletBalanceService) {
		this.walletBalanceService = walletBalanceService;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public WalletThresholdService getWalletThresholdService() {
		return walletThresholdService;
	}

	public void setWalletThresholdService(
			WalletThresholdService walletThresholdService) {
		this.walletThresholdService = walletThresholdService;
	}
	
}
