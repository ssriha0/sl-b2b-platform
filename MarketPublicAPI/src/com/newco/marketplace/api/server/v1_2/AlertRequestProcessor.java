/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.server.v1_2;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

/**
 * This class would act as an intercepter for all the alert public api services
 * in the application.
 * 
 * @author Infosys
 * @version 1.0
 */
// routing all incoming requests to the AlertRequestProcessor class
@Path("v1.2")
public class AlertRequestProcessor extends com.newco.marketplace.api.server.v1_1.AlertRequestProcessor{	
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;
	
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
