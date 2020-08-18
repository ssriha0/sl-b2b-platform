package com.newco.marketplace.web.dto.d2cproviderportal;

import java.io.Serializable;
import java.util.List;

public class ProviderPortalResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Object result;
	private List<? extends Object> results;
	private ErrorDto error;
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public ErrorDto getError() {
		return error;
	}
	public void setError(ErrorDto error) {
		this.error = error;
	}
	public List<? extends Object> getResults() {
		return results;
	}
	public void setResults(List<? extends Object> results) {
		this.results = results;
	}

}
