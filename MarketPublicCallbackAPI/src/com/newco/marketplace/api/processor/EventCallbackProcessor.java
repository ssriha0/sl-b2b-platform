package com.newco.marketplace.api.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newco.marketplace.api.beans.EventCallbackResponse;
import com.newco.marketplace.api.beans.EventCallbackSoRequest;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.service.CloseOrderService;
import com.newco.marketplace.api.service.EventCallbackService;
import com.newco.marketplace.api.service.RescheduleOrderService;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.interfaces.CloseOrderConstants;
import com.newco.marketplace.interfaces.EventCallbackConstants;

@Path("v1.0")
public class EventCallbackProcessor {

	private static final Log logger = LogFactory.getLog(EventCallbackProcessor.class);

	private EventCallbackService eventCallbackService;
	private XStreamUtility conversionUtility;
	private CloseOrderService closeOrderSerivce;
	private RescheduleOrderService rescheduleOrderService;

	@POST
	@Path("sl-callback-service/{event}")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	public Response getResponseForEventCallback(@PathParam("event") String event, String eventCallbackRequest) {

		logger.info("Inside buyer event callback service for inhome");
		logger.info("Event : " + event);
		logger.debug("Request::" + eventCallbackRequest);

		EventCallbackResponse response = eventCallbackService.validateRequestParams(event, eventCallbackRequest);

		if (response == null) {
			EventCallbackSoRequest eventCallbackSoRequest = conversionUtility
					.getRetrieveRequestObject(eventCallbackRequest);
			logger.info("SO Request::" + eventCallbackSoRequest.toString());

			List<String> eventCallbackDetails = new ArrayList<String>();
			eventCallbackDetails.add(CloseOrderConstants.TECHHUB_AUTH_TOKEN);
			eventCallbackDetails.add(CloseOrderConstants.TECHHUB_AUTH_URL);
			eventCallbackDetails.add(CloseOrderConstants.TECHHUB_CALL_CLOSE_URL);
			Map<String, String> eventCallbackMap = eventCallbackService.getApplicationDetails(eventCallbackDetails);
			Result result = null;

			try{
				if (event.equals(EventCallbackConstants.CLOSE_ORDER_EVENT)) {
					result = closeOrderSerivce.execute(eventCallbackSoRequest, eventCallbackMap);
				} else if (event.equals(EventCallbackConstants.RESCHEDULE_ORDER_EVENT)) {
					result = rescheduleOrderService.execute(eventCallbackSoRequest, eventCallbackMap);
				}
			}
			catch (Exception e) {
				logger.info("Exception occured while generating the API request body:" + e.getMessage());
				Result failResult=new Result();
				failResult.setCode(CloseOrderConstants.ATTEMPT_REQUEST_MAPPING_ERROR_CD);
				failResult.setMessage(EventCallbackConstants.REQ_BODY_MAPPING_ERROR);
				result=failResult;
			}
			

			if (result != null && result.getCode() == EventCallbackConstants.SUCCESS_CODE) {
				response = eventCallbackService.getSuccessResponse(result);
			} else {
				response = eventCallbackService.getFailedResponse(result);
				String responseXML = conversionUtility.getEventCallbackResponseXML(response);
				if (result != null) {
					if (CloseOrderConstants.errorCodes.contains(result.getCode())) {
						return Response.status(EventCallbackConstants.BAD_REQUEST_CODE).entity(responseXML).build();
					} else {
						int code = Integer.parseInt(result.getCode());
						return Response.status(code).entity(responseXML).build();
					}
				}
			}

		} else {
			String responseXML = conversionUtility.getEventCallbackResponseXML(response);
			return Response.status(EventCallbackConstants.BAD_REQUEST_CODE).entity(responseXML).build();
		}

		String responseXML = conversionUtility.getEventCallbackResponseXML(response);
		return Response.ok(responseXML).build();
	}

	public CloseOrderService getCloseOrderSerivce() {
		return closeOrderSerivce;
	}

	public void setCloseOrderSerivce(CloseOrderService closeOrderSerivce) {
		this.closeOrderSerivce = closeOrderSerivce;
	}

	public EventCallbackService getEventCallbackService() {
		return eventCallbackService;
	}

	public void setEventCallbackService(EventCallbackService eventCallbackService) {
		this.eventCallbackService = eventCallbackService;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public RescheduleOrderService getRescheduleOrderService() {
		return rescheduleOrderService;
	}

	public void setRescheduleOrderService(RescheduleOrderService rescheduleOrderService) {
		this.rescheduleOrderService = rescheduleOrderService;
	}

}
