package com.newco.marketplace.api.server.b2c.v1_0;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.b2c.CreateAndPostLeadRequest;
import com.newco.marketplace.api.beans.b2c.CreateAndPostLeadResponse;
import com.newco.marketplace.api.beans.b2c.DownLoadResponse;
import com.newco.marketplace.api.beans.b2c.DownloadErrorResult;
import com.newco.marketplace.api.beans.b2c.ProviderDetailsResponse;
import com.newco.marketplace.api.beans.b2c.SearchProvidersRequest;
import com.newco.marketplace.api.beans.b2c.SearchProvidersResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.server.homeimprovement.v1_0.BaseRequestProcessor;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.b2c.CreateAndPostLeadService;
import com.newco.marketplace.api.services.b2c.DocumentDownloadService;
import com.newco.marketplace.api.services.b2c.GetProviderDetailsService;
import com.newco.marketplace.api.services.b2c.SearchProvidersService;

/**
 * This class handles all the Lead management related requests. This is the
 * resource class for all the Leads related exposed webservices
 * 
 * @author Infosys
 * @version 1.0
 */

// Lead Management Request Processor class
@Path("v1.0")
public class B2CRequestProcessor extends BaseRequestProcessor {
	private Logger logger = Logger.getLogger(B2CRequestProcessor.class);

	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;
	
	protected SearchProvidersService searchProvidersService;
	protected GetProviderDetailsService getProviderDetailsService;
	protected DocumentDownloadService documentDownloadService;
	protected CreateAndPostLeadService createAndPostLeadService;

	@POST
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	@Path("/searchProviders")
	public SearchProvidersResponse getResponseForSearchProviders(SearchProvidersRequest request) {
		String requestXML = null;
		String responseXML = null;
		SearchProvidersResponse response = new SearchProvidersResponse();
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		
		requestXML = convertReqObjectToXMLString(request, SearchProvidersRequest.class); 
		apiVO.setRequestType(RequestType.Post);
		apiVO.setRequestFromPostPut(requestXML);
		
		responseXML = searchProvidersService.doSubmit(apiVO);
		
		response = (SearchProvidersResponse) convertXMLStringtoRespObject(responseXML, SearchProvidersResponse.class); 
		return response;
	}

	
	@GET
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	@Path("provider/{providerId}/getProviderDetails")
	public ProviderDetailsResponse getResponseForProviderDetails(@PathParam("providerId") String providerId){
		String responseXML = null;
		ProviderDetailsResponse response = new ProviderDetailsResponse();
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		
		apiVO.setRequestType(RequestType.Get);
		apiVO.setProviderId(providerId);
		
		responseXML=getProviderDetailsService.doSubmit(apiVO);
		
		response = (ProviderDetailsResponse) convertXMLStringtoRespObject(responseXML, ProviderDetailsResponse.class);
		return response;
		
	}

	@GET
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	@Path("/provider/{providerId}/serviceorder/{soId}/downloadDocument/{documentId}")
	public Response getResponseForDownloadDocument(@PathParam("providerId") String providerId,@PathParam("documentId") String documentId) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(RequestType.Get);
		apiVO.setProviderId(providerId);
		apiVO.setDocumentId(documentId);

		DownloadErrorResult result=documentDownloadService.execute(apiVO);

		if (null != result && StringUtils.isNotBlank(result.getFilePath())) {
			File file = new File(result.getFilePath());
			MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
			String mimeType = mimeTypesMap.getContentType(file);
			ResponseBuilder response = Response.ok((Object) file);
			String fileName=file.getName().substring(file.getName().indexOf("_")+1,file.getName().length());
			response.header("Content-disposition", "attachment; filename="+ fileName);
			response.header("Content-Type", mimeType);
			// update response 
			//get bytes content of File
			String responseXml="";
			//responseXml=getBytesFromFile(file);
			return response.build();
		} else{
			DownLoadResponse errorResponse=null;
			//errorResponse =	createDownloadResponse(result.getCode(),result.getMessage());
			String resultString= "";
			//resultString = convertXMLStringToJsonObject(errorResponse);
			Response response = Response.ok(resultString).build();
			// update response 
			return response;
		}

	}
	
	@POST
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	@Path("/createAndPostLead")
	public CreateAndPostLeadResponse getResponseForCreateAndPostLead(CreateAndPostLeadRequest request) {
		String requestXML = null;
		String responseXML = null;
		CreateAndPostLeadResponse response = new CreateAndPostLeadResponse();
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		
		requestXML = convertReqObjectToXMLString(request, CreateAndPostLeadRequest.class); 
		apiVO.setRequestType(RequestType.Post);
		apiVO.setRequestFromPostPut(requestXML);
		
		responseXML = createAndPostLeadService.doSubmit(apiVO);
		
		response = (CreateAndPostLeadResponse) convertXMLStringtoRespObject(responseXML, CreateAndPostLeadResponse.class); 
		return response;
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


	public SearchProvidersService getSearchProvidersService() {
		return searchProvidersService;
	}


	public void setSearchProvidersService(
			SearchProvidersService searchProvidersService) {
		this.searchProvidersService = searchProvidersService;
	}


	public GetProviderDetailsService getGetProviderDetailsService() {
		return getProviderDetailsService;
	}


	public void setGetProviderDetailsService(
			GetProviderDetailsService getProviderDetailsService) {
		this.getProviderDetailsService = getProviderDetailsService;
	}


	public DocumentDownloadService getDocumentDownloadService() {
		return documentDownloadService;
	}


	public void setDocumentDownloadService(
			DocumentDownloadService documentDownloadService) {
		this.documentDownloadService = documentDownloadService;
	}


	public CreateAndPostLeadService getCreateAndPostLeadService() {
		return createAndPostLeadService;
	}


	public void setCreateAndPostLeadService(
			CreateAndPostLeadService createAndPostLeadService) {
		this.createAndPostLeadService = createAndPostLeadService;
	}

	
}