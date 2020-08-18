/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-May-2014	KMSRTVST  Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.server.v1_3;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.so.v1_3.SOCreateService;
import com.newco.marketplace.api.services.so.v1_3.SORetrieveService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;

/**
 * This class would act as an intercepter for all the public api services version 1.3 in the
 * application.
 * 
 * @author Infosys
 * @version 1.0
 */
// routing all incoming requests for version v1.3 to the SORequestProcessor class
@Path("v1.3")
public class SORequestProcessor extends com.newco.marketplace.api.server.v1_2.SORequestProcessor{
	private Logger logger = Logger.getLogger(SORequestProcessor.class);
	private static final String MEDIA_TYPE_XML_STR = "application/xml";
	 private static final String MEDIA_TYPE_JSON_STR = "application/json";
	protected SORetrieveService retrieveService_v1_3;
	// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;	
	
	protected SOCreateService createService_v1_3;
	
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
		String responseXML = retrieveService_v1_3.doSubmit(apiVO);
		return Response.ok(responseXML).build();

	}
	
	/**
	 * This method returns a response for a Create service order request with unique id for the order.
	 * 
	 * @param String
	 *            buyerId
	 * @param String
	 *            createRequestXML
	 * @return Response
	 */
	@POST
	@Path("/buyers/{buyer_id}/serviceorders")
	// Removed '/' at the end since this URL is conflicting with the Edit SO's
	// URL
	public Response getResponseForCreate(@PathParam("buyer_id") String buyerId,
			String createRequest) {
		long start = System.currentTimeMillis();

		if (logger.isInfoEnabled()) {
			logger.info("Entering SORequestProcessor.getResponseForCreate()");
		}
		String createResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(createRequest);
		apiVO.setBuyerId(buyerId);
		apiVO.setAcceptHeader(MEDIA_TYPE_XML_STR);
		// Adding the code for enabling the json support
		if (getHttpRequest().getContentType() != null) {
			String contentType = getHttpRequest().getContentType().split(";")[0];
			apiVO.setContentType(contentType);

		}

		if (getHttpRequest().getHeader("Accept") != null
				&& getHttpRequest().getHeader("Accept").equalsIgnoreCase(
						MEDIA_TYPE_JSON_STR)) {
			apiVO.setAcceptHeader(MEDIA_TYPE_JSON_STR);
		}

		apiVO.setRequestType(RequestType.Post);
		createResponse = createService_v1_3.doSubmit(apiVO);
		if (MEDIA_TYPE_XML_STR.equalsIgnoreCase(apiVO.getAcceptHeader())) {
			createResponse = PublicAPIConstant.XML_VERSION + createResponse;
			}
		if (logger.isInfoEnabled()) {
			logger.info("Leaving SORequestProcessor.getResponseForCreateWithTimeslots()");
		}
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("Inside SORequestProcessor.getResponseForCreateWithTimeslots()..>>Creation Time Taken>>"
					+ (end - start));
		}
		return Response.ok(createResponse).build();
	}

	public SORetrieveService getRetrieveService_v1_3() {
		return retrieveService_v1_3;
	}

	public void setRetrieveService_v1_3(SORetrieveService retrieveService_v1_3) {
		this.retrieveService_v1_3 = retrieveService_v1_3;
	}

	
	public SOCreateService getCreateService_v1_3() {
		return createService_v1_3;
	}

	public void setCreateService_v1_3(SOCreateService createService_v1_3) {
		this.createService_v1_3 = createService_v1_3;
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
