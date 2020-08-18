/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-May-2014	KMSRTVST  Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.server.v1_5;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.so.v1_5.SORetrieveService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;

/**
 * This class would act as an intercepter for all the public api services version 1.3 in the
 * application.
 * 
 * @author Sears IT
 * @version 1.4
 */
// routing all incoming requests for version v1.4 to the SORequestProcessor class
@Path("v1.5")
public class SORequestProcessor extends com.newco.marketplace.api.server.v1_4.SORequestProcessor{
	private Logger logger = Logger.getLogger(SORequestProcessor.class);
	private static final String MEDIA_TYPE_XML_STR = "application/xml";
	 private static final String MEDIA_TYPE_JSON_STR = "application/json";
	protected SORetrieveService retrieveService_v1_5;
	// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;	
	
	/**
	 * This method returns a response for a get / retrieve service order
	 * request.
	 * @param String buyerId, String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyer_id}/serviceorders/{so_ids}")
	public Response getResponseForRetrieve(@PathParam("so_ids")
	String soIds, @PathParam("buyer_id")
	String buyerId) {
		if (logger.isInfoEnabled()){
			logger.info("getResponseForRetrieve method started");
		}
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		
		apiVO.setAcceptHeader(MEDIA_TYPE_XML_STR);
		// Adding the code for enabling the  json support
		if (getHttpRequest().getContentType()!=null){
		String contentType =getHttpRequest().getContentType().split(";")[0];
		apiVO.setContentType(contentType);
		
		}
		
		if(getHttpRequest().getHeader("Accept") !=null && getHttpRequest().getHeader("Accept").equalsIgnoreCase(MEDIA_TYPE_JSON_STR)){
			apiVO.setAcceptHeader(MEDIA_TYPE_JSON_STR);
		}
	
		
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setBuyerId(buyerId);
		apiVO.addProperties(PublicAPIConstant.SO_ID_LIST,soIds);
		String responseXML = retrieveService_v1_5.doSubmit(apiVO);
		return Response.ok(responseXML).build();

	}

	public SORetrieveService getRetrieveService_v1_5() {
		return retrieveService_v1_5;
	}

	public void setRetrieveService_v1_5(SORetrieveService retrieveService_v1_5) {
		this.retrieveService_v1_5 = retrieveService_v1_5;
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
