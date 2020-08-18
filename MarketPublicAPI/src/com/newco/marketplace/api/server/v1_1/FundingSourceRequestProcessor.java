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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.wallet.fundingsource.CreateFundingSourceService;
import com.newco.marketplace.api.services.wallet.fundingsource.GetFundingSourceService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.validators.ResponseValidator;

/**
 * @author ndixit
 * Request processor for funding source related APIs. 
 * 
 */
@Path("v1.1")
public class FundingSourceRequestProcessor {
	private Logger logger = Logger.getLogger(FundingSourceRequestProcessor.class);
	
	protected CreateFundingSourceService createFundingSourceService;
	protected GetFundingSourceService getFundingSourceService;
	protected ResponseValidator responseValidator;
	
	// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;
	//final protected MediaType mediaType = MediaType.parse("text/xml");
	final protected String mediaType = MediaType.TEXT_XML;
	
	


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
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());	
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap()); 
				
		apiVO.setBuyerId(buyerId);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);
		String fundingSourceResponse = (String)getFundingSourceService.doSubmit(apiVO);
		logger.info("Exiting FundingSourceRequestProcessor.getFundingSources()");
		return Response.ok(fundingSourceResponse, mediaType).build();
	}
	
	/**
	 * This method creates a funding source for a given resource ID
	 * @param xmlRequest, buyerResourceId
	 * @return Response
	 */
	@POST
	@Path("/buyers/{buyer_id}/fundingsources")
	public Response createFundingSources(@PathParam("buyer_id") String buyerId, String xmlRequest) {
		logger.info("Entering FundingSourceRequestProcessor.createFundingSources()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(xmlRequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.setBuyerId(buyerId);
		String fundingSourceResponse = (String)createFundingSourceService.doSubmit(apiVO);
		logger.info("Exiting FundingSourceRequestProcessor.createFundingSources()");
		return Response.ok(fundingSourceResponse, mediaType).build();
	}

	public ResponseValidator getResponseValidator() {
		return responseValidator;
	}

	public void setResponseValidator(ResponseValidator responseValidator) {
		this.responseValidator = responseValidator;
	}

	public CreateFundingSourceService getCreateFundingService() {
		return createFundingSourceService;
	}

	public void setCreateFundingSourceService(
			CreateFundingSourceService createFundingSourceService) {
		this.createFundingSourceService = createFundingSourceService;
	}

	public GetFundingSourceService getFundingSourceService() {
		return getFundingSourceService;
	}

	public void setGetFundingSourceService(GetFundingSourceService getFundingService) {
		this.getFundingSourceService = getFundingService;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
}
