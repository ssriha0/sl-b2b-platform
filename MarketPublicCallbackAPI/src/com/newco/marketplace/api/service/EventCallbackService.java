package com.newco.marketplace.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.EventCallbackResponse;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.business.iBusiness.IEventCallbackBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.CallbackEvent;
import com.newco.marketplace.interfaces.EventCallbackConstants;


public class EventCallbackService {
	private Logger logger = Logger.getLogger(EventCallbackService.class);
	private IEventCallbackBO eventCallbackBO;
	
	public EventCallbackResponse validateRequestParams(String event, String eventCallbackRequest){
		
		EventCallbackResponse response = null;
		ArrayList<String> eventList=new ArrayList<>();
		
		if(event == null || event.isEmpty()){
			logger.error("Path param event is null");
			return getFailedResponse(EventCallbackConstants.PATH_PARAM_EVENT_ERROR_CD, EventCallbackConstants.PATH_PARAM_EVENT_ERROR);
		}
		
		if(event != null && StringUtils.isAlphanumeric(event) || StringUtils.isNumeric(event)){
			logger.error("Path param event is numeric or alphanumeric");
			return getFailedResponse(EventCallbackConstants.PARAM_EVENT_NUMERIC_ERROR_CD, EventCallbackConstants.PARAM_EVENT_NUMERIC_ERROR);
		}

		if(event != null){
			for(CallbackEvent callbackEvent: CallbackEvent.values()){ 
				eventList.add(callbackEvent.getValue());
			}

			if(!eventList.contains(event)){
				logger.error("Path param event is invalid");
				return getFailedResponse(EventCallbackConstants.INVALID_PARAM_EVENT, EventCallbackConstants.PARAM_EVENT_INVALID_ERROR);
			}
		}
		
		if(eventCallbackRequest == null || eventCallbackRequest.isEmpty()){
			logger.error("Request is null");
			return getFailedResponse(EventCallbackConstants.PATH_PARAM_REQUEST_ERROR_CD, EventCallbackConstants.PATH_PARAM_REQUEST_ERROR);
		}
		
		return response;
	}
	
	public EventCallbackResponse getFailedResponse(String errorCode, String message){
		
		EventCallbackResponse response = new EventCallbackResponse();
		Results results = new Results();
		List<Result> resultList=new ArrayList<Result>();
		List<ErrorResult> errorList=new ArrayList<ErrorResult>();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(message);
		errorResult.setCode(errorCode);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		response.setResults(results);
		return response;
	}
	
	public EventCallbackResponse getFailedResponse(Result result){
		
		EventCallbackResponse response = new EventCallbackResponse();
		Results results = new Results();
		List<Result> resultList=new ArrayList<Result>();
		List<ErrorResult> errorList=new ArrayList<ErrorResult>();
		ErrorResult errorResult = new ErrorResult();
		errorResult.setMessage(result.getMessage());
		errorResult.setCode(result.getCode());
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		response.setResults(results);
		return response;
	}

	public EventCallbackResponse getSuccessResponse(Result result){
	
	EventCallbackResponse response = new EventCallbackResponse();
	Results results = new Results();
	List<Result> resultList=new ArrayList<Result>();
	List<ErrorResult> errorList=new ArrayList<ErrorResult>();
	resultList.add(result);
	results.setResult(resultList);
	results.setError(errorList);
	response.setResults(results);
	return response;
}
	
	public Map<String, String> getApplicationDetails(List<String> eventCallbackDetails){
		
		Map<String, String> eventCallbackMap = null;
		try {
			eventCallbackMap = eventCallbackBO.getEventCallbackDetails(eventCallbackDetails);
		} catch (BusinessServiceException e) {
			
			e.printStackTrace();
		}
		return eventCallbackMap;
	}
	
	public IEventCallbackBO getEventCallbackBO() {
		return eventCallbackBO;
	}
	public void setEventCallbackBO(IEventCallbackBO eventCallbackBO) {
		this.eventCallbackBO = eventCallbackBO;
	}
	
}
