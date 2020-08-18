/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-September-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.server.v1_1;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.document.DocumentDeleteService;
import com.newco.marketplace.api.services.document.DocumentUploadService;
import com.newco.marketplace.api.services.document.BuyerDocumentUploadService;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import common.Logger;

/**
 * This class would act as an intercepter for all the public api document 
 * services in the application.
 * 
 * @author Infosys
 * @version 1.0
 */
//routing all incoming requests to the DocumentRequestProcessor class
@Path("v1.1")
public class DocumentRequestProcessor {
	private Logger logger = Logger.getLogger(DocumentRequestProcessor.class);
	
	protected DocumentUploadService uploadService;
	protected DocumentDeleteService documentDeleteService;
	//Required for retrieving attributes from Get/Delete URL
	@Resource
	protected HttpServletRequest httpRequest;
	
	protected BuyerDocumentUploadService buyerDocumentUploadService;
	
	
	/**
	 * This method returns the response for a document upload.
	 * @param String documentCreateRequest
	 * @return Response
	 * URL: /public/buyers/1929/uploads/
	 */
	@POST
	@Path("/buyers/{buyerid}/uploads/")
	public Response getResponseForUpload(@PathParam("buyerid")String buyerId
			, String documentCreateRequest) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		logger.info("Entering DocumentRequestProcessor.getResponseForUpload()"+ documentCreateRequest);
		apiVO.setRequestFromPostPut(documentCreateRequest);
		apiVO.setRequestType(RequestType.Post);		
		apiVO.setBuyerId(buyerId);
		String responseXML = uploadService.doSubmit(apiVO);
		logger.info("Leaving DocumentRequestProcessor.getResponseForUpload()");
		return Response.ok(responseXML).build();		
	}
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
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> requestMap = CommonUtility
		.getRequestMap(getHttpRequest().getParameterMap());
		
		apiVO.setBuyerId(buyerId);
		apiVO.addProperties(APIRequestVO.FILENAME, fileName);
		apiVO.setRequestFromGetDelete(requestMap);
		apiVO.setRequestType(RequestType.Delete);
		String responseXML = documentDeleteService.doSubmit(apiVO);
		logger.info("Leaving DocumentRequestProcessor.getResponseForDelete()");
		return Response.ok(responseXML).build();
	}
	
	//code for document upload using multi-part form data
	//added new param tripNo for document upload corresponding to each trip
	@POST
    @Path("/buyers/{buyer_id}/serviceorders/{so_id}/uploadDocument")
    public Response getResponseForUploadDocument(@PathParam("so_id") String soId,
    		@PathParam("buyer_id") String buyerId, String documentCreateRequest) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		logger.info("Entering DocumentRequestProcessor.getResponseForUploadDocument()"+ documentCreateRequest);
		apiVO.setRequestFromPostPut(documentCreateRequest);
		apiVO.setRequestType(RequestType.Post);		
		apiVO.setBuyerId(buyerId);
		apiVO.setSOId(soId);
		String responseXML = buyerDocumentUploadService.doSubmit(apiVO);
		logger.info("Leaving DocumentRequestProcessor.getResponseForUploadDocument()");
		return Response.ok(responseXML).build();		
	}	
	
	
	
	public void setUploadService(DocumentUploadService uploadService) {
		this.uploadService = uploadService;
	}	
	public void setDocumentDeleteService(DocumentDeleteService documentDeleteService) {
		this.documentDeleteService = documentDeleteService;
	}
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}
	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
	public BuyerDocumentUploadService getBuyerDocumentUploadService() {
		return buyerDocumentUploadService;
	}

	public void setBuyerDocumentUploadService(
			BuyerDocumentUploadService buyerDocumentUploadService) {
		this.buyerDocumentUploadService = buyerDocumentUploadService;
	}
}
