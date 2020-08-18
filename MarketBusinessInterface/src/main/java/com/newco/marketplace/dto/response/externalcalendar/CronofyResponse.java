package com.newco.marketplace.dto.response.externalcalendar;

import java.io.Serializable;
import java.util.List;

public class CronofyResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	int responseCode;
	Object result;
	List<Object> results;
	ErrorResponse errorResponse;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public List<Object> getResults() {
		return results;
	}
	public void setResults(List<Object> results) {
		this.results = results;
	}
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
}
