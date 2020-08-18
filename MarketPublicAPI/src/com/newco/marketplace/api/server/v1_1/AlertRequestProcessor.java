/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.server.v1_1;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.alerts.SendAlertsService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;

import org.apache.log4j.Logger;

/**
 * This class would act as an intercepter for all the alert public api services
 * in the application.
 * 
 * @author Infosys
 * @version 1.0
 */
// routing all incoming requests to the AlertRequestProcessor class
@Path("v1.1")
public class AlertRequestProcessor {
	private Logger logger = Logger.getLogger(AlertRequestProcessor.class);
	protected SendAlertsService sendAlertsService;
	
	@Resource
	protected HttpServletRequest httpRequest;

	/**
	 * This method inserts an entry in alert_task table for sending mail.
	 * 
	 * @param resourceId String
	 * @param requestXML String
	 * @return Response 
	 * URL: /alert/send/91014
	 */
	@POST
	@Path("/marketplace/alerts")
	public Response getResponseForSendAlert(String requestXML) {
		logger.info("Entering AlertRequestProcessor.getResponseForSendAlert()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(RequestType.Post);
		String responseXML = sendAlertsService.doSubmit(apiVO);
		responseXML=PublicAPIConstant.XML_VERSION + responseXML;
		logger.info("Response XML for Send Alert is:"+responseXML);
		return Response.ok(responseXML).build();
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public void setSendAlertsService(SendAlertsService sendAlertsService) {
		this.sendAlertsService = sendAlertsService;
	}

}
