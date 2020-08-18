package com.newco.marketplace.api.server.v1_0;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.server.BaseRequestProcessor;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.buyerauthentication.BuyerAuthenticationDetailsService;

@Path("v1.0")
public class BuyerAuthenticationDetailsProcessor extends BaseRequestProcessor{
	
	private Logger logger = Logger.getLogger(BuyerEventCallbackProcessor.class);
	

	private static final String MEDIA_TYPE_XML_STR = "application/xml";
	private static final String MEDIA_TYPE_JSON_STR = "application/json";
	
	private BuyerAuthenticationDetailsService buyerAuthenticationDetailsService;
	
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/servicelive/{buyerId}/authenticationDetails")	
	public Response getBuyerAuthenticationDetails(@PathParam("buyerId") String buyerId){
		logger.info("Entering BuyerAuthenticationDetailsProcessor.getBuyerAuthenticationDetails()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(RequestType.Get);
		apiVO.setAcceptHeader(MEDIA_TYPE_XML_STR);
		// Adding the code for enabling the json support
		if (getHttpRequest().getContentType() != null) {
			String contentType = getHttpRequest().getContentType().split(";")[0];
			apiVO.setContentType(contentType);
		}
		if (getHttpRequest().getHeader("Accept") != null
				&& getHttpRequest().getHeader("Accept").equalsIgnoreCase(MEDIA_TYPE_JSON_STR)) {
			apiVO.setAcceptHeader(MEDIA_TYPE_JSON_STR);
		}
		apiVO.setBuyerId(buyerId);
		String response = buyerAuthenticationDetailsService.doSubmit(apiVO);
		logger.info("BuyerAuthenticationDetailsProcessor.getBuyerAuthorizationDetails()");
		return Response.ok(response).build();
		
	}
	

	public BuyerAuthenticationDetailsService getBuyerAuthenticationDetailsService() {
		return buyerAuthenticationDetailsService;
	}

	public void setBuyerAuthenticationDetailsService(BuyerAuthenticationDetailsService buyerAuthenticationDetailsService) {
		this.buyerAuthenticationDetailsService = buyerAuthenticationDetailsService;
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
