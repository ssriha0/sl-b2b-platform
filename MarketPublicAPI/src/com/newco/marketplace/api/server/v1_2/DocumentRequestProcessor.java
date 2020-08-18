/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-September-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.server.v1_2;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import common.Logger;

/**
 * This class would act as an intercepter for all the public api document 
 * services in the application.
 * 
 * @author Infosys
 * @version 1.0
 */
//routing all incoming requests to the DocumentRequestProcessor class
@Path("v1.2")
public class DocumentRequestProcessor extends com.newco.marketplace.api.server.v1_1.DocumentRequestProcessor{
private Logger logger = Logger.getLogger(DocumentRequestProcessor.class);
	
	//Required for retrieving attributes from Get/Delete URL
	@Resource
	protected HttpServletRequest httpRequest;
	/**
	 * This method returns the response for deleting documents from a simple
	 * buyer.
	 * 
	 * @return Response 
	 * URL:/buyers/1234/uploads/test1.jpg
	 */

	@SuppressWarnings("unchecked")
	@DELETE
	@Path("/buyers/{buyer_id}/uploads/{fileid}")	
	public Response getResponseForDelete(@PathParam("buyer_id")
			String buyerId, @PathParam("fileid")
			String fileName) {
		logger.info("Entering DocumentRequestProcessor.getResponseForDelete()");
		super.setHttpRequest(getHttpRequest());
		return(super.getResponseForDelete(buyerId,fileName));
		
	}
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}
	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
}
