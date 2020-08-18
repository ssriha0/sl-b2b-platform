package com.servicelive.service.dataTokenization;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("errors")
public class Errors {
	
	@XStreamImplicit(itemFieldName="error")
	private List<String> error;

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}
	
	
}
