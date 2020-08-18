package com.newco.marketplace.api.server.v1_0;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.server.BaseRequestProcessor;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.buyerEventAck.BuyerEventAcknowledgementService;
import com.newco.marketplace.api.services.buyerEventCallback.BuyerEventCallbackService;
import com.newco.marketplace.api.services.so.BuyerDetailsEventCallbackService;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;


	
	/*This class would act as an intercepter for all the Buyer Callback APIs in the
	 * application. It handles the following services:
	 * 
	 * /buyers/{buyerId}/eventcallback/{eventId}
	 
	 */
@Path("v1.0")
public class BuyerEventCallbackProcessor extends BaseRequestProcessor {
		private Logger logger = Logger.getLogger(BuyerEventCallbackProcessor.class);
		

		private static final String MEDIA_TYPE_XML_STR = "application/xml";
		private static final String MEDIA_TYPE_JSON_STR = "application/json";
		
		protected CommonUtility commonUtility;
		protected BuyerDetailsEventCallbackService buyerDetailsEventService;
		protected BuyerEventCallbackService buyerEventService;
		protected BuyerEventAcknowledgementService buyerEventAcknowledgementService;

		@Resource
		protected HttpServletRequest httpRequest;
		@Resource
		protected HttpServletResponse httpResponse;
		
	
		/**
		 * This method returns the event details.
		 * @param buyerId
		 * @param actionId (event)
		 * @return Response
		 * URL: /buyers/{buyerId}/serviceDetails
		 */
		@SuppressWarnings("unchecked")
		@GET
		@Path("/buyers/{buyerId}/buyerdetail")	
		public Response getBuyerServiceDetails(@PathParam("buyerId") String buyerId){
			logger.info("Entering BuyerEventCallbackProcessor.getBuyerServiceDetails()");
			APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
			String buyerDetailsEventServiceResponse = null;
			apiVO.setRequestType(RequestType.Get);
			apiVO.setBuyerId(buyerId);
			buyerDetailsEventServiceResponse = buyerDetailsEventService.doSubmit(apiVO);
			logger.info("BuyerEventCallbackProcessor.getBuyerServiceDetails()");
			return Response.ok(buyerDetailsEventServiceResponse).build();
		}
		


		@SuppressWarnings("unchecked")
		@GET
		@Path("/buyers/{buyerId}/eventcallback/{actionId}")
		@Produces({ "application/xml", "application/json", "text/xml" })
		public Response getBuyerEventService(@PathParam("buyerId") String buyerId, @PathParam("actionId") String actionId) {
			logger.info("Entering EventCallbackProcessor.getBuyerEventService()");
			APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
			Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
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
			apiVO.setRequestType(RequestType.Get);
			apiVO.setRequestFromGetDelete(reqMap);
			apiVO.setBuyerId(buyerId);
			apiVO.addProperties(APIRequestVO.ACTION_ID, actionId);
			apiVO.setLocale(getHttpRequest().getLocale());
			String responseXML = buyerEventService.doSubmit(apiVO);
			logger.info("Leaving EventCallbackProcessor.getBuyerEventService()");
			return Response.ok(responseXML).build();
		}
		
		/**
		 * This method will update the notification acknowledgement.
		 * @param buyerId
		 * @param actionId (event)
		 * @return Response
		 */
		@SuppressWarnings("unchecked")
		@POST		
		@Path("/buyers/{buyerId}/notification/{notificationId}")
		@Consumes({ "application/xml", "application/json", "text/xml" })
		public Response acknwledgeBuyerCallback(@PathParam("buyerId") String buyerId, @PathParam("notificationId") String notificationId,
												String buyerCallbackAckRequest){
			logger.info("Entering BuyerEventCallbackProcessor.acknwledgeBuyerCallback()");
			APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
			apiVO.setBuyerId(buyerId);
			apiVO.addProperties("notificationId", notificationId);
			apiVO.addProperties("result", buyerCallbackAckRequest);
			apiVO.setRequestType(RequestType.Post);
			apiVO.setRequestFromPostPut(buyerCallbackAckRequest);
			//Validate request
			String responseXML = buyerEventAcknowledgementService.doSubmit(apiVO);
			return Response.ok(responseXML).build();
			
		}

		public BuyerDetailsEventCallbackService getBuyerDetailsEventService() {
			return buyerDetailsEventService;
		}

		public void setBuyerDetailsEventService(
				BuyerDetailsEventCallbackService buyerDetailsEventService) {
			this.buyerDetailsEventService = buyerDetailsEventService;
		}

		public CommonUtility getCommonUtility() {
			return commonUtility;
		}

		public void setCommonUtility(CommonUtility commonUtility) {
			this.commonUtility = commonUtility;
		}

		
		
		public HttpServletRequest getHttpRequest() {
			return httpRequest;
		}
		
		public HttpServletResponse getHttpResponse() {
			return httpResponse;
		}

		public void setHttpResponse(HttpServletResponse httpResponse) {
			this.httpResponse = httpResponse;
		}


		public void setHttpRequest(HttpServletRequest httpRequest) {
			this.httpRequest = httpRequest;
		}
		
		public BuyerEventCallbackService getBuyerEventService() {
			return buyerEventService;
		}

		public void setBuyerEventService(BuyerEventCallbackService buyerEventService) {
			this.buyerEventService = buyerEventService;
		}

		public BuyerEventAcknowledgementService getBuyerEventAcknowledgementService() {
			return buyerEventAcknowledgementService;
		}

		public void setBuyerEventAcknowledgementService(BuyerEventAcknowledgementService buyerEventAcknowledgementService) {
			this.buyerEventAcknowledgementService = buyerEventAcknowledgementService;
		}
	}
