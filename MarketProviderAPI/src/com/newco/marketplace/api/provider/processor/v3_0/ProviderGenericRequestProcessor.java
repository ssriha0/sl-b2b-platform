package com.newco.marketplace.api.provider.processor.v3_0;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.provider.beans.v3_0.ProviderTimeOnSiteRequest;
import com.newco.marketplace.api.provider.beans.v3_0.ProviderTimeOnSiteResponse;
import com.newco.marketplace.api.provider.constants.ProviderAPIConstant;
import com.newco.marketplace.api.provider.services.v3_0.ProviderTimeOnSiteService;
import com.newco.marketplace.api.server.ProviderBaseRequestProcessor;
import com.newco.marketplace.api.services.BaseService.RequestType;

/*import oauth.signpost.basic.DefaultOAuthConsumer;
import okhttp3.OkHttpClient;
import okhttp3.internal.huc.OkHttpURLConnection;*/

/**
 * @author Infosys
 * $Revision: 1.0 $Date: 2015/04/10
 * Request Processor class for Mobile phase 2 API's
 */

@Path("v3.0")
@Consumes({ "application/xml", "application/json", "text/xml" })
@Produces({ "application/xml", "application/json", "text/xml" })
public class ProviderGenericRequestProcessor extends ProviderBaseRequestProcessor{
	@Resource
	protected HttpServletRequest httpRequest;
    protected HttpServletResponse httpResponse;
	private static final Logger LOGGER = Logger.getLogger(ProviderGenericRequestProcessor.class);
	
	// provider checkin checkout time update
	protected ProviderTimeOnSiteService timeOnSiteV3Service;
	
	/**
	 * This method adds the timeonsite/trip data to DB.
	 */
	@POST
	@Path("/providers/{providerId}/resources/{resourceId}/serviceorders/{soId}/timeonsite")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public ProviderTimeOnSiteResponse addTimeOnSiteV3(
			@PathParam("providerId") String providerId,@PathParam("resourceId") String resourceId,
			@PathParam("soId") String soId,
			ProviderTimeOnSiteRequest timeOnSiteRequest) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering ProviderTimeOnSiteService.addTimeOnSiteV3");
		}
		long addTimeOnSiteStart = System.currentTimeMillis();		
		// declare the response variables
		String timeOnSiteResponse = null;
		ProviderTimeOnSiteResponse mTimeOnSiteResponse = new ProviderTimeOnSiteResponse();

		// convert the request object to XML string
		// uses XStream toXML() method
		String request = convertReqObjectToXMLString(timeOnSiteRequest,
				ProviderTimeOnSiteRequest.class);

		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		// assign soId from the request URL
		apiVO.setSOId(soId);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} else {
			apiVO.setProviderResourceId(0);
		}

		// assign the XML string
		apiVO.setRequestFromPostPut(request);
		// assign the Request method as POST
		apiVO.setRequestType(RequestType.Post);

		// log the request
		// get resource Id from vendor_resource
		Integer resourceIdInt=timeOnSiteV3Service.validateResourceId(resourceId);
		Integer loggingId=timeOnSiteV3Service.logSOMobileHistory(request,ProviderAPIConstant.TIME_ON_SITE_V3, resourceIdInt,soId,ProviderAPIConstant.HTTP_POST);

		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of AdvancedTimeOnSiteService
		timeOnSiteResponse = timeOnSiteV3Service.doSubmit(apiVO);

		// convert the XML string to object
		mTimeOnSiteResponse = (ProviderTimeOnSiteResponse) convertXMLStringtoRespObject(
				timeOnSiteResponse, ProviderTimeOnSiteResponse.class);

		mTimeOnSiteResponse.setSoId(soId);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Leaving ProviderTimeOnSiteService.addTimeOnSiteV3");
		}
		// update response 
		timeOnSiteV3Service.updateSOMobileResponse(loggingId, timeOnSiteResponse);
		long addTimeOnSiteEnd = System.currentTimeMillis();
		LOGGER.info("Total time taken for timeonsite after logging in ms :::"+(addTimeOnSiteEnd-addTimeOnSiteStart));
		return mTimeOnSiteResponse;
	}
	
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}
	
	public ProviderTimeOnSiteService getTimeOnSiteV3Service() {
		return timeOnSiteV3Service;
	}


	public void setTimeOnSiteV3Service(ProviderTimeOnSiteService timeOnSiteV3Service) {
		this.timeOnSiteV3Service = timeOnSiteV3Service;
	}
}
